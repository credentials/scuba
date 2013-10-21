/*
 * $Id: $
 */

package net.sourceforge.scuba.simservice;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.smartcardio.CommandAPDU;
import javax.smartcardio.ResponseAPDU;

import net.sourceforge.scuba.smartcards.CardFileInputStream;
import net.sourceforge.scuba.smartcards.CardService;
import net.sourceforge.scuba.smartcards.CardServiceException;
import net.sourceforge.scuba.smartcards.FileInfo;
import net.sourceforge.scuba.smartcards.FileSystemStructured;
import net.sourceforge.scuba.util.Hex;

/**
 * A <code>CardService</code> for GSM SIM cards.
 *
 * @version $Revision: $
 *
 * @author Martijn Oostdijk (martijn.oostdijk@gmail.com)
 */
public class SIMService extends CardService
{
    public static final int CHV1 = SIMAPDUService.CHV1, CHV2 = SIMAPDUService.CHV2;

    private static final byte MODE_NEXT_RECORD = (byte) 0x02;
    private static final byte MODE_PREVIOUS_RECORD = (byte) 0x03;
    private static final byte MODE_ABSOLUTE = (byte) 0x04;

    private static final int BLOCK_SIZE = 127;

    private SIMAPDUService apduService;
    private FileSystemStructured fs;

    public SIMService(CardService service) {
        this.apduService = service instanceof SIMAPDUService ? (SIMAPDUService)service : new SIMAPDUService(service);
        fs = new SIMFileSystem(apduService);
    }

    public void open() throws CardServiceException {
        apduService.open();
    }

    public boolean isOpen() {
        return apduService.isOpen();
    }

    public ResponseAPDU transmit(CommandAPDU apdu) throws CardServiceException {
        return apduService.transmit(apdu);
    }

    public void close() {
        apduService.close();
    }

    /**
     * TODO: perhaps move this to SIMFileSystem?
     *
     * @param id either CHV1 or CHV2
     * @param pin ASCII string holding the CHV, such as "0000" or "1234"
     * @return number of remaining tries (FIXME: not implemented, always returns 3)
     * @throws CardServiceException
     *          if <code>id</code> is invalid
     *          or if <code>pin</code> has length greater than 8
     */
    public int verifyCHV(int id, String pin) throws CardServiceException {
        if (id != CHV1 && id != CHV2) { throw new CardServiceException("Illegal CHV selector"); }
        if (pin == null || pin.length() > 8) { throw new CardServiceException("Illegal PIN \"" + pin + "\""); }
        byte[] chv = new byte[8];
        for (int i = 0; i < pin.length(); i++) { chv[i] = (byte)pin.charAt(i); }
        for (int i = pin.length(); i < 8; i++) { chv[i] = (byte)0xFF; }
        ResponseAPDU rapdu = apduService.sendVerifyCHV(SIMAPDUService.CHV1, chv);
        /* FIXME: check rapdu, check attempts left in case of wrong PIN */
        return 3;
    }

    public InputStream readFile(short[] path) throws CardServiceException {
        for (short fid: path) { fs.selectFile(fid); }
        FileInfo[] filePath = fs.getSelectedPath();
        SIMFileInfo info = (SIMFileInfo)filePath[filePath.length - 1];
        if (!info.isEF()) { throw new CardServiceException("Cannot use readFile: non-EF file"); }
        if (!info.isTransparent()) { throw new CardServiceException("Cannot use readFile: non-transparent file"); }
        if (!info.isReadable()) { throw new CardServiceException("Cannot use readFile: Access denied"); }
        return new CardFileInputStream(BLOCK_SIZE, fs);
    }

    /* Reading complete EF files at once... */

    /**
     * Reads the EF file with file ID <code>fid</code>.
     * 
     * @param fid
     *            the file ID of the file to read.
     * 
     * @throws CardServiceException
     *             if there is no such file, or the file is not an EF file, or
     *             something else went wrong.
     */
    public EFFile readEFFile(short fid) throws CardServiceException {
        try {
            ResponseAPDU rapdu = apduService.sendSelect(fid);
            if ((short) rapdu.getSW() != GSM1111.SW_NO_ERROR) {
                throw new CardServiceException("File not found");
            }
            SIMFileInfo info = new SIMFileInfo(new ByteArrayInputStream(rapdu.getData()));
            if (info.isTransparent()) {
                return readTransparentEFFile(info, BLOCK_SIZE);
            } else if (info.isLinearFixed() || info.isCyclic()) {
                return readRecordEFFile(info);
            }
            throw new CardServiceException("Type of file [" + info + "] not supported");
        } catch (CardServiceException cte) {
            throw new CardServiceException("Error reading file");
        }
    }

    /**
     * Reads the EF file with path <code>path</code>. The path should consist of
     * file IDs of DFs (or of the MF) except for the last element, which should
     * be the file ID of an EF.
     * 
     * @param path
     *            the path.
     * 
     * @throws CardServiceException
     *             if there is no such valid path, or the file is not an EF
     *             file, or something else went wrong.
     */
    public EFFile readEFFile(short[] path) throws CardServiceException {
        try {
            if (path == null) {
                throw new CardServiceException("Invalid path");
            }
            for (int i = 0; i < path.length - 1; i++) {
                ResponseAPDU rapdu = apduService.sendSelect(path[i]);
                if ((short) rapdu.getSW() != GSM1111.SW_NO_ERROR) {
                    throw new CardServiceException("File not found");
                }
                SIMFileInfo info = new SIMFileInfo(new ByteArrayInputStream(rapdu.getData()));
                if (!info.isMF() && !info.isDF()) {
                    throw new CardServiceException("File [" + info + "] not found");
                }
            }
            return readEFFile(path[path.length - 1]);
        } catch (CardServiceException cte) {
            throw new CardServiceException("Error reading file");
        }
    }

    public int envelope(ProactiveCommand command) throws CardServiceException {
        ResponseAPDU rapdu = apduService.sendEnvelope(command.getEncoded());
        return getLength(rapdu.getSW());
    }

    public int terminalResponse(ProactiveCommand command) throws CardServiceException {
        ResponseAPDU rapdu = apduService.sendTerminalResponse(command.getEncoded());
        return getLength(rapdu.getSW());
    }

    public ProactiveCommand fetch(int length) throws CardServiceException {
        ResponseAPDU rapdu = apduService.sendFetch(length);
        byte[] data = rapdu.getData();
        if (data == null || data.length <= 0) { return null; }
        return ProactiveCommand.getInstance(new ByteArrayInputStream(data));
    }


    /**
     * Reads the currently selected transparant EF file. This sends a lot of
1    * <code>READ_BINARY</code> instructions to the card, each reading
     * <code>blockSize</code> bytes of the file.
     * 
     * @param info
     *            the file's info header.
     * 
     * @throws CardServiceException
     *             if the currently selected file is not a transparent EF file.
     * @throws CardServiceException
     *             if something went wrong.
     */
    private TransparentEFFile readTransparentEFFile(SIMFileInfo info, int blockSize)
    throws CardServiceException, CardServiceException {
        if (!info.isTransparent()) {
            throw new CardServiceException("Error reading file [" + info + "]");
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ResponseAPDU rapdu;
        int blocks = info.getLength() / blockSize;
        int rest = info.getLength() % blockSize;
        for (int i = 0; i < blocks; i++) {
            short offset = (short) ((i * blockSize) & 0x0000FFFF);
            byte length = (byte) (blockSize & 0x000000FF);
            rapdu = apduService.sendReadBinary(offset, length);
            if ((short) rapdu.getSW() != GSM1111.SW_NO_ERROR) {
                throw new CardServiceException("Error reading file [" + info + "]");
            }
            try {
                out.write(rapdu.getData());
            } catch (IOException ioe) {
                throw new CardServiceException("Error reading file [" + info + "]");
            }
        }
        if (rest > 0) {
            int i = blocks;
            short offset = (short) ((i * blockSize) & 0x0000FFFF);
            byte length = (byte) (rest & 0x000000FF);
            rapdu = apduService.sendReadBinary(offset, length);
            if ((short) rapdu.getSW() != GSM1111.SW_NO_ERROR) {
                throw new CardServiceException("Error reading file [" + info + "]");
            }
            try {
                out.write(rapdu.getData());
            } catch (IOException ioe) {
                throw new CardServiceException("Error reading file [" + info + "]");
            }
        }
        return new TransparentEFFile(info, out.toByteArray());
    }

    /**
     * Reads the currently selected record (linear fixed or cyclic) EF file.
     * This sends a lot of <code>READ_RECORD</code> instructions to the card.
     * 
     * @param info
     *            the file's info header.
     * 
     * @throws CardServiceException
     *             if the currently selected file is not a record (linear fixed
     *             or cyclic) EF file.
     * @throws CardServiceException
     *             if something went wrong.
     */
    private RecordEFFile readRecordEFFile(SIMFileInfo info)
    throws CardServiceException, CardServiceException {
        if (!info.isLinearFixed() && !info.isCyclic()) {
            throw new CardServiceException("Error reading file [" + info + "]");
        }
        RecordEFFile result = new RecordEFFile(info);
        int recordSize = info.getRecordSize();
        int size = info.getLength();
        int records = size / recordSize;
        for (int i = 0; i < records; i++) {
            byte recordNumber = (byte) 0x00;
            byte mode = MODE_NEXT_RECORD;
            byte length = (byte) (recordSize & 0x000000FF);
            ResponseAPDU rapdu = apduService.sendReadRecord(recordNumber, mode, length);
            if ((short) rapdu.getSW() != GSM1111.SW_NO_ERROR) {
                throw new CardServiceException("Error reading file [" + info
                        + "]");
            }
            result.appendRecord(rapdu.getData());
        }
        return result;
    }

    private int getLength(int sw) throws CardServiceException {
        int sw1 = (sw & 0xFF00) >> 8, sw2 = sw & 0xFF;
        if (sw != 0x9000 && sw1 != 0x91) {
            throw new CardServiceException("Command failed (status word not 9000 and nog 91xx)", sw);
        }
        return sw2;
    }
}

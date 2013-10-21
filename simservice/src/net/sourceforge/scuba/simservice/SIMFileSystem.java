package net.sourceforge.scuba.simservice;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.smartcardio.ResponseAPDU;

import net.sourceforge.scuba.smartcards.CardServiceException;
import net.sourceforge.scuba.smartcards.FileInfo;
import net.sourceforge.scuba.smartcards.FileSystemStructured;

public class SIMFileSystem implements FileSystemStructured
{
    private static final int BLOCK_SIZE = 127;

    private SIMAPDUService apduService;
    private final List<SIMFileInfo> path;
    private int blockSize;
    private byte[] CHV1;

    public SIMFileSystem (SIMAPDUService apduService) {
        this(apduService, BLOCK_SIZE);
    }
    
    private SIMFileSystem(SIMAPDUService apduService, int blockSize) {
        this.apduService = apduService;
        this.path = new ArrayList<SIMFileInfo>();
        this.blockSize = blockSize;
    }

    public FileInfo getFileInfo() throws CardServiceException {
        if (path.size() < 1) { selectFile(GSM1111.MF); }
        if (path.size() < 1) { throw new CardServiceException("No file selected and selecting MF failed"); }
        return path.get(path.size() - 1);
    }

    public FileInfo[] getSelectedPath() {
        FileInfo[] result = new FileInfo[path.size()];
        return path.toArray(result);
    }

    public void selectFile(short fid) throws CardServiceException {
        ResponseAPDU rapdu = apduService.sendSelect(fid);
        int sw = rapdu.getSW();
        if (sw != GSM1111.SW_NO_ERROR) { throw new CardServiceException("Error selecting file " + Integer.toHexString(fid & 0xFFFF) + " resulted in " + Integer.toHexString(sw & 0xFFFF)); }
        byte[] data = rapdu.getData();
        SIMFileInfo oldSelectedFile = null;
        if (path.size() >= 1) { oldSelectedFile = path.get(path.size() - 1); }
        SIMFileInfo newSelectedFile = new SIMFileInfo(new ByteArrayInputStream(data));
        if (fid == GSM1111.MF) {
            /* Changing back to root. */
            path.clear();
            path.add(newSelectedFile);
        } else if (oldSelectedFile == null || oldSelectedFile.isDirectory()) {
            /* Adding child, go one lever deeper. */
            path.add(newSelectedFile);
        } else {
            /* Adding sibling, remove previously selected EF. */
            path.remove(path.size() - 1);
            path.add(newSelectedFile);
        }
    }

    /**
     * Returns a fragment of the currently selected file.
     * Only works on transparent files.
     * 
     * @param offset an offset
     * @param length a length
     *
     * @return a byte array of at most <code>length</code> bytes
     * 
     * @throws CardServiceException when called on MF, DF or non-transpartent EF
     */
    public byte[] readBinary(int offset, int length) throws CardServiceException {
        SIMFileInfo selectedFile = (SIMFileInfo)getFileInfo();
        if (selectedFile == null) { throw new CardServiceException("No file selected"); }
        if (!selectedFile.isEF() || !selectedFile.isTransparent()) { throw new CardServiceException("Cannot use READ BINARY: non-transpartent file " + selectedFile); }
        length = Math.max(length, selectedFile.getLength() - offset);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ResponseAPDU rapdu;
        int blocks = length / blockSize;
        int rest = length % blockSize;

        for (int i = 0; i < blocks; i++) {
            short internalOffset = (short) ((offset + i * blockSize) & 0x0000FFFF);
            byte internalLength = (byte) (blockSize & 0x000000FF);
            rapdu = apduService.sendReadBinary(internalOffset, internalLength);
            int sw = rapdu.getSW();
            if (sw != GSM1111.SW_NO_ERROR) {
                throw new CardServiceException("Error using READ BINARY: reading " + selectedFile + " resulted in SW " + Integer.toHexString(sw));
            }
            try {
                out.write(rapdu.getData());
            } catch (IOException ioe) {
                throw new CardServiceException("Error using READ BINARY: reading " + selectedFile + " resulted in: " + ioe.getMessage());
            }
        }
        if (rest > 0) {
            int i = blocks;
            short internalOffset = (short) ((offset + i * blockSize) & 0x0000FFFF);
            byte internalLength = (byte) (rest & 0x000000FF);
            rapdu = apduService.sendReadBinary(internalOffset, internalLength);
            int sw = rapdu.getSW();
            if (sw != GSM1111.SW_NO_ERROR) {
                throw new CardServiceException("Error using READ BINARY: reading " + selectedFile + " resulted in SW " + Integer.toHexString(sw));
            }
            try {
                out.write(rapdu.getData());
            } catch (IOException ioe) {
                throw new CardServiceException("Error using READ BINARY: reading " + selectedFile + " resulted in: " + ioe.getMessage());
            }
        }
        return out.toByteArray();
    }
    
    public void setPIN(String pin) throws CardServiceException {
        if (pin == null || pin.length() > 8) { throw new CardServiceException("Illegal PIN \"" + pin + "\""); }
        byte[] chv = new byte[8];
        for (int i = 0; i < chv.length; i++) { chv[i] = (byte)0xFF; }
        for (int i = 0; i < pin.length(); i++) { chv[i] = (byte)pin.charAt(i); }
        CHV1 = chv;
    }
}   

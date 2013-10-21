/*
 * $Id: $
 */

package net.sourceforge.scuba.simservice;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

import net.sourceforge.scuba.smartcards.CardServiceException;
import net.sourceforge.scuba.smartcards.FileInfo;
import net.sourceforge.scuba.util.Hex;

/**
 * A representation of the file information header as received in
 * response to a <i>select</i> or <i>status</i> command.
 * See 9.2.1 of ETSI TS 100 977.
 *
 * @version $Revision: 1.1 $
 *
 * @author Martijn Oostdijk (martijn.oostdijk@gmail.com)
 */
public class SIMFileInfo extends FileInfo
{
    public static final int
    AC_ALW = 0x0, AC_CHV1 = 0x1, AC_CHV2 = 0x2, AC_RFU = 0x3,
    AC_ADM1 = 0x4,
    AC_ADM2 = 0x5,
    AC_ADM3 = 0x6,  
    AC_ADM4 = 0x7,
    AC_ADM5 = 0x8,
    AC_ADM6 = 0x9,
    AC_ADM7 = 0xA,
    AC_ADM8 = 0xB,
    AC_ADM9 = 0xC,
    AC_ADM10 = 0xD,
    AC_ADM11 = 0xE,
    AC_NEV = 0xF;

    private static final byte TYPE_MF = 0x01, TYPE_DF = 0x02, TYPE_EF = 0x04;
    
    private static final int STATUS_SECRET_CODE_INITIALIZED = 0x80;

    private int size;
    private short fid;
    private byte fileType;
    private byte fileStructure;
    private int fileStatus;
    private int recordSize;
    private int
    readAndSeekAC, updateAC,
    rfuAC, increaseAC,
    invalidateAC, rehabilitateAC;

    /**
     * Constructs a new file info header.
     *
     * @param data the APDU data sent by the card in response to a
     *    <i>select</i> or <i>status</i> command.
     */
    public SIMFileInfo(InputStream data) throws CardServiceException {
        if (data == null) { throw new CardServiceException("Invalid data"); }
        try {
            DataInputStream in = new DataInputStream(data);
            in.skipBytes(2);                              // 1-2 RFU
            size = in.readUnsignedShort();                // 3-4
            fid = in.readShort();                         // 5-6
            fileType = in.readByte();                     // 7
            switch (fileType) {
            case TYPE_MF:
            case TYPE_DF:
                in.skipBytes(4);                          // 8-12 RFU
                int count = in.readUnsignedByte();            // 13
                byte[] gsmSpecificData = new byte[count];
                in.readFully(gsmSpecificData);            // 14-34
                break;
            case TYPE_EF:
                in.skipBytes(1);                          // 8
                byte[] accessConditions = new byte[3];
                in.readFully(accessConditions);           // 9-11
                readAndSeekAC = accessConditions[0] & 0xF;
                updateAC = (accessConditions[0] >> 4) & 0xF;
                increaseAC = (accessConditions[1] >> 4) & 0xF;
                rfuAC = accessConditions[1] & 0xF;
                rehabilitateAC = (accessConditions[2] >> 4) & 0xF;
                invalidateAC = accessConditions[2] & 0xF;
                fileStatus = in.readUnsignedByte();       // 12
                System.out.println("DEBUG: fileStatus = " + Integer.toHexString(fileStatus));
                count = in.readUnsignedByte();            // 13
                if (count >= 1) {
                    fileStructure = in.readByte();        // 14
                }
                if (count >= 2) {
                    recordSize = in.readUnsignedByte();   // 15
                }
                break;
            }
        } catch (IOException e) {
            throw new CardServiceException("Invalid response APDU.");
        }
    }

    /**
     * Gets the file ID.
     *
     * @return the file ID.
     */
    public short getFileID() {
        return fid;
    }
    
    public boolean isReadable() {
        switch (readAndSeekAC) {
        case AC_ALW: return true;
        case AC_CHV1:
        case AC_CHV2:
            return (fileStatus & STATUS_SECRET_CODE_INITIALIZED) == STATUS_SECRET_CODE_INITIALIZED;
        case AC_NEV: return false;
        default: return true;
        }
    }
    
    public int getReadAndSeekAC() {
        return readAndSeekAC;
    }

    /**
     * Indicates whether this file is an MF.
     *
     * @return a boolean indicating whether this file is an MF.
     */
    public boolean isMF() {
        return (byte)(fileType & TYPE_MF) == TYPE_MF;
    }

    /**
     * Indicates whether this file is an DF.
     *
     * @return a boolean indicating whether this file is an DF.
     */
    public boolean isDF() {
        return (byte)(fileType & TYPE_DF) == TYPE_DF;
    }

    /**
     * Indicates whether this file is an EF.
     *
     * @return a boolean indicating whether this file is an EF.
     */
    public boolean isEF() {
        return (byte)(fileType & TYPE_EF) == TYPE_EF;
    }

    /**
     * Indicates whether this file is an DF.
     *
     * @return a boolean indicating whether this file is an DF.
     */
    public boolean isDirectory() {
        return (byte)(fileType & TYPE_DF) == TYPE_DF;
    }

    /**
     * Indicates whether this file is a transparent EF.
     *
     * @return a boolean indicating whether this file is a transparent EF.
     */
    public boolean isTransparent() {
        return isEF() && (fileStructure == 0x00);
    }

    /**
     * Indicates whether this file is a linear fixed EF.
     *
     * @return a boolean indicating whether this file is a linear fixed EF.
     */
    public boolean isLinearFixed() {
        return isEF() && (fileStructure == 0x01);
    }

    /**
     * Indicates whether this file is a cyclic EF.
     *
     * @return a boolean indicating whether this file is a cyclic EF.
     */
    public boolean isCyclic() {
        return isEF() && (fileStructure == 0x03);
    }

    /**
     * Indicates whether this file is a variable EF.
     *
     * @return a boolean indicating whether this file is a variable EF.
     */
    public boolean isVariable() {
        return false; // TODO: default value?
    }

    /**
     * Gets the length of this file.
     *
     * @return the total length of this file.
     */
    public int getLength() {
        return size;
    }

    /**
     * If this file is a record file gets the length of one record.
     *
     * @return the length of one record.
     */
    public int getRecordSize() {
        return recordSize;
    }

    /**
     * The header of this file, not implemented. FIXME: implement this.
     *
     * @return <code>null</code>.
     */
    public byte[] getHeader() {
        return null;
    }

    /**
     * Gets a textual representation of this object.
     *
     * @return a textual representation of this object.
     */
    public String toString() {
        StringBuffer result = new StringBuffer();
        result.append("[");
        result.append(Hex.shortToHexString(fid));
        result.append(": ");
        result.append(isMF() ? "MF" : (isDF() ? "DF" : (isEF() ? "EF" : "")));
        result.append(isTransparent() ? ",T" : (isLinearFixed() ? ",LF" : (isCyclic() ? ",C" : "")));
        switch (readAndSeekAC) {
        case AC_ALW: result.append(",r: ALW"); break;
        case AC_CHV1: result.append(",r: CHV1"); break;
        case AC_CHV2: result.append(",r: CHV2"); break;
        case AC_RFU: result.append(", r: RFU"); break;
        case AC_NEV: result.append(",r: NEW"); break;
        default: result.append(",r: ADM"); break;
        }
        result.append("]");
        return result.toString();
    }

    public short getFID() {
        return fid;
    }

    public int getFileLength() {
        return size;
    }
}


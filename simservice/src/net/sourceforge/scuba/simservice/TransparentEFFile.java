/*
 * $Id: $
 */

package net.sourceforge.scuba.simservice;

import net.sourceforge.scuba.smartcards.CardServiceException;
import net.sourceforge.scuba.util.Hex;

/**
 * A representation of transparent EF files.
 * Described in 6.4.1 of ETSI TS 100 977.
 * 
 * @version $Revision: 1.1 $
 *
 * @author Martijn Oostdijk (martijn.oostdijk@gmail.com)
 */
public class TransparentEFFile extends EFFile
{
    /** The info (header) of this file. */
    SIMFileInfo info;

    /** The body of this file. */
    byte[] body;

    /**
     * Constructs a new transparent EF file.
     *
     * @param info The info of the new file.
     * @param body The body of the new file.
     */
    public TransparentEFFile(SIMFileInfo info, byte[] body)
    throws CardServiceException {
        if (!info.isTransparent()) {
            throw new CardServiceException("Invalid file info");
        }
        this.info = info;
        if (body != null) {
            this.body = new byte[body.length];
            System.arraycopy(body,0,this.body,0,body.length);
        }
    }

    /**
     * Gets the file info (header) of this file.
     *
     * @return The file info (header) of this file.
     */
    public SIMFileInfo getFileInfo() {
        return info;
    }

    /**
     * Gets the body of this file.
     *
     * @return The body of this file.
     */
    public byte[] getBody() {
        return body;
    }

    /**
     * Gets a textual representation of this object.
     *
     * @return a textual representation of this object.
     */
    public String toString() {
        String result = info.toString() + ":\n";
        result += Hex.bytesToPrettyString(body);
        return result;
    }
}


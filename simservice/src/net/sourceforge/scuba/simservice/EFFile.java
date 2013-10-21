/*
 * $Id: $
 */

package net.sourceforge.scuba.simservice;

/**
 * A representation of EF files.
 * Described in 6.4 of ETSI TS 100 977.
 *
 * @version $Revision: 1.1 $
 *
 * @author Martijn Oostdijk (martijn.oostdijk@gmail.com)
 */
public abstract class EFFile
{
    /**
     * Clients should not construct instances of this class.
     */
    protected EFFile() {
    }

    /**
     * Gets the file info (header) of this file.
     *
     * @return the file info (header) of this file.
     */
    public abstract SIMFileInfo getFileInfo();

    /**
     * Gets the identifier of this file.
     *
     * @return the identifier of this file.
     */
    public short getFileID() {
        return getFileInfo().getFileID();
    }

    /**
     * Gets the length of this file.
     *
     * @return the length of this file.
     */
    public int getLength() {
        return getFileInfo().getLength();
    }

    /**
     * Indicates whether this file is a
     * transparent file.
     *
     * @return a boolean indidcating whether
     *    this file is a transparent file.
     */
    public boolean isTransparent() {
        return getFileInfo().isTransparent();
    }

    /**
     * Indicates whether this file is a
     * linear fixed record file.
     *
     * @return a boolean indidcating whether
     *    this file is a linear fixed record file.
     */
    public boolean isLinearFixed() {
        return getFileInfo().isLinearFixed();
    }

    /**
     * Indicates whether this file is a
     * cyclic record file.
     *
     * @return a boolean indidcating whether
     *    this file is a cyclic record file.
     */
    public boolean isCyclic() {
        return getFileInfo().isCyclic();
    }

    /**
     * Indicates whether this file is a
     * DF file (it is not).
     *
     * @return since this is an EF file, <code>false</code>.
     */
    public boolean isDF() {
        return false;
    }

    /**
     * Indicates whether this file is a
     * directory (it is not).
     *
     * @return since this is an EF file, <code>false</code>.
     */
    public boolean isDirectory() {
        return false;
    }

    /**
     * Indicates whether this file is an
     * EF file (it is).
     *
     * @return since this is an EF file, <code>true</code>.
     */
    public boolean isEF() {
        return true;
    }

    /**
     * Indicates whether this file is an
     * EF file (it is).
     *
     * @return since this is an EF file, <code>true</code>.
     */
    public boolean isFile() {
        return true;
    }
}


/*
 * This file is part of the SCUBA smart card framework.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA
 *
 * Copyright (C) 2009-2013 The SCUBA team.
 *
 * $Id: $
 */

package net.sourceforge.scuba.smartcards;

/**
 * Interface for minimal ISO file systems.
 * Implement this interface to tell {@link CardFileInputStream}
 * how to deal with card files.
 * 
 * @author Martijn Oostdijk (martijn.oostdijk@gmail.com)
 */
public interface FileSystemStructured {

    /**
     * Selects a file.
     * 
     * @param fid indicates which file to select
     * @throws CardServiceException in case of error
     */
    void selectFile(short fid) throws CardServiceException;

    /**
     * Reads a fragment of the currently selected file.
     * 
     * @param offset offset
     * @param length the number of bytes to read (the result may be shorter, though)
     * @return contents of currently selected file, contains at least 1 byte, at most length.
     * @throws CardServiceException on error (for instance: end of file)
     */
    byte[] readBinary(int offset, int length) throws CardServiceException;

    /**
     * Identifies the currently selected file.
     * 
     * @return a path of file identifiers or <code>null</code>.
     */
    FileInfo[] getSelectedPath() throws CardServiceException;
}

/* 
 * This file is part of the SCUBA smart card framework.
 * 
 * SCUBA is free software: you can redistribute it and/or modify it under the 
 * terms of the GNU General Public License as published by the Free Software 
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * SCUBA is distributed in the hope that it will be useful, but WITHOUT ANY 
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS 
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more 
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * SCUBA. If not, see <http://www.gnu.org/licenses/>.
 * 
 * Copyright (C) 2009-2012 The SCUBA team.
 * 
 * $Id: FileSystemStructured.java 203 2012-11-06 11:25:05Z martijno $
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
	FileInfo[] getSelectedPath();
}

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
 * $Id: CardFileInputStream.java 198 2012-10-25 20:39:05Z martijno $
 */

package net.sourceforge.scuba.smartcards;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

/**
 * Inputstream for reading files on ISO 7816 file system cards.
 * 
 * @author Martijn Oostdijk (martijn.oostdijk@gmail.com)
 */
public class CardFileInputStream extends InputStream {

	private FileInfo[] path;
	private final byte[] buffer;
	private int bufferLength;
	private int offsetBufferInFile;
	private int offsetInBuffer;
	private int markedOffset;
	private int fileLength;
	private FileSystemStructured fs;

	public CardFileInputStream(int maxBlockSize, FileSystemStructured fs) throws CardServiceException {
		this.fs = fs;
		synchronized(fs) {
			FileInfo[] fsPath = fs.getSelectedPath();
			if (fsPath == null || fsPath.length < 1) { throw new CardServiceException("No valid file selected"); }
			this.path = new FileInfo[fsPath.length];
			System.arraycopy(fsPath, 0, this.path, 0, fsPath.length);
			fileLength = fsPath[fsPath.length - 1].getFileLength();
			buffer = new byte[maxBlockSize];
			bufferLength = 0;
			offsetBufferInFile = 0;
			offsetInBuffer = 0;
			markedOffset = -1;
		}
	}

	public int read() throws IOException {
		synchronized(fs) {
			int offsetInFile = offsetBufferInFile + offsetInBuffer;
			if (offsetInFile >= fileLength) {
				return -1;
			}
			if (offsetInBuffer >= bufferLength) {
				int le = Math.min(buffer.length, fileLength - offsetInFile);
				try {
					offsetBufferInFile += bufferLength;
					offsetInBuffer = 0;
					bufferLength = fillBufferFromFile(path, offsetBufferInFile, le);
				} catch (CardServiceException cse) {
					throw new IOException(cse.toString());
				}
			}
			int result = buffer[offsetInBuffer] & 0xFF;
			offsetInBuffer++;
			return result;
		}
	}

	public long skip(long n) {
		int available = available();
		if (n > available) {
			n = available;
		}
		if (n < (buffer.length - offsetInBuffer)) {
			offsetInBuffer += n;
		} else {
			int absoluteOffset = offsetBufferInFile + offsetInBuffer;
			offsetBufferInFile = (int) (absoluteOffset + n);
			offsetInBuffer = 0;
		}
		return n;
	}

	public synchronized int available() {
		return bufferLength - offsetInBuffer;
	}

	public void mark(int readLimit) {
		markedOffset = offsetBufferInFile + offsetInBuffer;
	}

	public void reset() throws IOException {
		if (markedOffset < 0) {
			throw new IOException("Mark not set");
		}
		offsetBufferInFile = markedOffset;
		offsetInBuffer = 0;
		bufferLength = 0;
	}

	public boolean markSupported() {
		return true;
	}

	/**
	 * Gets the length of the underlying card file.
	 * 
	 * @return the length of the underlying card file.
	 */
	public int getFileLength() {
		return fileLength;
	}

	public int getFilePos() {
		return offsetBufferInFile + offsetInBuffer;
	}

	/**
	 * Reads from file with id <code>fid</code>.
	 * 
	 * @param fid
	 *            the file to read
	 * @param offsetInFile
	 *            starting offset in file
	 * @param length
	 *            the number of bytes to read, or -1 to read until EOF
	 * 
	 * @return the contents of the file.
	 */
	private int fillBufferFromFile(FileInfo[] path, int offsetInFile, int le)
			throws CardServiceException {
		synchronized (fs) {
			if (le > buffer.length) {
				throw new IllegalArgumentException("length too big");
			}
			if (!Arrays.equals(fs.getSelectedPath(), path)) {
				for (FileInfo fileInfo: path) { fs.selectFile(fileInfo.getFID()); }
			}
			byte[] data = fs.readBinary(offsetInFile, le);
			System.arraycopy(data, 0, buffer, 0, data.length);
			return data.length;
		}
	}
}

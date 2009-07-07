/*
 * SCUBA smart card framework.
 *
 * Copyright (C) 2009  The SCUBA team.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * $Id: $
 */

package net.sourceforge.scuba.smartcards;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Vector;

/**
 * Inputstream for reading ISO 7816 file system cards.
 * 
 * @author Martijn Oostdijk (martijn.oostdijk@gmail.com)
 */
public class CardFileInputStream extends InputStream {
	private short[] path;
	private final byte[] buffer;
	private int bufferLength;
	private int offsetBufferInFile;
	private int offsetInBuffer;
	private int markedOffset;
	private int fileLength;
	private FileSystemStructured fs;

	public CardFileInputStream(short[] path, int maxBlockSize, FileSystemStructured fs) throws CardServiceException {
		if (path == null) { throw new CardServiceException("Path is null"); }
		this.path = new short[path.length];
		System.arraycopy(path, 0, this.path, 0, path.length);
		this.fs = fs;
		for (short fid: path) { fs.selectFile(fid); }
		fileLength = fs.getFileInfo().getFileLength();
		buffer = new byte[maxBlockSize];
		bufferLength = 0;
		offsetBufferInFile = 0;
		offsetInBuffer = 0;
		markedOffset = -1;
	}

	public CardFileInputStream(short fid, int maxBlockSize, FileSystemStructured fs) throws CardServiceException {
		this(createSingletonPath(fid), maxBlockSize, fs);
	}
	
	private static short[] createSingletonPath(short fid) {
		short[] path = { fid };
		return path;
	}

	public int read() throws IOException {
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
	private int fillBufferFromFile(short[] path, int offsetInFile, int le)
	throws CardServiceException {
		synchronized (fs) {
			if (le > buffer.length) {
				throw new IllegalArgumentException("length too big");
			}
			if (!Arrays.equals(fs.getSelectedPath(), path)) {
				for (short fid: path) { fs.selectFile(fid); }
			}
			byte[] data = fs.readBinary((short) offsetInFile, le);
			System.arraycopy(data, 0, buffer, 0, data.length);
			return data.length;
		}
	}

	/**
	 * @return the contents of the file
	 */
	public byte[] toByteArray() {
		try {
			Vector<Integer> vec = new Vector<Integer>();
			int c = 0;
			while ((c = read()) != -1) {
				vec.add(new Integer(c));
			}
			byte[] result = new byte[vec.size()];
			int index = 0;
			for (Integer i : vec) {
				result[index++] = i.byteValue();
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}

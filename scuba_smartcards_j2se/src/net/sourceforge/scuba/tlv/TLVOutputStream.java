/*
 * SCUBA smart card framework.
 *
 * Copyright (C) 2009 - 2011  The SCUBA team.
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

package net.sourceforge.scuba.tlv;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * TLV output stream.
 * 
 * @author Martijn Oostdijk (martijn.oostdijk@gmail.com)
 * 
 * @version $Revision: $
 */
public class TLVOutputStream extends OutputStream {

	private DataOutputStream out;
	private TLVFullState state;

	public TLVOutputStream(OutputStream out) {
		this.out = out instanceof DataOutputStream ? (DataOutputStream)out : new DataOutputStream(out);
		this.state = new TLVFullState();
	}

	public void writeTag(int tag) throws IOException {
		byte[] tagAsBytes = TLVUtil.getTagAsBytes(tag);
		if (state.canBeWritten()) {
			out.write(tagAsBytes);
		}
		state.setTagProcessed(tag);
	}

	public void writeLength(int length) throws IOException {
		byte[] lengthAsBytes = TLVUtil.getLengthAsBytes(length);
		state.setLengthProcessed(length);
		if (state.canBeWritten()) {
			out.write(lengthAsBytes);
		}
	}

	/**
	 * Writes a value at once.
	 * If no tag was previously written, an exception is thrown.
	 * If no length was previously written, this method will write the length before writing <code>value</code>.
	 * If length was previously written, this method will check whether the length is consistent with <code>value</code>'s length. 
	 * 
	 * @param value the value to write
	 * 
	 * @throws IOException on error writing to the underlying output stream
	 */
	public void writeValue(byte[] value) throws IOException {
		if (value == null) { throw new IllegalArgumentException("Cannot write null."); }
		if (state.isAtStartOfTag()) { throw new IllegalStateException("Cannot write value bytes yet. Need to write a tag first."); }
		if (state.isAtStartOfLength()) {
			writeLength(value.length);
			write(value);
		} else {
			write(value);
			state.updatePreviousLength(value.length);
		}
	}

	public void write(int b) throws IOException {
		write(new byte[]{ (byte)b }, 0, 1);
	}

	public void write(byte[] bytes) throws IOException {
		write(bytes, 0, bytes.length);
	}

	public void write(byte[] bytes, int offset, int length) throws IOException {
		if (state.isAtStartOfTag()) {
			throw new IllegalStateException("Cannot write value bytes yet. Need to write a tag first.");
		}
		if (state.isAtStartOfLength()) {
			state.setDummyLengthProcessed();
		}
		state.updateValueBytesProcessed(bytes, offset, length);
		if (state.canBeWritten()) {
			out.write(bytes, offset, length);
		}
	}

	public void writeValueEnd() throws IOException {
		if (state.isAtStartOfLength()) { throw new IllegalStateException("Not processing value yet."); }
		if (state.isAtStartOfTag() && !state.isDummyLengthSet()) {
			return; /* TODO: check if this case ever happens. */
		}
		byte[] bufferedValueBytes = state.getValue();
		int length = bufferedValueBytes.length;
		state.updatePreviousLength(length);
		if (state.canBeWritten()) {
			byte[] lengthAsBytes = TLVUtil.getLengthAsBytes(length);
			out.write(lengthAsBytes);
			out.write(bufferedValueBytes);
		}
	}

	public void flush() throws IOException {
		out.flush();
	}

	public void close() throws IOException {
		if (!state.canBeWritten()) {
			throw new IllegalStateException("Cannot close stream yet, illegal TLV state.");
		}
		out.close();
	}
}

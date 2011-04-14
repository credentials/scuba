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

import java.io.IOException;
import java.io.*;

/**
 * TLV output stream.
 * 
 * @author Martijn Oostdijk (martijn.oostdijk@gmail.com)
 * 
 * @version $Revision: $
 */
public class TLVOutputStream extends OutputStream {

	private DataOutputStream out;
	private TLVState state;

	public TLVOutputStream(OutputStream out) {
		this.out = out instanceof DataOutputStream ? (DataOutputStream)out : new DataOutputStream(out);
		this.state = new TLVState();
	}

	public void writeTag(int tag) throws IOException {
		byte[] tagAsBytes = TLVUtil.getTagAsBytes(tag);
		out.write(tagAsBytes);
		state.setTagProcessed(tag, tagAsBytes.length);
	}

	public void writeLength(int length) throws IOException {
		byte[] lengthAsBytes = TLVUtil.getLengthAsBytes(length);
		out.write(lengthAsBytes);
		state.setLengthProcessed(length, lengthAsBytes.length);
	}

	public void writeValue(byte[] value) throws IOException {
		if (state.isAtStartOfTag()) {
			throw new IllegalStateException("Cannot write a value yet. Need to write a tag first.");
		}
		if (state.isAtStartOfLength()) {
			writeLength(value.length);
		}
		out.write(value);
		state.updateValueBytesProcessed(value.length);
	}

	public void write(int b) throws IOException {
		out.write(b);
		state.updateValueBytesProcessed(1);
	}
}

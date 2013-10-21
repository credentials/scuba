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

package net.sourceforge.scuba.tlv;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * TLV output stream.
 * 
 * @author Martijn Oostdijk (martijn.oostdijk@gmail.com)
 * 
 * @version $Revision: 186 $
 */
public class TLVOutputStream extends OutputStream {

    private DataOutputStream outputStream;
    private TLVOutputState state;

    public TLVOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream instanceof DataOutputStream ? (DataOutputStream)outputStream : new DataOutputStream(outputStream);
        this.state = new TLVOutputState();
    }

    public void writeTag(int tag) throws IOException {
        byte[] tagAsBytes = TLVUtil.getTagAsBytes(tag);
        if (state.canBeWritten()) {
            outputStream.write(tagAsBytes);
        }
        state.setTagProcessed(tag);
    }

    public void writeLength(int length) throws IOException {
        byte[] lengthAsBytes = TLVUtil.getLengthAsBytes(length);
        state.setLengthProcessed(length);
        if (state.canBeWritten()) {
            outputStream.write(lengthAsBytes);
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
            outputStream.write(bytes, offset, length);
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
            outputStream.write(lengthAsBytes);
            outputStream.write(bufferedValueBytes);
        }
    }

    public void flush() throws IOException {
        outputStream.flush();
    }

    public void close() throws IOException {
        if (!state.canBeWritten()) {
            throw new IllegalStateException("Cannot close stream yet, illegal TLV state.");
        }
        outputStream.close();
    }
}

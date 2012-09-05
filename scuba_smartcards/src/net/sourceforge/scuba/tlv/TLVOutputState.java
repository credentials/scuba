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
 * $Id: TLVOutputState.java 183 2012-09-04 18:54:58Z pimvullers $
 */

package net.sourceforge.scuba.tlv;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Stack;

import net.sourceforge.scuba.util.Hex;

/**
 * State to keep track of where we are in a TLV stream.
 * This variant also stores values that were encountered, to be used in
 * {@link TLVOutputStream}.
 * 
 * @author Martijn Oostdijk (martijn.oostdijk@gmail.com)
 * 
 * @version $Revision: 183 $
 */
class TLVOutputState implements Cloneable
{	
	/**
	 * Encoded the tags, lengths, and (partial) values.
	 */
	private Stack<TLVStruct> state;

	/**
	 * Encoded position, only one can be true.
	 * 
	 * TFF: ^TLVVVVVV
	 * FTF: T^LVVVVVV
	 * FFT: TL^VVVVVV
	 * FFT: TLVVVV^VV
	 * TFF: ^
	 */
	private boolean isAtStartOfTag, isAtStartOfLength, isReadingValue;

	public TLVOutputState() {
		state = new Stack<TLVStruct>();
		isAtStartOfTag = true;
		isAtStartOfLength = false;
		isReadingValue = false;
	}

	private TLVOutputState(Stack<TLVStruct> state, boolean isAtStartOfTag, boolean isAtStartOfLength, boolean isReadingValue) {
		this.state = state;
		this.isAtStartOfTag = isAtStartOfTag;
		this.isAtStartOfLength = isAtStartOfLength;
		this.isReadingValue = isReadingValue;
	}

	public boolean isAtStartOfTag() {
		return isAtStartOfTag;
	}

	public boolean isAtStartOfLength() {
		return isAtStartOfLength;
	}

	public boolean isProcessingValue() {
		return isReadingValue;
	}

	public int getTag() {
		if (state.isEmpty()) {
			throw new IllegalStateException("Tag not yet read.");
		}
		TLVStruct currentObject = state.peek();
		return currentObject.getTag();
	}

	public int getLength() {
		if (state.isEmpty()) {
			throw new IllegalStateException("Length not yet known.");
		}
		TLVStruct currentObject = state.peek();
		int length = currentObject.getLength();
		if (length < 0) {
			throw new IllegalStateException("Length not yet knwon.");
		}
		return length;
	}

	public int getValueBytesProcessed() {
		TLVStruct currentObject = state.peek();
		return currentObject.getValueBytesProcessed();
	}

	public int getValueBytesLeft() {
		if (state.isEmpty()) {
			throw new IllegalStateException("Length of value is unknown.");
		}
		TLVStruct currentObject = state.peek();
		int currentLength = currentObject.getLength();
		int valueBytesRead = currentObject.getValueBytesProcessed();
		return currentLength - valueBytesRead;
	}

	public void setTagProcessed(int tag) {
		/* Length is set to MAX INT, we will update it when caller calls our setLengthProcessed. */
		TLVStruct obj = new TLVStruct(tag);
		if (!state.isEmpty()) {
			TLVStruct parent = state.peek();
			byte[] tagBytes = TLVUtil.getTagAsBytes(tag);
			parent.write(tagBytes, 0, tagBytes.length);
		}
		state.push(obj);
		isAtStartOfTag = false;
		isAtStartOfLength = true;
		isReadingValue = false;
	}

	/**
	 * We've passed the length in the stream, but we don't know what it is yet...
	 */
	public void setDummyLengthProcessed() {
		isAtStartOfTag = false;
		isAtStartOfLength = false;
		isReadingValue = true;
		/* NOTE: doesn't call setLength, so that isLengthSet in stackFrame will remain false. */
	}
	
	public boolean isDummyLengthSet() {
		if (state.isEmpty()) { return false; }
		return !state.peek().isLengthSet();
	}

	public void setLengthProcessed(int length) {
		if (length < 0) {
			throw new IllegalArgumentException("Cannot set negative length (length = " + length + ").");
		}
		TLVStruct obj = state.pop();
		if (!state.isEmpty()) {
			TLVStruct parent = state.peek();
			byte[] lengthBytes = TLVUtil.getLengthAsBytes(length);
			parent.write(lengthBytes, 0, lengthBytes.length);
		}
		obj.setLength(length);
		state.push(obj);
		isAtStartOfTag = false;
		isAtStartOfLength = false;
		isReadingValue = true;
	}

	public void updatePreviousLength(int byteCount) {
		if (state.isEmpty()) { return; }
		TLVStruct currentObject = state.peek();
		
		if (currentObject.isLengthSet && currentObject.getLength() == byteCount) { return; }

		currentObject.setLength(byteCount);
		
		if (currentObject.getValueBytesProcessed() == currentObject.getLength()) {
			/* Update parent. */
			state.pop();
			byte[] lengthBytes = TLVUtil.getLengthAsBytes(byteCount);
			byte[] value = currentObject.getValue();
			updateValueBytesProcessed(lengthBytes, 0, lengthBytes.length);
			updateValueBytesProcessed(value, 0, value.length);
			isAtStartOfTag = true;
			isAtStartOfLength = false;
			isReadingValue = false;
		}
	}

	public void updateValueBytesProcessed(byte[] bytes, int offset, int length) {
		if (state.isEmpty()) { return; }
		TLVStruct currentObject = state.peek();
		int bytesLeft = currentObject.getLength() - currentObject.getValueBytesProcessed();
		if (length > bytesLeft) {
			throw new IllegalArgumentException("Cannot process " + length + " bytes! Only " + bytesLeft + " bytes left in this TLV object " + currentObject);
		}
		currentObject.write(bytes, offset, length);
		
		if (currentObject.getValueBytesProcessed() == currentObject.getLength()) {
			/* Stand back! I'm going to try recursion! Update parent(s)... */
			state.pop();
			updateValueBytesProcessed(currentObject.getValue(), 0, currentObject.getLength());
			isAtStartOfTag = true;
			isAtStartOfLength = false;
			isReadingValue = false;
		} else {
			/* We already have these values?!? */
			isAtStartOfTag = false;
			isAtStartOfLength = false;
			isReadingValue = true;
		}
		
	}

	public byte[] getValue() {
		if (state.isEmpty()) {
			throw new IllegalStateException("Cannot get value yet.");
		}
		return state.peek().getValue();
	}

	@SuppressWarnings("unchecked")
	public Object clone() {
		return new TLVOutputState((Stack<TLVStruct>)state.clone(), isAtStartOfTag, isAtStartOfLength, isReadingValue);
	}

	public String toString() {
		return state.toString();
	}

	/*
	 * TODO: ?? canBeWritten() <==> (state.size() == 1 && state.peek().isLengthSet()
	 */
	public boolean canBeWritten() {
		for (TLVStruct stackFrame: state) {
			if (!stackFrame.isLengthSet()) { return false; }
		}
		return true;
	}

	private class TLVStruct implements Cloneable
	{
		private int tag, length;
		private boolean isLengthSet;
		private ByteArrayOutputStream value;

		public TLVStruct(int tag) { this.tag = tag; this.length = Integer.MAX_VALUE; this.isLengthSet = false; this.value = new ByteArrayOutputStream(); }

		public void setLength(int length) { this.length = length; this.isLengthSet = true; }

		public int getTag() { return tag; }

		public int getLength() { return length; }

		public boolean isLengthSet() { return isLengthSet; }

		public int getValueBytesProcessed() { return value.size(); }

		public byte[] getValue() { return value.toByteArray(); }

		public void write(byte[] bytes, int offset, int length) { value.write(bytes, offset, length); }

		public Object clone() { TLVStruct copy = new TLVStruct(tag); copy.length = this.length; copy.value = new ByteArrayOutputStream(); try { copy.value.write(this.value.toByteArray()); } catch (IOException ioe) { ioe.printStackTrace(); } return copy; }

		public String toString() {
			byte[] valueBytes = value.toByteArray();
			return "[TLVStruct " + Integer.toHexString(tag) + ", " + (isLengthSet ? length : "UNDEFINED") +", " + Hex.bytesToHexString(valueBytes) + "(" + valueBytes.length + ") ]";
		}
	}
}

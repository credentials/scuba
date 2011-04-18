package net.sourceforge.scuba.tlv;

import java.util.Stack;

/**
 * State keeps track of where we are in a TLV stream.
 */
class TLVState implements Cloneable
{
	/**
	 * Encodes tags, lengths, and number of valueBytes encountered thus far.
	 */
	private Stack<TLStruct> state;
	
	/**
	 * TFF: ^TLVVVVVV
	 * FTF: T^LVVVVVV
	 * FFT: TL^VVVVVV
	 * FFT: TLVVVV^VV
	 * TFF: ^
	 */
	private boolean isAtStartOfTag, isAtStartOfLength, isReadingValue;

	
	public TLVState() {
		state = new Stack<TLStruct>();
		isAtStartOfTag = true;
		isAtStartOfLength = false;
		isReadingValue = false;
	}
	
	private TLVState(Stack<TLStruct> state, boolean isAtStartOfTag, boolean isAtStartOfLength, boolean isReadingValue) {
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
		TLStruct currentObject = state.peek();
		return currentObject.getTag();
	}

	public int getLength() {
		if (state.isEmpty()) {
			throw new IllegalStateException("Length not yet known.");
		}
		TLStruct currentObject = state.peek();
		int length = currentObject.getLength();
		return length;
	}

	public int getValueBytesProcessed() {
		TLStruct currentObject = state.peek();
		return currentObject.getValueBytesProcessed();
	}
	
	public int getValueBytesLeft() {
		if (state.isEmpty()) {
			throw new IllegalStateException("Length of value is unknown.");
		}
		TLStruct currentObject = state.peek();
		int currentLength = currentObject.getLength();
		int valueBytesRead = currentObject.getValueBytesProcessed();
		return currentLength - valueBytesRead;
	}

	public void setTagProcessed(int tag, int byteCount) {
		/* Length is set to MAX INT, we will update it when caller calls our setLengthProcessed. */
		TLStruct obj = new TLStruct(tag);
		if (!state.isEmpty()) {
			TLStruct parent = state.peek();
			parent.updateValueBytesProcessed(byteCount);
		}
		state.push(obj);
		isAtStartOfTag = false;
		isAtStartOfLength = true;
		isReadingValue = false;
	}

	public void setDummyLengthProcessed() {
		isAtStartOfTag = false;
		isAtStartOfLength = false;
		isReadingValue = true;		
	}
	
	public void setLengthProcessed(int length, int byteCount) {
		if (length < 0) {
			throw new IllegalArgumentException("Cannot set negative length (length = " + length + ").");
		}
		TLStruct obj = state.pop();
		if (!state.isEmpty()) {
			TLStruct parent = state.peek();
			parent.updateValueBytesProcessed(byteCount);
		}
		obj.setLength(length);
		state.push(obj);
		isAtStartOfTag = false;
		isAtStartOfLength = false;
		isReadingValue = true;
	}
	
	public void updateValueBytesProcessed(int byteCount) {
		if (state.isEmpty()) { return; }
		TLStruct currentObject = state.peek();
		int bytesLeft = currentObject.getLength() - currentObject.getValueBytesProcessed();
		if (byteCount > bytesLeft) {
			throw new IllegalArgumentException("Cannot process " + byteCount + " bytes! Only " + bytesLeft + " bytes left in this TLV object " + currentObject);
		}
		currentObject.updateValueBytesProcessed(byteCount);
		int currentLength = currentObject.getLength();
		if (currentObject.getValueBytesProcessed() == currentLength) {
			state.pop();
			/* Stand back! I'm going to try recursion! Update parent(s)... */
			updateValueBytesProcessed(currentLength);
			isAtStartOfTag = true;
			isAtStartOfLength = false;
			isReadingValue = false;
		} else {
			isAtStartOfTag = false;
			isAtStartOfLength = false;
			isReadingValue = true;
		}
	}
	
	@SuppressWarnings("unchecked")
	public Object clone() {
		return new TLVState((Stack<TLStruct>)state.clone(), isAtStartOfTag, isAtStartOfLength, isReadingValue);
	}
	
	public String toString() {
		return state.toString();
	}

	private class TLStruct implements Cloneable
	{
		private int tag, length, valueBytesRead;

		public TLStruct(int tag) { this.tag = tag; this.length = Integer.MAX_VALUE; this.valueBytesRead = 0; }

		public void setLength(int length) { this.length = length; }

		public int getTag() { return tag; }

		public int getLength() { return length; }

		public int getValueBytesProcessed() { return valueBytesRead; }

		public void updateValueBytesProcessed(int n) { this.valueBytesRead += n; }

		public Object clone() { TLStruct copy = new TLStruct(tag); copy.length = this.length; copy.valueBytesRead = this.valueBytesRead; return copy; }

		public String toString() { return "[TLStruct " + Integer.toHexString(tag) + ", " + length + ", " + valueBytesRead + "]"; }
	}
}

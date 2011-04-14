package net.sourceforge.scuba.tlv;

import java.util.Stack;

/**
 * State keeps track of where we are in a TLV stream.
 */
class TLVState implements Cloneable
{
	/** Which tags have we seen thus far? */
	private Stack<TLStruct> state;
	
	/** FIXME: These are probably redundant... */
	private boolean isAtStartOfTag, isAtStartOfLength, isReadingValue;

	/*
	 * TFF: ^TLVVVVVV
	 * FTF: T^LVVVVVV
	 * FFT: TL^VVVVVV
	 * FFT: TLVVVV^VV
	 * TFF: ^
	 */
	
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
		if (length < 0) {
			throw new IllegalStateException("Length not yet knwon.");
		}
		return length;
	}

	public int getValueBytesLeft() {
		if (state.isEmpty()) {
			throw new IllegalStateException("Not yet processing value.");
		}
		TLStruct currentObject = state.peek();
		int currentLength = currentObject.getLength();
		if (currentLength < 0) {
			throw new IllegalStateException("Not yet processing value.");
		}
		int currentBytesRead = currentObject.getValueBytesRead();
		return currentLength - currentBytesRead;
	}

	public void setTagProcessed(int tag, int byteCount) {
		/* Length is set to -1, we will update it when we encounter it */
		TLStruct obj = new TLStruct(tag, -1, 0);
		if (!state.isEmpty()) {
			TLStruct parent = state.peek();
			parent.updateValueBytesRead(byteCount);
		}
		state.push(obj);
		isAtStartOfTag = false;
		isAtStartOfLength = true;
		isReadingValue = false;
	}

	public void setLengthProcessed(int length, int byteCount) {
		if (length < 0) {
			throw new IllegalArgumentException("Cannot set negative length (length = " + length + ").");
		}
		TLStruct obj = state.pop();
		if (!state.isEmpty()) {
			TLStruct parent = state.peek();
			parent.updateValueBytesRead(byteCount);
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
		int bytesLeft = currentObject.getLength() - currentObject.getValueBytesRead();
		if (byteCount > bytesLeft) {
			throw new IllegalArgumentException("Cannot process " + byteCount + " bytes! Only " + bytesLeft + " bytes left in this TLV object " + currentObject);
		}
		currentObject.updateValueBytesRead(byteCount);
		int currentLength = currentObject.getLength();
		if (currentObject.getValueBytesRead() == currentLength) {
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

		public TLStruct(int tag, int length, int valueBytesRead) { this.tag = tag; this.length = length; this.valueBytesRead = valueBytesRead; }

		public void setLength(int length) { this.length = length; }

		public int getTag() { return tag; }

		public int getLength() { return length; }

		public int getValueBytesRead() { return valueBytesRead; }

		public void updateValueBytesRead(int n) { this.valueBytesRead += n; }

		public Object clone() { return new TLStruct(tag, length, valueBytesRead); }

		public String toString() { return "[TLStruct " + Integer.toHexString(tag) + ", " + length + ", " + valueBytesRead + "]"; }
	}
}

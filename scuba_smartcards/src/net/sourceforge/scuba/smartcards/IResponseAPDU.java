package net.sourceforge.scuba.smartcards;

public interface IResponseAPDU {

	/**
	 * Returns the number of data bytes in the response body (Nr) or 0 if this
	 * APDU has no body. This call is equivalent to
	 * <code>getData().length</code>.
	 *
	 * @return the number of data bytes in the response body or 0 if this APDU
	 * has no body.
	 */
	public int getNr();

	/**
	 * Returns a copy of the data bytes in the response body. If this APDU as
	 * no body, this method returns a byte array with a length of zero.
	 *
	 * @return a copy of the data bytes in the response body or the empty
	 *    byte array if this APDU has no body.
	 */
	public byte[] getData();

	/**
	 * Returns the value of the status byte SW1 as a value between 0 and 255.
	 *
	 * @return the value of the status byte SW1 as a value between 0 and 255.
	 */
	public int getSW1();

	/**
	 * Returns the value of the status byte SW2 as a value between 0 and 255.
	 *
	 * @return the value of the status byte SW2 as a value between 0 and 255.
	 */
	public int getSW2();

	/**
	 * Returns the value of the status bytes SW1 and SW2 as a single
	 * status word SW.
	 * It is defined as
	 * <code>(getSW1() << 8) | getSW2()</code>.
	 *
	 * @return the value of the status word SW.
	 */
	public int getSW();

	/**
	 * Returns a copy of the bytes in this APDU.
	 *
	 * @return a copy of the bytes in this APDU.
	 */
	public byte[] getBytes();

	/**
	 * Returns a string representation of this response APDU.
	 *
	 * @return a String representation of this response APDU.
	 */
	public String toString();

	/**
	 * Compares the specified object with this response APDU for equality.
	 * Returns true if the given object is also a ResponseAPDU and its bytes are
	 * identical to the bytes in this ResponseAPDU.
	 *
	 * @param obj the object to be compared for equality with this response APDU
	 * @return true if the specified object is equal to this response APDU
	 */
	public boolean equals(Object obj);

	/**
	 * Returns the hash code value for this response APDU.
	 *
	 * @return the hash code value for this response APDU.
	 */
	public int hashCode();

}
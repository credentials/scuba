package net.sourceforge.scuba.smartcards;

public interface ICommandAPDU {

	/**
	 * Returns the value of the class byte CLA.
	 *
	 * @return the value of the class byte CLA.
	 */
	public int getCLA();

	/**
	 * Returns the value of the instruction byte INS.
	 *
	 * @return the value of the instruction byte INS.
	 */
	public int getINS();

	/**
	 * Returns the value of the parameter byte P1.
	 *
	 * @return the value of the parameter byte P1.
	 */
	public int getP1();

	/**
	 * Returns the value of the parameter byte P2.
	 *
	 * @return the value of the parameter byte P2.
	 */
	public int getP2();

	/**
	 * Returns the number of data bytes in the command body (Nc) or 0 if this
	 * APDU has no body. This call is equivalent to
	 * <code>getData().length</code>.
	 *
	 * @return the number of data bytes in the command body or 0 if this APDU
	 * has no body.
	 */
	public int getNc();

	/**
	 * Returns a copy of the data bytes in the command body. If this APDU as
	 * no body, this method returns a byte array with length zero.
	 *
	 * @return a copy of the data bytes in the command body or the empty
	 *    byte array if this APDU has no body.
	 */
	public byte[] getData();

	/**
	 * Returns the maximum number of expected data bytes in a response
	 * APDU (Ne).
	 *
	 * @return the maximum number of expected data bytes in a response APDU.
	 */
	public int getNe();

	/**
	 * Returns a copy of the bytes in this APDU.
	 *
	 * @return a copy of the bytes in this APDU.
	 */
	public byte[] getBytes();

	/**
	 * Returns a string representation of this command APDU.
	 *
	 * @return a String representation of this command APDU.
	 */
	public String toString();

	/**
	 * Compares the specified object with this command APDU for equality.
	 * Returns true if the given object is also a CommandAPDU and its bytes are
	 * identical to the bytes in this CommandAPDU.
	 *
	 * @param obj the object to be compared for equality with this command APDU
	 * @return true if the specified object is equal to this command APDU
	 */
	public boolean equals(Object obj);

	/**
	 * Returns the hash code value for this command APDU.
	 *
	 * @return the hash code value for this command APDU.
	 */
	public int hashCode();

}
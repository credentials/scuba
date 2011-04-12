/*
 * $Id: $
 */

package net.sourceforge.scuba.simservice;

import java.util.ArrayList;

import net.sourceforge.scuba.smartcards.CardServiceException;
import net.sourceforge.scuba.util.Hex;

/**
 * A representation of (linear fixed and cyclic) record EF files.
 * Described in 6.4.2 of ETSI TS 100 977.
 *
 * @version $Revision: 1.1 $
 *
 * @author Martijn Oostdijk (martijn.oostdijk@gmail.com)
 */
public class RecordEFFile extends EFFile
{
	/** The info (header) of this file. */
	private SIMFileInfo info;

	/** The records of this file. */
	private ArrayList<byte[]> records;

	/** The current record. */
	private int recordPointer;

	/**
	 * Constructs a new record EF file.
	 */
	RecordEFFile(SIMFileInfo info) throws CardServiceException {
		if (info.isTransparent() || !info.isEF()) {
			throw new CardServiceException("Invalid file info");
		}
		this.info = info;
		this.records = new ArrayList<byte[]>();
		recordPointer = 0;
	}

	/**
	 * Gets the file info (header) of this file.
	 *
	 * @return the file info (header) of this file.
	 */
	public SIMFileInfo getFileInfo() {
		return info;
	}

	/**
	 * Gets the record size of this file.
	 *
	 * @return The record size of this file.
	 */
	public int getRecordSize() {
		return info.getRecordSize();
	}

	/**
	 * Adds a new record to this file.
	 *
	 * @param record The new record.
	 */
	public void appendRecord(byte[] record) {
		records.add(record);
	}

	/**
	 * Gets a record.
	 *
	 * @param i The record number to get (the first record has
	 *    record number 1).
	 *
	 * @return the record with record number <code>i</code>.
	 */
	public byte[] getRecord(int recordNumber) {
		if (records == null ||
				recordNumber < 1 ||
				recordNumber > records.size()) {
			return null;
		}
		recordPointer = recordNumber - 1;
		return (byte[])records.get(recordPointer);
	}

	/**
	 * Gets the next record, or <code>null</code> if the current
	 * record is the last record.
	 *
	 * @return the next record, or <code>null</code> if the current
	 *    record is the last record.
	 */
	public byte[] getNextRecord() {
		if (records == null || recordPointer + 1 >= records.size()) {
			return null;
		} else {
			return (byte[])records.get(++recordPointer);
		}
	}

	/**
	 * Gets the previous record, or <code>null</code> if the current
	 * record is the first record.
	 *
	 * @return the previous record, or <code>null</code> if the current
	 *    record is the first record.
	 */
	public byte[] getPreviousRecord() {
		if (records == null || recordPointer - 1 < 0) {
			return null;
		} else {
			return (byte[])records.get(--recordPointer);
		}
	}

	/**
	 * Gets a textual representation of this object.
	 *
	 * @return a textual representation of this object.
	 */
	public String toString() {
		String result = info.toString() + ":\n";
		for (int i = 0; i < records.size(); i++) {
			byte[] record = (byte[])records.get(i);
			String prefix = Integer.toString(i + 1) + ":";
			result += Hex.bytesToPrettyString(record,16,false,5,prefix,true);
		}
		return result;
	}
}


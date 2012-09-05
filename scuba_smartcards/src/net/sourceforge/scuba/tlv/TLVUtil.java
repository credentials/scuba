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
 * $Id: TLVUtil.java 183 2012-09-04 18:54:58Z pimvullers $
 */

package net.sourceforge.scuba.tlv;

import java.io.ByteArrayOutputStream;

/* FIXME: make class package visible only. */

public class TLVUtil implements ASN1Constants {
	
	/** Hide from public interface. */
	private TLVUtil() {
	}
	
	public static boolean isPrimitive(int tag) {
		int i = 3;
		for (; i >= 0; i--) {
			int mask = (0xFF << (8 * i));
			if ((tag & mask) != 0x00) { break; }
		}
		int msByte = (((tag & (0xFF << (8 * i))) >> (8 * i)) & 0xFF);
		boolean result = ((msByte & 0x20) == 0x00);
		return result;
	}

	public static int getTagLength(int tag) {
		return getTagAsBytes(tag).length;
	}

	public static int getLengthLength(int length) {
		return getLengthAsBytes(length).length;
	}

	/**
	 * The tag bytes of this object.
	 * 
	 * @return the tag bytes of this object.
	 */
	public static byte[] getTagAsBytes(int tag) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int byteCount = (int)(Math.log(tag) / Math.log(256)) + 1;
		for (int i = 0; i < byteCount; i++) {
			int pos = 8 * (byteCount - i - 1);
			out.write((tag & (0xFF << pos)) >> pos);
		}
		byte[] tagBytes = out.toByteArray();
		switch (getTagClass(tag)) {
		case APPLICATION_CLASS:
			tagBytes[0] |= 0x40;
			break;
		case CONTEXT_SPECIFIC_CLASS:
			tagBytes[0] |= 0x80;
			break;
		case PRIVATE_CLASS:
			tagBytes[0] |= 0xC0;
			break;
		}
		if (!isPrimitive(tag)) {
			tagBytes[0] |= 0x20;
		}
		return tagBytes;
	}

	/**
	 * The length bytes of this object.
	 * 
	 * @return length of encoded value as bytes
	 */
	public static byte[] getLengthAsBytes(int length) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		if (length < 0x80) {
			/* short form */
			out.write(length);
		} else {
			int byteCount = log(length, 256);
			out.write(0x80 | byteCount);
			for (int i = 0; i < byteCount; i++) {
				int pos = 8 * (byteCount - i - 1);
				out.write((length & (0xFF << pos)) >> pos);
			}
		}
		return out.toByteArray();
	}


	static int getTagClass(int tag) {
		int i = 3;
		for (; i >= 0; i--) {
			int mask = (0xFF << (8 * i));
			if ((tag & mask) != 0x00) { break; }
		}
		int msByte = (((tag & (0xFF << (8 * i))) >> (8 * i)) & 0xFF);
		switch (msByte & 0xC0) {
		case 0x00: return UNIVERSAL_CLASS;
		case 0x40: return APPLICATION_CLASS;
		case 0x80: return CONTEXT_SPECIFIC_CLASS;
		case 0xC0:
		default: return PRIVATE_CLASS;
		}
	}

	private static int log(int n, int base) {
		int result = 0;
		while (n > 0) {
			n = n / base;
			result ++;
		}
		return result;
	}
}

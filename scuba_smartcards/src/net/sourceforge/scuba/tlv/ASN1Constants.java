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
 * $Id: ASN1Constants.java 183 2012-09-04 18:54:58Z pimvullers $
 */

package net.sourceforge.scuba.tlv;

public interface ASN1Constants {
	
	/** Universal tag class. */
	public static final int UNIVERSAL_CLASS = 0;		/* 00 x xxxxx */
	/** Application tag class. */
	public static final int APPLICATION_CLASS = 1;		/* 01 x xxxxx */
	/** Context specific tag class. */
	public static final int CONTEXT_SPECIFIC_CLASS = 2;	/* 10 x xxxxx */
	/** Private tag class. */
	public static final int PRIVATE_CLASS = 3;			/* 11 x xxxxx */

	/**
	 * Universal tag type (tag number).
	 * Rightmost 5 bits, should be prefixed
	 * with class indicator (2 bits) and
	 * primitive/constructed indicator (1 bit).
	 */
	public static final int
	BOOLEAN_TYPE_TAG = 0x01,							/* 00 0 00001, Section 8.2 of X.690. */
	INTEGER_TYPE_TAG = 0x02,							/* 00 0 00010, Section 8.3 of X.690. */
	BIT_STRING_TYPE_TAG = 0x03,							/* 00 x 00011, Section 8.6 of X.690. */
	OCTET_STRING_TYPE_TAG = 0x04,						/* xx x 00100 */
	NULL_TYPE_TAG = 0x05,								/* xx x 00101 */
	OBJECT_IDENTIFIER_TYPE_TAG = 0x06,					/* xx x 00110 */
	OBJECT_DESCRIPTOR_TYPE_TAG = 0x07,					/* xx x 00111 */
	EXTERNAL_TYPE_TAG = 0x08,							/* xx x 01000 */
	REAL_TYPE_TAG = 0x09,								/* xx x 01001 */
	ENUMERATED_TYPE_TAG = 0x0A,							/* xx x 01010 */
	EMBEDDED_PDV_TYPE_TAG = 0x0B,						/* xx x 01011 */
	UTF8_STRING_TYPE_TAG = 0x0C,						/* xx x 01100 */
	SEQUENCE_TYPE_TAG = 0x10,							/* 00 1 10000, Section 8.10 of X.690. */
	SET_TYPE_TAG = 0x11,								/* 00 1 10001 */
	NUMERIC_STRING_TYPE_TAG = 0x12,						/* xx x 10010 */
	PRINTABLE_STRING_TYPE_TAG = 0x13,					/* xx x 10011 */
	T61_STRING_TYPE_TAG = 0x14,							/* xx x 10100 */
	IA5_STRING_TYPE_TAG = 0x16,							/* xx x 10110 */
	UTC_TIME_TYPE_TAG = 0x17,							/* xx x 10111 */
	GENERALIZED_TIME_TYPE_TAG = 0x18,					/* xx x 11000 */
	GRAPHIC_STRING_TYPE_TAG = 0x19,						/* xx x 11001 */
	VISIBLE_STRING_TYPE_TAG = 0x1A,						/* xx x 11010 */
	GENERAL_STRING_TYPE_TAG = 0x1B,						/* xx x 11011 */
	UNIVERSAL_STRING_TYPE_TAG = 0x1C,					/* xx x 11100 */
	BMP_STRING_TYPE_TAG = 0x1E;							/* xx x 11110 */
}

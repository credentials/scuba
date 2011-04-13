/*
 * SCUBA smart card framework.
 *
 * Copyright (C) 2009  The SCUBA team.
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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import net.sourceforge.scuba.util.Hex;

/**
 * Generic data structure for storing Tag Length Value (TLV) objects encoded
 * according to the Basic Encoding Rules (BER). Written by Martijn Oostdijk (MO)
 * and Cees-Bart Breunesse (CB) of the Security of Systems group (SoS) of the
 * Institute of Computing and Information Sciences (ICIS) at Radboud University
 * (RU). Based on ISO 7816-4 Annex D (which apparently is based on ISO 8825
 * and/or X.208, X.209, X.680, X.690). See <a
 * href="http://en.wikipedia.org/wiki/ASN.1">ASN.1</a>.
 * 
 * @author Martijn Oostdijk (martijno@cs.ru.nl)
 * @author Cees-Bart Breunesse (ceesb@cs.ru.nl)
 * @version $Revision: 227 $
 */
public class BERTLVObject implements ASN1Constants
{
	private static final SimpleDateFormat SDF = new SimpleDateFormat("yyMMddhhmmss'Z'");
	
	/** Tag. */
	private int tag;

	/** Length. */
	private int length;

	/** Value, is usually just a byte[]. */
	private Object value;

	/**
	 * Constructs a new TLV object with tag <code>tag</code> containing data
	 * <code>value</code>.
	 * 
	 * @param tag tag of TLV object
	 * @param value data of TLV object
	 * @throws IOException if something goes wrong.
	 */
	public BERTLVObject(int tag, Object value) {
		this(tag, value, true);
	}

	/**
	 * Constructs a new TLV object with tag <code>tag</code> containing data
	 * <code>value</code>.
	 * 
	 * @param tag tag of TLV object
	 * @param value data of TLV object
	 * @param interpretValue whether the embedded byte[] values should be
	 *                        interpreted/parsed. Some ASN1 streams don't like that :( 
	 * @throws IOException if something goes wrong.
	 */
	public BERTLVObject(int tag, Object value, boolean interpretValue) {
		try {
			this.tag = tag;
			this.value = value;
			if (value instanceof byte[]) {
				this.length = ((byte[])value).length;
			} else if (value instanceof BERTLVObject) {
				this.value = new BERTLVObject[1];
				((BERTLVObject[])this.value)[0] = (BERTLVObject)value;
			} else if (value instanceof BERTLVObject[]) {
				this.value = value;
			} else if (value instanceof Byte) {
				this.length = 1;
				this.value = new byte[1];
				((byte[])this.value)[0] = ((Byte)value).byteValue();
			} else if (value instanceof Integer) {
				this.value = new BERTLVObject[1];
				((BERTLVObject[])this.value)[0] = new BERTLVObject(ASN1Constants.INTEGER_TYPE_TAG, value);
			} else {
				throw new IllegalArgumentException("Cannot encode value of type: " + value.getClass());
			}
			if (value instanceof byte[] && interpretValue) {
				this.value = interpretValue(tag, (byte[])value);
			}
			// reconstructLength();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static BERTLVObject getInstance(
			InputStream in) throws IOException {
		BERTLVInputStream tlvIn = (in instanceof BERTLVInputStream) ? (BERTLVInputStream)in : new BERTLVInputStream(in);
		int tag = tlvIn.readTag();
		tlvIn.readLength();
		byte[] valueBytes = tlvIn.readValue();
		BERTLVObject result = new BERTLVObject(tag, valueBytes);
		return result;
	}

	private static Object interpretValue(int tag, byte[] valueBytes) {
		if (TLVUtil.isPrimitive(tag)) {
			return ASN1Util.interpretPrimitiveValue(tag, valueBytes);
		} else {
			/*
			 * Not primitive, the value itself consists of 0 or more BER-TLV
			 * objects.
			 */
			try {
				return interpretCompoundValue(tag, valueBytes);
			} catch (IOException ioe) {
				return new BERTLVObject[0];
			}
		}
	}

	private static BERTLVObject[] interpretCompoundValue(int tag, byte[] valueBytes)
	throws IOException {
		Collection<BERTLVObject> subObjects = new ArrayList<BERTLVObject>();
		BERTLVInputStream in = new BERTLVInputStream(new ByteArrayInputStream(valueBytes));
		int length = valueBytes.length;
		try {
			while (length > 0) {
				BERTLVObject subObject = BERTLVObject.getInstance(in);
				length -= subObject.getLength();
				subObjects.add(subObject);
			}
		} catch (EOFException eofe) { }
		BERTLVObject[] result = new BERTLVObject[subObjects.size()];
		subObjects.toArray(result);
		return result;
	}

	/****************************************************************************
	 * Adds
	 * <code>object</object> as subobject of <code>this</code> TLV object when
	 * <code>this</code> is not a primitive object.
	 * 
	 * @param object to add as a subobject.
	 */
	public void addSubObject(BERTLVObject object) {
		Collection<BERTLVObject> subObjects = new ArrayList<BERTLVObject>();

		if (value == null) {
			value = new BERTLVObject[1];
		} else if (value instanceof BERTLVObject[]){
			subObjects.addAll(Arrays.asList((BERTLVObject[])value));
		} else if (value instanceof BERTLVObject){
			/* NOTE: Should never happen if indeed !isPrimitive... */
			subObjects.add((BERTLVObject)value);
			value = new BERTLVObject[2];
		} else {
			throw new IllegalStateException("Error: Unexpected value in BERTLVObject");
		}
		subObjects.add(object);
		value = subObjects.toArray((BERTLVObject[])value);
		reconstructLength();
	}

	public int getTag() {
		return tag;
	}

	/**
	 * Reconstructs the length of the encoded value.
	 */
	public void reconstructLength() {
		/* NOTE: needed after sub-objects have been added. */
		length = getValueAsBytes(tag, value).length;
	}

	public int getLength() {
		return length;
	}

	/**
	 * The encoded value.
	 * 
	 * @return the encoded value
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * This object, including tag and length, as byte array.
	 * 
	 * @return this object, including tag and length, as byte array
	 */
	public byte[] getEncoded() {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			out.write(TLVUtil.getTagAsBytes(tag));
			out.write(TLVUtil.getLengthAsBytes(getLength()));
			out.write(getValueAsBytes(tag, value));
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return out.toByteArray();
	}

	/**
	 * Gets the first sub-object (including this object) whose tag equals
	 * <code>tag</code>.
	 * 
	 * @param tag the tag to search for
	 * @return the first
	 */
	public BERTLVObject getSubObject(int tag) {
		if (this.tag == tag) {
			return this;
		} else if (value instanceof BERTLVObject[]) {
			BERTLVObject[] children = (BERTLVObject[])value;
			for (int i = 0; i < children.length; i++) {
				BERTLVObject child = children[i];
				BERTLVObject candidate = child.getSubObject(tag);
				if (candidate != null) { return candidate; }
			}
		}
		return null;
	}

	/**
	 * Gets the first sub-object (including this object) following the tags in
	 * tagPath.
	 * 
	 * @param tagPath the path to follow
	 * @param offset in the tagPath
	 * @param length of the tagPath
	 * @return the first
	 */
	public BERTLVObject getSubObject(int[] tagPath, int offset, int length) {
		if (length == 0) {
			return this;
		} else {
			BERTLVObject child = getSubObject(tagPath[offset]);
			if (child != null) { return child.getSubObject(tagPath, offset + 1,
					length - 1); }
		}
		return null;
	}

	/****************************************************************************
	 * Returns the indexed child (starting from 0) or null otherwise.
	 * 
	 * @param index
	 * @return the object pointed to by index.
	 */
	public BERTLVObject getChildByIndex(int index) {

		if (value instanceof BERTLVObject[]) {
			BERTLVObject[] children = (BERTLVObject[])value;
			return children[index];
		}

		return null;
	}

	/**
	 * A textual (nested tree-like) representation of this object. Always ends in
	 * newline character, no need to add it yourself.
	 * 
	 * @return a textual representation of this object.
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return toString(0);
	}

	private String toString(int indent) {
		byte[] prefixBytes = new byte[indent];
		Arrays.fill(prefixBytes, (byte)' ');
		String prefix = new String(prefixBytes);
		StringBuffer result = new StringBuffer();
		result.append(prefix);
		result.append(ASN1Util.tagToString(tag));
		result.append(" ");
		result.append(Integer.toString(getLength()));
		result.append(" ");
		if (value instanceof byte[]) {
			byte[] valueData = (byte[])value;
			result.append("'0x");
			if (indent + 2 * valueData.length <= 60) {
				result.append(Hex.bytesToHexString(valueData));
			} else {
				result
				.append(Hex.bytesToHexString(valueData, 0, (50 - indent) / 2));
				result.append("...");
			}
			result.append("'\n");
		} else if (value instanceof BERTLVObject[]) {
			result.append("{\n");
			BERTLVObject[] subObjects = (BERTLVObject[])value;
			for (int i = 0; i < subObjects.length; i++) {
				result.append(subObjects[i].toString(indent + 3));
			}
			result.append(prefix);
			result.append("}\n");
	} else {
		result.append("\"");
		result.append(value != null ? value.toString() : "null");
		result.append("\"\n");
	}
		return result.toString();
	}




	/**
	 * The value of this object as a byte array. Almost the same as getEncoded(),
	 * but this one skips the tag and length of <code>this</code> BERTLVObject.
	 * 
	 * @return the value of this object as a byte array
	 */
	private static byte[] getValueAsBytes(int tag, Object value) {
		if (value == null) {
			System.out.println("DEBUG: object has no value: tag == "
					+ Integer.toHexString(tag));
		}
		if (TLVUtil.isPrimitive(tag)) {
			if (value instanceof byte[]) {
				return (byte[])value;
			} else if (value instanceof String) {
				return ((String)value).getBytes();
			} else if (value instanceof Date) {
				return SDF.format((Date)value).getBytes();
			} else if (value instanceof Integer) {
				int intValue = ((Integer)value).intValue();
				int byteCount = Integer.bitCount(intValue) / 8 + 1;
				byte[] result = new byte[byteCount];
				for (int i = 0; i < byteCount; i++) {
					int pos = 8 * (byteCount - i - 1);
					result[i] = (byte)((intValue & (0xFF << pos)) >> pos);
				}
				return result;
			} else if (value instanceof Byte) {
				byte[] result = new byte[1];
				result[0] = ((Byte)value).byteValue();
				return result;
			}
		}
		if (value instanceof BERTLVObject[]) {
			ByteArrayOutputStream result = new ByteArrayOutputStream();
			BERTLVObject[] children = (BERTLVObject[])value;
			for (int i = 0; i < children.length; i++) {
				try {
					result.write(children[i].getEncoded());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return result.toByteArray();
		}

		/* NOTE: Not primitive, and also not instance of BERTLVObject[]... */
		if (value instanceof byte[]) {
			System.err.println("DEBUG: WARNING: BERTLVobject with non-primitive tag "
					+ Hex.intToHexString(tag) + " has byte[] value");
			return (byte[])value;
		}
		throw new IllegalStateException("Cannot decode value of "
				+ (value == null ? "null" : value.getClass().getCanonicalName())
				+ " (tag = " + Hex.intToHexString(tag) + ")");
	}
}

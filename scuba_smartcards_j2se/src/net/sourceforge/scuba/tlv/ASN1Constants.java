package net.sourceforge.scuba.tlv;

public interface ASN1Constants {
	
	/** Universal tag class. */
	public static final int UNIVERSAL_CLASS = 0;
	/** Application tag class. */
	public static final int APPLICATION_CLASS = 1;
	/** Context specific tag class. */
	public static final int CONTEXT_SPECIFIC_CLASS = 2;
	/** Private tag class. */
	public static final int PRIVATE_CLASS = 3;
	
	/** Universal tag type. */
	public static final int BOOLEAN_TYPE_TAG = 0x01, INTEGER_TYPE_TAG = 0x02,
	BIT_STRING_TYPE_TAG = 0x03, OCTET_STRING_TYPE_TAG = 0x04,
	NULL_TYPE_TAG = 0x05, OBJECT_IDENTIFIER_TYPE_TAG = 0x06,
	OBJECT_DESCRIPTOR_TYPE_TAG = 0x07, EXTERNAL_TYPE_TAG = 0x08,
	REAL_TYPE_TAG = 0x09, ENUMERATED_TYPE_TAG = 0x0A,
	EMBEDDED_PDV_TYPE_TAG = 0x0B, UTF8_STRING_TYPE_TAG = 0x0C,
	SEQUENCE_TYPE_TAG = 0x10, SET_TYPE_TAG = 0x11,
	NUMERIC_STRING_TYPE_TAG = 0x12, PRINTABLE_STRING_TYPE_TAG = 0x13,
	T61_STRING_TYPE_TAG = 0x14, IA5_STRING_TYPE_TAG = 0x16,
	UTC_TIME_TYPE_TAG = 0x17, GENERALIZED_TIME_TYPE_TAG = 0x18,
	GRAPHIC_STRING_TYPE_TAG = 0x19, VISIBLE_STRING_TYPE_TAG = 0x1A,
	GENERAL_STRING_TYPE_TAG = 0x1B, UNIVERSAL_STRING_TYPE_TAG = 0x1C,
	BMP_STRING_TYPE_TAG = 0x1E;
}

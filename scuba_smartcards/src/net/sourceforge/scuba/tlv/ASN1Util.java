/*
 * This file is part of the SCUBA smart card framework.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA
 *
 * Copyright (C) 2009-2013 The SCUBA team.
 *
 * $Id: $
 */

package net.sourceforge.scuba.tlv;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import net.sourceforge.scuba.util.Hex;

class ASN1Util implements ASN1Constants {

    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyMMddhhmmss'Z'");    
    
    /*
     * Primitive, the value consists of 0 or more Simple-TLV objects, or
     * just (application-dependent) bytes. If tag is not known (or
     * universal) we assume the value is just bytes.
     */
    static Object interpretPrimitiveValue(int tag, byte[] valueBytes) {
        if (TLVUtil.getTagClass(tag) == UNIVERSAL_CLASS)
            switch (tag) {
            case INTEGER_TYPE_TAG:
            case BIT_STRING_TYPE_TAG:
            case OCTET_STRING_TYPE_TAG:
            case NULL_TYPE_TAG:
            case OBJECT_IDENTIFIER_TYPE_TAG:
                return valueBytes;
            case UTF8_STRING_TYPE_TAG:
            case PRINTABLE_STRING_TYPE_TAG:
            case T61_STRING_TYPE_TAG:
            case IA5_STRING_TYPE_TAG:
            case VISIBLE_STRING_TYPE_TAG:
            case GENERAL_STRING_TYPE_TAG:
            case UNIVERSAL_STRING_TYPE_TAG:
            case BMP_STRING_TYPE_TAG:
                return new String(valueBytes);
            case UTC_TIME_TYPE_TAG:
                try { return SDF.parse(new String(valueBytes)); } catch (ParseException pe) { }
            }
        return valueBytes;
    }
    
    static String tagToString(int tag) {
        if (TLVUtil.getTagClass(tag) == UNIVERSAL_CLASS) {
            if (TLVUtil.isPrimitive(tag)) {
                switch (tag & 0x1F) {
                case BOOLEAN_TYPE_TAG:
                    return "BOOLEAN";
                case INTEGER_TYPE_TAG:
                    return "INTEGER";
                case BIT_STRING_TYPE_TAG:
                    return "BIT_STRING";
                case OCTET_STRING_TYPE_TAG:
                    return "OCTET_STRING";
                case NULL_TYPE_TAG:
                    return "NULL";
                case OBJECT_IDENTIFIER_TYPE_TAG:
                    return "OBJECT_IDENTIFIER";
                case REAL_TYPE_TAG:
                    return "REAL";
                case UTF8_STRING_TYPE_TAG:
                    return "UTF_STRING";
                case PRINTABLE_STRING_TYPE_TAG:
                    return "PRINTABLE_STRING";
                case T61_STRING_TYPE_TAG:
                    return "T61_STRING";
                case IA5_STRING_TYPE_TAG:
                    return "IA5_STRING";
                case VISIBLE_STRING_TYPE_TAG:
                    return "VISIBLE_STRING";
                case GENERAL_STRING_TYPE_TAG:
                    return "GENERAL_STRING";
                case UNIVERSAL_STRING_TYPE_TAG:
                    return "UNIVERSAL_STRING";
                case BMP_STRING_TYPE_TAG:
                    return "BMP_STRING";
                case UTC_TIME_TYPE_TAG:
                    return "UTC_TIME";
                case GENERALIZED_TIME_TYPE_TAG:
                    return "GENERAL_TIME";
                }
            } else {
                switch (tag & 0x1F) {
                case ENUMERATED_TYPE_TAG:
                    return "ENUMERATED";
                case SEQUENCE_TYPE_TAG:
                    return "SEQUENCE";
                case SET_TYPE_TAG:
                    return "SET";
                }
            }
        }
        return "'0x" + Hex.intToHexString(tag) + "'";
    }
}

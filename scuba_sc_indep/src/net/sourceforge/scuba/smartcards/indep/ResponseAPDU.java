package net.sourceforge.scuba.smartcards.indep;


/*
 * Copyright 2005-2006 Sun Microsystems, Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Sun designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Sun in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Sun Microsystems, Inc., 4150 Network Circle, Santa Clara,
 * CA 95054 USA or visit www.sun.com if you need additional information or
 * have any questions.
 */

import java.util.Arrays;

import net.sourceforge.scuba.smartcards.IResponseAPDU;

/**
 * A response APDU as defined in ISO/IEC 7816-4. It consists of a conditional
 * body and a two byte trailer.
 * This class does not attempt to verify that the APDU encodes a semantically
 * valid response.
 *
 * <p>Instances of this class are immutable. Where data is passed in or out
 * via byte arrays, defensive cloning is performed.
 *
 * @see CommandAPDU
 *
 * @since   1.6
 * @author  Andreas Sterbenz
 * @author  JSR 268 Expert Group
 */
public final class ResponseAPDU implements java.io.Serializable, IResponseAPDU {

    private static final long serialVersionUID = 6962744978375594225L;

    /** @serial */
    private byte[] apdu;

    /**
     * Constructs a ResponseAPDU from a byte array containing the complete
     * APDU contents (conditional body and trailed).
     *
     * <p>Note that the byte array is cloned to protect against subsequent
     * modification.
     *
     * @param apdu the complete response APDU
     *
     * @throws NullPointerException if apdu is null
     * @throws IllegalArgumentException if apdu.length is less than 2
     */
    public ResponseAPDU(byte[] apdu) {
        apdu = apdu.clone();
        check(apdu);
        this.apdu = apdu;
    }

    private static void check(byte[] apdu) {
        if (apdu.length < 2) {
            throw new IllegalArgumentException("apdu must be at least 2 bytes long");
        }
    }

    /* (non-Javadoc)
	 * @see scuba.smartcards.indep.IResponseAPDU#getNr()
	 */
    public int getNr() {
        return apdu.length - 2;
    }

    /* (non-Javadoc)
	 * @see scuba.smartcards.indep.IResponseAPDU#getData()
	 */
    public byte[] getData() {
        byte[] data = new byte[apdu.length - 2];
        System.arraycopy(apdu, 0, data, 0, data.length);
        return data;
    }

    /* (non-Javadoc)
	 * @see scuba.smartcards.indep.IResponseAPDU#getSW1()
	 */
    public int getSW1() {
        return apdu[apdu.length - 2] & 0xff;
    }

    /* (non-Javadoc)
	 * @see scuba.smartcards.indep.IResponseAPDU#getSW2()
	 */
    public int getSW2() {
        return apdu[apdu.length - 1] & 0xff;
    }

    /* (non-Javadoc)
	 * @see scuba.smartcards.indep.IResponseAPDU#getSW()
	 */
    public int getSW() {
        return (getSW1() << 8) | getSW2();
    }

    /* (non-Javadoc)
	 * @see scuba.smartcards.indep.IResponseAPDU#getBytes()
	 */
    public byte[] getBytes() {
        return apdu.clone();
    }

    /* (non-Javadoc)
	 * @see scuba.smartcards.indep.IResponseAPDU#toString()
	 */
    public String toString() {
        return "ResponseAPDU: " + apdu.length + " bytes, SW="
            + Integer.toHexString(getSW());
    }

    /* (non-Javadoc)
	 * @see scuba.smartcards.indep.IResponseAPDU#equals(java.lang.Object)
	 */
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof ResponseAPDU == false) {
            return false;
        }
        ResponseAPDU other = (ResponseAPDU)obj;
        return Arrays.equals(this.apdu, other.apdu);
    }

    /* (non-Javadoc)
	 * @see scuba.smartcards.indep.IResponseAPDU#hashCode()
	 */
    public int hashCode() {
        return Arrays.hashCode(apdu);
    }

    private void readObject(java.io.ObjectInputStream in)
            throws java.io.IOException, ClassNotFoundException {
        apdu = (byte[])in.readUnshared();
        check(apdu);
    }

}
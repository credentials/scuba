/*
 * $Id: $
 */

package net.sourceforge.scuba.data;

import java.io.Serializable;

/**
 * ISO 3166 country codes.
 * Table based on Wikipedia information.
 * 
 * @author Martijn Oostdijk (martijn.oostdijk@gmail.com)
 */
public abstract class Country implements Serializable
{
	private static final long serialVersionUID = 7220597955847617859L;

	public static Country getInstance(int code) {
		return ISOCountry.getInstance(code);
	}

	public static Country getInstance(String code) {
		return ISOCountry.getInstance(code);
	}

	public static Country[] values() {
		return ISOCountry.values();
	}

	public abstract int valueOf();

	public abstract String getName();

	public abstract String toAlpha2Code();

	public abstract String toAlpha3Code();
}

/*
 * $Id: $
 */

package net.sourceforge.scuba.data;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Generic country data type.
 * 
 * @author Martijn Oostdijk (martijn.oostdijk@gmail.com)
 */
public abstract class Country
{
	private static final Class<?>[] SUB_CLASSES = { ISOCountry.class, TestCountry.class };

	public static Country getInstance(int code) {
		for (Country country: values()) {
			if (country.valueOf() == code) { return country; }
		}
		throw new IllegalArgumentException("Illegal country code" + Integer.toHexString(code));
	}

	public static Country getInstance(String code) {
		if (code == null) { throw new IllegalArgumentException("Illegal country code " + code); }
		code = code.trim();
		switch (code.length()) {
		case 2: return fromAlpha2(code);
		case 3: return fromAlpha3(code);
		default: throw new IllegalArgumentException("Illegal country code " + code);
		}
	}

	public static Country[] values() {
		List<Country> result = new ArrayList<Country>();
		for (Class<?> subClass: SUB_CLASSES) {
			if (!(Country.class.isAssignableFrom(subClass))) { continue; }
			try {
				Method method = subClass.getMethod("values");
				Country[] subClassValues = (Country[])method.invoke(null);
				result.addAll(Arrays.asList(subClassValues));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		Country[] values = new Country[result.size()];
		result.toArray(values);
		return values;
	}

	public abstract int valueOf();

	public abstract String getName();

	public abstract String toAlpha2Code();

	public abstract String toAlpha3Code();

	private static Country fromAlpha2(String code) {
		for (Country country: ISOCountry.values()) {
			if (country.toAlpha2Code().equals(code)) { return country; }
		}
		throw new IllegalArgumentException("Unknown country code " + code);
	}

	private static Country fromAlpha3(String code) {
		for (Country country: ISOCountry.values()) {
			if (country.toAlpha3Code().equals(code)) { return country; }
		}
		throw new IllegalArgumentException("Unknown country code " + code);
	}
}

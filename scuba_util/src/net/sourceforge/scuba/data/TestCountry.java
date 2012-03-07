/*
 * $Id: $
 */

package net.sourceforge.scuba.data;

public class TestCountry extends Country {

	public static final TestCountry UT = new TestCountry(-1, "UT", "UTO", "Utopia");

	private int code;
	private String alpha2Code;
	private String alpha3Code;
	private String name;

	private static final TestCountry[] VALUES = { UT };
	
	public static TestCountry[] values() {
		return VALUES;
	}
	
	private TestCountry() {
	}
	
	private TestCountry(int code, String alpha2Code, String alpha3Code, String name) {
		this.code = code;
		this.alpha2Code = alpha2Code;
		this.alpha3Code = alpha3Code;
		this.name = name;
	}
	
	public int valueOf() {
		return code;
	}

	public String getName() {
		return name;
	}

	public String toAlpha2Code() {
		return alpha2Code;
	}

	public String toAlpha3Code() {
		return alpha3Code;
	}
	
	public String toString() {
		return alpha2Code;
	}
	
	public boolean equals(Object other) {
		if (other == null) { return false; }
		if (other == this) { return true; }
		if (!(getClass().equals(other.getClass()))) { return false; }
		TestCountry otherCountry = (TestCountry)other;
		return alpha3Code.equals(otherCountry.alpha3Code);
	}
	
	public int hashCode() {
		return 2 * alpha3Code.hashCode() + 31;
	}
}

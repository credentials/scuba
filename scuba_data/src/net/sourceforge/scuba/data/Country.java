/*
 * $Id: $
 */

package net.sourceforge.scuba.data;


/**
 * ISO 3166 country codes.
 * Table based on Wikipedia information.
 * 
 * @author Martijn Oostdijk (martijn.oostdijk@gmail.com)
 */
public interface  Country
{
	int valueOf();

	String getName();

	String toAlpha2Code();

	String toAlpha3Code();
}

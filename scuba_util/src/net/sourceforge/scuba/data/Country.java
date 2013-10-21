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

package net.sourceforge.scuba.data;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Generic country data type.
 * See {@link ISOCountry} and {@link TestCountry} for concrete implementations.
 * 
 * @author Martijn Oostdijk (martijn.oostdijk@gmail.com)
 * 
 * @version $Revision: $
 */
public abstract class Country {

    private static final Class<?>[] SUB_CLASSES = { UnicodeCountry.class, ISOCountry.class, TestCountry.class };

    /**
     * Gets a country given a country code.
     * 
     * @param code an ISO 3166 code
     * 
     * @return a country
     */
    public static Country getInstance(int code) {
        for (Country country: values()) {
            if (country.valueOf() == code) { return country; }
        }
        throw new IllegalArgumentException("Illegal country code" + Integer.toHexString(code));
    }

    /**
     * Gets a country given a two or three letter code.
     * 
     * @param code an alpha code
     * 
     * @return a country
     */
    public static Country getInstance(String code) {
        if (code == null) { throw new IllegalArgumentException("Illegal country code"); }
        code = code.trim();
        switch (code.length()) {
        case 2: return fromAlpha2(code);
        case 3: return fromAlpha3(code);
        default: throw new IllegalArgumentException("Illegal country code " + code);
        }
    }

    /**
     * All countries.
     * 
     * @return an array containing all countries
     */
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

    /**
     * Gets the numerical value (the code) of this country.
     * 
     * @return the numerical value
     */
    public abstract int valueOf();

    /**
     * Gets the full name of the country.
     * 
     * @return a country name
     */
    public abstract String getName();

    /**
     * Gets the adjectival form corresponding to the country.
     * 
     * @return the nationality
     */
    public abstract String getNationality();
    
    /**
     * Gets the two-digit country code.
     * 
     * @return a two-digit country code
     */
    public abstract String toAlpha2Code();

    /**
     * Gets the three-digit country code.
     * 
     * @return a three-digit country code
     */
    public abstract String toAlpha3Code();

    /* ONLY PRIVATE METHODS BELOW */
    
    private static Country fromAlpha2(String code) {
        for (Country country: values()) {
            if (country.toAlpha2Code().equals(code)) { return country; }
        }
        throw new IllegalArgumentException("Unknown country code " + code);
    }

    private static Country fromAlpha3(String code) {
        for (Country country: values()) {
            if (country.toAlpha3Code().equals(code)) { return country; }
        }
        throw new IllegalArgumentException("Unknown country code " + code);
    }
}

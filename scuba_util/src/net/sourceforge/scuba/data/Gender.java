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
 * $Id: Gender.java 186 2012-09-11 15:24:38Z martijno $
 */

package net.sourceforge.scuba.data;

/**
 * Possible values for a person's gender.
 * Integer values correspond to Section 5.5.3 of ISO 19794-5.
 * 
 * @author Martijn Oostdijk (martijn.oostdijk@gmail.com)
 * @author Cees-Bart Breunesse (ceesb@riscure.com)
 */
public enum Gender {

	MALE { public int toInt() { return 0x01; } }, 
	FEMALE {public int toInt() { return 0x02; }}, 
	UNKNOWN { public int toInt() { return 0x03; } }, 
	UNSPECIFIED {public int toInt() { return 0x00; } };

	public abstract int toInt();

	public static Gender getInstance(int b) {
		for(Gender g : Gender.values()) {
			if(g.toInt() == b) {
				return g;
			}
		}
		return null;
	}
}

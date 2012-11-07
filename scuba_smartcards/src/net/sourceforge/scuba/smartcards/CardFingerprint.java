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
 * $Id: CardFingerprint.java 203 2012-11-06 11:25:05Z martijno $
 */

package net.sourceforge.scuba.smartcards;

import java.util.Properties;

/**
 * A fingerprint determines certain properties of a connected smart card.
 *
 * TODO: For long term: maybe turn this into some CardType like system similar to OCF?
 * 
 * @author Martijn Oostdijk (martijn.oostdijk@gmail.com)
 */
public interface CardFingerprint {

	Properties guessProperties();
}

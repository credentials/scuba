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
 * $Id: CardServiceException.java 197 2012-10-22 21:50:55Z martijno $
 */

package net.sourceforge.scuba.smartcards;

/**
 * CardServiceExceptions are used to signal error Response APDUs , ie responses
 * different from 0x9000, but also low level errors.
 * 
 * @author erikpoll
 * 
 */

public class CardServiceException extends Exception {

	private static final long serialVersionUID = 4489156194716970879L;

	/**
	 * The status word that caused this exception, or -1 if not known or
	 * recorded.
	 */
	private int sw = -1;

	public CardServiceException(String msg) {
		super(msg);
	}

	/**
	 * Create a CardServiceException with a status word
	 * 
	 * @param sw
	 *            The status word that caused this CardServiceException
	 */
	public CardServiceException(String msg, int sw) {
		super(msg);
		this.sw = sw;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	/**
	 * @return The status word that caused this exception
	 */
	public int getSW() {
		return sw;
	}

}

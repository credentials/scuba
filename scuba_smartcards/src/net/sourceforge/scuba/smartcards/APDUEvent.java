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
 * $Id: APDUEvent.java 188 2012-09-28 21:47:13Z martijno $
 */

package net.sourceforge.scuba.smartcards;

import java.util.EventObject;

public class APDUEvent extends EventObject
{
	private static final long serialVersionUID = 7152351242541552732L;

	private Object type;
	private int sequenceNumber;
	private CommandAPDU capdu;
	private ResponseAPDU rapdu;

	public APDUEvent(Object source, Object type, int sequenceNumber, CommandAPDU capdu, ResponseAPDU rapdu) {
		super(source);
		this.type = type;
		this.sequenceNumber = sequenceNumber;
		this.capdu = capdu;
		this.rapdu = rapdu;
	}
	
	public Object getType() { return type; }
	
	public int getSequenceNumber() { return sequenceNumber; }
	
	public CommandAPDU getCommandAPDU() { return capdu; }

	public ResponseAPDU getResponseAPDU() { return rapdu; }
}

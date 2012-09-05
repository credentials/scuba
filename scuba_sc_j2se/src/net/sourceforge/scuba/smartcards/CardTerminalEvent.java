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
 * Copyright (C) 2011-2012 The SCUBA team.
 * 
 * $Id: CardTerminalEvent.java 183 2012-09-04 18:54:58Z pimvullers $
 */

package net.sourceforge.scuba.smartcards;

import java.util.EventObject;

import javax.smartcardio.CardTerminal;

/**
 * Event for terminal addition and removal.
 *
 * @author Pim Vullers (pim@cs.ru.nl)
 *
 * @version $Revision: 183 $
 */
public class CardTerminalEvent extends EventObject
{
    private static final long serialVersionUID = 8884602877518044124L;

    /** Event type constant. */
	public static final int REMOVED = 0, ADDED = 1;

	private int type;
	private CardTerminal terminal;

	/**
	 * Creates an event.
	 *
	 * @param type event type
	 * @param terminal event source
	 */
	public CardTerminalEvent(int type, CardTerminal terminal) {
		super(terminal);
		this.type = type;
		this.terminal = terminal;
	}

	/**
	 * Gets the event type.
	 *
	 * @return event type
	 */
	public int getType() {
		return type;
	}

	/**
	 * Gets the event source.
	 *
	 * @return event source
	 */
	public CardTerminal getTerminal() {
		return terminal;
	}

	/**
	 * Gets a textual representation of this event.
	 * 
	 * @return a textual representation of this event
	 */
	public String toString() {
		switch (type) {
		case REMOVED: return "Terminal '" + terminal + "' removed";
		case ADDED: return "Terminal '" + terminal + "' added";
		}
		return "CardTerminalEvent " + terminal;
	}

	/**
	 * Whether this event is equal to the event in <code>other</code>.
	 * 
	 * @return a boolean
	 */
	public boolean equals(Object other) {
		try {
			if (other == null) { return false; }
			if (other == this) { return true; }
			if (!other.getClass().equals(this.getClass())) { return false; }
			CardTerminalEvent otherCardTerminalEvent = (CardTerminalEvent)other;
			return type == otherCardTerminalEvent.type && terminal.equals(otherCardTerminalEvent.terminal);
		} catch (ClassCastException cce) {
			return false;
		}
	}

	/**
	 * Gets a hash code for this event.
	 * 
	 * @return a hash code for this event
	 */
	public int hashCode() {
		return 5 * terminal.hashCode() + 7 * type;
	}
}

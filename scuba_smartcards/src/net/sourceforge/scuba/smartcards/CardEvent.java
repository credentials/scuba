/*
 * SCUBA smart card framework.
 *
 * Copyright (C) 2009  The SCUBA team.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * $Id: $
 */

package net.sourceforge.scuba.smartcards;

import java.util.EventObject;

/**
 * Event for card insertion and removal.
 *
 * @author Martijn Oostdijk (martijn.oostdijk@gmail.com)
 *
 * @version $Revision: $
 */
public class CardEvent<C, R> extends EventObject
{
	private static final long serialVersionUID = -5645277246646615351L;

	/** Event type constant. */
	public static final int REMOVED = 0, INSERTED = 1;

	private int type;
	private CardService<C, R> service;

	/**
	 * Creates an event.
	 *
	 * @param type event type
	 * @param service event source
	 */
	public CardEvent(int type, CardService<C, R> service) {
		super(service);
		this.type = type;
		this.service = service;
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
	public CardService<C, R> getService() {
		return service;
	}

	/**
	 * Gets a textual representation of this event.
	 * 
	 * @return a textual representation of this event
	 */
	public String toString() {
		switch (type) {
		case REMOVED: return "Card removed from " + service;
		case INSERTED: return "Card inserted in " + service;
		}
		return "CardEvent " + service;
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
			@SuppressWarnings("unchecked")
			CardEvent<C, R> otherCardEvent = (CardEvent<C, R>)other;
			return type == otherCardEvent.type && service.equals(otherCardEvent.service);
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
		return 5 * service.hashCode() + 7 * type;
	}
}

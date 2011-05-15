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

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;

import javax.smartcardio.CommandAPDU;
import javax.smartcardio.ResponseAPDU;

/**
 * Default abstract service. Provides some functionality for observing apdu
 * events.
 * 
 * @author Cees-Bart Breunesse (ceesb@cs.ru.nl)
 * @author Martijn Oostdijk (martijno@cs.ru.nl)
 * @version $Revision: 259 $
 */
public abstract class CardService implements Serializable
{
	private static final long serialVersionUID = 5618527358158494957L;

	static protected final int SESSION_STOPPED_STATE = 0;

	static protected final int SESSION_STARTED_STATE = 1;

	/** The apduListeners. */
	private Collection<APDUListener> apduListeners;

	/*
	 * @ invariant state == SESSION_STOPPED_STATE || state ==
	 * SESSION_STARTED_STATE;
	 */
	protected int state;

	protected boolean listeners = true;

	/**
	 * Creates a new service.
	 */
	public CardService() {
		apduListeners = new HashSet<APDUListener>();
		state = SESSION_STOPPED_STATE;
	}

	/**
	 * Adds a listener.
	 * 
	 * @param l the listener to add
	 */
	public void addAPDUListener(APDUListener l) {
		apduListeners.add(l);
	}

	/**
	 * Removes the listener <code>l</code>, if present.
	 * 
	 * @param l the listener to remove
	 */
	public void removeAPDUListener(APDUListener l) {
		apduListeners.remove(l);
	}

	/**
	 * Opens a session with the card. Selects a reader. Connects to the card.
	 * Notifies any interested apduListeners.
	 */
	/*
	 * @ requires state == SESSION_STOPPED_STATE;
	 * @ ensures state == SESSION_STARTED_STATE;
	 */
	public abstract void open() throws CardServiceException;

	/*
	 * @ ensures \result == (state == SESSION_STARTED_STATE);
	 */
	public abstract boolean isOpen();

	/**
	 * Sends and apdu to the card. Notifies any interested apduListeners.
	 * This method does not throw a CardServiceException if the ResponseAPDU
	 * is status word indicating error.
	 * 
	 * @param apdu the command apdu to send.
	 * @return the response from the card, including the status word.
	 * @throws CardServiceException - if the card operation failed 
	 */
	/*
	 * @ requires state == SESSION_STARTED_STATE; @ ensures state ==
	 * SESSION_STARTED_STATE;
	 */
	public abstract ResponseAPDU transmit(CommandAPDU apdu) throws CardServiceException;

	/**
	 * Closes the session with the card. Disconnects from the card and reader.
	 * Notifies any interested apduListeners.
	 */
	/*
	 * @ requires state == SESSION_STARTED_STATE; @ ensures state ==
	 * SESSION_STOPPED_STATE;
	 */
	public abstract void close();


	/**
	 * Notifies listeners about APDU event.
	 * 
	 * @param capdu APDU event
	 */
	protected void notifyExchangedAPDU(CommandAPDU capdu, ResponseAPDU rapdu) {
		if(listeners) {
			for (APDUListener listener: apduListeners) {
				listener.exchangedAPDU(new APDUEvent(this, capdu, rapdu));
			}
		}
	}

	public void setListenersState(boolean state) {
		listeners = state;       
	}
}

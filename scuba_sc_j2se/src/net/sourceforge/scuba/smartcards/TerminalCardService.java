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
 * $Id: TerminalCardService.java 207 2012-11-14 08:20:57Z martijno $
 */

package net.sourceforge.scuba.smartcards;

import javax.smartcardio.Card;
import javax.smartcardio.CardChannel;
import javax.smartcardio.CardException;
import javax.smartcardio.CardTerminal;

/**
 * Card service implementation for sending APDUs to a terminal using the
 * JSR 268 (<code>javax.smartcardio</code>) classes available in Java
 * SDK 6.0 and higher.
 * 
 * @author Martijn Oostdijk (martijno@cs.ru.nl)
 * 
 * @version $Revision: 207 $
 */
public class TerminalCardService extends CardService {

	private static final long serialVersionUID = 7918176921505623791L;

	private CardTerminal terminal;
	private Card card;
	private CardChannel channel;
	private long lastActiveTime;
	private int apduCount;

	/**
	 * Constructs a new card service.
	 * 
	 * @param terminal the card terminal to connect to
	 */
	public TerminalCardService(CardTerminal terminal) {
		this.terminal = terminal;
		lastActiveTime = System.currentTimeMillis();
		apduCount = 0;
	}
	
	/**
	 * Opens a session with the card.
	 */
	public void open() throws CardServiceException {
		if (isOpen()) { return; }
		try {
			card = terminal.connect("*");
			channel = card.getBasicChannel();
			if (channel == null) { 
				throw new CardServiceException("channel == null"); 
			}
			state = SESSION_STARTED_STATE;
		} catch (CardException ce) {
			throw new CardServiceException(ce.toString());
		}
	}

	/**
	 * Whether there is an open session with the card.
	 */
	public boolean isOpen() {
		return (state != SESSION_STOPPED_STATE);
	}

	/**
	 * Sends an APDU to the card.
	 * 
	 * @param ourCommandAPDU the command apdu to send
	 * @return the response from the card, including the status word
	 * @throws CardServiceException - if the card operation failed 
	 */
	public ResponseAPDU transmit(CommandAPDU ourCommandAPDU) 
	throws CardServiceException {
		try {
			if (channel == null) {
				throw new CardServiceException("channel == null");
			}
			javax.smartcardio.CommandAPDU command = new javax.smartcardio.CommandAPDU(ourCommandAPDU.getBytes());
			javax.smartcardio.ResponseAPDU response = channel.transmit(command);
			ResponseAPDU ourResponseAPDU = new ResponseAPDU(response.getBytes());
			notifyExchangedAPDU(++apduCount, ourCommandAPDU, ourResponseAPDU);
			lastActiveTime = System.currentTimeMillis();
			return ourResponseAPDU;
		} catch (CardException ce) {
			ce.printStackTrace();
			throw new CardServiceException(ce.toString());
		}
	}
	
	public byte[] getATR() {
		javax.smartcardio.ATR atr = channel.getCard().getATR();
		return atr.getBytes();
	}

	/**
	 * Sends a control command to the terminal
	 *
	 * @param controlCode the control code to send
	 * @param command the command data for the terminal
	 * @return response from the terminal/card
	 * @throws CardServiceException - if the card operation failed
	 */
	public byte[] transmitControlCommand(int controlCode, byte[] command)
	throws CardServiceException {
		try {
			return card.transmitControlCommand(controlCode, command);
		} catch (CardException ce) {
			ce.printStackTrace();
			throw new CardServiceException(ce.toString());
		}
	}

	/**
	 * Closes the session with the card.
	 */
	public void close() {
		try {
			if (card != null) {
                // WARNING: Woj: the meaning of the reset flag is actually
                // reversed w.r.t. to the official documentation, false means
                // that the card is going to be reset, true means do not reset
                // This is a bug in the smartcardio implementation from SUN
				// Moreover, Linux PCSC implementation goes numb if you try to
				// disconnect a card that is not there anymore.
				if(terminal.isCardPresent()) {
				  card.disconnect(false);
				}
			}
			state = SESSION_STOPPED_STATE;
		} catch (Exception ce) {
			/* Disconnect failed? Fine... */
		}
	}
	
	/**
	 * The terminal used by this service.
	 *
	 * @return a terminal
	 */
	public CardTerminal getTerminal() {
		return terminal;
	}
	
	/* package visible */ long getLastActiveTime() {
	   return lastActiveTime;
	}
	
	/**
	 * Produces a textual representation of this service.
	 * 
	 * @return a textual representation of this service
	 */
	public String toString() {
		return "TerminalCardService [" + terminal.getName() + "]";
	}
}

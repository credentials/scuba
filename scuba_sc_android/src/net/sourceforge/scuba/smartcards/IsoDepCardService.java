/*
 * SCUBA smart card framework.
 *
 * Copyright (C) 2012  The SCUBA team.
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
 */
/*
 * Based on work by Max Guenther (max.math.guenther@googlemail.com) for 
 * aJMRTD (An Android Client for JMRTD) who released it under the following
 * license:
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

 * $Id: $
 */

package net.sourceforge.scuba.smartcards;

import android.nfc.tech.IsoDep;

import java.io.IOException;

import net.sourceforge.scuba.smartcards.indep.CommandAPDU;
import net.sourceforge.scuba.smartcards.indep.ResponseAPDU;
import net.sourceforge.scuba.smartcards.indep.SCFactory;

/**
 * Card service implementation for sending APDUs to a terminal using the
 * Android NFC (<code>android.nfc.tech</code>) classes available in Android
 * SDK 2.3.3 (API 10) and higher.
 * 
 * @author Pim Vullers (pim@cs.ru.nl)
 * 
 * @version $Revision$
 */
public class IsoDepCardService extends CardService<CommandAPDU,ResponseAPDU> {

	private static final long serialVersionUID = -8123218195642784731L;
	
	private IsoDep nfc;
	private int apduCount;
	
	/**
	 * Constructs a new card service.
	 * 
	 * @param terminal the card terminal to connect to
	 */
    public IsoDepCardService(IsoDep nfc) {
        this.nfc = nfc;
        apduCount = 0;
    }
    
	/**
	 * Return the factory which should be used to construct APDUs for this 
	 * service.
	 */
    public ISCFactory<CommandAPDU, ResponseAPDU> getAPDUFactory() {
    	return new SCFactory();
    }
    
	/**
	 * Opens a session with the card.
	 */
    public void open() throws CardServiceException {
		if (isOpen()) { return; }
        try {
            nfc.connect();
            if (nfc.isConnected()) { 
            	throw new CardServiceException("failed to connect");
            }
			state = SESSION_STARTED_STATE;
        } catch (IOException e) {
            throw new CardServiceException(e.toString());
        }
    }

	/**
	 * Whether there is an open session with the card.
	 */
    public boolean isOpen() {
    	if (nfc.isConnected()) {
    		state = SESSION_STARTED_STATE;
    	} else {
    		state = SESSION_STOPPED_STATE;
    	}
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
        	if (!nfc.isConnected()) {
        		throw new CardServiceException("not connected");
        	}
        	ResponseAPDU ourResponseAPDU = new ResponseAPDU(
        			nfc.transceive(ourCommandAPDU.getBytes()));
			notifyExchangedAPDU(++apduCount, ourCommandAPDU, ourResponseAPDU);
			return ourResponseAPDU;
        } catch (IOException e) {
            throw new CardServiceException("could not transmit");
        }
    }

	/**
	 * Closes the session with the card.
	 */
    public void close() {
        try {
            nfc.close();
            state = SESSION_STOPPED_STATE;
        } catch (IOException e) {
			/* Disconnect failed? Fine... */
        }
    }
}

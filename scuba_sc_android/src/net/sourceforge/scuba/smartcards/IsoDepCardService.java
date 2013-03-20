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
 * Inspired by the work of Max Guenther (max.math.guenther@googlemail.com) for
 * aJMRTD (an Android client for JMRTD, released under the LGPL license).
 *
 * Copyright (C) 2012 The SCUBA team.
 *
 * $Id: IsoDepCardService.java 214 2013-02-19 22:03:49Z martijno $
 */

package net.sourceforge.scuba.smartcards;

import java.io.IOException;

import android.nfc.tech.IsoDep;

/**
 * Card service implementation for sending APDUs to a terminal using the
 * Android NFC (<code>android.nfc.tech</code>) classes available in Android
 * SDK 2.3.3 (API 10) and higher.
 *
 * @author Pim Vullers (pim@cs.ru.nl)
 *
 * @version $Revision: 214 $
 */
public class IsoDepCardService extends CardService {

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
     * Opens a session with the card.
     */
    @Override
    public void open() throws CardServiceException {
        if (isOpen()) { return; }
        try {
            nfc.connect();
            if (!nfc.isConnected()) {
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
    @Override
    public boolean isOpen() {
        if (nfc.isConnected()) {
            state = SESSION_STARTED_STATE;
            return true;
        } else {
            state = SESSION_STOPPED_STATE;
            return false;
        }
    }

    /**
     * Sends an APDU to the card.
     *
     * @param ourCommandAPDU the command apdu to send
     * @return the response from the card, including the status word
     * @throws CardServiceException - if the card operation failed
     */
    @Override
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

    @Override
    public byte[] transmitControlCommand(int controlCode, byte[] command)
    throws CardServiceException {
        return null;
    }

    @Override
    public byte[] getATR() {
        return null; // FIXME
    }

    @Override
    public String getName() {
        return "Android NFC";
    }

    @Override
    public boolean isExtendedAPDULengthSupported() {
        return false; /* FIXME, from API 14 and up we can ask ISODep!! */
    }

    /**
     * Closes the session with the card.
     */
    @Override
    public void close() {
        try {
            nfc.close();
        } catch (IOException e) {
            /* Disconnect failed? Fine... */
        }
        state = SESSION_STOPPED_STATE;
    }
}

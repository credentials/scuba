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
 * $Id: TerminalCardService.java 214 2013-02-19 22:03:49Z martijno $
 */

package net.sourceforge.scuba.smartcards;

import java.io.IOException;

import net.sourceforge.scuba.util.Hex;

/**
 * Card service implementation for printing command APDUs on a console and
 * receiving response APDUs from the console input.
 *
 * @author Pim Vullers (pim@cs.ru.nl)
 *
 * @version $Revision: 214 $
 */
public class InteractiveConsoleCardService extends CardService {

    private static final long serialVersionUID = 7918176921505623791L;

    private long lastActiveTime;
    private int apduCount;
    private java.io.BufferedReader console = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));

    /**
     * Constructs a new card service.
     *
     * @param terminal the card terminal to connect to
     */
    public InteractiveConsoleCardService() {
        lastActiveTime = System.currentTimeMillis();
        apduCount = 0;
    }

    /**
     * Opens a session with the card.
     */
    public void open() throws CardServiceException {
        if (isOpen()) { return; }
        System.out.println("Opening sesion...");
        state = SESSION_STARTED_STATE;
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
            System.out.println("Command:  " + Hex.toHexString(ourCommandAPDU.getBytes()));
            System.out.print("Response? ");
            String response = console.readLine();
            ResponseAPDU ourResponseAPDU = new ResponseAPDU(Hex.hexStringToBytes(response));
            notifyExchangedAPDU(++apduCount, ourCommandAPDU, ourResponseAPDU);
            lastActiveTime = System.currentTimeMillis();
            return ourResponseAPDU;
        } catch (Exception e) {
            e.printStackTrace();
            throw new CardServiceException(e.toString());
        }
    }

    public byte[] transmitControlCommand(int controlCode, byte[] command)
    throws CardServiceException {
        return null;
    }

    public byte[] getATR() {
        System.out.print("ATR? ");
        String atr = "";
        try {
            atr = console.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Hex.hexStringToBytes(atr);
    }

    public String getName() {
        return "InteractiveConsole";
    }

    public boolean isExtendedAPDULengthSupported() {
        return true; // FIXME: ask user input
    }


    /**
     * Closes the session with the card.
     */
    public void close() {
        System.out.println("Closing session...");
        state = SESSION_STOPPED_STATE;
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
        return "InteractiveConsoleCardService";
    }
}

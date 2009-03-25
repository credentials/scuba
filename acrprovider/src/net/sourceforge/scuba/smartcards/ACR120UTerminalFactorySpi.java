/*
 * ACR 120 USB driver for javax.smartcardio framework.
 * Copyright (C) 2008  Martijn Oostdijk
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.smartcardio.CardException;
import javax.smartcardio.CardTerminal;
import javax.smartcardio.CardTerminals;
import javax.smartcardio.TerminalFactorySpi;

/**
 * This creates ACR120UCardTerminal instances.
 *
 * @author Martijn Oostdijk (martijn.oostdijk@gmail.com)
 * @version $Revision: $
 */
public class ACR120UTerminalFactorySpi  extends TerminalFactorySpi
{
	private static final Map<Integer, CardTerminal> TERMINALS = new HashMap<Integer, CardTerminal>();

	public ACR120UTerminalFactorySpi(Object parameter) {
		addReader(ACR120UCardTerminal.ACR120_USB1);
		addReader(ACR120UCardTerminal.ACR120_USB2);
		addReader(ACR120UCardTerminal.ACR120_USB3);
		addReader(ACR120UCardTerminal.ACR120_USB4);
		addReader(ACR120UCardTerminal.ACR120_USB5);
		addReader(ACR120UCardTerminal.ACR120_USB6);
		addReader(ACR120UCardTerminal.ACR120_USB7);
		addReader(ACR120UCardTerminal.ACR120_USB8);
	}

	private void addReader(int port) {
		try {
			if (!TERMINALS.containsKey(port)) { TERMINALS.put(port, new ACR120UCardTerminal(port)); }
		} catch (CardException ce) {
			/* NOTE: Reader not connected? Fine, don't add it to list. */
		}
	}

	protected CardTerminals engineTerminals() {
		return new CardTerminals() {
			public List<CardTerminal> list(State state) throws CardException {
				return new ArrayList<CardTerminal>(TERMINALS.values());
			}

			public boolean waitForChange(long timeout) throws CardException {
				return false;
			}		
		};
	}
	
	public String toString() {
		return "ACSCardTerminalFactory " + TERMINALS;
	}
}

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

import java.io.PrintStream;

import net.sourceforge.scuba.util.Hex;

/**
 * A dummy card service to produce APDU traces instead of the actual communication
 * with CAD. So far can only serve APDUs that expect 9000 status words.
 * 
 * @author Wojciech Mostowski <woj@cs.ru.nl>
 *
 */
public class DummyAcceptingCardService<C,R> extends CardService<C,R>
{
	private static final long serialVersionUID = 959248891375637853L;

	private transient PrintStream out = null;
    
    private boolean closed = false;
    
    public DummyAcceptingCardService(PrintStream out) {
        this.out = out;
    }
    
    public void close() {
        out.flush();
        out.close();
        closed = true;
    }

    public boolean isOpen() {
        return !closed;
    }

    public void open() throws CardServiceException {
    }

    public R transmit(C apdu) throws CardServiceException {
        ScubaSmartcards<C, R> sc = ScubaSmartcards.getInstance();
    	
    	String c = Hex.bytesToHexString( sc.accesC(apdu).getBytes());
        String r = "9000";
        out.println("==> "+c);
        out.println("<== "+r);
        R response = sc.createResponseAPDU(Hex.hexStringToBytes(r));
        return response;
    }

}

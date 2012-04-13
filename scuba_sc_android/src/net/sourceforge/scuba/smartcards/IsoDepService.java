/*
 * aJMRTD - An Android Client for JMRTD, a Java API for accessing machine readable travel documents.
 *
 * Max Guenther, max.math.guenther@googlemail.com
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
 *
 */

package net.sourceforge.scuba.smartcards;

import android.nfc.tech.IsoDep;

import java.io.IOException;

import net.sourceforge.scuba.smartcards.CardService;
import net.sourceforge.scuba.smartcards.CardServiceException;
import net.sourceforge.scuba.smartcards.indep.CommandAPDU;
import net.sourceforge.scuba.smartcards.indep.ResponseAPDU;

public class IsoDepService extends CardService<CommandAPDU,ResponseAPDU> {

    private boolean isOpen = false;
    private IsoDep picc;
	
    public IsoDepService(IsoDep picc) {
        super();
        this.picc = picc;
    }

    @Override
    public void open() 
    throws CardServiceException {
        try {
            picc.connect();
            isOpen = true;
        } catch (IOException e) {
            e.printStackTrace();
            throw new CardServiceException("IOException");
        }
    }

    @Override
    public boolean isOpen() {
        return isOpen;
    }

    @Override
    public ResponseAPDU transmit(CommandAPDU apdu) 
    throws CardServiceException {
        try {
            return new ResponseAPDU(picc.transceive(apdu.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
            throw new CardServiceException("could not transmit");
        }
    }

    @Override
    public void close() {
        try {
            picc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        isOpen = false;
    }

}


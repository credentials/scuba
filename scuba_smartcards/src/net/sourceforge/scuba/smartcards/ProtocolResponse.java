/**
 * ProtocolResponse.java
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
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 * 
 * Copyright (C) Wouter Lueks, Radboud University Nijmegen, September 2012.
 */

package net.sourceforge.scuba.smartcards;

import net.sourceforge.scuba.smartcards.ResponseAPDU;

/**
 * Simple data structure for storing APDU responses from smart cards.
 * 
 * @author Wouter Lueks
 * @author Pim Vullers
 */
public class ProtocolResponse {
    
    /**
     * A short string used to uniquely identify this command. 
     */ 
    private String key;
    
    /**
     * The actual response APDU received from the smart card.
     */ 
    private ResponseAPDU response;

    /**
     * Construct a new ProtocolResponse.
     * 
     * @param key used to identify the response.
     * @param response from the smart card.
     */
    public ProtocolResponse(String key, ResponseAPDU response) {
        this.key = key;
        this.response = response;
    }
    
    /**
     * Get the key used to identify this response.
     * 
     * @return the key used to identify this response. 
     */
    public String getKey() {
        return key;
    }
    
    /**
     * Get the response APDU received from the smart card.
     * 
     * @return the response from the smart card.
     */
    public ResponseAPDU getAPDU() {
        return response;
    }

    /**
     * Set the response from the smart card.
     * 
     * @param apdu received from the smart card.
     */
    public void setAPDU(ResponseAPDU apdu) {
        this.response = apdu;
    }

    /**
     * Get the response data received from the smart card.
     * 
     * @return the response data from the smart card.
     */
    public byte[] getData() {
        return response.getData();
    }

    /**
     * Get the response status received from the smart card.
     * 
     * @return the response status from the smart card.
     */
    public int getStatus() {
        return response.getSW();
    }
}

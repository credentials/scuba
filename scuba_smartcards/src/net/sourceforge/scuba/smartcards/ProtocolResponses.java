/**
 * ProtocolResponses.java
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

import java.util.HashMap;

/**
 * Simple type declaration for a Map containing responses to protocol commands.
 * 
 * @author Wouter Lueks
 */
public class ProtocolResponses extends HashMap<String, ProtocolResponse> {

    /**
     * Universal version identifier to match versions during deserialisation.
     */
	private static final long serialVersionUID = 2358602306268421757L;
}

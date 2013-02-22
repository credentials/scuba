/**
 * ProtocolCommands.java
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
 * Copyright (C) Pim Vullers, Radboud University Nijmegen, September 2012.
 */

package net.sourceforge.scuba.smartcards;

import java.util.ArrayList;

/**
 * Simple type declaration for a List containing protocol commands.
 * 
 * @author Pim Vullers
 */
public class ProtocolCommands extends ArrayList<ProtocolCommand> {

    /**
     * Universal version identifier to match versions during deserialisation.
     */
	private static final long serialVersionUID = -3336896365299268066L;

}

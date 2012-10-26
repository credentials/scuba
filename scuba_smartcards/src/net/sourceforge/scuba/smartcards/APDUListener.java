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
 * $Id: APDUListener.java 197 2012-10-22 21:50:55Z martijno $
 */

package net.sourceforge.scuba.smartcards;

import java.util.EventListener;

/**
 * Specifies an event handler type to react to apdu events.
 * 
 * @author Engelbert Hubbers (hubbers@cs.ru.nl)
 * @author Martijn Oostdijk (martijno@cs.ru.nl)
 * @version $Revision: 197 $
 */
public interface APDUListener extends EventListener {

   /**
    * Is called after an apdu was exchanged.
    * 
    * @param e an APDU event containing the exchanged APDUs
    */
   void exchangedAPDU(APDUEvent e);
}

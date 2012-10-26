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
 * $Id: APDUWrapper.java 197 2012-10-22 21:50:55Z martijno $
 */

package net.sourceforge.scuba.smartcards;

/**
 * Wrapper interface for command APDU wrapping.
 * 
 * @author Cees-Bart Breunesse (ceesb@cs.ru.nl)
 * @author Martijn Oostdijk (martijno@cs.ru.nl)
 * 
 * @version $Revision: 197 $
 */
public interface APDUWrapper {

   /**
    * Wraps the command apdu buffer.
    * 
    * @param capdu should contain a header (length 4), an explicit lc (0 if
    *           no cdata), the cdata (of length lc), and an explicit le (0 if
    *           not specified).
    * @return wrapped apdu buffer
    */
   CommandAPDU wrap(CommandAPDU capdu);
   
   ResponseAPDU unwrap(ResponseAPDU rapdu, int len);
}
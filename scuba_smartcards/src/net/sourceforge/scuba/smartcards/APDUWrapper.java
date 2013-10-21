/*
 * This file is part of the SCUBA smart card framework.
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
 * Copyright (C) 2009-2013 The SCUBA team.
 *
 * $Id: $
 */

package net.sourceforge.scuba.smartcards;

/**
 * Wrapper interface for command APDU wrapping.
 * 
 * @author Cees-Bart Breunesse (ceesb@cs.ru.nl)
 * @author Martijn Oostdijk (martijno@cs.ru.nl)
 * 
 * @version $Revision: 203 $
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

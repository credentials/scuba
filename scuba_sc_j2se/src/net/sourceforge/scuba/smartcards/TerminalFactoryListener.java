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

import java.util.EventListener;

/**
 * Interface for terminal addition and removal event observers.
 *
 * @author Pim Vullers (pim@cs.ru.nl)
 *
 * @version $Revision: 183 $
 */
public interface TerminalFactoryListener extends EventListener
{
    /**
     * Called when terminal added.
     *
     * @param cte addition event
     */
    void cardTerminalAdded(CardTerminalEvent cte);

    /**
     * Called when terminal removed.
     *
     * @param cte removal event
     */
    void cardTerminalRemoved(CardTerminalEvent cte);
}

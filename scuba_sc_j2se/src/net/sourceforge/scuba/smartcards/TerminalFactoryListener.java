/*
 * SCUBA smart card framework.
 *
 * Copyright (C) 2011  The SCUBA team.
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

import java.util.EventListener;

/**
 * Interface for terminal addition and removal event observers.
 *
 * @author Pim Vullers (pim@cs.ru.nl)
 *
 * @version $Revision: $
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

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
 * Copyright (C) 2012 The SCUBA team.
 * 
 * $Id: WrappingCardService.java 188 2012-09-28 21:47:13Z martijno $
 */

package net.sourceforge.scuba.smartcards;

/**
 * CardService for easy wrapping of APDU messages.
 * 
 * @author Pim Vullers (pim@cs.ru.nl)
 *
 * @version $Revision: 188 $
 */
public class WrappingCardService extends CardService {

	private static final long serialVersionUID = -1872209495542386286L;

	private CardService service;
	private APDUWrapper wrapper;
	private boolean enabled;
	
	public WrappingCardService(CardService service, APDUWrapper wrapper) {
		this.service = service;
		this.wrapper = wrapper;
	}

	public void open() throws CardServiceException {
		service.open();
	}

	public boolean isOpen() {
		return service.isOpen();
	}

	public ResponseAPDU transmit(CommandAPDU capdu)
	throws CardServiceException {
		if (isEnabled()) {
			ResponseAPDU rapdu = service.transmit(wrapper.wrap(capdu));
			return wrapper.unwrap(rapdu, rapdu.getBytes().length);
		} else {
			return service.transmit(capdu);
		}
	}

	public void close() {
		service.close();
	}
	
	public void enable() {
		enabled = true;
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	
	public void disable() {
		enabled = false;
	}
}

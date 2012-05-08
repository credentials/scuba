/**
 * 
 */
package net.sourceforge.scuba.smartcards;

public class WrappingCardService extends CardService {

	private static final long serialVersionUID = -1872209495542386286L;

	private boolean enabled;
	private CardService service;
	private APDUWrapper wrapper;
	
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

	public IResponseAPDU transmit(ICommandAPDU capdu)
	throws CardServiceException {
		if (enabled) {
			IResponseAPDU rapdu = service.transmit(wrapper.wrap(capdu));
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

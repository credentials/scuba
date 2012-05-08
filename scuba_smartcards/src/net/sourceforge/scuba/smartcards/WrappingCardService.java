/**
 * 
 */
package net.sourceforge.scuba.smartcards;

public class WrappingCardService extends CardService {

	private static final long serialVersionUID = -1872209495542386286L;

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
		ICommandAPDU wrappedC = wrapper.wrap(capdu);
		IResponseAPDU rapdu = service.transmit(wrappedC);
		return wrapper.unwrap(rapdu, rapdu.getBytes().length);
	}

	public void close() {
		service.close();
	}
}

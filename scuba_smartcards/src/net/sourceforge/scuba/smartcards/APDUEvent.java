package net.sourceforge.scuba.smartcards;

import java.util.EventObject;

public class APDUEvent<C,R> extends EventObject
{
	private static final long serialVersionUID = 7152351242541552732L;

	private C capdu;
	private R rapdu;
	
	public APDUEvent(Object source, C capdu, R rapdu) {
		super(source);
		this.capdu = capdu;
		this.rapdu = rapdu;
	}
	
	public C getCommandAPDU() { return capdu; }
	
	public R getResponseAPDU() { return rapdu; }
}

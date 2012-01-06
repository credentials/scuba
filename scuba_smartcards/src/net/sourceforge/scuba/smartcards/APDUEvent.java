package net.sourceforge.scuba.smartcards;

import java.util.EventObject;

public class APDUEvent<C,R> extends EventObject
{
	private static final long serialVersionUID = 7152351242541552732L;

	private Object type;
	private int sequenceNumber;
	private C capdu;
	private R rapdu;

	public APDUEvent(Object source, Object type, int sequenceNumber, C capdu, R rapdu) {
		super(source);
		this.type = type;
		this.sequenceNumber = sequenceNumber;
		this.capdu = capdu;
		this.rapdu = rapdu;
	}
	
	public Object getType() { return type; }
	
	public int getSequenceNumber() { return sequenceNumber; }
	
	public C getCommandAPDU() { return capdu; }

	public R getResponseAPDU() { return rapdu; }
}

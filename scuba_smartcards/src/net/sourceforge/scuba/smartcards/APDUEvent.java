package net.sourceforge.scuba.smartcards;

import java.util.EventObject;

public class APDUEvent extends EventObject
{
	private static final long serialVersionUID = 7152351242541552732L;

	private Object type;
	private int sequenceNumber;
	private ICommandAPDU capdu;
	private IResponseAPDU rapdu;

	public APDUEvent(Object source, Object type, int sequenceNumber, ICommandAPDU capdu, IResponseAPDU rapdu) {
		super(source);
		this.type = type;
		this.sequenceNumber = sequenceNumber;
		this.capdu = capdu;
		this.rapdu = rapdu;
	}
	
	public Object getType() { return type; }
	
	public int getSequenceNumber() { return sequenceNumber; }
	
	public ICommandAPDU getCommandAPDU() { return capdu; }

	public IResponseAPDU getResponseAPDU() { return rapdu; }
}

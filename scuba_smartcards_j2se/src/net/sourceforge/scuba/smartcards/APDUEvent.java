package net.sourceforge.scuba.smartcards;

import java.util.EventObject;

import javax.smartcardio.CommandAPDU;
import javax.smartcardio.ResponseAPDU;

public class APDUEvent extends EventObject
{
	private static final long serialVersionUID = 7152351242541552732L;

	private CommandAPDU capdu;
	private ResponseAPDU rapdu;
	
	public APDUEvent(Object source, CommandAPDU capdu, ResponseAPDU rapdu) {
		super(source);
		this.capdu = capdu;
		this.rapdu = rapdu;
	}
	
	public CommandAPDU getCommandAPDU() { return capdu; }
	
	public ResponseAPDU getResponseAPDU() { return rapdu; }
}

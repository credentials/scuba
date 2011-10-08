package net.sourceforge.scuba.smartcards.indep;

import net.sourceforge.scuba.smartcards.ICommandAPDU;
import net.sourceforge.scuba.smartcards.IResponseAPDU;
import net.sourceforge.scuba.smartcards.ISCFactory;

public class SCFactory implements ISCFactory<CommandAPDU, ResponseAPDU> {

	@Override
	public ICommandAPDU createAccessorC(CommandAPDU c) {
		return c;
	}

	@Override
	public IResponseAPDU createAccessorR(ResponseAPDU r) {
		return r;
	}

	@Override
	public CommandAPDU createCommandAPDU(int cla, int ins, int p1, int p2, byte[] data, int ne) {
		return new CommandAPDU(cla, ins, p1, p2, data, ne);
	}

	@Override
	public CommandAPDU createCommandAPDU(int cla, int ins, int p1, int p2, byte[] data) {
		return new CommandAPDU(cla, ins, p1, p2, data);
	}

	@Override
	public CommandAPDU createCommandAPDU(int cla, int ins, int p1, int p2, int ne) {
		return new CommandAPDU(cla,ins, p1, p2, ne);
	}

	@Override
	public ResponseAPDU createResponseAPDU(byte[] bytes) {
		return new ResponseAPDU(bytes);
	}

}

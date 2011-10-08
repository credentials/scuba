package net.sourceforge.scuba.smartcards;

import javax.smartcardio.CommandAPDU;
import javax.smartcardio.ResponseAPDU;

public class SCFactory implements ISCFactory<CommandAPDU, ResponseAPDU> {

	@Override
	public ICommandAPDU createAccessorC(final CommandAPDU c) {
		return new ICommandAPDU() {
			@Override
			public int getCLA() { return c.getCLA(); }

			@Override
			public int getINS() { return c.getINS(); }

			@Override
			public int getP1() { return c.getP1(); }

			@Override
			public int getP2() { return c.getP2(); }

			@Override
			public int getNc() { return c.getNc(); }

			@Override
			public byte[] getData() { return c.getData(); }

			@Override
			public int getNe() { return c.getNe(); }

			@Override
			public byte[] getBytes() { return c.getBytes(); }
		};
	}

	@Override
	public IResponseAPDU createAccessorR(final ResponseAPDU r) {
		return new IResponseAPDU() {

			@Override
			public int getNr() { return r.getNr(); }

			@Override
			public byte[] getData() { return r.getData(); }

			@Override
			public int getSW1() { return r.getSW1(); }

			@Override
			public int getSW2() { return r.getSW2(); }

			@Override
			public int getSW() { return r.getSW(); }

			@Override
			public byte[] getBytes() { return r.getBytes(); }
		};
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

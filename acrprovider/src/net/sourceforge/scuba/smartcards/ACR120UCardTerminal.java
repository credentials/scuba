/*
 * ACR 120 USB driver for javax.smartcardio framework.
 * Copyright (C) 2008  Martijn Oostdijk
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

import java.nio.ByteBuffer;

import javax.smartcardio.ATR;
import javax.smartcardio.Card;
import javax.smartcardio.CardChannel;
import javax.smartcardio.CardException;
import javax.smartcardio.CardTerminal;
import javax.smartcardio.CommandAPDU;
import javax.smartcardio.ResponseAPDU;

import acs.jni.ACR120U;

/**
 * CardTerminal implementation for ACS ACR 120 USB contactless card reader.
 * This wraps the proprietary JNI code by ACS in order to use this reader
 * within the <code>javax.smartcardio</code> framework.
 * 
 * See <a href="http://www.acs.com.hk/download/">http://www.acs.com.hk/download/</a>.
 * (Tested with <code>ACR120U-JNI-1.0.0.3.zip</code>.)
 *
 * @author Martijn Oostdijk (martijn.oostdijk@gmail.com)
 * 
 * @version $Revision: $
 */
public class ACR120UCardTerminal extends CardTerminal
{
	public static final int
	ACR120_USB1 = ACR120U.ACR120_USB1,	  
	ACR120_USB2 = ACR120U.ACR120_USB2,
	ACR120_USB3 = ACR120U.ACR120_USB3,
	ACR120_USB4 = ACR120U.ACR120_USB4,
	ACR120_USB5 = ACR120U.ACR120_USB5,
	ACR120_USB6 = ACR120U.ACR120_USB6,
	ACR120_USB7 = ACR120U.ACR120_USB7,
	ACR120_USB8 = ACR120U.ACR120_USB8;

	private static final byte POWER_OFF = 0, POWER_ON = 1;
	
	private static final int
	ATS_OFFSET_TL = 0,
	ATS_OFFSET_T0 = 1,
	ATS_OFFSET_TA = 2,
	ATS_OFFSET_TB = 3,
	ATS_OFFSET_TC = 4;

	private static final long CARD_CHECK_SLEEP_TIME = 150;
	private static final long HEARTBEAT_TIMEOUT = 600;

	private long heartBeat;

	private ACR120U lib;
	private int hReader;
	private boolean isBasicChannelOpen;
	private final Object terminal;

	public ACR120UCardTerminal(int readerPort) throws CardException {
		terminal = this;
		synchronized(terminal) {
			switch (readerPort) {
			case ACR120_USB1:
			case ACR120_USB2:
			case ACR120_USB3:
			case ACR120_USB4:
			case ACR120_USB5:
			case ACR120_USB6:
			case ACR120_USB7:
			case ACR120_USB8: break;
			default: throw new IllegalArgumentException("Unsupported USB port");
			}
			hReader = -1;
			lib = new ACR120U();

			hReader = lib.open((short)readerPort) & 0xFFFF;
			short errorCode;
			errorCode = lib.power((short)hReader, POWER_OFF);
			if (errorCode < 0) { throw new CardException("Could not initialize reader, power off failed with error code " + errorCode); }
			errorCode = lib.power((short)hReader, POWER_ON);
			if (errorCode < 0) { throw new CardException("Could not initialize reader, power on failed with error code " + errorCode); }
			heartBeat = System.currentTimeMillis();
		}
	}

	public synchronized Card connect(String protocol) throws CardException {
		synchronized(terminal) {
			if (hReader < 0) {
				throw new CardException("Could not initialize reader");
			}

			if (!isCardPresent()) {
				throw new CardException("No card present");
			}
			return new ACR120UCard();
		}
	}

	public String getName() {
		return "ACR120U";
	}

	public boolean isCardPresent() throws CardException {
		if (lib == null || hReader < 0) { return false; }
		synchronized(terminal) {
			if (isBasicChannelOpen && (System.currentTimeMillis() - heartBeat) < HEARTBEAT_TIMEOUT) { return true; }
			short errorCode;
			byte[] pTagCount = new byte[1];
			byte[] pTagTypes = new byte[ACR120U.ACR120UJNI_MAX_NUM_TAGS];
			byte[] pTagLengths = new byte[ACR120U.ACR120UJNI_MAX_NUM_TAGS];
			byte[][] pSNs = new byte[ACR120U.ACR120UJNI_MAX_NUM_TAGS][10];
			errorCode = lib.listTags((short)hReader, pTagCount, pTagTypes, pTagLengths, pSNs);
			if (errorCode < 0) { throw new CardException("Could not initialize reader, listTags failed with error code " + errorCode); }
			int tagCount = pTagCount[0] & 0xFF;
			heartBeat = System.currentTimeMillis();
			return tagCount > 0;
		}
	}

	public boolean waitForCardAbsent(long timeout) throws CardException {
		/* TODO: test this method. */
		long startTime = System.currentTimeMillis();
		if (CARD_CHECK_SLEEP_TIME > timeout) { return !isCardPresent(); }
		try {
			while (isCardPresent()) {
				if (System.currentTimeMillis() - startTime > timeout) { break; }
				Thread.sleep(CARD_CHECK_SLEEP_TIME);
			}
		} catch (InterruptedException ie) {
			/* NOTE: Exit on interruption of thread. */
		}
		return !isCardPresent();
	}

	public boolean waitForCardPresent(long timeout) throws CardException {
		/* TODO: test this method. */
		long startTime = System.currentTimeMillis();
		if (CARD_CHECK_SLEEP_TIME > timeout) { return isCardPresent(); }
		try {
			while (!isCardPresent()) {
				if (System.currentTimeMillis() - startTime > timeout) { break; }
				Thread.sleep(CARD_CHECK_SLEEP_TIME);
			}
		} catch (InterruptedException ie) {
			/* NOTE: Exit on interruption of thread. */
		}
		return isCardPresent();
	}

	public void finalize() throws Throwable {
		try {
			if (lib != null && hReader >= 0) { lib.close((short)hReader); }
			hReader = -1;
			lib = null;
		} finally {
			super.finalize();
		}
	}

	/**
	 * Card merely wraps channel.
	 */
	private class ACR120UCard extends Card
	{
		private ACR120UCardChannel basicChannel;

		public ACR120UCard() throws CardException {			
			basicChannel = new ACR120UCardChannel(this);
		}

		public void beginExclusive() throws CardException {
		}

		public void disconnect(boolean reset) throws CardException {
			basicChannel.close();
		}

		public void endExclusive() throws CardException {
		}

		public ATR getATR() {
			return basicChannel.getATR();
		}

		public CardChannel getBasicChannel() {
			return basicChannel;
		}

		public String getProtocol() {
			return "T=1";
		}

		public CardChannel openLogicalChannel() throws CardException {
			return null;
		}

		public byte[] transmitControlCommand(int controlCode, byte[] command) throws CardException {
			return null;
		}		
	}

	/**
	 * Channel.
	 */
	private class ACR120UCardChannel extends CardChannel
	{
		private short[] transmitLengthBuffer;
		private short[] receiveLengthBuffer;
		private byte[] receiveBuffer;
		private ATR atr;
		private boolean isTypeA;

		private Card card;

		public ACR120UCardChannel(Card card) throws CardException {
			synchronized(terminal) {
				this.card = card;
				transmitLengthBuffer = new short[1];
				receiveLengthBuffer = new short[1];
				receiveBuffer = new byte[512];

				byte[] pResultTagType = { (byte)0xFF };
				byte[] pResultTagLength = { (byte)4 };
				byte[] pResultSN = new byte[10];
				short errorCode = lib.select((short)hReader, pResultTagType, pResultTagLength, pResultSN);
				if (errorCode < 0) { throw new CardException("Could not initialize reader, select failed with errorCode " + errorCode); }
				int tagType = pResultTagType[0] & 0xFF;
				isTypeA = ((tagType & 0x80) == 0x80);

				int fsd = 256; /* Our max frame length... */
				int fsc = 16; /* Their max frame length... */

				System.out.println("DEBUG: isTypeA = " + isTypeA);

				if (isTypeA) {
					byte[] atslen = new byte[1];
					byte[] ats = new byte[fsd];
					errorCode = lib.rATS((short)hReader, lookupFSDI(fsd), atslen, ats);
					if (errorCode < 0) { throw new CardException("Could not initialize reader, rATS failed with error code " + errorCode); }
					byte[] atrBytes = new byte[atslen[0] & 0xFF];
					System.arraycopy(ats, 0, atrBytes, 0, atrBytes.length);
					atr = new ATR(atrBytes);

					System.out.println("DEBUG: ATS = " + net.sourceforge.scuba.util.Hex.bytesToHexString(atrBytes));

					int tl = atrBytes[ATS_OFFSET_TL];
					if (tl > 1) {
						/* Check Y */
						int t0 = atrBytes[ATS_OFFSET_T0];
						if ((t0 & 0x40) == 0x40) {
							int tc = atrBytes[ATS_OFFSET_TC];
						}
						if ((t0 & 0x20) == 0x20) {
							int tb = atrBytes[ATS_OFFSET_TB];
						}
						if ((t0 & 0x10) == 0x10) {
							int ta = atrBytes[ATS_OFFSET_TA];
						}
						
						/* Get FSC */
						byte fsci = (byte)(t0 & 0x0F);
						fsc = lookupFSC(fsci);
						System.out.println("DEBUG: fsci = " + fsci + ", setting fsc to " + fsc);
					}
				}
				errorCode = lib.initBlockNumber((short)fsc);
				if (errorCode < 0) { throw new CardException("Could not initialize reader, initBlockNumber failed with error code " + errorCode); }

				isBasicChannelOpen = true;
				heartBeat = System.currentTimeMillis();
			}
		}

		/**
		 * Gets FSDI from FSD.
		 * See ISO 14443-4.
		 * 
		 * @param fsd the FSD
		 * @return the FSDI
		 */
		private byte lookupFSDI(int fsd) {
			switch(fsd) {
			case 16: return 0;
			case 24: return 1;
			case 32: return 2;
			case 40: return 3;
			case 48: return 4;
			case 64: return 5;
			case 96: return 6;
			case 128: return 7;
			case 256: return 8;
			default: throw new NumberFormatException("Illegal FSD");
			}
		}

		private int lookupFSC(byte fsci) {
			switch(fsci) {
			case 0: return 16;
			case 1: return 24;
			case 2: return 32;
			case 3: return 40;
			case 4: return 48;
			case 5: return 64;
			case 6: return 96;
			case 7: return 128;
			case 8: return 256;
			default: throw new NumberFormatException("Illegal FSCI");
			}
		}

		public ATR getATR() {
			return atr;
		}

		public void close() throws CardException {
			synchronized(terminal) {
				lib.deselect((short)hReader, isTypeA);
				isBasicChannelOpen = false;
			}
		}

		public Card getCard() {
			return card;
		}

		public int getChannelNumber() {
			return 0;
		}

		public ResponseAPDU transmit(CommandAPDU apdu) throws CardException {
			synchronized(terminal) {
				if (lib == null || hReader < 0) { throw new CardException("Reader not ready"); }
				short errorCode;
				byte[] data = apdu.getBytes();
				transmitLengthBuffer[0] = (short)data.length;
				errorCode = lib.xchAPDU((short)hReader, isTypeA, transmitLengthBuffer, data, receiveLengthBuffer, receiveBuffer);
				if (errorCode < 0) { throw new CardException("Error exchanging APDU, xchAPDU failed with error code " + errorCode); }
				byte[] result = new byte[receiveLengthBuffer[0] & 0xFFFF];
				System.arraycopy(receiveBuffer, 0, result, 0, result.length);
				heartBeat = System.currentTimeMillis();
				return new ResponseAPDU(result);
			}
		}

		public int transmit(ByteBuffer command, ByteBuffer response) throws CardException {
			synchronized(terminal) {
				ResponseAPDU rapdu = transmit(new CommandAPDU(command));
				byte[] rapduBytes = rapdu.getBytes();
				response.put(rapduBytes);
				return rapduBytes.length;
			}
		}		
	}
}

/*
 * $Id: $
 */

package net.sourceforge.scuba.simservice;

import java.util.Arrays;

import javax.smartcardio.CommandAPDU;
import javax.smartcardio.ResponseAPDU;

import net.sourceforge.scuba.smartcards.CardService;
import net.sourceforge.scuba.smartcards.CardServiceException;

/**
 * A low level <code>CardService</code> for GSM SIM cards.
 *
 * @version $Revision: 1.2 $
 *
 * @author Martijn Oostdijk (martijn.oostdijk@gmail.com)
 */
public class SIMAPDUService extends CardService
{
	/** PIN selector values. */
	public static final byte CHV1 = (byte) 0x01, CHV2 = (byte) 0x02;

	/** The underlying service used to send APDUs to the card. */
	private CardService service;

	/**
	 * Constructs a new <code>SIMService</code>.
	 * 
	 * @param service
	 *            the underlying service used to send APDUs to the card.
	 */
	public SIMAPDUService(CardService service) {
		this.service = service;
	}

	public void open() throws CardServiceException {
		service.open();
	}

	public boolean isOpen() {
		return service.isOpen();
	}

	public void close() {
		service.close();
	}

	/**
	 * Sends the command APDU <code>capdu</code> to the card.
	 * 
	 * @param capdu
	 *            The command APDU to send.
	 * 
	 * @return The response APDU that was received.
	 * 
	 * @throws CardServiceException
	 *             If something goes wrong.
	 */
	public ResponseAPDU transmit(CommandAPDU capdu) throws CardServiceException {
		ResponseAPDU rapdu = service.transmit(capdu);
		int sw = rapdu.getSW();
		while ((sw & 0x9E00) == 0x9E00 || (sw & 0x9F00) == 0x9F00) {
			int le = sw & 0xFF;
			capdu = new CommandAPDU(GSM1111.CLA_GSM, GSM1111.INS_GET_RESPONSE, 0x00, 0x00, le);
			rapdu = service.transmit(capdu);
			sw = rapdu.getSW();
		}
		return rapdu;
	}

	/**
	 * Sends a <code>SELECT</code> instruction to the card. Described in 9.2.1
	 * of ETSI TS 100 977.
	 * 
	 * @throws CardServiceException
	 *             if something goes wrong.
	 */
	public ResponseAPDU sendSelect(short fid) throws CardServiceException {
		byte[] data = { (byte) ((fid >> 8) & 0x000000FF),
				(byte) (fid & 0x000000FF) };
		CommandAPDU capdu = new CommandAPDU(GSM1111.CLA_GSM, GSM1111.INS_SELECT, (byte) 0x00,
				(byte) 0x00, data, 0);
		return transmit(capdu);
	}

	/**
	 * Described in 9.2.2 of ETSI TS 100 977.
	 * 
	 * @throws CardServiceException
	 *             if something goes wrong.
	 */
	public ResponseAPDU sendStatus() throws CardServiceException {
		CommandAPDU capdu = new CommandAPDU(GSM1111.CLA_GSM, GSM1111.INS_STATUS, (byte) 0x00,
				(byte) 0x00, 0);
		return transmit(capdu);
	}

	/**
	 * Described in 9.2.3 of ETSI TS 100 977.
	 * 
	 * @throws CardServiceException
	 *             if something goes wrong.
	 */
	public ResponseAPDU sendReadBinary(short offset, byte length) throws CardServiceException {
		CommandAPDU capdu = new CommandAPDU(GSM1111.CLA_GSM, GSM1111.INS_READ_BINARY,
				(byte) ((offset >> 8) & 0x000000FF),
				(byte) (offset & 0x000000FF), (int) (length & 0x000000FF));
		return transmit(capdu);
	}

	/**
	 * Described in 9.2.4 of ETSI TS 100 977.
	 * 
	 * @throws CardServiceException
	 *             if something goes wrong.
	 */
	public ResponseAPDU sendUpdateBinary() throws CardServiceException {
		return null; // TODO
	}

	/**
	 * Described in 9.2.5 of ETSI TS 100 977.
	 * 
	 * @throws CardServiceException
	 *             if something goes wrong.
	 */
	public ResponseAPDU sendReadRecord(byte recordNumber, byte mode, byte length) throws CardServiceException {
		CommandAPDU capdu = new CommandAPDU(GSM1111.CLA_GSM, GSM1111.INS_READ_RECORD,
				recordNumber, mode, (int) (length & 0x000000FF));
		return transmit(capdu);
	}

	/**
	 * Described in 9.2.6 of ETSI TS 100 977.
	 * 
	 * @throws CardServiceException
	 *             if something goes wrong.
	 */
	public ResponseAPDU sendUpdateRecord() throws CardServiceException {
		return null;
	}

	/**
	 * Described in 9.2.7 of ETSI TS 100 977.
	 * 
	 * @throws CardServiceException
	 *             if something goes wrong.
	 */
	public ResponseAPDU sendSeek() throws CardServiceException {
		return null; // TODO
	}

	/**
	 * Described in 9.2.8 of ETSI TS 100 977.
	 * 
	 * @throws CardServiceException
	 *             if something goes wrong.
	 */
	public ResponseAPDU sendIncrease() throws CardServiceException {
		return null; // TODO
	}

	/**
	 * Sends a VERIFY_CHV instruction. Described in 9.2.9 of ETSI TS 100 977. If
	 * <code>chv</code> is less than 8 bytes long, it is padded with
	 * <code>0xFF</code>s.
	 * 
	 * @param id
	 *            either CHV1 or CHV2.
	 * @param chv
	 *            the 8-byte CHV.
	 * 
	 * @throws CardServiceException
	 *             if <code>chv</code> is longer than 8 bytes.
	 * @throws CardServiceException
	 *             if something goes wrong.
	 */
	public ResponseAPDU sendVerifyCHV(byte id, byte[] chv) throws CardServiceException {
		if (chv == null || chv.length > 8) {
			throw new CardServiceException("Invalid CHV length");
		}
		byte[] data = new byte[8];
		Arrays.fill(data, 0, 8, (byte) 0xFF);
		System.arraycopy(chv, 0, data, 0, chv.length);
		CommandAPDU capdu = new CommandAPDU(GSM1111.CLA_GSM, GSM1111.INS_VERIFY_CHV,
				(byte) 0x00, id, data);
		return transmit(capdu);
	}

	/**
	 * Described in 9.2.10 of ETSI TS 100 977.
	 * 
	 * @throws CardServiceException
	 *             if something goes wrong.
	 */
	public ResponseAPDU sendChangeCHV(byte id) throws CardServiceException {
		return null; // TODO
	}

	/**
	 * Described in 9.2.11 of ETSI TS 100 977.
	 * 
	 * @throws CardServiceException
	 *             if something goes wrong.
	 */
	public ResponseAPDU sendDisableCHV(byte[] chv) throws CardServiceException {
		return null; // TODO
	}

	/**
	 * Described in 9.2.12 of ETSI TS 100 977.
	 * 
	 * @throws CardServiceException
	 *             if something goes wrong.
	 */
	public ResponseAPDU sendEnableCHV(byte[] chv) throws CardServiceException {
		return null; // TODO
	}

	/**
	 * Described in 9.2.13 of ETSI TS 100 977.
	 * 
	 * @throws CardServiceException
	 *             if something goes wrong.
	 */
	public ResponseAPDU sendUnblockCHV(byte id) throws CardServiceException {
		return null; // TODO
	}

	/**
	 * Described in 9.2.14 of ETSI TS 100 977.
	 * 
	 * @throws CardServiceException
	 *             if something goes wrong.
	 */
	public ResponseAPDU sendInvalidate() throws CardServiceException {
		return null; // TODO
	}

	/**
	 * Described in 9.2.15 of ETSI TS 100 977.
	 * 
	 * @throws CardServiceException
	 *             if something goes wrong.
	 */
	public ResponseAPDU sendRehabilitate() throws CardServiceException {
		return null; // TODO
	}

	/**
	 * Described in 9.2.16 of ETSI TS 100 977.
	 * 
	 * @throws CardServiceException
	 *             if something goes wrong.
	 */
	public ResponseAPDU sendRunGSMAlgorithm() throws CardServiceException {
		return null; // TODO
	}

	/**
	 * Described in 9.2.17 of ETSI TS 100 977.
	 * 
	 * @throws CardServiceException
	 *             if something goes wrong.
	 */
	public ResponseAPDU sendSleep() throws CardServiceException {
		return null; // TODO
	}

	/**
	 * Described in 9.2.18 of ETSI TS 100 977.
	 * 
	 * @throws CardServiceException
	 *             if something goes wrong.
	 */
	public ResponseAPDU sendGetResponse(int length) throws CardServiceException {
		CommandAPDU capdu = new CommandAPDU(GSM1111.CLA_GSM, GSM1111.INS_GET_RESPONSE, 0x00, 0x00, length);
		return transmit(capdu);
	}

	/**
	 * Described in 9.2.19 of ETSI TS 100 977.
	 * 
	 * @throws CardServiceException
	 *             if something goes wrong.
	 */
	public ResponseAPDU sendTerminalProfile() throws CardServiceException {
		return null; // TODO
	}

	/**
	 * Described in 9.2.20 of ETSI TS 100 977.
	 * 
	 * @throws CardServiceException
	 *             if something goes wrong.
	 */
	public ResponseAPDU sendEnvelope(byte[] data) throws CardServiceException {
		CommandAPDU capdu = new CommandAPDU(GSM1111.CLA_GSM, GSM1111.INS_ENVELOPE, 0x00, 0x00, data);
		return transmit(capdu);
	}

	/**
	 * Described in 9.2.21 of ETSI TS 100 977.
	 * 
	 * @throws CardServiceException
	 *             if something goes wrong.
	 */
	public ResponseAPDU sendFetch(int length) throws CardServiceException {
		CommandAPDU capdu = new CommandAPDU(GSM1111.CLA_GSM, GSM1111.INS_FETCH, 0x00, 0x00, length);
		return transmit(capdu);
	}

	/**
	 * Described in 9.2.22 of ETSI TS 100 977.
	 * 
	 * @throws CardServiceException
	 *             if something goes wrong.
	 */
	public ResponseAPDU sendTerminalResponse(byte[] data) throws CardServiceException {
		CommandAPDU capdu = new CommandAPDU(GSM1111.CLA_GSM, GSM1111.INS_TERMINAL_RESPONSE, 0x00, 0x00, data);
		return transmit(capdu);
	}

	/**
	 * Produces a textual representation of this object.
	 */
	public String toString() {
		return "SIMService (based on: " + service + ")";
	}
}

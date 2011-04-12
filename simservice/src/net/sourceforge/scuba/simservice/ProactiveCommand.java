package net.sourceforge.scuba.simservice;

import java.util.*;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

import net.sourceforge.scuba.tlv.BERTLVInputStream;

public abstract class ProactiveCommand {

	public static final int
	SIM_TO_ME_PROACTIVE_COMMAND_TAG = 0xD0,
	ME_TO_SIM_SMS_PP_DOWNLOAD_TAG = 0xD1,
	ME_TO_SIM_CELL_BROADCAST_DOWNLOAD_TAG = 0xD2,
	ME_TO_SIM_MENU_SELECTION_TAG = 0xD3,
	ME_TO_SIM_CALL_CONTROL_TAG = 0xD4;

	public static final int
	DEVICE_ID_TAG = 0x02,
	RESULT_TAG = 0x03,
	ITEM_IDENTIFIER_TAG = 0x10,
	DATE_TAG = 0x26,
	COMMAND_DETAILS_TAG = 0x81;

	public static final int 
	COMMAND_ID_REFRESH = 0x01,
	COMMAND_ID_MORE_TIME = 0x02,
	COMMAND_ID_POLL_INTERVAL = 0x03,
	COMMAND_ID_POLLING_OFF = 0x04,
	COMMAND_ID_SET_UP_CALL = 0x10,
	COMMAND_ID_SEND_SS = 0x11,
	COMMAND_ID_SEND_USSD = 0x12,
	COMMAND_ID_SEND_SHORT_MESSAGE = 0x13,
	COMMAND_ID_PLAY_TONE = 0x20,
	COMMAND_ID_DISPLAY_TEXT = 0x21,
	COMMAND_ID_GET_INKEY = 0x22,
	COMMAND_ID_GET_INPUT = 0x23,
	COMMAND_ID_SELECT_ITEM = 0x24,
	COMMAND_ID_SET_UP_MENU = 0x25,
	COMMAND_ID_PROVIDE_LOCAL_INFORMATION = 0x26;

	public static final int
	KEYPAD_DEVICE_ID = 0x01,
	DISPLAY_DEVICE_ID = 0x02,
	EARPIECE_DEVICE_ID = 0x03,
	SIM_DEVICE_ID = 0x81,
	ME_DEVICE_ID = 0x82,
	NETWORK_DEVICE_ID = 0x83;

	protected int srcDeviceId, destDeviceId;

	protected CommandDetails commandDetails;

	public static ProactiveCommand getInstance(InputStream in) {
		try {
			int commandId = -1;
			List<SimpleTLVObject> simpleTLVs = new ArrayList<SimpleTLVObject>();
			BERTLVInputStream tlvIn = new BERTLVInputStream(in);
			int commandTag = tlvIn.readTag();
			if (commandTag != 0xD0) {
				throw new IllegalArgumentException("Expected tag 0xD0, found " + Integer.toHexString(commandTag));
			}
			int commandLength = tlvIn.readLength();
			int bytesRead = 0;
			while (bytesRead < commandLength) {
				try {
					int tag = tlvIn.readTag(); bytesRead++;
					int length = tlvIn.readLength(); bytesRead++;
					byte[] value = tlvIn.readValue(); bytesRead += length;
					if (tag == COMMAND_DETAILS_TAG && value.length > 1) {
						commandId = value[1];
					}
					simpleTLVs.add(new SimpleTLVObject(tag, value));
				} catch (EOFException eofe) {
					eofe.printStackTrace();
					System.out.println("DEBUG: eof bytesRead = " + bytesRead + " commandLength = " + commandLength);
				}
			}
			switch (commandId) {
			case COMMAND_ID_REFRESH: throw new IllegalArgumentException("Not yet supported");
			case COMMAND_ID_MORE_TIME: throw new IllegalArgumentException("Not yet supported"); 
			case COMMAND_ID_POLL_INTERVAL: throw new IllegalArgumentException("Not yet supported");
			case COMMAND_ID_POLLING_OFF: throw new IllegalArgumentException("Not yet supported");
			case COMMAND_ID_SET_UP_CALL: throw new IllegalArgumentException("Not yet supported");
			case COMMAND_ID_SEND_SS: throw new IllegalArgumentException("Not yet supported");
			case COMMAND_ID_SEND_USSD: throw new IllegalArgumentException("Not yet supported");
			case COMMAND_ID_SEND_SHORT_MESSAGE: throw new IllegalArgumentException("Not yet supported");
			case COMMAND_ID_PLAY_TONE: throw new IllegalArgumentException("Not yet supported");
			case COMMAND_ID_DISPLAY_TEXT: return new DisplayTextProactiveCommand(simpleTLVs);
			case COMMAND_ID_GET_INKEY: throw new IllegalArgumentException("Not yet supported");
			case COMMAND_ID_GET_INPUT: throw new IllegalArgumentException("Not yet supported");
			case COMMAND_ID_SELECT_ITEM: return new SelectItemProactiveCommand(simpleTLVs);
			case COMMAND_ID_SET_UP_MENU: throw new IllegalArgumentException("Not yet supported");
			case COMMAND_ID_PROVIDE_LOCAL_INFORMATION: return new ProvideLocalInformationProactiveCommand(simpleTLVs);
			default: throw new IllegalArgumentException("Unsupported command Id: " + Integer.toHexString(commandId));
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
			throw new IllegalArgumentException(ioe.getMessage()); /* TODO: replace with proper exception type */
		}
	}

	public abstract byte[] getEncoded();

	public CommandDetails getCommandDetails() {
		return commandDetails;
	}

	/**
	 * @return the srcDeviceId
	 */
	public int getSrcDeviceId() {
		return srcDeviceId;
	}

	/**
	 * @return the destDeviceId
	 */
	public int getDestDeviceId() {
		return destDeviceId;
	}

}

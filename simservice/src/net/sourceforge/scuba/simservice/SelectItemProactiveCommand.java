package net.sourceforge.scuba.simservice;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Used in FETCH response APDU (SIM to ME traffic).
 */
public class SelectItemProactiveCommand extends ProactiveCommand {

	private String alphaIdentifier;
	private Map<Integer, String> itemTexts;
	private int itemIdentifier;

	/**
	 * @param srcDeviceId
	 * @param destDeviceId
	 * @param alphaIdentifier
	 * @param itemTexts
	 */
	public SelectItemProactiveCommand(int srcDeviceId, int destDeviceId, String alphaIdentifier, Map<Integer, String> itemTexts) {
		this.srcDeviceId = srcDeviceId;
		this.destDeviceId = destDeviceId;
		this.alphaIdentifier = alphaIdentifier;
		this.itemIdentifier = 0;
		this.itemTexts = itemTexts;
	}

	public SelectItemProactiveCommand(int srcDeviceId, int destDeviceId, int resultCode, int itemIdentifier) {
		this.srcDeviceId = srcDeviceId;
		this.destDeviceId = destDeviceId;
		this.alphaIdentifier = null;
		this.itemIdentifier = itemIdentifier;
		this.itemTexts = null;
	}
	
	public SelectItemProactiveCommand(List<SimpleTLVObject> simpleTLVs) {
		itemTexts = new HashMap<Integer, String>();
		for (SimpleTLVObject entry: simpleTLVs) {
			int tag = entry.getTag();
			byte[] value = entry.getValue();
			switch (tag) {
			case 0x81:
				/* TODO: 01 24 00, check that byte 2 is 0x24 */;
				break;
			case 0x82:
				/* TODO: check length = 2 and deviceId's in { 1, 2, 3, 81, 82, 83 } */
				srcDeviceId = value[0];
				destDeviceId = value[1];
				break;
			case 0x05:
				/* TODO: expect string "DIGIPASS" */
				try {
					alphaIdentifier = new String(value, "UTF-8");
				} catch (UnsupportedEncodingException usee) {
					usee.printStackTrace();
					/* NOTE: "UTF-8" is supported, will never be thrown. */
				}
				break;
			case 0x8F: /* l=15 01 "Authentication" */
				try {
					int index = value[0];
					String itemText = new String(value, 1, value.length - 1, "UTF-8");
					itemTexts.put(index, itemText);
				} catch (UnsupportedEncodingException usee) {
					usee.printStackTrace();
					/* NOTE: "UTF-8" is supported, will never be thrown. */
				}
				break;
			default:
				throw new IllegalArgumentException("Unknown tag " + Integer.toHexString(tag));
			}
		}
	}

	/**
	 * @return the alphaIdentifier
	 */
	public String getAlphaIdentifier() {
		return alphaIdentifier;
	}

	/**
	 * @return the itemTexts
	 */
	public Map<Integer, String> getItemTexts() {
		return itemTexts;
	}

	/**
	 * Encoding to transmit this command from SIM to ME (in FETCH response APDU)
	 */
	public byte[] getEncoded() {
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			if (destDeviceId == SIM_DEVICE_ID) {
				/* This command is payload TERMINAL RESPONSE command APDU... */
				byte[] data = new byte[] {
						(byte)0x81, 3, 0x01, 0x24, 0x00,
						DEVICE_ID_TAG, 2, (byte)srcDeviceId, (byte)destDeviceId,
						RESULT_TAG, 1, 0x00,
						ITEM_IDENTIFIER_TAG, 1, (byte)itemIdentifier };
				out.write(data);
			} else if (srcDeviceId == SIM_DEVICE_ID) {
				/* This command is payload of FETCH response APDU...  */
				ByteArrayOutputStream dataOut = new ByteArrayOutputStream();
				dataOut.write(new byte[] {
						(byte)0x81, 3, 0x01, 0x24, 0x00,
						DEVICE_ID_TAG, 2, (byte)srcDeviceId, (byte)destDeviceId
				});
				
				dataOut.write(0x05);
				dataOut.write((byte)alphaIdentifier.length());
				dataOut.write(alphaIdentifier.getBytes("UTF-8"));

				for (Entry<Integer, String> item: itemTexts.entrySet()) {
					int index = item.getKey();
					String text = item.getValue();
					dataOut.write((byte)0x8F);
					dataOut.write(1 + text.length());
					dataOut.write((byte)index);
					dataOut.write(text.getBytes("UTF-8"));
				}

				out.write((byte)0xD0);
				byte[] data = dataOut.toByteArray();
				out.write((byte)data.length);
				out.write(data);
			}
			return out.toByteArray();
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return null;
		}
	}
}

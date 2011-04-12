package net.sourceforge.scuba.simservice;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class DisplayTextProactiveCommand extends ProactiveCommand {

	private String text;

	public DisplayTextProactiveCommand(int src, int dst, int result) {
		this.srcDeviceId = src;
		this.destDeviceId = dst;
		commandDetails = new CommandDetails(new byte[] { 0x01, 0x21, (byte)0x80 });
	}

	public DisplayTextProactiveCommand(List<SimpleTLVObject> simpleTLVs) {
		for (SimpleTLVObject entry: simpleTLVs) {
			int tag = entry.getTag();
			byte[] value = entry.getValue();
			switch (tag) {
			case 0x0D:
				try {
					/* int dataCodingScheme = value[0] & 0xFF; // always 0x04? */
					text = new String(value, 1, value.length - 1, "UTF-8");
				} catch (UnsupportedEncodingException usee) {
					usee.printStackTrace(); /* FIXME proper exception handling */
					text = null;
				}
				break;
			case COMMAND_DETAILS_TAG: /* Command details */
				/* FIXME: check length value, should be 3 */
				/* FIXME: check value[1], should be 0x26 */
				commandDetails = new CommandDetails(value);
				break;
			case DEVICE_ID_TAG:
			case 0x82:
				/* FIXME: check length value, should be 2 */
				srcDeviceId = value[0];
				destDeviceId = value[1];
				break;
			default:
				throw new IllegalArgumentException("Unknown tag " + Integer.toHexString(tag));
			}
		}
	}
	
	public String getText() {
		return text;
	}

	public byte[] getEncoded() {
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();

			out.write((byte)COMMAND_DETAILS_TAG);
			byte[] commandDetailsBytes = commandDetails.getEncoded();
			out.write(commandDetailsBytes.length);
			out.write(commandDetailsBytes);

			out.write((byte)DEVICE_ID_TAG);
			byte[] deviceBytes = new byte[] { (byte)srcDeviceId, (byte)destDeviceId };
			out.write(deviceBytes.length);
			out.write(deviceBytes);

			out.write((byte)RESULT_TAG);
			byte[] resultBytes = new byte[] { 0 };
			out.write(resultBytes.length);		
			out.write(resultBytes);

			return out.toByteArray();
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return null;
		}
	}
}

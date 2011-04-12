package net.sourceforge.scuba.simservice;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

public class ProvideLocalInformationProactiveCommand extends ProactiveCommand {

	private long date;
	
	public ProvideLocalInformationProactiveCommand(int srcDeviceId, int destDeviceId, long date) {
		this.srcDeviceId = srcDeviceId;
		this.destDeviceId = destDeviceId;
		this.date = date;
		commandDetails = new CommandDetails(new byte[]{ 0x01, DATE_TAG, RESULT_TAG });
	}
	
	public ProvideLocalInformationProactiveCommand(List<SimpleTLVObject> simpleTLVs) {
		for (SimpleTLVObject entry: simpleTLVs) {
			int tag = entry.getTag();
			byte[] value = entry.getValue();
			switch (tag) {
			case RESULT_TAG: /* Result */
				break;
			case DATE_TAG: /* Date */
				date = (new BigInteger(value)).longValue();
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
				throw new IllegalArgumentException("");
			}
		}
	}
	
	public long getDate() {
		return date;
	}

	public byte[] getEncoded() {
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			if (destDeviceId == SIM_DEVICE_ID) {
				/* This command is payload TERMINAL RESPONSE command APDU... */
				byte[] data = new byte[] {
						(byte)COMMAND_DETAILS_TAG, 3, 0x01, 0x26, 0x03,
						(byte)DEVICE_ID_TAG, 2, (byte)srcDeviceId, (byte)destDeviceId,
						(byte)RESULT_TAG, 1, 0x00 };
				out.write(data);
				byte[] dateBytes = BigInteger.valueOf(date).toByteArray();
				out.write((byte)DATE_TAG);
				out.write(dateBytes.length);
				out.write(dateBytes);
			} else if (srcDeviceId == SIM_DEVICE_ID) {
				/* This command is payload of FETCH response APDU...  */
				ByteArrayOutputStream dataOut = new ByteArrayOutputStream();
				dataOut.write(new byte[] {
					(byte)COMMAND_DETAILS_TAG, 3, 0x01, 0x26, 0x03,
					(byte)DEVICE_ID_TAG, 2, (byte)srcDeviceId, (byte)destDeviceId
				});
				
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

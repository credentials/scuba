package net.sourceforge.scuba.simservice;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class SelectMenuProactiveCommand extends ProactiveCommand {

	private int itemId;

	public SelectMenuProactiveCommand(int srcDeviceId, int destDeviceId, int itemId) {
		this.srcDeviceId = srcDeviceId;
		this.destDeviceId = destDeviceId;
		this.itemId = itemId;
	}

	public int getItemId() {
		return itemId;
	}

	public byte[] getEncoded() {
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			byte[] data = new byte[]{
					(byte)DEVICE_ID_TAG, 2, (byte)srcDeviceId, (byte)destDeviceId,
					(byte)ITEM_IDENTIFIER_TAG, 1, (byte)itemId };
			out.write((byte)0xD3);
			out.write(data.length);
			out.write(data);
			return out.toByteArray();
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return null;
		}
	}
}

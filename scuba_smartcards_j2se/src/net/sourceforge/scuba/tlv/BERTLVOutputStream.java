package net.sourceforge.scuba.tlv;

import java.io.IOException;
import java.io.*;

public class BERTLVOutputStream extends OutputStream {

	private DataOutputStream out;
	
	public BERTLVOutputStream(OutputStream out) {
		this.out = out instanceof DataOutputStream ? (DataOutputStream)out : new DataOutputStream(out);
	}
	
	public void writeTag(int tag) throws IOException {
		out.write(TLVUtil.getTagAsBytes(tag));
	}
	
	public void write(int b) throws IOException {
		out.write(b);
	}

}

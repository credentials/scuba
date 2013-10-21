package net.sourceforge.scuba.simservice;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class CommandDetails {

    List<Integer> commandDetails;
    
    private CommandDetails() {
        this.commandDetails = new ArrayList<Integer>();
    }
    
    public CommandDetails(byte[] value) {
        this();
        for (byte b: value) {
            commandDetails.add(b & 0xFF);
        }
    }
    
    /** Without the tag. */
    public byte[] getEncoded() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();        
        for (int tag: commandDetails) {
            out.write((byte)tag);
        }
        return out.toByteArray();
    }
    
    public String toString() {
        return commandDetails.toString();
    }
}

package net.sourceforge.scuba.simservice;

public class SimpleTLVObject {
    
    private int tag;
    private byte[] value;
    
    public SimpleTLVObject(int tag, byte[] value) {
        this.tag = tag;
        if (value == null) { this.value = null; return; }
        this.value = new byte[value.length];
        System.arraycopy(value, 0, this.value, 0, value.length);
    }
    
    public int getTag() {
        return tag;
    }
    
    public byte[] getValue() {
        return value;
    }
}

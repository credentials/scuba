package scuba.smartcards;

public interface ISCFactory<C,R> 
{
	public ICommandAPDU createAccessorC(C c);
	public IResponseAPDU createAccessorR(R c);
	
	public C createCommandAPDU( int cla, int ins, int p1, int p2, byte[] data, int ne );
	public C createCommandAPDU(int cla, int ins, int p1, int p2, byte[] data);
	public C createCommandAPDU (int cla, int ins, int p1, int p2, int ne);
	
	
	public R createResponseAPDU(byte[] bytes);
	
}

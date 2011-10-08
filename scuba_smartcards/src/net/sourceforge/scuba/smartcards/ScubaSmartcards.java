package net.sourceforge.scuba.smartcards;

public class ScubaSmartcards<C,R> 
{
	private static ScubaSmartcards<?,?> instance;

	private ISCFactory<C,R> factory;

	public void init( ISCFactory<C,R> fac ) {
		this.factory = fac;
	}

	@SuppressWarnings("unchecked")
	public static <C,R> ScubaSmartcards<C,R> getInstance() {
		if( instance == null )
			instance = new ScubaSmartcards<C,R>();
		return (ScubaSmartcards<C,R>)instance;
	}

	public C createCommandAPDU( int cla, int ins, int p1, int p2, byte[] data, int ne )
	{
		return factory.createCommandAPDU(cla, ins, p1, p2, data, ne);
	}
	public C createCommandAPDU(int cla, int ins, int p1, int p2, byte[] data)
	{
		return factory.createCommandAPDU(cla, ins, p1, p2, data);
	}
	public C createCommandAPDU (int cla, int ins, int p1, int p2, int ne)
	{
		return factory.createCommandAPDU(cla, ins, p1, p2, ne);
	}


	public ICommandAPDU accesC(C c) {
		return factory.createAccessorC(c);
	}

	public IResponseAPDU accesR(R r) {
		return factory.createAccessorR(r);
	}

	public R createResponseAPDU(byte[] bytes) {
		return factory.createResponseAPDU(bytes);
	}
}
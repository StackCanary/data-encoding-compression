package zip.no.longer.needed;

public class Utility {

	static int constructInteger(int i, int j, int k, int l)
	{
		return (i << 24) + (j << 16) + (k << 8) + l;
	}
	
	static short constructShort(int a, int b)
	{
		return (short) ((a << 8) + b);
	}
	
	public static void main(String...strings)
	{
		
		System.out.format("%x", constructShort(255, 5));
		
		
	}
}

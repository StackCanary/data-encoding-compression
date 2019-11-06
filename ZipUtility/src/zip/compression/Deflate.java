package zip.compression;

import java.util.Arrays;
import java.util.zip.Deflater;

public class Deflate extends Compression {

	public Deflate()
	{
		
	}

	@Override
	public byte[] encode(byte[] input) {
		int size = input.length * 2;
		
		byte[] output = new byte[size];

		Deflater deflator = new Deflater(Deflater.HUFFMAN_ONLY);
		deflator.setInput(input);
		int rSize = deflator.deflate(output);

		deflator.finish();
		
		return Arrays.copyOf(output, rSize);
	}
	
}

package zip.archive;

import zip.compression.Compression;


public class Options {

	public enum CompressionType {
		Uncompressed((short) 0x08, null),
		Deflate((short) 0x08, Compression.class);
		
		final short value;
		final Class compression;
		CompressionType(short value, Class compression)
		{
			this.value = value;
			this.compression = compression;
		}
		
		public short getValue()
		{
			return this.value;
		}
		
		public Class getCompression()
		{
			return compression;
		}
	};
	
}

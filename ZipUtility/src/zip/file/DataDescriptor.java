package zip.file;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/*
      This descriptor exists only if bit 3 of the general
      purpose bit flag is set (see below).  It is byte aligned
      and immediately follows the last byte of compressed data.
      This descriptor is used only when it was not possible to
      seek in the output .ZIP file, e.g., when the output .ZIP file
      was standard output or a non seekable device.  For Zip64 format
      archives, the compressed and uncompressed sizes are 8 bytes each.
      
      (This comment above is copied from 
      	https://www.loc.gov/preservation/digital/formats/digformatspecs/APPNOTE%2820120901%29_Version_6.3.3.txt
      )
 */
public class DataDescriptor {
	int crc32;
	int compressedSize;
	int uncompressedSize;
	
	/*
	 * This can be set to 0 since bit 3 is not set in the general purpose flag
	 */
	public DataDescriptor(boolean bit3IsSet) {
		
		if (bit3IsSet)
			throw new RuntimeException("Unimplemented");
		
		this.crc32 = 0;
		this.compressedSize = 0;
		this.uncompressedSize = 0;
	}
	
	public ByteBuffer getRawBytes()
	{
		ByteBuffer b = ByteBuffer.allocate(getSize());
		b.order(ByteOrder.LITTLE_ENDIAN);
		
		b.putInt(crc32);
		b.putInt(compressedSize);
		b.putInt(uncompressedSize);

		b.flip();
		return b;
	}
	
	public int getSize()
	{
		return 4 * 3;
	}
	
	
}

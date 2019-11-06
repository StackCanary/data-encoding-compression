package zip.file;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.zip.CRC32;
import java.util.zip.Deflater;

import zip.compression.Compression;
import zip.compression.Deflate;
import zip.utility.FileUtility;

public class FileData {

	byte fileData[];
	CRC32 crc32 = new CRC32();
	
	public FileData(byte fileData[])
	{
		this.fileData = fileData.clone();
	}
	
	public ByteBuffer getRawBytes()
	{
		ByteBuffer b = ByteBuffer.allocate(getSize());
		b.order(ByteOrder.LITTLE_ENDIAN);
		
		b.put(fileData);
		
		b.flip();
		return b;
	}
	
	public int getSize()
	{
		return fileData.length;
	}
	
	public int getCompressedFileSize()
	{
		return fileData.length;
	}
	
	public int getUnCompressedFileSize()
	{
		return fileData.length;
	}

	public int getcrc32() {
		crc32.reset();
		crc32.update(fileData);
		
		return (int) crc32.getValue();
	}
	
	
}

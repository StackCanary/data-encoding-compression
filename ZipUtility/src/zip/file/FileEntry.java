package zip.file;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import zip.utility.FileUtility;

public class FileEntry {
	
	public LocalFileHeader lfHeader;
	public FileData fData;
//	public DataDescriptor descriptor;
	
	public FileEntry(String filename, byte contents[])
	{
		this.fData = new FileData(contents);
		lfHeader = new LocalFileHeader(filename, this.fData);
	//	descriptor = new DataDescriptor(false);
	}
	
	public int getSize()
	{	
		return lfHeader.getSize() + fData.getSize();
	}
	
	public ByteBuffer getRawBytes()
	{
		ByteBuffer b = ByteBuffer.allocate(getSize());
		b.order(ByteOrder.LITTLE_ENDIAN);
		
		b.put(lfHeader.getRawBytes()).put(fData.getRawBytes());
		/*.put(descriptor.getRawBytes()); */
		
		b.flip();
		return b;
	}
	
	
	public LocalFileHeader getLocalFileHeader() {
		return lfHeader;
	}

	public FileData getFileData() {
		return fData;
	}

//	public DataDescriptor getDataDescriptor() {
//		return descriptor;
//	}

	public static FileEntry CreateFileEntry(File file) throws IOException
	{
		return new FileEntry(file.getName(), FileUtility.getContents(file));
	}
	
}

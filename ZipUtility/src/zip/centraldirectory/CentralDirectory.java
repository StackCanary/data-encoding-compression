package zip.centraldirectory;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;

import zip.archive.Archive;
import zip.file.FileEntry;

public class CentralDirectory {

	ArrayList<FileHeader> fHeaders = new ArrayList<FileHeader>();
	// DigitalSignature signature; // This looks like it isn't needed
	EndOfCentralDirectoryRecord endOfCDRecord;
	
	public CentralDirectory(Archive archive)
	{
		
		int offset = 0;
		for (FileEntry entry : archive.getFilesEntries()) {
			fHeaders.add(new FileHeader(entry.getLocalFileHeader(), offset));
			offset += entry.getSize();
		}
		
		endOfCDRecord = new EndOfCentralDirectoryRecord(archive, this);
	}
	
	public int getHeaderSize()
	{
		int size = 0;
		
		for (FileHeader fHeader : fHeaders) {
			size += fHeader.getSize();
		}
		
		return size;
	}
	
	public int getSize()
	{
		return getHeaderSize() + endOfCDRecord.getSize();
	}
	
	public ByteBuffer getRawBytes()
	{
		ByteBuffer b = ByteBuffer.allocate(getSize());
		b.order(ByteOrder.LITTLE_ENDIAN);
		
		for (FileHeader fHeader : fHeaders) {
			b.put(fHeader.getRawBytes());
		}
		
		b.put(endOfCDRecord.getRawBytes());
		
		b.flip();
		return b;
	}


	public ArrayList<FileHeader> getFileHeaders() {
		return fHeaders;
	}


	public EndOfCentralDirectoryRecord getEndOfCDRecord() {
		return endOfCDRecord;
	}
	
	
	
	
	
	
}

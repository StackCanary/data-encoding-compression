package zip.centraldirectory;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;

import zip.archive.Archive;

public class EndOfCentralDirectoryRecord {
	
	public int endOfCentralDirSignature;
	public short numberOfThisDisk;
	public short numberOfTheDiskWithTheStartOfTheCentralDirectory;
	public short noOfEntriesOnThisDisk;
	public short noOfEntries;
	public int size;
	public int offset;
	
	public short zipFileCommentLength;
	public byte zipFileComment[];
	
	public EndOfCentralDirectoryRecord(Archive archive, CentralDirectory cd) 
	{
		endOfCentralDirSignature = 0x06054b50;
		numberOfThisDisk = 0;
		numberOfTheDiskWithTheStartOfTheCentralDirectory = 0;
		
		this.setNoOfEntries((short) cd.getFileHeaders().size());
		this.setNoOfEntriesOnThisDisk((short) cd.getFileHeaders().size());
		this.setOffset(archive.getTotalFileEntrySize()); 
		this.setSize(cd.getHeaderSize());
		
		zipFileCommentLength = 0;
		zipFileComment = new byte[0];
	}
	
	
	public int getSize()
	{
		return 4 * 3 + 2 * 5;
	}
	
	public ByteBuffer getRawBytes()
	{
		ByteBuffer b = ByteBuffer.allocate(getSize());
		b.order(ByteOrder.LITTLE_ENDIAN);
		
		b.putInt(endOfCentralDirSignature);
		b.putShort(numberOfThisDisk);
		b.putShort(numberOfTheDiskWithTheStartOfTheCentralDirectory);
		b.putShort(noOfEntriesOnThisDisk);
		b.putShort(noOfEntries);
		b.putInt(size);
		b.putInt(offset);
		b.putShort(zipFileCommentLength);
		b.put(zipFileComment);
		
		b.flip();
		return b;
	}
	
	public void setNoOfEntriesOnThisDisk(short noOfEntriesOnThisDisk) {
		this.noOfEntriesOnThisDisk = noOfEntriesOnThisDisk;
	}


	public void setNoOfEntries(short noOfEntries) {
		this.noOfEntries = noOfEntries;
	}


	public void setSize(int size) {
		this.size = size;
	}


	public void setOffset(int offset) {
		this.offset = offset;
	}
	
}

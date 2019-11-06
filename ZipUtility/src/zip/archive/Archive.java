package zip.archive;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;

import zip.centraldirectory.CentralDirectory;
import zip.file.FileEntry;

public class Archive {

	public ArrayList<FileEntry> files = new ArrayList<FileEntry>();
	public CentralDirectory directory;
	
	public Archive()
	{
	
	}
	
	public void createCentralDirectory()
	{
		directory = new CentralDirectory(this);
	}
	
	public int getTotalFileEntrySize()
	{
		int size = 0;
		
		for (FileEntry file : files) {
			size += file.getSize();
		}
		
		return size;
	}
	
	
	public ArrayList<FileEntry> getFilesEntries() {
		return files;
	}

	public CentralDirectory getCentralDirectory() {
		return directory;
	}

	/*
	 * TODO Resolve Offsets
	 */
	public void resolveOffsets() 
	{
		int offset = 0;
		
		for (FileEntry file : files) {
		//	file.fHeader.setRelativeOffsetOfLocalHeader(offset);
			offset += file.getSize();
		}
		
	}
	
	public int getSize()
	{	
		return this.getTotalFileEntrySize() + directory.getSize();
	}
	
	public ByteBuffer getRawBytes()
	{
		ByteBuffer b = ByteBuffer.allocate(getSize());
		
		b.order(ByteOrder.LITTLE_ENDIAN);
		for (FileEntry entry : getFilesEntries()) {
			b.put(entry.getRawBytes());
		}
		
		b.put(getCentralDirectory().getRawBytes());
		
		b.flip();
		return b;
	}
	
	
	
	
	
}

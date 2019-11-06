package zip.create;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import zip.archive.Archive;
import zip.file.FileEntry;
import zip.utility.FileUtility;

/*
 * Creates a Zip file
 */
public class ZipCreator {

	Archive archive = new Archive();
	
	public ZipCreator(List<File> files) throws IOException
	{
		for (File file : files) {
			archive.getFilesEntries().add(FileEntry.CreateFileEntry(file));
		}
		
		archive.createCentralDirectory();
	}
	
	public Archive getArchive()
	{
		return archive;
	}
	
}

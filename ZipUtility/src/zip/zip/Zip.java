package zip.zip;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import zip.create.ZipCreator;
import zip.utility.FileUtility;

public class Zip {

	public static void main(String ...strings) throws IOException
	{
		
		if (strings.length < 1)
		{
			System.out.println("Usage: {program} file1 file2");
			return;
		}
		
		List<File> files = new ArrayList<File>();

		for (String file : strings)
		{
			files.add(new File(file));
		}

		ZipCreator zc = new ZipCreator(files);
		
		FileUtility.writeContents("output.zip", zc.getArchive().getRawBytes());
		
	}
	
}

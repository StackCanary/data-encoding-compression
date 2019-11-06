package zip.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class FileUtility {

	public static byte[] getContents(String filename) throws IOException
	{
		
			File file = new File(filename);
			FileInputStream fs = new FileInputStream(file);
			byte content[] = new byte[(int) file.length()];
			fs.read(content);
			return content;
			
	}
	
	public static byte[] getContents(File file) throws IOException
	{
			FileInputStream fs = new FileInputStream(file);
			byte content[] = new byte[(int) file.length()];
			fs.read(content);
			return content;
			
	}

	public static void writeContents(String filename, byte contents[]) throws IOException
	{
		File file = new File(filename);
		FileOutputStream fs = new FileOutputStream(file);
		fs.write(contents);
	}
	
	public static void writeContents(String filename, ByteBuffer bBuffer) throws IOException
	{
		
		byte contents[] = new byte[bBuffer.limit()];
		bBuffer.get(contents);
		
		File file = new File(filename);
		FileOutputStream fs = new FileOutputStream(file);
		fs.write(contents);
	}
	

}

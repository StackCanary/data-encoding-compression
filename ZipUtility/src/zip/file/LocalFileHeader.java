package zip.file;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class LocalFileHeader {

	public int signature;
	public short versionNeededToExtract;
	public short generalPurposeBitFlag;
	public short compressionMethod;
	public short lastModFileTime;
	public short lastModFileDate;
	public int crc32;

	public int compressedSize;
	public int unCompressedSize;
	public short fileNameLength;
	public short extraFieldLength;

	public byte fileName[];
	public byte extraField[];


	public LocalFileHeader(String filename, FileData fData)
	{
		this.signature = 0x04034b50;
		this.versionNeededToExtract = 0x0A;
		this.generalPurposeBitFlag = 0;
		this.compressionMethod = 0; // 0x08; No Compression / Deflate_
		this.lastModFileTime = 0;
		this.lastModFileDate = 0;


		byte[] extra = {
				  0x55, 0x54, 0x09, 0x00, 0x03, 0x33, (byte) 0xea, (byte) 0xcb, 0x59, (byte) 0xf5, (byte) 0x88, (byte) 0xcf,
				  0x59, 0x75, 0x78, 0x0b, 0x00, 0x01, 0x04, (byte) 0xe8, 0x03, 0x00, 0x00, 0x04,
				  (byte) 0xe8, 0x03, 0x00, 0x00
				};


		this.setFileName(filename.getBytes());
		this.setExtraField(extra);
		this.setCrc32(fData.getcrc32());
		this.setCompressedSize(fData.getCompressedFileSize());
		this.setUnCompressedSize(fData.getUnCompressedFileSize());
	}

	public LocalFileHeader()
	{

	}

	public int getSize()
	{
		return 4 * 4 + 2 * 7 + (fileName.length + extraField.length);
	}

	public ByteBuffer getRawBytes()
	{
		ByteBuffer b = ByteBuffer.allocate(getSize());
		b.order(ByteOrder.LITTLE_ENDIAN);

		b.putInt(signature);
		b.putShort(versionNeededToExtract);
		b.putShort(generalPurposeBitFlag);
		b.putShort(compressionMethod);
		b.putShort(lastModFileTime);
		b.putShort(lastModFileDate);
		b.putInt(crc32);

		b.putInt(compressedSize);
		b.putInt(unCompressedSize);
		b.putShort(fileNameLength);

		b.putShort(extraFieldLength);
		b.put(fileName);
		b.put(extraField);

		b.flip();
		return b;
	}

	public void setCrc32(int crc32) {
		this.crc32 = crc32;
	}

	public void setCompressedSize(int compressedSize) {
		this.compressedSize = compressedSize;
	}

	public void setUnCompressedSize(int unCompressedSize) {
		this.unCompressedSize = unCompressedSize;
	}

	public void setFileName(byte[] fileName) {
		this.fileName = fileName;
		this.fileNameLength = (short) fileName.length;
	}

	public void setExtraField(byte[] extraField) {
		this.extraField = extraField;
		this.extraFieldLength = (short) extraField.length;
	}


}

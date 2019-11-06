package zip.centraldirectory;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import zip.file.LocalFileHeader;

public class FileHeader extends LocalFileHeader {

	public short versionMadeBy;
	
	public short fileCommentLength;
	public short diskNumberStart;
	public short internalFileAttributes;
	public int externalFileAttributes;
	public int relativeOffsetOfLocalHeader; // Offset of File from start
	public byte fileComment[];
	
	public FileHeader(LocalFileHeader lfHeader, int offset)
	{
		this.signature = 0x02014b50;
		this.copy(lfHeader);

		this.setFileComment(new byte[0]);
		this.setDiskNumberStart((short) 0);
		this.setInternalFileAttributes((short) 0);
		this.setExternalFileAttributes(0);
		
		byte[] extra = {
				  0x55, 0x54, 0x05, 0x00, 0x03, 0x33, (byte) 0xea, (byte) 0xcb, 0x59, 0x75, 0x78, 0x0b,
				  0x00, 0x01, 0x04, (byte) 0xe8, 0x03, 0x00, 0x00, 0x04, (byte) 0xe8, 0x03, 0x00, 0x00
				};
		
		this.setExtraField(extra);
		
		/*
		 * This needs to be corrected
		 */
		this.setRelativeOffsetOfLocalHeader(offset);
	}
	
	public void copy(LocalFileHeader toBeCopied)
	{
		this.versionMadeBy = 0x31e;
		this.versionNeededToExtract = toBeCopied.versionNeededToExtract;
		this.generalPurposeBitFlag = toBeCopied.generalPurposeBitFlag;
		this.compressionMethod = toBeCopied.compressionMethod;
		this.lastModFileTime = toBeCopied.lastModFileTime;
		this.lastModFileDate = toBeCopied.lastModFileDate;
		this.crc32 = toBeCopied.crc32;

		this.compressedSize = toBeCopied.compressedSize;
		this.unCompressedSize = toBeCopied.unCompressedSize;
		
		this.fileName = toBeCopied.fileName.clone();
		this.fileNameLength = toBeCopied.fileNameLength;
	}
	
	@Override
	public int getSize()
	{
		return (2 * 4 + 4 * 2 + fileComment.length)  
				+ (4 * 4 + 2 * 7 + (fileName.length + extraField.length));
	}

	@Override
	public ByteBuffer getRawBytes()
	{
		ByteBuffer b = ByteBuffer.allocate(getSize());
		b.order(ByteOrder.LITTLE_ENDIAN);
		
		b.putInt(signature);
		b.putShort(versionMadeBy);
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
		b.putShort(fileCommentLength);
		
		b.putShort(diskNumberStart);
		b.putShort(internalFileAttributes);
		b.putInt(externalFileAttributes);
		b.putInt(relativeOffsetOfLocalHeader);
		
		b.put(fileName);
		b.put(extraField);
		b.put(fileComment);

		b.flip();
		return b;
	}
	
	public void setDiskNumberStart(short diskNumberStart) {
		this.diskNumberStart = diskNumberStart;
	}

	public void setInternalFileAttributes(short internalFileAttributes) {
		this.internalFileAttributes = internalFileAttributes;
	}

	public void setExternalFileAttributes(int externalFileAttributes) {
		this.externalFileAttributes = externalFileAttributes;
	}

	public void setRelativeOffsetOfLocalHeader(int relativeOffsetOfLocalHeader) {
		this.relativeOffsetOfLocalHeader = relativeOffsetOfLocalHeader;
	}

	public void setFileComment(byte[] fileComment) {
		this.fileComment = fileComment;
		this.fileCommentLength = (short) fileComment.length;
	}
	
}

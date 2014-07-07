package uk.co.bernkastel.ebook.defunct;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

/*
 * THIS CODE IS NOT USED IN THE FINAL PRODUCT. PROVIDED AS EXAMPLE OF ATTEMPT AT MOBI IMPLEMENTATION.
 * 
 * Mobi Header class code adapted from Gluggy's Mobi Metadata Editor
 * https://github.com/gluggy/Java-Mobi-Metadata-Editor
 * Available under the MIT Licence
 */
public class MobiHeader {
	private MobiCompression compression;
	private byte[] unused0 = new byte[2];
	private byte[] textLength = new byte[4];
	private byte[] recordCount = new byte[2];
	private byte[] recordSize = new byte[2];
	private byte[] encryptionType = new byte[2];
	private byte[] unused1 = new byte[2];
	private byte[] identifier = new byte[4];
	private byte[] headerLength = new byte[4]; // from offset 0x10
	private byte[] mobiType = new byte[4];
	private byte[] textEncoding = new byte[4];
	private byte[] uniqueID = new byte[4];
	private byte[] fileVersion = new byte[4];
	private byte[] orthographicIndex = new byte[4];
	private byte[] inflectionIndex = new byte[4];
	private byte[] indexNames = new byte[4];
	private byte[] indexKeys = new byte[4];
	private byte[] extraIndex0 = new byte[4];
	private byte[] extraIndex1 = new byte[4];
	private byte[] extraIndex2 = new byte[4];
	private byte[] extraIndex3 = new byte[4];
	private byte[] extraIndex4 = new byte[4];
	private byte[] extraIndex5 = new byte[4];
	private byte[] firstNonBookIndex = new byte[4];
	private byte[] fullNameOffset = new byte[4];
	private byte[] fullNameLength = new byte[4];
	private byte[] locale = new byte[4];
	private byte[] inputLanguage = new byte[4];
	private byte[] outputLanguage = new byte[4];
	private byte[] minVersion = new byte[4];
	private byte[] firstImageIndex = new byte[4];
	private byte[] huffmanRecordOffset = new byte[4];
	private byte[] huffmanRecordCount = new byte[4];
	private byte[] huffmanTableOffset = new byte[4];
	private byte[] huffmanTableLength = new byte[4];
	private byte[] exthFlags = new byte[4];
	private byte[] restOfMobiHeader = null;
	private EXTHHeader exthHeader = null;
	private byte[] remainder = null;

	private byte[] fullName = null;
	private String characterEncoding = null;

	public MobiHeader(InputStream in, long mobiHeaderSize) throws IOException {
		byte[] compressionBytes = new byte[2];
		MobiParserUtils.readByteArray(in, compressionBytes);
		int comp = MobiParserUtils.byteArrayToInt(compressionBytes);
		switch (comp) {
		case 1:
			compression = null;
		case 2:
			compression = MobiCompression.PALMDOC;
		case 17480:
			compression = MobiCompression.HUFF_CDIC;
		default:
			compression = MobiCompression.UNKNOWN;
		}
		
		MobiParserUtils.readByteArray(in, unused0);
		MobiParserUtils.readByteArray(in, textLength);
		MobiParserUtils.readByteArray(in, recordCount);
		MobiParserUtils.readByteArray(in, recordSize);
		MobiParserUtils.readByteArray(in, encryptionType);
		MobiParserUtils.readByteArray(in, unused1);

		MobiParserUtils.readByteArray(in, identifier);

		if ((identifier[0] != 77) || (identifier[1] != 79) || (identifier[2] != 66) || (identifier[3] != 73)) {
			throw new IOException("Did not get expected MOBI identifier");
		}

		MobiParserUtils.readByteArray(in, headerLength);
		int headLen = MobiParserUtils.byteArrayToInt(headerLength);
		restOfMobiHeader = new byte[headLen + 16 - 132];

		MobiParserUtils.readByteArray(in, mobiType);

		MobiParserUtils.readByteArray(in, textEncoding);
		switch (MobiParserUtils.byteArrayToInt(textEncoding)) {
		case 1252:
			characterEncoding = "CP1252";
			break;
		case 65001:
			characterEncoding = "UTF-8";
			break;
		default:
			characterEncoding = null;
			break;
		}

		MobiParserUtils.readByteArray(in, uniqueID);
		MobiParserUtils.readByteArray(in, fileVersion);
		MobiParserUtils.readByteArray(in, orthographicIndex);
		MobiParserUtils.readByteArray(in, inflectionIndex);
		MobiParserUtils.readByteArray(in, indexNames);
		MobiParserUtils.readByteArray(in, indexKeys);
		MobiParserUtils.readByteArray(in, extraIndex0);
		MobiParserUtils.readByteArray(in, extraIndex1);
		MobiParserUtils.readByteArray(in, extraIndex2);
		MobiParserUtils.readByteArray(in, extraIndex3);
		MobiParserUtils.readByteArray(in, extraIndex4);
		MobiParserUtils.readByteArray(in, extraIndex5);
		MobiParserUtils.readByteArray(in, firstNonBookIndex);
		MobiParserUtils.readByteArray(in, fullNameOffset);

		MobiParserUtils.readByteArray(in, fullNameLength);
		int fullNameLen = MobiParserUtils.byteArrayToInt(fullNameLength);
		MobiParserUtils.readByteArray(in, locale);
		MobiParserUtils.readByteArray(in, inputLanguage);
		MobiParserUtils.readByteArray(in, outputLanguage);
		MobiParserUtils.readByteArray(in, minVersion);
		MobiParserUtils.readByteArray(in, firstImageIndex);
		MobiParserUtils.readByteArray(in, huffmanRecordOffset);
		MobiParserUtils.readByteArray(in, huffmanRecordCount);
		MobiParserUtils.readByteArray(in, huffmanTableOffset);
		MobiParserUtils.readByteArray(in, huffmanTableLength);
		MobiParserUtils.readByteArray(in, exthFlags);
		boolean exthExists = ((MobiParserUtils.byteArrayToInt(exthFlags) & 0x40) != 0);
		MobiParserUtils.readByteArray(in, restOfMobiHeader);

		if (exthExists) {
			exthHeader = new EXTHHeader(in);
		}

		int currentOffset = 132 + restOfMobiHeader.length + exthHeaderSize();

		remainder = new byte[(int) (mobiHeaderSize - currentOffset)];
		MobiParserUtils.readByteArray(in, remainder);

		int fullNameIndexInRemainder = MobiParserUtils.byteArrayToInt(fullNameOffset) - currentOffset;
		fullName = new byte[fullNameLen];

		if ((fullNameIndexInRemainder >= 0) && (fullNameIndexInRemainder < remainder.length) && ((fullNameIndexInRemainder + fullNameLen) <= remainder.length) && (fullNameLen > 0)) {
			System.arraycopy(remainder, fullNameIndexInRemainder, fullName, 0, fullNameLen);
		}
	}

	public String getCharacterEncoding() {
		return characterEncoding;
	}

	public String getFullName() {
		return MobiParserUtils.byteArrayToString(fullName, characterEncoding);
	}

	public void setFullName(String s) {
		byte[] fullBytes = MobiParserUtils.stringToByteArray(s, characterEncoding);
		int len = fullBytes.length;
		MobiParserUtils.intToByteArray(len, fullNameLength);

		// the string must be terminated by 2 null bytes
		// then this must end in a 4-byte boundary
		//
		int padding = (len + 2) % 4;
		if (padding != 0)
			padding = 4 - padding;
		padding += 2;

		byte[] buffer = new byte[len + padding];
		System.arraycopy(fullBytes, 0, buffer, 0, len);
		for (int i = len; i < buffer.length; i++)
			buffer[i] = 0;

		fullName = buffer;
	}

	public int getLocale() {
		return MobiParserUtils.byteArrayToInt(locale);
	}

	public void setLocale(int localeInt) {
		MobiParserUtils.intToByteArray(localeInt, locale);
	}

	public int getInputLanguage() {
		return MobiParserUtils.byteArrayToInt(inputLanguage);
	}

	public void setInputLanguage(int input) {
		MobiParserUtils.intToByteArray(input, inputLanguage);
	}

	public int getOutputLanguage() {
		return MobiParserUtils.byteArrayToInt(outputLanguage);
	}

	public void setOutputLanguage(int output) {
		MobiParserUtils.intToByteArray(output, outputLanguage);
	}

	public List<EXTHRecord> getEXTHRecords() {
		return (exthHeader == null) ? (new LinkedList<EXTHRecord>()) : exthHeader.getRecordList();
	}

	public void setEXTHRecords(List<EXTHRecord> list) {
		int flag = MobiParserUtils.byteArrayToInt(exthFlags) & 0xffffbf;
		if ((list == null) || (list.size() == 0)) {
			exthHeader = null;
			MobiParserUtils.intToByteArray(flag, exthFlags);
		} else {
			if (exthHeader == null)
				exthHeader = new EXTHHeader(list);
			else
				exthHeader.setRecordList(list);

			MobiParserUtils.intToByteArray(flag | 0x40, exthFlags);
		}
	}

	public void pack() {
		// dump existing remainder, set to fullName
		remainder = new byte[fullName.length];
		System.arraycopy(fullName, 0, remainder, 0, remainder.length);

		// adjust fullNameOffset
		MobiParserUtils.intToByteArray(132 + restOfMobiHeader.length + exthHeaderSize(), fullNameOffset);

	}

	public int size() {
		return 132 + restOfMobiHeader.length + exthHeaderSize() + remainder.length;
	}

	public MobiCompression getCompression() {
		return compression;
	}

	public long getTextLength() {
		return MobiParserUtils.byteArrayToLong(textLength);
	}

	public int getRecordCount() {
		return MobiParserUtils.byteArrayToInt(recordCount);
	}

	public int getRecordSize() {
		return MobiParserUtils.byteArrayToInt(recordSize);
	}

	public String getEncryptionType() {
		int enc = MobiParserUtils.byteArrayToInt(encryptionType);
		switch (enc) {
		case 0:
			return "None";
		case 1:
			return "Old Mobipocket";
		case 2:
			return "Mobipocket";
		default:
			return "Unknown (" + enc + ")";
		}
	}

	public long getHeaderLength() {
		return MobiParserUtils.byteArrayToLong(headerLength);
	}

	public String getMobiType() {
		long type = MobiParserUtils.byteArrayToLong(mobiType);
		if (type == 2)
			return "Mobipocket Book";
		else if (type == 3)
			return "PalmDoc Book";
		else if (type == 4)
			return "Audio";
		else if (type == 257)
			return "News";
		else if (type == 258)
			return "News Feed";
		else if (type == 259)
			return "News Magazine";
		else if (type == 513)
			return "PICS";
		else if (type == 514)
			return "WORD";
		else if (type == 515)
			return "XLS";
		else if (type == 516)
			return "PPT";
		else if (type == 517)
			return "TEXT";
		else if (type == 518)
			return "HTML";
		else
			return "Unknown (" + type + ")";
	}

	public long getUniqueID() {
		return MobiParserUtils.byteArrayToLong(uniqueID);
	}

	public long getFileVersion() {
		return MobiParserUtils.byteArrayToLong(fileVersion);
	}

	public long getOrthographicIndex() {
		return MobiParserUtils.byteArrayToLong(orthographicIndex);
	}

	public long getInflectionIndex() {
		return MobiParserUtils.byteArrayToLong(inflectionIndex);
	}

	public long getIndexNames() {
		return MobiParserUtils.byteArrayToLong(indexNames);
	}

	public long getIndexKeys() {
		return MobiParserUtils.byteArrayToLong(indexKeys);
	}

	public long getExtraIndex0() {
		return MobiParserUtils.byteArrayToLong(extraIndex0);
	}

	public long getExtraIndex1() {
		return MobiParserUtils.byteArrayToLong(extraIndex1);
	}

	public long getExtraIndex2() {
		return MobiParserUtils.byteArrayToLong(extraIndex2);
	}

	public long getExtraIndex3() {
		return MobiParserUtils.byteArrayToLong(extraIndex3);
	}

	public long getExtraIndex4() {
		return MobiParserUtils.byteArrayToLong(extraIndex4);
	}

	public long getExtraIndex5() {
		return MobiParserUtils.byteArrayToLong(extraIndex5);
	}

	public long getFirstNonBookIndex() {
		return MobiParserUtils.byteArrayToLong(firstNonBookIndex);
	}

	public long getFullNameOffset() {
		return MobiParserUtils.byteArrayToLong(fullNameOffset);
	}

	public long getFullNameLength() {
		return MobiParserUtils.byteArrayToLong(fullNameLength);
	}

	public long getMinVersion() {
		return MobiParserUtils.byteArrayToLong(minVersion);
	}

	public long getHuffmanRecordOffset() {
		return MobiParserUtils.byteArrayToLong(huffmanRecordOffset);
	}

	public long getHuffmanRecordCount() {
		return MobiParserUtils.byteArrayToLong(huffmanRecordCount);
	}

	public long getHuffmanTableOffset() {
		return MobiParserUtils.byteArrayToLong(huffmanTableOffset);
	}

	public long getHuffmanTableLength() {
		return MobiParserUtils.byteArrayToLong(huffmanTableLength);
	}

	private int exthHeaderSize() {
		return (exthHeader == null) ? 0 : exthHeader.size();
	}
}

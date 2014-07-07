package uk.co.bernkastel.ebook.defunct;

import java.io.IOException;
import java.io.InputStream;

/*
 * THIS CODE IS NOT USED IN THE FINAL PRODUCT. PROVIDED AS EXAMPLE OF ATTEMPT AT MOBI IMPLEMENTATION.
 * 
 * Mobi record class code adapted from Gluggy's Mobi Metadata Editor
 * https://github.com/gluggy/Java-Mobi-Metadata-Editor
 * Available under the MIT Licence
 */
public class MobiRecord {

	private byte[] recordOffset = new byte[4];
	private byte recordAttributes = 0;
	private byte[] uniqueID = new byte[3];
	private String recordContents;

	public MobiRecord(InputStream in) throws IOException {
		MobiParserUtils.readByteArray(in, recordOffset);
		setRecordAttributes(MobiParserUtils.readByte(in));
		MobiParserUtils.readByteArray(in, uniqueID);
	}

	public long getRecordOffset() {
		return MobiParserUtils.byteArrayToLong(recordOffset);
	}

	public void setRecordOffset(long newOffset) {
		MobiParserUtils.longToByteArray(newOffset, recordOffset);
	}

	public String getRecordContents() {
		return recordContents;
	}

	public void setRecordContents(String recordContents) {
		this.recordContents = recordContents;
	}

	public byte getRecordAttributes() {
		return recordAttributes;
	}

	public void setRecordAttributes(byte recordAttributes) {
		this.recordAttributes = recordAttributes;
	}
}

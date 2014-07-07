package uk.co.bernkastel.ebook.defunct;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
 * THIS CODE IS NOT USED IN THE FINAL PRODUCT. PROVIDED AS EXAMPLE OF ATTEMPT AT MOBI IMPLEMENTATION.
 * 
 * PDBHeader class code adapted from Gluggy's Mobi Metadata Editor
 * https://github.com/gluggy/Java-Mobi-Metadata-Editor
 * Available under the MIT Licence
 */
public class PDBHeader {
	private byte[] name = new byte[32];
	private byte[] attributes = new byte[2];
	private byte[] version = new byte[2];
	private byte[] creationDate = new byte[4];
	private byte[] modificationDate = new byte[4];
	private byte[] lastBackupDate = new byte[4];
	private byte[] modificationNumber = new byte[4];
	private byte[] appInfoID = new byte[4];
	private byte[] sortInfoID = new byte[4];
	private byte[] type = new byte[4];
	private byte[] creator = new byte[4];
	private byte[] uniqueIDSeed = new byte[4];
	private byte[] nextRecordListID = new byte[4];
	private byte[] numRecords = new byte[2];
	private byte[] gapToData = new byte[2];
	private List<MobiRecord> recordList;

	public PDBHeader(FileInputStream input) throws IOException {
		MobiParserUtils.readByteArray(input, name);
        MobiParserUtils.readByteArray(input, attributes);
        MobiParserUtils.readByteArray(input, version);
        MobiParserUtils.readByteArray(input, creationDate);
        MobiParserUtils.readByteArray(input, modificationDate);
        MobiParserUtils.readByteArray(input, lastBackupDate);
        MobiParserUtils.readByteArray(input, modificationNumber);
        MobiParserUtils.readByteArray(input, appInfoID);
        MobiParserUtils.readByteArray(input, sortInfoID);
        MobiParserUtils.readByteArray(input, type);
        MobiParserUtils.readByteArray(input, creator);
        MobiParserUtils.readByteArray(input, uniqueIDSeed);
        MobiParserUtils.readByteArray(input, nextRecordListID);
        MobiParserUtils.readByteArray(input, numRecords);

		int records = MobiParserUtils.byteArrayToInt(numRecords);
		recordList = new ArrayList<MobiRecord>();
		for (int i = 0; i < records; i++)
			recordList.add(new MobiRecord(input));

		MobiParserUtils.readByteArray(input, gapToData);
	}

	public long getMobiHeaderSize() {
		long l = 0;
		if (recordList.size() > 1)
			l = recordList.get(1).getRecordOffset() - recordList.get(0).getRecordOffset();
		return l;
	}

	public long getOffsetAfterMobiHeader() {
		long l = 0;
		if (recordList.size() > 1)
			l = recordList.get(1).getRecordOffset();
		return l;
	}

	public String getName() {
		return MobiParserUtils.byteArrayToString(name);
	}

	public int getAttributes() {
		return MobiParserUtils.byteArrayToInt(attributes);
	}

	public int getVersion() {
		return MobiParserUtils.byteArrayToInt(version);
	}

	public long getCreationDate() {
		return MobiParserUtils.byteArrayToLong(creationDate);
	}

	public long getModificationDate() {
		return MobiParserUtils.byteArrayToLong(modificationDate);
	}

	public long getLastBackupDate() {
		return MobiParserUtils.byteArrayToLong(lastBackupDate);
	}

	public long getModificationNumber() {
		return MobiParserUtils.byteArrayToLong(modificationNumber);
	}

	public long getAppInfoID() {
		return MobiParserUtils.byteArrayToLong(appInfoID);
	}

	public long getSortInfoID() {
		return MobiParserUtils.byteArrayToLong(sortInfoID);
	}

	public long getType() {
		return MobiParserUtils.byteArrayToLong(type);
	}

	public long getCreator() {
		return MobiParserUtils.byteArrayToLong(creator);
	}

	public long getUniqueIDSeed() {
		return MobiParserUtils.byteArrayToLong(uniqueIDSeed);
	}

	public List<MobiRecord> getRecordList() {
		return recordList;
	}

}

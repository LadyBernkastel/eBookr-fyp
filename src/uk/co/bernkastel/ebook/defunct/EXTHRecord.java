package uk.co.bernkastel.ebook.defunct;

import java.io.IOException;

/*
 * THIS CODE IS NOT USED IN THE FINAL PRODUCT. PROVIDED AS EXAMPLE OF ATTEMPT AT MOBI IMPLEMENTATION.
 * 
 * EXTHRecord class code adapted from Gluggy's Mobi Metadata Editor
 * https://github.com/gluggy/Java-Mobi-Metadata-Editor
 * Available under the MIT Licence
 * 
 */


import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.HashSet;

public class EXTHRecord {

	public final static int[] booleanTypes = { 404 };
	public final static int[] knownTypes = { 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 118, 119, 200, 404, 501, 503, 504 };
	public final static String[] knownDesc = { "author", "publisher", "imprint", "description", "ISBN", "subject", "publishing date", "review", "contributor", "rights", "subject code", "type", "source", "ASIN", "version number", "retail price", "retail price currency", "dictionary short name", "TTS off", "CDE type", "updated title", "ASIN" };
	private static HashMap<Integer, String> typeHash;
	private static HashSet<Integer> booleanTypesSet;

	private byte[] recordType = { 0, 0, 0, 0 };
	private byte[] recordLength = { 0, 0, 0, 0 };
	private byte[] recordData = null;

	static {
		typeHash = new HashMap<Integer, String>(knownTypes.length);
		for (int i = 0; i < knownTypes.length; i++)
			typeHash.put(Integer.valueOf(knownTypes[i]), knownDesc[i]);

		booleanTypesSet = new HashSet<Integer>(booleanTypes.length);
		for (int i = 0; i < booleanTypes.length; i++)
			booleanTypesSet.add(Integer.valueOf(booleanTypes[i]));
	}

	public static boolean isBooleanType(int type) {
		return booleanTypesSet.contains(Integer.valueOf(type));
	}

	public static boolean isKnownType(int type) {
		return typeHash.containsKey(Integer.valueOf(type));
	}

	public static String getDescriptionForType(int type) {
		return typeHash.get(Integer.valueOf(type));
	}

	public EXTHRecord(int recType, String data, String characterEncoding) {
		this(recType, MobiParserUtils.stringToByteArray(data, characterEncoding));
	}

	public EXTHRecord(int recType, boolean data) {
		MobiParserUtils.intToByteArray(recType, recordType);
		recordData = new byte[1];
		recordData[0] = data ? (byte) 1 : 0;
		MobiParserUtils.intToByteArray(size(), recordLength);
	}

	public EXTHRecord(int recType, byte[] data) {
		MobiParserUtils.intToByteArray(recType, recordType);
		int len = (data == null) ? 0 : data.length;
		MobiParserUtils.intToByteArray(len + 8, recordLength);
		recordData = new byte[len];
		if (len > 0) {
			System.arraycopy(data, 0, recordData, 0, len);
		}
	}

	public EXTHRecord(InputStream in) throws IOException {

		MobiParserUtils.readByteArray(in, recordType);
		MobiParserUtils.readByteArray(in, recordLength);

		int len = MobiParserUtils.byteArrayToInt(recordLength);
		if (len < 8)
			throw new IOException("Invalid EXTH record length");

		recordData = new byte[len - 8];
		MobiParserUtils.readByteArray(in, recordData);
	}

	public int getRecordType() {
		return MobiParserUtils.byteArrayToInt(recordType);
	}

	public byte[] getData() {
		return recordData;
	}

	public int getDataLength() {
		return recordData.length;
	}

	public int size() {
		return getDataLength() + 8;
	}

	public void setData(String s, String encoding) {
		recordData = MobiParserUtils.stringToByteArray(s, encoding);
		MobiParserUtils.intToByteArray(size(), recordLength);
	}

	public void setData(int value) {
		if (recordData == null) {
			recordData = new byte[4];
			MobiParserUtils.intToByteArray(size(), recordLength);
		}

		MobiParserUtils.intToByteArray(value, recordData);
	}

	public void setData(boolean value) {
		if (recordData == null) {
			recordData = new byte[1];
			MobiParserUtils.intToByteArray(size(), recordLength);
		}

		MobiParserUtils.intToByteArray(value ? 1 : 0, recordData);
	}

	public EXTHRecord copy() {
		return new EXTHRecord(MobiParserUtils.byteArrayToInt(recordType), recordData);
	}

	public boolean isKnownType() {
		return isKnownType(MobiParserUtils.byteArrayToInt(recordType));
	}

	public String getTypeDescription() {
		return getDescriptionForType(MobiParserUtils.byteArrayToInt(recordType));
	}

	public void write(OutputStream out) throws IOException {
		out.write(recordType);
		out.write(recordLength);
		out.write(recordData);
	}

}

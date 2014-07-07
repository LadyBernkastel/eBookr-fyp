package uk.co.bernkastel.ebook.defunct;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

import uk.co.bernkastel.ebook.view.MessageDialog;

/*
 * THIS CODE IS NOT USED IN THE FINAL PRODUCT. PROVIDED AS EXAMPLE OF ATTEMPT AT MOBI IMPLEMENTATION.
 * 
 * Mobi parser utils class code adapted from Gluggy's Mobi Metadata Editor
 * https://github.com/gluggy/Java-Mobi-Metadata-Editor
 * Available under the MIT Licence
 */
public class MobiParserUtils {

	// static String convertStreamToString(InputStream is) {
	// Scanner s = new Scanner(is).useDelimiter("\\A");
	// String str = s.hasNext() ? s.next() : "";
	// try {
	// is.close();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// return str;
	// }

	//
	@SuppressWarnings("resource")
	static String convertStreamToString(InputStream is) {
		Scanner s = new Scanner(is).useDelimiter("\\A");
		return s.hasNext() ? s.next() : "";
	}

	public static boolean readByteArray(InputStream in, byte[] array) throws IOException {
		int length = array.length;
		int bytesLeft = length;
		int offset = 0;
		while (bytesLeft > 0) {
			int bytesRead = in.read(array, offset, bytesLeft);
			if (bytesRead == -1)
				return false;
			// throw new IOException("Unable to read Mobi byte array");
			offset += bytesRead;
			bytesLeft -= bytesRead;
		}
		return true;
	}

	public static int byteArrayToInt(byte[] array) {
		int total = 0;
		int length = array.length;
		for (int i = 0; i < length; i++) {
			total = (total << 8) + (array[i] & 0xff);
		}
		return total;
	}

	public static String byteArrayToString(byte[] array) {
		return byteArrayToString(array, "cp1252");
	}

	public static String byteArrayToString(byte[] array, String encoding) {
		try {
			return new String(array, encoding);
		} catch (UnsupportedEncodingException e) {
			MessageDialog.showErrorDialog("The Character Encoding for this file is unsupported. Text may not appear accurately.");
			e.printStackTrace();
		}
		return new String(array);
	}

	// public static String byteArrayToString(byte[] array) {
	// return byteArrayToString(array, "cp1252");
	// }
	//
	// public static String byteArrayToString(byte[] array, String encoding) {
	// int length = array.length;
	// int zeroIndex = -1;
	// for (int i = 0; i < length; i++) {
	// byte b = array[i];
	// if (b == 0) {
	// zeroIndex = i;
	// break;
	// }
	// }
	// if (encoding != null) {
	// try {
	// if (zeroIndex == -1)
	// return new String(array, encoding);
	// else
	// return new String(array, 0, zeroIndex, encoding);
	// } catch (UnsupportedEncodingException e) {
	// }
	// }
	// if (zeroIndex == -1)
	// return new String(array);
	// else
	// return new String(array, 0, zeroIndex);
	// }

	public static long byteArrayToLong(byte[] array) {
		long total = 0;
		int length = array.length;
		for (int i = 0; i < length; i++) {
			total = (total << 8) + (array[i] & 0xff);
		}
		return total;
	}

	public static byte readByte(InputStream in) throws IOException {
		int b = in.read();
		if (b == -1)
			throw new IOException("Unable to read byte");
		return (byte) (b & 0xff);
	}

	public static void longToByteArray(long value, byte[] array) {
		int lastIndex = array.length - 1;
		for (int i = lastIndex; i >= 0; i--) {
			array[i] = (byte) (value & 0xff);
			value = value >> 8;
		}
	}

	public static void intToByteArray(int value, byte[] array) {
		int lastIndex = array.length - 1;
		for (int i = lastIndex; i >= 0; i--) {
			array[i] = (byte) (value & 0xff);
			value = value >> 8;
		}
	}

	public static byte[] stringToByteArray(String s) {
		return stringToByteArray(s, null);
	}

	public static byte[] stringToByteArray(String s, String encoding) {
		if (encoding != null) {
			try {
				return s.getBytes(encoding);
			} catch (UnsupportedEncodingException e) {
			}
		}

		return s.getBytes();
	}

	public static String dumpByteArray(byte[] array) {
		StringBuffer sb = new StringBuffer();
		sb.append("{ ");
		int len = array.length;
		for (int i = 0; i < len; i++) {
			if (i > 0)
				sb.append(", ");
			sb.append(array[i] & 0xff);
		}
		sb.append(" }");
		return sb.toString();
	}

	public static int safeLongToInt(long l) {
		if (l < Integer.MIN_VALUE || l > Integer.MAX_VALUE) {
			throw new IllegalArgumentException(l + " cannot be cast to int without changing its value.");
		}
		return (int) l;
	}

}

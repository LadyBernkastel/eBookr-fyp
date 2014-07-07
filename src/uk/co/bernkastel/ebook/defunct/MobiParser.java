package uk.co.bernkastel.ebook.defunct;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import uk.co.bernkastel.ebook.model.Format;
import uk.co.bernkastel.ebook.model.Metadata;
import uk.co.bernkastel.ebook.model.book.Ebook;
import uk.co.bernkastel.ebook.model.parser.Parser;
import uk.co.bernkastel.ebook.view.MessageDialog;


/*
 * THIS CODE IS NOT USED IN THE FINAL PRODUCT. PROVIDED AS EXAMPLE OF ATTEMPT AT MOBI IMPLEMENTATION.
 * 
 * Attempted Mobi parser class.
 */
public class MobiParser extends Parser {
	private PDBHeader pdbHeader;
	private MobiHeader mobiHeader;
	private List<EXTHRecord> exthRecords;

	@Override
	public Format getFormat() {
		//return Format.MOBI;
		return null;
	}

	@Override
	protected Metadata extractMetadata(File file) {
		if (mobiHeader != null || exthRecords != null) {
			Metadata meta = new Metadata();
			if (mobiHeader != null)
				meta.setTitle(mobiHeader.getFullName());
			meta.setPath(file.toPath());
			if (exthRecords != null) {
				for (EXTHRecord record : exthRecords) {
					if (record.isKnownType()) {
						switch (record.getRecordType()) {
						case 100:
							meta.addAuthor(new String(record.getData()));
							break;
						case 101:
							meta.addPublisher(new String(record.getData()));
							break;
						case 104:
							meta.setISBN(new String(record.getData()));
							break;
						default:
							break;
						}
					}
				}
			}
			return meta;
		}
		return null;
	}

	@Override
	public Ebook readBook(File file) {
		FileInputStream input = null;
		MobiEbook ebook = new MobiEbook();
		try {
			input = new FileInputStream(file);
			pdbHeader = new PDBHeader(input);
			long headerSize = pdbHeader.getMobiHeaderSize();
			mobiHeader = new MobiHeader(input, headerSize);
			exthRecords = mobiHeader.getEXTHRecords();

			String charEncoding = mobiHeader.getCharacterEncoding() != null ? mobiHeader
					.getCharacterEncoding() : "UTF-8";
			StringBuffer sb = new StringBuffer();

			for (@SuppressWarnings("unused")
			MobiRecord record : pdbHeader.getRecordList()) {
				int recordSize = mobiHeader.getRecordSize();
				byte bytes[] = new byte[recordSize];

				if (MobiParserUtils.readByteArray(input, bytes)) {
					if (mobiHeader.getCompression() == MobiCompression.PALMDOC)
						bytes = decompressBuffer(bytes);

					String s = "";
					try {
						s = new String(bytes, charEncoding);
					} catch (UnsupportedEncodingException e) {
						MessageDialog
								.showErrorDialog("The Character Encoding for this file is unsupported. Text may not appear accurately.");
						charEncoding = "UTF-8";
						s = new String(bytes, charEncoding);
					} finally {
						sb.append(s);
					}
				}
			}

			Document document = Jsoup.parse(sb.toString());
			document = cleanHTML(document);
			String html = document.html();
			ebook.setContent(html);
		} catch (IOException e) {
			MessageDialog
					.showErrorDialog("File Input Stream Error when loading Mobi file");
			e.printStackTrace();
		} finally {
			try {
				if (input != null)
					input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		ebook.setMetadata(extractMetadata(file));
		ebook.setExthRecords(exthRecords);
		ebook.setMobiHeader(mobiHeader);
		ebook.setPdbHeader(pdbHeader);
		return ebook;
	}

	private Document cleanHTML(Document doc) {
		doc.getElementsByTag("style").remove();
		doc.getElementsByTag("script").remove();

		Element head = doc.getElementsByTag("head").first();
		head.getElementsByAttributeValue("type", "text/css").remove();

		doc.select("font").unwrap();
		doc.select("blockquote").unwrap();

		doc.select("p[width]").removeAttr("width");

		return doc;
	}

	private byte[] decompressBuffer(byte[] data) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		int i = 0;

		while (i < data.length - 1) {
			// // Get the next compressed input byte
			// if (i == data.length-1)
			// System.out.println("");
			int c = ((int) data[i++]) & 0x00FF;

			if (c >= 0x00C0) {
				// type C command (space + char)
				bos.write(' ');
				bos.write((byte) (c & 0x007F));
			} else if (c >= 0x0080) {
				// type B command (sliding window sequence)
				c = (c << 8) | (((int) data[i++]) & 0x00FF);
				// 3 + low 3 bits (Beirne's 'n'+3)
				int windowLen = 3 + (c & 0x0007);
				// next 11 bits (Beirne's 'm')
				int windowDist = (c >> 3) & 0x07FF;

				byte[] window = bos.toByteArray();
				int windowCopyFrom = window.length - windowDist;
				if (windowLen <= windowDist && windowDist < window.length - 1)
					while (windowLen-- > 0)
						bos.write(window[windowCopyFrom++]);
			} else if (c >= 0x0009) {
				// self-representing, no command
				bos.write(c);
			} else if (c >= 0x0001) {
				// type A command (next c chars are literal)
				while (c-- > 0)
					if (i < data.length)
						bos.write(data[i++]);
			} else {
				// c == 0, also self-representing
				bos.write(c);
			}
		}

		byte[] output = bos.toByteArray();
		System.out.println(new String(output));

		try {
			bos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return output;
	}
}

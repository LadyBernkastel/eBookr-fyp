package uk.co.bernkastel.ebook.model.parser;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import uk.co.bernkastel.ebook.model.Format;
import uk.co.bernkastel.ebook.model.Metadata;
import uk.co.bernkastel.ebook.model.book.Ebook;
import uk.co.bernkastel.ebook.model.book.HTMLEbook;

public class HTMLParser extends Parser {
		
	@Override
	public Format getFormat() {
		return Format.HTML;
	}

	@Override
	protected Metadata extractMetadata(File file) {
		Metadata metadata = new Metadata();
		Document document;
		try {
			document = Jsoup.parse(file, "UTF-8");
			metadata.setTitle(document.title());
			metadata.setPath(file.toPath());
		} catch (IOException e) {
			System.err.println("IO Error extracting metadata");
			e.printStackTrace();
		}		
		return metadata;
	}

	@Override
	public Ebook readBook(File file) {
		HTMLEbook ebook = new HTMLEbook();
		ebook.setMetadata(extractMetadata(file));
		Document document;
		try {
			document = Jsoup.parse(file, "UTF-8");
			document = cleanHTML(document);
			String html = document.html();
			ebook.setContent(html);
		} catch (IOException e) {
			System.err.println("IO Error reading book");
			e.printStackTrace();
		}
		return ebook;
	}
	
	private Document cleanHTML(Document doc) {
		doc.getElementsByTag("style").remove();
		doc.getElementsByTag("script").remove();
		Element head = doc.getElementsByTag("head").first();
		head.getElementsByAttributeValue("type", "text/css").remove();
		return doc;
	}

}

package uk.co.bernkastel.ebook.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import uk.co.bernkastel.ebook.model.book.ComicEbook;
import uk.co.bernkastel.ebook.model.book.Ebook;
import uk.co.bernkastel.ebook.model.book.HTMLEbook;
import uk.co.bernkastel.ebook.model.book.PDFEbook;
import uk.co.bernkastel.ebook.model.parser.ComicParser;
import uk.co.bernkastel.ebook.model.parser.EpubParser;
import uk.co.bernkastel.ebook.model.parser.HTMLParser;
import uk.co.bernkastel.ebook.model.parser.PDFParser;
import uk.co.bernkastel.ebook.model.parser.Parser;
import uk.co.bernkastel.ebook.view.book.BookView;
import uk.co.bernkastel.ebook.view.book.ComicBookView;
import uk.co.bernkastel.ebook.view.book.HTMLBookView;
import uk.co.bernkastel.ebook.view.book.PDFBookView;

public enum Format {
	
	EPUB("Epub", HTMLBookView.class, EpubParser.class, HTMLEbook.class, false, ".epub"),
	PDF("PDF", PDFBookView.class, PDFParser.class, PDFEbook.class, true, ".pdf"),
	HTML("HTML", HTMLBookView.class, HTMLParser.class, HTMLEbook.class, false, ".htm", ".html"),
	COMIC("Comic Book", ComicBookView.class, ComicParser.class, ComicEbook.class, true, ".cbz");
	
	private String name;
	private Class<? extends BookView> bookView;
	private Class<? extends Parser> parser;
	private Class<? extends Ebook> ebook;
	private List<String> extensions;
	private boolean pageable;
	
	private Format(String name, Class<? extends BookView> view, Class<? extends Parser> parser, Class<? extends Ebook> book, boolean pageable, String...extensions) {
		this.name = name;
		this.bookView = view;
		this.parser = parser;
		this.ebook = book;
		this.pageable = pageable;
		this.extensions = new ArrayList<String>(Arrays.asList(extensions));
	}

	public Class<? extends BookView> getBookView() {
		return bookView;
	}
	public Class<? extends Parser> getParser() {
		return parser;
	}
	public Class<? extends Ebook> getEbook() {
		return ebook;
	}
	public String getName() {
		return name;
	}
	public List<String> getExtensions() {
		return extensions;
	}	
	public boolean isPageable() {
		return pageable;
	}
}

package uk.co.bernkastel.ebook.test;

import static org.junit.Assert.*;

import java.io.File;
import java.net.URL;

import org.junit.Before;
import org.junit.Test;

import uk.co.bernkastel.ebook.model.Format;
import uk.co.bernkastel.ebook.model.Metadata;
import uk.co.bernkastel.ebook.model.book.Ebook;
import uk.co.bernkastel.ebook.model.book.HTMLEbook;
import uk.co.bernkastel.ebook.model.parser.EpubParser;
import uk.co.bernkastel.ebook.model.parser.Parser;

public class EpubParserTest {
	
	private Parser parser = new EpubParser();
	private File testFile;

	@Before
	public void setUp() throws Exception {
		URL url = EpubParserTest.class.getResource("data/A Briefer History of Time - Hawking_ Stephen.epub");
		testFile = new File(url.toURI());
	}

	@Test
	public void testGetFormat() {
		assertEquals("Should return epub format", parser.getFormat(), Format.EPUB);
	}

	@Test
	public void testReadBookFile() {
		Ebook ebook = parser.readBook(testFile);
		assertTrue(ebook instanceof HTMLEbook);
		assertNotNull(((HTMLEbook) ebook).getContent());
		assertTrue(ebook.getImages().size() == 40);
		
		Metadata metadata = ebook.getMetadata();
		assertNotNull(metadata);
		assertTrue(metadata.getAuthorList().contains("Hawking, Stephen"));
		assertTrue(metadata.getPublisherList().contains("Bantam Dell"));
		assertEquals("Title should equal A Briefer History of Time", metadata.getTitle(), "A Briefer History of Time");	
	}

}

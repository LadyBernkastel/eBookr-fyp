package uk.co.bernkastel.ebook.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.net.URL;

import org.junit.Before;
import org.junit.Test;

import uk.co.bernkastel.ebook.model.Format;
import uk.co.bernkastel.ebook.model.Metadata;
import uk.co.bernkastel.ebook.model.book.Ebook;
import uk.co.bernkastel.ebook.model.book.HTMLEbook;
import uk.co.bernkastel.ebook.model.parser.HTMLParser;
import uk.co.bernkastel.ebook.model.parser.Parser;

public class HTMLParserTest {
	
	private Parser parser = new HTMLParser();
	private File testFile;
	
	@Before
	public void setUp() throws Exception {
		URL url = EpubParserTest.class.getResource("data/Unchecked Exceptions.htm");
		testFile = new File(url.toURI());
	}

	@Test
	public void testGetFormat() {
		assertEquals("Should return html format", parser.getFormat(), Format.HTML);
	}

	@Test
	public void testReadBookFile() {
		Ebook ebook = parser.readBook(testFile);
		assertTrue(ebook instanceof HTMLEbook);
		assertNotNull(((HTMLEbook) ebook).getContent());
		
		Metadata metadata = ebook.getMetadata();
		assertNotNull(metadata);
		assertEquals(metadata.getTitle(), "Unchecked Exceptions � The Controversy (The Java� Tutorials > Essential Classes > Exceptions)");	
	}

}

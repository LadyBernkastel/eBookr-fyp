package uk.co.bernkastel.ebook.test;

import static org.junit.Assert.*;

import java.io.File;
import java.net.URL;

import org.junit.Before;
import org.junit.Test;

import uk.co.bernkastel.ebook.model.Format;
import uk.co.bernkastel.ebook.model.Metadata;
import uk.co.bernkastel.ebook.model.book.Ebook;
import uk.co.bernkastel.ebook.model.book.PDFEbook;
import uk.co.bernkastel.ebook.model.parser.PDFParser;
import uk.co.bernkastel.ebook.model.parser.Parser;

public class PDFParserTest {

	private Parser parser = new PDFParser();
	private File testFile;
	
	@Before
	public void setUp() throws Exception {
		URL url = EpubParserTest.class.getResource("data/The Tales of Beedle the Bard - J.K. Rowling.pdf");
		testFile = new File(url.toURI());
	}

	@Test
	public void testGetFormat() {
		assertEquals("Should return pdf format", parser.getFormat(), Format.PDF);
	}

	@Test
	public void testReadBookFile() {
		Ebook ebook = parser.readBook(testFile);
		assertTrue(ebook instanceof PDFEbook);
		assertNotNull(((PDFEbook) ebook).getContent());
		
		Metadata metadata = ebook.getMetadata();
		assertNotNull(metadata);
		assertEquals(metadata.getTitle(), "The Tales of Beedle the Bard");	
		assertTrue(metadata.getAuthorList().contains("J.K. Rowling"));
		assertTrue(metadata.getPublisherList().contains("Acrobat Distiller 7.0 (Windows)"));
	}

}

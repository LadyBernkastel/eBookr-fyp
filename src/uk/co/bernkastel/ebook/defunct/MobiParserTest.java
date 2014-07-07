package uk.co.bernkastel.ebook.defunct;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.net.URL;

import org.junit.Before;
import org.junit.Test;

import uk.co.bernkastel.ebook.model.Metadata;
import uk.co.bernkastel.ebook.model.book.Ebook;
import uk.co.bernkastel.ebook.model.parser.Parser;

/*
 * THIS CODE IS NOT USED IN THE FINAL PRODUCT. PROVIDED AS EXAMPLE OF ATTEMPT AT MOBI IMPLEMENTATION.
 * 
 * Mobi parser test created to test mobi parsing.
 */
public class MobiParserTest {
	private Parser parser = new MobiParser();
	private File testFile;

	@Before
	public void setUp() throws Exception {
		URL url = MobiParserTest.class.getResource("data/I, Robot (Robots #1) - Isaac Asimov.mobi");
		testFile = new File(url.toURI());
	}

	@Test
	public void testGetFormat() {
		assertEquals("Should return mobi format", parser.getFormat(), null);
	}

	@Test
	public void testReadBookFile() {
		Ebook ebook = parser.readBook(testFile);
		assertTrue(ebook instanceof MobiEbook);
		assertNotNull(((MobiEbook) ebook).getContent());
		
		Metadata metadata = ebook.getMetadata();
		assertNotNull(metadata);
	}

}

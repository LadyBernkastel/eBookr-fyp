package uk.co.bernkastel.ebook.test.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import uk.co.bernkastel.ebook.test.EpubParserTest;
import uk.co.bernkastel.ebook.test.HTMLParserTest;
import uk.co.bernkastel.ebook.test.PDFParserTest;

@RunWith(Suite.class)
@SuiteClasses({ EpubParserTest.class, HTMLParserTest.class, PDFParserTest.class })
public class ParserTests {

}

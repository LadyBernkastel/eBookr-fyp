package uk.co.bernkastel.ebook.test.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ ApplicationTests.class, ParserTests.class })
public class AllTests {

}

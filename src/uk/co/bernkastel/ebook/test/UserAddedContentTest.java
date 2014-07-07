package uk.co.bernkastel.ebook.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Before;
import org.junit.Test;

import uk.co.bernkastel.ebook.controller.BookManager;
import uk.co.bernkastel.ebook.controller.BookmarkManager;
import uk.co.bernkastel.ebook.model.Bookmark;
import uk.co.bernkastel.ebook.model.book.Ebook;
import uk.co.bernkastel.ebook.model.book.HTMLEbook;

public class UserAddedContentTest {
	private BookmarkManager uacMan = BookmarkManager.getInstance();
	private Ebook testBook = new HTMLEbook();
	private String testString = "Test String";
	private File file = new File(uacMan.getLocation() + "testfile.ser");
	private String md5String;

	@Before
	public void setUp() throws Exception {
		// Check whether the testfile.ser already exists
		if (!file.exists()) {
			// If not, check the uac location exists
			if (!new File(uacMan.getLocation()).exists())
				// If not, create it.
				Files.createDirectory(new File(uacMan.getLocation()).toPath());
			// Create the test file
			Files.createFile(file.toPath());
		}
		// Get the file hex = d41d8cd98f00b204e9800998ecf8427e.ser
		md5String = DigestUtils.md5Hex(new FileInputStream(file));

		List<Bookmark> uacList = new ArrayList<Bookmark>();
		
		// Test bookmark 1
		Bookmark a = new Bookmark();
		a.setName(testString);
		
		// Test bookmark 2
		Bookmark b = new Bookmark();
		b.setStartLocation(3);

		// Add bookmarks
		uacList.add(b);
		uacList.add(a);
		uacList.add(new Bookmark());
		
		// Add them to test book
		testBook.setBookmarks(uacList);
		// Set the test book's md5 and add it to book manager
		// This is normally done by book manager on open
		testBook.setMd5(md5String);
		BookManager.getInstance().setOpenBook(testBook);
	}

	@Test
	public void testSaveLoadUAC() {
		uacMan.saveBookmarks();
		assertTrue(file.exists());

		List<Bookmark> deserializedList = uacMan
				.getBookmarks(file);
		assertFalse(deserializedList.isEmpty());
		assertTrue(deserializedList.size() == 3);
		assertTrue(deserializedList.get(1) instanceof Bookmark);
		assertEquals(((Bookmark) deserializedList.get(1)).getName(),
				testString);
		
		for (Bookmark b : deserializedList)
			System.out.println(b);
	}

}

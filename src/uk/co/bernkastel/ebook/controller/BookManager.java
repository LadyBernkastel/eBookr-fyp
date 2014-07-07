package uk.co.bernkastel.ebook.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.commons.codec.digest.DigestUtils;

import uk.co.bernkastel.ebook.model.Format;
import uk.co.bernkastel.ebook.model.Metadata;
import uk.co.bernkastel.ebook.model.book.Ebook;
import uk.co.bernkastel.ebook.model.prefs.ApplicationPreferences;
import uk.co.bernkastel.ebook.view.book.BookView;

/**
 * The BookManager class is responsible for opening books, dealing with metadata
 * and creating the BookView to display a book. It also provides the currently
 * open book for other controllers to use and manages the list of previously
 * opened books.
 * 
 * @author racha_000
 * 
 */
public class BookManager {
	private Format currentFormat;
	private Ebook book = null;
	private static BookManager instance = null;
	private LinkedHashSet<Ebook> previousBooks = null;

	private BookManager() {
		book = null;
		retrievePreviousBooks();
	}

	public static BookManager getInstance() {
		if (instance == null)
			instance = new BookManager();
		return instance;
	}

	/**
	 * Open the given file and return a book view representing that book.
	 * 
	 * @param file
	 *            A file representing an ebook in the user's file system.
	 * @return A book view allowing the display of that book.
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public BookView openBook(File file) throws InstantiationException,
			IllegalAccessException, FileNotFoundException, IOException {
		// Obtain the file's format
		currentFormat = getFormat(file);
		// Use the parser for the book's format to read in the book file
		book = currentFormat.getParser().newInstance().readBook(file);

		// Bookmark retrieval
		book.setMd5(DigestUtils.md5Hex(new FileInputStream(file)));
		book.setBookmarks(BookmarkManager.getInstance().getBookmarks(file));

		// Add the book to the previous books list if the book is not the
		// default book
		if (getMetadata() != null
				&& !getMetadata().getPath().toString()
						.contains("defaultBook-ebookr")) {
			if (previousBooks.size() >= PreviousBooksManager.MAX_NUMBER_BOOKS) {
				Iterator<Ebook> it = previousBooks.iterator();
				it.next();
				it.remove();
			}
			previousBooks.add(book);
		}

		BookView view = createBookView(book);
		return view;
	}

	/**
	 * Set the currently open book in the book manager.
	 * 
	 * @param book
	 */
	public void setOpenBook(Ebook book) {
		this.book = book;
	}

	/**
	 * Create a book view for the given ebook.
	 * 
	 * @param book
	 *            Ebook to create a book view for.
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public BookView createBookView(Ebook book) throws InstantiationException,
			IllegalAccessException {
		BookView view = currentFormat.getBookView().newInstance();
		view.setContent(book);
		return view;
	}

	/**
	 * Determine the ebook format for a given file.
	 * 
	 * @param file
	 * @return
	 */
	private Format getFormat(File file) {
		for (Format format : Format.values())
			for (String extension : format.getExtensions())
				if (file.getPath().endsWith(extension))
					return format;
		return null;
	}

	/**
	 * Returns metadata for currently open book.
	 * 
	 * @return Returns metadata, or null if no currently open book.
	 */
	public Metadata getMetadata() {
		if (book != null)
			return book.getMetadata();
		else
			return null;
	}

	public Ebook getBook() {
		return book;
	}

	public Format getFormat() {
		return currentFormat;
	}

	private void retrievePreviousBooks() {
		ApplicationPreferences prefs = new ApplicationPreferences();
		if (prefs
				.getBooleanPreference(ApplicationPreferences.KEY_SAVE_PREVIOUS_BOOKS))
			previousBooks = PreviousBooksManager.getInstance().getBooks();
	}

	public Set<Ebook> getPreviousBooks() {
		return previousBooks;
	}

}

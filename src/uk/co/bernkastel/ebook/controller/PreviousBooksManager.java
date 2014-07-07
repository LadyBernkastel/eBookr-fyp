package uk.co.bernkastel.ebook.controller;

import java.nio.file.Files;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import uk.co.bernkastel.ebook.model.book.Ebook;

public class PreviousBooksManager extends StateManager {
	private static PreviousBooksManager instance = null;
	public static final int MAX_NUMBER_BOOKS = 5;
	private static final String filename = "previous_books";

	protected PreviousBooksManager() {

	}

	public static PreviousBooksManager getInstance() {
		if (instance == null)
			instance = new PreviousBooksManager();
		return instance;
	}

	/**
	 * Returns a list of the books previously opened by the user that still
	 * exist in the same location on the file system.
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public LinkedHashSet<Ebook> getBooks() {
		LinkedHashSet<Ebook> books = new LinkedHashSet<Ebook>(MAX_NUMBER_BOOKS);
		Object obj = getStateObject(filename);

		if (obj instanceof LinkedHashSet<?>)
			books = (LinkedHashSet<Ebook>) obj;
		else
			return books;

		// Checks if all the books in the list still exist and removes any that
		// don't
		Iterator<Ebook> it = books.iterator();

		while (it.hasNext()) {
			Ebook bookI = it.next();
			if (!previousBookExists(bookI)) {
				it.remove();
			}
		}
		return books;
	}

	/**
	 * Saves the previous books list currently held by the book manager to the
	 * file system.
	 */
	public void saveBooks() {
		Set<Ebook> books = BookManager.getInstance().getPreviousBooks();
		saveStateObject(books, filename);
	}

	/**
	 * Determines whether a given book currently exists on the file system at
	 * the path that is specified in the book's metadata.
	 * 
	 * @param book
	 * @return
	 */
	private boolean previousBookExists(Ebook book) {
		if (Files.exists(book.getMetadata().getPath()))
			return true;
		return false;
	}
}

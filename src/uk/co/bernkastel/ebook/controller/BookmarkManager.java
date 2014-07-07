package uk.co.bernkastel.ebook.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.commons.codec.digest.DigestUtils;

import uk.co.bernkastel.ebook.model.Bookmark;
import uk.co.bernkastel.ebook.model.book.Ebook;
import uk.co.bernkastel.ebook.model.prefs.ApplicationPreferences;
import uk.co.bernkastel.ebook.model.prefs.DateSaveFormat;
import uk.co.bernkastel.ebook.view.MessageDialog;
import uk.co.bernkastel.ebook.view.book.BookView;

/**
 * BookmarkManager is responsible for saving/removing/loading bookmarks.
 * @author racha_000
 *
 */
public class BookmarkManager extends StateManager {
	private static BookmarkManager instance = null;

	private BookmarkManager() {
	}

	public static BookmarkManager getInstance() {
		if (instance == null)
			instance = new BookmarkManager();
		return instance;
	}

	/**
	 * Creates a bookmark in the currently open book with the current location
	 * selected in the book view.
	 * 
	 * @param name
	 *            User added text given to name the bookmark.
	 * @param pagedBook
	 *            Whether or not the current book is paged.
	 */
	public void createBookmark(String name) {
		int start = 0;
		int end = 0;

		// Current book view
		BookView view = Controller.getInstance().getCurrentView().getBookView();

		start = view.getSelectedStart();
		end = view.getSelectedEnd();

		// Create the date added and format it according to the user's preferences
		Date currentDate = new Date();
		ApplicationPreferences prefs = new ApplicationPreferences();
		String dateDisplay = prefs.getPreference(ApplicationPreferences.KEY_DATE_FORMAT);
		DateSaveFormat dsf = null;
		for (DateSaveFormat d : DateSaveFormat.values())
			if (dateDisplay.equals(d.getDisplay()))
				dsf = d;
		
		String dateAdded = dsf.formatDate(currentDate);

		// Add information to bookmark
		Bookmark bookmark = new Bookmark();
		bookmark.setEndLocation(end);
		bookmark.setStartLocation(start);
		bookmark.setName(name);
		bookmark.setPreview(view.getSelectedText());
		bookmark.setDateAdded(dateAdded);

		BookManager.getInstance().getBook().addBookmark(bookmark);

		BookmarkManager.getInstance().saveBookmarks();
	}

	/**
	 * Creates a dialog asking the user to give their bookmark a name. If they
	 * cancel the dialog, a bookmark is not created. If they click OK then the
	 * createBookmark method is called. Provides a consistent UI for adding
	 * bookmarks.
	 * 
	 * @param pagedBook
	 *            Whether or not the current book is paged
	 */
	public void createBookmarkDialog() {
		String name = JOptionPane.showInputDialog(null,
				"Give your bookmark a name:");
		if (name != null)
			createBookmark(name);
		JOptionPane.showMessageDialog(null, "Your bookmark has been added.");
	}

	/**
	 * Deletes the given bookmark if it exists in the current book.
	 * 
	 * @param bookmark
	 *            Bookmark to delete.
	 */
	public void deleteBookmark(Bookmark bookmark) {
		List<Bookmark> bookmarks = BookManager.getInstance().getBook()
				.getBookmarks();
		if (bookmarks.contains(bookmark)) {
			bookmarks.remove(bookmark);
			saveBookmarks();
		} else
			MessageDialog
					.showErrorDialog("Unable to delete bookmark. Bookmark not found.");
	}

	/**
	 * Obtains the list of bookmarks for the given file, if any exist.
	 * 
	 * @param bookFile
	 *            File representing the book to obtain bookmarks for.
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Bookmark> getBookmarks(File bookFile) {
		List<Bookmark> uacList = new ArrayList<Bookmark>();
		Object obj = getStateObject(getMD5Hash(bookFile));

		if (obj instanceof List<?>)
			uacList = (List<Bookmark>) obj;

		return uacList;
	}

	/**
	 * Saves the current book's bookmark list to the file system
	 */
	public void saveBookmarks() {
		Ebook book = BookManager.getInstance().getBook();
		List<Bookmark> bookmarkList = book.getBookmarks();
		String md5 = book.getMd5();

		// If the list has bookmarks
		if (!bookmarkList.isEmpty()) {
			saveStateObject(bookmarkList, md5);
		}
	}

	/**
	 * Return the String MD5 hash of the given file. MD5 hashes are used to
	 * identify books.
	 * 
	 * @param file
	 *            File to return MD5 hash of.
	 * @return
	 */
	private String getMD5Hash(File file) {
		try {
			return DigestUtils.md5Hex(new FileInputStream(file));
		} catch (IOException e) {
			System.err
					.println("Unable to create MD5 hash with FileInputStream.");
			e.printStackTrace();
		}
		return null;
	}
}

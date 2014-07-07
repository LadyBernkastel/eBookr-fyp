package uk.co.bernkastel.ebook.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JFrame;

import org.apache.commons.codec.digest.DigestUtils;

import uk.co.bernkastel.ebook.model.prefs.ApplicationPreferences;
import uk.co.bernkastel.ebook.view.MainWindowView;
import uk.co.bernkastel.ebook.view.MessageDialog;
import uk.co.bernkastel.ebook.view.book.BookView;

/**
 * Main controller for the application. Interacts with other controllers (ie the
 * BookManager) and creates the main view.
 * 
 * @author racha_000
 * 
 */
public class Controller {
	private MainWindowView currentView;
	private BookManager bookManager;
	private static Controller instance = null;

	protected Controller() {
		bookManager = BookManager.getInstance();
	}

	public static Controller getInstance() {
		if (instance == null)
			instance = new Controller();
		return instance;
	}

	public void initView() {
		currentView = new MainWindowView();
		openInitialBook();
	}

	/**
	 * Determines the book to open on application load and loads it in to the
	 * reader. This could either be the default page or the last opened book by
	 * the user.
	 */
	private void openInitialBook() {
		ApplicationPreferences prefs = new ApplicationPreferences();
		boolean previousBookLoad = prefs.getPreference(
				ApplicationPreferences.KEY_AUOTMATICALLY_OPEN_BOOK).equals(
				"true");

		// If the user has chosen to automatically open their book on load
		if (previousBookLoad) {
			// load the details of the book
			String path = prefs
					.getPreference(ApplicationPreferences.KEY_PREVIOUS_BOOK_PATH);
			String md5 = prefs
					.getPreference(ApplicationPreferences.KEY_PREVIOUS_BOOK_MD5);
			int location = 0;

			// If the user has chosen to save the location
			boolean loadLocation = prefs.getPreference(
					ApplicationPreferences.KEY_OPEN_PREVIOUS_LOC)
					.equals("true");
			if (loadLocation) {
				// load the location
				String locPref = prefs
						.getPreference(ApplicationPreferences.KEY_PREVIOUS_BOOK_LOC);
				if (locPref != "null")
					location = Integer.parseInt(locPref);
			}

			// Load the file in
			File file = new File(path);
			if (file.exists()) {
				try {
					if (DigestUtils.md5Hex(new FileInputStream(file)).equals(
							md5)) {
						openBook(file);
						if (loadLocation) {
							getCurrentView().getBookView().goToLocation(
									location);
						}
					} else {
						System.err
								.println("MD5 hash doesn't match previously saved file");
						openDefaultBook();
					}
				} catch (IOException e) {
					System.err
							.println("There was an IO error when loading in previous book");
					e.printStackTrace();
					openDefaultBook();
				}
			} else {
				System.err
						.println("Unable to load previous book, file missing.");
				openDefaultBook();
			}

		} else {
			openDefaultBook();
		}
	}

	public void openDefaultBook() {
		File file = null;
		try {
			file = File.createTempFile("defaultBook-ebookr", ".html");
			FileOutputStream fos = new FileOutputStream(file);
			String content = new String(
					"<!DOCTYPE html><html><head><meta charset=\"ISO-8859-1\">"
							+ "<title>Ebookr</title></head><body><h1>Welcome to Ebookr</h1>"
							+ "	<p>Ebookr is a simple-to-use, multi platform Ebook Reader which is fully functional and "
							+ "currently supports Epub, PDF and HTML files."
							+ "	Ebookr is also the the Final Year Project of Rachael Smith, studying Computer Science "
							+ "at the University of Salford.</p>"
							+ "<h2>How to Use</h2><p>To start using Ebookr, simply go File > Open Book "
							+ "and select a book to start reading. Ebookr includes a number of tools and"
							+ " customisation options available in the menus above.</p>"
							+ "	<h2>Having Problems?</h2>"
							+ "	<p>If you need any help, simply go to the Help menu at the top and select the Help option "
							+ "to access the help pages.</p></body></html>");
			fos.write(content.getBytes());
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			openBook(file);
		}
	}

	public void openBook(File selectedFile) {
		BookView view = null;
		try {
			view = bookManager.openBook(selectedFile);
		} catch (InstantiationException | IllegalAccessException | IOException e) {
			MessageDialog.showErrorDialog("Unable to open book - "
					+ e.getMessage());
			e.printStackTrace();
		} finally {
			if (view != null)
				setBookView(view);
		}
	}

	private void setBookView(BookView bookView) {
		if (bookView != null) {
			currentView.setBookView(bookView);
			if (bookManager.getMetadata() != null)
				if (bookManager.getMetadata().getTitle() != null)
					currentView.setTitle("eBookr - "
							+ bookManager.getMetadata().getTitle());
		}
	}

	public void refreshBookView() {
		BookView view;
		try {
			view = bookManager.createBookView(bookManager.getBook());
			setBookView(view);
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * To be called by the view on application exit.
	 */
	public void applicationExit() {
		ApplicationPreferences prefs = new ApplicationPreferences();

		// If the user has chosen to automatically open their book on load
		if (prefs
				.getBooleanPreference(ApplicationPreferences.KEY_AUOTMATICALLY_OPEN_BOOK) == true) {
			// Save the details of the current book
			String path = bookManager.getMetadata().getPath().toString();
			String md5 = bookManager.getBook().getMd5();
			prefs.setPreference(ApplicationPreferences.KEY_PREVIOUS_BOOK_PATH,
					path);
			prefs.setPreference(ApplicationPreferences.KEY_PREVIOUS_BOOK_MD5,
					md5);

			// If the user has chosen to save the location
			if (prefs.getPreference(
					ApplicationPreferences.KEY_OPEN_PREVIOUS_LOC)
					.equals("true")) {
				// Save the location
				int location = getCurrentView().getBookView()
						.getSelectedStart();
				prefs.setPreference(
						ApplicationPreferences.KEY_PREVIOUS_BOOK_LOC, location
								+ "");
			}
		}

		// If the user has chosen to save their list of previous books then save
		if (prefs
				.getBooleanPreference(ApplicationPreferences.KEY_SAVE_PREVIOUS_BOOKS) == true)
			PreviousBooksManager.getInstance().saveBooks();

		// Quit
		System.exit(0);
	}

	public JFrame getMainWindowFrame() {
		return currentView.getFrame();
	}

	public MainWindowView getCurrentView() {
		return currentView;
	}
}

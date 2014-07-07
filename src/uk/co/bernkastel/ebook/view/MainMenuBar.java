package uk.co.bernkastel.ebook.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import uk.co.bernkastel.ebook.controller.BookManager;
import uk.co.bernkastel.ebook.controller.BookmarkManager;
import uk.co.bernkastel.ebook.controller.Controller;
import uk.co.bernkastel.ebook.model.book.Ebook;

public class MainMenuBar extends JMenuBar implements Observer {
	private static final long serialVersionUID = 1L;
	private boolean pageable;
	private List<JMenuItem> observingPagedMenuItems = new ArrayList<JMenuItem>();
	private List<JMenuItem> observingScrollMenuItems = new ArrayList<JMenuItem>();

	public MainMenuBar() {
		// Create the File Menu
		JMenu fileMenu = new JMenu("File");
		JMenuItem menuItemOpen = new JMenuItem("Open Book");
		menuItemOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser jfc = new JFileChooser();
				jfc.setFileFilter(new BookFileFilter());
				int result = jfc.showOpenDialog(new JFrame());
				if (result == JFileChooser.APPROVE_OPTION) {
					if (Controller.getInstance() != null)
						Controller.getInstance()
								.openBook(jfc.getSelectedFile());
					else
						MessageDialog
								.showErrorDialog("Unable to open book - no Book Manager present");
				}
			}
		});
		fileMenu.add(menuItemOpen);
		JMenuItem menuItemClose = new JMenuItem("Close Book");
		menuItemClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Controller.getInstance().openDefaultBook();
			}			
		});
		fileMenu.add(menuItemClose);
		JMenuItem menuItemPreferences = new JMenuItem("Preferences");
		menuItemPreferences.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new PreferencesDialog();
			}
		});
		fileMenu.add(menuItemPreferences);
		fileMenu.addSeparator();
		
		/*
		 * Adding previous books to the menu
		 */
		for (final Ebook book : BookManager.getInstance().getPreviousBooks()) {
			// Determine the label name
			String title = book.getMetadata().getTitle();
			if (title == null || title.trim().isEmpty())
				title = "No Title";
			else if (title.length() > 50)
				title = title.substring(0, 46) + "...";
			
			JMenuItem previousBookItem = new JMenuItem(title);
			previousBookItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					Controller.getInstance().openBook(new File(book.getMetadata().getPath().toString()));				
				}				
			});
			fileMenu.add(previousBookItem);
		}
		
		if (!BookManager.getInstance().getPreviousBooks().isEmpty())
			fileMenu.addSeparator();
		
		JMenuItem menuItemExit = new JMenuItem("Exit");
		menuItemExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Controller.getInstance().applicationExit();
			}
		});
		fileMenu.add(menuItemExit);

		this.add(fileMenu);

		// Create the View Menu
		JMenu viewMenu = new JMenu("View");
		JMenuItem menuItemDisplayOptions = new JMenuItem("Display Options");
		menuItemDisplayOptions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new DisplayOptionsDialog();
			}
		});
		viewMenu.add(menuItemDisplayOptions);

		JMenuItem menuItemProperties = new JMenuItem("Book Properties");
		menuItemProperties.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new BookPropertiesDialog();
			}
		});
		viewMenu.add(menuItemProperties);

		this.add(viewMenu);

		// Create the Navigate menu
		JMenu navigateMenu = new JMenu("Navigate");
		JMenuItem menuItemGoTo = new JMenuItem("Go to Page...");
		menuItemGoTo.setEnabled(pageable);
		menuItemGoTo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new GoToPageDialog();
			}
		});
		navigateMenu.add(menuItemGoTo);
		JMenuItem menuItemBeginning = new JMenuItem("Beginning");
		menuItemBeginning.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Controller.getInstance().getCurrentView().getBookView()
						.goToBeginning();
			}
		});
		navigateMenu.add(menuItemBeginning);
		JMenuItem menuItemEnd = new JMenuItem("End");
		menuItemEnd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Controller.getInstance().getCurrentView().getBookView()
						.goToEnd();
			}
		});
		navigateMenu.add(menuItemEnd);

		this.add(navigateMenu);

		// Create the Tools Menu
		JMenu toolsMenu = new JMenu("Bookmarks");
		JMenuItem menuItemAddBookmark = new JMenuItem("Add Bookmark");
		menuItemAddBookmark.addActionListener(new AddBookmarkListener());
		toolsMenu.add(menuItemAddBookmark);
		JMenuItem menuItemViewBookmark = new JMenuItem("View Bookmarks");
		menuItemViewBookmark.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new BookmarksMenuDialog();
			}
		});
		toolsMenu.add(menuItemViewBookmark);
		this.add(toolsMenu);

		// Create the Help Menu
		JMenu helpMenu = new JMenu("Help");
		JMenuItem menuItemHelp = new JMenuItem("Help");
		menuItemHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new HelpDialog();
			}			
		});
		helpMenu.add(menuItemHelp);
		JMenuItem menuItemAbout = new JMenuItem("About");
		menuItemAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new AboutDialog();
			}
		});
		helpMenu.add(menuItemAbout);

		this.add(helpMenu);

		// Add menu items that observe the pageable enable/disable
		observingPagedMenuItems.add(menuItemGoTo);
		observingScrollMenuItems.add(menuItemDisplayOptions);
	}

	private class AddBookmarkListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			BookmarkManager.getInstance().createBookmarkDialog();
		}
	}

	@Override
	public void update(Observable obs, Object obj) {
		if (obs instanceof MainWindowView) {
			pageable = (boolean) obj;
			for (JMenuItem menuItem : observingPagedMenuItems)
				menuItem.setEnabled(pageable);
			for (JMenuItem menuItem : observingScrollMenuItems)
				menuItem.setEnabled(!pageable);
		}
	}

}

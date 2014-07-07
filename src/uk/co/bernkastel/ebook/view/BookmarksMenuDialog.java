package uk.co.bernkastel.ebook.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

import uk.co.bernkastel.ebook.controller.BookManager;
import uk.co.bernkastel.ebook.controller.BookmarkManager;
import uk.co.bernkastel.ebook.controller.Controller;
import uk.co.bernkastel.ebook.model.Bookmark;

/**
 * BookmarksMenu is the dialog responsible for user interaction with saved
 * bookmarks. Allows for the editing and deletion of bookmarks. Also lets the
 * user select a bookmark and go to the text that bookmark is referring to.
 * 
 * @author racha_000
 * 
 */
public class BookmarksMenuDialog extends JDialog implements Observer {

	private static final long serialVersionUID = 1L;
	
	private final JPanel scrollContentPanel = new JPanel();
	private JScrollPane scrollPane;
	
	private List<JButton> enabledButtons = new ArrayList<JButton>();
	private List<BookmarkItemPanel> bookmarkPanels = new ArrayList<BookmarkItemPanel>();
	
	public static final int SCROLL_CONTENT_WIDTH_DIFF = 42;
	public static final int SCROLL_CONTENT_HEIGHT_DIFF = 109;
	public static final int WINDOW_INIT_WIDTH = 350;
	public static final int WINDOW_INIT_HEIGHT = 455;

	/**
	 * Create the dialog.
	 */
	public BookmarksMenuDialog() {
		List<Bookmark> bookmarks = BookManager.getInstance().getBook()
				.getBookmarks();
		if (!bookmarks.isEmpty()) {
			initMenu(bookmarks.size());
			createButtons();
			createBookmarkPanels(bookmarks);
			setVisible(true);
		} else {
			MessageDialog.showInfoDialog("This book has no bookmarks saved.");
		}
	}

	/**
	 * Initializes the menu screen.
	 * 
	 * @param numberOfBookmarks
	 *            The number of bookmarks to begin displaying. This is required
	 *            for the calculation of the scroll area size.
	 */
	private void initMenu(int numberOfBookmarks) {
		/*
		 * Set basic window properties
		 */
		JPanel contentPanel = new JPanel();
		setTitle("Bookmarks");
		setBounds(100, 100, WINDOW_INIT_WIDTH, WINDOW_INIT_HEIGHT);
		setMinimumSize(new Dimension(WINDOW_INIT_WIDTH, WINDOW_INIT_HEIGHT));

		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 0, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));

		/*
		 * Size the scroll area correctly
		 */
		int scrollWidth = WINDOW_INIT_WIDTH - SCROLL_CONTENT_WIDTH_DIFF;
		int scrollHeight = numberOfBookmarks
				* (5 + BookmarkItemPanel.BOOKMARK_ITEM_HEIGHT);
		Dimension scrollDi = new Dimension(scrollWidth, scrollHeight);
		scrollContentPanel.setPreferredSize(scrollDi);
		scrollContentPanel.setMaximumSize(scrollDi);

		/*
		 * Create user interface panels
		 */
		JPanel titlePanel = new JPanel();
		contentPanel.add(titlePanel, BorderLayout.NORTH);

		JLabel lblSelectABookmark = new JLabel(
				"Select a bookmark by clicking on its name below:");
		titlePanel.add(lblSelectABookmark);

		scrollPane = new JScrollPane(scrollContentPanel);
		scrollPane
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		contentPanel.add(scrollPane);

		// Add the resize listener
		addComponentListener(new ResizeListener());
	}

	/**
	 * Initialise and create the buttons for the menu.
	 */
	private void createButtons() {
		// Create the button panel
		JPanel buttonPane = new JPanel();
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		buttonPane.setLayout(new BorderLayout(0, 0));

		// Create the panel for the left hand edit/delete buttons
		JPanel editButtonPanel = new JPanel();
		buttonPane.add(editButtonPanel, BorderLayout.WEST);

		// Create the panel for the right hand ok/cancel buttons
		JPanel confirmationButtonPanel = new JPanel();
		buttonPane.add(confirmationButtonPanel, BorderLayout.EAST);

		// Create the delete button
		JButton btnDelete = new JButton("Delete");
		enabledButtons.add(btnDelete);
		editButtonPanel.add(btnDelete);
		btnDelete.setEnabled(false);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				BookmarkManager.getInstance().deleteBookmark(
						getSelectedBookmarkItemPanel().getBookmark());
				refreshBookmarkList();
			}
		});

		// Create the edit button
		JButton btnEdit = new JButton("Edit");
		enabledButtons.add(btnEdit);
		btnEdit.setEnabled(false);
		editButtonPanel.add(btnEdit);
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				BookmarkItemPanel selected = getSelectedBookmarkItemPanel();
				String name = JOptionPane.showInputDialog(null,
						"Rename your bookmark:", selected.getBookmark()
								.getName());
				selected.setName(name);
				selected.getPanel().revalidate();
				BookmarkManager.getInstance().saveBookmarks();
			}
		});

		// Create the Go button
		JButton btnGo = new JButton("Go");
		enabledButtons.add(btnGo);
		confirmationButtonPanel.add(btnGo);
		btnGo.setEnabled(false);
		btnGo.setActionCommand("OK");
		btnGo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Bookmark bookmark = getSelectedBookmarkItemPanel()
						.getBookmark();
				Controller.getInstance().getCurrentView().getBookView()
						.goToLocation(bookmark.getStartLocation());
			}
		});

		// Create the cancel button
		JButton cancelButton = new JButton("Cancel");
		confirmationButtonPanel.add(cancelButton);
		cancelButton.setActionCommand("Cancel");
		getRootPane().setDefaultButton(cancelButton);
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
	}

	/**
	 * Enables or disables buttons registered in the enabledButtons list.
	 * 
	 * @param enable
	 *            If the buttons should be enabled or disabled.
	 */
	private void enableButtons(boolean enable) {
		for (JButton button : enabledButtons)
			button.setEnabled(enable);
		revalidate();
	}

	/**
	 * Is called when the dialog resizes. Calculates the correct sizing of the
	 * scroll area and the bookmark items and redraws them correctly.
	 */
	private void resize() {
		int scrollWidth = getWidth() - SCROLL_CONTENT_WIDTH_DIFF;
		int scrollHeight = getBookmarksPanelHeight();
		scrollContentPanel.setPreferredSize(new Dimension(scrollWidth,
				scrollHeight));

		Dimension bookmarkDi = new Dimension(getWidth()
				- BookmarkItemPanel.BOOKMARK_ITEM_WIDTH_DIFF,
				BookmarkItemPanel.BOOKMARK_ITEM_HEIGHT);
		for (BookmarkItemPanel b : bookmarkPanels)
			b.getPanel().setPreferredSize(bookmarkDi);

		scrollPane.getViewport().setViewSize(
				new Dimension(getWidth() - 35, getHeight() - 109));
		revalidate();
		repaint();
	}

	/**
	 * Returns the correct height of the bookmark scroll panel given the number
	 * of bookmarks currently in the bookmark panel list.
	 * 
	 * @return The correct height of the bookmark scroll panel. Returns 0 if no
	 *         bookmarks.
	 */
	private int getBookmarksPanelHeight() {
		if (!bookmarkPanels.isEmpty()) {
			return (bookmarkPanels.size() * (5 + BookmarkItemPanel.BOOKMARK_ITEM_HEIGHT)) + 5;
		}
		return 0;
	}

	/**
	 * Recreates the bookmark list and clears then repopulates the scroll panel
	 * containing them. Used when deleting a bookmark from the list.
	 */
	private void refreshBookmarkList() {
		// Remove bookmarks from the UI and the list
		scrollContentPanel.removeAll();
		bookmarkPanels.clear();

		// Create the panels, they are added to the bookmarkPanels list in the
		// createBookmarkPanels method.
		createBookmarkPanels(BookManager.getInstance().getBook().getBookmarks());

		// Set buttons to disabled as no bookmark is selected after refresh
		enableButtons(false);

		// Calculate the new height of the scroll area.
		int scrollWidth = getWidth() - SCROLL_CONTENT_WIDTH_DIFF;
		int scrollHeight = getBookmarksPanelHeight();
		scrollContentPanel.setPreferredSize(new Dimension(scrollWidth,
				scrollHeight));

		// Redraw the UI
		revalidate();
		repaint();
	}

	/**
	 * Creates bookmark panels from the given list of bookmarks. Also adds them
	 * to the bookmark panel list for this menu.
	 * 
	 * @param bookmarks
	 *            The list of bookmarks to create BookmarkItemPanels for.
	 */
	private void createBookmarkPanels(List<Bookmark> bookmarks) {
		for (Bookmark bm : bookmarks) {
			BookmarkItemPanel item = new BookmarkItemPanel(bm);
			item.addObserver(this);
			scrollContentPanel.add(item.getPanel());
			bookmarkPanels.add(item);
		}
	}

	/**
	 * Determines if a bookmark is currently selected.
	 * 
	 * @return
	 */
	private boolean isBookmarkSelected() {
		for (BookmarkItemPanel panel : bookmarkPanels)
			if (panel.isSelected())
				return true;
		return false;
	}

	/**
	 * Returns the currently selected BookmarkItemPanel. Returns null if none
	 * selected.
	 * 
	 * @return
	 */
	private BookmarkItemPanel getSelectedBookmarkItemPanel() {
		for (BookmarkItemPanel panel : bookmarkPanels)
			if (panel.isSelected())
				return panel;
		return null;
	}

	/**
	 * Resize listener for the menu.
	 * 
	 * @author racha_000
	 * 
	 */
	private class ResizeListener extends ComponentAdapter {
		public void componentResized(ComponentEvent e) {
			resize();
		}
	}

	@Override
	public void update(Observable obs, Object obj) {
		if (obs instanceof BookmarkItemPanel) {
			BookmarkItemPanel item = (BookmarkItemPanel) obs;
			boolean selected = (boolean) obj;
			if (selected)
				for (BookmarkItemPanel bip : bookmarkPanels)
					if (!bip.equals(item))
						bip.setSelected(false);
			enableButtons(isBookmarkSelected());
		}
	}
}

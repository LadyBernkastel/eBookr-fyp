package uk.co.bernkastel.ebook.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.JSplitPane;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.SwingConstants;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HelpDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private final JTextPane textPane;


	/**
	 * Create the dialog.
	 */
	public HelpDialog() {
		setResizable(false);
		setBounds(100, 100, 718, 656);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JSplitPane splitPane = new JSplitPane();
			contentPanel.add(splitPane);
			{
				JScrollPane scrollPane = new JScrollPane();
				splitPane.setLeftComponent(scrollPane);
				{
					JPanel panel = new JPanel();
					panel.setBorder(new EmptyBorder(0, 10, 0, 10));
					scrollPane.setViewportView(panel);
					GridBagLayout gbl_panel = new GridBagLayout();
					gbl_panel.columnWidths = new int[] { 230, 0 };
					gbl_panel.rowHeights = new int[] { 30, 69, 30, 240, 30, 0,
							0 };
					gbl_panel.columnWeights = new double[] { 1.0,
							Double.MIN_VALUE };
					gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0,
							0.0, 1.0, Double.MIN_VALUE };
					panel.setLayout(gbl_panel);
					{
						JLabel lblApplication = new JLabel("Application");
						lblApplication
								.setFont(new Font("Dialog", Font.BOLD, 12));
						GridBagConstraints gbc_lblApplication = new GridBagConstraints();
						gbc_lblApplication.fill = GridBagConstraints.VERTICAL;
						gbc_lblApplication.insets = new Insets(0, 0, 5, 0);
						gbc_lblApplication.gridx = 0;
						gbc_lblApplication.gridy = 0;
						panel.add(lblApplication, gbc_lblApplication);
					}
					{
						JPanel applicationPanel = new JPanel();
						applicationPanel.setBorder(null);
						GridBagConstraints gbc_applicationPanel = new GridBagConstraints();
						gbc_applicationPanel.fill = GridBagConstraints.BOTH;
						gbc_applicationPanel.insets = new Insets(0, 0, 5, 0);
						gbc_applicationPanel.gridx = 0;
						gbc_applicationPanel.gridy = 1;
						panel.add(applicationPanel, gbc_applicationPanel);
						applicationPanel.setLayout(new GridLayout(0, 1, 0, 0));

						applicationPanel
								.add(createHelpLabel(
										"What does eBookr require to run?",
										"eBookr requires Java 1.7 to be installed on your PC to run."));

						applicationPanel.add(createHelpLabel(
								"What platforms does eBookr support?",
								"Windows XP/Vista/7 and 8, Linux, Mac OSX."));

						applicationPanel
								.add(createHelpLabel(
										"Who developed eBookr?",
										"eBookr was developed by Rachael Smith at the University of Salford for a Final Year Project. You can find her at http://bernkastel.co.uk."));

						JLabel lblBooks = new JLabel("Books");
						lblBooks.setFont(new Font("Dialog", Font.BOLD, 12));
						GridBagConstraints gbc_lblBooks = new GridBagConstraints();
						gbc_lblBooks.fill = GridBagConstraints.VERTICAL;
						gbc_lblBooks.insets = new Insets(0, 0, 5, 0);
						gbc_lblBooks.gridx = 0;
						gbc_lblBooks.gridy = 2;
						panel.add(lblBooks, gbc_lblBooks);

						JPanel booksPanel = new JPanel();
						booksPanel.setBorder(null);
						GridBagConstraints gbc_booksPanel = new GridBagConstraints();
						gbc_booksPanel.insets = new Insets(0, 0, 5, 0);
						gbc_booksPanel.fill = GridBagConstraints.BOTH;
						gbc_booksPanel.gridx = 0;
						gbc_booksPanel.gridy = 3;
						panel.add(booksPanel, gbc_booksPanel);
						booksPanel.setLayout(new GridLayout(0, 1, 0, 0));

						booksPanel.add(createHelpLabel(
								"What book formats does eBookr support?",
								"HTML, Epub and PDF files."));

						booksPanel
								.add(createHelpLabel(
										"How do I navigate in a book?",
										"If the book that you’re trying to view has a scrollbar on it, then you can use the scroll wheel on your mouse, or the scrollbar, to navigate up and down the book. If the book has buttons on the left and right, you can use these to go forwards and backwards in the book."));

						booksPanel
								.add(createHelpLabel(
										"How do I go to a specific page in a book?",
										"Click Navigate on the top menu bar and click the ‘Go to Page…’ option. If this is grey, you can use the Beginning and End options to move to those sections of the book you are currently reading."));

						booksPanel
								.add(createHelpLabel(
										"How do I change how my book appears?",
										"You can change the font, size and colour of the reader by going to View > Display Options. In this menu you can change your settings or reset it to the default."));

						booksPanel
								.add(createHelpLabel(
										"Why am I asked when to apply display settings?",
										"To change how your book looks, eBookr needs to re-open it. This means losing your current reading position. If you don’t want to re-open your book now, you can ask it to do it the next time a book is opened. You can also set a default setting for this in the File > Preferences menu."));

						booksPanel
								.add(createHelpLabel(
										"Why is my PDF not displaying correctly?",
										"The library used to read PDF files by eBookr appears to have issues with some types of PDF files. At the moment, there is nothing that can be done to fix this, however it is an issue the developer is aware of."));

						booksPanel
								.add(createHelpLabel(
										"How do I clear the list of recently opened books?",
										"You can do this in the File > Preferences menu. Simply click on the Book tab and click the ‘Clear Recently Opened books list’ button."));

						booksPanel
								.add(createHelpLabel(
										"How do I view the properties of a book?",
										"Click the View option on the top menu bar and click ‘Book Properties’ to view metadata/information about the book. eBookr doesn’t maintain a comprehensive list of this information so there may be missing/incorrect information."));

						booksPanel
								.add(createHelpLabel(
										"Images aren\u2019t loading correctly in my book.",
										"This is because the image has not been extracted properly from the book or it isn’t where the book says it should be. Unfortunately there is no fix for this at this time."));

						booksPanel
								.add(createHelpLabel(
										"Does eBookr support embedded media in Epubs?",
										"Not at this time. eBookr supports images embedded in Epub files, however it doesn’t support any other media types."));

					}
					{
						JLabel lblBookmarks = new JLabel("Bookmarks");
						lblBookmarks.setFont(new Font("Dialog", Font.BOLD, 12));
						GridBagConstraints gbc_lblBookmarks = new GridBagConstraints();
						gbc_lblBookmarks.insets = new Insets(0, 0, 5, 0);
						gbc_lblBookmarks.gridx = 0;
						gbc_lblBookmarks.gridy = 4;
						panel.add(lblBookmarks, gbc_lblBookmarks);
					}
					{
						JPanel bookmarksPanel = new JPanel();
						bookmarksPanel.setBorder(null);
						GridBagConstraints gbc_bookmarksPanel = new GridBagConstraints();
						gbc_bookmarksPanel.fill = GridBagConstraints.VERTICAL;
						gbc_bookmarksPanel.anchor = GridBagConstraints.WEST;
						gbc_bookmarksPanel.gridx = 0;
						gbc_bookmarksPanel.gridy = 5;
						panel.add(bookmarksPanel, gbc_bookmarksPanel);
						bookmarksPanel.setLayout(new GridLayout(0, 1, 0, 0));

						bookmarksPanel
								.add(createHelpLabel(
										"How do I add a bookmark to my book?",
										"Click on the text you would like to bookmark the point of, or select it using your cursor, and either right click and select ‘Add Bookmark’ or go to Tools > Add Bookmark. You will then be prompted to add a name to the bookmark – (this is not required)."));

						bookmarksPanel
								.add(createHelpLabel(
										"How do I view saved bookmarks?",
										"Go to Tools > View Bookmarks to access the bookmarks list."));

						bookmarksPanel
								.add(createHelpLabel(
										"Navigating using a Bookmark",
										"When in the Bookmarks List (Tools > View Bookmarks), click a bookmark that you wish to navigate to. The selected bookmark will highlight with a white background. Then click the Go button and you will be taken to the location of the bookmark in that book."));

						bookmarksPanel
								.add(createHelpLabel(
										"Deleting a Bookmark",
										"When in the Bookmarks List (Tools > View), click a bookmark to select it. Then click the Delete button and it will be removed from the list."));

						bookmarksPanel
								.add(createHelpLabel(
										"Editing a Bookmark",
										"You can edit the name of bookmark in the Bookmarks List (Tools > View) by clicking the bookmark you wish to edit then clicking the edit button and typing the new name for the bookmark. You cannot edit the location a bookmark refers to in a book."));

						bookmarksPanel
								.add(createHelpLabel(
										"Where are bookmarks stored?",
										"By default, bookmarks are stored in a directory called .ebookr in your user home directory. If you wish to change the location, go to File > Preferences and use the Browse button to select a new folder. Make sure to save the changed location before leaving the menu."));
						;
						bookmarksPanel
								.add(createHelpLabel(
										"My bookmarks have disappeared!",
										"This could be caused by 3 things:\n\n"
										+ "1. The bookmark save location has been changed and the old bookmarks were not copied over. To fix this, go to the location your bookmarks were previously saved at in your file browser and copy all the files ending in .ser into the current bookmarks folder.\n\n"
										+ "2. The book you are reading has been changed. To make sure that bookmarks don’t break if a book is changed, they will only work with the exact book they were created for. If a newer version is released or edits to the content of the same file are made, the bookmarks will no longer apply. There is no way to apply the bookmarks to the new file.\n\n"
										+ "3. The bookmarks have been deleted from the file system. This is not something eBookr can fix, however if you are able to restore them then you can copy them into the current bookmarks folder and they should work."));

					}
				}
			}
			{
				JScrollPane scrollPane = new JScrollPane();
				splitPane.setRightComponent(scrollPane);
				{
					textPane = new JTextPane();
					textPane.setEditable(false);
					textPane.setBorder(new MatteBorder(5, 10, 5, 10, Color.WHITE));
					scrollPane.setViewportView(textPane);
				}
			}
		}
		{
			JLabel lblHelp = new JLabel("eBookr Help Pages");
			lblHelp.setHorizontalAlignment(SwingConstants.CENTER);
			lblHelp.setFont(new Font("Tahoma", Font.BOLD, 16));
			contentPanel.add(lblHelp, BorderLayout.NORTH);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				okButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
					
				});
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
		
		setVisible(true);
	}

	private JLabel createHelpLabel(String question, String answer) {
		JLabel label = new JLabel(question);
		label.setFont(new Font("Dialog", Font.PLAIN, 12));
		label.addMouseListener(new HelpLabelMouseAdapter(answer));
		return label;
	}

	private class HelpLabelMouseAdapter extends MouseAdapter {
		private String answer;

		public HelpLabelMouseAdapter(String answer) {
			this.answer = answer;
		}

		@Override
		public void mouseClicked(MouseEvent arg0) {
			textPane.setText(answer);
		}
	}

}

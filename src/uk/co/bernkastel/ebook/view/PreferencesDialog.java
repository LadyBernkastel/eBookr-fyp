package uk.co.bernkastel.ebook.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.border.EmptyBorder;

import uk.co.bernkastel.ebook.controller.BookManager;
import uk.co.bernkastel.ebook.controller.BookmarkManager;
import uk.co.bernkastel.ebook.model.prefs.ApplicationPreferences;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

import uk.co.bernkastel.ebook.model.prefs.DateSaveFormat;

public class PreferencesDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	private final ApplicationPreferences prefs = new ApplicationPreferences();
	private final HashMap<String, JToggleButton> buttons = new HashMap<String, JToggleButton>();

	private final ButtonGroup askMeGroup = new ButtonGroup();
	private final ButtonGroup refreshGroup = new ButtonGroup();
	private JTextField textBxUACSaveLoc;
	private JComboBox<String> comboBox;


	/**
	 * Create the dialog.
	 */
	public PreferencesDialog() {
		initializeContentPane();
		createApplicationPane();
		createBookPane();
		createDisplayPane();
		createButtonPane();
		loadPreferences();
		setVisible(true);
	}

	/**
	 * Initializes the basic settings of the content panel and dialog.
	 */
	private void initializeContentPane() {
		setTitle("Preferences");
		setBounds(100, 100, 431, 286);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));

		tabbedPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		contentPanel.add(tabbedPane);

		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}

	/**
	 * Creates the UI within the Application tab
	 */
	private void createApplicationPane() {
		JPanel appPanel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) appPanel.getLayout();
		flowLayout.setHgap(10);
		flowLayout.setAlignment(FlowLayout.LEFT);
		tabbedPane.addTab("Application", null, appPanel, null);

		JLabel lblSaveBookmarksTo = new JLabel(
				"Save bookmarks & recent books list to folder:");
		appPanel.add(lblSaveBookmarksTo);

		textBxUACSaveLoc = new JTextField();
		textBxUACSaveLoc.setEditable(false);
		appPanel.add(textBxUACSaveLoc);
		textBxUACSaveLoc.setColumns(25);

		JButton btnBrowse = new JButton("Browse");
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createStateSaveBrowseDialog();
			}
		});
		appPanel.add(btnBrowse);

		JLabel lblDateFormatTo = new JLabel("Date format to use:");
		appPanel.add(lblDateFormatTo);

		// Get the list of format names
		String[] formatList = new String[DateSaveFormat.values().length];
		int i = 0;
		for (DateSaveFormat f : DateSaveFormat.values())
			formatList[i++] = f.getDisplay();

		comboBox = new JComboBox<String>();
		comboBox.setModel(new DefaultComboBoxModel<String>(formatList));
		appPanel.add(comboBox);
	}

	/**
	 * Creates the UI within the Book tab
	 */
	private void createBookPane() {
		JPanel bookPanel = new JPanel();
		FlowLayout bookFlowLayout = (FlowLayout) bookPanel.getLayout();
		bookFlowLayout.setVgap(10);
		bookFlowLayout.setHgap(10);
		bookFlowLayout.setAlignment(FlowLayout.LEFT);
		tabbedPane.addTab("Book", null, bookPanel, null);

		JButton btnClearRecentlyOpened = new JButton(
				"Clear Recently Opened books list");
		btnClearRecentlyOpened.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				BookManager.getInstance().getPreviousBooks().clear();
			}
		});
		bookPanel.add(btnClearRecentlyOpened);

		JCheckBox chckbxSavePreviousBooks = new JCheckBox(
				"Save a list of previously opened books");
		chckbxSavePreviousBooks.setSelected(true);
		bookPanel.add(chckbxSavePreviousBooks);
		buttons.put(ApplicationPreferences.KEY_SAVE_PREVIOUS_BOOKS,
				chckbxSavePreviousBooks);

		final JCheckBox chckbxAutomaticallyOpenLast = new JCheckBox(
				"Automatically open last opened book");
		bookPanel.add(chckbxAutomaticallyOpenLast);
		buttons.put(ApplicationPreferences.KEY_AUOTMATICALLY_OPEN_BOOK,
				chckbxAutomaticallyOpenLast);

		final JCheckBox chckbxOpenToPrevious = new JCheckBox(
				"Open to previous location");
		bookPanel.add(chckbxOpenToPrevious);
		buttons.put(ApplicationPreferences.KEY_OPEN_PREVIOUS_LOC,
				chckbxOpenToPrevious);

		chckbxAutomaticallyOpenLast.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				chckbxOpenToPrevious.setEnabled(chckbxAutomaticallyOpenLast
						.isSelected());
			}
		});
	}

	/**
	 * Creates the UI within the Display tab
	 */
	private void createDisplayPane() {
		JPanel displayPanel = new JPanel();
		displayPanel.setBorder(new EmptyBorder(5, 0, 0, 0));
		FlowLayout displayFlowLayout = (FlowLayout) displayPanel.getLayout();
		displayFlowLayout.setHgap(10);
		displayFlowLayout.setAlignment(FlowLayout.LEFT);
		tabbedPane.addTab("Reader Display", null, displayPanel, null);

		JButton btnChangeDisplayPreferences = new JButton(
				"Change Display Preferences");
		btnChangeDisplayPreferences.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new DisplayOptionsDialog();
			}
		});
		displayPanel.add(btnChangeDisplayPreferences);

		final JRadioButton rdbtnAskMeWhether = new JRadioButton(
				"Ask me when to make display changes");
		rdbtnAskMeWhether.setSelected(true);
		displayPanel.add(rdbtnAskMeWhether);
		buttons.put(ApplicationPreferences.KEY_DO_NOT_ASK_REFRESH_DISPLAY
				+ "false", rdbtnAskMeWhether);

		JRadioButton rdbtnAlwaysDoThis = new JRadioButton(
				"Always make display changes:");
		displayPanel.add(rdbtnAlwaysDoThis);
		buttons.put(ApplicationPreferences.KEY_DO_NOT_ASK_REFRESH_DISPLAY
				+ "true", rdbtnAlwaysDoThis);

		askMeGroup.add(rdbtnAskMeWhether);
		askMeGroup.add(rdbtnAlwaysDoThis);

		JPanel dispPrefChoicePanel = new JPanel();
		dispPrefChoicePanel.setBorder(new EmptyBorder(0, 20, 0, 0));
		displayPanel.add(dispPrefChoicePanel);
		dispPrefChoicePanel.setLayout(new GridLayout(0, 1, 0, 0));

		final JRadioButton rdbtnImmediatelyReopens = new JRadioButton(
				"Immediately - Re-opens book to the beginning");
		dispPrefChoicePanel.add(rdbtnImmediatelyReopens);
		buttons.put(ApplicationPreferences.KEY_REFRESH_DISPLAY + "true",
				rdbtnImmediatelyReopens);

		final JRadioButton rdbtnLaterThe = new JRadioButton(
				"Later - The next time a book is opened");
		rdbtnLaterThe.setSelected(true);
		dispPrefChoicePanel.add(rdbtnLaterThe);
		buttons.put(ApplicationPreferences.KEY_REFRESH_DISPLAY + "false",
				rdbtnLaterThe);

		refreshGroup.add(rdbtnImmediatelyReopens);
		refreshGroup.add(rdbtnLaterThe);

		/*
		 * Action Listeners
		 */
		rdbtnAskMeWhether.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// if the ask me whether choice is selected then set the timing
				// options to disabled
				rdbtnImmediatelyReopens.setEnabled(false);
				rdbtnLaterThe.setEnabled(false);
			}
		});
		rdbtnAlwaysDoThis.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// If the always do this choice is clicked then set the timing
				// options to enabled
				rdbtnImmediatelyReopens.setEnabled(true);
				rdbtnLaterThe.setEnabled(true);
			}
		});

	}

	/**
	 * Creates the button pane at the bottom of the dialog
	 */
	private void createButtonPane() {
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton btnResetDefaults = new JButton("Reset Defaults");
		btnResetDefaults.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				File src = new File(textBxUACSaveLoc.getText());
				prefs.clear();
				loadPreferences();
				File dest = new File(textBxUACSaveLoc.getText());
				askToMoveStateFiles(src, dest);
			}
		});
		buttonPane.add(btnResetDefaults);

		JButton okButton = new JButton("OK");
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				savePreferences();
				dispose();
			}
		});
		getRootPane().setDefaultButton(okButton);

		JButton btnApply = new JButton("Apply");
		btnApply.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				savePreferences();
				loadPreferences();
			}
		});
		buttonPane.add(btnApply);

		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);
	}

	/**
	 * Creates the dialog called when the user clicks the browse button to
	 * select a location to save state information in.
	 */
	private void createStateSaveBrowseDialog() {
		JFileChooser jfc = new JFileChooser();
		jfc.setDialogTitle("Select a Directory");
		jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		jfc.setAcceptAllFileFilterUsed(false);
		int result = jfc.showDialog(new JFrame(), "Select");
		if (result == JFileChooser.APPROVE_OPTION) {
			if (!jfc.getSelectedFile().getPath()
					.equals(textBxUACSaveLoc.getText())) {
				File src = new File(BookmarkManager.getInstance().getLocation());
				askToMoveStateFiles(src, jfc.getSelectedFile());
			}
			textBxUACSaveLoc.setText(jfc.getSelectedFile().getPath());
			prefs.setPreference(ApplicationPreferences.KEY_STATE_LOCATION,
					textBxUACSaveLoc.getText());
		}
	}

	/**
	 * Creates a dialog asking the user if they wish to move their
	 * state/bookmarks.
	 * 
	 * @param dest
	 *            The file destination to move the state from
	 * @param src
	 *            The file source that the state is being moved from.
	 */
	private void askToMoveStateFiles(File dest, File src) {
		int moveuac = JOptionPane
				.showConfirmDialog(
						null,
						"Do you wish to move your existing bookmarks & recent books list into this Directory? \n"
								+ "If you do not, you will loose any existing bookmarks & recent books.");

		if (moveuac == JOptionPane.OK_OPTION)
			BookmarkManager.getInstance().moveLocation(src, dest);
	}

	/**
	 * Loads in the already saved preferences for this dialog
	 */
	private void loadPreferences() {
		HashMap<String, String> prefsMap = prefs.getAllPreferences();
		for (String prefKey : prefsMap.keySet()) {
			if (buttons.containsKey(prefKey)) {
				JToggleButton button = buttons.get(prefKey);
				button.setSelected(Boolean.parseBoolean(prefsMap.get(prefKey)));
				fireActionListeners(button);
			}
		}

		String dateFormat = prefsMap
				.get(ApplicationPreferences.KEY_DATE_FORMAT);
		comboBox.setSelectedItem(dateFormat);

		String uacLocation = prefsMap
				.get(ApplicationPreferences.KEY_STATE_LOCATION);
		textBxUACSaveLoc.setText(uacLocation);
		if (!textBxUACSaveLoc.getText().equals(uacLocation))
			BookmarkManager.getInstance().moveLocation(new File(uacLocation));

		boolean askRefresh = Boolean.parseBoolean(prefsMap
				.get(ApplicationPreferences.KEY_DO_NOT_ASK_REFRESH_DISPLAY));
		askMeGroup.clearSelection();

		JToggleButton askButton;

		if (askRefresh)
			askButton = buttons
					.get(ApplicationPreferences.KEY_DO_NOT_ASK_REFRESH_DISPLAY
							+ "true");
		else
			askButton = buttons
					.get(ApplicationPreferences.KEY_DO_NOT_ASK_REFRESH_DISPLAY
							+ "false");

		fireActionListeners(askButton);
		askButton.setSelected(true);

		boolean refresh = Boolean.parseBoolean(prefsMap
				.get(ApplicationPreferences.KEY_REFRESH_DISPLAY));
		refreshGroup.clearSelection();
		JToggleButton refreshButton;
		if (refresh) {
			refreshButton = buttons
					.get(ApplicationPreferences.KEY_REFRESH_DISPLAY + "true");
		} else {
			refreshButton = buttons
					.get(ApplicationPreferences.KEY_REFRESH_DISPLAY + "false");
		}
		refreshButton.setSelected(true);
	}

	/**
	 * Saves the preferences currently set by the user in this dialog.
	 */
	private void savePreferences() {
		for (String buttonKey : buttons.keySet()) {
			if (prefs.getKeys().contains(buttonKey)) {
				boolean prefValue = buttons.get(buttonKey).isSelected();
				prefs.setPreference(buttonKey, String.valueOf(prefValue));
			}
		}

		prefs.setPreference(ApplicationPreferences.KEY_DATE_FORMAT, comboBox
				.getSelectedItem().toString());

		boolean askPref = buttons.get(
				ApplicationPreferences.KEY_DO_NOT_ASK_REFRESH_DISPLAY + "true")
				.isSelected();
		prefs.setPreference(
				ApplicationPreferences.KEY_DO_NOT_ASK_REFRESH_DISPLAY,
				String.valueOf(askPref));

		boolean refreshPref = buttons.get(
				ApplicationPreferences.KEY_REFRESH_DISPLAY + "true")
				.isSelected();
		prefs.setPreference(ApplicationPreferences.KEY_REFRESH_DISPLAY,
				String.valueOf(refreshPref));

		prefs.setPreference(ApplicationPreferences.KEY_STATE_LOCATION,
				textBxUACSaveLoc.getText());
	}

	/**
	 * Fires the action listener on the given button with a null action.
	 * 
	 * @param button
	 *            The button to fire the action listeners of.
	 */
	private void fireActionListeners(JToggleButton button) {
		for (ActionListener listener : button.getActionListeners())
			listener.actionPerformed(null);
	}

}

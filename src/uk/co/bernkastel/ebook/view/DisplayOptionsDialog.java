package uk.co.bernkastel.ebook.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import uk.co.bernkastel.ebook.controller.Controller;
import uk.co.bernkastel.ebook.model.prefs.ApplicationPreferences;
import uk.co.bernkastel.ebook.model.prefs.DisplayPreferences;
import uk.co.bernkastel.ebook.model.prefs.ReaderTextSize;
import uk.co.bernkastel.ebook.model.prefs.ReaderTextStyle;
import uk.co.bernkastel.ebook.model.prefs.ReaderTheme;

public class DisplayOptionsDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private DisplayPreferences displPrefs = new DisplayPreferences();
	private ApplicationPreferences appPrefs = new ApplicationPreferences();

	private JComboBox<String> comboBox;
	private ButtonGroup sizeButtons = new ButtonGroup();
	private ButtonGroup fontButtons = new ButtonGroup();
	private List<JRadioButton> radioButtons = new ArrayList<JRadioButton>();


	/**
	 * Create the dialog.
	 */
	public DisplayOptionsDialog() {
		setTitle("Reader Display Options");
		setBounds(100, 100, 450, 245);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[] { 108, 105, 67, 109, 0 };
		gbl_contentPanel.rowHeights = new int[] { 20, 14, 37, 14, 27, 0 };
		gbl_contentPanel.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_contentPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		contentPanel.setLayout(gbl_contentPanel);

		/*
		 * Theme
		 */
		JLabel lblReaderColourTheme = new JLabel("Reader Colour Theme:");
		GridBagConstraints gbc_lblReaderColourTheme = new GridBagConstraints();
		gbc_lblReaderColourTheme.anchor = GridBagConstraints.EAST;
		gbc_lblReaderColourTheme.insets = new Insets(0, 0, 5, 5);
		gbc_lblReaderColourTheme.gridx = 0;
		gbc_lblReaderColourTheme.gridy = 0;
		contentPanel.add(lblReaderColourTheme, gbc_lblReaderColourTheme);

		comboBox = new JComboBox<String>(getReaderThemeNames());
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.anchor = GridBagConstraints.NORTH;
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 0;

		contentPanel.add(comboBox, gbc_comboBox);

		/*
		 * Text Size
		 */
		JLabel lblReaderTextSize = new JLabel("Reader Text Size:");
		GridBagConstraints gbc_lblReaderTextSize = new GridBagConstraints();
		gbc_lblReaderTextSize.anchor = GridBagConstraints.NORTH;
		gbc_lblReaderTextSize.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblReaderTextSize.insets = new Insets(0, 0, 5, 5);
		gbc_lblReaderTextSize.gridx = 0;
		gbc_lblReaderTextSize.gridy = 1;
		contentPanel.add(lblReaderTextSize, gbc_lblReaderTextSize);

		JRadioButton rdbtnSmall = new JRadioButton(
				ReaderTextSize.SMALL.getName());
		radioButtons.add(rdbtnSmall);
		rdbtnSmall.setFont(new Font("Tahoma", Font.PLAIN, 9));
		GridBagConstraints gbc_rdbtnSmall = new GridBagConstraints();
		gbc_rdbtnSmall.fill = GridBagConstraints.HORIZONTAL;
		gbc_rdbtnSmall.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnSmall.gridx = 0;
		gbc_rdbtnSmall.gridy = 2;
		contentPanel.add(rdbtnSmall, gbc_rdbtnSmall);
		sizeButtons.add(rdbtnSmall);

		JRadioButton rdbtnMedium = new JRadioButton(
				ReaderTextSize.MEDIUM.getName());
		radioButtons.add(rdbtnMedium);
		rdbtnMedium.setFont(new Font("Tahoma", Font.PLAIN, 12));
		GridBagConstraints gbc_rdbtnMedium = new GridBagConstraints();
		gbc_rdbtnMedium.fill = GridBagConstraints.HORIZONTAL;
		gbc_rdbtnMedium.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnMedium.gridx = 1;
		gbc_rdbtnMedium.gridy = 2;
		contentPanel.add(rdbtnMedium, gbc_rdbtnMedium);
		sizeButtons.add(rdbtnMedium);

		JRadioButton rdbtnLarge = new JRadioButton(
				ReaderTextSize.LARGE.getName());
		radioButtons.add(rdbtnLarge);
		rdbtnLarge.setFont(new Font("Tahoma", Font.PLAIN, 16));
		GridBagConstraints gbc_rdbtnLarge = new GridBagConstraints();
		gbc_rdbtnLarge.fill = GridBagConstraints.HORIZONTAL;
		gbc_rdbtnLarge.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnLarge.gridx = 2;
		gbc_rdbtnLarge.gridy = 2;
		contentPanel.add(rdbtnLarge, gbc_rdbtnLarge);
		sizeButtons.add(rdbtnLarge);

		JRadioButton rdbtnExtraLarge = new JRadioButton(
				ReaderTextSize.EXTRA_LARGE.getName());
		radioButtons.add(rdbtnExtraLarge);
		rdbtnExtraLarge.setFont(new Font("Tahoma", Font.PLAIN, 24));
		GridBagConstraints gbc_rdbtnExtraLarge = new GridBagConstraints();
		gbc_rdbtnExtraLarge.anchor = GridBagConstraints.NORTHWEST;
		gbc_rdbtnExtraLarge.insets = new Insets(0, 0, 5, 0);
		gbc_rdbtnExtraLarge.gridx = 3;
		gbc_rdbtnExtraLarge.gridy = 2;
		contentPanel.add(rdbtnExtraLarge, gbc_rdbtnExtraLarge);
		sizeButtons.add(rdbtnExtraLarge);

		/*
		 * Font style
		 */
		JLabel lblReaderTextStyle = new JLabel("Reader Text Style:");
		GridBagConstraints gbc_lblReaderTextStyle = new GridBagConstraints();
		gbc_lblReaderTextStyle.anchor = GridBagConstraints.NORTH;
		gbc_lblReaderTextStyle.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblReaderTextStyle.insets = new Insets(0, 0, 5, 5);
		gbc_lblReaderTextStyle.gridx = 0;
		gbc_lblReaderTextStyle.gridy = 3;
		contentPanel.add(lblReaderTextStyle, gbc_lblReaderTextStyle);

		JRadioButton rdbtnSerif = new JRadioButton(
				ReaderTextStyle.SERIF.getName());
		radioButtons.add(rdbtnSerif);
		rdbtnSerif.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		GridBagConstraints gbc_rdbtnSerif = new GridBagConstraints();
		gbc_rdbtnSerif.fill = GridBagConstraints.HORIZONTAL;
		gbc_rdbtnSerif.insets = new Insets(0, 0, 0, 5);
		gbc_rdbtnSerif.gridx = 0;
		gbc_rdbtnSerif.gridy = 4;
		contentPanel.add(rdbtnSerif, gbc_rdbtnSerif);
		fontButtons.add(rdbtnSerif);

		JRadioButton rdbtnSansserif = new JRadioButton(
				ReaderTextStyle.SANS_SERIF.getName());
		radioButtons.add(rdbtnSansserif);
		rdbtnSansserif.setFont(new Font("Tahoma", Font.PLAIN, 12));
		GridBagConstraints gbc_rdbtnSansserif = new GridBagConstraints();
		gbc_rdbtnSansserif.fill = GridBagConstraints.HORIZONTAL;
		gbc_rdbtnSansserif.insets = new Insets(0, 0, 0, 5);
		gbc_rdbtnSansserif.gridx = 1;
		gbc_rdbtnSansserif.gridy = 4;
		contentPanel.add(rdbtnSansserif, gbc_rdbtnSansserif);
		fontButtons.add(rdbtnSansserif);

		JRadioButton rdbtnCursive = new JRadioButton(
				ReaderTextStyle.CURSIVE.getName());
		radioButtons.add(rdbtnCursive);
		rdbtnCursive.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		GridBagConstraints gbc_rdbtnCursive = new GridBagConstraints();
		gbc_rdbtnCursive.anchor = GridBagConstraints.NORTHWEST;
		gbc_rdbtnCursive.insets = new Insets(0, 0, 0, 5);
		gbc_rdbtnCursive.gridx = 2;
		gbc_rdbtnCursive.gridy = 4;
		contentPanel.add(rdbtnCursive, gbc_rdbtnCursive);
		fontButtons.add(rdbtnCursive);

		loadPreferences();

		/*
		 * Buttons
		 */
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton btnResetDefaults = new JButton("Reset Defaults");
		buttonPane.add(btnResetDefaults);
		btnResetDefaults.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				displPrefs.clear();
				loadPreferences();
			}
		});

		JButton okButton = new JButton("OK");
		okButton.setActionCommand("OK");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				savePreferences();
				dispose();
			}
		});
		getRootPane().setDefaultButton(okButton);
		buttonPane.add(okButton);

		JButton btnApply = new JButton("Apply");
		btnApply.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				savePreferences();
				loadPreferences();
			}
		});
		buttonPane.add(btnApply);

		JButton cancelButton = new JButton("Cancel");
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	private void savePreferences() {
		String theme = comboBox.getSelectedItem().toString().toUpperCase();
		displPrefs.setReaderTheme(ReaderTheme.valueOf(theme));

		String sizeStr = getRadioButtonContent(sizeButtons.getSelection());
		ReaderTextSize size = null;
		for (ReaderTextSize rts : ReaderTextSize.values())
			if (sizeStr.equals(rts.getName()))
				size = rts;
		displPrefs.setTextSize(size);

		String styleStr = getRadioButtonContent(fontButtons.getSelection());
		ReaderTextStyle style = null;
		for (ReaderTextStyle rts : ReaderTextStyle.values())
			if (styleStr.equals(rts.getName()))
				style = rts;
		displPrefs.setTextStyle(style);

		// Determine whether or not to ask to refresh the view
		if (appPrefs.getPreference(
				ApplicationPreferences.KEY_DO_NOT_ASK_REFRESH_DISPLAY).equals(
				"true")) {
			if (appPrefs.getPreference(
					ApplicationPreferences.KEY_REFRESH_DISPLAY).equals("true")) {
				Controller.getInstance().refreshBookView();
			}
		} else {
			new SavingDisplayPrefsDialog();
		}
	}

	private void loadPreferences() {
		String defaultTheme = displPrefs.getReaderTheme().getName();
		String defaultSize = displPrefs.getTextSize().getName();
		String defaultFont = displPrefs.getTextStyle().getName();

		comboBox.setSelectedItem(defaultTheme);

		for (JRadioButton rb : radioButtons)
			if (rb.getText().equals(defaultSize))
				sizeButtons.setSelected(rb.getModel(), true);

		for (JRadioButton rb : radioButtons)
			if (rb.getText().equals(defaultFont))
				fontButtons.setSelected(rb.getModel(), true);
	}

	private String[] getReaderThemeNames() {
		String[] nameList = new String[ReaderTheme.values().length];
		int i = 0;
		for (ReaderTheme rt : ReaderTheme.values())
			nameList[i++] = rt.getName();
		return nameList;
	}

	private String getRadioButtonContent(ButtonModel bm) {
		for (JRadioButton rb : radioButtons)
			if (bm.equals(rb.getModel()))
				return rb.getText();
		return null;
	}

	private class SavingDisplayPrefsDialog extends JDialog {
		private static final long serialVersionUID = 1L;
		private final JPanel contentPanel = new JPanel();

		/**
		 * Create the dialog.
		 */
		public SavingDisplayPrefsDialog() {
			setBounds(100, 100, 563, 179);
			getContentPane().setLayout(new BorderLayout());
			contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
			getContentPane().add(contentPanel, BorderLayout.CENTER);
			GridBagLayout gbl_contentPanel = new GridBagLayout();
			gbl_contentPanel.columnWidths = new int[] { 0, 0 };
			gbl_contentPanel.rowHeights = new int[] { 23, 0, 0, 0, 0 };
			gbl_contentPanel.columnWeights = new double[] { 0.0,
					Double.MIN_VALUE };
			gbl_contentPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0,
					Double.MIN_VALUE };
			contentPanel.setLayout(gbl_contentPanel);

			JLabel lblNewLabel = new JLabel(
					"Changes made will set your book back to the beginning unless you choose to apply these later.");
			lblNewLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
			GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
			gbc_lblNewLabel.insets = new Insets(0, 10, 5, 0);
			gbc_lblNewLabel.fill = GridBagConstraints.VERTICAL;
			gbc_lblNewLabel.gridx = 0;
			gbc_lblNewLabel.gridy = 0;
			contentPanel.add(lblNewLabel, gbc_lblNewLabel);

			JLabel lblNewLabel_1 = new JLabel(
					"If you choose later, you will see your changes the next time you open a book.");
			lblNewLabel_1.setFont(new Font("Dialog", Font.PLAIN, 12));
			lblNewLabel_1.setHorizontalAlignment(SwingConstants.LEFT);
			GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
			gbc_lblNewLabel_1.insets = new Insets(0, 10, 5, 0);
			gbc_lblNewLabel_1.anchor = GridBagConstraints.LINE_START;
			gbc_lblNewLabel_1.gridx = 0;
			gbc_lblNewLabel_1.gridy = 1;
			contentPanel.add(lblNewLabel_1, gbc_lblNewLabel_1);

			JLabel lblSetChangesTo = new JLabel(
					"Set changes to take effect now or later?");
			lblSetChangesTo.setFont(new Font("Dialog", Font.PLAIN, 12));
			GridBagConstraints gbc_lblSetChangesTo = new GridBagConstraints();
			gbc_lblSetChangesTo.anchor = GridBagConstraints.LINE_START;
			gbc_lblSetChangesTo.insets = new Insets(3, 10, 5, 0);
			gbc_lblSetChangesTo.gridx = 0;
			gbc_lblSetChangesTo.gridy = 2;
			contentPanel.add(lblSetChangesTo, gbc_lblSetChangesTo);

			final JCheckBox chckbxDoNotAsk = new JCheckBox(
					"Do not ask me this again");
			chckbxDoNotAsk.setFont(new Font("Dialog", Font.BOLD, 12));
			GridBagConstraints gbc_chckbxDoNotAsk = new GridBagConstraints();
			gbc_chckbxDoNotAsk.anchor = GridBagConstraints.LINE_START;
			gbc_chckbxDoNotAsk.insets = new Insets(5, 10, 0, 0);
			gbc_chckbxDoNotAsk.gridx = 0;
			gbc_chckbxDoNotAsk.gridy = 3;
			contentPanel.add(chckbxDoNotAsk, gbc_chckbxDoNotAsk);
			chckbxDoNotAsk.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					appPrefs.setPreference(
							ApplicationPreferences.KEY_DO_NOT_ASK_REFRESH_DISPLAY,
							chckbxDoNotAsk.isSelected() + "");
				}
			});

			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);

			JButton okButton = new JButton("Now");
			buttonPane.add(okButton);
			getRootPane().setDefaultButton(okButton);
			okButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if (chckbxDoNotAsk.isSelected())
						appPrefs.setPreference(
								ApplicationPreferences.KEY_REFRESH_DISPLAY,
								"true");
					Controller.getInstance().refreshBookView();
					dispose();
				}
			});

			JButton cancelButton = new JButton("Later");
			cancelButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (chckbxDoNotAsk.isSelected())
						appPrefs.setPreference(
								ApplicationPreferences.KEY_REFRESH_DISPLAY,
								"false");
					dispose();
				}
			});
			buttonPane.add(cancelButton);

			setVisible(true);
		}

	}
}

package uk.co.bernkastel.ebook.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import javax.swing.JLabel;

import uk.co.bernkastel.ebook.controller.BookManager;
import uk.co.bernkastel.ebook.model.Metadata;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.jgoodies.forms.factories.FormFactory;

public class BookPropertiesDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private Metadata meta;
	
	private String title = "Title Unknown";
	private String authors = "Author Unknown";
	private String publishers = "Unavailable";
	private String year = "Unavailable";
	private String isbn = "Unavailable";
	private String format = "Unavailable";
	private String location = "Unavailable";
	private String fileSize = "Unavailable";
	
	/**
	 * Create the dialog.
	 */
	public BookPropertiesDialog() {
		
		populateMetadata();
		
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new FormLayout(new ColumnSpec[] { ColumnSpec.decode("153px"), ColumnSpec.decode("275px"), FormFactory.LABEL_COMPONENT_GAP_COLSPEC, ColumnSpec.decode("42px"), }, new RowSpec[] { FormFactory.UNRELATED_GAP_ROWSPEC, RowSpec.decode("20px"), FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("max(4dlu;default)"), FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("max(5dlu;default)"), FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, }));
		JLabel lblBookName = new JLabel(title);
		contentPanel.add(lblBookName, "2, 2, left, top");
		lblBookName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		JLabel lblAuthors = new JLabel(authors);
		lblAuthors.setFont(new Font("Tahoma", Font.PLAIN, 12));
		contentPanel.add(lblAuthors, "2, 4, left, center");
		JLabel lblPublishers = new JLabel("Publisher: " + publishers);
		contentPanel.add(lblPublishers, "2, 8");		
		JLabel lblYear = new JLabel("Year: " + year);
		contentPanel.add(lblYear, "2, 10");		
		JLabel lblIsbn = new JLabel("ISBN: " + isbn);
		contentPanel.add(lblIsbn, "2, 12");		
		JLabel lblFormat = new JLabel("Format: " + format);
		contentPanel.add(lblFormat, "2, 16");
		JLabel lblLocation = new JLabel("File Name: " + location);
		contentPanel.add(lblLocation, "2, 18");
		JLabel lblFileSize = new JLabel("File Size: " + fileSize);
		contentPanel.add(lblFileSize, "2, 20");
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		JButton okButton = new JButton("OK");
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
	private void populateMetadata() {
		if (BookManager.getInstance().getMetadata() != null) {
			meta = BookManager.getInstance().getMetadata();
			
			if (meta.getTitle() != null)
				title = meta.getTitle();
			if (meta.getAuthors() != null)
				authors = meta.getAuthors();
			if (meta.getPublishers() != null)
				publishers = meta.getPublishers();
			if (meta.getYear() != 0)
				year = meta.getYear() + "";
			if (meta.getISBN() != null)
				isbn = meta.getISBN();
			if (BookManager.getInstance().getFormat() != null)
				format = BookManager.getInstance().getFormat().getName();
			if (meta.getFileName() != null)
				location = meta.getFileName();
			if (meta.getFileSize() != 0)
				fileSize = meta.getFileSize() + "";			
		}
	}

}

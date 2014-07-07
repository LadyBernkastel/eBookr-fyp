package uk.co.bernkastel.ebook.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.NumberFormat;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;

import uk.co.bernkastel.ebook.controller.Controller;
import uk.co.bernkastel.ebook.view.book.BookView;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class GoToPageDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JFormattedTextField pageNo;
	private BookView bookView;

	/**
	 * Create the dialog.
	 */
	public GoToPageDialog() {		
		bookView = Controller.getInstance().getCurrentView().getBookView();

		setBounds(100, 100, 235, 213);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new FormLayout(new ColumnSpec[] { FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"), }, new RowSpec[] { FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, }));

		JLabel lblSelectWhereTo = new JLabel("Select where to go:");
		contentPanel.add(lblSelectWhereTo, "2, 2");

		final JRadioButton rdbtnGoToPage = new JRadioButton("Go to Page:");
		rdbtnGoToPage.setSelected(true);
		rdbtnGoToPage.setMnemonic(KeyEvent.VK_P);
		contentPanel.add(rdbtnGoToPage, "2, 4");

		NumberFormat goToFormat = NumberFormat.getIntegerInstance();
		goToFormat.setMaximumIntegerDigits(6);
		goToFormat.setGroupingUsed(false);
		pageNo = new JFormattedTextField(goToFormat);
		pageNo.setValue(1l);
		contentPanel.add(pageNo, "4, 4, fill, default");
		pageNo.setColumns(10);

		final JRadioButton rdbtnGoToBeginning = new JRadioButton("Go to Beginning");
		rdbtnGoToBeginning.setMnemonic(KeyEvent.VK_B);
		contentPanel.add(rdbtnGoToBeginning, "2, 6");

		final JRadioButton rdbtnGoToEnd = new JRadioButton("Go to End");
		rdbtnGoToEnd.setMnemonic(KeyEvent.VK_E);
		contentPanel.add(rdbtnGoToEnd, "2, 8");

		ButtonGroup group = new ButtonGroup();
		group.add(rdbtnGoToPage);
		group.add(rdbtnGoToBeginning);
		group.add(rdbtnGoToEnd);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton okButton = new JButton("Go");
		okButton.setActionCommand("OK");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rdbtnGoToPage.isSelected()) {
					bookView.goToLocation(Integer.parseInt(pageNo.getText()));
				} else if (rdbtnGoToBeginning.isSelected()) {
					bookView.goToBeginning();
				} else {
					bookView.goToEnd();
				}
				dispose();
			}
		});
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);

		JButton cancelButton = new JButton("Cancel");
		cancelButton.setActionCommand("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}			
		});
		buttonPane.add(cancelButton);

		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
	}

}

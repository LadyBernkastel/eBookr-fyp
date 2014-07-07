package uk.co.bernkastel.ebook.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AboutDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();

	/**
	 * Create the dialog.
	 */
	public AboutDialog() {
		setBounds(100, 100, 470, 229);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 15, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(0, 1, 0, 0));
		{
			JLabel lblAboutEbookr = new JLabel("About eBookr");
			contentPanel.add(lblAboutEbookr);
		}
		{
			JLabel lblEbookr = new JLabel("Developed by Rachael Smith");
			lblEbookr.setFont(new Font("Dialog", Font.PLAIN, 12));
			contentPanel.add(lblEbookr);
		}
		{
			JLabel lblEmail = new JLabel("Email: rachael@bernkastel.co.uk");
			lblEmail.setFont(new Font("Dialog", Font.PLAIN, 12));
			contentPanel.add(lblEmail);
		}
		{
			JLabel lblWebsite = new JLabel("Website: http://bernkastel.co.uk");
			lblWebsite.setFont(new Font("Dialog", Font.PLAIN, 12));
			contentPanel.add(lblWebsite);
		}
		{
			JLabel lblDevelopment = new JLabel("Developed as a Final Year Project for the University of Salford - 2013/2014");
			lblDevelopment.setFont(new Font("Dialog", Font.PLAIN, 12));
			contentPanel.add(lblDevelopment);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				okButton.addActionListener(new ActionListener() {
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

}

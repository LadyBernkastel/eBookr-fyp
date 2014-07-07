package uk.co.bernkastel.ebook.view;

import javax.swing.JOptionPane;

public class MessageDialog extends JOptionPane {

	private static final long serialVersionUID = 1L;
	private String errorMessage;

	public MessageDialog(String errorMessage) {
		super();
		this.errorMessage = errorMessage;
	}
	
	public static void showErrorDialog(String error) {
		JOptionPane.showMessageDialog(null, error);
	}
	
	public static void showInfoDialog(String info) {
		JOptionPane.showMessageDialog(null, info);
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}

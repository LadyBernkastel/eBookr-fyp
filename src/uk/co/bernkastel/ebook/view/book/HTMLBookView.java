package uk.co.bernkastel.ebook.view.book;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

import uk.co.bernkastel.ebook.model.Format;
import uk.co.bernkastel.ebook.model.book.Ebook;
import uk.co.bernkastel.ebook.model.book.HTMLEbook;
import uk.co.bernkastel.ebook.model.prefs.DisplayPreferences;
import uk.co.bernkastel.ebook.model.prefs.ReaderTheme;
import uk.co.bernkastel.ebook.view.MainWindowView;

public class HTMLBookView extends BookView {

	private static final int BORDER_WIDTH = 20;
	private DisplayPreferences prefs = new DisplayPreferences();

	public HTMLBookView() {
		setViewComponent(createScrollPane(createTextPane()));
	}

	@Override
	public void setContent(Ebook ebook) {
		if (checkFormatAccepted(ebook)) {
			// Get the content from the ebook
			String content = ((HTMLEbook) ebook).getContent();
			// Create and add a text pane
			JTextPane textPane = createTextPane();

			// Get the editor kit for the text pane & get the document
			HTMLEditorKit htmlEditorKit = (HTMLEditorKit) textPane
					.getEditorKit();
			HTMLDocument htmlDoc = (HTMLDocument) textPane.getDocument();

			// Style the pane
			styleTextPane(textPane);

			try {
				htmlEditorKit.insertHTML(htmlDoc, htmlDoc.getLength(), content,
						0, 0, null);
			} catch (BadLocationException e) {
				System.err
						.println("Unable to insert html in html book view at location: "
								+ htmlDoc.getLength());
				e.printStackTrace();
			} catch (IOException e) {
				System.err
						.println("IOException when inserting html to html book view");
				e.printStackTrace();
			} finally {
				htmlDoc.setCharacterAttributes(0, htmlDoc.getLength(),
						textPane.getInputAttributes(), false);
			}
			setViewComponent(createScrollPane(textPane));
		}
	}

	/**
	 * Creates a JTextPane with the correct styling, borders and caret settings.
	 * Also adds a mouse adapter for the right click menu.
	 * 
	 * @return
	 */
	private JTextPane createTextPane() {
		JTextPane textPane = new JTextPane();
		textPane.setContentType("text/html;charset=utf-8");
		textPane.setEditable(false);
		textPane.getDocument().putProperty("IgnoreCharsetDirective",
				Boolean.TRUE);
		Border border = new MatteBorder(BORDER_WIDTH * 2, BORDER_WIDTH,
				BORDER_WIDTH * 2, BORDER_WIDTH, prefs.getReaderTheme()
						.getBackgroundColour());
		textPane.setBorder(border);
		DefaultCaret caret = (DefaultCaret) textPane.getCaret();
		caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
		textPane.addMouseListener(new BookViewMouseAdapter());
		return textPane;
	}

	/**
	 * Creates a JScrollPane from the given textPane with the correct settings.
	 * 
	 * @param textPane
	 * @return
	 */
	private JScrollPane createScrollPane(JTextPane textPane) {
		JScrollPane jsc = new JScrollPane(textPane);
		jsc.setPreferredSize(MainWindowView.getContentSize());
		jsc.setBorder(new EmptyBorder(10, 10, 10, 10));
		return jsc;
	}

	/**
	 * Adds the styling mutable attribute set to the given JTextPane.
	 * @param pane
	 */
	private void styleTextPane(JTextPane pane) {
		MutableAttributeSet mats = pane.getInputAttributes();

		ReaderTheme rt = prefs.getReaderTheme();
		Color textColour = rt.getTextColour();
		Color bgColour = rt.getBackgroundColour();
		String textFont = prefs.getTextStyle().getFont();
		int textSize = prefs.getTextSize().getPx();

		StyleConstants.setForeground(mats, textColour);
		StyleConstants.setFontFamily(mats, textFont);
		StyleConstants.setFontSize(mats, textSize);

		pane.setBackground(bgColour);
	}

	@Override
	public List<Format> getFormatsAccepted() {
		List<Format> list = new ArrayList<Format>();
		list.add(Format.EPUB);
		list.add(Format.HTML);
		return list;
	}

	public void updateContentSize(Dimension di) {
		getViewComponent().setPreferredSize(di);
		getViewComponent().revalidate();
		getViewComponent().repaint();
	}

	@Override
	public void goToBeginning() {
		((JScrollPane) getViewComponent()).getVerticalScrollBar().setValue(0);
	}

	@Override
	public void goToEnd() {
		JScrollBar scrollBar = ((JScrollPane) getViewComponent())
				.getVerticalScrollBar();
		scrollBar.setValue(scrollBar.getMaximum());
	}

	@Override
	public int getSelectedStart() {
		return getTextPane().getSelectionStart();
	}

	@Override
	public int getSelectedEnd() {
		return getTextPane().getSelectionEnd();
	}

	public void goToLocation(int location) {
		getTextPane().setCaretPosition(location);
	}

	@Override
	public String getSelectedText() {
		return getTextPane().getSelectedText();
	}

	private JTextPane getTextPane() {
		return (JTextPane) ((Container) getViewComponent().getComponent(0))
				.getComponent(0);
	}
}

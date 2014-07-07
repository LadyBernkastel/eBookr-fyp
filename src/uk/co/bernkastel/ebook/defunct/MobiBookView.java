package uk.co.bernkastel.ebook.defunct;

import java.awt.Color;

/*
 * THIS CODE IS NOT USED IN THE FINAL PRODUCT. PROVIDED AS EXAMPLE OF ATTEMPT AT MOBI IMPLEMENTATION.
 */
import java.awt.Dimension;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

import uk.co.bernkastel.ebook.model.Format;
import uk.co.bernkastel.ebook.model.book.Ebook;
import uk.co.bernkastel.ebook.view.MainWindowView;
import uk.co.bernkastel.ebook.view.book.BookView;

public class MobiBookView extends BookView {

	private static final int BORDER_WIDTH = 20;

	public MobiBookView() {
		setViewComponent(createScrollPane(createTextPane()));
	}

	@Override
	public void setContent(Ebook ebook) {
		if (checkFormatAccepted(ebook)) {
			String content = ((MobiEbook) ebook).getContent();
			JTextPane textPane = createTextPane();

			HTMLEditorKit htmlEditorKit = (HTMLEditorKit) textPane
					.getEditorKit();
			HTMLDocument htmlDoc = (HTMLDocument) textPane.getDocument();
			// System.out.println(content);
			try {
				htmlEditorKit.insertHTML(htmlDoc, htmlDoc.getLength(), content,
						0, 0, null);
			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			setViewComponent(createScrollPane(textPane));
		}

	}

	private JTextPane createTextPane() {
		JTextPane textPane = new JTextPane();
		textPane.setContentType("text/html;charset=utf-8");
		textPane.setEditable(false);
		textPane.getDocument().putProperty("IgnoreCharsetDirective",
				Boolean.TRUE);
		Border border = new MatteBorder(BORDER_WIDTH * 2, BORDER_WIDTH,
				BORDER_WIDTH * 2, BORDER_WIDTH, Color.WHITE);
		textPane.setBorder(border);
		DefaultCaret caret = (DefaultCaret) textPane.getCaret();
		caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
		return textPane;
	}

	private JScrollPane createScrollPane(JTextPane textPane) {
		JScrollPane jsc = new JScrollPane(textPane);
		jsc.setPreferredSize(MainWindowView.getContentSize());
		jsc.setBorder(new EmptyBorder(10, 10, 10, 10));
		return jsc;
	}

	@Override
	public List<Format> getFormatsAccepted() {
		List<Format> list = new ArrayList<Format>();
		//list.add(Format.MOBI);
		return list;
	}

	@Override
	public void updateContentSize(Dimension di) {
		getViewComponent().setPreferredSize(di);
	}

	@Override
	public void goToBeginning() {
		// TODO Auto-generated method stub
	}

	@Override
	public void goToEnd() {
		// TODO Auto-generated method stub
	}

	@Override
	public int getSelectedStart() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getSelectedEnd() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void goToLocation(int location) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getSelectedText() {
		// TODO Auto-generated method stub
		return null;
	}

}

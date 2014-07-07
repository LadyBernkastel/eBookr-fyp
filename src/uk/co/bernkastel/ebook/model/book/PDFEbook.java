package uk.co.bernkastel.ebook.model.book;

import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;

import uk.co.bernkastel.ebook.model.Format;

public class PDFEbook extends Ebook {

	private static final long serialVersionUID = 1L;
	private transient PDDocument content;

	public PDDocument getContent() {
		return content;
	}

	public void setContent(PDDocument content) {
		this.content = content;
	}

	@Override
	public List<Format> getFormats() {
		List<Format> list = new ArrayList<Format>();
		list.add(Format.PDF);
		return list;
	}

	/**
	 * Required to close the PDDocument when the object is cleared up by the
	 * garbage collection.
	 */
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		if (content != null)
			content.close();
	}
}

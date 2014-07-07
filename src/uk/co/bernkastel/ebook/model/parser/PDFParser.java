package uk.co.bernkastel.ebook.model.parser;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;

import uk.co.bernkastel.ebook.model.Format;
import uk.co.bernkastel.ebook.model.Metadata;
import uk.co.bernkastel.ebook.model.book.Ebook;
import uk.co.bernkastel.ebook.model.book.PDFEbook;
import uk.co.bernkastel.ebook.view.MessageDialog;

public class PDFParser extends Parser {
	@Override
	protected Metadata extractMetadata(File file) {
		Metadata metadata = new Metadata();
		PDDocument pdfDoc;
		try {
			pdfDoc = PDDocument.load(file);
			PDDocumentInformation info = pdfDoc.getDocumentInformation();
			pdfDoc.close();
			if (info.getAuthor() != null)
				metadata.addAuthor(info.getAuthor());
			if (info.getTitle() != null)
				metadata.setTitle(info.getTitle());
			if (info.getProducer() != null)
				metadata.addPublisher(info.getProducer());
			metadata.setPath(file.toPath());
		} catch (IOException e) {
			System.err.println("IO Error reading metadata");
			e.printStackTrace();
		}
		return metadata;
	}

	@Override
	public Ebook readBook(File file) {
		PDFEbook book = new PDFEbook();
		try {
			PDDocument pdfDoc = PDDocument.load(file);
			if (!pdfDoc.isEncrypted()) {
				book.setContent(pdfDoc);
				book.setMetadata(extractMetadata(file));
			} else {
				MessageDialog.showErrorDialog("Unable to open Encrypted PDF File. Please decrypt and try again.");
				return null;
			}
		} catch (IOException e) {
			System.err.println("IO Error reading book");
			e.printStackTrace();
		}
		return book;
	}

	@Override
	public Format getFormat() {
		return Format.PDF;
	}
}

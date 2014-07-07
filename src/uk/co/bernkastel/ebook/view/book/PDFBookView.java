package uk.co.bernkastel.ebook.view.book;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import org.apache.pdfbox.pdmodel.PDPage;

import uk.co.bernkastel.ebook.model.Format;
import uk.co.bernkastel.ebook.model.book.Ebook;
import uk.co.bernkastel.ebook.model.book.PDFEbook;
import uk.co.bernkastel.ebook.view.MessageDialog;

public class PDFBookView extends PageableBookView {
	private PDFEbook ebook;
	private List<PDPage> pages = new ArrayList<PDPage>();	
	
	@SuppressWarnings("unchecked")
	@Override
	public void setContent(Ebook ebook) {
		if (checkFormatAccepted(ebook)) {
			this.ebook = (PDFEbook) ebook;
			pages = this.ebook.getContent().getDocumentCatalog().getAllPages();
			getViewComponent().add(createScrollPane(1), 2);
		}
	}	

	@Override
	public List<Format> getFormatsAccepted() {
		List<Format> list = new ArrayList<Format>();
		list.add(Format.PDF);
		return list;
	}

	@Override
	protected ImageIcon getScaledImageIcon(int pageNo) {
		// Uses the default scaled image icon method	
		pageNo--;
		ImageIcon icon = super.getScaledImageIcon(pageNo);
		if(icon != null)
			return icon;
		
		// If the icon doesn't exist, extract it from the PDF		
		BufferedImage image;		
		PDPage page = pages.get(pageNo);
		try {
			image = page.convertToImage();
			pageImagesMap.put(pageNo, image);
			return getScaledInstance(image);
		} catch (IOException e) {
			MessageDialog.showErrorDialog("Unable to convert PDF page to Image");
			e.printStackTrace();
		}
		return null;
	}


	@Override
	public int getNumberOfPages() {
		return pages.size();
	}
	
}

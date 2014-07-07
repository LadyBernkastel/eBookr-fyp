package uk.co.bernkastel.ebook.view.book;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import uk.co.bernkastel.ebook.model.Format;
import uk.co.bernkastel.ebook.model.book.ComicEbook;
import uk.co.bernkastel.ebook.model.book.Ebook;
import uk.co.bernkastel.ebook.view.MessageDialog;

public class ComicBookView extends PageableBookView {
	private ComicEbook ebook;

	@Override
	public void setContent(Ebook ebook) {
		if (checkFormatAccepted(ebook)) {
			this.ebook = (ComicEbook) ebook;
			getViewComponent().add(createScrollPane(1), 2);
		}		
	}

	@Override
	public List<Format> getFormatsAccepted() {
		List<Format> list = new ArrayList<Format>();
		list.add(Format.COMIC);
		return list;
	}
	
	private BufferedImage getPageImage(int pageNumber) {
		String location = ebook.getPagesLocationMap().get(pageNumber);
		if (location != null) {
			File file = new File(location);
			if (file.exists()) {
				BufferedImage img = null;
				try {
					// Read the image into memory
					img = ImageIO.read(file);	
					return img;				
				} catch (IOException e) {
					e.printStackTrace();
					MessageDialog.showErrorDialog("Unable to load image");
					return null;
				}
			}
		}
		return null;
	}
	
	@Override
	protected ImageIcon getScaledImageIcon(int pageNo) {
		// Uses the default scaled image icon method
		ImageIcon icon = super.getScaledImageIcon(pageNo);
		if(icon != null)
			return icon;
		
		BufferedImage image = getPageImage(pageNo);
		if (image != null) {
			pageImagesMap.put(pageNo, image);
			return getScaledInstance(image);
		}
		return null;
	}
	
	@Override
	public int getNumberOfPages() {
		return ebook.getPagesLocationMap().size();
	}
	
}

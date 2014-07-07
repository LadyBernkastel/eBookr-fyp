package uk.co.bernkastel.ebook.view;

import java.io.File;

import javax.swing.filechooser.FileFilter;

import uk.co.bernkastel.ebook.model.Format;

public class BookFileFilter extends FileFilter {

	@Override
	public boolean accept(File f) {
		if (f.isDirectory())
			return true;
		for (Format format : Format.values())
			for (String extension : format.getExtensions())
				if (f.getName().endsWith(extension))
					return true;
		return false;
	}

	@Override
	public String getDescription() {
		return "Ebook files - .epub, .pdf, .html, .cbz";
	}

}

package uk.co.bernkastel.ebook.model.parser;

import java.io.File;
import java.io.FileFilter;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import uk.co.bernkastel.ebook.model.Format;
import uk.co.bernkastel.ebook.model.Metadata;
import uk.co.bernkastel.ebook.model.book.ComicEbook;
import uk.co.bernkastel.ebook.model.book.Ebook;

public class ComicParser extends Parser {
	private String destination = System.getProperty("java.io.tmpdir")
			+ File.separator + "eBookr" + File.separator;
	private ComicEbook book = new ComicEbook();

	@Override
	public Format getFormat() {
		return Format.COMIC;
	}

	@Override
	protected Metadata extractMetadata(File file) {
		Metadata metadata = new Metadata();
		metadata.setPath(file.toPath());
		String filename = file.getName();
		filename = filename.substring(0, filename.indexOf("."));
		metadata.setTitle(filename);
		return metadata;
	}

	@Override
	public Ebook readBook(File file) {
		book.setFolderLocation(extractZipComic(file));
		// Extract the folder location and list of files
		String folderLoc = book.getFolderLocation();
		File folder = new File(folderLoc);
		extractPageLocations(folder);
		book.setMetadata(extractMetadata(file));
		return book;
	}

	private String extractZipComic(File file) {
		String destination = this.destination + file.getName() + File.separator;
		String source = file.getPath();
		try {
			ZipFile zipFile = new ZipFile(source);
			if (!zipFile.isEncrypted()) {
				zipFile.extractAll(destination);
			}
		} catch (ZipException e) {
			System.err.println("Error unzipping cbz file.");
			e.printStackTrace();
		}
		return destination;
	}

	private void extractPageLocations(File folder) {
		// Create a file filter to obtain image files only.
		FileFilter filter = new ImageFileFilter();
		File[] files = folder.listFiles(filter);
		// For each file obtained
		for (File file : files) {
			if (file.isDirectory())
				extractPageLocations(file);
			else {
				// Extract the page number from the title
				String name = file.getName().replaceFirst(".*?(\\d+).*", "$1");
				int pageNumber = Integer.parseInt(name);
				// Add to the book
				book.addPageLocation(pageNumber, file.getPath());
			}
		}
	}

	private class ImageFileFilter implements FileFilter {
		@Override
		public boolean accept(File file) {
			if (file.isDirectory())
				return true;
			String[] extensions = { ".png", ".jpg", ".jpeg", ".bmp", ".gif" };
			for (String ext : extensions)
				if (file.getName().endsWith(ext))
					return true;
			return false;
		}
	}

}

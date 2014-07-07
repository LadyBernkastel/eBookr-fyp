package uk.co.bernkastel.ebook.model.book;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import uk.co.bernkastel.ebook.model.Format;

public class ComicEbook extends Ebook {
	
	private static final long serialVersionUID = 1L;
	private transient String folderLocation;
	private transient Map<Integer, String> pagesLocationMap = new HashMap<Integer, String>();
	
	@Override
	public List<Format> getFormats() {
		List<Format> list = new ArrayList<Format>();
		list.add(Format.COMIC);
		return list;
	}
	
	public String getFolderLocation() {
		return folderLocation;
	}

	public void setFolderLocation(String folderLocation) {
		this.folderLocation = folderLocation;
	}

	public Map<Integer, String> getPagesLocationMap() {
		return pagesLocationMap;
	}

	public void setPagesLocationMap(Map<Integer, String> pagesLocationMap) {
		this.pagesLocationMap = pagesLocationMap;
	}
	
	public void addPageLocation(int page, String location) {
		pagesLocationMap.put(page, location);
	}	

	/**
	 * Deletes the extracted image files when no longer required.
	 */
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		if (getFolderLocation() != null) {
			File file = new File(getFolderLocation());
			if (file.exists() && file.isDirectory())
				FileUtils.deleteDirectory(file);
		}
			
	}
}

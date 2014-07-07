package uk.co.bernkastel.ebook.model.book;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import uk.co.bernkastel.ebook.model.Bookmark;
import uk.co.bernkastel.ebook.model.Format;
import uk.co.bernkastel.ebook.model.ImageResource;
import uk.co.bernkastel.ebook.model.Metadata;
import uk.co.bernkastel.ebook.view.MessageDialog;

public abstract class Ebook implements Serializable {
	private static final long serialVersionUID = 1L;
	private Metadata metadata;
	private transient List<ImageResource> images = new ArrayList<ImageResource>();
	private transient List<Bookmark> bookmarkList = new ArrayList<Bookmark>();
	private String md5;
	
	public Metadata getMetadata() {
		return metadata;
	}
	public void setMetadata(Metadata metadata) {
		this.metadata = metadata;
	}
	public List<ImageResource> getImages() {
		return images;
	}
	public void setImages(List<ImageResource> images) {
		this.images = images;
	}
	public void addImage(ImageResource image) {
		if (images != null && !images.contains(image)) {
			images.add(image);
		}
	}
	public abstract List<Format> getFormats();
	
	public List<Bookmark> getBookmarks() {
		return bookmarkList;
	}
	
	public void addBookmark(Bookmark uac) {
		if (bookmarkList != null)
			bookmarkList.add(uac);
		else
			MessageDialog.showErrorDialog("Unable to add bookmark. Bookmark list not present in ebook.");
	}
	
	public void setBookmarks(List<Bookmark> bookmarks) {
		bookmarkList = bookmarks;
	}
	public String getMd5() {
		return md5;
	}
	public void setMd5(String md5) {
		this.md5 = md5;
	}	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((md5 == null) ? 0 : md5.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ebook other = (Ebook) obj;
		if (md5 == null) {
			if (other.md5 != null)
				return false;
		} else if (!md5.equals(other.md5))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Ebook [metadata=" + metadata + ", md5=" + md5 + "]\n"
				+ metadata.toString();
	}
	
}

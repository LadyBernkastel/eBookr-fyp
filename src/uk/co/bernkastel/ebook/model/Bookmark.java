package uk.co.bernkastel.ebook.model;

import java.io.Serializable;

public class Bookmark implements Serializable {

	private static final long serialVersionUID = 1L;
	private String name;
	private String preview;	
	private int startLocation;
	private int endLocation;
	private String dateAdded;
	
	public int getStartLocation() {
		return startLocation;
	}

	public void setStartLocation(int startLocation) {
		this.startLocation = startLocation;
	}

	public int getEndLocation() {
		return endLocation;
	}

	public void setEndLocation(int endLocation) {
		this.endLocation = endLocation;
	}

	public String getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(String dateAdded) {
		this.dateAdded = dateAdded;
	}

	public String getName() {
		return name;
	}

	public void setName(String bookmarkName) {
		this.name = bookmarkName;
	}

	public String getPreview() {
		return preview;
	}

	public void setPreview(String preview) {
		this.preview = preview;
	}
	

	@Override
	public String toString() {
		return "Bookmark [name=" + name + ", preview=" + preview
				+ ", startLocation=" + startLocation + ", endLocation="
				+ endLocation
				+ ", dateAdded=" + dateAdded + "]";
	}
}

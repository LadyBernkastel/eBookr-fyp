package uk.co.bernkastel.ebook.model;

public class ImageResource extends Resource {
	private String originalLocation;
	private String actualLocation;
	
	public ImageResource() {		
	}
	
	public ImageResource(String original, String actual) {
		this();
		originalLocation = original;
		actualLocation = actual;
	}

	public String getOriginalLocation() {
		return originalLocation;
	}

	public void setOriginalLocation(String originalLocation) {
		this.originalLocation = originalLocation;
	}

	public String getActualLocation() {
		return actualLocation;
	}

	public void setActualLocation(String actualLocation) {
		this.actualLocation = actualLocation;
	}	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((actualLocation == null) ? 0 : actualLocation.hashCode());
		result = prime * result + ((originalLocation == null) ? 0 : originalLocation.hashCode());
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
		ImageResource other = (ImageResource) obj;
		if (actualLocation == null) {
			if (other.actualLocation != null)
				return false;
		} else if (!actualLocation.equals(other.actualLocation))
			return false;
		if (originalLocation == null) {
			if (other.originalLocation != null)
				return false;
		} else if (!originalLocation.equals(other.originalLocation))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ImageResource [originalLocation=" + originalLocation + ", actualLocation=" + actualLocation + "]";
	}
	
	
}

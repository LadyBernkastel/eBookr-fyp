package uk.co.bernkastel.ebook.model;

/**
 * Represents a resource that is a part of the book.
 * Can be text, images, html, xml ect.
 *
 */
public class Resource {
	private String name;
	private String url;
	
	public Resource() {
		
	}

	public Resource(String name, String url) {
		this();
		this.name = name;
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}

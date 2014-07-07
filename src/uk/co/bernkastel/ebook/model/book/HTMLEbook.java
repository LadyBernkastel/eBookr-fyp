package uk.co.bernkastel.ebook.model.book;

import java.util.ArrayList;
import java.util.List;

import uk.co.bernkastel.ebook.model.Format;


public class HTMLEbook extends Ebook {	
	private static final long serialVersionUID = 1L;
	private transient String content;
	
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Override
	public List<Format> getFormats() {
		List<Format> list = new ArrayList<Format>();
		list.add(Format.EPUB);
		list.add(Format.HTML);
		return list;
	}
}

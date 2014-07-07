package uk.co.bernkastel.ebook.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Metadata implements Serializable {
	private static final long serialVersionUID = 1L;
	private String title;
	private transient List<String> authors = new ArrayList<String>();
	private transient int year = 0;
	private transient String ISBN;
	private transient List<String> publishers = new ArrayList<String>();
	private transient int fileSize = 0;
	private transient Path path;

	private void writeObject(ObjectOutputStream oos)
			throws IOException {
		oos.defaultWriteObject();
		oos.writeObject(path.toString());
	}

	private void readObject(ObjectInputStream ois)
			throws ClassNotFoundException, IOException {
		ois.defaultReadObject();
		path = Paths.get((String)ois.readObject());
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<String> getAuthorList() {
		return authors;
	}

	public String getAuthors() {
		String authorList = "";
		for (String author : authors)
			authorList += author + " ";
		return authorList;
	}

	public void addAuthor(String author) {
		authors.add(author);
	}

	public void setAuthors(List<String> authors) {
		this.authors = authors;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getISBN() {
		return ISBN;
	}

	public void setISBN(String string) {
		ISBN = string;
	}

	public List<String> getPublisherList() {
		return publishers;
	}

	public String getPublishers() {
		String publisherList = "";
		for (String publisher : publishers)
			publisherList += publisher + " ";
		return publisherList;
	}

	public void setPublishers(List<String> publishers) {
		this.publishers = publishers;
	}

	public void addPublisher(String publisher) {
		publishers.add(publisher);
	}

	public int getFileSize() {
		return fileSize;
	}

	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}

	public String getFileName() {
		if (path != null)
			return path.getFileName().toString();
		return "No Filename";
	}

	public Path getPath() {
		return path;
	}

	public void setPath(Path path) {
		this.path = path;
	}

	@Override
	public String toString() {
		return "Metadata [title=" + title + ", authors=" + authors + ", year="
				+ year + ", ISBN=" + ISBN + ", publishers=" + publishers
				+ ", fileSize=" + fileSize + ", path=" + path + "]";
	}
	
	
}

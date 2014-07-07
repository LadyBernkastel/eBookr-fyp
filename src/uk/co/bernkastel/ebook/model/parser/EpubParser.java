package uk.co.bernkastel.ebook.model.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import nl.siegmann.epublib.domain.Author;
import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.MediaType;
import nl.siegmann.epublib.domain.Resource;
import nl.siegmann.epublib.domain.Resources;
import nl.siegmann.epublib.domain.Spine;
import nl.siegmann.epublib.domain.SpineReference;
import nl.siegmann.epublib.epub.EpubReader;
import nl.siegmann.epublib.service.MediatypeService;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import uk.co.bernkastel.ebook.model.Format;
import uk.co.bernkastel.ebook.model.ImageResource;
import uk.co.bernkastel.ebook.model.Metadata;
import uk.co.bernkastel.ebook.model.book.HTMLEbook;

public class EpubParser extends Parser {
	private String tempDir = System.getProperty("java.io.tmpdir");

	@Override
	protected Metadata extractMetadata(File file) {
		EpubReader reader = new EpubReader();
		Book book;
		Metadata metadata = new Metadata();
		try {
			book = reader.readEpub(new FileInputStream(file));
			nl.siegmann.epublib.domain.Metadata meta = book.getMetadata();
			for (Author author : meta.getAuthors()) {
				metadata.addAuthor(author.getFirstname() + " " + author.getLastname());
			}
			metadata.setTitle(meta.getTitles().get(0));
			metadata.setPublishers(meta.getPublishers());
			metadata.setPath(file.toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return metadata;
	}

	@Override
	public HTMLEbook readBook(File file) {
		EpubReader reader = new EpubReader();
		HTMLEbook hTMLEbook = new HTMLEbook();
		try {
			Book book = reader.readEpub(new FileInputStream(file));
			hTMLEbook.setMetadata(extractMetadata(file));
			StringBuilder sb = new StringBuilder();
			Spine spine = book.getSpine();
			for (SpineReference sr : spine.getSpineReferences()) {
				Resource resource = sr.getResource();
				String s = new String(resource.getData(), resource.getInputEncoding());
				sb.append(s);
			}
			hTMLEbook.setImages(processImages(book));
			Document document = Jsoup.parse(sb.toString());
			document = cleanHTML(document);
			document = replaceImagesHTML(document, hTMLEbook.getImages());
			String html = document.html();
			hTMLEbook.setContent(html);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return hTMLEbook;
	}

	private Document cleanHTML(Document doc) {
		doc.getElementsByTag("style").remove();
		Element head = doc.getElementsByTag("head").first();
		head.getElementsByAttributeValue("type", "text/css").remove();
		return doc;
	}
	
	private Document replaceImagesHTML(Document doc, List<ImageResource> images) {
		
		for (Element element : doc.getElementsByTag("img")) {
			String src = element.attr("src");			
			int lastSlash = src.lastIndexOf('\\');
			if (lastSlash == -1)
				lastSlash = src.lastIndexOf('/');
			if (lastSlash != -1) {				
				src = src.substring(lastSlash+1);
			}			
			
			String newSrc = null;
			
			int i = 0;			
			while (newSrc == null && i < images.size()) {
				ImageResource image = images.get(i);				
				if (src.equals(image.getOriginalLocation()))
					newSrc = image.getActualLocation();
				i++;
			}
			if (newSrc != null)
				element.attr("src", newSrc);
			else
				element.attr("src", "");
		}
		return doc;
	}

	private List<ImageResource> processImages(Book book) throws IOException {
		Resources resources = book.getResources();
		List<ImageResource> images = new ArrayList<ImageResource>();

		File tempDirectory = new File(tempDir + File.separator + "eBookr" + File.separator);
		if (!tempDirectory.exists()) {
			tempDirectory.mkdirs();
		}
		
		for (Resource resource : resources.getAll()) {
			MediaType mediaType = resource.getMediaType();
			
			if (MediatypeService.isBitmapImage(mediaType)) {
				String resourceId = resource.getId();
				String actualLocation = "file:///" + tempDirectory.toString() + "/" + resourceId + mediaType.getDefaultExtension();
				String originalLocation = resource.getHref();
				
				int lastSlash = originalLocation.lastIndexOf('\\');
				if (lastSlash == -1)
					lastSlash = originalLocation.lastIndexOf('/');
				if (lastSlash != -1) {				
					originalLocation = originalLocation.substring(lastSlash+1);
				}
				
				ImageResource image = new ImageResource(originalLocation, actualLocation);
								
				File file = new File(tempDirectory.toString() + "/" + resourceId + mediaType.getDefaultExtension());
				file.deleteOnExit();
				
				FileOutputStream fos = new FileOutputStream(file);
				fos.write(resource.getData());
				fos.close();
				
				if (file.exists())
					images.add(image);		
			}			
		}
		
		return images;
	}
	

	@Override
	public Format getFormat() {
		return Format.EPUB;
	}
}

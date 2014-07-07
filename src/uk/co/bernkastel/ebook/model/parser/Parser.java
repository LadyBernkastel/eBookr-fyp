package uk.co.bernkastel.ebook.model.parser;

import java.io.File;

import uk.co.bernkastel.ebook.model.Format;
import uk.co.bernkastel.ebook.model.Metadata;
import uk.co.bernkastel.ebook.model.book.Ebook;

public abstract class Parser {

	public abstract Format getFormat();

	protected abstract Metadata extractMetadata(File file);

	public abstract Ebook readBook(File file);
}

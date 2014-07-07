package uk.co.bernkastel.ebook.model.prefs;

public enum ReaderTextStyle {

	SERIF("Serif", "Georgia"), SANS_SERIF("Sans-Serif", "Arial"), CURSIVE("Cursive", "Comic Sans MS");

	private String name;
	private String font;

	private ReaderTextStyle(String name, String font) {
		this.name = name;
		this.font = font;
	}

	public String getName() {
		return name;
	}

	public String getFont() {
		return font;
	}

}

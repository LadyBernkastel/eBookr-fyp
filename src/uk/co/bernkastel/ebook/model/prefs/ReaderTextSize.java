package uk.co.bernkastel.ebook.model.prefs;

public enum ReaderTextSize {
	SMALL("Small", 11), MEDIUM("Medium", 14), LARGE("Large", 18), EXTRA_LARGE("X-Large", 24);

	private String name;
	private int px;

	private ReaderTextSize(String name, int px) {
		this.name = name;
		this.px = px;
	}

	public String getName() {
		return name;
	}

	public int getPx() {
		return px;
	}

}

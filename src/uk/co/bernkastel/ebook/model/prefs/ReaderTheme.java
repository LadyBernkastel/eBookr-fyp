package uk.co.bernkastel.ebook.model.prefs;

import java.awt.Color;

public enum ReaderTheme {
	WHITE("White", Color.BLACK, Color.WHITE),
	BLACK("Black", Color.WHITE, Color.BLACK),
	PAPER("Paper", Color.BLACK, new Color(234, 225, 204));

	private String name;
	private Color textColour;
	private Color backgroundColour;

	private ReaderTheme(String name, Color textColour, Color bgColour) {
		this.name = name;
		this.textColour = textColour;
		this.backgroundColour = bgColour;
	}

	public String getName() {
		return name;
	}

	public Color getTextColour() {
		return textColour;
	}

	public Color getBackgroundColour() {
		return backgroundColour;
	}
}

package uk.co.bernkastel.ebook.model.prefs;

import java.text.SimpleDateFormat;
import java.util.Date;

public enum DateSaveFormat {
	DAY_FIRST("DD/MM/YYYY", new SimpleDateFormat("dd/MM/yyyy")),
	MONTH_FIRST("MM/DD/YYYY", new SimpleDateFormat("MM/dd/yyyy")), YEAR_FIRST(
			"YYYY/MM/DD", new SimpleDateFormat("yyyy/MM/dd"));
	
	private String display;
	private SimpleDateFormat format;

	private DateSaveFormat(String display, SimpleDateFormat format) {
		this.display = display;
		this.format = format;
	}

	public String getDisplay() {
		return display;
	}

	public String formatDate(Date date) {
		return format.format(date);
	}
}

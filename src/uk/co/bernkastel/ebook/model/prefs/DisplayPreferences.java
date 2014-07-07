package uk.co.bernkastel.ebook.model.prefs;

import java.util.ArrayList;
import java.util.prefs.Preferences;

import uk.co.bernkastel.ebook.controller.PreferencesManager;

public class DisplayPreferences extends PreferencesNode {

	public static final String KEY_THEME = "theme";
	public static final String KEY_TEXT_SIZE = "text_size";
	public static final String KEY_TEXT_STYLE = "text_style";
	
	public DisplayPreferences() {
		super(Preferences.userNodeForPackage(DisplayPreferences.class));
	}
	
	@Override
	public String getPreference(String key) {
		String preference = "";
		if (key.equals(KEY_THEME))
			preference = getReaderTheme().toString();
		else if (key.equals(KEY_TEXT_SIZE))
			preference = getTextSize().toString();
		else if (key.equals(KEY_TEXT_STYLE))
			preference = getTextStyle().toString();
		return preference;
	}

	public ReaderTheme getReaderTheme() {
		return (ReaderTheme) PreferencesManager.getEnumeratedPreference(
				ReaderTheme.class, ReaderTheme.WHITE, KEY_THEME, getPrefs());
	}

	public ReaderTextSize getTextSize() {
		return (ReaderTextSize) PreferencesManager.getEnumeratedPreference(
				ReaderTextSize.class, ReaderTextSize.MEDIUM, KEY_TEXT_SIZE,
				getPrefs());
	}

	public ReaderTextStyle getTextStyle() {
		return (ReaderTextStyle) PreferencesManager.getEnumeratedPreference(
				ReaderTextStyle.class, ReaderTextStyle.SERIF, KEY_TEXT_STYLE,
				getPrefs());
	}

	public void setReaderTheme(ReaderTheme value) {
		PreferencesManager.setEnumeratedPreference(KEY_THEME, value, getPrefs());
	}

	public void setTextSize(ReaderTextSize value) {
		PreferencesManager.setEnumeratedPreference(KEY_TEXT_SIZE, value, getPrefs());
	}

	public void setTextStyle(ReaderTextStyle value) {
		PreferencesManager
				.setEnumeratedPreference(KEY_TEXT_STYLE, value, getPrefs());
	}
	
	@Override
	public ArrayList<String> getKeys() {
		ArrayList<String> list = new ArrayList<String>(3);
		list.add(KEY_THEME);
		list.add(KEY_TEXT_SIZE);
		list.add(KEY_TEXT_STYLE);
		return list;
	}

}

package uk.co.bernkastel.ebook.model.prefs;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.prefs.Preferences;

import uk.co.bernkastel.ebook.controller.PreferencesManager;

public class ApplicationPreferences extends PreferencesNode {

	private int numberOfKeys;
	private ArrayList<String> keyList;
	public static final String KEY_DO_NOT_ASK_REFRESH_DISPLAY = "do_not_ask_refresh_display";
	public static final String KEY_REFRESH_DISPLAY = "refresh_display";

	public static final String KEY_SAVE_PREVIOUS_BOOKS = "save_previous_books";
	public static final String KEY_STATE_LOCATION = "uac_save_location";
	
	public static final String KEY_DATE_FORMAT = "date_format";

	public static final String KEY_AUOTMATICALLY_OPEN_BOOK = "automatically_open_book";
	public static final String KEY_OPEN_PREVIOUS_LOC = "open_previous_location";
	public static final String KEY_PREVIOUS_BOOK_PATH = "previous_book_path";
	public static final String KEY_PREVIOUS_BOOK_MD5 = "previous_book_md5";
	public static final String KEY_PREVIOUS_BOOK_LOC = "previous_book_location";

	public ApplicationPreferences() {
		super(Preferences.userNodeForPackage(ApplicationPreferences.class));
		createKeyList();
	}

	@Override
	public String getPreference(String key) {
		String preference = "";
		switch (key) {
		case KEY_DO_NOT_ASK_REFRESH_DISPLAY:
		case KEY_REFRESH_DISPLAY:
		case KEY_AUOTMATICALLY_OPEN_BOOK:
		case KEY_OPEN_PREVIOUS_LOC:
			preference = PreferencesManager.getStringPreference(key, "false",
					getPrefs());
			break;
		case KEY_SAVE_PREVIOUS_BOOKS:
			preference = PreferencesManager.getStringPreference(key, "true",
					getPrefs());
			break;
		case KEY_STATE_LOCATION:
			preference = PreferencesManager
					.getStringPreference(key, System.getProperty("user.home")
							+ File.separator + ".ebookr" + File.separator, getPrefs());
			break;
		case KEY_PREVIOUS_BOOK_PATH:
		case KEY_PREVIOUS_BOOK_MD5:
		case KEY_PREVIOUS_BOOK_LOC:
			preference = PreferencesManager.getStringPreference(key, "null",
					getPrefs());
			break;
		case KEY_DATE_FORMAT:
			preference = PreferencesManager.getStringPreference(key, DateSaveFormat.DAY_FIRST.getDisplay(), getPrefs());
		default:
			break;
		}
		return preference;
	}

	public boolean getBooleanPreference(String key) {
		boolean boolPref = false;
		String pref = getPreference(key);
		boolPref = Boolean.valueOf(pref);
		return boolPref;
	}

	public HashMap<String, String> getAllPreferences() {
		HashMap<String, String> map = new HashMap<String, String>(numberOfKeys);
		for (String key : getKeys())
			map.put(key, getPreference(key));
		return map;
	}
	
	private void createKeyList() {
		keyList = new ArrayList<String>();
		keyList.add(KEY_DO_NOT_ASK_REFRESH_DISPLAY);
		keyList.add(KEY_REFRESH_DISPLAY);
		keyList.add(KEY_SAVE_PREVIOUS_BOOKS);
		keyList.add(KEY_AUOTMATICALLY_OPEN_BOOK);
		keyList.add(KEY_OPEN_PREVIOUS_LOC);
		keyList.add(KEY_STATE_LOCATION);
		keyList.add(KEY_PREVIOUS_BOOK_PATH);
		keyList.add(KEY_PREVIOUS_BOOK_MD5);
		keyList.add(KEY_PREVIOUS_BOOK_LOC);
		keyList.add(KEY_DATE_FORMAT);
		numberOfKeys = keyList.size();
	}

	@Override
	public ArrayList<String> getKeys() {
		return keyList;
	}

}

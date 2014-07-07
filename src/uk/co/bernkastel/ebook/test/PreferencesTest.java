package uk.co.bernkastel.ebook.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Test;

import uk.co.bernkastel.ebook.model.prefs.ApplicationPreferences;
import uk.co.bernkastel.ebook.model.prefs.DisplayPreferences;
import uk.co.bernkastel.ebook.model.prefs.ReaderTextSize;
import uk.co.bernkastel.ebook.model.prefs.ReaderTextStyle;
import uk.co.bernkastel.ebook.model.prefs.ReaderTheme;

public class PreferencesTest {
	
	private DisplayPreferences display = new DisplayPreferences();
	private ApplicationPreferences appPrefs = new ApplicationPreferences();
		
	@Test
	public void testLoadDefaultDisplayPreferences() {
		display.clear();
		
		ReaderTheme theme = display.getReaderTheme();
		assertNotNull(theme);
		assertEquals(theme, ReaderTheme.WHITE);

		ReaderTextSize size = display.getTextSize();
		assertNotNull(size);
		assertEquals(size, ReaderTextSize.MEDIUM);		

		ReaderTextStyle style = display.getTextStyle();
		assertNotNull(style);
		assertEquals(style, ReaderTextStyle.SERIF);
	}
	
	@Test
	public void testSetDisplayPreferences() {
		display.setReaderTheme(ReaderTheme.PAPER);
		ReaderTheme theme = display.getReaderTheme();
		assertNotNull(theme);
		assertEquals(theme, ReaderTheme.PAPER);
		
		display.setTextSize(ReaderTextSize.EXTRA_LARGE);
		ReaderTextSize size = display.getTextSize();
		assertNotNull(size);
		assertEquals(size, ReaderTextSize.EXTRA_LARGE);	
		
		display.setTextStyle(ReaderTextStyle.CURSIVE);
		ReaderTextStyle style = display.getTextStyle();
		assertNotNull(style);
		assertEquals(style, ReaderTextStyle.CURSIVE);
	}
	
	@Test
	public void testDefaultApplicationPreferences() {
		appPrefs.clear();
		
		boolean preference = appPrefs.getBooleanPreference(ApplicationPreferences.KEY_DO_NOT_ASK_REFRESH_DISPLAY);
		assertFalse(preference);
		
		preference = appPrefs.getBooleanPreference(ApplicationPreferences.KEY_AUOTMATICALLY_OPEN_BOOK);
		assertFalse(preference);
		
		preference = appPrefs.getBooleanPreference(ApplicationPreferences.KEY_REFRESH_DISPLAY);
		assertFalse(preference);
		
		preference = appPrefs.getBooleanPreference(ApplicationPreferences.KEY_OPEN_PREVIOUS_LOC);
		assertFalse(preference);
		
		preference = appPrefs.getBooleanPreference(ApplicationPreferences.KEY_SAVE_PREVIOUS_BOOKS);
		assertTrue(preference);		
	}
	
	public void testSetApplicationPreferences() {
		String value = "true";
		
		appPrefs.setPreference(ApplicationPreferences.KEY_AUOTMATICALLY_OPEN_BOOK, value);
		String preference = appPrefs.getPreference(ApplicationPreferences.KEY_AUOTMATICALLY_OPEN_BOOK);
		assertEquals(preference, value);
		
		appPrefs.setPreference(ApplicationPreferences.KEY_DO_NOT_ASK_REFRESH_DISPLAY, value);
		preference = appPrefs.getPreference(ApplicationPreferences.KEY_DO_NOT_ASK_REFRESH_DISPLAY);
		assertEquals(preference, value);
		
		appPrefs.setPreference(ApplicationPreferences.KEY_OPEN_PREVIOUS_LOC, value);
		preference = appPrefs.getPreference(ApplicationPreferences.KEY_OPEN_PREVIOUS_LOC);
		assertEquals(preference, value);
		
		appPrefs.setPreference(ApplicationPreferences.KEY_REFRESH_DISPLAY, value);
		preference = appPrefs.getPreference(ApplicationPreferences.KEY_REFRESH_DISPLAY);
		assertEquals(preference, value);
		
		value = "false";
		
		appPrefs.setPreference(ApplicationPreferences.KEY_SAVE_PREVIOUS_BOOKS, value);
		preference = appPrefs.getPreference(ApplicationPreferences.KEY_SAVE_PREVIOUS_BOOKS);
		assertEquals(preference, value);
	}
	
	@After
	public void tearDown() {
		display.clear();
		appPrefs.clear();
	}

}

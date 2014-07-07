package uk.co.bernkastel.ebook.model.prefs;

import java.util.ArrayList;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import uk.co.bernkastel.ebook.controller.PreferencesManager;
import uk.co.bernkastel.ebook.view.MessageDialog;

/*
 * Preferences Node requires basic String key saving & loading for each preference
 * Concrete classes may provide their own individual implementations using Enumerated preferences
 */
public abstract class PreferencesNode {

	private Preferences prefs;

	public PreferencesNode(Preferences preferences) {
		prefs = preferences;
	}

	public abstract String getPreference(String key);

	public void setPreference(String key, String value) {
		if (getKeys().contains(key)) {
			PreferencesManager.setStringPreference(key, value, getPrefs());
		} else {
			System.err.println("Unable to save given preference key - not found in keys list.");
		}
	}

	public abstract ArrayList<String> getKeys();

	public Preferences getPrefs() {
		return prefs;
	}	

	public void clear() {
		try {
			getPrefs().clear();
		} catch (BackingStoreException e) {
			MessageDialog.showErrorDialog("Unable to clear preferences.");
			e.printStackTrace();
		}
	}
}

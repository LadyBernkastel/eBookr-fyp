package uk.co.bernkastel.ebook.controller;

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class PreferencesManager {

	public static void clear(Preferences prefs) {
		try {
			prefs.clear();
		} catch (BackingStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Allows a preference to be returned as an Enum value from the user's
	 * preferences.
	 * 
	 * @param enumClass
	 *            Class of the enum type to be returned.
	 * @param defaultValue
	 *            Default value if the value does not exist.
	 * @param key
	 *            Key representing the preference to be obtained.
	 * @param prefs
	 *            Preferences node to obtain the preference from.
	 * @return Enum type.
	 */
	public static <E extends Enum<E>> Enum<E> getEnumeratedPreference(
			Class<E> enumClass, E defaultValue, String key, Preferences prefs) {
		String preferenceKey = getStringPreference(key,
				defaultValue.toString(), prefs);
		Enum<E> preference = null;
		try {
			preference = Enum.valueOf(enumClass, preferenceKey);
		} catch (IllegalArgumentException e) {
			System.err.println("Unable to find enum value of class "
					+ enumClass.toString());
			System.err.println("Using default value: "
					+ defaultValue.toString());
			preference = defaultValue;
		}
		return preference;
	}

	/**
	 * Obtain a string value preference from the user's preferences.
	 * 
	 * @param key
	 *            Key representing the preference to be obtained.
	 * @param defaultValue
	 *            Default value if the value does not exist.
	 * @param prefs
	 *            Preferences node to obtain the preference from.
	 * @return String value of the preference.
	 */
	public static String getStringPreference(String key, String defaultValue,
			Preferences prefs) {
		return prefs.get(key, defaultValue);
	}

	/**
	 * Remove a preference from given preference node.
	 * 
	 * @param key
	 *            Key relating to the preference to be removed.
	 * @param prefs
	 *            Preferences node to remove from.
	 */
	public static void removePreference(String key, Preferences prefs) {
		prefs.remove(key);
	}

	/**
	 * Store a preference by passing in an enum value.
	 * 
	 * @param key
	 *            Key relating to the preference to store.
	 * @param value
	 *            Enum value of the preference.
	 * @param prefs
	 *            Preferences node to store in.
	 */
	public static <E extends Enum<E>> void setEnumeratedPreference(String key,
			E value, Preferences prefs) {
		setStringPreference(key, value.toString(), prefs);
	}

	/**
	 * Store a preference by passing in a string value.
	 * 
	 * @param key
	 *            Key relating to the preference to store.
	 * @param value
	 *            string value of the preference.
	 * @param prefs
	 *            Preferences node to store in.
	 */
	public static void setStringPreference(String key, String value,
			Preferences prefs) {
		prefs.put(key, value);
	}
}

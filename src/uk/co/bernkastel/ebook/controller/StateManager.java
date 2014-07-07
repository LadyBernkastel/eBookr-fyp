package uk.co.bernkastel.ebook.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;

import uk.co.bernkastel.ebook.model.prefs.ApplicationPreferences;
import uk.co.bernkastel.ebook.view.MessageDialog;

public abstract class StateManager {

	/**
	 * Obtain the current state location from application preferences.
	 * 
	 * @return
	 */
	public String getLocation() {
		ApplicationPreferences prefs = new ApplicationPreferences();
		return prefs.getPreference(ApplicationPreferences.KEY_STATE_LOCATION);
	}

	/**
	 * Move Location is called when user wishes to move existing state/bookmarks
	 * into a new directory.
	 * 
	 * Source directory is considered to be the default state file location.
	 * 
	 * @param destDir
	 *            Directory to move the state to.
	 */
	public void moveLocation(File destDir) {
		moveLocation(new File(getLocation()), destDir);
	}

	/**
	 * Move Location is called when user wishes to move existing state into a
	 * new directory.
	 * 
	 * @param srcDir
	 *            Directory state are being moved from.
	 * @param destDir
	 *            Directory to move the state to.
	 */
	public void moveLocation(File srcDir, File destDir) {
		try {
			if (!Files.isSameFile(srcDir.toPath(), destDir.toPath())) {
				// Create a file filter for files
				IOFileFilter suffixFilter = FileFilterUtils
						.suffixFileFilter(getExtension());
				// Only copy files within this directory
				FileUtils.copyDirectory(srcDir, destDir, suffixFilter);
			} else {
				MessageDialog
						.showInfoDialog("Source and destination locations are the same. No changes have been made.");
			}
		} catch (IOException e) {
			MessageDialog
					.showErrorDialog("Unable to copy existing bookmarks/highlights.\n"
							+ "You can still copy them manually in your file system. Bookmarks/highlights have a .ser extension.");
			e.printStackTrace();
		}
	}

	/**
	 * Retrieves a serialized object relating to the application's state.
	 * 
	 * @return Returns null if the file does not exist or the filename is not
	 *         correct. Returns an object if one is retrieved.
	 */
	protected Object getStateObject(String filename) {
		Object stateObject = null;

		if (filename.equals(""))
			return null;

		if (!stateFileExists(filename))
			return null;
		File stateFile = new File(getPathname(filename));

		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try {
			fis = new FileInputStream(stateFile);
			ois = new ObjectInputStream(fis);
			stateObject = ois.readObject();
		} catch (FileNotFoundException e) {
			System.err.println("Unable to find file " + stateFile.getPath()
					+ " when getting state file.");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.err.println("Class exception when getting state file");
			e.printStackTrace();
		} finally {
			try {
				ois.close();
				fis.close();
			} catch (IOException e) {
				System.err
						.println("Unable to close Object/File Input Streams when getting bookmarks");
				e.printStackTrace();
			}
		}
		return stateObject;
	}

	/**
	 * Saves the given object relating to the application's state to the user's
	 * file system.
	 * 
	 * @param stateObject
	 *            The object to serialize.
	 * @param filename
	 *            The filname for the file. This should be WITHOUT any path
	 *            entries or any extensions. For a file called
	 *            'user/bookmarks.ser' the filename should be 'bookmarks'.
	 */
	protected void saveStateObject(Object stateObject, String filename) {
		try {
			// Check if the ebookr storage location exists
			if (!new File(getLocation()).exists()) {
				// If not, check if the user's home dir exists
				if (!new File(System.getProperty("user.home")).exists())
					throw new IOException(
							"Unable to save state. User home directory does not exist");
				// If home dir exists & ebookr dir not, create ebookr dir
				Files.createDirectories(new File(getLocation()).toPath());
			}
		} catch (IOException e) {
			MessageDialog
					.showErrorDialog("Unable to save State - Unable to find user home directory.");
			e.printStackTrace();
		}

		// Create file to store UAC in
		File file = new File(getPathname(filename));
		try {
			FileOutputStream fos = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(stateObject);
			oos.close();
		} catch (IOException e) {
			MessageDialog
					.showErrorDialog("Unable to save State - Error creating Output Stream.");
			e.printStackTrace();
		}
	}

	/**
	 * Returns whether the state save location exists and whether the file with
	 * the given filename in the current state save location exists.
	 * 
	 * @param filename
	 * @return
	 */
	protected boolean stateFileExists(String filename) {
		if (!new File(getLocation()).exists())
			return false;
		if (!new File(getPathname(filename)).exists())
			return false;
		return true;
	}

	/**
	 * Returns the extension used for state files.
	 * 
	 * @return
	 */
	private String getExtension() {
		return ".ser";
	}

	/**
	 * Returns the filename for the given state save file.
	 * 
	 * @param name
	 * @return
	 */
	private String getPathname(String name) {
		return getLocation() + name + getExtension();
	}

}

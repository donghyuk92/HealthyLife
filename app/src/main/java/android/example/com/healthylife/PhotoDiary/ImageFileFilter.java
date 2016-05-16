package android.example.com.healthylife.PhotoDiary;

import java.io.File;
import java.io.FileFilter;

public class ImageFileFilter implements FileFilter {
	private final String[] okFileExtensions = new String[] { "jpg", "png", "gif", "jpeg" };

	/**
     * 
     */
	public ImageFileFilter() {
	}

	public boolean accept(File file) {
		for (String extension : okFileExtensions) {
			if (file.getName().toLowerCase().endsWith(extension) && file.length() != 0) {
				return true;
			}
		}
		return false;
	}

}
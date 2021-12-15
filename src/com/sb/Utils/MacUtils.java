package com.sb.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

import com.sb.an_dl.Utils;

public class MacUtils {
	private static final String TAG="MacUtils";
	public static void open(final File f) throws FileNotFoundException {
		if (!f.exists()) {
			throw new FileNotFoundException();
		}
		try {
			ProcessBuilder pb = new ProcessBuilder();
			pb.command("open", f.getAbsolutePath());
			if (pb.start().waitFor() != 0) {
				throw new FileNotFoundException();
			}
		} catch (Exception e) {
			Utils.e(TAG, e.getMessage(), true);
		}
	}

	public static void openFolder(String folder, String file) throws FileNotFoundException {
		if (file == null) {
			openFolder2(folder);
			return;
		}
		File f = new File(folder, file);
		if (!f.exists()) {
			throw new FileNotFoundException();
		}
		try {
			ProcessBuilder pb = new ProcessBuilder();
			Utils.l(TAG, "Opening folder: " + f.getAbsolutePath(),true);
			pb.command("open", "-R", f.getAbsolutePath());
			if (pb.start().waitFor() != 0) {
				throw new FileNotFoundException();
			}
		} catch (Exception e) {
			Utils.e(TAG, e.getMessage(), true);
		}
	}
	
	public static void openBrowser(String url){
		try {
			ProcessBuilder pb = new ProcessBuilder();
			Utils.l(TAG, "Opening URL: " + url,true);
			pb.command("open", url);
			if (pb.start().waitFor() != 0) {
				throw new FileNotFoundException();
			}
		} catch (Exception e) {
			Utils.e(TAG, e.getMessage(), true);
		}
	}

	private static void openFolder2(String folder) {
		try {
			ProcessBuilder builder = new ProcessBuilder();
			ArrayList<String> lst = new ArrayList<String>();
			lst.add("open");
			lst.add(folder);
			builder.command(lst);
			builder.start();
		} catch (Exception e) {
			Utils.e(TAG, e.getMessage(), true);
		}
	}
}

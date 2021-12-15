package com.sb.Utils;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import com.sb.an_dl.Utils;

public class WinUtils {
	private static String TAG = "WinUtils";
	public static void openFolder(String folder, String file) throws FileNotFoundException {
		try {
			File f = new File(folder, file);
			if (!f.exists()) {
				throw new FileNotFoundException();
			}
			ProcessBuilder builder = new ProcessBuilder();
			ArrayList<String> lst = new ArrayList<String>();
			lst.add("explorer");
			lst.add("/select,");
			lst.add(f.getAbsolutePath());
			builder.command(lst);
			Utils.e(TAG,"Cmd: " + lst,true);
			builder.start();
		} catch (IOException e) {
			Utils.e(TAG,e.toString(),true);
		}
	}
	public static void openBrowser(String s){
		if(Desktop.isDesktopSupported())
			{
			    try {
					Desktop.getDesktop().browse(new URI(s));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	}
}

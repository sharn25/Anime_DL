package com.sb.Utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import com.sb.an_dl.Utils;


public class LinuxUtils {
	private static String TAG = "LinuxUtils";
	public static void open(final File f) throws FileNotFoundException {
		if (!f.exists()) {
			throw new FileNotFoundException();
		}
		try {
			ProcessBuilder pb = new ProcessBuilder();
			pb.command("xdg-open", f.getAbsolutePath());
			pb.start();// .waitFor();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void openBrower(String url){
		
		try {
			if (url==null) {
				throw new FileNotFoundException();
			}
			ProcessBuilder pb = new ProcessBuilder();
			pb.command("xdg-open", url);
			pb.start();// .waitFor();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static Boolean isPackageInstalled(String pkg){
		Process process1;
		try {
			String script = "" + pkg;
			process1 = Runtime.getRuntime().exec(script);
			return true;
			/*
			BufferedReader errreader1 = new BufferedReader(new InputStreamReader(process1.getErrorStream()));
			String line1;
			Utils.l(TAG, "Checking package - " + script,true);
			while((line1 = errreader1.readLine())!=null) {
				Utils.l(TAG, "line: " + line1,true);
				line1 = line1.toLowerCase();
				if(line1.contains(pkg) && line1.contains("error")){
					return true;
				}
			}*/
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		//return false;
	}
}

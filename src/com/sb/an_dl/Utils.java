package com.sb.an_dl;

import java.awt.Component;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.sb.Utils.LinuxUtils;
import com.sb.Utils.MacUtils;
import com.sb.Utils.WinUtils;

public class Utils {
	private static final String TAG="Utils";
	
	 static int i = 0;
	 static Timer timer;
	

	public static void l(String tag, String msg) {
		//System.out.println("[LOG_IN] " + tag + " : " + msg);
	}
	
	public static void i(String tag, String msg, boolean z) {
		if(z) {
		System.out.println("[INFO] " + tag + " : " + msg);
		}
	}
	
	public static void l(String tag, String msg, boolean z) {
		if(false) {
			System.out.println("[LOG] " + tag + " : " + msg);
		}
	}

	public static void l(String tag, char in2) {
		// TODO Auto-generated method stub
		//System.out.println("[LOG] " +tag + " : " + in2);
	}

	public static void l(String tag, int var14) {
		// TODO Auto-generated method stub
		//System.out.println("[LOG] " +tag + " : " + var14);
	}

	public static void e(String tag, String err, boolean b) {
		// TODO Auto-generated method stub
		if(b) {
		System.out.println("[ERROR] " +tag + " : " + err);
		}
	}
	
	public static void popInfo(String msg) {
		JOptionPane.showMessageDialog(null, msg);
	}
	public static boolean popOption(Object i,Object[] msg) {
		return (JOptionPane.showConfirmDialog((Component) i, msg, "Confirm Box", 0) == 0);
	}
	
	public static void openFolder(String file, String folder) throws Exception {
		int os = detectOS();
		switch (os) {
		case StaticResource.WINDOWS:
			WinUtils.openFolder(folder, file);
			break;
		case StaticResource.LINUX:
			File f = new File(folder);
			LinuxUtils.open(f);
			break;
		case StaticResource.MAC:
			MacUtils.openFolder(folder, file);
			break;
		default:
			File ff = new File(folder);
			Desktop.getDesktop().open(ff);
		}
	}
	
	public static final int detectOS() {
		String os = System.getProperty("os.name").toLowerCase(Locale.ENGLISH);
		if (os.contains("mac") || os.contains("darwin") || os.contains("os x") || os.contains("os x")) {
			return StaticResource.MAC;
		} else if (os.contains("linux")) {
			return StaticResource.LINUX;
		} else if (os.contains("windows")) {
			return StaticResource.WINDOWS;
		} else {
			return -1;
		}
	}
	
	public static void timer(String s) {
		i=0;
		if(false) {
		  timer = new Timer();
	      timer.schedule(new TimerTask() {
	          @Override
	          public void run() {
	            l(s,i + " secs..",true);
	            i = i+1;
	          }
	      },0,1000);
		}
	      
	}
	
	public static long getDiskMemory(int s, String loc){
		long mem;
		File driveLoc = new File(loc);
		switch(s){
		case StaticResource.FREE_SPACE:
			return driveLoc.getFreeSpace();
		case StaticResource.TOTAL_SPACE:
			return driveLoc.getTotalSpace();
		case StaticResource.USED_SPACE:
			return driveLoc.getTotalSpace() - driveLoc.getFreeSpace();
		default:
			return 0;
		}
	}
	
	public static String formatMemorysize(long l){
		float size_bytes = l;
		String cnt_size;
		
		float size_kb = size_bytes /1024;
		float size_mb = size_kb / 1024;
		float size_gb = size_mb / 1024 ;
		
		 if (size_gb > 0){
			  
			    cnt_size = String.format("%.02f", size_gb) + "GB";
	        }else if(size_mb > 0){
			    cnt_size = String.format("%.02f", size_mb) + "MB";
	        }else{
	    	           cnt_size = size_kb + "KB";
	        }	     
		return cnt_size;		
	}
	public static void openBrowser(String url) {
		int os = detectOS();
		switch (os) {
		case StaticResource.WINDOWS:
			WinUtils.openBrowser(url);
			break;
		case StaticResource.LINUX:
			LinuxUtils.openBrower(url);
			break;
		case StaticResource.MAC:
			MacUtils.openBrowser(url);
			break;
		default:
			Utils.e(TAG, "Unable to open browser.", true);
			break;
		}
	}
	
	
	public static String getFFMPEGname(){
		int OS  = detectOS();
		String ffmpegFile  = "";
		switch(OS){
		case StaticResource.LINUX:
		case StaticResource.MAC:
			ffmpegFile = "ffmpeg";
			break;
		default: 
			ffmpegFile = "ffmpeg.exe";
			break;
		}
		return ffmpegFile;
	}
	
	public static String getVLCname(){
		int OS  = detectOS();
		String ffmpegFile  = "";
		switch(OS){
		case StaticResource.LINUX:
		case StaticResource.MAC:
			ffmpegFile = ".so";
			break;
		default: 
			ffmpegFile = ".dll";
			break;
		}
		return ffmpegFile;
	}
	
	public static String getFFMPEGErrormsg(){
		int OS = detectOS();
		switch(OS){
		case StaticResource.MAC:
			return "";
		case StaticResource.LINUX:
			return "Please install the FFMPEG libs.\nClick yes for continue";
		default:
			return "Please set the ffmpeg installation directory in settings tab for source 2 multi-resolution support.\nor\nClick yes to Download ffpmeg.zip and place in the same folder as Anime_Dl jar file.";
		}
	}
	
	public static int stringToInt(String s) {
		int i =0;
		try {
			i = Integer.parseInt(s);
		}catch(Exception e) {
			i = 0;
		}
		return i;
	}
	
}

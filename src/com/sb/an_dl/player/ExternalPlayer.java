package com.sb.an_dl.player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.sb.Utils.LinuxUtils;
import com.sb.an_dl.StaticResource;
import com.sb.an_dl.Utils;
import com.sb.downport.StringResource;
import com.sb.downport.animeconfig;

/**
 * Play videos in external player - VLC 
 * @author Sharn25
 * @version 1.0
 * @since 23-05-2021
 * @update 17-12-2021 - updated VLC command for MAC
 */
public class ExternalPlayer {
	private static final String TAG = "ExternalPlayer";
	
	public static boolean play(String url, String params){
		if(!animeconfig.isVLCInstalled){
			Utils.e(TAG, StringResource.getString("VLC_NOT_FOUND"), true);
			return false;
		}
		Utils.l(TAG, "Starting VLC - Video: " + url + " Params: "+ params, true);
		Process process1;
		try {
			String script = getVLCcmd() + " " + url + " " + params;
			process1 = Runtime.getRuntime().exec(script);			
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Utils.e(TAG, "Something wrong with VLC. Please ensure VLC access throught CMD/Terminal.", true);
			return false;
		}
	}
	
	private static String getVLCcmd() {
		int OS=Utils.detectOS();
		if(OS==StaticResource.LINUX) {
			return StaticResource.VLCLinux;
		}else if(OS==StaticResource.MAC) {
			return StaticResource.VLCMAC;
		}else {
			return "";
		}
	}
}

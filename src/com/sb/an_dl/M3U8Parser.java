package com.sb.an_dl;

import java.util.ArrayList;

public class M3U8Parser {
	private final static String PLAYLIST_TAG = "EXT-X-STREAM-INF";
	private final static String TAG = "M3U8Parser";
	
	public static String[] getM3U8url(String m3u8, String type) {
		Utils.l(TAG,"getM3U8url: " + m3u8,true);
		String[] m3u8url = new String[2];
		boolean hasSETURL=false;
		ArrayList<String> resolution = new ArrayList<String>();
		ArrayList<String> url = new ArrayList<String>();
		if(!isPlaylisttype(m3u8)) {
			Utils.l(TAG,"Not playlist",true);
			return null;
		}
		
		String[] lines = m3u8.split(" ");
		Utils.l(TAG,"Length: " + lines.length,true);
		for(int i=0;i<lines.length;i++) {
			String a = lines[i];
			Utils.l(TAG,"a: " + a,true);
			if(a.contains(PLAYLIST_TAG)){
				
				String res = "RESOLUTION=";
				int index = a.indexOf(res);
				a = a.substring(index);
				a = a.substring(res.length(),a.indexOf(","));
				resolution.add(hdptype(a));
				Utils.l(TAG,"hdpa: " + hdptype(a),true);
				Utils.l(TAG,"url: " + lines[i+1],true);
				url.add(lines[i+1]);
				i=i+1;
				
			}
		}
		if(resolution.contains(type)) {
			int i = resolution.indexOf(type);
			m3u8url[0] = (String) resolution.get(i);
			m3u8url[1] = (String) url.get(i);
			Utils.l(TAG,"Found hdptype: " + m3u8url[0]+ " url: " + m3u8url[1],true);
		}else {
			Utils.l(TAG,"not Found hdptype: " + m3u8url[0]+ " url: " + m3u8url[1],true);
			Utils.l(TAG,"resolution.size(): " + resolution.size(),true);
			for(int i=0;i<resolution.size();i++) {
				int chk = Integer.parseInt(resolution.get(i));
				Utils.l(TAG,"chk: " + chk+ " type: " + type,true);
				if(Integer.parseInt(type)<chk) {
					int j = resolution.indexOf(resolution.get(i));
					Utils.l(TAG,"1: "+"j: " + j,true);
					m3u8url[0] = (String) resolution.get(j);
					m3u8url[1] = (String) url.get(j);
				}else if(Integer.parseInt(type)>chk ) {
					//l("after2");
					if(!hasSETURL) {
						int j = resolution.indexOf(resolution.get(i));
						Utils.l(TAG,"2: "+"j: " + j,true);
						m3u8url[0] = (String) resolution.get(j);
						m3u8url[1] = (String) url.get(j);
						hasSETURL=true;
					}
				}
			}
			Utils.l(TAG,"Result hdptype: " + m3u8url[0]+ " url: " + m3u8url[1],true);
		}
		
		return m3u8url;
	}
	
	private static String hdptype(String s) {
		 String hd="";
	        if(s.contains("480")){
	            hd="480";
	        }else if(s.contains("720")){
	            hd ="720";
	        }else if(s.contains("1080")){
	            hd = "1080";
	        }else if(s.contains("360")){
	            hd = "360";
	        }else if(s.contains("240")){
	            hd = "240";
	        }
	        return hd;
	}
	
	private static boolean isPlaylisttype(String s) {
		if(s.contains(PLAYLIST_TAG)){
		return true;
	}
	return false;
	}
}

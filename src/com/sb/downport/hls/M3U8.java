package com.sb.downport.hls;

import java.io.*;
import java.util.*;
import java.net.*;

import com.sb.an_dl.Utils;
import com.sb.downport.AnimeUtil;

public class M3U8 {
	private static String TAG = "M3U8";
	public static ArrayList<String> getURIList(ArrayList<String> list, String playlist_url) throws Exception {
		ArrayList<String> list2 = new ArrayList<String>();
		String base_url = "";
		URI uri = new URI(playlist_url);
		for (int i = 0; i < list.size(); i++) {
			String item = list.get(i);
			String item_url = null;
			if (item.startsWith("/")) {
				if (AnimeUtil.isNullOrEmpty(base_url)) {
					base_url = uri.getScheme() + "://" + uri.getHost() + ""
							+ (uri.getPort() > 0 ? ":" + uri.getPort() : "");
				}
				item_url = base_url + item;
			} else if (item.startsWith("http://") || item.startsWith("https://")) {
				item_url = item;
			} else {
				int index=playlist_url.lastIndexOf('/');
				item_url=playlist_url.substring(0, index)+"/";
				item_url += item;
//				if (XDMUtil.isNullOrEmpty(base_url)) {
//					base_url = uri.getScheme() + "://" + uri.getHost() + ""
//							+ (uri.getPort() > 0 ? ":" + uri.getPort() : "");
//				}
//				item_url = base_url + "/";
//				String[] seg = uri.getPath().split("/");
//				for (int j = 0; j < seg.length - 1; j++) {
//					item_url += (seg[j]+"/");
//				}
//				item_url += item;
			}
			list2.add(item_url);

		}
		return list2;
	}
	
	public static boolean isEncrypted(InputStream s) {
		try {
			BufferedReader r = new BufferedReader(new InputStreamReader(s));
			// StreamReader r = new StreamReader(s, Encoding.UTF8);
			boolean expect = false;
			while (true) {
				String line = r.readLine();
				if (line == null)
					break;
				String highline = line.toUpperCase().trim();
				if (highline.length() < 1) {
					continue;
				}
				if (highline.startsWith("#EXT-X-KEY")) {
					Utils.l(TAG+"_parse","Encrypted Content",true);
					//throw new Exception("encrypted content");
					expect=true;
				}
			}
			return expect;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean parse(InputStream s, ArrayList<String> list, int[] pl, ArrayList<String> props) {
		try {
			BufferedReader r = new BufferedReader(new InputStreamReader(s));
			// StreamReader r = new StreamReader(s, Encoding.UTF8);
			boolean expect = false;
			while (true) {
				String line = r.readLine();
				if (line == null)
					break;
				String highline = line.toUpperCase().trim();
				if (highline.length() < 1)
					continue;
				if (expect) {
					list.add(line);
					expect = false;
				}
				if (highline.startsWith("#EXT-X-STREAM-INF")) {
					pl[0] = 1;
					expect = true;
					String[] arr = highline.split(":");
					if (arr.length > 1) {
						props.add(arr[1].trim());
					}
				}
				if (highline.startsWith("#EXTINF")) {
					pl[0] = 0;
					expect = true;
					String[] arr = highline.split(":");
					if (arr.length > 1) {
						props.add(arr[1].trim());
					}
				}
				if (highline.startsWith("#EXT-X-KEY")) {
					Utils.l(TAG+"_parse","Encrypted Content",true);
					//throw new Exception("encrypted content");
				}
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}

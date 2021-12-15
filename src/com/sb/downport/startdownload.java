package com.sb.downport;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import com.sb.an_dl.Utils;
import com.sb.downport.ConnectionManager;
import com.sb.downport.DownloadList;
import com.sb.downport.DownloadListItem;
import com.sb.downport.animeconfig;

public class startdownload {
	
	static animeconfig config;
	static DownloadList list = null;

public static void main(String[] args) {
	UUID id = UUID.randomUUID();
	String url="http://s320.ve.vc/data/320/42606/287914/My%20Boyz%20-%20Raj%20Brar%20(DjPunjab.Com).mp3";
	String name="my.mp3";
	String folder="C:\\Users\\Sharanjit Singh\\Desktop\\Data\\temp\\startdownload";
	String tempdir="C:\\Users\\Sharanjit Singh\\Desktop\\Data\\temp\\startdownload\\temp";
	String userAgent="";
	String referer="";
	ArrayList cookies=null;
	File fAppDir = new File(System.getProperty("user.home"), ".animedl");
	File fAppdirconfig=new File(fAppDir,".anime_dlconfig");
	list=new DownloadList(fAppDir.getAbsolutePath());
	String user="";
	String pass="";
	config=animeconfig.load(fAppdirconfig);
	config.destdir=folder;
	config.tempdir=tempdir;
	
	 Utils.l("startdownload","start...");
	 ConnectionManager mgr = new ConnectionManager(id, url, name, folder, tempdir, userAgent, referer, cookies, config);
	 DownloadListItem item = new DownloadListItem();
	 list.add(item);
	 Utils.l("startdownload","itme add");
	 mgr.setCredential(user, pass);
	 Utils.l("startdownload","USER and PASS");
     item.user = user;
     item.pass = pass;
     item.mgr = mgr;
     item.isDASH = false;
     item.filename = name;
     item.url = url;
     item.q = false;
     item.dateadded = item.lasttry = (new SimpleDateFormat("MMM dd")).format(new Date());
     item.id = id;
     item.saveto = folder;
     //item.icon = IconUtil.getIcon(XDMUtil.findCategory(item.filename));
     item.userAgent = userAgent;
     item.referer = referer;
     if(cookies != null) {
        item.cookies = new ArrayList();
        item.cookies.addAll(cookies);
     }
     item.type = "Anime";
     list.downloadStateChanged();
     item.state = 10;
    // mgr.dwnListener = this;
     try {
         mgr.start();
      } catch (Exception var15) {
         var15.printStackTrace();
      }
	 
}
public void startdownload(animeconfig condfig) {
	
}

}

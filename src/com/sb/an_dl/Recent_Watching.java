package com.sb.an_dl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Observable;

import javax.swing.ImageIcon;

import com.sb.downport.animeconfig;

public class Recent_Watching extends Observable implements Serializable {

	private String anime_title;
	private String anime_video_title;
	private int video_location;
	
	public String getAnime_video_title() {
		return anime_video_title;
	}

	public void setAnime_video_title(String anime_video_title) {
		this.anime_video_title = anime_video_title;
	}

	public int getVideo_location() {
		return video_location;
	}

	public void setVideo_location(int i) {
		this.video_location = i;
	}
	private ImageIcon anime_icon;
	private String anime_url;
	private String epno;
	public String getEpno() {
		return epno;
	}

	public void setEpno(String epno) {
		this.epno = epno;
	}
	private static Recent_Watching rec_watch;
	
	public static Recent_Watching get_inten() {
		if(rec_watch==null) {
			Utils.l("Recent_Watching", "Object_null. Creating one", true);
			rec_watch =  new Recent_Watching();
			return rec_watch;
		}else {
			
			return rec_watch;
		}
	}
	
	public static void set_inten(Recent_Watching obj) {
		rec_watch = obj;
	}
	public String getAnime_title() {
		return anime_title;
	}
	public void setAnime_title(String anime_title) {
		this.anime_title = anime_title;
	}
	
	
	public String getAnime_url() {
		return anime_url;
	}
	public void setAnime_url(String anime_url) {
		this.anime_url = anime_url;
	}
	
	public static void save(File file) {
	      ObjectOutputStream out = null;

	      try {
	         out = new ObjectOutputStream(new FileOutputStream(file));
	         out.writeObject(rec_watch);
	         out.close();
	      } catch (Exception var3) {
	         var3.printStackTrace();
	      }

	      rec_watch.setChanged();
	      rec_watch.notifyObservers();
	   }
	public static Recent_Watching load(File file) {
	      ObjectInputStream in = null;

	      try {
	         in = new ObjectInputStream(new FileInputStream(file));
	         Recent_Watching e = (Recent_Watching)in.readObject();
	       
	         return e;
	      } catch (Exception var5) {
	         var5.printStackTrace();

	         try {
	            in.close();
	         } catch (Exception var4) {
	            ;
	         }

	         return new Recent_Watching();
	      }
	   }
}

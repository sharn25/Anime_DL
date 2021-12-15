package com.sb.downport;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;
import javax.swing.Icon;

import com.sb.an_dl.StaticResource;
import com.sb.an_dl.Utils;
import com.sb.downport.AnimeUtil;
import com.sb.downport.ConnectionManager;
import com.sb.downport.DownloadInfo;
import com.sb.downport.animeconfig;;

public class DownloadListItem implements Serializable, Comparable {
   private static final long serialVersionUID = -4925098929484510725L;
   public ArrayList cookies;
   public String filename;
   public boolean q;
   public int state;
   public UUID id;
   public String status;
   public int dtype;
   public String timeleft;
   public String transferrate;
   public String lasttry;
   public String description;
   public String dateadded;
   public String saveto;
   public String type;
   public String url;
   public String size;
   public String tempdir = "";
   public String referer;
   public String userAgent;
   public String user;
   public String pass;
   public transient Icon icon;
   public transient IDownloader mgr;
   //transient XDMDownloadWindow window;
   public long date_created = System.currentTimeMillis();
   public String sdwnld;
   public String sprg;
   public String prime_url;
   public String second_url;
   public String prime_file;
   public String second_file;
   public String prime_dest_dir;
   public String second_dest_dir;
   public boolean secondary_done;
   public boolean isDASH = false;
   public long totalDASHSize;
   public boolean isMerging;
   public long length;
   public long dwnld;

   public void updateData(DownloadInfo info) {
      this.status = info.status;
      this.timeleft = info.eta;
      this.transferrate = info.speed;
      Utils.l("ConnectionMaannager","Update Downloadlistitem");
      this.url = info.url;
      this.size = info.length;
      this.type = info.category;
      this.state = info.state;
      this.sdwnld = info.downloaded;
      this.sprg = info.progress;
      if(info.state != 50 && info.state != 40 && info.state != 30) {
         this.status = this.status + " " + this.sprg + "% of " + this.size;
      } else {
         this.mgr = null;
         //this.window = null;
         if(info.state == 50) {
            this.q = false;
            this.status = StringResource.getString("DOWNLOAD_COMPLETE") + " " + this.size;
         } else {
            this.status = StringResource.getString("STOPPED") + " " + this.size;
         }
      }

   }

   public int compareTo(Object it) {
      DownloadListItem item = (DownloadListItem)it;
      int c = animeconfig.sortField;
      switch(c) {
      case 0:
         return this.date_created > item.date_created?1:-1;
      case 1:
         return AnimeUtil.nvl(this.size).compareToIgnoreCase(AnimeUtil.nvl(item.size));
      case 2:
         return AnimeUtil.nvl(this.filename).compareToIgnoreCase(AnimeUtil.nvl(item.filename));
      case 3:
         return this.getExt(this.filename).compareToIgnoreCase(this.getExt(item.filename));
      default:
         return 0;
      }
   }

   String getExt(String name) {
      try {
         String[] e = name.split("\\.");
         return e.length > 1?e[e.length - 1]:"";
      } catch (Exception var3) {
         var3.printStackTrace();
         return "";
      }
   }
}

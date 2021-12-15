package com.sb.downport;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

import com.sb.an_dl.StaticResource;
import com.sb.an_dl.Utils;
import com.sb.downport.AnimeUtil;
import com.sb.downport.DownloadListItem;
import com.sb.downport.IXDMConstants;
import com.sb.downport.animeconfig;;

public class DownloadList implements IXDMConstants, Serializable {
   private static final long serialVersionUID = -3009294778243929872L;
   public ArrayList list = new ArrayList();
   String type;
   int state;
   String appdir;
   public transient String searchStr = "";

   public DownloadList(String appdir) {
      this.appdir = appdir;
      this.loadDownloadList();
   }

   boolean isMatched(DownloadListItem item) {
      return !AnimeUtil.isNullOrEmpty(this.searchStr)?item.filename.contains(this.searchStr):true;
   }

   public DownloadListItem get(int index) {
      int k = 0;

      for(int i = 0; i < this.list.size(); ++i) {
         DownloadListItem item = (DownloadListItem)this.list.get(i);
         if(this.sameType(item.type) && this.sameState(item.state) && this.isMatched(item)) {
            if(k == index) {
               return item;
            }

            ++k;
         }
      }

      return null;
   }

   public DownloadListItem getByID(UUID id) {
      for(int i = 0; i < this.list.size(); ++i) {
         DownloadListItem item = (DownloadListItem)this.list.get(i);
         if(item.id != null && item.id.equals(id)) {
            return item;
         }
      }

      return null;
   }
   
   public DownloadListItem getByfilename(String filename) {
	      for(int i = 0; i < this.list.size(); ++i) {
	         DownloadListItem item = (DownloadListItem)this.list.get(i);
	         if(item.filename != null && item.filename.equals(filename)) {
	            return item;
	         }
	      }

	      return null;
	   }

   public void remove(DownloadListItem item) {
      this.list.remove(item);
   }

   void remove(int index) {
      int k = 0;

      for(int i = 0; i < this.list.size(); ++i) {
         DownloadListItem item = (DownloadListItem)this.list.get(i);
         if(this.sameType(item.type) && this.sameState(item.state) && this.isMatched(item)) {
            if(k == index) {
               this.list.remove(i);
            }

            ++k;
         }
      }

   }

   public void add(DownloadListItem item) {
      this.list.add(0, item);
   }

   public int size() {
      int k = 0;

      for(int i = 0; i < this.list.size(); ++i) {
         DownloadListItem item = (DownloadListItem)this.list.get(i);
         if(this.sameType(item.type) && this.sameState(item.state) && this.isMatched(item)) {
            ++k;
         }
      }

      return k;
   }

   public void setType(String type) {
      this.type = type;
   }

   void setState(int state) {
      this.state = state;
   }

   public int getIndex(DownloadListItem item) {
      int k = 0;

      for(int i = 0; i < this.list.size(); ++i) {
         DownloadListItem itm = (DownloadListItem)this.list.get(i);
         if(this.sameType(itm.type) && this.sameState(itm.state) && this.isMatched(itm)) {
            if(item.equals(itm)) {
               return k;
            }

            ++k;
         }
      }

      return -1;
   }

   boolean sameType(String type) {
      return this.type == null?true:this.type.equalsIgnoreCase(type);
   }

   boolean sameState(int state) {
      return this.state == 0?true:(this.state == 50?this.state == state:state != 50);
   }

   public void downloadStateChanged() {
      this.saveDownloadList();
   }

   private synchronized void saveDownloadList() {
      ObjectOutputStream out = null;

      try {
         out = new ObjectOutputStream(new FileOutputStream(new File(this.appdir, ".anime_dl")));
         out.writeObject(this.list);
         Utils.l("DownloadList","savelist");
      } catch (Exception var11) {
         var11.printStackTrace();
      } finally {
         try {
            out.close();
         } catch (Exception var10) {
            ;
         }

      }

   }

   private void loadDownloadList() {
      ObjectInputStream in = null;

      try {
         in = new ObjectInputStream(new FileInputStream(new File(this.appdir, ".anime_dl")));
         this.list = (ArrayList)in.readObject();
        
         Utils.l("DownloadList","loadlist");
      } catch (Exception var11) {
         var11.printStackTrace();
      } finally {
         try {
            in.close();
         } catch (Exception var10) {
            ;
         }

      }

      if(this.list == null) {
         this.list = new ArrayList();
      }

      for(int i = 0; i < this.list.size(); ++i) {
         DownloadListItem item = (DownloadListItem)this.list.get(i);
         item.icon = StaticResource.getIcon("vedio.png");
         if(item.state != 50 && item.state != 30) {
            item.state = 40;
            item.status = StringResource.getString("STOPPED") + " " + (item.sprg == null?"---":item.sprg) + " of " + (item.size == null?"---":item.size);
         }
      }

      this.sort();
   }

   public void sort() {
      Collections.sort(this.list);
      if(!animeconfig.sortAsc) {
         Collections.reverse(this.list);
      }

   }
}

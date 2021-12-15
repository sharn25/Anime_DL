package com.sb.downport;

import java.util.ArrayList;

import com.sb.downport.AnimeUtil;

public class DownloadIntercepterInfo {
   public String url;
   public String ua;
   public ArrayList cookies;
   public String referer;

   public void copyTo(DownloadIntercepterInfo info) {
      info.url = new String(this.url.toCharArray());
      if(!AnimeUtil.isNullOrEmpty(this.ua)) {
         info.ua = new String(this.ua.toCharArray());
      }

      if(!AnimeUtil.isNullOrEmpty(this.referer)) {
         info.referer = new String(this.referer.toCharArray());
      }

      if(this.cookies != null) {
         info.cookies = new ArrayList();
         info.cookies.addAll(this.cookies);
      }

   }
}

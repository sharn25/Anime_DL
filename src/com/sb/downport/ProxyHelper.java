package com.sb.downport;

import java.net.URL;

import com.sb.downport.AnimeUtil;
import com.sb.downport.WebProxy;
import com.sb.downport.animeconfig;

public class ProxyHelper {
   public static WebProxy getProxyForURL(String url, animeconfig config) {
      if(url == null) {
         return null;
      } else {
         String host;
         int port;
         WebProxy wp;
         if(url.indexOf("http://") == 0 && config.useHttpProxy) {
            host = config.httpProxyHost;
            port = config.httpProxyPort;
            if(!AnimeUtil.isNullOrEmpty(host)) {
               if(port < 0) {
                  port = 80;
               }

               wp = new WebProxy();
               wp.host = host;
               wp.port = port;
               return wp;
            }
         }

         if(url.indexOf("https://") == 0 && config.useHttpsProxy) {
            host = config.httpsProxyHost;
            port = config.httpsProxyPort;
            if(!AnimeUtil.isNullOrEmpty(host)) {
               if(port < 0) {
                  port = 80;
               }

               wp = new WebProxy();
               wp.host = host;
               wp.port = port;
               return wp;
            }
         }

         return null;
      }
   }

   public static WebProxy getProxyForURL(URL url, animeconfig config) {
      return getProxyForURL("" + url, config);
   }
}

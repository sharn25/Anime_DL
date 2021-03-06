package com.sb.downport;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import com.sb.an_dl.Utils;
import com.sb.downport.AnimeUtil;
import com.sb.downport.Authenticator;
import com.sb.downport.Connection;
import com.sb.downport.ConnectionManager;
import com.sb.downport.Credential;
import com.sb.downport.HelpListener;
import com.sb.downport.http.XDMHttpClient2;

public class HelperConnection implements Runnable {
   long start;
   long length;
   animeconfig config;
   String url;
   XDMHttpClient2 client;
   InputStream in;
   ByteArrayOutputStream out;
   HelpListener listerner;
   Connection c;
   boolean stop = false;
   String fileName;
   Credential credential;
   int timeout;
   ConnectionManager mgr;

   public HelperConnection(animeconfig config, long start, long length, String url, HelpListener l, Connection c, String fileName, Credential credential, ConnectionManager mgr) {
      this.config = config;
      this.start = start;
      this.length = length;
      this.url = url;
      this.listerner = l;
      this.c = c;
      this.fileName = fileName;
      this.credential = credential;
      this.mgr = mgr;
   }

   void start() {
      Thread t = new Thread(this);
      t.start();
   }

   void stop() {
      this.stop = true;
   }

   public void run() {
      try {
         this.client = new XDMHttpClient2(this.config);
         this.client.connect(this.url);
         if(this.stop) {
            this.close();
            return;
         }

         this.client.addCookies(this.mgr.cookieList);
         this.client.addRequestHeaders("referer", this.mgr.referer);
         this.client.addRequestHeaders("user-agent", this.mgr.userAgent);
         this.client.addRequestHeaders("range", "bytes=" + this.start + "-");
         if(this.credential == null) {
            this.credential = Authenticator.getInstance().getCredential(this.client.host);
         }

         if(this.credential != null) {
            this.client.user = this.credential.user;
            this.client.pass = this.credential.pass;
         }

         this.client.sendRequest();
         if(this.stop) {
            this.close();
            return;
         }

         int e = this.client.getResponseCode();
         Utils.l("ConnectionMaannager","Helper RESPONSE " + e);
         if(e != 206) {
            throw new Exception("Invalid RESPONSE CODE");
         }

         this.in = this.client.in;
         this.out = new ByteArrayOutputStream();
         byte[] buf = new byte[this.config.tcpBuf];
         long dwn = 0L;

         do {
            if(this.stop) {
               this.close();
               return;
            }

            int rem = (int)(this.length - dwn);
            int x;
            if(buf.length > rem) {
               x = this.in.read(buf, 0, rem);
            } else {
               x = this.in.read(buf);
            }

            if(this.stop) {
               this.close();
               return;
            }

            if(x == -1) {
               throw new Exception("UNEXPECTED EOF");
            }

            this.out.write(buf, 0, x);
            dwn += (long)x;
         } while(dwn < this.length);

         if(this.listerner != null) {
            if(this.stop) {
               this.close();
               return;
            }

            this.listerner.helpComplete(this, this);
         }

         this.close();
      } catch (Exception var7) {
    	  Utils.l("ConnectionMaannager","Error IN HELPER: " + var7);
         var7.printStackTrace();
         this.close();
      }

   }

   void close() {
	   Utils.l("ConnectionMaannager","closing helper conn. " + this.stop);

      try {
         this.client.close();
      } catch (Exception var4) {
         ;
      }

      try {
         this.in.close();
      } catch (Exception var3) {
         ;
      }

      try {
         this.out.close();
      } catch (Exception var2) {
         ;
      }

   }
}

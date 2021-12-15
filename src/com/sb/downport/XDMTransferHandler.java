package com.sb.downport;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.swing.JComponent;
import javax.swing.TransferHandler;

import com.sb.an_dl.Utils;
import com.sb.downport.DownloadIntercepterInfo;
import com.sb.downport.DownloadStateListner;

public class XDMTransferHandler extends TransferHandler {
   private static final long serialVersionUID = 8226815435490071235L;
   DownloadStateListner mgr;

   public XDMTransferHandler(DownloadStateListner mgr) {
      this.mgr = mgr;
   }

   public boolean canImport(JComponent comp, DataFlavor[] transferFlavors) {
      return true;
   }

   public boolean importData(JComponent comp, Transferable t) {
      DataFlavor[] flavors = t.getTransferDataFlavors();
      if(flavors == null) {
         return false;
      } else if(flavors.length < 1) {
         return false;
      } else {
         try {
            int e = 0;

            String data;
            while(true) {
               if(e >= flavors.length) {
                  return false;
               }

               DataFlavor flavor = flavors[e];
               Utils.l("XDMTransfer",flavor.getMimeType());
               if(flavor.isFlavorTextType()) {
                  data = this.getData(t.getTransferData(flavor));
                  Utils.l("XDMTransfer",data);

                  try {
                     new URL(data);
                     break;
                  } catch (Exception var8) {
                     ;
                  }
               }

               ++e;
            }

            if(this.mgr != null) {
               DownloadIntercepterInfo info = new DownloadIntercepterInfo();
               info.url = data;
               this.mgr.interceptDownload(info);
            }

            return true;
         } catch (Exception var9) {
            return false;
         }
      }
   }

   String getData(Object obj) {
      try {
         StringBuffer data;
         int x;
         if(obj instanceof Reader) {
            Reader e1 = (Reader)obj;
            data = new StringBuffer();

            while(true) {
               x = e1.read();
               if(x == -1) {
                  return data.toString();
               }

               data.append((char)x);
            }
         } else if(!(obj instanceof InputStream)) {
            return obj instanceof String?obj.toString():null;
         } else {
            InputStream e = (InputStream)obj;
            data = new StringBuffer();

            while(true) {
               x = e.read();
               if(x == -1) {
                  return data.toString();
               }

               data.append((char)x);
            }
         }
      } catch (Exception var5) {
         return null;
      }
   }
}

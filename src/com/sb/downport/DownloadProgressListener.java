package com.sb.downport;

import com.sb.downport.DownloadInfo;

public interface DownloadProgressListener {
   void update(DownloadInfo var1);

   boolean isValidWindow();
}

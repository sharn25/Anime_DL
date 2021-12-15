package com.sb.downport.hls;

import com.sb.downport.InvalidContentException;

public interface IHLSProgress {
	void setcombineStatus(String s);
	void setDownloaded(long downloaded);
    void downloadFailed(BasicDownloader bd);
    void downloadComplete(BasicDownloader bd) throws InvalidContentException;
}

package com.sb.downport.hls;

import java.io.*;
import java.util.*;

//import javax.rmi.CORBA.Util;

import com.sb.an_dl.FFmpegHelper;
import com.sb.an_dl.StaticConstants;
import com.sb.an_dl.Utils;
import com.sb.downport.AnimeUtil;
import com.sb.downport.Credential;
import com.sb.downport.DownloadInfo;
import com.sb.downport.DownloadProgressListener;
import com.sb.downport.DownloadStateListner;
import com.sb.downport.IDownloader;
import com.sb.downport.IXDMConstants;
import com.sb.downport.InvalidContentException;
import com.sb.downport.animeconfig;


public class HLSDownloader implements IHLSProgress, IDownloader {
	String index_file_url;
	ArrayList<HLS_SEGMENT> segments;
	String out_file;
	String out_dir;
	String temp_dir;
	String m3u8_file;
	animeconfig config;
	String state_file;
	Credential credentials;
	Object lock_obj;
	boolean overwrite;
	boolean isEncryped;
	long downloaded;
	int state = IXDMConstants.STOPPED;
	String status = "connecting...";
	long total_size = -1;
	String ua, referer;
	ArrayList<String> cookies;
	DownloadProgressListener prgListener;
	DownloadStateListner mgr;
	UUID id;
	private final static String TAG = "HLSDownloader";
	private final static boolean enableLogs = false;

	public HLSDownloader(UUID id, String url, String file, String dest_dir, String temp_dir, String referer, String ua,
			ArrayList<String> cookies, animeconfig config) {
		Utils.l(TAG,"Referer: " + referer,enableLogs);
		this.id = id;
		this.index_file_url = url;
		this.out_file = file;
		this.out_dir = dest_dir;
		this.temp_dir = temp_dir;
		this.ua = ua;
		this.referer = referer;
		this.cookies = cookies;
		this.config = config;
		this.state_file = ".state";
		this.lock_obj = new Object();
	}

	public Credential getCreditential() {
		return this.credentials;
	}

	public UUID getID() {
		return id;
	}

	public void setProgressListener(DownloadProgressListener pl) {
		this.prgListener = pl;
	}

	public void setStateListener(DownloadStateListner mgr) {
		this.mgr = mgr;
	}

	// public void SetProgressListener(IDownloadProgressListener p) {
	// prgListener = p;
	// if (prgListener != null) {
	// prgListener.InitProgressListener(this, config.maxConn);
	// }
	// }

	// public void SetManager(IDownloadManager mgr) {
	// this.mgr = mgr;
	// }

	public void setTempDir(String temp_dir) {
		this.temp_dir = temp_dir;
	}

	public String getTempDir() {
		return this.temp_dir;
	}

	public int getDownloadType() {
		return 1;
	}

	public void setOverriteIfFileExists(boolean overwrite) {
		this.overwrite = overwrite;
	}

	public long getFileSize() {
		return total_size;
	}

	public String getSize() {
		return total_size > 0 ? AnimeUtil.getFormattedLength(total_size) : "---";
	}

	public void setCredential(String user, String pass, String host) {
	}

	public void setCredential(String user, String pass) {
	}

	// public void SetParams(DownloadInterceptInfo p) {
	// this.info = p;
	// }

	// public DownloadInterceptInfo GetParams() {
	// return info;
	// }
	//
	// public IDownloadProgressListener GetProgressListener() {
	// return this.prgListener;
	// }

	public void setTempdir(String tempdir) {
		this.temp_dir = tempdir;
	}

	public String getTempdir() {
		return this.temp_dir;
	}

	public String getFileName() {
		return this.out_file;
	}

	public void setFileName(String file) {
		out_file = file;
	}

	public void setDestdir(String destdir) {
		this.out_dir = destdir;
	}

	public String getDestdir() {
		return this.out_dir;
	}

	public void setUrl(String url) {
		this.index_file_url = url;
	}

	public String getUrl() {
		return this.index_file_url;
	}

	public void saveDownload() {
		saveState();
	}

	boolean manifest_avail = false;

	private boolean restoreState() {
		synchronized (lock_obj) {
			DataInputStream br = null;
			try {
				// MessageBox.Show(this.temp_dir);
				this.state_file = new File(this.temp_dir, ".state").getAbsolutePath();
				br = new DataInputStream(new FileInputStream(new File(this.temp_dir, ".state")));
				this.index_file_url = br.readUTF();
				this.m3u8_file = br.readUTF();
				// this.out_file =
				br.readUTF();
				this.out_dir = br.readUTF();
				this.downloaded = br.readLong();
				this.duration = br.readInt();
				if (br.readBoolean()) {
					this.ua = br.readUTF();
				}
				if (br.readBoolean()) {
					this.referer = br.readUTF();
				}
				if (br.readBoolean()) {
					cookies = new ArrayList<String>();
					int count = br.readInt();
					for (int i = 0; i < count; i++) {
						this.cookies.add(br.readUTF());
					}
				}

				int c = br.readInt();
				manifest_avail = true;
				segments = new ArrayList<HLS_SEGMENT>();
				for (int i = 0; i < c; i++) {
					HLS_SEGMENT segment = new HLS_SEGMENT();
					segment.url = br.readUTF();
					boolean hasfile = br.readBoolean();
					if (hasfile) {
						segment.tempfile = br.readUTF();
					}
					segment.sequence = br.readInt();
					segment.done = br.readBoolean();
					segments.add(segment);
				}

				br.close();
				Utils.l(TAG+"_restoreState","m3u8_file: " + m3u8_file, enableLogs);
				return true;
			} catch (Exception e) {
				try {
					br.close();
				} catch (Exception e2) {
				}
			}
			return false;
		}
	}

	private void saveState() {
		Utils.l(TAG+"_saveState","m3u8_file: " + m3u8_file, enableLogs);
		synchronized (lock_obj) {
			DataOutputStream fs = null;
			try {
				if (segments != null && segments.size() > 0) {
					fs = new DataOutputStream(new FileOutputStream(new File(this.temp_dir, ".state")));
					fs.writeUTF(this.index_file_url);
					fs.writeUTF(this.m3u8_file);
					fs.writeUTF(out_file);
					fs.writeUTF(out_dir);
					fs.writeLong(downloaded);
					fs.writeInt((int) duration);
					boolean hasUA = (!AnimeUtil.isNullOrEmpty(this.ua)),
							hasReferer = (!AnimeUtil.isNullOrEmpty(this.referer)), hasCookies = (!(this.cookies == null));
					fs.writeBoolean(hasUA);
					if (hasUA) {
						fs.writeUTF(this.ua);
					}
					fs.writeBoolean(hasReferer);
					if (hasReferer) {
						fs.writeUTF(this.referer);
					}
					fs.writeBoolean(hasCookies);
					if (hasCookies) {
						fs.writeInt(this.cookies.size());
						for (int i = 0; i < this.cookies.size(); i++) {
							String str = this.cookies.get(i);
							fs.writeUTF(str);
						}
					}
					fs.writeInt(this.segments.size());
					for (int i = 0; i < segments.size(); i++) {
						HLS_SEGMENT s = segments.get(i);
						fs.writeUTF(s.url);
						boolean hasFile = (!AnimeUtil.isNullOrEmpty(s.tempfile));
						fs.writeBoolean(hasFile);
						if (hasFile) {
							fs.writeUTF(s.tempfile);
						}
						fs.writeInt(s.sequence);
						fs.writeBoolean(s.done);
					}
					fs.close();
				}
			} catch (Exception e) {
				try {
					fs.close();
				} catch (Exception e2) {
					Utils.e(TAG+"_saveState", "Unbale to Close file.", true);
				}
			}
		}
	}

	public void setDownloadListener(DownloadStateListner pl) {
		this.mgr = pl;
	}

	public void setOverwrite(boolean b) {
		this.overwrite = b;
	}

	double duration = 0.0f;

	private void parseManifest(String tmpfile) throws Exception {
		int pl[] = new int[1];
		ArrayList<String> list = new ArrayList<String>();
		ArrayList<String> props = new ArrayList<String>();
		FileInputStream fs = new FileInputStream(tmpfile);
		
		if (!M3U8.parse(fs, list, pl, props)) {
			fs.close();
			throw new Exception("unable to parse index file");
		}
			
		
		fs.close();
		
		if (list.size() < 1) {
			throw new Exception("no segment in index file");
		}

		if (pl[0] == 1) {
			throw new InvalidContentException(
					"This file only contains video information, its not exactly a video file\nPlease select some other video from 'DOWNLOAD VIDEO' panel");
		}

		Utils.l(TAG+"parseManifest","total " + list.size() + " segments",enableLogs);
		Utils.l(TAG+"parseManifest","index_file_url: " + index_file_url,enableLogs);

		list = M3U8.getURIList(list, index_file_url);

		String op = "";

		segments = new ArrayList<HLS_SEGMENT>();

		for (int i = 0; i < list.size(); i++) {
			HLS_SEGMENT hs = new HLS_SEGMENT();
			hs.url = list.get(i);

			// hs.tempfile = Path.Combine(this.temp_dir, i+".ts");
			hs.sequence = i;
			hs.done = false;
			segments.add(hs);
			op += hs.url + "\n";
		}

		duration = 0.0f;

		if (props != null) {
			for (int i = 0; i < list.size(); i++) {
				String prop = props.get(i).trim();
				String[] arr = prop.split(",");
				String sduration = arr[0];
				double fdur = 0.0f;
				try {
					fdur = Double.parseDouble(sduration);
					segments.get(i).duration = (float) fdur;
					duration += fdur;
				} catch (Exception e) {
					Utils.e(TAG+"_parseManifest", "Error while parseing manifent.", true);
				}
			}
		}

		Utils.l(TAG+"_parseManifest",op, enableLogs);
	}

	public void resume() {
		if (state == IXDMConstants.DOWNLOADING)
			return;
		state = IXDMConstants.DOWNLOADING;
		if (!restoreState()) {
			start();
			return;
		}
		blist = new ArrayList<BasicDownloader>();
		Thread t = new Thread() {
			public void run() {
				downloadSegments();
			}
		};
		t.start();
		
	}

	public int getState() {
		return state;
	}

	public void start() {
		Utils.l("HLSDownloader_start", "Starting HLSDownloader",enableLogs);
		state = IXDMConstants.DOWNLOADING;
		String tmpfile = AnimeUtil.getTempFile(this.temp_dir).getAbsolutePath();
		Utils.l("BasicDownloader", "tempdir: " + tmpfile.toString(),enableLogs);
		manifest_avail = false;
		BasicDownloader bd = new BasicDownloader(index_file_url, tmpfile, credentials, this.referer, this.ua,
				this.cookies, config, this);
		blist = new ArrayList<BasicDownloader>();
		blist.add(bd);
		bd.start();
		// MessageBox.Show("started");
	}

	ArrayList<BasicDownloader> blist = null;

	private void downloadSegments() {
		synchronized (lock_obj) {
			if (checkFinished()) {
				Utils.l("HLSDownloader_downloadSegments", "assembling: " + checkFinished(), enableLogs);
				assemble();
				return;
			}
			if (blist != null) {
				// for (int i = blist.size(); i < config.maxConn; i++)
				// {
				for (int j = 0; j < segments.size(); j++) {
					HLS_SEGMENT hs = segments.get(j);
					if (hs.bd == null) {
						if (!hs.done) {
							if (blist.size() < config.maxConn) {
								BasicDownloader bd = new BasicDownloader(hs.url,
										new File(temp_dir, UUID.randomUUID().toString() + ".ts").getAbsolutePath(),
										credentials, this.referer, this.ua, this.cookies, config, this);
								blist.add(bd);
								hs.bd = bd;
								bd.start();
							}
						}
					}
				}
				// }
			}
		}
	}

	public void setDownloaded(long downloaded) {
		Utils.l("HLSDownloader_setDownload", "From basicdownloader(run()) " + downloaded , enableLogs);
		this.downloaded += downloaded;
		updated();
	}
	public void setcombineStatus(String s) {
		status = "FFmpeg: Appending all parts..."+s+ " Downloaded";
		updated();
	}

	public void downloadFailed(BasicDownloader bd) {
		synchronized (lock_obj) {
			if (state == IXDMConstants.FAILED)
				return;
			status = "Download failed due to error";
			if (bd.lasterror != null && bd.lasterror instanceof InvalidContentException) {
				status = bd.lasterror.getMessage();
				Utils.e(TAG+"_downloadFailed", "Status: " + status, true);
			} else {
				Utils.e(TAG+"_downloadFailed", "" + bd.lasterror, true);
			}
			state = IXDMConstants.FAILED;
			failed();
		}
	}

	boolean stop = false;

	public void stop() {
		status = "Stopped";
		stop = true;
		if (manifest_avail) {
			saveState();
		}
		if (blist != null) {
			for (int i = 0; i < blist.size(); i++) {
				blist.get(i).Stop();
			}
		}
		saveState();
		state = IXDMConstants.STOPPED;
		if (mgr != null) {
			mgr.downloadPaused(this.id);
		}
		updated();
	}

	public void failed() {
		if (manifest_avail) {
			saveState();
		}
		if (blist != null) {
			for (int i = 0; i < blist.size(); i++) {
				blist.get(i).Stop();
			}
		}
		saveState();
		state = IXDMConstants.FAILED;
		if (mgr != null) {
			mgr.downloadFailed(id);
		} 
		updated();
	}

	String category;

	public void downloadComplete(BasicDownloader bd) throws InvalidContentException {
		Utils.l("HLSDownloader_downloadComplete", "Returned from basicdownloader(run())" , enableLogs);
		synchronized (lock_obj) {
			if (!manifest_avail) {
				m3u8_file = bd.GetFile();
				temp_dir = new File(temp_dir, UUID.randomUUID().toString()).getAbsolutePath();
				Utils.l("HLSDownloader_download_completed", "temp_dir_path : " + temp_dir.toString(), enableLogs);
				try {
					parseManifest(bd.GetFile());
				} catch (Exception e) {
					throw new InvalidContentException(e.getMessage());
				}
				File f = new File(bd.GetFile());
				Utils.l(TAG+"_downloadCompleted","manifest downloaded",enableLogs);
				manifest_avail = true;
				new File(temp_dir).mkdirs();
				if (!overwrite) {
					out_file = AnimeUtil.getUniqueFileName(out_dir, out_file);
				}
				category = "ANIME";
				int state2 = state;
				state = IXDMConstants.REDIRECTING;
				updated();
				state = state2;
				updated();
				if (mgr != null) {
					mgr.downloadConfirmed(id, this);
				}
				f.delete();
				Utils.l(TAG+"_downloadCompleted","Download confirmed", enableLogs);
			}
			Utils.l("HLSDownloader_downloadCompleted", "Normal download : " + bd.GetFile(), enableLogs);
			blist.remove(bd);
			if (segments != null) {
				for (int i = 0; i < segments.size(); i++) {
					if (segments.get(i).bd == bd) {
						HLS_SEGMENT segment = segments.get(i);
						segment.bd = null;
						segment.done = true;
						segment.tempfile = bd.GetFile();
						break;
					}
				}
			}
			saveState();
			if (checkFinished()) {
				assemble();
				return;
			}
			downloadSegments();
			saveState();
			status = "downloading...";
		}
	}

	private boolean checkFinished() {
		boolean finished = true;
		if (segments != null) {
			for (int i = 0; i < segments.size(); i++) {
				if (!segments.get(i).done) {
					finished = false;
					break;
				}
			}
		}
		return finished;
	}

	long tgap2 = Integer.MIN_VALUE;
	long tgap = tgap2;

	public void updated() {
		long t = System.currentTimeMillis();
		if (state == IXDMConstants.FAILED || state == IXDMConstants.COMPLETE || state == IXDMConstants.STOPPED
				|| state == IXDMConstants.REDIRECTING || state == IXDMConstants.ASSEMBLING) {
			updated2();
			tgap = t;
			if (state != IXDMConstants.COMPLETE) {
				tgap2 = Integer.MIN_VALUE;
			}
		} else {
			if ((t - tgap) > 1000) {
				tgap = t;
				updated2();
			}
		}
		if ((t - tgap2) > 5000) {
			tgap2 = t;
			try {
				saveState();
			} catch (Exception e) {
				Utils.e(TAG+"_updated", "Unable to complete saveState", true);
			}
		}
	}

	long prevdownload;
	long startTime;

	public void updated2() {
		try {
			DownloadInfo info = new DownloadInfo();
			info.path = new File(temp_dir);
			info.url = getUrl();
			info.file = out_file;
			String stat = "downloading...";
			long dwnld = downloaded;
			info.rlen = -1;
			info.rdwn = downloaded;
			info.length = (duration > 0.0f ? AnimeUtil.hms1((int) duration) + "" : "---");
			info.downloaded = AnimeUtil.getFormattedLength(downloaded);
			if(state == IXDMConstants.ASSEMBLING) {
				info.downloaded = "";
			}
			
			if (state == IXDMConstants.COMPLETE) {
				info.length = info.downloaded;
			}
			long diff = dwnld - prevdownload;
			long time = System.currentTimeMillis();
			long dt = (time - startTime);
			float rt = 0;
			// System.out.println("diff: "+diff+" dt: "+dt+" rate:
			// "+(((float)diff
			// / dt) * 1000));
			if (dt != 0) {
				rt = ((float) diff / dt) * 1000;
				// System.out.println("****Real speed: " +
				// XDMUtils.GetFormattedLength(rate));
			}
			startTime = time;
			info.speed = AnimeUtil.getFormattedLength(rt) + "/sec";
			prevdownload = dwnld;
			info.eta = "---";

			info.resume = "Yes";
			info.status = this.status;//stat;
			if (segments != null) {
				// float prg = 0;
				int c = 0;
				for (int i = 0; i < segments.size(); i++) {
					if (segments.get(i).done) {
						c++;
					}
				}

				info.prg = (segments.size() > 0 ? ((c * 100) / segments.size()) : 0);
				info.progress = info.prg + "";
			}
			
			info.state = this.state;
			info.msg = this.status;
			info.category = category;
			info.tempdir = temp_dir;

			if (prgListener != null) {
				if (prgListener.isValidWindow()) {
					prgListener.update(info);
				}
			}
			if (mgr != null) {
				// XDMUtils.KeepSystemAlive();

				mgr.updateManager(id, info);
			}
		} catch (Exception e) {
		}
	}

	boolean isEncrypted(byte[] b) {
		return false;
	}

	void assemble() {
		// FileOutputStream outStream = null;
		status = "FFmpeg: Appending all parts...";
		state = IXDMConstants.ASSEMBLING;
		updated();
		try {
			new File(out_dir).mkdirs();

			if (!overwrite) {
				out_file = AnimeUtil.getUniqueFileName(out_dir, out_file);
			}
			updated();

			ArrayList<String> list = new ArrayList<String>();
			for (int i = 0; i < segments.size(); i++) {
				HLS_SEGMENT segment = segments.get(i);
				list.add(new File(segment.tempfile).getName());// segment.tempfile);
			}
			
			File fOutFile = new File(out_dir, out_file);
			if(FFmpegHelper.hasFFmpeg()) {
				
				FileInputStream fs = new FileInputStream(m3u8_file);
			if(M3U8.isEncrypted(fs)) {
				if (!FFmpegHelper.combineHLSEncrypted(list, fOutFile.getAbsolutePath(), temp_dir,this.m3u8_file,this)) {
					throw new Exception("ffmpeg error - encrypted.");
				}
				fs.close();
			}else {
				if (!FFmpegHelper.combineHLS(list, fOutFile.getAbsolutePath(), temp_dir,this)) {
					throw new Exception("ffmpeg error");
				}
			}
			}else {
				Utils.l("HLSDownlooder_FFmpeg", "FFmpeg not found",enableLogs); 
			}

			
			if (mgr != null) {
				mgr.downloadComplete(this.id);
			}
			for (int i = 0; i < segments.size(); i++) {
				HLS_SEGMENT segment = segments.get(i);
				new File(segment.tempfile).delete();
			}
			try {
				new File(temp_dir).delete();
			} catch (Exception e) {
			}
			status = "Finished";
			state = IXDMConstants.COMPLETE;
			updated();
			return;
		} catch (FileNotFoundException e) {
			status = "Partially downloaded files have been deleted or modified\n\n";
			state = IXDMConstants.FAILED;
			updated();
			if (mgr != null) {
				mgr.downloadFailed(this.id);
			}
		} catch (IOException e) {
			status = "Output folder is write protected or full.\n" + "Try another location using 'Save As' option\n\n"
					+ e.getMessage();
			// System.Windows.Forms.MessageBox.Show(e + "");
			state = IXDMConstants.FAILED;
			updated();
			if (mgr != null) {
				mgr.downloadFailed(this.id);
			}
			Utils.e(TAG+"_assemble", "Assemble error: " + e, true);
		} catch (Exception e) {
			status = "Download failed due to internal error\n\nDetails:\n" + e.getMessage();
			state = IXDMConstants.FAILED;
			updated();
			if (mgr != null) {
				mgr.downloadFailed(this.id);
			}
			Utils.e(TAG+"_assemble", "Assemble error: " + e.getMessage(), true);
		}
	}

	public int getType() {
		return StaticConstants.HLS;
	}
}

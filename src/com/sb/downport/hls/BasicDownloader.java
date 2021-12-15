package com.sb.downport.hls;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import com.sb.downport.Credential;
import com.sb.downport.animeconfig;

import com.sb.downport.InvalidContentException;

import com.sb.downport.http.XDMHttpClient2;
import com.sb.an_dl.Utils;
import com.sb.downport.AnimeUtil;

public class BasicDownloader implements Runnable {
	String url;
	String file;
	private XDMHttpClient2 conn;
	private String user_agent, referer;
	private ArrayList<String> cookies;
	private animeconfig config;
	// private Credential credential;
	private InputStream stream;
	// private long size;
	private IHLSProgress prg;
	private Thread t;
	// private boolean started = false;
	private OutputStream fs;
	private boolean stopflag = false;
	private long file_length=0;
	
	private static String TAG = "BasicDownloader";
	private final static boolean enableLogs = false;

	public BasicDownloader(String url, String file, Credential auth, String referer, String user_agent,
			ArrayList<String> cookies, animeconfig config, IHLSProgress prg) {
		Utils.l(TAG,"Referer: " + referer, enableLogs);
		this.url = url;
		this.file = file;
		//credential = auth;
		this.referer = referer;
		this.user_agent = user_agent;
		this.cookies = cookies;
		this.config = config;
		this.prg = prg;
	}

	public void start() {
		Utils.l("BasicDownloader", "Thread Started",enableLogs);
		t = new Thread(this);
		//started = true;
		t.start();
	}

	public void Stop() {
		stopflag = true;
		try {
			fs.close();
		} catch (Exception e) {
		}
	}

	private void connect() throws InvalidContentException, Exception {
		int maxretry = 10;
		int count = 0;
		while (!stopflag) {
			try {
				conn = new XDMHttpClient2(config);
				conn.connect(url);
				if (!AnimeUtil.isNullOrEmpty(referer)) {
					conn.addRequestHeaders("referer", referer);
				}
				if (!AnimeUtil.isNullOrEmpty(user_agent)) {
					conn.addRequestHeaders("user-agent", user_agent);
				}
				if (cookies != null) {
					conn.addCookies(cookies);
				}
				conn.sendRequest();

				if (stopflag)
					return;

				stream = conn.in;
				//size = conn.getContentLength();
				int rc = conn.getResponseCode();
				file_length = conn.getContentLength();
				
				if (!(rc == 200 || rc == 206)) {
					throw new Error("Error downloading video");
				}
				return;
			} catch (Exception e) {
				e.printStackTrace();
				conn.close();
			} catch (Error e) {
				conn.close();
				e.printStackTrace();
				throw new InvalidContentException("download error");
			}
			Thread.sleep(1000);
			if (stopflag)
				return;
			count++;
			if (count > maxretry)
				throw new InvalidContentException("download error");
		}
	}

	public String GetFile() {
		return file;
	}

	byte[] buf;

	long oldRead = 0;

	long _startTime = System.currentTimeMillis();// Environment.TickCount;
	long _bytesCount = 0;
	long read;
	long downloaded;
	float rate;
	long time = System.currentTimeMillis();

	public float GetDownloadRate() {
		return rate;
	}

	public void run() {
		// MessageBox.Show("download");
		// long dw = fs.Length;
		// prg.SetDownloaded(dw);
		Utils.l(TAG+"_run", "Starting BaseDownloader.",enableLogs);
		try {
			while (!stopflag) {
				// MessageBox.Show("connecting...");
				connect();
				buf = new byte[8192];
				int total_readed=0;
				fs = new FileOutputStream(file);
				try {
					read = 0;
					if (stopflag)
						break;
					while (!stopflag) {
						if(total_readed==file_length) {
							Utils.l(TAG+"_run", "END OF FILE BREAKING.", enableLogs);
							break;
						}
						int x = stream.read(buf, 0, buf.length);
						total_readed = total_readed+x;
						if (x == -1)
							break;
						fs.write(buf, 0, x);
						downloaded += x;
						_bytesCount += x;
						read += x;
						// Throttle speed
						if (x > 0) {
							long maxBPS = config.maxBPS / config.maxConn;
							if (maxBPS > 0) {
								// Start Throttling
								long _elapsedTime = System.currentTimeMillis() - _startTime;

								if (_elapsedTime > 0) {
									long bps = _bytesCount * 1000 / _elapsedTime;

									if (bps > maxBPS) {
										long _waitTime = _bytesCount * 1000 / maxBPS;
										long _toWait = _waitTime - _elapsedTime;
										if (_toWait > 1) {
											try {
												Utils.l("BasicDwonloader","------------------Sleeping for : " + _toWait + " ms", enableLogs);
												Thread.sleep((int) _toWait);
											} catch (Exception e) {
											}
											long _diff = System.currentTimeMillis() - _startTime;
											if (_diff > 1000) {
												_bytesCount = 0;
												_startTime = System.currentTimeMillis();
											}
										}
									}
								}
							}
						}
						long currentTime = System.currentTimeMillis();// System.DateTime.currentTimeMillis();
						long tdiff = currentTime - time;
						long diff = read - oldRead;
						if (((int) (tdiff / 1000)) > 0) {
							rate = ((float) diff / tdiff) * 1000;
							oldRead = read;
							time = currentTime;
						}
						// dw += x;
						prg.setDownloaded(x);
					}
					Utils.l("BasicDownloader", "fs closing." ,enableLogs);
					fs.close();
					try {
						stream.close();
					} catch (Exception e) {
					}
					try {
						conn.close();
					} catch (Exception e) {
					}
					// System.Windows.Forms.MessageBox.Show("done");
					if (!stopflag) {
						prg.downloadComplete(this);
					}
					break;
				} catch (InvalidContentException ee) {
					try {
						fs.close();
					} catch (Exception e) {
					}
					try {
						stream.close();
					} catch (Exception e) {
					}
					try {
						conn.close();
					} catch (Exception e) {
					}
					throw ee;
				} catch (Exception e2) {
					// MessageBox.Show(e+"");
					try {
						fs.close();
					} catch (Exception e) {
					}
					try {
						stream.close();
					} catch (Exception e) {
					}
					try {
						conn.close();
					} catch (Exception e) {
					}
				}
			}
		} catch (Exception exx) {
			try {
				fs.close();
			} catch (Exception e) {
			}
			try {
				stream.close();
			} catch (Exception e) {
			}
			try {
				// response.close();
				conn.close();
			} catch (Exception e) {
			}
			if (!stopflag) {
				this.lasterror = exx;
				prg.downloadFailed(this);
			}
		}
	}

	Exception lasterror;
}

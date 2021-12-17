package com.sb.an_dl;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

import com.sb.downport.AnimeUtil;
import com.sb.downport.animeconfig;
import com.sb.downport.hls.IHLSProgress;

public class FFmpegHelper {
	
	private static String TAG = "FFmpegHelper";

	public static boolean hasFFmpeg() {
		return true;/*
		boolean ffmpeg = new File(System.getProperty("user.home"), "ffmpeg.exe").exists();
		if (!ffmpeg) {
			String jarfile = AnimeUtil.getJarPath();
			if (jarfile != null) {
				ffmpeg = new File(jarfile, "ffmpeg").exists();
			}
		}
		return ffmpeg;*/
	}

	public static String getFFmpegPath() {
		String jarfile = AnimeUtil.getJarPath();
		if (jarfile == null) {
			return new File(System.getProperty("user.home"), "ffmpeg").getAbsolutePath();
		} else {
			File ffFile = new File(jarfile, "ffmpeg");
			if (!ffFile.exists()) {
				return new File(System.getProperty("user.home"), "ffmpeg").getAbsolutePath();
			}
			return ffFile.getAbsolutePath();
		}
	}
	
	public static void getkeyFile(final File filename, final String urlString)
	        throws MalformedURLException, IOException {
	    BufferedInputStream in = null;
	    FileOutputStream fout = null;
	    try {
	        in = new BufferedInputStream(new URL(urlString).openStream());
	        fout = new FileOutputStream(filename);

	        final byte data[] = new byte[1024];
	        int count;
	        while ((count = in.read(data, 0, 1024)) != -1) {
	            fout.write(data, 0, count);
	        }
	    } finally {
	        if (in != null) {
	            in.close();
	        }
	        if (fout != null) {
	            fout.close();
	        }
	    }
	}

	public static boolean combineHLSEncrypted(ArrayList<String> list, String target_file, String tempdir, String m3u8File, IHLSProgress pgr) {
		if(!animeconfig.isFFMPEGexist){
			Utils.e(TAG, "FFMPEG not installed. Please check.", true);
			return false;
		}
		try {
			File tmpFile = new File(System.getProperty("user.home"), ".animedl");
			File lstFile = new File(tempdir, "ffmpeg_" + System.currentTimeMillis() + ".txt");
			File key_File = new File(tempdir, "key_file_" + System.currentTimeMillis() + ".key");
			FileOutputStream fs = new FileOutputStream(lstFile);
			for (int i = 0; i < list.size(); i++) {
				fs.write(("file " + tempdir.replace("\\","/")+"/" + list.get(i) + "\r\n").getBytes());
			}
			fs.close();
			FileInputStream fs1 = new FileInputStream(m3u8File);
			FileInputStream f_input = new FileInputStream(lstFile);
			String outdir = tempdir;
			FileOutputStream outfile = null;
			 BufferedReader r = new BufferedReader(new InputStreamReader(fs1));
			 BufferedReader r1 = new BufferedReader(new InputStreamReader(f_input));
			 File newM3U8File = new File(outdir, "m3u8_" + System.currentTimeMillis() + ".m3u8");
			 outfile = new FileOutputStream(newM3U8File);
			 while(true) {
				 String line = r.readLine();
					if (line == null)
						break;
					String highline = line.trim();
					if (highline.length() < 1) {
						continue;
					}
					//l("highline_line: " + highline);
					if (highline.startsWith("#EXT-X-KEY")) {
						
						int index = highline.indexOf("\"");
						String key_file = highline.substring(index+1);
						key_file = key_file.substring(0,key_file.indexOf("\""));
						getkeyFile(key_File,key_file);
						highline = highline.substring(0,index+1);
						
						highline = highline + key_File.getAbsolutePath().replace("\\","/") + "\"";
						//throw new Exception("encrypted content");
					
						Utils.l(TAG+"combineHLSEncrypted","highline_added : " + highline,true);
					}
					if(highline.startsWith("http") || highline.startsWith("HTTP")) {
						String read = r1.readLine();
						if (read == null)
							break;
						String readline = read.trim();
						if (readline.length() < 1) {
							continue;
						}
						readline = readline.replace("file ", "");
						highline = readline;
						//Utils.l(TAG+"combineHLSEncrypted","http updated : " + highline,true);
					}
					highline = highline + "\n";
					outfile.write(highline.getBytes());
			 }
			 outfile.close();
			 f_input.close();
			 fs1.close();
			
			
			
			 String ffmpeg = "";
			 String script = "";
				if(Utils.detectOS()==StaticResource.LINUX){
					ffmpeg = Utils.getFFMPEGname();
					script = ffmpeg + " -f concat -safe 0 -i " + lstFile.getAbsolutePath()
					+ " -acodec copy -vcodec copy " + target_file + " -y";// +"\r\n";
				}else if(Utils.detectOS()==StaticResource.MAC){
					ffmpeg = Fetcher.getFFMPEGpath() + File.separator + Utils.getFFMPEGname();
					script = ffmpeg + " -f concat -safe 0 -i " + lstFile.getAbsolutePath()
					+ " -acodec copy -vcodec copy " + target_file + " -y";// +"\r\n";
				}
				else{
					ffmpeg = "\"" + Fetcher.getFFMPEGpath() + File.separator+ Utils.getFFMPEGname() + "\"";
					script = ffmpeg + " -f concat -safe 0 -i \"" + lstFile.getAbsolutePath()
					+ "\" -acodec copy -vcodec copy \"" + target_file + "\" -y";// +"\r\n";
				}
				Utils.l("FFmpegHelper_cobineHLS", "ffmpeg_file: " + ffmpeg,true);
		
			Process process = Runtime.getRuntime().exec(script);
			BufferedReader errreader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
			String line;
			String time = "time=";
			while((line = errreader.readLine())!=null) {
				//Utils.i("FFMPEG_combine", line,true);
				int d = line.indexOf(time);
				
				if(d!=-1) {
					d = d + time.length();
					line = line.substring(d);
					int dot = line.indexOf(".");
					
					if(dot!=-1) {
						line = line.substring(0,dot);
						Utils.l("FFMPEG_combine", "line: " + line,true);
						pgr.setcombineStatus(line);
					}
				}
			}
			int result =process.waitFor();
			// ffScript.delete();
			// lstFile.delete();
			if(result==0) {
				
			}
			
			System.out.println("ffmpeg exit status: " + result);
			return result == 0;
		} catch (Exception e) {
			e.printStackTrace();
			//outfile.close();
			return false;
		}
	}
	public static boolean combineHLS(ArrayList<String> list, String target_file, String tempdir,IHLSProgress pgr) {
		if(!animeconfig.isFFMPEGexist){
			Utils.e(TAG, "FFMPEG not installed. Please check.", true);
			return false;
		}
		try {
			
			File tmpFile = new File(System.getProperty("user.home"), ".animedl");
			File lstFile = new File(tempdir, "ffmpeg_" + System.currentTimeMillis() + ".txt");
			FileOutputStream fs = new FileOutputStream(lstFile);
			for (int i = 0; i < list.size(); i++) {
				fs.write(("file " + tempdir.replace("\\","/")+"/" + list.get(i) + "\r\n").getBytes());
			}
			fs.close();
			String ffmpeg ="";
			String script = "";
			if(Utils.detectOS()==StaticResource.LINUX){
				ffmpeg = Utils.getFFMPEGname();
				script = ffmpeg + " -f concat -safe 0 -i " + lstFile.getAbsolutePath()
				+ " -acodec copy -vcodec copy " + target_file + " -y";// +"\r\n";
			}else if(Utils.detectOS()==StaticResource.MAC){
				ffmpeg = Fetcher.getFFMPEGpath() + File.separator + Utils.getFFMPEGname();
				script = ffmpeg + " -f concat -safe 0 -i " + lstFile.getAbsolutePath()
				+ " -acodec copy -vcodec copy " + target_file + " -y";// +"\r\n";
			}
			else{
				ffmpeg = "\"" + Fetcher.getFFMPEGpath() + File.separator+ Utils.getFFMPEGname() + "\"";
				script = ffmpeg + " -f concat -safe 0 -i \"" + lstFile.getAbsolutePath()
				+ "\" -acodec copy -vcodec copy \"" + target_file + "\" -y";// +"\r\n";
			}
		
			Utils.l(TAG+"_cobineHLS", "Script: " + script, true);
			Process process = Runtime.getRuntime().exec(script);
			BufferedReader errreader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
			String line;
			String time = "time=";
			while((line = errreader.readLine())!=null) {
				Utils.i("FFMPEG_combine", line,true);
				int d = line.indexOf(time);
				if(d!=-1) {
					d = d + time.length();
					line = line.substring(d);
					int dot = line.indexOf(".");
					if(dot!=-1) {
						line = line.substring(0,dot);
						Utils.l("FFMPEG_combine", "line: " + line,true);
						pgr.setcombineStatus(line);
					}
				}
			}
			
			int r = process.waitFor();
			
			
			// ffScript.delete();
			// lstFile.delete();
			
			System.out.println("ffmpeg exit status: " + r);
			return r == 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean combineDASH(String file1, String file2, String target_file) {
		try {
			String ffmpeg = getFFmpegPath();
			String script = ffmpeg + " -i \"" + file1 + "\" -i \"" + file2 + "\" -acodec copy -vcodec copy \""
					+ target_file + "\" -y  -loglevel quiet >/dev/null 2>&1\n";
			File tmpFile = new File(System.getProperty("user.home"), ".xdm");
			File ffScript = new File(tmpFile, "ffmpeg_" + System.currentTimeMillis() + ".sh");
			FileOutputStream fs = new FileOutputStream(ffScript);
			fs.write(script.getBytes());
			fs.close();

			ProcessBuilder pb = new ProcessBuilder("sh", ffScript.getAbsolutePath());
			int r = pb.start().waitFor();
			ffScript.delete();
			System.out.println("ffmpeg exit status: " + r);
			return r == 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}

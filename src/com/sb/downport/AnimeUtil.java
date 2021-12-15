package com.sb.downport;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.UUID;

import com.sb.an_dl.StaticResource;
import com.sb.downport.IXDMConstants;

public class AnimeUtil implements IXDMConstants {
	
	private static String jarPath = null;
	
	public static String getUniqueFileName(String dir, String f) {
	      for(File target = new File(dir, f); target.exists(); target = new File(dir, f)) {
	         String name = getWithoutExt(target.getName());
	         int index = name.lastIndexOf(95);
	         String prefix = name;
	         int count = 0;
	         if(index > 0 && index < name.length() - 1) {
	            try {
	               count = Integer.parseInt(name.substring(index + 1));
	               prefix = name.substring(0, index);
	            } catch (Exception var8) {
	               ;
	            }
	         }

	         ++count;
	         String ext = getExt(target.getName());
	         f = prefix + "_" + count + ext;
	      }

	      return f;
	   }
	
	static String getWithoutExt(String file) {
	      int index = file.lastIndexOf(".");
	      return index < 0?file:file.substring(0, index);
	   }

	   static String getExt(String file) {
	      try {
	         int e = file.lastIndexOf(".");
	         return e < 0?"":file.substring(e);
	      } catch (Exception var2) {
	         return "";
	      }
	   }
	   public static String getFormattedLength(double length) {
		      return length < 0.0D?"---":(length > 1048576.0D?String.format("%.1f MB", new Object[]{Float.valueOf((float)length / 1048576.0F)}):(length > 1024.0D?String.format("%.1f KB", new Object[]{Float.valueOf((float)length / 1024.0F)}):String.format("%d B", new Object[]{Integer.valueOf((int)length)})));
		   }

		   public static String getETA(double length, float rate) {
		      if(length == 0.0D) {
		         return "0 second.";
		      } else if(length >= 1.0D && rate > 0.0F) {
		         int sec = (int)(length / (double)rate);
		         return hms(sec);
		      } else {
		         return "---";
		      }
		   }
		   public static String getFileName(String uri) {
			      try {
			         if(uri == null) {
			            return "FILE";
			         } else if(!uri.equals("/") && uri.length() >= 1) {
			            int e = uri.lastIndexOf("/");
			            String path = uri;
			            if(e > -1) {
			               path = uri.substring(e);
			            }

			            int qindex = path.indexOf("?");
			            if(qindex > -1) {
			               path = path.substring(0, qindex);
			            }

			            path = decode(path);
			            return path.length() < 1?"FILE":(path.equals("/")?"FILE":path);
			         } else {
			            return "FILE";
			         }
			      } catch (Exception var4) {
			         return "FILE";
			      }
			   }
		   public static String decode(String str) {
			      char[] ch = str.toCharArray();
			      StringBuffer buf = new StringBuffer();

			      for(int i = 0; i < ch.length; ++i) {
			         if(ch[i] != 47 && ch[i] != 92 && ch[i] != 34 && ch[i] != 63 && ch[i] != 42 && ch[i] != 60 && ch[i] != 62 && ch[i] != 58) {
			            if(ch[i] == 37 && i + 2 < ch.length) {
			               int c = Integer.parseInt(String.valueOf(ch[i + 1]) + ch[i + 2], 16);
			               buf.append((char)c);
			               i += 2;
			            } else {
			               buf.append(ch[i]);
			            }
			         }
			      }

			      return buf.toString();
			   }
		   
		   static String hms(int sec) {
			      boolean hrs = false;
			      boolean min = false;
			      int hrs1 = sec / 3600;
			      int min1 = sec % 3600 / 60;
			      sec %= 60;
			      String str = "";
			      if(hrs1 > 0) {
			         str = str + hrs1 + " hour ";
			      }

			      str = str + min1 + " min ";
			      str = str + sec + " seconds ";
			      return str;
			   }
		   public static boolean isNullOrEmpty(String str) {
			      return str == null || str.length() < 1;
			   }
		   
		   public static String nvl(Object o) {
			      return o == null?"":o.toString();
			   }
		   
		   public static void open(File f) {
			   char ch = File.separatorChar;
			      if(ch == 92) {
			         openWindows(f);
			      }
		   }
		   private static void openWindows(File f) {
			      try {
			         ProcessBuilder e = new ProcessBuilder(new String[0]);
			         ArrayList lst = new ArrayList();
			         lst.add("rundll32");
			         lst.add("url.dll,FileProtocolHandler");
			         lst.add(f.getAbsolutePath());
			         e.command(lst);
			         e.start();
			      } catch (IOException var3) {
			         var3.printStackTrace();
			      }

			   }
		   
		//For HLS download
			public synchronized static File getTempFile(String tmpdir) {
				String name = UUID.randomUUID().toString();
				return new File(tmpdir, name);
			} 
			
			public static String hms1(int sec) {
				int hrs = 0, min = 0;
				hrs = sec / 3600;
				min = (sec % 3600) / 60;
				sec = sec % 60;
				String str = String.format("%02d:%02d:%02d", hrs, min, sec);
				// if (hrs > 0)
				// str += (hrs + " hour ");
				// str += (min + " min ");
				// str += (sec + " seconds ");
				return str;
			}
			
			public static String getJarPath() {
				if (jarPath == null) {
					try {
						String name = "icon.png";
						//String rawPath = AnimeUtil.class.getResource("/res/icon.png").toString();
						String rawPath = StaticResource.class.getResource("/Resources/Icons/" + name).toString();
						rawPath = rawPath.replace("jar:", "");
						int index = rawPath.lastIndexOf("!");
						if (index > 0) {
							rawPath = rawPath.substring(0, index);
						}
						String path = new URI(rawPath).getPath();
						jarPath = new File(path).getParent();
					} catch (Exception e) {
						e.printStackTrace();
						try {
							jarPath = new File(
									AnimeUtil.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath())
											.getParent();
						} catch (Exception exx) {
							e.printStackTrace();
						}
					}
				}
				return jarPath;
			}
		
		   
		
}

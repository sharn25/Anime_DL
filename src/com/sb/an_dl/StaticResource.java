package com.sb.an_dl;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.border.Border;

import com.sb.Utils.LinuxUtils;
import com.sb.downport.StringResource;
import com.sb.downport.animeconfig;
import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;

import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;
import uk.co.caprica.vlcj.runtime.x.LibXUtil;

public class StaticResource {
	public static Color whiteColor = new Color(235, 235, 235);
	public static Color whiteori = Color.white;
	public static Color titleColor = new Color(38, 42, 51);// (14, 20, 25);
	public static Color titleColor2 = new Color(0, 172, 82);
	public static Color testcolor2 = new Color(225, 78, 81);
	public static Color selectedColor = new Color(51, 181, 229);
	public static Color btnBgColor = new Color(36, 122, 241);
	public static Color lightgreycolor = new Color(166, 165, 165);
	public static Font plainFont = new Font("Dialog", 0, 12);
	public static Font customFont = new Font("Dialog", 0, 15);
	public static Font customFont2 = new Font("Dialog", 1, 15);
	public static Font customFont2i = new Font("Dialog", Font.ITALIC, 15);
	public static Font boldFont = new Font("Dialog", 1, 12);
	public static Font plainFontBig = new Font("Dialog", 1, 20);
	public static Font plainFontBig2 = new Font("Dialog", 0, 18);
	public static Font defaultfont = new Font("Tahoma", Font.PLAIN, 11);
	public static Border borderblack = BorderFactory.createLineBorder(Color.black, 1);
	public static String[] dtlsource = new String[] {
			"<html>Name: Source 0<br>Quality: 1080p<br>Size: ~100mb - ~400mb<br>HighSpeed links.</html>",
			"<html>Name: Source 1<br>Quality: 1080p<br>Size:~100mb - ! ~400mb<br>HighSpeed links.</html>",
			"<html>Name: Source 2<br>Quality: 1080p<br>Size: ~70mb - ~400mb<br>HighSpeed download links.</html>",
			"<html>Name: Source 3(Spanish)<br>Quality: 1080p<br>Size: ~70mb - ~400mb<br>HighSpeed download links.</html>"};
	public final static String[] weekday = new String[]{"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
	private final static String TAG = "StaticResource";
	public final static String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.102 Safari/537.36";
	public final static int FREE_SPACE = 99;
	public final static int USED_SPACE = 88;
	public final static int TOTAL_SPACE = 77;
	public final static int CUR_VER = 33;//29-01-2022
	public static final int WINDOWS = 10, MAC = 20, LINUX = 30;
	public final static String ANIME_CONF = "anime.json";
	public final static String EP_LOC = File.separator + "ep" + File.separator;
	
	//Linux Packages
	public final static String VLCLinux = "vlc";
	
	//Mac Packages
	public final static String VLCMAC="/Applications/VLC.app/Contents/MacOS/VLC";
	
	//url section
	public final static String homeURL = "http://a.animedlweb.ga";
	public final static String chkUpdateURL = homeURL + "/update/u_animedl.html";
	public final static String announcementURL = homeURL + "/animedl_anu.html";
	public final static String donateURL = homeURL + "/donate/donate.html";
	public final static String donateStatusURL = homeURL + "/donation_status.php";
	
	//Material StaticResources
	//Color
	//public static Color textColor = Color.decode(StringResource.getColorString("TEXT_COLOR"));
	
	//Fonts
	public static Font materialFontBigR, materialFontBigBlack, materialFontBigB, materialFontBigBI, materialFontBigI, materialFontBigL, materialFontBigLI, materialFontBigM, materialFontBigMI, materialFontBigT, materialFontBigTI;
	//public static Font materialFontBig;
	//public static Font materialFontBigB = new Font("Roboto", 1, 20);
	public static Font materialFontSmallLi, materialFontSmallL, materialFontSmallR;
	public static Font materialFontSoSmallLI, materialFontSoSmallL;
	public static Font materialFontMidL, materialFontMidR;
	
	
	public static void initFonts() {
		try {
		    //create the font to use. Specify the size!
			materialFontBigR = loadFont1("Roboto-Regular.ttf").deriveFont(20f);
			materialFontBigL = loadFont1("Roboto-Light.ttf").deriveFont(20f);
			materialFontBigLI =loadFont1("Roboto-LightItalic.ttf").deriveFont(20f);
			materialFontBigB = loadFont1("Roboto-Bold.ttf").deriveFont(20f);
			materialFontMidL = loadFont1("Roboto-Light.ttf").deriveFont(15f);
			materialFontMidR= loadFont1("Roboto-Regular.ttf").deriveFont(15f);
			materialFontSmallL = loadFont1("Roboto-Light.ttf").deriveFont(12f);
			materialFontSmallR = loadFont1("Roboto-Regular.ttf").deriveFont(12f);
			materialFontSmallLi = loadFont1("Roboto-LightItalic.ttf").deriveFont(12f);
			materialFontSoSmallLI =  loadFont1("Roboto-LightItalic.ttf").deriveFont(10f);
			materialFontSoSmallL = loadFont1("Roboto-Light.ttf").deriveFont(10f);
		    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		    //register the font
		    ge.registerFont(materialFontBigR);
		    ge.registerFont(materialFontBigL);
		    ge.registerFont(materialFontMidR);
		    ge.registerFont(materialFontSmallLi);
		    ge.registerFont(materialFontSmallL);
		    ge.registerFont(materialFontSoSmallLI);
		    ge.registerFont(materialFontSoSmallL);
		} catch (Exception e) {
		    e.printStackTrace();
		    Utils.e(TAG,"Loading Fonts Error",true);
		} 
	}
	
	static MouseAdapter ma = new MouseAdapter() {
		Color bgColor;

		public void mouseEntered(MouseEvent e) {
			this.bgColor = ((JButton) e.getSource()).getBackground();
			((JButton) e.getSource()).setBackground(StaticResource.selectedColor);
		}

		public void mouseExited(MouseEvent e) {
			((JButton) e.getSource()).setBackground(this.bgColor);
		}
	};


	public static Font loadFont1(String resourceName) {
		Font  f = null;
		try {
			f = Font.createFont(Font.TRUETYPE_FONT, StaticResource.class.getResourceAsStream("/Resources/Fonts/" + resourceName));
		}catch(Exception e) {
			try {
				f = Font.createFont(Font.TRUETYPE_FONT, new File("Resources/Fonts/" + resourceName));
			} catch (FontFormatException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				f=null;
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				f=null;
			}
		}
		return f;
	}
	
	public static File getFontFile(String name) {
		try {
			 
			URL e = StaticResource.class.getResource("/Resources/Fonts/" + name);
			if (e == null) {
				throw new Exception();
			} else {
				return new File(e.toString());
			}
		} catch (Exception var2) {
			return new File("Resources/Fonts/" + name);
		}
	}
	
	public static ImageIcon getIcon(String name) {
		try {
			URL e = StaticResource.class.getResource("/Resources/Icons/" + name);
			if (e == null) {
				throw new Exception();
			} else {
				return new ImageIcon(e);
			}
		} catch (Exception var2) {
			return new ImageIcon("Resources/Icons/" + name);
		}
	}
	
	public static ImageIcon getAnimeIcon(String name) {
		try {
			URL e = StaticResource.class.getResource(name);
			if (e == null) {
				throw new Exception();
			} else {
				return new ImageIcon(e);
			}
		} catch (Exception var2) {
			return new ImageIcon(name);
		}
	}

	public static boolean chkFFMPEGLibs(String libpath) {
		String ffmpegFile = Utils.getFFMPEGname();
		if(Utils.detectOS()==StaticResource.LINUX){
			return LinuxUtils.isPackageInstalled(ffmpegFile);
		}
		File f1 = new File(libpath, ffmpegFile);
		if (f1.exists()) {
			Utils.i("StaticResource_FFMPEGlibsload", "FFMPEG Libs loaded Successfuly", true);
			return true;
		} else {
			Utils.i("StaticResource_FFMPEGlibsload", "FFMPEG Libs loading unsuccessful", true);
			return false;
		}

	}

	public static boolean chkVLCLibs(String libpath) {
		int OS = Utils.detectOS();
		if(OS==StaticResource.LINUX){
			return LinuxUtils.isPackageInstalled(StaticResource.VLCLinux + " --help");
		}else if(OS==StaticResource.MAC){
			return new File(VLCMAC).exists();
			
		}
		File f1 = new File(libpath, "libvlc" + Utils.getVLCname());
		File f2 = new File(libpath, "libvlccore" + Utils.getVLCname());
		File f3 = new File(libpath, "plugins");

		if (f1.exists() && f2.exists() && f3.exists()) {
			Utils.i("StaticResource_VLClibsload", "VLC Libs loaded Successfuly", true);
			return true;
		} else {
			Utils.i("StaticResource_VLClibsload", "VLC Libs loading unsuccessful", true);
			return false;
		}
	}

	public static boolean loadVLCLibs(String libpath) {

		NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), libpath);
		NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcCoreName(), libpath);
		NativeLibrary.addSearchPath(RuntimeUtil.getPluginsDirectoryName(), libpath);
		Utils.l("StaticResources", RuntimeUtil.getLibVlcLibraryName(), true);
		try {
			Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Please set the VLC installation directory in settings tab.\nor\nDownload VLClibs.zip from http://animedl.atwebpages.com/ site and place in the same folder as Anime_Dl jar file.");
			e.printStackTrace();
			animeconfig.isFFMPEGexist = false;
			return false;
		}
		LibXUtil.initialise();
		Utils.i("StaticResources", "version: {}" + LibVlc.INSTANCE.libvlc_get_version(), true);
		Utils.i("StaticResources", " compiler: {}" + LibVlc.INSTANCE.libvlc_get_compiler(), true);
		Utils.i("StaticResources", "changeset: {}" + LibVlc.INSTANCE.libvlc_get_changeset(), true);
		animeconfig.isFFMPEGexist = true;
		return true;

	}
	
	public static String fileToString(File file) {
		String s = "";
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), Charset.forName("UTF-8")));
			StringBuilder sb = new StringBuilder();
		    String line = reader.readLine();

		    while (line != null) {
		        sb.append(line);
		        sb.append(System.lineSeparator());
		        line = reader.readLine();
		    }
		    s = sb.toString();
		} catch (Exception e) {
			Utils.e(TAG+"_fileToString", "Unable to read from file - " + file.getAbsolutePath(), true);
		}finally{
			try {
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return s;
	}
	
	public static void saveAnimeIcon(String file, ImageIcon icon){
		BufferedImage imgIcon = toBufferedImage(icon.getImage());
		File image = new File(file, "animeIcon.png");
		try {
			ImageIO.write(imgIcon, "png", image);
		} catch (IOException e) {
			e.printStackTrace();
			Utils.e(TAG+"_saveAnimeIcon", "Uable to save Anime image.", true);
		}
	}
	private static BufferedImage toBufferedImage(Image src) {
		int w = src.getWidth(null);
		int h = src.getHeight(null);
		int type = BufferedImage.TYPE_INT_RGB; // other options
		BufferedImage dest = new BufferedImage(w, h, type);
		Graphics2D g2 = dest.createGraphics();
		g2.drawImage(src, 0, 0, null);
		g2.dispose();
		return dest;
	}

}

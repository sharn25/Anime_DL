package com.sb.an_dl;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
//import javax.rmi.CORBA.Util;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.sb.GUI.SAlertDialog;
import com.sb.GUI.SButton;
import com.sb.GUI.SCheckBox;
import com.sb.GUI.SPopup;
import com.sb.an_dl.player.ExternalPlayer;
import com.sb.downport.StringResource;
import com.sb.downport.animeconfig;

public class Fetcher {

	List<Anime> AnimeList = new ArrayList<Anime>();
	Anime[] aryanime;
	private String animename;
	private String animedtl;
	private String epnumber;
	private String animeurl;
	private String animeicon;
	private String refernceURL;
	private String data = "";
	private String value = "";
	static animeconfig an_con;
	public String[] imagesary;
	public String[] scheduleImgAry;
	private String ERROR_MSG;
	private int source;
	private boolean isDoShowQlty;
	boolean action = false;
	int epnum;
	String hdtype = "";
	String pref = "";
	private final static String TAG = "Fetcher";
	// Objects
	private static Fetcher fetch;

	public Fetcher(animeconfig an_con) {
		this.an_con = an_con;
	}

	public static Fetcher getFetcher(animeconfig an_con) {
		if (fetch == null) {
			fetch = new Fetcher(an_con);
		}
		return fetch;
	}

	public List<Anime> getdata(Document doc, int cases, MainWindow mainwindow) {

		source = cases;
		aryanime = null;
		String selecter = null;
		int animearry = 0;
		int total = 0;

		switch (cases) {
		case 0:
			animearry = 0;
			total = 0;
			if (doc == null) {
				return null;
			}
			Elements galink = doc.select("div.last_episodes > ul.items > li > div.img > a");
			aryanime = new Anime[galink.size()];
			imagesary = new String[galink.size()];
			total = galink.size();

			for (Element link : galink) {
				animename = link.attr("title");
				String gaanimeurl = link.attr("href");
				int i = gaanimeurl.length();
				if (gaanimeurl.contains("-episode")) {

					i = gaanimeurl.indexOf("-episode");
				}

				if (mainwindow.searchmode) {
					epnumber = "";
				} else {
					epnumber = "Episode " + gaanimeurl.substring(i + 9);
				}
				gaanimeurl = an_con.source_url[an_con.source] + "/category/"
						+ gaanimeurl.substring(gaanimeurl.lastIndexOf("/") + 1, i);
				animeurl = gaanimeurl;
				animeicon = link.select("img").attr("src");
				animedtl = "";

				createarrylist(animearry);

				mainwindow.setprogressstring(Integer.toString(animearry + 1) + "/" + Integer.toString(total));
				animearry++;
			}
			break;
		case 1:
			animearry = 0;
			total = 0;
			if (doc == null) {
				return null;
			}
			try {
				Elements sel_VS = doc.select("ul.listing > li > a");
				
				Utils.l(TAG,"Size Source 1: " + doc.html(),true);
				aryanime = new Anime[sel_VS.size()];
				imagesary = new String[sel_VS.size()];
				total = sel_VS.size();
				for (int i = 0; i < sel_VS.size(); i++) {
					Element tmp = sel_VS.get(i);
					animeurl = an_con.source_url[cases] + tmp.attr("href");
					tmp = tmp.selectFirst("div.img > div.picture > img");
					animeicon = tmp.attr("src");
					animename = tmp.attr("alt");
					epnumber = "";
					animedtl = "";
					createarrylist(animearry);
					mainwindow.setprogressstring(Integer.toString(animearry + 1) + "/" + Integer.toString(total));
					animearry++;
				}
			} catch (Exception e) {
				Utils.e(TAG, "CASE 1: ", true);
			}
			break;
		case 2:
			animearry = 0;
			total = 0;
			if (doc == null) {
				return null;
			}
			Element tenshi_sel = doc.selectFirst("ul.anime-loop");
			if (tenshi_sel == null) {
				return null;
			}
			Elements tenshi_sel1 = tenshi_sel.select("li");
			aryanime = new Anime[tenshi_sel1.size()];
			imagesary = new String[tenshi_sel1.size()];
			for (int i = 0; i < tenshi_sel1.size(); i++) {
				tenshi_sel = tenshi_sel1.get(i).selectFirst("a");
				animeurl = tenshi_sel.attr("href");
				animedtl = "<html><body>" + tenshi_sel.attr("data-content") + "</body></html>";
				String search_sector = "div.overlay";
				if (!mainwindow.searchmode) {
					Element tenshi_img = tenshi_sel.selectFirst("img");
					animeicon = tenshi_img.attr("src");
				} else {
					animeicon = "";
					search_sector = "div.label > span.text-primary";
				}
				tenshi_sel = tenshi_sel.selectFirst(search_sector);
				animename = tenshi_sel.text();
				epnumber = "";
				createarrylist(animearry);
				mainwindow.setprogressstring(Integer.toString(animearry + 1) + "/" + Integer.toString(total));
				animearry++;
			}
			break;
		case 3:
			animearry = 0;
			total = 0;
			if (doc == null) {
				return null;
			}
			Elements selAF = doc.select("div.list-series > article.serie-card");
			if (selAF == null) {
				return null;
			}

			aryanime = new Anime[selAF.size()];
			imagesary = new String[selAF.size()];
			for (int i = 0; i < selAF.size(); i++) {
				Element tmp = selAF.get(i);
				Element tmp2 = tmp.select("figure.image > a").first();
				animeurl = tmp2.attr("href");
				animedtl = "";
				animeicon = tmp2.selectFirst("img").attr("src");

				animename = tmp.selectFirst("div.title").text();
				epnumber = "";
				createarrylist(animearry);
				mainwindow.setprogressstring(Integer.toString(animearry + 1) + "/" + Integer.toString(total));
				animearry++;
			}
			break;
		}
		return AnimeList;
	}

	public List<Anime> getSeheduledata(String day, List<Anime> list, MainWindow mainwindow) {
		String M = "_getSeheduledata";
		list.clear();

		Document doc = getdoc(an_con.scheduleURL + day, "", "");
		if (doc == null) {
			return list;
		}
		try {
			JSONObject json = new JSONObject(doc.body().text());
			JSONArray jsonArry = json.getJSONArray(day.toLowerCase());
			scheduleImgAry = new String[jsonArry.length()];
			for (int i = 0; i < jsonArry.length(); i++) {
				JSONObject animeObj = jsonArry.getJSONObject(i);
				String score = "";
				try {
					score = "" + animeObj.getInt("score");
				} catch (Exception e) {
					score = "";
				}
				String snopsis = animeObj.getString("synopsis");
				if (snopsis.length() > 100) {
					snopsis.substring(0, 100);
				}
				String des = "<html><body><ul>" + "<li>Type: " + animeObj.getString("type") + "</li><li>MAL Score: "
						+ score + "</li><li>Description: " + snopsis + "</li></ul><body><html>";
				Anime a = new Anime(animeObj.getString("title"), des, "", setimg(false, "", 0),
						animeObj.getString("url"));
				list.add(a);
				scheduleImgAry[i] = animeObj.getString("image_url");
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ERROR_MSG = "Error while fetching data.<br>CODE = #X01";
			Utils.e(TAG + M, ERROR_MSG, true);
			SAlertDialog.showAlertInfo("Error", ERROR_MSG, mainwindow);
		}
		return list;
	}

	private Document getdoc(String url, String ref, String method) {
		Document doc = null;
		Utils.l(TAG + "_getdoc", "URL: " + url + " ref: " + ref, true);
		Connection.Response response = null;
		int responseCode;
		Utils.l("Fetcher_getdoc", url, false);
		MainWindow.setstatus_loggger("Fatching data...");
		try {
			switch (method) {
			case "":
				if (ref.equals("")) {
					response = Jsoup.connect(url).ignoreContentType(true).userAgent(StaticResource.USER_AGENT)
							.maxBodySize(0).execute();
				} else {
					response = Jsoup.connect(url).ignoreContentType(true).referrer(ref)
							.userAgent(StaticResource.USER_AGENT).execute();
				}
				break;
			case "post":
				if (ref.equals("")) {
					response = Jsoup.connect(url).ignoreContentType(true).userAgent(StaticResource.USER_AGENT)
							.data(data, value).method(Method.POST).maxBodySize(0).execute();
				} else {
					response = Jsoup.connect(url).ignoreContentType(true).referrer(ref)
							.userAgent(StaticResource.USER_AGENT).data(data, value).method(Method.POST).execute();
				}
				break;

			case "get":

				break;
			}
			responseCode = response.statusCode();
			if (responseCode == 200) {
				doc = response.parse();
			} else {
				ERROR_MSG = "Invalid Response Code: " + responseCode;
				MainWindow.setstatus_loggger(ERROR_MSG);
				doc = null;
			}
			MainWindow.setstatus_loggger("");
		} catch (java.net.SocketTimeoutException ex) {
			// ex.printStackTrace();
			ERROR_MSG = "Server TimeOut. Please try again.";
			MainWindow.setstatus_loggger(ERROR_MSG);
			doc = null;
		} catch (org.jsoup.HttpStatusException e) {
			int st = e.getStatusCode();
			if (st == 404) {
				ERROR_MSG = "HTTP ERROR " + e.getStatusCode() + "<br>Anime not found.";
			} else {
				ERROR_MSG = "HTTP ERROR " + e.getStatusCode() + "<br>Try different Anime.";
			}
			MainWindow.setstatus_loggger(ERROR_MSG);
			doc = null;
		} catch (Exception e) {
			e.printStackTrace();
			ERROR_MSG = "Unexpected Error! Try checking your internet connection.";
			Utils.e("Fetcher_getdoc", ERROR_MSG, true);
			MainWindow.setstatus_loggger(ERROR_MSG);
			// Utils.timer.cancel();
			doc = null;
		}
		if (doc == null) {
			SAlertDialog.showAlertInfo("Error", ERROR_MSG, MainWindow.getmainwindow());
		}
		return doc;
	}

	public void aphelper(int selcted, int cases, MainWindow mainwindow) {
		String M = "_aphelper";
		this.isDoShowQlty = false;
		Document doc;

		switch (cases) {
		case 0:
			doc = null;
			doc = getdoc(aryanime[selcted].geturl(), "", "");
			if (doc == null) {
				mainwindow.epno = null;
				return;
			}
			mainwindow.dwnanimename = aryanime[selcted].getName();
			mainwindow.animeurldownload = aryanime[selcted].geturl();
			mainwindow.apicon = aryanime[selcted].geticon();
			Elements gadlink = doc.select("div.anime_info_body_bg > p.type");
			String description = "";
			for (Element link : gadlink) {
				description += link.html() + "<br>";
			}
			mainwindow.description = "<html>" + description + "</html>";
			gadlink = doc.select("div.anime_video_body > ul > li");
			String lastep = gadlink.last().select("a").attr("ep_end");
			int lastepint = Integer.parseInt(lastep);

			mainwindow.epno = new String[lastepint];
			mainwindow.epname = new String[lastepint];
			String currenturl = aryanime[selcted].geturl();
			for (int i = 0; i < lastepint; i++) {
				int j = i + 1;
				mainwindow.epno[i] = Integer.toString(j);
				mainwindow.epname[i] = currenturl.replaceAll("category/", "") + "-episode-" + j;
			}
			break;
		case 1:
			doc = null;

			doc = getdoc(aryanime[selcted].geturl(), "", "");
			if (doc == null) {
				mainwindow.epno = null;
				return;
			}
			mainwindow.dwnanimename = aryanime[selcted].getName();
			mainwindow.animeurldownload = aryanime[selcted].geturl();
			mainwindow.apicon = aryanime[selcted].geticon();
			Element sel_VSi = doc.selectFirst("div.content-more-js");
			String des = "<HTML><BODY>" + sel_VSi.html() + "</BODY></HTML>";
			mainwindow.description = des;

			Element sel_VSi1 = doc.selectFirst("ul.listing");
			Elements sel_VSep = sel_VSi1.select("li.video-block > a");

			mainwindow.epno = new String[sel_VSep.size()];
			mainwindow.epname = new String[sel_VSep.size()];
			int subs = 0;
			int dubs = 0;
			int total_ep = sel_VSep.size() - 1;
			for (int i = 0; i < sel_VSep.size(); i++) {
				int j = total_ep - 1;
				Element tmp = sel_VSep.get(i);
				String animeEPurl = an_con.source_url[an_con.source] + tmp.attr("href");
				String type = tmp.selectFirst("div.type").text();
				if (type.contains("DUB")) {
					dubs = dubs + 1;
				} else {
					subs = subs + 1;
				}
				String epno = animeEPurl;
				mainwindow.epname[total_ep - i] = epno;
				String epno1 = Integer.toString(j);
				mainwindow.epno[total_ep - i] = tmp.selectFirst("div.name").text().replaceAll("[^0-9]", "");
			}

			break;
		case 2:
			doc = null;
			doc = getdoc(aryanime[selcted].geturl(), "", "");
			if (doc == null) {
				mainwindow.epno = null;
				return;
			}
			mainwindow.dwnanimename = aryanime[selcted].getName();
			mainwindow.animeurldownload = aryanime[selcted].geturl();
			mainwindow.apicon = aryanime[selcted].geticon();
			mainwindow.description = aryanime[selcted].getdetails();
			Elements tenshi_sel = doc.select("ul.episode-loop > li");
			Utils.l(TAG + M, "Size: " + tenshi_sel.size(), true);
			mainwindow.epno = new String[tenshi_sel.size()];
			mainwindow.epname = new String[tenshi_sel.size()];
			for (int i = 0; i < tenshi_sel.size(); i++) {
				Element tenshi_sel1 = tenshi_sel.get(i).selectFirst("a");
				mainwindow.epname[i] = tenshi_sel1.attr("href");
				mainwindow.epno[i] = tenshi_sel1.selectFirst("div.episode-number").text().replaceAll("[^0-9]", "");
			}
			break;

		case 3:
			doc = null;

			doc = getdoc(aryanime[selcted].geturl(), "", "");
			if (doc == null) {
				mainwindow.epno = null;
				return;
			}
			mainwindow.dwnanimename = aryanime[selcted].getName();
			mainwindow.animeurldownload = aryanime[selcted].geturl();
			mainwindow.apicon = aryanime[selcted].geticon();

			Element selAF = doc.select("p.sinopsis").first();
			mainwindow.description = "<html><body>" + selAF.html() + "</body></html>";

			Elements selAFep = doc.select("ul.anime-page__episode-list > li > a");
			Utils.l(TAG + M, "Size: " + selAFep.size(), true);
			mainwindow.epno = new String[selAFep.size()];
			mainwindow.epname = new String[selAFep.size()];
			int total_ep_AF = selAFep.size() - 1;
			for (int i = 0; i < selAFep.size(); i++) {
				String epURL = selAFep.get(i).attr("href");
				int index = epURL.lastIndexOf("-");
				mainwindow.epname[total_ep_AF - i] = epURL;
				mainwindow.epno[total_ep_AF - i] = epURL.substring(index + 1);
			}
			break;
		}
	}

	public void downloadhelper(String epInfo, int cases, MainWindow mainwindow, boolean startall) {
		int index_Ep = epInfo.lastIndexOf(";");
		String url = epInfo.substring(0, index_Ep);
		String epno = epInfo.substring(index_Ep + 1).replaceAll("[^0-9]", "");
		String M = "_downloadhelper";
		if (!an_con.isStreamEnable) {
			mainwindow.setlbldwn("Download(+ep)");
		}
		source = cases;
		switch (cases) {
		case 0:
			epno = url.substring(url.lastIndexOf("-") + 1);
			this.source0down(url, epno, startall, mainwindow);
			break;
		case 1:
			this.source1down(url, epno, startall, mainwindow);
			break;
		case 2:
			epno = url.substring(url.lastIndexOf("/") + 1);
			this.source2down(url, epno, cases, startall, mainwindow);
			break;
		case 3:
			this.source3down(url, epno, cases, startall, mainwindow);
			break;
		}
	}

	public ImageIcon setimg(boolean z, String rimage, int i) {
		ImageIcon image = null;
		try {
			if (z) {
				image = new ImageIcon(
						new ImageIcon(getimage(rimage)).getImage().getScaledInstance(130, 180, Image.SCALE_DEFAULT));
			} else {
				image = StaticResource.getIcon(StringResource.getColorString("LOADING_IMG"));
			}
		} catch (Exception e1) {
			image = StaticResource.getIcon(StringResource.getColorString("NO_IMG"));

		}

		return image;
	}

	public List<Anime> getSceduleImg(List<Anime> list) {
		Utils.l("Fetcher_getSceduleImg", "loading image", true);
		for (int i = 0; i < list.size(); i++) {
			if (!MainWindow.isScheduleLoading) {
				Utils.l(TAG + "_getSceduleImg", "Image URL: " + scheduleImgAry[i], false);
				list.get(i).seticon(setimg(true, this.scheduleImgAry[i], i));
			}
		}
		return list;
	}
	

	public List<Anime> getimguparlst() {
		int j = AnimeList.size() - 1;
		Utils.l("Fetcher_getimage", "loading image", true);
		for (int i = 0; i < AnimeList.size(); i++) {
			if (!MainWindow.isActionChanged) {
				MainWindow.setstatus_loggger("Loading Images : " + i + "/" + j);
				Utils.l(TAG + "_getimage", "Image URL: " + imagesary[i], false);
				AnimeList.get(i).seticon(setimg(true, this.imagesary[i], i));
			}
		}
		return AnimeList;
	}

	public void createarrylist(int i) {
		ImageIcon image = null;
		try {
			image = setimg(false, "", i);
			/// Using Custom table model
			aryanime[i] = new Anime(this.animename, this.animedtl, this.epnumber, image, this.animeurl);
			if (an_con.source == 0) {
				imagesary[i - 1] = this.animeicon;
			} else {
				imagesary[i] = this.animeicon;
			}
			AnimeList.add(aryanime[i]);
		} catch (Exception e1) {
			System.out.println("");
		}

	}

	public BufferedImage getimage(String url) {
		BufferedImage img = null;
		try {
			URL imgurl = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) imgurl.openConnection();
			connection.addRequestProperty("User-Agent", "Mozilla/4.76");
			connection.connect();
			InputStream in = connection.getInputStream();
			img = ImageIO.read(in);

		} catch (IOException e) {
			Utils.e(TAG,"Unable to load image.",true);
		}
		return img;
	}

	public static String getFFMPEGpath() {
		return an_con.FFMPEG_LIBS_PATH;
	}

	public void loadFFMPEGLibs() {
		Thread t = new Thread() {
			public void run() {
				if (!StaticResource.chkFFMPEGLibs(an_con.FFMPEG_LIBS_PATH)) {
					String path;
					String sysbit = System.getProperty("sun.arch.data.model");
					if (sysbit.equals(32)) {
						path = "./ffmpeg";
					} else {
						path = "./ffmpeg";
					}
					if (!StaticResource.chkFFMPEGLibs(path)) {
						int y = JOptionPane.showConfirmDialog(null, Utils.getFFMPEGErrormsg(), "FFMPEG Libs Download",
								JOptionPane.YES_NO_OPTION);
						if (y == 0) {
							if (Desktop.isDesktopSupported()) {
								try {
									Desktop.getDesktop().browse(new URI("https://www.ffmpeg.org/download.html"));
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (URISyntaxException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
					} else {
						an_con.FFMPEG_LIBS_PATH = path;
						an_con.isFFMPEGexist = true;
					}
				} else {
					if (Utils.detectOS() == StaticResource.LINUX) {
						an_con.FFMPEG_LIBS_PATH = "Detected.";
					}
					an_con.isFFMPEGexist = true;
				}

				SwingUtilities.invokeLater(new Runnable() {// do swing work on EDT
					public void run() {

					}
				});
			}
		};
		t.start();
	}

	public void loadVLCLibs() {
		Thread t = new Thread() {
			public void run() {
				// an_con.VLC_LIBS_PATH="";
				// an_con.VLC_LIBS_PATH="G:/Other/Program/VLCPortable/App/vlc/win32-amd64";
				if (!StaticResource.chkVLCLibs(an_con.VLC_LIBS_PATH)) {
					String path;
					String sysbit = System.getProperty("sun.arch.data.model");
					if (sysbit.equals(32)) {
						path = "./VLCLibs/32";
					} else {
						path = "./VLCLibs/64";
					}
					if (!StaticResource.chkVLCLibs(path)) {
						int y = JOptionPane.showConfirmDialog(null,
								"Please set the VLC installation directory in settings tab.\nor\nClick yes to Download VLClibs.zip and place in the same folder as Anime_Dl jar file.",
								"VLC Libs Download", JOptionPane.YES_NO_OPTION);

						if (y == 0) {
							if (Desktop.isDesktopSupported()) {
								try {
									Desktop.getDesktop().browse(new URI("https://archive.org/details/VLCLibs"));
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (URISyntaxException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}

					} else {
						an_con.VLC_LIBS_PATH = path;
						an_con.isVLCInstalled = true;
					}

				} else {
					if (Utils.detectOS() == StaticResource.LINUX) {
						an_con.VLC_LIBS_PATH = "Detected.";
					}
					an_con.isVLCInstalled = true;
				}

				SwingUtilities.invokeLater(new Runnable() {// do swing work on EDT
					public void run() {

					}
				});
			}
		};
		t.start();
	}

	public String gethomeurrl(String url1, int cases) {
		String url = null;
		switch (cases) {
		case 0:
			url = url1;
			break;

		case 1:
			url = url1;
			break;

		case 2:
			url = url1;
			break;
			
		case 3:
			url = url1;
			break;
		}
		return url;
	}

	public String getsrchbox(String serachtxt, int cases) {
		String url = null;
		switch (cases) {
		case 0:
			serachtxt = serachtxt.replaceAll(" ", "%20");
			url = an_con.source_url[cases] + "/search.html?keyword=" + serachtxt;
			break;

		case 1:
			serachtxt = serachtxt.replaceAll(" ", "%20");
			url = an_con.source_url[cases] + "/search.html?keyword=" + serachtxt;
			break;

		case 2:
			serachtxt = serachtxt.replaceAll(" ", "+");
			url = an_con.source_url[cases] + "/anime?q=" + serachtxt;
			break;
		case 3:
			serachtxt = serachtxt.replaceAll(" ", "+");
			url = an_con.source_url[cases] + "/animes?q=" + serachtxt;
			break;
		}

		return url;
	}

	private void source0down(String url, String epno, boolean startall, MainWindow mainwindow) {
		final String M = "_source0down";
		Utils.l(TAG+M, "url: " + url, true);
		boolean hasMP4 = false;
		boolean hasSETURL = false;
		String hdtype = mainwindow.qlty;
		String final_link = "";
		String base_url = "";
		String[] sources = new String[2];

		this.pref = "";
		this.hdtype = "";

		Document doc = getdoc(url, "", "");
		String downloadurl = null;
		if (doc == null) {
			ERROR_MSG = "Download Link not found.<br>CODE = #01";
			error(TAG+M,mainwindow);
			return;
		}
		try {
			Elements dlink = doc.select("div.favorites_book > ul > li.dowloads > a");
			String refernceurl = dlink.get(0).attr("href");
			refernceurl = refernceurl.replace("download", "load.php");

			doc = getdoc(refernceurl, refernceurl, "");
			this.refernceURL = refernceurl;
			if (doc == null) {
				ERROR_MSG = "Download Link not found.<br>CODE = #02";
				error(TAG+M,mainwindow);
				return;
			}
			Elements s1 = doc.select("ul.list-server-items > li");
			for (int i = 0; i < s1.size(); i++) {
				String s_temp = s1.get(i).attr("data-video");
				// Utils.l(TAG, "s_temp: " + s_temp, true);
				if (s_temp.contains("gogoplay")) {
					url = s1.get(i).attr("data-video");
					break;
				}
			}

			doc = getdoc(url, "", "");

			Elements sel = doc.select("script[type=text/JavaScript]");
			String p = null;

			for (int i = 0; i < sel.size(); i++) {
				String s = sel.get(i).toString();
				if (s.contains("sources:[{file:")) {
					p = s;
				}
			}
			if (p.contains("mp4")) {
				hasMP4 = true;
			}
			sources = geturls(p, sources);
			Utils.l(TAG, "source 3: " + sources[0], true);
			if (!this.verify_link(sources[0], "")) {
				hasMP4 = false;
			}
			if (hasMP4 && hdtype.contains("1080")) {
				Utils.l(TAG + M, "MP4 Link: " + sources[0], true);
				final_link = sources[0];
			} else {
				Utils.l(TAG + M, "Not Found MP4 Link 0: " + sources[0], true);
				Utils.l(TAG + M, "Not Found MP4 Link 1: " + sources[1], true);
				String url_hls = null;

				if (sources[0].contains("m3u8")) {
					url_hls = sources[0];
				} else {
					if (sources[1].contains("null")) {
						ERROR_MSG = "Download Link not found.<br>CODE = #03";
						error(TAG+M,mainwindow);
						return;
					} else if (sources[1].contains("m3u8")) {
						url_hls = sources[1];
					} else {
						ERROR_MSG = "Download Link not found.<br>CODE = #04";
						error(TAG+M,mainwindow);
						return;
					}
				}

				doc = getdoc(url_hls, refernceurl, "");
				base_url = url_hls.substring(0, url_hls.lastIndexOf("/")) + "/";
				if (doc == null) {
					ERROR_MSG = "Download Link not found.<br>CODE = #05";
					error(TAG+M,mainwindow);
					return;
				}
				String s_hls = doc.body().text();
				if (s_hls.contains(".ts")) {
					Utils.l(TAG+M,"Direct m3u8 link.",true);
					if (hasMP4) {
						final_link = sources[0];
					} else {
						final_link = url_hls;
					}
				} else {
					Utils.l(TAG + M, "Not Direct m3u8 link.", true);

					boolean hasM3U8 = s_hls.contains("m3u8");
					while (hasM3U8) {
						int index_m3u8 = s_hls.lastIndexOf("m3u8");
						s_hls = s_hls.substring(0, index_m3u8 + 4);
						int l_index = s_hls.lastIndexOf(" ") + 1;
						String name = s_hls.substring(l_index);
						if (name.contains(hdtype)) {
							final_link = base_url + name;
							break;
						} else {
							int l_index2 = name.lastIndexOf(".");
							String chk = name.substring(0, l_index2);
							chk = chk.substring(chk.lastIndexOf(".") + 1);
							if (Integer.parseInt(hdtype) < Integer.parseInt(chk)) {
								final_link = base_url + name;
							} else if (Integer.parseInt(hdtype) > Integer.parseInt(chk)) {
								if (!hasSETURL) {
									final_link = base_url + name;
									hasSETURL = true;
								}
							}
						}
						s_hls = s_hls.substring(0, l_index);
						hasM3U8 = s_hls.contains("m3u8");
					}

				}
			}
			gethdp(final_link);

			if (final_link.contains(".m3u8") && !final_link.contains(hdtype)) {
				if (!startall) {
					if (!this.isDoShowQlty) {

						String msg = "Unable to find the selected quality, want to procced with " + this.hdtype;
						JPanel mainPanel = new JPanel(new BorderLayout());
						mainPanel.setOpaque(true);
						JPanel panel = new JPanel();
						panel.setOpaque(false);
						panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
						JLabel msgLbl = new JLabel(msg);
						msgLbl.setBorder(new EmptyBorder(0, 10, 0, 0));
						msgLbl.setFont(StaticResource.materialFontSmallL);
						msgLbl.setForeground(StringResource.getColor("TEXT_COLOR"));
						panel.add(msgLbl);
						final SCheckBox chk = new SCheckBox(StringResource.getString("SHOW_DIALOG_AGAIN"));
						chk.setFont(StaticResource.materialFontSmallLi);
						panel.add(chk);
						mainPanel.add(panel, "Center");
						JPanel btnPanel = new JPanel();
						btnPanel.setOpaque(false);
						SButton okBtn = new SButton(StringResource.getString("YES_BTN"));
						okBtn.setBackGround(StringResource.getColor("SEARCH_BG"));
						okBtn.setBorderPainted(false);
						okBtn.setHover(StringResource.getColor("SELECTION"));
						okBtn.setFont(StaticResource.materialFontSmallL);
						okBtn.setPressedColor(StringResource.getColor("PRESSED"));
						okBtn.setForeground(StringResource.getColor("TEXT_COLOR"));
						okBtn.setWidth(70);
						okBtn.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent arg0) {
								// TODO Auto-generated method stub
								action = true;
								SAlertDialog.closeDialog();
							}

						});
						btnPanel.add(okBtn);
						SButton canelBtn = new SButton(StringResource.getString("NO_BTN"));
						canelBtn.setBackGround(StringResource.getColor("SEARCH_BG"));
						canelBtn.setBorderPainted(false);
						canelBtn.setHover(StringResource.getColor("SELECTION"));
						canelBtn.setFont(StaticResource.materialFontSmallL);
						canelBtn.setPressedColor(StringResource.getColor("PRESSED"));
						canelBtn.setForeground(StringResource.getColor("TEXT_COLOR"));
						canelBtn.setWidth(70);
						canelBtn.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent arg0) {
								// TODO Auto-generated method stub
								action = false;
								SAlertDialog.closeDialog();
							}

						});
						btnPanel.add(canelBtn);
						mainPanel.add(btnPanel, "South");
						SAlertDialog.showAlertAction(StringResource.getString("DELETE_CONFIRM_TITLE"), mainPanel,
								mainwindow, 350, 120);
						if (!action) {
							return;
						} else {
							if (chk.isSelected()) {
								Utils.l("Fetcher_S3D!", "Checked not show again", true);
								this.isDoShowQlty = true;
							}
						}
					}
				}
			}
			getpref(final_link);
			downloadurl = final_link;
			Utils.l(TAG + M, "Download_URL: " + downloadurl, true);
			if (downloadurl != null) {
				this.startdownload(downloadurl, epno, startall, mainwindow);
			} else {
				throw new Exception();
			}
		} catch (Exception e) {
			e.printStackTrace();
			hdtype = "";
			ERROR_MSG = "Download Link not found.<br>CODE = #06";
			error(TAG+M,mainwindow);
		}

	}

	private String[] geturls(String doctxt, String[] ary) {
		int index = doctxt.indexOf("sources:[{file:");
		int index2 = 0;
		doctxt = doctxt.substring(index + 9);

		index = doctxt.indexOf("',label");
		index2 = doctxt.indexOf("http");
		String links = doctxt.substring(index2, index);
		// l(links);
		ary[0] = links;
		if (doctxt.contains("sources:[{file:")) {
			index = doctxt.indexOf("sources:[{file:");
			doctxt = doctxt.substring(index + 9);

			index = doctxt.indexOf("',label");
			index2 = doctxt.indexOf("http");
			links = doctxt.substring(index2, index);
			ary[1] = links;
		} else {
			ary[1] = "null";
		}
		return ary;
	}
	

	private String getpref(String url) {
		pref = "";
		if (url.lastIndexOf("sub") != -1 || url.lastIndexOf("Sub") != -1) {
			pref = "sub";
		}
		if (url.lastIndexOf("dub") != -1 || url.lastIndexOf("Dub") != -1) {
			pref = "dub";
		}
		if (pref.equals("")) {
			pref = "sub";
		}
		return pref;
	}

	private void gethdp(String url) {
		if (hdtype != "") {
			return;
		}
		if (url.lastIndexOf("360") != -1 || url.lastIndexOf("360p") != -1) {
			hdtype = "360p";
		}
		if (url.lastIndexOf("480") != -1 || url.lastIndexOf("480p") != -1) {
			hdtype = "480p";
		}
		if (url.lastIndexOf("720") != -1 || url.lastIndexOf("720p") != -1) {
			hdtype = "720p";
		}
		if (url.lastIndexOf("1080") != -1 || url.lastIndexOf("1080p") != -1) {
			hdtype = "1080p";
		}

	}

	private void source1down(String url, String epno, boolean startall, MainWindow mainwindow) {
		final String M = "_source4down";
		Utils.l(TAG+M, "url: " + url, true);
		boolean hasMP4 = false;
		boolean hasSETURL = false;
		String hdtype = mainwindow.qlty;
		String final_link = "";
		String base_url = "";
		String[] sources = new String[2];

		this.pref = "";
		this.hdtype = "";

		Document doc = getdoc(url, "", "");
		String downloadurl = null;
		if (doc == null) {
			ERROR_MSG = "Download Link not found.<br>CODE = #11";
			error(TAG+M,mainwindow);
			return;
		}
		try {
			Element sel_VS = doc.selectFirst("iframe");
			String url1 = sel_VS.attr("src");
			String refernceurl = "https:" + url1;
			Utils.l(TAG, "source 2: " + refernceurl, true);
			refernceurl = refernceurl.replace("streaming.php", "load.php");

			doc = getdoc(refernceurl, refernceurl, "");
			this.refernceURL = refernceurl;
			Utils.l(TAG + M, "refernceURL: " + this.refernceURL, true);
			if (doc == null) {
				ERROR_MSG = "Download Link not found.<br>CODE = #12";
				error(TAG+M,mainwindow);
				return;
			}
			Elements s1 = doc.select("ul.list-server-items > li");
			Utils.l(TAG, "s1 Size: " + s1.size(), true);
			for (int i = 0; i < s1.size(); i++) {
				String s_temp = s1.get(i).attr("data-video");
				if (s_temp.contains("gogoplay")) {
					url = s1.get(i).attr("data-video");
					break;
				}
			}

			doc = getdoc(url, "", "");

			Elements sel = doc.select("script[type=text/JavaScript]");
			Utils.l("Fetcher S3D!", "" + Integer.toString(sel.size()), true);
			String p = null;
			for (int i = 0; i < sel.size(); i++) {
				String s = sel.get(i).toString();
				if (s.contains("sources:[{file:")) {
					p = s;
				}
			}
			if (p.contains("mp4")) {
				hasMP4 = true;
			}
			sources = geturls(p, sources);
			Utils.l(TAG, "source 3: " + sources[0], true);
			if (!this.verify_link(sources[0], "")) {
				hasMP4 = false;
			}
			if (hasMP4 && hdtype.contains("1080")) {
				Utils.l(TAG + M, "MP4 Link: " + sources[0], true);
				final_link = sources[0];
			} else {// else start--------------------------
				Utils.l(TAG + M, "Not Found MP4 Link 0: " + sources[0], true);
				Utils.l(TAG + M, "Not Found MP4 Link 1: " + sources[1], true);
				String url_hls = null;

				if (sources[0].contains("m3u8")) {
					url_hls = sources[0];
				} else {
					if (sources[1].contains("null")) {
						ERROR_MSG = "Download Link not found.<br>CODE = #13";
						error(TAG+M,mainwindow);
						return;
					} else if (sources[1].contains("m3u8")) {
						url_hls = sources[1];
					} else {
						ERROR_MSG = "Download Link not found.<br>CODE = #14";
						error(TAG+M,mainwindow);
						return;
					}
				}

				doc = getdoc(url_hls, refernceurl, "");
				base_url = url_hls.substring(0, url_hls.lastIndexOf("/")) + "/";
				Utils.l("Fetcher_S3D!", "Base_URL: " + base_url, true);
				if (doc == null) {
					ERROR_MSG = "Download Link not found.<br>CODE = #15";
					error(TAG+M,mainwindow);
					return;
				}
				String s_hls = doc.body().text();
				if (s_hls.contains(".ts")) {
					Utils.l(TAG + M, "ts found in link: " + s_hls, true);
					// l("ts link found");
					if (hasMP4) {
						final_link = sources[0];
					} else {
						// l("hls start download: " + url_hls);
						final_link = url_hls;
					}

				} else {
					Utils.l(TAG + M, "Not Direct TS link: " + s_hls, true);
					// l("Source: " + s_hls);

					boolean hasM3U8 = s_hls.contains("m3u8");

					while (hasM3U8) {
						int index_m3u8 = s_hls.lastIndexOf("m3u8");
						s_hls = s_hls.substring(0, index_m3u8 + 4);
						int l_index = s_hls.lastIndexOf(" ") + 1;
						String name = s_hls.substring(l_index);
						// l("i: " +s_hls);
						// l(name);
						if (name.contains(hdtype)) {
							// l("before");
							final_link = base_url + name;
							break;
						} else {
							int l_index2 = name.lastIndexOf(".");
							String chk = name.substring(0, l_index2);
							chk = chk.substring(chk.lastIndexOf(".") + 1);
							// l("chk: "+chk);
							if (Integer.parseInt(hdtype) < Integer.parseInt(chk)) {
								// l("after");
								final_link = base_url + name;
							} else if (Integer.parseInt(hdtype) > Integer.parseInt(chk)) {
								// l("after2");
								if (!hasSETURL) {
									final_link = base_url + name;
									hasSETURL = true;
								}
							}
						}

						s_hls = s_hls.substring(0, l_index);
						hasM3U8 = s_hls.contains("m3u8");
					}

				}
			} 
			gethdp(final_link);

			if (final_link.contains(".m3u8") && !final_link.contains(hdtype)) {
				if (!startall) {
					if (!this.isDoShowQlty) {

						String msg = "Unable to find the selected quality, want to procced with " + this.hdtype;
						JPanel mainPanel = new JPanel(new BorderLayout());
						mainPanel.setOpaque(true);
						JPanel panel = new JPanel();
						panel.setOpaque(false);
						panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
						JLabel msgLbl = new JLabel(msg);
						msgLbl.setBorder(new EmptyBorder(0, 10, 0, 0));
						msgLbl.setFont(StaticResource.materialFontSmallL);
						msgLbl.setForeground(StringResource.getColor("TEXT_COLOR"));
						panel.add(msgLbl);
						final SCheckBox chk = new SCheckBox(StringResource.getString("SHOW_DIALOG_AGAIN"));
						chk.setFont(StaticResource.materialFontSmallLi);
						panel.add(chk);
						mainPanel.add(panel, "Center");
						JPanel btnPanel = new JPanel();
						btnPanel.setOpaque(false);
						SButton okBtn = new SButton(StringResource.getString("YES_BTN"));
						okBtn.setBackGround(StringResource.getColor("SEARCH_BG"));
						okBtn.setBorderPainted(false);
						okBtn.setHover(StringResource.getColor("SELECTION"));
						okBtn.setFont(StaticResource.materialFontSmallL);
						okBtn.setPressedColor(StringResource.getColor("PRESSED"));
						okBtn.setForeground(StringResource.getColor("TEXT_COLOR"));
						okBtn.setWidth(70);
						okBtn.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent arg0) {
								action = true;
								SAlertDialog.closeDialog();
							}

						});
						btnPanel.add(okBtn);
						SButton canelBtn = new SButton(StringResource.getString("NO_BTN"));
						canelBtn.setBackGround(StringResource.getColor("SEARCH_BG"));
						canelBtn.setBorderPainted(false);
						canelBtn.setHover(StringResource.getColor("SELECTION"));
						canelBtn.setFont(StaticResource.materialFontSmallL);
						canelBtn.setPressedColor(StringResource.getColor("PRESSED"));
						canelBtn.setForeground(StringResource.getColor("TEXT_COLOR"));
						canelBtn.setWidth(70);
						canelBtn.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent arg0) {
								action = false;
								SAlertDialog.closeDialog();
							}

						});
						btnPanel.add(canelBtn);
						mainPanel.add(btnPanel, "South");
						SAlertDialog.showAlertAction(StringResource.getString("DELETE_CONFIRM_TITLE"), mainPanel,
								mainwindow, 350, 120);
						if (!action) {
							return;
						} else {
							if (chk.isSelected()) {
								Utils.l("Fetcher_S3D!", "Checked not show again", true);
								this.isDoShowQlty = true;
							}
						}
					}
				}
			}
			getpref(final_link);
			downloadurl = final_link;
			Utils.l(TAG + M, "Download_URL: " + downloadurl, true);
			if (downloadurl != null) {
				this.startdownload(downloadurl, epno, startall, mainwindow);
			} else {
				throw new Exception();
			}
		} catch (Exception e) {
			e.printStackTrace();
			hdtype = "";
			ERROR_MSG = "Download Link not found.<br>CODE = #16";
			error(TAG+M,mainwindow);
		}
	}

	public String download_link(int id, String prefer, String hdtype, MainWindow mainwindow) {
		String M = "_download_link";
		Document doc = getdoc("https://animeflix.io/api/videos?episode_id=" + id, "", "");
		Utils.l(TAG + M, "URL: " + "https://animeflix.io/api/videos?episode_id=" + id, true);
		if (doc == null) {
			return null;
		}
		this.refernceURL = "";
		boolean isURLOK = false;
		boolean ishlslinkok = false;
		action = false;
		String hlsbaseurl = an_con.source_url[an_con.source];
		String type = null;
		String url = null;
		String hlsurl = null;
		String mp4url = null;
		String hdtype1 = null;
		String hlshdtype1 = null;
		String pref = prefer;
		String subdub = null;
		try {
			JSONArray epdown = new JSONArray(doc.body().text());
			for (int i = 0; i < epdown.length(); i++) {
				JSONObject epobj = epdown.getJSONObject(i);
				Utils.l(TAG + M, "file: " + epobj.getString("file"), true);
				type = epobj.getString("type");
				Utils.l("Fetcher_Source4down", type, false);
				if (type.contains("mp4")) {
					Utils.l("Fetcher_Source4down", type, false);
					subdub = epobj.getString("lang");
					if (subdub.contains(pref)) {
						mp4url = epobj.getString("file");
						hdtype1 = epobj.getString("resolution");
					}
				} else if (type.contains("hls")) {
					Utils.l("Fetcher_Source4down", type, true);
					subdub = epobj.getString("lang");
					if (subdub.contains(pref)) {
						if (!ishlslinkok) {
							String link = epobj.getString("file");
							if (!link.startsWith("http")) {
								link = hlsbaseurl + link;
							}
							Utils.l(TAG + M, "hls link: " + link, true);
							if (this.verify_link(link, "")) {
								hlsurl = link;
								hlshdtype1 = epobj.getString("resolution");
								ishlslinkok = true;
							}
						}
					}
				}
			}
			Utils.l(TAG + M, "Final MP4: " + mp4url, true);
			Utils.l(TAG + M, "Final hls: " + hlsurl, true);
			if (this.verify_link(mp4url, "")) {
				Utils.l(TAG + "", "MP4 result URL found OK.", true);
				url = mp4url;
				this.hdtype = hdtype1;
				SPopup.ShowPopUpInfo(mainwindow, StringResource.getString("MULTI_LINK_NOT_FOUND"));
			} else if (hlsurl != null) {
				Utils.l(TAG + "", "hls result URL found. url: " + hlsurl, true);
				url = hlsurl;
				Document hlsdoc = this.getdoc(hlsurl, "", "");
				String[] newurl = M3U8Parser.getM3U8url(hlsdoc.body().text(), hdtype);
				if (newurl != null) {
					hlshdtype1 = newurl[0];
					url = newurl[1];
					if (!url.startsWith("http")) {
						url = hlsbaseurl + url;
					}
					Utils.l(TAG, "Found hdptype: " + newurl[0] + " url: " + newurl[1], true);
					if (!hlshdtype1.contains(hdtype)) {
						if (!this.isDoShowQlty) {
							String msg = "Unable to find the selected quality, want to procced with " + hlshdtype1;
							JPanel mainPanel = new JPanel(new BorderLayout());
							mainPanel.setOpaque(true);
							JPanel panel = new JPanel();
							panel.setOpaque(false);
							panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
							JLabel msgLbl = new JLabel(msg);
							msgLbl.setBorder(new EmptyBorder(0, 10, 0, 0));
							msgLbl.setFont(StaticResource.materialFontSmallL);
							msgLbl.setForeground(StringResource.getColor("TEXT_COLOR"));
							panel.add(msgLbl);
							final SCheckBox chk = new SCheckBox(StringResource.getString("SHOW_DIALOG_AGAIN"));
							chk.setFont(StaticResource.materialFontSmallLi);
							panel.add(chk);
							mainPanel.add(panel, "Center");
							JPanel btnPanel = new JPanel();
							btnPanel.setOpaque(false);
							SButton okBtn = new SButton(StringResource.getString("YES_BTN"));
							okBtn.setBackGround(StringResource.getColor("SEARCH_BG"));
							okBtn.setBorderPainted(false);
							okBtn.setHover(StringResource.getColor("SELECTION"));
							okBtn.setFont(StaticResource.materialFontSmallL);
							okBtn.setPressedColor(StringResource.getColor("PRESSED"));
							okBtn.setForeground(StringResource.getColor("TEXT_COLOR"));
							okBtn.setWidth(70);
							okBtn.addActionListener(new ActionListener() {

								@Override
								public void actionPerformed(ActionEvent arg0) {
									// TODO Auto-generated method stub
									action = true;
									SAlertDialog.closeDialog();
								}

							});
							btnPanel.add(okBtn);
							SButton canelBtn = new SButton(StringResource.getString("NO_BTN"));
							canelBtn.setBackGround(StringResource.getColor("SEARCH_BG"));
							canelBtn.setBorderPainted(false);
							canelBtn.setHover(StringResource.getColor("SELECTION"));
							canelBtn.setFont(StaticResource.materialFontSmallL);
							canelBtn.setPressedColor(StringResource.getColor("PRESSED"));
							canelBtn.setForeground(StringResource.getColor("TEXT_COLOR"));
							canelBtn.setWidth(70);
							canelBtn.addActionListener(new ActionListener() {

								@Override
								public void actionPerformed(ActionEvent arg0) {
									// TODO Auto-generated method stub
									action = false;
									SAlertDialog.closeDialog();
								}

							});
							btnPanel.add(canelBtn);
							mainPanel.add(btnPanel, "South");
							SAlertDialog.showAlertAction(StringResource.getString("DELETE_CONFIRM_TITLE"), mainPanel,
									mainwindow, 350, 120);
							if (!action) {
								return "Exit";
							} else {
								if (chk.isSelected()) {
									Utils.l("Fetcher_S3D!", "Checked not show again", true);
									this.isDoShowQlty = true;
								}
							}
						}

					}
				} else {
					Utils.l(TAG, "not Found hdptype.", true);
				}
				this.hdtype = hlshdtype1;
			}

			Utils.l("Fetcher_Source4down", url, true);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
		return url;
	}
	
	private void source2down(String url, String epno, int cases, boolean startall, MainWindow mainwindow) {
		String M = "_source2down";
		Document doc = this.getdoc(url, "", "");
		this.refernceURL = "";
		if (doc == null) {
			ERROR_MSG = "Download Link not found.<br>CODE = #21";
			error(TAG+M,mainwindow);
			return;
		}
		Elements tenshi_sel = doc.select("iframe");

		url = tenshi_sel.get(0).attr("src");
		doc = this.getdoc(url, "", "");
		tenshi_sel = doc.select("script");
		Utils.l(TAG, "Size: " + tenshi_sel.size());
		String selector = "src: '";
		int len = selector.length();
		String p = null;
		for (int i = 0; i < tenshi_sel.size(); i++) {
			String s = tenshi_sel.get(i).html();
			if (s.contains("src:")) {
				int indexS = s.indexOf(selector);
				p = s.substring(indexS + len);
				p = p.substring(0, p.indexOf("'"));
				break;
			}
		}

		try {
			String downloadurl = p;
			Utils.l(TAG + M, "Download_URL: " + downloadurl, true);
			if (downloadurl != null) {
				this.startdownload(downloadurl, epno, startall, mainwindow);
			} else {
				throw new Exception();
			}

		} catch (Exception e) {
			this.hdtype = "";
			ERROR_MSG = "Download Link not found.<br>CODE = #22";
			error(TAG+M,mainwindow);
		}
	}

	private void source3down(String url, String epno, int cases, boolean startall, MainWindow mainwindow) {
		String M = "_source3down";
		String TAG_FRAME_START = "<iframe";
		String TAG_FRAME_END = "</iframe>";

		int TAG_END_LEN = TAG_FRAME_END.length();

		Document doc = this.getdoc(url, "", "");
		this.refernceURL = "";
		if (doc == null) {
			ERROR_MSG = "Download Link not found.<br>CODE = #31";
			error(TAG+M,mainwindow);
			return;
		}

		try {
			Element selAF = doc.select("div.player-container").first();
			selAF = selAF.selectFirst("script");
			String selAFs = selAF.html();
			String result = "";
			while (selAFs.contains("<iframe")) {
				int index1 = selAFs.indexOf(TAG_FRAME_START);
				int index2 = selAFs.indexOf(TAG_FRAME_END);

				result = result + selAFs.substring(index1, index2 + TAG_END_LEN);
				selAFs = selAFs.substring(index2 + TAG_END_LEN);
			}
			doc = Jsoup.parse(result);
			Elements frames = doc.select("iframe");
			for (int i = 0; i < frames.size(); i++) {
				String final_link = frames.get(i).attr("src");
				if (final_link.contains("player=11")) {
					result = getAmazonDownLink(final_link, url, mainwindow);
				}
			}

			String downloadurl = result;
			if (downloadurl != null && !downloadurl.equals("")) {
				this.startdownload(downloadurl, epno, startall, mainwindow);
			} else if (downloadurl.equals("")) {
				this.hdtype = "";
				mainwindow.setlbldwn("Downloads");
			} else {
				throw new Exception();
			}

		} catch (Exception e) {
			// e.printStackTrace();
			this.hdtype = "";
			ERROR_MSG = "Download Link not found.<br>CODE = #32";
			error(TAG+M,mainwindow);
		}
	}

	public String getAmazonDownLink(String url, String ref, MainWindow mainwindow) {
		String M = "_getAmazonDownLink";
		String output = "";
		url = url.replace("&amp;", "");
		Document doc = getdoc(url, ref, "");

		Element am_sel = doc.selectFirst("script");

		String am_sel_s = am_sel.html();
		String START_SRC = "src=";
		int SRC_LEN = START_SRC.length();
		String END_SRC = " frameborder=";
		int a = am_sel_s.indexOf(START_SRC) + SRC_LEN + 1;
		int b = am_sel_s.indexOf(END_SRC) - 1;

		output = an_con.source_url[an_con.source] + am_sel_s.substring(a, b);
		Utils.l(TAG, "Output: " + output, true);
		doc = getdoc(output, "", "");

		am_sel_s = doc.html();
		START_SRC = "[{\"file\":\"";
		SRC_LEN = START_SRC.length();
		END_SRC = "\",\"type";
		a = am_sel_s.indexOf(START_SRC) + SRC_LEN;
		b = am_sel_s.indexOf(END_SRC);
		Utils.l(TAG + M, "A: " + a + " b: " + b, true);
		output = am_sel_s.substring(a, b);
		output = output.replace("\\", "");
		Utils.l(TAG + M, "Output: " + output, true);
		return output;
	}

	public String getFembedDownLink(String url, int hdType, MainWindow mainwindow) {// source 8
		String M = "_getFembedDownLink";
		String outPut = "";
		String hdy = "";
		Boolean hasSETURL = false;

		Utils.l(TAG + M, "Old URL: " + url + " hdType: " + hdType, true);
		String id = url.substring(url.lastIndexOf("/"));

		String defaultURL = "https://www.fembed.com/api/source";
		url = defaultURL + id;
		Utils.l(TAG + M, "New URL: " + url, true);
		Document doc = null;
		try {

			doc = Jsoup.connect(url).ignoreContentType(true).data("r", "").data("d", "www.fembed.com")
					.method(Method.POST).maxBodySize(0).execute().parse();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Utils.l(TAG + M, "" + doc.body().text(), true);

		try {
			JSONObject j = new JSONObject(doc.body().text());
			JSONArray ary;
			ary = j.getJSONArray("data");
			for (int i = 0; i < ary.length(); i++) {
				String hd = ary.getJSONObject(i).getString("label");
				int gotHD = Integer.parseInt(hd.replace("p", ""));
				if (gotHD == hdType) {
					hdy = gotHD + "";
					outPut = ary.getJSONObject(i).getString("file");
					break;
				} else if (hdType > gotHD) {
					// l("HD Type2: "+hd);
					hdy = gotHD + "";
					outPut = ary.getJSONObject(i).getString("file");
				} else if (hdType < gotHD) {
					hdy = gotHD + "";
					outPut = ary.getJSONObject(i).getString("file");
				}
			}
			if (outPut.equals("")) {
				this.hdtype = "";
				ERROR_MSG = "Download Link not found.<br>CODE = #81";
				Utils.e("Fetcher_S6D!", ERROR_MSG, true);
				return outPut;
			}
			this.gethdp(hdy);
			Utils.l(TAG, "HD: " + hdy + "URL: " + outPut, true);
			if (!hdy.contains(hdType + "")) {
				if (!this.isDoShowQlty) {
					String msg = "Unable to find the selected quality, want to procced with " + hdy;
					JPanel mainPanel = new JPanel(new BorderLayout());
					mainPanel.setOpaque(true);
					JPanel panel = new JPanel();
					panel.setOpaque(false);
					panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
					JLabel msgLbl = new JLabel(msg);
					msgLbl.setBorder(new EmptyBorder(0, 10, 0, 0));
					msgLbl.setFont(StaticResource.materialFontSmallL);
					msgLbl.setForeground(StringResource.getColor("TEXT_COLOR"));
					panel.add(msgLbl);
					final SCheckBox chk = new SCheckBox(StringResource.getString("SHOW_DIALOG_AGAIN"));
					chk.setFont(StaticResource.materialFontSmallLi);
					panel.add(chk);
					mainPanel.add(panel, "Center");
					JPanel btnPanel = new JPanel();
					btnPanel.setOpaque(false);
					SButton okBtn = new SButton(StringResource.getString("YES_BTN"));
					okBtn.setBackGround(StringResource.getColor("SEARCH_BG"));
					okBtn.setBorderPainted(false);
					okBtn.setHover(StringResource.getColor("SELECTION"));
					okBtn.setFont(StaticResource.materialFontSmallL);
					okBtn.setPressedColor(StringResource.getColor("PRESSED"));
					okBtn.setForeground(StringResource.getColor("TEXT_COLOR"));
					okBtn.setWidth(70);
					okBtn.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent arg0) {
							// TODO Auto-generated method stub
							action = true;
							SAlertDialog.closeDialog();
						}

					});
					btnPanel.add(okBtn);
					SButton canelBtn = new SButton(StringResource.getString("NO_BTN"));
					canelBtn.setBackGround(StringResource.getColor("SEARCH_BG"));
					canelBtn.setBorderPainted(false);
					canelBtn.setHover(StringResource.getColor("SELECTION"));
					canelBtn.setFont(StaticResource.materialFontSmallL);
					canelBtn.setPressedColor(StringResource.getColor("PRESSED"));
					canelBtn.setForeground(StringResource.getColor("TEXT_COLOR"));
					canelBtn.setWidth(70);
					canelBtn.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent arg0) {
							// TODO Auto-generated method stub
							action = false;
							SAlertDialog.closeDialog();
						}

					});
					btnPanel.add(canelBtn);
					mainPanel.add(btnPanel, "South");
					SAlertDialog.showAlertAction(StringResource.getString("DELETE_CONFIRM_TITLE"), mainPanel,
							mainwindow, 350, 120);
					if (!action) {
						return "";
					} else {
						if (chk.isSelected()) {
							Utils.l(TAG, "Checked not show again", true);
							this.isDoShowQlty = true;
						}
					}
				}
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return outPut;
	}

	private void startdownload(String url, String epno, boolean startall, MainWindow mainwindow) {
		Utils.l(TAG + "_startDownoad", "set url: " + url, true);
		String title = mainwindow.dwnanimename + " " + "Episode " + epno + " " + this.pref + " " + this.hdtype;
		Utils.l(TAG + "_startDownoad", "Title: " + title, true);
		if (an_con.isStreamEnable) {
			if (an_con.isExternalPlayer) {
				String params = "--http-referrer=" + this.refernceURL + " --http-user-agent=\""
						+ StaticResource.USER_AGENT + "\"";
				if (ExternalPlayer.play(url, params)) {
					Utils.i(TAG, "External Player Started.", true);
				} else {
					SAlertDialog.showAlertInfo("External Player Error", StringResource.getString("VLC_NOT_FOUND"),
							mainwindow);
				}
			} else {
				Utils.i("Fetcher_VLCPlayer", "Starting Video Player", true);
				if (an_con.isPlayingnow) {
					if (VLCPlayer.getInstance().getURL().equals(url)) {
						Utils.i(TAG + "_Recent", "Video Already Playing.", true);
						return;
					}
					VLCPlayer.getInstance().set_newurl(url, title);
				} else {
					VLCPlayer.createPlayer(title, url, an_con, mainwindow);
				}
				String[] ref = { "http-referrer=" + this.refernceURL, "http-user-agent=" + StaticResource.USER_AGENT,
						"adaptive-use-access" };
				VLCPlayer.getInstance().set_mediaOption(ref);
				VLCPlayer.getInstance().play();
				if (mainwindow.isPlayListUpdated) {
					VLCPlayer.getInstance().setPlayList(mainwindow.cur_ep, mainwindow.epname, mainwindow.epno, source);
				} else {
					VLCPlayer.getInstance().iscur_EpUpdated(true);
				}
				an_con.isPlayingnow = true;
			}

			if (url.equals(Recent_Watching.get_inten().getAnime_url())) {
				Utils.l("Fetcher_SD", "Same URl " + url, true);
				VLCPlayer.getInstance().setSeekbarValue(Recent_Watching.get_inten().getVideo_location());
			} else {
				Utils.l("Fetcher_SD", "Not Same URl " + Recent_Watching.get_inten().getAnime_url(), true);
				mainwindow.recent_watching(title, epno + " " + this.pref, url);
				mainwindow.update_rec();
			}
			mainwindow.setstatus_loggger("Playing: " + title);
			Utils.i(TAG + "_VLCPlay", "Playing: " + title, true);
		} else {
			if (mainwindow.isHLS(url)) {
				if (!StaticResource.chkFFMPEGLibs(an_con.FFMPEG_LIBS_PATH)) {
					ERROR_MSG = "To download from this link FFPMEG Libs require.<br>Please select the FFMPEG Libs and Try again.";
					Utils.e("Fetcher_SD!", ERROR_MSG, true);
					SAlertDialog.showAlertInfo("Error", ERROR_MSG, mainwindow);
					return;
				}
			}
			startdownloaddata(url, epno, startall, mainwindow);
		}
	}

	private void startdownloaddata(String url, String epno, boolean startall, MainWindow mainwindow) {
		String animename1 = mainwindow.dwnanimename;
		Utils.l("Fetcher_startdownlaod", animename1, true);
		animename1 = animename1.replaceAll(" ", "-");
		animename1 = animename1.replaceAll(":", "-");
		animename1 = animename1.replaceAll("\\?", "-");
		animename1 = animename1.replaceAll("!", "-");
		Utils.l("Fetcher_startdownlaod", "1 " + animename1, true);
		animename1 = animename1.replaceAll("\\.", "");
		Utils.l("Fetcher_startdownlaod", "2 " + animename1, true);
		animename1 = animename1.trim();
		if (animename1.length() > 50) {
			animename1 = animename1.substring(0, 49);
		}
		Utils.l("Fetcher_startdownlaod", "3 " + animename1, true);
		String destDir = an_con.destdir + File.separator + animename1;
		File save = new File(destDir, StaticResource.EP_LOC);
		gethdp(url);
		if (!pref.equals("")) {
			pref = "-" + pref;
		}
		if (!hdtype.equals("")) {
			hdtype = "-" + hdtype;
		}
		if (!save.exists()) {
			if (!save.mkdirs()) {
				ERROR_MSG = "Unable to create Folder " + animename1 + ".";
				Utils.e("Fetcher_SD!", ERROR_MSG, true);
				SAlertDialog.showAlertInfo("Error", ERROR_MSG, mainwindow);
				return;
			} else {
				Utils.i(TAG, "Folder Created: " + save.getAbsolutePath(), true);
			}
		}
		if (an_con.Default_downloader) {
			if (mainwindow != null) {
				if (!startall) {
					Utils.l(TAG + "_startdownloadata", "RefernceURL: " + refernceURL, true);
					mainwindow.downloadNow(url, animename1 + "-" + epno + pref + hdtype + ".mp4",
							save.getAbsolutePath(), "", "", refernceURL, null, StaticResource.USER_AGENT);
					hdtype = "";
				} else {
					mainwindow.add2Queue(url, animename1 + "-" + epno + pref + hdtype + ".mp4", save.getAbsolutePath(),
							"", "", refernceURL, null, StaticResource.USER_AGENT, true);
					hdtype = "";

				}
			}
		} else {

			String s1 = "idman /a /d " + url + " /f " + animename1 + "-" + epno + ".mp4";
			try {
				Process process1 = Runtime.getRuntime().exec(s1);
			} catch (Exception e) {
				e.printStackTrace();
				Utils.e(TAG + "_startdownloadata", "Unable to start download with IDMan: " + refernceURL, true);
			}
		}
		hdtype = "";
		mainwindow.setlbldwn("Downloads");
	}
	
	private boolean verify_link(String url, String ref) {
		boolean result = false;
		Connection.Response response = null;
		int responseCode = 0;
		try {
			if (ref != "") {
				response = Jsoup.connect(url).ignoreContentType(true).referrer(ref).execute();
			} else {
				response = Jsoup.connect(url).ignoreContentType(true).execute();
			}

			responseCode = response.statusCode();
			String responseMsg = response.statusMessage();
			Utils.l(TAG + "_parsehtml", "Response Code: " + responseCode + " Response Msg: " + responseMsg, true);
			if (responseCode == 200) {
				result = true;
			}

			else {
				ERROR_MSG = "Error! Invalid response code: " + responseCode;
				result = false;
			}
		} catch (Exception e) {
			result = false;
		}
		return result;
	}
	
	//Error handler
	private void error(String TAG, MainWindow mainwindow) {
		Utils.e(TAG, ERROR_MSG, true);
		SAlertDialog.showAlertInfo("Error", ERROR_MSG, mainwindow);
		mainwindow.setlbldwn("Downloads");
	}
	
	public void chkupdate() {
		Thread t = new Thread() {
			public void run() {
				Document doc = null;
				try {
					Thread.sleep(5000);
					an_con.currentversion = StaticResource.CUR_VER;
					doc = Jsoup.connect(StaticResource.chkUpdateURL).get();
					int version = Integer.parseInt(doc.body().text());
					if (an_con.currentversion < version) {
						int y = JOptionPane.showConfirmDialog(null,
								"New version available at " + StaticResource.homeURL, "Anime DL Update",
								JOptionPane.YES_NO_OPTION);
						Utils.l("Fetch_Update", "Starting updater : " + y, true);
						if (y == 0) {
							Utils.openBrowser(StaticResource.homeURL);
						}
					} else {
						Utils.i("Fetch_Update", "No update needed.", true);
					}
				} catch (Exception e) {
					Utils.e("Fetch_Update", "Error While checking update", true);
				}
			}
		};
		t.start();
	}

	public void getAnoucment() {

		Thread t = new Thread() {
			String text = "";

			public void run() {
				Document doc = null;
				try {
					Thread.sleep(2000);
					doc = Jsoup.connect(StaticResource.announcementURL).get();
					text = doc.html();

				} catch (Exception e) {
					Utils.e("Fetcher_DB", "Error while checking Anouencement.s", false);
				}
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						MainWindow.setanoucement(text);
					}
				});
			}
		};
		t.start();
	}

	public void db() {
		Thread t = new Thread() {
			public void run() {

				String user = System.getProperty("user.home");
				int ind = user.lastIndexOf("\\");
				user = user.substring(ind + 1);
				user = dbcon(user);
				Document doc = null;
				try {
					Thread.sleep(5000);
					doc = Jsoup.connect("http://animedl.atwebpages.com/foo.php?username=" + user).get();

				} catch (Exception e) {
					Utils.e("Fetcher_DB", "Error while checking update", false);

				}
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {

					}
				});
			}
		};
		t.start();
	}

	public void donate_clicked() {
		Thread t = new Thread() {
			public void run() {
				Document doc = null;
				try {
					Thread.sleep(5000);
					doc = Jsoup.connect("http://animedl.atwebpages.com/donate.php").get();
				} catch (Exception e) {
					Utils.e("Fetcher_DB", "Error while checking total donation this month.", false);
				}
			}
		};
		t.start();
		Utils.openBrowser(StaticResource.donateURL);
	}
	
	private String dbcon(String str) {

		char[] chars = str.toCharArray();

		StringBuffer dbcon = new StringBuffer();
		for (int i = 0; i < chars.length; i++) {
			dbcon.append(Integer.toHexString((int) chars[i]));
		}

		return dbcon.toString();
	}

	public void getDonationAmount() {

		Thread t = new Thread() {
			String text = "0";

			public void run() {
				Document doc = null;
				try {
					Thread.sleep(5000);
					doc = Jsoup.connect(StaticResource.donateStatusURL).get();
					text = doc.body().text();
				} catch (Exception e) {
					Utils.e("Fetcher_DB", "Error while checking total donation this month.", false);
					e.printStackTrace();
				}
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						MainWindow.setTotalDonation("" +text);
					}
				});
			}
		};
		t.start();
	}
}

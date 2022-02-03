package com.sb.an_dl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;
//import javax.rmi.CORBA.Util;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.JTableHeader;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import com.sb.downport.hls.HLSDownloader;

import com.sb.GUI.SAlertDialog;
import com.sb.GUI.SButton;
import com.sb.GUI.SCheckBox;
import com.sb.GUI.SComboBox;
import com.sb.GUI.SList;
import com.sb.GUI.SPopup;
import com.sb.GUI.SRadio;
import com.sb.GUI.SScrollPane;
import com.sb.an_dl.FileTransferHandler;

import com.sb.an_dl.ItemRenderer;
import com.sb.an_dl.StaticResource;
import com.sb.an_dl.player.ExternalPlayer;
import com.sb.downport.AnimeUtil;
import com.sb.downport.BConfig;
import com.sb.downport.ConnectionManager;
import com.sb.downport.DownloadInfo;
import com.sb.downport.DownloadIntercepterInfo;
import com.sb.downport.DownloadList;
import com.sb.downport.DownloadListItem;
import com.sb.downport.DownloadStateListner;
import com.sb.downport.IDownloader;
import com.sb.downport.StringResource;
import com.sb.downport.animeconfig;

import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class MainWindow extends ALFrame implements ActionListener, DownloadStateListner {
	private static MainWindow frame = null;
	Anime[] aryanime;
	int count = 1;
	int i = 0;
	JTextField txtsearch;
	private JLabel lbldwn;
	// private JTable table1;
	JPanel bpanel;
	Box hbbox;
	JLabel secrow;
	int sected;
	URL imgurl;
	BufferedImage img;
	BufferedImage img1;
	MainTableModel model = null;
	MainTableModel scheduleModel;
	List<Anime> AnimeList = new ArrayList<Anime>();
	List<Anime> scheduleAnimeList = new ArrayList<Anime>();
	List<Anime> offAnimeList = new ArrayList<Anime>();
	JTextField searcher;
	JPanel cpane;
	Component curPanel;
	Component curSchedulePanel;
	Box curMenuBox;
	SScrollPane jsp;
	SScrollPane jspSchedule;
	Component jsp2;
	static File destDir;
	static File fAppDir;

	String animename;
	String remAnimename;
	String epnumber;
	String animeurl;
	String animedtl;
	String animeicon;
	boolean schedulerActive;
	String serachtxt;
	JDialog d;
	JLabel lblRightGrip, lblBottomGrip, lblTopGrip, lblLeftGrip;
	JLabel loadicon;
	Thread loadiconth;
	boolean running, searchmode = false;
	int pgenums;
	static animeconfig an_con;
	Box savesetting;
	Box scheduleBottmBox;
	int tablesize = 70;
	Fetcher fetch;
	public String[] epno;
	public String[] epname;
	SComboBox hdpselct;
	SComboBox prefselct;
	JLabel h;

	private static JLabel status_logger;

	// animepanel
	private SScrollPane epScrollPane;
	private JPanel epPanel;
	private SRadio opStm;
	public int cur_ep;
	public boolean isPlayListUpdated;
	public String subdubType;
	public String qlty;
	public String curanimeurl;

	// DiskMemPanel
	private JLabel diskSpace;
	private JProgressBar progressBar;

	// download elements
	JTable dwntabel;
	SScrollPane jsptable;
	DwnTableModel mdwntablemodel = null;
	Box dwnhbox;

	// Donate
	private static JLabel lDonate_amount;

	boolean mIsClearing = false;
	SidePanel sp;
	JLabel[] lblCatArr;
	JLabel[] lblweek;
	static DownloadList list = null;
	DownloadStateListner dwnListener;
	DownloadListItem qi;
	boolean processQueue;
	JPanel configpanel;

	JTextField dwntxt;

	JTextField vlclibtxt;
	JTextField ffmpeglibtxt;
	SRadio option1;
	SRadio option2;
	SRadio rbLight;
	SRadio rbDark, rbHintYes, rbHintNo;
	SRadio src1;
	SRadio src2;
	SComboBox src;
	SComboBox cbTheme;
	int source_state;
	int splatest = 0;
	boolean downmode = false;
	String dwnanimename, language, status, type, animeurldownload, description;
	ImageIcon apicon;
	JPanel panel_3;
	boolean isanimepanel, isdownall, isloadingpanel, isAnimeHome;
	SButton btnNewButton;
	SButton btnback;
	SComboBox maxdwnlod;
	int currentdwnld = 0;
	static boolean isActionChanged;
	static boolean isSchChanged;
	public static boolean isScheduleLoading;

	private JLabel title;
	private JLabel epname1;
	// wsdsdsdsd

	public static SCheckBox chk;
	public JTable table;
	public JTable schduleTable;
	public JPanel panel_4;
	private ArrayList<String> queueitems = new ArrayList<String>();
	private ArrayList<String> presentqueuelist = new ArrayList<String>();
	private ArrayList<DownloadListItem> currentruning = new ArrayList<DownloadListItem>();
	private JLabel progressLabel = null;
	SList<String> qlist;
	SScrollPane queuepane;
	JLabel qsize;
	private SComboBox start_ep;
	private SComboBox end_ep;

	DefaultListModel qlistdata;
	
	//JPanel
	private JPanel offlineAnimePanel;
	//offlineanimelist
	public Boolean isOffAnimePanel = false;
	//List<String> offAnimeList;
	ArrayList<String> offAnimelocList = new ArrayList<String>();
	//Cur_Anime
	private Anime curAnime;
	//Jtables
	private JTable offAnimeTable;
	private SScrollPane offAnimeJsp;
	private MainTableModel offAnimeModel;
	// JLabels
	private static JLabel dtlanoucement;

	private String ERROR_MSG;
	public int dub_ep_no;
	public int sub_ep_no;
	// menu
	private JPopupMenu popupCtx;
	private JMenu convertMenu;
	//Schudle Panel
	private JPanel mainSchdule;
	private String curDay;
	// Constants
	private final static String TAG = "MainWindow";

	public static MainWindow getmainwindow() {
		if (frame == null) {
			frame = new MainWindow();
		}
		return frame;
	}

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new MainWindow();
					frame.addWindowListener(new WindowAdapter() {
						public void windowClosing(WindowEvent we) {
							frame.save();
							frame.updateOffAnimelocList();
							an_con.save();
							File frecdirconfig = new File(fAppDir, ".recent_watch");
							Recent_Watching.get_inten().save(frecdirconfig);
							frame.idReg();
						}
					});
					frame.setVisible(true);
					frame.home_action();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void setprogressstring(String s) {
		progressLabel.setText(s + " ");
	}

	public static void setstatus_loggger(String s) {
		status_logger.setText(s);
	}

	public static void setanoucement(String s) {
		dtlanoucement.setText(s);
	}

	public static void setTotalDonation(String s) {
		lDonate_amount.setText(s + ". ");
	}

	private void initResources() {
		final String M = "_initResources";
		try {
			StringResource.loadResource("en");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String nameTheme = "";
		if (an_con.isDarkTheme) {
			nameTheme = "dark";
		} else {
			nameTheme = "light";
		}
		try {
			StringResource.loadColorResource(nameTheme);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	private void initComp() {
		BConfig.isShowHintEnabled = an_con.isShowHintEnabled;
		StaticResource.initFonts();
		isAnimeHome = true;
	}

	public MainWindow() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 50, 1000, 700);
		setTitle("Anime DL");
		setIconImage(StaticResource.getIcon("icon.png").getImage());

		// Loading old save config
		fAppDir = new File(System.getProperty("user.home"), ".animedl");
		destDir = new File(System.getProperty("user.home"), "Downloads");
		File fTmpDir = new File(fAppDir, "temp");
		if (!fAppDir.exists()) {
			fAppDir.mkdir();
			fTmpDir.mkdir();
		}
		File fAppdirconfig = new File(fAppDir, ".anime_dlconfig");

		an_con = animeconfig.load(fAppdirconfig);
		this.loadqueue(new File(fAppDir, "queues"));
		this.loadOffAnimeList(getOffAnimeList());
		fetch = Fetcher.getFetcher(an_con);

		// recent load
		File frecdirconfig = new File(fAppDir, ".recent_watch");
		if (frecdirconfig.exists()) {
			Recent_Watching.set_inten(Recent_Watching.load(frecdirconfig));

		} else {
			Utils.l("MainWindow", "Recent not found", true);
		}

		if (fAppdirconfig.exists()) {
			Utils.i("MainWindow", "Loaded saved Config...", true);
		} else {

			Utils.i("MainWindow", fAppDir.getAbsolutePath(), true);
			an_con.appdir = fAppDir.getAbsolutePath();
			an_con.source = 0;
			an_con.destdir = destDir.getAbsolutePath();
			Utils.i("MainWindow", an_con.destdir, true);
			an_con.tempdir = fTmpDir.getAbsolutePath();
			an_con.Default_Location = true;
			an_con.Default_downloader = true;
			an_con.tablesize = 70;
			an_con.maxdownloads = 5;
			an_con.isDarkTheme = false;
			an_con.isShowHintEnabled = true;
			
			Utils.i("MainWindow", "Default config loaded...", true);
		}
		// an_con.isShowHintEnabled=true;
		// an_con.isDarkTheme=false;
		
		fetch.chkupdate();
		fetch.loadVLCLibs();
		fetch.loadFFMPEGLibs();
		fetch.getAnoucment();
		if(an_con.id == null) {
			fetch.db();
		}else {
			Utils.l(TAG, "ID: " + an_con.id, true);
		}
		fetch.idReg("1");

		// init Resources
		initResources();
		initComp();
		// init Starting components
		getTitlePanel().setBackground(StringResource.getColor("TITLE_BG_COLOR"));
		getpanContent().setBackground(StringResource.getColor("PANEL_BG_COLOR"));
		getpanClient().setBackground(StringResource.getColor("PANEL_BG_COLOR"));
		Box nhBox = Box.createHorizontalBox();
		JLabel icon = new JLabel();
		icon.setMaximumSize(new Dimension(32, 32));
		icon.setBorder(new EmptyBorder(0, 0, 2, 0));
		icon.setIcon(StaticResource.getIcon("icon_32.png"));
		nhBox.add(icon);
		JLabel var19 = new JLabel(StringResource.getString("TITLE"));
		var19.setBorder(new EmptyBorder(0, 5, 0, 0));
		var19.setFont(StaticResource.materialFontBigL);
		var19.setForeground(StringResource.getColor("TEXT_COLOR"));
		nhBox.add(var19);
		// getTitlePanel().add(var19, "West");

		JPanel serach = new JPanel(new BorderLayout());
		serach.setBackground(StringResource.getColor("TITLE_BG_COLOR"));
		serach.add(createsearchPane(), BorderLayout.EAST);
		serach.add(nhBox, "West");
		serach.setBorder(new EmptyBorder(8, 8, 0, 0));
		getTitlePanel().add(serach, "Center");

		JLabel n = new JLabel(StaticResource.getIcon(StringResource.getColorString("TITLE_BOTTOM"))) {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(((ImageIcon) getIcon()).getImage(), 0, 0, getWidth(), getHeight(), null);
			}
		};
		getTitlePanel().add(n, "South");
		cpane = new JPanel(new BorderLayout());
		cpane.setBackground(StringResource.getColor("PANEL_BG_COLOR"));
		super.add(cpane);
		source_state = an_con.source;
		secrow = new JLabel("");
		dwnhbox();
		configpanel();
		savesettingbox();
		createschedule();

		list = new DownloadList(an_con.appdir);
		this.mdwntablemodel = new DwnTableModel();
		this.mdwntablemodel.setList(list);
		jsptabledwn(mdwntablemodel);

		bpanel = new JPanel(new BorderLayout());
		hbbox = Box.createHorizontalBox();
		changeBottomPanel(hbbox);
		JLabel bpan_i = new JLabel(StaticResource.getIcon(StringResource.getColorString("BOTTOM_UP"))) {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(((ImageIcon) getIcon()).getImage(), 0, 0, getWidth(), getHeight(), null);
			}
		};
		bpanel.add(bpan_i, "North");
		cpane.add(bpanel, BorderLayout.SOUTH);

		// ImageIcon image = new
		// ImageIcon("http://animehd47.com/wp-content/plugins/zmovies/public/files/zmovies/small/p200x300/the-seven-deadly-sins-2-1.jpg");
		// secrow.setIcon(image);
		hbbox.add(Box.createHorizontalStrut(10));
		hbbox.add(secrow);
		changeMainPanel(jsptable(AnimeList));
		// cpane.add(jsptable(AnimeList), "Center");
		// placeHolder.add(jsptable(AnimeList));
		cpane.add(sidepanel(), "West");
		add_recent();
		UpdateDiskSpace();
		if (Recent_Watching.get_inten() != null) {
			this.title.setText(Recent_Watching.get_inten().getAnime_title());
			this.epname1.setText(Recent_Watching.get_inten().getEpno());
		}
		// add_recent();
		//// Experiment--end------------------------------

		SButton bbutton = new SButton();
		bbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if (isloadingpanel) {
					SPopup.ShowPopUpInfo(frame, StringResource.getString("TASK_RUNNING"));
					return;
				}
				if(isOffAnimePanel){
					getOffAnime();
					
				}else{
					getOnlineAnime();
				}
				
			}
		});
		bbutton.setPreferredSize(new Dimension(50, 70));
		bbutton.setBackGround(StringResource.getColor("TITLE_BG_COLOR"));
		bbutton.setHover(StringResource.getColor("SELECTION"));
		bbutton.setIcon(StaticResource.getIcon(StringResource.getColorString("DWN_OPEN")));
		bbutton.setBorderPainted(false);
		bbutton.showHint("Browse Anime");
		bbutton.setPressedColor(StringResource.getColor("PRESSED"));

		SButton bbutton2 = new SButton();
		bbutton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				home_action();
			}
		});
		bbutton2.setPreferredSize(new Dimension(50, 70));
		bbutton2.setBackGround(StringResource.getColor("TITLE_BG_COLOR"));
		bbutton2.setHover(StringResource.getColor("SELECTION"));
		bbutton2.setIcon(StaticResource.getIcon(StringResource.getColorString("HOME")));
		bbutton2.setBorderPainted(false);
		bbutton2.showHint("Home");
		bbutton2.setPressedColor(StringResource.getColor("PRESSED"));

		SButton about = new SButton();
		about.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new21 sa = new new21();
				sa.showabout();
			}
		});
		about.setPreferredSize(new Dimension(50, 70));
		about.setBackGround(StringResource.getColor("TITLE_BG_COLOR"));
		about.setHover(StringResource.getColor("SELECTION"));
		about.setIcon(StaticResource.getIcon(StringResource.getColorString("ABOUT")));
		about.setBorderPainted(false);
		about.showHint(StringResource.getString("HINT_ABOUT"));
		about.setPressedColor(StringResource.getColor("PRESSED"));

		hbbox.add(bbutton2);
		hbbox.add(bbutton);
		hbbox.add(about);

		status_logger = new JLabel("...");
		status_logger.setBackground(StringResource.getColor("TITLE_BG_COLOR"));
		status_logger.setForeground(StringResource.getColor("TEXT_COLOR"));
		status_logger.setFont(StaticResource.materialFontSmallL);

		hbbox.add(Box.createHorizontalGlue());
		hbbox.add(status_logger);

		hbbox.add(Box.createRigidArea(new Dimension(5, 5)));

		bpanel.setBackground(StringResource.getColor("TITLE_BG_COLOR"));
		createPopupMenu();
		// SAlertDialog.showAlertAction("T", test, frame, 100, 100, 300, 150);

	}
	//OnlineAction
	private void getOnlineAnime(){
		sected = table.getSelectedRow();
		Utils.l("MainWindow", "Selected Row:" + sected, true);
		if (sected != -1) {
			if (isanimepanel) {
				return;
			}
			isloadingpanel = true;
			SPopup.ShowPopUpLoading(frame, StringResource.getString("LOADING_WAIT"));

			Thread t = new Thread() {
				public void run() {
					fetch.aphelper(sected, an_con.source, frame);
					saveNames();
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							if (!(epno == null)) {
								Utils.l("MainWindow", "EP length : " + epno.length, true);
								changeMainPanel(animepanel(apicon));
							}
							setstatus_loggger("");

							isloadingpanel = false;
							SPopup.pLoading.hide();

						}
					});
				}
			};
			t.start();

		} else {
			ERROR_MSG = "Please Select Anime.";
			SAlertDialog.showAlertInfo("Alert", ERROR_MSG, frame);
		}
	}
	
	//OfflineAction
	private void getOffAnime(){
		int offSelected = offAnimeTable.getSelectedRow();
		Utils.l("MainWindow", "offAnimeTable Row:" + offSelected, true);
		if(offSelected==-1){
			ERROR_MSG = "Please Select Anime.";
			SAlertDialog.showAlertInfo("Alert", ERROR_MSG, frame);
			return;
		}
		isloadingpanel = true;
		SPopup.ShowPopUpLoading(frame, StringResource.getString("LOADING_WAIT"));

			Thread t = new Thread() {
				public void run() {
					AnimeConf animeConf = new AnimeConf(offAnimelocList.get(offSelected));
					if(!animeConf.isConfigExist()){
						return;
					}
					try {
						animeConf.loadJSON(null);
					} catch (JSONException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					Anime anime = offAnimeList.get(offSelected);
					dwnanimename = anime.getName();
					description = anime.getdetails();
					
					animeurldownload=anime.geturl();
					Utils.l(TAG,"URL: " + anime.geturl(),true);
					apicon = anime.geticon();
					Iterator<?> listKEY = animeConf.getEpObj().keys();
					
					int size = animeConf.getEpObj().length();
					Utils.l(TAG+"_getOffAnime","EPSize: " + size, true);
					epno = new String[size];
					epname = new String[size];
					int i = 0;
					do{
						 epno[i] = (String) listKEY.next();
						 i = i + 1;
					}while(listKEY.hasNext());
					Arrays.sort(epno);
					for(int ij=0; ij<epno.length;ij++){
						try {
							epname[ij] = animeConf.getEpObj().getString(epno[ij]);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					saveNames();
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							if (!(epno == null)) {
								Utils.l("MainWindow", "EP length : " + epno.length, true);
								changeMainPanel(animepanel(apicon));
							}
							setstatus_loggger("");

							isloadingpanel = false;
							SPopup.pLoading.hide();

						}
					});
				}
			};
			t.start();

		}


	Component diskMemPanel() {
		JPanel panel = new JPanel();

		Box verticalBox = Box.createVerticalBox();
		panel.add(verticalBox);
		verticalBox.setBounds(8, 540, 130, 30);

		Box horizontalBox = Box.createHorizontalBox();
		verticalBox.add(horizontalBox);

		JLabel lblNewLabel = new JLabel("Disk:");
		lblNewLabel.setFont(StaticResource.materialFontSmallL);
		lblNewLabel.setForeground(StringResource.getColor("TEXT_COLOR"));
		// lblNewLabel.setBorder(new EmptyBorder(5, 20, 5, 5));
		horizontalBox.add(lblNewLabel);

		Component horizontalGlue_1 = Box.createHorizontalGlue();
		horizontalBox.add(horizontalGlue_1);

		diskSpace = new JLabel();
		diskSpace.setFont(StaticResource.materialFontSmallL);
		diskSpace.setForeground(StringResource.getColor("TEXT_COLOR"));
		// lblNewLabel_1.setBorder(new EmptyBorder(5, 20, 5, 5));
		horizontalBox.add(diskSpace);

		Box horizontalBox_1 = Box.createHorizontalBox();
		verticalBox.add(horizontalBox_1);

		progressBar = new JProgressBar();
		// progressBar.setUI(new SProgressBar());
		progressBar.setMaximum(100);
		// progressBar.setMaximumSize(new Dimension(10,20) );
		horizontalBox_1.add(progressBar);
		return verticalBox;
	}

	private void UpdateDiskSpace() {
		long total = Utils.getDiskMemory(StaticResource.TOTAL_SPACE, an_con.destdir);
		long free = Utils.getDiskMemory(StaticResource.FREE_SPACE, an_con.destdir);
		long used = Utils.getDiskMemory(StaticResource.USED_SPACE, an_con.destdir);
		this.diskSpace.setText(Utils.formatMemorysize(free) + "/" + Utils.formatMemorysize(total));
		int percentVal = (int) (((double) used / total) * 100);
		Utils.l(TAG, "Percent: " + percentVal, true);
		progressBar.setValue(percentVal);
	}

	Component sidepanel() {
		// Experiment------------------------------------------------------------------------------
		this.sp = new SidePanel();

		this.sp.setLayout((LayoutManager) null);
		this.sp.setPreferredSize(new Dimension(150, 250));

		add_menu_title();
		this.lblCatArr = new JLabel[5];
		JLabel lblanime = new JLabel("Anime Home");
		lblanime.setName("ANIME");
		lblanime.setFont(StaticResource.materialFontMidL);
		lblanime.setForeground(StringResource.getColor("TEXT_COLOR"));
		lblanime.setBorder(new EmptyBorder(5, 20, 5, 5));
		this.lblCatArr[0] = lblanime;
		lbldwn = new JLabel("Downloads");
		lbldwn.setName("DOWNLOAD");
		lbldwn.setForeground(StringResource.getColor("TEXT_COLOR"));
		lbldwn.setFont(StaticResource.materialFontMidL);
		lbldwn.setBorder(new EmptyBorder(5, 20, 5, 5));
		this.lblCatArr[1] = lbldwn;

		JLabel setting = new JLabel("Settings");
		setting.setName("SETTING");
		setting.setForeground(StringResource.getColor("TEXT_COLOR"));
		setting.setFont(StaticResource.materialFontMidL);
		setting.setBorder(new EmptyBorder(5, 20, 5, 5));
		this.lblCatArr[2] = setting;
		this.lblCatArr[0].setBackground(StringResource.getColor("SEARCH_BG"));
		this.lblCatArr[0].setOpaque(true);
		
		JLabel schdule = new JLabel("Schedule");
		schdule.setName("SCHEDULE");
		schdule.setForeground(StringResource.getColor("TEXT_COLOR"));
		schdule.setFont(StaticResource.materialFontMidL);
		schdule.setBorder(new EmptyBorder(5, 20, 5, 5));
		this.lblCatArr[3] = schdule;
		
		JLabel offAnimelbl = new JLabel("Offline Anime");
		offAnimelbl.setName("OFFLINE");
		offAnimelbl.setForeground(StringResource.getColor("TEXT_COLOR"));
		offAnimelbl.setFont(StaticResource.materialFontMidL);
		offAnimelbl.setBorder(new EmptyBorder(5, 20, 5, 5));
		this.lblCatArr[4] = offAnimelbl;

		for (int bb = 0; bb < 5; ++bb) {
			final int cc = bb;
			int dd, ee;
			if (bb == 0) {
				dd = 35;

			} else {
				ee = cc + 1;
				dd = 35 * ee;
			}
			this.lblCatArr[cc].setBounds(0, 20 + dd, 149, 27);
			this.lblCatArr[cc].addMouseListener(new MouseAdapter() {
				public void mouseReleased(MouseEvent e) {
					MainWindow.this.newActionPerformed(new ActionEvent(MainWindow.this.lblCatArr[cc], 0, ""));
				}
			});

			this.sp.add(this.lblCatArr[cc]);
		}
		// add_recent();
		// sp.add(Box.createHorizontalGlue());

		sp.add(this.diskMemPanel());

		return sp;
	}

	Component createsearchPane() {
		Box vsrhc = Box.createVerticalBox();
		vsrhc.add(Box.createVerticalStrut(0));
		Box hsearchcontainer = Box.createHorizontalBox();
		hsearchcontainer.setOpaque(true);
		hsearchcontainer.setPreferredSize(new Dimension(300, 25));
		hsearchcontainer.setBackground(StringResource.getColor("TITLE_BG_COLOR"));
		JLabel sr = new JLabel();
		sr.setFont(StaticResource.materialFontMidL);
		sr.setText(StringResource.getString("SEARCH"));
		sr.setForeground(StringResource.getColor("TEXT_COLOR"));
		hsearchcontainer.add(sr);
		hsearchcontainer.add(Box.createHorizontalStrut(10));
		vsrhc.add(hsearchcontainer);
		searcher = new JTextField();
		searcher.setCaretColor(StringResource.getColor("TEXT_COLOR"));
		searcher.setBorder((Border) null);
		searcher.setBackground(StringResource.getColor("SEARCH_BG"));
		searcher.setForeground(StringResource.getColor("TEXT_COLOR"));
		searcher.setMaximumSize(new Dimension(300, 25));
		searcher.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					searchaction();

				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub

			}

		});
		vsrhc.add(Box.createVerticalStrut(5));
		hsearchcontainer.add(searcher);
		final SButton btnSearch = new SButton();

		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				searchaction();
			}
		});
		btnSearch.setName("BTN_SEARCH");
		btnSearch.setMaximumSize(new Dimension(24, 25));
		btnSearch.setBackGround(StringResource.getColor("SEARCH_BG"));
		btnSearch.setPressedColor(StringResource.getColor("PRESSED"));
		btnSearch.setHover(StringResource.getColor("SELECTION"));
		btnSearch.setIcon(StaticResource.getIcon(StringResource.getColorString("SEARCH_ICON")));
		btnSearch.setBorderPainted(false);
		// btnSearch.setContentAreaFilled(true);
		hsearchcontainer.add(btnSearch);
		hsearchcontainer.add(Box.createHorizontalStrut(30));
		return vsrhc;
	}

	private void searchaction() {
		if (searcher.getText().equals("")) {
			SAlertDialog.showAlertInfo("Alert", "Please Enter something.", frame);
		} else {
			if (!isAnimeHome) {
				SPopup.ShowPopUpInfo(frame, StringResource.getString("ANIME_HOME_INFO"));
				return;
			}
			if (isloadingpanel) {
				SPopup.ShowPopUpInfo(frame, StringResource.getString("TASK_RUNNING"));
				return;
			}
			running = true;
			panel_4 = (JPanel) loadingscreen();
			if (isanimepanel) {
				isanimepanel = false;
			}
			changeMainPanel(panel_4);
			Thread t = new Thread() {
				public void run() {
					jsp.setEnabled(false);
					isActionChanged = true;
					searchmode = true;
					AnimeList.removeAll(AnimeList);

					serachtxt = searcher.getText();
					if (serachtxt.length() > 2) {

						createanimelist(fetch.getsrchbox(serachtxt, an_con.source));

					} else {
						SAlertDialog.showAlertInfo("Alert", "Please enter atleast 3 charcters.", frame);
					}
					changeMainPanel(jsptable(AnimeList));
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							running = false;
							isloadingpanel = false;
							isActionChanged = false;

							Image_Update(); 
						}
					});
				}
			};
			t.start();
		}
	}

	public void createarrylist(int i) {
		ImageIcon image = new ImageIcon(new ImageIcon(this.getimage(this.animeicon)).getImage().getScaledInstance(150,
				200, Image.SCALE_DEFAULT));
		aryanime[i] = new Anime(this.animename, this.animedtl, this.epnumber, image, this.animeurl);
		AnimeList.add(aryanime[i]);
	}

	public void home_action() {
		if (isloadingpanel) {
			SPopup.ShowPopUpInfo(frame, StringResource.getString("TASK_RUNNING"));
			return;
		}
		if (!isAnimeHome) {
			SPopup.ShowPopUpInfo(frame, StringResource.getString("ANIME_HOME_PANEL"));
			return;
		}
		isActionChanged = true;
		running = true;
		panel_4 = (JPanel) loadingscreen();
		if (isanimepanel) {
			isanimepanel = false;
		}
		changeMainPanel(panel_4);
		Thread t = new Thread() {
			public void run() {
				searcher.setText("");
				jsp.setEnabled(false);
				searchmode = false;
				AnimeList.removeAll(AnimeList);

				createanimelist(fetch.gethomeurrl(an_con.source_url[an_con.source], an_con.source));
				
				changeMainPanel(jsptable(AnimeList));
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						running = false;
						isloadingpanel = false;
						isActionChanged = false;
						Image_Update();
					}
				});
			}
		};
		t.start();
	}
	
	private void browse_action(){
		if (isloadingpanel) {
			SPopup.ShowPopUpInfo(frame, StringResource.getString("TASK_RUNNING"));
			return;
		}
		if(isOffAnimePanel){
			getOffAnime();
		}else{
			getOnlineAnime();
		}
	}

	public void Image_Update() {
		Thread t = new Thread() {
			public void run() {
				AnimeList = fetch.getimguparlst();
				SwingUtilities.invokeLater(new Runnable() {// do swing work on
					public void run() {
						running = false;
						isloadingpanel = false;
						model.fireTableDataChanged();
						setstatus_loggger("");
						Utils.l("MainWindow_image_update", "Closing.....", false);
					}
				});
			}
		};
		t.start();
	}

	public void setlbldwn(int epnum) {
		lbldwn.setText("Down..(+ep-" + epnum + ")");
	}

	public void setlbldwn(String epnum) {
		lbldwn.setText(epnum);
	}

	public void createanimelist(String url) {
		setstatus_loggger("Fetching data...");
		Document doc = parsehtml(url);
		AnimeList = fetch.getdata(doc, an_con.source, this);
		if (AnimeList == null) {
			AnimeList = new ArrayList<Anime>();
		}
	}

	public Document parsehtml(String url) {
		Utils.l("MainWindow_Docparser", url, true);
		Document doc = null;
		Connection.Response response = null;
		int responseCode = 0;
		try {
			if (searchmode && (an_con.source == 1 || an_con.source == 9)) {
				response = Jsoup.connect(fetch.getsrchbox("", an_con.source)).data("anime", serachtxt)
						.method(Connection.Method.POST).userAgent(StaticResource.USER_AGENT).execute();
			}else if(false){
				response = Jsoup.connect(fetch.getsrchbox("", 9)).data("id", serachtxt.replaceAll(" ", "+"))
						.method(Connection.Method.POST).userAgent(StaticResource.USER_AGENT).execute();
			}else {
				response = Jsoup.connect(url).ignoreContentType(true).userAgent(StaticResource.USER_AGENT).execute();
			}
			responseCode = response.statusCode();
			if (responseCode == 200) {
				doc = response.parse();
			} else {
				setstatus_loggger(ERROR_MSG);
				SAlertDialog.showAlertInfo("Error", "Error! Invalid response code: " + responseCode, frame);
				doc = null;
			}
		} catch (org.jsoup.HttpStatusException e) {
			if (this.isloadingpanel) {
				running = false;
				cpane.remove(panel_4);
				Utils.l("MainWindow_Docparser", "Removed Loading screen..", true);
			}
			int st = e.getStatusCode();
			if (st == 404) {
				ERROR_MSG = "HTTP ERROR " + e.getStatusCode() + "<br>Anime not found.";
			} else {
				ERROR_MSG = "HTTP ERROR " + e.getStatusCode() + "<br>Try different Anime.";
			}
			setstatus_loggger(ERROR_MSG);
			SAlertDialog.showAlertInfo("Error", ERROR_MSG, frame);
			doc = null;
		} catch (java.net.SocketTimeoutException ex) {
			if (this.isloadingpanel) {
				running = false;
				cpane.remove(panel_4);
				Utils.l("MainWindow_Docparser", "Removed Loading screen..", true);
			}
			ERROR_MSG = "Sever Timeout. Please try again.";
			setstatus_loggger(ERROR_MSG);
			SAlertDialog.showAlertInfo("Error", ERROR_MSG, frame);

			doc = null;
		} catch (Exception e) {
			if (this.isloadingpanel) {
				running = false;
				cpane.remove(panel_4);
				Utils.l("MainWindow_Docparser", "Removed Loading screen..", true);
			}

			setstatus_loggger("Some unexpected error occured.");
			ERROR_MSG = "Some unexpected error occured.<br>Try checking your internet connection.";
			SAlertDialog.showAlertInfo("Error", ERROR_MSG, frame);
			doc = null;
		}
		setstatus_loggger("");
		return doc;
	}

	public BufferedImage getimage(String url) {
		try {
			imgurl = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) imgurl.openConnection();
			connection.addRequestProperty("User-Agent", "Mozilla/4.76");
			connection.connect();
			InputStream in = connection.getInputStream();
			img = ImageIO.read(in);

		} catch (IOException e) {
			try {
				img = ImageIO.read(new File("Resources/Icons/noimage.png"));
			} catch (IOException e1) {
				Utils.e(TAG,"Error While Loading Image.",true);
			}
		}
		return img;
	}

	public void threadcreater() {
		loadicon = new JLabel("");
		loadiconth = new Thread() {
			public void run() {
				while (running) {
					for (int i = 0; i <= 30; i++) {
						loadicon.setIcon(StaticResource
								.getIcon(StringResource.getColorString("ECLIPSE") + "/frame-" + i + ".png"));
						try {
							Thread.sleep(11);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}

		};
	}
	
	private void getScheduleAnime(final String day){
		isScheduleLoading = true;
		isSchChanged = true;
		Thread t = new Thread() {
			public void run() {
				scheduleAnimeList.removeAll(scheduleAnimeList);
				Anime anime = new Anime("Featching Anime data....", "", "", StaticResource.getIcon(StringResource.getColorString("LOADING_IMG")), "");
				scheduleAnimeList.add(anime);
				scheduleAnimeList.add(anime);
				scheduleAnimeList.add(anime);
				scheduleModel.fireTableDataChanged();
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				fetch.getSeheduledata(day, scheduleAnimeList, frame);
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						scheduleModel.fireTableDataChanged();
						isScheduleLoading=false;
						Image_Update_Sch();
					}
				});
			}
		};
		t.start();
	}
	
	public void Image_Update_Sch() {
		Thread t = new Thread() {
			public void run() {
				scheduleAnimeList = fetch.getSceduleImg(scheduleAnimeList);
				SwingUtilities.invokeLater(new Runnable() {// do swing work on
					public void run() {
						scheduleModel.fireTableDataChanged();
						Utils.l("MainWindow_Image_Update_Sch", "Closing.....", false);
					}
				});
			}
		};
		t.start();
	}
	
	Component getSchdulePanel(){
		if(mainSchdule==null){
			mainSchdule = new JPanel(new BorderLayout());
			mainSchdule.setBackground(StringResource.getColor("PANEL_BG_COLOR"));
			JPanel dayContainerPanel = new JPanel(new GridLayout(1,7));
			dayContainerPanel.setBackground(StringResource.getColor("PANEL_BG_COLOR"));
			//Create Day buttons
			lblweek = new JLabel[7];
			for(int i = 0;i<lblweek.length;i++){
				final int j = i;
				lblweek[i] = new JLabel(StaticResource.weekday[i],SwingConstants.CENTER);
				lblweek[i].setName(StaticResource.weekday[i]);
				lblweek[i].setForeground(StringResource.getColor("TEXT_COLOR"));
				lblweek[i].setFont(StaticResource.materialFontMidL);
				lblweek[i].setBorder(new EmptyBorder(8, 0, 8, 0));
				lblweek[i].addMouseListener(new MouseAdapter() {
					public void mouseReleased(MouseEvent e) {
						MainWindow.this.weekDayActionPerformed(new ActionEvent(MainWindow.this.lblweek[j], 0, ""));
					}
				});
				dayContainerPanel.add(lblweek[i]);
			}
			Calendar  cal = Calendar .getInstance();
			int day = cal.get(Calendar.DAY_OF_WEEK)-1;
			Utils.l(TAG,"Day: " + day,true);
			
			weekSelected(lblweek[day]);
			curDay = lblweek[day].getName();
			mainSchdule.add(dayContainerPanel, "North");
			changeSchedulePanel(scheduleTable(scheduleAnimeList));
			getScheduleAnime(lblweek[day].getName());
		}
		
		return mainSchdule;
	}
	
	Component scheduleTable(List<Anime> list){
		scheduleModel = new MainTableModel(list);
		this.schduleTable = new JTable(scheduleModel);
		this.schduleTable.setTableHeader((JTableHeader) null);
		this.schduleTable.setDefaultRenderer(Anime.class, new SimpleHeaderRenderer());
		this.schduleTable.setRowHeight(200);
		this.schduleTable.setShowGrid(false);
		this.schduleTable.setFillsViewportHeight(true);
		this.schduleTable.setBorder(new EmptyBorder(0, 0, 0, 0));
		this.schduleTable.setDragEnabled(false);
		this.schduleTable.setBackground(StringResource.getColor("PANEL_BG_COLOR"));
		
		jspSchedule= new SScrollPane(this.schduleTable);
		jspSchedule.setOpaque(false);
		jspSchedule.setBorder(new EmptyBorder(0, 0, 0, 0));
		jspSchedule.setSize(new Dimension(600, 700));
		return jspSchedule;
	}
	
	Component getOffAnimePanel(){
		if(offlineAnimePanel==null){
			offlineAnimePanel = new JPanel(new BorderLayout());
			offlineAnimePanel.setBackground(StringResource.getColor("PANEL_BG_COLOR"));
			offlineAnimePanel.add(offlineAnimeList(offAnimeList),"Center");
		}
		getOfflineAnimes();
		return offlineAnimePanel;
	}
	
	private void getOfflineAnimes(){
		Thread t = new Thread() {
			public void run() {
				offAnimeList.removeAll(offAnimeList);
				for(int i = 0; i<offAnimelocList.size();i++){
					String loc = offAnimelocList.get(i);
					AnimeConf animeConf = new AnimeConf(loc);
					if(animeConf.isConfigExist()){
						try {
							animeConf.loadJSON(null);
							JSONObject obj = animeConf.getAnimeObj();
							String name = obj.getString("name");
							String epnum = obj.getString("epnum");
							String details = obj.getString("details");
							String url = obj.getString("url");
							ImageIcon icon = StaticResource.getAnimeIcon(obj.getString("icon"));
							Anime anime = new Anime(name, details, epnum, icon, url);
							offAnimeList.add(anime);
						} catch (JSONException e) {
							e.printStackTrace();
						}
						
					}
				}
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						offAnimeModel.fireTableDataChanged();
					
					}
				});
			}
		};
		t.start();
	}
	
	Component offlineAnimeList(List<Anime> list){
		offAnimeModel = new MainTableModel(list);
		offAnimeTable = new JTable(offAnimeModel);
		this.offAnimeTable.setTableHeader((JTableHeader) null);
		this.offAnimeTable.setDefaultRenderer(Anime.class, new SimpleHeaderRenderer());
		this.offAnimeTable.setRowHeight(200);
		this.offAnimeTable.setShowGrid(false);
		this.offAnimeTable.setFillsViewportHeight(true);
		this.offAnimeTable.setBorder(new EmptyBorder(0, 0, 0, 0));
		this.offAnimeTable.setDragEnabled(false);
		this.offAnimeTable.setBackground(StringResource.getColor("PANEL_BG_COLOR"));
		this.offAnimeTable.addMouseListener(new MouseAdapter() {
		    public void mousePressed(MouseEvent mouseEvent) {
		        JTable table =(JTable) mouseEvent.getSource();
		        if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
		        	browse_action();
		        }
		    }
		});
		offAnimeJsp = new SScrollPane(this.offAnimeTable);
		offAnimeJsp.setOpaque(false);
		offAnimeJsp.setBorder(new EmptyBorder(0, 0, 0, 0));
		offAnimeJsp.setSize(new Dimension(650, 800));
		return offAnimeJsp;
	}
	
	private void createOffLineAnime(String url, String folderLoc, String name){
		Utils.l(TAG+"_createOffLineAnime","Local Folder name: " + folderLoc,true);
		folderLoc = folderLoc.substring(0,folderLoc.length()-3);
		String epName = name.substring(0,name.lastIndexOf("."));
		if(!offAnimelocList.contains(folderLoc)){
			offAnimelocList.add(folderLoc);
			Utils.l(TAG+"_createOffLineAnime", "Successfully added: " + offAnimelocList.get(0),true);
		}
		File animeEp = new File(folderLoc+StaticResource.EP_LOC,name);
		try{
			Anime anime = AnimeList.get(sected);
			AnimeConf animeConf = new AnimeConf(folderLoc);
			animeConf.loadJSON(anime);
			animeConf.addEP(epName, animeEp.getAbsolutePath());
			animeConf.saveAndClose();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	Component jsptable(List<Anime> list) {
		model = new MainTableModel(list);
		this.table = new JTable(model);
		this.table.setTableHeader((JTableHeader) null);
		this.table.setDefaultRenderer(Anime.class, new SimpleHeaderRenderer());
		this.table.setRowHeight(200);
		this.table.setShowGrid(false);
		this.table.setFillsViewportHeight(true);
		this.table.setBorder(new EmptyBorder(0, 0, 0, 0));
		this.table.setDragEnabled(false);
		this.table.setBackground(StringResource.getColor("PANEL_BG_COLOR"));
		this.table.addMouseListener(new MouseAdapter() {
		    public void mousePressed(MouseEvent mouseEvent) {
		        JTable table =(JTable) mouseEvent.getSource();
		        if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
		        	browse_action();
		        }
		    }
		});
		jsp = new SScrollPane(this.table);
		jsp.setOpaque(false);
		jsp.setBorder(new EmptyBorder(0, 0, 0, 0));
		jsp.setSize(new Dimension(650, 800));
		return jsp;
	}

	// Dwonload Tabel Creation
	Component jsptabledwn(DwnTableModel mdwnTableModel) {
		dwntabel = new JTable(mdwnTableModel);
		dwntabel.setTableHeader((JTableHeader) null);
		dwntabel.setDefaultRenderer(DownloadListItem.class, new ItemRenderer());
		dwntabel.setRowHeight(70);
		dwntabel.setShowGrid(false);
		dwntabel.setFillsViewportHeight(true);
		dwntabel.setBorder(new EmptyBorder(0, 0, 0, 0));
		dwntabel.setDragEnabled(true);
		dwntabel.setBackground(StringResource.getColor("PANEL_BG_COLOR"));
		dwntabel.setTransferHandler(new FileTransferHandler(list, this));
		jsptable = new SScrollPane(this.dwntabel);
		jsptable.setBorder(new EmptyBorder(0, 0, 0, 0));
		jsptable.setOpaque(false);
		jsptable.setSize(new Dimension(650, 800));
		dwntabel.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent arg0) {

			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_DELETE) {
					Utils.l(TAG, "Delete Pressed", true);
					removeDownloads();
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {

			}

		});
		return jsptable;
	}

	Component configpanel() {
		// init
		Color textcolor = StringResource.getColor("TEXT_COLOR");
		Color elementBG = StringResource.getColor("SEARCH_BG");
		Color bg = StringResource.getColor("PANEL_BG_COLOR");
		Color sel = StringResource.getColor("SELECTION");
		Color pre = StringResource.getColor("PRESSED");
		Border border = BorderFactory.createLineBorder(StringResource.getColor("BORDER"), 1);
		boolean old_theme = an_con.isDarkTheme;
		configpanel = new JPanel();
		configpanel.setLayout(null);
		configpanel.setBackground(bg);
		JLabel midSprater = new JLabel("");
		midSprater.setBackground(new Color(222, 222, 222));
		midSprater.setBounds(420, 11, 1, 498);
		midSprater.setOpaque(true);
		midSprater.setBackground(elementBG);
		midSprater.setMaximumSize(new Dimension(1, 10));
		midSprater.setPreferredSize(new Dimension(1, 10));
		configpanel.add(midSprater);

		JLabel dwnloc = new JLabel("Download Location:");
		dwnloc.setFont(StaticResource.materialFontSmallL);
		dwnloc.setForeground(textcolor);
		dwnloc.setBounds(48, 50, 136, 23);
		configpanel.add(dwnloc);

		dwntxt = new JTextField();
		dwntxt.setText(an_con.destdir);
		dwntxt.setForeground(textcolor);
		dwntxt.setBackground(elementBG);
		dwntxt.setFont(StaticResource.materialFontSmallL);
		dwntxt.setBounds(167, 51, 166, 24);
		configpanel.add(dwntxt);
		dwntxt.setColumns(10);
		dwntxt.setEditable(false);
		dwntxt.setOpaque(true);
		dwntxt.setBorder(null);

		SButton dwnbwr = new SButton();
		dwnbwr.setBounds(333, 51, 30, 24);
		dwnbwr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				JFileChooser jfc = new JFileChooser();
				jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				if (jfc.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
					dwntxt.setText(jfc.getSelectedFile().getAbsolutePath());
				}

			}
		});
		dwnbwr.setPreferredSize(new Dimension(30, 24));
		dwnbwr.setIcon(StaticResource.getIcon(StringResource.getColorString("BWR_DIR")));
		dwnbwr.setBorderPainted(false);
		dwnbwr.setBackGround(elementBG);
		dwnbwr.setHover(sel);
		dwnbwr.setPressedColor(pre);
		configpanel.add(dwnbwr);

		JLabel dwnloc1 = new JLabel("Default Downloader:");
		dwnloc1.setBounds(48, 96, 110, 14);
		dwnloc1.setFont(StaticResource.materialFontSmallL);
		dwnloc1.setForeground(textcolor);
		configpanel.add(dwnloc1);

		JLabel lblSetting = new JLabel("Setting");
		lblSetting.setBounds(21, 11, 381, 16);
		lblSetting.setFont(StaticResource.materialFontMidR);
		lblSetting.setForeground(textcolor);
		configpanel.add(lblSetting);

		option1 = new SRadio("Default");
		option1.setBounds(178, 93, 70, 23);
		option1.setFont(StaticResource.materialFontSmallL);
		option1.setBackground(bg);
		option1.setHover(sel);
		option1.setPressed(pre);
		option1.setForeground(textcolor);
		configpanel.add(option1);

		option2 = new SRadio("IDMan");
		option2.setBounds(250, 93, 70, 23);
		option2.setFont(StaticResource.materialFontSmallL);
		option2.setBackground(bg);
		option2.setHover(sel);
		option2.setPressed(pre);
		option2.setForeground(textcolor);
		configpanel.add(option2);

		if (an_con.Default_downloader) {
			option1.setSelected(true);
		} else {
			option2.setSelected(true);
		}

		ButtonGroup group = new ButtonGroup();
		group.add(option1);
		group.add(option2);

		JLabel Setting_Sep = new JLabel("");
		Setting_Sep.setBackground(elementBG);
		Setting_Sep.setBounds(21, 29, 381, 1);
		Setting_Sep.setOpaque(true);
		Setting_Sep.setMinimumSize(new Dimension(1, 10));
		Setting_Sep.setMaximumSize(new Dimension(1, 10));
		Setting_Sep.setPreferredSize(new Dimension(1, 10));
		configpanel.add(Setting_Sep);
		//
		JLabel dtlSource = new JLabel();

		//
		JLabel srcloc = new JLabel("Default Source:");
		srcloc.setBounds(48, 134, 106, 14);
		srcloc.setFont(StaticResource.materialFontSmallL);
		srcloc.setForeground(textcolor);
		src = new SComboBox();
		for (i = 0; i < an_con.source_url.length; i++) {
			src.addItem("Source " + i + " (" + an_con.source_names[i] + ")");
		}
		src.setSelectedIndex(an_con.source);
		src.setFont(StaticResource.materialFontSmallL);
		src.setBackGround(elementBG);
		src.setForeground(textcolor);

		src.setBorder(new EmptyBorder(0, 0, 0, 0));
		src.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				dtlSource.setText(StaticResource.dtlsource[src.getSelectedIndex()]);
			}

		});
		configpanel.add(srcloc);

		src.setBounds(169, 131, 166, 20);
		configpanel.add(src);

		JLabel maxdownloads = new JLabel("Max. Simn. Downloads:");
		maxdownloads.setBounds(48, 169, 118, 14);
		maxdownloads.setFont(StaticResource.materialFontSmallL);
		maxdownloads.setForeground(textcolor);
		configpanel.add(maxdownloads);

		maxdwnlod = new SComboBox();
		maxdwnlod.setBounds(169, 166, 166, 20);
		maxdwnlod.addItem(1);
		maxdwnlod.addItem(2);
		maxdwnlod.addItem(5);
		maxdwnlod.addItem(8);
		maxdwnlod.addItem(10);
		maxdwnlod.addItem(15);
		maxdwnlod.addItem(30);
		maxdwnlod.setFont(StaticResource.materialFontSmallL);
		maxdwnlod.setBackGround(elementBG);
		maxdwnlod.setForeground(textcolor);
		maxdwnlod.setBorder(new EmptyBorder(0, 0, 0, 0));
		configpanel.add(maxdwnlod);

		JLabel VLClibs = new JLabel("VLCLibs Location:");
		VLClibs.setBounds(48, 204, 118, 14);// 51, 242, 118, 14
		VLClibs.setFont(StaticResource.materialFontSmallL);
		VLClibs.setForeground(textcolor);
		configpanel.add(VLClibs);

		vlclibtxt = new JTextField();
		vlclibtxt.setText(an_con.VLC_LIBS_PATH);
		vlclibtxt.setBounds(167, 201, 168, 24);
		configpanel.add(vlclibtxt);
		vlclibtxt.setColumns(10);
		vlclibtxt.setEditable(false);
		vlclibtxt.setOpaque(true);
		vlclibtxt.setForeground(textcolor);
		vlclibtxt.setBackground(elementBG);
		vlclibtxt.setFont(StaticResource.materialFontSmallL);
		vlclibtxt.setBorder(null);

		SButton libbwr = new SButton();
		libbwr.setBounds(335, 201, 30, 24);
		libbwr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int os = Utils.detectOS();
				if(os==StaticResource.LINUX || os==StaticResource.MAC){
					if(StaticResource.chkVLCLibs("")){
						vlclibtxt.setText("Detected.");
						animeconfig.isVLCInstalled = true;
					}else {
						SAlertDialog.showAlertInfo("Alert", "Libs not found. Please check the directry and try again.", frame);
					}
				}else{
					JFileChooser jfc = new JFileChooser();
					jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					if (jfc.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
						if (StaticResource.chkVLCLibs(jfc.getSelectedFile().getAbsolutePath())) {
							vlclibtxt.setText(jfc.getSelectedFile().getAbsolutePath());
							Utils.l(TAG + "_ConfigPanel", "VLC Libs Path: " + jfc.getSelectedFile().getAbsolutePath(), true);
							animeconfig.isVLCInstalled = true;
						} else {
							SAlertDialog.showAlertInfo("Alert", "Libs not found. Please check the directry and try again.", frame);
						}
					}
				}
				
			}
		});
		libbwr.setPreferredSize(new Dimension(30, 24));
		libbwr.setIcon(StaticResource.getIcon(StringResource.getColorString("BWR_DIR")));
		libbwr.setBorderPainted(false);
		libbwr.setBackGround(elementBG);
		libbwr.setHover(sel);
		libbwr.setPressedColor(pre);
		configpanel.add(libbwr);
		
		SCheckBox cbExternalPlayer = new SCheckBox("Use External VLC Player");
		cbExternalPlayer.setFont(StaticResource.materialFontSmallL);
		cbExternalPlayer.setBounds(165, 222, 150, 24);
		if(an_con.isExternalPlayer){
			cbExternalPlayer.setSelected(true);
		}
		int OS = Utils.detectOS();
		if(OS==StaticResource.LINUX || OS==StaticResource.MAC){
			cbExternalPlayer.setSelected(true);
			cbExternalPlayer.setEnabled(false);
			an_con.isExternalPlayer = true;
		}
		cbExternalPlayer.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent event) {
		        SCheckBox cb = (SCheckBox) event.getSource();
		        if (cb.isSelected()) {
		        		an_con.isExternalPlayer = true;
		        		Utils.l(TAG, "CB action: " + an_con.isExternalPlayer, true);
		        } else {
		        	an_con.isExternalPlayer = false;
		        	Utils.l(TAG, "CB action: " + an_con.isExternalPlayer, true);
		        }
		    }
		});
		configpanel.add(cbExternalPlayer);
		//End Player Options
		//FFMPEG part
		JLabel lbl_ffmpeg_loc = new JLabel("FFMPEGLibs Loc:");
		lbl_ffmpeg_loc.setFont(StaticResource.materialFontSmallL);
		lbl_ffmpeg_loc.setForeground(textcolor);
		lbl_ffmpeg_loc.setBounds(48, 247, 118, 14);
		configpanel.add(lbl_ffmpeg_loc);

		ffmpeglibtxt = new JTextField();
		ffmpeglibtxt.setText(an_con.FFMPEG_LIBS_PATH);
		ffmpeglibtxt.setBounds(167, 245, 168, 24);
		configpanel.add(ffmpeglibtxt);
		ffmpeglibtxt.setColumns(10);
		ffmpeglibtxt.setEditable(false);
		ffmpeglibtxt.setOpaque(true);
		ffmpeglibtxt.setForeground(textcolor);
		ffmpeglibtxt.setBackground(elementBG);
		ffmpeglibtxt.setFont(StaticResource.materialFontSmallL);
		ffmpeglibtxt.setBorder(null);

		SButton libffbwr = new SButton();
		libffbwr.setBounds(335, 247, 30, 24);
		libffbwr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(Utils.detectOS()==StaticResource.LINUX){
					if(StaticResource.chkFFMPEGLibs("")){
						ffmpeglibtxt.setText("Detected.");
						animeconfig.isFFMPEGexist = true;
					}else {
						SAlertDialog.showAlertInfo("Alert", "Libs not found. Please check the directry and try again.", frame);
						animeconfig.isFFMPEGexist = false;
					}
				}else{
					JFileChooser jfc = new JFileChooser();
					jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					if (jfc.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
						if (StaticResource.chkFFMPEGLibs(jfc.getSelectedFile().getAbsolutePath())) {
							ffmpeglibtxt.setText(jfc.getSelectedFile().getAbsolutePath());
							an_con.isFFMPEGexist = true;
						} else {
							SAlertDialog.showAlertInfo("Alert", "Libs not found. Please check the directry and try again.", frame);
							animeconfig.isFFMPEGexist = false;
						}
					}
				}
			}
		});
		libffbwr.setPreferredSize(new Dimension(30, 24));
		libffbwr.setIcon(StaticResource.getIcon(StringResource.getColorString("BWR_DIR")));
		libffbwr.setBorderPainted(false);
		libffbwr.setBackGround(elementBG);
		libffbwr.setHover(sel);
		libffbwr.setPressedColor(pre);
		configpanel.add(libffbwr);

		// Dark Theme
		JLabel lbl_theme = new JLabel("App Theme:");
		lbl_theme.setFont(StaticResource.materialFontSmallL);
		lbl_theme.setForeground(textcolor);
		lbl_theme.setBounds(48, 280, 118, 14);
		configpanel.add(lbl_theme);

		rbLight = new SRadio("Light");
		rbLight.setBounds(178, 278, 70, 23);
		rbLight.setFont(StaticResource.materialFontSmallL);
		rbLight.setBackground(bg);
		rbLight.setHover(sel);
		rbLight.setPressed(pre);
		rbLight.setForeground(textcolor);
		configpanel.add(rbLight);

		rbDark = new SRadio("Dark");
		rbDark.setBounds(250, 278, 70, 23);
		rbDark.setFont(StaticResource.materialFontSmallL);
		rbDark.setBackground(bg);
		rbDark.setHover(sel);
		rbDark.setPressed(pre);
		rbDark.setForeground(textcolor);
		configpanel.add(rbDark);

		if (an_con.isDarkTheme) {
			rbDark.setSelected(true);
		} else {
			rbLight.setSelected(true);
		}

		ButtonGroup groupTheme = new ButtonGroup();
		groupTheme.add(rbLight);
		groupTheme.add(rbDark);

		// Hint Show
		JLabel lbl_Hint = new JLabel("Show Hints:");
		lbl_Hint.setFont(StaticResource.materialFontSmallL);
		lbl_Hint.setForeground(textcolor);
		lbl_Hint.setBounds(48, 316, 118, 14);
		configpanel.add(lbl_Hint);
		rbHintYes = new SRadio("Yes");
		rbHintYes.setBounds(178, 316, 70, 23);
		rbHintYes.setFont(StaticResource.materialFontSmallL);
		rbHintYes.setBackground(bg);
		rbHintYes.setForeground(textcolor);
		rbHintYes.setHover(sel);
		rbHintYes.setPressed(pre);
		configpanel.add(rbHintYes);

		rbHintNo = new SRadio("No");
		rbHintNo.setBounds(250, 318, 70, 23);
		rbHintNo.setFont(StaticResource.materialFontSmallL);
		rbHintNo.setBackground(bg);
		rbHintNo.setHover(sel);
		rbHintNo.setForeground(textcolor);
		rbHintNo.setPressed(pre);
		configpanel.add(rbHintNo);
		ButtonGroup groupHint = new ButtonGroup();
		groupHint.add(rbHintYes);
		groupHint.add(rbHintNo);

		if (an_con.isShowHintEnabled) {
			rbHintYes.setSelected(true);
		} else {
			rbHintNo.setSelected(true);
		}

		JLabel lblPleasePress = new JLabel("Please press");
		lblPleasePress.setFont(StaticResource.materialFontSmallL);
		lblPleasePress.setForeground(textcolor);
		lblPleasePress.setBounds(89, 360, 71, 14);
		configpanel.add(lblPleasePress);

		JLabel imgview = new JLabel();
		imgview.setIcon(StaticResource.getIcon(StringResource.getColorString("SAVE")));
		imgview.setBounds(158, 345, 50, 50);
		configpanel.add(imgview);

		JLabel lblNewLabel_3 = new JLabel("in bottom to save the changes.");
		lblNewLabel_3.setFont(StaticResource.materialFontSmallL);
		lblNewLabel_3.setForeground(textcolor);
		lblNewLabel_3.setBounds(190, 360, 150, 14);
		configpanel.add(lblNewLabel_3);

		JLabel lblQueueSetting = new JLabel("Queue Setting");
		lblQueueSetting.setBounds(440, 11, 100, 16);
		lblQueueSetting.setFont(StaticResource.materialFontMidR);
		lblQueueSetting.setForeground(textcolor);
		configpanel.add(lblQueueSetting);

		JLabel Queue_Sep = new JLabel("");
		Queue_Sep.setPreferredSize(new Dimension(1, 10));
		Queue_Sep.setOpaque(true);
		Queue_Sep.setMinimumSize(new Dimension(1, 10));
		Queue_Sep.setMaximumSize(new Dimension(1, 10));
		Queue_Sep.setBackground(elementBG);
		Queue_Sep.setBounds(441, 29, 381, 1);
		configpanel.add(Queue_Sep);

		qlistdata = new DefaultListModel();
		createqlist();

		queuepane.setBounds(445, 63, 373, 160);
		queuepane.setBorder(border);
		configpanel.add(queuepane);

		JLabel lblItemsInQueue = new JLabel("Items in Queue:");
		lblItemsInQueue.setBounds(445, 44, 100, 14);
		lblItemsInQueue.setFont(StaticResource.materialFontSmallL);
		lblItemsInQueue.setForeground(textcolor);
		configpanel.add(lblItemsInQueue);

		qsize = new JLabel("0");
		qsize.setFont(StaticResource.materialFontSmallL);
		qsize.setForeground(textcolor);
		qsize.setBounds(535, 44, 31, 14);
		configpanel.add(qsize);

		SButton btnMoveUp = new SButton();

		btnMoveUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int i = qlist.getSelectedIndex();
				if (i == -1) {
					Utils.l("MainWindow", "Nothing selected in queue", true);
					return;
				}
				if (i == 0) {
					Utils.l("MainWindow", "Nothing selected in queue", true);
					return;
				}
				qarraymove(i, i - 1);
				updatelist();
				qlist.setSelectedIndex(i - 1);

			}
		});
		btnMoveUp.setBounds(445, 229, 24, 24);
		btnMoveUp.setPreferredSize(new Dimension(24, 24));
		btnMoveUp.setBackGround(bg);
		btnMoveUp.setHover(sel);
		btnMoveUp.setPressedColor(pre);
		btnMoveUp.setIcon(StaticResource.getIcon(StringResource.getColorString("QUEUE_UP")));
		btnMoveUp.setBorderPainted(false);
		configpanel.add(btnMoveUp);

		SButton btnMoveDown = new SButton();
		btnMoveDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int i = qlist.getSelectedIndex();
				if (i == -1) {
					Utils.l("MainWindow", "Nothing selected in queue", true);
					return;
				}
				if (i == queueitems.size() - 1) {
					Utils.l("MainWindow", "Nothing selected in queue", true);
					return;
				}
				qarraymove(i, i + 1);
				updatelist();
				qlist.setSelectedIndex(i + 1);

			}
		});
		btnMoveDown.setBounds(475, 229, 24, 24);
		btnMoveDown.setBackGround(bg);
		btnMoveDown.setHover(sel);
		btnMoveDown.setPressedColor(pre);
		btnMoveDown.setIcon(StaticResource.getIcon(StringResource.getColorString("QUEUE_DOWN")));
		btnMoveDown.setBorderPainted(false);
		configpanel.add(btnMoveDown);

		SButton btnRemove = new SButton("");
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int i = qlist.getSelectedIndex();
				if (i == -1) {
					Utils.l("MainWindow", "Removing item..Nothing selected in queue", true);
					return;
				}
				if (queueitems.size() == -1 && queueitems == null) {
					return;
				}
				for (int j = 0; j < list.list.size(); ++j) {
					DownloadListItem item = (DownloadListItem) list.list.get(j);
					if (queueitems.get(i).toString().equals(item.filename)) {

						item.q = false;
						break;
					}
				}

				mdwntablemodel.fireTableDataChanged();
				list.downloadStateChanged();
				queueitems.remove(i);
				updatelist();
			}
		});
		btnRemove.setBounds(505, 229, 24, 24);
		btnRemove.setBackGround(bg);
		btnRemove.setHover(sel);
		btnRemove.setPressedColor(pre);
		btnRemove.setIcon(StaticResource.getIcon(StringResource.getColorString("QUEUE_REMOVE")));
		btnRemove.setBorderPainted(false);
		configpanel.add(btnRemove);

		JLabel dtlsourcetitle = new JLabel("Source Info:");
		dtlsourcetitle.setBounds(51, 350, 350, 100);
		dtlsourcetitle.setFont(StaticResource.materialFontSmallR);
		dtlsourcetitle.setForeground(textcolor);
		configpanel.add(dtlsourcetitle);

		dtlSource.setBounds(51, 410, 350, 100);
		dtlSource.setText(StaticResource.dtlsource[an_con.source]);
		dtlSource.setBorder(null);
		dtlSource.setBackground(elementBG);
		dtlSource.setFont(StaticResource.materialFontSmallL);
		dtlSource.setForeground(textcolor);
		dtlSource.setVerticalAlignment(JLabel.TOP);
		dtlSource.setOpaque(true);
		dtlSource.setBorder(border);
		configpanel.add(dtlSource);

		JLabel anoucementtitle = new JLabel("Announcements:");
		anoucementtitle.setBounds(445, 350, 350, 100);
		anoucementtitle.setFont(StaticResource.materialFontSmallR);
		anoucementtitle.setForeground(textcolor);
		configpanel.add(anoucementtitle);

		dtlanoucement = new JLabel("No Info..");
		dtlanoucement.setBounds(445, 410, 350, 100);
		dtlanoucement.setVerticalAlignment(JLabel.TOP);
		dtlanoucement.setBackground(elementBG);
		dtlanoucement.setFont(StaticResource.materialFontSmallL);
		dtlanoucement.setForeground(textcolor);
		dtlanoucement.setOpaque(true);
		dtlanoucement.setBorder(border);
		configpanel.add(dtlanoucement);

		Box boxDonate = Box.createHorizontalBox();
		boxDonate.setBounds(51, 500, 800, 100);
		JLabel lDonate_text = new JLabel(StringResource.getString("DONATE_TEXT") + " ");
		lDonate_text.setFont(StaticResource.materialFontSmallR);
		lDonate_text.setForeground(textcolor);

		JLabel lDonate = new JLabel(StringResource.getString("DONATE_STATUS"));
		lDonate.setFont(StaticResource.materialFontSmallR);
		lDonate.setForeground(textcolor);

		lDonate_amount = new JLabel("0");
		lDonate_amount.setFont(StaticResource.materialFontSmallR);
		lDonate_amount.setForeground(textcolor);

		fetch.getDonationAmount();
		SButton btnDonate = new SButton(StringResource.getString("DONATE_BTN_TEXT"));
		btnDonate.setBackGround(StringResource.getColor("SEARCH_BG"));
		btnDonate.setBorderPainted(false);
		btnDonate.setHover(StringResource.getColor("SELECTION"));
		btnDonate.setFont(StaticResource.materialFontSmallL);
		btnDonate.setPressedColor(StringResource.getColor("PRESSED"));
		btnDonate.setForeground(textcolor);

		btnDonate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				fetch.donate_clicked();
			}

		});
		boxDonate.add(btnDonate);
		boxDonate.add(lDonate_text);
		boxDonate.add(lDonate);
		boxDonate.add(lDonate_amount);
		configpanel.add(boxDonate);

		return configpanel;
	}

	private void createqlist() {

		qlist = new SList<String>(this.queueitems.toArray());
		qlist.setFont(StaticResource.materialFontSmallL);

		qlist.setModel(this.qlistdata);
		qlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		Utils.l("MainWindow", "List updated", true);

		queuepane = new SScrollPane(qlist);

	}

	private void updatelist() {
		qlistdata = (DefaultListModel) qlist.getModel();
		qlistdata.clear();
		qsize.setText(Integer.toString(this.queueitems.size()));
		for (int i = 0; i < this.queueitems.size(); i++) {
			qlistdata.addElement((String) this.queueitems.get(i));
		}

	}

	// move array
	private void qarraymove(int firstposition, int secondposition) {
		String temp1, temp2;
		temp1 = (String) this.queueitems.get(firstposition);
		temp2 = (String) this.queueitems.get(secondposition);
		this.queueitems.set(firstposition, temp2);
		this.queueitems.set(secondposition, temp1);

	}
	
	public void createschedule(){
		scheduleBottmBox = Box.createHorizontalBox();
		scheduleBottmBox.add(Box.createHorizontalStrut(10));
		SButton searchBTN = new SButton();
		searchBTN.setPreferredSize(new Dimension(50, 70));
		searchBTN.setIcon(StaticResource.getIcon(StringResource.getColorString("SEARCH_ICON")));
		searchBTN.setBorderPainted(false);
		searchBTN.setBackGround(StringResource.getColor("TITLE_BG_COLOR"));
		searchBTN.setHover(StringResource.getColor("SELECTION"));
		searchBTN.showHint("Search Anime");
		searchBTN.setPressedColor(StringResource.getColor("PRESSED"));
		searchBTN.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (isScheduleLoading) {
					SPopup.ShowPopUpInfo(frame, StringResource.getString("TASK_RUNNING"));
					return;
				}
				sected = schduleTable.getSelectedRow();
				Utils.l("MainWindow", "Selected Row:" + sected, true);
				if (sected != -1) {
					searcher.setText(scheduleAnimeList.get(sected).getName());
					newActionPerformed(new ActionEvent(MainWindow.this.lblCatArr[0], 0, ""));
					searchaction();
				}else{
					
				}
				
			}
			
		});
		scheduleBottmBox.add(searchBTN);
	}

	// setting box
	Component savesettingbox() {
		source_state = an_con.source;
		savesetting = Box.createHorizontalBox();
		savesetting.add(Box.createHorizontalStrut(10));
		savesetting.add(secrow);
		SButton save = new SButton();
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (isloadingpanel || isdownall) {
					SPopup.ShowPopUpInfo(frame, StringResource.getString("TASK_RUNNING"));
					return;
				}
				if (src.getSelectedIndex() == -1) {
					SAlertDialog.showAlertInfo("Info", StringResource.getString("SOURCE_DISABLED"), frame);
					return;
				}
				boolean oldTheme = an_con.isDarkTheme;
				boolean oldHintop = an_con.isShowHintEnabled;
				an_con.destdir = dwntxt.getText();
				if (option1.isSelected()) {
					an_con.Default_downloader = true;
				} else {
					an_con.Default_downloader = false;
				}
				an_con.maxdownloads = (int) maxdwnlod.getSelectedItem();

				an_con.source = src.getSelectedIndex();

				if (an_con.source == 0) {

					an_con.tablesize = 70;

				} else {

					an_con.tablesize = 70;

				}
				an_con.VLC_LIBS_PATH = vlclibtxt.getText();
				an_con.FFMPEG_LIBS_PATH = ffmpeglibtxt.getText();

				if (rbDark.isSelected()) {
					an_con.isDarkTheme = true;
				} else {
					an_con.isDarkTheme = false;
				}
				if (rbHintYes.isSelected()) {
					an_con.isShowHintEnabled = true;
				} else {
					an_con.isShowHintEnabled = false;
				}
				if (oldTheme != an_con.isDarkTheme || oldHintop != an_con.isShowHintEnabled) {
					SAlertDialog.showAlertInfo("Alert",
							"Change in Theme or Hint Setting will be applied on next start.", frame);

					// initResources();
					// frame.revalidate();
					// frame.repaint();
				}
				SPopup.ShowPopUpInfo(frame, StringResource.getString("SAVE_SETTING"));
				/*
				 * cpane.remove(sp); cpane.add(sidepanel(),"West");
				 * lblCatArr[2].setBackground(new Color(242, 242, 242));
				 * lblCatArr[2].setOpaque(true); lblCatArr[0].setOpaque(false);
				 * cpane.revalidate();xcx cpane.repaint();
				 */
			}
		});
		save.setPreferredSize(new Dimension(50, 70));

		save.setIcon(StaticResource.getIcon(StringResource.getColorString("SAVE")));
		save.setBorderPainted(false);
		save.setBackGround(StringResource.getColor("TITLE_BG_COLOR"));
		save.setHover(StringResource.getColor("SELECTION"));
		save.showHint("Save Settings");
		save.setPressedColor(StringResource.getColor("PRESSED"));

		savesetting.add(save);
		return savesetting;
	}

	// Download Hbox
	Component dwnhbox() {
		dwnhbox = Box.createHorizontalBox();
		dwnhbox.add(Box.createHorizontalStrut(10));
		dwnhbox.add(secrow);
		SButton resume = new SButton("");
		resume.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				resumeDownload();
			}
		});
		resume.setPreferredSize(new Dimension(50, 70));
		resume.setIcon(StaticResource.getIcon(StringResource.getColorString("PLAY")));
		resume.setBorderPainted(false);
		resume.setBackGround(StringResource.getColor("TITLE_BG_COLOR"));
		resume.setHover(StringResource.getColor("SELECTION"));
		resume.setPressedColor(StringResource.getColor("PRESSED"));
		resume.showHint(StringResource.getString("HINT_RESUME"));
		SButton pause = new SButton("");
		pause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				pauseDownload();
			}
		});
		pause.setPreferredSize(new Dimension(50, 70));
		pause.setIcon(StaticResource.getIcon(StringResource.getColorString("PAUSE")));
		pause.setBorderPainted(false);
		pause.setBackGround(StringResource.getColor("TITLE_BG_COLOR"));
		pause.setHover(StringResource.getColor("SELECTION"));
		pause.setPressedColor(StringResource.getColor("PRESSED"));
		pause.showHint(StringResource.getString("HINT_PAUSE"));
		SButton del = new SButton("");
		del.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				removeDownloads();
			}
		});
		del.setPreferredSize(new Dimension(50, 70));
		del.setBackGround(StringResource.getColor("TITLE_BG_COLOR"));
		del.setHover(StringResource.getColor("SELECTION"));
		del.setIcon(StaticResource.getIcon(StringResource.getColorString("DELETE")));
		del.setBorderPainted(false);
		del.showHint(StringResource.getString("HINT_DEL"));
		del.setPressedColor(StringResource.getColor("PRESSED"));
		SButton restart = new SButton("");
		restart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				restartDownload();
			}
		});
		restart.setPreferredSize(new Dimension(50, 70));
		restart.setBackGround(StringResource.getColor("TITLE_BG_COLOR"));
		restart.setHover(StringResource.getColor("SELECTION"));
		restart.setIcon(StaticResource.getIcon(StringResource.getColorString("RESTART")));
		restart.setBorderPainted(false);
		restart.showHint(StringResource.getString("HINT_RESTART"));
		restart.setPressedColor(StringResource.getColor("PRESSED"));
		SButton startqueue = new SButton("");
		startqueue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				startQueue();
			}
		});
		startqueue.setPreferredSize(new Dimension(50, 70));
		startqueue.setBackGround(StringResource.getColor("TITLE_BG_COLOR"));
		startqueue.setHover(StringResource.getColor("SELECTION"));
		startqueue.setIcon(StaticResource.getIcon(StringResource.getColorString("START_QUEUE")));
		startqueue.setBorderPainted(false);
		startqueue.showHint(StringResource.getString("HINT_START_QUEUE"));
		startqueue.setPressedColor(StringResource.getColor("PRESSED"));
		SButton stopqueue = new SButton("");
		stopqueue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				stopQueue();
			}
		});
		stopqueue.setPreferredSize(new Dimension(50, 70));
		stopqueue.setBackGround(StringResource.getColor("TITLE_BG_COLOR"));
		stopqueue.setHover(StringResource.getColor("SELECTION"));
		stopqueue.setIcon(StaticResource.getIcon(StringResource.getColorString("PAUSE_QUEUE")));
		stopqueue.setBorderPainted(false);
		stopqueue.showHint(StringResource.getString("HINT_STOP_QUEUE"));
		stopqueue.setPressedColor(StringResource.getColor("PRESSED"));

		SButton add2q = new SButton("");
		add2q.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				add2q();
			}
		});
		add2q.setPreferredSize(new Dimension(50, 70));
		add2q.setBackGround(StringResource.getColor("TITLE_BG_COLOR"));
		add2q.setHover(StringResource.getColor("SELECTION"));
		add2q.setIcon(StaticResource.getIcon(StringResource.getColorString("ADD_QUEUE")));
		add2q.setBorderPainted(false);
		add2q.showHint(StringResource.getString("HINT_ADD_QUEUE"));
		add2q.setPressedColor(StringResource.getColor("PRESSED"));

		SButton removefromq = new SButton("");
		removefromq.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				removefromq();
			}
		});
		removefromq.setPreferredSize(new Dimension(50, 70));
		removefromq.setBackGround(StringResource.getColor("TITLE_BG_COLOR"));
		removefromq.setHover(StringResource.getColor("SELECTION"));
		removefromq.setIcon(StaticResource.getIcon(StringResource.getColorString("REMOVE_QUEUE")));
		removefromq.setBorderPainted(false);
		removefromq.showHint(StringResource.getString("HINT_REMOVE_QUEUE"));
		removefromq.setPressedColor(StringResource.getColor("PRESSED"));

		dwnhbox.add(resume);
		dwnhbox.add(pause);
		dwnhbox.add(del);
		dwnhbox.add(restart);
		dwnhbox.add(startqueue);
		dwnhbox.add(stopqueue);
		dwnhbox.add(add2q);
		dwnhbox.add(removefromq);

		return dwnhbox;
	}

	private void add2q() {
		int[] indexes = this.dwntabel.getSelectedRows();
		if (indexes.length < 1) {
			this.showMessageBox(this.getString("NONE_SELECTED"), this.getString("DEFAULT_TITLE"), 0);
		} else {
			if (this.processQueue) {
				this.showMessageBox("Queue is Runing. Please stop the queue to add new queue.",
						this.getString("DEFAULT_TITLE"), 0);
				return;
			}
			DownloadListItem[] items = new DownloadListItem[indexes.length];

			int i;
			DownloadListItem item;
			for (i = 0; i < indexes.length; ++i) {
				item = list.get(indexes[i]);
				if (item.mgr != null) {
					this.showMessageBox(this.getString("DWN_ACTIVE"), this.getString("DEFAULT_TITLE"), 0);
					return;
				}

				items[i] = item;
			}

			for (i = 0; i < indexes.length; ++i) {
				item = items[i];
				String tmp = item.filename;

				if (!this.queueitems.contains(tmp) && item.state != 50) {

					item.q = true;
					this.queueitems.add(tmp);
				}

			}

			this.mdwntablemodel.fireTableDataChanged();
			list.downloadStateChanged();
		}
	}

	private void removefromq() {
		int[] indexes = this.dwntabel.getSelectedRows();
		if (indexes.length < 1) {
			this.showMessageBox(this.getString("NONE_SELECTED"), this.getString("DEFAULT_TITLE"), 0);
		} else {
			if (this.processQueue) {
				this.showMessageBox("Queue is Runing. Please stop the queue to remove from queue.",
						this.getString("DEFAULT_TITLE"), 0);
				return;
			}
			DownloadListItem[] items = new DownloadListItem[indexes.length];

			int i;
			DownloadListItem item;
			for (i = 0; i < indexes.length; ++i) {
				item = list.get(indexes[i]);
				if (item.mgr != null) {
					this.showMessageBox(this.getString("DWN_ACTIVE"), this.getString("DEFAULT_TITLE"), 0);
					return;
				}

				items[i] = item;
			}

			for (i = 0; i < indexes.length; ++i) {
				item = items[i];
				String tmp = item.filename;
				if (this.queueitems.contains(tmp) && item.state != 50) {

					item.q = false;
					this.queueitems.remove(tmp);
				}

			}

			this.mdwntablemodel.fireTableDataChanged();
			list.downloadStateChanged();
		}
	}

	private void restartDownload() {
		int index = this.dwntabel.getSelectedRow();
		if (index < 0) {
			this.showMessageBox(this.getString("NONE_SELECTED"), this.getString("DEFAULT_TITLE"), 0);
		} else {
			DownloadListItem item = list.get(index);
			if (item != null) {
				this.restartDownload(item);

			}
		}
	}

	private void newActionPerformed(ActionEvent e) {
		JLabel actionVar = (JLabel) e.getSource();
		if(isOffAnimePanel){
			isanimepanel = false;
		}
		isOffAnimePanel = false;
		if (actionVar.getName().equals("ANIME")) {
			isAnimeHome = true;
			changeBottomPanel(hbbox);
			if (isloadingpanel) {
				changeMainPanel(panel_4);
			} else if (!isanimepanel || source_state != an_con.source) {
				if (source_state != an_con.source) {
					source_state = an_con.source;
					splatest = 0;
					home_action();
				} else {
					changeMainPanel(jsp);
				}
			} else {
				changeMainPanel(animepanel(apicon));
			}

		} else if (actionVar.getName().equals("DOWNLOAD")) {
			isAnimeHome = false;
			changeBottomPanel(dwnhbox);
			changeMainPanel(jsptable);
		} else if (actionVar.getName().equals("SETTING")) {
			isAnimeHome = false;
			changeBottomPanel(savesetting);
			updatelist();
			changeMainPanel(configpanel);
		} else if(actionVar.getName().equals("SCHEDULE")){
			isAnimeHome = false;
			changeBottomPanel(this.scheduleBottmBox);
			changeMainPanel(this.getSchdulePanel());
		}else if(actionVar.getName().equals("OFFLINE")){
			isOffAnimePanel = true;
			isAnimeHome = false;
			changeBottomPanel(hbbox);
			changeMainPanel(getOffAnimePanel());
		}
		sprenew(actionVar);
	}
	
	private void changeSchedulePanel(Component p) {
		if (curSchedulePanel != null) {
			mainSchdule.remove(curSchedulePanel);
		}
		curSchedulePanel = p;
		mainSchdule.add(p, "Center");
		mainSchdule.repaint();
		mainSchdule.revalidate();
	}
	
	private void changeMainPanel(Component p) {
		if (curPanel != null) {
			cpane.remove(curPanel);
		}
		curPanel = p;
		cpane.add(p, "Center");
		cpane.repaint();
		cpane.revalidate();
	}

	private void changeBottomPanel(Box b) {
		if (curMenuBox != null) {
			bpanel.remove(curMenuBox);
		}
		curMenuBox = b;
		bpanel.add(b, BorderLayout.CENTER);
		bpanel.repaint();
		bpanel.revalidate();
	}

	// Action method
	public void weekDayActionPerformed(ActionEvent e) {
		JLabel lbl = (JLabel) e.getSource();
		if(curDay.equals(lbl.getName())){
			return;
		}
		if(isScheduleLoading){
			SPopup.ShowPopUpInfo(frame, StringResource.getString("TASK_RUNNING"));
			return;
		}
		curDay = lbl.getName();
		getScheduleAnime(lbl.getName());
		weekSelected(lbl);
	}
	
	public void weekSelected(JLabel var16) {
		for (int var13 = 0; var13 < this.lblweek.length; ++var13) {
			if (var16 == this.lblweek[var13]) {
				this.lblweek[var13].setBackground(StringResource.getColor("SEARCH_BG"));
				this.lblweek[var13].setOpaque(true);
			} else {
				this.lblweek[var13].setOpaque(false);
			}
			mainSchdule.repaint();
		}
	}

	public void sprenew(JLabel var16) {
		for (int var13 = 0; var13 < this.lblCatArr.length; ++var13) {
			if (var16 == this.lblCatArr[var13]) {
				this.lblCatArr[var13].setBackground(StringResource.getColor("SEARCH_BG"));
				this.lblCatArr[var13].setOpaque(true);
			} else {
				this.lblCatArr[var13].setOpaque(false);
			}
		}

		this.sp.repaint();
	}

	// checking if duplicate file exists
	int getDupAction(String url) {
		JTextField txt = new JTextField(url, 30);
		String lbl = StringResource.getString("DUP_TXT");
		JComboBox choice = new JComboBox(new String[] { StringResource.getString("DUP_OP1"),
				StringResource.getString("DUP_OP2"), StringResource.getString("DUP_OP3") });
		SCheckBox chk = new SCheckBox(StringResource.getString("DUP_CHK"));
		int ret = JOptionPane.showOptionDialog((Component) null, new Object[] { txt, lbl, choice, chk },
				StringResource.getString("DUP_TITLE"), 2, 3, (Icon) null, (Object[]) null, (Object) null);
		if (ret == 0) {
			int index = choice.getSelectedIndex();
			if (chk.isSelected()) {
				an_con.duplicateLinkAction = index;
			}

			return index;
		} else {
			return -1;
		}
	}

	@Override
	public void addDownload(String var1, String var2, String var3, String var4, String var5, String var6,
			ArrayList var7, String var8) {
		// TODO Auto-generated method stub

	}

	@Override
	public void downloadNow(String url, String name, String folder, String user, String pass, String referer,
			ArrayList cookies, String userAgent) {
		Utils.l(TAG + "_downloadNow", "Referer: " + referer, true);
		createOffLineAnime(url, folder, name);//create offline
		
		boolean overwrite = false;
		Utils.i("MainWindow", "Downloadnow: " + name, true);
		for (int i = 0; i < list.list.size(); ++i) {
			DownloadListItem item = (DownloadListItem) list.list.get(i);
			if (url.equals(item.url) || name.equals(item.filename)) {

				int action = an_con.duplicateLinkAction;
				if (action == 3) {
					action = this.getDupAction(url);
				}

				if (action == -1) {
					return;
				}

				if (action == 2) {
					if (item.state == 50) {
						File file = new File(item.saveto, item.filename);
						if (file.exists()) {
							AnimeUtil.open(file.getParentFile());
						} else if (item.mgr == null) {
							this.restartDownload(item);
						} else {
							this.showMessageBox(this.getString("DWN_ACTIVE"), "Message", 0);
						}
					} else if (item.mgr == null) {
						this.resumeDownload(item);
					} else {
						this.showMessageBox(this.getString("DWN_ACTIVE"), "Message", 0);
					}

					return;
				}

				overwrite = action == 1;
				break;
			}
		}

		this.startDownload(url, name, folder, user, pass, userAgent, referer, cookies, (DownloadListItem) null,
				overwrite);
		// Utils.l("MainWindow","[LINE : 1548] Download downloadnow Current
		// count(without) " + currentdwnld,true);
	}

	synchronized void startDownload(String url, String name, String folder, String user, String pass, String userAgent,
			String referer, ArrayList cookies, DownloadListItem item, boolean overriteExisting) {
		UUID id = UUID.randomUUID();
		Utils.l(TAG + "_startDownload", "Referer: " + referer, true);

		IDownloader mgr = createDownloader(id, url, name, folder, an_con.tempdir, userAgent, referer, cookies, an_con);

		mgr.setOverwrite(overriteExisting);

		if (item == null) {
			item = new DownloadListItem();
			list.add(item);
			Utils.i("MainWindow", "itme added to Download List", true);
		}

		if (!AnimeUtil.isNullOrEmpty(user) && !AnimeUtil.isNullOrEmpty(pass)) {
			mgr.setCredential(user, pass);
			Utils.i("MainWindow", "USER and PASS Require", true);
			item.user = user;
			item.pass = pass;
		}

		item.mgr = mgr;
		item.isDASH = false;
		item.filename = name;
		item.url = url;
		item.q = true;
		item.dateadded = item.lasttry = (new SimpleDateFormat("MMM dd")).format(new Date());
		item.id = id;
		item.saveto = folder;
		item.dtype = mgr.getType();
		item.icon = StaticResource.getIcon("vedio.png");
		item.userAgent = userAgent;
		item.referer = referer;
		if (cookies != null) {
			item.cookies = new ArrayList();
			item.cookies.addAll(cookies);
		}

		item.state = 10;
		item.type = "Anime";
		list.sort();
		this.mdwntablemodel.fireTableDataChanged();
		list.downloadStateChanged();

		mgr.setDownloadListener(this);
		// Starting function for queue

		if (!queueitems.contains(item.filename)) {
			queueitems.add(item.filename);
		}

		try {
			if (currentdwnld < an_con.maxdownloads) {
				mgr.start();
				currentdwnld += 1;
				// Utils.l("MainWindow","Start Download. Current count " +
				// currentdwnld,false);
				if (this.processQueue && !this.presentqueuelist.contains(item.filename)) {
					this.presentqueuelist.add(item.filename);
				}
			} else {
				item.mgr = null;
				// same like pause
				item.status = "Stopped";
				item.state = 40;
				if (!processQueue) {
					presentqueuelist = null;
					presentqueuelist = new ArrayList<String>();
					this.processQueue = true;
					// Utils.l("MainWindow","[Line : 1608] Download limit
					// reached added to queue,false);
					if (!this.processQueue) {

					}
				}
			}
		} catch (Exception var15) {
			var15.printStackTrace();
		}

	}

	IDownloader createDownloader(UUID id, String url, String name, String folder, String tempdir, String userAgent,
			String referer, ArrayList cookies, animeconfig config) {

		if (isHLS(url)) {
			Utils.l("MainWindow_isHLS", "HLS_True : " + url, true);

			return new HLSDownloader(id, url, name, folder, tempdir, referer, userAgent, cookies, config);

		} else {
			Utils.l("MainWindow_isHLS", "ConnectionManager Started", true);

			return new ConnectionManager(id, url, name, folder, config.tempdir, userAgent, referer, cookies, config);
		}
	}

	boolean isHLS(String url) {
		try {
			return (new URI(url).getPath().endsWith("m3u8"));

		} catch (URISyntaxException e) {
			Utils.l("MainWindow_isHLS", "False", true);
			return false;
		}
	}

	private void resumeDownload() {
		int index = this.dwntabel.getSelectedRow();
		if (index < 0) {
			this.showMessageBox(this.getString("NONE_SELECTED"), this.getString("DEFAULT_TITLE"), 0);
		} else {
			DownloadListItem item = list.get(index);
			if (item != null) {
				this.resumeDownload(item);
				// Utils.l("MainWindow","[LINE : 1636] Resume Download. Current
				// count(without) " + currentdwnld,false);
			}
		}
	}

	void resumeDownload(DownloadListItem item) {
		if (item.mgr != null) {
			this.showMessageBox(this.getString("DWN_ACTIVE"), this.getString("DEFAULT_TITLE"), 0);
		} else if (item.state == 50) {
			this.showMessageBox(this.getString("DWN_FINISHED"), this.getString("DEFAULT_TITLE"), 0);
		} else {
			if (item.tempdir != null && item.tempdir.length() >= 1) {
				IDownloader mgr = null;

				if (item.dtype == StaticConstants.HLS) {
					Utils.l("MainWindow_Resume", "HLS_Resume : " + item.dtype, true);
					mgr = new HLSDownloader(item.id, item.url, item.filename, item.saveto, item.tempdir, item.referer,
							item.userAgent, item.cookies, an_con);
				} else if (item.dtype == StaticConstants.HTTP) {
					Utils.l("MainWindow_Resume", "HTTP_Resume : " + item.dtype, true);
					mgr = new ConnectionManager(item.id, item.url, item.filename, item.saveto, item.tempdir,
							item.userAgent, item.referer, item.cookies, an_con);
				}

				if (!AnimeUtil.isNullOrEmpty(item.user) && !AnimeUtil.isNullOrEmpty(item.pass)) {
					mgr.setCredential(item.user, item.pass);
				}

				item.mgr = mgr;
				this.mdwntablemodel.fireTableDataChanged();
				list.downloadStateChanged();
				/*
				 * XDMDownloadWindow dw = new XDMDownloadWindow(mgr);
				 * item.window = dw; if(config.showDownloadPrgDlg && this.qi !=
				 * item) { dw.showWindow(); }
				 * 
				 * mgr.setProgressListener(dw);
				 */
				mgr.setDownloadListener(this);

				if (!this.queueitems.contains(item.filename)) {
					this.queueitems.add(item.filename);
					// Utils.l("MainWindow","Adding to queue in resume",true);
				}
				// Utils.l("MainWindow","Queueitems under resume : " +
				// queueitems,true);
				// Utils.l("MainWindow","Queueitem size under resume : " +
				// queueitems.size(),true);

				try {
					if (this.currentdwnld < an_con.maxdownloads) {
						mgr.resume();
						currentdwnld += 1;
						// Utils.l("MainWindow","Resume Download. Current count
						// " + currentdwnld,true);
						if (this.processQueue && !this.presentqueuelist.contains(item.filename)) {
							this.presentqueuelist.add(item.filename);
						}
					} else {
						item.mgr = null;

						if (!processQueue) {
							presentqueuelist = null;
							presentqueuelist = new ArrayList<String>();
							this.processQueue = true;
						}
						// Utils.l("MainWindow","Download item limit reached
						// wait ",true);
					}

				} catch (Exception var5) {
					var5.printStackTrace();
				}

			} else {
				this.startDownload(item.url, item.filename, item.saveto, (String) null, (String) null, item.userAgent,
						item.referer, item.cookies, item, true);
				// Utils.l("MainWindow","Start Download under resume. Current
				// count(without) " + currentdwnld,true);

			}

		}
	}

	void restartDownload(DownloadListItem item) {
		if (item.mgr != null) {
			this.showMessageBox(this.getString("DWN_ACTIVE"), this.getString("DEFAULT_TITLE"), 0);
		} else {
			this.startDownload(item.url, item.filename, item.saveto, (String) null, (String) null, item.userAgent,
					item.referer, item.cookies, item, true);

		}
	}

	void showMessageBox(String msg, String title, int msgType) {
		SAlertDialog.showAlertInfo(this.getString("ALERT"), msg, frame);
	}

	String getString(String id) {
		String str = StringResource.getString(id);
		return str == null ? "" : str;
	}

	void createDelDialog(String message, int[] indexs) {
		Color bgBtn = StringResource.getColor("SEARCH_BG");
		Color textcolor = StringResource.getColor("TEXT_COLOR");
		Color hover = StringResource.getColor("SELECTION");
		Color pressed = StringResource.getColor("PRESSED");
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setOpaque(true);
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		JLabel msgLbl = new JLabel(message);
		msgLbl.setBorder(new EmptyBorder(0, 10, 0, 0));
		msgLbl.setFont(StaticResource.materialFontSmallL);
		msgLbl.setForeground(StringResource.getColor("TEXT_COLOR"));
		panel.add(msgLbl);
		final SCheckBox chk = new SCheckBox(StringResource.getString("DELETE_FILE"));
		chk.setFont(StaticResource.materialFontSmallLi);
		panel.add(chk);
		mainPanel.add(panel, "Center");
		JPanel btnPanel = new JPanel();
		btnPanel.setOpaque(false);
		SButton okBtn = new SButton(StringResource.getString("OK_BTN"));
		okBtn.setBackGround(bgBtn);
		okBtn.setBorderPainted(false);
		okBtn.setHover(hover);
		okBtn.setFont(StaticResource.materialFontSmallL);
		okBtn.setPressedColor(pressed);
		okBtn.setForeground(textcolor);
		okBtn.setWidth(70);
		okBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				deleteOperation(indexs, chk);
				SAlertDialog.closeDialog();
			}

		});
		btnPanel.add(okBtn);
		SButton canelBtn = new SButton(StringResource.getString("CANCEL_BTN"));
		canelBtn.setBackGround(bgBtn);
		canelBtn.setBorderPainted(false);
		canelBtn.setHover(hover);
		canelBtn.setFont(StaticResource.materialFontSmallL);
		canelBtn.setPressedColor(pressed);
		canelBtn.setForeground(textcolor);
		canelBtn.setWidth(70);
		canelBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				SAlertDialog.closeDialog();
			}

		});
		btnPanel.add(canelBtn);
		mainPanel.add(btnPanel, "South");
		SAlertDialog.showAlertAction(StringResource.getString("DELETE_CONFIRM_TITLE"), mainPanel, frame, 350, 120);
	}

	void deleteOperation(int[] indexes, SCheckBox delFile) {
		DownloadListItem[] items = new DownloadListItem[indexes.length];

		int i;
		DownloadListItem item;
		for (i = 0; i < indexes.length; ++i) {
			item = list.get(indexes[i]);
			if (item.mgr != null) {
				SAlertDialog.closeDialog();
				this.showMessageBox(this.getString("DWN_ACTIVE"), this.getString("DEFAULT_TITLE"), 0);
				return;
			}

			items[i] = item;
		}
		//delete ep from config
		
		
		for (i = 0; i < indexes.length; ++i) {
			item = items[i];
			String tmpdir = item.tempdir;
			list.remove(item);
			// UUID = item.id;
			if (this.queueitems.contains(item.filename)) {
				this.queueitems.remove(this.queueitems.indexOf(item.filename));
			}
			if (this.presentqueuelist != null && this.presentqueuelist.contains(item.filename)) {
				this.presentqueuelist.remove(this.presentqueuelist.indexOf(item.filename));
			}
			String strfile = item.saveto + File.separator + item.filename;
			if (!AnimeUtil.isNullOrEmpty(tmpdir)) {
				this.delDirRec(tmpdir);
			}
			if (delFile.isSelected()) {
				Utils.l("MainWindow_removeDownloads", "Deleting file: " +strfile,true);
				if (!AnimeUtil.isNullOrEmpty(strfile)) {
					this.delstrfile(strfile);
					
					//Update animeconf
					String animeLoc = item.saveto;
					if(animeLoc.contains("ep")){
						animeLoc = animeLoc.substring(0,animeLoc.length()-3);
					}
					AnimeConf animeConf = new AnimeConf(animeLoc);
					int epSize = 0;
					if(animeConf.isConfigExist()){
						try {
							animeConf.loadJSON(null);
							animeConf.removeEP(item.filename.substring(0,item.filename.lastIndexOf(".")));
							epSize = animeConf.getEpObj().length();
							animeConf.saveAndClose();
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							//e.printStackTrace();
							Utils.e(TAG+"_deleteOperation", "Unable to update Anime Config file.", true);
						}
					}
					if(epSize==0){
						this.delEmptyDir(item.saveto);	
					}
				}

			}
		}

		this.mdwntablemodel.fireTableDataChanged();
		list.downloadStateChanged();
	}

	void removeDownloads() {
		int[] indexes = this.dwntabel.getSelectedRows();
		Utils.l("MainWindow_removeDownloads", "Clicked", true);

		String message = StringResource.getString("DELETE_CONFIRM") + (indexes.length > 1 ? "s" : "") + "?";

		if (indexes.length < 1) {
			this.showMessageBox(this.getString("NONE_SELECTED"), this.getString("DEFAULT_TITLE"), 0);
		} else {
			// if(JOptionPane.showConfirmDialog(this, params, "Confirm delete",
			// 0) == 0)
			createDelDialog(message, indexes);
		}
		UpdateDiskSpace();
	}

	void delstrfile(String file) {
		// Utils.l("MainWindow_removeDownloads", "Deleting folder: "
		// +file,true);
		File fdir = new File(file);
		if (fdir.exists()) {
			fdir.delete();
			Utils.i("MainWindow_removeDownloads", "Sucessfully Deleted File: " + fdir.getAbsolutePath(), true);
		}
	}

	void delEmptyDir(String dir) {
		//dir = dir.substring(0, dir.length() - 2);
		
		File fdir = new File(dir);
		
		if (fdir.exists()) {
			File[] files = fdir.listFiles();
			if (files == null || files.length == 0) {
				if(dir.contains("ep")){
				fdir = fdir.getParentFile();
				}
				if(deleteDirectory(fdir)){
					removeItemOffAnimelocList(fdir.getAbsolutePath());
					Utils.i("MainWindow_removeDownloads", "Sucessfully Deleted Directory: " + fdir.getAbsolutePath(), true);
				}
			}
		}
	}
	
	boolean deleteDirectory(File directoryToBeDeleted) {
	    File[] allContents = directoryToBeDeleted.listFiles();
	    if (allContents != null) {
	        for (File file : allContents) {
	            deleteDirectory(file);
	        }
	    }
	    return directoryToBeDeleted.delete();
	}

	void delDirRec(String dir) {
		File fdir = new File(dir);
		File[] files = fdir.listFiles();
		if (files == null) {
			fdir.delete();

		} else {
			for (int i = 0; i < files.length; ++i) {
				files[i].delete();
			}
			fdir.delete();
		}
		Utils.i("MainWindow_removeDownloads", "Sucessfully Deleted Temp: " + fdir.getAbsolutePath(), true);
	}

	void add2Q(DownloadListItem item, boolean add) {
		if (item.mgr == null && item.state != 50) {
			item.q = add;
			list.downloadStateChanged();
			this.model.fireTableDataChanged();
		} else {
			this.showMessageBox(this.getString("DWN_ACTIVE_OR_FINISHED"), this.getString("DEFAULT_TITLE"), 0);
		}
	}

	@Override
	public void add2Queue(String url, String name, String folder, String user, String pass, String referer,
			ArrayList cookies, String userAgent, boolean q) {
		UUID id = UUID.randomUUID();
		DownloadListItem item = new DownloadListItem();
		createOffLineAnime(url, folder, name);
		item.isDASH = false;
		list.add(item);
		item.user = user;
		item.pass = pass;
		item.filename = name;
		item.url = url;
		item.q = q;
		item.dateadded = item.lasttry = (new SimpleDateFormat("MMM dd")).format(new Date());
		item.id = id;
		item.saveto = folder;
		item.icon = StaticResource.getIcon("vedio.png");
		item.userAgent = userAgent;
		item.referer = referer;
		if (cookies != null) {
			item.cookies.addAll(cookies);
		}
		item.state = 40;
		item.status = "Stopped";
		item.type = "Anime";
		if (!queueitems.contains(item.filename)) {
			queueitems.add(item.filename);
		}

		this.mdwntablemodel.fireTableDataChanged();
		list.downloadStateChanged();
	}

	@Override
	public void updateManager(UUID id, Object data) {
		// TODO Auto-generated method stub
		DownloadListItem item = list.getByID(id);
		if (item != null) {
			DownloadInfo info = (DownloadInfo) data;
			item.updateData(info);
			int index = list.getIndex(item);
			if (index >= 0) {
				this.mdwntablemodel.fireTableRowsUpdated(index, index);

			}

		}
	}

	@Override
	public void downloadComplete(UUID id) {
		// TODO Auto-generated method stub
		UpdateDiskSpace();
		DownloadListItem item = list.getByID(id);
		if (item != null) {
			// item.window = null;
			item.status = "Download Complete " + item.size;
			item.timeleft = "";
			item.state = 50;
			item.q = false;
			this.mdwntablemodel.fireTableDataChanged();
			list.downloadStateChanged();
			/*
			 * if(this.qi == item) { this.qi = null; if(this.processQueue &&
			 * this.processNextQueuedDownload()) { return; } }
			 */
			if (currentdwnld != 0) {
				currentdwnld -= 1;
			}

			if (this.queueitems.contains(item.filename)) {
				this.queueitems.remove(this.queueitems.indexOf(item.filename));
			}
			if (this.processQueue) {
				if (this.currentdwnld < an_con.maxdownloads) {
					if (this.currentruning.contains(item)) {
						this.currentruning.remove(item);
					}
					this.processNextQueuedDownload();
				} else {
					// Utils.l("MainWindow","Max Download limit reached ",true);
				}
			}
		}
	}

	@Override
	public void downloadFailed(UUID id) {

		if (currentdwnld != 0) {
			currentdwnld -= 1;
		}

		list.downloadStateChanged();
		DownloadListItem item = list.getByID(id);
		if (item != null) {
			// item.window = null;

			if (this.processQueue) {
				if (this.currentruning.contains(item)) {
					this.currentruning.remove(item);
				}
				this.processNextQueuedDownload();
			} else {
				Utils.i("MainWindow", "Queue stopped", true);
			}
		} else {
			Utils.i("MainWindow", "Not Queued", true);
		}
	}

	boolean processNextQueuedDownload() {
		// System.out.println("[Line : 1899] ProcessNextQueue");
		if (!this.processQueue) {
			return false;
		} else if (this.currentruning == null) {
			return false;
		} else {
			boolean okchk = true;
			int i = 0;
			while (this.currentdwnld < an_con.maxdownloads) {
				if (this.queueitems.size() == 0) {
					return false;
				}
				DownloadListItem di = list.getByfilename(this.queueitems.get(i));
				if (!this.presentqueuelist.contains(di.filename) && di.q && di.mgr == null && di.state != 50) {
					if (di.mgr == null && di.state != 50 && di.q) {
						this.currentruning.add(di);
						this.resumeDownload(di);

					}
				} else {
					i += 1;

					if (i == this.queueitems.size()) {
						this.qi = null;
						this.processQueue = false;
						an_con.schedule = false;
						this.schedulerActive = false;

						return false;
					}
				}
			}
			return true;

		}
	}

	@Override
	public void downloadConfirmed(UUID id, Object data) {
		DownloadListItem item = list.getByID(id);
		IDownloader mgr = item.mgr;
		item.tempdir = mgr.getTempdir();
		item.url = mgr.getUrl();
		item.filename = mgr.getFileName();
		item.icon = StaticResource.getIcon("vedio.png");
		item.updateData((DownloadInfo) data);
		this.mdwntablemodel.fireTableDataChanged();
		list.downloadStateChanged();
		// System.out.println("[LINE : 1859] Download Confirmed. Current
		// count(without) " + currentdwnld);
	}

	@Override
	public void downloadPaused(UUID id) {

		synchronized (this) {
			DownloadListItem item = list.getByID(id);
			if (item != null) {
				// item.window = null;
				item.mgr = null;
				item.status = "Stopped " + item.sprg + "% of " + item.size;
				item.state = 40;
				currentdwnld -= 1;
				// System.out.println("[LINE : 1959] Download Paused. Current
				// count " + currentdwnld);
				int index = list.getIndex(item);
				if (index >= 0) {
					this.mdwntablemodel.fireTableRowsUpdated(index, index);
					list.downloadStateChanged();

					if (this.processQueue) {
						if (JOptionPane.showConfirmDialog(this, this.getString("CONTINUE_Q"),
								this.getString("DEFAULT_TITLE"), 0, 3) == 0) {
							if (this.currentruning.contains(item)) {
								this.currentruning.remove(item);
							}
							this.processNextQueuedDownload();

						} else {
							this.processQueue = false;
							this.qi = null;
						}
					} else {
						this.qi = null;
					}

				}
			}
		}

	}

	@Override
	public void interceptDownload(DownloadIntercepterInfo info) {
		this.addDownload(info.url, AnimeUtil.getFileName(info.url), an_con.destdir, (String) null, (String) null,
				info.referer, info.cookies, info.ua);
		if (currentdwnld != 0) {
			currentdwnld -= 1;
		}
		// System.out.println("[LINE : 1903] Intercept Download. Current count "
		// + currentdwnld);

	}

	@Override
	public void getCredentials(ConnectionManager var1, String var2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void configChanged() {
		// TODO Auto-generated method stub

	}

	@Override
	public void ytCallback(String var1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void restoreWindow() {
		// TODO Auto-generated method stub
		// this.setVisible(true);
	}

	@Override
	public void startQueue() {
		if (this.processQueue) {
			this.showMessageBox(this.getString("Q_STARTED"), this.getString("DEFAULT_TITLE"), 0);
		} else if (this.qi == null) {
			this.processQueue = true;
			presentqueuelist = null;
			presentqueuelist = new ArrayList<String>();
			this.processNextQueuedDownload();
		}
	}

	@Override
	public void stopQueue() {
		this.processQueue = false;
		if (this.currentruning != null) {
			for (int i = 0; i < currentruning.size(); i++) {
				qi = currentruning.get(i);
				if (this.qi.mgr != null) {
					this.qi.mgr.stop();
					// add2Q(qi, true);
					this.qi = null;
				} else {
					this.qi = null;
				}
			}

		}
	}

	private void pauseDownload() {
		int[] indexes = this.dwntabel.getSelectedRows();

		for (int i = 0; i < indexes.length; ++i) {
			DownloadListItem item = list.get(indexes[i]);
			if (item.mgr != null) {
				item.mgr.stop();
			}
		}

	}

	@Override
	public void exit() {
		// TODO Auto-generated method stub

	}

	// Second source program below

	// downloadall
	public void downloadallep() {
		btnback.setText("Cancel");
		btnNewButton.setEnabled(false);
		qlty = (String) hdpselct.getSelectedItem();
		subdubType = (String) prefselct.getSelectedItem();
		dwnanimename = remAnimename;
		animeurldownload = curanimeurl;
		if (opStm.isSelected()) {
			an_con.isStreamEnable = true;
		} else {
			an_con.isStreamEnable = false;
		}
		final int start = (int) start_ep.getSelectedItem();
		final int end = (int) end_ep.getSelectedItem();
		if (start > end) {
			Utils.e("MainWindow_DownAll", "Start cannot be greater than from End", true);
			SAlertDialog.showAlertInfo("Alert", "Start Episode cannot be greater than from End Episode.", frame);

			btnNewButton.setEnabled(true);
			isdownall = false;
			btnback.setText("Back");
			return;
		}
		Utils.i("MainWindow_DownAll", "Running download All", true);

		Thread t = new Thread() {
			public void run() {
				int f_start = start - 1;
				int f_end = end - 1;
				int ep_no = 0;
				for (int i = f_start; i <= f_end; i++) {
					if (!isdownall) {
						break;
					}
					ep_no = ep_no + 1;
					Utils.l(TAG, "Adding Ep " + ep_no + " to queue", true);
					btnNewButton.setText("Adding Ep " + ep_no + " to queue");
					fetch.downloadhelper(epname[i] + ";" + epno[i], an_con.source, frame, true);
					
				}
				SwingUtilities.invokeLater(new Runnable() {// do swing work on
															// EDT
					public void run() {
						btnNewButton.setEnabled(true);
						btnback.setEnabled(true);
						btnNewButton.setText("Download All");
						isdownall = false;
						btnback.setText("Back");
						SAlertDialog.showAlertInfo("Alert",
								"Please click start queue in Download to start your download.", frame);
					}
				});
			}
		};
		t.start();

	}

	// Adding Menu title
	public void add_menu_title() {
		Box vbox_rec = Box.createVerticalBox();
		Box hbox_rec = Box.createHorizontalBox();

		JLabel lblNewLabel_1 = new JLabel(" ");
		lblNewLabel_1.setBackground(StringResource.getColor("TEXT_COLOR"));
		lblNewLabel_1.setOpaque(true);
		lblNewLabel_1.setBorder(new EmptyBorder(7, 0, 5, 7));

		JLabel rec_lbl = new JLabel("Main Menu");
		rec_lbl.setFont(StaticResource.materialFontMidR);
		rec_lbl.setForeground(StringResource.getColor("TEXT_COLOR"));
		rec_lbl.setBorder(new EmptyBorder(5, 5, 5, 5));

		hbox_rec.add(lblNewLabel_1);
		hbox_rec.add(rec_lbl);
		hbox_rec.add(Box.createHorizontalGlue());
		vbox_rec.add(hbox_rec);
		vbox_rec.setBounds(0, 15, 147, 27);
		this.sp.add(vbox_rec);
	}

	// Adding Recent view to Side panel
	public void add_recent() {

		Utils.l("MainWindow_AR", "Adding recent to panel", true);

		Box vbox_rec = Box.createVerticalBox();

		vbox_rec = Box.createVerticalBox();

		Box hbox_rec = Box.createHorizontalBox();

		JLabel lblNewLabel_1 = new JLabel(" ");
		lblNewLabel_1.setBackground(StringResource.getColor("TEXT_COLOR"));
		lblNewLabel_1.setOpaque(true);
		lblNewLabel_1.setBorder(new EmptyBorder(7, 0, 5, 7));

		JLabel rec_lbl = new JLabel("Last Watching");
		rec_lbl.setFont(StaticResource.materialFontMidR);
		rec_lbl.setForeground(StringResource.getColor("TEXT_COLOR"));
		rec_lbl.setBorder(new EmptyBorder(5, 5, 5, 5));

		hbox_rec.add(lblNewLabel_1);
		hbox_rec.add(rec_lbl);
		hbox_rec.add(Box.createHorizontalGlue());

		vbox_rec.add(hbox_rec);

		Box hbox_anime = Box.createHorizontalBox();
		title = new JLabel("");
		title.setForeground(StringResource.getColor("TEXT_COLOR"));

		title.setName("RECENT");
		title.setBorder(new EmptyBorder(5, 20, 5, 5));
		title.setFont(StaticResource.materialFontMidL);
		title.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {

			}
		});

		hbox_anime.add(title);
		Box hbox_ep = Box.createHorizontalBox();
		epname1 = new JLabel("No Info...");
		epname1.setForeground(StringResource.getColor("TEXT_COLOR"));
		epname1.setBorder(new EmptyBorder(0, 20, 0, 0));
		epname1.setFont(StaticResource.materialFontSmallLi);
		epname1.setForeground(StaticResource.lightgreycolor);
		epname1.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				if (isloadingpanel) {
					SPopup.ShowPopUpInfo(frame, StringResource.getString("TASK_RUNNING"));
					return;
				}
				isloadingpanel = true;
				setstatus_loggger("Starting Video Player");
				Thread t = new Thread() {
					public void run() {
						if (title.getText().equals("")) {
							isloadingpanel = false;
							return;
						}
						Recent_Watching rec1 = Recent_Watching.get_inten();
						Utils.l("MainWindow_AR", "Anime url : " + rec1.getAnime_url(), true);
						if (rec1.getAnime_url() != null) {
							if(an_con.isExternalPlayer){
								if(ExternalPlayer.play(rec1.getAnime_url(),  "")){
									Utils.i(TAG,"External Player Started.", true);
								}else{
									SAlertDialog.showAlertInfo("External Player Error", StringResource.getString("VLC_NOT_FOUND"), frame);
								}
							}else{
								if (an_con.isPlayingnow) {
									if (VLCPlayer.getInstance().getURL()
											.equals(rec1.getAnime_url() + " " + rec1.getEpno())) {
										Utils.i(TAG + "_Recent", "Video Already Playing.", true);
										isloadingpanel = false;
										return;
									}
									VLCPlayer.getInstance().set_newurl(rec1.getAnime_url(), rec1.getAnime_title());
								} else {
									Utils.i(TAG + "_Recent", "CreatingPlayer.", true);
									VLCPlayer.createPlayer(rec1.getAnime_title() + " " + rec1.getEpno(),
											rec1.getAnime_url(), an_con, frame);
								}
								Utils.l("MainWindow_AR", "Anime playing", true);
								VLCPlayer.getInstance().play();
								VLCPlayer.getInstance().setPlayList(-1, null, null, an_con.source);
								Utils.l("MainWindow_AR", "Setting seekbar", true);
								VLCPlayer.getInstance().setSeekbarValue(rec1.getVideo_location());
								an_con.isPlayingnow = true;
							}
						}
						SwingUtilities.invokeLater(new Runnable() {// do swing
																	// work on
																	// EDT
							public void run() {
								isloadingpanel = false;

							}
						});
					}
				};
				t.start();

			}
		});
		hbox_ep.add(epname1);

		hbox_anime.add(Box.createHorizontalGlue());
		hbox_ep.add(Box.createHorizontalGlue());
		vbox_rec.add(hbox_anime);
		vbox_rec.add(hbox_ep);
		vbox_rec.setBounds(0, 230, 147, 500);
		this.sp.add(vbox_rec);

	}

	public void update_rec() {

		if (Recent_Watching.get_inten() != null) {
			this.title.setText(Recent_Watching.get_inten().getAnime_title());
			this.epname1.setText(Recent_Watching.get_inten().getEpno());
		}
	}

	// Recent_watching
	public void recent_watching(String name, String i, String VideoURL) {

		Recent_Watching.get_inten().setAnime_title(name);
		Recent_Watching.get_inten().setAnime_url(VideoURL);
		Recent_Watching.get_inten().setEpno("Episode " + i);
		File frecdirconfig = new File(fAppDir, ".recent_watch");
		Recent_Watching.save(frecdirconfig);
	}

	private void saveNames() {
		curanimeurl = animeurldownload;
		remAnimename = dwnanimename;
	}

	// anime panel
	Component animepanel(ImageIcon icon) {
		isanimepanel = true;
		SPopup.pLoading.hide();
		Color textcolor = StringResource.getColor("TEXT_COLOR");
		Color elmBG = StringResource.getColor("SEARCH_BG");
		Color bg = StringResource.getColor("PANEL_BG_COLOR");
		Color sel = StringResource.getColor("SELECTION");
		Color pre = StringResource.getColor("PRESSED");
		Border border_elm = BorderFactory.createLineBorder(StringResource.getColor("BORDER"), 1);
		Border border = BorderFactory.createLineBorder(textcolor, 1);
		panel_3 = new JPanel();
		panel_3.setLayout(new BorderLayout());
		panel_3.setOpaque(false);
		String detail = this.description;

		JPanel aDetialpanel = new JPanel(new BorderLayout());
		// aDetialpanel.setBackground(Color.RED);
		aDetialpanel.setOpaque(false);
		panel_3.add(aDetialpanel, BorderLayout.NORTH);

		Box verticalBox_1 = Box.createVerticalBox();
		aDetialpanel.add(verticalBox_1, BorderLayout.NORTH);
		verticalBox_1.add(Box.createVerticalStrut(20));

		Box horizontalBox = Box.createHorizontalBox();
		// panel.add(horizontalBox, BorderLayout.WEST);
		verticalBox_1.add(horizontalBox);
		horizontalBox.add(Box.createHorizontalStrut(25));
		JLabel aIcon = new JLabel();
		aIcon.setIcon(icon);
		aIcon.setBorder(border);
		horizontalBox.add(aIcon);
		// aIcon.setVerticalAlignment(SwingConstants.TOP);
		// horizontalBox.add(Box.createHorizontalStrut(10));

		JPanel aTitle_DetailsP = new JPanel(new BorderLayout());
		// aTitle_DetailsP.setBackground(Color.green);
		aTitle_DetailsP.setOpaque(false);
		horizontalBox.add(aTitle_DetailsP);

		Box vBoxTitle_dtl = Box.createVerticalBox();
		aTitle_DetailsP.add(vBoxTitle_dtl, "Center");

		JLabel aTitleLbl = new JLabel(remAnimename);
		aTitleLbl.setFont(StaticResource.materialFontBigL);
		aTitleLbl.setForeground(textcolor);
		aTitleLbl.setBorder(new EmptyBorder(0, 10, 0, 0));
		vBoxTitle_dtl.add(aTitleLbl);

		// detail=detail.substring(0,1000);
		JLabel aDtlLbl = new JLabel(detail);
		aDtlLbl.setFont(StaticResource.materialFontSmallL);
		aDtlLbl.setForeground(textcolor);
		aDtlLbl.setBorder(new EmptyBorder(0, 14, 0, 0));
		vBoxTitle_dtl.add(aDtlLbl);
		verticalBox_1.add(Box.createVerticalStrut(20));
		// end of Detailing Anime
		GridLayout experimentLayout = new GridLayout(0, 1);

		JPanel opepPanel = new JPanel(new BorderLayout());
		opepPanel.setOpaque(false);
		JPanel opPanel = new JPanel(new BorderLayout());
		// opPanel.setBackground(Color.BLUE);
		opPanel.setOpaque(false);
		panel_3.add(opepPanel, BorderLayout.CENTER);
		opepPanel.add(opPanel, "West");
		epPanel = new JPanel(new BorderLayout());
		epPanel.setOpaque(false);
		opepPanel.add(epPanel, "Center");
		Box vBoxOp = Box.createVerticalBox();
		opPanel.add(vBoxOp, "Center");

		// options

		// vBoxOp.add(hBox_space);
		JLabel opLbl = new JLabel(StringResource.getString("OPTIONS"));
		opLbl.setAlignmentY(Component.TOP_ALIGNMENT);
		opLbl.setAlignmentX(Component.LEFT_ALIGNMENT);
		opLbl.setFont(StaticResource.materialFontMidR);
		opLbl.setForeground(textcolor);
		opLbl.setBorder(new EmptyBorder(2, 5, 0, 160));
		vBoxOp.add(opLbl);
		JLabel opSep = new JLabel();
		opSep.setAlignmentY(Component.TOP_ALIGNMENT);
		opSep.setAlignmentX(Component.LEFT_ALIGNMENT);
		opSep.setPreferredSize(new Dimension(197, 1));
		opSep.setMaximumSize(new Dimension(197, 1));

		opSep.setOpaque(true);
		opSep.setBackground(elmBG);
		vBoxOp.add(opSep);
		vBoxOp.add(Box.createVerticalStrut(2));
		//Buttons
		Box hBoxStmDwn = Box.createHorizontalBox();
		hBoxStmDwn.setAlignmentX(Component.LEFT_ALIGNMENT);
		hBoxStmDwn.setAlignmentY(Component.TOP_ALIGNMENT);
		Dimension dop = new Dimension(14, 100);
		JLabel modeLbl = new JLabel(StringResource.getString("MODE"));
		modeLbl.setAlignmentY(Component.CENTER_ALIGNMENT);
		modeLbl.setFont(StaticResource.materialFontSmallL);
		modeLbl.setForeground(textcolor);
		modeLbl.setBorder(new EmptyBorder(2, 5, 0, 0));
		hBoxStmDwn.add(modeLbl);
		opStm = new SRadio("Stream");
		opStm.setAlignmentY(Component.CENTER_ALIGNMENT);
		opStm.setFont(StaticResource.materialFontSmallL);
		opStm.setBackground(bg);
		opStm.setHover(sel);
		opStm.setPressed(pre);
		opStm.setForeground(textcolor);
		opStm.setMinimumSize(dop);
		opStm.setPreferredSize(dop);
		hBoxStmDwn.add(opStm);
		SRadio opDwn = new SRadio("Download");
		opDwn.setAlignmentY(Component.CENTER_ALIGNMENT);
		// opDwn.setMinimumSize(new Dimension(100,50));
		opDwn.setFont(StaticResource.materialFontSmallL);
		opDwn.setBackground(bg);
		opDwn.setHover(sel);
		opDwn.setPressed(pre);
		// opDwn.setAlignmentX(Component.LEFT_ALIGNMENT);
		opDwn.setMinimumSize(dop);
		opDwn.setPreferredSize(dop);
		opDwn.setForeground(textcolor);
		hBoxStmDwn.add(opDwn);

		if (an_con.isStreamEnable) {
			opStm.setSelected(true);
		} else {
			opDwn.setSelected(true);
		}

		ButtonGroup StmDwmgroup = new ButtonGroup();
		StmDwmgroup.add(opStm);
		StmDwmgroup.add(opDwn);

		ActionListener sliceActionListener = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				JRadioButton aButton = (JRadioButton) actionEvent.getSource();
				if ("Stream".equals(aButton.getText())) {
					an_con.isStreamEnable = true;
					btnNewButton.setEnabled(false);
				} else {
					an_con.isStreamEnable = false;
					btnNewButton.setEnabled(true);
				}
				Utils.i("MainWindow", "Mode Entered: " + aButton.getText(), false);
			}
		};

		opDwn.addActionListener(sliceActionListener);
		opStm.addActionListener(sliceActionListener);
		vBoxOp.add(hBoxStmDwn);
		vBoxOp.add(Box.createVerticalStrut(4));
		Box hBoxSubDub = Box.createHorizontalBox();
		hBoxSubDub.setAlignmentX(Component.LEFT_ALIGNMENT);
		prefselct = new SComboBox();
		if (false) {
			JLabel subdubLbl = new JLabel(StringResource.getString("SUBDUB"));
			subdubLbl.setFont(StaticResource.materialFontSmallL);
			subdubLbl.setBorder(new EmptyBorder(0, 5, 0, 0));
			subdubLbl.setAlignmentY(Component.CENTER_ALIGNMENT);
			subdubLbl.setForeground(textcolor);
			hBoxSubDub.add(subdubLbl);
			prefselct.setFont(StaticResource.materialFontSmallL);
			prefselct.setAlignmentY(Component.CENTER_ALIGNMENT);
			prefselct.setBackGround(elmBG);
			prefselct.setForeground(textcolor);
			prefselct.setMaximumSize(new Dimension(120, 24));
			prefselct.addItem("sub");
			prefselct.addItem("dub");
			hBoxSubDub.add(prefselct);
			vBoxOp.add(hBoxSubDub);
			vBoxOp.add(Box.createVerticalStrut(4));
		}
		// vBoxOp.add(Box.createVerticalStrut(200));
		// HDP
		Box hBoxqty = Box.createHorizontalBox();
		hBoxqty.setAlignmentX(Component.LEFT_ALIGNMENT);

		JLabel subQty = new JLabel(StringResource.getString("QTY"));
		subQty.setAlignmentY(Component.CENTER_ALIGNMENT);
		subQty.setFont(StaticResource.materialFontSmallL);
		subQty.setForeground(textcolor);
		subQty.setBorder(new EmptyBorder(0, 5, 0, 0));
		hBoxqty.add(subQty);

		hdpselct = new SComboBox();
		hdpselct.setAlignmentY(Component.CENTER_ALIGNMENT);
		hdpselct.setAlignmentX(SwingConstants.LEFT);
		hdpselct.setFont(StaticResource.materialFontSmallL);
		hdpselct.setBackGround(elmBG);
		hdpselct.setForeground(textcolor);
		hdpselct.setMaximumSize(new Dimension(120, 24));
		hBoxqty.add(hdpselct);
		
		if (an_con.source == 2) {
			hdpselct.addItem("1080");
			hdpselct.addItem("720");
			hdpselct.addItem("480");
			hdpselct.addItem("360");
			vBoxOp.add(hBoxqty);
			vBoxOp.add(Box.createVerticalStrut(4));
		}
		// Downlad All
		vBoxOp.add(Box.createVerticalGlue());
		JLabel dwnAllLbl = new JLabel(StringResource.getString("DWNALL"));
		dwnAllLbl.setAlignmentY(Component.CENTER_ALIGNMENT);
		dwnAllLbl.setFont(StaticResource.materialFontSmallR);
		dwnAllLbl.setForeground(textcolor);
		dwnAllLbl.setBorder(new EmptyBorder(0, 5, 0, 0));
		vBoxOp.add(dwnAllLbl);
		vBoxOp.add(Box.createVerticalStrut(4));

		Box hBoxDwn = Box.createHorizontalBox();
		hBoxDwn.setAlignmentX(Component.LEFT_ALIGNMENT);

		JLabel from_ep = new JLabel(StringResource.getString("FROM"));
		from_ep.setAlignmentY(Component.CENTER_ALIGNMENT);
		from_ep.setFont(StaticResource.materialFontSmallL);
		from_ep.setForeground(textcolor);
		from_ep.setBorder(new EmptyBorder(0, 5, 0, 0));
		hBoxDwn.add(from_ep);

		Dimension Ddwnall = new Dimension(50, 24);
		start_ep = new SComboBox();
		start_ep.setAlignmentY(Component.CENTER_ALIGNMENT);
		start_ep.setFont(StaticResource.materialFontSmallL);
		start_ep.setBackGround(elmBG);
		start_ep.setForeground(textcolor);
		start_ep.setPreferredSize(Ddwnall);
		start_ep.setMaximumSize(Ddwnall);
		hBoxDwn.add(start_ep);

		JLabel to_ep = new JLabel(StringResource.getString("TO"));
		to_ep.setAlignmentY(Component.CENTER_ALIGNMENT);
		to_ep.setFont(StaticResource.materialFontSmallL);
		to_ep.setForeground(textcolor);
		to_ep.setBorder(new EmptyBorder(0, 5, 0, 0));
		hBoxDwn.add(to_ep);

		end_ep = new SComboBox();
		end_ep.setAlignmentY(Component.CENTER_ALIGNMENT);
		end_ep.setFont(StaticResource.materialFontSmallL);
		end_ep.setBackGround(elmBG);
		end_ep.setForeground(textcolor);
		end_ep.setPreferredSize(Ddwnall);
		end_ep.setMaximumSize(Ddwnall);
		hBoxDwn.add(end_ep);

		for (int i = 1; i <= epno.length; i++) {
			start_ep.addItem(i);
			end_ep.addItem(i);
		}
		end_ep.setSelectedIndex(epno.length - 1);

		vBoxOp.add(hBoxDwn);
		vBoxOp.add(Box.createVerticalStrut(4));
		Box hBoxbtnDwnBck = Box.createHorizontalBox();
		hBoxbtnDwnBck.setAlignmentX(Component.LEFT_ALIGNMENT);
		btnNewButton = new SButton("Download All");
		btnNewButton.setAlignmentY(Component.CENTER_ALIGNMENT);
		if (an_con.isStreamEnable || isOffAnimePanel) {
			btnNewButton.setEnabled(false);
		}
		btnNewButton.setBorderPainted(false);
		btnNewButton.setBackGround(elmBG);
		btnNewButton.setHover(sel);
		btnNewButton.setPressedColor(pre);
		btnNewButton.setFont(StaticResource.materialFontSmallL);
		btnNewButton.setForeground(textcolor);
		btnNewButton.setWidth(110);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				isdownall = true;
				downloadallep();
			}
		});
		hBoxbtnDwnBck.add(btnNewButton);
			
		btnback = new SButton("Back");
		btnback.setAlignmentY(Component.CENTER_ALIGNMENT);
		if (isdownall) {
			btnback.setText("Cancel");
			btnNewButton.setEnabled(false);
		}
		btnback.setBorderPainted(false);
		btnback.setBackGround(elmBG);
		btnback.setFont(StaticResource.materialFontSmallL);
		btnback.setHover(sel);
		btnback.setPressedColor(pre);
		btnback.setForeground(textcolor);
		btnback.setWidth(105);
		btnback.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (isdownall) {
					isdownall = false;
					btnback.setText("Back");
				} else {

					isanimepanel = false;
					if(isOffAnimePanel){
						changeMainPanel(getOffAnimePanel());
					}else{
						changeMainPanel(jsp);
					}
					
					// cpane.remove(panel_3);
					// cpane.add(jsp, "Center");
					// cpane.revalidate();
					// cpane.repaint();
				}
			}
		});
		hBoxbtnDwnBck.add(btnback);
		vBoxOp.add(hBoxbtnDwnBck);
		
		//Delete Anime Button
		SButton deleteAnimeBTN = new SButton("Delete Anime");
		deleteAnimeBTN.setAlignmentY(Component.CENTER_ALIGNMENT);
		deleteAnimeBTN.setBorderPainted(false);
		deleteAnimeBTN.setBackGround(elmBG);
		deleteAnimeBTN.setHover(sel);
		deleteAnimeBTN.setPressedColor(pre);
		deleteAnimeBTN.setFont(StaticResource.materialFontSmallL);
		deleteAnimeBTN.setForeground(textcolor);
		deleteAnimeBTN.setWidth(110);
		deleteAnimeBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		if(isOffAnimePanel){
			//vBoxOp.add(deleteAnimeBTN);	
		}
		
		
		// vBoxOp.add(Box.createVerticalStrut(4));
		// Episode Part
		Box vBoxepL = Box.createVerticalBox();
		epPanel.add(vBoxepL, "North");
		JLabel epTitleLbl = new JLabel("Episodes");
		epTitleLbl.setAlignmentY(Component.TOP_ALIGNMENT);
		epTitleLbl.setHorizontalAlignment(SwingConstants.LEFT);
		epTitleLbl.setFont(StaticResource.materialFontMidR);
		epTitleLbl.setForeground(textcolor);
		epTitleLbl.setBorder(new EmptyBorder(2, 5, 0, 0));
		vBoxepL.add(epTitleLbl);
		JLabel lbl_sub_dub = new JLabel();
		lbl_sub_dub.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_sub_dub.setAlignmentY(Component.TOP_ALIGNMENT);
		lbl_sub_dub.setFont(StaticResource.materialFontSmallL);
		lbl_sub_dub.setForeground(textcolor);
		lbl_sub_dub.setBorder(new EmptyBorder(2, 5, 0, 0));
		if (an_con.source == 4) {
			lbl_sub_dub.setText("Last Sub: " + sub_ep_no + " Dub: " + dub_ep_no);
		}

		vBoxepL.add(lbl_sub_dub);
		// old

		JPanel panel2 = new JPanel();
		panel2.setBackground(StringResource.getColor("PANEL_BG_COLOR"));
		panel2.setOpaque(true);
		panel2.setLayout(experimentLayout);
		epScrollPane = new SScrollPane(panel2);
		epScrollPane.setOpaque(false);
		// panel2.setLayout(new FlowLayout());
		epPanel.add(epScrollPane, "Center");
		epScrollPane.setAlignmentX(LEFT_ALIGNMENT);
		epScrollPane.setBorder(null);
		// scrollPane.setBackground(Color.white);
		epScrollPane.setOpaque(false);
		// scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		epScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		// scrollPane.setViewportBorder(new LineBorder(Color.RED));

		if (epno.length != 0) {
			SButton[] air = new SButton[epno.length];

			for (int i = 0; i < epno.length; i++) {
				final int a = i;

				air[i] = new SButton();
				String btn_name = "";
				if(isOffAnimePanel){
					btn_name = epno[i];
				}else{
					btn_name = "Episode " + epno[i];
				}
				air[i].setText(btn_name);
				air[i].setName(epname[i]);
				air[i].setBackGround(elmBG);
				air[i].setHover(sel);
				air[i].setPressedColor(pre);
				air[i].setFont(StaticResource.materialFontSmallL);
				air[i].setForeground(textcolor);
				air[i].setBorder(null);
				air[i].setHeight(25);
				air[i].addMouseListener(new MouseAdapter() {
					public void mouseReleased(MouseEvent e) {
						actionPerformed1(new ActionEvent(air[a], a, ""));
					}
				});
				panel2.add(air[i]);
			}
		} else {
			SButton empty = new SButton("No Episode/nAvaliable");
			empty.setFont(StaticResource.materialFontSmallL);
			empty.setBorder(null);
			empty.setEnabled(false);
			empty.setBackGround(bg);
			panel2.add(empty);

		}
		
		if(isOffAnimePanel){
			 hBoxDwn.setVisible(false);
			 hBoxqty.setVisible(false);
			 hBoxStmDwn.setVisible(false);
			 hBoxSubDub.setVisible(false);
		}
		return panel_3;
	}

	protected void actionPerformed1(ActionEvent actionEvent) {
		final JButton var16 = (JButton) actionEvent.getSource();
		if(isOffAnimePanel){
			cur_ep = (int) actionEvent.getID();
			isPlayListUpdated = true;
			if(an_con.isExternalPlayer){
				if(ExternalPlayer.play(var16.getName(), "")){
					Utils.i(TAG,"External Player Started.", true);
				}else{
					SAlertDialog.showAlertInfo("External Player Error", StringResource.getString("VLC_NOT_FOUND"), frame);
				}
			}else{
				startOfflineVideo(var16.getText(),var16.getName(),cur_ep);
			}

		}else{
			cur_ep = (int) actionEvent.getID();
			qlty = (String) hdpselct.getSelectedItem();
			subdubType = (String) prefselct.getSelectedItem();
			dwnanimename = remAnimename;
			animeurldownload = curanimeurl;
			if (opStm.isSelected()) {
				an_con.isStreamEnable = true;
			} else {
				an_con.isStreamEnable = false;
			}
			isPlayListUpdated = true;
			fetch.downloadhelper(var16.getName() + ";" + var16.getText(), an_con.source, frame, false);
		}
		
	}
	
	public void startOfflineVideo(String name, String path, int curEP){
		File a = new File(path);
		if(!a.exists()){
			SPopup.ShowPopUpInfo(frame, "Episode doesn't exists. Please check if the downloading is completed.");
			return;
		}
		if (isloadingpanel) {
			SPopup.ShowPopUpInfo(frame, StringResource.getString("TASK_RUNNING"));
			return;
		}
		setstatus_loggger("Starting Video Player");
		isloadingpanel = true;
		Thread t = new Thread() {
			public void run() {
				Utils.l(TAG+"_startOfflineVideo", "Anime EP Name : " + name, true);
					if (an_con.isPlayingnow) {
						if (VLCPlayer.getInstance().getURL().equals(path + " " + name)) {
							Utils.i(TAG + "_Recent", "Video Already Playing.", true);
							return;
						}
						VLCPlayer.getInstance().set_newurl(path, name);
					} else {
						Utils.i(TAG + "_Recent", "CreatingPlayer.", true);
						VLCPlayer.createPlayer(name, path, an_con, frame);
					}
					Utils.l("MainWindow_AR", "Anime playing", true);
					VLCPlayer.getInstance().play();
					Utils.l("MainWindow_AR", "Setting seekbar", true);
					an_con.isPlayingnow = true;
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						Utils.i(TAG+"_startOfflineVideo", "Playing: " + name, true);
						if(isPlayListUpdated) {
							Utils.l(TAG,"isPlayListUpdated - true",true);
			        		VLCPlayer.getInstance().setPlayList(cur_ep, epname, epno,an_con.source);
			        	}else {
			        		Utils.l(TAG,"iscur_EpUpdated - true",true);
			        		//VLCPlayer.getInstance().iscur_EpUpdated(true);
			        		VLCPlayer.getInstance().setCur_EP(curEP);
			        	}
						if(path.equals(Recent_Watching.get_inten().getAnime_url())){
							Utils.l("Fetcher_SD", "Same URl " + path ,true);
							VLCPlayer.getInstance().setSeekbarValue(Recent_Watching.get_inten().getVideo_location());
						}else {
							Utils.l("Fetcher_SD", "Not Same URl " + Recent_Watching.get_inten().getAnime_url() ,true);
							recent_watching(dwnanimename, name.replaceAll("[^0-9]", ""),path);
			    			update_rec();
						}
						isloadingpanel = false;
					}
				});
			}
		};
		t.start();
	}

	private JComponent loadingscreen() {
		isloadingpanel = true;
		threadcreater();
		loadiconth.start();
		JPanel loadingscreen = new JPanel();
		loadingscreen.setLayout(null);
		loadingscreen.setBackground(Color.WHITE);
		loadingscreen.setBorder(new EmptyBorder(5, 5, 5, 5));

		Box horizontalBox = Box.createHorizontalBox();
		horizontalBox.setBackground(Color.WHITE);
		horizontalBox.setBounds(300, 200, 255, 114);
		loadingscreen.add(horizontalBox);
		horizontalBox.add(loadicon);

		Component horizontalStrut = Box.createHorizontalStrut(5);
		horizontalBox.add(horizontalStrut);

		progressLabel = new JLabel();
		progressLabel.setFont(StaticResource.materialFontSmallL);

		progressLabel.setVerticalAlignment(SwingConstants.CENTER);
		progressLabel.setHorizontalAlignment(SwingConstants.LEFT);
		horizontalBox.add(progressLabel);

		JLabel lblNewLabel = new JLabel("Loading Please wait....");
		lblNewLabel.setForeground(StringResource.getColor("TEXT_COLOR"));
		lblNewLabel.setFont(StaticResource.materialFontSmallL);
		horizontalBox.add(lblNewLabel);
		loadingscreen.setOpaque(false);
		Utils.l(TAG + "_loadingScree", "created", true);
		return loadingscreen;
	}

	public void createPopupMenu() {
		Utils.l(TAG, "createPopupMenu", true);
		popupCtx = new JPopupMenu();
		Border borderblack = BorderFactory.createLineBorder(Color.black, 1);
		popupCtx.setBackground(StringResource.getColor("PANEL_BG_COLOR"));
		popupCtx.setBorder(borderblack);
		addMenuItem("CTX_OPEN", popupCtx);
		addMenuItem("CTX_OPEN_FOLDER", popupCtx);
		addMenuItem("CTX_RESUME", popupCtx);
		addMenuItem("CTX_PAUSE", popupCtx);
		addMenuItem("CTX_DELETE", popupCtx);
		popupCtx.setInvoker(dwntabel);
		dwntabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent me) {
				// JOptionPane.showMessageDialog(null,"Mouse clicked:
				// "+me.getButton()+" "+MouseEvent.BUTTON3);
				if (me.getButton() == MouseEvent.BUTTON3 || SwingUtilities.isRightMouseButton(me)
						|| me.isPopupTrigger()) {// || isMacPopupTrigger(me)
					Point p = me.getPoint();
					JTable tbl = dwntabel;
					if (tbl.getRowCount() < 1)
						return;
					if (tbl.getSelectedRow() < 0) {
						int row = tbl.rowAtPoint(p);
						if (row >= 0) {
							tbl.setRowSelectionInterval(row, row);
						}
					}
					if (tbl.getSelectedRows().length > 0 && tbl.getSelectedRows().length < 2) {
						popupCtx.show(dwntabel, me.getX(), me.getY());
					}

				}
			}
		});

	}

	private void addMenuItem(String id, JComponent menu) {
		JMenuItem mitem = new JMenuItem(StringResource.getString(id));
		mitem.setName(id);
		mitem.setBackground(StringResource.getColor("PANEL_BG_COLOR"));
		mitem.setForeground(StringResource.getColor("TEXT_COLOR"));
		mitem.setFont(StaticResource.materialFontMidR);
		mitem.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				actionPerformed_ctxMenu(new ActionEvent(mitem, 0, ""));
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				mitem.setBackground(StringResource.getColor("SEARCH_BG"));
				// super.mouseEntered(e);

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				mitem.setBackground(StringResource.getColor("PANEL_BG_COLOR"));
				// super.mouseExited(e);

			}

		});
		menu.add(mitem);
	}

	protected void actionPerformed_ctxMenu(ActionEvent actionEvent) {
		final JMenuItem var16 = (JMenuItem) actionEvent.getSource();
		int index = this.dwntabel.getSelectedRow();
		DownloadListItem item = list.get(index);
		if (item == null) {
			return;
		}
		switch (var16.getName()) {
		case "CTX_OPEN":
			playVideo(item);
			break;
		case "CTX_OPEN_FOLDER":
			openfolder(item);
			break;
		case "CTX_RESUME":
			resumeDownload();
			break;
		case "CTX_PAUSE":
			pauseDownload();
			break;
		case "CTX_DELETE":
			removeDownloads();
			break;
		}
	}

	// menu actions
	private void openfolder(DownloadListItem item) {
		File file = new File(item.saveto);
		if (!file.exists()) {
			SPopup.ShowPopUpInfo(frame, StringResource.getString("FOLDER_NOT_FOUND"));
			return;
		}
		try {
			Utils.l(TAG, "FileName: " + item.saveto, true);
			Utils.openFolder(item.filename, item.saveto);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Utils.l(TAG, e.toString());
		}
	}

	private void playVideo(DownloadListItem item) {
		File file = new File(item.saveto, item.filename);
		if (!file.exists()) {
			SPopup.ShowPopUpInfo(frame, StringResource.getString("FILE_NOT_FOUND"));
			return;
		}
		if (isloadingpanel) {
			SPopup.ShowPopUpInfo(frame, StringResource.getString("TASK_RUNNING"));
			return;
		}
		isloadingpanel = true;
		setstatus_loggger("Starting Video Player");
		Thread t = new Thread() {
			public void run() {
				if(an_con.isExternalPlayer){
					if(ExternalPlayer.play(file.getAbsolutePath(),  "")){
						Utils.i(TAG,"External Player Started.", true);
						recent_watching(item.filename, extEpNo(item.filename), file.getAbsolutePath());
						update_rec();
					}else{
						SAlertDialog.showAlertInfo("External Player Error", StringResource.getString("VLC_NOT_FOUND"), frame);
					}
				}else{
					if (an_con.isPlayingnow) {
						if (VLCPlayer.getInstance().getURL().equals(file.getAbsolutePath())) {
							Utils.i(TAG + "_playVideo", "Video Already Playing.", true);
							isloadingpanel = false;
							return;
						}
						VLCPlayer.getInstance().set_newurl(file.getAbsolutePath(), item.filename);
					} else {
						Utils.i(TAG + "_playVideo", "CreatingPlayer.", true);
						VLCPlayer.createPlayer(item.filename, file.getAbsolutePath(), an_con, frame);
					}
					VLCPlayer.getInstance().play();
					VLCPlayer.getInstance().setPlayList(-1, null, null, an_con.source);
					an_con.isPlayingnow = true;
					if (file.getAbsolutePath().equals(Recent_Watching.get_inten().getAnime_url())) {
						Utils.l("Fetcher_SD", "Same URl " + file.getAbsolutePath(), true);
						VLCPlayer.getInstance().setSeekbarValue(Recent_Watching.get_inten().getVideo_location());
					} else {
						Utils.l("Fetcher_SD", "Not Same URl " + Recent_Watching.get_inten().getAnime_url(), true);
						recent_watching(item.filename, extEpNo(item.filename), file.getAbsolutePath());
						update_rec();
					}
				}
				
				setstatus_loggger("Playing: " + title);
				SwingUtilities.invokeLater(new Runnable() {// do swing work on
															// EDT
					public void run() {
						isloadingpanel = false;

					}
				});
			}
		};
		t.start();
	}

	private String extEpNo(String name) {
		name = name.replace("mp4", "");
		name = name.replace("1080", "");
		name = name.replace("720", "");
		name = name.replace("480", "");
		name = name.replace("360", "");
		int value = Integer.parseInt(name.replaceAll("[^0-9]", ""));
		Utils.l(TAG + "_extEpNo", "" + value, true);
		return Integer.toString(value);
	}

	public void loadqueue(File file) {

		if (!file.exists()) {
			return;

		}
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), Charset.forName("UTF-8")));
			int count = Integer.parseInt(reader.readLine().trim());
			for (int i = 0; i < count; i++) {
				queueitems.add(reader.readLine().trim());
			}
		} catch (Exception e) {

		}
		try {
			reader.close();
		} catch (Exception e1) {
		}

	}
	
	public void loadOffAnimeList(File file) {

		if (!file.exists()) {
			return;

		}
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), Charset.forName("UTF-8")));
			int count = Integer.parseInt(reader.readLine().trim());
			Utils.l(TAG+"_loadOffAnimeList","Total offline Animes: " + count,true);
			for (int i = 0; i < count; i++) {
				offAnimelocList.add(reader.readLine().trim());
			}
		} catch (Exception e) {

		}
		try {
			reader.close();
		} catch (Exception e1) {
		}

	}

	public void save() {
		int count = queueitems.size();
		File file = new File(fAppDir, "queues");
		BufferedWriter writer = null;
		String newLine = System.getProperty("line.separator");

		try {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), Charset.forName("UTF-8")));
			writer.write(count + newLine);
			for (int i = 0; i < count; i++) {

				writer.write(queueitems.get(i) + newLine);

			}
		} catch (Exception e) {

		}
		if (writer != null) {
			try {
				writer.close();
			} catch (IOException e) {

			}
		}
	}
	
	private File getOffAnimeList(){
		return new File(fAppDir, "offline");
	}
	
	
	public void updateOffAnimelocList() {
		int count = offAnimelocList.size();
		File file = getOffAnimeList();
		BufferedWriter writer = null;
		String newLine = System.getProperty("line.separator");

		try {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), Charset.forName("UTF-8")));
			writer.write(count + newLine);
			for (int i = 0; i < count; i++) {
				writer.write(offAnimelocList.get(i) + newLine);
			}
		} catch (Exception e) {

		}
		if (writer != null) {
			try {
				writer.close();
			} catch (IOException e) {

			}
		}
	}
	public void removeItemOffAnimelocList(String path) {
			if(offAnimelocList.contains(path)){
				int index = offAnimelocList.indexOf(path);
				if(offAnimeList.size()>index){
					offAnimeList.remove(index);
				}
				offAnimelocList.remove(path);
				Utils.l(TAG+"_removeItemOffAnimelocList","Anime: " + path + " removed successfully.",true);
			}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
	}
	
	private void idReg() {
		fetch.idReg("0");
	}

}

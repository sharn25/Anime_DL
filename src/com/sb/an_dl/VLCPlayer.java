package com.sb.an_dl;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Font;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;

import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.sb.GUI.SAlertDialog;
import com.sb.GUI.SButton;
import com.sb.GUI.SPopup;
import com.sb.GUI.SSlider;
import com.sb.downport.StringResource;
import com.sb.downport.animeconfig;
import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;


import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;

import uk.co.caprica.vlcj.player.embedded.DefaultAdaptiveRuntimeFullScreenStrategy;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;
import uk.co.caprica.vlcj.runtime.x.LibXUtil;
import javax.swing.JButton;
import javax.rmi.CORBA.Util;
import javax.swing.Box;

import java.awt.Dimension;
import java.awt.EventQueue;

public class VLCPlayer extends ALFrame    {

	/**
	 * Create the panel.
	 */
	// variables
	
	EmbeddedMediaPlayerComponent mediaPlayerComponent;
	//VLCPlayer Object
	private static VLCPlayer vlcplayer;
	private animeconfig an_con;
	//MainWindow Object
	private static MainWindow mainwindow;
	private boolean isPlaying = false;
	private boolean isMute = false;
	private boolean isFullScreen = false;
	private boolean oldmoment = false;
	private boolean isSliderchanging = false;
	private boolean isPositionChanging = false;
	private boolean isCur_EpUpdated;
	private boolean isOffAnimePanel;
	private int lastMutevalue;
	public SButton btnMute;
	private static String url;
	private  SButton btnPlayorPause;
	private SSlider volslider;
	private SSlider seekslider;
	EmbeddedMediaPlayer mediaPlayer;
	Canvas videoSurface = null;
	static String name;
	private JLabel playingLbl;
	private String[] epPlaylist;
	private String[] epPlaylistno;
	private int cur_ep;
	private int cases;
	//String
	private String subdub;
	private String qlty; 
	private String animename;
	private String animeurl;
	private String[] ref;
	//Constants
	private final static String TAG="VLCPlayer";
	public static void main(String[] args) {
		
	}
	
	public void setSeekbarValue(int value) {
		seekslider.setValue(value);
	}
	
	public static void createPlayer(String title,String link, animeconfig an_con, MainWindow Mainwindow) {
		
		if(vlcplayer==null) {
			vlcplayer = new VLCPlayer(title, an_con);
		}
		if(mainwindow==null) {
			mainwindow = Mainwindow;
		}
		link = link.replace(" ", "%20");
		Utils.l("VLCPlayer", "Video URL : " + link,true);
		url=link;
		name = title;
	}
	public static VLCPlayer getInstance() {
		return vlcplayer;
	}
	public String getURL() {
		return url;
	}
	
	public VLCPlayer(String name, animeconfig an_con) {
		if(!StaticResource.loadVLCLibs(an_con.VLC_LIBS_PATH)) {
			Utils.i("VLCPlayer","Unable to libs",true);
			return;
		}
		
		this.an_con = an_con;
		
		//loadVLClib("G:/Other/Program/VLCPortable/App/vlc/win32-amd64");
		this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try{mediaPlayerComponent.release();}catch(Exception e1) {
                	//e1.printStackTrace();
                	Utils.l("VLCPlayer","MediaPlayerComponet is null", true);
                }
                Recent_Watching.get_inten().setVideo_location(seekslider.getValue());
                Utils.l("VLCPlayer", "Seekbar location : " + seekslider.getValue(),true );
                an_con.isPlayingnow=false;
                vlcplayer=null;
                MainWindow.getmainwindow().setstatus_loggger("");
                //System.exit(0);
            }
        });
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 50, 800, 500);
		setTitle("Anime DL Player");
		setIconImage(StaticResource.getIcon("icon.png").getImage());
		getTitlePanel().setBackground(StringResource.getColor("TITLE_BG_COLOR"));
		getpanContent().setBackground(StringResource.getColor("PANEL_BG_COLOR"));
	    getpanClient().setBackground(StringResource.getColor("PANEL_BG_COLOR"));
	    Box nhBox = Box.createHorizontalBox();
		 JLabel icon = new JLabel();
		 icon.setMaximumSize(new Dimension(32,32));
		 icon.setBorder(new EmptyBorder(0, 0, 2, 0));
		 icon.setIcon(StaticResource.getIcon("icon_32.png"));
		 nhBox.add(icon);
		 Box nvBox = Box.createVerticalBox();
		 JLabel var19 = new JLabel(StringResource.getString("TITLE_PLAYER"));
	      var19.setBorder(new EmptyBorder(0, 5, 0, 0));
	      var19.setFont(StaticResource.materialFontBigL);
	      var19.setForeground(StringResource.getColor("TEXT_COLOR"));
	      nvBox.add(var19);
	      
	      playingLbl = new JLabel();
	      playingLbl.setBorder(new EmptyBorder(0, 5, 0, 0));
	      playingLbl.setFont(StaticResource.materialFontSmallLi);
	      playingLbl.setForeground(StringResource.getColor("TEXT_COLOR"));
	      nvBox.add(playingLbl);
	      nhBox.add(nvBox);
	      nhBox.setBorder(new EmptyBorder(8,8,0,0));
	      getTitlePanel().add(nhBox, "Center");
	      
	      JPanel cpanel = new JPanel(new BorderLayout());
	      cpanel.setOpaque(false);
	      super.add(cpanel);
	      JPanel bpanel = new JPanel(new BorderLayout());
	      bpanel.setBackground(StringResource.getColor("TITLE_BG_COLOR"));
	      cpanel.add(bpanel, BorderLayout.SOUTH);
	     // /*
	      videoSurface = new Canvas();
	      final List<String> vlcArgs = new ArrayList<String>();
		  configureParameters(vlcArgs); 
		  videoSurface.addMouseListener(new MouseAdapter(){
			    @Override
			    public void mouseClicked(MouseEvent e){
			        if(e.getClickCount()==2){
			        	
			            // your code here
			        	mediaPlayerComponent.getMediaPlayer().toggleFullScreen();
			        }else {
			        	
			        	
			        }
			        	
			    }
			 });
	      
		  videoSurface.addMouseMotionListener(new mouselistner(bpanel));
		  videoSurface.addKeyListener(new keylistner());
		  videoSurface.setFocusable(true);
		 
		  
		  cpanel.add(videoSurface, BorderLayout.CENTER);
	     
	      
	      Box horizontalBox = Box.createHorizontalBox();
	      horizontalBox.add(Box.createHorizontalStrut(10));
	      bpanel.add(horizontalBox,"West");
	      
	      btnPlayorPause = new SButton();
	      btnPlayorPause.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent arg0) {
	            	//play or pause
	            	if(isPlaying) {
	            		if(actionPause()) {
	            		btnPlayorPause.setIcon(StaticResource.getIcon(StringResource.getColorString("PLAY")));
	            		isPlaying = false;
	            		}
	            	}else {
	            		
	            		if(actionPlay()) {
	            		btnPlayorPause.setIcon(StaticResource.getIcon(StringResource.getColorString("PAUSE")));
	            		isPlaying = true;
	            		}
	            	}
	            }
	            });
	      btnPlayorPause.setPreferredSize(new Dimension(50, 50));
	      btnPlayorPause.setIcon(StaticResource.getIcon(StringResource.getColorString("PAUSE")));
	      btnPlayorPause.setBorderPainted(false);
	      btnPlayorPause.setBackGround(StringResource.getColor("TITLE_BG_COLOR"));
	      btnPlayorPause.setHover(StringResource.getColor("SELECTION"));
	      btnPlayorPause.setPressedColor(StringResource.getColor("PRESSED"));
	      horizontalBox.add(btnPlayorPause);
	      
	      SButton btnStop = new SButton();
	      btnStop.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent arg0) {
	            	//pre
	            	if(mediaPlayer.isPlaying()) {
	            		mediaPlayer.stop();
	            		btnPlayorPause.setIcon(StaticResource.getIcon(StringResource.getColorString("PLAY")));
	            	}
	            }
	            });
	      btnStop.setPreferredSize(new Dimension(50, 50));
	      btnStop.setIcon(StaticResource.getIcon(StringResource.getColorString("STOP")));
	      btnStop.setBorderPainted(false);
	      btnStop.setBackGround(StringResource.getColor("TITLE_BG_COLOR"));
	      btnStop.setHover(StringResource.getColor("SELECTION"));
	      btnStop.setPressedColor(StringResource.getColor("PRESSED"));
	      horizontalBox.add(btnStop);
	      
	      SButton btnPre = new SButton();
	      btnPre.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent arg0) {
	            	//Previous Ep
	            	actionPreEpisode(an_con);
	            }
	            });
	      btnPre.setPreferredSize(new Dimension(50, 50));
	      btnPre.setIcon(StaticResource.getIcon(StringResource.getColorString("PREVIOUS")));
	      btnPre.setBorderPainted(false);
	      btnPre.setBackGround(StringResource.getColor("TITLE_BG_COLOR"));
	      btnPre.setHover(StringResource.getColor("SELECTION"));
	      btnPre.setPressedColor(StringResource.getColor("PRESSED"));
	      horizontalBox.add(btnPre);
	      
	      SButton btnNext = new SButton();
	      btnNext.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent arg0) {
	            	//Next Action
	            	actionNextEpisode(an_con);
	            }
	            });
	      btnNext.setPreferredSize(new Dimension(50, 50));
	      btnNext.setIcon(StaticResource.getIcon(StringResource.getColorString("NEXT")));
	      btnNext.setBorderPainted(false);
	      btnNext.setBackGround(StringResource.getColor("TITLE_BG_COLOR"));
	      btnNext.setHover(StringResource.getColor("SELECTION"));
	      btnNext.setPressedColor(StringResource.getColor("PRESSED"));
	      horizontalBox.add(btnNext);
	      	      
	      Box hboxseekbar = Box.createHorizontalBox();
	      seekslider = new SSlider();
	      seekslider.setName("seek");
	      seekslider.setMinimum(0);
	      seekslider.setMaximum(1000);
	      seekslider.setValue(0);
	      seekslider.setBackground(StringResource.getColor("TITLE_BG_COLOR"));
	      seekslider.addChangeListener(new SliderChangeListener());
	      
	      
	      JLabel currenttime = new JLabel("--:--:--");
	      currenttime.setFont(StaticResource.materialFontSmallL);
	      currenttime.setForeground(StringResource.getColor("TEXT_COLOR"));
	      JLabel duration = new JLabel("--:--:--");
	      duration.setFont(StaticResource.materialFontSmallL);
	      duration.setForeground(StringResource.getColor("TEXT_COLOR"));
	      hboxseekbar.add(currenttime);
	      hboxseekbar.add(seekslider);
	      hboxseekbar.add(duration);
	      bpanel.add(hboxseekbar,"North");
	      
	      Box hboxvolume = Box.createHorizontalBox();
	      volslider = new SSlider();
	      volslider.setName("vol");
	      volslider.setBackground(StringResource.getColor("TITLE_BG_COLOR"));
	      volslider.addChangeListener(new SliderChangeListener());
	     // volslider.setValue(30);
	      volslider.setBounds(0, 0, 50, 10);
	     
	      
	      btnMute = new SButton();
	      btnMute.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent arg0) {
	            	if(isMute) {
	            	volslider.setValue(lastMutevalue);
	            	isMute=false;
	            	
	            	}else {
	            		lastMutevalue = volslider.getValue();
	            		volslider.setValue(0);
	            		isMute = true;
	            		
	            	}
	            	
	            }
	            });
	      btnMute.setPreferredSize(new Dimension(30, 30));
	    
	      btnMute.setIcon(StaticResource.getIcon(StringResource.getColorString("AUDIO_66%")));
	      btnMute.setBorderPainted(false);
	      btnMute.setBackGround(StringResource.getColor("TITLE_BG_COLOR"));
	      btnMute.setHover(StringResource.getColor("SELECTION"));
	      btnMute.setPressedColor(StringResource.getColor("PRESSED"));
	      
	      hboxvolume.add(btnMute);
	      hboxvolume.add(volslider);
	      bpanel.add(hboxvolume,"East");
	      
	      	      
	     // /*
	      mediaPlayer = createPlayer(vlcArgs, videoSurface);
	      mediaPlayer.setFullScreenStrategy(new fullscreenlistener(this,this.getTitlePanel(),bpanel));
	      mediaPlayer.addMediaPlayerEventListener(new mediaplayerlistener(btnPlayorPause, seekslider, currenttime, duration));
	      //*/
	      this.setVisible(true);
	}
	public void iscur_EpUpdated(boolean c) {
		this.isCur_EpUpdated = c;
	}
	public void setCur_EP(int c) {
		this.cur_ep = c;
	}
	public void setPlayList(int cur_ep, String[] s, String[] no, int c) {
		this.cur_ep = cur_ep;
		this.cases = c;
		epPlaylist = s;
		epPlaylistno = no;
		this.qlty = mainwindow.qlty;
		this.subdub = mainwindow.subdubType;
		this.animename = mainwindow.dwnanimename;
		this.animeurl = mainwindow.curanimeurl;
		this.isOffAnimePanel = mainwindow.isOffAnimePanel;
	}
	
	public void set_newurl(String url1, String title) {
		url = url1;
		name=title;
	}
	public void set_mediaOption(String[] ref) {
		this.ref = ref;
	}
	
	public void play() {
		try {
			
		      if(mediaPlayer.isPlaying()) {
		    	  
		    	  mediaPlayer.stop();
		      }
		      //Utils.l("VLCPlayer_Play", "Starting Anime: " + name,true);
		      if(ref!=null) {
		    	  Utils.l(TAG,"ref: " + ref[0],true);
		    	  mediaPlayer.playMedia(url,this.ref);
		      }else {
		    	  Utils.l(TAG,"not ref: " + ref,true);
		    	  mediaPlayer.playMedia(url);
		      }
			 
			  playingLbl.setText(StringResource.getString("NOW_PLAYING") + name);
			  MainWindow.getmainwindow().setstatus_loggger(name);
			  //mediaPlayer.setVolume(100);
			 
			  isPlaying = true;
		      
				//Thread.currentThread().join();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				 isPlaying = false;
				e1.printStackTrace();
			}
	}
	
	private boolean actionPreEpisode(animeconfig an_con){
    	//pre	 
    	int c = cur_ep-1;
    	isCur_EpUpdated = false;
    	if(mainwindow.isdownall) {
    		SPopup.ShowPopUpInfo(vlcplayer, StringResource.getString("DWN_ALL_ENABLE"));
    		return false;
    	}
    	if(c<0 || c>=epPlaylist.length-1) {
    		SPopup.ShowPopUpInfo(vlcplayer, StringResource.getString("NO_EP"));
    		return false;
    	}
    	Utils.l(TAG, "Epnumber: " + c+" URL: " + epPlaylist[c],true);
    	if(mediaPlayer.isPlaying()) {
    		mediaPlayer.stop();
    	}
    	mainwindow.isPlayListUpdated=false;
    	an_con.isStreamEnable=true;
    	mainwindow.qlty=qlty;
    	mainwindow.subdubType=subdub;
    	mainwindow.dwnanimename = animename;
    	mainwindow.animeurldownload = animeurl;
    	Utils.l(TAG, "Source: " + cases,true);
    	Utils.l(TAG, "mainwindow.qlty: " + mainwindow.qlty,true);
    	Utils.l(TAG, "mainwindow.subdubType: " + mainwindow.subdubType,true);
    	Utils.i(TAG,"Playing previous episode - " + epPlaylistno[c], true);
    	if(isOffAnimePanel){
    		mainwindow.startOfflineVideo(epPlaylistno[c], epPlaylist[c],c);
    	}else{
    		Fetcher.getFetcher(an_con).downloadhelper(epPlaylist[c] +  ";" + epPlaylistno[c] , cases, mainwindow, false);	
    		if(isCur_EpUpdated) {
            	cur_ep=c;
            }
    	}
    	return false;
	}
	
	private boolean actionNextEpisode(animeconfig an_con){
    	//nxt
    	int c = cur_ep+1;
    	isCur_EpUpdated = false;
    	if(mainwindow.isdownall) {
    		SPopup.ShowPopUpInfo(vlcplayer, StringResource.getString("NO_EP"));
    		return false;
    	}
    	if(cur_ep<0 || cur_ep>=epPlaylist.length-1) {
    		SPopup.ShowPopUpInfo(vlcplayer, StringResource.getString("NO_EP"));
    		return false;
    	}
    	Utils.l(TAG, "Epnumber: " + c+" URL: " + epPlaylist[c],true);
    	if(mediaPlayer.isPlaying()) {
    		mediaPlayer.stop();
    	}
    	an_con.isStreamEnable=true;
    	mainwindow.isPlayListUpdated=false;
    	mainwindow.qlty=qlty;
    	mainwindow.subdubType=subdub;
    	mainwindow.dwnanimename = animename;
    	mainwindow.animeurldownload = animeurl;
    	Utils.l(TAG, "Source: " + cases,true);
    	Utils.l(TAG, "mainwindow.qlty: " + mainwindow.qlty,true);
    	Utils.l(TAG, "mainwindow.subdubType: " + mainwindow.subdubType,true);
    	Utils.i(TAG,"Playing Next episode - " + epPlaylistno[c], true);
    	if(isOffAnimePanel){
    		mainwindow.startOfflineVideo(epPlaylistno[c], epPlaylist[c],c);
    	}else{
    		Fetcher.getFetcher(an_con).downloadhelper(epPlaylist[c] + ";" + epPlaylistno[c], cases, mainwindow, false);	
    		if(isCur_EpUpdated) {
            	cur_ep=c;
            }
    	}
    	return true;
	}
	
	public class keylistner implements KeyListener {
		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			
			if (e.getKeyCode()==KeyEvent.VK_UP){
		
				volslider.setValue(volslider.getValue()+5);
		    }
			if (e.getKeyCode()==KeyEvent.VK_DOWN){
	
				volslider.setValue(volslider.getValue()-5);
		    }
			if (e.getKeyCode()==KeyEvent.VK_LEFT){
		
				seekslider.setValue(seekslider.getValue()-3);
		    }
			if (e.getKeyCode()==KeyEvent.VK_RIGHT){
				
				seekslider.setValue(seekslider.getValue()+3);
		    }
			if(e.getKeyCode()==KeyEvent.VK_SPACE) {
				if(isPlaying) {
	        		if(actionPause()) {
	        		btnPlayorPause.setIcon(StaticResource.getIcon(StringResource.getColorString("PLAY")));
	        		isPlaying = false;
	        		}
	        	}else {
	        		if(actionPlay()) {
	        		btnPlayorPause.setIcon(StaticResource.getIcon(StringResource.getColorString("PAUSE")));
	        		isPlaying = true;
	        		}
	        	}
			}
			if(e.getKeyCode()==KeyEvent.VK_F) {
				mediaPlayerComponent.getMediaPlayer().toggleFullScreen();
			}
			if(e.getKeyCode()==KeyEvent.VK_ESCAPE) {
				if(mediaPlayerComponent.getMediaPlayer().isFullScreen()) {
				mediaPlayerComponent.getMediaPlayer().toggleFullScreen();
				}
			}
			if(e.getKeyCode()==KeyEvent.VK_M) {
				if(isMute) {
	            	volslider.setValue(lastMutevalue);
	            	isMute=false;
	            	
	            	}else {
	            		lastMutevalue = volslider.getValue();
	            		volslider.setValue(0);
	            		isMute = true;
	            		
	            	}
			}
			if(e.getKeyCode()==KeyEvent.VK_N){
				if(e.isShiftDown()){
					Utils.l(TAG,"Play Next",true);
					actionNextEpisode(an_con);//an_con is not require as fetcher is already created.
				}
			}
			if(e.getKeyCode()==KeyEvent.VK_P){
				if(e.isShiftDown()){
					Utils.l(TAG,"Play Next",true);
					actionPreEpisode(an_con);//an_con is not require as fetcher is already created.
				}
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
	}

	public class mouselistner implements MouseMotionListener{
		JPanel bottompanel;
		
		mouselistner(JPanel bpanel){
			bottompanel = bpanel;
			
		}
		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub
			//l.d("Moused moved");
			videoSurface.setFocusable(true);
			if(isFullScreen && !oldmoment) {
			bottompanel.setVisible(true);
			
			oldmoment = true;
			new java.util.Timer().schedule( 
			        new java.util.TimerTask() {
			            @Override
			            public void run() {
			                // your code here
			            	if(isFullScreen) {
			            	bottompanel.setVisible(false);}
			            	oldmoment = false;
			            }
			        }, 
			        2000 
			);
			
			}
		}
		
	}
	
	public class mediaplayerlistener extends MediaPlayerEventAdapter{
		JButton playorpause;
		SSlider seekbarslider;
		JLabel newtime, timeduration;
		public mediaplayerlistener(JButton button, SSlider seekbar, JLabel time, JLabel duration) {
			playorpause = button;
			seekbarslider = seekbar;
			newtime=time;
			timeduration=duration;
		}
		@Override
	    public void buffering( MediaPlayer mediaPlayer, float newCache) {
			SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                	Utils.l("VLCPlayer","Buffering", false);
                	Utils.l("VLCPlayer","Cached: " + newCache, false);
        			newtime.setText(Math.round(newCache) + "%");
                }
            });
			
	    }
		@Override
	    public void playing(MediaPlayer mediaPlayer) {
			SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                	
        			isPlaying = true;
        			playorpause.setIcon(StaticResource.getIcon(StringResource.getColorString("PAUSE")));
                }
            });
			
	    }
	    @Override
	    public void finished(MediaPlayer mediaPlayer) {
	    	SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                	Utils.i("VLCPlayer","Finished Playing.",true);
                	if(!actionNextEpisode(an_con)){
                		isPlaying = false;
            	    	playorpause.setIcon(StaticResource.getIcon(StringResource.getColorString("PLAY")));	
                	}
                }
            });
	    }
	    @Override
	    public void error(MediaPlayer mediaPlayer) {
	    	SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                	Utils.e("VLCPlayer","Error while loading video",true);
                	
                	SAlertDialog.showAlertInfo(StringResource.getString("ERROR"), StringResource.getString("ERROR_VIDEO_LOADING"), vlcplayer);
                	
                	
                }
            });
	    	    
	    }
	    @Override
        public void lengthChanged(MediaPlayer mediaPlayer, long newLength) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                   //length
                	
                	timeduration.setText(formatTime(newLength));
                }
            });
        }
	    @Override
        public void timeChanged(MediaPlayer mediaPlayer, long newTime) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    //positionPane.setTime(newTime);
                    //statusBar.setTime(newTime);
                	//l.d("timechanged: " + newTime);
                	newtime.setText(formatTime(newTime));
                	//l.d("MediaPlayer POsition: " + mediaPlayerComponent.getMediaPlayer().getPosition());
                	//l.d("MediaPlayer POsition *100: " + mediaPlayerComponent.getMediaPlayer().getPosition()*1000);
                	if(!isSliderchanging) {
                		isPositionChanging = true;
                		float val1 =  mediaPlayerComponent.getMediaPlayer().getPosition() * 1000;
                		int val = (int)  val1;
                		
                	seekbarslider.setValue(val);
                
                	isPositionChanging = false;
                	}
                }
            });
        }
	}
	
	
	
	public static String formatTime(long value) {
        value /= 1000;
        int hours = (int) value / 3600;
        int remainder = (int) value - hours * 3600;
        int minutes = remainder / 60;
        remainder = remainder - minutes * 60;
        int seconds = remainder;
        return String.format("%d:%02d:%02d", hours, minutes, seconds);
    }
	
	public class fullscreenlistener extends DefaultAdaptiveRuntimeFullScreenStrategy {
		JPanel titlepanel;
		JPanel bottompanel;
		
		public fullscreenlistener(Window window,JPanel jPanel, JPanel bpanel) {
			super(window);
			titlepanel = jPanel;
			bottompanel = bpanel;
			// TODO Auto-generated constructor stub
		}
			@Override
	        protected void beforeEnterFullScreen() {
	            
	            isFullScreen=true;
	            titlepanel.setVisible(false);
	            bottompanel.setVisible(false);
	        }

	        @Override
	        protected void afterExitFullScreen() {
	        	
	        	 isFullScreen=false;
	        	titlepanel.setVisible(true);
	            bottompanel.setVisible(true);
	        }
		
	}
	
	private boolean setvolicon(int value, JButton mute) {
		if(value==0) {
			mute.setIcon(StaticResource.getIcon(StringResource.getColorString("MUTE")));
		}else if(value>0 && value< 34) {
			mute.setIcon(StaticResource.getIcon(StringResource.getColorString("AUDIO_33%")));
		}else if (value>33 & value <67) {
			mute.setIcon(StaticResource.getIcon(StringResource.getColorString("AUDIO_66%")));
		}else {
			mute.setIcon(StaticResource.getIcon(StringResource.getColorString("AUDIO_100%")));
		}
		
		return true;
	}
	private boolean actionPlay() {
		try {
			mediaPlayerComponent.getMediaPlayer().play();
			return true;
		}catch(Exception e) {
			Utils.e("VLCPlayer", "Error while playing", true);
			SAlertDialog.showAlertInfo(StringResource.getString("ERROR"), StringResource.getString("ERROR_PLAYING"), vlcplayer);
			return false;
		}
	}
	private boolean actionPause() {
		try {
			mediaPlayerComponent.getMediaPlayer().pause();
			return true;
		}catch(Exception e) {
			Utils.e("VLCPlayer", "Error while pauseing", true);
			SAlertDialog.showAlertInfo(StringResource.getString("ERROR"), StringResource.getString("ERROR_PAUSE"), vlcplayer);
			return false;
		}
	}
	
	public class SliderChangeListener implements ChangeListener {
		
		@Override
		public void stateChanged(ChangeEvent changeEvent) { 
	      Object source = changeEvent.getSource();
	      SSlider theJSlider = (SSlider)source;
	      if (theJSlider.getValueIsAdjusting()){
	    	  isSliderchanging = true;
	      }else {
	      switch(theJSlider.getName()) {
	      case "seek":
	    	  if(!isPositionChanging) {
	    	  if (theJSlider.getValue() / 1000 <= 1) {
	    		  mediaPlayerComponent.getMediaPlayer().setPosition((float) theJSlider.getValue() / 1000);
	    		  isSliderchanging = false;
	    	  }
	    	  
	    	  }
	    	  break;
	      case "vol":
	    	  isSliderchanging = false;
	    	  int vol = (200 * theJSlider.getValue())/100;
	    	 
	    	  setvolicon(theJSlider.getValue(), btnMute);
	    	  
	    	  mediaPlayerComponent.getMediaPlayer().setVolume(vol);
	    	  break;
	      }        
	     } 
	    }		
	  } 
	
	
	
	//Loading Libs
	public static void loadVLClib(String libpath) {
		NativeLibrary.addSearchPath( RuntimeUtil.getLibVlcLibraryName(), libpath); 
		NativeLibrary.addSearchPath( RuntimeUtil.getLibVlcCoreName(), libpath); 
		NativeLibrary.addSearchPath( RuntimeUtil.getPluginsDirectoryName(), libpath); 
		Utils.l("StaticResources", RuntimeUtil.getLibVlcLibraryName(), true);
		try {
		Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);
		
		LibXUtil.initialise(); 
		}catch(Exception e) {
		Utils.l("VLCPlayer","Unable to Libs",true);
		return;
		}
		Utils.i("StaticResources", "version: {}" + LibVlc.INSTANCE.libvlc_get_version(),true);
		Utils.i("StaticResources", " compiler: {}" + LibVlc.INSTANCE.libvlc_get_compiler(),true);
		Utils.i("StaticResources", "changeset: {}" + LibVlc.INSTANCE.libvlc_get_changeset(),true);
		
	}

	//Building player
	private EmbeddedMediaPlayer createPlayer(final List<String> vlcArgs, final Canvas videoSurface) {
		 mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
		 
		EmbeddedMediaPlayer embeddedMediaPlayer = mediaPlayerComponent.getMediaPlayer();
		MediaPlayerFactory mediaPlayerFactory = new MediaPlayerFactory(vlcArgs.toArray(new String[vlcArgs.size()]));
		
		mediaPlayerFactory.setUserAgent("vlcj test player");
		embeddedMediaPlayer.setVideoSurface(mediaPlayerFactory.newVideoSurface(videoSurface)); 
		embeddedMediaPlayer.setPlaySubItems(true);
		return embeddedMediaPlayer; 
	}
	
	/** * Configure VLC parameters */ 
	private void configureParameters(final List<String> vlcArgs) {
		vlcArgs.add("--no-plugins-cache");
		vlcArgs.add("--no-video-title-show");
		vlcArgs.add("--no-snapshot-preview");
		// Important, if this parameter would not be set on Windows, the app won't work
		if (RuntimeUtil.isWindows()) { 
			vlcArgs.add("--plugin-path=G:\\Other\\Program\\VLCPortable\\App\\vlc\\win32-amd64\\plugins");
			}
		}

}

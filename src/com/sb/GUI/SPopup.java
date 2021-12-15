package com.sb.GUI;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

import com.sb.an_dl.MainWindow;
import com.sb.an_dl.StaticResource;
import com.sb.an_dl.VLCPlayer;
import com.sb.downport.StringResource;

public class SPopup {
	public static Popup pLoading;
	public static void ShowPopUpInfo(MainWindow m, String msg) {
		int x = m.getX();
		int y = m.getY();
		int h = m.getHeight()-120;
		int msg_len = msg.length()/2;
		int w = m.getWidth()/2 - msg_len;
		int f_x = x+w;
		int f_y = y+h;
		Border border = BorderFactory.createLineBorder(StringResource.getColor("BORDER"), 1);
		PopupFactory pf = new PopupFactory(); 
		JPanel l = new JPanel();
		l.setBorder(border);
		JLabel a = new JLabel(msg);
		a.setFont(StaticResource.materialFontSmallL);
		a.setForeground(StringResource.getColor("TEXT_COLOR"));
		l.setBackground(StringResource.getColor("SEARCH_BG"));
		l.add(a);
		Popup p;
		p = pf.getPopup(m, l,f_x , f_y);
		p.show();
		Thread t = new Thread(){
	          public void run(){
	        	 try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            SwingUtilities.invokeLater(new Runnable(){//do swing work on EDT
	              public void run(){
	            	  p.hide();
	            	 //Second runner
	              //d.dispose();
	              }
	            });
	          }
	        };	
	        t.start();
	}
	public static void ShowPopUpInfo(VLCPlayer m, String msg) {
		int x = m.getX();
		int y = m.getY();
		int h = m.getHeight()-120;
		int msg_len = msg.length()/2;
		int w = m.getWidth()/2 - msg_len;
		int f_x = x+w;
		int f_y = y+h;
		Border border = BorderFactory.createLineBorder(StringResource.getColor("BORDER"), 1);
		PopupFactory pf = new PopupFactory(); 
		JPanel l = new JPanel();
		l.setBorder(border);
		JLabel a = new JLabel(msg);
		a.setFont(StaticResource.materialFontSmallL);
		a.setForeground(StringResource.getColor("TEXT_COLOR"));
		l.setBackground(StringResource.getColor("SEARCH_BG"));
		l.add(a);
		Popup p;
		p = pf.getPopup(m, l,f_x , f_y);
		p.show();
		Thread t = new Thread(){
	          public void run(){
	        	 try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            SwingUtilities.invokeLater(new Runnable(){//do swing work on EDT
	              public void run(){
	            	  p.hide();
	            	 //Second runner
	              //d.dispose();
	              }
	            });
	          }
	        };	
	        t.start();
	}
	
	public static void ShowPopUpLoading(MainWindow m, String msg) {
		int x = m.getX();
		int y = m.getY();
		int h = m.getHeight()-120;
		int msg_len = msg.length()/2;
		int w = m.getWidth()/2 - msg_len;
		int f_x = x+w;
		int f_y = y+h;
		Border border = BorderFactory.createLineBorder(StringResource.getColor("BORDER"), 1);
		PopupFactory pf = new PopupFactory(); 
		JPanel l = new JPanel();
		l.setBorder(border);
		JLabel a = new JLabel(msg);
		a.setFont(StaticResource.materialFontSmallL);
		a.setForeground(StringResource.getColor("TEXT_COLOR"));
		l.setBackground(StringResource.getColor("SEARCH_BG"));
		l.add(a);
		
		pLoading = pf.getPopup(m,l,f_x , f_y);
		pLoading.show();
		Thread t = new Thread(){
	          public void run(){
	        	 try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            SwingUtilities.invokeLater(new Runnable(){//do swing work on EDT
	              public void run(){
	            	 // p.hide();
	            	 //Second runner
	              //d.dispose();
	              }
	            });
	          }
	        };	
	        //t.start();
	}
}

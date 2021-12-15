package com.sb.GUI;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollBar;
import javax.swing.plaf.basic.BasicScrollBarUI;

import com.sb.an_dl.StaticResource;
import com.sb.an_dl.Utils;
import com.sb.downport.StringResource;

public class SScrollBar extends JScrollBar {
	private boolean isThumbPressed;
	private static final String TAG = "SScrollBar";
	public SScrollBar(final int orientation, final Image thumb, final Image thumbPressed, final Image track, final Image imageThumbRoll) {
		
		 super (orientation);
		 setUnitIncrement(25);
	        addMouseListener (new MouseAdapter () {
	            public void mousePressed (MouseEvent e) {
	                isThumbPressed = true;
	            }
	            public void mouseReleased (MouseEvent e) {
	                isThumbPressed = false;
	            }
	        });
	        setUI (new BasicScrollBarUI () {
	            @Override
				protected JButton createDecreaseButton(int orientation) {
					// TODO Auto-generated method stub
	            	
	            	SButton up = new SButton();
	            	if(orientation==7){
	            		
	            		up.setIcon(StaticResource.getIcon(StringResource.getColorString("LEFT_SCROLL")));
	            	}else{
	            		
	            		up.setIcon(StaticResource.getIcon(StringResource.getColorString("UP_SCROLL")));
	            	}
	            	up.setBorderPainted(false);
	            	up.setBackGround(StringResource.getColor("SEARCH_BG"));
	            	up.setHover(StringResource.getColor("SELECTION"));
	            	up.setPressedColor(StringResource.getColor("PRESSED"));
					return up;
				}
				@Override
				protected JButton createIncreaseButton(int orientation) {
					// TODO Auto-generated method stub
	            	SButton up = new SButton();
	            	if(orientation==3){
	            		up.setIcon(StaticResource.getIcon(StringResource.getColorString("RIGHT_SCROLL")));
	            	}else{
	            		
	            		up.setIcon(StaticResource.getIcon(StringResource.getColorString("DOWN_SCROLL")));
	            	}
	            	
	            	up.setBorderPainted(false);
	            	up.setBackGround(StringResource.getColor("SEARCH_BG"));
	            	up.setHover(StringResource.getColor("SELECTION"));
	            	up.setPressedColor(StringResource.getColor("PRESSED"));
					return up;
				}
				@Override protected void paintThumb (Graphics g, JComponent c, Rectangle r) {
	   
	                if (isThumbPressed) g.drawImage (thumbPressed, r.x, r.y, r.width, r.height, null);
	                else if (isThumbRollover()) g.drawImage (imageThumbRoll, r.x, r.y, r.width, r.height, null);
	                else g.drawImage (thumb, r.x, r.y, r.width, r.height, null);
	            }
	            @Override protected void paintTrack (Graphics g, JComponent c, Rectangle r) {
	                g.drawImage(track, r.x, r.y, r.width, r.height, null);
	            }
	                   	
	        	
	        });
	}
}

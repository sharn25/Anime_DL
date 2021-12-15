package com.sb.GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.Popup;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;

import com.sb.an_dl.StaticResource;
import com.sb.downport.StringResource;

public class SComboBox extends JComboBox {
	private Color Hover;
	private Color Pressed;
	private Color Background;
	
	public SComboBox() {
		super();
		init();
	}
	
	private void init() {
		Hover = new Color(51, 181, 229);
		 Background = Color.white;
		 Pressed = Color.white;
		 setUI(new BasicComboBoxUI() {

			@Override
			protected JButton createArrowButton() {
				// TODO Auto-generated method stub
				SButton up = new SButton();
            	up.setIcon(StaticResource.getIcon(StringResource.getColorString("DOWN_SCROLL")));
            	up.setBorderPainted(false);
            	up.setBackGround(StringResource.getColor("SEARCH_BG"));
            	up.setHover(StringResource.getColor("SELECTION"));
            	up.setPressedColor(StringResource.getColor("PRESSED"));
				return up;
			}

			@Override
			protected ComboPopup createPopup() {
				// TODO Auto-generated method stub
				return new BasicComboPopup(comboBox) {

					@Override
					protected JList createList() {
						// TODO Auto-generated method stub
						SList i = new SList<>();
						i.setFont(StaticResource.materialFontSmallL);
				        i.setModel(comboBox.getModel());
				        
				        i.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
						return i;//super.createList()
					}
					
				};
				//ComboPopup p = super.createPopup();
				
				//return p;
			}

			@Override
			public void paintCurrentValue(Graphics g, Rectangle bounds, boolean hasFocus) {
				// TODO Auto-generated method stub
				super.paintCurrentValue(g, bounds, hasFocus);
			}

			@Override
			public void paintCurrentValueBackground(Graphics g, Rectangle bounds, boolean hasFocus) {
				// TODO Auto-generated method stub
				super.paintCurrentValueBackground(g, bounds, hasFocus);
			}
			 
		 });
	}
	
	@Override
	  protected void paintComponent(java.awt.Graphics g)
	  {
		  if(hasFocus()) {
			  g.setColor(Pressed);
		  }else if(!hasFocus()) {
			  g.setColor(Hover);
		  }else {
			  g.setColor(Background);
		  }
		  g.fillRect(0, 0, getWidth(), getHeight());
	    super.paintComponent(g);
	  }

	public void setHover(Color hover) {
		Hover = hover;
	}
	

	public void setPressed(Color pressed) {
		Pressed = pressed;
	}

	public void setBackGround(Color background) {
		Background = background;
		setBackground(Background);
	}
	
}

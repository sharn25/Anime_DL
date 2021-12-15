package com.sb.GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JRadioButton;
import javax.swing.plaf.ButtonUI;
import javax.swing.plaf.basic.BasicRadioButtonUI;
import javax.swing.plaf.basic.BasicScrollBarUI;

import com.sb.an_dl.StaticResource;
import com.sb.downport.StringResource;

public class SRadio extends JRadioButton {
	private Color Hover;
	private Color Pressed;
	private Color Background;
	
	private final Color rangeColor = StringResource.getColor("TEXT_COLOR");
	private final Color bg = StringResource.getColor("SEARCH_BG");
	
	public SRadio() {
		super();
		
		init();
	}
	
	private Image createImage (Color c) {
        BufferedImage image = new BufferedImage (15, 15, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = (Graphics2D)image.getGraphics ();
        //Shape thumbShape2 = createThumbShape(12, 12);
	    Shape thumbShape = createThumbShape(12, 12);
	    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	        RenderingHints.VALUE_ANTIALIAS_ON);
	    g2d.translate(2, 1);
	   	g2d.setColor(c);
	    g2d.fill(thumbShape);
	   // g2d.translate(0, 0);
	    g2d.setColor(bg);
	    g2d.draw(thumbShape);
	    g2d.dispose();
        return image;
    }
	
	public SRadio(String s) {
		super(s);
		init();
	}
	
	private Shape createThumbShape(int width, int height) {
	    Ellipse2D shape = new Ellipse2D.Double(0, 0, width, height);
	    return shape;
	}
	
	private void init() {
		Hover = new Color(51, 181, 229);
		 Background = Color.white;
		 Pressed = Color.white;
		 setContentAreaFilled(false);
		 setIcon(new ImageIcon(this.createImage(bg)));
		 this.setSelectedIcon(new ImageIcon(this.createImage(rangeColor)));
		 this.setRolloverIcon(new ImageIcon(this.createImage(Hover)));
		 this.setRolloverSelectedIcon(new ImageIcon(this.createImage(Hover)));
		 this.setPressedIcon(new ImageIcon(this.createImage(Pressed)));
	}
	/*
	@Override
	  protected void paintComponent(java.awt.Graphics g)
	  {
		  if(getModel().isPressed()) {
			  g.setColor(Pressed);
		  }else if(getModel().isRollover()) {
			  g.setColor(Hover);
		  }else {
			  g.setColor(Background);
		  }
		  g.fillRect(0, 0, getWidth(), getHeight());
	
	    super.paintComponent(g);
	  }
	  */
	
	public Color getHover() {
		return Hover;
	}

	public void setHover(Color hover) {
		Hover = hover;
	}

	public Color getPressed() {
		return Pressed;
	}

	public void setPressed(Color pressed) {
		Pressed = pressed;
	}

	public Color getBackground() {
		return Background;
	}

	public void setBackground(Color background) {
		Background = background;
	}

	
}

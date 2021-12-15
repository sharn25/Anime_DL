package com.sb.GUI;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;

import com.sb.downport.StringResource;

public class SCheckBox extends JCheckBox {
	
	private Color IconColor, IconRollOver, IconSelected, IconPressed;
	
	public SCheckBox(String text){
		super(text);
		setOpaque(false);
		setForeground(StringResource.getColor("TEXT_COLOR"));
		IconColor = StringResource.getColor("SEARCH_BG");
		IconRollOver= StringResource.getColor("SELECTION");
		IconSelected = StringResource.getColor("TEXT_COLOR");
		IconPressed =  StringResource.getColor("PRESSED");
		init();
	}
	void init() {
		setIcon(new ImageIcon(this.createImage(IconColor)));
		 this.setSelectedIcon(new ImageIcon(this.createImage(IconSelected)));
		 this.setRolloverIcon(new ImageIcon(this.createImage(IconRollOver)));
		 this.setRolloverSelectedIcon(new ImageIcon(this.createImage(IconRollOver)));
		 this.setPressedIcon(new ImageIcon(this.createImage(IconPressed)));
	}
	
	private Image createImage (Color c) {
        BufferedImage image = new BufferedImage (12, 12, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = (Graphics2D)image.getGraphics ();
        //Shape thumbShape2 = createThumbShape(12, 12);
	    Shape thumbShape = createThumbShape(12, 12);
	    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	        RenderingHints.VALUE_ANTIALIAS_ON);
	    g2d.translate(2, 1);
	   	g2d.setColor(c);
	    g2d.fill(thumbShape);
	   // g2d.translate(0, 0);
	    g2d.setColor(IconColor);
	    g2d.draw(thumbShape);
	    g2d.dispose();
        return image;
    }
	
	private Shape createThumbShape(int width, int height) {
	    Rectangle2D shape = new Rectangle2D.Double(0, 0, width, height);
	    return shape;
	}
}

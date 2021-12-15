package com.sb.GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import com.sb.downport.StringResource;

public class SScrollPane extends JScrollPane {

	public SScrollPane(JComponent t){
		super (t,VERTICAL_SCROLLBAR_AS_NEEDED, HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
        Image imageThumb = createImage (16, 16, StringResource.getColor("BORDER"));
        Image imageThumbRoll = createImage(8, 8, StringResource.getColor("SELECTION"));
        Image imageThumbPressed = createImage (8, 8, StringResource.getColor("PRESSED"));
        Image imageTrack = createImage (8, 8, StringResource.getColor("SEARCH_BG"));
        setVerticalScrollBar(new SScrollBar (JScrollBar.VERTICAL, imageThumb, imageThumbPressed, imageTrack,imageThumbRoll));
        setHorizontalScrollBar(new SScrollBar (JScrollBar.HORIZONTAL, imageThumb, imageThumbPressed, imageTrack, imageThumbRoll));
	}
	private Image createImage (int width, int height, Color color) {
        BufferedImage image = new BufferedImage (width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics g = image.getGraphics ();
        g.setColor (color);
        g.fillRect (0, 0, width, height);
        g.dispose ();
        return image;
    }
}

package com.sb.GUI;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import com.sb.an_dl.StaticConstants;
import com.sb.downport.BConfig;

public class SButton extends JButton{
	float alpha = 1.0f;
	private Color Hover;
	private Color Pressed;
	private Color Background;

	public SButton() {
		super();
		init();
	    
	}
	
	public void init() {
		 setFocusPainted(false);
		 setBorderPainted(true);
		 this.setHorizontalTextPosition(SwingConstants.CENTER);
		 Hover = new Color(51, 181, 229);
		 Background = Color.white;
		 Pressed = Color.white;
		 setBackground(Background);
		 setContentAreaFilled(false);
		 addMouseListener(new ML());
		 
	}
	public void setHeight(int h) {
		Dimension d = this.getPreferredSize();
		d.height =h;
		this.setPreferredSize(d);
		this.setMinimumSize(d);
		this.setMaximumSize(d);
	}
	public void setWidth(int w) {
		Dimension d = this.getPreferredSize();
		d.width =w;
		this.setPreferredSize(d);
		this.setMinimumSize(d);
		this.setMaximumSize(d);
	}
	public void setHover(Color c) {
		Hover= c;
	}
	public Color getHover() {
		return Hover;
	}
	public void setPressedColor(Color c) {
		Pressed = c;
	}
	public Color getPressedColor() {
		return Pressed;
	}
	
	public void setFontColor(Color c) {
		this.setForeground(c);
	}
	public void setBackGround(Color c) {
		Background = c;
		setBackground(c);
	}
	public Color getBackGround() {
		return Background;
		
	}
	
	  public SButton(String text)
	  {
	    super(text);
	    init();
	  }
	  public void setBorder(Border b) {
		  super.setBorder(b);
	  }
	  public float getAlpha()
	  {
	    return alpha;
	  }
	 
	  public void setText(String s) {
		  super.setText(s);
	  }
	  public void setAlpha(float alpha)
	  {
	    this.alpha = alpha;
	    repaint();
	  }
	  
	  public void showHint(String s) {
		  if(BConfig.isShowHintEnabled) {
		  this.setToolTipText(s);
		  }
	  }
	  
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

	  public class ML extends MouseAdapter
	  {
	    @Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			super.mouseReleased(e);
			 
		}

		public void mouseExited(MouseEvent me)
	    {
	     // setBackground(Background);
	    }

	    public void mouseEntered(MouseEvent me)
	    {
	      //setBackground(Hover);
	    }

	    public void mousePressed(MouseEvent me)
	    {
	    	// setBackground(Hover);
	    }
	    
	    
	  }
}

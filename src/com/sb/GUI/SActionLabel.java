package com.sb.GUI;

import java.awt.Color;

import javax.swing.JLabel;

public class SActionLabel extends JLabel {
	private Color Hover;
	private Color Background;
	public SActionLabel() {
		super();
		init();
	    
	}
	void init(){
		
		Hover = Color.white;
	}
	

	public SActionLabel(String name) {
		super(name);
		init();
	    
	}
}

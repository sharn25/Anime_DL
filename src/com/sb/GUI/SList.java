package com.sb.GUI;

import javax.swing.JList;
import javax.swing.ListModel;

import com.sb.downport.StringResource;

public class SList<E> extends JList<E> {
	
	SList(){
		super();
	}
	public SList(Object[] objects){
		super();
		setSelectionBackground(StringResource.getColor("PRESSED"));
		setSelectionForeground(StringResource.getColor("TEXT_COLOR"));
		setBackground(StringResource.getColor("SEARCH_BG"));
	 	setForeground(StringResource.getColor("TEXT_COLOR"));
	}
}

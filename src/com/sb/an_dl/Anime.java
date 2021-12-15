package com.sb.an_dl;

import javax.swing.ImageIcon;

public class Anime {
	 	private String details;
	    private String name;
	    private String epnum;
	    private ImageIcon icon;
	    private String url;
	    
	 
	    public Anime(String name, String details, String epnum, ImageIcon icon, String url)
	    {
	        this.details = details;
	        this.name = name;
	        this.epnum = epnum;
	        this.icon = icon;
	        this.url = url;
	    }
	 
	    public String getdetails()
	    {
	        return details;
	    }
	 
	    public void setdetails(String details)
	    {
	        this.details = details;
	    }
	    
	    public String geturl()
	    {
	        return this.url;
	    }
	 
	    public void seturl(String url)
	    {
	        this.url = url;
	    }
	 
	    public String getName()
	    {
	        return name;
	    }
	 
	    public void setName(String name)
	    {
	        this.name = name;
	    }
	 
	    public String getepnum()
	    {
	        return epnum;
	    }
	 
	    public void setHourlyRate(String epnum)
	    {
	        this.epnum = epnum;
	    }
	 
	    public ImageIcon geticon()
	    {
	        return icon;
	    }
	 
	    public void seticon(ImageIcon icon)
	    {
	        this.icon = icon;
	    }
	 
}

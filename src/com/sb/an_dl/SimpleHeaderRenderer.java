package com.sb.an_dl;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;
import com.sb.an_dl.StaticResource;
import com.sb.downport.StringResource;
import com.sb.an_dl.Anime;
 
/**
 * A simple renderer class for JTable component.
 * @author www.codejava.net
 *
 */
public class SimpleHeaderRenderer extends JPanel implements TableCellRenderer {
	
	Box hbox;
	   JLabel iconLbl;
	   JLabel EpLbl;
	   JLabel titleLbl;
	   JLabel anDetialsLbl;
	   JLabel statLbl;
	   JLabel line;
	   JLabel detail;
	   private final static String TAG = "SimpleHeaderRenderer";
		   
    public SimpleHeaderRenderer() {
    	
       /* setFont(new Font("Consolas", Font.BOLD, 14));
        setForeground(Color.BLUE);
        setBorder(BorderFactory.createEtchedBorder());*/
    	super(new BorderLayout());
        this.setBorder(new EmptyBorder(0, 20, 0, 20));
        this.setBackground(StringResource.getColor("PANEL_BG_COLOR"));
        
        Color textcolor = StringResource.getColor("TEXT_COLOR");
        JPanel p = new JPanel(new BorderLayout());
        p.setBorder(new EmptyBorder(0, 0, 0, 0));
        p.setOpaque(false);
        Box vbox = Box.createVerticalBox();
        p.add(vbox,"Center");
        //vbox.setAlignmentY(LEFT_ALIGNMENT);
        this.hbox = Box.createHorizontalBox();
        
        this.iconLbl = new JLabel();
        Border border = BorderFactory.createLineBorder(textcolor, 1);
        this.iconLbl.setBorder(border);
        this.titleLbl = new JLabel("test title", 2);
        this.titleLbl.setFont(StaticResource.materialFontBigL);
        this.titleLbl.setForeground(textcolor);
        this.titleLbl.setBorder(new EmptyBorder(7, 10, 0, 0));
        vbox.add(this.titleLbl);
        EpLbl = new JLabel("Epidoesd 1");
        EpLbl.setBorder(new EmptyBorder(2,12,0,0));
        EpLbl.setFont(StaticResource.materialFontSoSmallLI);
        EpLbl.setForeground(textcolor);
        vbox.add(EpLbl);
        anDetialsLbl = new JLabel();
        anDetialsLbl.setFont(StaticResource.materialFontSmallL);
        anDetialsLbl.setForeground(textcolor);
        anDetialsLbl.setBorder(new EmptyBorder(5,10,0,0));
        vbox.add(anDetialsLbl);
        
        this.hbox.add(this.iconLbl);
        this.hbox.add(p);
      
        this.add(this.hbox,"Center");
        this.line = new JLabel();
        this.line.setBackground(new Color(222, 222, 222));
        this.line.setOpaque(true);
        this.line.setMinimumSize(new Dimension(10, 1));
        this.line.setMaximumSize(new Dimension(this.line.getMaximumSize().width, 1));
        this.line.setPreferredSize(new Dimension(this.line.getPreferredSize().width, 1));
        this.add(this.line, "South");
    }
     
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
        boolean isSelected, boolean hasFocus, int row, int column) {
    	final String M = "_getTableCell";
    	Anime animeitem = (Anime)value;
    	this.iconLbl.setIcon(animeitem.geticon());
    	this.titleLbl.setText(animeitem.getName());
    	this.anDetialsLbl.setText(animeitem.getdetails());
    	this.EpLbl.setText(animeitem.getepnum());
    	if(isSelected) {
            this.setBackground(StringResource.getColor("SELECTION"));
            this.line.setOpaque(false);
            Utils.l("SimpleHeaderRender", "Row Selected " + table.getSelectedRow(),false);
         } else {
            this.setBackground(StringResource.getColor("PANEL_BG_COLOR"));
            this.line.setOpaque(true);
            
         }
        return this;
    }
 
}
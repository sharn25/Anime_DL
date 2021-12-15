package com.sb.an_dl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;

import com.sb.an_dl.StaticResource;
import com.sb.downport.DownloadListItem;
import com.sb.downport.StringResource;

public class ItemRenderer extends JPanel implements TableCellRenderer {
   private static final long serialVersionUID = 8617303844602588189L;
   Box hbox;
   JLabel iconLbl;
   JLabel titleLbl;
   JLabel statLbl;
   JLabel dateLbl;
   JLabel line;
   JLabel speed;

   public ItemRenderer() {
      super(new BorderLayout());
      Color textcolor = StringResource.getColor("TEXT_COLOR");
      this.setBorder(new EmptyBorder(0, 20, 0, 20));
      this.setBackground(Color.WHITE);
      JPanel p = new JPanel(new BorderLayout());
      p.setBorder(new EmptyBorder(10, 10, 15, 0));
      p.setOpaque(false);
      this.hbox = Box.createHorizontalBox();
      this.iconLbl = new JLabel();
      this.iconLbl.setBorder(new EmptyBorder(10, 0, 10, 0));
      this.titleLbl = new JLabel("test title", 2);
      this.titleLbl.setForeground(textcolor);
      this.titleLbl.setFont(StaticResource.materialFontBigL);
      
      this.statLbl = new JLabel("status label for test status");
      this.statLbl.setForeground(textcolor);
      this.statLbl.setFont(StaticResource.materialFontSmallL);
      
      this.speed = new JLabel("speed label for test status");
      this.speed.setForeground(textcolor);
      this.speed.setFont(StaticResource.materialFontSmallL);
      
      this.dateLbl = new JLabel("Today");
      this.dateLbl.setForeground(textcolor);
      this.dateLbl.setHorizontalAlignment(4);
      this.dateLbl.setFont(StaticResource.materialFontSmallL);
      
      this.hbox.add(this.iconLbl);
      this.hbox.add(p);
      p.add(this.titleLbl);
      Box box = Box.createHorizontalBox();
      box.add(this.statLbl);
      box.add(Box.createHorizontalStrut(10));
      box.add(this.speed);
      box.add(Box.createHorizontalGlue());
      box.add(this.dateLbl);
      box.add(Box.createRigidArea(new Dimension(5, 5)));
      p.add(box, "South");
      this.add(this.hbox);
      this.line = new JLabel();
      this.line.setBackground(new Color(222, 222, 222));
      this.line.setOpaque(true);
      this.line.setMinimumSize(new Dimension(10, 1));
      this.line.setMaximumSize(new Dimension(this.line.getMaximumSize().width, 1));
      this.line.setPreferredSize(new Dimension(this.line.getPreferredSize().width, 1));
      this.add(this.line, "South");
   }

   public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
      DownloadListItem item = (DownloadListItem)value;
      this.iconLbl.setIcon(item.icon);
      this.titleLbl.setText(item.filename);
      String stat = "";
      if(item.q) {
         stat = "[Q] ";
      }

      this.statLbl.setText(stat + item.status);
      this.speed.setText("");
      

      if(item.state==10 || item.state ==20) {
    	  this.speed.setText("Network Speed: " + item.transferrate);
      }
      this.dateLbl.setText(item.dateadded);
      if(isSelected) {
         this.setBackground(StringResource.getColor("SELECTION"));
         this.line.setOpaque(false);
      } else {
         this.setBackground(StringResource.getColor("PANEL_BG_COLOR"));
         this.line.setOpaque(true);
         
      }

      return this;
   }
}

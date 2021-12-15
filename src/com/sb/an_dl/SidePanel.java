package com.sb.an_dl;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;

import com.sb.downport.StringResource;

public class SidePanel extends JPanel {
   private static final long serialVersionUID = 3821650643051584496L;
   Image imgBar = StaticResource.getIcon(StringResource.getColorString("NAV_BG")).getImage();

   public SidePanel() {
      this.setOpaque(false);
   }

   protected void paintComponent(Graphics g) {
      g.drawImage(this.imgBar, 0, 0, this.getWidth(), this.getHeight(), this);
      super.paintComponent(g);
   }
}

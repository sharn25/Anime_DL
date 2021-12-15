package com.sb.an_dl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowEvent;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.sb.GUI.SButton;
import com.sb.an_dl.StaticResource;
import com.sb.an_dl.TitlePanel;

public class ALFrame extends JFrame {
   private static final long serialVersionUID = -8094995420106046965L;
   private boolean maximizeBox = true;
   private boolean minimizeBox = true;
   JPanel panTitle;
   JPanel panClient;
   private JLabel lblRightGrip;
   private JLabel lblLeftGrip;
   private JLabel lblTopGrip;
   private JLabel lblBottomGrip;
   int diffx;
   int diffy;
   Box vBox;
   JPanel panContent;
   
   public ActionListener actClose = new ActionListener() {
      public void actionPerformed(ActionEvent action) {
    	 
         ALFrame.this.dispatchEvent(new WindowEvent(ALFrame.this, 201));
      }
   };
   ActionListener actMax = new ActionListener() {
      public void actionPerformed(ActionEvent action) {
    	
         ALFrame.this.setMaximizedBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());
         ALFrame.this.setExtendedState((ALFrame.this.getExtendedState() & 6) == 6?0:6);
      }
   };
   ActionListener actMin = new ActionListener() {
      public void actionPerformed(ActionEvent action) {
         ALFrame.this.setExtendedState(ALFrame.this.getExtendedState() | 1);
      }
   };
   Cursor curDefault;
   Cursor curNResize;
   Cursor curEResize;
   Cursor curWResize;
   Cursor curSResize;
   Cursor curSEResize;
   Cursor curSWResize;

   public ALFrame() {
	  
	  Utils.l("ALFrame","ALFrame",false);
      this.setUndecorated(true);
      this.createCursors();
      this.createResizeGrip();
      this.setBackground(StaticResource.whiteColor);
      this.panTitle = new TitlePanel(new BorderLayout(), this);
      this.panTitle.setBorder(new EmptyBorder(0, 0, 0, 0));
      this.panTitle.setOpaque(true);
      this.registerTitlePanel(this.panTitle);
      this.panClient = new JPanel(new BorderLayout());
      panContent = new JPanel(new BorderLayout());
      panContent.add(this.panTitle, "North");
      panContent.add(this.panClient, "Center");
      
      super.add(panContent);
   }
   
   
   public JPanel getTitlePanel() {
      return this.panTitle;
   }
   
   public int getPanelHeight() {
	return this.getHeight();
	   
   }
   
   public JPanel getpanClient() {
	   return panClient;
   }
   
   public JPanel getpanContent() {
	   return this.panContent;
   }

   public void setMaximizeBox(boolean maximizeBox) {
      this.maximizeBox = maximizeBox;
   }

   public boolean isMaximizeBox() {
      return this.maximizeBox;
   }

   public void setMinimizeBox(boolean minimizeBox) {
      this.minimizeBox = minimizeBox;
   }

   public boolean isMinimizeBox() {
      return this.minimizeBox;
   }

   public Component add(Component c) {
      return this.panClient.add(c);
   }

   private void createResizeGrip() {
	   
      ALFrame.GripMouseAdapter gma = new ALFrame.GripMouseAdapter();
      this.lblRightGrip = new JLabel();
      this.lblRightGrip.setMaximumSize(new Dimension(1, this.lblRightGrip.getMaximumSize().height));
      this.lblRightGrip.setPreferredSize(new Dimension(1, this.lblRightGrip.getPreferredSize().height));
      this.lblRightGrip.setBackground(Color.BLACK);
      this.lblRightGrip.setOpaque(true);
      super.add(this.lblRightGrip, "East");
      this.lblBottomGrip = new JLabel();
      this.lblBottomGrip.setMaximumSize(new Dimension(this.lblBottomGrip.getPreferredSize().width, 1));
      this.lblBottomGrip.setPreferredSize(new Dimension(this.lblBottomGrip.getPreferredSize().width, 1));
      this.lblBottomGrip.setBackground(Color.BLACK);
      this.lblBottomGrip.setOpaque(true);
      super.add(this.lblBottomGrip, "South");
      this.lblLeftGrip = new JLabel();
      this.lblLeftGrip.setMaximumSize(new Dimension(1, this.lblLeftGrip.getPreferredSize().height));
      this.lblLeftGrip.setPreferredSize(new Dimension(1, this.lblLeftGrip.getPreferredSize().height));
      this.lblLeftGrip.setBackground(Color.BLACK);
      this.lblLeftGrip.setOpaque(true);
      super.add(this.lblLeftGrip, "West");
      this.lblTopGrip = new JLabel();
      this.lblTopGrip.setMaximumSize(new Dimension(this.lblTopGrip.getPreferredSize().width, 1));
      this.lblTopGrip.setPreferredSize(new Dimension(this.lblTopGrip.getPreferredSize().width, 1));
      this.lblTopGrip.setBackground(Color.BLACK);
      this.lblTopGrip.setOpaque(true);
      super.add(this.lblTopGrip, "North");
      if(this.isResizable()) {
         this.lblTopGrip.addMouseListener(gma);
         this.lblTopGrip.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent me) {
               int y = me.getYOnScreen();
               int diff = ALFrame.this.getLocationOnScreen().y - y;
               ALFrame.this.setLocation(ALFrame.this.getLocation().x, me.getLocationOnScreen().y);
              
               ALFrame.this.setSize(ALFrame.this.getWidth(), ALFrame.this.getHeight() + diff);
            }
         });
         this.lblRightGrip.addMouseListener(gma);
         this.lblRightGrip.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent me) {
               int x = me.getXOnScreen();
               int diff = x - ALFrame.this.getLocationOnScreen().x;
               ALFrame.this.setSize(diff, ALFrame.this.getHeight());
            }
         });
         this.lblLeftGrip.addMouseListener(gma);
         this.lblLeftGrip.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent me) {
               int x = me.getXOnScreen();
               int diff = ALFrame.this.getLocationOnScreen().x - x;
               ALFrame.this.setLocation(me.getLocationOnScreen().x, ALFrame.this.getLocation().y);
               ALFrame.this.setSize(diff + ALFrame.this.getWidth(), ALFrame.this.getHeight());
            }
         });
         this.lblBottomGrip.addMouseListener(gma);
         this.lblBottomGrip.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent me) {
               int y = me.getYOnScreen();
               int diff = y - ALFrame.this.getLocationOnScreen().y;
               ALFrame.this.setSize(ALFrame.this.getWidth(), diff);
            }
         });
      }

   }

   void registerTitlePanel(JPanel panel) {
      this.vBox = Box.createVerticalBox();
      this.vBox.setOpaque(true);
      vBox.setBorder(new EmptyBorder(8,0,0,5));
      Box hBox = Box.createHorizontalBox();
      
      //hBox.setBackground(StaticResource.titleColor);
      if(this.minimizeBox) {
         hBox.add(this.createTransparentButton(StaticResource.getIcon("minus.png"), StaticResource.getIcon("minus_r.png"), new Dimension(24, 24), this.actMin));
      }

      if(this.maximizeBox) {
         hBox.add(this.createTransparentButton(StaticResource.getIcon("max.png"), StaticResource.getIcon("max_r.png"), new Dimension(24, 24), this.actMax));
      }

      hBox.add(this.createTransparentButton(StaticResource.getIcon("close.png"), StaticResource.getIcon("close_r.png"), new Dimension(24, 24), this.actClose));
      this.vBox.add(hBox);
      this.vBox.add(Box.createVerticalGlue());
      panel.add(this.vBox, "East");
   }

   private void createCursors() {
	  
      this.curDefault = new Cursor(0);
      this.curNResize = new Cursor(8);
      this.curWResize = new Cursor(10);
      this.curEResize = new Cursor(11);
      this.curSResize = new Cursor(9);
   }

   JButton createTransparentButton(ImageIcon icon, ImageIcon rIcon, Dimension d, ActionListener actionListener) {
      JButton btn = new JButton();
      btn.setIcon(icon);
      btn.setRolloverIcon(rIcon);
      btn.setBorderPainted(false);
      btn.setContentAreaFilled(false);
      btn.setFocusPainted(false);
      btn.setPreferredSize(d);
      btn.addActionListener(actionListener);
      return btn;
   }

   class GripMouseAdapter extends MouseAdapter {
      public void mouseEntered(MouseEvent me) {
         if(me.getSource() == ALFrame.this.lblBottomGrip) {
            ALFrame.this.lblBottomGrip.setCursor(ALFrame.this.curSResize);
         } else if(me.getSource() == ALFrame.this.lblRightGrip) {
            ALFrame.this.lblRightGrip.setCursor(ALFrame.this.curEResize);
         } else if(me.getSource() == ALFrame.this.lblLeftGrip) {
            ALFrame.this.lblLeftGrip.setCursor(ALFrame.this.curWResize);
         } else if(me.getSource() == ALFrame.this.lblTopGrip) {
            ALFrame.this.lblTopGrip.setCursor(ALFrame.this.curNResize);
         }

      }

      public void mouseExited(MouseEvent me) {
         ((JLabel)me.getSource()).setCursor(ALFrame.this.curDefault);
      }
   }

public void windowClosed(WindowEvent e) {
	// TODO Auto-generated method stub
	
}
}

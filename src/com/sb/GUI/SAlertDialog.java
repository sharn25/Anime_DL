package com.sb.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowEvent;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.sb.an_dl.ALFrame;
import com.sb.an_dl.StaticResource;
import com.sb.an_dl.TitlePanel;
import com.sb.an_dl.Utils;
import com.sb.downport.StringResource;

public class SAlertDialog extends JDialog {
	private static String TAG="SAlertDialog";
	private static Color bgBtn = StringResource.getColor("SEARCH_BG");
	private static Color bgTitle = StringResource.getColor("TITLE_BG_COLOR");
	private static Color bgCentre = StringResource.getColor("PANEL_BG_COLOR");
	private static Color textcolor = StringResource.getColor("TEXT_COLOR");
	private static Color hover = StringResource.getColor("SELECTION");
	private static Color pressed = StringResource.getColor("PRESSED");
	private static int result=-1;
	private static boolean isNotClicked=false;
	//Main Object
	private static SAlertDialog s;
	//JComponenets
	JPanel contentPane;
	JPanel titlePane;
	JPanel clientPane;
	Box vBox;
	private JLabel lblRightGrip;
	private JLabel lblLeftGrip;
	private JLabel lblTopGrip;
	private JLabel lblBottomGrip;
	public SAlertDialog(String title, JFrame owner, int w, int h) {
		super(owner,true);
		//setLocationRelativeTo(owner);
		int x = (owner.getX() +(owner.getWidth()/2))-w/2;
		int y = (owner.getY()+(owner.getHeight()/2))-h/2;
		setBounds(x, y, w, h);
		setUndecorated(true);
		setSize(new Dimension(w,h));
		setLayout(new BorderLayout());
		contentPane = new JPanel(new BorderLayout());
		add(contentPane,"Center");
		createResizeGrip();
		titlePane = new TitlePanel(new BorderLayout(),this);
		registerTitlePanel(titlePane);
		clientPane = new JPanel(new BorderLayout());
		clientPane.setBackground(bgCentre);
		clientPane.setOpaque(true);
		contentPane.add(titlePane,"North");
		contentPane.add(clientPane,"Center");
		addTitle(titlePane,title);
	}
	
	void addTitle(JPanel panel,String title) {
		JLabel msgLbl = new JLabel(title);
	    msgLbl.setBorder(new EmptyBorder(6, 6, 0, 0));
	    msgLbl.setFont(StaticResource.materialFontMidL);
	    msgLbl.setForeground(textcolor);
	    panel.add(msgLbl,"Center");
	}
	
	void addToPanel(JComponent c) {
		clientPane.add(c,"Center");
	}
	
	void registerTitlePanel(JPanel panel) {
		  panel.setOpaque(true);
		  panel.setBackground(bgTitle);
	      this.vBox = Box.createVerticalBox();
	      this.vBox.setOpaque(true);
	      Box hBox = Box.createHorizontalBox();
	      //hBox.setBackground(StaticResource.titleColor);
	      hBox.add(this.createTransparentButton(StaticResource.getIcon("close.png"), StaticResource.getIcon("close_r.png"), new Dimension(24, 24), this.actClose));
	      this.vBox.add(hBox);
	      this.vBox.add(Box.createVerticalGlue());
	      panel.add(this.vBox, "East");
	      JLabel n = new JLabel(StaticResource.getIcon(StringResource.getColorString("TITLE_BOTTOM"))) {
	    	    @Override
	    	    public void paintComponent(Graphics g) {
	    	        super.paintComponent(g);
	    	        g.drawImage(((ImageIcon)getIcon()).getImage(), 0, 0, getWidth(), getHeight(), null);
	    	    }
	    	};
	    	panel.add(n,"South");
	   }
	
	public static void closeDialog() {
		s.dispose();
	}
	
	 public ActionListener actClose = new ActionListener() {
	      public void actionPerformed(ActionEvent action) {
	    	 
	    	  closeDialog();
	      }
	   };
	   
	   JButton createTransparentButton(ImageIcon icon, ImageIcon rIcon, Dimension d, ActionListener actionListener) {
		      JButton btn = new JButton();
		      btn.setIcon(icon);
		      btn.setRolloverIcon(rIcon);
		      btn.setBorderPainted(false);
		      btn.setContentAreaFilled(false);
		      btn.setFocusPainted(false);
		      btn.setPreferredSize(d);
		      btn.setBorder(new EmptyBorder(6,0,2,5));
		      btn.addActionListener(actionListener);
		      return btn;
		   }
	   
	   private void createResizeGrip() {
		   
		      //ALFrame.GripMouseAdapter gma = new ALFrame.GripMouseAdapter();
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
	 
		   }
	   
	public static void showAlertAction(String title, JPanel panel,JFrame owner,int w,int h) {
		panel.setBackground(bgCentre);
		s = new SAlertDialog(title, owner,w,h);
		 JPanel cpanel = new JPanel(new BorderLayout());
		 cpanel.setOpaque(false);
	     s.addToPanel(panel);
	     s.setVisible(true);
	}
	public static void showAlertInfo(String title, String msg,JFrame owner) {
		String submsg="";
		if(msg.contains("<br>")) {
			submsg = msg.substring(0,msg.indexOf("<br>"));
		}else {
			submsg=msg;
		}
		int width = submsg.length()*6+50;
		
		
		s = new SAlertDialog(title, owner,width,130);
		
		 JPanel cpanel = new JPanel(new BorderLayout());
	      cpanel.setOpaque(false);
	      s.addToPanel(cpanel);
	      JLabel msgLbl = new JLabel("<html>"+msg+"</html>");
		  msgLbl.setBorder(new EmptyBorder(0, 10, 0, 0));
		  msgLbl.setFont(StaticResource.materialFontSmallL);
		  msgLbl.setForeground(textcolor);
	      cpanel.add(msgLbl,"Center");
	      Box hbottom = Box.createHorizontalBox();
	      hbottom.add(Box.createHorizontalGlue());
	      SButton okBtn = new SButton("OK");
	      okBtn.setBackGround(bgBtn);
	      okBtn.setBorderPainted(false);
	      okBtn.setHover(hover);
	      okBtn.setFont(StaticResource.materialFontSmallL);
	      okBtn.setPressedColor(pressed);
	      okBtn.setForeground(textcolor);
	      okBtn.setWidth(70);
	      okBtn.addActionListener(s.actClose);
	      hbottom.add(okBtn);
	      hbottom.add(Box.createHorizontalStrut(10));
	      Box vBox = Box.createVerticalBox();
	      vBox.add(hbottom);
	      //vBox.add(Box.createHorizontalStrut(5));
	      JLabel sem =new JLabel(" ");
	      sem.setPreferredSize(new Dimension(1,8));
	      sem.setMaximumSize(new Dimension(1,8));
	      vBox.add(sem);
	      cpanel.add(vBox,"South");
	      s.setVisible(true);
	   
	}
}

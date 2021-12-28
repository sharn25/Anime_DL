package com.sb.an_dl;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.sb.downport.StringResource;

import javax.swing.JLabel;

public class new21 extends ALFrame {

	private JPanel contentPane;
	private static String about = "<html><H1>Anime DL 8.6</H1>"
			+ "build 0000"
			+ "<br>Stream & Download latest anime by direct link with integrated download manager"
			+ "<br>Website:http://a.animedlweb.ga"
			+ "<br>Developed by : SBdev(Sharn25)"
			+ "<br>Email : sbdev.r@gmail.com"
			+ "<br>For any feedback or query feel free to email me.</html>";
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new21 frame = new new21();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	
	public void showabout() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new21 frame = new new21();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public new21() {
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 321, 280);
		setTitle("DL Alert");
		setIconImage(StaticResource.getIcon("icon.png").getImage());
		getTitlePanel().setBackground(StringResource.getColor("TITLE_BG_COLOR"));
		getpanContent().setBackground(StringResource.getColor("PANEL_BG_COLOR"));
	    getpanClient().setBackground(StringResource.getColor("PANEL_BG_COLOR"));
	    JLabel var19 = new JLabel(StringResource.getString("ABOUT"));
	      var19.setBorder(new EmptyBorder(8, 8, 0, 0));
	      var19.setFont(StaticResource.materialFontMidL);
	      var19.setForeground(StringResource.getColor("TEXT_COLOR"));
	    getTitlePanel().add(var19, "Center");
	    JLabel n = new JLabel(StaticResource.getIcon(StringResource.getColorString("TITLE_BOTTOM"))) {
    	    @Override
    	    public void paintComponent(Graphics g) {
    	        super.paintComponent(g);
    	        g.drawImage(((ImageIcon)getIcon()).getImage(), 0, 0, getWidth(), getHeight(), null);
    	    }
    	};
      getTitlePanel().add(n,"South");
      JPanel cpanel = new JPanel(new BorderLayout());
      cpanel.setOpaque(false);
      add(cpanel);
      JPanel j = new JPanel();
      JLabel icon = new JLabel(StaticResource.getIcon("icon.png"));
      icon.setBorder(null);
      icon.setAlignmentX(Component.LEFT_ALIGNMENT);
      cpanel.add(icon,"North");
      JLabel msgLbl = new JLabel(about);
      msgLbl.setBorder(new EmptyBorder(0, 10, 0, 0));
      msgLbl.setFont(StaticResource.materialFontSmallL);
      msgLbl.setForeground(StringResource.getColor("TEXT_COLOR"));
      
      cpanel.add(msgLbl,"Center");
		
		
	}

}

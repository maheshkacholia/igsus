/*
 * Created on May 18, 2007
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.frontend;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Label;
import java.awt.LayoutManager;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JWindow;

import com.stockfaxforu.util.StockConstants;
/**
 * @author sdeopura
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ToolScreen extends JWindow
{
	public ToolScreen()
	{
//		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(380, 200);

		this.setContentPane(getJContentPane());
//		this.setTitle("Add Note");
		this.move(300, 280);
		Image im = java.awt.Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("image/toolicon.jpg"));
//		this.setIconImage(im);
		this.setVisible(true);
//		this.setResizable(false);
//		 this.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG); 

			

	}
	private javax.swing.JPanel jContentPane = null;
	private javax.swing.JPanel getJContentPane()
	{
		int xinc = 10;
		int yinc = 20;
		int xincdis = 100;
		if (jContentPane == null)
		{
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.setBackground(Color.lightGray);
			ImageIcon cup = new ImageIcon(ClassLoader.getSystemResource("image/logo.jpg"));
			JButton button = new JButton(cup);
			button.setAlignmentY(0);
			
			button.setBounds(0, 0, this.getWidth(), this.getHeight()-70);
			jContentPane.add(button);

			JLabel lable = new JLabel(StockConstants.VERSION);
			lable.setBackground(Color.white);
			lable.setForeground(Color.black);
			lable.setFont(new Font("Arial", Font.BOLD, 12));
			lable.setBounds(5, this.getHeight()-60, this.getWidth()-10, 15);
			jContentPane.add(lable);
			
			
			JLabel lable1 = new JLabel(StockConstants.COPYRIGHT);
			lable1.setBackground(Color.white);
			lable1.setForeground(Color.black);
			lable1.setFont(new Font("Arial", Font.BOLD, 12));
			lable1.setBounds(5, this.getHeight()-40, this.getWidth()-10, 15);
			jContentPane.add(lable1);
			
			JLabel lable2 = new JLabel(StockConstants.EXPIRY);
			lable2.setBackground(Color.white);
			lable2.setForeground(Color.red);
			lable2.setFont(new Font("Arial", Font.BOLD, 12));
			lable2.setBounds(5, this.getHeight()-20, this.getWidth()-10, 15);
			
			jContentPane.add(lable2);

			
		}
		return jContentPane;
	}
	public static void main(String[] args)
	{
		ToolScreen tool = new ToolScreen();
	}
}

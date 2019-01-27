/*
 * Created on Jul 23, 2009
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.config;

import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

/**
 * @author Mahesh
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ConfigurePropertyScreen extends JFrame implements ActionListener
{
	int x=900;
	int y=800;
	DataValuePanel datavalue = null;
	public static void main(String[] args)
	{
		ConfigurePropertyScreen conf = new ConfigurePropertyScreen();
	}
	public ConfigurePropertyScreen()
	{
		this.setTitle("View/Update Setting ");
		setBackground(Color.lightGray);
		this.setSize(x,y);
		this.move(50,50);
		Image im = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("image/toolicon.jpg"));
		this.setIconImage(im);
	
		this.setContentPane(getJContentPane());
				this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	}
	JPanel jContentPane=null;
	public JPanel getJContentPane()
	{
		if(jContentPane==null)
		{
			jContentPane = new javax.swing.JPanel(null);
		}
		PropertyTreePanel treepanel = new PropertyTreePanel(250,y,this);
		treepanel.setBounds(0,0,250,y);
		JScrollPane scrool = new JScrollPane(treepanel);
		scrool.setBounds(1,1, 280, y);
		jContentPane.add(scrool, null);
		
		
		return jContentPane;
	}
	public void actionPerformed(ActionEvent e)
	{
		// TODO Auto-generated method stub
		
	}
	


}

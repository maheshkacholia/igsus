package com.stockfaxforu.frontend;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.stockfaxforu.util.StockConstants;
import com.stockfaxforu.util.Utility;

public class HelpWindowForAll extends JFrame
{
	private javax.swing.JPanel jContentPane;
	String helpFile=null;
	public HelpWindowForAll(String helpFile)
	{
		//		super(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.helpFile = helpFile;

		this.setContentPane(getJContentPane());
		this.setTitle("IGS  Help Window");
		this.move(50, 50);
		Image im = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("image/toolicon.jpg"));
		this.setIconImage(im);

		this.setSize(900, 800);
		this.setVisible(true);
		this.setResizable(false);
	}
	public javax.swing.JPanel getJContentPane()
	{
		try
		{
			
		
			if (jContentPane == null)
			{
				jContentPane = new javax.swing.JPanel();
				jContentPane.setLayout(new BorderLayout());
			}
			String s = Utility.getFileContent(StockConstants.INSTALL_DIR + "/help/" + helpFile );
			AdPanel editorPane = new AdPanel(new StringBuffer(s));
			editorPane.setEditable(false);
			JScrollPane listScrollPane = new JScrollPane(editorPane);
			//listScrollPane.createVerticalScrollBar();
			//listScrollPane.createHorizontalScrollBar();
			jContentPane.add(listScrollPane);
			return jContentPane;
			}
		catch(Exception e)
		{
			e.printStackTrace();
			return jContentPane;
		}
		
	}
}

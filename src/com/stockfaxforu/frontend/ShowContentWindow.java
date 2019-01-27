/*
 * Created on May 8, 2009
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.frontend;

import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import com.stockfaxforu.util.StockConstants;
import com.stockfaxforu.util.Utility;

/**
 * @author Mahesh
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ShowContentWindow extends JFrame implements HyperlinkListener
{
	String content;
	String filename;
	String directory;
	public ShowContentWindow(String content)
	{
		this.move(100, 100);
		this.content = content;
		Image im = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("image/toolicon.jpg"));
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setIconImage(im);
		this.setName("Backtesting Result");
		this.setSize(700,700);

		this.setContentPane(getJContentPane());
		this.setVisible(true);
		

	}
	public ShowContentWindow(String directory,String filename)
	{
		this.move(100, 100);
		this.filename = filename;
		this.directory = directory;
		try
		{
			this.content = Utility.getFileContent(directory + "/" + filename);  
					
		}
		catch(Exception e)
		{
			return;
		}
		Image im = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("image/toolicon.jpg"));
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setIconImage(im);
		this.setName("Help Window");
		this.setSize(700,700);

		this.setContentPane(getJContentPane());
		this.setVisible(true);
		

	}
		
	public JPanel jContentPane;
	private javax.swing.JPanel getJContentPane()
	{
		if(jContentPane == null) 
		{
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
		}
		
		AdPanel editorPane=null;
		try
		{
			editorPane = new AdPanel(new StringBuffer(content));
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
	//		e.printStackTrace();
		}
		editorPane.setEditable(false);
		editorPane.addHyperlinkListener(this);
		JScrollPane scrollPane = new JScrollPane(editorPane,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		scrollPane.setBounds(0,0,700-20,700-30);
		jContentPane.add(scrollPane,null);
		return jContentPane;
	
	}
	public static void main(String[] args)
	{
		ConsoleWindow console = new ConsoleWindow();
	}
	public void hyperlinkUpdate(HyperlinkEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}

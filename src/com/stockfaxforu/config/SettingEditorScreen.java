/*
 * Created on May 10, 2009
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.config;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.RandomAccessFile;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.stockfaxforu.formulabuilder.MessageDiaglog;
import com.stockfaxforu.frontend.MainScreen;
import com.stockfaxforu.util.StockConstants;
import com.stockfaxforu.util.Utility;

/**
 * @author Mahesh
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class SettingEditorScreen extends JFrame implements ComponentListener,ActionListener
{
	int x=900;
	int y=650;
	public SettingEditorScreen()
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
	JPanel jcontentpane=null;
	JTextArea buyformula=null;
	private javax.swing.JPanel getJContentPane() 
	{
		if(jcontentpane==null)
		{
			jcontentpane = new JPanel();
			jcontentpane.setLayout(null);
		}
		buyformula = new JTextArea(x-25,y-100);
		buyformula.setFont(new Font("Arial", Font.ITALIC, 14));
		try
		{
			buyformula.setText(	Utility.getFileContent(StockConstants.SOFTWARE_PROPERTY_FILE));
		}
		catch (Exception e)
		{
			MessageDiaglog msg = new MessageDiaglog("Error occured while getting configuration data");
			return jcontentpane;
		}

		scrollPane = new JScrollPane(buyformula,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS); 
		scrollPane.setBounds(0,0,x-25,y-100);
		
		jcontentpane.add(scrollPane,null);
		
		JButton apply = new JButton("Apply");
		apply.setName("apply");
		jcontentpane.add(apply);
		apply.addActionListener(this);
		apply.setBounds(100,y-75,100,25);
		
		JButton cancel = new JButton("Cancel");
		cancel.setName("cancel");
		jcontentpane.add(cancel);
		cancel.addActionListener(this);
		cancel.setBounds(300,y-75,100,25);

		
//		this.addComponentListener(this);
		return jcontentpane;
	}
	JScrollPane scrollPane =null;
	public static void main(String[] args)
	{
		SettingEditorScreen set = new SettingEditorScreen();
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ComponentListener#componentResized(java.awt.event.ComponentEvent)
	 */
	public void componentResized(ComponentEvent arg0)
	{
		Dimension d = this.getSize();
		x = (int)d.getWidth();
		y = (int)d.getHeight();
		scrollPane.resize(x,y);
		scrollPane.setBounds(0,0,x,y);
		
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ComponentListener#componentMoved(java.awt.event.ComponentEvent)
	 */
	public void componentMoved(ComponentEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ComponentListener#componentShown(java.awt.event.ComponentEvent)
	 */
	public void componentShown(ComponentEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see java.awt.evsent.ComponentListener#componentHidden(java.awt.event.ComponentEvent)
	 */
	public void componentHidden(ComponentEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent arg0)
	{
		Object o = arg0.getSource();
		if(o instanceof JButton)
		{
			JButton but = (JButton)o;
			String name = but.getName();
	//		// ln("name="+name);
			if(name.equalsIgnoreCase("apply"))
			{
				try
				{
					Utility.saveContent(StockConstants.SOFTWARE_PROPERTY_FILE,buyformula.getText());
					this.dispose();
				}
				catch (Exception e)
				{
					MessageDiaglog msg = new MessageDiaglog("Not able to update configuration data , Error occured");
					return;
				}
				ConfigUtility.loadProperty();
				MainScreen.getSingleton().doReSize();
			}
			else if(name.equalsIgnoreCase("cancel"))
			{
				this.dispose();
			}
 
		}
		// TODO Auto-generated method stub
		
	}
	
	
}

/*
 * Created on Jul 9, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.frontend;

import java.awt.Button;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.stockfaxforu.getolddataforu.thread.IndexThread;
import com.stockfaxforu.util.StockConstants;

/**
 * @author sdeopura
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class InstallScreen extends JFrame implements ActionListener
{
	public JPanel jcontentpane=null;
	public JProgressBar progress=null;
	public JButton install = null;
	public JTextArea message = null;
	public JButton cancel=null;
	public static int fileprocessed=0;
	public static int maxvalue=100;
	private static InstallScreen installScreen = null;
	public static InstallScreen getSingleton()
	{
		if (installScreen==null)
			installScreen = new InstallScreen();		
		return installScreen;
	}
	public static void main(String args[])
	{
		InstallScreen grme = new InstallScreen();
		grme.show();
		try
		{
			Thread.sleep(1000);
		}
		catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
		}
//		grme.progress.setValue(90);

	}
	private InstallScreen()
	{
	//	super(maingraph,s,b);
		this.setTitle("Installation Screen ");
		setBackground(Color.lightGray);
		this.setSize(525,400);
		this.move(200,200);
		Image im = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("image/toolicon.jpg"));
		this.setIconImage(im);

		this.setContentPane(getJContentPane());
				this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	private javax.swing.JPanel getJContentPane() 
	{
		if(jcontentpane==null)
		{
			jcontentpane = new JPanel();
			jcontentpane.setLayout(null);
		}
		int yinc =25;
		int xinc = 50;	

		install = new JButton("Install");
		install.setBounds(xinc,yinc, 100,25);
		install.setName("install");
		install.addActionListener(this);
		jcontentpane.add(install,null);
		
		xinc = xinc + 125;
		progress = new JProgressBar(0,maxvalue);
		progress.setBounds(xinc,yinc, 300,25);
		progress.setValue(0);
		
	
		jcontentpane.add(progress,null);

		yinc = yinc +50;
		xinc = 50;
		message = new JTextArea(10,5);
		message.setBounds(xinc,yinc, 425,200);
		JScrollPane scroolpane = new JScrollPane(message);
		scroolpane.setBounds(xinc,yinc, 425,200);

		message.setName("install");
		jcontentpane.add(scroolpane,null);

		yinc = yinc +225;
		xinc = 50;

		JButton proxy = new JButton("Proxy");
		proxy.setBounds(xinc,yinc, 100,25);
		proxy.addActionListener(this);
		proxy.setName("proxy");
		jcontentpane.add(proxy,null);
		
		xinc = xinc + 150;
		cancel = new JButton("Cancel");
		cancel.setBounds(xinc,yinc, 100,25);
		cancel.setName("cancel");

		cancel.addActionListener(this);

		jcontentpane.add(cancel,null);


		return jcontentpane;
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent arg0)
	{
		Object obj = arg0.getSource();
		if(obj instanceof JButton)
		{
			JButton obj1 = (JButton)obj;
			String name = obj1.getName();
			if(name.equals("install"))
			{
				
				StockConstants.optionname = "All Securities";
				Runnable runnable = new IndexThread();
				Thread thread = new Thread(runnable);
				thread.start();
				install.setEnabled(false);
				
			}
			else if(name.equals("cancel"))
			{
				this.dispose();
			}
			else if(name.equals("proxy"))
			{
				ProxySetting f = new ProxySetting();
			}
			
		}
		// TODO Auto-generated method stub
		
	}
}
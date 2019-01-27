package com.stockfaxforu.config;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.stockfaxforu.formulabuilder.MessageDiaglog;
import com.stockfaxforu.livemarket.YahooMail;
import com.stockfaxforu.swingcomponent.IGSLabel;
import com.stockfaxforu.util.StockConstants;

public class YahooLogin extends JFrame implements ActionListener
{
	public JTextField yahootext;
	public JPasswordField yahoopasswordtext;
	
	public static String YAHOOID="YAHOOID";
	public static String   PASSWORD="PASSWORD";
	
	public static String yahoo="";
	public static String yahoopassword="";
	
	
	public static HashMap yahooInfo=null;
	
	public static void loadInfo()
	{
		File f = new File(StockConstants.INSTALL_DIR + "/" + "yahoo.obj");

		if(f.exists() || yahooInfo==null)
		{
			try
			{
				FileInputStream input = new FileInputStream(f);
				ObjectInputStream obj = new ObjectInputStream(input);
				yahooInfo = (HashMap)obj.readObject();
				yahoo = (String)yahooInfo.get(YAHOOID);
				yahoopassword = (String)yahooInfo.get(PASSWORD);
				
			}
			catch(Exception e)
			{
				
			}
		}	
	}
	public static String getYahooInfo(String id)
	{
		if(yahooInfo==null)
				loadInfo();
		try
		{
			return (String)yahooInfo.get(id);
		}
		catch(Exception e)
		{
			return null;
		}	
	}	
	public YahooLogin()
	{
		loadInfo();
		setBackground(Color.lightGray);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setTitle("Yahoo Login Screen");
		this.setContentPane(getJContentPane());
	
		this.move(100, 100);
		this.setSize(400,300);
		Image im = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("image/toolicon.jpg"));
		this.setIconImage(im);
	
		this.setVisible(true);
	}
	private javax.swing.JPanel jContentPane = null;
	private javax.swing.JPanel getJContentPane() 
	{
		int xinc = 10;
		int yinc = 20;
		int xincdis=100;

		if(jContentPane == null) 
		{
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
		}
		JLabel lable = new IGSLabel("Yahoo User ID");
		lable.setFont(new Font("Arial",Font.BOLD,12));

		lable.setFont(new Font("Arial",Font.BOLD,12));
		lable.setBounds(xinc,yinc,100,15);	
		jContentPane.add(lable,null);
		
		xinc = xinc + 150;
		yahootext = new JTextField(yahoo);
		yahootext.setBounds(xinc,yinc,100,20);	
		jContentPane.add(yahootext,null);
		
		yinc = yinc + 40;
		xinc = 10;
		
		JLabel lable1 = new IGSLabel("Yahoo Password");
		lable1.setFont(new Font("Arial",Font.BOLD,12));

		lable1.setFont(new Font("Arial",Font.BOLD,12));
		lable1.setBounds(xinc,yinc,300,15);	
		jContentPane.add(lable1,null);

		xinc = xinc + 150;
		yahoopasswordtext = new JPasswordField(yahoopassword);
//		buyinpertext.setFont(new Font("Arial",Font.BOLD,12));
		yahoopasswordtext.setBounds(xinc,yinc,100,20);	
		jContentPane.add(yahoopasswordtext,null);
		
		yinc = yinc + 40;

		
		xinc = 10;
		
		JButton save = new JButton();
		save.setBounds(xinc,yinc,75,20);
		save.setName("save");
		save.setText("Save");
		jContentPane.add(save,null);
		save.addActionListener(this);


		xinc = xinc + 100;

		JButton execute = new JButton();
		execute.setBounds(xinc,yinc,125,20);
		execute.setName("execute");
		execute.setText("Login");
		jContentPane.add(execute,null);
		execute.addActionListener(this);


		
		xinc = xinc + 150;
		
		JButton cancel = new JButton();
		cancel.setBounds(xinc,yinc,75,20);
		cancel.setName("cancel");
		cancel.setText("Cancel");
		jContentPane.add(cancel,null);
		cancel.addActionListener(this);

		
		return jContentPane;
		
	}
	FileOutputStream output = null;
	ObjectOutputStream obj=null;
	public void actionPerformed(ActionEvent e)
	{
		Object o = e.getSource();
		JButton cb = (JButton)e.getSource();
		String name = cb.getName();
		
		if(name.equalsIgnoreCase("execute"))
		{
//			YahooLogin.loadInfo();
			StockConstants.YAHOO = new YahooMail();
			try
			{
				StockConstants.YAHOO.loginToYahoo();
				MessageDiaglog msg = new MessageDiaglog("Login Successful");
				
			}
			catch (Exception e1)
			{
				e1.printStackTrace();
				MessageDiaglog msg = new MessageDiaglog("Exception Occured while trying to login yahoo");
			}
		}
		else if(name.equalsIgnoreCase("save"))
		{
			try
			{
				yahoo = yahootext.getText();
				yahoopassword = yahoopasswordtext.getText();
				
				yahooInfo  = new HashMap();
				yahooInfo.put(YAHOOID, yahoo);
				yahooInfo.put(PASSWORD, yahoopassword);
				
				File f = new File(StockConstants.INSTALL_DIR + "/" + "yahoo.obj");
				output = new FileOutputStream(f);
				obj = new ObjectOutputStream(output);
				obj.writeObject(yahooInfo);
				obj.close();
				
				MessageDiaglog msg = new MessageDiaglog("Parameter saved in harddisk");
				return;
				
			}
			catch(Exception e1)
			{
				MessageDiaglog msg = new MessageDiaglog("Wrong Data is entered");
				return;		
			}
			finally
			{
				try
				{
					obj.close();
					output.close();
				}
				catch(Exception e2)
				{
					
				}
			}
		}
		else if(name.equalsIgnoreCase("cancel"))
		{
			this.dispose();
		}
	}
	

}

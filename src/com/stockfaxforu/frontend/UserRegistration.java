/*
 * Created on Feb 19, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.frontend;

import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Label;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutput;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.stockfaxforu.component.*;
import com.stockfaxforu.formulabuilder.MessageDiaglog;
import com.stockfaxforu.util.ManageIndicator;
import com.stockfaxforu.util.StockConstants;
import com.stockfaxforu.util.Utility;

/**
 * @author sdeopura
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class UserRegistration extends JFrame implements ActionListener
{
	/**
	 * This is the default constructor
	 */
	JTextField note =  null;
	JButton add = null;
	JButton cancel = null;
	JTextField name,emailaddress,phoneno,refer;
	JComboBox drive;
	
	public UserRegistration()
	{
//		super(null);
//		setBackground(Color.lightGray);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setContentPane(getJContentPane());
		this.setTitle("User Registration");
		this.move(200, 200);
		this.setSize(500,400);
		Image im = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("image/toolicon.jpg"));
		this.setIconImage(im);

		this.setVisible(true);
	}
	private javax.swing.JPanel jContentPane = null;
	JCheckBox c=null;
	private javax.swing.JPanel getJContentPane() 
	{
		int xinc = 50;
		int yinc = 40;
		int xincdis=175;
		note =  new JTextField();

		if(jContentPane == null) 
		{
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
		}
/*		JLabel lable10 = new JLabel("Windows Installation Drive :");
		lable10.setFont(new Font("Arial",Font.BOLD,12));
		lable10.setBounds(xinc,yinc+10,150,15);	
		lable10.setForeground(Color.black);

		jContentPane.add(lable10,null);
		String[] drivearray = {"C:","D:","E:","F:","G:","H:","I:","J:","K:","L:","M:","N:","O:","P:","Q:","R:","S:","T:","U:","V:","W:","X:","Y:","Z:"};
		drive = new JComboBox(drivearray);
		drive.setFont(new Font("Arial",Font.BOLD,12));
		drive.setBounds(xinc+xincdis,yinc,20,20);
		jContentPane.add(drive,null);
		
		yinc = yinc + 40;
*/
		JLabel lable = new JLabel("Name :");
		lable.setFont(new Font("Arial",Font.BOLD,12));
		lable.setBounds(xinc,yinc+10,150,15);	
		lable.setForeground(Color.black);

		jContentPane.add(lable,null);
		
		name = new JTextField();
		name.setFont(new Font("Arial",Font.BOLD,12));
		name.setBounds(xinc+xincdis,yinc,200,20);
		jContentPane.add(name,null);
		
		yinc = yinc + 40;
		

		
		JLabel lable1 = new JLabel("Email Address :");
		lable1.setFont(new Font("Arial",Font.BOLD,12));
		lable1.setForeground(Color.black);

		lable1.setBounds(xinc,yinc,150,15);	
		jContentPane.add(lable1,null);

		JLabel lable2 = new JLabel("(This will be your user name)");
		lable2.setFont(new Font("Arial",Font.PLAIN,12));
		lable2.setBounds(xinc,yinc+15,200,15);	
		lable2.setForeground(Color.black);

		jContentPane.add(lable2,null);

		emailaddress = new JTextField();
		emailaddress.setFont(new Font("Arial",Font.BOLD,12));
		emailaddress.setBounds(xinc+xincdis,yinc,200,20);	
		jContentPane.add(emailaddress,null);

		yinc = yinc + 40;
		
		JLabel lable3 = new JLabel("Phone No :");
		lable3.setFont(new Font("Arial",Font.BOLD,12));
		lable3.setBounds(xinc,yinc,150,15);	
		lable3.setForeground(Color.black);

		jContentPane.add(lable3,null);

		phoneno = new JTextField();
		phoneno.setFont(new Font("Arial",Font.BOLD,12));
		phoneno.setBounds(xinc+xincdis,yinc,200,20);	
		jContentPane.add(phoneno,null);

		yinc = yinc + 40;
		JLabel lable4 = new JLabel("Referrer :");
		lable4.setFont(new Font("Arial",Font.BOLD,12));
		lable4.setBounds(xinc,yinc,150,15);	
		lable4.setForeground(Color.black);

		jContentPane.add(lable4,null);

		refer = new JTextField();
		refer.setFont(new Font("Arial",Font.BOLD,12));
		refer.setBounds(xinc+xincdis,yinc,200,20);	
		jContentPane.add(refer,null);

/*		yinc = yinc + 40;
		JLabel lable5 = new JLabel("Country : ");
		lable5.setFont(new Font("Arial",Font.BOLD,12));
		lable5.setBounds(xinc,yinc,150,15);	
		lable5.setForeground(Color.black);

		jContentPane.add(lable5,null);

		String[] countryVec = Utility.getCountries();
		country = new JComboBox(countryVec);
		country.setFont(new Font("Arial",Font.BOLD,12));
		country.setBounds(xinc+xincdis,yinc,100,20);	
		jContentPane.add(country,null);
*/
		yinc = yinc + 40;
		
		c = new JCheckBox(" Terms and Conditions ");
		c.setName("terms");
		c.setBounds(xinc,yinc,200,20);
		jContentPane.add(c,null);
		c.addActionListener(this);
		
		yinc = yinc + 40;
		
		add = new JButton();
		add.setBounds(xinc,yinc,150,20);
		add.setName("add");
		add.setText("Register");
		jContentPane.add(add,null);
		add.addActionListener(this);

		
		cancel = new JButton();
		cancel.setBounds(xinc+160,yinc,75,20);
		cancel.setName("cancel");
		cancel.setText("Cancel");
		jContentPane.add(cancel,null);
		cancel.addActionListener(this);
		
		JButton proxy = new JButton();
		proxy.setBounds(xinc+160+85,yinc,100,20);
		proxy.setName("proxy");
		proxy.setText("Proxy");
		jContentPane.add(proxy,null);
		proxy.addActionListener(this);

		 
		return jContentPane;	 
	
	}	
	Vector countryVec = null;
	JComboBox country = null;

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize()
	{
		this.setSize(300, 250);
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e)
	{
 		BufferedReader buff=null;
		String emailid="",username="",mcnameval="",phval="",referval="";
		Object o = (Object)e.getSource();
		if( o instanceof JCheckBox)
		{
			if (((JCheckBox)o).isSelected())
				Utility.openURL("http://www.chartalive.com");
			return;
		}
		else if(o instanceof JButton)
		{
			JButton cb = (JButton)e.getSource();
			String namebut = cb.getName();
			
						
			if(namebut.equalsIgnoreCase("add"))
			{
				if(!c.isSelected())
				{
					MessageDiaglog msg = new MessageDiaglog("Pls Accept Terms And Conditions");
					return;
				}

/*
				try
				{
				//	MessageDiaglog message = new MessageDiaglog("Congratulations, You are Registered Successfully");
					
					File f = new File(StockConstants.userTempFile);
										
					RandomAccessFile out = new RandomAccessFile( StockConstants.userTempFile,"rw");
					out.writeBytes("temp");
										
				//	out.writeBytes("uid="+emailaddress.getText().trim()+"&mcname="+mcname+"&prodtype="+StockConstants.PRODUCTTYPE);
			
					out.close();
				}
				catch(Exception e2)
				{
					String usrname = System.getProperty("user.name");
					MessageDiaglog msg = new MessageDiaglog("Not able to create file in " + "/Windows directory"  + ", Pls give all authority to user " + usrname + " to create file in windows directory");
					return;
				}
*/
				String val = name.getText().trim();
				if(val==null || val.equals("") || val.length() > 100 || Utility.isInavlidChar(val))
				{
					MessageDiaglog msg = new MessageDiaglog("Please Enter Your Name");
					return;
				}
				val = emailaddress.getText().trim();
				if(val.indexOf('@') == -1 )
				{
					MessageDiaglog msg = new MessageDiaglog("Invalid Email Address");
					return;
					
				}
				if(val.indexOf(' ') != -1 )
				{
					MessageDiaglog msg = new MessageDiaglog("Invalid Email Address");
					return;
					
				}
				if(val==null || val.equals(""))
				{
					MessageDiaglog msg = new MessageDiaglog("Please Enter Your Email Address");
					return;
				}
				val = phoneno.getText().trim();
				if(val==null || val.equals(""))
				{
					MessageDiaglog msg = new MessageDiaglog("Please Enter Your Phone No");
					return;
				}
				
				
				String mcname=emailaddress.getText().trim();
				mcname=Utility.getComputerName();
				if(mcname==null)
				{
					mcname=emailaddress.getText().trim();
				}
	//			String country = (String)this.country.getSelectedItem();
				String country = "IN";
				
				String s = "uid="+ emailaddress.getText().trim()+"&name="+name.getText().trim()+"&mcname="+mcname+"&ph="+phoneno.getText().trim()+"&prodtype="+StockConstants.PRODUCTTYPE+"&refer="+refer.getText().trim()+"&country="+country;
				StockConstants.USERDETAIL.put("uid",emailaddress.getText().trim() );
				StockConstants.USERDETAIL.put("name",name.getText().trim() );
				StockConstants.USERDETAIL.put("mcname",mcname.trim() );
				StockConstants.USERDETAIL.put("ph",phoneno.getText().trim());
				StockConstants.USERDETAIL.put("prodtype",StockConstants.PRODUCTTYPE);
				StockConstants.USERDETAIL.put("refer",refer.getText().trim());
				StockConstants.USERDETAIL.put("country",country);
				
				s = Utility.encodeurl(StockConstants.USERDETAIL);
				// ln("s="+s);
		//		StockConstants.UNIQUECODE = 	generateCode();
				try {
				//	String url = "http://localhost:8080/test11/NewFile.jsp?"+"to="+URLEncoder.encode(emailaddress.getText().trim())+"&code="+StockConstants.UNIQUECODE;
					String url = "http://www.livechartnse.com/project/fortool/sendEmail.jsp?"+"to="+URLEncoder.encode(emailaddress.getText().trim())+"&code="+StockConstants.UNIQUECODE;
					
					
			//		System.out.println(url);
			//		Utility.getUrlContent(url);
				} catch (Exception e1) 
				{
					MessageDiaglog msg = new MessageDiaglog("Error occurred while sending email");
					return;
				}
				
			//this code will enable once emailsending is setup	
			//	ConfirmRegistration confirm = new ConfirmRegistration(s,emailaddress.getText().trim());
			//this code till email setup does not work
			
				doRegistration(s,emailaddress.getText().trim());
				
				this.dispose();
				
			}
			else if(namebut.equalsIgnoreCase("cancel"))
			{
				System.exit(0);
			}
			else if(namebut.equalsIgnoreCase("proxy"))
			{
				ProxySetting proxy = new ProxySetting();
			}
			
			
		}
		if(o instanceof JFrame)
		{
			this.dispose();
			
		}
	}
	/**
	 * @return
	 */
	
	private void doRegistration(String finalStr,String email)
	{
	/*	MessageDiaglog msg = new MessageDiaglog("correct code entered");
		if(true)
			return;
	*/	
		
		try
		{
			String retStr =  Utility.getUrlContent(StockConstants.ADDUSER+finalStr);
			retStr = retStr.trim();
			if(retStr.toString().equalsIgnoreCase("SUCCESS"))
			{
				//	StockConstants.COUNTRY = StockConstants.USERDETAIL.get("country")+"";						
					InstallScreen progress = InstallScreen.getSingleton();
					this.dispose();
					return;
			}
			else if(retStr.toString().equalsIgnoreCase("EXIST"))
			{
				MessageDiaglog message = new MessageDiaglog("User Id Already Exists in Database.");
				return;
				
			}
			else
			{
				MessageDiaglog message = new MessageDiaglog("Error Occured while Registration, Please Conatct iGuideStocks");
				return;

			}

		}
		catch (Exception e1)
		{
			// TODO Auto-generated catch block
			MessageDiaglog message = new MessageDiaglog("Error Occurred, Please check if you are connected to the Internet or you are behind a proxy");
		}
	}	
	private boolean checkValue()
	{
		// TODO Auto-generated method stub
		return false;
	}
	public static void main(String[] args)
		{
			JFrame frame = new JFrame("Test angled lines");
			frame.move(300, 300);

			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			Vector v = new Vector();
			v.addElement("Period 1(1..100)");
			v.addElement("Period 2(2..50)");
			
			
			UserRegistration d = new UserRegistration();
			frame.getContentPane().add(d);
			frame.pack();
			//		frame.setBounds(0,0,1000,700);
			frame.setVisible(true);

		}
	public static int generateCode()
	{
		double d = Math.random();
		int num = (int)(d*10);
		if(num < 3)
		{
			return 85086;
		}
		else if (num > 3 && num < 7 )
		{
			return 85086;
			
		}
		else
		{
			return 85086;
		}
		
		
	}
	
}  //  @jve:visual-info  decl-index=0 visual-constraint="4,11"

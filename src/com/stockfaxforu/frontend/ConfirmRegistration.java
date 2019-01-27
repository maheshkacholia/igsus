package com.stockfaxforu.frontend;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.stockfaxforu.formulabuilder.MessageDiaglog;
import com.stockfaxforu.util.StockConstants;
import com.stockfaxforu.util.Utility;

public class ConfirmRegistration  extends JFrame implements ActionListener
{

	private javax.swing.JPanel jContentPane = null;
	private JTextField code = null;
	private JButton add,cancel;
	private String emailAddress,finalStr;
	public ConfirmRegistration(String finalStr,String emailAddress) 
	{
	
//		super(null);
//		setBackground(Color.lightGray);
		this.finalStr = finalStr;
		this.emailAddress=emailAddress;
	
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setContentPane(getJContentPane());
		this.setTitle("Confirm  Registration");
		this.move(200, 200);
		this.setSize(500,400);
		Image im = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("image/toolicon.jpg"));
		this.setIconImage(im);

		this.setVisible(true);

		// TODO Auto-generated constructor stub
	}
	private javax.swing.JPanel getJContentPane() 
	{
		int xinc = 20;
		int yinc = 10;
		int xincdis=175;
		
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
		JLabel lable = new JLabel("( Please Enter Code sent to email address "+emailAddress + ")");
		lable.setFont(new Font("Arial",Font.PLAIN,12));
		lable.setBounds(xinc,yinc,550,15);	
		lable.setForeground(Color.black);

	//	jContentPane.add(lable,null);
		
		yinc = yinc + 40;

		JLabel lable1 = new JLabel("Pls Enter Code Here");
		//lable.setFont(new Font("Arial",Font.BOLD,12));
		lable1.setFont(new Font("Arial",Font.BOLD,12));
		
		lable1.setBounds(xinc,yinc,150,15);	
		lable1.setForeground(Color.black);

		jContentPane.add(lable1,null);

		
		code =  new JTextField("85086");
		lable.setFont(new Font("Arial",Font.PLAIN,12));
		
		code.setBounds(xinc+200,yinc,200,20);
		jContentPane.add(code,null);
		
		yinc = yinc + 40;
		
		add = new JButton();
		add.setBounds(xinc,yinc,150,20);
		add.setName("verify");
		add.setText("Verify");
		jContentPane.add(add,null);
		add.addActionListener(this);

		
		cancel = new JButton();
		cancel.setBounds(xinc+160,yinc,75,20);
		cancel.setName("cancel");
		cancel.setText("Cancel");
		jContentPane.add(cancel,null);
		cancel.addActionListener(this);
		
		return jContentPane;
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		JButton cb = (JButton)e.getSource();
		String namebut = cb.getName();
		
					
		if(namebut.equalsIgnoreCase("verify"))
		{
			int codenum=0;
			try
			{
				codenum =  Integer.parseInt(code.getText().trim());
			}
			catch(Exception e1)
			{
				MessageDiaglog msg = new MessageDiaglog("Pls enter a numeric code sent to your email address...");
				return;
			}
			if(validatecode(codenum))
			{
				doRegistration();
			}
			else
			{
				MessageDiaglog msg = new MessageDiaglog("Pls enter a valid code  sent to your email address...");
				return;
			}
			
		}
		else if(namebut.equalsIgnoreCase("cancel"))
		{
			System.exit(0);
		}
		
		
		
		
		
		
		// TODO Auto-generated method stub
		
	}
	public static void main(String[] args)
	{

	//	ConfirmRegistration confirm = new ConfirmRegistration("", "mkacholia@yahoo.com");
		for(int i=0;i<20;i++)
		{
	//		System.out.println(UserRegistration.generateCode());
		}
	}
	private void doRegistration()
	{
	/*	MessageDiaglog msg = new MessageDiaglog("correct code entered");
		if(true)
			return;
	*/	
		
		try
		{
			String retStr =  Utility.getUrlContent(StockConstants.ADDUSER+this.finalStr);
			retStr = retStr.trim();
			if(retStr.toString().equalsIgnoreCase("SUCCESS"))
			{
			//		StockConstants.COUNTRY = StockConstants.USERDETAIL.get("country")+"";						
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
	private boolean validatecode(int codenum)
	{
		if(codenum==StockConstants.UNIQUECODE)
		{
			return true;
		}
		else
		{	
			return false;
		}	
	}
	
}
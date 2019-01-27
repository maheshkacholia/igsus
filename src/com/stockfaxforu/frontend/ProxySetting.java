/*
 * Created on Sep 5, 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.frontend;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import com.stockfaxforu.util.StockConstants;
import com.stockfaxforu.util.*;


/**
 * @author sdeopu1
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ProxySetting extends JFrame
{

	private javax.swing.JPanel jContentPane = null;

	private javax.swing.JLabel jLabel = null;
	private javax.swing.JButton jButton = null;
	private javax.swing.JButton jButton1 = null;
	
	private javax.swing.JCheckBox jCheckBox = null;
	private javax.swing.JLabel jLabel1 = null;
	private javax.swing.JTextField jTextField = null;
	private javax.swing.JLabel jLabel2 = null;
	private javax.swing.JTextField jTextField1 = null;
	private javax.swing.JLabel jLabel3 = null;
	private javax.swing.JTextField jTextField2 = null;
	private javax.swing.JLabel jLabel4 = null;
	private javax.swing.JPasswordField jPasswordField = null;
	/**
	 * This is the default constructor
	 */
	private static ProxySetting proxysetting = null;
	public static ProxySetting getSingleton()
	{
		if(proxysetting==null)
			proxysetting = new ProxySetting();
		proxysetting.show();
		return proxysetting;
		 
	}
	public ProxySetting()
	{
		super();
		initialize();
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize()
	{
		this.setSize(369, 356);
		this.setContentPane(getJContentPane());
		this.setVisible(true);
		this.move(300, 300);
		this.setTitle("Proxy Setup");
		Image im = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("image/toolicon.jpg"));
		this.setIconImage(im);
 
		this.setIconImage(im);
		this.setVisible(true);

		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		this.addWindowListener(new java.awt.event.WindowAdapter()
		{
			public void windowClosing(java.awt.event.WindowEvent e)
			{
				// TODO Auto-generated stub windowClosing()

			}
		});
	}
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane()
	{
		if (jContentPane == null)
		{
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getJLabel(), null);
			jContentPane.add(getJButton(), null);
			jContentPane.add(getJButton1(), null);
		
			jContentPane.add(getJCheckBox(), null);
			jContentPane.add(getJLabel1(), null);
			jContentPane.add(getJTextField(), null);
			jContentPane.add(getJLabel2(), null);
			jContentPane.add(getJTextField1(), null);
			jContentPane.add(getJLabel3(), null);
			jContentPane.add(getJTextField2(), null);
			jContentPane.add(getJLabel4(), null);
			jContentPane.add(getJPasswordField(), null);

//setting value
			try
			{
				Properties p = new Properties();
				p.load(new java.io.FileInputStream(StockConstants.INSTALL_DIR + "/install.properties"));
				String proxyname = p.getProperty("proxyname");
				String port = p.getProperty("port");
				String userid = p.getProperty("userid");
				String password = p.getProperty("password");
				String proxy = p.getProperty("proxy");
				if(proxy != null && proxy.equals("yes"))
					jCheckBox.setSelected(true);
				else
					jCheckBox.setSelected(false);
				
				jTextField.setText(proxyname);
				jTextField1.setText(port);
				jTextField2.setText(userid);
				jPasswordField.setText(password);
				
				
			}
			catch(Exception e)
			{
			}


		}
		return jContentPane;
	}
	/**
	 * This method initializes jLabel
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel()
	{
		if (jLabel == null)
		{
			jLabel = new javax.swing.JLabel();
			jLabel.setBounds(20, 19, 90, 27);
			jLabel.setText("Proxy");
		}
		return jLabel;
	}
	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButton()
	{
		if (jButton == null)
		{
			jButton = new javax.swing.JButton();
			jButton.setBounds(50, 216, 87, 26);
			jButton.setText("submit");
			jButton.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					boolean b = jCheckBox.isSelected();
					String proxy="";
					if(b)
						proxy="yes";
					else
						proxy="no";
					StringBuffer sb = new StringBuffer();
					sb.append("proxyname="+jTextField.getText().trim()+"\n");
					sb.append("port="+jTextField1.getText().trim()+"\n");
					sb.append("userid="+jTextField2.getText().trim()+"\n");
					sb.append("password="+jPasswordField.getText().trim()+"\n");
					sb.append("proxy="+proxy+"\n");
					
					java.io.FileOutputStream filestream = null;
					File file = null;
					try
					{
						File f = new File(StockConstants.INSTALL_DIR);
						if(!f.exists())
							f.mkdirs();
						f = new File(StockConstants.INSTALL_DIR + "/install.properties");
						if(f.exists());
							f.delete();	
						filestream = new java.io.FileOutputStream(StockConstants.INSTALL_DIR + "/install.properties");
						filestream.write(sb.toString().getBytes());
						filestream.close();
						Utility.setAllConstants();
						ProxySetting.getSingleton().setVisible(false);
					}
					catch(Exception e1)
					{
					}
				}

			});
		}
		return jButton;
	}
	private javax.swing.JButton getJButton1()
	{
		if (jButton1 == null)
		{
			jButton1 = new javax.swing.JButton();
			jButton1.setBounds(175, 216, 87, 26);
			jButton1.setText("Close");
			jButton1.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					dispose();
				}

			});
		}
		return jButton1;
	}



	public static void main(String[] argv)
	{
		ProxySetting f = new ProxySetting();
	}
	/**
	 * This method initializes jCheckBox
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private javax.swing.JCheckBox getJCheckBox()
	{
		if (jCheckBox == null)
		{
			jCheckBox = new javax.swing.JCheckBox();
			jCheckBox.setBounds(131, 19, 31, 25);
		}
		return jCheckBox;
	}
	/**
	 * This method initializes jLabel1
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel1()
	{
		if (jLabel1 == null)
		{
			jLabel1 = new javax.swing.JLabel();
			jLabel1.setBounds(20, 52, 84, 24);
			jLabel1.setText("Proxy Name");
		}
		return jLabel1;
	}
	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getJTextField()
	{
		if (jTextField == null)
		{
			jTextField = new javax.swing.JTextField();
			jTextField.setBounds(134, 55, 216, 19);
		}
		return jTextField;
	}
	/**
	 * This method initializes jLabel2
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel2()
	{
		if (jLabel2 == null)
		{
			jLabel2 = new javax.swing.JLabel();
			jLabel2.setBounds(20, 97, 84, 16);
			jLabel2.setText("Proxy Port");
		}
		return jLabel2;
	}
	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getJTextField1()
	{
		if (jTextField1 == null)
		{
			jTextField1 = new javax.swing.JTextField();
			jTextField1.setBounds(134, 95, 85, 19);
		}
		return jTextField1;
	}
	/**
	 * This method initializes jLabel3
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel3()
	{
		if (jLabel3 == null)
		{
			jLabel3 = new javax.swing.JLabel();
			jLabel3.setBounds(20, 132, 84, 15);
			jLabel3.setText("User Id");
		}
		return jLabel3;
	}
	/**
	 * This method initializes jTextField2
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getJTextField2()
	{
		if (jTextField2 == null)
		{
			jTextField2 = new javax.swing.JTextField();
			jTextField2.setBounds(134, 131, 85, 19);
		}
		return jTextField2;
	}
	/**
	 * This method initializes jLabel4
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel4()
	{
		if (jLabel4 == null)
		{
			jLabel4 = new javax.swing.JLabel();
			jLabel4.setBounds(23, 165, 88, 20);
			jLabel4.setText("Password");
		}
		return jLabel4;
	}
	/**
	 * This method initializes jPasswordField
	 * 
	 * @return javax.swing.JPasswordField
	 */
	private javax.swing.JPasswordField getJPasswordField()
	{
		if (jPasswordField == null)
		{
			jPasswordField = new javax.swing.JPasswordField();
			jPasswordField.setBounds(137, 165, 126, 19);
		}
		return jPasswordField;
	}
} //  @jve:visual-info  decl-index=0 visual-constraint="10,10"

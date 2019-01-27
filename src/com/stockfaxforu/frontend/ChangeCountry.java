/*
 * Created on Mar 3, 2009
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.frontend;

import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.stockfaxforu.swingcomponent.IGSLabel;
import com.stockfaxforu.util.DownloadIndexesSymbolMap;
import com.stockfaxforu.util.IndexUtility;
import com.stockfaxforu.util.Utility;

/**
 * @author Mahesh
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ChangeCountry extends JFrame implements ActionListener
{
	public ChangeCountry()
	{
//		super(null);
//		setBackground(Color.lightGray);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setContentPane(getJContentPane());
		this.setTitle("Change Country");
		this.move(300, 300);
		this.setSize(300,300);
		Image im = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("image/toolicon.jpg"));
		this.setIconImage(im);

		this.setVisible(true);
	}
	private javax.swing.JPanel jContentPane = null;
	JComboBox country=null;
	private javax.swing.JPanel getJContentPane() 
	{
		int xinc = 10;
		int yinc = 10;
		int xincdis=150;

		if(jContentPane == null) 
		{
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
		}
		IGSLabel lable = new IGSLabel("Select Country :");
		lable.setBounds(xinc,yinc,150,15);	

		jContentPane.add(lable,null);

		String[] countryVec = Utility.getCountries();
		country = new JComboBox(countryVec);
		country.setFont(new Font("Arial",Font.BOLD,12));
		country.setBounds(xinc+xincdis,yinc,100,20);	
		jContentPane.add(country,null);

				yinc = yinc + 40;
		JButton add = new JButton();
		add.setBounds(xinc,yinc,150,20);
		add.setName("Change");
		add.setText("Change");
		jContentPane.add(add,null);
		add.addActionListener(this);
		
				
		JButton cancel = new JButton();
		cancel.setBounds(xinc+160,yinc,75,20);
		cancel.setName("cancel");
		cancel.setText("Cancel");
		jContentPane.add(cancel,null);
		cancel.addActionListener(this);
	
		return jContentPane;
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent arg0)
	{
		JButton but =(JButton)arg0.getSource();
		String name = but.getName();
		if(name.equalsIgnoreCase("Change"))
		{
			String countryName = (String)country.getSelectedItem();
			String countryCode = Utility.getCountryCode(countryName);
			DownloadIndexesSymbolMap.saveIndex(countryCode);
			DownloadIndexesSymbolMap.saveMappingFile(countryCode);
			
			IndexUtility.loadIndexes();
			Utility.loadStock();
			MainScreen.getSingleton().addTreeAdPanel();
			MainScreen.getSingleton().doReSize();
		

		}
		else if (name.equalsIgnoreCase("cancel"))
		{
			this.dispose();	
		}		
		// TODO Auto-generated method stub
		
	}
}	

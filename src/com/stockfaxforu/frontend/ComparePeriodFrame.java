/*
 * Created on Jun 30, 2009
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.frontend;

import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.stockfaxforu.getolddataforu.util.UtilityForOldData;
import com.stockfaxforu.swingcomponent.IGSLabel;
import com.stockfaxforu.util.Utility;

/**
 * @author Mahesh
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ComparePeriodFrame extends JFrame implements ActionListener
{

	/**
	 * @param panel1
	 */
	PanelForGraphImpl panel;
	public ComparePeriodFrame(PanelForGraphImpl panel1)
	{
		this.panel = panel1;
		this.setTitle("Compare Periods");
		setBackground(Color.lightGray);
		this.setSize(350,300);
		this.move(50,50);
		Image im = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("image/toolicon.jpg"));
		this.setIconImage(im);
	
		this.setContentPane(getJContentPane());
				this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	  JPanel jcontentpane=null;
	JComboBox yearcombo;
	JComboBox startmonthcombo;
	JComboBox endmonthcombo;
	  private javax.swing.JPanel getJContentPane() 
	  {
		  if(jcontentpane==null)
		  {
			  jcontentpane = new JPanel();
			  jcontentpane.setLayout(null);
		  }
		  int x= 10;
		  int y=10;
		  int xpossec = 200;
			IGSLabel label1 = new IGSLabel("Start Year:");
			label1.setBounds(x,y,200,20);
			jcontentpane.add(label1);
			
			
			yearcombo = new JComboBox(Utility.getYear());
			yearcombo.setName("yearcombo");
			yearcombo.setBounds(xpossec,y,75,20);
		jcontentpane.add(yearcombo);
			yearcombo.addActionListener(this);
			
			y = y + 30;
		IGSLabel label2 = new IGSLabel("Start Month:");
		label2.setBounds(x,y,200,20);
		jcontentpane.add(label2);

		String months[] = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
		startmonthcombo = new JComboBox(months);
		
		startmonthcombo.setName("startmonthcombo");
		startmonthcombo.setBounds(xpossec,y,50,20);
		startmonthcombo.addActionListener(this);
		jcontentpane.add(startmonthcombo);
			
		y = y + 30;
			IGSLabel label3 = new IGSLabel("End Month:");
			label3.setBounds(x,y,200,20);
			jcontentpane.add(label3);

			endmonthcombo = new JComboBox(months);
		endmonthcombo.setName("endmonthcombo");
		endmonthcombo.setBounds(xpossec,y,50,20);
		endmonthcombo.addActionListener(this);
		jcontentpane.add(endmonthcombo);
		y = y + 50;
		JButton apply = new JButton("Apply");
		apply.setName("apply");
		jcontentpane.add(apply);
		apply.addActionListener(this);
		apply.setBounds(10,y,100,25);
		
		JButton cancel = new JButton("Cancel");
		cancel.setName("cancel");
		jcontentpane.add(cancel);
		cancel.addActionListener(this);
		cancel.setBounds(150,y,100,25);

		
//		this.addComponentListener(this);
		return jcontentpane;

	  
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
			if(name.equalsIgnoreCase("apply"))
			{
				String startyear = (String)yearcombo.getSelectedItem();
				String startmonth = Utility.getIntMonth((String)startmonthcombo.getSelectedItem())+"";
				String endmonth = Utility.getIntMonth((String)endmonthcombo.getSelectedItem())+"";
				
				
				String startd =   startmonth + "-" + "01";
				String endd =  endmonth + "-" + "28";
				int yy = Integer.parseInt(startyear);
				this.panel.setDate(yy,startd, endd);
				MainScreen.getSingleton().doReSize();
			}
			else if(name.equalsIgnoreCase("cancel"))
			{
				this.dispose();
			}
		}	
		// TODO Auto-generated method stub
		
	}
	public static void main(String[] args)
	{
		ComparePeriodFrame compare = new ComparePeriodFrame(null);
		
	}
}

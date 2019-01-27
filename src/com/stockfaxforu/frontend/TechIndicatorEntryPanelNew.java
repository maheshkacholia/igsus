/*
 * Created on Feb 19, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.frontend;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Label;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.stockfaxforu.component.*;
import com.stockfaxforu.component.Indicator;
import com.stockfaxforu.util.ManageIndicator;
import com.stockfaxforu.util.Utility;

/**
 * @author sdeopura
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class TechIndicatorEntryPanelNew extends JFrame implements ActionListener
{
	/**
	 * This is the default constructor
	 */
	JTextField[] variable =  null;
	JButton add = null;
	JButton cancel = null;
	JComboBox indiactorList = null;
	PanelForGraphImpl maingraph=null;
	String indicatorName=null;
	Indicator selInd = null;
	JLabel[] periods = new JLabel[5];
	ManageIndicatorInterface manageind;
	public TechIndicatorEntryPanelNew(Indicator selInd,ManageIndicatorInterface manageind)
	{
	
//		super(null);
		setBackground(Color.lightGray);
		this.selInd = selInd;
		this.manageind = manageind;
		this.maingraph = maingraph;
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setContentPane(getJContentPane());
		this.setTitle("Add Indicator");
		this.move(300, 300);
		this.setSize(300,300);
		Image im = java.awt.Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("image/toolicon.jpg"));
		this.setIconImage(im);
	
		this.setVisible(true);
	}
	private javax.swing.JPanel jContentPane = null;
	private javax.swing.JPanel getJContentPane() 
	{
		int xinc = 10;
		int yinc = 20;
		int xincdis=70;
		variable =  new JTextField[3];

		if(jContentPane == null) 
		{
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
		}
		JLabel lable = new JLabel("Indicator :"+selInd.indicatorName);
		lable.setFont(new Font("Arial",Font.BOLD,12));
		lable.setForeground(Color.black);
		lable.setBounds(xinc,yinc,200,15);	
		jContentPane.add(lable,null);
		int[] defaultvalue = ManageIndicator.getDefaultValue(selInd.indicatorName);
		String[] defaultname = ManageIndicator.getPeriodName(selInd.indicatorName);
		
		for(int i=0;i<selInd.noofperiods;i++)
		{
			yinc =yinc +  40;
			periods[i] = new JLabel(defaultname[i]);
			periods[i].setFont(new Font("Arial",Font.BOLD,12));
			periods[i].setForeground(Color.black);
			periods[i].setBounds(xinc,yinc,100,15);	
			jContentPane.add(periods[i],null);

			variable[i] = new JTextField();
			variable[i].setText(defaultvalue[i]+"");
			variable[i].setFont(new Font("Arial",Font.BOLD,12));
			variable[i].setForeground(Color.black);
			variable[i].setBounds(xinc+xincdis,yinc,100,15);	
		//	variable[i].enable();
			jContentPane.add(variable[i],null);
			
		}
		yinc = yinc + 50;
		add = new JButton();
		add.setBounds(xinc,yinc,75,20);
		add.setName("add");
		add.setText("Add");
		jContentPane.add(add,null);
		add.addActionListener(this);

		
		cancel = new JButton();
		cancel.setBounds(xinc+100,yinc,75,20);
		cancel.setName("cancel");
		cancel.setText("Cancel");
		jContentPane.add(cancel,null);
		cancel.addActionListener(this);
		 
		return jContentPane;	 
	
	}	


	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize()
	{
		this.setSize(300, 200);
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e)
	{
		Object o = (Object)e.getSource();
		if(o instanceof JButton)
		{
			JButton cb = (JButton)e.getSource();
			String name = cb.getName();
			if(name.equalsIgnoreCase("add"))
			{
				if(!checkValue())
				{
					if(selInd.noofperiods==1)
					{
						selInd.period1 = Integer.parseInt(variable[0].getText().trim());
					}
					else if(selInd.noofperiods==2)
					{
						selInd.period1 = Integer.parseInt(variable[0].getText().trim());
						selInd.period2 = Integer.parseInt(variable[1].getText().trim());

					}
					else if(selInd.noofperiods==3)
					{
						selInd.period1 = Integer.parseInt(variable[0].getText().trim());
						selInd.period2 = Integer.parseInt(variable[1].getText().trim());
						selInd.period3 = Integer.parseInt(variable[2].getText().trim());
					
					}
					manageind.addIndicator(selInd);
					
					this.dispose();
					((ManageIndicatorsScreen)this.manageind).dispose();
				}
			}
			if(name.equalsIgnoreCase("cancel"))
			{
				this.dispose();
			
			}
						
		}
		else if(o instanceof JComboBox)
		{
			JComboBox cb = (JComboBox)e.getSource();
			String name = cb.getName();
//			String indname = (String)cb.getSelectedItem();
			String indname	=	ManageIndicator.indicatorVector.elementAt(cb.getSelectedIndex())+"";
			Indicator indobj = ManageIndicator.getIndicatorObject(indname);
			int[] defaultvalue = ManageIndicator.getDefaultValue(indname);
			String[] textname = ManageIndicator.getPeriodName(indname);
			selInd = indobj;
			int noofperiods = indobj.noofperiods;
			
			for(int i=0;i<variable.length;i++)
			{
				variable[i].disable();
				variable[i].setVisible(false);
				periods[i].setVisible(false); 
				 
			}
			for(int i=0;i<noofperiods;i++)
			{
				variable[i].enable(); 
				variable[i].setText(defaultvalue[i]+"");
				periods[i].setText(textname[i]); 
				variable[i].setVisible(true);
				periods[i].setVisible(true); 

			}

		}
		// TODO Auto-generated method stub
		
	}
	/**
	 * @return
	 */
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
			
			
			TechIndicatorEntryPanelNew d = new TechIndicatorEntryPanelNew(null,null);
			frame.getContentPane().add(d);
			frame.pack();
			//		frame.setBounds(0,0,1000,700);
			frame.setVisible(true);

		}
	
}  //  @jve:visual-info  decl-index=0 visual-constraint="4,11"

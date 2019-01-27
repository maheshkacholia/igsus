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
import java.util.Collection;
import java.util.Collections;
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
public class TechIndicatorRemovePanel extends JFrame implements ActionListener
{
	/**
	 * This is the default constructor
	 */
	JTextField[] variable =  null;
	JButton add = null;
	JButton cancel = null;
	JComboBox indiactorList = null;
	GraphComponentContainer maingraph=null;
	String indicatorName=null;
	Indicator selInd = null;
	public TechIndicatorRemovePanel()
	{
		super();
		Image im = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("image/toolicon.jpg"));
		this.setIconImage(im);

		initialize();
	}
	public TechIndicatorRemovePanel(GraphComponentContainer maingraph)
	{
//		super(null);
		setBackground(Color.lightGray);
		this.maingraph = maingraph;
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setContentPane(getJContentPane());
		this.setTitle("Remove Indicator");
		Image im = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("image/toolicon.jpg"));
		this.setIconImage(im);

		this.move(300, 300);
		this.setSize(400,300);
	
		this.setVisible(true);
	}
	private javax.swing.JPanel jContentPane = null;
	private javax.swing.JPanel getJContentPane() 
	{
		int xinc = 10;
		int yinc = 20;
		int xincdis=100;
		variable =  new JTextField[3];

		if(jContentPane == null) 
		{
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
		}
		JLabel lable = new JLabel("Indicator List :");
		lable.setFont(new Font("Arial",Font.BOLD,12));
		lable.setForeground(Color.black);
		lable.setBounds(xinc,yinc,100,15);	
		jContentPane.add(lable,null);

		Vector indiVec = maingraph.getIndicators();
		
		indiactorList = new JComboBox((Vector)indiVec);
		indiactorList.setFont(new Font("Arial",Font.BOLD,10));
		indiactorList.setBounds(xinc+xincdis,yinc,200,20);	
		jContentPane.add(indiactorList,null);
		indiactorList.addActionListener(this);


		yinc = yinc +40;
		
		setSize(400, 400);
		add = new JButton();
		add.setBounds(xinc,yinc,140,20);
		add.setName("remove");
		add.setText("Remove");
		jContentPane.add(add,null);
		add.addActionListener(this);

		
		cancel = new JButton();
		cancel.setBounds(xinc+150,yinc,75,20);
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
		this.setSize(300, 250);
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
			if(name.equalsIgnoreCase("remove"))
			{
				String indname = (String)indiactorList.getSelectedItem();
				
				Indicator sel = (Indicator)maingraph.indHash.get(indname);
				if(sel != null)
					maingraph.removeIndicator(sel);
				this.dispose();
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
			String indname = (String)cb.getSelectedItem();
			Indicator indobj = ManageIndicator.getIndicatorObject(indname);
			selInd = indobj;
			int noofperiods = indobj.noofperiods;
			for(int i=0;i<variable.length;i++)
			{
				variable[i].disable(); 
			}
			for(int i=0;i<noofperiods;i++)
			{
				variable[i].enable(); 
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
			
			
			TechIndicatorRemovePanel d = new TechIndicatorRemovePanel(null);
			frame.getContentPane().add(d);
			frame.pack();
			//		frame.setBounds(0,0,1000,700);
			frame.setVisible(true);

		}
	
}  //  @jve:visual-info  decl-index=0 visual-constraint="4,11"

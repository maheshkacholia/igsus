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
public class TechIndicatorEntryPanel extends JFrame implements ActionListener
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
	public TechIndicatorEntryPanel()
	{
		super();
		Image im = java.awt.Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("image/toolicon.jpg"));
		this.setIconImage(im);

		initialize();
	}
	public TechIndicatorEntryPanel(PanelForGraphImpl maingraph)
	{
	
//		super(null);
		setBackground(Color.lightGray);
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
		JLabel lable = new JLabel("Indicator :");
		lable.setFont(new Font("Arial",Font.BOLD,12));
		lable.setForeground(Color.black);
		lable.setBounds(xinc,yinc,100,15);	
		jContentPane.add(lable,null);

		Vector indiName = ManageIndicator.getIndicatorName();
		Integer[] intArray = new Integer[indiName.size()];
		for (int i = 0; i < indiName.size(); i++)
		{
			intArray[i] = new Integer(i);
		}
		indiactorList = new JComboBox(intArray);
		ComboBoxRenderer coboren = new ComboBoxRenderer();
		coboren.tooltips = ManageIndicator.getIndicatorDesc();
		indiactorList.setFont(new Font("Arial",Font.BOLD,10));
		indiactorList.setBounds(xinc+xincdis,yinc,200,20);	
		indiactorList.setRenderer(coboren);
		jContentPane.add(indiactorList,null);
		indiactorList.addActionListener(this);
		
		String indname	=	ManageIndicator.indicatorVector.elementAt(indiactorList.getSelectedIndex())+"";

		selInd = ManageIndicator.getIndicatorObject(indname);
	

		yinc =yinc +  40;
		periods[0] = new JLabel("No of Days:");
		periods[0].setFont(new Font("Arial",Font.BOLD,12));
		periods[0].setForeground(Color.black);
		periods[0].setBounds(xinc,yinc,100,15);	
		jContentPane.add(periods[0],null);

		variable[0] = new JTextField("14");
		variable[0].setFont(new Font("Arial",Font.BOLD,12));
		variable[0].setForeground(Color.black);
		variable[0].setBounds(xinc+xincdis,yinc,100,15);	
		variable[0].enable();
		jContentPane.add(variable[0],null);

		yinc =yinc +  40;
		periods[1] = new JLabel("Period 2:");
		periods[1].setFont(new Font("Arial",Font.BOLD,12));
		periods[1].setForeground(Color.black);
		periods[1].setBounds(xinc,yinc,100,15);
		periods[1].setVisible(false);	
		jContentPane.add(periods[1],null);

		variable[1] = new JTextField("0");
		variable[1].setFont(new Font("Arial",Font.BOLD,12));
		variable[1].setForeground(Color.black);
		variable[1].setBounds(xinc+xincdis,yinc,100,15);	
		variable[1].setVisible(false);
		variable[1].disable();

		jContentPane.add(variable[1],null);


		yinc =yinc +  40;
		periods[2] = new JLabel("Period 3:");
		periods[2].setFont(new Font("Arial",Font.BOLD,12));
		periods[2].setForeground(Color.black);
		periods[2].setBounds(xinc,yinc,100,15);	
		periods[2].setVisible(false);	
		jContentPane.add(periods[2],null);

		variable[2] = new JTextField("0");
		variable[2].setFont(new Font("Arial",Font.BOLD,12));
		variable[2].setForeground(Color.black);
		variable[2].setBounds(xinc+xincdis,yinc,100,15);	
		variable[2].disable();
		variable[2].setVisible(false);

		jContentPane.add(variable[2],null);

		yinc = yinc +40;
		
		setSize(300, 300);
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
					maingraph.addIndicator(selInd);
					
					this.dispose();
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
			
			
			TechIndicatorEntryPanel d = new TechIndicatorEntryPanel(null);
			frame.getContentPane().add(d);
			frame.pack();
			//		frame.setBounds(0,0,1000,700);
			frame.setVisible(true);

		}
	
}  //  @jve:visual-info  decl-index=0 visual-constraint="4,11"

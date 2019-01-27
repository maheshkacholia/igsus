/*
 * Created on Jun 22, 2009
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.config;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.stockfaxforu.component.GraphComponentContainer;
import com.stockfaxforu.component.MYDimension;
import com.stockfaxforu.component.MainGraphComponent;
import com.stockfaxforu.formulabuilder.MessageDiaglog;
import com.stockfaxforu.swingcomponent.IGSLabel;
import com.stockfaxforu.util.Utility;

/**
 * @author Mahesh
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class SetDrawingComponentProperty extends JFrame implements ActionListener
{
	/**
	 * This is the default constructor
	 */
	JTextField note =  null;
	JButton add = null;
	JButton cancel = null;
	JComboBox fontcolorcombo,fillcolorcombo,linecolorcombo;
	GraphComponentContainer macdgraph=null;
	String fontcolorcombostr,fillcolorcombostr,linecolorcombostr;
	String indicatorName=null;
	MYDimension	mydimension;
	public SetDrawingComponentProperty()
	{
		super();
		initialize();
	}
	public SetDrawingComponentProperty(GraphComponentContainer macdgraph)
	{
		mydimension = macdgraph.mydimension;		
		if(mydimension == null)
			{
				MessageDiaglog msg = new MessageDiaglog("No Component Selected,Pls select drawing component");
				return;
			}

		this.macdgraph = macdgraph;
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setContentPane(getJContentPane());
		this.setTitle("Update Drawing Component Property");
		this.move(300, 300);
		this.setSize(300,250);
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
		note =  new JTextField();

		if(jContentPane == null) 
		{
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
		}
		JLabel lable = new IGSLabel("Text Color :");
		lable.setBounds(xinc,yinc,100,15);	
		jContentPane.add(lable,null);
		
		xinc = xinc + 100;
		fontcolorcombo = new JComboBox(ConfigUtility.colorStr);
		fontcolorcombo.setBounds(xinc,yinc,100,20);
		fontcolorcombo.setName("fontcolorcombostr")	;
		fontcolorcombo.setSelectedItem(ConfigUtility.getColorString(mydimension.fontcolor));

		fontcolorcombo.addActionListener(this);
		jContentPane.add(fontcolorcombo,null);
		
		yinc = yinc + 30;
		xinc = 10;
		JLabel lable1 = new IGSLabel("Line Color :");
		lable1.setBounds(xinc,yinc,100,15);	
		jContentPane.add(lable1,null);

		xinc = xinc + 100;
		linecolorcombo = new JComboBox(ConfigUtility.colorStr);
		linecolorcombo.setBounds(xinc,yinc,100,20);	
		linecolorcombo.setName("linecolorcombostr");
		linecolorcombo.setSelectedItem(ConfigUtility.getColorString(mydimension.boundarycolor ));

		linecolorcombo.addActionListener(this);
		
		jContentPane.add(linecolorcombo,null);
		yinc = yinc + 30;
		xinc = 10;
		JLabel lable2 = new IGSLabel("Fill Color :");
		lable2.setBounds(xinc,yinc,100,15);	
		jContentPane.add(lable2,null);


		xinc = xinc + 100;
		fillcolorcombo = new JComboBox(ConfigUtility.colorStr);
		fillcolorcombo.setBounds(xinc,yinc,100,20);	
		fillcolorcombo.setName("fillcolorcombostr");
		fillcolorcombo.setSelectedItem(ConfigUtility.getColorString(mydimension.fillcolor ));

		fillcolorcombo.addActionListener(this);
		jContentPane.add(fillcolorcombo,null);
		yinc = yinc + 30;

		xinc = 10;	
		add = new JButton();
		add.setBounds(xinc,yinc,75,20);
		add.setName("add");
		add.setText("OK");
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
			MYDimension	mydimension = macdgraph.mydimension;		
			if(mydimension == null)
				{
					MessageDiaglog msg = new MessageDiaglog("No Component Selected,Pls select drawing component");
				}
			JButton cb = (JButton)e.getSource();
			String name = cb.getName();
			Color fontcolor=null,linecolor=null,fillcolor=null;
			if(name.equalsIgnoreCase("add"))
			{
				if(fontcolorcombostr != null && !fontcolorcombostr.trim().equals(""))
				{
					fontcolor = ConfigUtility.getColor((String)fontcolorcombo.getSelectedItem());
				}
				else
				{
					fontcolor = mydimension.fontcolor;
				}
				if(linecolorcombostr != null && !linecolorcombostr.trim().equals(""))
				{
					linecolor = ConfigUtility.getColor((String)linecolorcombo.getSelectedItem());
				}
				else
				{
					linecolor = mydimension.boundarycolor;		
				}
				if(fillcolorcombostr != null && !fillcolorcombostr.trim().equals(""))
				{
					fillcolor = ConfigUtility.getColor((String)fillcolorcombo.getSelectedItem());
				}
				else
				{
					fillcolor = mydimension.fillcolor;
				}
				mydimension.setColor(fontcolor, linecolor, fillcolor);
				macdgraph.repaint();
			}
			else if(name.equalsIgnoreCase("cancel"))
			{
				this.dispose();
			}

		}
		else if(o instanceof JComboBox)
		{
			JComboBox cb1 = (JComboBox)e.getSource();
			String name1 = cb1.getName();
	//		// ln(name1);

			if(name1.equalsIgnoreCase("linecolorcombostr"))
			{
				linecolorcombostr = (String)cb1.getSelectedItem();
			}
			else if(name1.equalsIgnoreCase("fillcolorcombostr"))
			{
				fillcolorcombostr = (String)cb1.getSelectedItem();
			}
			else if(name1.equalsIgnoreCase("fontcolorcombostr"))
			{
				fontcolorcombostr = (String)cb1.getSelectedItem();
			}
			 
		}
			
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
			SetDrawingComponentProperty s = new SetDrawingComponentProperty(null);
		}
}
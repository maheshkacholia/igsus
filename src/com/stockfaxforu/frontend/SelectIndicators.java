/*
 * Created on Jul 13, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.frontend;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.stockfaxforu.formulabuilder.FormulaBuilderScreen;
import com.stockfaxforu.formulabuilder.IndiactorParaEntry;
import com.stockfaxforu.formulabuilder.ManageVariable;
import com.stockfaxforu.formulabuilder.MessageDiaglog;

/**
 * @author sdeopura
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class SelectIndicators extends FormulaBuilderScreen implements ListSelectionListener,ActionListener
{
	public JList indiactorList10,selectededindiactorList10;
	public Vector indiactorVector,selectededindiactorVector;
	public JPanel jContentPane;
	public static void main(String[] args)
	{
		SelectIndicators sel = new SelectIndicators();
		
	}
	public  SelectIndicators()
	{
		
		Image im = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("image/toolicon.jpg"));
		this.setIconImage(im);

		//		super(null);
		this.setBackground(Color.lightGray);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setContentPane(getJContentPane());
		this.setTitle("Select Indicator List");
		this.move(300, 300);
		this.setSize(650, 450);
		this.setVisible(true);
		this.setResizable(false);
	}
	public javax.swing.JPanel getJContentPane()
	{

		
			if(jContentPane==null)
			{
				jContentPane = new JPanel();
				jContentPane.setLayout(null);
				
			}
			int xinc = 50;
			int yinc = 20;
			JLabel label1 = new JLabel("Select Indicator");
			label1.setBounds(xinc,yinc,200,20);
			jContentPane.add(label1,null);
			
	
			
			yinc=50;
			indiactorVector = ManageVariable.getIndicatorName();
			indiactorList10 = new JList(indiactorVector);
			indiactorList10.setFont(new Font("Arial",Font.BOLD,10));
			indiactorList10.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			indiactorList10.addListSelectionListener(this);
			JScrollPane scroolpane = new JScrollPane(indiactorList10);
			scroolpane.setBounds(xinc,yinc,200,300);
			jContentPane.add(scroolpane,null);

			xinc = xinc + 225;
			JButton select = new JButton(">>");
			select.setFont(new Font("Arial", Font.BOLD, 12));
			select.setBounds(xinc , yinc+150,100, 20);
			select.addActionListener(this);
			select.setName("selectformula");
			jContentPane.add(select);
			
			JButton deselect = new JButton("<<");
			deselect.setFont(new Font("Arial", Font.BOLD, 12));
			deselect.setBounds(xinc , yinc+180, 100, 20);
			deselect.addActionListener(this);
			deselect.setName("deselectformula");
			jContentPane.add(deselect,null);

			xinc =xinc+125;

			JLabel label2 = new JLabel("Selected Indicator");
			label2.setBounds(xinc,20,200,20);
			jContentPane.add(label2,null);

			selectededindiactorVector = ManageVariable.getSelectedIndicatorForDisplay();
			if
			(selectededindiactorVector==null)
				selectededindiactorVector = new Vector();
			selectededindiactorList10 = new JList(selectededindiactorVector);
			selectededindiactorList10.setFont(new Font("Arial",Font.BOLD,10));
			selectededindiactorList10.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			selectededindiactorList10.addListSelectionListener(this);
			JScrollPane scroolpane1 = new JScrollPane(selectededindiactorList10);
			scroolpane1.setBounds(xinc,yinc,200,300);
			jContentPane.add(scroolpane1,null);
			
			yinc = yinc + 325;
			xinc = 50;
			JButton apply = new JButton("Apply");
			apply.setFont(new Font("Arial", Font.BOLD, 12));
			apply.setBounds(xinc , yinc,100, 20);
			apply.addActionListener(this);
			apply.setName("apply");
			jContentPane.add(apply);
			
			xinc = xinc+125;
			JButton cancel = new JButton("Cancel");
			cancel.setFont(new Font("Arial", Font.BOLD, 12));
			cancel.setBounds(xinc , yinc,100, 20);
			cancel.addActionListener(this);
			cancel.setName("cancel");
			jContentPane.add(cancel);
	

			return jContentPane;
	}
	/* (non-Javadoc)
	 * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
	 */
	public void valueChanged(ListSelectionEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent arg0)
	{
		if(!(arg0.getSource() instanceof JButton))
			return;
		String name = ((JButton)arg0.getSource()).getName();
		
		if(name.equalsIgnoreCase("selectformula"))
		{
			String s = this.indiactorList10.getSelectedValue() + "";
			if(s==null)
			{
				JFrame mesg = new MessageDiaglog("No Operation is Selected,Pls Select a operation");
				return ;					
			}
			else
			{
				JFrame frme = new IndiactorParaEntry(s,this);		

			}
		}
		else if(name.equalsIgnoreCase("deselectformula"))
		{
			String s = (String)selectededindiactorList10.getSelectedValue();
			if(s==null)
			{
				JFrame mesg = new MessageDiaglog("No Operation is Selected,Pls Select a operation");
				return ;					
			}
			else
			{
				selectededindiactorVector.remove(s);
				selectededindiactorList10.setListData(selectededindiactorVector);
			}
			
		}
		else if(name.equalsIgnoreCase("apply"))
		{
			ManageVariable.saveIndicatorList(selectededindiactorVector);
			this.dispose();
		}
		else if(name.equalsIgnoreCase("cancel"))
		{
			this.dispose();
		}
		
	}

}

package com.stockfaxforu.scan.frontend;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import com.stockfaxforu.util.Utility;
public class CheckBoxGridPanelScan extends JPanel implements ActionListener
{
	JCheckBox[] indname=null;
	public CheckBoxGridPanelScan()
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
			this.setSize(300, 200);
		}
	
	public JCheckBox[] getAddedCheckBox()
	{
		return indname;
	}
	public CheckBoxGridPanelScan(int x2,int y2,int col,int row,String title,Vector butNames)
	{
		super(new GridLayout(col,row));
		setPreferredSize(new Dimension(x2,y2));

		TitledBorder titled;

			 titled = BorderFactory.createTitledBorder(title);
			this.setBorder(titled);	
 		indname = new JCheckBox[butNames.size()];
		for(int i=0;i<indname.length;i++)
		{
			indname[i] = new JCheckBox(butNames.elementAt(i).toString());
			indname[i].setFont(new Font("Arial",Font.BOLD,9));
//			symbollist.setBackground(Color.darkGray);
//			symbollist.setForeground(Color.white);
//			symbollist1.setToolTipText("Select Stock you want to watch");
			indname[i].setName(butNames.elementAt(i).toString());
			indname[i].addActionListener(this);
			add( indname[i]);
			
		}

	}
	public static void main(String[] args)
	{
		JFrame frame = new JFrame("Test angled lines");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		CheckBoxGridPanelScan d = new CheckBoxGridPanelScan(200,200,0,2,"Price Up",null);
		frame.getContentPane().add(d);
		frame.pack();
		//		frame.setBounds(0,0,1000,700);
		frame.setVisible(true);
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent arg0)
	{
		
	}}

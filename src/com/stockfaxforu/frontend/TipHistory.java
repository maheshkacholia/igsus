/*
 * Created on Jul 13, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.frontend;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.stockfaxforu.formulabuilder.ManageVariable;
import com.stockfaxforu.formulabuilder.MessageDiaglog;
import com.stockfaxforu.strategy.BuySellStrategy;
import com.stockfaxforu.util.Utility;

/**
 * @author sdeopura
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class TipHistory extends JFrame
{

	JPanel jContentPane=null;
	String tipHis;
	public TipHistory(String tipHis)
	{
		Image im = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("image/toolicon.jpg"));
		this.setIconImage(im);
		this.setTitle("Tip History");
		this.tipHis = tipHis;
		setBackground(Color.lightGray);
		this.setSize(1000,200);
		setBackground(Color.lightGray);

		this.move(200,200);
		this.setContentPane(getJContentPane());
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			
	}
	private javax.swing.JPanel getJContentPane() 
	{
		if(jContentPane == null) 
		{
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
		}
		String[] columnNames = {"Time","Tip"};
		
		StringTokenizer st = new StringTokenizer(tipHis,"\n");
		Vector v = new Vector();
		while(st.hasMoreTokens())
		{
			v.addElement(st.nextToken());
		}
		
		String[][] dataValues = new String[v.size()][2];
		
		for(int i=0;i<v.size();i++)
		{
			String s = (String)v.elementAt(i);	
			st = new StringTokenizer(s,"|");
			dataValues[i][0] = st.nextToken();
			dataValues[i][1] = st.nextToken();
			
		}
		
		
		JTable table = new JTable( dataValues, columnNames );
	
		// Add the table to a scrolling pane
		JScrollPane scrollPane = JTable.createScrollPaneForTable( table );
		Dimension d = scrollPane.getPreferredSize();
//		this.setSize(d);
		scrollPane.setBounds(0,0,1000,200);
					
		jContentPane.add( scrollPane, null );
		return jContentPane;				
	}
	public static void main(String[] args)
	{
		TipHistory ind = new TipHistory("TCS");
	}
}

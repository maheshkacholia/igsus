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
public class ShowResultInTable extends JFrame
{

	JPanel jContentPane=null;
	String tipHis;
	public ShowResultInTable(String tipHis,String title)
	{
		Image im = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("image/toolicon.jpg"));
		this.setIconImage(im);
		this.setTitle(title);
		this.tipHis = tipHis;
		setBackground(Color.lightGray);
		this.setSize(1000,900);
		setBackground(Color.lightGray);

		this.move(100,100);
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
		String[] columnNames = null;
		String[] row = null;
		
		StringTokenizer st = new StringTokenizer(tipHis,"\n");
		Vector v = new Vector();
		int pos=0;
		while(st.hasMoreTokens())
		{
			String line = st.nextToken();
			StringTokenizer linest = new StringTokenizer(line,",");
			if(pos==0)
			{
				Vector  v1 = new Vector();
				while(linest.hasMoreTokens())
				{
					v1.add((String)linest.nextToken());
				}
				
				columnNames = new String[v1.size()];
				for(int i=0;i<v1.size();i++)
				{
					columnNames[i] = (String)v1.get(i);
					
				}
				pos++;
			}
			else
			{
				Vector  v1 = new Vector();
				while(linest.hasMoreTokens())
				{
					v1.add((String)linest.nextToken());
				}
				
				row = new String[v1.size()];
				for(int i=0;i<v1.size();i++)
				{
					row[i] = (String)v1.get(i);
					
				}
				v.add(row);
			}
		}
		
		String[][] dataValues = new String[v.size()][columnNames.length];
		
		for(int i=0;i<v.size();i++)
		{
			dataValues[i] = (String [])v.get(i);
		}
		
		
		JTable table = new JTable( dataValues, columnNames );
	
		// Add the table to a scrolling pane
		JScrollPane scrollPane = JTable.createScrollPaneForTable( table );
		Dimension d = scrollPane.getPreferredSize();
//		this.setSize(d);
		scrollPane.setBounds(0,0,1000,900);
					
		jContentPane.add( scrollPane, null );
		return jContentPane;				
	}
	public static void main(String[] args)
	{
		ShowResultInTable show = new ShowResultInTable("a,b,c\n1,2,3","a");
	}
}

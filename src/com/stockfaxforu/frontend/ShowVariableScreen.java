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
import java.util.HashMap;
import java.util.Iterator;
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
public class ShowVariableScreen extends JFrame
{

	JPanel jContentPane=null;
	HashMap variable=null;
	public ShowVariableScreen(HashMap variable)
	{
		Image im = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("image/toolicon.jpg"));
		this.setIconImage(im);
		this.setTitle("Variable Inspect Screen");
		this.variable = variable;
		setBackground(Color.lightGray);
		this.setSize(300,500);
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
		String[] columnNames = {"Variable","Value"};
		String[][] dataValues = new String[this.variable.size()][2];		

		Iterator it = this.variable.keySet().iterator();
		int i=0;
		while(it.hasNext())
		{
			String s = (String)it.next();	
			if(this.variable.get(s) != null && this.variable.get(s) instanceof String)
			{
				String s1 = (String)this.variable.get(s);
				dataValues[i][0] = s;

				dataValues[i][1] = s1;
				i++;
					
			}
		}
		
		
		JTable table = new JTable( dataValues, columnNames );
	
		// Add the table to a scrolling pane
		JScrollPane scrollPane = JTable.createScrollPaneForTable( table );
		Dimension d = scrollPane.getPreferredSize();
		this.setSize(d);
		scrollPane.setBounds(0,0,this.getWidth(),this.getHeight());
					
		jContentPane.add( scrollPane, null );
		return jContentPane;				
	}
	public static void main(String[] args)
	{
	}
}

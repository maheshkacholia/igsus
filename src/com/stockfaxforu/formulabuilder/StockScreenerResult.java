/*
 * Created on Jul 13, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.formulabuilder;

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

import com.stockfaxforu.component.MainGraphComponent;
import com.stockfaxforu.strategy.BuySellStrategy;
import com.stockfaxforu.util.Utility;

/**
 * @author sdeopura
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class StockScreenerResult extends JFrame
{

	JPanel jContentPane=null;
	Vector resultVector=null;
	public StockScreenerResult(Vector resultVector)
	{
		this.resultVector = resultVector;
		if(resultVector==null || resultVector.size()==0)
		{
			MessageDiaglog message = new MessageDiaglog("No Result Found");
			this.dispose();
			return;	
		}
		setBackground(Color.lightGray);
		this.setSize(700,700);
		setBackground(Color.lightGray);

		this.move(200,200);
		this.setContentPane(getJContentPane());
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setTitle("Result Screen");
		Image im = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("image/toolicon.jpg"));
		this.setIconImage(im);
//		this.setResizable(false);		
	}
	private javax.swing.JPanel getJContentPane() 
	{
		if(jContentPane == null) 
		{
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
		}
		HashMap resultHash = (HashMap)resultVector.elementAt(0);
		String[] columnNames = new String[resultHash.size()];
		
		Iterator it = resultHash.keySet().iterator();
		
		int i=0;
		columnNames[0] = MainGraphComponent.Symbol;
		i++;
		while(it.hasNext())
		{
			String s= (String)it.next();
			if(s.equalsIgnoreCase(MainGraphComponent.Symbol))
				continue;
			columnNames[i] = s; 
			i++;
		}
		String[][] datavalue = new String[resultVector.size()][columnNames.length];
		for(i=0;i<resultVector.size();i++)
		{
			HashMap hs = (HashMap)resultVector.elementAt(i);
			for(int j=0;j<columnNames.length;j++)
			{
				String s= (String)hs.get(columnNames[j]);
				try
				{
					s = Utility.oneDigitFloat(s);	
				}
				catch(Exception e)
				{
				}
				datavalue[i][j] = s;				
			}
		}
		
		JTable table = new JTable( datavalue, columnNames );
	
		// Add the table to a scrolling pane
		JScrollPane scrollPane = JTable.createScrollPaneForTable( table );
		Dimension d = table.getPreferredSize();
		this.setSize(d.width+20, d.height);
		scrollPane.setBounds(0,0,this.getWidth(),this.getHeight());
					
		jContentPane.add( scrollPane, null );
		return jContentPane;				
	}
	public static void main(String[] args)
	{
		
	}
}

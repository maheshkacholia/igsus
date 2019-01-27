/*
 * Created on Jul 13, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.scan.frontend;

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
public class IndiacatorValueScreen extends JFrame
{

	JPanel jContentPane=null;
	String symbol=null;
	public IndiacatorValueScreen(String symbol)
	{
		Image im = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("image/toolicon.jpg"));
		this.setIconImage(im);
		this.setTitle("Indicator Value For "+symbol);
		this.symbol = symbol;
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
		String[] columnNames = {"Indicator Name","Indicator Value"};
		
		BuySellStrategy buysell = new BuySellStrategy();
		Vector v1 = new Vector();
		HashMap hs = new HashMap();
		hs.put("symbol", symbol);
		v1.addElement(hs);
		Vector v = ManageVariable.getSelectedIndicatorForDisplay();
		if(v==null)
		{
			MessageDiaglog mess = new MessageDiaglog("Pls create indicator list first");
			this.dispose();
		}
		StringBuffer sb = new StringBuffer(" ");
		for(int i=0;i<v.size();i++)
		{
			sb.append(v.elementAt(i) + " ");
			if(i > (v.size() - 1))
				sb.append(",");
		}
		String query = BuySellStrategy.SELECTSTOCKQUERY + sb.toString(); 
		v =null ; // buysell.getHashMapForIndicators(v1, query);
		HashMap hs1 = (HashMap)v.elementAt(0);
		String[][] dataValues = new String[hs1.size()][2];

		Iterator it = hs1.keySet().iterator();
		int i=0;
		while(it.hasNext())
		{
			String s = (String)it.next();	
			if(s.equals("symbol"))
				continue;
			dataValues[i][0] = s;
			String s1 = (String)hs.get(s);
			try
			{
				s1 = Utility.oneDigitFloat(s1);
			}
			catch(Exception e)
			{
				
			}
			dataValues[i][1] = s1;
			i++;
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
		IndiacatorValueScreen ind = new IndiacatorValueScreen("TCS");
	}
}

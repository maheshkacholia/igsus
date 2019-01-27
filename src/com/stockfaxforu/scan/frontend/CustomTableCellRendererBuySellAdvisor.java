/*
 * Created on Mar 11, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.scan.frontend;
import java.awt.Component;
import java.awt.Color;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import com.stockfaxforu.config.ConfigUtility;
import com.stockfaxforu.util.StockConstants;
import com.stockfaxforu.util.Utility;
public class CustomTableCellRendererBuySellAdvisor extends DefaultTableCellRenderer
{
	String[][] dataValue = null;
	
	public static HashMap colorMap = new HashMap();
	
	public CustomTableCellRendererBuySellAdvisor(String[][] dataValue)
	{
		this.dataValue = dataValue;
	}
	String BULLISH = ":BULLISH:OVERSOLD:BUY:STRONGBUY:CHEAP:";
	String BEARISH = ":BEARISH:OVERBOUGHT:SELL:STRONGSELL:EXPENSIVE:";
	
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
	{
		Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		try
		{
			
			String s = dataValue[row][column];
			if (s != null && BULLISH.indexOf(s.toUpperCase()) != -1)
			{
				
				cell.setBackground(Color.GREEN);
				return cell;
			}
			else if (s != null && BEARISH.indexOf(s.toUpperCase()) != -1 )
			{
				cell.setBackground(Color.RED);
				return cell;	
			}
			else if (ConfigUtility.getColor(s) != null)
			{
				Color c = ConfigUtility.getColor(s);
				cell.setBackground(c);
				return cell;	
				
			}
			else
			{
				cell.setBackground(Color.black);
				cell.setForeground(Color.white);
				return cell;
			}
		
/*			
			if(this.TYPE==FILTER)
			{
				int x = row%2;
				if(x==0)
				{
					cell.setBackground(StockConstants.SqlOutputColor1);
					cell.setForeground(Color.black);
					return cell;
					
				}
				else
				{
					cell.setBackground(StockConstants.SqlOutputColor2);
					cell.setForeground(Color.black);
					return cell;
					
				}
			}
			String type = dataValue[row][1];
			if(type==null)
			{
				cell.setBackground(Color.black);
				cell.setForeground(Color.white);
				return cell;
				
			}
			type = type.replace('"',' ').trim();
			if(type.equalsIgnoreCase("STRONGBUY"))
			{
				cell.setBackground(Color.green);
				cell.setForeground(Color.black);
				return cell;
							
			}
			else if(type.equalsIgnoreCase("BUY"))
	 		{
				cell.setBackground(Color.lightGray);
				cell.setForeground(Color.black);
				return cell;
	 			
	 		}
			else if(type.equalsIgnoreCase("SELL"))
			{
				cell.setBackground(Color.pink);
				cell.setForeground(Color.black);
				return cell;
	
				 		
			}
			else if(type.equalsIgnoreCase("STRONGSELL"))
			{
				cell.setBackground(Color.red);
				cell.setForeground(Color.black);
				return cell;
	
			}
			else if(type.equalsIgnoreCase("HOLD"))
			{
				cell.setBackground(Color.yellow);
				cell.setForeground(Color.black);
				return cell;			 		
			}
			else if(type.equalsIgnoreCase("WATCH"))
			{
				cell.setBackground(Color.yellow);
				cell.setForeground(Color.black);
				return cell;			 				 		
			}
			else
			{
				cell.setBackground(Color.black);
				cell.setForeground(Color.white);
				return cell;
				
			}  */
		}
		catch (Exception e)
		{
			cell.setBackground(Color.black);
			cell.setForeground(Color.white);
			return cell;
		}
	}
}

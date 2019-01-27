/*
 * Created on Mar 11, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.trading;
import java.awt.Component;
import java.awt.Color;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import com.stockfaxforu.util.StockConstants;
public class CustomTableCellRendererPure extends DefaultTableCellRenderer
{
	String[][] dataValue = null;
	
	public static HashMap colorMap = new HashMap();
	public static int FILTER=1;
	public int TYPE=2;
	public CustomTableCellRendererPure(String[][] dataValue,int type)
	{
		this.TYPE = type;
		this.dataValue = dataValue;
	}
	public CustomTableCellRendererPure(String[][] dataValue)
	{
		this.TYPE = 2;
		this.dataValue = dataValue;
	}
	
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
	{
		Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		try
		{
			String symbol = dataValue[row][0];

			if(symbol.indexOf("Overall") != -1)
			{
					cell.setBackground(Color.red);
					cell.setForeground(Color.white);
					return cell;
			}
			else if(symbol.indexOf("Total") != -1)
			{
					cell.setBackground(StockConstants.SqlOutputColor1);
					cell.setForeground(Color.black);
					return cell;
			}

			else
			{
				cell.setBackground(Color.black);
				cell.setForeground(Color.white);
				return cell;
				
			}
		}
		catch (Exception e)
		{
			cell.setBackground(Color.black);
			cell.setForeground(Color.white);
			return cell;
		}
	}
}

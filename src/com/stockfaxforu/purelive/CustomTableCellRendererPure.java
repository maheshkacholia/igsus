/*
 * Created on Mar 11, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.purelive;
import java.awt.Component;
import java.awt.Color;
import java.util.HashMap;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
public class CustomTableCellRendererPure extends DefaultTableCellRenderer
{
	String[][] oldData = null;
	String[][] datavalue = null;
	int selectedindex=-1;
	
	public static HashMap colorMap = new HashMap();
	public CustomTableCellRendererPure(String[][] oldData,String[][] datavalue,int selectedindex)
	{
		this.oldData = oldData;
		this.datavalue = datavalue;
		this.selectedindex = selectedindex;
	}
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
	{
		Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		

		if(this.oldData != null && this.selectedindex >= 0 && this.selectedindex < this.oldData.length &&  row==this.selectedindex)
		{
			cell.setBackground(Color.yellow);
			cell.setForeground(Color.black);
			return cell;
			
		}
		if (column == 3 || column == 5 || column == 7 || column==0)
		{
			cell.setBackground(Color.black);
			cell.setForeground(Color.black);

			try
			{
				if(column==0)
				{

					if(this.datavalue[row][13].charAt(0)=='-')
					{
						cell.setBackground(Color.red);
						cell.setForeground(Color.black);
						return cell;
					}
					else
					{
						cell.setBackground(Color.green);
						cell.setForeground(Color.black);
						return cell;
						
					}
				}

					
				float f1 = Float.parseFloat(oldData[row][column].trim());
		
				float f = Float.parseFloat(value.toString());

				if (f == f1)
				{
					try
					{
						cell.setBackground((Color)colorMap.get(row+":"+column));
						
					}
					catch(Exception e)
					{
						
					}
					
//					cell.setForeground(Color.white);
					return cell;
				}

				if (f < f1)
				{
					cell.setBackground(Color.red);
					colorMap.put(row+":"+column, Color.red);
					cell.setForeground(Color.black);

				}
				else if (f > f1)
				{
					colorMap.put(row+":"+column, Color.green);

					cell.setBackground(Color.green);
					cell.setForeground(Color.black);
	
				}
				return cell;
			}
			catch (Exception e)
			{
				cell.setBackground(Color.black);
				cell.setForeground(Color.white);
				return cell;
			}
		}
		else
		{
			cell.setBackground(Color.black);
			cell.setForeground(Color.white);
			return cell;
			
		}
	}
}

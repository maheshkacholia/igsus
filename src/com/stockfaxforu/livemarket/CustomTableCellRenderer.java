/*
 * Created on Mar 11, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.livemarket;
import java.awt.Component;
import java.awt.Color;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
public class CustomTableCellRenderer extends DefaultTableCellRenderer
{
	public String[][] oldData = null;
	public String[][] newData = null;
	
	public CustomTableCellRenderer(String[][] oldData,String[][] newData)
	{
		this.oldData = oldData;
		this.newData = newData;
	}
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
	{
		Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		if (column == 0)
		{
			try
			{
				float f1 = Float.parseFloat(newData[row][3]);
				float f = Float.parseFloat(newData[row][1]);
				if (f < f1)
				{
					cell.setBackground(Color.red);
					cell.setForeground(Color.black);

				}
				else if (f > f1)
				{
					cell.setBackground(Color.green);
					cell.setForeground(Color.black);
	
				}
				else if (f == f1)
				{
					cell.setBackground(Color.black);
					cell.setForeground(Color.white);
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

		if (column == 1)
		{
			try
			{
				float f1 = Float.parseFloat(oldData[row][1]);
				float f = Float.parseFloat(newData[row][1]);
				if (f < f1)
				{
					cell.setBackground(Color.red);
					cell.setForeground(Color.black);

				}
				else if (f > f1)
				{
					cell.setBackground(Color.green);
					cell.setForeground(Color.black);
	
				}
				else if (f == f1)
				{
					cell.setBackground(Color.black);
					cell.setForeground(Color.white);
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
		else if(column == 2)
		{
			if(value.toString().charAt(0)=='-')
			{
				cell.setBackground(Color.red);
				cell.setForeground(Color.black);

			}
			else
			{
				cell.setBackground(Color.green);
				cell.setForeground(Color.black);
				
			}
			return cell;
		}
		else
		{
			cell.setBackground(Color.black);
			cell.setForeground(Color.white);
			return cell;
			
		}
	}
}

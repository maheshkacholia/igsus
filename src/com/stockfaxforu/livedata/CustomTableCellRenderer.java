/*
 * Created on Mar 11, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.livedata;
import java.awt.Component;
import java.awt.Color;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
public class CustomTableCellRenderer extends DefaultTableCellRenderer
{
	public Vector oldData = null;
	public Vector newData = null;
	
	public CustomTableCellRenderer(Vector oldData,Vector newData)
	{
		this.oldData = oldData;
		this.newData = newData;
	}
	public void setDataValues(Vector oldData,Vector newData)
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
				float f1 = Float.parseFloat((String)((Vector)newData.get(row)).get(3));
			//	float f = Float.parseFloat((String)((Vector)newData.get(row)).get(2));
				if ( f1 < 0)
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
			catch (Exception e)
			{
				cell.setBackground(Color.black);
				cell.setForeground(Color.white);
				return cell;
			}
		}

		if (column == 2)
		{
			try
			{
				float f1 = Float.parseFloat((String)((Vector)oldData.get(row)).get(2));
				float f = Float.parseFloat((String)((Vector)newData.get(row)).get(2));
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
		else
		{
			cell.setBackground(Color.black);
			cell.setForeground(Color.white);
			return cell;
			
		}
	}
}

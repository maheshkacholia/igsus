/*
 * Created on Mar 11, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.formulabuilder;

import javax.swing.table.AbstractTableModel;

/**
 * @author sdeopura
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class LiveMarketTableModelPure extends AbstractTableModel 
{
	String[] columnNames=null;
	String[][] dataValue = null;
	public LiveMarketTableModelPure(String[] columnNames,String[][] dataValue) 
	{
		   this.columnNames = columnNames;
		   this.dataValue = dataValue;
	   }

	/* (non-Javadoc)
	 * @see javax.swing.table.AbstractTableModel#getValueAt(int, int)
	 */
	public Object getValueAt(int row, int column)
	{
		
		// TODO Auto-generated method stub
		return this.dataValue[row][column];
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.AbstractTableModel#getColumnCount()
	 */
	public int getColumnCount()
	{
		// TODO Auto-generated method stub
		return columnNames.length;
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.AbstractTableModel#getRowCount()
	 */
	public int getRowCount()
	{
		// TODO Auto-generated method stub
		return dataValue.length;
	}
	
	public String getColumnName(int column) {
		return columnNames[column];
	}

}

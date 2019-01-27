/*
 * Created on Jun 9, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.frontend;
import java.awt.Component;
import java.awt.Font;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import com.stockfaxforu.util.ManageIndicator;
import com.stockfaxforu.util.Utility;
public class ComboBoxRendererSymbol extends JLabel implements ListCellRenderer
{
	ImageIcon[] images;
	String[] petStrings ;
	String[] tooltips;

	private Font uhOhFont;
	public Vector symbolList = new Vector();
	public ComboBoxRendererSymbol(Vector symbolList)
	{
		this.symbolList = symbolList;
//		setOpaque(true);
//		setHorizontalAlignment(LEFT);
//		setVerticalAlignment(CENTER);
	}
	/*
	 * This method finds the image and text corresponding
	 * to the selected value and returns the label, set up
	 * to display the text and image.
	 */
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus)
	{
		//Get the selected index. (The index param isn't
		//always valid, so just use the value.)
		String name = value.toString();
//		int index = ManageIndicator.getIndiPosition(indName);
		setText(name);	
		if(isSelected)
		{
			setBackground(list.getSelectionBackground());
			setForeground(list.getSelectionForeground());
			setFont(list.getFont());
			
		}
		if (-1 < index) 
		{
			String s = Utility.getStockDes(name);
			if(s==null)
				s="";
			list.setToolTipText(	s	);
		}
	return this;
	}
}

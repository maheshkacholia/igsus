/*
 * Created on May 29, 2007
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.frontend;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.HashMap;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import com.stockfaxforu.scan.util.ScanUtility;
import com.stockfaxforu.util.Utility;
public class CustomTreeCellRenderer extends DefaultTreeCellRenderer
{
	/**   
	* Constructor  
	*  
	* @return void  
	* @exception  
	*/
	public CustomTreeCellRenderer()
	{
		//always call super 
		super();
	}
	/**  
	* getTreeCellRendererComponent  
	* This method is overridden to set the node specific icons and tooltips 
	*     
	* @return The Component object used to render the cell value 
	* @exception  
	*/
	public Component getTreeCellRendererComponent(
		JTree tree,
		Object value,
		boolean selection,
		boolean expanded,
		boolean leaf,
		int row,
		boolean hasFocus)
	{
		super.getTreeCellRendererComponent(tree, value, selection, expanded, leaf, row, hasFocus);
		//The value object is nothing but the DefaultMutableTreeNode. 
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
		setIconAndToolTip(node.getUserObject(), tree);
		Font f = new Font("Arial",Font.BOLD,9);
		setFont(f);
		return this;
	}
	/**  
	* setIconAndToolTip  
	* This method checks the userobject and appropiately sets the icons and tooltip 
	*     
	* @return void 
	* @exception  
	*/
	
	private void setIconAndToolTip(Object obj, JTree tree)
	{
		try
		{
			String s = Utility.getStockDes(obj.toString());
			HashMap hs = ScanUtility.getSymbolDetail(obj.toString());
			
			if(hs !=null)
			{
				String ss = " C="+hs.get(ScanUtility.close);
				setToolTipText(s+ss);
				float c = Float.parseFloat(hs.get(ScanUtility.close)+"");
				float o = Float.parseFloat(hs.get(ScanUtility.open)+"");
				if ( o > c)
				{
					setForeground(Color.red);
				}
				else
				{
					setForeground(Color.GREEN);
									
				}
			}
			else
			{
				setToolTipText(s);
			}
				
			
		}
		catch(Exception e)
		{
			
		}	
	}
}

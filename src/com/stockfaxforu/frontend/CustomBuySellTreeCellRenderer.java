/*
 * Created on May 29, 2007
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.frontend;
import java.awt.Component;
import java.awt.Point;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.StringTokenizer;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;

import com.stockfaxforu.scan.util.ScanUtility;
import com.stockfaxforu.util.BuySellAdvisorUtility;
import com.stockfaxforu.util.StockConstants;
import com.stockfaxforu.util.Utility;
public class CustomBuySellTreeCellRenderer extends DefaultTreeCellRenderer
{
	/**   
	* Constructor  
	*  
	* @return void  
	* @exception  
	*/
	public CustomBuySellTreeCellRenderer()
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
			//String s = Utility.getStockDes(obj.toString());
			String query = BuySellAdvisorUtility.getInfo(obj.toString());
			StringTokenizer st = new StringTokenizer(query,"\n");
			String query1="";
			if(st.hasMoreTokens())
				query = st.nextToken();
			setToolTipText(query+""); 
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}	
	}
}

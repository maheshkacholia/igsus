/*
 * Created on Jul 23, 2009
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import org.w3c.dom.Element;

import com.stockfaxforu.util.IndexUtility;
import com.stockfaxforu.util.StockConstants;

/**
 * @author Mahesh
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class PropertyTreePanel extends JScrollPane implements TreeSelectionListener
{

	// Instance attributes used in this example
	private JPanel topPanel;
	private JTree tree;
	private JScrollPane scrollPane;
	private ConfigurePropertyScreen graphPanel;
	public DefaultTreeModel treeModel;
	DefaultMutableTreeNode root;
	// Constructor of main frame
	public PropertyTreePanel(int x2, int y2, ConfigurePropertyScreen graphPanel)
	{
		this.graphPanel = graphPanel; 
		root = new DefaultMutableTreeNode("Settings");
		Iterator it = ConfigXMLManager.categoryHash.keySet().iterator();
		while (it.hasNext())
		{
			String index = (String) it.next();
			DefaultMutableTreeNode nifty = new DefaultMutableTreeNode(index);
			addAllCard(nifty, index);

			root.add(nifty);
		}
		// Create a new tree control
		treeModel = new DefaultTreeModel(root);
		tree = new JTree(treeModel);
/*
		CustomTreeCellRenderer customCellRenderer = new CustomTreeCellRenderer();
		tree.setCellRenderer(customCellRenderer);
		ToolTipManager.sharedInstance().registerComponent(tree);
*/
		tree.addTreeSelectionListener(this);
		getViewport().add(tree);
		tree.setBounds(0, 0, x2, y2 - 30);
	}
	public void addAllCard(DefaultMutableTreeNode suit, String name)
	{
		Vector v = (Vector) ConfigXMLManager.categoryHash.get(name);
		for(int i=0;i < v.size();i++)
		{
			String s1 = (String)v.get(i);
			if (s1.equals(""))
				continue;
			suit.add(new DefaultMutableTreeNode(s1));
		}
	}

	
	/* (non-Javadoc)
	 * @see javax.swing.event.TreeSelectionListener#valueChanged(javax.swing.event.TreeSelectionEvent)
	 */
	public void valueChanged(TreeSelectionEvent event)
	{
		if (event.getSource() == tree)
		{
			TreePath path = tree.getSelectionPath();
			if(path==null)
				return;
			String name = path.getLastPathComponent().toString();

			Object element = (Element)ConfigXMLManager.propertyHash.get(name);
			if (element != null)
				updatePanel(name);
		}
	}
	public void updatePanel(String name)
	{
		try
		{
			this.graphPanel.remove(this.graphPanel.datavalue);
		}
		catch(Exception e)
		{
			
		}
		this.graphPanel.datavalue = new DataValuePanel(name,this.graphPanel);
		this.graphPanel.jContentPane.add(this.graphPanel.datavalue);
		this.graphPanel.datavalue.setBounds(300,00,this.graphPanel.x-300,this.graphPanel.y-300);
		
	}
	

}

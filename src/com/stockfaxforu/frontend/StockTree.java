package com.stockfaxforu.frontend;
import java.awt.*;
import java.awt.event.*;
import java.io.RandomAccessFile;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.*;

import com.stockfaxforu.buyselladvisior.BuySellPopUpMenuItem;
import com.stockfaxforu.maintree.ManageStockPopUpMenuItem;
import com.stockfaxforu.scan.util.ScanUtility;
import com.stockfaxforu.util.IndexUtility;
import com.stockfaxforu.util.StockConstants;
import com.stockfaxforu.util.Utility;
public class StockTree extends JScrollPane implements TreeSelectionListener,MouseListener,ComponentListener
{
	// Instance attributes used in this example
	private JPanel topPanel;
	public JTree tree;
	private JScrollPane scrollPane;
	private JFrame graphPanel;
	public DefaultTreeModel treeModel;
	DefaultMutableTreeNode root;
	public static enum TYPEOFLOAD{PERCHANGE,PRICE,INDEX};
	
	// Constructor of main frame
	public StockTree(int x2, int y2, JFrame graphPanel)
	{
		IndexUtility.loadIndexes();
		this.graphPanel = graphPanel;
		root = new DefaultMutableTreeNode("Indexes");
		Enumeration it = IndexUtility.indexHashMap.keys();
		while (it.hasMoreElements())
		{
			String index = (String) it.nextElement();
			DefaultMutableTreeNode nifty = new DefaultMutableTreeNode(index);
			addAllCard(nifty, index);
			root.add(nifty);
		}
		// Create a new tree control
		treeModel = new DefaultTreeModel(root);
		tree = new JTree(treeModel);
		CustomTreeCellRenderer customCellRenderer = new CustomTreeCellRenderer();
		tree.setCellRenderer(customCellRenderer);
		ToolTipManager.sharedInstance().registerComponent(tree);
		tree.addTreeSelectionListener(this);
		getViewport().add(tree);
		tree.setBounds(0, 0, x2, y2 - 30);
//		tree.setBackground(Color.black);
		 tree.addMouseListener(new MouseAdapter() {
		      public void mousePressed(MouseEvent event) {
		        if (((event.getModifiers() & InputEvent.BUTTON3_MASK) != 0)
		            && (tree.getSelectionCount() > 0)) {
		          showMenu(event.getX(), event.getY());
		        }
		      }
		    });

	}

	 protected void showMenu(int x, int y) 
	 {
		 ManageStockPopUpMenuItem buysellmenu = new ManageStockPopUpMenuItem(this);
		    buysellmenu.show(tree, x, y);
			
	 } 
	// Helper method to write an enitre suit of cards to the
	// current tree node
	public void addAllCard(DefaultMutableTreeNode suit, String name)
	{
		String s = (String) IndexUtility.indexHashMap.get(name);
		StringTokenizer st = new StringTokenizer(s, "|");
		while (st.hasMoreTokens())
		{
			String s1 = st.nextToken();
			if (s1.equals(""))
				continue;
			DefaultMutableTreeNode d = new DefaultMutableTreeNode(s1);
			
			suit.add(d);
		
		}
	}
	public void addAllCard(String index, Vector name)
	{
		DefaultMutableTreeNode suit = new DefaultMutableTreeNode(index);
		for (int i=0;i<name.size();i++)
		{
			String s1 = (String)name.elementAt(i);
			if (s1.equals(""))
				continue;
			suit.add(new DefaultMutableTreeNode(s1));
		}
		this.root.add(suit);
		this.treeModel.reload();
		this.repaint();
	}
	


	// Main entry point for this example
	public static void main(String args[])
	{
		// Create an instance of the test application
		StockTree mainFrame = new StockTree(200, 200, null);
		mainFrame.setVisible(true);
	}
	public void valueChanged(TreeSelectionEvent event)
	{
		if (event.getSource() == tree)
		{
			TreePath path = tree.getSelectionPath();
			if(path==null)
				return;
			String name = path.getLastPathComponent().toString();
			if(name==null )
				return;
			MainScreen graphPanel1 = ((MainScreen) this.graphPanel);
			if (!(graphPanel1.panel instanceof PanelForGraphImpl))
			{
				try
				{
					graphPanel1.jContentPane.remove(graphPanel1.panel);
				}
				catch (Exception ex)
				{
				}
				graphPanel1.panel =
					new PanelForGraph(
						0,
						0,
						StockConstants.length - 200,
						StockConstants.height - StockConstants.YPOSITIONFORGRAPH - StockConstants.YPOSITIONFORGRAPHGAP,
						name);
				graphPanel1.panel.setBounds(
					201,
					StockConstants.YPOSITIONFORGRAPH,
					StockConstants.length - 200,
					StockConstants.height - StockConstants.YPOSITIONFORGRAPH);
				graphPanel1.panel.setBackground(Color.black);
				graphPanel1.panel.setOpaque(true);
				graphPanel1.panel.setVisible(true);
				graphPanel1.jContentPane.add(graphPanel1.panel, null);
//				graphPanel1.doReSize();
			}
			else
			{
				((MainScreen) this.graphPanel).setNewSymbol(name);
//				((MainScreen) this.graphPanel).doReSize();
			}
//			StockConstants.SELECTED_STOCK = name;
		}
	}
	public String getToolTipText(MouseEvent evt)
	{
	//	// ln("aaaaaaaaaaaaaaaaaaaaaaa");
		TreePath path = tree.getSelectionPath();
		// Get the text for the last component
		String name = path.getLastPathComponent().toString();
		return Utility.getStockDes(name).toString();
	}
	public void mouseClicked(MouseEvent e) 
	{
		
		// TODO Auto-generated method stub
		
	}
	public void mouseEntered(MouseEvent e) 
	{
		// TODO Auto-generated method stub
		
	}
	public void mouseExited(MouseEvent e) {
		

		// TODO Auto-generated method stub
		
	}
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void componentResized(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}
}

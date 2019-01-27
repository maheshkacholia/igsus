package com.stockfaxforu.frontend;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.RandomAccessFile;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.*;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.*;

import com.stockfaxforu.buyselladvisior.BuySellAdvisorScreen;
import com.stockfaxforu.buyselladvisior.BuySellPopUpMenuItem;
import com.stockfaxforu.scan.util.ScanUtility;
import com.stockfaxforu.util.BuySellAdvisorUtility;
import com.stockfaxforu.util.IndexUtility;
import com.stockfaxforu.util.StockConstants;
import com.stockfaxforu.util.Utility;
public class BuySellPanelTree extends JScrollPane implements TreeSelectionListener,MouseListener,ActionListener,TreeModelListener
{
	// Instance attributes used in this example
	public JPanel topPanel;
	public JTree tree;
	public JScrollPane scrollPane;
	public BuySellAdvisorScreen graphPanel;
	public DefaultTreeModel treeModel;
	public DefaultMutableTreeNode root;
	public MutableTreeNode  node = null;
	  
	// Constructor of main frame
	public BuySellPanelTree(int x2, int y2, BuySellAdvisorScreen graphPanel)
	{
		BuySellAdvisorUtility.loadIndexes();
		this.graphPanel = graphPanel;
		root = new DefaultMutableTreeNode("Advisory Types");
		Enumeration it = BuySellAdvisorUtility.indexHashMap.keys();
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
		CustomBuySellTreeCellRenderer customCellRenderer = new CustomBuySellTreeCellRenderer();
		tree.setCellRenderer(customCellRenderer);
		ToolTipManager.sharedInstance().registerComponent(tree);
		tree.addTreeSelectionListener(this);
		getViewport().add(tree);
		tree.setBounds(0, 0, x2, y2 - 30);
		
		 tree.addMouseListener(new MouseAdapter() {
		      public void mousePressed(MouseEvent event) {
		        if (((event.getModifiers() & InputEvent.BUTTON3_MASK) != 0)
		            && (tree.getSelectionCount() > 0)) {
		          showMenu(event.getX(), event.getY());
		        }
		      }
		    });
		
//		this.addMouseListener(this)
	}
	// Helper method to write an enitre suit of cards to the
	// current tree node
	public void addAllCard(DefaultMutableTreeNode suit, String name)
	{
		String s = (String) BuySellAdvisorUtility.indexHashMap.get(name);
		StringTokenizer st = new StringTokenizer(s, "|");
		while (st.hasMoreTokens())
		{
			String s1 = st.nextToken();
			if (s1.equals("")||s1.equals("dummy"))
				continue;
			suit.add(new DefaultMutableTreeNode(s1));
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
	
	 protected void showMenu(int x, int y) 
	 {
		 BuySellPopUpMenuItem buysellmenu = new BuySellPopUpMenuItem(this);
		    buysellmenu.show(tree, x, y);
			
	 } 
	
	// Main entry point for this example
	public static void main(String args[])
	{
		// Create an instance of the test application
		BuySellPanelTree mainFrame = new BuySellPanelTree(200, 200, null);
		mainFrame.setVisible(true);
	}
	
	public void valueChanged(TreeSelectionEvent event)
	{	try
		{
			if (event.getSource() == tree)
			{
				TreePath path = tree.getSelectionPath();
				if(path==null)
					return;
				String name = path.getLastPathComponent().toString();
				
				
				if(name==null )
					return;
				String name1 = path.getParentPath().getLastPathComponent().toString();
				
				
				File fil = new File(StockConstants.INSTALL_DIR +"/buysell/"+ name1 + "-" + name );
				FileInputStream f = new FileInputStream(fil);
				byte[] b = new byte[(int)fil.length()];
				int i = f.read(b);
				f.close();
				String query = new String(b,0,i);
				this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				this.graphPanel.panelresult.submitQuery(query,BuySellAdvisorUtility.getInfo(name));
				this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				
			}
			
		}
		catch(Exception e)
		{
			this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

			//			e.printStackTrace();
			
		}
	}
	public String getToolTipText(MouseEvent evt)
	{
	//	// ln("aaaaaaaaaaaaaaaaaaaaaaa");
		TreePath path = tree.getSelectionPath();
		// Get the text for the last component
		String name = path.getLastPathComponent().toString();
		return "mammama---"+name;
	}
	public void mouseClicked(MouseEvent e) 
	{
		
		// TODO Auto-generated method stub
		
	}
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		TreePath path = tree.getSelectionPath();
		// Get the text for the last component
		String name = path.getLastPathComponent().toString();

		this.setToolTipText("mammama---"+name);
	}
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		this.setToolTipText("");
	}
	public void mousePressed(MouseEvent e) 
	{
		// TODO Auto-generated method stub
		
	}
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void treeNodesChanged(TreeModelEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void treeNodesInserted(TreeModelEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void treeNodesRemoved(TreeModelEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void treeStructureChanged(TreeModelEvent e) {
		// TODO Auto-generated method stub
		
	}
}

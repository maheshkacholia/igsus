package com.stockfaxforu.maintree;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Vector;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;

import com.stockfaxforu.favourite.AddRemoveStock;
import com.stockfaxforu.favourite.NewFavouriteCategoryEntry;
import com.stockfaxforu.frontend.BuySellPanelTree;
import com.stockfaxforu.frontend.IndexComboBox;
import com.stockfaxforu.frontend.MainScreen;
import com.stockfaxforu.frontend.StockTree;
import com.stockfaxforu.util.IndexUtility;
import com.stockfaxforu.util.StockConstants;
import com.stockfaxforu.util.Utility;

public class ManageStockPopUpMenuItem extends JPopupMenu implements ActionListener
{
	StockTree buysellpanel =null;
	JMenuItem addmenu = null;
	 JMenuItem editmenu = null;
	 JMenuItem deletemenu = null;
	 
	public ManageStockPopUpMenuItem(StockTree buysellpanel)
	{
		 this.buysellpanel = buysellpanel;
		 
		 addmenu = new JMenuItem("Add");
		 addmenu.setName("add");
		 addmenu.addActionListener(this);
		 //this.add(addmenu);
		 
		 editmenu = new JMenuItem("Edit");
		 editmenu.setName("edit");
		 editmenu.addActionListener(this);
		 //this.add(editmenu);
			
		 deletemenu = new JMenuItem("Delete");
		 deletemenu.addActionListener(this);
		 editmenu.setName("delete");
		 //this.add(deletemenu);
		
		 TreePath path = buysellpanel.tree.getSelectionPath();
		 MutableTreeNode node = (MutableTreeNode)path.getLastPathComponent();
		Object[] o= buysellpanel.tree.getSelectionPath().getPath();
		if(o.length==3)
		{
			this.add(addmenu);
			 this.add(editmenu);
			this.add(deletemenu);
						 	
		}
		else if (o.length==2)
		{
			this.add(addmenu);
			// this.add(editmenu);
			this.add(deletemenu);
			
		}
		else
		{
			this.add(addmenu);
			
		}
		 /*if (node == buysellpanel.tree.getModel().getRoot()) 
		 {
			 this.deletemenu.setEnabled(false);
		 }*/
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		
		 //   JMenuItem addmenu = new JMenuItem("Add");
	    
	    TreePath path = buysellpanel.tree.getSelectionPath();
	    MutableTreeNode node = (MutableTreeNode)path.getLastPathComponent();
	    
	    JMenuItem item=(JMenuItem)e.getSource();
	    String name = item.getText();
	    if(name.equals("Delete"))
	    {
	    	Object[] o= buysellpanel.tree.getSelectionPath().getPath();
	    	StringBuffer sb = new StringBuffer();
	    	
	    	if(o.length==3)
	    	{	
	    		
	    		Vector v = new Vector();
	    		v.add(o[2]+"");
	    		IndexUtility.deleteStocksToCategory(v, o[1]+"");

				buysellpanel.treeModel.removeNodeFromParent(node);
	    	}
	    	else if(o.length==2)
	    	{
				String s = o[1]+"";
	    		IndexUtility.deleteCategory(s);
				IndexUtility.loadIndexes();
	
	    		buysellpanel.treeModel.removeNodeFromParent(node);
	    		
	    	}
	    		
	    }
	    else if(name.equals("Add"))
	    {
	    	Object[] o= buysellpanel.tree.getSelectionPath().getPath();
	    	if(o.length==1)
	    	{
				NewFavouriteCategoryEntry cat = new NewFavouriteCategoryEntry(buysellpanel);    		
	    	}
	    	else if (o.length==2)
	    	{
				AddRemoveStock addremove = new AddRemoveStock(o[1]+"");
				  		
	  //  		NewScannerEntry scanner = new NewScannerEntry(this, o[1]+"");
	    	}
	    	
	    }
	    else if(name.equals("Edit"))
	    {
	    	Object[] o= buysellpanel.tree.getSelectionPath().getPath();
	    	if(o.length==3)
	    	{
	    		try 
	    		{
	    			String formulaContent=Utility.getFileContent(StockConstants.INSTALL_DIR +"/buysell/"+o[1]+"-"+o[2]);
	    //			NewScannerEntry newscanner = new NewScannerEntry(this,o[1]+"",o[2]+"",formulaContent);
	    		} 
	    		catch (Exception e1) 
	    		{
					// TODO Auto-generated catch block
				//	e1.printStackTrace();
				}
	    		
	    	}
	    }
		    	    	 
	}

	public void addNewCategory(String indName) 
	{
		try 
		{
			FileOutputStream fo = new FileOutputStream(StockConstants.INSTALL_DIR +"/buysell/"+indName+"-dummy");
			fo.write("dummy".getBytes());
			fo.close();
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		DefaultMutableTreeNode root = (DefaultMutableTreeNode)buysellpanel.treeModel.getRoot();
		buysellpanel.treeModel.insertNodeInto(new DefaultMutableTreeNode(indName), root, root.getChildCount());
		
	}
	public void addNewFormula(String catName,String formunaName) 
	{
		
		DefaultMutableTreeNode root = (DefaultMutableTreeNode)buysellpanel.treeModel.getRoot();
//		root.getIndex()
//		buysellpanel.treeModel.insertNodeInto(new DefaultMutableTreeNode(indName), root, root.getChildCount());
		
	}
}

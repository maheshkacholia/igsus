package com.stockfaxforu.buyselladvisior;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Enumeration;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;

import com.stockfaxforu.frontend.BuySellPanelTree;
import com.stockfaxforu.util.BuySellAdvisorUtility;
import com.stockfaxforu.util.StockConstants;
import com.stockfaxforu.util.Utility;

public class BuySellPopUpMenuItem extends JPopupMenu implements ActionListener
{
	BuySellPanelTree buysellpanel =null;
	JMenuItem addmenu = null;
	 JMenuItem editmenu = null;
	 JMenuItem deletemenu = null;
	 
	public BuySellPopUpMenuItem(BuySellPanelTree buysellpanel)
	{
		 this.buysellpanel = buysellpanel;
		 
		 addmenu = new JMenuItem("Add");
		 addmenu.setName("add");
		 addmenu.addActionListener(this);
//		 this.add(addmenu);
		 
		 editmenu = new JMenuItem("Edit");
		 editmenu.setName("edit");
		 editmenu.addActionListener(this);
	//	 this.add(editmenu);
			
		 deletemenu = new JMenuItem("Delete");
		 deletemenu.addActionListener(this);
		 editmenu.setName("delete");
		// this.add(deletemenu);
		
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

		 
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		
		 //   JMenuItem addmenu = new JMenuItem("Add");
	    
	    TreePath path = buysellpanel.tree.getSelectionPath();
	    MutableTreeNode node = (MutableTreeNode)path.getLastPathComponent();
	    
	    JMenuItem item=(JMenuItem)e.getSource();
	    System.out.println(item);
	    String name = item.getText();
	    if(name.equals("Delete"))
	    {
	    	Object[] o= buysellpanel.tree.getSelectionPath().getPath();
	    	StringBuffer sb = new StringBuffer();
	    	
	    	if(o.length==3)
	    	{	
		    	for(int i=1;i<o.length;i++)
		    	{
		    		System.out.println(o[i]);
		    		if(i != o.length-1)
		    			sb.append(o[i]+"-");
		    		else
		    			sb.append(o[i]);
		    	}
		    	String s = sb.toString();
		    	System.out.println(StockConstants.INSTALL_DIR +"/buysell/"+ s);
		    	File fil = new File(StockConstants.INSTALL_DIR +"/buysell/"+ s );
				boolean b= fil.delete();
				
				buysellpanel.treeModel.removeNodeFromParent(node);
	    	}
	    	else if(o.length==2)
	    	{
	    	//	System.out.println("inside length==1");
	    		String catName= o[1]+"";
	    		File f = new File(StockConstants.INSTALL_DIR +"/buysell/");
	    		File[] list= f.listFiles();
	    		for(int i=0;i<list.length;i++)
	    		{
	    			String filename = list[i].getName();
	    			if(filename.startsWith(catName))
	    			{
	    				list[i].delete();
	    			}
	    		}
				buysellpanel.treeModel.removeNodeFromParent(node);
				
	    	}
	    		
	    }
	    else if(name.equals("Add"))
	    {
	    	Object[] o= buysellpanel.tree.getSelectionPath().getPath();
	    	if(o.length==1)
	    	{
	    		NewCategoryEntry newcategory = new NewCategoryEntry(this);
	    		
	    	}
	    	else if (o.length==2)
	    	{
	    		
	    		NewScannerEntry scanner = new NewScannerEntry(this, o[1]+"");
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
	    			NewScannerEntry newscanner = new NewScannerEntry(this,o[1]+"",o[2]+"",formulaContent);
	    		} 
	    		catch (Exception e1) 
	    		{
					// TODO Auto-generated catch block
					e1.printStackTrace();
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
		BuySellAdvisorUtility.makeAllReloadablr();
		
	
	}
	public void addNewFormula(String catName,String formunaName) 
	{
		
		DefaultMutableTreeNode root = (DefaultMutableTreeNode)buysellpanel.treeModel.getRoot();
		DefaultMutableTreeNode treenode =null;
		if(BuySellAdvisorUtility.isFormulaExists(catName,formunaName))
			return;
		for(Enumeration e=root.children();e.hasMoreElements();)
			
		{
			treenode =(DefaultMutableTreeNode) e.nextElement();
			String name = treenode.getUserObject()+"";
		//	System.out.println(treenode.getUserObject().toString());
			if(name.equals(catName))
			{
				break;
			}
		}
		buysellpanel.treeModel.insertNodeInto(new DefaultMutableTreeNode(formunaName),treenode, treenode.getChildCount());
		
	}
}

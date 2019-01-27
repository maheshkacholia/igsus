/*
 * Created on Feb 19, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.favourite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Label;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import com.stockfaxforu.component.*;
import com.stockfaxforu.formulabuilder.MessageDiaglog;
import com.stockfaxforu.frontend.IndexComboBox;
import com.stockfaxforu.frontend.MainScreen;
import com.stockfaxforu.frontend.StockTree;
import com.stockfaxforu.strategy.StrategyUtility;
import com.stockfaxforu.strategy.StrategyUtilityStockScreener;
import com.stockfaxforu.swingcomponent.IGSLabel;
import com.stockfaxforu.util.IndexUtility;
import com.stockfaxforu.util.ManageIndicator;
import com.stockfaxforu.util.Utility;
/**
 * @author sdeopura
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class NewFavouriteCategoryEntry extends JFrame implements ActionListener
{
	/**
	 * This is the default constructor
	 */
	JTextField number = null;
	JTextField daybefore = null;
	JButton add = null;
	JButton cancel = null;
	String indicatorName = null;
	Vector paras = new Vector();
	JComboBox operatorList = null;
	int index = 0;
	JComboBox type = null;
	String operationname = "";
	JLabel msg = null;
	StockTree stocktree=null;
	public NewFavouriteCategoryEntry()
	{
		//		super(null);
		setBackground(Color.lightGray);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Image im = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("image/toolicon.jpg"));
		this.setIconImage(im);
		this.setContentPane(getJContentPane());
		this.setTitle("Create/Delete Category");
		this.move(300, 300);
		this.setSize(500, 250);
		this.setVisible(true);
	}
	public NewFavouriteCategoryEntry(StockTree stocktree)
	{
		//		super(null);
		setBackground(Color.lightGray);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Image im = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("image/toolicon.jpg"));
		this.setIconImage(im);
		this.stocktree=stocktree;
		this.setContentPane(getJContentPane());
		this.setTitle("Create new Category");
		this.move(300, 300);
		this.setSize(500, 250);
		this.setVisible(true);
	}
	private javax.swing.JPanel jContentPane = null;
	public JComboBox indexes = null;
	private javax.swing.JPanel getJContentPane()
	{
		int xinc = 10;
		int yinc = 20;
		int xincdis = 120;
		if (jContentPane == null)
		{
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
		}
		if(stocktree ==null)
		{	
			IGSLabel lable = new IGSLabel("Select Category to Delete:");
	//		lable.setFont(new Font("Arial", Font.PLAIN, 12));
	//		lable.setForeground(Color.black);
			lable.setBounds(xinc, yinc, 150, 15);
			jContentPane.add(lable, null);
			
	//		
			xinc = xinc + 175;
			Vector v = IndexUtility.getCategories();
			 indexes = new IndexComboBox(v);
			indexes.setFont(new Font("Arial", Font.PLAIN, 12));
			indexes.setForeground(Color.black);
			indexes.setBounds(xinc, yinc, 100, 20);
			indexes.addActionListener(this);
			jContentPane.add(indexes, null);
	
			xinc = xinc + 125;
			
			JButton delete = new JButton();
			delete.setBounds(xinc, yinc, 75, 20);
			delete.setName("delete");
			delete.setText("Delete");
			jContentPane.add(delete, null);
			delete.addActionListener(this);
			
	
			
			yinc = yinc + 40;
			xinc = 10;
		}
		IGSLabel lable1 = new IGSLabel("New Category Name:");
		lable1.setBounds(xinc, yinc, 150, 15);
		jContentPane.add(lable1, null);
		
		xinc = xinc + 175;
		number = new JTextField("");
		number.setFont(new Font("Arial", Font.BOLD, 12));
		number.setForeground(Color.black);
		number.setBounds(xinc , yinc, 200, 20);
		jContentPane.add(number, null);
		yinc = yinc + 40;
		
		msg = new JLabel("");
		msg.setFont(new Font("Arial", Font.BOLD, 12));
		msg.setForeground(Color.black);
		msg.setBounds(xinc, yinc, 200, 15);
		jContentPane.add(msg, null);

		yinc = yinc +40;
		add = new JButton();
		add.setBounds(xinc, yinc, 75, 20);
		add.setName("add");
		add.setText("Add");
		jContentPane.add(add, null);
		add.addActionListener(this);
		cancel = new JButton();
		cancel.setBounds(xinc + 100, yinc, 75, 20);
		cancel.setName("cancel");
		cancel.setText("Cancel");
		jContentPane.add(cancel, null);
		cancel.addActionListener(this);
		return jContentPane;
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize()
	{
		this.setSize(300, 200);
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e)
	{
		Object o = (Object) e.getSource();
		if (o instanceof JButton)
		{
			JButton cb = (JButton) e.getSource();
			String name = cb.getName();
			String indName = number.getText();
			if (name.equalsIgnoreCase("add"))
			{
				String s = number.getText();
				if(IndexUtility.isExists(s))
				{
					MessageDiaglog msg = new MessageDiaglog("Category Already Exists");
				}
				else
				{
					IndexUtility.addCategory(s);
					IndexUtility.loadIndexes();
					MainScreen.getSingleton().addTreeAdPanel();
					MainScreen.getSingleton().doReSize();
				}
				
			}
			if (name.equalsIgnoreCase("delete"))
			{
				String s = (String)indexes.getSelectedItem();
				IndexUtility.deleteCategory(s);
				IndexUtility.loadIndexes();
				this.remove(indexes);

				Vector v = IndexUtility.getCategories();
				 indexes = new IndexComboBox(v);
				indexes.setFont(new Font("Arial", Font.PLAIN, 12));
				indexes.setForeground(Color.black);
				indexes.setBounds(185, 10, 100, 20);
				indexes.addActionListener(this);
				jContentPane.add(indexes, null);
				MainScreen.getSingleton().addTreeAdPanel();
				MainScreen.getSingleton().doReSize();
				this.dispose();
			}  
			else if (name.equalsIgnoreCase("cancel"))
			{
				this.dispose();
			}
		}
	}
	/**
	 * @param s
	 */
	private void addCategory(String s)
	{
		// TODO Auto-generated method stub
		
	}
	/**
	 * @param s
	 * @return
	 */
	private boolean isCategoryNotExist(String s)
	{
		
		// TODO Auto-generated method stub
		return false;
	}
	/**
	 * @return
	 */
	public static void main(String[] args)
	{
		NewFavouriteCategoryEntry frame = new NewFavouriteCategoryEntry();
	}
} //  @jve:visual-info  decl-index=0 visual-constraint="4,11"

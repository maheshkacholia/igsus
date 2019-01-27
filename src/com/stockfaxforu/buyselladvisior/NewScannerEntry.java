/*
 * Created on Feb 19, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.buyselladvisior;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Label;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import com.stockfaxforu.component.*;
import com.stockfaxforu.formulabuilder.MessageDiaglog;
import com.stockfaxforu.frontend.IndexComboBox;
import com.stockfaxforu.frontend.MainScreen;
import com.stockfaxforu.strategy.StrategyUtility;
import com.stockfaxforu.strategy.StrategyUtilityStockScreener;
import com.stockfaxforu.swingcomponent.IGSLabel;
import com.stockfaxforu.util.BuySellAdvisorUtility;
import com.stockfaxforu.util.IndexUtility;
import com.stockfaxforu.util.ManageIndicator;
import com.stockfaxforu.util.StockConstants;
import com.stockfaxforu.util.Utility;
/**
 * @author sdeopura
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class NewScannerEntry extends JFrame implements ActionListener
{
	/**
	 * This is the default constructor
	 */
	JComboBox categoryList = null;
	JTextField formulaName = null;
	JButton save = null;
	JButton cancel = null;
	String indicatorName = null;
	Vector paras = new Vector();
	JComboBox operatorList = null;
	int index = 0;
	JComboBox type = null;
	String operationname =null;
	BuySellPopUpMenuItem buysellmenuitem;
	String categoryName=null;
	JTextArea filter=null;
	String existingformula = "@COMMENT write comment here\n@RECORDSTOUSE=50=";
	String oldformulaName="";

	public NewScannerEntry(BuySellPopUpMenuItem buysellmenuitem,String categoryName)
	{
		//		super(null);
		setBackground(Color.lightGray);
		this.categoryName=categoryName;
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Image im = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("image/toolicon.jpg"));
		this.setIconImage(im);
		this.setContentPane(getJContentPane());
		this.setTitle("Add New Filter");
		this.move(50, 50);
		this.setSize(1200, 800);
		this.setVisible(true);
		this.buysellmenuitem=buysellmenuitem;
	
	}
	public NewScannerEntry(BuySellPopUpMenuItem buysellmenuitem,String categoryName,String formulaName,String existingFormula)
	{
		//		super(null);
		setBackground(Color.lightGray);
		this.categoryName=categoryName;
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.oldformulaName=formulaName;
		this.existingformula=existingFormula;
		Image im = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("image/toolicon.jpg"));
		this.setIconImage(im);
		this.setContentPane(getJContentPane());
		this.setTitle("Add New Filter");
		this.move(50, 50);
		this.setSize(1200, 800);
		this.setVisible(true);
		this.buysellmenuitem=buysellmenuitem;
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
		xinc = 10;
		IGSLabel lable1 = new IGSLabel("Category :");
		lable1.setBounds(xinc, yinc, 150, 15);
		jContentPane.add(lable1, null);
		
		xinc = xinc + 100;
		
		categoryList = new JComboBox(BuySellAdvisorUtility.getCategoriesUnique());
		categoryList.setSelectedItem(this.categoryName);
		categoryList.setFont(new Font("Arial", Font.BOLD, 12));
		categoryList.setForeground(Color.black);
		categoryList.setBounds(xinc , yinc, 200, 20);
		jContentPane.add(categoryList, null);
		yinc = yinc + 40;
		xinc = 10;
	
		IGSLabel lable3 = new IGSLabel("Formula Name :");
		lable3.setBounds(xinc, yinc, 150, 15);
		jContentPane.add(lable3, null);
		xinc = xinc + 100;
		formulaName = new JTextField(oldformulaName);
		formulaName.setBounds(xinc , yinc, 200, 20);
		jContentPane.add(formulaName, null);
		yinc = yinc + 40;
		xinc = 10;
		
		
		IGSLabel lable2 = new IGSLabel("Filter :");
		lable2.setBounds(xinc, yinc, 150, 15);
		jContentPane.add(lable2, null);
		yinc = yinc + 20;
		
		filter = new JTextArea(existingformula);
		JScrollPane scrollPane = new JScrollPane(filter, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(xinc,yinc, 1100, 500);
		jContentPane.add(scrollPane, null);
		
		yinc = yinc + 550;
				
				
		save = new JButton();
		save.setBounds(xinc, yinc, 75, 20);
		save.setName("save");
		save.setText("Save");
		jContentPane.add(save, null);
		save.addActionListener(this);
		
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
			if (name.equalsIgnoreCase("save"))
			{
				String catName = categoryList.getSelectedItem()+"";
				String forName = formulaName.getText().trim();
				String filterText = filter.getText();
				try
				{
					buysellmenuitem.addNewFormula(catName, formulaName.getText());
					
					Utility.saveContent(StockConstants.INSTALL_DIR +"/buysell/"+categoryName+"-"+forName,filterText);
					this.dispose();
				}
				catch(Exception e1)
				{
					MessageDiaglog diaglog = new MessageDiaglog("Sorry,Could't save the file");
				}
				return;
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
} //  @jve:visual-info  decl-index=0 visual-constraint="4,11"

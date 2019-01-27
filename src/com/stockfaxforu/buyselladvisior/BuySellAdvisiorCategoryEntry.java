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
import com.stockfaxforu.frontend.BuySellPanelTree;
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
public class BuySellAdvisiorCategoryEntry extends JFrame implements ActionListener
{
	/**
	 * This is the default constructor
	 */
	JTextArea formula=null;
	JTextField newcategory = null,newformula=null;
	JButton save = null;
	JButton cancel = null;
	String indicatorName = null;
	Vector paras = new Vector();
	JComboBox operatorList = null;
	int index = 0;
	JComboBox type = null;
	String operationname = "";
	JLabel msg = null;
	public BuySellAdvisiorCategoryEntry()
	{
		//		super(null);
		setBackground(Color.lightGray);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Image im = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("image/toolicon.jpg"));
		this.setIconImage(im);
		this.setContentPane(getJContentPane());
		this.setTitle("Create/Delete Category");
		this.move(300, 300);
		this.setSize(900, 500);
		this.setVisible(true);
	}
	private javax.swing.JPanel jContentPane = null;
	public JComboBox category = null;
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
		
		IGSLabel lable = new IGSLabel("Select Category ");
//		lable.setFont(new Font("Arial", Font.PLAIN, 12));
//		lable.setForeground(Color.black);
		lable.setBounds(xinc, yinc, 150, 15);
		jContentPane.add(lable, null);
		
//		
		xinc = xinc + 125;
		Vector v = BuySellAdvisorUtility.getCategoriesUnique();
		category = new IndexComboBox(v);
		category.setName("category");
		category.setFont(new Font("Arial", Font.PLAIN, 12));
		category.setForeground(Color.black);
		category.setBounds(xinc, yinc, 200, 20);
		category.addActionListener(this);
		jContentPane.add(category, null);

		yinc = yinc + 40;
		xinc = 10;
		IGSLabel lable1 = new IGSLabel("New Category :");
		lable1.setBounds(xinc, yinc, 150, 15);
		jContentPane.add(lable1, null);
		
		xinc = xinc + 125;
		
		newcategory = new JTextField("");
		newcategory.setFont(new Font("Arial", Font.BOLD, 12));
		newcategory.setForeground(Color.black);
		newcategory.setBounds(xinc , yinc, 200, 20);
		jContentPane.add(newcategory, null);
		
		
		yinc = yinc + 40;
		xinc = 10;
		IGSLabel lable4 = new IGSLabel("New Formula :");
		lable4.setBounds(xinc, yinc, 150, 15);
		jContentPane.add(lable4, null);
		
		xinc = xinc + 125;
		
		newformula = new JTextField("");
		newformula.setFont(new Font("Arial", Font.BOLD, 12));
		newformula.setForeground(Color.black);
		newformula.setBounds(xinc , yinc, 200, 20);
		jContentPane.add(newformula, null);
		
		
		
		yinc = yinc + 40;
		xinc = 10;
		IGSLabel lable2 = new IGSLabel("Formula :");
		lable2.setBounds(xinc, yinc, 150, 15);
		jContentPane.add(lable2, null);
		
		
		xinc = xinc + 125;
		formula = new JTextArea(5,100);
		JScrollPane scroll = new JScrollPane(formula);
		this.add(scroll);
		
		scroll.setBounds(xinc , yinc, 700, 150);
		jContentPane.add(scroll, null);
		yinc = yinc + 175;
		
		msg = new JLabel("");
		msg.setFont(new Font("Arial", Font.BOLD, 12));
		msg.setForeground(Color.black);
		msg.setBounds(xinc, yinc, 200, 15);
		jContentPane.add(msg, null);

		xinc = 125;
		
		yinc = yinc +40;
		save = new JButton();
		save.setBounds(xinc, yinc, 75, 20);
		save.setName("save");
		save.setText("Save");
		jContentPane.add(save, null);
		save.addActionListener(this);
		
		
		
		
		
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
			String indName = newcategory.getText();
			if (name.equalsIgnoreCase("save"))
			{
				saveFormula();
			}
		}
		else if ( o instanceof JComboBox)
		{

			JComboBox cb = (JComboBox) e.getSource();
			String name = cb.getName();
			if (name.equals("category"))
			{
				String indName = this.category.getSelectedItem()+"";
				newcategory.setText(indName);	
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
		BuySellAdvisiorCategoryEntry frame = new BuySellAdvisiorCategoryEntry();
	}
	
	public void saveFormula()
	{
		if(checkforError())
			return;
		
		String formulaName= StockConstants.INSTALL_DIR+"/buysell/"+newcategory.getText()+"-" + newformula.getText();
		String formula = this.formula.getText();
		try {
			Utility.saveContent(formulaName, formula);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private boolean checkforError()
	{
		// TODO Auto-generated method stub
		return false;
	}
	
} //  @jve:visual-info  decl-index=0 visual-constraint="4,11"

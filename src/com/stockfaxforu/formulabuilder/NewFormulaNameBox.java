/*
 * Created on Feb 19, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.formulabuilder;
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
import com.stockfaxforu.component.Indicator;
import com.stockfaxforu.strategy.StrategyUtility;
import com.stockfaxforu.strategy.StrategyUtilityStockScreener;
import com.stockfaxforu.util.ManageIndicator;
import com.stockfaxforu.util.Utility;
/**
 * @author sdeopura
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class NewFormulaNameBox extends JFrame implements ActionListener
{
	/**
	 * This is the default constructor
	 */
	JTextField number = null;
	JTextField daybefore = null;
	JButton add = null;
	JButton cancel = null;
	String indicatorName = null;
	FormulaBuilderScreen forbuilder;
	Vector paras = new Vector();
	JComboBox operatorList = null;
	FormulaDisplayPanel fordisplaypanel = null;
	int index = 0;
	JComboBox type = null;
	String operationname = "";
	public NewFormulaNameBox(FormulaBuilderScreen forbuilder)
	{
		//		super(null);
		setBackground(Color.lightGray);
		this.forbuilder = forbuilder;
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setContentPane(getJContentPane());
		this.setTitle("Formula Name Entry");
		this.move(300, 300);
		this.setSize(500, 250);
		this.setVisible(true);
		Image im = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("image/toolicon.jpg"));
		this.setIconImage(im);
		this.setResizable(false);
		
	}
	private javax.swing.JPanel jContentPane = null;
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
		JLabel lable = new JLabel("Formula Name :");
		lable.setFont(new Font("Arial", Font.BOLD, 12));
		lable.setForeground(Color.black);
		lable.setBounds(xinc, yinc, 100, 15);
		jContentPane.add(lable, null);
		number = new JTextField("");
		number.setFont(new Font("Arial", Font.BOLD, 12));
		number.setForeground(Color.black);
		number.setBounds(xinc + 150, yinc, 300, 20);
		jContentPane.add(number, null);
		yinc = yinc + 40;
		JLabel lable1 = new JLabel("Type :");
		lable1.setFont(new Font("Arial", Font.BOLD, 12));
		lable1.setForeground(Color.black);
		lable1.setBounds(xinc, yinc, 100, 15);
		jContentPane.add(lable1, null);
		String[] typeopt = { "Buy", "Sell" };
		type = new JComboBox(typeopt);
		type.setFont(new Font("Arial", Font.BOLD, 12));
		type.setForeground(Color.black);
		type.setBounds(xinc + 150, yinc, 100, 20);
		jContentPane.add(type, null);
		yinc = yinc + 40;
		setSize(300, 300);
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
				String s = forbuilder.formulaName.getText();
				
				if (this.operationname.equalsIgnoreCase("saveas") || this.operationname.equalsIgnoreCase("save"))
				{
					String lformulaname = number.getText();
					String lformulatype = type.getSelectedItem().toString();
					String formula = forbuilder.fordispanel.getFormula();
					
					forbuilder.formulaName.setText(number.getText());
					forbuilder.formulaType.setText(type.getSelectedItem().toString());
					
					if(forbuilder.isStockScreener)
					{
						StrategyUtilityStockScreener.saveFormula(lformulatype,lformulaname,formula,"");
			
					}
					else
					{
//						StrategyUtility.saveFormula(lformulatype,lformulaname,formula,"");
			
					}
					forbuilder.repaint();
				} 
				else
				{
					if (this.operationname.equalsIgnoreCase("newformula"))
					{
						forbuilder.fordispanel.resetTokens();
						forbuilder.fordispanel.repaint();
						forbuilder.formulaName.setText("" + number.getText());
						forbuilder.formulaType.setText("" + type.getSelectedItem().toString());
						forbuilder.repaint();
					} else
					{
						//					forbuilder.fordispanel.resetTokens();
						forbuilder.fordispanel.repaint();
						forbuilder.formulaName.setText(number.getText());
						forbuilder.formulaType.setText(type.getSelectedItem().toString());
						forbuilder.repaint();
					}
				}
				this.dispose();
			} else if (name.equalsIgnoreCase("cancel"))
			{
				this.dispose();
			}
		}
	}
	/**
	 * @return
	 */
	public static void main(String[] args)
	{
		JFrame frame = new JFrame("Test angled lines");
		frame.move(300, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Vector v = new Vector();
		v.addElement("Period 1(1..100)");
		v.addElement("Period 2(2..50)");
		//		NumberEntryPanel d = new NumberEntryPanel(null, 0, null);
		//		frame.getContentPane().add(d);
		frame.pack();
		//		frame.setBounds(0,0,1000,700);
		frame.setVisible(true);
	}
} //  @jve:visual-info  decl-index=0 visual-constraint="4,11"

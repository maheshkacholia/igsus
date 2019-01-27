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
import com.stockfaxforu.util.ManageIndicator;
import com.stockfaxforu.util.Utility;
/**
 * @author sdeopura
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class OperatorEditPanel extends JFrame implements ActionListener
{
	/**
	 * This is the default constructor
	 */
	JTextField[] variable = null;
	JTextField daybefore = null;
	JButton add = null;
	JButton cancel = null;
	JComboBox indiactorList = null;
	GraphComponentContainer maingraph = null;
	String indicatorName = null;
	Indicator selInd = null;
	JLabel[] periods = new JLabel[5];
	FormulaDisplayPanel forbuilder;
	Vector paras = new Vector();
	JComboBox operatorList = null;
	int index = 0;
	public OperatorEditPanel(String indicatorNameWithPara, int index, FormulaDisplayPanel forbuilder)
	{
		//		super(null);
		setBackground(Color.lightGray);
		this.indicatorName = indicatorNameWithPara;
		this.forbuilder = forbuilder;
		this.index = index;
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setContentPane(getJContentPane());
		this.setTitle("Edit Operator");
		this.move(300, 300);
		this.setSize(400, 250);
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
		variable = new JTextField[3];
		if (jContentPane == null)
		{
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
		}
		JLabel lable = new JLabel("Operator :");
		lable.setFont(new Font("Arial", Font.BOLD, 12));
		lable.setForeground(Color.black);
		lable.setBounds(xinc, yinc, 100, 15);
		jContentPane.add(lable, null);
		Vector operatorVector = ManageVariable.getOperators();
		operatorVector.removeElement(this.indicatorName);
		operatorVector.insertElementAt(this.indicatorName, 0);
		operatorList = new JComboBox(operatorVector);
		operatorList.setName("operatorList");
		operatorList.setBounds(xinc + xincdis, yinc, 100, 20);
		jContentPane.add(operatorList, null);
		operatorList.addActionListener(this);
		
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
			String indName = "";
			if (name.equalsIgnoreCase("add"))
			{
					((JLabel)forbuilder.tokens.elementAt(index)).setText(operatorList.getSelectedItem().toString());
					//
					forbuilder.repaint();
					this.dispose();
			}
			else if (name.equalsIgnoreCase("cancel"))
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
		OperatorEditPanel d = new OperatorEditPanel(null, 0, null);
		frame.getContentPane().add(d);
		frame.pack();
		//		frame.setBounds(0,0,1000,700);
		frame.setVisible(true);
	}
} //  @jve:visual-info  decl-index=0 visual-constraint="4,11"

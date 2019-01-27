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
public class NumberEntryPanel extends JFrame implements ActionListener
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
	FormulaDisplayPanel fordisplaypanel=null;
	int index = 0;


	public NumberEntryPanel(String indicatorNameWithPara, int index,FormulaDisplayPanel fordisplaypanel)
	{
	
		//		super(null);
		setBackground(Color.lightGray);
		this.indicatorName = indicatorNameWithPara;
		this.fordisplaypanel = fordisplaypanel;
		this.index = index;
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setContentPane(getJContentPane());
		this.setTitle("Edit Number");
		this.move(300, 300);
		this.setSize(350, 200);
		this.setVisible(true);
		Image im = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("image/toolicon.jpg"));
		this.setIconImage(im);
		this.setResizable(false);
	}



	public NumberEntryPanel(String indicatorNameWithPara, FormulaBuilderScreen forbuilder)
	{
		//		super(null);
		setBackground(Color.lightGray);
		Image im = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("image/toolicon.jpg"));
		this.setIconImage(im);

		this.indicatorName = indicatorNameWithPara;
		this.forbuilder = forbuilder;
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setContentPane(getJContentPane());
		this.setTitle("Add Number");
		this.move(300, 300);
		this.setSize(350, 200);
		this.setVisible(true);
	}
	private javax.swing.JPanel jContentPane = null;
	private javax.swing.JPanel getJContentPane()
	{
		int xinc = 10;
		int yinc = 20;
		int xincdis = 120;
		
		if(jContentPane == null) 
		{
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
		}

		JLabel lable = new JLabel("Number :");
		lable.setFont(new Font("Arial", Font.BOLD, 12));
		lable.setForeground(Color.black);
		lable.setBounds(xinc, yinc, 100, 15);
		
		jContentPane.add(lable, null);
		if(this.forbuilder != null)
			number = new JTextField("0");
		else
			number = new JTextField(this.indicatorName);
			
		number.setFont(new Font("Arial", Font.BOLD, 12));
		number.setForeground(Color.black);
		number.setBounds(xinc + 150, yinc, 50, 20);
		jContentPane.add(number, null);
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
			String indName =number.getText();
			if (name.equalsIgnoreCase("add"))
			{
				if(this.forbuilder != null)
				{
					this.forbuilder.fordispanel.addToken(indName);
//					this.forbuilder.addoperator.setEnabled(true);
//					this.forbuilder.addindicator.setEnabled(false);
					
				}
				else
				{
					((JLabel)fordisplaypanel.tokens.elementAt(index)).setText(indName);
					fordisplaypanel.repaint();
				}
				
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
//		NumberEntryPanel d = new NumberEntryPanel(null, 0, null);
//		frame.getContentPane().add(d);
		frame.pack();
		//		frame.setBounds(0,0,1000,700);
		frame.setVisible(true);
	}
} //  @jve:visual-info  decl-index=0 visual-constraint="4,11"

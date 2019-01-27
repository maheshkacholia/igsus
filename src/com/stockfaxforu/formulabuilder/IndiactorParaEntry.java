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
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import com.stockfaxforu.component.*;
import com.stockfaxforu.component.Indicator;
import com.stockfaxforu.frontend.SelectIndicators;
import com.stockfaxforu.util.ManageIndicator;
import com.stockfaxforu.util.Utility;
/**
 * @author sdeopura
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class IndiactorParaEntry extends JFrame implements ActionListener
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
	JComboBox operatorList = null;
	FormulaBuilderScreen forbuilder;
	public IndiactorParaEntry(String indicatorName, FormulaBuilderScreen forbuilder)
	{
		//		super(null);
		setBackground(Color.lightGray);
		this.indicatorName = indicatorName;
		this.forbuilder = forbuilder;
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setContentPane(getJContentPane());
		this.setTitle("Add Indicator");
		this.move(300, 300);
		this.setSize(300, 350);
		Image im = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("image/toolicon.jpg"));
		this.setIconImage(im);
		this.setVisible(true);
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
		JLabel lable = new JLabel("Indicator :");
		lable.setFont(new Font("Arial", Font.BOLD, 12));
		lable.setForeground(Color.black);
		lable.setBounds(xinc, yinc, 100, 15);
		jContentPane.add(lable, null);
		JLabel lable1 = new JLabel(indicatorName);
		lable1.setFont(new Font("Arial", Font.BOLD, 12));
		lable1.setForeground(Color.black);
		lable1.setBounds(xinc + xincdis, yinc, 300, 15);
		jContentPane.add(lable1, null);
		yinc = yinc + 40;
		JLabel daybeforelabel = new JLabel("Day Before:");
		daybeforelabel.setFont(new Font("Arial", Font.BOLD, 12));
		daybeforelabel.setForeground(Color.black);
		daybeforelabel.setBounds(xinc, yinc, 100, 15);
		jContentPane.add(daybeforelabel, null);
		this.daybefore = new JTextField("0");
		this.daybefore.setFont(new Font("Arial", Font.BOLD, 12));
		this.daybefore.setForeground(Color.black);
		this.daybefore.setBounds(xinc + xincdis, yinc, 100, 15);
		jContentPane.add(this.daybefore, null);
		yinc = yinc + 40;
		periods[0] = new JLabel("Period 1:");
		periods[0].setFont(new Font("Arial", Font.BOLD, 12));
		periods[0].setForeground(Color.black);
		periods[0].setBounds(xinc, yinc, 100, 15);
		jContentPane.add(periods[0], null);
		variable[0] = new JTextField("0");
		variable[0].setFont(new Font("Arial", Font.BOLD, 12));
		variable[0].setForeground(Color.black);
		variable[0].setBounds(xinc + xincdis, yinc, 100, 15);
		variable[0].disable();
		jContentPane.add(variable[0], null);
		yinc = yinc + 40;
		periods[1] = new JLabel("Period 2:");
		periods[1].setFont(new Font("Arial", Font.BOLD, 12));
		periods[1].setForeground(Color.black);
		periods[1].setBounds(xinc, yinc, 100, 15);
		jContentPane.add(periods[1], null);
		variable[1] = new JTextField("0");
		variable[1].setFont(new Font("Arial", Font.BOLD, 12));
		variable[1].setForeground(Color.black);
		variable[1].setBounds(xinc + xincdis, yinc, 100, 15);
		variable[1].disable();
		jContentPane.add(variable[1], null);
		yinc = yinc + 40;
		periods[2] = new JLabel("Period 3:");
		periods[2].setFont(new Font("Arial", Font.BOLD, 12));
		periods[2].setForeground(Color.black);
		periods[2].setBounds(xinc, yinc, 100, 15);
		jContentPane.add(periods[2], null);
		variable[2] = new JTextField("0");
		variable[2].setFont(new Font("Arial", Font.BOLD, 12));
		variable[2].setForeground(Color.black);
		variable[2].setBounds(xinc + xincdis, yinc, 100, 15);
		variable[2].disable();
		jContentPane.add(variable[2], null);
		int noofperiods = Integer.parseInt((String) ManageVariable.getValues(indicatorName, ManageVariable.PARAS));
		int[] defaultvalue = (int[]) ManageVariable.getValues(indicatorName, ManageVariable.DEFAULTVALUE);
		String[] textname = (String[]) ManageVariable.getValues(indicatorName, ManageVariable.PERIODNAME);
		selInd = new Indicator(indicatorName, noofperiods, 1);
		for (int i = 0; i < variable.length; i++)
		{
			variable[i].disable();
			variable[i].setVisible(false);
			periods[i].setVisible(false);
		}
		for (int i = 0; i < noofperiods; i++)
		{
			variable[i].enable();
			variable[i].setText(defaultvalue[i] + "");
			periods[i].setText(textname[i]);
			variable[i].setVisible(true);
			periods[i].setVisible(true);
		}
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
		if (o instanceof JComboBox)
		{
			JButton cb = (JButton) e.getSource();
			String name = cb.getName();
			if (name.equalsIgnoreCase("operatorList"))
			{
				this.indicatorName = operatorList.getSelectedItem().toString();
				int noofperiods = Integer.parseInt((String) ManageVariable.getValues(indicatorName, ManageVariable.PARAS));
				int[] defaultvalue = (int[]) ManageVariable.getValues(indicatorName, ManageVariable.DEFAULTVALUE);
				String[] textname = (String[]) ManageVariable.getValues(indicatorName, ManageVariable.PERIODNAME);
				selInd = new Indicator(indicatorName, noofperiods, 1);
				for (int i = 0; i < variable.length; i++)
				{
					variable[i].disable();
					variable[i].setVisible(false);
					periods[i].setVisible(false);
				}
				for (int i = 0; i < noofperiods; i++)
				{
					variable[i].enable();
					variable[i].setText(defaultvalue[i] + "");
					periods[i].setText(textname[i]);
					variable[i].setVisible(true);
					periods[i].setVisible(true);
				}
				repaint();
			}
		}
		else if (o instanceof JButton)
		{
			JButton cb = (JButton) e.getSource();
			String name = cb.getName();
			String indName = "";
			if (name.equalsIgnoreCase("add"))
			{
				if (!checkValue())
				{
					int i = 0;
					try
					{
						i = Integer.parseInt(this.daybefore.getText());
					}
					catch (Exception e1)
					{
						MessageDiaglog msg = new MessageDiaglog("Pls enter Integer value only");
						this.daybefore.setText("0");
						return;
					}
					if (i < 0)
					{
						MessageDiaglog msg = new MessageDiaglog("Pls enter +ive integer value or zero");
						this.daybefore.setText("0");
						return;
					}
					i = -1 * i;
					try
					{
						if (selInd.noofperiods == 0)
						{
							indName = this.indicatorName + "[" + i + "]";
						}
						else if (selInd.noofperiods == 1)
						{
							int period1 = Integer.parseInt(variable[0].getText().trim());
							if (period1 < 0)
							{
								MessageDiaglog msg = new MessageDiaglog("Pls enter +ive integer value or zero");
								return;
							}
							indName = this.indicatorName + "[" + period1 + ";" + i + "]";
						}
						else if (selInd.noofperiods == 2)
						{
							int period1 = Integer.parseInt(variable[0].getText().trim());
							int period2 = Integer.parseInt(variable[1].getText().trim());
							if (period1 < 0 || period2 < 0)
							{
								MessageDiaglog msg = new MessageDiaglog("Pls enter +ive integer value or zero");
								return;
							}
							indName = this.indicatorName + "[" + period1 + ";" + period2 + ";" + i + "]";
						}
						else if (selInd.noofperiods == 3)
						{
							int period1 = Integer.parseInt(variable[0].getText().trim());
							int period2 = Integer.parseInt(variable[1].getText().trim());
							int period3 = Integer.parseInt(variable[2].getText().trim());
							if (period1 < 0 || period2 < 0 || period3 < 0)
							{
								MessageDiaglog msg = new MessageDiaglog("Pls enter +ive integer value or zero");
								return;
							}
							indName = this.indicatorName + "[" + period1 + ";" + period2 + ";" + period3 + ";" + i + "]";
						}
					}
					catch (Exception e1)
					{
						MessageDiaglog msg = new MessageDiaglog("Pls enter Integer value only");
						return;
					}
					if (forbuilder instanceof SelectIndicators)
					{
						SelectIndicators selind = (SelectIndicators) forbuilder;
						selind.selectededindiactorVector.addElement(indName);
						selind.selectededindiactorList10.setListData(selind.selectededindiactorVector);
					}
					else
					{
						this.forbuilder.fordispanel.addToken(indName);
						this.forbuilder.repaint();
					}
					//			this.forbuilder.addoperator.setEnabled(true);
					//			this.forbuilder.addindicator.setEnabled(false);
					this.dispose();
				}
			}
			if (name.equalsIgnoreCase("cancel"))
			{
				this.dispose();
			}
		}
		else if (o instanceof JComboBox)
		{
			JComboBox cb = (JComboBox) e.getSource();
			String name = cb.getName();
			//			String indname = (String)cb.getSelectedItem();
			String indname = ManageIndicator.indicatorVector.elementAt(cb.getSelectedIndex()) + "";
		}
		// TODO Auto-generated method stub
	}
	/**
	 * @return
	 */
	private boolean checkValue()
	{
		// TODO Auto-generated method stub
		return false;
	}
	public static void main(String[] args)
	{
		JFrame frame = new JFrame("Test angled lines");
		frame.move(300, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Vector v = new Vector();
		v.addElement("Period 1(1..100)");
		v.addElement("Period 2(2..50)");
		IndiactorParaEntry d = new IndiactorParaEntry(null, null);
		frame.getContentPane().add(d);
		frame.pack();
		//		frame.setBounds(0,0,1000,700);
		frame.setVisible(true);
	}
} //  @jve:visual-info  decl-index=0 visual-constraint="4,11"

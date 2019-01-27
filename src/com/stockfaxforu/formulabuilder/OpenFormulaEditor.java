/*
 * Created on Feb 24, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.formulabuilder;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import com.stockfaxforu.component.GraphComponentContainer;
import com.stockfaxforu.strategy.StrategyUtility;
import com.stockfaxforu.strategy.StrategyUtilityCustmizedIndicator;
import com.stockfaxforu.strategy.StrategyUtilityStockScreener;
import com.stockfaxforu.util.Utility;
import java.util.*;
//SplitPaneDemo itself is not a visible component.
public class OpenFormulaEditor extends JFrame implements ListSelectionListener, ActionListener, MouseListener
{
	private FormulaBuilderScreen forbuiscreen;
	private JList list;
	private JList list1;
	private JTextField code;
	private JTextField value;
	private javax.swing.JPanel jContentPane;
	Vector stockCode, listvector, list1vector;
	Vector stockName;
	private JTextArea formula = null;
	public OpenFormulaEditor(FormulaBuilderScreen forbuiscreen)
	{
		//		super(null);
		setBackground(Color.lightGray);
		this.forbuiscreen = forbuiscreen;
		if(forbuiscreen instanceof CustmizedIndicatorBuilderScreen)
		{
			this.forbuiscreen = (CustmizedIndicatorBuilderScreen)forbuiscreen;
		}
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setContentPane(getJContentPane());
		this.setTitle("Select Formula");
		this.move(300, 200);
		this.setSize(850, 420);
		this.setVisible(true);
		Image im = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("image/toolicon.jpg"));
		this.setIconImage(im);
		this.setResizable(false);
		
	}
	public javax.swing.JPanel getJContentPane()
	{
		listvector = new Vector();
		list1vector = new Vector();
		if (jContentPane == null)
		{
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
		}
		int xinc = 20;
		int yinc = 10;
		jContentPane = new JPanel(null);
		JLabel lable = new JLabel("Select Strategy");
		lable.setFont(new Font("Arial", Font.BOLD, 12));
		lable.setForeground(Color.black);
		lable.setBounds(xinc, yinc, 100, 15);
		jContentPane.add(lable, null);
		//			yinc = yinc + 20;
		if (this.forbuiscreen.isStockScreener)
		{
			listvector = StrategyUtilityStockScreener.getAllStrategy();
		} 
		else if(this.forbuiscreen instanceof CustmizedIndicatorBuilderScreen)
		{
			listvector = StrategyUtilityCustmizedIndicator.getAllStrategy();
		}
		else
		{
			listvector = StrategyUtility.getAllStrategy();
			
		}
		list = new JList(listvector);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setSelectedIndex(0);
		list.addListSelectionListener(this);
		list.addMouseListener(this);
		JScrollPane listScrollPane = new JScrollPane(list);
		listScrollPane.setBounds(xinc, yinc + 20, 300, 300);
		jContentPane.add(listScrollPane);
		//			yinc = yinc + 300;
		xinc = xinc + 350;
		JLabel lable4 = new JLabel("Formula & Comment");
		lable4.setFont(new Font("Arial", Font.BOLD, 12));
		lable4.setForeground(Color.black);
		lable4.setBounds(xinc, yinc, 300, 15);
		jContentPane.add(lable4, null);
		//			yinc = yinc + 20;
		formula = new JTextArea();
		formula.setFont(new Font("Arial", Font.BOLD, 12));
		JScrollPane jscpn = new JScrollPane(formula);
		jscpn.setBounds(xinc, yinc + 20, 450, 300);
		formula.setName("formula");
		jContentPane.add(jscpn);
		yinc = yinc + 340;
		JButton apply = new JButton("Apply");
		apply.setFont(new Font("Arial", Font.BOLD, 12));
		apply.setBounds(xinc - 350, yinc, 100, 20);
		apply.addActionListener(this);
		apply.setName("Apply");
		jContentPane.add(apply);
		JButton cancel = new JButton("Cancel");
		cancel.setFont(new Font("Arial", Font.BOLD, 12));
		cancel.setBounds(xinc - 350 + 120, yinc, 100, 20);
		cancel.addActionListener(this);
		cancel.setName("Cancel");
		jContentPane.add(cancel);
		JButton delete = new JButton("Delete");
		delete.setFont(new Font("Arial", Font.BOLD, 12));
		delete.setBounds(xinc - 350 + 120 + 120, yinc, 100, 20);
		delete.addActionListener(this);
		delete.setName("Delete");
		jContentPane.add(delete);
		return jContentPane;
	}
	//Listens to the list
	boolean textEventChk = false;
	public void valueChanged(ListSelectionEvent e)
	{
		Object obj = e.getSource();
		if (obj instanceof JList)
		{
			JList mylist = (JList) e.getSource();
			String s = (String) mylist.getSelectedValue();

			String forval="";	
			if (this.forbuiscreen.isStockScreener)
			{
				forval = StrategyUtilityStockScreener.getFormulaAndComment(s);

			}
			else if(this.forbuiscreen instanceof CustmizedIndicatorBuilderScreen)
			{
				forval = StrategyUtilityCustmizedIndicator.getFormulaAndComment(s);
			}
			else
			{
				forval = StrategyUtility.getFormulaAndComment(s);
				
			}
			formula.setText(forval);
		}
	}
	public JPanel getSplitPane()
	{
		return jContentPane;
	}
	public static void main(String[] args)
	{}
	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	public void actionPerformed(ActionEvent arg0)
	{
		Object obj = (JButton) arg0.getSource();
		if (obj instanceof JButton)
		{
			String name = ((JButton) obj).getName();
			if (name.equals("Cancel"))
			{
				dispose();
			} else if (name.equals("Apply"))
			{
				String s = (String) list.getSelectedValue();
				if (s == null)
				{
					JFrame mesg = new MessageDiaglog("No Formula is Selected,Pls Select a Formula");
					return;
				}
				HashMap hs = null;
				if (this.forbuiscreen.isStockScreener)
				{
					hs = StrategyUtilityStockScreener.getStrategyMap(s);

				} 
				else if(this.forbuiscreen instanceof CustmizedIndicatorBuilderScreen)
				{
					hs = StrategyUtilityCustmizedIndicator.getStrategyMap(s);
				}
				else
				{
					hs = StrategyUtility.getStrategyMap(s);

				}
//				// ln(hs.get(StrategyUtilityStockScreener.Type));
//				// ln(this.forbuiscreen.formulaType.getText());

				forbuiscreen.formulaType.setText(hs.get(StrategyUtilityStockScreener.Type) + "");
				forbuiscreen.formulaName.setText(hs.get(StrategyUtilityStockScreener.Name) + "");
				
				//					forbuiscreen.fordispanel.formulaType = (String)hs.get(StrategyUtility.Type);
				forbuiscreen.fordispanel.openFormula((String) hs.get(StrategyUtilityStockScreener.Formula));

				forbuiscreen.repaint();
				forbuiscreen.fordispanel.repaint();
				dispose();
			} else if (name.equals("Delete"))
			{
				String s = (String) list.getSelectedValue();
				if (s == null)
				{
					JFrame mesg = new MessageDiaglog("No Formula is Selected,Pls Select a Formula");
					return;
				}
	
				HashMap hs = null;
				if (this.forbuiscreen.isStockScreener)
				{
					hs = StrategyUtilityStockScreener.getStrategyMap(s);
					StrategyUtilityStockScreener.deleteFormula(hs.get(StrategyUtility.Name) + "");
					listvector = StrategyUtilityStockScreener.getAllStrategy();
					list.setListData(listvector);


				} else
				{
					hs = StrategyUtility.getStrategyMap(s);
					StrategyUtility.deleteFormula(hs.get(StrategyUtility.Name) + "");
					listvector = StrategyUtility.getAllStrategy();
					list.setListData(listvector);

				}
	
				repaint();
			}
		}
		// TODO Auto-generated method stub
	}
	public void mouseClicked(MouseEvent arg0)
	{

		Object obj = arg0.getSource();
		if (obj instanceof JList)
		{
			JList mylist = (JList) arg0.getSource();
			String s = (String) mylist.getSelectedValue();
			if (s == null)
				return;
			String forval="";	
			if (this.forbuiscreen.isStockScreener)
			{
				forval = StrategyUtilityStockScreener.getFormulaAndComment(s);

			}
			
			else if( this.forbuiscreen instanceof CustmizedIndicatorBuilderScreen )
			{
				forval = StrategyUtilityCustmizedIndicator.getFormulaAndComment(s);
				
			}
			else
			{
				forval = StrategyUtility.getFormulaAndComment(s);
				
			}
			formula.setText(forval);
		}
		// TODO Auto-generated method stub
	}
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	public void mousePressed(MouseEvent arg0)
	{
		// TODO Auto-generated method stub
	}
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	public void mouseReleased(MouseEvent arg0)
	{

		// TODO Auto-generated method stub
	}
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	public void mouseEntered(MouseEvent arg0)
	{

		// TODO Auto-generated method stub
	}
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	public void mouseExited(MouseEvent arg0)
	{
		// TODO Auto-generated method stub
	}
}

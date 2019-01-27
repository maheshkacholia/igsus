/*
 * Created on Feb 24, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.frontend;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import com.stockfaxforu.component.GraphComponentContainer;
import com.stockfaxforu.strategy.StrategyUtility;
import com.stockfaxforu.util.Utility;
import java.util.*;
//SplitPaneDemo itself is not a visible component.
public class SelectComponent extends JPanel implements ListSelectionListener, ActionListener, MouseListener
{
	private GraphComponentContainer macdgraph;
	private JList list;
	private JList list1;
	private JTextField code;
	private JTextField value;
	private javax.swing.JPanel jContentPane;
	Vector stockCode, listvector, list1vector;
	Vector stockName;
	private JTextArea formula = null;
	public JLabel label1,label2;
	public SelectComponent(int x, int y,Vector listvector)
	{
		//		super(null);
		setBackground(Color.lightGray);
		this.setSize(x, y);
		this.setVisible(true);
		
		int xinc = 20;
		int yinc = 10;
		
		label1 = new JLabel("Select Strategy");
		label1.setFont(new Font("Arial", Font.BOLD, 12));
		label1.setForeground(Color.black);
		label1.setBounds(xinc, yinc, 100, 15);
		add(label1, null);
		
		label2 = new JLabel("Selected Strategy");
		label2.setFont(new Font("Arial", Font.BOLD, 12));
		label2.setForeground(Color.black);
		label2.setBounds(xinc + 350, yinc, 200, 15);
		add(label2, null);
		
		JButton select = new JButton(">>");
		select.setFont(new Font("Arial", Font.BOLD, 12));
		select.setBounds(xinc + 275, 150, 50, 20);
		select.addActionListener(this);
		select.setName("select");
		add(select);
		JButton deselect = new JButton("<<");
		deselect.setFont(new Font("Arial", Font.BOLD, 12));
		deselect.setBounds(xinc + 275, 200, 50, 20);
		deselect.addActionListener(this);
		deselect.setName("deselect");
		add(deselect);
		yinc = yinc + 20;

		this.listvector = listvector;
		list = new JList(listvector);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setSelectedIndex(0);
		list.addListSelectionListener(this);
		list.addMouseListener(this);
		JScrollPane listScrollPane = new JScrollPane(list);
		listScrollPane.setBounds(xinc, yinc, 250, 400);
		add(listScrollPane);
		list1 = new JList();
		list1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list1.addListSelectionListener(this);
		list1.addMouseListener(this);
		JScrollPane pictureScrollPane = new JScrollPane(list1);
		pictureScrollPane.setBounds(xinc + 350, yinc, 250, 400);
		add(pictureScrollPane);

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
			String forval = StrategyUtility.getFormulaAndComment(s);
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
			if (name.equals("select"))
			{
				if (list.getSelectedIndex() == -1)
					return;
				String s = (String) list.getSelectedValue();
				list1vector.addElement(s);
				list1.setListData(list1vector);
				listvector.removeElement(s);
				list.setListData(listvector);
			} else if (name.equals("deselect"))
			{
				if (list1.getSelectedIndex() == -1)
					return;
				String s = (String) list1.getSelectedValue();
				listvector.addElement(s);
				list.setListData(listvector);
				list1vector.removeElement(s);
				list1.setListData(list1vector);
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
			String forval = StrategyUtility.getFormulaAndComment(s);
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

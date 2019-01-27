/*
 * Created on Feb 24, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.purelive;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import com.stockfaxforu.formulabuilder.MessageDiaglog;
import com.stockfaxforu.util.Utility;
import java.util.*;
//SplitPaneDemo itself is not a visible component.
public class AddRemoveStock extends JFrame implements ActionListener, ListSelectionListener
{
//	private ShowLiveMarketPure panelforgrapha;
	private JList list;
	private JList list1;
	private JTextField code;
	private JTextField value;
	private javax.swing.JPanel jContentPane;
	Vector stockCode;
	Vector selectedStockCode;
	public AddRemoveStock(ShowLiveMarketPure panelforgrapha)
	{
		//		super(null);
		Image im = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("image/toolicon.jpg"));
		this.setIconImage(im);

		setBackground(Color.lightGray);
//		this.panelforgrapha = panelforgrapha;
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setContentPane(getJContentPane());
		this.setTitle("Add Stock to Watch ");
		this.move(100, 100);
		this.setSize(450, 550);
		this.setVisible(true);
	}
	public javax.swing.JPanel getJContentPane()
	{
		if (jContentPane == null)
		{
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
		}
		int xinc = 20;
		int yinc = 10;
		jContentPane = new JPanel(null);
		JLabel lable = new JLabel("Select Stock");
		lable.setFont(new Font("Arial", Font.BOLD, 12));
		lable.setForeground(Color.black);
		lable.setBounds(xinc, yinc, 100, 15);
		jContentPane.add(lable, null);
		JLabel lable1 = new JLabel("Selected Stock");
		lable1.setFont(new Font("Arial", Font.BOLD, 12));
		lable1.setForeground(Color.black);
		lable1.setBounds(xinc + 225, yinc, 100, 15);
		jContentPane.add(lable1, null);
		yinc = yinc + 30;
		stockCode = Utility.getLiveStockList();
		list = new JList(stockCode);
		list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		list.setSelectedIndex(0);
		list.addListSelectionListener(this);
		JScrollPane listScrollPane = new JScrollPane(list);
		listScrollPane.setBounds(xinc, yinc, 150, 400);
		jContentPane.add(listScrollPane);
		selectedStockCode = new Vector();
		list1 = new JList(selectedStockCode);
		list1.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		list1.setSelectedIndex(0);
		list1.addListSelectionListener(this);
		JScrollPane pictureScrollPane = new JScrollPane(list1);
		pictureScrollPane.setBounds(xinc + 225, yinc, 150, 400);
		jContentPane.add(pictureScrollPane);
		yinc = yinc + 420;
		JButton ok = new JButton("OK");
		ok.setFont(new Font("Arial", Font.BOLD, 12));
		ok.setBounds(xinc, yinc, 100, 20);
		ok.addActionListener(this);
		ok.setName("ok");
		jContentPane.add(ok);
		JButton cancel = new JButton("Cancel");
		cancel.setFont(new Font("Arial", Font.BOLD, 12));
		cancel.setBounds(xinc + 225, yinc, 100, 20);
		cancel.addActionListener(this);
		cancel.setName("cancel");
		jContentPane.add(cancel);
		JButton select = new JButton(">>");
		select.setFont(new Font("Arial", Font.BOLD, 12));
		select.setBounds(xinc + 160, 200, 50, 20);
		select.addActionListener(this);
		select.setName("select");
		jContentPane.add(select);
		JButton deselect = new JButton("<<");
		deselect.setFont(new Font("Arial", Font.BOLD, 12));
		deselect.setBounds(xinc + 160, 250, 50, 20);
		deselect.addActionListener(this);
		deselect.setName("deselect");
		jContentPane.add(deselect);
		return jContentPane;
	}
	//Listens to the list
	boolean textEventChk = false;
	public void actionPerformed(ActionEvent arg0)
	{
		Object obj = (JButton) arg0.getSource();
		if (obj instanceof JButton)
		{
			String name = ((JButton) obj).getName();
			if (name.equals("ok"))
			{
				StringBuffer sb = new StringBuffer();
				for(int i=0;i<selectedStockCode.size();i++)
				{
					sb.append("|"+selectedStockCode.elementAt(i)+"|");
				}
//				GetLiveData.fetchSymbolList = sb.toString();
//				panelforgrapha.histDataHashMap = new HashMap();
//				panelforgrapha.selectedIndex = -1;
					dispose();
			}
			else if (name.equals("select"))
			{
				Object[] selectedstockindex = list.getSelectedValues();
				if (selectedstockindex == null || selectedstockindex.length==0)
				{
					MessageDiaglog msg = new MessageDiaglog("No stock Selected,Select a stock to add");
					return;
				}
				for(int j=0;j<selectedstockindex.length;j++)
				{
//					int i= selectedstockindex[j];
//					String stock = (String) stockCode.elementAt(i);
					stockCode.remove(selectedstockindex[j]);
					selectedStockCode.addElement(selectedstockindex[j]);
				}
				list.setListData(stockCode);
				list1.setListData(selectedStockCode);

			}
			else if (name.equals("deselect"))
			{
				Object[] selectedstockindex = list1.getSelectedValues();
				if (selectedstockindex == null || selectedstockindex.length==0)
				{
					MessageDiaglog msg = new MessageDiaglog("No stock Selected,Select a stock to remove");
					return;
				}
				for(int j=0;j<selectedstockindex.length;j++)
				{
//					int i= selectedstockindex[j];
//					String stock = (String) stockCode.elementAt(i);
					selectedStockCode.remove(selectedstockindex[j]);
					stockCode.addElement(selectedstockindex[j]);
				}
				list.setListData(stockCode);
				list1.setListData(selectedStockCode);
			}
			else if (name.equals("cancel"))
			{
				dispose();
			}
		}
		// TODO Auto-generated method stub
	}
	/**
	 * @param stockname
	 * @return
	 */
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
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
	/* (non-Javadoc)
	 * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
	 */
	public void valueChanged(ListSelectionEvent arg0)
	{
		// TODO Auto-generated method stub
	}
}

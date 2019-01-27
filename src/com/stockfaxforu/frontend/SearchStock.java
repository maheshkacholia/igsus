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
import com.stockfaxforu.util.Utility;
import java.util.*;
//SplitPaneDemo itself is not a visible component.
public class SearchStock extends JFrame implements ListSelectionListener, KeyListener, ActionListener, MouseListener
{
	private PanelForGraphImpl panelforgrapha;
	private JList list;
	private JList list1;
	private JTextField code;
	private JTextField value;
	private javax.swing.JPanel jContentPane;
	Vector stockCode;
	Vector stockName;
	public SearchStock(PanelForGraphImpl panelforgrapha)
	{
		//		super(null);
		setBackground(Color.lightGray);
		this.panelforgrapha = panelforgrapha;
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setContentPane(getJContentPane());
		this.setTitle("Search Stock");
		this.move(100, 100);
		Image im = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("image/toolicon.jpg"));
		this.setIconImage(im);

		this.setSize(600, 600);
		this.setVisible(true);
		this.setResizable(false);
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
			JLabel lable = new JLabel("Company Code");
			lable.setFont(new Font("Arial", Font.BOLD, 12));
			lable.setForeground(Color.black);
			lable.setBounds(xinc, yinc, 100, 15);
			jContentPane.add(lable, null);
			JLabel lable1 = new JLabel("Company Name");
			lable1.setFont(new Font("Arial", Font.BOLD, 12));
			lable1.setForeground(Color.black);
			lable1.setBounds(xinc + 225, yinc, 100, 15);
			jContentPane.add(lable1, null);
			yinc = yinc + 20;
			code = new JTextField();
			code.setFont(new Font("Arial", Font.BOLD, 12));
			code.setBounds(xinc, yinc, 150, 20);
			code.addActionListener(this);
			code.addKeyListener(this);
			code.setName("code");
			jContentPane.add(code, null);
			value = new JTextField();
			value.setFont(new Font("Arial", Font.BOLD, 12));
			value.setBounds(xinc + 225, yinc, 300, 20);
			value.addActionListener(this);
			value.addKeyListener(this);
			value.setName("value");
			jContentPane.add(value, null);
			yinc = yinc + 30;
			stockCode = Utility.getStockCode();
			list = new JList(stockCode);
			list.setName("list");
			list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			list.setSelectedIndex(0);
			list.addListSelectionListener(this);
			list.addMouseListener(this);
			JScrollPane listScrollPane = new JScrollPane(list);
			listScrollPane.setBounds(xinc, yinc, 150, 400);
			jContentPane.add(listScrollPane);
			stockName = Utility.getStockName();
			list1 = new JList(stockName);
			list1.setName("list1");
			list1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			list1.setSelectedIndex(0);
			list1.addListSelectionListener(this);
			list1.addMouseListener(this);
			JScrollPane pictureScrollPane = new JScrollPane(list1);
			pictureScrollPane.setBounds(xinc + 225, yinc, 300, 400);
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
			return jContentPane;
		}
		//Listens to the list
		boolean textEventChk = false;
		public void updateList(String name,int i,String s)
		{
			if(name.equalsIgnoreCase("list1"))
			{
				list1.setSelectedIndex(i);
				list1.ensureIndexIsVisible(i);

				String name1 = (String)Utility.symBolHash1.get(s);
				if(name1 == null)
				{
					return;
				}
				int j = Utility.stockVector.indexOf(name1);
				list.setSelectedIndex(j);
				list.ensureIndexIsVisible(j);
//				code.setText((String) list.getSelectedValue());
//				value.setText((String) list1.getSelectedValue());
				
			}
			else
			{
				list.setSelectedIndex(i);
				list.ensureIndexIsVisible(i);

				String name1 = (String)Utility.symBolHash.get(s);
				if(name1 == null)
				{
					return;
				}
				int j = Utility.stockVector1.indexOf(name1);
				list1.setSelectedIndex(j);
				list1.ensureIndexIsVisible(j);
//				code.setText((String) list.getSelectedValue());
//				value.setText((String) list1.getSelectedValue());
								
			}
			
		}
		public void valueChanged(ListSelectionEvent e)
		{
			if (textEventChk)
			{
				textEventChk = false;
				return;
			}
			JList mylist = (JList) e.getSource();
			int i = mylist.getSelectedIndex();
			String s = (String)mylist.getSelectedValue();
			
			updateList(mylist.getName(), i, s);
		}
		public JPanel getSplitPane()
		{
			return jContentPane;
		}
		public static void main(String[] args)
		{
			SearchStock searc = new SearchStock(null);
			
		}
		/* (non-Javadoc)
		 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
		 */
		public void keyTyped(KeyEvent arg0)
		{
			// TODO Auto-generated method stub
		}
		/* (non-Javadoc)
		 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
		 */
		public void keyPressed(KeyEvent arg0)
		{
			// TODO Auto-generated method stub
		}
		/* (non-Javadoc)
		 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
		 */
		public void keyReleased(KeyEvent arg0)
		{
			Object obj = arg0.getSource();
			if (obj instanceof JTextField)
			{
				String name = ((JTextField) obj).getName();
				if (name.equals("code"))
				{
					String s = code.getText().trim();
					int i = findFirstStockCodeStartingWith(s);
					if (i != -1)
					{
						list.setSelectedIndex(i);
						s =(String) list.getSelectedValue();

						textEventChk = true;
						updateList("list", i, s);

						textEventChk = true;
						
					}
				}
				else if (name.equals("value"))
				{
					String s = value.getText().trim();
					int i = findFirstStockNameStartingWith(s);
					if (i != -1)
					{
						list1.setSelectedIndex(i);
						s =(String) list1.getSelectedValue();

						textEventChk = true;
						updateList("list1", i, s);

						textEventChk = true;
					}
				}
				else
				{
				}
			}
			// TODO Auto-generated method stub
		}
		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent arg0)
		{
			Object obj = null;
			obj = arg0.getSource();
			if(obj instanceof JButton)
			{
				String name = ((JButton)obj).getName();
				if(name.equals("ok"))
				{
					
					String stockname = (String)list.getSelectedValue();
					if(isAvailable(stockname)) 
					{
						panelforgrapha.searchStockUpdate(stockname.toUpperCase());
						dispose();
					}
				}
				else if(name.equals("cancel"))
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
		private boolean isAvailable(String stockname)
		{
			stockname = stockname.toUpperCase();
			if (stockname.trim().length() == 0)
				return false;
			for (int i = 0; i < stockCode.size(); i++)
			{
				String str = (String) stockCode.elementAt(i);
				if (str.toUpperCase().equals(stockname))
					return true;
			}
			return false;
		}
		public int findFirstStockCodeStartingWith(String startStr)
		{
			startStr = startStr.toUpperCase();
			if (startStr.trim().length() == 0)
				return -1;
			for (int i = 0; i < stockCode.size(); i++)
			{
				String str = (String) stockCode.elementAt(i);
				if (str.toUpperCase().startsWith(startStr))
					return i;
			}
			return -1;
		}
		public int findFirstStockNameStartingWith(String startStr)
		{
			startStr = startStr.toUpperCase();
			if (startStr.trim().length() == 0)
				return -1;
			for (int i = 0; i < stockName.size(); i++)
			{
				String str = (String) stockName.elementAt(i);
				if (str.toUpperCase().startsWith(startStr))
					return i;
			}
			return -1;
		}
		/* (non-Javadoc)
		 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
		 */
		public void mouseClicked(MouseEvent arg0)
		{
			Object obj = arg0.getSource();
			if (obj instanceof JList)
			{
				JList mylist = (JList) arg0.getSource();
				int i = mylist.getSelectedIndex();
				if(mylist.getName().equalsIgnoreCase("list"))
				{
					list.setSelectedIndex(i);
					String s = (String)list.getSelectedValue();
					updateList("list", i, s);
				}
				else
				{
					list1.setSelectedIndex(i);
					String s = (String)list1.getSelectedValue();
					updateList("list1", i, s);				
				}
				code.setText((String) list.getSelectedValue());
				value.setText((String) list1.getSelectedValue());

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

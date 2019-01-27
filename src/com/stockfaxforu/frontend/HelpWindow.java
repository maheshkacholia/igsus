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

import com.stockfaxforu.util.CreateHelp;
import com.stockfaxforu.util.Utility;
import java.util.*;
//SplitPaneDemo itself is not a visible component.
public class HelpWindow extends JFrame implements ListSelectionListener, KeyListener, ActionListener, MouseListener
{
	private JPanel panel;
	private JList list;
	private JList list1;
	private JTextField code;
	private JTextField value;
	private javax.swing.JPanel jContentPane;
	Vector stockCode;
	Vector stockName;
	AdPanel helpPanel=null;

	public HelpWindow(JPanel panel)
	{
		//		super(null);
		setBackground(Color.lightGray);
		this.panel = panel;
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setContentPane(getJContentPane());
		this.setTitle("IGS Formula API Help Window");
		this.move(100, 100);
		Image im = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("image/toolicon.jpg"));
		this.setIconImage(im);

		this.setSize(800, 600);
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
			JLabel lable = new JLabel("Function List");
			lable.setFont(new Font("Arial", Font.BOLD, 12));
			lable.setForeground(Color.black);
			lable.setBounds(xinc, yinc, 100, 15);
			jContentPane.add(lable, null);
			
			yinc = yinc + 20;
			code = new JTextField();
			code.setFont(new Font("Arial", Font.BOLD, 12));
			code.setBounds(xinc, yinc, 150, 20);
			code.addActionListener(this);
			code.addKeyListener(this);
			code.setName("code");
			jContentPane.add(code, null);
			
			yinc = yinc + 30;
			stockCode =CreateHelp.getKeyList();
			list = new JList(stockCode);
			list.setName("list");
			list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			list.setSelectedIndex(0);
			list.addListSelectionListener(this);
			list.addMouseListener(this);
			JScrollPane listScrollPane = new JScrollPane(list);
			listScrollPane.setBounds(xinc, yinc, 150, 400);
			jContentPane.add(listScrollPane);
			
			
			try
			{
				helpPanel = new AdPanel(new StringBuffer());
			}
			catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			JScrollPane pictureScrollPane = new JScrollPane(helpPanel);
			pictureScrollPane.setBounds(xinc + 225, yinc, 500, 400);
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
				list.setSelectedIndex(i);
				list.ensureIndexIsVisible(i);
				String helpStr = CreateHelp.getKeyValue(s);
				helpPanel.setText(helpStr);
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
			HelpWindow searc = new HelpWindow(null);
			
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
			}
			// TODO Auto-generated method stub
		}
		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent arg0)
		{
			Object obj = (JButton)arg0.getSource();
			if(obj instanceof JButton)
			{
				String name = ((JButton)obj).getName();
				if(name.equals("ok"))
				{
					if(this.panel==null)
					{
						this.dispose();
						return;
					}

					
					String stockname = (String)list.getSelectedValue();
					helpPanel.setText(CreateHelp.getKeyValue(stockname));
					String s = (String)CreateHelp.syntaxHash.get(stockname);
					
					
					s = ((FormulaEditor)panel).buyformula.getText() + " " + s;
					((FormulaEditor)panel).buyformula.setText(s);
					this.dispose();
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
				code.setText((String) list.getSelectedValue());
		
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

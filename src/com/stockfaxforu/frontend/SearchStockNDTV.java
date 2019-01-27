/*
 * Created on Feb 24, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.frontend;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;

import javax.swing.*;
import javax.swing.event.*;

import com.stockfaxforu.util.NDTVMatching;
import com.stockfaxforu.util.Utility;
import java.util.*;
//SplitPaneDemo itself is not a visible component.
public class SearchStockNDTV extends JFrame implements ListSelectionListener, KeyListener, ActionListener, MouseListener
{
	private JPanel panelforgrapha;
	private JList listNDTV;
	private JList listNSE;
	private JList listFinal;

	private JTextField code;
	private JTextField value;
	private javax.swing.JPanel jContentPane;
	Vector stockCodeNDTV;
	Vector stockNameNSECODE;
	Vector finalList = new Vector();
	
	public SearchStockNDTV(JPanel panelforgrapha)
	{
		//		super(null);
		setBackground(Color.lightGray);
		this.panelforgrapha = panelforgrapha;
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setContentPane(getJContentPane());
		this.setTitle("Remove Indicator");
		this.move(300, 300);
		this.setSize(1300, 800);
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
			JLabel lable = new JLabel("NDTV Code");
			lable.setFont(new Font("Arial", Font.BOLD, 12));
			lable.setForeground(Color.black);
			lable.setBounds(xinc, yinc, 100, 15);
			jContentPane.add(lable, null);
			JLabel lable1 = new JLabel("NSE Code");
			lable1.setFont(new Font("Arial", Font.BOLD, 12));
			lable1.setForeground(Color.black);
			lable1.setBounds(xinc + 225, yinc, 100, 15);
			jContentPane.add(lable1, null);

			JLabel lable2 = new JLabel("NDTV,NSE Name,NSE Code");
			lable2.setFont(new Font("Arial", Font.BOLD, 12));
			lable2.setForeground(Color.black);
			lable2.setBounds(xinc + 550, yinc, 300, 15);
			jContentPane.add(lable2, null);

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
			
			stockCodeNDTV = NDTVMatching.getNDTVNames();
			
			listNDTV = new JList(stockCodeNDTV);
			listNDTV.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			listNDTV.setSelectedIndex(0);
			listNDTV.addListSelectionListener(this);
			listNDTV.addMouseListener(this);
			JScrollPane listScrollPane = new JScrollPane(listNDTV);
			listScrollPane.setBounds(xinc, yinc, 150+100, 400);
			jContentPane.add(listScrollPane);
			
			stockNameNSECODE = NDTVMatching.getNSENames();
			
			listNSE = new JList(stockNameNSECODE);
			listNSE.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			listNSE.setSelectedIndex(0);
			listNSE.addListSelectionListener(this);
			listNSE.addMouseListener(this);
			JScrollPane pictureScrollPane = new JScrollPane(listNSE);
			pictureScrollPane.setBounds(xinc + 225+100, yinc, 300, 400);
			jContentPane.add(pictureScrollPane);
			
			listFinal = new JList();
			listFinal.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//			listFinal.setSelectedIndex(0);
			listFinal.addListSelectionListener(this);
			listFinal.addMouseListener(this);
			JScrollPane pictureScrollPane1 = new JScrollPane(listFinal);
			pictureScrollPane1.setBounds(xinc + 550+100, yinc, 500, 400);
			jContentPane.add(pictureScrollPane1);

			
			
			yinc = yinc + 420;
			JButton ok = new JButton(">>");
			ok.setFont(new Font("Arial", Font.BOLD, 12));
			ok.setBounds(xinc+160+100, yinc -300, 50, 20);
			ok.addActionListener(this);
			ok.setName("ok");
			jContentPane.add(ok);

//			yinc = yinc + 420;
			JButton remove = new JButton("<<");
			remove.setFont(new Font("Arial", Font.BOLD, 12));
			remove.setBounds(xinc+160+100, yinc -250, 50, 20);
			remove.addActionListener(this);
			remove.setName("remove");
			jContentPane.add(remove);


			JButton cancel = new JButton("Submit");
			cancel.setFont(new Font("Arial", Font.BOLD, 12));
			cancel.setBounds(xinc + 225, yinc, 100, 20);
			cancel.addActionListener(this);
			cancel.setName("cancel");
			jContentPane.add(cancel);
			return jContentPane;
		}
		//Listens to the list
		boolean textEventChk = false;
		public void valueChanged(ListSelectionEvent e)
		{
/*			if (textEventChk)
			{
				textEventChk = false;
				return;
			}
			JList mylist = (JList) e.getSource();
			int i = mylist.getSelectedIndex();
			list1.setSelectedIndex(i);
			list.setSelectedIndex(i);
			list1.ensureIndexIsVisible(i);
			list.ensureIndexIsVisible(i);
			code.setText((String) list.getSelectedValue());
			value.setText((String) list1.getSelectedValue());
*/
				}
		public JPanel getSplitPane()
		{
			return jContentPane;
		}
		public static void main(String[] args)
		{
			SearchStockNDTV d = new SearchStockNDTV(null);

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
/*			Object obj = arg0.getSource();
			if (obj instanceof JTextField)
			{
				String name = ((JTextField) obj).getName();
				if (name.equals("code"))
				{
					String s = code.getText().trim();
					int i = findFirstStockCodeStartingWith(s);
					if (i != -1)
					{
						textEventChk = true;
						list1.setSelectedIndex(i);
						textEventChk = true;
						list.setSelectedIndex(i);
						list1.ensureIndexIsVisible(i);
						list.ensureIndexIsVisible(i);
					}
				}
				else if (name.equals("value"))
				{
					String s = value.getText().trim();
					int i = findFirstStockNameStartingWith(s);
					if (i != -1)
					{
						textEventChk = true;
						list1.setSelectedIndex(i);
						textEventChk = true;
						list.setSelectedIndex(i);
						list1.ensureIndexIsVisible(i);
						list.ensureIndexIsVisible(i);
					}
				}
				else
				{
				}
			}
			// TODO Auto-generated method stub
*/
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
					String ndtv = (String)listNDTV.getSelectedValue();

					String nse = (String)listNSE.getSelectedValue();

					String listfinal = ndtv + "," + nse  ;
					finalList.addElement(listfinal);
					listFinal.setListData(finalList);					 


					stockCodeNDTV.removeElement(ndtv);
					listNDTV.setListData(stockCodeNDTV);					 

					stockNameNSECODE.removeElement(nse);
					listNSE.setListData(stockNameNSECODE);					 

				}
				else if(name.equals("remove"))
				{
					String finalstr = (String)listFinal.getSelectedValue();
					String ndtv = finalstr.substring(0,finalstr.indexOf(","));

					String nse = finalstr.substring(finalstr.indexOf(",")+1);


					finalList.removeElement(finalstr);
					listFinal.setListData(finalList);					 


					stockCodeNDTV.addElement(ndtv);
					listNDTV.setListData(stockCodeNDTV);					 

					stockNameNSECODE.addElement(nse);
					listNSE.setListData(stockNameNSECODE);					 

				}

				else if(name.equals("cancel"))
				{
					RandomAccessFile r =null;
					try
					{
						File f = new File("/igs1/ndtvnsecodelist.csv");
						f.delete();
						r = new RandomAccessFile("/igs1/ndtvnsecodelist.csv","rw");
						StringBuffer sb = new StringBuffer("NDTV code    ,NSEName   ,NSE code    \n");
						for(int i=0;i<finalList.size();i++)
						{
							sb.append(finalList.elementAt(i)+"\n");
						}
						r.writeBytes(sb.toString());
						r.close();

						f = new File("/igs1/NDTVoutput.csv");
						f.delete();
						r = new RandomAccessFile("/igs1/NDTVoutput.csv","rw");
						sb = new StringBuffer("Name,Index,Current Price (Rs),Change (Rs),Change (%),Volume,Mkt Cap (Rs),Year High,Year Low,Day High,Day Low    \n");
						for(int i=0;i<stockCodeNDTV.size();i++)
						{
							sb.append(stockCodeNDTV.elementAt(i)+"\n");
						}
						r.writeBytes(sb.toString());
						r.close();

						f = new File("/igs1/EQUITY_L.csv");
						f.delete();
						r = new RandomAccessFile("/igs1/EQUITY_L.csv","rw");
						sb = new StringBuffer("SYMBOL,NAME OF COMPANY, SERIES, DATE OF LISTING, PAID UP VALUE, MARKET LOT, ISIN NUMBER, FACE VALUE    \n");
						for(int i=0;i<stockNameNSECODE.size();i++)
						{
							sb.append(stockNameNSECODE.elementAt(i)+"\n");
						}
						r.writeBytes(sb.toString());
						r.close();


						
					}
					catch (Exception e)
					{
						// TODO Auto-generated catch block
//						e.printStackTrace();
					}
					
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
			for (int i = 0; i < stockCodeNDTV.size(); i++)
			{
				String str = (String) stockCodeNDTV.elementAt(i);
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
			for (int i = 0; i < stockCodeNDTV.size(); i++)
			{
				String str = (String) stockCodeNDTV.elementAt(i);
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
			for (int i = 0; i < stockNameNSECODE.size(); i++)
			{
				String str = (String) stockCodeNDTV.elementAt(i);
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
/*			Object obj = arg0.getSource();
			if (obj instanceof JList)
			{
				JList mylist = (JList) arg0.getSource();
				int i = mylist.getSelectedIndex();
				listNSE.setSelectedIndex(i);
				listNDTV.setSelectedIndex(i);
				listNSE.ensureIndexIsVisible(i);
				listNDTV.ensureIndexIsVisible(i);
				code.setText((String) listNDTV.getSelectedValue());
				value.setText((String) listNSE.getSelectedValue());
			}
			// TODO Auto-generated method stub
*/
		
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

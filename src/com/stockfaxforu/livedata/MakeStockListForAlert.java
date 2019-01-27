/*
 * Created on Feb 19, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.livedata;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Label;
import java.awt.List;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.stockfaxforu.component.*;
import com.stockfaxforu.component.Indicator;
import com.stockfaxforu.formulabuilder.MessageDiaglog;
import com.stockfaxforu.frontend.ComboBoxRendererSymbol;
import com.stockfaxforu.frontend.IndexComboBox;
import com.stockfaxforu.frontend.MainScreen;
import com.stockfaxforu.strategy.StrategyUtility;
import com.stockfaxforu.strategy.StrategyUtilityStockScreener;
import com.stockfaxforu.swingcomponent.IGSLabel;
import com.stockfaxforu.util.IndexUtility;
import com.stockfaxforu.util.ManageIndicator;
import com.stockfaxforu.util.StockConstants;
import com.stockfaxforu.util.Utility;
/**
 * @author sdeopura
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class MakeStockListForAlert extends JFrame implements ListSelectionListener, KeyListener, ActionListener, MouseListener
{
	public JTextField stockcodeText;
	public Vector stockCode=null;
	/**
	 * This is the default constructor
	 */
	JTextField number = null;
	JTextField daybefore = null;
	JButton add = null;
	JButton cancel = null;
	String indicatorName = null;
	Vector paras = new Vector();
	JComboBox operatorList = null;
	int index = 0;
	JComboBox type = null;
	String operationname = "";
	JLabel msg = null;
	String symbol;
	JComboBox categoryCombo;
	Vector categoryVector;
	JList list1,list;
	Vector selectedStock=null;
	JComboBox indexes=null;
	JButton addstock=null;
	public MakeStockListForAlert()
	{
		//		super(null);
		setBackground(Color.lightGray);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Image im = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("image/toolicon.jpg"));
		this.setIconImage(im);

		this.setContentPane(getJContentPane());
		this.setTitle("Select Stock For Alert");
		this.move(300, 100);
		this.setSize(550, 525);
		this.setVisible(true);
	}
	private javax.swing.JPanel jContentPane = null;
	private javax.swing.JPanel getJContentPane()
	{
		int xinc = 50;
		int yinc = 10;
		int xincdis = 120;
		if (jContentPane == null)
		{
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
		}

		IGSLabel lable1 = new IGSLabel("Select Stock:");
		lable1.setBounds(xinc, yinc, 100, 15);
		jContentPane.add(lable1, null);

		xinc = xinc + 300;

		IGSLabel lable2 = new IGSLabel("Selected Stock:");
		lable2.setBounds(xinc, yinc, 100, 15);
		jContentPane.add(lable2, null);
		
		yinc = yinc + 20;
		xinc = 50;	
		

		stockCode = Utility.getAlertStocks();
		list = new JList(stockCode);
		list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		list.addListSelectionListener(this);
		JScrollPane listScrollPane = new JScrollPane(list);
		listScrollPane.setBounds(xinc, yinc, 150, 300);
		jContentPane.add(listScrollPane);
		
		selectedStock = new Vector();
		list1 = new JList();
		list1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list1.setSelectedIndex(0);
		list1.addListSelectionListener(this);
		JScrollPane pictureScrollPane = new JScrollPane(list1);
		pictureScrollPane.setBounds(xinc + 300, yinc, 150, 300);
		jContentPane.add(pictureScrollPane);
		yinc = yinc + 320;
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


		xinc = 225;
		yinc = 200;

		JButton select = new JButton(">>");
		select.setFont(new Font("Arial", Font.BOLD, 12));
		select.setBounds(xinc, yinc, 100, 20);
		select.addActionListener(this);
		select.setName("select");
		jContentPane.add(select);
		
		yinc = yinc + 100;
		JButton deselect = new JButton("<<");
		deselect.setFont(new Font("Arial", Font.BOLD, 12));
		deselect.setBounds(xinc , yinc, 100, 20);
		deselect.addActionListener(this);
		deselect.setName("deselect");
		jContentPane.add(deselect);
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
	String inexName ="";

	public void actionPerformed(ActionEvent e)
	{
		Object o = (Object) e.getSource();
		if( o instanceof JComboBox)
		{
			JComboBox cb = (JComboBox) e.getSource();
			String name = cb.getName();
			inexName = (String)cb.getSelectedItem();
			selectedStock = IndexUtility.getIndexStockVector(inexName);
			if(selectedStock==null)
				selectedStock = new Vector();
			list1.setListData(selectedStock);
//			list1.
		}
		else if (o instanceof JButton)
		{
			JButton cb = (JButton) e.getSource();
			String name = cb.getName();
			if (name.equalsIgnoreCase("select"))
			{
				Object[] value = (Object [])list.getSelectedValues();
				if(value==null)
					return;
				for(int i=0;i<value.length;i++)
				{
					if(!selectedStock.contains(value[i]))
						selectedStock.addElement(value[i]);
				}
				list1.setListData(selectedStock);						
				
			} 
			else if (name.equalsIgnoreCase("addstock"))
			{
				String value = stockcodeText.getText().trim();
				if(value==null || value.equalsIgnoreCase(""))
					return;
				value = value.toUpperCase();
				if(!selectedStock.contains(value))
					selectedStock.addElement(value);
				list1.setListData(selectedStock);						
				
			}
			else if (name.equalsIgnoreCase("deselect"))
			{
				Object[] value = (Object [])list1.getSelectedValues();
				if(value==null)
					return;
				for(int i=0;i<value.length;i++)
				{
					selectedStock.removeElement(value[i]);
				}
				list1.setListData(selectedStock);						
			} 
			else if (name.equalsIgnoreCase("ok"))
			{
				if(inexName==null || inexName.equalsIgnoreCase(""))
				{
					MessageDiaglog msg = new MessageDiaglog("Pls select category first, which you want to update");
					return;
				}
				saveIndex();
				MainScreen.getSingleton().addTreeAdPanel();
				MainScreen.getSingleton().doReSize();
			} 

			else if (name.equalsIgnoreCase("cancel"))
			{
				this.dispose();
			}
		}
	}
	/**
	 * @param s
	 */
	private void addCategory(String s)
	{
		// TODO Auto-generated method stub
		
	}
	/**
	 * @param s
	 * @return
	 */
	private boolean isCategoryNotExist(String s)
	{
		// TODO Auto-generated method stub
		return false;
	}
	/**
	 * @return
	 */
	public static void main(String[] args)
	{
		MakeStockListForAlert addremove = new MakeStockListForAlert();
	}
	/* (non-Javadoc)
	 * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
	 */
	public void saveIndex()
	{
		String indexStr = (String)indexes.getSelectedItem();
		IndexUtility.addStocksToCategory(selectedStock, indexStr);
	}	
	
	public void saveIndexOld()
	{
		String indexStr = (String)indexes.getSelectedItem();

		RandomAccessFile rfile = null;
		StringBuffer sb = new StringBuffer();
		StringBuffer stocks = new StringBuffer();
		for(int i=0;i<selectedStock.size();i++)
			stocks.append(selectedStock.elementAt(i)+"|");
			
		try
		{
			rfile = new RandomAccessFile(StockConstants.INSTALL_DIR+"/indexsymbol.txt","r");
			String line="";
			while((line = rfile.readLine()) != null)
			{
				StringTokenizer st = new StringTokenizer(line,"=");
				String index = st.nextToken();
				if(index.equals(indexStr))
				{
					sb.append(indexStr+"="+stocks.toString()+"\n");
					
				}
				else
				{
					sb.append(line+"\n");
				}
			}
			String s = sb.toString().substring(0,sb.length()-1);
			File f = new File(StockConstants.INSTALL_DIR+"/indexsymbol.txt");
			f.delete();
			rfile = new RandomAccessFile(StockConstants.INSTALL_DIR+"/indexsymbol.txt","rw");
			rfile.writeBytes(s);
			rfile.close();
			IndexUtility.loadIndexes();
			MainScreen.getSingleton().treepanel.treeModel.reload();
			MainScreen.getSingleton().treepanel.repaint();
			
			this.dispose();
		}
		catch(Exception e)
		{
		}
	}
	boolean textEventChk = false;

	public void valueChanged(ListSelectionEvent e)
	{
		if (textEventChk)
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
//		stockcodeText.setText((String) list.getSelectedValue());
	}

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
		for (int i = 0; i < stockCode.size(); i++)
		{
			String str = (String) stockCode.elementAt(i);
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
			list.setSelectedIndex(i);
			list.ensureIndexIsVisible(i);
			stockcodeText.setText((String) list.getSelectedValue());
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
			if (name.equals("stockcodeText"))
			{
				String s = stockcodeText.getText().trim();
				int i = findFirstStockCodeStartingWith(s);
				if (i != -1)
				{
					textEventChk = true;
					textEventChk = true;
					list.setSelectedIndex(i);
					list.ensureIndexIsVisible(i);
				}
			}
		}	
	}

	
} //  @jve:visual-info  decl-index=0 visual-constraint="4,11"

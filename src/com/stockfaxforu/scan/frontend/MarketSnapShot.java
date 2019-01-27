/*
 * Created on Feb 24, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.scan.frontend;
import java.awt.*;
import java.awt.event.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import com.stockfaxforu.formulabuilder.MessageDiaglog;
import com.stockfaxforu.frontend.PanelForGraph;
import com.stockfaxforu.frontend.PanelForGraphFrame;
import com.stockfaxforu.query.DataBaseInMemory;
import com.stockfaxforu.scan.util.ScanUtility;
import com.stockfaxforu.strategy.StrategyUtility;
import com.stockfaxforu.util.IndexUtility;
import com.stockfaxforu.util.StockConstants;
import com.stockfaxforu.util.Utility;

import java.util.*;
//SplitPaneDemo itself is not a visible component.
public class MarketSnapShot extends JPanel implements ListSelectionListener, KeyListener, ActionListener, MouseListener
{
	private JPanel panelforgrapha;
	private JList list;
	private JList list1;
	private JTextField code;
	private JTextField value;
	private javax.swing.JPanel jContentPane;
	Vector stockCode;
	Vector stockName;
	int xmax;
	int ymax;
	JTable table = null;
	String[][] dataValues = null;
	String[] columnNames={"SYMBOL","OPEN","HIGH","LOW","CLOSE","LAST","PREVCLOSE","TOTTRDQTY"};
	JScrollPane scrollPane = null;
	JCheckBox[] checkboxes = null;
	JButton submit,load;
	GridPanelForScan ind,ind1,volind,mktind;
	public static int LIVE=1;
	public static int HIST=2;
	public MarketSnapShot(int x1, int y1,int type)
	{
		super(null);
		this.xmax = x1;
		this.ymax = y1;
		
		this.type = type;
		String mys = "";
		if(type==HIST)
		{
			mys = "    What Happened Yesterday";
		}
		else
		{
			mys = "    What Is Happening In Market";
			
		}
		JLabel typelable = new JLabel(mys);
		typelable.setBounds(0,0,200,20);
		add(typelable);
		
		setBackground(Color.lightGray);
		this.panelforgrapha = panelforgrapha;
		//		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		//		this.setContentPane(getJContentPane());
		//		this.setTitle("Remove Indicator");
		//		this.move(100, 100);
		this.setSize(x1, y1);

		int width = this.xmax / 5;
		int xpos = 0;
		int ylen = 75;
	
		HashMap hs = new HashMap();
		
		try
		{
			
			FileInputStream f = new FileInputStream(StockConstants.INSTALL_DIR + "/scanparameter.obj");
			ObjectInputStream o = new ObjectInputStream(f);
			hs = (HashMap)o.readObject();
			
		}
		catch(Exception e)
		{
			
		}

		ind = new GridPanelForScan(width, ylen, 0, 2, "% Change", "ind",hs,"% Change");
		add(ind, null);
		ind.setBounds(0,20, width, ylen);

		
		/*	JButton[] addedbutton = ind.getAddedButton();
		for (int i = 0; i < addedbutton.length; i++)
		{
			addedbutton[i].addActionListener(this);
		}
*/
		xpos = xpos + width;
		ind1 = new GridPanelForScan(width, ylen, 0, 2, "Price ","ind1",hs,"Price ");
		ind1.setBounds(xpos + 1, 20, width, ylen);

		add(ind1,null);
/*		JButton[] addedbutton1 = ind1.getAddedButton();
		for (int i = 0; i < addedbutton1.length; i++)
		{
			addedbutton1[i].addActionListener(this);
		}
*/
		xpos = xpos + width;
		volind = new GridPanelForScan(width, ylen, 0, 2, "Volume ","volind",hs,"Volume");
		volind.setBounds(xpos + 1, 20, width, ylen);

		add(volind,null);
		if(type==LIVE)
		{
			xpos = xpos + width;
			mktind = new GridPanelForScan(width, ylen, 0, 2, "Market Captilization ", "mktind",hs,"Mkt Cap.");
			mktind.setBounds(xpos + 1, 20, width, ylen);

			add(mktind,null);
			
		}
		if(type==HIST)
		{
			xpos = xpos + width;
			Vector v = IndexUtility.getFilters();
			CheckBoxGridPanelScan checkbox = new CheckBoxGridPanelScan(width+50, ylen, 0, 3, "Filter", v);
			checkbox.setBounds(xpos + 1,20, width+50, ylen);

			add(checkbox);
			checkboxes = checkbox.getAddedCheckBox();
		
		}
	
		
		xpos = xpos + width+50;
		
/*		load = new JButton("Load");
		load.setName("load");
		load.setBounds(xpos+1, 20,100, 15);
		add(load);
		load.addActionListener(this);
*/		
		submit = new JButton("Submit");
		submit.setName("submit");
		submit.setBounds(xpos+1, 20,100, 20);
		add(submit);
		submit.addActionListener(this);
		String[] sortType = {"Sort Ascending","Sort Desending"};
		sorttype = new JComboBox(sortType);
		sorttype.setName("sorttype");
		sorttype.setToolTipText("Select Sorting Type , when you click on table header");
		sorttype.setBounds(xpos+1, 45,100, 20);
		add(sorttype);
	//	submit.addActionListener(this);
		
		
		if(scrollPane != null && table != null)
		{
			scrollPane.remove(table);
			this.remove(scrollPane);
		}
		dataValues = new String[1][columnNames.length];
		table = new JTable( dataValues, columnNames );
		table.addMouseListener(this);
		tableheader = table.getTableHeader();
		tableheader.addMouseListener(this);	
		tableheader.setToolTipText("Click to sort");
		// Add the table to a scrolling pane
		scrollPane = JTable.createScrollPaneForTable( table );
		scrollPane.setBounds(0,101, xmax, ymax-101);
					
		this.add( scrollPane, null );
		
	}
	//Listens to the list
	boolean textEventChk = false;
	JComboBox sorttype =null;
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
		code.setText((String) list.getSelectedValue());
		value.setText((String) list1.getSelectedValue());
	}
	public JPanel getSplitPane()
	{
		return jContentPane;
	}
	public static void main(String[] args)
	{
		MarketSnapShot s = new MarketSnapShot(1000, 800,1);
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
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	String query;
	public void actionPerformed(ActionEvent arg0)
	{
		try
		{
			Object obj = (JButton) arg0.getSource();
			if (obj instanceof JButton)
			{
				String name = ((JButton) obj).getName();
				if (name.equals("Graph"))
				{
					int i = table.getSelectedRow();
					String symbol = dataValues[i][0];
					PanelForGraphFrame pframe = new PanelForGraphFrame(symbol);
				}
				else if (name.equals("Tech Ind"))
				{
					int i = table.getSelectedRow();
					String symbol = dataValues[i][0];
					IndiacatorValueScreen pframe = new IndiacatorValueScreen(symbol);
				}
				else if (name.equals("submit"))
				{
					String minperchange = ind.minchange.getText().trim();
					String maxperchange = ind.maxchange.getText().trim();
					
					
					String minprice = ind1.minchange.getText().trim();
					String maxprice = ind1.maxchange.getText().trim();
					
					String minvolume = volind.minchange.getText().trim();
					String maxvolume = volind.maxchange.getText().trim();
					
					String mincap="";
					String maxcap="";
					if(this.type==MarketSnapShot.LIVE)
					{
						mincap = mktind.minchange.getText().trim();
						maxcap = mktind.maxchange.getText().trim();
						
					}
						
					
					
					StringBuffer sb = new StringBuffer();
					
					try
					{
						
						float f; 
						sb.append("TRUE ");
						if(!minperchange.equals(""))
						{
							f = Float.parseFloat(minperchange);
							sb.append(" AND PERCHANGE > " + minperchange);	
						}
						if(!maxperchange.equals(""))
						{
							f = Float.parseFloat(maxperchange);
							sb.append(" AND PERCHANGE < " + maxperchange);	
							
						}
						if(!minprice.equals(""))
						{
							f = Float.parseFloat(minprice);
							sb.append(" AND CURRPRICE > " + minprice);	
							
						}
						if(!maxprice.equals(""))
						{
							f = Float.parseFloat(maxprice);
							sb.append(" AND CURRPRICE < " + maxprice);	
	
						}
						if(!minvolume.equals(""))
						{
							f = Float.parseFloat(minvolume);
							sb.append(" AND VOLUME > " + minvolume);	
											
						}
						if(!maxvolume.equals(""))
						{
							f = Float.parseFloat(maxvolume);
							sb.append(" AND VOLUME < " + maxvolume);	
							
						}
						if(!mincap.equals(""))
						{
							f = Float.parseFloat(mincap);
							sb.append(" AND MKTCAP > " + mincap);	
								
						}
						if(!maxcap.equals(""))
						{
							f = Float.parseFloat(maxcap);
							sb.append(" AND MKTCAP < " + maxcap);	
									
						}
						
					}
					catch(Exception e)
					{
						MessageDiaglog m = new MessageDiaglog("Please Enter a Numeric Value");
						return;
					}
					
					try
					{
						HashMap hs = new HashMap();
						hs.put("ind.minchange", minperchange);
						hs.put("ind.maxchange", maxperchange);
						
						hs.put("ind1.minchange", minprice);
						hs.put("ind1.maxchange", maxprice);
						
						hs.put("volind.minchange", minvolume);
						hs.put("volind.maxchange", maxvolume);
						
						hs.put("mktind.minchange", mincap);
						hs.put("mktind.maxchange", maxcap);
						
						FileOutputStream f = new FileOutputStream(StockConstants.INSTALL_DIR + "/scanparameter.obj");
						ObjectOutputStream o = new ObjectOutputStream(f);
						o.writeObject(hs);
						
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
						
					
					
				
//					String query = "SELECT SYMBOL,CURRPRICE,PERCHANGE,VOLUME,MKTCAP FROM TABLE WHERE PERCHANGE > 5 SORTBY PERCHANGE ";
					if(this.type==MarketSnapShot.LIVE)
					{
						query = "SELECT SYMBOL,CURRPRICE,PERCHANGE,VOLUME,DAYHIGH,DAYLOW,MKTCAP,YEARLOW,YEARHIGH FROM TABLE WHERE " + sb;
					
					}
					else
						
					{
					//"SYMBOL", "OPEN", "HIGH", "LOW", "CLOSE", "PREVCLOSE", "LAST", "TOTTRDQTY", "SORTCOLUMN" 
						String s = sb.toString();
						s = Utility.replaceString(s, "CURRPRICE","CLOSE");
						s = Utility.replaceString(s, "DAYHIGH","HIGH");
						s = Utility.replaceString(s, "DAYLOW","LOW");
						
						query = "SELECT SYMBOL,OPEN,HIGH,LOW,CLOSE,PREVCLOSE,PERCHANGE,VOLUME  FROM TABLE WHERE " + s;
											
					}
	
					doOther();
				}
				//				repaint();
			}
		}
		catch (Exception e)
		{
		}

		// TODO Auto-generated method stub
	}
	public int type;
	public void doOther()
	{
		
		HashMap hs = null; 
		if( this.type==LIVE)
			hs =ScanUtility.executeQueryNDTV(query, ScanUtility.livebhavcopylist);
		else
		{
			Vector indList = new Vector();

			for (int i = 0; i < checkboxes.length; i++)
			{
				if (checkboxes[i].isSelected())
				{
					indList.addElement(checkboxes[i].getName());
				}
			}

			
			hs =ScanUtility.executeQuery(query, indList);
			
		}
			
		output = ScanUtility.output;	
		dataValues = (String[][]) hs.get("result");
		columnNames = (String[]) hs.get("column");
		if (scrollPane != null && table != null)
		{
			scrollPane.remove(table);
			this.remove(scrollPane);
		}
		String[] cols = new String[columnNames.length];
		for (int i = 0; i < cols.length; i++)
		{
			cols[i] = columnNames[i];
		}
		table = new JTable(dataValues, cols);

		table.addMouseListener(this);
		tableheader = table.getTableHeader();
		tableheader.addMouseListener(this);	
		tableheader.setToolTipText("Click to sort");
		// Add the table to a scrolling pane
		scrollPane = JTable.createScrollPaneForTable( table );
		scrollPane.setBounds(0,101, xmax-20, ymax-130);
		this.add(scrollPane, null);

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
			String str = (String) stockCode.elementAt(i);
			if (str.toUpperCase().startsWith(startStr))
				return i;
		}
		return -1;
	}
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	public void mouseClicked(MouseEvent e)
	{
		Object obj = e.getSource();
		int row = table.getSelectedRow();
	//	// ln("row="+row);
		TableColumnModel colModel = table.getColumnModel();
		int columnModelIndex = colModel.getColumnIndexAtX(e.getX());
		int modelIndex = colModel.getColumn(columnModelIndex)
			.getModelIndex();
	//	// ln("columnModelIndex="+columnModelIndex);
	//	// ln("modelIndex="+modelIndex);
		if (modelIndex < 0)
			  return;

		if(obj instanceof JTableHeader)
		{
			String myquery = query;
			if(sorttype.getSelectedItem().toString().equalsIgnoreCase("Sort Ascending") || columnModelIndex == 0)
				query =  query + " SORTBY  "  + columnNames[columnModelIndex];
			else 
				query =  query + " SORTBY  "  + "-1 * " +  columnNames[columnModelIndex];
				
			doOther();
			query = myquery;
			
			
		}
		else if(obj instanceof JTable)
		{
			String s = dataValues[row][0];
			PanelForGraphFrame frame = new PanelForGraphFrame(s);

		}
	//	// ln("mouseclicked");

		
	
		// TODO Auto-generated method stub
	}
	public Vector output=null;

	public void sortData(int columnno)
	{
		String[] sortedTemp = new String[dataValues.length];
		for(int i= 0;i<dataValues.length;i++)
		{
//			output.get(i);
			sortedTemp[i] = dataValues[i][columnno] + ":"  + i;
		}
		Arrays.sort(sortedTemp);
		String[][] dataValues1 = new String[dataValues.length][columnno];
		
		for(int i=0;i<sortedTemp.length;i++)
		{
			StringTokenizer st = new StringTokenizer(sortedTemp[i],":");
			st.nextToken();
			int row = Integer.parseInt(st.nextToken());
			dataValues1[i] = dataValues[row];
		}
		dataValues = dataValues1;
		addTable();
		
	}
	public DefaultTableModel model;
	JTableHeader tableheader=null;

	public void addTable()
	{
		if(scrollPane != null && table != null)
			{
				scrollPane.remove(table);
				this.remove(scrollPane);

			}

			model = new DefaultTableModel(dataValues, columnNames);

			table = new JTable( model )
			{
				public Component prepareRenderer(TableCellRenderer renderer,int row, int col) 
				{
			  		Component comp = super.prepareRenderer(renderer, row, col);
			  		JComponent jcomp = (JComponent)comp;
			  		if (comp == jcomp) 
			  		{
						String symbolName = dataValues[row][0];
						String desc = Utility.getStockDes(symbolName);
						jcomp.setToolTipText(desc);
			  		}
			  		return comp;
				}
	  		};
			table.addMouseListener(this);
//			TableCellRenderer renderer = new CustomTableCellRendererPure(dataValues);
//			table.setDefaultRenderer(Object.class, renderer);
			tableheader = table.getTableHeader();
			tableheader.addMouseListener(this);
			tableheader.setToolTipText("Click to sort");
			// Add the table to a scrolling pane
			scrollPane = JTable.createScrollPaneForTable( table );
			scrollPane.setBounds(0, 101, this.xmax - 10, this.ymax - 100);
					
			this.add( scrollPane, null );

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
		Object obj = arg0.getSource();
		TableColumnModel colModel = table.getColumnModel();
		int columnModelIndex = colModel.getColumnIndexAtX(arg0.getX());
		
		int modelIndex = colModel.getColumn(columnModelIndex)
			.getModelIndex();
	//	// ln("columnModelIndex="+columnModelIndex);
	//	// ln("modelIndex="+modelIndex);
		if (modelIndex < 0)
			  return;

		if(obj instanceof JTableHeader)
		{
			this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		}
		else if(obj instanceof JTable)
		{
			this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

			int row = table.getSelectedRow();
			}
	//	// ln("mouseclicked");

		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	public void mouseExited(MouseEvent arg0)
	{
		Object obj = arg0.getSource();
		if(obj instanceof JTableHeader)
		{
			this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
		else if(obj instanceof JTable)
		{
			this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			
		}
	}
}

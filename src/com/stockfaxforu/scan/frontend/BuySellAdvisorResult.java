/*
 * Created on Feb 24, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.scan.frontend;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import com.stockfaxforu.component.MainGraphComponent;
import com.stockfaxforu.formulabuilder.AddStockToTreeNameFrame;
import com.stockfaxforu.formulabuilder.MessageDiaglog;
import com.stockfaxforu.frontend.DataDownloadScreen;
import com.stockfaxforu.frontend.HelpWindow;
import com.stockfaxforu.frontend.HelpWindowForAll;
import com.stockfaxforu.frontend.PanelForGraph;
import com.stockfaxforu.frontend.PanelForGraphFrame;
import com.stockfaxforu.frontend.ShowContentWindow;
import com.stockfaxforu.query.DataBaseInMemory;
import com.stockfaxforu.scan.util.ScanUtility;
import com.stockfaxforu.strategy.BuySellStrategy;
import com.stockfaxforu.strategy.StrategyUtility;
import com.stockfaxforu.util.IndexUtility;
import com.stockfaxforu.util.StockConstants;
import com.stockfaxforu.util.Utility;

import java.text.DecimalFormat;
import java.util.*;
//SplitPaneDemo itself is not a visible component.
public class BuySellAdvisorResult extends JPanel implements ListSelectionListener, KeyListener, ActionListener, MouseListener
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
	String[][] dataValuesColor = null;
	
	String[] columnNames={"SYMBOL","OPEN","HIGH","LOW","CLOSE","LAST","PREVCLOSE","TOTTRDQTY"};
	JScrollPane scrollPane = null;
	JCheckBox[] checkboxes = null;
	JButton submit,load;
	GridPanelForScan ind,ind1,volind,mktind;
	public static int LIVE=1;
	public static int HIST=2;
	JLabel[] commentslabel = new JLabel[3];
	int mypos=200;
	public static int GRAPH_DIFF=500;
	
	String[] gifname = {  "addtotree", "Apply", "csv","help"};
	String[] gifname1 = { "addtotree.jpg", "runstrategy.jpg","csv.jpg","help.jpg"};
	String[] desc = {  "Add Result to Tree", 
                       "Execute selected scan/strategy","Download File as CSV","Help"};
	JButton[] icon = new JButton[gifname.length];

	public void addIcons(int xpos, int ypos)
	{
		int iconsize = 26;
		for (int i = 0; i < gifname.length; i++)
		{
			String s = "image/formulabuilder/" + gifname1[i];
			ImageIcon cup = new ImageIcon(ClassLoader.getSystemResource(s));
			icon[i] = new JButton(cup);
			icon[i].setBounds(xpos, ypos, iconsize, iconsize);
			icon[i].setName(gifname[i]);
			icon[i].setToolTipText(desc[i]);
			this.add(icon[i], null);
			icon[i].addActionListener(this);
			xpos = xpos + iconsize + 2;
		}
		//		String s = "image/formulabuilder/open.jpg";
		//		ImageIcon cup = new ImageIcon(ClassLoader.getSystemResource(s));
		/*		icon[4] = new JButton(cup);
				icon[4].setBounds(xpos,ypos,iconsize,iconsize);
				icon[4].setName("sss");
				this.add(icon[4],null);
				icon[4].addActionListener(this);
				xpos = xpos + iconsize + 10 ;
		
		*/
	}

	JTextArea commentarea = null;
	
	public void adjustDataValue()
	{
		String BULLISH = ":BULLISH:OVERSOLD:BUY:STRONGBUY:CHEAP:";
		String BEARISH = ":BEARISH:OVERBOUGHT:SELL:STRONGSELL:EXPENSIVE:";
		if(dataValues==null || dataValues.length==0 || dataValues[0].length==0)
		{
			dataValuesColor= new String[0][0];
			return;
		}
		dataValuesColor = new String[dataValues.length][dataValues[0].length];
		
		for (int i=0;i<dataValues.length;i++)
		{
			for(int j=0;j<dataValues[0].length;j++)
			{
				String s = dataValues[i][j];
				if(s==null || s.equalsIgnoreCase(""))
				{
					continue;
				}
				else
				{
					StringTokenizer st = new StringTokenizer(dataValues[i][j],"#");
					String s11 = st.nextToken();
					if(st.hasMoreTokens())
					{
						String s12 = st.nextToken();
						dataValues[i][j] = s12;
						dataValuesColor[i][j] = s11;
					}
					else
					{
						dataValuesColor[i][j] = s11;
								
					}
				}
			}
		}
	}
	public BuySellAdvisorResult(int x1, int y1,int type)
	{
		super(null);
		this.xmax = x1;
		this.ymax = y1;
		
		this.type = type;
		String mys = "";
	
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
		
		int xpos = 5;//xpos + width+50;
		String[] sortType = {"Sort Ascending","Sort Desending"};
		sorttype = new JComboBox(sortType);
		sorttype.setName("sorttype");
		sorttype.setToolTipText("Select Sorting Type by  clicking on table headers");
		sorttype.setBounds(xpos+1, 5,150, 20);
		add(sorttype);
		
		xpos = xpos + 155;
		
		addIcons(xpos, 1);

		int width = this.xmax / 3;
		xpos = 0;
		int ylen = 75;
	
		HashMap hs = new HashMap();
		FileInputStream f = null;
		ObjectInputStream o = null;
	
		try
		{
			
			f = new FileInputStream(StockConstants.INSTALL_DIR + "/scanparameter.obj");
			o = new ObjectInputStream(f);
			hs = (HashMap)o.readObject();
			o.close();
			f.close();
		}
		catch(Exception e)
		{
			try
			{
				o.close();
				f.close();
				
			}
			catch(Exception e1)
			{
				
			}
			
		}
		ind1 = new GridPanelForScan(width, ylen, 0, 2, "Price ","ind1",hs,"Price ");
		ind1.setBounds(xpos + 1, 30, width, ylen);

		add(ind1,null);
/*		JButton[] addedbutton1 = ind1.getAddedButton();
		for (int i = 0; i < addedbutton1.length; i++)
		{
			addedbutton1[i].addActionListener(this);
		}
*/
		xpos = xpos + width;
		volind = new GridPanelForScan(width, ylen, 0, 2, "Volume ","volind",hs,"Volume");
		volind.setBounds(xpos + 1, 30, width, ylen);

		add(volind,null);
		xpos = xpos + width;
		Vector v = IndexUtility.getFilters();
		CheckBoxGridPanelScan checkbox = new CheckBoxGridPanelScan(width+50, ylen, 0, 4, "Filter", v);
		checkbox.setBounds(xpos + 1,30, width+50, ylen);

		add(checkbox);
		checkboxes = checkbox.getAddedCheckBox();

		commentarea = new JTextArea(4,200);
		JScrollPane scroll = new JScrollPane(commentarea);
		this.add(scroll);
		scroll.setBounds(0,105,xmax-10, 90);	

/*		for(int i=0;i<commentslabel.length;i++)
		{
			commentslabel[i] = new JLabel("");
			commentslabel[i].setFont(new Font("Serif", Font.PLAIN, 12));
			commentslabel[i].setBounds(0,105+i*15, 1000, 14);
			commentslabel[i].setForeground(Color.DARK_GRAY);
			this.add(commentslabel[i],null);
		}
*/		
		if(scrollPane != null && table != null)
		{
			scrollPane.remove(table);
			this.remove(scrollPane);
		}
		dataValues = new String[1][columnNames.length];
		table = new JTable( dataValues, columnNames );
		table.addMouseListener(this);
		
		table.addKeyListener(this);
		
		tableheader = table.getTableHeader();
		tableheader.addMouseListener(this);
		
		tableheader.setToolTipText("Click to sort");
		// Add the table to a scrolling pane
		scrollPane = JTable.createScrollPaneForTable( table );
		scrollPane.setBounds(0,mypos, xmax-GRAPH_DIFF, ymax-mypos);
					
		this.add( scrollPane, null );
		
	
		panel = new PanelForGraph(0, 0, GRAPH_DIFF ,ymax-mypos-80 , StockConstants.SELECTED_STOCK);
		panel.macdgraph.showstats=false;
		panel.setBounds(xmax-GRAPH_DIFF,mypos ,xmax-GRAPH_DIFF, ymax-mypos);
//	setBackground(StockConstants.graphbackgroundcolor);
	panel.setOpaque(true);
	panel.setVisible(true);
	this.add(panel, null);

	
	}
	PanelForGraph panel=null;
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
		BuySellAdvisorResult s = new BuySellAdvisorResult(1000, 800,1);
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
		else if (obj instanceof JTable)
		{
			int row = table.getSelectedRow();
			String s = dataValues[row][0];
			panel.macdgraph.setSymbol(s);

		}
		// TODO Auto-generated method stub
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	String query;
	String comment;
	public void setComments()
	{
/*		for(int i=0;i<commentslabel.length;i++)
			commentslabel[i].setText("");
		StringTokenizer st = new StringTokenizer(comment,"\n");
		int i=0;
		while(st.hasMoreTokens() && i < commentslabel.length)
		{
			commentslabel[i].setText(st.nextToken());
			i++;
		}
*/
		commentarea.setText(comment);
	}
	public void submitQuery(String query,String comment)
	{
			this.query = query;
			this.comment = comment;
			setComments();
			
			String minprice = ind1.minchange.getText().trim();
			String maxprice = ind1.maxchange.getText().trim();
			
			String minvolume = volind.minchange.getText().trim();
			String maxvolume = volind.maxchange.getText().trim();
					
			StringBuffer sb = new StringBuffer();
			
			try
			{
				
				float f; 
				sb.append("TRUE ");
				if(!minprice.equals(""))
				{
					f = Float.parseFloat(minprice);
					sb.append(" AND CLOSE[0] > " + minprice);	
					
				}
				if(!maxprice.equals(""))
				{
					f = Float.parseFloat(maxprice);
					sb.append(" AND CLOSE[0] < " + maxprice);	

				}
				if(!minvolume.equals(""))
				{
					f = Float.parseFloat(minvolume);
					sb.append(" AND VOLUME[0] > " + minvolume);	
									
				}
				if(!maxvolume.equals(""))
				{
					f = Float.parseFloat(maxvolume);
					sb.append(" AND VOLUME[0] < " + maxvolume);	
					
				}
			}
			catch(Exception e)
			{
				MessageDiaglog m = new MessageDiaglog("Please Enter a Numeric Value");
				return;
			}

			FileOutputStream f = null;
			ObjectOutputStream o = null;

			try
			{
				HashMap hs = new HashMap();
				
				hs.put("ind1.minchange", minprice);
				hs.put("ind1.maxchange", maxprice);
				
				hs.put("volind.minchange", minvolume);
				hs.put("volind.maxchange", maxvolume);
				
				
				f = new FileOutputStream(StockConstants.INSTALL_DIR + "/scanparameter.obj");
				o = new ObjectOutputStream(f);
				o.writeObject(hs);
				o.close();
				f.close();
			
			}
			catch(Exception e)
			{
				try
				{
					o.close();
					f.close();
						
				}
				catch(Exception e1)
				{
					
				}
			}

			int sortbyind = this.query.toUpperCase().indexOf(" SORTBY ");
			String afterSortBy="";
			if ( sortbyind != -1)
			{
				afterSortBy =  this.query.toUpperCase().substring(sortbyind); 
				this.query = this.query.substring(0,sortbyind);
			}

			if ( this.query.toUpperCase().indexOf(" WHERE ") == -1 )
			{
				
				this.query = this.query.trim() + "  WHERE " +  sb + " "+afterSortBy;
			}
			else
			{
			//	System.out.println("query=="+query);

				this.query = this.query + " and " + sb + afterSortBy;
			}
		//	System.out.println("query=="+this.query);

			//this.query = query;
			doOther();
	}
	public int type;
	Vector treesymbolVector=null;
	public void doOther()
	{
		Vector fileterList = new Vector();

		for (int i = 0; i < checkboxes.length; i++)
		{
			if (checkboxes[i].isSelected())
			{
	/*			if( DataDownloadScreen.downloadedIndex.get(checkboxes[i].getName())==null)
				{
					MessageDiaglog msg = new MessageDiaglog("Use Data Manager to download all stocks for filter  " + checkboxes[i].getName() + " before executing");
					return;
					
				}
	*/
				fileterList.addElement(checkboxes[i].getName());
			}
		}

		if(fileterList==null || fileterList.size()==0)
		{
			MessageDiaglog msg = new MessageDiaglog("Pls select  filters before executing ");
			return;
		}
		
		this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		HashMap hs=null;
		try
		{
			hs =ScanUtility.executeQueryForBuySellAdvisor(query, fileterList);
			this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			
		}
		catch(Exception e)
		{
			this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			return;
		}

		output = ScanUtility.output;	
		dataValues = (String[][]) hs.get("result");
		columnNames = (String[]) hs.get("column");
		 this.adjustDataValue();
		 
		treesymbolVector=new Vector();
		
		if(columnNames != null && dataValues != null && columnNames.length > 0 && columnNames[0].equalsIgnoreCase(MainGraphComponent.Symbol))
		{
			for(int i=0;i<dataValues.length;i++)
			{
				String s = dataValues[i][0];
				treesymbolVector.add(s);
			}

		}
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

		TableCellRenderer renderer = new CustomTableCellRendererBuySellAdvisor(dataValuesColor);
		table.setDefaultRenderer(Object.class, renderer);
		
		table.addMouseListener(this);
		table.addKeyListener(this);
		
		tableheader = table.getTableHeader();
		tableheader.addMouseListener(this);	
		tableheader.setToolTipText("Click to sort");
		// Add the table to a scrolling pane
		scrollPane = JTable.createScrollPaneForTable( table );
		scrollPane.setBounds(0,mypos, xmax-GRAPH_DIFF, ymax-mypos);
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
	String FORMULANAME="FORMULANAME";
	public String getFormulaFile(String query)
	{
		try
		{
			int x = query.indexOf(FORMULANAME); 
			if (x== -1)
			{
				return "";
			}
			String s= query.substring(x);
			StringTokenizer st = new StringTokenizer(s,"=");
			String name = st.nextToken();
			String value = st.nextToken();
			if(name.equals(FORMULANAME))
			{
				return value;
			}
			return "";
			
			
		}
		catch(Exception e)
		{
			return "";
		}
		
		
	}

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
			int x=query.toUpperCase().indexOf("SORTBY");
			if(x != -1)
			{
				query = query.substring(0,x);
			//	System.out.println("query=="+query);
			}
			if(sorttype.getSelectedItem().toString().equalsIgnoreCase("Sort Ascending") || columnModelIndex == 0)
				query =  query + " SORTBY  "  + columnNames[columnModelIndex];
			else 
				query =  query + " SORTBY  "  + "-1 * " +  columnNames[columnModelIndex];
				
		//	System.out.println("q="+query);
			doOther();
			query = myquery;
			
			
		}
		else if(obj instanceof JTable)
		{
			String s = dataValues[row][0];
	/*		PanelForGraphFrame frame = new PanelForGraphFrame(s);

			String formulaFileName=getFormulaFile(query);
			if(formulaFileName.equals(""))
			{
				return;
			}
	*/		
			try 
			{
/*				String realname = formulaFileName;
				int y = realname.indexOf("["); 
				if ( y != -1)
				{
					realname  = realname.substring(0,y);
		
				}

/*				String formula = Utility.getFileContent(StockConstants.INSTALL_DIR +"/library/"+realname+".fl");
				formula = convertVariableIntoValue(formula,formulaFileName);
				HashMap hs1 = new HashMap();
				hs1.put(StrategyUtility.Formula,formula);
				hs1.put(StrategyUtility.Type, "B");
				Vector  newVector = new Vector();
				newVector.add(hs1);
				frame.macd.macdgraph.drawStrategy(newVector);
*/
				panel.macdgraph.setSymbol(s);

			} catch (Exception e1) {
				// TODO Auto-generated catch block
		//		e1.printStackTrace();
			} ;
		
		}
	//	// ln("mouseclicked");

		
	
		// TODO Auto-generated method stub
	}
	public String convertVariableIntoValue(String formula1,String formulaName)
	{
		try
		{
			
			String formula = formula1.toUpperCase();
			int x = formulaName.indexOf("[");
			if(x==-1)
				return formula1;
			String temp = formulaName.substring(x+1);
			x = temp.indexOf("]");
			if(x==-1)
				return formula1;
			
			temp = temp.substring(0,x);;
			System.out.println(temp);
			StringTokenizer st = new StringTokenizer(temp,";");
			Vector value = new Vector();
			while(st.hasMoreTokens())
			{
				value.add(st.nextToken());
			}
			
			
			st = new StringTokenizer(formula,"\n");
			String parameter = st.nextToken();
			if(parameter.toUpperCase().startsWith("@PARAMETER"))
			{
				StringTokenizer st1 = new StringTokenizer(parameter.trim()," ");
				st1.nextToken();
				Vector v = new Vector();
				while(st1.hasMoreTokens())
				{
					v.add(st1.nextToken().toString().trim());
				}
				if(v.size() > 0)
				{
					String formula2 = formula1;
					for(int i=0;i<v.size();i++)
					{
						String rep = "$"+v.get(i).toString().trim()+"$";
						formula2 = Utility.replaceString(formula2, rep, (String)value.get(i));
			
					}
					return formula2;
				}
				return formula1;
			}
			return formula1;

		}
		catch(Exception e)
		{
			return formula1;
		}
		
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

			treesymbolVector=new Vector();

			if(columnNames[0].equalsIgnoreCase(MainGraphComponent.Symbol))
			{
				for(int i=0;i<dataValues[i].length;i++)
				{
					String s = dataValues[i][0];
					treesymbolVector.add(s);
				}

			}

	  		
	  		table.addMouseListener(this);
	  		table.addKeyListener(this);
			
			TableCellRenderer renderer = new CustomTableCellRendererBuySellAdvisor(dataValues);
			table.setDefaultRenderer(Object.class, renderer);
			tableheader = table.getTableHeader();
			tableheader.addMouseListener(this);
			tableheader.setToolTipText("Click to sort");
			// Add the table to a scrolling pane
			scrollPane = JTable.createScrollPaneForTable( table );
			
	/*		StringTokenizer st = new StringTokenizer(comment,"\n");
			Vector v = new Vector();
			while(st.hasMoreTokens())
			{
				v.add(st.nextToken());
			}
			
			JLabel[] mylablecomment =  new JLabel[v.size()];
			for(int i=0;i<mylablecomment.length;i++)
			{
				mylablecomment[i] = new JLabel((String)v.get(i));
				mylablecomment[i].setBounds(0, mypos , this.xmax - 10,10);

				this.add( mylablecomment[i], null );

			}
			*/
				
			scrollPane.setBounds(0, mypos, this.xmax - GRAPH_DIFF, this.ymax - mypos);
					
			this.add( scrollPane, null );


			panel = new PanelForGraph(0, 0, GRAPH_DIFF ,ymax-mypos-80 , StockConstants.SELECTED_STOCK);
			panel.macdgraph.showstats=false;
			panel.setBounds(xmax-GRAPH_DIFF,mypos ,xmax-GRAPH_DIFF, ymax-mypos);
	
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
	public void actionPerformed(ActionEvent e) 
	{
		Object obj = (Object) e.getSource();
		
		if (obj instanceof JButton)
		{
			String name = ((JButton) obj).getName();
			if (name.equalsIgnoreCase("download"))
			{
				DataDownloadScreen download = DataDownloadScreen.getSingleton();
				download.show();
			}
			else if (name.equals("csv"))
			{
				StringBuffer sb = new StringBuffer();
				for(int i=0;i<columnNames.length;i++)
				{
					sb.append(columnNames[i]+",");
				}
				sb.append("\n");
				for(int i=0;i<dataValues.length;i++)
				{
					for(int j=0;j<dataValues[i].length;j++)
					{
						sb.append(dataValues[i][j]+",");
					}
					sb.append("\n");
				}
				File f = new File(StockConstants.INSTALL_DIR+"/temp/temp.csv");
				f.delete();
				try
				{
					RandomAccessFile r = new RandomAccessFile(StockConstants.INSTALL_DIR+"/temp/temp.csv","rw");
					r.writeBytes(sb.toString());
					Runtime runtime = Runtime.getRuntime();
					Process process =
						runtime.exec(
							StockConstants.IEEXPLORERLOC + "   -new " + StockConstants.INSTALL_DIR_O + "\\temp\\temp.csv");
					r.close();
					
				}
				catch(Exception e1)
				{
		//			e1.printStackTrace();
				}
	
			}
			else if (name.equals("Apply"))
			{
				this.submitQuery(this.query, this.comment);
			}
			else if(name.equals("addtotree"))
			{
				if(treesymbolVector==null || treesymbolVector.size()==0)
				{
					MessageDiaglog message = new MessageDiaglog("No stock to add in tree");
					return;
				}
				AddStockToTreeNameFrame newformula = new  AddStockToTreeNameFrame(treesymbolVector);
			}
			else if(name.equals("help"))
			{
					ShowContentWindow searc = new ShowContentWindow(StockConstants.INSTALL_DIR + "/help","buyselladvisor.html");
			}	
			else if(name.equals("category"))
			{
					ShowContentWindow searc = new ShowContentWindow(StockConstants.INSTALL_DIR + "/help","buyselladvisor.html");
			}	

			
		}
		

		// TODO Auto-generated method stub
		
	}
}

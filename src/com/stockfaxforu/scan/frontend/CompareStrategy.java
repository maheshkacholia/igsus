/*
 * Created on Feb 19, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.scan.frontend;
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Label;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import com.stockfaxforu.backtesting.BackTesting;
import com.stockfaxforu.backtesting.BackTestingParameterScreen;
import com.stockfaxforu.component.*;
import com.stockfaxforu.formulabuilder.AddStockToTreeNameFrame;
import com.stockfaxforu.formulabuilder.FormulaDisplayPanel;
import com.stockfaxforu.formulabuilder.MessageDiaglog;
//import com.stockfaxforu.formulabuilder.NewFormulaNameBox;
import com.stockfaxforu.formulabuilder.NewFormulaNameBoxStockScanner;
import com.stockfaxforu.frontend.DataDownloadScreen;
import com.stockfaxforu.frontend.DateButton;
import com.stockfaxforu.frontend.MainScreen;
import com.stockfaxforu.frontend.PanelForGraph;
import com.stockfaxforu.frontend.PanelForGraphFrame;
import com.stockfaxforu.frontend.StrategyDataInterface;
import com.stockfaxforu.frontend.StrategyScreen;
import com.stockfaxforu.getolddataforu.loader.Loader;
import com.stockfaxforu.query.DataBaseInMemory;
import com.stockfaxforu.scan.util.ScanUtility;
import com.stockfaxforu.scan.util.StockScreenerThreadMaster;
import com.stockfaxforu.strategy.BuySellStrategy;
import com.stockfaxforu.strategy.StrategyUtility;
import com.stockfaxforu.strategy.StrategyUtilityStockScreener;
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
public class CompareStrategy extends JPanel implements FrameDataInterface,ActionListener, ListSelectionListener, WindowListener,MouseListener,StrategyDataInterface
{
	/**
	 * This is the default constructor
	 */
	public JTextField[] variable = null;
	public JButton addindicator = null;
	public JButton cancel = null;
	public JList formulaVectorList = null;
	public MainScreen maingraph = null;
	public String indicatorName = null;
	public Indicator selInd = null;
	public JLabel[] periods = new JLabel[5];
	public JList formulaSelectedVectorList, outputFieldList,selectedoutputFieldList;
	public String screenType = "";
	public Vector formulaVector=new Vector();
	public Vector formulaSelectedVector=new Vector();
	public Vector outputFieldVector=new Vector();
	public Vector selectedoutputFieldVector= new Vector();
	public Vector treesymbolVector= new Vector();
	public FormulaDisplayPanel fordispanel = new FormulaDisplayPanel();
	public JButton addoperator = null;
	public JLabel formulaName = null;
	public JLabel formulaType,lable = null;
	boolean isStockScreener = false;
	public JTable outputResultTable;
	public DefaultTableModel model;
	public Vector strategyVector=null;
	JCheckBox[] checkboxes;
	JTextArea formulaText;
	JTableHeader tableheader=null;
	public JScrollPane outputResultTableScrollPane;
	public String formulaFileName = "";
	int xinc = 10;
	int yinc = 35;
	int xincdis = 70;
	int xmax=1100;
	int ymax=900;
	String mainStr = "";
	String startDate,endDate;
	public CompareStrategy()
	{
		super();
		initialize();
	}
	
	public CompareStrategy(int xmax,int ymax)
	{
		//		super(null);
		this.xmax = xmax;
		this.ymax = ymax;
		setBackground(Color.lightGray);
		getJContentPane();
		this.setVisible(true);
//		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//		this.addWindowListener(this);
	}
	private javax.swing.JPanel jContentPane = null;
	private javax.swing.JPanel getJContentPane()
	{
		setLayout(null);	
		addIcons(10, 0);
		lable = new JLabel(mainStr );
		lable.setBounds(0, yinc, xmax, 20);
		this.add(lable,null);

		//		jContentPane.setBackground(Color.white);
		JSeparator jsep = new JSeparator(SwingConstants.HORIZONTAL);
		jsep.setBounds(0, yinc, xmax, 20);
		//		jsep.setForeground(Color.white);
		jsep.setBackground(Color.white);
		this.add(jsep, null);
		yinc = yinc + 20;

//		columnNames =  {"Symbol","Open","High","Low","Close","Buy/Sell","Pattern"};
		selectedoutputFieldVector.copyInto(columnNames);
			
				
		if(outputResultTableScrollPane != null && outputResultTable != null)
		{
			outputResultTableScrollPane.remove(outputResultTable);
			this.remove(outputResultTableScrollPane);
		}
		dataValues = new String[1][columnNames.length];
		outputResultTable = new JTable( dataValues, columnNames );
		outputResultTable.addMouseListener(this);
		tableheader = outputResultTable.getTableHeader();
		tableheader.addMouseListener(this);	
		tableheader.setToolTipText("Click to sort");
		// Add the table to a scrolling pane
		outputResultTableScrollPane = JTable.createScrollPaneForTable( outputResultTable );
		outputResultTableScrollPane.setBounds(0,yinc, xmax, ymax-yinc);
					
		this.add( outputResultTableScrollPane, null );

		
		
		int ysize = 150;
		
		
		return this;
	}
	JButton[] icon = new JButton[10];
	String[] gifname = { "Apply","csv","help"};
	String[] gifname1 = {"runstrategy.jpg","csv.jpg","help.jpg"};
	String[] desc = {    "Execute Strategies for Comparing","Down Data in CSV Format","Help"};
	public void addIcons(int xpos, int ypos)
	{
		int iconsize = 30;
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
			xpos = xpos + iconsize + 4;
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
	String[][] dataValues = null;
	String[] columnNames={"Symbol"};;
	String[] columnNames1={"SYMBOL"};;
	
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

			else if (name.equalsIgnoreCase("selectstockcategory"))
			{
				SelectCategory selcat = new SelectCategory(this);
			}
			else if (name.equals("graph"))
			{
			
				int i = outputResultTable.getSelectedRow();
				if(i==-1)
				{
					MessageDiaglog msg = new MessageDiaglog("Pls select a stock from table");
				}
				String symbol = dataValues[i][0];
				PanelForGraphFrame pframe = new PanelForGraphFrame(symbol);
			}
			if (name.equals("selectformula"))
			{
				StrategyScreen strategy = new StrategyScreen(this);				
				
			}
			else if (name.equals("Cancel"))
			{
//				dispose();
			}
			else if (name.equals("Apply"))
			{
				StrategyScreen strategy = new StrategyScreen(this);				
					
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
							StockConstants.IEEXPLORERLOC + "   -new " + StockConstants.INSTALL_DIR_O+"\\temp\\temp.csv");
					r.close();
					
				}
				catch(Exception e1)
				{
			//		e1.printStackTrace();
				}
	
			}
			else if(name.equals("help"))
			{
				try
				{
					Utility.openHelpLink("comparestrategy");
				}
				catch (Exception e1)
				{
				}
			}	

		}
		// TODO Auto-generated method stub
	}
	public void addTable()
	{
		if(outputResultTableScrollPane != null && outputResultTable != null)
			{
				outputResultTableScrollPane.remove(outputResultTable);
				this.remove(outputResultTableScrollPane);

			}

			model = new DefaultTableModel(dataValues, columnNames);

			outputResultTable = new JTable( model )
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
			outputResultTable.addMouseListener(this);
			TableCellRenderer renderer = new CustomTableCellRendererPure(dataValues,CustomTableCellRendererPure.FILTER);
			outputResultTable.setDefaultRenderer(Object.class, renderer);
			tableheader = outputResultTable.getTableHeader();
			tableheader.addMouseListener(this);
			tableheader.setToolTipText("Click to sort");
			// Add the table to a scrolling pane
			outputResultTableScrollPane = JTable.createScrollPaneForTable( outputResultTable );
			outputResultTableScrollPane.setBounds(0,yinc, xmax-20, ymax-yinc);
					
			this.add( outputResultTableScrollPane, null );

	}
	/**
	 * @param newVector
	 * 
	 */

	public Vector output=null;
	public void setFormula(String dirName1,String fileName1)
	{
		try
			{
				File f = new File(dirName1 + "/" + fileName1);
				FileInputStream file = new FileInputStream(f);
				long len = f.length();
				byte[] b = new byte[(int)len];
				file.read(b, 0, b.length);
				formulaFileName = new String(b);
				file.close();	
			}
			catch (Exception e)
			{
					
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}
	private String[][] drawStrategy()
	{
		if(BackTestingParameterScreen.dataHash==null)
				BackTestingParameterScreen.loadParameter();
			HashMap hs1 = BackTestingParameterScreen.dataHash;
			if(hs1==null)
			{
				MessageDiaglog msg = new MessageDiaglog("Please set your Backtesting parameter( goto File-->backtesting ");
				return null;
			}

		Vector v10 = IndexUtility.getIndexStockVector(catogary);
		String[] symbols = new String[v10.size()];
		v10.copyInto(symbols);
		
//		newVector.addElement(" ( CLOSE[0] > 800 ) and ( CLOSE[0] < 1000 ) ");
		
		long t1 = System.currentTimeMillis();
		String[][] data = new String[symbols.length + 1][strategyVector.size() + 1];
		
		columnNames = new String[strategyVector.size()+1];
		columnNames[0] = "Symbol";
		for(int i=0;i<strategyVector.size();i++)
		{
			HashMap hs = (HashMap)strategyVector.get(i);
			String name = (String)hs.get(StrategyUtility.Name);
			String formula = (String)hs.get(StrategyUtility.Formula);

			columnNames[i+1] = name;
		}
		for(int i=0;i<symbols.length;i++)
		
		{
			String symbolname = (String)symbols[i];	
			data[i][0] = symbolname;
			try
			{
				for(int j=0;j<strategyVector.size();j++)
				{
					try
					{
						BuySellStrategy buysell = new BuySellStrategy();
						BackTesting backtesting = new BackTesting();
						
						Vector v = Loader.getFileContentForScreening(StockConstants.STARTDATE, symbolname, StockConstants.SCREENDATE,"");
						v = Loader.createSubList(v, this.startDate, this.endDate);
						
						
						HashMap hs = (HashMap)strategyVector.get(j);
						String name = (String)hs.get(StrategyUtility.Name);
						String formula = (String)hs.get(StrategyUtility.Formula);
						Vector v1 = buysell.executeStrategy(v, formula);
						data[i][1+j] = Utility.oneDigitFloat(backtesting.returnProftLoss(BackTestingParameterScreen.buyinper,BackTestingParameterScreen.sellinper,BackTestingParameterScreen.initialamt,BackTestingParameterScreen.tranamtf,BackTestingParameterScreen.trantypestr, v1)) + "";

					}
					catch(Exception e)
					{
						continue;
					}
				}
			}
			catch (Exception e)
			{
				continue;
			}
		}
		for(int i=1;i<data[0].length;i++)
		{
			float total = 0;
			int count = 0;
			for(int j=0;j<data.length - 1;j++)
			{
				try
				{
					float x = Float.parseFloat(data[j][i]);  
					total = total + x;
					count++;
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
			float x = total / (float)( count + .00000001);
			data[data.length-1][i] = x+"";
		}
		data[data.length-1][0] = "Average Return";
		
		
/*		float[] temp = new float[data[0].length -1];
		for(int i=1;i<data[0].length;i++)
		{
			temp[i-1] = 0;
			for(int j=0;j<data)
		}
*/
		return data;
		// TODO Auto-generated method stub
	}
	
	
	public static void main(String[] args)
	{
		JFrame f = new JFrame();
		CompareStrategy newscan = new CompareStrategy(1000,1000);
		newscan.drawStrategy();
		f.getContentPane().add(newscan);
		f.setSize(1000,1000);
		f.show();
//		SelectStockScanner d = new SelectStockScanner(null);
	}
	/* (non-Javadoc)
	 * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
	 */
	public void valueChanged(ListSelectionEvent arg0)
	{
		// TODO Auto-generated method stub
	}
	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowOpened(java.awt.event.WindowEvent)
	 */
	public void windowOpened(WindowEvent arg0)
	{
		// TODO Auto-generated method stub
	}
	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowClosing(java.awt.event.WindowEvent)
	 */
	public void windowClosing(WindowEvent arg0)
	{
		// TODO Auto-generated method stub
	}
	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowClosed(java.awt.event.WindowEvent)
	 */
	public void windowClosed(WindowEvent arg0)
	{
		// TODO Auto-generated method stub
	}
	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowIconified(java.awt.event.WindowEvent)
	 */
	public void windowIconified(WindowEvent arg0)
	{
//		this.toFront();
		// TODO Auto-generated method stub
	}
	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowDeiconified(java.awt.event.WindowEvent)
	 */
	public void windowDeiconified(WindowEvent arg0)
	{
//		this.toFront();
		// TODO Auto-generated method stub
	}
	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowActivated(java.awt.event.WindowEvent)
	 */
	public void windowActivated(WindowEvent arg0)
	{
		// TODO Auto-generated method stub
	}
	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowDeactivated(java.awt.event.WindowEvent)
	 */
	public void windowDeactivated(WindowEvent arg0)
	{
//		this.toFront();
		// TODO Auto-generated method stub
	}

	/* (non-Javadoc)
	 * @see com.stockfaxforu.scan.frontend.FrameDataInterface#setData()
	 */
	String catogary=null; 
	String filter = null;
	public void setData(String catogary,String filter)
	{
		this.catogary = catogary;
		this.filter = filter;
		this.lable.setText(this.mainStr + ",Category  " + this.catogary );

		// TODO Auto-generated method stub
		
	}
	public void sortColumn(DefaultTableModel model, int colIndex, boolean ascending) {
		   Vector data = model.getDataVector();
		   Object[] colData = new Object[model.getRowCount()];
    
		   // Copy the column data in an array
		   for (int i=0; i<colData.length; i++) {
			   colData[i] = ((Vector)data.get(i)).get(colIndex);
		   }
    
		   // Sort the array of column data
		   Arrays.sort(colData, new ColumnSorter(ascending));
    
		   // Copy the sorted values back into the table model
		   for (int i=0; i<colData.length; i++) {
			   ((Vector)data.get(i)).set(colIndex, colData[i]);
		   }
		   model.fireTableStructureChanged();
	   }
    
	   public class ColumnSorter implements Comparator {
		   boolean ascending;
		   ColumnSorter(boolean ascending) {
			   this.ascending = ascending;
		   }
		   public int compare(Object a, Object b) {
			   // Treat empty strains like nulls
			   if (a instanceof String && ((String)a).length() == 0) {
				   a = null;
			   }
			   if (b instanceof String && ((String)b).length() == 0) {
				   b = null;
			   }
    
			   // Sort nulls so they appear last, regardless
			   // of sort order
			   if (a == null && b == null) {
				   return 0;
			   } else if (a == null) {
				   return 1;
			   } else if (b == null) {
				   return -1;
			   } else if (a instanceof Comparable) {
				   if (ascending) {
					   return ((Comparable)a).compareTo(b);
				   } else {
					   return ((Comparable)b).compareTo(a);
				   }
			   } else {
				   if (ascending) {
					   return a.toString().compareTo(b.toString());
				   } else {
					   return b.toString().compareTo(a.toString());
				   }
			   }
		   }
	   }
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	public void mouseClicked(MouseEvent e)
	{
		Object obj = e.getSource();
		int row = outputResultTable.getSelectedRow();
		TableColumnModel colModel = outputResultTable.getColumnModel();
		int columnModelIndex = colModel.getColumnIndexAtX(e.getX());
		int modelIndex = colModel.getColumn(columnModelIndex)
			.getModelIndex();
		if (modelIndex < 0)
			  return;

		if(obj instanceof JTableHeader)
		{
			sortData(columnModelIndex);			
		}
		else if(obj instanceof JTable)
		{
			String s = dataValues[row][0];
			PanelForGraphFrame frame = new PanelForGraphFrame(s);
//			frame.macd.
			HashMap hs1 = new HashMap();
			hs1.put(StrategyUtility.Formula,formulaFileName);
			hs1.put(StrategyUtility.Type, "B");
			Vector  newVector = new Vector();
			newVector.add(hs1);
			frame.macd.macdgraph.drawStrategy(newVector);

		}

		
	
		// TODO Auto-generated method stub
		
	}
	public void sortData(int columnno)
	{
		String[] sortedTemp = new String[dataValues.length];
		for(int i= 0;i<dataValues.length;i++)
		{
			output.get(i);
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
		TableColumnModel colModel = outputResultTable.getColumnModel();
		int columnModelIndex = colModel.getColumnIndexAtX(arg0.getX());
		
		int modelIndex = colModel.getColumn(columnModelIndex)
			.getModelIndex();
		if (modelIndex < 0)
			  return;

		if(obj instanceof JTableHeader)
		{
			this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		}
		else if(obj instanceof JTable)
		{
			this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

			int row = outputResultTable.getSelectedRow();
			}

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

	/* (non-Javadoc)
	 * @see com.stockfaxforu.frontend.StrategyDataInterface#drawStrategy(java.util.Vector)
	 */
	public void drawStrategy(Vector v)
	{
		this.strategyVector = v;
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.stockfaxforu.frontend.StrategyDataInterface#getStrategyVector()
	 */
	public Vector getStrategyVector()
	{
		return strategyVector;
	}

	 
	@Override
	public void drawStrategy(Vector strategyVector, String catogary,
			String startDateButton, String endDateButton) 
	{
		this.lable.setText("Start Date "+startDateButton + " End Date "+endDateButton);
	
		this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

//		dataValues = drawStrategy();
	
		if(BackTestingParameterScreen.dataHash==null)
				BackTestingParameterScreen.loadParameter();
			HashMap hs1 = BackTestingParameterScreen.dataHash;
			if(hs1==null)
			{
				MessageDiaglog msg = new MessageDiaglog("Please set your Backtesting parameter( goto File-->backtesting ");
				return ;
			}

		Vector v10 = IndexUtility.getIndexStockVector(catogary);
		String[] symbols = new String[v10.size()];
		v10.copyInto(symbols);
		
//		newVector.addElement(" ( CLOSE[0] > 800 ) and ( CLOSE[0] < 1000 ) ");
		
		long t1 = System.currentTimeMillis();
		String[][] data = new String[symbols.length + 1][strategyVector.size() + 1];
		
		columnNames = new String[strategyVector.size()+1];
		columnNames[0] = "Symbol";
		for(int i=0;i<strategyVector.size();i++)
		{
			HashMap hs = (HashMap)strategyVector.get(i);
			String name = (String)hs.get(StrategyUtility.Name);
			String formula = (String)hs.get(StrategyUtility.Formula);

			columnNames[i+1] = name;
		}
		for(int i=0;i<symbols.length;i++)
		
		{
			String symbolname = (String)symbols[i];	
			data[i][0] = symbolname;
			try
			{
				for(int j=0;j<strategyVector.size();j++)
				{
					try
					{
						BuySellStrategy buysell = new BuySellStrategy();
						BackTesting backtesting = new BackTesting();
						
						Vector v = Loader.getFileContentForScreening(StockConstants.STARTDATE, symbolname, StockConstants.SCREENDATE,"");
						v = Loader.createSubList(v, startDateButton, endDateButton);
						
						
						HashMap hs = (HashMap)strategyVector.get(j);
						String name = (String)hs.get(StrategyUtility.Name);
						String formula = (String)hs.get(StrategyUtility.Formula);
						Vector v1 = buysell.executeStrategy(v, formula);
						data[i][1+j] = Utility.oneDigitFloat(backtesting.returnProftLoss(BackTestingParameterScreen.buyinper,BackTestingParameterScreen.sellinper,BackTestingParameterScreen.initialamt,BackTestingParameterScreen.tranamtf,BackTestingParameterScreen.trantypestr, v1)) + "";

					}
					catch(Exception e)
					{
						continue;
					}
				}
			}
			catch (Exception e)
			{
				continue;
			}
		}
		for(int i=1;i<data[0].length;i++)
		{
			float total = 0;
			int count = 0;
			for(int j=0;j<data.length - 1;j++)
			{
				try
				{
					float x = Float.parseFloat(data[j][i]);  
					total = total + x;
					count++;
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
			float x = total / (float)( count + .00000001);
			data[data.length-1][i] = x+"";
		}
		data[data.length-1][0] = "Average Return";
		
		dataValues=data;
		
/*		float[] temp = new float[data[0].length -1];
		for(int i=1;i<data[0].length;i++)
		{
			temp[i-1] = 0;
			for(int j=0;j<data)
		}
*/
		addTable();			

		this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
// TODO Auto-generated method stub

		
		
		
		// TODO Auto-generated method stub
		
	}

	
} //  @jve:visual-info  decl-index=0 visual-constraint="4,11"

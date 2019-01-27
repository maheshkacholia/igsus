/*
 * Created on Feb 19, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.scan.frontend;
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Label;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
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
import com.stockfaxforu.component.*;
import com.stockfaxforu.formulabuilder.AddStockToTreeNameFrame;
import com.stockfaxforu.formulabuilder.FormulaDisplayPanel;
import com.stockfaxforu.formulabuilder.MessageDiaglog;
//import com.stockfaxforu.formulabuilder.NewFormulaNameBox;
import com.stockfaxforu.formulabuilder.NewFormulaNameBoxStockScanner;
import com.stockfaxforu.frontend.MainScreen;
import com.stockfaxforu.frontend.PanelForGraph;
import com.stockfaxforu.frontend.PanelForGraphFrame;
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
public class SelectStockScanner extends JPanel implements ActionListener, ListSelectionListener, WindowListener
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
	public Vector formulaVector, formulaSelectedVector, outputFieldVector,selectedoutputFieldVector,treesymbolVector;
	public FormulaDisplayPanel fordispanel = new FormulaDisplayPanel();
	public JButton addoperator = null;
	public JLabel formulaName = null;
	public JLabel formulaType = null;
	boolean isStockScreener = false;
	public JTable outputResultTable;
	JCheckBox[] checkboxes;
	JTextArea formulaText;
	public JScrollPane outputResultTableScrollPane;

	int xinc = 10;
	int yinc = 35;
	int xincdis = 70;
	int xmax=1100;
	int ymax=900;

	public SelectStockScanner()
	{
		super();
		initialize();
	}
	
	public SelectStockScanner(int xmax,int ymax)
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
		//		jContentPane.setBackground(Color.white);
		JSeparator jsep = new JSeparator(SwingConstants.HORIZONTAL);
		jsep.setBounds(0, yinc, xmax, 20);
		//		jsep.setForeground(Color.white);
		jsep.setBackground(Color.white);
		this.add(jsep, null);
		yinc = yinc + 20;
		int ysize = 150;
		
		JLabel label = new JLabel("Select Formula");
		this.add(label,null);
		label.setBounds(xinc, yinc-15, 100, 10);
		
		formulaVector = StrategyUtilityStockScreener.getAllStrategy();
		formulaVectorList = new JList(formulaVector);
		formulaVectorList.setFont(new Font("Arial", Font.BOLD, 10));
		formulaVectorList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		formulaVectorList.addListSelectionListener(this);
		JScrollPane scroolpane = new JScrollPane(formulaVectorList);
		scroolpane.setBounds(xinc, yinc, xmax/6, ysize);
		this.add(scroolpane, null);
		
		JButton apply = new JButton("Apply");
		apply.setFont(new Font("Arial", Font.BOLD, 12));
		apply.setBounds(xinc , yinc+ysize+5, 100, 20);
		apply.addActionListener(this);
		apply.setName("Apply");
		this.add(apply);

//		JButton cancel = new JButton("Cancel");
//		cancel.setFont(new Font("Arial", Font.BOLD, 12));
//		cancel.setBounds(xinc+105 , yinc+ysize+5, 100, 20);
//		cancel.addActionListener(this);
//		cancel.setName("Cancel");
//		this.add(cancel);

		xinc = xinc + 	xmax/6+5;	
		
		JButton select = new JButton(">>");
		select.setFont(new Font("Arial", Font.BOLD, 12));
		select.setBounds(xinc , 100, 50, 20);
		select.addActionListener(this);
		select.setName("selectformula");
		this.add(select);
		JButton deselect = new JButton("<<");
		deselect.setFont(new Font("Arial", Font.BOLD, 12));
		deselect.setBounds(xinc, 130, 50, 20);
		deselect.addActionListener(this);
		deselect.setName("deselectformula");
		this.add(deselect,null);
		//		indiactorList.addActionListener(this);
		//		yinc = yinc + 210;
		xinc = xinc + 55;
		JLabel label1 = new JLabel("Selected Formula");
		this.add(label1,null);
		label1.setBounds(xinc, yinc-15, 100, 10);

		formulaSelectedVector = new Vector();
		formulaSelectedVectorList = new JList(formulaSelectedVector);
//		formulaSelectedVectorList.setFont(new Font("Arial", Font.BOLD, 12));
		formulaSelectedVectorList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scroolpane = new JScrollPane(formulaSelectedVectorList);
		scroolpane.setBounds(xinc, yinc, xmax/6, ysize);
		this.add(scroolpane, null);
		//		indiactorList.addActionListener(this);
		xinc = xinc + xmax/6 + 5;
		JSeparator jsep2 = new JSeparator(SwingConstants.VERTICAL);
		jsep2.setBounds(xinc , yinc - 20, 20, ysize + 60);
		//		jsep.setForeground(Color.white);
		jsep2.setBackground(Color.white);
		this.add(jsep2, null);
		xinc = xinc + 10;
		Vector v = IndexUtility.getFilters();
		CheckBoxGridPanelScan checkbox = new CheckBoxGridPanelScan(200, 75, 0, 2, "Filter", v);
		checkbox.setBounds(xinc , yinc - 10, xmax/6, 75);
		this.add(checkbox, null);
		checkboxes = checkbox.getAddedCheckBox();
		
		JSeparator jsep1 = new JSeparator(SwingConstants.HORIZONTAL);
		jsep1.setBounds(0, yinc + ysize + 40, xmax, 20);
		//		jsep.setForeground(Color.white);
		jsep1.setBackground(Color.white);
		this.add(jsep1, null);
		xinc = xinc + xmax/6 +20;
		JSeparator jsep4 = new JSeparator(SwingConstants.VERTICAL);
		jsep4.setBounds(xinc - 20, yinc - 20, 20, ysize + 60);
		//		jsep.setForeground(Color.white);
		jsep4.setBackground(Color.white);
		this.add(jsep4, null);
		//		xinc = xinc + 430;		
		outputFieldVector = ScanUtility.getOutPutField();
		outputFieldList = new JList(outputFieldVector);
		outputFieldList.setFont(new Font("Arial", Font.BOLD, 10));
		outputFieldList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		outputFieldList.addListSelectionListener(this);
		JScrollPane scroolpane1 = new JScrollPane(outputFieldList);
		scroolpane1.setBounds(xinc, yinc, xmax/9, ysize);
		this.add(scroolpane1, null);
		xinc = xinc + xmax/9 + 5;
		JButton selectoutput = new JButton(">>");
		selectoutput.setFont(new Font("Arial", Font.BOLD, 12));
		selectoutput.setBounds(xinc, 100, 50, 20);
		selectoutput.addActionListener(this);
		selectoutput.setName("selectoutput");
		this.add(selectoutput);
		JButton deselectoutput = new JButton("<<");
		deselectoutput.setFont(new Font("Arial", Font.BOLD, 12));
		deselectoutput.setBounds(xinc, 130, 50, 20);
		deselectoutput.addActionListener(this);
		deselectoutput.setName("deselectoutput");
		this.add(deselectoutput);
		xinc = xinc + 55;
		
		selectedoutputFieldVector = new Vector();
		selectedoutputFieldVector.addElement("symbol");
		selectedoutputFieldList = new JList(selectedoutputFieldVector);
		selectedoutputFieldList.setFont(new Font("Arial", Font.BOLD, 10));
		selectedoutputFieldList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		selectedoutputFieldList.addListSelectionListener(this);
		JScrollPane scroolpane2 = new JScrollPane(selectedoutputFieldList);
		scroolpane2.setBounds(xinc, yinc, xinc/9, ysize);
		this.add(scroolpane2, null);
		/*		cancel = new JButton();
				cancel.setBounds(xinc+100,yinc,75,20);
				cancel.setName("cancel");
				cancel.setText("Cancel");
				this.add(cancel,null);
				cancel.addActionListener(this);
		*/

		yinc = yinc +5;
		formulaText =new JTextArea();
		
		JScrollPane scr = new JScrollPane(formulaText);
		scr.setBounds(0, yinc + ysize + 40, xmax, 80);
		this.add(scr,null);
		
		JSeparator jsep5 = new JSeparator(SwingConstants.HORIZONTAL);
		jsep5.setBounds(0, yinc + ysize + 40+80+5, xmax, 20);
		//		jsep.setForeground(Color.white);
		jsep5.setBackground(Color.white);
		this.add(jsep5, null);
		yinc = yinc + ysize + 40+80+10;
		
		return this;
	}
	JButton[] icon = new JButton[10];
	String[] gifname = {  "addtotree", "formulabuilder","save" ,"graph"};
	String[] gifname1 = { "addtotree.jpg", "formulabuilder.jpg","save.jpg","intraday.jpg"};
	String[] desc = {  "Add Result to tree", "Open Formula Builder","Save Formula","Show Graph"};
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
	String[] columnNames=null;
	public void actionPerformed(ActionEvent e)
	{
		Object obj = (Object) e.getSource();
		if (obj instanceof JButton)
		{
			String name = ((JButton) obj).getName();
			if (name.equals("graph"))
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
				if (this.formulaVectorList.getSelectedIndex() == -1)
					return;
				String s = (String) formulaVectorList.getSelectedValue();
				formulaSelectedVector.addElement(s);
				formulaSelectedVectorList.setListData(formulaSelectedVector);
				formulaVector.removeElement(s);
				formulaVectorList.setListData(formulaVector);
				
				String s1 = StrategyUtilityStockScreener.getFormula(s);
				BuySellStrategy buysell = new BuySellStrategy();
				Vector indVec = buysell.findAllIndicatorInQuery(s1);
				outputFieldVector.addAll(indVec);
				outputFieldList.setListData(outputFieldVector);
				showFormula();
			}
			else if (name.equals("deselectformula"))
			{
				if (this.formulaSelectedVectorList.getSelectedIndex() == -1)
					return;
				String s = (String) formulaSelectedVectorList.getSelectedValue();
				formulaVector.addElement(s);
				formulaVectorList.setListData(formulaVector);
				formulaSelectedVector.removeElement(s);
				formulaSelectedVectorList.setListData(formulaSelectedVector);
				showFormula();	
			}
			else if (name.equals("selectoutput"))
			{
				if (this.outputFieldList.getSelectedIndex() == -1)
					return;
				String s = (String) outputFieldList.getSelectedValue();
				selectedoutputFieldVector.addElement(s);
				selectedoutputFieldList.setListData(selectedoutputFieldVector);
				outputFieldVector.removeElement(s);
				outputFieldList.setListData(outputFieldVector);
			}
			else if (name.equals("deselectoutput"))
			{
				if (this.selectedoutputFieldList.getSelectedIndex() == -1)
					return;
				String s = (String) selectedoutputFieldList.getSelectedValue();
				outputFieldVector.addElement(s);
				outputFieldList.setListData(outputFieldVector);
				selectedoutputFieldVector.removeElement(s);
				selectedoutputFieldList.setListData(selectedoutputFieldVector);
			}
			else if (name.equals("Cancel"))
			{
//				dispose();
			}
			else if (name.equals("Apply"))
			{
/*
				if(StockConstants.globalScreenTable == null)
				{
					MessageDiaglog msg = new MessageDiaglog(" Data is populating , wait till data is populated ");
					return ;
				}
*/				
				Vector newVector = new Vector();

				for (int i = 0; i < formulaSelectedVector.size(); i++)
				{
					HashMap hs = (HashMap) StrategyUtilityStockScreener.getStrategyMap((String) formulaSelectedVector.elementAt(i));
					newVector.addElement(hs.get(StrategyUtilityStockScreener.Formula));
				}
				dataValues = drawStrategy(newVector,selectedoutputFieldVector);
				columnNames =  new String[selectedoutputFieldVector.size()];
				selectedoutputFieldVector.copyInto(columnNames);
			
				
				if(outputResultTableScrollPane != null && outputResultTable != null)
				{
					outputResultTableScrollPane.remove(outputResultTable);
					this.remove(outputResultTableScrollPane);
				}
				outputResultTable = new JTable( dataValues, columnNames );
	
				// Add the table to a scrolling pane
				outputResultTableScrollPane = JTable.createScrollPaneForTable( outputResultTable );
				outputResultTableScrollPane.setBounds(0,yinc, xmax, ymax-yinc);
					
				this.add( outputResultTableScrollPane, null );

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
			else if(name.equals("save"))
			{
				if(formulaSelectedVector==null || formulaSelectedVector.size()==0)
				{
//					MessageDiaglog message = new MessageDiaglog("No stock to add in tree");
					return;
				}
				saveFormula();
			}

		}
		// TODO Auto-generated method stub
	}
	/**
	 * @param newVector
	 * 
	 */
	public void showFormula()
	{
		Vector newVector = new Vector();

		for (int i = 0; i < formulaSelectedVector.size(); i++)
		{
			HashMap hs = (HashMap) StrategyUtilityStockScreener.getStrategyMap((String) formulaSelectedVector.elementAt(i));
			newVector.addElement(hs.get(StrategyUtilityStockScreener.Formula));
		}
		StringBuffer clubedQuery = new StringBuffer();
		int length=0;
		for(int i=0;i<newVector.size();i++)
		{
			length = length +	newVector.elementAt(i).toString().length();
			if(length > 150)
			{
				clubedQuery.append("\n");	
				length=0;
			}

			clubedQuery.append("( " + newVector.elementAt(i) + " )" );
			if((i+1)<  newVector.size())
				clubedQuery.append( " or ");
		}
		formulaText.setText(clubedQuery.toString());
		
	}
	public void saveFormula()
	{
		Vector newVector = new Vector();

		for (int i = 0; i < formulaSelectedVector.size(); i++)
		{
			HashMap hs = (HashMap) StrategyUtilityStockScreener.getStrategyMap((String) formulaSelectedVector.elementAt(i));
			newVector.addElement(hs.get(StrategyUtilityStockScreener.Formula));
		}
		StringBuffer clubedQuery = new StringBuffer();
		int length=0;
		for(int i=0;i<newVector.size();i++)
		{
			clubedQuery.append("( " + newVector.elementAt(i) + " )" );
			if((i+1)<  newVector.size())
				clubedQuery.append( " or ");
		}
		NewFormulaNameBoxStockScanner newformula = new NewFormulaNameBoxStockScanner(clubedQuery.toString());		
	}


	private String[][] drawStrategy(Vector newVector,Vector outputField)
	{
		Vector v10 = new Vector();
	
		for(int i=0;i<checkboxes.length;i++)
		{
	
			if(checkboxes[i].isSelected())
			{
				Vector v = IndexUtility.getIndexStockVector(checkboxes[i].getName());
				v10.addAll(v);
			}
		}

		String[] symbols = new String[v10.size()];
		v10.copyInto(symbols);
		
//		newVector.addElement(" ( CLOSE[0] > 800 ) and ( CLOSE[0] < 1000 ) ");
		
		Vector symbolVector = new Vector();
		for(int i=0;i<symbols.length;i++)
		{
			HashMap hs = new HashMap();
			hs.put("symbol",symbols[i]);
			symbolVector.addElement(hs);
		}

//		long t1 = System.currentTimeMillis();
//		BuySellStrategy buysell = new BuySellStrategy();
//		String[] output = buysell.getAllStocksMultipleQuery(symbolVector, newVector);
//		long t2 = System.currentTimeMillis();

		long t1 = System.currentTimeMillis();
//		Vector indVec = findAllIndicatorInQuery(strategyQuery);
		
		StockScreenerThreadMaster master = new StockScreenerThreadMaster();
		this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		Vector output = master.getAllStocksMultipleQueryHash(symbolVector, newVector.toString());
		StringBuffer sb = new StringBuffer();
		BuySellStrategy buysell = new BuySellStrategy();
		String query = buysell.createQuery(newVector);
	//	// ln(query);
//		Vector output = DataBaseInMemory.executeQuery(query, StockConstants.globalScreenTable);
		this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

		long t2 = System.currentTimeMillis();
		

		
		String[][] returnStr= new String[output.size()][outputField.size()];
		treesymbolVector = new Vector();
		for(int i=0;i<output.size();i++)
		{
			HashMap hs = (HashMap)output.elementAt(i);
			for(int j=0;j<outputField.size();j++)
			{
				String s = (String)hs.get(outputField.elementAt(j));
				try
				{
					float f = Utility.floatDataAtTwoPrecision(Float.parseFloat(s));
					s= f+"";
				}
				catch(Exception e)
				{
				}
//				String[] temp = ScanUtility.getSymbolDetail(s,outputField);
				returnStr[i][j]=s;
				
			}
			treesymbolVector.addElement(hs.get("symbol"));
		}
		
		return returnStr;
		// TODO Auto-generated method stub
	}
	
	
	public static void main(String[] args)
	{
		JFrame f = new JFrame();
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
} //  @jve:visual-info  decl-index=0 visual-constraint="4,11"

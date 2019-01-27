/*
 * Created on Feb 18, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.frontend;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.Customizer;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;


import com.stockfaxforu.backtesting.BackTesting;
import com.stockfaxforu.backtesting.BackTestingParameterScreen;
import com.stockfaxforu.component.*;
import com.stockfaxforu.config.ConfigUtility;
import com.stockfaxforu.config.SetDrawingComponentProperty;
import com.stockfaxforu.config.SetGraphProperty;
import com.stockfaxforu.config.YahooLogin;
import com.stockfaxforu.formulabuilder.CustmizedIndicatorBuilderScreen;
import com.stockfaxforu.formulabuilder.MessageDiaglog;
import com.stockfaxforu.getolddataforu.loader.Loader;
import com.stockfaxforu.scan.frontend.IndiacatorValueScreen;
import com.stockfaxforu.strategy.BuySellStrategy;
import com.stockfaxforu.strategy.StrategyUtility;
import com.stockfaxforu.trading.BuySellStockScreen;
import com.stockfaxforu.util.GlobalData;
import com.stockfaxforu.util.StockConstants;
import com.stockfaxforu.util.Utility;
/**
 * @author sdeopura
 *
 * To change the template for this generated type comment go to
 */
public class PanelForGraph extends JPanel implements ActionListener, PanelForGraphImpl,MouseListener
{
	public static final String INDVECTROR = "INDVECTROR";
	public static final String STRVECTROR = "STRVECTROR";
	
	String[] petStrings = { "line", "trendline", "horline", "verline", "addnote","fibhor","fib","buyarrow","sellarrow" };
		String[] desp = { "Line", "Trendline", "Hor. Line", "Ver. Line", "Add Note","Fib. Ret(Time)","Fib Ret.","Buy Arrow","Sell Arrow" };
		String[] tooltips = {"Draw Line", "Draw Trendline", "Draw Horizontal Line", "Draw Vertical Line", "Add Note","Draw Fibinnoci Retracement(Time)","Draw Fibinnoci Retracement","Draw Buy Arrow","Draw Sell Arrow" };
	
	int x1, y1, x2, y2;
//	public JComboBox symbollist;
	public GraphComponentContainer macdgraph = null;
//	public Vector symbolVector;
	public String symbol;
	public JComboBox graphresolutionbut,yearBox,expiryDateCombo;
	
	/**
	 * This is the default constructor
	 */
	public PanelForGraph()
	{
		super();
		initialize();
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
	int xposforcombo=0;
	boolean intraday=false;
	JComboBox intradayDateBox=null;
	boolean smallintradaypanel=false;
	public PanelForGraph(int x1, int y1, int x2, int y2, String symbol,boolean intraday)
	{
		super(null);
		this.intraday=true;
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.symbol = symbol;
		doInitilizataion();		
		macdgraph.convert.startDate = "today-09:00:00";
	}	
	public PanelForGraph(int x1, int y1, int x2, int y2, String symbol,boolean intraday,boolean smallintradaypanel)
	{
		super(null);
		this.intraday=intraday;
		if(intraday)
		{
			
			if(!Utility.isIntraDayUser())
			{
				return;
			}
		}	
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.symbol = symbol;
		this.smallintradaypanel=smallintradaypanel;
		doInitilizataion();
		if(intraday)
			macdgraph.convert.startDate = "today-09:00:00";
	}	
	
	public PanelForGraph(int x1, int y1, int x2, int y2, String symbol)
	{
		super(null);
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.symbol = symbol;
		doInitilizataion();
	}
	
	public void doDrawingComponent(String butName)
	{
		String drawingcom = "line:horline:verline:fibret:fibrethor:buyarrow:sellarrow:note";
		if (butName.equalsIgnoreCase("line"))
		{
			this.macdgraph.doPaintLine(true);
		}
		else if (butName.equalsIgnoreCase("gannfan"))
		{
			GannFan gannfan = new GannFan(100, 100, 500, 500,this.macdgraph);
			this.macdgraph.addComponent(gannfan);
			this.macdgraph.repaint();
		}
		else if (butName.equalsIgnoreCase("addnote"))
		{
			AddNotePanel addpanel = new AddNotePanel(this);
			//			FibonnciRetracement fibret = new FibonnciRetracement(100,100,300,300);
			//			macdgraph.addComponent(fibret);
		}
		else if (butName.equalsIgnoreCase("fib"))
		{
			//			AddNotePanel addpanel = new AddNotePanel(this);
			FibonnciRetracement fibret = new FibonnciRetracement(100, 100, 300, 300,this.macdgraph);
			
			this.macdgraph.addComponent(fibret);
			this.macdgraph.repaint();
		}
		else if (butName.equalsIgnoreCase("fibhor"))
		{
			//			AddNotePanel addpanel = new AddNotePanel(this);
			FibonnciRetracementTime fibret = new FibonnciRetracementTime(100, 100, 300, 300,this.macdgraph);
//			fibret.graphcomponent = graph.macdgraph;
			this.macdgraph.addComponent(fibret);
			this.macdgraph.repaint();
		}
		else if (butName.equalsIgnoreCase("horline"))
		{
			this.macdgraph.doPaintLineHor(true);
		}
		else if (butName.equalsIgnoreCase("verline"))
		{
			this.macdgraph.doPaintLineVer(true);
		}
		else if (butName.equalsIgnoreCase("buyarrow"))
		{
			this.macdgraph.doPaintBuyArrow(true);
		}
		else if (butName.equalsIgnoreCase("sellarrow"))
		{
			this.macdgraph.doPaintSellArrow(true);
		}
	}

	public int addDrawingComponent(int xinc)
	{
		int iconsize = 20;
		xinc = xinc + 5;
		JSeparator jsep = new JSeparator(SwingConstants.VERTICAL);
		this.add(jsep,null);
		jsep.setBounds(xinc, 1, 10, iconsize);
		xinc = xinc + 5;
		ImageIcon cup1 = new ImageIcon(ClassLoader.getSystemResource("image/drawing/line.jpg"));
		JButton line = new JButton(cup1);
		line.setBounds(xinc, 1, iconsize, iconsize);
		line.setName("line");
		line.setToolTipText(" Draw Line ");
		this.add(line,null);
		line.addActionListener(this);
		//		xinc = xinc + iconsize;
		//		cup1 = new ImageIcon(ClassLoader.getSystemResource("image/drawing/trendline.jpg"));
		//		JButton trendline = new JButton(cup1);
		//		trendline.setBounds(xinc, 1, iconsize, iconsize);
		//		trendline.setName("trendline");
		//		trendline.setToolTipText(" Draw Trend Line ");
		//		jContentPane.add(trendline);
		//		trendline.addActionListener(this);
		xinc = xinc + iconsize;
		cup1 = new ImageIcon(ClassLoader.getSystemResource("image/drawing/horline.jpg"));
		JButton horline = new JButton(cup1);
		horline.setBounds(xinc, 1, iconsize, iconsize);
		horline.setName("horline");
		horline.setToolTipText(" Draw Horizontal Line ");
		this.add(horline,null);
		horline.addActionListener(this);
		xinc = xinc + iconsize;
		cup1 = new ImageIcon(ClassLoader.getSystemResource("image/drawing/verline.jpg"));
		JButton verline = new JButton(cup1);
		verline.setBounds(xinc, 1, iconsize, iconsize);
		verline.setName("verline");
		verline.setToolTipText(" Draw Vertical Line ");
		this.add(verline,null);
		verline.addActionListener(this);
		xinc = xinc + iconsize;
		cup1 = new ImageIcon(ClassLoader.getSystemResource("image/drawing/addnote.jpg"));
		JButton addnote = new JButton(cup1);
		addnote.setBounds(xinc, 1, iconsize, iconsize);
		addnote.setName("addnote");
		addnote.setToolTipText("Add Note/Comment");
		this.add(addnote,null);
		addnote.addActionListener(this);
		/*
				xinc = xinc + iconsize;
				cup1 = new ImageIcon(ClassLoader.getSystemResource("image/drawing/rectangle.jpg"));
				JButton rectangle = new JButton(cup1);
				rectangle.setBounds(xinc, 1, iconsize, iconsize);
				rectangle.setName("rectangle");
				rectangle.setToolTipText(" Add rectangle to Graph ");
				jContentPane.add(rectangle);
				rectangle.addActionListener(this);
		*/
		xinc = xinc + iconsize;
		cup1 = new ImageIcon(ClassLoader.getSystemResource("image/drawing/fibhor.jpg"));
		JButton fibhor = new JButton(cup1);
		fibhor.setBounds(xinc, 1, iconsize, iconsize);
		fibhor.setName("fibhor");
		fibhor.setToolTipText(" Draw Fibonnoci Retracement With Time");
		this.add(fibhor);
		fibhor.addActionListener(this);
		xinc = xinc + iconsize;
		cup1 = new ImageIcon(ClassLoader.getSystemResource("image/drawing/fib.jpg"));
		JButton fib = new JButton(cup1);
		fib.setBounds(xinc, 1, iconsize, iconsize);
		fib.setName("fib");
		fib.setToolTipText(" Draw Fibonnoci Retracement With Price");
		this.add(fib,null);
		fib.addActionListener(this);
		xinc = xinc + iconsize;
		cup1 = new ImageIcon(ClassLoader.getSystemResource("image/drawing/buyarrow.jpg"));
		JButton buyarrow = new JButton(cup1);
		buyarrow.setBounds(xinc, 1, iconsize, iconsize);
		buyarrow.setName("buyarrow");
		buyarrow.setToolTipText(" Draw Buy Arrow ");
		this.add(buyarrow,null);
		buyarrow.addActionListener(this);
		xinc = xinc + iconsize;
		cup1 = new ImageIcon(ClassLoader.getSystemResource("image/drawing/sellarrow.jpg"));
		JButton sellarrow = new JButton(cup1);
		sellarrow.setBounds(xinc, 1, iconsize, iconsize);
		sellarrow.setName("sellarrow");
		sellarrow.setToolTipText(" Draw Sell Arrow ");
		this.add(sellarrow,null);
		sellarrow.addActionListener(this);
		xinc = xinc + iconsize;
		cup1 = new ImageIcon(ClassLoader.getSystemResource("image/drawing/gann.jpg"));
		JButton gannfan = new JButton(cup1);
		gannfan.setBounds(xinc, 1, iconsize, iconsize);
		gannfan.setName("gannfan");
		gannfan.setToolTipText(" Draw Gann Fan ");
		this.add(gannfan,null);
		gannfan.addActionListener(this);
		xinc = xinc + iconsize;
		cup1 = new ImageIcon(ClassLoader.getSystemResource("image/help.jpg"));
		JButton help = new JButton(cup1);
		help.setBounds(xinc, 1, iconsize, iconsize);
		help.setName("help");
		help.setToolTipText(" Help ");
		this.add(help,null);
		help.addActionListener(this);
		xinc = xinc + 5;
		JSeparator jsep1 = new JSeparator(SwingConstants.VERTICAL);
		this.add(jsep1);
		jsep1.setBounds(xinc, 1, iconsize, iconsize);
		return xinc;
	}
	JComboBox pivot=null;
	public void doInitilizataion()
	{
		GlobalData.addVisitedStock(symbol);
		
		int xinc = x1 + 10;
		setPreferredSize(new Dimension(x2, y2));
		setBackground(StockConstants.graphbackgroundcolor);
		//adding symbol
/*		symbolVector = GlobalData.visitedStock;
		if(!intraday)
		{
			symbollist = new JComboBox(symbolVector);
			symbollist.setFont(new Font("Arial", Font.BOLD, 9));
			//		symbollist.setBackground(Color.darkGray);
			//		symbollist.setForeground(Color.white);
			symbollist.setToolTipText("Select Stock");
			symbollist.setName("symbollist");
			symbollist.setBounds(xinc, 1, 75, 20);
			symbollist.addActionListener(this);
			xinc = xinc + 75;
			add(symbollist, null);
			
		}
*/		
		if(!intraday)
		{
			Vector year = Utility.getYear();
			yearBox = new JComboBox(year);
			yearBox.setFont(new Font("Arial", Font.BOLD, 9));
			yearBox.setToolTipText(" Select Year ");
			yearBox.setName("year");
			yearBox.setBounds(xinc, 1, 50, 20);
	//	String s = StockConstants.ShowGraphYear ;
	//	String[] ss = s.split("-");
	//	yearBox.setSelectedItem(ss[0]);

			add(yearBox, null);
		yearBox.addActionListener(this);
	
			
			xinc = xinc + 50;

		}
		else 
		{
			Vector year = Utility.getTime();
			yearBox = new JComboBox(year);
			yearBox.setFont(new Font("Arial", Font.BOLD, 9));
			yearBox.setToolTipText(" Select Time ");
			yearBox.setName("year");
			yearBox.setBounds(xinc, 1, 100, 20);
			String s = StockConstants.ShowGraphYear ;
			add(yearBox, null);
			yearBox.addActionListener(this);

			
			xinc = xinc + 100;
			
			Vector intraday = Utility.getIntradayDate();
			intraday.add(0,"today");
			intradayDateBox = new JComboBox(intraday);
			intradayDateBox.setFont(new Font("Arial", Font.BOLD, 9));
			intradayDateBox.setToolTipText(" Select Time ");
			intradayDateBox.setName("intradayDateBox");
			intradayDateBox.setBounds(xinc, 1, 100, 20);
//			String s = StockConstants.ShowGraphYear ;
			add(intradayDateBox, null);
			intradayDateBox.addActionListener(this);

			
			xinc = xinc + 100;
			
			
		}
	
		Vector graphtype = Utility.getGraphType();
		JComboBox graphtypebut = new JComboBox(graphtype);
		graphtypebut.setFont(new Font("Arial", Font.BOLD, 9));
		graphtypebut.setToolTipText(" Select Graph Type ");
		graphtypebut.setBounds(xinc, 1, 75, 20);
		graphtypebut.setName("graphtype");
		add(graphtypebut, null);
		graphtypebut.setSelectedItem(getGraphType(StockConstants.GraphDisplay));
		graphtypebut.addActionListener(this);
		
		
		xinc = xinc + 75;
		xposforcombo = xinc;
		Vector graphresolution = Utility.getGraphResolutions();
		if(!intraday)
			graphresolution = Utility.getGraphResolutions();
		else 
			graphresolution = Utility.getGraphResolutionsForIntraday();
			
		graphresolutionbut = new JComboBox(graphresolution);
		graphresolutionbut.setFont(new Font("Arial", Font.BOLD, 9));
		graphresolutionbut.setToolTipText(" Select Graph Resolution ");
		graphresolutionbut.setName("graphresolution");
		graphresolutionbut.setBounds(xinc, 1,75, 20);
		add(graphresolutionbut, null);
		String s = StockConstants.GraphResolution +"-Day";
		graphresolutionbut.setSelectedItem(s);
		graphresolutionbut.addActionListener(this);

		xinc = xinc + 75;
		Vector cursortypeVec = Utility.getCursortype();
		JComboBox cursortype = new JComboBox(cursortypeVec);
		cursortype.setFont(new Font("Arial", Font.BOLD, 9));
		cursortype.setToolTipText(" Select Cursor type ");
		cursortype.setBounds(xinc, 1, 100, 20);
		cursortype.setName("cursortype");
		cursortype.setSelectedItem(StockConstants.CursorType);

		add(cursortype, null);
		cursortype.addActionListener(this);
		xinc = xinc + 100;
		ImageIcon  cup1=null;
		if(!intraday)
		{
			cup1 = new ImageIcon(ClassLoader.getSystemResource("image/formulabuilder.jpg"));
			JButton compare = new JButton(cup1);
			compare.setBackground(StockConstants.graphbackgroundcolor);
			compare.setBounds(xinc, 1, 20, 20);
			compare.setName("compare");
			compare.setForeground(Color.white);
			compare.setToolTipText(" Compare Stock ");
			add(compare, null);
			compare.addActionListener(this);
			
		}
		if(intraday)
		{	
			String[] pivots={"none","CamarillaPivotPoints","WoodiePivotPoint"};
			 pivot = new JComboBox(pivots);
			 pivot.setFont(new Font("Arial", Font.PLAIN, 9));
			 pivot.setToolTipText(" Select Pivot type for intraday support/resistance(preferred CalculatingPivot ), none if you do't want ");
			 pivot.setBounds(xinc, 1, 125, 20);
			 pivot.setName("pivot");
			// pivot.setSelectedItem(StockConstants.CursorType);
			 pivot.addActionListener(this);
			 add(pivot);
			 xinc = xinc + 125;
		}
		int yinc = 1;
		
//
		if(intraday && smallintradaypanel==false)
		{
			addDrawingComponent(xinc);
		}
		
		//2nd row
		yinc = yinc + 20;
		xinc = 10;
		
		cup1 = new ImageIcon(ClassLoader.getSystemResource("image/prev.jpg"));
		JButton prev = new JButton(cup1);
		prev.setBackground(StockConstants.graphbackgroundcolor);
		prev.setBounds(xinc, yinc, 20, 20);
		prev.setName("prev");
		prev.setForeground(Color.white);
		prev.setToolTipText(" Prev Data ");
		add(prev, null);
		prev.addActionListener(this);
		
		xinc = xinc + 20;
		cup1 = new ImageIcon(ClassLoader.getSystemResource("image/next.jpg"));
		JButton next = new JButton(cup1);
		next.setBackground(StockConstants.graphbackgroundcolor);
		next.setBounds(xinc, yinc, 20, 20);
		next.setName("next");
		next.setForeground(Color.white);
		next.setToolTipText(" Next data ");
		add(next, null);
		next.addActionListener(this);
		
		xinc = xinc + 20;
		cup1 = new ImageIcon(ClassLoader.getSystemResource("image/expand.GIF"));
		JButton expand = new JButton(cup1);
		expand.setBackground(StockConstants.graphbackgroundcolor);
		expand.setBounds(xinc, yinc, 20, 20);
		expand.setName("expand");
		expand.setForeground(Color.white);
		expand.setToolTipText(" Zoom In ");
		add(expand, null);
		expand.addActionListener(this);
		xinc = xinc + 20;
		cup1 = new ImageIcon(ClassLoader.getSystemResource("image/shrink.GIF"));
		JButton shrink = new JButton(cup1);
		shrink.setBackground(StockConstants.graphbackgroundcolor);
		shrink.setBounds(xinc, yinc, 20, 20);
		shrink.setName("shrink");
		shrink.setForeground(Color.white);
		shrink.setToolTipText(" Zoom Out ");
		add(shrink, null);
		shrink.addActionListener(this);
		xinc = xinc + 20;
		cup1 = new ImageIcon(ClassLoader.getSystemResource("image/resetzoom.jpg"));
		JButton resetzoom = new JButton(cup1);
		resetzoom.setBackground(StockConstants.graphbackgroundcolor);
		resetzoom.setBounds(xinc, yinc, 20, 20);
		resetzoom.setName("resetzoom");
		resetzoom.setForeground(Color.white);
		resetzoom.setToolTipText(" Reset Zoom ");
		add(resetzoom, null);
		resetzoom.addActionListener(this);
		
		
		xinc = xinc + 20;
		cup1 = new ImageIcon(ClassLoader.getSystemResource("image/expanddrag.jpg"));
		JButton expanddrag = new JButton(cup1);
		expanddrag.setBackground(StockConstants.graphbackgroundcolor);
		expanddrag.setBounds(xinc, yinc, 20, 20);
		expanddrag.setName("expanddrag");
		expanddrag.setForeground(Color.white);
		expanddrag.setToolTipText(" Expand by Dragging ");
		add(expanddrag, null);
		expanddrag.addActionListener(this);

		if(!intraday && !smallintradaypanel)
		{	
			xinc = xinc + 20;
			cup1 = new ImageIcon(ClassLoader.getSystemResource("image/showindicator.jpg"));
			JButton showindicator = new JButton(cup1);
			showindicator.setBackground(Color.black);
			showindicator.setBounds(xinc, yinc, 20, 20);
			showindicator.setName("updateproperty");
			showindicator.setForeground(Color.white);
			showindicator.setToolTipText(" Update Drawing Component property ");
			add(showindicator, null);
			showindicator.addActionListener(this);
		}
		
		xinc=xinc+20;
		ImageIcon cup = new ImageIcon(ClassLoader.getSystemResource("image/addindicator.jpg"));
		JButton addind = new JButton(cup);
		addind.setBackground(StockConstants.graphbackgroundcolor);
		addind.setBounds(xinc, yinc, 20, 20);
		addind.setName("addind");
		add(addind, null);
		addind.addActionListener(this);
		addind.setToolTipText(" Manage Indicator ");
		xinc = xinc + 20;

		cup1 = new ImageIcon(ClassLoader.getSystemResource("image/inbuiltstrategy.jpg"));
		JButton inbuiltstrategy = new JButton(cup1);
		inbuiltstrategy.setBackground(StockConstants.graphbackgroundcolor);
		inbuiltstrategy.setBounds(xinc, yinc, 20, 20);
		inbuiltstrategy.setName("inbuiltstrategy");
		inbuiltstrategy.setToolTipText(" Apply Inbuilt Strategies  ");
		add(inbuiltstrategy, null);
		inbuiltstrategy.addActionListener(this);
		xinc = xinc + 20;

	//	if(!intraday)	
		{	
			cup1 = new ImageIcon(ClassLoader.getSystemResource("image/find_small.jpg"));
			JButton find = new JButton(cup1);
			find.setBackground(StockConstants.graphbackgroundcolor);
			find.setBounds(xinc, yinc, 20, 20);
			find.setName("find");
			find.setForeground(Color.white);
			find.setToolTipText(" Find Stock ");
			find.addActionListener(this);

			
			xinc = xinc + 20;

			add(find, null);
		}		

		cup1 = new ImageIcon(ClassLoader.getSystemResource("image/formula.jpg"));
		JButton buysell = new JButton(cup1);
		buysell.setBackground(StockConstants.graphbackgroundcolor);
		buysell.setBounds(xinc, yinc, 20, 20);
		buysell.setName("buysell");
		buysell.setForeground(Color.white);
		buysell.setToolTipText(" Apply Strategy ");
		add(buysell, null);
		buysell.addActionListener(this);
		xinc = xinc + 20;
		
		cup1 = new ImageIcon(ClassLoader.getSystemResource("image/backtesting.jpg"));
		JButton backtesting = new JButton(cup1);
		backtesting.setBackground(StockConstants.graphbackgroundcolor);
		backtesting.setBounds(xinc, yinc, 20, 20);
		backtesting.setName("backtesting");
		backtesting.setForeground(Color.white);
		backtesting.setToolTipText(" Backtesting ");
		add(backtesting, null);
		backtesting.addActionListener(this);
		xinc = xinc + 20;
		
		
		cup1 = new ImageIcon(ClassLoader.getSystemResource("image/deletebuysell.jpg"));
		JButton deletebuysell = new JButton(cup1);
		deletebuysell.setBackground(StockConstants.graphbackgroundcolor);
		deletebuysell.setBounds(xinc, yinc, 20, 20);
		deletebuysell.setName("deletebuysell");
		deletebuysell.setForeground(Color.white);
		deletebuysell.setToolTipText("Remove Strategy");
		add(deletebuysell, null);
		deletebuysell.addActionListener(this);

		if(!intraday)
		{

			xinc = xinc + 20;
			cup1 = new ImageIcon(ClassLoader.getSystemResource("image/save.jpg"));
			JButton save = new JButton(cup1);
			save.setBackground(StockConstants.graphbackgroundcolor);
			save.setBounds(xinc, yinc, 20, 20);
			save.setName("save");
			save.setForeground(Color.white);
			save.setToolTipText(" Save Graph ");
			add(save, null);
			save.addActionListener(this);
			
		}
		xinc = xinc + 20;
		cup1 = new ImageIcon(ClassLoader.getSystemResource("image/template.jpg"));
		JButton template = new JButton(cup1);
		template.setBackground(StockConstants.graphbackgroundcolor);
		template.setBounds(xinc, yinc, 20, 20);
		template.setName("template");
		template.setForeground(Color.white);
		template.setToolTipText(" Apply Template ");
		add(template, null);
		template.addActionListener(this);
		
		xinc = xinc + 20;
		cup1 = new ImageIcon(ClassLoader.getSystemResource("image/custom.jpg"));
		JButton custom = new JButton(cup1);
		custom.setBackground(StockConstants.graphbackgroundcolor);
		custom.setBounds(xinc, yinc, 20, 20);
		custom.setName("custom");
		custom.setForeground(Color.white);
		custom.setToolTipText(" Customized Indicator");
		add(custom, null);
		custom.addActionListener(this);
		
		if(!intraday  && !smallintradaypanel)
		{
			xinc = xinc + 20;
			cup1 = new ImageIcon(ClassLoader.getSystemResource("image/portfolio.jpg"));
			JButton portfolio = new JButton(cup1);
			portfolio.setBackground(StockConstants.graphbackgroundcolor);
			portfolio.setBounds(xinc, yinc, 20, 20);
			portfolio.setName("portfolio");
			portfolio.setForeground(Color.white);
			portfolio.setToolTipText(" Buy/Sell Stock (add to portfolio )");
			add(portfolio, null);
			portfolio.addActionListener(this);
				
		}
/*		
		if(!intraday  && !smallintradaypanel)
		{
			xinc = xinc + 20;
			cup1 = new ImageIcon(ClassLoader.getSystemResource("image/futurehis.jpg"));
			JButton futurehis = new JButton(cup1);
		//	custom.setBackground(Color.black);
			futurehis.setBounds(xinc, yinc, 20, 20);
			futurehis.setName("futurehis");
			futurehis.setForeground(Color.white);
			futurehis.setToolTipText(" Future Historical");
			add(futurehis, null);
			futurehis.addActionListener(this);
			
		}
		
*/		
	
		
		if(!intraday && !smallintradaypanel)
		{

			xinc = xinc + 20;
			cup1 = new ImageIcon(ClassLoader.getSystemResource("image/intraday.jpg"));
			JButton intradaybut = new JButton(cup1);
			intradaybut.setBackground(Color.black);
			intradaybut.setBounds(xinc, yinc, 20, 20);
			intradaybut.setName("intraday");
			intradaybut.setForeground(Color.white);
			intradaybut.setToolTipText(" Intraday Chart ");
			add(intradaybut, null);
			intradaybut.addActionListener(this);
			
/*		
			xinc = xinc + 20;
			cup1 = new ImageIcon(ClassLoader.getSystemResource("image/showfinancial.jpg"));
			JButton showfinancial = new JButton(cup1);
			showfinancial.setBackground(Color.black);
			showfinancial.setBounds(xinc, yinc, 20, 20);
			showfinancial.setName("showfinancial");
			showfinancial.setForeground(Color.white);
			showfinancial.setToolTipText(" Show Financial ");
			add(showfinancial, null);
			showfinancial.addActionListener(this);
*/
	/*		xinc = xinc + 20;

			cup1 = new ImageIcon(ClassLoader.getSystemResource("image/help.jpg"));
			JButton help = new JButton(cup1);
			help.setBackground(StockConstants.graphbackgroundcolor);
			help.setBounds(xinc, yinc, 20, 20);
			help.setName("help");
			help.setForeground(Color.white);
			help.setToolTipText(" Help ");
			add(help, null);
			help.addActionListener(this);
	*/		
			
		}
		yinc = yinc + 20;
		
	//	// ln("x1="+x1 + " y1="+yinc + "x2="+x2 + "y2="+y2);
		if(intraday)
		{
			macdgraph = new GraphComponentContainer(x1, yinc + 20, x2, y2, this.symbol,true);
			
		}
		else
		{
			macdgraph = new GraphComponentContainer(x1, yinc + 20, x2, y2, this.symbol);
			
		}

	
		macdgraph.setBounds(x1, yinc, x2, y2);
		//		JScrollPane scrollPane = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		//		scrollPane.getViewport().add( macdgraph );
		//		scrollPane.setBounds(x1,yinc,x2-10,y2);
		//		add( scrollPane, null);
		

		
		if(intraday)
			this.doIntraday();
		add(macdgraph, null);



	}
	public void resize(int x,int y)
	{
		this.x2 = x;
		this.y2 = y;
		macdgraph.updateSize(this.x1,this.y1, this.x2,this.y2);
//		this.remove(macdgraph);
//		this.add(macdgraph,null);
		macdgraph.setBounds(0,41, x2, y2);
		
		macdgraph.repaint();
		this.repaint();
	}
	public void setResolution(String selName)
	{
		int i = 0;
		
		if (selName.equalsIgnoreCase("Weekly"))
		{
			i = MainGraphComponent.WEEKLY;
		}
		else if (selName.equalsIgnoreCase("Monthly"))
		{
			i = MainGraphComponent.MONTHLY;
		}
		else
		{
			StringTokenizer st = new StringTokenizer(selName,"-");
			String s3 = st.nextToken();
			i= Integer.parseInt(s3);
		}
		macdgraph.setGraphResolution(i);
		this.repaint();
	}
	public void setCursorType(String selName)
	{
		macdgraph.setCursorType(selName);
		
	}
	public void setYear(String selName)
	{
	//	// ln("date="+date);
		if(!intraday)
		{
			String date = selName + "-01-01";

			if (selName.indexOf("Mon") != -1)
			{
				StringTokenizer st = new StringTokenizer(selName,"-");
				String month = st.nextElement()+"";
				Calendar c = Calendar.getInstance();
				c.add(Calendar.MONTH, -1 * Integer.parseInt(month));
				String ss = c.get(Calendar.YEAR)+"-" + ( c.get(Calendar.MONTH) + 1 ) +"-"+ c.get(Calendar.DATE);
				macdgraph.setDate(ss);
				
			}
			else
			{
				macdgraph.setDate(date);
				
			}

		}
		else
		{
			String date1 = (String)intradayDateBox.getSelectedItem();
			StringTokenizer st = new StringTokenizer(macdgraph.convert.startDate,"-");
			String s2 = st.nextToken();
			//			int dd = Integer.parseInt(st.nextToken());
//			int mm = Integer.parseInt(st.nextToken()) - 1 ;
//			int yy = Integer.parseInt(st.nextToken());
//			String s2 = mm +"/" +dd + "/" +yy;

			macdgraph.convert.startDate = s2 + "-" + yearBox.getSelectedItem();
			// ("macdgraph.convert.startDate="+macdgraph.convert.startDate);
//			macdgraph.setDate(selName);
		}	
		//		
	}
	public void setHistoricalIntradayDate(String date)
	{

		if(date.equalsIgnoreCase("today"))
		{
			macdgraph.convert.startDate = "today" + "-" + yearBox.getSelectedItem();
			macdgraph.histIntradayData = new Vector();

			return;
		}
		Vector v = new Vector();
		Calendar today = Calendar.getInstance();
		Calendar cal = Calendar.getInstance();
		StringTokenizer st = new StringTokenizer(date,"-");
		int dd = Integer.parseInt(st.nextToken());
		int mm = Integer.parseInt(st.nextToken()) - 1 ;
		int yy = Integer.parseInt(st.nextToken());
		
		cal.set(yy, mm,dd);
		Vector vtemp = new Vector();
		while(true)
		{
			if(!cal.after(today))
			{
				
				String s = (cal.get(Calendar.MONTH) + 1 ) +"-" + cal.get(Calendar.DATE) + "-" +cal.get(Calendar.YEAR);
				vtemp.addAll(Loader.getIntradayDataForDate(s,this.symbol));
				cal.add(Calendar.DATE, 1);
				
			}
			else
			{
				break;
			}
		}
		macdgraph.convert.startDate = ( mm  + 1) +"/" + dd + "/" + yy + "-" + yearBox.getSelectedItem();
		macdgraph.histIntradayData = vtemp;

	}
	public void setDate(int year,String startDate,String endDate)
	{
		setDate(year+"-"+startDate,year+"-"+endDate);
	}
	public void setDate(String startDate,String endDate)
	{
//		macdgraph.setDate(date,"2009-06-10");
		macdgraph.setDate(startDate,endDate);
		this.repaint();
	}
	public static void main(String[] args)
	{
		JFrame frame = new JFrame("Test angled lines");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		PanelForGraph d = new PanelForGraph(0, 0, 1300, 900, "BA");
		//d.macdgraph.writeImage();
		frame.getContentPane().add(d);
		frame.pack();
		//		frame.setBounds(0,0,1000,700);
		frame.setVisible(true);

	//	// ln(System.getProperty("user.dir"));
	//	// ln(System.getProperty("user.home"));
	//	// ln(System.getProperty("user.name"));

		
		

		}
	boolean wrongeventfire=false;	
	
	public void setNewSymbolIntraday(String name)
	{
		this.symbol = name;
/*			try
			{
				this.jContentPane.remove(this.macd);
			}
			catch (Exception ex)
			{
			}
	//		macd.macdgraph.destroyThread();
	/*		macd =  new PanelForGraph(0, 50, this.getWidth() - 200,this.getHeight()-50-50,name,true);
			macd.setBounds(201,50, this.getWidth(), this.getHeight() -50);
			jContentPane.add(macd,null);

			repaint();
	*/
			this.symbol = name;
			this.macdgraph.symbol=name;
			String selName = (String) this.intradayDateBox.getSelectedItem();
			this.setHistoricalIntradayDate(selName);
			this.macdgraph.setForIntraDay();
	}
	public void searchStockUpdate(String symbol)
	{

		if(macdgraph.convert.isintraday)
		{
			setNewSymbolIntraday(symbol);			
			return;
		}

	//	int ind = symbolVector.indexOf(symbol);
		wrongeventfire=true;
		this.yearBox.enable();

		//if (ind == -1)
		
		if (true==false)
				
		{
		//	symbolVector.addElement(symbol);
			
	//		symbollist.setSelectedItem(symbol);
	
			//			symbollist.addItem(symbol);
			
			macdgraph.setSymbol(symbol);
			if(macdgraph.symbol.equalsIgnoreCase(symbol))
			{
				this.symbol = symbol;
	//			symbollist.repaint();
				if(!intraday)
					MainScreen.getSingleton().doReSize();
			}
			
				//			}
		}
		else
		{
			
		//	symbollist.setSelectedItem(symbol);
			macdgraph.setSymbol(symbol);
			if(macdgraph.symbol.equalsIgnoreCase(symbol))
			{
				this.symbol = symbol;
				this.repaint();
				if(!intraday)
					MainScreen.getSingleton().doReSize();

			}
			
	
		}




	}
	public void actionPerformed(ActionEvent e)
	{
		Object o = (Object) e.getSource();
		if (o instanceof JComboBox)
		{
			JComboBox cb = (JComboBox) e.getSource();
			String name = cb.getName();
			cb.setSelectedIndex(cb.getSelectedIndex());

			if (name.equalsIgnoreCase("symbollist"))
			{
				String selName = (String) cb.getSelectedItem();
				cb.setSelectedIndex(cb.getSelectedIndex());
				this.yearBox.enable();
				if(wrongeventfire != true)
				{
					macdgraph.setSymbol(selName);
					
				}
				this.symbol = selName;
				wrongeventfire=false;
			}

			else if (name.equalsIgnoreCase("cursortype"))
			{
				String selName = (String) cb.getSelectedItem();
				cb.setSelectedIndex(cb.getSelectedIndex());
				setCursorType(selName);
			}
			else if (name.equalsIgnoreCase("graphtype"))
			{
				String selName = (String) cb.getSelectedItem();
				cb.setSelectedIndex(cb.getSelectedIndex());
				setGraphType(selName);
			}
			else if (name.equalsIgnoreCase("pivot"))
			{
				String selName = (String) cb.getSelectedItem();
				this.macdgraph.convert.pivotPointStyle = "pivot-"+selName;
			}
			
			else if (name.equalsIgnoreCase("graphresolution"))
			{
				String selName = (String) cb.getSelectedItem();
				cb.setSelectedIndex(cb.getSelectedIndex());
				setResolution(selName);
			}
			else if (name.equalsIgnoreCase("year"))
			{
				String selName = (String) cb.getSelectedItem();
				cb.setSelectedIndex(cb.getSelectedIndex());
				if(selName.equalsIgnoreCase("year"))
				{
					selName = (String)cb.getItemAt(1);
				}
//				String date =  selName + "-01-01";
				this.setYear(selName);
				
			}
			else if (name.equalsIgnoreCase("intradayDateBox"))
			{
				String selName = (String) cb.getSelectedItem();
				cb.setSelectedIndex(cb.getSelectedIndex());
				if(selName.equalsIgnoreCase("year"))
				{
					selName = (String)cb.getItemAt(1);
				}
//				String date =  selName + "-01-01";
				setHistoricalIntradayDate(selName);
				
			}
			
		}
		else if (o instanceof JButton)
		{
			JButton but = (JButton) o;
			if (but.getName().equals("addind"))
			{
				//TechIndicatorEntryPanel d = new TechIndicatorEntryPanel(this);
				ManageIndicatorsScreen m = new ManageIndicatorsScreen(this);
				
			}
			else if (but.getName().equals("compare"))
			{
				CompareStockScreen compare = new CompareStockScreen(this);
			}

			else if (but.getName().equals("updateproperty"))
			{
				SetDrawingComponentProperty d = new SetDrawingComponentProperty(this.macdgraph);
			}
			else if(but.getName().equalsIgnoreCase("expanddrag"))
						{

							macdgraph.doExpand(true);
					
						}

			else if (but.getName().equals("inbuiltstrategy"))
			{
			//	TechIndicatorRemovePanel d1 = new TechIndicatorRemovePanel(macdgraph);
				ManageStrategyScreen manage = new ManageStrategyScreen(this);
			}
			else if (":expand:shrink:prev:next:resetzoom:".indexOf(but.getName().toLowerCase()) != -1 )
			{
				setZooming(but.getName().toLowerCase());
			}
			else if (but.getName().equals("showfinancial"))
			{
		//		financialgra = new FinancialGraphical(100, 50, 100 + 600, 50 + 400,macdgraph.symbol, MYDimension.FINANCIALDATA);

				financial = new Financial(100, 50, 100 + 400, 50 + 200,macdgraph.symbol, MYDimension.FINANCIALDATA);
				macdgraph.addComponent(financial);
				macdgraph.repaint();
//				if (!showFinancial)
//				{
	//				showFinancial = true;
//				}
	/*			else
				{
					if (financial != null)
					{
						macdgraph.removeComponent(financial);
					}
					showFinancial = false;
				}
	*/
			}
			else if (but.getName().equals("find"))
			{
				SearchStock d = new SearchStock(this);
//				StrategyScreen d = new StrategyScreen(this);

			}
			else if (but.getName().equals("buysell"))
			{
				if(Utility.isBasicUser())
					return;

//				SearchStock d = new SearchStock(this);
				FileDialog fd = new FileDialog(new Frame(),"Open Formula", FileDialog.LOAD);
				fd.setFile("*.fl");
				fd.setDirectory(StockConstants.FORMULA_DIR);
				fd.setLocation(50, 50);
				fd.show();
			
				String fileName1 = fd.getFile();
				String dirName1 = fd.getDirectory();
				if(fileName1==null || dirName1==null)
					return;
				String s = getFormula(dirName1, fileName1);

				checkFormulaForStrategy(fileName1, s);

//old code				
//				FormulaEditorScreen formulaeditorscreen = new FormulaEditorScreen(this);

			}
			else if (but.getName().equals("save"))
			{
				//macdgraph.saveGraph();
				FileDialog fd = new FileDialog(new Frame(), "Save Graph As Image", FileDialog.SAVE);
				fd.setFile("*.jpg");
				fd.setDirectory(StockConstants.IMG_DIR);
				fd.setLocation(50, 50);
				fd.show();

				String fileName = fd.getFile();
				String dirName = fd.getDirectory();
				if (fileName != null && !fileName.equals("") && dirName != null && !dirName.equals(""))
				{

				macdgraph.writeImage(dirName + "/" + fileName);
				}
			}
			else if (but.getName().equals("intraday"))
			{
				if(!Utility.isIntraDayUser())
				{
					return;
				}
				
				
				if(YahooLogin.yahooInfo==null && StockConstants.IntradayDataSource.equalsIgnoreCase(StockConstants.YAHOOSOURCE))
				{
					YahooLogin.loadInfo();
					if(YahooLogin.yahooInfo==null)
					{
						MessageDiaglog msg = new MessageDiaglog("Pls setup your yahoo crediential.");
						return;
					}
				}

				PanelForGraphFrame frame = new PanelForGraphFrame(macdgraph.symbol,true);
			}
			else if (but.getName().equals("future"))
			{
				macdgraph.convert.chartResolution=1;
				this.yearBox.disable();
				macdgraph.setForIntraDayFuture((String)expiryDateCombo.getSelectedItem());
				macdgraph.convert.isintraday=true;

			}
			else if (but.getName().equals("futurehis"))
			{
				Demo demo = new Demo(this);
				demo.show();			
				
			}
			else if (but.getName().equals("addnote"))
			{
//				AddNotePanel addpanel = new AddNotePanel(this);
				FibonnciRetracement fibret = new FibonnciRetracement(100,100,300,300);
				macdgraph.addComponent(fibret);

			}
			else if (but.getName().equals("template"))
			{
				this.applyTemplate();
			}

			else if (but.getName().equals("line"))
			{
				this.setDrawLine(true);
			}
			else if (but.getName().equals("drawingtool"))
			{
//				DrawingToolPanel d = new DrawingToolPanel(this);
				
			}
			else if (but.getName().equals("deletebuysell"))
			{
				
				macdgraph.strategyVector = new Vector();
				macdgraph.removeVariables();
				macdgraph.repaint();				
			}
			else if (but.getName().equals("portfolio"))
			{
				BuySellStockScreen buysellscreen = new BuySellStockScreen(this.symbol,null);			
			}
			
			if (but.getName().equals("backtesting"))
			{
				// Vector v =
				// ((PanelForGraph)maingraph).macdgraph.stratetgyOutput;
				Vector v = macdgraph.getStrategyVector();
				if (v == null || v.size()==0)
				{
					MessageDiaglog msg = new MessageDiaglog("Please execute strategy ");
					return;
				}
				HashMap hs = BackTestingParameterScreen.dataHash;
				if (hs == null)
				{
					
					BackTestingParameterScreen backtestingscreen = new BackTestingParameterScreen(this);
				}
				else
				{
					executeBackTesting();
				}

			}

			else if (but.getName().equals("custom"))
			{
				if(Utility.isBasicUser())
					return;
//				CustmizedIndicatorBuilderScreen custom = new CustmizedIndicatorBuilderScreen(this);
					FileDialog fd = new FileDialog(new Frame(),"Open Indicator", FileDialog.LOAD);
					fd.setFile("*.fl");
					fd.setDirectory(StockConstants.IND_DIR);
					fd.setLocation(50, 50);
					fd.show();
				
					String fileName1 = fd.getFile();
					String dirName1 = fd.getDirectory();
					
					String s = getFormula(dirName1, fileName1);

					checkFormulaForCustmizedIndicator(fileName1, s);
//					this.executeCustmizedIndicator(fileName1,s);

			}
			else if(but.getName().equals("help"))
			{
				try
				{
					Utility.openHelpLink("panel");
				}
				catch (Exception e1)
				{
				}

			}
			else if (drawingcom.indexOf(but.getName()) != -1)
			{
				doDrawingComponent(but.getName());
			}

			
			
		}
	}
	
	public void executeBackTesting()
	{
		Vector v = macdgraph.getStrategyVector();
		if (BackTestingParameterScreen.dataHash == null)
			BackTestingParameterScreen.loadParameter();
		HashMap hs = BackTestingParameterScreen.dataHash;
		if (hs == null)
		{
			
			return;
		}
		BackTesting backtesting = new BackTesting();

		String content = backtesting.doBackTesting(BackTestingParameterScreen.buyinper, BackTestingParameterScreen.sellinper,
				BackTestingParameterScreen.initialamt, BackTestingParameterScreen.tranamtf, BackTestingParameterScreen.trantypestr, v);
		ShowContentWindow showcontent = new ShowContentWindow(content);
	
	}
	public static 	String drawingcom = "line:horline:verline:fibret:fibhor:buyarrow:sellarrow:addnote:gannfan";

	public void applyCustmizedIndicator(ManageIndicatorInterface manageind,String dirName,String fileName)
	{
		this.addindicatorflag=true;
		this.manageindicator = manageind;
		String s = getFormula(dirName, fileName);

		checkFormulaForCustmizedIndicator(fileName, s);

	}
	public void checkFormulaForCustmizedIndicator(String fileName,String formula )
	{
		try
		{
			
//		formula = formula.toUpperCase();
		StringTokenizer st = new StringTokenizer(formula,"\n");
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
			if(v.size()==0)
			{
				this.executeCustmizedIndicator(fileName,formula.toUpperCase());
				this.addindicatorflag=false;
			}
			else
			{
				CustmizedIndicatorPanel panel = new CustmizedIndicatorPanel(fileName,formula,v,this);
			}
		}
		else
		{
			this.executeCustmizedIndicator(fileName,formula);
			this.addindicatorflag=false;
		}
		}
		catch(Exception e)
		{
			this.addindicatorflag=false;
		}
	}	
	public void checkFormulaForStrategy(String fileName,String formula )
	{
		formula = formula.toUpperCase();
		StringTokenizer st = new StringTokenizer(formula,"\n");
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
			if(v.size()==0)
			{
				HashMap hs1 = new HashMap();
				hs1.put(StrategyUtility.Formula, formula);
				
				hs1.put(StrategyUtility.FormulaName, fileName);
				hs1.put(StrategyUtility.Type, "B");
				Vector newVector = new Vector();
				newVector.add(hs1);

				// ((PanelForGraph)maingraph).macdgraph.drawStrategy(newVector);
				macdgraph.drawStrategy(newVector);
				
			}
			else
			{
				CustmizedIndicatorPanel panel = new CustmizedIndicatorPanel(fileName,formula,v,this,CustmizedIndicatorPanel.STRATEGY);
			}
		}
		else
		{
			HashMap hs1 = new HashMap();
			hs1.put(StrategyUtility.Formula, formula);
			
			hs1.put(StrategyUtility.FormulaName, fileName);
			hs1.put(StrategyUtility.Type, "B");
			Vector newVector = new Vector();
			newVector.add(hs1);

			// ((PanelForGraph)maingraph).macdgraph.drawStrategy(newVector);
			macdgraph.drawStrategy(newVector);
			
		}
	}	
	public Vector getAllAppliedStrategies()
	{
		Vector v = new Vector();
		for (int i=0;i<macdgraph.strategyVector.size();i++)
		{
			HashMap hs = (HashMap)macdgraph.strategyVector.get(i);
			v.add(hs.get(StrategyUtility.FormulaName)+"");
		}
		return v;
	}
	public void removeStrategy(String strategyName)
	{
		Vector v = new Vector();
		for (int i=0;i<macdgraph.strategyVector.size();i++)
		{
			HashMap hs = (HashMap)macdgraph.strategyVector.get(i);
			String name = (String)hs.get(StrategyUtility.FormulaName);
			if(!name.equals(strategyName))
			{	
				v.add(hs);
			}	
		}
		macdgraph.strategyVector = new Vector();
		macdgraph.removeVariables();
		macdgraph.drawStrategy(v);
		macdgraph.repaint();
	}

	/**
	 * @param string
	 */
	public void doIntraday()
	{
		if(Utility.isBasicUser())
			return;
		try
		{
			remove(graphresolutionbut);
		}
		catch(Exception ee)
		{
			
		}
//		xposforcombo ;
		Vector graphresolution = Utility.getGraphResolutionsForIntraday();
		graphresolutionbut = new JComboBox(graphresolution);
		
		graphresolutionbut.setFont(new Font("Arial", Font.BOLD, 9));
		graphresolutionbut.setToolTipText(" Select Graph Resolution ");
		graphresolutionbut.setName("graphresolution");
		graphresolutionbut.setBounds(xposforcombo, 1,75, 20);
		add(graphresolutionbut, null);
		graphresolutionbut.addActionListener(this);
		macdgraph.convert.chartResolution=1;
//		this.yearBox.disable();
		macdgraph.setForIntraDay();
		macdgraph.convert.isintraday=true;
		macdgraph.repaint();
		this.repaint();
		if(!intraday)
			MainScreen.getSingleton().doReSize();

	}	
	
	public void setZooming(String but)
	{
		if (but.equals("expand"))
		{
			macdgraph.doZoomOut();
		}
		else if (but.equals("shrink"))
		{
			macdgraph.doZoomIn();
		}
		else if (but.equals("prev"))
		{
			macdgraph.doLeft();
		}
		else if (but.equals("next"))
		{
			macdgraph.doRight();
		}
		else if (but.equals("resetzoom"))
		{
			macdgraph.doResetZoom();
		}		
	}
	/**
	 * @param selName
	 */
	public void setGraphType(String selName)
	{
		int i = 0;
		if (selName.equalsIgnoreCase("Line"))
		{
			i = MainGraphComponent.LINEGRAPH;
		}
		else if (selName.equalsIgnoreCase("OHLC"))
		{
			i = MainGraphComponent.OHLCGRAPH;
		}
		else if (selName.equalsIgnoreCase("Candle"))
		{
			i = MainGraphComponent.CANDLEGRAPH;
		}
		macdgraph.setGraphDisplay(i);
		
	}
	public String getGraphType(int i)
	{
		if (i==MainGraphComponent.LINEGRAPH)
		{
			return "Line";
		}
		else if (i==MainGraphComponent.OHLCGRAPH)
		{
			return "OHLC" ;
		}
		else if (i==MainGraphComponent.CANDLEGRAPH)
		{
			return "Candle" ;
		}
		else 
		{
			return "Line";
		}
	}
	public String getFormula(String dirName1,String fileName1)
	{
		String formulaFileName="";
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
		//		e.printStackTrace();
			}
		return formulaFileName;
	}

	boolean showFinancial = false;
	Financial financial = null;
	FinancialGraphical financialgra = null;

	/**
	 * @param string
	 */
	public void addNote(String string)
	{
		Note note = new Note(x1 + 50, y1 + 50, string,this.macdgraph);
		macdgraph.addComponent(note);
		macdgraph.repaint();
	}




	/**
	 * @param b
	 */
	public void setDrawLine(boolean b)
	{
		macdgraph.doPaintLine(true);
		// TODO Auto-generated method stub
	}
	/**
	 * @param i
	 */
	public void setCursorType(int i)
	{
		macdgraph.setCursorType(i);
		// TODO Auto-generated method stub
	}
	public boolean addindicatorflag=false;
	public ManageIndicatorInterface manageindicator=null;
	public void executeCustmizedIndicator(String formulaname,String formula)
	{
		try
		{
			formulaname = formulaname.substring(0,formulaname.indexOf(".fl"));
		}
		catch(Exception e)
		{
			
		}		
		if(manageindicator==null || manageindicator.getIndiactorType()== ManageIndicatorInterface.INDICATOR)

		{
			addCustomIndicatorFinalCall(formulaname, formula);
			
		}
		else if (manageindicator.getIndiactorType()== ManageIndicatorInterface.STRATEGY)
		{
			addCustomStrategyFinalCall(formulaname, formula);
					
		}
		
	}
	public void addCustomIndicatorFinalCall(String formulaname,String formula)
	{
		Indicator id = new Indicator(formulaname,0,Indicator.CUSTOM);
		try
		{
			BuySellStrategy buysell = new BuySellStrategy();
			
			String[] rsiStr = buysell.getValueForDataForCustmizedInd(this.macdgraph.convert.inputdata,formula);
			
			float[] rsi = new float[rsiStr.length];
			for(int i=0;i<rsi.length;i++)
			{
				rsi[i] = Float.parseFloat(rsiStr[i]);
			}
			
		}
		catch(Exception e)
		{
			MessageDiaglog msg = new MessageDiaglog("Exception Occured , Indicator not added");
			this.addindicatorflag=false;
			return;
		}

		id.query = formula;
		if(formulaname==null || formulaname.trim().equalsIgnoreCase(""))
		{
			formulaname = "TEMP#"+StockConstants.COUNTER +"  ";
			StockConstants.COUNTER++;
			
		}
		id.indicatorName = formulaname;
		macdgraph.addIndicator(id);	
		if(this.addindicatorflag)
		{
			this.addindicatorflag=false;
			this.manageindicator.addCustomIndicator();
			
		}
	
	}
	
	public void addCustomStrategyFinalCall(String formulaname,String formula)
	{

		if(formulaname==null || formulaname.trim().equalsIgnoreCase(""))
		{
			formulaname = "TEMPSTRATEGY#"+StockConstants.COUNTER +"  ";
			StockConstants.COUNTER++;
			
		}

		HashMap hs1 = new HashMap();
		hs1.put(StrategyUtility.FormulaName, formulaname);
		hs1.put(StrategyUtility.Formula, formula);
		
		hs1.put(StrategyUtility.Type, "B");
		Vector newVector = new Vector();
		newVector.add(hs1);

		// ((PanelForGraph)maingraph).macdgraph.drawStrategy(newVector);
		macdgraph.drawStrategy(newVector);
		if(this.addindicatorflag)
		{
			this.addindicatorflag=false;
			this.manageindicator.addCustomIndicator();
			
		}
	
	}

	
	/* (non-Javadoc)
	 * @see com.stockfaxforu.frontend.PanelForGraphImpl#destroyThread()
	 */
	public void destroyThread()
	{
		this.macdgraph.destroyThread();

		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see com.stockfaxforu.frontend.PanelForGraphImpl#drawStrategy(java.util.Vector)
	 */
	
	public HashMap getIndicators(Vector strategyVector)
	{
		String ind ="@INDICATOR";
		HashMap outHash = new HashMap();
		for(int i=0;i<strategyVector.size();i++)
		{
			HashMap hs = (HashMap)strategyVector.get(i);
			String formula = (String)hs.get(StrategyUtility.Formula);
			StringTokenizer st = new StringTokenizer(formula,"\n");
			while(st.hasMoreTokens())
			{
				String s = st.nextToken();
				if(s.trim().startsWith(ind))
				{
					StringTokenizer st1 = new StringTokenizer(s,"|");
					String name = st1.nextToken().substring(ind.length());
					String value = st1.nextToken();
					
					outHash.put(name,value);
					
					
				}
			}
		}
		return outHash;
	}
	public void drawStrategy(Vector newVector)
	{
		this.macdgraph.drawStrategy(newVector);		
		HashMap hs = getIndicators(newVector);
		for(Iterator it = hs.keySet().iterator();it.hasNext();)
		{
			String key = (String)it.next();
			String value =(String)hs.get(key);
			
			 executeCustmizedIndicator(key,value);
		}

		
		
	}
	/* (non-Javadoc)
	 * @see com.stockfaxforu.frontend.PanelForGraphImpl#getStrategyHashMap()
	 */
	public HashMap getStrategyHashMap()
	{
		int size = this.macdgraph.stratetgyOutput.size() -1;
		HashMap outputHash = (HashMap)this.macdgraph.stratetgyOutput.get(size);
		return outputHash;
	}
	/* (non-Javadoc)
	 * @see com.stockfaxforu.frontend.PanelForGraphImpl#getStrategyVector()
	 */
	public Vector getStrategyVector()
	{
		return this.macdgraph.stratetgyOutput;
	}
	/* (non-Javadoc)
	 * @see com.stockfaxforu.frontend.PanelForGraphImpl#getInputVector()
	 */
	public Vector getInputVector()
	{
		return this.macdgraph.convert.inputdata;
	}
	/* (non-Javadoc)
	 * @see com.stockfaxforu.frontend.PanelForGraphImpl#addIndicator(com.stockfaxforu.component.Indicator)
	 */
	public void addIndicator(Indicator selInd)
	{
		this.macdgraph.addIndicator(selInd);
			
	}
	public void applyTemplate()
	{
		FileDialog fd = new FileDialog(new Frame(),"Open Template", FileDialog.LOAD);
		fd.setFile("*.template");
		fd.setDirectory(StockConstants.TEMPLATE_DIR);
		fd.setLocation(50, 50);
		fd.show();
		
		String fileName = fd.getFile();
		String dirName = fd.getDirectory();
		if(fileName != null && !fileName.equals("") && dirName != null && !dirName.equals(""))
		{
			File f = new File(dirName + "/" + fileName);
			try
			{
				FileInputStream file = new FileInputStream(f);
				ObjectInputStream obj = new ObjectInputStream(file);
				HashMap hs = (HashMap)obj.readObject();

				HashMap indHash = (HashMap)hs.get(PanelForGraph.INDVECTROR);
				Vector  strVec = (Vector)hs.get(PanelForGraph.STRVECTROR);
				
				for(Iterator it = this.macdgraph.indHash.keySet().iterator();it.hasNext();)
				{
					Object o = it.next();
					Indicator ind = (Indicator)this.macdgraph.indHash.get(o);
					this.macdgraph.removeIndicator(ind);
				}
				for(Iterator it = indHash.keySet().iterator();it.hasNext();)
				{
					Object o = it.next();
					Indicator ind = (Indicator)indHash.get(o);
					this.macdgraph.addIndicator(ind);
				}
				
				this.macdgraph.strategyVector = strVec;
//				this.macdgraph.indHash = indHash;
				this.macdgraph.updateDimension();
//				addComponentVector(v3);
//				updateDimension();
				this.macdgraph.drawStrategy();
//				this.macdgraph.updateDimension();
				
				obj.close();
				file.close();
			}
			catch(Exception e1)
			{
				e1.printStackTrace();
			}
			
		}

		
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void mouseClicked(MouseEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}
	public void mouseEntered(MouseEvent e)
	{
		// TODO Auto-generated method stub
		
	}
	public void mouseExited(MouseEvent e)
	{
		// TODO Auto-generated method stub
		
	}
	public void mousePressed(MouseEvent e)
	{
		// ln("mousePressed");
		JButton but = (JButton) e.getSource();
		if (but.getName().equals("prev"))
		{
			macdgraph.doLeft();
			
		}
		else if (but.getName().equals("next"))
		{
			macdgraph.doRight();
			
		}

		// TODO Auto-generated method stub
		
	}
	public void mouseReleased(MouseEvent e)
	{
		// TODO Auto-generated method stub
		
	}
}

/*
 * Created on Sep 8, 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.frontend;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Label;
import java.awt.LayoutManager;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.peer.ComponentPeer;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.tree.DefaultMutableTreeNode; //import com.mahesh.sensex.IndexAndShareInfo;
//import com.mahesh.sensex.Result;
//import com.mahesh.sensex.ShareAnnoucement;
import com.stockfaxforu.backtesting.BackTestingParameterScreen;
import com.stockfaxforu.buyselladvisior.BuySellAdvisorScreen;
import com.stockfaxforu.component.*;
import com.stockfaxforu.config.ConfigUtility;
import com.stockfaxforu.config.ConfigurePropertyScreen;
import com.stockfaxforu.config.SettingEditorScreen;
import com.stockfaxforu.config.YahooLogin;
import com.stockfaxforu.dataextractor.DataExtractorScreen;
import com.stockfaxforu.favourite.AddRemoveStock;
import com.stockfaxforu.favourite.NewFavouriteCategoryEntry;
import com.stockfaxforu.formulabuilder.FormulaBuilderScreen;
import com.stockfaxforu.formulabuilder.MessageDiaglog;
import com.stockfaxforu.getolddataforu.loader.Loader;
import com.stockfaxforu.getolddataforu.thread.IndexThread;
import com.stockfaxforu.getolddataforu.thread.NewsThread;
import com.stockfaxforu.getolddataforu.thread.UpdateThread;
import com.stockfaxforu.livemarket.ShowLiveMarket;
import com.stockfaxforu.livemarket.YahooMail; //import com.stockfaxforu.purelive.ShowLiveMarketPure;
import com.stockfaxforu.optimize.OptimizeScreen;
import com.stockfaxforu.scan.frontend.CompareStrategy;
import com.stockfaxforu.scan.frontend.MarketSnapShot;
import com.stockfaxforu.scan.frontend.SelectStockScanner;
import com.stockfaxforu.scan.frontend.SelectStockScannerNew;
import com.stockfaxforu.scan.frontend.StockFilter;
import com.stockfaxforu.trading.ShowTradingScreen;
import com.stockfaxforu.util.*;

//import com.mahesh.util.Utility;
/**
 * @author sdeopu1
 * 
 *         To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class MainScreen extends JFrame implements ActionListener, MouseMotionListener, ComponentListener, KeyListener
{
	private AdPanel editorPane = null;
	public javax.swing.JPanel jContentPane = null;
	public static MainScreen mainscreen = null;
	public JPanel panel = null;
	public JTextArea message = null;
	public NewsPanel news = null;
	public static int YPOSITIONFORGRAPH = 60;
	public static int YPOSITIONFORGRAPHGAP = 75;
	public StockTree treepanel;

	public static MainScreen getSingleton()
	{
		if (mainscreen == null)
		{
			mainscreen = new MainScreen();
		}
		else
		{
		}
		mainscreen.show();
		return mainscreen;
	}

	public MainScreen()
	{
		super();
		initialize();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addComponentListener(this);
		this.addMouseMotionListener(this);
		
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	public JMenuBar createMenuBar()
	{
		JMenuBar menuBar;
		JMenu menu, submenu;
		JMenuItem menuItem;
		// Create the menu bar.
		menuBar = new JMenuBar();
		// Build the first menu.
		menu = new JMenu("File");
		menuBar.add(menu);
		// a group of JMenuItems
		menuItem = new JMenuItem("Set up Backtesting Parameter");
	//	menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, ActionEvent.ALT_MASK));
		menuItem.addActionListener(this);
		menuItem.setToolTipText("Set up backtesting parameter");
		menu.add(menuItem);
		// menuItem = new JMenuItem("Add/Update Show Indicator List",
		// KeyEvent.VK_A);
		// menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,
		// ActionEvent.ALT_MASK));
		// menuItem.addActionListener(this);
		// menu.add(menuItem);
		menuItem = new JMenuItem("Add/Delete New Category");
		//menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.ALT_MASK));
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menuItem = new JMenuItem("Add/Update Stock to Category");
//		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.ALT_MASK));
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menuItem = new JMenuItem("Save Graph as Template");
//		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.ALT_MASK));
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menu = new JMenu("Download");
//		menuBar.add(menu);

		/*
		menuItem = new JMenuItem("Download data");
		menuItem.setName("download");
//		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.ALT_MASK));
		menuItem.addActionListener(this);
		menuItem.setToolTipText("Use this option to download and cache data in memory start from screen date parameter specified in configuration");
		menu.add(menuItem);
*/
		
/*
		menuItem = new JMenuItem("Download Financial data");
		menuItem.setName("downloadfinancialdata");
		
	//	menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.ALT_MASK));
		menuItem.addActionListener(this);
		menuItem.setToolTipText("Download financial data for making financial query and for displaying financial info on graph.");

		menu.add(menuItem);

		menuItem = new JMenuItem("Download EOD data");
		menuItem.setName("downloadeoddata");

		
//		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.ALT_MASK));
		menuItem.addActionListener(this);
		menu.add(menuItem);
*/
		
		
/*	
		menuItem = new JMenuItem("Manage Split/Bonus");
		menuItem.setName("managesplitbonus");

		menuItem.setToolTipText("Use this if the displayed stock is having a split, this will download data from 2007 , Not click when stock is having no split.");
//		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.ALT_MASK));
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menuItem = new JMenuItem("Download Data For New Symbol");
		menuItem.setName("downloaddatafornewsymbol");
//		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.ALT_MASK));
		menuItem.addActionListener(this);
		menuItem.setToolTipText("Will download data for symbol which is not in current symbol list. ");

		menu.add(menuItem);
*/

		menu = new JMenu("View");
		menuBar.add(menu);
		// a group of JMenuItems
		menuItem = new JMenuItem("Open new screen");
		menuItem.setToolTipText("Will open new window for stock. ");

//		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menuItem = new JMenuItem("Compare Selected Period ");
		menuItem.setToolTipText("To compare a period for multiple year use this option.");

//		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.ALT_MASK));
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menuItem = new JMenuItem("Single Panel View");
		menuItem.setName("singlepane");

//		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.ALT_MASK));
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menuItem = new JMenuItem("Vertical Panel View");
		menuItem.setName("verdiv");

//		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.ALT_MASK));
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menuItem = new JMenuItem("Horizontal Panel View");
		menuItem.setName("hordiv");

//		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.ALT_MASK));
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menuItem = new JMenuItem("Four Panel View");
		menuItem.setName("fourpanel");
//		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.ALT_MASK));
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Four Panel Intraday View");
		menuItem.setName("fourpanelintra");
//		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.ALT_MASK));
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Two Panel Intraday View");
		menuItem.setName("twopanelintra");
//		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.ALT_MASK));
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
		

		menuItem = new JMenuItem("Panel With Formula window");
		menuItem.setName("hordivformula");
		
//		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.ALT_MASK));
		menuItem.addActionListener(this);
		menu.add(menuItem);

/*		menuItem = new JMenuItem("IGS Analysis Panel");
		menuItem.setName("chartigs");

//		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.ALT_MASK));
		menuItem.addActionListener(this);
		menu.add(menuItem);
*/
		// configure
		menu = new JMenu("Configure");
		menuBar.add(menu);
		menuItem = new JMenuItem("Proxy Setup");
//		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
		menuItem.addActionListener(this);
		menu.add(menuItem);

		/*
		 * menuItem = new JMenuItem("Change Country", KeyEvent.VK_C);
		 * menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,
		 * ActionEvent.CTRL_MASK)); menuItem.addActionListener(this);
		 * menu.add(menuItem);
		 */
		menuItem = new JMenuItem("Settings");
		menuItem.setToolTipText("Use this to configure graph,changing data source,time setting,screen date,start date change.");

//		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));
		menuItem.addActionListener(this);
		menu.add(menuItem);

	/*	menuItem = new JMenuItem("Login to Yahoo");
		menuItem.setToolTipText("To get live data from yahoo first you need to login to yahoo using this option");

//		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, ActionEvent.CTRL_MASK));
		menuItem.addActionListener(this);
		menu.add(menuItem);
*/
		// configure
		menu = new JMenu("Tools");
		menuBar.add(menu);
		menuItem = new JMenuItem("Find Stock");
		menuItem.setName("find");
//		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
		menuItem.setToolTipText("Search stock by symbol or name");

		menuItem.addActionListener(this);
		menu.add(menuItem);

		/*
		 * menuItem = new JMenuItem("Change Country", KeyEvent.VK_C);
		 * menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,
		 * ActionEvent.CTRL_MASK)); menuItem.addActionListener(this);
		 * menu.add(menuItem);
		 */
		menuItem = new JMenuItem("Stock Filter");
		menuItem.setName("query");

//		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menuItem.setToolTipText("Use this to write your exploration  to scan . IGS SQL language is used to scanning stock.");

/*		
		menuItem = new JMenuItem("Live Nifty");
		menuItem.setName("liveniftyquotepanel");
//		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, ActionEvent.CTRL_MASK));
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menuItem.setToolTipText("Live Nifty stocks");
*/
		menuItem = new JMenuItem("Porfolio");
		menuItem.setName("portfolio");
		menuItem.setToolTipText("Manage your portfolio");

		
//		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, ActionEvent.CTRL_MASK));
		menuItem.addActionListener(this);
		menu.add(menuItem);
/*
		menuItem = new JMenuItem("Bhav Copy Analysis");
		menuItem.setName("marketsnapshot");
		menuItem.setToolTipText("How Market performed yesterday");
//		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, ActionEvent.CTRL_MASK));
		menuItem.addActionListener(this);
		menu.add(menuItem);
*/
/*
		menuItem = new JMenuItem("Market Snapshot(Current)");
		menuItem.setName("livemarketsnapshot");
		menuItem.setToolTipText("How Market is performing");
//		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, ActionEvent.CTRL_MASK));
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
*/		
		


/*		menuItem = new JMenuItem("Buy/Sell Screener");
		menuItem.setName("runstockscreener");
*/
//		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, ActionEvent.CTRL_MASK));
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menuItem = new JMenuItem("Formula Editor");
		menuItem.setName("formula");
		menuItem.setToolTipText("Editor for writing formula");

		
//		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, ActionEvent.CTRL_MASK));
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menuItem = new JMenuItem("Portfolio Backtesting");
		menuItem.setName("strategycompare");
		menuItem.setToolTipText("Compare your strategies on a portfolio and find out which is best");

		
//		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, ActionEvent.CTRL_MASK));
		menuItem.addActionListener(this);
		menu.add(menuItem);


		menuItem = new JMenuItem("Optimization");
		menuItem.setName("optimization");
		menuItem.setToolTipText("Optimize your formula");

//		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, ActionEvent.CTRL_MASK));
		menuItem.addActionListener(this);
		menu.add(menuItem);


		menuItem = new JMenuItem("Intraday");
		menuItem.setName("intraday");
//		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, ActionEvent.CTRL_MASK));
		menuItem.addActionListener(this);
		menuItem.setToolTipText("Live Intraday charts  ");

		menu.add(menuItem);

/*
		menuItem = new JMenuItem("Intraday Scan/Live Quote");
		menuItem.setName("intradayscan");
//		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, ActionEvent.CTRL_MASK));
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menuItem.setToolTipText("Live Intraday scan for Nifty stocks ");

*/
		menuItem = new JMenuItem("Buy/Sell Advisor");
		menuItem.setName("buyselladvisor");
//		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, ActionEvent.CTRL_MASK));
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menuItem.setToolTipText("Buy/Sell Advisor with Inbuilt scanner  ");
		
		menuItem = new JMenuItem("Manage Indicator");
		menuItem.setName("addind");
//		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, ActionEvent.CTRL_MASK));
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menuItem.setToolTipText("Add/Remove Indicator   ");

		
		menuItem = new JMenuItem("Apply Inbuilt Strategies");
		menuItem.setName("inbuiltstrategy");
//		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, ActionEvent.CTRL_MASK));
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menuItem.setToolTipText("Apply Inbuilt Strategies   ");
	
		
		
		
		menu = new JMenu("Info");
//		menuBar.add(menu);
		// a group of JMenuItems
		menuItem = new JMenuItem("Company Info");
//		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.ALT_MASK));
		menuItem.addActionListener(this);
		menu.add(menuItem);
/*		menuItem = new JMenuItem("Board Meeting");
//		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, ActionEvent.ALT_MASK));
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menuItem = new JMenuItem("Result");
//		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.ALT_MASK));
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menuItem = new JMenuItem("Result Compare");
//		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.ALT_MASK));
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menuItem = new JMenuItem("Share Holding");
//		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.ALT_MASK));
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menuItem = new JMenuItem("Quote");
//		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.ALT_MASK));
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menuItem = new JMenuItem("Company Address");
//		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.ALT_MASK));
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menuItem = new JMenuItem("Corporate Action");
//		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.ALT_MASK));
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menuItem = new JMenuItem("Annual Report");
//		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.ALT_MASK));
		menuItem.addActionListener(this);
		menu.add(menuItem);
*/
		menu = new JMenu("Board Meeting");
//		menuBar.add(menu);
/*		menuItem = new JMenuItem("ForthComing");
//		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.ALT_MASK));
		menuItem.addActionListener(this);
		menu.add(menuItem);
*/
		menuItem = new JMenuItem("Today");
//		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, ActionEvent.ALT_MASK));
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menuItem = new JMenuItem("Tomorrow");
//		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, ActionEvent.ALT_MASK));
		menuItem.addActionListener(this);
		menu.add(menuItem);
/*
		menuItem = new JMenuItem("Latest");
//		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.ALT_MASK));
		menuItem.addActionListener(this);
		menu.add(menuItem);
*/
		menuItem = new JMenuItem("Block Deal");
//		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.ALT_MASK));
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menu = new JMenu("Indexes");
//		menuBar.add(menu);
		// a group of JMenuItems
/*		
		menuItem = new JMenuItem("BSE 30");
//		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.ALT_MASK));
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menuItem = new JMenuItem("BSE 100");
//		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.ALT_MASK));
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menuItem = new JMenuItem("BSE 200");
//		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_J, ActionEvent.ALT_MASK));
		menuItem.addActionListener(this);
		menu.add(menuItem);
*/
		menuItem = new JMenuItem("Nifty");
//		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.ALT_MASK));
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menuItem = new JMenuItem("Nifty Junior");
//		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, ActionEvent.ALT_MASK));
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menuItem = new JMenuItem("CNX Midcap");
//		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));
		menuItem.addActionListener(this);
		menu.add(menuItem);
	
/*		menuItem = new JMenuItem("INDICES");
//		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));
		menuItem.addActionListener(this);
		menu.add(menuItem);
*/
		// Help
		menu = new JMenu("Help");
		menuBar.add(menu);
		menuItem = new JMenuItem("User Info");
//		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, ActionEvent.CTRL_MASK));
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menuItem = new JMenuItem("Help");
//		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menuItem = new JMenuItem("FAQ");
//		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.CTRL_MASK));
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menuItem = new JMenuItem("IGS Formula Language");
//		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.CTRL_MASK));
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menuItem = new JMenuItem("Formula Charter");
//		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menuItem = new JMenuItem("IGS SQL Language");
//		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, ActionEvent.CTRL_MASK));
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menuItem = new JMenuItem("Version");
//		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
		menuItem.addActionListener(this);
		menu.add(menuItem);
		menuItem = new JMenuItem("About iGuideStocks...");
//		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menu.add(menuItem);
		menuItem = new JMenuItem("Terms & Condition");
//		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));
		menuItem.addActionListener(this);
		menu.add(menuItem);

		// menuItem = new JMenuItem("About", KeyEvent.VK_T);
		// menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,
		// ActionEvent.CTRL_MASK));
		// menuItem.addActionListener(this);
		// menu.add(menuItem);
		return menuBar;
	}

	public void initialize()
	{
		this.setSize(StockConstants.length, StockConstants.height);
		// Image im =
		// Toolkit.getDefaultToolkit().getImage("image/toolicon.jpg");
		Image im = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("image/toolicon.jpg"));
		this.setIconImage(im);
		this.setContentPane(getJContentPane());
		this.setJMenuBar(createMenuBar());
		this.setVisible(true);
		this.setTitle("Chart Alive for user " + StockConstants.USERDETAIL.getProperty("uid"));
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.addWindowListener(new java.awt.event.WindowAdapter()
		{
			public void windowClosing(java.awt.event.WindowEvent e)
			{
				// // ln("windowClosing()");
				// TODO Auto-generated stub windowClosing()
			}
		});
		UpdateThread newsthread = new UpdateThread();
		Thread th = new Thread(newsthread);
		th.start();

	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	JButton nextStock = null;
	JButton prevStock = null;
	JTextField symbol = null;

	public javax.swing.JPanel getJContentPane()
	{
		if (jContentPane == null)
		{
			// public void setBounds(int x, int y, int width, int height)
			jContentPane = new javax.swing.JPanel(null);
			/*
			 * ImageIcon cup = new
			 * ImageIcon(ClassLoader.getSystemResource("image/home.jpg"));
			 * JButton home = new JButton(cup); home.setBounds(xpos, 1, 24, 24);
			 * home.setName("Home"); home.setToolTipText("Home");
			 * jContentPane.add(home, null); xpos = xpos + 24; cup = new
			 * ImageIcon(ClassLoader.getSystemResource("image/portfolio.jpg"));
			 * JButton portfolio = new JButton(cup); portfolio.setBounds(xpos,
			 * 1, 24, 24); portfolio.setToolTipText("Portfolio");
			 * jContentPane.add(portfolio, null); xpos = xpos + 24;
			 * 
			 * cup = new
			 * ImageIcon(ClassLoader.getSystemResource("image/balancesheet.jpg"
			 * )); JButton balancesheet = new JButton(cup);
			 * balancesheet.setBounds(xpos, 1, 24, 24);
			 * portfolio.setToolTipText("Balance Sheet");
			 * jContentPane.add(balancesheet, null); xpos = xpos + 24;
			 */
			int xpos = 1;
			symbol = new JTextField();
			symbol.setFont(new Font("Arial", Font.BOLD, 12));
			symbol.setBounds(xpos, 1, 70, 24);
			symbol.addActionListener(this);
			symbol.addKeyListener(this);
			symbol.setToolTipText("Enter symbol and click enter to watch");
			jContentPane.add(symbol, null);
			xpos = xpos + 70;
			ImageIcon cup = null;

			cup = new ImageIcon(ClassLoader.getSystemResource("image/find_small.jpg"));
			JButton find = new JButton(cup);
			find.setBackground(this.getBackground());
			find.setBounds(xpos, 1, 24, 24);
			find.setName("find");
			find.setForeground(Color.white);
			find.setToolTipText(" Find Stock ");
			jContentPane.add(find, null);
			find.addActionListener(this);

			xpos = xpos + 24;

			cup = new ImageIcon(ClassLoader.getSystemResource("image/query.jpg"));
			JButton query = new JButton(cup);
			query.setBounds(xpos, 1, 24, 24);
			query.setName("query");
			query.addActionListener(this);
			query.setToolTipText("Write/Execute Filter ");
			jContentPane.add(query, null);
			xpos = xpos + 24;

			cup = new ImageIcon(ClassLoader.getSystemResource("image/syncview.jpg"));
			JButton syncview = new JButton(cup);
			
			syncview.setBounds(xpos, 1, 24, 24);
			syncview.setName("liveniftyquotepanel");
			syncview.addActionListener(this);
	//		jContentPane.add(syncview, null);
			syncview.setToolTipText("Intraday scannar /Live Market Quote");
	//		xpos = xpos + 24;

			cup = new ImageIcon(ClassLoader.getSystemResource("image/portfolio.jpg"));
			JButton portfolio = new JButton(cup);
			portfolio.setBounds(xpos, 1, 24, 24);
			portfolio.setName("portfolio");
			portfolio.addActionListener(this);
			jContentPane.add(portfolio, null);
			portfolio.setToolTipText("Portfolio");
			xpos = xpos + 24;

			cup = new ImageIcon(ClassLoader.getSystemResource("image/stockscreener.jpg"));
			JButton stocksreenerformulabuilder = new JButton(cup);
			stocksreenerformulabuilder.setBounds(xpos, 1, 24, 24);
			stocksreenerformulabuilder.setName("marketsnapshot");
			stocksreenerformulabuilder.addActionListener(this);
			stocksreenerformulabuilder.setToolTipText("Market Snapshot");
//			jContentPane.add(stocksreenerformulabuilder, null);
//			xpos = xpos + 24;

/*			
			cup = new ImageIcon(ClassLoader.getSystemResource("image/stockscreener.jpg"));
			JButton stocksreenerformulabuilderlive = new JButton(cup);
			stocksreenerformulabuilderlive.setBounds(xpos, 1, 24, 24);
			stocksreenerformulabuilderlive.setName("livemarketsnapshot");
			stocksreenerformulabuilderlive.addActionListener(this);
			stocksreenerformulabuilderlive.setToolTipText("LIve Market Snapshot");
			jContentPane.add(stocksreenerformulabuilderlive, null);
			xpos = xpos + 24;
*/

		//	xpos = xpos + 24;
			cup = new ImageIcon(ClassLoader.getSystemResource("image/intraday.jpg"));
			JButton intradaybut = new JButton(cup);
			intradaybut.setBackground(Color.black);
			intradaybut.setBounds(xpos, 1, 24, 24);
			intradaybut.setName("intraday");
			intradaybut.setForeground(Color.white);
			intradaybut.setToolTipText(" Intraday Chart Screen ");
			jContentPane.add(intradaybut, null);
			intradaybut.addActionListener(this);

			xpos = xpos + 24;
			cup = new ImageIcon(ClassLoader.getSystemResource("image/runstockscreener.jpg"));
			JButton runstockscreener = new JButton(cup);
			runstockscreener.setBounds(xpos, 1, 24, 24);
			runstockscreener.setName("buyselladvisor");
			runstockscreener.setToolTipText("Buy Sell Advisor with Inbuilt Scans ");
			runstockscreener.addActionListener(this);
			jContentPane.add(runstockscreener, null);
			
			xpos = xpos + 24;
			cup = new ImageIcon(ClassLoader.getSystemResource("image/formulabuilder.jpg"));
			JButton formulabuilder = new JButton(cup);
			formulabuilder.setBounds(xpos, 1, 24, 24);
			formulabuilder.setName("strategycompare");
			formulabuilder.addActionListener(this);
			formulabuilder.setToolTipText("Portfolio Back Testing and Compare Strategies");
			jContentPane.add(formulabuilder, null);
			xpos = xpos + 24;
			/*
			 * cup = new
			 * ImageIcon(ClassLoader.getSystemResource("image/download.jpg"));
			 * JButton download = new JButton(cup); download.setBounds(xpos, 1,
			 * 24, 24); download.setName("download");download.setToolTipText(
			 * "Download and Cash data for faster screening ");
			 * download.addActionListener(this); jContentPane.add(download,
			 * null); xpos = xpos + 24;
			 */
			int iconsize = 24;
	/*		xpos = xpos + 5;
			JSeparator jsep3 = new JSeparator(SwingConstants.VERTICAL);
			jContentPane.add(jsep3);
			jsep3.setBounds(xpos, 1, 10, iconsize);
			xpos = xpos + 5;
*/
			int currentx = xpos;
			cup = new ImageIcon(ClassLoader.getSystemResource("image/formula.jpg"));
			JButton formula = new JButton(cup);
			formula.setBounds(xpos, 1, 24, 24);
			formula.setName("formula");
			formula.setToolTipText("Formula Editor  ");
			formula.addActionListener(this);
			jContentPane.add(formula, null);
			int ypos = 30;
			//xpos = xpos + 24;
			xpos = 70;
			iconsize = 24;
			xpos = xpos + 5;
			JSeparator jsep = new JSeparator(SwingConstants.VERTICAL);
			jContentPane.add(jsep);
			jsep.setBounds(xpos, ypos, 10, iconsize);
			xpos = xpos + 5;
			
			cup = new ImageIcon(ClassLoader.getSystemResource("image/addindicator.jpg"));
			JButton addind = new JButton(cup);
			addind.setBackground(this.getBackground());
			addind.setBounds(xpos, ypos, 24, 24);
			addind.setName("addind");
			jContentPane.add(addind, null);
			addind.addActionListener(this);
			addind.setToolTipText(" Manage Indicator ");

			xpos = xpos + 24;
			
			cup = new ImageIcon(ClassLoader.getSystemResource("image/inbuiltstrategy.jpg"));
			JButton inbuiltstrategy = new JButton(cup);
			inbuiltstrategy.setBackground(this.getBackground());
			
			inbuiltstrategy.setBounds(xpos, ypos, 24, 24);
			inbuiltstrategy.setName("inbuiltstrategy");
			inbuiltstrategy.setToolTipText(" Apply Inbuilt Strategies  ");
			jContentPane.add(inbuiltstrategy, null);
			inbuiltstrategy.addActionListener(this);

			xpos = xpos + 24;
						

			ImageIcon cup1 = new ImageIcon(ClassLoader.getSystemResource("image/prev.jpg"));
			JButton prev = new JButton(cup1);
			prev.setBackground(this.getBackground());
			prev.setBounds(xpos, ypos, 24, 24);
			prev.setName("prev");
			prev.setForeground(Color.white);
			prev.setToolTipText(" Prev Data ");
			jContentPane.add(prev, null);
			prev.addActionListener(this);

			xpos = xpos + 24;
			cup1 = new ImageIcon(ClassLoader.getSystemResource("image/next.jpg"));
			JButton next = new JButton(cup1);
			// next.setBackground(StockConstants.graphbackgroundcolor);
			next.setBounds(xpos, ypos, 24, 24);
			next.setName("next");
			next.setForeground(Color.white);
			next.setToolTipText(" Next data ");
			jContentPane.add(next, null);
			next.addActionListener(this);

			xpos = xpos + 24;
			cup1 = new ImageIcon(ClassLoader.getSystemResource("image/expand.GIF"));
			JButton expand = new JButton(cup1);
			expand.setBackground(this.getBackground());
			expand.setBounds(xpos, ypos, 24, 24);
			expand.setName("expand");
			expand.setForeground(Color.white);
			expand.setToolTipText(" Zoom In ");
			jContentPane.add(expand, null);
			expand.addActionListener(this);
			xpos = xpos + 24;
			cup1 = new ImageIcon(ClassLoader.getSystemResource("image/shrink.GIF"));
			JButton shrink = new JButton(cup1);
			shrink.setBackground(this.getBackground());
			shrink.setBounds(xpos, ypos, 24, 24);
			shrink.setName("shrink");
			shrink.setForeground(Color.white);
			shrink.setToolTipText(" Zoom Out ");
			jContentPane.add(shrink, null);
			shrink.addActionListener(this);
			xpos = xpos + 24;
			cup1 = new ImageIcon(ClassLoader.getSystemResource("image/resetzoom.jpg"));
			JButton resetzoom = new JButton(cup1);
			resetzoom.setBackground(this.getBackground());
			resetzoom.setBounds(xpos, ypos, 24, 24);
			resetzoom.setName("resetzoom");
			resetzoom.setForeground(Color.white);
			resetzoom.setToolTipText(" Reset Zoom ");
			jContentPane.add(resetzoom, null);
			resetzoom.addActionListener(this);

			/*
			 * xpos = xpos + 24;
			 * 
			 * iconsize = 24; xpos = xpos + 5; JSeparator jsep = new
			 * JSeparator(SwingConstants.VERTICAL); jContentPane.add(jsep);
			 * jsep.setBounds(xpos, 1, 10, iconsize); xpos = xpos + 5;
			 * 
			 * cup = new
			 * ImageIcon(ClassLoader.getSystemResource("image/console.jpg"));
			 * JButton console = new JButton(cup); console.setBounds(xpos, 1,
			 * 24, 24); console.setName("console");
			 * console.setToolTipText("output Window ");
			 * console.addActionListener(this); jContentPane.add(console, null);
			 * xpos = xpos + 24; cup = new
			 * ImageIcon(ClassLoader.getSystemResource("image/clear.jpg"));
			 * JButton clear = new JButton(cup); clear.setBounds(xpos, 1, 24,
			 * 24); clear.setName("clearconsole");
			 * clear.setToolTipText("Clear output window ");
			 * clear.addActionListener(this); jContentPane.add(clear, null);
			 */

			xpos = xpos + 24;

			iconsize = 24;
			xpos = xpos + 5;
			jsep = new JSeparator(SwingConstants.VERTICAL);
			jContentPane.add(jsep);
			jsep.setBounds(xpos, ypos, 10, iconsize);
			xpos = xpos + 5;

			Vector year = Utility.getYear();
			JComboBox yearBox = new JComboBox(year);
			yearBox.setFont(new Font("Arial", Font.BOLD, 9));
			yearBox.setToolTipText(" Select Year ");
			yearBox.setName("year");
			yearBox.setBounds(xpos, ypos, 50, 24);
			jContentPane.add(yearBox, null);
			yearBox.addActionListener(this);

			xpos = xpos + 50;
			Vector graphtype = Utility.getGraphType();
			JComboBox graphtypebut = new JComboBox(graphtype);
			graphtypebut.setFont(new Font("Arial", Font.BOLD, 9));
			graphtypebut.setToolTipText(" Select Graph Type ");
			graphtypebut.setBounds(xpos, ypos, 60, 24);
			graphtypebut.setName("graphtype");
			jContentPane.add(graphtypebut, null);
			graphtypebut.addActionListener(this);
			xpos = xpos + 60;
			Vector graphresolution = Utility.getGraphResolutions();
			JComboBox graphresolutionbut = new JComboBox(graphresolution);
			graphresolutionbut.setFont(new Font("Arial", Font.BOLD, 9));
			graphresolutionbut.setToolTipText(" Select Graph Resolution ");
			graphresolutionbut.setName("graphresolution");
			graphresolutionbut.setBounds(xpos, ypos, 60, 24);
			jContentPane.add(graphresolutionbut, null);
			graphresolutionbut.addActionListener(this);
			xpos = xpos + 60;

			Vector v = Utility.getSnapshot();
			JComboBox showsnapshotbut = new JComboBox(v);
			showsnapshotbut.setToolTipText(" This will show stock snapshot and recommendation on intraday and EOD graph, Select No stock Snap shot option if you do'nt want  ");
			showsnapshotbut.setName("snapshot");
			showsnapshotbut.setBounds(xpos, ypos,150, 24);
			jContentPane.add(showsnapshotbut, null);
			showsnapshotbut.addActionListener(this);
			xpos = xpos + 150;

			
			
			// all drawing component added
			addDrawingComponent(currentx + 24);
			xpos = xpos + 5;
			JSeparator jsep1 = new JSeparator(SwingConstants.VERTICAL);
			jContentPane.add(jsep1);
			jsep1.setBounds(xpos, 1, 10, 24);
			xpos = xpos + 5;
			int xpanelpos = StockConstants.length - 45;

			/*
			 * cup = new
			 * ImageIcon(ClassLoader.getSystemResource("image/livequote.jpg"));
			 * JButton chartindex = new JButton(cup);
			 * chartindex.setBounds(xpanelpos, 1, 24, 24);
			 * chartindex.setName("liveniftyquotepanel");
			 * chartindex.addActionListener(this);
			 * chartindex.setToolTipText("View chart with live index quote ");
			 * jContentPane.add(chartindex, null); xpanelpos = xpanelpos - 24;
			 */
			/*
			 * cup = newImageIcon(ClassLoader.getSystemResource(
			 * "image/liveniftyquote2panel.jpg")); JButton liveniftyquote2panel
			 * = new JButton(cup); liveniftyquote2panel.setBounds(xpanelpos, 1,
			 * 24, 24); liveniftyquote2panel.setName("liveniftyquote2panel");
			 * liveniftyquote2panel.addActionListener(this);
			 * liveniftyquote2panel
			 * .setToolTipText("Double Pane With Live Nifty Quote ");
			 * jContentPane.add(liveniftyquote2panel, null); xpanelpos =
			 * xpanelpos - 24;
			 * 
			 * cup = newImageIcon(ClassLoader.getSystemResource(
			 * "image/liveniftyquotepanel.jpg")); JButton liveniftyquotepanel =
			 * new JButton(cup); liveniftyquotepanel.setBounds(xpanelpos, 1, 24,
			 * 24); liveniftyquotepanel.setName("liveniftyquotepanel");
			 * liveniftyquotepanel.addActionListener(this);
			 * liveniftyquotepanel.setToolTipText
			 * ("Single Pane With Live Nifty Quote ");
			 * jContentPane.add(liveniftyquotepanel, null); xpanelpos =
			 * xpanelpos - 24;
			 */
			cup = new ImageIcon(ClassLoader.getSystemResource("image/singlepane.jpg"));
			JButton singlePanel = new JButton(cup);
			singlePanel.setBounds(xpanelpos, 1, 24, 24);
			singlePanel.setName("singlepane");
			singlePanel.addActionListener(this);
			singlePanel.setToolTipText("Single Pane View ");
			jContentPane.add(singlePanel, null);
			xpanelpos = xpanelpos - 24;
			cup = new ImageIcon(ClassLoader.getSystemResource("image/verdiv.jpg"));
			JButton verdivPanel = new JButton(cup);
			verdivPanel.setBounds(xpanelpos, 1, 24, 24);
			verdivPanel.setName("verdiv");
			verdivPanel.addActionListener(this);
			verdivPanel.setToolTipText("Vertical Pane View");
			jContentPane.add(verdivPanel, null);
			xpanelpos = xpanelpos - 24;
			cup = new ImageIcon(ClassLoader.getSystemResource("image/hordiv.jpg"));
			JButton hordivPanel = new JButton(cup);
			hordivPanel.setBounds(xpanelpos, 1, 24, 24);
			hordivPanel.setName("hordiv");
			hordivPanel.addActionListener(this);
			hordivPanel.setToolTipText("Horizontal Pane View");
			jContentPane.add(hordivPanel, null);
			xpanelpos = xpanelpos - 24;
			cup = new ImageIcon(ClassLoader.getSystemResource("image/fourpanel.jpg"));
			JButton fourpanel = new JButton(cup);
			fourpanel.setBounds(xpanelpos, 1, 24, 24);
			jContentPane.add(fourpanel, null);
			fourpanel.setName("fourpanel");
			fourpanel.setToolTipText("4 view panel ");
			fourpanel.addActionListener(this);
			fourpanel.setToolTipText("Four Pane View");
			xpanelpos = xpanelpos - 24;

			cup = new ImageIcon(ClassLoader.getSystemResource("image/liveniftyquotepanel.jpg"));
			JButton hordivformula = new JButton(cup);
			hordivformula.setBounds(xpanelpos, 1, 24, 24);
			jContentPane.add(hordivformula, null);
			hordivformula.setName("hordivformula");
			hordivformula.setToolTipText("Graph with Formula Screen ");
			hordivformula.addActionListener(this);
			hordivformula.setToolTipText("Graph with Formula Screen");
			xpanelpos = xpanelpos - 24;

	/*		cup = new ImageIcon(ClassLoader.getSystemResource("image/home.jpg"));
			JButton chartigs = new JButton(cup);
			chartigs.setBounds(xpanelpos, 1, 24, 24);
			chartigs.setName("chartigs");
			chartigs.addActionListener(this);
			chartigs.setToolTipText("IGS Analaysis Panel");
			jContentPane.add(chartigs, null);

*/
			news = new NewsPanel();
			news.msg = "welcome to iguidestocks";
			news.setBounds(0, 60, StockConstants.length, 28);
			news.setBackground(Color.black);
			jContentPane.add(news, null);

			// thread for news and other stuff like auto updating data
			try
			{
				// NewsThread news = new NewsThread(this);
				// Thread th = new Thread(news);
				// th.start();
			}
			catch (Exception e)
			{
			}
			// end
			try
			{
				panel = new PanelForGraph(0, 0, StockConstants.length - LEFT_END_POS, StockConstants.height - StockConstants.YPOSITIONFORGRAPH
						- StockConstants.YPOSITIONFORGRAPHGAP, StockConstants.SELECTED_STOCK);
				
					panel.setBounds(LEFT_END_POS, StockConstants.YPOSITIONFORGRAPH, StockConstants.length - LEFT_END_POS, StockConstants.height
						- StockConstants.YPOSITIONFORGRAPH);
				setBackground(StockConstants.graphbackgroundcolor);
				panel.setOpaque(true);
				panel.setVisible(true);
				jContentPane.add(panel, null);
			
			}
			catch (Exception e)
			{
			}
			addTreeAdPanel();
		}
		return jContentPane;
	}

	public int addDrawingComponent(int xinc)
	{
		
		int iconsize = 24;
		xinc = xinc + 5;
		JSeparator jsep = new JSeparator(SwingConstants.VERTICAL);
		jContentPane.add(jsep);
		jsep.setBounds(xinc, 1, 10, iconsize);
		xinc = xinc + 5;
		ImageIcon cup1 = new ImageIcon(ClassLoader.getSystemResource("image/drawing/line.jpg"));
		JButton line = new JButton(cup1);
		line.setBounds(xinc, 1, iconsize, iconsize);
		line.setName("line");
		line.setToolTipText(" Draw Line ");
		jContentPane.add(line);
		line.addActionListener(this);
		// xinc = xinc + iconsize;
		// cup1 = new
		// ImageIcon(ClassLoader.getSystemResource("image/drawing/trendline.jpg"));
		// JButton trendline = new JButton(cup1);
		// trendline.setBounds(xinc, 1, iconsize, iconsize);
		// trendline.setName("trendline");
		// trendline.setToolTipText(" Draw Trend Line ");
		// jContentPane.add(trendline);
		// trendline.addActionListener(this);
		xinc = xinc + iconsize;
		cup1 = new ImageIcon(ClassLoader.getSystemResource("image/drawing/horline.jpg"));
		JButton horline = new JButton(cup1);
		horline.setBounds(xinc, 1, iconsize, iconsize);
		horline.setName("horline");
		horline.setToolTipText(" Draw Horizontal Line ");
		jContentPane.add(horline);
		horline.addActionListener(this);
		xinc = xinc + iconsize;
		cup1 = new ImageIcon(ClassLoader.getSystemResource("image/drawing/verline.jpg"));
		JButton verline = new JButton(cup1);
		verline.setBounds(xinc, 1, iconsize, iconsize);
		verline.setName("verline");
		verline.setToolTipText(" Draw Vertical Line ");
		jContentPane.add(verline);
		verline.addActionListener(this);
		xinc = xinc + iconsize;
		cup1 = new ImageIcon(ClassLoader.getSystemResource("image/drawing/addnote.jpg"));
		JButton addnote = new JButton(cup1);
		addnote.setBounds(xinc, 1, iconsize, iconsize);
		addnote.setName("addnote");
		addnote.setToolTipText("Add Note/Comment");
		jContentPane.add(addnote);
		addnote.addActionListener(this);
		/*
		 * xinc = xinc + iconsize; cup1 = new
		 * ImageIcon(ClassLoader.getSystemResource
		 * ("image/drawing/rectangle.jpg")); JButton rectangle = new
		 * JButton(cup1); rectangle.setBounds(xinc, 1, iconsize, iconsize);
		 * rectangle.setName("rectangle");
		 * rectangle.setToolTipText(" Add rectangle to Graph ");
		 * jContentPane.add(rectangle); rectangle.addActionListener(this);
		 */
		xinc = xinc + iconsize;
		cup1 = new ImageIcon(ClassLoader.getSystemResource("image/drawing/fibhor.jpg"));
		JButton fibhor = new JButton(cup1);
		fibhor.setBounds(xinc, 1, iconsize, iconsize);
		fibhor.setName("fibhor");
		fibhor.setToolTipText(" Draw Fibonnoci Retracement With Time");
		jContentPane.add(fibhor);
		fibhor.addActionListener(this);
		xinc = xinc + iconsize;
		cup1 = new ImageIcon(ClassLoader.getSystemResource("image/drawing/fib.jpg"));
		JButton fib = new JButton(cup1);
		fib.setBounds(xinc, 1, iconsize, iconsize);
		fib.setName("fib");
		fib.setToolTipText(" Draw Fibonnoci Retracement With Price");
		jContentPane.add(fib);
		fib.addActionListener(this);
		xinc = xinc + iconsize;
		cup1 = new ImageIcon(ClassLoader.getSystemResource("image/drawing/buyarrow.jpg"));
		JButton buyarrow = new JButton(cup1);
		buyarrow.setBounds(xinc, 1, iconsize, iconsize);
		buyarrow.setName("buyarrow");
		buyarrow.setToolTipText(" Draw Buy Arrow ");
		jContentPane.add(buyarrow);
		buyarrow.addActionListener(this);
		xinc = xinc + iconsize;
		cup1 = new ImageIcon(ClassLoader.getSystemResource("image/drawing/sellarrow.jpg"));
		JButton sellarrow = new JButton(cup1);
		sellarrow.setBounds(xinc, 1, iconsize, iconsize);
		sellarrow.setName("sellarrow");
		sellarrow.setToolTipText(" Draw Sell Arrow ");
		jContentPane.add(sellarrow);
		sellarrow.addActionListener(this);
		xinc = xinc + iconsize;
		cup1 = new ImageIcon(ClassLoader.getSystemResource("image/drawing/gann.jpg"));
		JButton gannfan = new JButton(cup1);
		gannfan.setBounds(xinc, 1, iconsize, iconsize);
		gannfan.setName("gannfan");
		gannfan.setToolTipText(" Draw Gann Fan ");
		jContentPane.add(gannfan);
		gannfan.addActionListener(this);
		xinc = xinc + iconsize;
		cup1 = new ImageIcon(ClassLoader.getSystemResource("image/help.jpg"));
		JButton help = new JButton(cup1);
		help.setBounds(xinc, 1, iconsize, iconsize);
		help.setName("help");
		help.setToolTipText(" Help ");
		jContentPane.add(help);
		help.addActionListener(this);
		xinc = xinc + 5;
		JSeparator jsep1 = new JSeparator(SwingConstants.VERTICAL);
		jContentPane.add(jsep1);
		jsep1.setBounds(xinc, 1, iconsize, iconsize);
		return xinc;
	}

	public void setNewSymbol(String symbol)
	{
		// // ln("setNewSymbol in mainscreen");
		this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		try
		{
			// Loader.loadAllData(StockConstants.STARTDATE,symbol);
			((PanelForGraphImpl) panel).searchStockUpdate(symbol);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			// e.printStackTrace();
			MessageDiaglog msg = new MessageDiaglog("Error Getting data");
		}
		this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		// doReSize();
	}

	public static String namelist = ":downloaddatafornewsymbol:managesplitbonus:query:console:clearconsole:runstockscreener:strategycompare:optimization:intraday:intradayscan:chartigs:singlepane:verdiv:formula:hordivformula:liveniftyquotepanel:hordiv:find:download:fourpanel:fourpanelintra:twopanelintra:portfolio:syncview:formulabuilder:stocksreenerformulabuilder:marketsnapshot:livemarketsnapshot:buyselladvisor:addind:inbuiltstrategy:";

	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() instanceof JMenuItem)
		{
			JMenuItem source = (JMenuItem) (e.getSource());
			// // ln("source=" + source.getText());

			String s = source.getText();
			String name = source.getName();
			if(name==null)
				name="zzzzz";
			s = s.trim();
			if(namelist.indexOf(name) != -1)
			{
				executeButtonEvent(name);
	
			}
			else if (name != null && name.equalsIgnoreCase("download"))
			{
				if (StockConstants.downloadDataFromInternet.equals("true"))
				{
					StockConstants.downloadDataFromInternet = "false";
					source.setText("Start Downloading Data From Internet");
				}
				else
				{
					StockConstants.downloadDataFromInternet = "true";
					source.setText("Stop Downloading Data From Internet");

				}
			}
			if (s.equals("Download data"))
			{
				DataDownloadScreen datadownload = DataDownloadScreen.getSingleton();
				datadownload.show();
			}
			else if (s.equalsIgnoreCase("Download Financial data"))
			{
				DataExtractorScreen data = new DataExtractorScreen();
			}
			if (s.equals("Open new screen"))
			{
				PanelForGraphFrame frame = new PanelForGraphFrame(StockConstants.SELECTED_STOCK);
				frame.show();
			}
			else if (s.equals("Delete All Old data"))
			{
				File f = new File(StockConstants.INSTALL_DIR + "/data");
				String[] files = f.list();
				for (int i = 0; i < files.length; i++)
				{
					try
					{
						File f1 = new File(StockConstants.INSTALL_DIR + "/data/" + files[i]);
						f1.delete();
					}
					catch (Exception e1)
					{
					}
				}
				MessageDiaglog msg = new MessageDiaglog("All Files under data directory is deleted");
			}
			else if (s.equals("Set up Backtesting Parameter"))
			{
				BackTestingParameterScreen backtesting = new BackTestingParameterScreen(null);
			}
			else if (s.equals("Save Graph as Template"))
			{
				if (panel instanceof PanelForGraph)
				{
					FileDialog fd = new FileDialog(new Frame(), "Save Template", FileDialog.SAVE);
					fd.setFile("*.template");
					fd.setDirectory(StockConstants.TEMPLATE_DIR);
					fd.setLocation(50, 50);
					fd.show();

					String fileName = fd.getFile();
					String dirName = fd.getDirectory();
					if (fileName != null && !fileName.equals("") && dirName != null && !dirName.equals(""))
					{
						File f = new File(dirName + "/" + fileName);
						try
						{
							PanelForGraph mypanel = (PanelForGraph) panel;
							HashMap hs = new HashMap();
							FileOutputStream file = new FileOutputStream(f);
							ObjectOutputStream obj = new ObjectOutputStream(file);
							hs.put(PanelForGraph.INDVECTROR, mypanel.macdgraph.indHash);
							hs.put(PanelForGraph.STRVECTROR, mypanel.macdgraph.strategyVector);

							obj.writeObject(hs);
							obj.close();
							file.close();
						}
						catch (Exception e1)
						{
							e1.printStackTrace();
						}

					}

				}
				else
				{
					MessageDiaglog msg = new MessageDiaglog("Use Single Panel to Save Graph as Template");
				}
			}

			else if (s.equals("Download Historical Data"))
			{
				// File f = new File(StockConstants.INSTALL_DIR);
				// if(!f.exists())
				// f.mkdirs();
				// Utility.setAllConstants();
				// Loader loader = new Loader();
				// loader.loadAllData();
				// jTextArea.append(StockConstants.LogBuffer.toString());
			}
			else if (s.equalsIgnoreCase("Download EOD data"))
			{
				try
				{
					Runnable r = new UpdateThread();
					Thread th = new Thread(r);
					th.start();				}
				catch (Exception e1)
				{
				}
			}
			else if (s.equalsIgnoreCase("Proxy Setup"))
			{
				ProxySetting f = new ProxySetting();
			}
			else if (s.equalsIgnoreCase("Change Country"))
			{
				ChangeCountry f = new ChangeCountry();
			}

			else if (s.equalsIgnoreCase("Compare Selected Period"))
			{
				if (Utility.isBasicUser())
					return;

				if (panel instanceof PanelForGraphImpl)
				{
					PanelForGraphImpl panel1 = (PanelForGraphImpl) panel;
					ComparePeriodFrame comparframe = new ComparePeriodFrame(panel1);
				}
			}

			else if (s.equalsIgnoreCase("Settings"))
			{
				// SettingEditorScreen stst = new SettingEditorScreen();
				ConfigurePropertyScreen conf = new ConfigurePropertyScreen();

			}
			else if (s.equalsIgnoreCase("Add/Update Show Indicator List"))
			{
				SelectIndicators sel = new SelectIndicators();
			}
			else if (s.equalsIgnoreCase("User Info"))
			{
				StringBuffer sb10 = new StringBuffer();
				sb10.append("Your User Id for IGS Software is " + StockConstants.USERDETAIL.getProperty("uid"));
				sb10.append(" for computer  " + StockConstants.USERDETAIL.getProperty("mcname") + ".");
				MessageDiaglog u = new MessageDiaglog(sb10.toString());
			}
			else if (s.equalsIgnoreCase("Download Result"))
			{
				File f = new File(StockConstants.INSTALL_DIR + "/result/");
				f.mkdirs();
				// IndexThread indexthread = new IndexThread(null);
				// indexthread.status = IndexThread.RESULT;
				// Thread th = new Thread(indexthread);
				// th.start();
			}
			else if (s.equalsIgnoreCase("Add/Delete New Category"))
			{
				NewFavouriteCategoryEntry cat = new NewFavouriteCategoryEntry();
			}
			else if (s.equalsIgnoreCase("Add/Update Stock to Category"))
			{
				AddRemoveStock addremove = new AddRemoveStock();
				// AddNewStock addnewstock = new AddNewStock(null);
			}
			else if (s.equalsIgnoreCase("version"))
			{
				ToolScreenPanel tool = new ToolScreenPanel();
			}
			else if (s.equalsIgnoreCase("Help"))
			{
				try
				{
					Utility.openHelpLink("mainpage");
				}
				catch (Exception e1)
				{
				}
			}
			else if (s.equalsIgnoreCase("FAQ"))
			{
				try
				{
					Utility.openHelpLink("faq");
				}
				catch (Exception e1)
				{
				}
			}
			else if (s.equalsIgnoreCase("IGS Formula Language"))
			{
				try
				{
					Utility.openHelpLink("igsfl");
				}
				catch (Exception e1)
				{
				}
			}
			else if (s.equalsIgnoreCase("Formula Charter"))
			{
				try
				{
					Utility.openHelpLink("formulacharter");
				}
				catch (Exception e1)
				{
				}
			}
			else if (s.equalsIgnoreCase("Terms & Condition"))
			{
				try
				{
					Utility.openURL("http://www.iguidestocks.com/project/global/igs/legal.jsp");
				}
				catch (Exception e1)
				{
				}
			}
			else if (s.equalsIgnoreCase("Login to Yahoo"))
			{
				YahooLogin yahoo = new YahooLogin();
			}

			else if (s.equalsIgnoreCase("IGS SQL Language"))
			{
				try
				{
					Utility.openHelpLink("igsquery");
				}
				catch (Exception e1)
				{
				}
			}
			else if (s.equalsIgnoreCase("About iGuideStocks..."))
			{
				try
				{
					Utility.openHelpLink("about");
				}
				catch (Exception e1)
				{
				}
			}
			else
			{
				try
				{
					Utility.getLinkForOption(s, StockConstants.SELECTED_STOCK);
				}
				catch (Exception e1)
				{
				}
			}
		}
		else if (e.getSource() instanceof JComboBox)
		{
			JComboBox cb = (JComboBox) e.getSource();
			String butName = cb.getName();
			if (!(panel instanceof PanelForGraphImpl))
			{
				return;
			}
			PanelForGraphImpl panelimpl = (PanelForGraphImpl) panel;

			String selName = (String) cb.getSelectedItem();
			cb.setSelectedIndex(cb.getSelectedIndex());

			if (butName.equalsIgnoreCase("graphresolution"))
			{
				panelimpl.setResolution(selName);
			}
			else if (butName.equalsIgnoreCase("snapshot"))
			{
				String s = (cb.getSelectedItem()+"").trim();
				if(s.equalsIgnoreCase("Stock Snapshot"))
				{
					StockConstants.StockSnapshot=true; 
							
				}
				else if(s.equalsIgnoreCase("No Stock Snapshot"))
				{
					StockConstants.StockSnapshot=false; 
					
				}
			}
			
			else if (butName.equalsIgnoreCase("graphtype"))
			{
				panelimpl.setGraphType(selName);
			}
			else if (butName.equalsIgnoreCase("year"))
			{
				panelimpl.setYear(selName);
			}
		}
		else if (e.getSource() instanceof JButton)
		{
			JButton but = (JButton) e.getSource();
			String butName = but.getName();

			executeButtonEvent(butName);
		}
		else if (e.getSource() instanceof JComboBox)
		{
			JComboBox combo = (JComboBox) e.getSource();
			String comame = combo.getName();
			if (comame.equalsIgnoreCase("cursortype"))
			{
				String selname = (String) combo.getSelectedItem();
				if (selname.equals("Cross-Hair"))
				{
					((PanelForGraph) panel).setCursorType(ComponentManager.CROSSHAIR);
				}
				else if (selname.equals("Cursor"))
				{
					((PanelForGraph) panel).setCursorType(ComponentManager.CURSOR);
				}
				else if (selname.equals("Mouse"))
				{
					((PanelForGraph) panel).setCursorType(ComponentManager.MOUSE);
				}
			}
			else if (comame.equalsIgnoreCase("graphresolution"))
			{
				String selname = (String) combo.getSelectedItem();
				((PanelForGraph) panel).setResolution(selname);
			}
			else if (comame.equalsIgnoreCase("graphtype"))
			{
				String selname = (String) combo.getSelectedItem();
				// ((PanelForGraph) panel).setGraphType(selname);
			}

		}
	}

	public void executeButtonEvent(String butName)
	{
		String drawingcom = "line:horline:verline:fibret:fibhor:buyarrow:sellarrow:addnote:gannfan";
		if (butName.equals("Download data Now"))
		{
			// StockConstants.optionname = "All Securities";
			// Runnable runnable = new IndexThread(this);
			// Thread thread = new Thread(runnable);
			// thread.start();
			// LoaderThread ld = new LoaderThread(this);
			// Thread th = new Thread(ld);
			// th.start();
		}
		else if (butName.equals("managesplitbonus"))
		{
			try
			{
				SplitHandler.handleSplit(StockConstants.SELECTED_STOCK);

				((PanelForGraphImpl)panel).searchStockUpdate(StockConstants.SELECTED_STOCK);
				
			}
			catch(Exception e)
			{
				
			}
		}
		else if (butName.equals("downloaddatafornewsymbol"))
		{
			SymbolEntryPanel s = new SymbolEntryPanel(); 
		}
		else if (butName.equals("optimization"))
		{
			if (Utility.isBasicUser())
				return;

			OptimizeScreen s = new OptimizeScreen(); 
		}
		else if (butName.equals("intraday"))
		{
			if(!Utility.isIntraDayUser())
			{
				return;
			}
			

			IntradayPanelForGraphFrame s = new IntradayPanelForGraphFrame(); 
		}
		else if (butName.equals("buyselladvisor"))
		{
			if(Utility.isBasicUser())
			{
				return;
			}
			

			new BuySellAdvisorScreen();
					
		}
		
		else if (butName.equals("intradayscan"))
		{
			if(!Utility.isIntraDayUser())
			{
				return;
			}
			

			
			com.stockfaxforu.livedata.ShowLiveMarket x =new com.stockfaxforu.livedata.ShowLiveMarket(StockConstants.length-100,StockConstants.height-50,1);
	}
		
		else if (butName.equals("find"))
		{
			if (panel instanceof PanelForGraphImpl)
			{
				PanelForGraphImpl panelimpl = (PanelForGraphImpl) panel;

				SearchStock d = new SearchStock(panelimpl);

			}
		}
		
		else if (butName.equals("download"))
		{
			DataDownloadScreen datadownload = DataDownloadScreen.getSingleton();
			datadownload.show();
		}
		else if (drawingcom.indexOf(butName) != -1)
		{
			doDrawingComponent(butName);
		}
		else if (butName.equals("fourpanel"))
		{
			try
			{
				jContentPane.remove(panel);
			}
			catch (Exception ex)
			{
				// // ln("exception while removing" +
				// ex.toString());
			}
		panel = new PanelFor4Graph(0, 0, StockConstants.length - LEFT_END_POS, StockConstants.height - StockConstants.YPOSITIONFORGRAPH
					- StockConstants.YPOSITIONFORGRAPHGAP);
			
		//	IntradayPanelFor4GraphFrame panel = new IntradayPanelFor4GraphFrame();
			paintMainPanel();
		}
		else if (butName.equals("fourpanelintra"))
		{
			if(!Utility.isIntraDayUser())
			{
				return;
			}
			try
			{
				jContentPane.remove(panel);
			}
			catch (Exception ex)
			{
				// // ln("exception while removing" +
				// ex.toString());
			}
		//	panel = new PanelFor4Graph(0, 0, StockConstants.length - LEFT_END_POS, StockConstants.height - StockConstants.YPOSITIONFORGRAPH
		//			- StockConstants.YPOSITIONFORGRAPHGAP);
			
			IntradayPanelFor4GraphFrame panel = new IntradayPanelFor4GraphFrame();
			paintMainPanel();
		}
		else if (butName.equals("twopanelintra"))
		{
			if(!Utility.isIntraDayUser())
			{
				return;
			}
			try
			{
				jContentPane.remove(panel);
			}
			catch (Exception ex)
			{
				// // ln("exception while removing" +
				// ex.toString());
			}
		//	panel = new PanelFor4Graph(0, 0, StockConstants.length - LEFT_END_POS, StockConstants.height - StockConstants.YPOSITIONFORGRAPH
		//			- StockConstants.YPOSITIONFORGRAPHGAP);
			
			IntradayPanelFor2GraphFrame panel = new IntradayPanelFor2GraphFrame();
			paintMainPanel();
		}
		
		else if (butName.equals("portfolio"))
		{
			if (Utility.isBasicUser())
				return;

		//	JFrame f = new JFrame();
			ShowTradingScreen newscan = new ShowTradingScreen(1000, 1000);
	//		f.getContentPane().add(newscan);
	//		f.setSize(1000, 1000);
	//		f.show();

		}
		else if (butName.equals("syncview"))
		{
		}
/*		else if (butName.equals("formulabuilder"))
		{
			JFrame f = new FormulaBuilderScreen(this, "Strategy Builder", false);
			f.toFront();
		}
		else if (butName.equals("stocksreenerformulabuilder"))
		{
			if (Utility.isBasicUser())
				return;

			JFrame f = new FormulaBuilderScreen(this, "Stock Screener Formula Builder", true);
			f.toFront();
		}
*/		
		else if (butName.equals("marketsnapshot"))
		
		{
			if (Utility.isBasicUser())
				return;

			try
			{
				jContentPane.remove(panel);
				if (panel instanceof PanelForGraphImpl)
				{
					((PanelForGraphImpl) panel).destroyThread();
				}
			}
			catch (Exception ex)
			{
			}
			panel = new MarketSnapShot(StockConstants.length - LEFT_END_POS, StockConstants.height - YPOSITIONFORGRAPHGAP,MarketSnapShot.HIST);
	//		panel = new Scan(StockConstants.length - 200, StockConstants.height - YPOSITIONFORGRAPHGAP);
			panel.setOpaque(true);
			panel.setVisible(true);
			jContentPane.add(panel, null);
			doReSize();

		}
		else if (butName.equals("livemarketsnapshot"))
		{
			if (Utility.isBasicUser())
				return;

			try
			{
				jContentPane.remove(panel);
				if (panel instanceof PanelForGraphImpl)
				{
					((PanelForGraphImpl) panel).destroyThread();
				}
			}
			catch (Exception ex)
			{
			}
			panel = new MarketSnapShot(StockConstants.length - LEFT_END_POS, StockConstants.height - YPOSITIONFORGRAPHGAP,MarketSnapShot.LIVE);
	//		panel = new Scan(StockConstants.length - 200, StockConstants.height - YPOSITIONFORGRAPHGAP);
			panel.setOpaque(true);
			panel.setVisible(true);
			jContentPane.add(panel, null);
			doReSize();

		}
		else if (butName.equals("query"))
		{
			if (Utility.isBasicUser())
				return;
			try
			{
				jContentPane.remove(panel);
				if (panel instanceof PanelForGraphImpl)
				{
					((PanelForGraphImpl) panel).destroyThread();
				}
			}
			catch (Exception ex)
			{
			}
			panel = new StockFilter(StockConstants.length - LEFT_END_POS, StockConstants.height - YPOSITIONFORGRAPHGAP);
			// panel = new ShowLiveMarket(StockConstants.length - 200,
			// StockConstants.height - 70);
			paintMainPanel();
		}
		else if (butName.equals("console"))
		{
			ConsoleWindow console = new ConsoleWindow();
		}
		else if (butName.equals("clearconsole"))
		{
			StockConstants.console = new StringBuffer();
			;
			MessageDiaglog msg = new MessageDiaglog("Output window is cleared");
		}
		else if (butName.equals("runstockscreener"))
		{
			if (Utility.isBasicUser())
				return;

			try
			{
				jContentPane.remove(panel);
			}
			catch (Exception ex)
			{
				// // ln("exception while removing" +
				// ex.toString());
			}
			panel = new SelectStockScannerNew(StockConstants.length - LEFT_END_POS, StockConstants.height - YPOSITIONFORGRAPHGAP);
			// panel = new ShowLiveMarket(StockConstants.length - 200,
			// StockConstants.height - 70);
		}
		else if (butName.equals("strategycompare"))
		{
			if (Utility.isBasicUser())
				return;

			try
			{
				jContentPane.remove(panel);
			}
			catch (Exception ex)
			{
				// // ln("exception while removing" +
				// ex.toString());
			}
			panel = new CompareStrategy(StockConstants.length - LEFT_END_POS, StockConstants.height - YPOSITIONFORGRAPHGAP);
			// panel = new ShowLiveMarket(StockConstants.length - 200,
			// StockConstants.height - 70);
			paintMainPanel();
		}
		else if (butName.equals("chartigs"))
		{

			try
			{
				jContentPane.remove(panel);
			}
			catch (Exception ex)
			{
				// // ln("exception while removing" +
				// ex.toString());
			}
			panel = new PanelForGraphIGS(0, 0, StockConstants.length - LEFT_END_POS, StockConstants.height - StockConstants.YPOSITIONFORGRAPH
					- StockConstants.YPOSITIONFORGRAPHGAP);
			paintMainPanel();
		}
		else if (butName.equals("singlepane"))
		{
			try
			{
				jContentPane.remove(panel);
			}
			catch (Exception ex)
			{
				// // ln("exception while removing" +
				// ex.toString());
			}
			panel = new PanelForGraph(0, 0, StockConstants.length - 300, StockConstants.height - StockConstants.YPOSITIONFORGRAPH
					- StockConstants.YPOSITIONFORGRAPHGAP, StockConstants.SELECTED_STOCK);
			paintMainPanel();
		}
		else if (butName.equals("verdiv"))
		{
			try
			{
				jContentPane.remove(panel);
				if (panel instanceof PanelForGraphImpl)
				{
					((PanelForGraphImpl) panel).destroyThread();
				}
			}
			catch (Exception ex)
			{
				// // ln("exception while removing" +
				// ex.toString());
			}
			panel = new PanelFor2Graph(0, 0, StockConstants.length - LEFT_END_POS, StockConstants.height - StockConstants.YPOSITIONFORGRAPH
					- StockConstants.YPOSITIONFORGRAPHGAP);
			paintMainPanel();
		}
		else if (butName.equals("formula"))
		{
			if (Utility.isBasicUser())
				return;

			FormulaEditorScreen formulaeditorscreen = new FormulaEditorScreen();
		}
		else if (butName.equals("addind"))
		{
			if (panel instanceof PanelForGraph)
			{
			//	TechIndicatorEntryPanel d = new TechIndicatorEntryPanel((PanelForGraphImpl) panel);
				ManageIndicatorsScreen m = new ManageIndicatorsScreen((PanelForGraph) panel);
				
			}
			else
			{
				MessageDiaglog m = new MessageDiaglog("You can apply indicator only in single panel window");
			}
		}
		else if (butName.equals("inbuiltstrategy"))
		{
			if (panel instanceof PanelForGraph)
			{
			//	TechIndicatorEntryPanel d = new TechIndicatorEntryPanel((PanelForGraphImpl) panel);
				ManageStrategyScreen manage = new ManageStrategyScreen((PanelForGraph) panel);
					
			}
			else
			{
				MessageDiaglog m = new MessageDiaglog("You can apply inbuilt strategies only in single panel window");
			}
		}
		
		
		
		else if (":expand:shrink:prev:next:resetzoom:".indexOf(butName.toLowerCase()) != -1)
		{
			if (!(panel instanceof PanelForGraphImpl))
			{
				return;
			}
			((PanelForGraphImpl) panel).setZooming(butName.toLowerCase());
		}

		else if (butName.equals("hordivformula"))
		{
			if (Utility.isBasicUser())
				return;
			try
			{
				jContentPane.remove(panel);
				if (panel instanceof PanelForGraphImpl)
				{
					((PanelForGraphImpl) panel).destroyThread();
				}
			}
			catch (Exception ex)
			{
				// // ln("exception while removing" +
				// ex.toString());
			}
			panel = new PanelFor2GraphHorFormula(0, 0, StockConstants.length - LEFT_END_POS, StockConstants.height - StockConstants.YPOSITIONFORGRAPH
					- StockConstants.YPOSITIONFORGRAPHGAP);
			paintMainPanel();
		}
//mahesh
		else if (butName.equals("liveniftyquotepanel"))
		{
			
/*			if(!Utility.isIntraDayUser())
			{
				return;
			}
			

			
			com.stockfaxforu.livedata.ShowLiveMarket x =new com.stockfaxforu.livedata.ShowLiveMarket(StockConstants.length-100,StockConstants.height-50,1);
*/
			String url="http://www.nseindia.com/live_market/dynaContent/live_watch/equities_stock_watch.htm?cat=N";

			Runtime runtime = Runtime.getRuntime();
			try {
				Process process =
					runtime.exec(
						StockConstants.IEEXPLORERLOC + "   -new 	" + url);
			} catch (IOException e) {
				// TODO Auto-generated catch block
		//		e.printStackTrace();
			}
	
			
		}

		else if (butName.equals("hordiv"))
		{
			try
			{
				jContentPane.remove(panel);
				if (panel instanceof PanelForGraphImpl)
				{
					((PanelForGraphImpl) panel).destroyThread();
				}
			}
			catch (Exception ex)
			{
				// // ln("exception while removing" +
				// ex.toString());
			}
			panel = new PanelFor2GraphHor(0, 0, StockConstants.length - LEFT_END_POS, StockConstants.height - StockConstants.YPOSITIONFORGRAPH
					- StockConstants.YPOSITIONFORGRAPHGAP);
			paintMainPanel();
		}
		else if (butName.equalsIgnoreCase("help"))
		{
			try
			{
				Utility.openHelpLink("mainpage");
			}
			catch (Exception e1)
			{
			}
		}
	}
	public void paintMainPanel()
	{
		panel.setBounds(LEFT_END_POS, StockConstants.YPOSITIONFORGRAPH, StockConstants.length - LEFT_END_POS, StockConstants.height
				- StockConstants.YPOSITIONFORGRAPH);
		// panel.setBackground(Color.black);
		panel.setOpaque(true);
		panel.setVisible(true);
		jContentPane.add(panel, null);
		doReSize();
	
	}
	public void doReSize()
	{
		// resetAd();
		repaint();
		int height = MainScreen.getSingleton().getHeight();
		int length = MainScreen.getSingleton().getWidth();
		if (height == (StockConstants.height) && length == StockConstants.length)
			MainScreen.getSingleton().setSize(StockConstants.length + 1, StockConstants.height + 1);
		else
			MainScreen.getSingleton().setSize(StockConstants.length, StockConstants.height);
		panel.repaint();
	}

	public void doDrawingComponent(String butName)
	{
		if (!(panel instanceof PanelForGraph))
			return;
		PanelForGraph graph = (PanelForGraph) panel;
		String drawingcom = "line:horline:verline:fibret:fibrethor:buyarrow:sellarrow:note";
		if (butName.equalsIgnoreCase("line"))
		{
			graph.macdgraph.doPaintLine(true);
		}
		else if (butName.equalsIgnoreCase("gannfan"))
		{
			GannFan gannfan = new GannFan(100, 100, 500, 500, graph.macdgraph);
			graph.macdgraph.addComponent(gannfan);
			graph.macdgraph.repaint();
		}
		else if (butName.equalsIgnoreCase("addnote"))
		{
			AddNotePanel addpanel = new AddNotePanel(graph);
			// FibonnciRetracement fibret = new
			// FibonnciRetracement(100,100,300,300);
			// macdgraph.addComponent(fibret);
		}
		else if (butName.equalsIgnoreCase("fib"))
		{
			// AddNotePanel addpanel = new AddNotePanel(this);
			FibonnciRetracement fibret = new FibonnciRetracement(100, 100, 300, 300, graph.macdgraph);
			graph.macdgraph.addComponent(fibret);
			graph.macdgraph.repaint();
		}
		else if (butName.equalsIgnoreCase("fibhor"))
		{
			// AddNotePanel addpanel = new AddNotePanel(this);
			FibonnciRetracementTime fibret = new FibonnciRetracementTime(100, 100, 300, 300, graph.macdgraph);
			// fibret.graphcomponent = graph.macdgraph;
			graph.macdgraph.addComponent(fibret);
			graph.macdgraph.repaint();
		}
		else if (butName.equalsIgnoreCase("horline"))
		{
			graph.macdgraph.doPaintLineHor(true);
		}
		else if (butName.equalsIgnoreCase("verline"))
		{
			graph.macdgraph.doPaintLineVer(true);
		}
		else if (butName.equalsIgnoreCase("buyarrow"))
		{
			graph.macdgraph.doPaintBuyArrow(true);
		}
		else if (butName.equalsIgnoreCase("sellarrow"))
		{
			graph.macdgraph.doPaintSellArrow(true);
		}
	}

	public void doReSizeInit()
	{
		repaint();
		int height = MainScreen.getSingleton().getHeight();
		int length = MainScreen.getSingleton().getWidth();
		if (height == (StockConstants.height + StockConstants.MYINC) && length == StockConstants.length)
			MainScreen.getSingleton().setSize(StockConstants.length + 5, StockConstants.height + StockConstants.MYINC + 5);
		else
			MainScreen.getSingleton().setSize(StockConstants.length, StockConstants.height + StockConstants.MYINC);
	}

	public void resetAd()
	{
		// // ln("in editorpane");
		try
		{
			String s = Utility.getUrlContent(StockConstants.ADURL + "?" + StockConstants.USERDETAIL);
			editorPane.updateContent(s);
			editorPane.repaint();
		}
		catch (Exception e)
		{
		}
	}

	public static void main(String[] args)
	{
		Runtime runtime = Runtime.getRuntime();
//		// ln("totmem=" + runtime.totalMemory());
//		// ln("freemem=" + runtime.freeMemory());

		File f = new File(StockConstants.INSTALL_DIR);
		f.mkdirs();
		f = new File(StockConstants.INSTALL_DIR_TEMP);
		f.mkdirs();
		Utility.createAllDirectory();

		File firsttime = new File(StockConstants.INSTALL_DIR + StockConstants.userDetailFile);
		File userdetail = new File(StockConstants.INSTALL_DIR_TEMP + StockConstants.userDetailFile);
		if (!firsttime.exists())
		{
			if (!userdetail.exists())
			{
				UserRegistration user = new UserRegistration();
				return;
			}
			else
			{
				try
				{
					ConfigUtility.loadUserDetail();
					String s = Utility.getPropertiesAsString(StockConstants.USERDETAIL);
					// Utility.saveContent(StockConstants.INSTALL_DIR +
					// StockConstants.userDetailFile, s);
					
					InstallScreen progress = InstallScreen.getSingleton();
					return;
				}
				catch (Exception e1)
				{
					// TODO Auto-generated catch block
					// e1.printStackTrace();
					return;
				}

			}
		}
		if (!userdetail.exists())
		{
			try
			{
				ConfigUtility.loadUserDetailFromInstall();
				String s = Utility.getPropertiesAsString(StockConstants.USERDETAIL);
				Utility.saveContent(StockConstants.INSTALL_DIR_TEMP + StockConstants.userDetailFile, s);
			}
			catch (Exception e1)
			{
				// TODO Auto-generated catch block
				// e1.printStackTrace();
			}

		}
		String str="";
		try
		{
			str = Utility.getFileContent(StockConstants.FirstTime_INI);
		/*	if(str.indexOf(StockConstants.CURRENTVERSION) == -1)
			{
				InstallScreen progress = InstallScreen.getSingleton();
				return;
				
			}
*/
		}
		catch (Exception e1)
		{
			// TODO Auto-generated catch block
	//		e1.printStackTrace();
		};
		
		// everything ok do normal processing
		Utility.setAllConstants();
		ConfigUtility.loadProperty();
		try
		{
			ConfigUtility.loadOtherProperty();
		}
		catch (Exception e)
		{
			return;
		}

		doOtherWork();
	}

	public static void doOtherWork()
	{
		copyOtherFile();
		StockConstants.EXPIRY = StockConstants.UPGRADEMSG;

		ToolScreen toolscreen = new ToolScreen();
		toolscreen.show();
		try
		{
			Thread.sleep(5000);
			toolscreen.dispose();
		}
		catch (Exception e)
		{
		}
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		StockConstants.length = (int) dim.getWidth() - 30;
		StockConstants.height = (int) dim.getHeight() - 100;
		// // ln("length=" + StockConstants.length);
		// // ln("width=" + StockConstants.height);
		getSingleton();
	}

	private static void copyOtherFile()
	{
		// not want right now
		if (true)
			return;

		File f = new File(StockConstants.INSTALL_DIR + "/" + "software.properties");
		if (!f.exists())
		{
			String[] files = { "strategycustmizedindicator.txt", "software.properties" };
			for (int i = 0; i < files.length; i++)
			{
				String file1content = Loader.returnFileContent(files[i]);
				if (file1content.equals(""))
					return;
				java.io.FileOutputStream out;
				try
				{
					out = new java.io.FileOutputStream(StockConstants.INSTALL_DIR + "/" + files[i]);
					out.write(file1content.getBytes());
					out.close();
				}
				catch (Exception e)
				{
				}
			}
		}
		// TODO Auto-generated method stub
	}

	public void mouseDragged(MouseEvent arg0)
	{
		
		// TODO Auto-generated method stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
	 */
	public void mouseMoved(MouseEvent arg0)
	{
		int i= arg0.getX();
		if (i > LEFT_END_POS - 2 && i < LEFT_END_POS + 5)
		{
			Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR);
		}
		else
		{
			Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
					
		}
		// TODO Auto-generated method stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seejava.awt.event.ComponentListener#componentResized(java.awt.event.
	 * ComponentEvent)
	 */
	public void componentResized(ComponentEvent arg0)
	{
		// ln("component resized");
		Dimension d = this.getSize();
		StockConstants.length = d.width;
		StockConstants.height = d.height;
		if(panel != null)
		{
			panel.resize(StockConstants.length - LEFT_END_POS, StockConstants.height - StockConstants.YPOSITIONFORGRAPH - StockConstants.YPOSITIONFORGRAPHGAP);
			panel.setBounds(LEFT_END_POS, StockConstants.YPOSITIONFORGRAPH, StockConstants.length - LEFT_END_POS, StockConstants.height - StockConstants.YPOSITIONFORGRAPH);
			
		}
		// TODO Auto-generated method stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ComponentListener#componentMoved(java.awt.event.ComponentEvent
	 * )
	 */
	public void componentMoved(ComponentEvent arg0)
	{
		// TODO Auto-generated method stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ComponentListener#componentShown(java.awt.event.ComponentEvent
	 * )
	 */
	public void componentShown(ComponentEvent arg0)
	{
		// TODO Auto-generated method stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seejava.awt.event.ComponentListener#componentHidden(java.awt.event.
	 * ComponentEvent)
	 */
	public void componentHidden(ComponentEvent arg0)
	{
		// TODO Auto-generated method stub
	}

	// @Override
	public void keyPressed(KeyEvent arg0)
	{
		int key = arg0.getKeyCode(); // keyboard code for the pressed key
		if (key == KeyEvent.VK_ENTER)
		{
			try
			{
				// Loader.loadAllData(StockConstants.STARTDATE,symbol.getText());
				MainScreen.getSingleton().setNewSymbol(symbol.getText().toUpperCase());
				// doReSize();
			}
			catch (Exception e1)
			{
				// TODO Auto-generated catch block
				// e1.printStackTrace();
			}
		}
		// TODO Auto-generated method stub
	}

	// @Override
	public void keyReleased(KeyEvent arg0)
	{
		// TODO Auto-generated method stub
	}
	public static int LEFT_END_POS=150;
	// @Override
	public void keyTyped(KeyEvent arg0)
	{
		// TODO Auto-generated method stub
	}

	public static int COMBOPOS=20;
	public void addTreeAdPanel()
	{
		try
		{
			jContentPane.remove(treepanel);
		}
		catch (Exception e)
		{

		}
		try
		{
		/*	JComboBox searchcombo = new JComboBox(Utility.getDifferntScanOption());
			searchcombo.setBounds(1, StockConstants.YPOSITIONFORGRAPH, LEFT_END_POS, COMBOPOS);
			jContentPane.add(searchcombo, null);
*/
			
			treepanel = new StockTree(LEFT_END_POS, StockConstants.height - StockConstants.YPOSITIONFORGRAPH, this);
			treepanel.setBounds(1, StockConstants.YPOSITIONFORGRAPH, LEFT_END_POS, StockConstants.height - StockConstants.YPOSITIONFORGRAPH);
			treepanel.addComponentListener(this);
			jContentPane.add(treepanel, null);

		}
		catch (Exception e)
		{
			treepanel = new StockTree(LEFT_END_POS, StockConstants.height - StockConstants.YPOSITIONFORGRAPH, this);
			treepanel.setBounds(1, StockConstants.YPOSITIONFORGRAPH, LEFT_END_POS, StockConstants.height - StockConstants.YPOSITIONFORGRAPH);
			jContentPane.add(treepanel, null);
			// e.printStackTrace();
		}
	}
}

/*
 * Created on Feb 24, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.livedata;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;

import com.stockfaxforu.component.Indicator;
import com.stockfaxforu.frontend.AlertScreen;
import com.stockfaxforu.frontend.IndexComboBox;
import com.stockfaxforu.frontend.PanelForGraph;
import com.stockfaxforu.frontend.PanelForGraphFrame;
import com.stockfaxforu.frontend.PanelForGraphImpl;
import com.stockfaxforu.frontend.PanelForGraphNifty;
import com.stockfaxforu.frontend.ShowContentWindow;
import com.stockfaxforu.query.DataBaseInMemory;
import com.stockfaxforu.scan.frontend.CheckBoxGridPanelScan;
import com.stockfaxforu.scan.frontend.GridPanelForScan;
import com.stockfaxforu.scan.frontend.StockFilterAlert;
import com.stockfaxforu.scan.frontend.StockLiveFilter;
import com.stockfaxforu.scan.util.ScanUtility;
import com.stockfaxforu.util.IndexUtility;
import com.stockfaxforu.util.StockConstants;
import com.stockfaxforu.util.Utility;
import java.util.*;
//SplitPaneDemo itself is not a visible component.
public class ShowLiveMarket extends JFrame implements ShowLiveMarketInterface,ListSelectionListener, KeyListener, ActionListener, MouseListener, TableModelListener,WindowListener,PanelForGraphImpl
{
	public LiveMarketTableModel tableModel;
	private JPanel panelforgrapha;
	private JList list;
	private JList list1;
	private JTextField code;
	private JTextField value;
	public javax.swing.JPanel jContentPane;
	Vector stockCode;
	Vector stockName;
	int x1;
	int y1;
	JTable table = null;
//	String[][] dataValues = null;
//	String[] columnNames = { "Symbol", "Price", "Per Diff", "Prev Ticker Price", "Prev Close" };
	Vector columnNames = new Vector();
	Vector unchangedcolumnNames = new Vector();

	JScrollPane scrollPane = null;
	JCheckBox[] checkboxes = null;
	int noofpanel;
//	PanelForGraphNifty panenifty;
	private static ShowLiveMarket showlivemarket=null;
	PanelForGraph panel1,panel2;
	HashMap strategyMap = new HashMap();
	
	public static String ID = "ID";
	public static String Formula = "Formula";
	public static String Alert = "Alert";
	public static String Repeat = "Repeat";
	
	public HashMap globalIntradayData = new HashMap();
	
	
	public static void main(String[] args)
	{

		
		
		ShowLiveMarket s = new ShowLiveMarket(1000,500,1);
		
	}
	public ShowLiveMarket(int x1, int y1,int noofpanel)
	{
		Image im = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("image/toolicon.jpg"));
		this.setIconImage(im);
		this.setTitle("Live Intraday Quote");
//		this.symbol = symbol;
		setBackground(Color.lightGray);
		this.setSize(x1,y1);
		setBackground(Color.lightGray);

		alertscreen = AlertScreen.getSingleton();
		
		this.move(50,50);
		this.setContentPane(getJContentPane());
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			this.x1 = x1;
		this.y1 = y1;
		this.noofpanel = noofpanel;
//		this.panenifty = panenifty;
//		Image im = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("image/toolicon.jpg"));
//		this.setIconImage(im);
		setBackground(Color.lightGray);
		this.setSize(x1, y1);
		this.x1 = x1;
		this.y1 = y1;
		setBackground(Color.lightGray);
//		this.move(50, 50);
//		this.setContentPane(getJContentPane());
//		this.setVisible(true);
//		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//		this.add(jContentPane);
		this.addWindowListener(this);
		
		String[] s ={ "Symbol","Time", "Price", "Volume" };
		
//		String[] s ={ "Symbol","Time", "Price", "Change", "Open", "High","Low","Volume" };
		for(int i=0;i<s.length;i++)
		{
			unchangedcolumnNames.add(s[i]);
			columnNames.add(s[i]);
			
		}
		runnable = new RunLiveData(this);
		thread = new Thread(runnable);
		thread.start();
	//	runnable.run();
	}
	int ypos, xpos = 10;
	private javax.swing.JPanel getJContentPane()
	{
		if(jContentPane == null) 
		{
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
		}
		addButtons();
	//	xpos = xpos + 30;
		
		combo = new IndexComboBox(IndexUtility.getCategories());
		combo.setToolTipText("Select Category for which you want to see live quote and do scan");
		combo.setBounds(xpos,ypos,100,20);
		jContentPane.add(combo,null);
		combo.setName("indexcombo");
		combo.addActionListener(this);
		
		ypos = ypos + 35;

		return jContentPane;
	}
	public JButton[] icon = new JButton[10];
	AlertScreen alertscreen=null;
	public JComboBox combo;
	public void addButtons()
	{
		String[] buttonName = { "alert", "liveindicator","help" };
		String[] imageName = { "startfetching.jpg", "histprc.jpg","help.jpg" };
		String[] desc = { "Manage Alert", "Live Data Query","help" };
		int iconsize = 20;
		for (int i = 0; i < imageName.length; i++)
		{
			String s = "image/purelive/" + imageName[i];
			ImageIcon cup = new ImageIcon(ClassLoader.getSystemResource(s));
			icon[i] = new JButton(cup);
			icon[i].setBounds(xpos, 0, iconsize, iconsize);
			icon[i].setName(buttonName[i]);
			icon[i].setToolTipText(desc[i]);
			jContentPane.add(icon[i],null);
			icon[i].addActionListener(this);
			xpos = xpos + iconsize + 4;
		}
	}
	//Listens to the list
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
		code.setText((String) list.getSelectedValue());
		value.setText((String) list1.getSelectedValue());
	}
	public JPanel getSplitPane()
	{
		return jContentPane;
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
	{}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	Thread thread = null;
	RunLiveData runnable = null;
	public String selcat="nifty";
	public void actionPerformed(ActionEvent arg0)
	{
		Object obj =  arg0.getSource();
		
		if (obj instanceof JButton)
		{
			String name = ((JButton) obj).getName();
			if (name.equals("Graph"))
			{
				int i = table.getSelectedRow();
				String symbol = "";//dataValues[i][0];
				PanelForGraphFrame pframe = new PanelForGraphFrame(symbol);
			}
			else if (name.equals("alert"))
			{
				 alertscreen.show();

			}
			else if (name.equals("liveindicator"))
			{
		//		JFrame f = new JFrame();
				StockLiveFilter newscan = new StockLiveFilter(this,1000,1000);
//		
			}
			else if (name.equals("stopfetching"))
			{
				try
				{
					thread.suspend();
				}
				catch (Exception e)
				{}
			}
			else if (name.equals("help"))
			{
				ShowContentWindow searc = new ShowContentWindow(StockConstants.INSTALL_DIR + "/help","intradayscan.html");

			}
			
			
		}
		else if (obj instanceof JComboBox)
		{
			System.out.println("combox event");
		
			
			
		}
		// TODO Auto-generated method stub
	}
	/**
	 * @param stockname
	 * @return
	 */
	public void mouseClicked(MouseEvent arg0)
	{
		
//		System.out.println("moueeeeeeeeeeeeeee");
		
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
	 * @see javax.swing.event.TableModelListener#tableChanged(javax.swing.event.TableModelEvent)
	 */
	public void tableChanged(TableModelEvent arg0)
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
		//destroyThread();
		//thread.suspend();
		// TODO Auto-generated method stub
	//	System.out.println("closing window");
		runnable.runthread=false;
		runnable=null;
		thread=null;
	}
	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowIconified(java.awt.event.WindowEvent)
	 */
	public void windowIconified(WindowEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowDeiconified(java.awt.event.WindowEvent)
	 */
	public void windowDeiconified(WindowEvent arg0)
	{
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
		// TODO Auto-generated method stub
//		this.toFront();
	}
	public void doReSize()
	{
		repaint();
		int height = this.getHeight();
		int length = this.getWidth();
		if (height == (this.y1 ) && length == this.x1)
			this.setSize(this.x1 + 5, this.y1+ 5);
		else
			this.setSize(this.x1, this.y1 );
		this.repaint();
	}
	/* (non-Javadoc)
	 * @see com.stockfaxforu.frontend.PanelForGraphImpl#searchStockUpdate(java.lang.String)
	 */
	public void searchStockUpdate(String symbol)
	{
		panel1.macdgraph.setSymbol(symbol);
		panel2.macdgraph.setSymbol(symbol);
	}
	/* (non-Javadoc)
	 * @see com.stockfaxforu.frontend.PanelForGraphImpl#destroyThread()
	 */
	public void destroyThread()
	{
		
		runnable=null;
		if(thread != null)
		{
				thread.suspend();
		}
		thread=null;
		// TODO Auto-generated method stub
		
	}
	public void addIndicator(Indicator selInd)
	{
		// TODO Auto-generated method stub
		
	}
	public void drawStrategy(Vector newVector)
	{
		// TODO Auto-generated method stub
		
	}
	public void executeCustmizedIndicator(String fileName, String formula)
	{
		// TODO Auto-generated method stub
		
	}
	public Vector getInputVector()
	{
		// TODO Auto-generated method stub
		return null;
	}
	public HashMap getStrategyHashMap()
	{
		// TODO Auto-generated method stub
		return null;
	}
	public Vector getStrategyVector()
	{
		// TODO Auto-generated method stub
		return null;
	}
	public void setDate(int year, String startDate, String endDate)
	{
		// TODO Auto-generated method stub
		
	}
	public void setGraphType(String selName)
	{
		// TODO Auto-generated method stub
		
	}
	public void setResolution(String selName)
	{
		// TODO Auto-generated method stub
		
	}
	public void setYear(String selName)
	{
		// TODO Auto-generated method stub
		
	}
	public void setZooming(String selName)
	{
		// TODO Auto-generated method stub
		
	}
//	@Override
	public HashMap getLiveDataHashMap()
	{
		// TODO Auto-generated method stub
		return this.globalIntradayData;
	}
	
}

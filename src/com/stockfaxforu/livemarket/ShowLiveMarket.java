/*
 * Created on Feb 24, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.livemarket;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;

import com.stockfaxforu.component.Indicator;
import com.stockfaxforu.frontend.PanelForGraph;
import com.stockfaxforu.frontend.PanelForGraphFrame;
import com.stockfaxforu.frontend.PanelForGraphImpl;
import com.stockfaxforu.frontend.PanelForGraphNifty;
import com.stockfaxforu.query.DataBaseInMemory;
import com.stockfaxforu.scan.frontend.CheckBoxGridPanelScan;
import com.stockfaxforu.scan.frontend.GridPanelForScan;
import com.stockfaxforu.scan.util.ScanUtility;
import com.stockfaxforu.util.IndexUtility;
import com.stockfaxforu.util.Utility;
import java.util.*;
//SplitPaneDemo itself is not a visible component.
public class ShowLiveMarket extends JPanel implements ListSelectionListener, KeyListener, ActionListener, MouseListener, TableModelListener,WindowListener,PanelForGraphImpl
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
	String[][] dataValues = null;
	String[] columnNames = { "Symbol", "Price", "Per Diff", "Prev Ticker Price", "Prev Close" };
	JScrollPane scrollPane = null;
	JCheckBox[] checkboxes = null;
	int noofpanel;
//	PanelForGraphNifty panenifty;
	private static ShowLiveMarket showlivemarket=null;
	PanelForGraph panel1,panel2;
	public static void main(String[] args)
	{
		 new ShowLiveMarket(1000,500,1);

	}
	public ShowLiveMarket(int x1, int y1,int noofpanel)
	{
		//		super(null);
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
		getJContentPane();
//		this.add(jContentPane);
		runnable = new GetLiveData(this);
		thread = new Thread(runnable);
		thread.start();

	}
	int ypos, xpos = 10;
	private javax.swing.JPanel getJContentPane()
	{
		this.setLayout(null);	
//		addButtons();
//		ypos = ypos + 35;
		if(this.noofpanel==1)
		{
			panel1 = new PanelForGraph(0,0,x1/2,y1,com.stockfaxforu.util.StockConstants.SELECTED_STOCK);
			panel1.setBounds(0,0,x1/2,y1);
			add(panel1,null);
			
		}
		else
		{
			panel1 = new PanelForGraph(0,0,x1/2,y1/2,com.stockfaxforu.util.StockConstants.SELECTED_STOCK);
			panel1.setBounds(0,0,x1/2,y1/2);
			add(panel1,null);
			panel2 = new PanelForGraph(0,y1/2+1,x1/2,y1/2,com.stockfaxforu.util.StockConstants.SELECTED_STOCK);
			panel2.setBounds(0,y1/2+1,x1/2,y1/2);
			add(panel2,null);
			
		}

		return jContentPane;
	}
	public JButton[] icon = new JButton[10];
	

	public void addButtons()
	{
		String[] buttonName = { "startfetching", "stopfetching", "addremovestock" };
		String[] imageName = { "startfetching.jpg", "stopfetching.jpg", "find_small.jpg" };
		String[] desc = { "Start Fetching Live Quote", "Stop Fetching Live Quote", "Add Remove Stock" };
		int iconsize = 30;
		for (int i = 0; i < imageName.length; i++)
		{
			String s = "image/purelive/" + imageName[i];
			ImageIcon cup = new ImageIcon(ClassLoader.getSystemResource(s));
			icon[i] = new JButton(cup);
			icon[i].setBounds(xpos, ypos, iconsize, iconsize);
			icon[i].setName(buttonName[i]);
			icon[i].setToolTipText(desc[i]);
			this.add(icon[i], null);
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
	Runnable runnable = null;
	public void actionPerformed(ActionEvent arg0)
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
			else if (name.equals("startfetching"))
			{
				if (thread == null)
				{
					runnable = new GetLiveData(this);
					thread = new Thread(runnable);
					thread.start();
				}
				else
				{
					try
					{
						thread.resume();
					}
					catch (Exception e)
					{}
				}
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
		}
		// TODO Auto-generated method stub
	}
	/**
	 * @param stockname
	 * @return
	 */
	public void mouseClicked(MouseEvent arg0)
	{}
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
		thread.suspend();
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowClosed(java.awt.event.WindowEvent)
	 */
	public void windowClosed(WindowEvent arg0)
	{
		thread.suspend();
		// TODO Auto-generated method stub
		
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
		if(panel1 != null)
			panel1.macdgraph.destroyThread();
		if(panel2 != null)
			panel1.macdgraph.destroyThread();
		
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
}

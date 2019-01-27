package com.stockfaxforu.buyselladvisior;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.stockfaxforu.config.ConfigUtility;
import com.stockfaxforu.frontend.BuySellPanelTree;
import com.stockfaxforu.frontend.PanelForGraph;
import com.stockfaxforu.scan.frontend.BuySellAdvisorResult;
import com.stockfaxforu.scan.frontend.MarketSnapShot;
import com.stockfaxforu.util.StockConstants;

public class BuySellAdvisorScreen extends JFrame implements ComponentListener,WindowListener,ActionListener
{
	Button b;
	public PanelForGraph macd;
	String symbol;
	boolean intraday=false;
	public BuySellAdvisorResult panelresult;
	public BuySellAdvisorScreen()
	{
		this.intraday=intraday;
		Image im = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("image/toolicon.jpg"));
		this.setIconImage(im);
		this.setTitle("BUY/Sell Advisor Screen");
		setBackground(Color.lightGray);

		this.setContentPane(getJContentPane());	
		addComponentListener(this);
		addWindowListener(this);
		length = StockConstants.length;
		height = StockConstants.height;
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//		this.macd.doIntraday();
		this.setSize(StockConstants.length, StockConstants.height);

		this.setVisible(true);
	}
	JPanel jContentPane=null;
	JPanel jContentPane1=null;
	JButton future;
	JComboBox expiryDateCombo;
	public BuySellPanelTree treepanel;
	int length ;
	int height;
	private javax.swing.JPanel getJContentPane() 
	{
		if(jContentPane == null) 
		{
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
		}
		
	
		
		treepanel = new BuySellPanelTree(0, 0, this);
		treepanel.setBounds(0, 0, 250, StockConstants.height );
		jContentPane.add(treepanel, null);

		panelresult = new BuySellAdvisorResult(StockConstants.length-250 , StockConstants.height ,MarketSnapShot.HIST);
		//		panel = new Scan(StockConstants.length - 200, StockConstants.height - YPOSITIONFORGRAPHGAP);
		panelresult.setOpaque(true);
		panelresult.setVisible(true);
		panelresult.setBounds(251, 0, StockConstants.length-250, StockConstants.height );

				jContentPane.add(panelresult, null);

		return jContentPane;
		
	}

	public void componentResized(ComponentEvent ce)
	{
		
	}
	
	public void componentHidden(ComponentEvent ce) {;}
	public void componentShown(ComponentEvent ce) {;}
	public void componentMoved(ComponentEvent ce) {;}

	public static void main(String[] args)
	{
		new BuySellAdvisorScreen();
	}
	public void windowActivated(WindowEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}
	public void windowClosed(WindowEvent arg0)
	{
//		// ln("windowClosed event called");
	//	this.macd.destroyThread();
		// TODO Auto-generated method stub
		
	}
	public void windowClosing(WindowEvent arg0)
	{
	}
	public void windowDeactivated(WindowEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}
	public void windowDeiconified(WindowEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}
	public void windowIconified(WindowEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}
	public void windowOpened(WindowEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}
	public void setNewSymbol(String name)
	{
	}

	public void actionPerformed(ActionEvent e) 
	{
		
	}
}


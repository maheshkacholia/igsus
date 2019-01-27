package com.stockfaxforu.frontend;
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
import com.stockfaxforu.frontend.PanelForGraph;
import com.stockfaxforu.util.StockConstants;

public class IntradayPanelFor4GraphFrame extends JFrame implements ComponentListener,WindowListener,ActionListener
{
	Button b;
	public PanelFor4GraphIntraday macd;
	String symbol;
	boolean intraday=false;

	public IntradayPanelFor4GraphFrame()
	{
		this.intraday=intraday;
		Image im = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("image/toolicon.jpg"));
		this.setIconImage(im);
		this.setTitle("Intraday Screen");
		setBackground(Color.lightGray);
		length = StockConstants.length;
		height = StockConstants.height;
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//		this.macd.doIntraday();
		this.setSize(StockConstants.length, StockConstants.height);

		this.setContentPane(getJContentPane());	
		addComponentListener(this);
		addWindowListener(this);
	
		this.setVisible(true);
	}
	JPanel jContentPane=null;
	JPanel jContentPane1=null;
	JButton future;
	JComboBox expiryDateCombo;
	public IntradayStockTree treepanel;
	int length ;
	int height;
	int ystart=0;
	private javax.swing.JPanel getJContentPane() 
	{
		if(jContentPane == null) 
		{
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
		}
		this.symbol = "ACC";
		macd = new PanelFor4GraphIntraday(0, ystart, StockConstants.length,StockConstants.height,true,true);
		macd.setBounds(0, 0, StockConstants.length, StockConstants.height);
		jContentPane.add(macd,null);
		return jContentPane;
		
	}

	public void componentResized(ComponentEvent ce)
	{
	//	// ln("Window resized");
		Dimension d = this.getSize();
		this.remove(macd);
//		macd = new PanelForGraph(0, 0, d.width-30,d.height-30,"AAPL");
		this.getContentPane().add(macd,BorderLayout.CENTER);

		setBounds(0,0,d.width,d.height);
		//macd.resize(d.width,d.height-40);


//		this.add(macd,BorderLayout.CENTER);
		setVisible(true);
		
	}
	
	public void componentHidden(ComponentEvent ce) {;}
	public void componentShown(ComponentEvent ce) {;}
	public void componentMoved(ComponentEvent ce) {;}

	public static void main(String[] args)
	{
		new IntradayPanelFor4GraphFrame();
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
		try
		{
		//	// ln("windowClosing event called");

			this.macd.destroyThread();
			
		}
		catch(Exception e)
		{
			
		}		
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

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}


package com.stockfaxforu.frontend;
import java.awt.*;
import java.awt.event.*;

import javax.swing.JFrame;

import com.stockfaxforu.frontend.PanelForGraph;
import com.stockfaxforu.util.StockConstants;

public class PanelForGraphFrame extends JFrame implements ComponentListener,WindowListener
{
	Button b;
	public PanelForGraph macd;
	String symbol;
	boolean intraday=false;

	public PanelForGraphFrame(String symbol,boolean intraday)
	{
		this.intraday=intraday;
		Image im = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("image/toolicon.jpg"));
		this.setIconImage(im);
		this.symbol = symbol;
		setBackground(Color.lightGray);

		this.setTitle("Graph For "+symbol);

		getContentPane().setLayout(new BorderLayout());
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int length = (int) dim.getWidth() - 30;
		int height = (int) dim.getHeight() - 100;

		macd = new PanelForGraph(0, 0,length ,height-50,this.symbol,true);
		this.getContentPane().add(macd,BorderLayout.CENTER);
		setBounds(0,0,length,height);
		setVisible(true);
			
		addComponentListener(this);
		addWindowListener(this);

		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//		this.macd.doIntraday();

	}
	public PanelForGraphFrame(String symbol)
	{
		Image im = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("image/toolicon.jpg"));
		this.setIconImage(im);
		this.symbol = symbol;
		setBackground(Color.lightGray);

		this.setTitle("Graph For "+symbol);

		getContentPane().setLayout(new BorderLayout());
		
		macd = new PanelForGraph(0, 0, StockConstants.length,StockConstants.height,this.symbol);
		this.getContentPane().add(macd,BorderLayout.CENTER);
		setBounds(0,0,StockConstants.length,StockConstants.height);
		setVisible(true);
			
		addComponentListener(this);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		

	}

	public void componentResized(ComponentEvent ce)
	{
	//	// ln("Window resized");
		Dimension d = this.getSize();
		this.remove(macd);
//		macd = new PanelForGraph(0, 0, d.width-30,d.height-30,"AAPL");
		this.getContentPane().add(macd,BorderLayout.CENTER);

		setBounds(0,0,d.width,d.height);
		macd.resize(d.width-20,d.height-40);


//		this.add(macd,BorderLayout.CENTER);
		setVisible(true);
		
	}
	
	public void componentHidden(ComponentEvent ce) {;}
	public void componentShown(ComponentEvent ce) {;}
	public void componentMoved(ComponentEvent ce) {;}

	public static void main(String[] args)
	{
		new PanelForGraphFrame("AAPL");
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
}


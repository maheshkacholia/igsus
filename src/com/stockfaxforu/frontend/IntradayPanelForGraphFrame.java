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

public class IntradayPanelForGraphFrame extends JFrame implements ComponentListener,WindowListener,ActionListener
{
	Button b;
	public PanelForGraph macd;
	String symbol;
	boolean intraday=false;

	public IntradayPanelForGraphFrame()
	{
		this.intraday=intraday;
		Image im = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("image/toolicon.jpg"));
		this.setIconImage(im);
		this.setTitle("Intraday Screen");
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
	public IntradayStockTree treepanel;
	int length ;
	int height;
	int ystart=25;
	private javax.swing.JPanel getJContentPane() 
	{
		if(jContentPane == null) 
		{
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
		}
		
		int xinc = 10;
		int yinc=0;
	
//		xinc = xinc + 20;
		Vector expiryDate = ConfigUtility.getFutureExpiry();
		expiryDateCombo = new JComboBox(expiryDate);
		expiryDateCombo.setFont(new Font("Arial", Font.PLAIN,9));
		expiryDateCombo.setToolTipText(" Select Expiry Date ");
		expiryDateCombo.setBounds(xinc, yinc, 85, 20);
		expiryDateCombo.setName("cursortype");
		jContentPane.add(expiryDateCombo, null);
		expiryDateCombo.addActionListener(this);

		xinc = xinc +90;
		
		ImageIcon cup1 = new ImageIcon(ClassLoader.getSystemResource("image/future.jpg"));
		future = new JButton(cup1);
	//	custom.setBackground(Color.black);
		future.setBounds(xinc, yinc, 20, 20);
		future.setName("future");
		future.setForeground(Color.white);
		future.setToolTipText(" Future Intraday");
		jContentPane.add(future, null);
		future.addActionListener(this);

		
		
		
		xinc = xinc + 20;
	
		//ImageIcon 
		cup1 = new ImageIcon(ClassLoader.getSystemResource("image/intraday.jpg"));
		JButton intradaybut = new JButton(cup1);
		intradaybut.setBackground(Color.black);
		intradaybut.setBounds(xinc, yinc, 20, 20);
		intradaybut.setName("intraday");
		intradaybut.setForeground(Color.white);
		intradaybut.setToolTipText(" Intraday Chart ");
		jContentPane.add(intradaybut, null);
		intradaybut.addActionListener(this);

		xinc = xinc + 20;

		cup1 = new ImageIcon(ClassLoader.getSystemResource("image/help.jpg"));
		JButton help = new JButton(cup1);
		help.setBounds(xinc, yinc, 20, 20);
		help.setName("help");
		help.setToolTipText(" Help ");
		jContentPane.add(help);
		help.addActionListener(this);
	
		
		
		
		treepanel = new IntradayStockTree(0, 0, this);
		treepanel.setBounds(0, ystart, MainScreen.LEFT_END_POS, StockConstants.height );
		jContentPane.add(treepanel, null);

		
//		panel = new PanelForGraph(0, 0, StockConstants.length - 200, StockConstants.height - StockConstants.YPOSITIONFORGRAPH
//				- StockConstants.YPOSITIONFORGRAPHGAP, StockConstants.SELECTED_STOCK);
//		panel.setBounds(201, StockConstants.YPOSITIONFORGRAPH, StockConstants.length - 200, StockConstants.height
//				- StockConstants.YPOSITIONFORGRAPH);

//		macd =  new PanelForGraph(0, 50, StockConstants.length - 200,StockConstants.height-50-50,name,true);
//		macd.setBounds(201,50, StockConstants.length, StockConstants.height -50);
		this.symbol = "ACC";
		macd = new PanelForGraph(0, ystart, StockConstants.length - MainScreen.LEFT_END_POS,StockConstants.height-50-ystart,"ACC",true);
		macd.setBounds(MainScreen.LEFT_END_POS, ystart, StockConstants.length, StockConstants.height -50);
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

		setBounds(0,ystart,d.width,d.height);
		macd.resize(d.width-MainScreen.LEFT_END_POS,d.height-40-ystart);


//		this.add(macd,BorderLayout.CENTER);
		setVisible(true);
		
	}
	
	public void componentHidden(ComponentEvent ce) {;}
	public void componentShown(ComponentEvent ce) {;}
	public void componentMoved(ComponentEvent ce) {;}

	public static void main(String[] args)
	{
		new IntradayPanelForGraphFrame();
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
	public void setNewSymbol(String name)
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
			macd.symbol = name;
			macd.macdgraph.symbol=name;
			String selName = (String) macd.intradayDateBox.getSelectedItem();
			
			macd.setHistoricalIntradayDate(selName);
			macd.macdgraph.setForIntraDay();
	}

	public void actionPerformed(ActionEvent e) 
	{
		Object o = (Object) e.getSource();
		if(o instanceof JButton)
		{
			String name1 = ((JButton)o).getName();
			if(name1.equalsIgnoreCase("intraday"))
			{
				macd.symbol = this.symbol;
				macd.macdgraph.symbol=this.symbol;
				String selName = (String) macd.intradayDateBox.getSelectedItem();
				
				macd.setHistoricalIntradayDate(selName);
				macd.macdgraph.setForIntraDay();
				
			}
			else if (name1.equalsIgnoreCase("future"))
			{
				macd.symbol = this.symbol;
				macd.macdgraph.symbol=this.symbol;
				String selName = (String) expiryDateCombo.getSelectedItem();
				
		//		macd.setHistoricalIntradayDate(selName);
				macd.macdgraph.setForIntraDayFuture(selName);

				
			}
			else if (name1.equalsIgnoreCase("help"))
			{
				ShowContentWindow searc = new ShowContentWindow(StockConstants.INSTALL_DIR + "/help","intraday.html");
				
			}
		
		}
		
		// TODO Auto-generated method stub
		
	}
}


/*
 * Created on May 3, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.trading;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.stockfaxforu.component.Indicator;
import com.stockfaxforu.formulabuilder.MessageDiaglog;
import com.stockfaxforu.frontend.PanelForGraphImpl;
import com.stockfaxforu.frontend.SearchStock;
import com.stockfaxforu.scan.util.ScanUtility;
import com.stockfaxforu.util.StockConstants;
import com.stockfaxforu.util.Utility;

/**
 * @author sdeopura
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class BuySellStockScreen extends JFrame implements ActionListener,PanelForGraphImpl
{

	/**
	 * 
	 */
	public BuySellStockScreen()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	private JButton cancelButton;
	private JButton submitButton;
	private JTextField brokargeText;
	private JComboBox buySellCombo;
	private JTextField qtyText;
	private JTextField dateText;
	private JTextField priceText;
	JPanel jContentPane=null;
	String symbol=null;
	ShowTradingScreen show=null;
	public BuySellStockScreen(String symbol,ShowTradingScreen show)
	{
		Image im = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("image/toolicon.jpg"));
		this.setIconImage(im);
		this.setTitle("Buy/Sell Screen");
		this.symbol = symbol;
		setBackground(Color.lightGray);
		this.setSize(350,300);
		setBackground(Color.lightGray);

		this.move(250,200);
		this.setContentPane(getJContentPane());
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.show = show;
	}
	JLabel lable1;
	private javax.swing.JPanel getJContentPane() 
	{
		if(jContentPane == null) 
		{
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
		}

		int xpos=10;
		int ypos = 20;
		String stockDesc = Utility.getStockDes(this.symbol);
		HashMap hs = ScanUtility.getSymbolDetail(this.symbol);
		
		JLabel lable = new JLabel("Symbol " );
		lable.setFont(new Font("Arial",Font.BOLD,12));
		lable.setForeground(Color.black);
		lable.setBounds(xpos,ypos,100,20);	
		jContentPane.add(lable,null);
		
		xpos = xpos + 150;
		lable1 = new JLabel(this.symbol);
		lable1.setFont(new Font("Arial",Font.BOLD,12));
		lable1.setForeground(Color.black);
		lable1.setBounds(xpos,ypos,100,20);	
		jContentPane.add(lable1,null);
		
		ImageIcon cup1 = new ImageIcon(ClassLoader.getSystemResource("image/find_small.jpg"));
		JButton find = new JButton(cup1);
	//	find.setBackground(StockConstants.graphbackgroundcolor);
		find.setBounds(xpos + 105, ypos, 20, 20);
		find.setName("find");
		find.setForeground(Color.white);
		find.setToolTipText(" Find Stock ");
		find.addActionListener(this);
		jContentPane.add(find,null);

		
		ypos = ypos + 30;
		xpos = 10;

		JLabel lable2 = new JLabel("Current price  " );
		lable2.setFont(new Font("Arial",Font.BOLD,12));
		lable2.setForeground(Color.black);
		lable2.setBounds(xpos,ypos,100,20);	
		jContentPane.add(lable2,null);
		
		xpos = xpos +150;
		priceText = new JTextField(hs.get(ScanUtility.close).toString());
		priceText.setFont(new Font("Arial",Font.BOLD,12));
		priceText.setForeground(Color.black);
		priceText.setBounds(xpos,ypos,100,20);	
		jContentPane.add(priceText,null);
		
		ypos = ypos + 30;
		xpos = 10;

		JLabel lable3 = new JLabel("Date  " );
		lable3.setFont(new Font("Arial",Font.BOLD,12));
		lable3.setForeground(Color.black);
		lable3.setBounds(xpos,ypos,100,20);	
		jContentPane.add(lable3,null);
		
		xpos = xpos +150;
		dateText = new JTextField(ScanUtility.Date);
		dateText.setFont(new Font("Arial",Font.BOLD,12));
		dateText.setForeground(Color.black);
		dateText.setBounds(xpos,ypos,100,20);	
		jContentPane.add(dateText,null);


		ypos = ypos + 30;
		xpos = 10;

		JLabel lable5 = new JLabel("Buy/Sell  " );
		lable5.setFont(new Font("Arial",Font.BOLD,12));
		lable5.setForeground(Color.black);
		lable5.setBounds(xpos,ypos,100,20);	
		jContentPane.add(lable5,null);
		
		xpos = xpos +150;
		String[] buysellarray = {"Buy","Sell"};
		buySellCombo = new JComboBox(buysellarray);
		buySellCombo.setFont(new Font("Arial",Font.BOLD,12));
		buySellCombo.setForeground(Color.black);
		buySellCombo.setBounds(xpos,ypos,100,20);	
		jContentPane.add(buySellCombo,null);

	/*	ypos = ypos + 30;
		xpos = 10;

		JLabel lable6 = new JLabel("Brokarage(%)" );
		lable6.setFont(new Font("Arial",Font.BOLD,12));
		lable6.setForeground(Color.black);
		lable6.setBounds(xpos,ypos,100,20);	
		jContentPane.add(lable6,null);
		
		xpos = xpos +150;
		brokargeText = new JTextField();
		brokargeText.setFont(new Font("Arial",Font.PLAIN,12));
		brokargeText.setForeground(Color.black);
		brokargeText.setBounds(xpos,ypos,100,20);	
		jContentPane.add(brokargeText,null);
*/
		ypos = ypos + 30;
		
		xpos = 10;

		JLabel lable7 = new JLabel("Qty" );
		lable7.setFont(new Font("Arial",Font.BOLD,12));
		lable7.setForeground(Color.black);
		lable7.setBounds(xpos,ypos,100,20);	
		jContentPane.add(lable7,null);
		
		xpos = xpos +150;
		qtyText = new JTextField();
		qtyText.setFont(new Font("Arial",Font.PLAIN,12));
		qtyText.setForeground(Color.black);
		qtyText.setBounds(xpos,ypos,100,20);	
		jContentPane.add(qtyText,null);

		ypos = ypos + 50;
		
		xpos = 10;

		
		submitButton = new JButton("Submit");
		submitButton.setName("submit");
		submitButton.setFont(new Font("Arial",Font.BOLD,12));
		submitButton.setForeground(Color.black);
		submitButton.setBounds(xpos,ypos,100,20);	
		jContentPane.add(submitButton,null);
		submitButton.addActionListener(this);

		xpos = xpos +150;
		cancelButton = new JButton("Cancel");
		cancelButton.setFont(new Font("Arial",Font.BOLD,12));
		cancelButton.setForeground(Color.black);
		cancelButton.setBounds(xpos,ypos,100,20);	
		cancelButton.addActionListener(this);
		cancelButton.setName("cancel");
		
		jContentPane.add(cancelButton,null);
		
		
		return jContentPane;				
	}


	public static void main(String[] args)
	{
		BuySellStockScreen buysell = new BuySellStockScreen("DLF",null);
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent arg0)
	{
		JButton but=null;
		Object o = arg0.getSource();
		if(o instanceof JButton)
			but = (JButton)o;
		else
			return;
		
		String name = but.getName();
		if(name.equalsIgnoreCase("find"))
		{
			SearchStock d = new SearchStock(this);

		}
		else if(name.equalsIgnoreCase("submit"))
		{
			StringBuffer sb = new StringBuffer();
			String symbol = this.symbol;
			String price = priceText.getText();
			String buysell = buySellCombo.getSelectedItem().toString();
			String qty = qtyText.getText();
			String date = dateText.getText();
//			String broString = brokargeText.getText();
			
			try
			{
				float f =  Float.parseFloat(price);
				if(f <= 0)
					throw new Exception();
			}
			catch(Exception e)
			{
				MessageDiaglog msg = new MessageDiaglog("Enter a positive number in price field");
				return;
			}
			
			try
			{
				int f =  Integer.parseInt(qty);
				if(f <= 0 )
					throw new Exception();
			}
			catch(Exception e)
			{
				MessageDiaglog msg = new MessageDiaglog("Enter a positive integer number in qty field");
				return;
			}

			
			TradingDiary.saveTransaction(symbol+","+date+","+buysell+","+price+","+qty);
			this.dispose();
			if(show != null)
			{
				show.dispose();
				ShowTradingScreen newscan = new ShowTradingScreen(1000, 1000);

			}
		}
		else if (name.equalsIgnoreCase("cancel"))
		{
			this.dispose();
		}
			
		// TODO Auto-generated method stub
		
	}
	public void addIndicator(Indicator selInd)
	{
		// TODO Auto-generated method stub
		
	}
	public void destroyThread()
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
	public void searchStockUpdate(String symbol)
	{
		this.symbol = symbol;
		String stockDesc = Utility.getStockDes(this.symbol);
		HashMap hs = ScanUtility.getSymbolDetail(this.symbol);
		lable1.setText(this.symbol);
		priceText.setText(hs.get(ScanUtility.close).toString());
		

		// TODO Auto-generated method stub
		
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

/*
 * Created on Feb 18, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.frontend;

import java.awt.Color;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.stockfaxforu.component.Indicator;
import com.stockfaxforu.livemarket.ShowLiveMarket;
import com.stockfaxforu.util.StockConstants;

/**
 * @author sdeopura
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class PanelForGraphNifty extends JPanel implements PanelForGraphImpl
{
	private JScrollPane listScrollPane;
	/**
	 * This is the default constructor
	 */
	PanelForGraph panel1;
	PanelForGraph panel2;
	public int x1,x2,y1,y2;
	public ShowLiveMarket panel =null;
	public PanelForGraphNifty()
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
	public PanelForGraphNifty(int x1, int y1, int x2, int y2)
	{
		super(null);
		this.x1 = x1;
		this.y1= y1;
		this.x2 = x2;
		this.y2= y2;
		int xinc = 0;

		setPreferredSize(new Dimension(x2, y2));
		setBackground(Color.black);

		panel1 = new PanelForGraph(0,0,x2/2,y2,com.stockfaxforu.util.StockConstants.SELECTED_STOCK);
		panel1.setBounds(0,0,x2/2,y2);
		add(panel1,null);
	
		try
		{	
//http://localhost:9080/igswar/market/quotedetailtool.jsp?jspname=stocks&symbol=NDTV	
//			  panel = new ShowLiveMarket((x2/2)+1,0);
			  panel.setBounds((x2/2)+1,0,x2/2,y2-10);
			  //				panel.setBackground(Color.black);
//			listScrollPane = new JScrollPane(panel);
//			listScrollPane.createVerticalScrollBar();
			add(panel,null);
	
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
			 
	}
	public void repaint()
	{
		super.repaint();
		try
		{
			panel.repaint();
			
		}
		catch(Exception e)
		{
			
		}
	}
	public void searchStockUpdate(String symbol)
	{
	
		try
		
		{	
			try
			{
				remove(listScrollPane);
			}
			catch(Exception e)
			{
			}	
//http://localhost:9080/igswar/market/quotedetailtool.jsp?jspname=stocks&symbol=NDTV	
			AdPanel editorPane = new AdPanel(StockConstants.QUOTEDETAIL+symbol);
			editorPane.setEditable(false);
			listScrollPane = new JScrollPane(editorPane);
			listScrollPane.createVerticalScrollBar();
			listScrollPane.setBounds((x2/2)+1,0,x2/2,y2-10);
			add(listScrollPane,null);
	
		}
		catch(Exception e)
		{
		}
		panel1.macdgraph.setSymbol(symbol);
			
	}


	
	
	public static void main(String[] args)
	{
		JFrame frame = new JFrame("Test angled lines");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		PanelFor2GraphHor d = new PanelFor2GraphHor(0, 0, 1300, 900);
		frame.getContentPane().add(d);
		frame.pack();
		//		frame.setBounds(0,0,1000,700);
		frame.setVisible(true);
	}
	/* (non-Javadoc)
	 * @see com.stockfaxforu.frontend.PanelForGraphImpl#destroyThread()
	 */
	public void destroyThread()
	{
		panel1.macdgraph.destroyThread();

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
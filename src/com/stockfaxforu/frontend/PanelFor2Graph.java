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

import com.stockfaxforu.component.Indicator;
import com.stockfaxforu.util.StockConstants;

/**
 * @author sdeopura
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class PanelFor2Graph extends JPanel implements PanelForGraphImpl
{
	/**
	 * This is the default constructor
	 */
	PanelForGraph panel1;
	PanelForGraph panel2;
	public PanelFor2Graph()
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
	public PanelFor2Graph(int x1, int y1, int x2, int y2)
	{
		super(null);

		int xinc = 0;

		setPreferredSize(new Dimension(x2, y2));
		setBackground(StockConstants.graphbackgroundcolor);

		panel1 = new PanelForGraph(0,0,x2/2,y2,com.stockfaxforu.util.StockConstants.SELECTED_STOCK,false,true);
		panel1.setBounds(0,0,x2/2,y2);
		add(panel1,null);
		
		 panel2 = new PanelForGraph(0,0,x2/2,y2,com.stockfaxforu.util.StockConstants.SELECTED_STOCK,false,true);
		panel2.setBounds((x2/2)+1,0,x2/2,y2);
		add(panel2,null);
		
			 
	}
	public void resize(int x2,int y2)
	{
		panel1.resize(x2/2, y2);
		panel1.setBounds(0,0,x2/2,y2);
		panel2.resize(x2/2,y2);
		panel2.setBounds((x2/2)+1,0,x2/2,y2);

	}

	public static void main(String[] args)
	{
		JFrame frame = new JFrame("Test angled lines");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		PanelFor2Graph d = new PanelFor2Graph(0, 0, 1300, 900);
		frame.getContentPane().add(d);
		frame.pack();
		//		frame.setBounds(0,0,1000,700);
		frame.setVisible(true);
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
	 * @see com.stockfaxforu.frontend.PanelForGraphImpl#updateSize()
	 */
	public void updateSize()
	{
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see com.stockfaxforu.frontend.PanelForGraphImpl#destroyThread()
	 */
	public void destroyThread()
	{
		try
		{	
			panel1.macdgraph.destroyThread();
		}
		catch(Exception e)
		{
			
		}
		try
		{	
			panel2.macdgraph.destroyThread();
		}
		catch(Exception e)
		{
			
		}

		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see com.stockfaxforu.frontend.PanelForGraphImpl#drawStrategy(java.util.Vector)
	 */
	public void drawStrategy(Vector newVector)
	{
		panel1.drawStrategy(newVector);
		panel2.drawStrategy(newVector);
				
	}
	/* (non-Javadoc)
	 * @see com.stockfaxforu.frontend.PanelForGraphImpl#getStrategyHashMap()
	 */
	public HashMap getStrategyHashMap()
	{
		// TODO Auto-generated method stub
		return panel1.getStrategyHashMap();
	}
	/* (non-Javadoc)
	 * @see com.stockfaxforu.frontend.PanelForGraphImpl#getStrategyVector()
	 */
	public Vector getStrategyVector()
	{
		// TODO Auto-generated method stub
		return panel1.getStrategyVector();
	}
	/* (non-Javadoc)
	 * @see com.stockfaxforu.frontend.PanelForGraphImpl#getInputVector()
	 */
	public Vector getInputVector()
	{
		// TODO Auto-generated method stub
		return panel1.getInputVector();
	}
	/* (non-Javadoc)
	 * @see com.stockfaxforu.frontend.PanelForGraphImpl#executeCustmizedIndicator(java.lang.String, java.lang.String)
	 */
	public void executeCustmizedIndicator(String fileName, String formula)
	{
		panel1.executeCustmizedIndicator(fileName, formula);
		panel2.executeCustmizedIndicator(fileName, formula);
		
	}
	/* (non-Javadoc)
	 * @see com.stockfaxforu.frontend.PanelForGraphImpl#addIndicator(com.stockfaxforu.component.Indicator)
	 */
	public void addIndicator(Indicator selInd)
	{
		panel1.addIndicator(selInd);
		panel2.addIndicator(selInd);
		
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see com.stockfaxforu.frontend.PanelForGraphImpl#setYear(java.lang.String)
	 */
	public void setYear(String selName)
	{
		panel1.setYear(selName);
		panel2.setYear(selName);
		
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see com.stockfaxforu.frontend.PanelForGraphImpl#setGraphType(java.lang.String)
	 */
	public void setGraphType(String selName)
	{
		panel1.setGraphType(selName);
			panel2.setGraphType(selName);
			
	}
	/* (non-Javadoc)
	 * @see com.stockfaxforu.frontend.PanelForGraphImpl#setResolution(java.lang.String)
	 */
	public void setResolution(String selName)
	{
		panel1.setResolution(selName);
		panel2.setResolution(selName);
				
	}
	public void setZooming(String selName)
	{
		panel1.setZooming(selName);
		panel2.setZooming(selName);
				
	}
	/* (non-Javadoc)
	 * @see com.stockfaxforu.frontend.PanelForGraphImpl#setDate(java.lang.String, java.lang.String, java.lang.String)
	 */
	public void setDate(int year, String startDate, String endDate)
	{
		panel1.setDate(year,startDate,endDate);
		
		int yy = year -1;

		panel2.setDate(yy,startDate,endDate);
		
	}


}
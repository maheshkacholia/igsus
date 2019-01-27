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
import com.stockfaxforu.component.StockAnalysis;
import com.stockfaxforu.util.StockConstants;

/**
 * @author sdeopura
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class PanelForGraphIGS extends JPanel implements PanelForGraphImpl
{
	private JScrollPane listScrollPane,listScrollPane1;
	/**
	 * This is the default constructor
	 */
	PanelForGraph panel1;
//	PanelForGraph panel2;
	int x1,x2,y1,y2;
	public PanelForGraphIGS()
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
//		this.setSize(300, 200);
	}
	public PanelForGraphIGS(int x1, int y1, int x2, int y2)
	{
		super(null);
	//	// ln("in igsconstruction");
		this.x1 = x1;
		this.y1= y1;
		this.x2 = x2;
		this.y2= y2;
		int xinc = 0;

		setPreferredSize(new Dimension(x2, y2));
		setBackground(Color.black);

		panel1 = new PanelForGraph(0,0,3*x2/4,y2,com.stockfaxforu.util.StockConstants.SELECTED_STOCK);
		panel1.setBounds(x2/4+1,0,x2,y2);
		add(panel1,null);
	
		try
		{	
//http://localhost:9080/igswar/market/quotedetailtool.jsp?jspname=stocks&symbol=NDTV	
			StockAnalysis stockanalysis = new StockAnalysis(com.stockfaxforu.util.StockConstants.SELECTED_STOCK);
			stockanalysis.calculateValue();
			
		//	StringBuffer sb = new StringBuffer(stockanalysis.createString("analysis.html"));
			AdPanel editorPane = new AdPanel("http://www.iguidestocks.com/project/igsnew/igs/downloadreport.jsp?filename="+StockConstants.SELECTED_STOCK+".html");
			editorPane.setEditable(false);
			listScrollPane = new JScrollPane(editorPane);
			listScrollPane.createVerticalScrollBar();
			listScrollPane.createHorizontalScrollBar();
			
//			listScrollPane.setBounds((y2/2)+1,0,x2/2,y2-10);
			listScrollPane.setBounds(0,0,x2/4,y2);
			
			add(listScrollPane,null);
	
	/*		StringBuffer sb1 = new StringBuffer(stockanalysis.createString("analysis1.html"));
			AdPanel editorPane1 = new AdPanel(sb1);
			editorPane1.setEditable(false);
			listScrollPane1 = new JScrollPane(editorPane1);
			listScrollPane1.createVerticalScrollBar();
			listScrollPane1.createHorizontalScrollBar();

			//			listScrollPane.setBounds((y2/2)+1,0,x2/2,y2-10);
			listScrollPane1.setBounds(0,y2/2+1,x2,y2);
			
			add(listScrollPane1,null);
*/
		
		
		}
		catch(Exception e)
		{
	//		e.printStackTrace();
		}
		
			 
	}
	public void repainIGS()
	{
		try
		
		{	
			try
			{
				remove(listScrollPane);
				remove(listScrollPane1);
	
			}
			catch(Exception e)
			{
			}	
			try
			{	
	//http://localhost:9080/igswar/market/quotedetailtool.jsp?jspname=stocks&symbol=NDTV	
				StockAnalysis stockanalysis = new StockAnalysis(com.stockfaxforu.util.StockConstants.SELECTED_STOCK);
				stockanalysis.calculateValue();
				
				StringBuffer sb = new StringBuffer(stockanalysis.createString("analysis.html"));
				AdPanel editorPane = new AdPanel(sb);
				editorPane.setEditable(false);
				listScrollPane = new JScrollPane(editorPane);
				listScrollPane.createVerticalScrollBar();
				listScrollPane.createHorizontalScrollBar();
				
//				listScrollPane.setBounds((y2/2)+1,0,x2/2,y2-10);
				listScrollPane.setBounds(0,0,x2/4,y2);
				
				add(listScrollPane,null);
/*		
				StringBuffer sb1 = new StringBuffer(stockanalysis.createString("analysis1.html"));
				AdPanel editorPane1 = new AdPanel(sb1);
				editorPane1.setEditable(false);
				listScrollPane1 = new JScrollPane(editorPane1);
				listScrollPane1.createVerticalScrollBar();
				listScrollPane1.createHorizontalScrollBar();

				//			listScrollPane.setBounds((y2/2)+1,0,x2/2,y2-10);
				listScrollPane1.setBounds(0,y2/2+1,x2,y2);
				
				add(listScrollPane1,null);
*/
				repaint();
			
			}
			catch(Exception e)
			{
			//	e.printStackTrace();
			}
			
	
		}
		catch(Exception e)
		{
		}
		repaint();
		MainScreen.getSingleton().doReSize();
	}
	public void searchStockUpdate(String symbol)
	{
			panel1.macdgraph.setSymbol(symbol);
		repainIGS();
	
	}
	public static void main(String[] args)
	{
		JFrame frame = new JFrame("Test angled lines");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		PanelForGraphIGS d = new PanelForGraphIGS(0, 0, 1300, 900);
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
//		panel1.macdgraph.destroyThread();

		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see com.stockfaxforu.frontend.PanelForGraphImpl#drawStrategy(java.util.Vector)
	 */
	public void drawStrategy(Vector newVector)
	{
		panel1.drawStrategy(newVector);	
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
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see com.stockfaxforu.frontend.PanelForGraphImpl#addIndicator(com.stockfaxforu.component.Indicator)
	 */
	public void addIndicator(Indicator selInd)
	{
		panel1.addIndicator(selInd);
			
	}
	/* (non-Javadoc)
	 * @see com.stockfaxforu.frontend.PanelForGraphImpl#setYear(java.lang.String)
	 */
	public void setYear(String selName)
	{
		panel1.setYear(selName);
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see com.stockfaxforu.frontend.PanelForGraphImpl#setGraphType(java.lang.String)
	 */
	public void setGraphType(String selName)
	{
		panel1.setGraphType(selName);
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see com.stockfaxforu.frontend.PanelForGraphImpl#setResolution(java.lang.String)
	 */
	public void setResolution(String selName)
	{
		panel1.setResolution(selName);
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see com.stockfaxforu.frontend.PanelForGraphImpl#setZooming(java.lang.String)
	 */
	public void setZooming(String selName)
	{
		panel1.setZooming(selName);
		// TODO Auto-generated method stub
		
	}
	public void setDate(int year, String startDate, String endDate)
	{
		panel1.setDate(year,startDate,endDate);
		
		
	}


}
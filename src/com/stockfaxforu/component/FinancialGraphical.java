/*
 * Created on Feb 14, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.component;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;

import com.stockfaxforu.finance.ShowFinance;
import com.stockfaxforu.formulabuilder.MessageDiaglog;
import com.stockfaxforu.query.DataBaseInMemory;
import com.stockfaxforu.query.Function;
import com.stockfaxforu.strategy.BuySellStrategy;
import com.stockfaxforu.util.StockConstants;
import com.stockfaxforu.util.Utility;

/**
 * @author sdeopura
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class FinancialGraphical extends MYDimension implements MYDimensionImpl
{
	/**
	 * @param startx
	 * @param starty
	 * @param endx
	 * @param endy
	 * @param componenttype
	 */
	String symbolname ="";
	public FinancialGraphical(int startx, int starty, int endx, int endy,String symbol, int componenttype)
	{
		super(startx, starty, endx, endy, componenttype);
		// TODO Auto-generated constructor stub
		this.symbolname = symbol;

	}
	public FinancialGraphical(int startx, int starty, int endx, int endy)
	{
		super(startx, starty, endx, endy, MYDimension.FINANCIALDATA);
		// TODO Auto-generated constructor stub
	}

	public boolean isFinancialAvailable()
	{
		String s = StockConstants.INSTALL_DIR + "/result/" + symbolname +".txt";
		File f = new File(s);
		if(f.exists())
			return true;
		else
			return false;
	}

	/* (non-Javadoc)
	 * @see com.stockfaxforu.component.MYDimensionImpl#drawComponent(java.awt.Graphics)
	 */
	int max,min;
	public int getYPositionFor(float rsivalue)
	{
		int yposition = 0;
		float ratio = (float) (starty - endy) / (this.max - this.min);
		yposition = (int) ((starty - endy) - ratio * (rsivalue - this.min));
		return (yposition + starty);
	}
	public float getValueYPosition(int yposition)
	{
		yposition = yposition - starty;
		float ratio = (float) (starty - endy) / (this.max - this.min);
		//	float price = ((float)(( - yposition))  
		float price = ((starty - endy) + ratio * this.min - yposition) / ratio;
		return price;
	}
	int[] xpos;
	Vector inputdata;
	
	
	public int getXPosition(int datapos)
	{
		int xposition = 0;
		float val1 = (float) datapos / (float) noofx;
		val1 = val1 * (endx - startx);
		xposition = (int) val1 + startx;
		return xposition;
	}
	public void paintComponent(Graphics g)
	{
		int oldx = 0, oldy = 0;
		this.xpos = new int[this.inputdata.size()];
		int newy = 0;
		int newx = 0;
		for (int x = 0; x < this.inputdata.size(); x++)
		{
			HashMap hs = (HashMap) this.inputdata.elementAt(x);
			float sales = Float.parseFloat((String) hs.get(NETSALES));
		//	float totalincome = Float.parseFloat((String) hs.get(TOTALINCOME));
			newy = this.getYPositionFor(sales);
			newx = this.getXPosition(x);
			this.xpos[x] = newx;

			g.drawLine(oldx, oldy, newx, newy);
			oldx = newx;
			oldy = newy;
		}
		//		showPriceOnLeft(newx, newy, g);
		//		showAddedTechnicalIndicator(g);
		this.drawRateLine(g);
//		g.setColor(StockConstants.leftpricecolor);
//		g.setFont(new Font("Arial", Font.BOLD, 12));
//		g.drawBytes(this.note.getBytes(), 0, this.note.length(), startx + 10, starty + (starty - endy) - 20);
		int x=0;
		HashMap hs=new HashMap();
	
	}

	public String getDateForXpos(int x_pos) throws Exception
	{
		StringBuffer retStr = new StringBuffer();
		try
		{
			if(x_pos > xpos[xpos.length - 1])
			{
				HashMap hs = (HashMap) this.inputdata.elementAt(xpos.length - 1);
				String mydate = (String) hs.get(MainGraphComponent.Date);
				return mydate;
				
			}
			for (int i = 1; i < this.xpos.length; i++)
			{
				if (this.xpos[i] > x_pos)
				{
					HashMap hs = (HashMap) this.inputdata.elementAt(i - 1);
					String mydate = (String) hs.get(MainGraphComponent.Date);
					return mydate;
				}
				if (this.xpos[i] == x_pos)
				{
					HashMap hs = (HashMap) this.inputdata.elementAt(i);
					String mydate = (String) hs.get(MainGraphComponent.Date);
					return mydate;
				}
			}
		}
		catch (Exception e)
		{
		}
		return retStr.toString();
	}
	public void drawRateLine(Graphics g)
	{
		//		drawName(g);
		int height = starty - endy;
		//		int gap = 5;
		Color c = g.getColor();
		g.setColor(Color.lightGray);
		//drawing y area		
		for (int i = starty; i <= starty + height; i = i + 40)
		{
			float prc1 = getValueYPosition(i);
			g.setColor(Color.lightGray);
			g.drawLine(this.startx, i, this.endx, i);
			g.setColor(Color.white);
			g.setFont(new Font("Arial", Font.BOLD, 10));
			String s = Utility.floatDataAtTwoPrecision((prc1)) + "";
			g.drawBytes(s.getBytes(), 0, s.length(), this.endx + 6, i);
		}
		//drawing x area
		for (int x = startx; x < endx; x = x + 100)
		{
			String s = "";
			try
			{
				s = getDateForXpos(x);
			}
			catch (Exception e)
			{
				s = "";
			}
			try
			{
				s = getDateForXpos(x);
			}
			catch (Exception e)
			{
				s = "";
			}
	
			int xpos = x;
			int xpos1 = xpos - 20;
			g.setColor(Color.white);
			g.setFont(new Font("Arial", Font.BOLD, 10));
			g.drawBytes((s + "").getBytes(), 0, (s + "").length(), xpos1, starty + height + 10);
			g.setColor(Color.lightGray);
			g.drawLine(xpos, starty + height, xpos, starty);
		}
		g.setColor(Color.pink);
		g.drawLine(startx, starty, startx, starty + height);
		g.drawLine(endx, starty, endx, starty + height);
		g.drawLine(startx, starty, endx, starty);
		g.drawLine(startx, starty + height, endx, starty + height);
		g.setColor(c);
	}
	public static String QUARTER="QUARTER";
	public static String NETSALES="NETSALES";
	public static String TOTALINCOME="TOTALINCOME";
	public static String DEPRECIATION="DEPRECIATION";
	
	int noofx;
	float[] rsi;
	
	public void setHighLowValues()
	{
		this.noofx = this.inputdata.size();
		try
		{
			
			rsi = new float[inputdata.size()];
			for(int i=0;i<inputdata.size();i++)
			{
				HashMap hs = (HashMap)inputdata.get(i);
				rsi[i] = Float.parseFloat((String)hs.get(NETSALES));
			}
				
		}
		catch(Exception e)
		{
			return;
		}

		setMaxMin(rsi);
	}

	public void setMaxMin(float val[])
	{
		float tmpmin = 10000;
		float tmphigh = -10000;
		int size = val.length / 2 ;
		for (int i = val.length-1; i > size ; i--)
		{
			if (tmphigh < val[i])
				tmphigh = val[i];
			if (tmpmin > val[i])
				tmpmin = val[i];
		}
		this.min = (int)tmpmin;
		this.max = (int)tmphigh;
		
	}
	
	public void drawComponent(Graphics g)
	{
		try
		{
			
			String s = StockConstants.INSTALL_DIR + "/result/" + symbolname +".txt";
			File f = new File(s);
			if(!f.exists())
				return ;
	
			Color c = g.getColor();		
			
			int end_x = this.endx;
			int financial_x = this.startx;
			int end_y = this.endy;
			int financial_y = this.starty;
			
			int width= (end_x-financial_x);
			int height = (end_y-financial_y);
	
			g.setColor(this.boundarycolor);
	
			g.drawRect(financial_x,financial_y,width,height);
			g.setColor(this.fillcolor);
			g.fillRect(financial_x + 2,financial_y + 2,width-2,height-2);
			g.setColor(this.fillcolor);
			
			g.drawLine(financial_x + 4, financial_y, financial_x + 130, financial_y);
			g.setColor(this.fontcolor);
			g.setFont(new Font("Arial",Font.PLAIN,12));
			s = " Fundamental Data  ";
			g.drawBytes(s.getBytes(), 0, s.length(), financial_x+10,financial_y+5);
	
			ShowFinance showfin = new ShowFinance();
			Properties p = new Properties();
			p.load(new FileInputStream(StockConstants.INSTALL_DIR+"/result/"+this.symbolname +".txt"));
			String[] keys = {"QUARTER","NETSALES","TOTALINCOME","DEPRECIATION","TAXEXPENSE","NETPROFIT","BASICEPS"};
			String[] displayName = {"Quarter","Net Sale","Total Income","Depreciation","Tax Expense","Net Profit","EPS"};
	
			int counter=0;
			int counter1=0;
			int myy=financial_y + 30;
			int myx=financial_x + 15;
	
			Color[] col = { Color.red, Color.blue, Color.pink,Color.cyan,Color.magenta };
	
			int inc = 0;
			g.setFont(new Font("Arial",Font.PLAIN,9));
			
			String mytokenStr = p.getProperty(QUARTER);
			StringTokenizer st = new StringTokenizer(mytokenStr,";");
			
			String netsale = p.getProperty(NETSALES);
			StringTokenizer stnetsale = new StringTokenizer(netsale,";");
			
			String totalincome = p.getProperty(TOTALINCOME);
			StringTokenizer stTOTALINCOME = new StringTokenizer(totalincome,";");
			
			
			inputdata = new Vector();
			while(st.hasMoreElements())
			{
				HashMap hs = new HashMap();
				hs.put(QUARTER, st.nextElement());
				hs.put(NETSALES, stnetsale.nextElement());
				hs.put(FinancialGraphical.TOTALINCOME, stTOTALINCOME.nextElement());
				inputdata.add(hs);
			}
			setHighLowValues();
			paintComponent(g);
			drawRateLine(g);
			
			g.setColor(c);
		}
		catch(Exception e)
		{
			
		}
	}
	public void showSelection(Graphics g)
	{
		Color c = g.getColor();
		g.setColor(Color.red);

		g.fillRect(this.startx,this.starty, 5, 5);
		g.fillRect(this.endx,this.starty, 5, 5);
		g.fillRect(this.startx,this.endy, 5, 5);
		g.fillRect(this.endx,this.endy, 5, 5);

		g.setColor(c);

	}
}

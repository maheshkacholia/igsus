/*
 * Created on Feb 2, 2007
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.component;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.Vector;

import com.stockfaxforu.util.StockConstants;
import com.stockfaxforu.util.Utility;
public class MACDGraph extends MainGraphComponent
{
	private float minrate9day=1000;
	private float maxrate9day=-100;
	private float minmacd=1000;
	private float maxmacd=-100;
	public int noofdays26 = 26;
	public int noofdays12 = 12;
	public int noofdays9 = 9;
	public int movavgtype = 1;
	float[] rate26 = null;
	float[] rate12 = null;
	float[] rate9 = null;
	float[] close = null;
	float[] macd = null;
	float[] macd9 = null;
	
	
	public static int ZOOMCOUNTER = 0;
	public MACDGraph(int startx, int starty, int endx, int endy, int totalpanelsize)
	{
		super(startx, starty, endx, endy, totalpanelsize, MYDimension.MACDGRAPH);
	}
	public static void main(String[] args)
	{
	}
	public void createMACDGraphs(int noofdays26, int noofdays12, int noofdays9, int movavgtype)
	{
		this.noofx = this.inputdata.size();
		rate26 = new float[this.inputdata.size()];
		rate12 = new float[this.inputdata.size()];
		rate9 = new float[this.inputdata.size()];
		close = this.getClosePrice();

		macd = new float[this.inputdata.size()];
		macd9 = new float[this.inputdata.size()];
		rate26 = this.createExpoMovingAverage(noofdays26, close);
		rate12 = this.createExpoMovingAverage(noofdays12, close);
		
		for (int i = 0; i < macd.length; i++)
		{
			macd[i] = 0;	
			macd9[i] = 0;
		}	
		this.maxmacd = -1000;
		this.minmacd = 1000;
		
		for (int i = noofdays26; i < macd.length; i++)
		{
		
			if (rate26[i] == 0 || rate12[i] == 0)
			{
				close[i] = 0;
			}
			float macdtmp = rate12[i] - rate26[i];
			macd[i] = rate12[i] - rate26[i];
			if (this.maxmacd < macdtmp)
				this.maxmacd = macdtmp;
			if (this.minmacd > macdtmp)
				this.minmacd = macdtmp;
		}

		rate9 = createExpoMovingAverage(noofdays9,macd);
		this.maxrate9day = -100;
		this.minrate9day=1000;
		for (int i = noofdays9; i < rate9.length; i++)
		{
			if (this.maxrate9day < rate9[i])
				this.maxrate9day = rate9[i];
			if (this.minrate9day > rate9[i])
				this.minrate9day = rate9[i];
		}
		if(this.maxrate9day > this.maxmacd)
			this.maxmacd = this.maxrate9day;
		else
			this.maxrate9day = this.maxmacd ; 

		if(this.minrate9day < this.minmacd)
			this.minmacd = this.minrate9day;
		else
			this.minrate9day = this.minmacd ; 
		String s = this.getID();
		addToInputData(this.getID(), macd);
		addToInputData(this.getID()+":"+SIGNALVAL, rate9);
	}
	public int getYPositionForMACD(float macdvalue)
	{
		int yposition = 0;
		float ratio = (float) (starty - endy) / (this.maxmacd - this.minmacd);
		yposition = (int) ((starty - endy) - ratio * (macdvalue - this.minmacd));
		return (yposition + starty);
	}
	public float getMACDValueFromYPosition(int yposition)
	{
		yposition = yposition-starty;
		float ratio = (float)(starty - endy )/(this.maxmacd - this.minmacd);
//		float price = ((float)(( - yposition))  
		float price = ((starty - endy) +  ratio*this.minmacd - yposition)/ratio; 

		return price;
	}
	public int getYPositionFor9Day(float price9day)
	{
		int yposition = 0;
		float ratio = (float) (starty - endy) / (this.maxrate9day - this.minrate9day);
		yposition = (int) ((starty - endy) - ratio * (price9day - this.minrate9day));
		return (yposition + starty);
	}
	public void drawRateLine(Graphics g)
	{
//		drawName(g);
		int height = starty - endy;
	
//		int gap = 5;

		Color c = g.getColor();
		g.setColor(StockConstants.hline);
		//drawing y area		
		for (int i = starty; i <= starty+height; i=i + StockConstants.hlinegap)
		{
			float prc1 = getMACDValueFromYPosition(i);
			g.setColor(StockConstants.hline);
			g.drawLine(this.startx, i, this.endx, i);
			g.setColor(StockConstants.pricecolor);
			g.setFont(new Font("Arial", Font.BOLD, 10));
			String s = (int)(prc1) + "";
			g.drawBytes(s.getBytes(), 0, s.length(), this.endx + 6, i);
		}
		//drawing x area
		for (int x = startx; x < endx; x=x+100)
		{
			String s = "";
				try
				{
					s = getDateForXpos(x);

				}
				catch(Exception e)
				{
					
				}
				try
				{
					s = getDateForXpos(x);
				}
				catch (Exception e)
				{
					s = "";
				}
				if (s.indexOf(":") != -1)
				{
					try
					{
						StringTokenizer st = new StringTokenizer(s,"-");
						st.nextToken();
						s = st.nextToken();
						
					}
					catch(Exception e)
					{
						
					}
				}

				int xpos = x;
				int xpos1 = xpos - 20;
				g.setColor(StockConstants.timecolor);
				g.setFont(new Font("Arial", Font.BOLD, 10));
				g.drawBytes((s + "").getBytes(), 0, (s + "").length(), xpos1, starty + height + 10);
				g.setColor(StockConstants.vline);
				g.drawLine(xpos, starty + height , xpos, starty );
			
		}
		g.setColor(StockConstants.boundarycolor);
		g.drawLine(startx, starty, startx, starty + height);
		g.drawLine(endx, starty, endx, starty + height);
		g.drawLine(startx, starty, endx, starty);
		g.drawLine(startx, starty + height, endx, starty + height);
		g.setColor(c);
	}
	
	public void showPriceOnLeft(int xpos, int ypos, Graphics g)
	{
		try
		{
		g.setColor(StockConstants.graphbackgroundcolor);
		//showing price on mouse move						
		//public void drawBytes(byte[] data,  int offset,  int length,  int x,  int y)
		//			String s = 	"D-12/10/2007,O-100,H-120,L-100,C-50,V-100K";		

		g.fillRect(startx + 5, starty + 10, 230, 20);

		int i = this.getIndexForX(xpos);
		HashMap hs =(HashMap)inputdata.elementAt(i);
		float val = Utility.floatDataAtTwoPrecision((Float.parseFloat((String)hs.get(this.getID()))));
		String s = this.note + " " + (val);
		g.setColor(Color.red);
		g.setFont(new Font("Arial", Font.BOLD, 11));
		g.drawBytes(s.getBytes(), 0, s.length(), startx + 10, starty + 20);
	
		val =  Utility.floatDataAtTwoPrecision(Float.parseFloat((String)hs.get(this.getID()+":"+SIGNALVAL)));
		s = "SIGNAL " + " " + (val);
		g.setColor(Color.green);
		g.setFont(new Font("Arial", Font.BOLD, 11));
		g.drawBytes(s.getBytes(), 0, s.length(), startx + 130, starty + 20);
		}
		catch(Exception e)
		{
		}
	}
	public static String MACDVAL="MACDVAL";
	public static String SIGNALVAL="SIGNALVAL";
		
	public void paintComponent(Graphics g)
	{
		int oldx=0;
		int oldy=0;
		int newx=0;
		int newy=0;
		
		int old9x=0;
		int old9y=0;
		int new9x=0;
		int new9y=0;
		int yposvol9=0;
		int xposvol9=0;
		
		int old9xmacd=0;
		int old9ymacd=0;
		int new9xmacd=0;
		int new9ymacd=0;
		int yposvol9macd=0;
		int xposvol9macd=0;
		
		

		Color c = g.getColor();
		int xposvol = 0, yposvol = 0;
		this.xpos = new int[this.inputdata.size()];
		float oldprice = 0, newprice = 0;
		int height = starty - endy;
		boolean firsttime=true;
		for (int x = 0; x < this.macd.length; x++)
		{
			float macdval = this.macd[x];
			if(macdval==0)
			    continue;
			  
			HashMap tmpHash = (HashMap)inputdata.elementAt(x);
//			tmpHash.put(SIGNALVAL, rate9[x]);
			
			  
			yposvol = getYPositionForMACD(this.macd[x]);
			yposvol9 = getYPositionFor9Day(this.rate9[x]);
			yposvol9macd = getYPositionFor9Day(this.macd9[x]);
			
			xposvol = getXPosition(x);
			this.xpos[x] = xposvol;
			if(firsttime)
			{
			   oldx = xposvol;
			   oldy = yposvol;
			   old9y = yposvol9;
			   old9ymacd = yposvol9macd;
			   
			   firsttime=false;
			   continue;
			}
			newx = xposvol;
			newy =  yposvol;
			new9y =  yposvol9;
			new9ymacd =  yposvol9macd;
			

			g.setColor(Color.red);
			
			g.drawLine(oldx, oldy, newx,newy);
			g.setColor(Color.green);

			g.drawLine(oldx, old9y, newx,new9y);
			
			g.setColor(Color.orange);

//			g.drawLine(oldx, old9ymacd,newx,new9ymacd);

			oldx =  newx;
			oldy = newy;
			old9y = new9y;
			old9ymacd = new9ymacd;
			
			
		}
		this.showPriceOnLeft(this.endx,0,g);
		this.drawRateLine(g);
		showYPosPriceOnRight(newx,newy,Color.red,g,this.macd[this.macd.length -1]);
		showYPosPriceOnRight(newx,new9y,Color.green,g,this.rate9[this.rate9.length -1]);

		g.setColor(c);
	}
	
	public void showYPosPriceOnRight(int xpos, int ypos, Color color, Graphics g,float val)
	{
		Color c = g.getColor();

		try
		{
			//showing price on mouse move						
			//public void drawBytes(byte[] data,  int offset,  int length,  int x,  int y)
			//			String s = 	"D-12/10/2007,O-100,H-120,L-100,C-50,V-100K";		
			float f = Utility.floatDataAtOnePrecision(val);
			g.setColor(color);
			g.fillRect(this.endx, ypos - 12, 35, 15);
			g.setColor(Color.black);
			g.setFont(new Font("Arial", Font.BOLD, 12));
			g.drawBytes(( f + "").getBytes(), 0, ( f + "").length(), this.endx + 6, ypos);
		
		}
		catch (Exception e)
		{
		}
		g.setColor(c);		
	}
	public void showYPosPriceOnRight(int xpos,int ypos,Color c,Graphics g)
	{
		//showing price on mouse move						
		//public void drawBytes(byte[] data,  int offset,  int length,  int x,  int y)
		//			String s = 	"D-12/10/2007,O-100,H-120,L-100,C-50,V-100K";		
		float f = this.getMACDValueFromYPosition(ypos);
		g.setColor(StockConstants.mousemovecolor);
		g.fillRect(this.endx + 6, ypos - 10, 30, 15);
		g.setColor(StockConstants.mousemovepricecolor);
		g.setFont(new Font("Arial", Font.BOLD, 10));
		g.drawBytes((f + "").getBytes(), 0, (f + "").length(), this.endx + 6, ypos);
	}


	

	public void setHighLowValues()
		{
			
			createMACDGraphs(this.noofdays26, this.noofdays12, this.noofdays9, this.movavgtype);
		}
	/**
	 * @param selInd
	 */
	
	
}

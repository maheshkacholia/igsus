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
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.Vector;

import com.stockfaxforu.util.StockConstants;
import com.stockfaxforu.util.Utility;

public class StochasticGraph extends MainGraphComponent
{
	
	public int period2=3;
	private float minperK=0;
	private float maxperK=100;
	public int noofdays = 9;
	public int movavgtype = 1;
	float[] perK=null;	
	
	public static int ZOOMCOUNTER = 0;
	public StochasticGraph(int startx, int starty, int endx, int endy, int totalpanelsize)
	{
		super(startx, starty, endx, endy, totalpanelsize, MYDimension.Stochastic);
	}
	public static void main(String[] args)
	{
	}
	public float[] createStochastic(int noofdays)
	{
		this.noofx = this.inputdata.size();
		float[] close = getClosePrice();

		perK = new float[close.length];
		
		for(int i=noofdays;i<close.length;i++)
		{
			float[] val = getHighValues(noofdays, i);
			float diffden = val[1] - val[0];
			if(diffden==0)
			{
				diffden = 0.00001f;
			}
			perK[i] = ((close[i] - val[0])/(diffden))*100;
		}
		this.minperK = 0;
		this.maxperK = 100;
		

		return this.perK;
	}
	float[] perD=null;
	public float[] createFastStochastic(int period1,int period2)
	{
		perD = new float[perK.length];
		
		perD = createSimpleMovingAverage(period2, perK);
		
		for(int i=0;i<(period1+period2);i++)
		{
			perD[i] = 0;
		}	
		return this.perD;
	}

	/**
	 * @param noofdays
	 * @param i
	 * @return
	 */
	private float[] getHighValues(int noofdays, int index)
	{
		int start = index - noofdays;
		float[] retval = new float[2];
		retval[0] = 100000;
		retval[1] = 0;
		for(int i=start;i<=index;i++)
		{
			HashMap hs = (HashMap)inputdata.elementAt(i);
			float high = Float.parseFloat((String)hs.get(High));
			float low = Float.parseFloat((String)hs.get(Low));
			if(high > retval[1])
				retval[1] = high;
			if(low < retval[0])
				retval[0] = low;	
			
		}	
		// TODO Auto-generated method stub
		return retval;
	}
	public int getYPositionForPerK(float perK)
	{
		int yposition = 0;
		float ratio = (float) (starty - endy) / (this.maxperK - this.minperK);
		yposition = (int) ((starty - endy) - ratio * (perK - this.minperK));
		return (yposition + starty);
	}
	public float getPerKValueYPosition(int  yposition)
	{

	
		yposition = yposition-starty;
		float ratio = (float)(starty - endy )/(this.maxperK - this.minperK);
	//	float price = ((float)(( - yposition))  
		float price = ((starty - endy) +  ratio*this.minperK - yposition)/ratio; 
	
		return price;
	}
	public void drawRateLine(Graphics g)
	{

//		drawName(g);
		int height = starty - endy;
	
//		int gap = 5;

		Color c = g.getColor();
		g.setColor(StockConstants.hline);
		//drawing y area		
		for (int i = starty; i <= starty+height; i=i+StockConstants.hlinegap)
		{
			float prc1 = getPerKValueYPosition(i);
			g.setColor(StockConstants.hline);
			g.drawLine(this.startx, i, this.endx, i);
			g.setColor(StockConstants.pricecolor);
			g.setFont(new Font("Arial", Font.BOLD, 10));
			String s = (int)(prc1) + "";
			g.drawBytes(s.getBytes(), 0, s.length(), this.endx + 6, i);
		}
		//drawing x area
		for (int x = startx; x < endx; x=x+StockConstants.vlinegap)
		{
				String s="";
				try
				{
					s = getDateForXpos(x);
				}
				catch (Exception e)
				{
					// TODO Auto-generated catch block
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
	public void paintComponent(Graphics g)
	{
		int oldx=0;
		int oldy=0;
		int newx=0;
		int newy=0;

		int oldxD=0;
		int oldyD=0;
		int newxD=0;
		int newyD=0;

		
		Color c = g.getColor();
		int xposvol = 0, yposvol = 0;
		this.xpos = new int[this.inputdata.size()];
		float oldprice = 0, newprice = 0;
		int height = starty - endy;
		int tmpnewx=0,tmpnewy;
		boolean firsttime=true;
		for (int x = 0; x < this.perK.length; x++)
		{
			float rsi = this.perK[x];
			if(rsi==0)
			    continue;
			   
			yposvol = getYPositionForPerK(this.perK[x]);
			xposvol = getXPosition(x);
			this.xpos[x] = xposvol;
			if(firsttime)
			{
			   oldx = xposvol;
			   oldy = yposvol;
			   firsttime=false;
			   continue;
			}
			newx = xposvol;
			newy =  yposvol;
			g.setColor(Color.red);
			g.drawLine(oldx, oldy, newx,newy);

			oldx =  newx;
			oldy = newy;

		}
		tmpnewx = newx;
		tmpnewy = newy;

		firsttime=true;
		for (int x = 0; x < this.perD.length; x++)
		{
			float rsi = this.perD[x];
			if(rsi==0)
				continue;
			   
			yposvol = getYPositionForPerK(this.perD[x]);
			xposvol = getXPosition(x);
			this.xpos[x] = xposvol;
			if(firsttime)
			{
			   oldx = xposvol;
			   oldy = yposvol;
			   firsttime=false;
			   continue;
			}
			newx = xposvol;
			newy =  yposvol;
			g.setColor(Color.green);
			g.drawLine(oldx, oldy, newx,newy);

			oldx =  newx;
			oldy = newy;

		}
		
		this.drawRateLine(g);
		showYPosPriceOnRight(tmpnewx,tmpnewy,Color.red,g,this.perK[this.perK.length -1]);

		showYPosPriceOnRight(newx,newy,Color.green,g,this.perD[this.perD.length -1]);

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

	
	public void setHighLowValues()
		{
			perK = createStochastic(this.noofdays);
			perD = createFastStochastic(this.noofdays,this.period2);
			String s = this.getID();
			addToInputData(this.getID()+perKStr, perK);
			addToInputData(this.getID()+perDStr, perD);

		}
	public static String perKStr = 	"%K";
	public static String perDStr = 	"%D";
	
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
		float val = Utility.floatDataAtTwoPrecision((Float.parseFloat((String)hs.get(this.getID()+perKStr))));
		String s = perKStr + "  =  " + (val);
		g.setColor(Color.red);
		g.setFont(new Font("Arial", Font.BOLD, 11));
		g.drawBytes(s.getBytes(), 0, s.length(), startx + 10, starty + 20);
	
		val =  Utility.floatDataAtTwoPrecision(Float.parseFloat((String)hs.get(this.getID()+perDStr)));
		s = perDStr + "  =  " +  (val);
		g.setColor(Color.green);
		g.setFont(new Font("Arial", Font.BOLD, 11));
		g.drawBytes(s.getBytes(), 0, s.length(), startx + 100, starty + 20);
		}
		catch(Exception e)
		{
		}
	}	
	public void showYPosPriceOnRight(int xpos,int ypos,Color c,Graphics g)
		{
			//showing price on mouse move						
			//public void drawBytes(byte[] data,  int offset,  int length,  int x,  int y)
			//			String s = 	"D-12/10/2007,O-100,H-120,L-100,C-50,V-100K";		
			float f = this.getPerKValueYPosition(ypos);
			g.setColor(StockConstants.mousemovecolor);
			g.fillRect(this.endx + 6, ypos - 10, 30, 15);
			g.setColor(StockConstants.mousemovepricecolor);
			g.setFont(new Font("Arial", Font.BOLD, 10));
			g.drawBytes(((int) f +"").getBytes(), 0, ((int) f + "").length(), this.endx + 6, ypos);
		}
}

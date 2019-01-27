/*
 * Created on Feb 2, 2007
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.component;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Label;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.Vector;

import com.stockfaxforu.util.StockConstants;
import com.stockfaxforu.util.Utility;
public class OtherIndicatorGraph extends MainGraphComponent
{
	private float minrsi = 10;
	private float maxrsi = -10;
	public int noofdays = 9;
	public int noofdays2 = 9;
	public int movavgtype = 1;
	float[] rsi = null;
	public void showPriceOnLeft(int xpos, int ypos, Graphics g)
	{
		try
		{
			int i = this.getIndexForX(xpos);
			HashMap hs = (HashMap) inputdata.elementAt(i);
			float val = Utility.floatDataAtTwoPrecision((Float.parseFloat((String) hs.get(this.getID()))));
			String s = this.getID() + " " + (val);
			g.setColor(StockConstants.graphbackgroundcolor);
			int width = s.length()*7;
			g.fillRect(startx + 5, starty +5, width, 20);

			g.setColor(this.col[this.componenttype % this.col.length]);
			g.setFont(new Font("Arial", Font.BOLD, 11));
			g.drawBytes(s.getBytes(), 0, s.length(), startx + 10, starty + 15);
		}
		catch (Exception e)
		{
		}
	}
//Detrended Price Oscillator (DPO)
  public float[] createDetrendedPriceOscillator(int noofdays)
  {
	  float[] dpo = new float[inputdata.size()];
	float[] dpo1 = new float[inputdata.size()];
	  
	  float[] simtypprc = createSimpleMovingAverage(noofdays, getClosePrice());
		float[] close = getClosePrice();
	  int x = noofdays/2;
	  for(int i=x;i<inputdata.size();i++)
	  {
		dpo[i] = close[i] - simtypprc[i - x + 1]; 
	  }
	  return dpo;
	
  }
  public float avgPrc(int index,int noofdays,float[] close)
  {
  	int start = index-noofdays+1;
  	float totprc=0;
  	for(int i=start;i<=index;i++)
  	{
		totprc = totprc + close[i];
  	}
  	totprc = (totprc/noofdays);
  	return totprc;
  }



	//commodity channel index
	public float[] createComodityChannelIndex(int noofdays)
	{
		float[] cci = new float[inputdata.size()];
		float[] typprc = getTypicalPrice();
		float[] simtypprc = createSimpleMovingAverage(noofdays, getClosePrice());
		for(int i=noofdays-1;i<inputdata.size();i++)
		{
			float meandeviation = getMeanDeviation(i,noofdays);
			float meandeviation1 = (float)(meandeviation*0.015);
			float diff = (float)(typprc[i] - simtypprc[i]);
			cci[i] = diff/meandeviation1;	
		}
		cci[noofdays-1] =0;
		return cci;
	
	}
	float[] getTypicalPrice()
	{
		float[] typprc = new float[inputdata.size()];
		for (int i = 0; i < inputdata.size(); i++)
		{
			HashMap hs = (HashMap) inputdata.elementAt(i);
			float close = Float.parseFloat((String) hs.get(Close));
			float high = Float.parseFloat((String) hs.get(High));
			float low = Float.parseFloat((String) hs.get(Low));
			typprc[i] = (high + low + close) / 3;
		}
		return typprc;
		
	}
	float getMeanDeviation(int index,int noofdays)
	{
		float totprc=0;
		int start = index - noofdays +1;
		for(int i=start;i<=index;i++)
		{
			HashMap hs = (HashMap) inputdata.elementAt(i);
			float close = Float.parseFloat((String) hs.get(Close));
			totprc = totprc + close;			
		}
		float meanprc = totprc/noofdays;
		float meandeviation=0;
		for(int i=start;i<=index;i++)
		{
			HashMap hs = (HashMap) inputdata.elementAt(i);
			float close = Float.parseFloat((String) hs.get(Close));
			meandeviation = meandeviation + Math.abs(close-meanprc);			
		}
		meandeviation = meandeviation/noofdays;
		return meandeviation;
		
	}
	
	//volume oscillator
	public float[] createVolumeOscilatorGraphs(int noofdays12, int noofdays26)
	{
		this.noofx = this.inputdata.size();
		float[] volume12 = new float[this.inputdata.size()];
		float[] volume26 = new float[this.inputdata.size()];
		float[] pvo = new float[this.inputdata.size()];
		float[] volume = this.getVolumeFloat();
		volume12 = this.createExpoMovingAverage(noofdays12, volume);
		volume26 = this.createExpoMovingAverage(noofdays26, volume);
		for (int i = noofdays26 - 1; i < volume12.length; i++)
		{
			if (volume26[i] == 0)
				continue;
			//		  float voltmp = 100*((volume12[i] - volume26[i])/volume26[i]);
			float voltmp = 100 * ((volume12[i] - volume26[i]) / volume12[i]);
			pvo[i] = voltmp;
		}
		return pvo;
	}
	//method ends	
	//methods for willams
	public float[] createWilliam(int noofdays)
	{
		this.noofx = this.inputdata.size();
		float[] close = getClosePrice();
		float[] william = new float[close.length];
		for (int i = noofdays; i < close.length; i++)
		{
			float[] val = getHighValues(noofdays, i);
			float diffden = val[1] - val[0];
			if (diffden == 0)
			{
				diffden = 0.00001f;
			}
			william[i] = ((close[i] - val[1]) / (diffden)) * 100;
		}
		return william;
	}
	private float[] getHighValues(int noofdays, int index)
	{
		int start = index - noofdays;
		float[] retval = new float[2];
		retval[0] = 100000;
		retval[1] = 0;
		for (int i = start; i <= index; i++)
		{
			HashMap hs = (HashMap) inputdata.elementAt(i);
			float high = Float.parseFloat((String) hs.get(High));
			float low = Float.parseFloat((String) hs.get(Low));
			if (high > retval[1])
				retval[1] = high;
			if (low < retval[0])
				retval[0] = low;
		}
		// TODO Auto-generated method stub
		return retval;
	}
	//willam ind end
	public OtherIndicatorGraph(int startx, int starty, int endx, int endy, int totalpanelsize)
	{
		super(startx, starty, endx, endy, totalpanelsize, MYDimension.MACDGRAPH);
	}
	public OtherIndicatorGraph(int startx, int starty, int endx, int endy, int totalpanelsize, int graphtype)
	{
		super(startx, starty, endx, endy, totalpanelsize, graphtype);
	}
	public static void main(String[] args)
	{
	}
	public float[] createMFI(int noofdays)
	{
		double[][] mftypprc = createMF();
		float[] mfi = new float[inputdata.size()];
		for (int i = noofdays; i < inputdata.size(); i++)
		{
			mfi[i] = getMFI(noofdays, i, mftypprc);
		}
		setMaxMin(mfi);
		return mfi;
	}
	public void setMaxMin(float val[])
	{
		float tmpmin = 50;
		float tmphigh = -100;
		for (int i = 0; i < val.length; i++)
		{
			if (tmphigh < val[i])
				tmphigh = val[i];
			if (tmpmin > val[i])
				tmpmin = val[i];
		}
		this.minrsi = tmpmin;
		this.maxrsi = tmphigh;
	}
	float getMFI(int noofdays, int index, double[][] mf)
	{
		int start = index - noofdays + 1;
		double upmf = 0;
		double downmf = 0;
		for (int i = start; i <= index; i++)
		{
			if (mf[1][i] > mf[1][i - 1])
			{
				upmf = upmf + mf[0][i];
			}
			if (mf[1][i] < mf[1][i - 1])
			{
				downmf = downmf + mf[0][i];
			}
		}
		float moneyratio = (float) (upmf / (downmf));
		float mfi = (100 - 100 / (1 + moneyratio));
		return mfi;
	}
	public double[][] createMF()
	{
		double[][] moneyflowandtypprc = new double[2][inputdata.size()];
		double oldmoneyflow = 0;
		for (int i = 0; i < inputdata.size(); i++)
		{
			HashMap hs = (HashMap) inputdata.elementAt(i);
			float close = Float.parseFloat((String) hs.get(Close));
			float high = Float.parseFloat((String) hs.get(High));
			float low = Float.parseFloat((String) hs.get(Low));
			float volume = Float.parseFloat((String) hs.get(Volume));
			float typprc = (high + low + close) / 3;
			moneyflowandtypprc[0][i] = typprc * volume;
			moneyflowandtypprc[1][i] = typprc;
		}
		return moneyflowandtypprc;
	}
	//	method for creating Accumulation Distribution graph
	public float[] createWillamAccumulationDistribution()
	{
		float[] output = new float[inputdata.size()];
		float ad = 0;
		float prevad = 0;
		float tmplow = 0;
		float tmphigh = 0;

		HashMap hs = (HashMap) inputdata.elementAt(0);
		float prevclose = Float.parseFloat((String) hs.get(Close));
		float open = Float.parseFloat((String) hs.get(Open));
		float high = Float.parseFloat((String) hs.get(High));
		float low = Float.parseFloat((String) hs.get(Low));
		float close=0;
		
		for (int i = 1; i < inputdata.size(); i++)
		{
			hs = (HashMap) inputdata.elementAt(i);
			close = Float.parseFloat((String) hs.get(Close));
			open = Float.parseFloat((String) hs.get(Open));
			high = Float.parseFloat((String) hs.get(High));
			low = Float.parseFloat((String) hs.get(Low));
			if (close > prevclose)
			{
				if (low < prevclose)
				{
					tmplow = low;
				}
				else
				{
					tmplow = prevclose;
				}
				ad = prevad + close - tmplow;
			}
			
			
			
			if (close < prevclose)
			{
				if (high < prevclose)
				{
					tmphigh = prevclose;
				}
				else
				{
					tmphigh = high;
				}
				ad = prevad - (tmphigh - close);
			}
			if(close == prevclose)
			{
				ad = prevad;
			}
			output[i] = ad;
			prevad = ad;
			prevclose = close;
		}
		return output;
	}
	//	method for creating Accumulation Distribution graph
	public float[] createAccumulationDistribution()
	{
		float[] output = new float[inputdata.size()];
		float prevAccDis = 0;
		for (int i = 0; i < inputdata.size(); i++)
		{
			HashMap hs = (HashMap) inputdata.elementAt(i);
			int volume = Integer.parseInt((String) hs.get(Volume));
			volume = volume / 1000;
			float close = Float.parseFloat((String) hs.get(Close));
			float open = Float.parseFloat((String) hs.get(Open));
			float high = Float.parseFloat((String) hs.get(High));
			float low = Float.parseFloat((String) hs.get(Low));
			float clv = ((close - low) - (high - close)) / (high - low);
			float accDist = prevAccDis + clv * volume;
			int tmpint = (int)(accDist);
			output[i] = tmpint;
			prevAccDis = accDist;
		}
		//		  this.maxrsi = maxvalue;
		//		  this.minrsi = minvalue;
		return output;
	}
	public float[] createAverageTrueRange(int noofdays)
	{
		float[] truerange = createTrueRange();
		float[] emv10 = createExpoMovingAverage(noofdays, truerange);
		return emv10;
	}
	public float[] createTrueRange()
	{
		float[] truerange = new float[inputdata.size()];
		HashMap hs = (HashMap) inputdata.elementAt(0);
		float prevclose = Float.parseFloat((String) hs.get(Close));
		float prevopen = Float.parseFloat((String) hs.get(Open));
		float prevhigh = Float.parseFloat((String) hs.get(High));
		float prevlow = Float.parseFloat((String) hs.get(Low));
		float close = 0;
		float open = 0;
		float high = 0;
		float low = 0;
		float high1 = 0;
		float low1 = 0;
		for (int i = 1; i < inputdata.size(); i++)
		{
			hs = (HashMap) inputdata.elementAt(i);
			close = Float.parseFloat((String) hs.get(Close));
			open = Float.parseFloat((String) hs.get(Open));
			high = Float.parseFloat((String) hs.get(High));
			low = Float.parseFloat((String) hs.get(Low));
			if (high > prevclose)
			{
				high1 = high;
			}
			else
			{
				high1 = prevclose;
			}
			if (low < prevclose)
			{
				low1 = low;
			}
			else
			{
				low1 = prevclose;
			}
			truerange[i] = high1 - low1;
			prevclose = close;
		}
		return truerange;
	}
	public float[] createAroopOscilator(int noofdays)
	{
		float[] aroopOsc = new float[inputdata.size()];
		for (int i = noofdays-1; i < aroopOsc.length; i++)
		{
			int[] aroop = getAroopUPDown(noofdays, i);
			float arropUp = (float) (100.0 * (noofdays - aroop[1]) / (float) noofdays);
			float arropDown = (float) (100.0 * (noofdays - aroop[0]) / (float) noofdays);
			aroopOsc[i] = arropUp - arropDown;
		}
		return aroopOsc;
	}
	public float[] createAroopUp(int noofdays)
	{
		float[] arropUp = new float[inputdata.size()];
		for (int i = noofdays-1; i < arropUp.length; i++)
		{
			int[] aroop = getAroopUPDown(noofdays, i);
			arropUp[i] = (float) (100.0 * (noofdays - aroop[1]) / (float) noofdays);
//			float arropDown = (float) (100.0 * (noofdays - aroop[0]) / (float) noofdays);
//			aroopOsc[i] = arropUp - arropDown;
		}
		return arropUp;
	}
	public float[] createAroopDown(int noofdays)
	{
		float[] arropDown = new float[inputdata.size()];
		for (int i = noofdays-1; i < arropDown.length; i++)
		{
			int[] aroop = getAroopUPDown(noofdays, i);
			arropDown[i] = (float) (100.0 * (noofdays - aroop[0]) / (float) noofdays);
//				float arropDown = (float) (100.0 * (noofdays - aroop[0]) / (float) noofdays);
//				aroopOsc[i] = arropUp - arropDown;
		}
		return arropDown;
	}
	
	int[] getAroopUPDown(int noofdays, int index)
	{
		int start = index - noofdays+1;
		HashMap hs = (HashMap) inputdata.elementAt(start);
		float tmphigh = Float.parseFloat((String) hs.get(High));
		float tmplow = Float.parseFloat((String) hs.get(Low));
		int indexhigh = 1;
		int indexlow = 1;
		for (int i = start + 1; i <= index; i++)
		{
			hs = (HashMap) inputdata.elementAt(i);
			
			float high = Float.parseFloat((String) hs.get(High));
			float low = Float.parseFloat((String) hs.get(Low));
			if (low < tmplow)
			{
				indexlow++;
				tmplow = low;
			}
			if (high > tmphigh)
			{
				indexhigh++;
				tmphigh = high;
			}
		}
		int[] ret = new int[2];
		ret[0] = noofdays - indexlow;
		ret[1] = noofdays - indexhigh;
		return ret;
	}
	public float[] createChaikinMoneyFlow(int noofdays)
	{
		float[] chkosc = new float[inputdata.size()];
		for (int i = noofdays - 1; i < chkosc.length; i++)
		{
			float cmf = getChaikinMoneyFlow(noofdays, i);
			chkosc[i] = cmf;
		}
		return chkosc;
	}
	public float getChaikinMoneyFlow(int noofdays, int index)
	{
		int start = index - noofdays + 1;
		float[] output = new float[inputdata.size()];
		double clvvoltot = 0;
		double voltot = 0;
		for (int i = start; i <= index; i++)
		{
			HashMap hs = (HashMap) inputdata.elementAt(i);
			long volume = Long.parseLong((String) hs.get(Volume));
			//			volume = volume/1000;
			float close = Float.parseFloat((String) hs.get(Close));
			float open = Float.parseFloat((String) hs.get(Open));
			float high = Float.parseFloat((String) hs.get(High));
			float low = Float.parseFloat((String) hs.get(Low));
			float clv = ((close - low) - (high - close)) / (high - low);
			float accDist = clv * volume;
			clvvoltot = clvvoltot + accDist;
			voltot = voltot + volume;
		}
		float cmf = (float) (clvvoltot / voltot);
		return cmf;
	}
	//end chanklin money flow	
	public float[] createChaikinOscillator()
	{
		float[] accdis = createAccumulationDistribution();
		float[] emv10 = createExpoMovingAverage(10, accdis);
		float[] accdis1 = createSimpleMovingAverage(3, accdis);
		float[] chkosc = new float[accdis.length];
		for (int i = 9; i < chkosc.length; i++)
		{
			chkosc[i] = accdis1[i] - emv10[i];
		}
		return chkosc;
	}
	public float[] createOnBalanceVolume()
	{
		this.noofx = this.inputdata.size();
		float[] onbalvol = new float[inputdata.size()];
		HashMap hs = (HashMap) inputdata.elementAt(0);
		long oldvol = Long.parseLong((String) hs.get(Volume)) / 1000;
		float oldclose = Float.parseFloat((String) hs.get(Close));
		float totvol = 0;
		for (int i = 1; i < inputdata.size(); i++)
		{
			hs = (HashMap) inputdata.elementAt(i);
			long volume = Long.parseLong((String) hs.get(Volume));
			volume = volume / 1000;
			float close = Float.parseFloat((String) hs.get(Close));
			if (close > oldclose)
				totvol = totvol + volume;
			if (close < oldclose)
				totvol = totvol - volume;
			onbalvol[i] = totvol;
			oldclose = close;
		}
		return onbalvol;
	}
	public float[] createRSIGraphs(int noofdays)
	{
		this.noofx = this.inputdata.size();
		float[] close = getClosePrice();
		float[] up = new float[close.length];
		float[] down = new float[close.length];
		for (int i = 0; i < close.length; i++)
		{
			if (i == 0)
			{
				up[i] = 0;
				down[i] = 0;
			}
			else
			{
				if (close[i] > close[i - 1])
				{
					up[i] = close[i] - close[i - 1];
					down[i] = 0;
				}
				else
				{
					down[i] = close[i - 1] - close[i];
					up[i] = 0;
				}
			}
		}
		float[] emaUp = createExpoMovingAverage(noofdays, up);
		float[] emaDown = createExpoMovingAverage(noofdays, down);
		float[] rs = new float[close.length];
		for (int i = 0; i < close.length; i++)
		{
			if (emaDown[i] == 0)
			{
				rs[i] = 10000000.00f;
			}
			else
			{
				rs[i] = emaUp[i] / emaDown[i];
			}
		}
		float[] rsi = new float[close.length];
		for (int i = 0; i < rsi.length; i++)
		{
			rsi[i] = 100 - 100 * (1 / (1 + rs[i]));
		}
		this.minrsi = 0;
		this.maxrsi = 100;
		return rsi;
	}
	public int getYPositionForRSI(float rsivalue)
	{
		int yposition = 0;
		float ratio = (float) (starty - endy) / (this.maxrsi - this.minrsi);
		yposition = (int) ((starty - endy) - ratio * (rsivalue - this.minrsi));
		return (yposition + starty);
	}
	public float getRSIValueYPosition(int yposition)
	{
		yposition = yposition - starty;
		float ratio = (float) (starty - endy) / (this.maxrsi - this.minrsi);
		//	float price = ((float)(( - yposition))  
		float price = ((starty - endy) + ratio * this.minrsi - yposition) / ratio;
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
		for (int i = starty; i <= starty + height; i = i + StockConstants.hlinegap)
		{
			float prc1 = getRSIValueYPosition(i);
			g.setColor(StockConstants.hline);
			g.drawLine(this.startx, i, this.endx, i);
			g.setColor(StockConstants.pricecolor);
			g.setFont(new Font("Arial", Font.BOLD, 10));
			String s = Utility.floatDataAtTwoPrecision((prc1)) + "";
			g.drawBytes(s.getBytes(), 0, s.length(), this.endx + 6, i);
		}
		//drawing x area
		for (int x = startx; x < endx; x = x + StockConstants.vlinegap)
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
			g.drawLine(xpos, starty + height, xpos, starty);
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
		int oldx = 0;
		int oldy = 0;
		int newx = 0;
		int newy = 0;
		Color c = g.getColor();
		int xposvol = 0, yposvol = 0;
		this.xpos = new int[this.inputdata.size()];
		float oldprice = 0, newprice = 0;
		int height = starty - endy;
		boolean firsttime = true;
		Color c1 = this.col[this.componenttype % this.col.length];
		g.setColor(c1);
		for (int x = 0; x < this.rsi.length; x++)
		{
			float rsi = this.rsi[x];
			if (rsi == 0)
				continue;
			yposvol = getYPositionForRSI(this.rsi[x]);
			xposvol = getXPosition(x);
			this.xpos[x] = xposvol;
			if (firsttime)
			{
				oldx = xposvol;
				oldy = yposvol;
				firsttime = false;
				continue;
			}
			newx = xposvol;
			newy = yposvol;
			g.drawLine(oldx, oldy, newx, newy);
			oldx = newx;
			oldy = newy;
		}
		this.drawRateLine(g);
		showYPosPriceOnRight1(xposvol,yposvol, c1, g);
		g.setColor(c);
	}

	public void showYPosPriceOnRight1(int xpos, int ypos,Color color, Graphics g)
	{
		Color c = g.getColor();

		try
		{
			//showing price on mouse move						
			//public void drawBytes(byte[] data,  int offset,  int length,  int x,  int y)
			//			String s = 	"D-12/10/2007,O-100,H-120,L-100,C-50,V-100K";		
			float f = Utility.floatDataAtOnePrecision(this.rsi[this.rsi.length -1]);
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
		float f = this.getRSIValueYPosition(ypos);
		g.setColor(StockConstants.mousemovecolor);
		g.fillRect(this.endx + 6, ypos - 10, 30, 15);
		g.setColor(StockConstants.mousemovepricecolor);
		g.setFont(new Font("Arial", Font.BOLD, 10));
		g.drawBytes(((int) f +"").getBytes(), 0, ((int) f + "").length(), this.endx + 6, ypos);
	}


	public void setHighLowValues()
	{
		this.noofx = this.inputdata.size();
		if (this.componenttype == MYDimension.RSI)
			rsi = createRSIGraphs(this.noofdays);
		else if (this.componenttype == MYDimension.MFI)
			rsi = createMFI(noofdays);
		else if (this.componenttype == MYDimension.ONBALANCEVOLUME)
			rsi = createOnBalanceVolume();
		else if (this.componenttype == MYDimension.AccDistri)
			rsi = createAccumulationDistribution();
		else if (this.componenttype == MYDimension.ChaikinOscillator)
			rsi = createChaikinOscillator();
		else if (this.componenttype == MYDimension.AverageTrueRange)
			rsi = createAverageTrueRange(noofdays);
		else if (this.componenttype == MYDimension.AroopOscillator)
			rsi = createAroopOscilator(noofdays);
		else if (this.componenttype == MYDimension.William)
			rsi = createWilliam(noofdays);
		else if (this.componenttype == MYDimension.VolumeOscillator)
			rsi = createVolumeOscilatorGraphs(noofdays, noofdays2);
		else if (this.componenttype == MYDimension.ChaikinMoneyFlow)
			rsi = createChaikinMoneyFlow(noofdays);
		else if (this.componenttype == MYDimension.WillamAD)
			rsi = createWillamAccumulationDistribution();
		else if (this.componenttype == MYDimension.CCI)
			rsi = createComodityChannelIndex(noofdays);
		else if (this.componenttype == MYDimension.DPO)
			rsi = createDetrendedPriceOscillator(noofdays);
		else if (this.componenttype == MYDimension.EOM)
			rsi = createEaseofMovement(noofdays);


		setMaxMin(rsi);
		addToInputData(this.getID(), rsi);
	}
	/**
	 * @param noofdays
	 * @return
	 */
//ease of movement	
	private float[] createEaseofMovement(int noofdays)
	{
		float[] eom = new float[inputdata.size()];
		for(int i=1;i<inputdata.size();i++)
		{
			HashMap hs =(HashMap) inputdata.elementAt(i);
			float close = Float.parseFloat((String)hs.get(Close)); 
			float open = Float.parseFloat((String)hs.get(Open)); 
			float high = Float.parseFloat((String)hs.get(High)); 
			float low = Float.parseFloat((String)hs.get(Low));
			float volume = Float.parseFloat((String)hs.get(Volume));

			hs = (HashMap)inputdata.elementAt(i-1);
			float close1 = Float.parseFloat((String)hs.get(Close)); 
			float open1 = Float.parseFloat((String)hs.get(Open)); 
			float high1 = Float.parseFloat((String)hs.get(High)); 
			float low1 = Float.parseFloat((String)hs.get(Low));

			float diff = (high - low)/2;
			float diff1 = (high1 - low1)/2;
			float volume1 = volume/(float)1000;
			float midpoint = (diff) - (diff1);
			float boxratio =  (volume1)/(float)(high - low + 0.0001);
			eom[i] =  (midpoint * (high - low)) /volume1;  

		}
		float[] eom1 = createSimpleMovingAverage(noofdays,eom);
		// TODO Auto-generated method stub
		return eom1;
	}
	/**
		 * @param selInd
		 */
}

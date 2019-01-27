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
import java.util.Vector;

public class WillamGraph extends MainGraphComponent
{
	
	public int period2=3;
	private float minWil=-100;
	private float maxWil=0;
	public int noofdays = 9;
	public int movavgtype = 1;
	float[] william=null;	
	
	public static int ZOOMCOUNTER = 0;
	public WillamGraph(int startx, int starty, int endx, int endy, int totalpanelsize)
	{
		super(startx, starty, endx, endy, totalpanelsize, MYDimension.Stochastic);
	}
	public static void main(String[] args)
	{
	}
	public float[] createWilliam(int noofdays)
	{
		this.noofx = this.inputdata.size();
		float[] close = getClosePrice();

		william = new float[close.length];
		
		for(int i=noofdays;i<close.length;i++)
		{
			float[] val = getHighValues(noofdays, i);
			float diffden = val[1] - val[0];
			if(diffden==0)
			{
				diffden = 0.00001f;
			}
			william[i] = ((close[i] - val[1])/(diffden))*100;
		}
		this.minWil = -100;
		this.maxWil = 0;
		
		return this.william;
	}
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
	public int getYPositionForWil(float perK)
	{
		int yposition = 0;
		float ratio = (float) (starty - endy) / (this.maxWil - this.minWil);
		yposition = (int) ((starty - endy) - ratio * (perK - this.minWil));
		return (yposition + starty);
	}
	public float getWilValueYPosition(int  yposition)
	{

	
		yposition = yposition-starty;
		float ratio = (float)(starty - endy )/(this.maxWil - this.minWil);
	//	float price = ((float)(( - yposition))  
		float price = ((starty - endy) +  ratio*this.minWil - yposition)/ratio; 
	
		return price;
	}
	public void drawRateLine(Graphics g)
	{

//		drawName(g);
		int height = starty - endy;
	
//		int gap = 5;

		Color c = g.getColor();
		g.setColor(Color.lightGray);
		//drawing y area		
		for (int i = starty; i <= starty+height; i=i+40)
		{
			float prc1 = getWilValueYPosition(i);
			g.setColor(Color.lightGray);
			g.drawLine(this.startx, i, this.endx, i);
			g.setColor(Color.white);
			g.setFont(new Font("Arial", Font.BOLD, 10));
			String s = (int)(prc1) + "";
			g.drawBytes(s.getBytes(), 0, s.length(), this.endx + 6, i);
		}
		//drawing x area
		for (int x = startx; x < endx; x=x+100)
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
				int xpos = x;
				int xpos1 = xpos - 20;
				g.setColor(Color.white);
				g.setFont(new Font("Arial", Font.BOLD, 10));
				g.drawBytes((s + "").getBytes(), 0, (s + "").length(), xpos1, starty + height + 10);
				g.setColor(Color.lightGray);
				g.drawLine(xpos, starty + height , xpos, starty );
			
		}
		g.setColor(Color.pink);
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
		boolean firsttime=true;
		for (int x = 0; x < this.william.length; x++)
		{
			float rsi = this.william[x];
			if(rsi==0)
			    continue;
			   
			yposvol = getYPositionForWil(rsi);
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
			this.drawRateLine(g);
	
		g.setColor(c);
	}
	public void setHighLowValues()
		{
			william = createWilliam(noofdays);
		}
	/**
	 * @param selInd
	 */
	
	
}

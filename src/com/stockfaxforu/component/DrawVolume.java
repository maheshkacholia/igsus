/*
 * Created on Feb 15, 2008
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

import com.stockfaxforu.formulabuilder.MessageDiaglog;
import com.stockfaxforu.util.StockConstants;

/**
 * @author sdeopura
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class DrawVolume extends MainGraphComponent implements MainGraphInterface
{

	private float lowvolume;

	/**
	 * @param xbase
	 * @param ybase
	 * @param xmax
	 * @param ymax
	 */
	public DrawVolume(int startx, int starty, int endx, int endy,int totalpanelsize)
	{
		super(startx, starty, endx, endy,totalpanelsize,MYDimension.VOLUMEGRAPH);
		// TODO Auto-generated constructor stub
	}
	
	public void paintComponent(Graphics g)
	{
		Color c = g.getColor();
		int xposvol = 0, yposvol = 0;
		this.xpos = new int[this.inputdata.size()];
		float oldprice=0,newprice=0;
		int height = starty - endy;
		int width = this.getXWidth(3);
//		if (width < 3)
//			width = 3;

		for (int x = 0; x < this.inputdata.size(); x++)
		{
			HashMap hs = (HashMap) this.inputdata.elementAt(x);
			float close = Float.parseFloat((String) hs.get(MainGraphComponent.Close));
			float open = Float.parseFloat((String) hs.get(MainGraphComponent.Open));
			float high = Float.parseFloat((String) hs.get(MainGraphComponent.High));
			float low = Float.parseFloat((String) hs.get(MainGraphComponent.Low));
			long volume = Long.parseLong((String) hs.get(MainGraphComponent.Volume));

			yposvol = getYPosition(volume);
			xposvol = getXPosition(x);

			if (x == 0)
			{
				oldprice = close;
			}
			if(close < oldprice)
			{
				g.setColor(StockConstants.downcolor);
			}
			else
			{
				g.setColor(StockConstants.upcolor);
			}
			this.xpos[x] = xposvol;

			if(width >= 4)
			{
				g.fillRect(xposvol-width/2, yposvol, width, (starty+height)-yposvol);
//				g.setColor(Color.white);

//				g.drawRect(xposvol, yposvol, width, (starty+height)-yposvol);

			}
			else
			{
				g.drawLine(xposvol, yposvol, xposvol, (starty+height));
				
			}	
			oldprice = close;
		}
		this.drawRateLine(g);

		g.setColor(c);
	}
	public void setHighLowValues()
	{
		long lowvolume = 0;
		long highvolume = 0;
		long totalvolume = 0; 
		for(int x=0;x<this.inputdata.size();x++)
		{
			HashMap hs = (HashMap)this.inputdata.elementAt(x);
			long vol = (long)(Double.parseDouble((String)hs.get(Volume))/1000.00);
			if(vol < 0)
				vol = vol*-1;
			if(vol > highvolume)
				highvolume = vol;
			if(vol < lowvolume)
				lowvolume = vol;

		}
		this.noofx = this.inputdata.size();
		this.maxvolume = highvolume;
		this.lowvolume = lowvolume;
	}
	public int getYPosition(long volume)
	{
		volume = volume/1000;
		int yposition=0;
		float ratio = (float)(starty - endy )/(this.maxvolume - this.lowvolume);
		yposition = (int)((starty - endy ) - ratio*(volume - this.lowvolume) ); 
		
		return (yposition + starty);
	}
	public int getYPosition(float volume)
	{
		volume = volume/1000;
		int yposition=0;
		float ratio = (float)(starty - endy )/(this.maxvolume - this.lowvolume);
		yposition = (int)((starty - endy ) - ratio*(volume - this.lowvolume) ); 
		
		return (yposition + starty);
	}

	public void showPriceOnLeft(int xpos, int ypos, Graphics g)
	{
		g.setColor(Color.darkGray);
		//showing price on mouse move						
		//public void drawBytes(byte[] data,  int offset,  int length,  int x,  int y)
		//			String s = 	"D-12/10/2007,O-100,H-120,L-100,C-50,V-100K";		
		try
		{
		int i = this.getIndexForX(xpos);
		HashMap hs =(HashMap)inputdata.elementAt(i);
		long vol = Long.parseLong((String)hs.get(Volume));
		String s = this.note + " " + (vol/1000) + " K";
		g.setColor(StockConstants.graphbackgroundcolor);
		g.fillRect(startx + 5, starty + 5, 100, 20);
		g.setColor(StockConstants.leftpricecolor);
		g.setFont(new Font("Arial", Font.BOLD, 11));
		g.drawBytes(s.getBytes(), 0, s.length(), startx + 10, starty + 20);
		}
		catch(Exception e)
		{
		}
	}


	public void drawRateLine(Graphics g)
	  {
	  	int myheight = starty - endy;
		Color c = g.getColor();
		  g.setColor(StockConstants.hline);
		  for (int i = starty; i < starty+myheight; i=i+StockConstants.hlinegap)
		  {
			  float prc1 = this.getVolumeFromYPos(i);

			  g.setColor(StockConstants.hline);
			  g.drawLine(this.startx, i, this.endx , i);


			  g.setColor(StockConstants.pricecolor);
			  g.setFont(new Font("Arial",Font.BOLD,10));
			  String s = (int)(prc1) + " K";
			  g.drawBytes(s.getBytes(), 0, s.length(), this.endx + 6, i);

		  }
		for (int x = startx; x < endx; x=x+StockConstants.vlinegap)
		{
				String s =""; 
				try
				{
					s = getDateForXpos(x);

				}
				catch(Exception e)
				{
					s="";					
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
				g.drawBytes((s + "").getBytes(), 0, (s + "").length(), xpos1, starty + myheight + 10);
				g.setColor(StockConstants.vline);
				g.drawLine(xpos, starty + myheight , xpos, starty );
			
		}
		
		g.setColor(StockConstants.boundarycolor);		  

		g.drawLine(startx , starty, startx, starty+myheight);
	  g.drawLine(endx, starty, endx, starty+myheight);

	  g.drawLine(startx , starty, endx, starty);
	  g.drawLine(startx , starty+myheight, endx, starty+myheight);
		  g.setColor(c);

	  }
	public void drawCrossHair(int xpos,int ypos,Graphics g)
	{
			g.setColor(StockConstants.mousemovecolor);
			g.drawLine(this.startx, ypos, this.endx, ypos);
			g.drawLine(xpos, this.starty, xpos,this.starty + (this.starty-this.endy));

			showYPosPriceOnRight(xpos, ypos, g);
	}
	public void showYPosPriceOnRight(int xpos,int ypos,Graphics g)
	{
		//showing price on mouse move						
		//public void drawBytes(byte[] data,  int offset,  int length,  int x,  int y)
		//			String s = 	"D-12/10/2007,O-100,H-120,L-100,C-50,V-100K";		
		float f = this.getVolumeFromYPos(ypos);
		g.setColor(Color.green);
		g.fillRect(this.endx + 6, ypos - 10, 30, 15);
		g.setColor(Color.black);
		g.setFont(new Font("Arial", Font.BOLD, 10));
		g.drawBytes(((int) f + " K").getBytes(), 0, ((int) f + " K").length(), this.endx + 6, ypos);
	}
	public void showYPosPriceOnRight(int xpos,int ypos,Color c,Graphics g)
	{
		//showing price on mouse move						
		//public void drawBytes(byte[] data,  int offset,  int length,  int x,  int y)
		//			String s = 	"D-12/10/2007,O-100,H-120,L-100,C-50,V-100K";		
		float f = this.getVolumeFromYPos(ypos);
		g.setColor(StockConstants.mousemovecolor);
		g.fillRect(this.endx + 6, ypos - 10, 30, 15);
		g.setColor(StockConstants.mousemovepricecolor);
		g.setFont(new Font("Arial", Font.BOLD, 10));
		g.drawBytes(((int) f + " K").getBytes(), 0, ((int) f + " K").length(), this.endx + 6, ypos);
	}

	/**
	 * @param ypos
	 * @return
	 */
	private float getVolumeFromYPos(int yposition)
	{
		yposition = yposition-starty;
		float ratio = (float)(starty - endy )/(maxvolume - lowvolume);
//		float price = ((float)(( - yposition))  
		float price = ((starty - endy) +  ratio*lowvolume - yposition)/ratio; 

		return price;
	}
	
	
}

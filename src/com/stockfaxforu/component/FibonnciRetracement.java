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
import java.util.StringTokenizer;

import com.stockfaxforu.util.GlobalData;
import com.stockfaxforu.util.Utility;

/**
 * @author sdeopura
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class FibonnciRetracement extends MYDimension implements MYDimensionImpl
{
	public static int width=8;
	public GraphComponentContainer graphcont=null;
	public FibonnciRetracement(int startx, int starty, int endx, int endy, int componenttype)
	{
		super(startx, starty, endx, endy, componenttype);
		// TODO Auto-generated constructor stub
	}
	public FibonnciRetracement(int startx, int starty, int endx, int endy,GraphComponentContainer graphcont)
	{
		super(startx, starty, endx, endy, MYDimension.FIBRET);
				this.graphcont = graphcont;
				MainGraphComponent maingraph = graphcont.convert;
				if(maingraph != null)
				{

					this.startprice = Utility.floatDataAtOnePrecision(maingraph.getPriceFromYPos(this.starty));
					this.endprice = Utility.floatDataAtOnePrecision(maingraph.getPriceFromYPos(this.endy));
					try
					{
						this.startdate = maingraph.getDateForXpos(startx);
						this.enddate = maingraph.getDateForXpos(endx);

					}
					catch(Exception e)
					{
						
					}
				
				
				}
		
		
	}
	
	public FibonnciRetracement(int startx, int starty, int endx, int endy)
	{
		super(startx, starty, endx, endy, MYDimension.FIBRET);

/*		MainGraphComponent maingraph = graphcont.convert;
		if(maingraph != null)
		{

			this.startprice = Utility.floatDataAtOnePrecision(maingraph.getPriceFromYPos(this.starty));
			this.endprice = Utility.floatDataAtOnePrecision(maingraph.getPriceFromYPos(this.endy));
			try
			{
				this.startdate = maingraph.getDateForXpos(startx);
				this.enddate = maingraph.getDateForXpos(endx);

			}
			catch(Exception e)
			{
				
			}
		
		
		}
*/
	}
	public static double[] points = {0,23.6,38.2,50.0,61.8,76.4,100};

	public void drawComponent(Graphics g)
	{
		MainGraphComponent maingraph = graphcont.convert;

	
		Color c = g.getColor();
		Font f = g.getFont();
		if(maingraph != null && maingraph.draggingComponent == false)
		{
			this.starty = maingraph.getYPosition(startprice);
			this.endy = maingraph.getYPosition(endprice);
			
			this.startx = maingraph.getXPosFromDate(startdate);
			this.endx = maingraph.getXPosFromDate(enddate);
		}

		float ydiff = starty - endy;
		g.setColor(this.boundarycolor);

//		g.drawRect(this.startx,this.starty,this.endx-this.startx,this.endy-this.starty);

		for(int i=0;i<points.length;i++)
		{
			double tmp = points[i];
			double tmpy = starty - (ydiff*tmp)/100;
			int tmpypos = (int)tmpy;
			g.setColor(this.boundarycolor);
			g.drawLine(startx, tmpypos,endx, tmpypos);
			String tmps = (tmp+"%");
			g.setFont(new Font("Arial",Font.PLAIN,10));
			g.setColor(this.fontcolor);
			g.drawBytes(tmps.getBytes(),0,tmps.length(), startx, tmpypos);
			tmps = Utility.floatDataAtOnePrecision(maingraph.getPriceFromYPos((int)tmpy)) + "";
			g.drawBytes(tmps.getBytes(),0,tmps.length(), endx, tmpypos);
			
		}
		g.setFont(f);

		g.setColor(c);
	}
	public void showSelection(Graphics g)
	{
		Color c = g.getColor();
			g.setColor(Color.red);

			g.fillRect(this.startx-2,this.starty-2, 5, 5);
			g.fillRect(this.endx-3,this.starty-2, 5, 5);
			g.fillRect(this.startx-2,this.endy-3, 5, 5);
			g.fillRect(this.endx-3,this.endy-3, 5, 5);

			g.setColor(c);

	}
	public void updateDimension()
	{
		MainGraphComponent maingraph = graphcont.convert;

		this.startprice = Utility.floatDataAtOnePrecision(maingraph.getPriceFromYPos(this.starty));
			this.endprice = Utility.floatDataAtOnePrecision(maingraph.getPriceFromYPos(this.endy));
			try
			{
				this.startdate = maingraph.getDateForXpos(startx);
				this.enddate = maingraph.getDateForXpos(endx);

			}
			catch(Exception e)
			{
				
			}
		
	}

}

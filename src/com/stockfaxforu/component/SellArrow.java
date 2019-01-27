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

import com.stockfaxforu.util.GlobalData;
import com.stockfaxforu.util.Utility;

/**
 * @author sdeopura
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class SellArrow extends MYDimension implements MYDimensionImpl
{
	public static int width=3;
	public SellArrow(int startx, int starty, int endx, int endy, int componenttype)
	{
		super(startx, starty, endx, endy, componenttype);
		// TODO Auto-generated constructor stub
	}
	GraphComponentContainer graphcon;
	public SellArrow(int startx, int starty, int endx, int endy,GraphComponentContainer graphcon)
	{
		
		super(startx, starty, endx, endy, MYDimension.SellArrow);
		this.graphcon = graphcon;
		MainGraphComponent maingraph = this.graphcon.convert;
		if(maingraph != null)
		{

			try
			{
				this.startdate = maingraph.getDateForXpos(this.startx);
//				this.starty		= maingraph.getYPosForXpos(this.startx);
			}
			catch(Exception e)
			{
				
			}

			this.startprice = Utility.floatDataAtOnePrecision(maingraph.getPriceFromYPos(this.starty));
		
		
		}
		this.endx = this.startx;
		this.endy = this.starty - 30;
	}
	public void drawComponent(Graphics g)
	{
		
		Color c = g.getColor();
		MainGraphComponent maingraph = this.graphcon.convert;
		if(maingraph != null && maingraph.draggingComponent == false)
		{

			try
			{
				this.startx = maingraph.getXPosFromDate(this.startdate);
				this.starty		= maingraph.getYPosition(this.startprice);
			}
			catch(Exception e)
			{
			
			}

			this.endx = this.startx;
				this.endy = this.starty - 30;
	
		}
		g.setColor(this.boundarycolor);
		g.drawLine(this.startx,this.starty,this.endx,this.endy);
		g.drawLine(this.startx,this.starty,this.startx-width*2,this.starty-width*2);
		g.drawLine(this.startx,this.starty,this.startx+width*2,this.starty-width*2);
		
		
		g.setColor(this.fontcolor);
			
		String startprc=this.startprice+"";
		String endprc=this.endprice+"";
			
		g.drawBytes(startprc.getBytes(),0,startprc.length(), startx-5,starty);
			
		g.setColor(c);
		}
	public void showSelection(Graphics g)
	{
		Color c = g.getColor();
		g.setColor(Color.red);

		g.fillRect(this.startx-width,this.starty-width,width*2, width*2);
		g.fillRect(this.endx-width,this.endy-width, width*2, width*2);

		g.setColor(c);

	}
	public void updateDimension()
	{
		MainGraphComponent maingraph = this.graphcon.convert;

		this.startprice = Utility.floatDataAtOnePrecision(maingraph.getPriceFromYPos(this.starty));
			this.endprice = Utility.floatDataAtOnePrecision(maingraph.getPriceFromYPos(this.endy));
			try
			{
				this.startdate = maingraph.getDateForXpos(this.startx);
				this.enddate = maingraph.getDateForXpos(this.endx);

			}
			catch(Exception e)
			{
			}
	}
	public void expand(int xxpos, int yypos,int xdiffnew,int ydiffnew)
	{
		
	}	
	
}

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
public class Line extends MYDimension implements MYDimensionImpl
{
	public static int width=5;
	public Color color= Color.pink;
	public Line(int startx, int starty, int endx, int endy, int componenttype)
	{
		super(startx, starty, endx, endy, componenttype);
		// TODO Auto-generated constructor stub
	}
	GraphComponentContainer graphcont=null;
	public Line(int startx, int starty, int endx, int endy,GraphComponentContainer graphcont)
	{
		super(startx, starty, endx, endy, MYDimension.LINE);
		this.graphcont = graphcont;
		MainGraphComponent maingraph = this.graphcont.convert;
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
	public void drawComponent(Graphics g)
	{
		
		Color c = g.getColor();
		MainGraphComponent maingraph = this.graphcont.convert;
	if(maingraph != null && maingraph.draggingComponent == false)
		{
			this.starty = maingraph.getYPosition(startprice);
			this.endy = maingraph.getYPosition(endprice);
			
			this.startx = maingraph.getXPosFromDate(startdate);
			this.endx = maingraph.getXPosFromDate(enddate);
	//		// ln(arg0)
		}

		g.setColor(this.boundarycolor);

		g.drawLine(this.startx,this.starty,this.endx,this.endy);
		g.setColor(this.fillcolor);
		
		g.fillRect(this.startx-width/2,this.starty-width/2,width*1,width*1);
		g.fillRect(this.endx-width/2,this.endy-width/2,width*1,width*1);
		
		g.setColor(this.fontcolor);
			
		String startprc=this.startprice+"";
		String endprc=this.endprice+"";
			
		g.drawBytes(startprc.getBytes(),0,startprc.length(), startx-5,starty);
		g.drawBytes(endprc.getBytes(),0,endprc.length(), endx-5,endy);
			
		g.setColor(c);
		}
	public void showSelection(Graphics g)
	{
		Color c = g.getColor();
		g.setColor(Color.red);

		g.fillRect(this.startx-width/2,this.starty-width/2,width*1, width*1);
		g.fillRect(this.endx-width/2,this.endy-width/2, width*1, width*1);

		g.setColor(c);

	}
	public void updateDimension()
	{
		MainGraphComponent maingraph = this.graphcont.convert;

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

}

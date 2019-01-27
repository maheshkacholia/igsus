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
public class LineHor extends MYDimension implements MYDimensionImpl
{
	public static int width=4;
	public LineHor(int startx, int starty, int endx, int endy, int componenttype)
	{
		super(startx, starty, endx, endy, MYDimension.LINEHOR);
		// TODO Auto-generated constructor stub
		MainGraphComponent maingraph = this.graphcomp.convert;
			
			this.endy = this.starty;
			this.startx = maingraph.startx;
			this.endx = maingraph.endx;
		
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
	GraphComponentContainer graphcomp=null;
	public LineHor(int startx, int starty, int endx, int endy,GraphComponentContainer graphcomp)
	{
		super(startx, starty, endx, endy, MYDimension.LINEHOR);
		this.graphcomp = graphcomp;
		MainGraphComponent maingraph = this.graphcomp.convert;
			
		this.endy = starty;
		this.startx = maingraph.startx;
		this.endx = maingraph.endx;
//		// ln("startx "+ this.startx + "endx"+this.endx);
		if(maingraph != null)
		{

			this.startprice = Utility.floatDataAtOnePrecision(maingraph.getPriceFromYPos(this.starty));
			this.endprice = Utility.floatDataAtOnePrecision(maingraph.getPriceFromYPos(this.endy));
			try
			{
				this.startdate = maingraph.getDateForXpos(this.startx);
				this.enddate = maingraph.getDateForXpos(this.endx);
//				// ln("startDATE "+ this.startdate + "enddate"+this.enddate);

			}
			catch(Exception e)
			{
				
			}
		
		
		}
	}
	public void drawComponent(Graphics g)
	{
		
		Color c = g.getColor();
		MainGraphComponent maingraph = this.graphcomp.convert;
		if(maingraph != null && maingraph.draggingComponent == false)
		{
			this.starty = maingraph.getYPosition(startprice);
			this.endy = maingraph.getYPosition(endprice);
			
			this.startx = maingraph.getXPosFromDate(startdate);
			this.endx = maingraph.getXPosFromDate(enddate);
		}
		this.startx = maingraph.startx;
		this.endx = maingraph.endx;
		g.setColor(this.boundarycolor);
		
		g.drawLine(maingraph.startx,this.starty,maingraph.endx,this.endy);
		
		g.setColor(this.fillcolor);
		
//		g.fillRect(this.startx-width,this.starty-width,width*2,width*2);
//		g.fillRect(this.endx-width,this.endy-width,width*2,width*2);
		
	//	g.setColor(Color.white);
			
		String startprc=this.startprice+"";
		String endprc=this.endprice+"";

		g.setColor(this.fontcolor);
	//	g.setFont(new Font("Arial",Font.BOLD,10));	
		g.drawBytes(startprc.getBytes(),0,startprc.length(), maingraph.startx-5,starty);
		g.drawBytes(endprc.getBytes(),0,endprc.length(), maingraph.endx-5,endy);
			
		g.setColor(c);
		}
	public void showSelection(Graphics g)
	{
		Color c = g.getColor();
		g.setColor(Color.red);

		g.fillRect(graphcomp.convert.startx-width,this.starty-width,width*2, width*2);
		g.fillRect(graphcomp.convert.endx-width,this.endy-width, width*2, width*2);

		g.setColor(c);

	}
	public void updateDimension()
	{
		MainGraphComponent maingraph = this.graphcomp.convert;

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
	public void expand(int xxpos, int yypos,int xdiffnew,int ydiffnew)
		{
		}
	
	public void updateXYCord(int xxpos, int yypos,int xdiffnew,int ydiffnew)
	{
		int xdifffromcur = xxpos - this.startx;
		int ydifffromcur = yypos - this.starty;
		int xdiff = this.endx - this.startx;
		int ydiff = this.endy - this.starty;
	
//		this.startx = this.startx + xdiffnew;
		this.starty = this.starty + ydiffnew;
//		this.endx = this.startx + xdiff;
		this.endy = this.starty + ydiff;
		this.updateDimension();
	}

}

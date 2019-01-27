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
public class GannFan extends MYDimension implements MYDimensionImpl
{
	public static int width=8;
	public GraphComponentContainer graphcont=null;

	public GannFan(int startx, int starty, int endx, int endy, int componenttype)
	{
		super(startx, starty, endx, endy, componenttype);
		// TODO Auto-generated constructor stub
	}
	public GannFan(int startx, int starty, int endx, int endy,GraphComponentContainer graphgraphcont)
	{
		super(startx, starty, endx, endy, MYDimension.GANNFAN);
		this.graphcont = graphgraphcont;
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

	public GannFan(int startx, int starty, int endx, int endy)
	{
		super(startx, starty, endx, endy, MYDimension.GANNFAN);

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
	public static double[] points = {0,7.5,15,18.75,26.25,45};
	public static double[] points1 = {63.75,71.25,75,82.5,90};

	
//63.75,71.25,75,82.5
	public void drawComponent(Graphics g)
	{
		MainGraphComponent maingraph = this.graphcont.convert;

	
		Color c = g.getColor();
		Font f =g.getFont();
		if(maingraph != null && maingraph.draggingComponent == false)
		{
			this.starty = maingraph.getYPosition(startprice);
			this.endy = maingraph.getYPosition(endprice);
			
			this.startx = maingraph.getXPosFromDate(startdate);
			this.endx = maingraph.getXPosFromDate(enddate);
			
			int diffy = this.starty - this.endy;
			int diffx = this.endx - this.startx;
			
			if(diffx != diffy)
			{
				this.endx = this.startx + Math.abs(diffy); 
			}
//			updateDimension();
		}

		float ydiff = starty - endy;
		g.setColor(this.boundarycolor);

	//	g.drawRect(this.startx,this.starty,this.endx-this.startx,this.endy-this.starty);
		float xdiff = endx -startx;

		for(int i=0;i<points.length;i++)
		{
			double tmp = points[i];
			double val = Math.tan(Math.toRadians(tmp));

			double tmpy = endy - val*xdiff;
			int tmpypos = (int)tmpy;
			g.setColor(this.boundarycolor);
			g.drawLine(startx, endy,endx, tmpypos);
			String tmps1 = Utility.floatDataAtOnePrecision(maingraph.getPriceFromYPos((int)tmpy)) + "";
			String tmps = tmp+"%(" + (tmps1) + ")";
			g.setColor(this.fontcolor);

			g.setFont(new Font("Arial",Font.PLAIN,10));
			g.drawBytes(tmps.getBytes(),0,tmps.length(), endx, tmpypos);
			
		}
		for(int i=0;i<points1.length;i++)
		{
			double tmp = points1[i];
			double val = 1/Math.tan(Math.toRadians(tmp));

			double tmpx = startx + val*xdiff;
			int tmpxpos = (int)tmpx;
			g.setColor(this.boundarycolor);

			g.drawLine(startx, endy,tmpxpos, starty);
			String tmps = (tmp+"%");
			g.setFont(new Font("Arial",Font.PLAIN,10));
			g.setColor(this.fontcolor);

			g.drawBytes(tmps.getBytes(),0,tmps.length(), tmpxpos, starty);
			
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
		MainGraphComponent maingraph = this.graphcont.convert;

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
		if(Math.abs(xdiffnew) > Math.abs(ydiffnew))
			ydiffnew = xdiffnew;
		else
			xdiffnew = ydiffnew;	
		int xdifffromcur = xxpos - this.startx;
		int ydifffromcur = yypos - this.starty;
		int xdiff = this.endx - this.startx;
		int ydiff = this.endy - this.starty;
			
		this.startx = this.startx + xdiffnew;
		this.starty = this.starty + ydiffnew;
		this.updateDimension();
	}



}

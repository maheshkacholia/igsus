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
public class LineVer extends MYDimension implements MYDimensionImpl
{
	public static int width = 4;
	public LineVer(int startx, int starty, int endx, int endy, int componenttype)
	{
		super(startx, starty, endx, endy, MYDimension.LINEVER);
		this.graphcomp = graphcomp;
		MainGraphComponent maingraph = this.graphcomp.convert;
			
//		this.endy = starty;
		this.startx = maingraph.startx;
		this.endx = maingraph.startx;
		// ln("startx "+ this.startx + "endx"+this.endx);
		// ln("starty "+ this.starty + "endy"+this.endy);
		
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
				e.printStackTrace();
			}
		}	
		// ln("startx "+ this.startx + "endx"+this.endx);
		// ln("starty "+ this.starty + "endy"+this.endy);
	}
	GraphComponentContainer graphcomp=null;
	public LineVer(int startx, int starty, int endx, int endy,GraphComponentContainer graphcomp)
	{
		super(startx, starty, endx, endy, MYDimension.LINEVER);
		this.graphcomp = graphcomp;
		MainGraphComponent maingraph = this.graphcomp.convert;
			
//		this.endy = starty;
//		this.startx = maingraph.startx;
//		this.endx = maingraph.startx;
//		// ln("startx "+ this.startx + "endx"+this.endx);
//		// ln("starty "+ this.starty + "endy"+this.endy);
		
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
				e.printStackTrace();
			}
		}	
		// ln("startx "+ this.startx + "endx"+this.endx);
		// ln("starty "+ this.starty + "endy"+this.endy);
	
	}
	public void drawComponent(Graphics g)
	{
		Color c = g.getColor();
		MainGraphComponent maingraph = graphcomp.convert;
		if (maingraph != null && maingraph.draggingComponent == false)
		{
			this.startx = maingraph.getXPosFromDate(startdate);
			this.endx = maingraph.getXPosFromDate(enddate);
		//	this.endy = maingraph.starty - maingraph.endy ;
	//		this.starty = maingraph.starty;
			this.starty = maingraph.getYPosition(startprice);
			this.endy = maingraph.getYPosition(endprice);
		
		}
	//	this.starty = maingraph.starty;
	//	this.endy= maingraph.endy;

		g.setColor(this.boundarycolor);
		// ln("startx "+ this.startx + "endx"+this.endx);
		// ln("starty "+ this.starty + "endy"+this.endy);

		g.drawLine(this.startx, this.starty , this.startx, this.endy);
		g.setColor(this.fillcolor);
		g.fillRect(this.startx - width, maingraph.starty- width, width * 2, width * 2);
		g.fillRect(this.startx - width, maingraph.endy - width, width * 2, width * 2);
		String startprc = this.startprice + "";
		String endprc = this.endprice + "";
		g.setColor(c);
	}
	public void showSelection(Graphics g)
	{
		Color c = g.getColor();
		g.setColor(Color.red);
		g.fillRect(this.startx - width, this.starty- width, width * 2, width * 2);
		g.fillRect(this.endx - width, this.endy - width, width * 2, width * 2);
		g.setColor(c);
	}
	public void updateDimension()
	{
		MainGraphComponent maingraph = graphcomp.convert;
		try
		{
			this.startdate = maingraph.getDateForXpos(startx);
			this.enddate = maingraph.getDateForXpos(endx);
		}
		catch (Exception e)
		{
		}
	}
	public void expand(int xxpos, int yypos, int xdiffnew, int ydiffnew)
	{
	}
	public void updateXYCord(int xxpos, int yypos, int xdiffnew, int ydiffnew)
	{
		int xdifffromcur = xxpos - this.startx;
		int ydifffromcur = yypos - this.starty;
		int xdiff = this.endx - this.startx;
		int ydiff = this.endy - this.starty;
		this.startx = this.startx + xdiffnew;
		//		this.starty = this.starty + ydiffnew;
		this.endx = this.startx + xdiff;
		//		this.endy = this.starty + ydiff;
		this.updateDimension();
	}
}

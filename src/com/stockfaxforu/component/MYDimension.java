/*
 * Created on Feb 13, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.component;
import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

import com.stockfaxforu.util.GlobalData;
import com.stockfaxforu.util.StockConstants;
/**
 * @author sdeopura
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class MYDimension implements MYDimensionImpl, Serializable
{
	public static int CUSTOM=26;
	public float startprice = 0;
	public float endprice = 0;
	public String startdate = "";
	public String enddate = "";

	public Color fontcolor = StockConstants.drawingcomponentfontcolor;	
	public Color boundarycolor = StockConstants.drawingcomponentlinecolor;
	public Color fillcolor = StockConstants.drawingcomponentfillcolor;
	
	public int startx = 0;
	public int starty = 0;
	public int endx = 0;
	public int endy = 0;
	public int height = 0;
	public int xpos = 0;
	public int ypos = 0;
	public int radious = 0;
	public int componenttype = 0;
	public int componentcolor = 0;
	public String note = "";
	//component type	
	public static int LINE = 0;
	public static int CIRCLE = 1;
	public static int NOTE = 2;
	public static int RECTANGLE = 3;
	public static int FINANCIALDATA = 3;
	public static int COMPANYNAME = 4;
	public static int MAINGRAPH = 5;
	public static int StockSnapShot = 6;
	
	public static int FIBRET = 100;
	public static int LINEHOR = 101;
	public static int LINEVER = 102;
	public static int GANNFAN=103;
	public static int BuyArrow=104;
	public static int SellArrow=105;
	
	public static int VOLUMEGRAPH = 6;
	public static int OTHERGRAPH = 7;
	public static int MACDGRAPH = 8;
	public static int Stochastic = 9;
	public static int MFI = 10;
	public static int RSI = 11;
	public static int ONBALANCEVOLUME = 12;
	public static int AccDistri = 13;
	public static int ChaikinOscillator = 14;
	public static int AverageTrueRange = 15;
	public static int AroopOscillator = 16;
	public static int William = 17;
	public static int VolumeOscillator = 18;
	public static int ChaikinMoneyFlow = 19;
	public static int WillamAD = 20;
	public static int CCI = 21;
	public static int DPO = 22;
	public static int EOM = 23;
	//component color
	public static int RED = 0;
	public static int GREEN = 2;
	public static int WHITE = 3;
	public static int BLUE = 4;
	/* (non-Javadoc)
	 * @see com.stockfaxforu.component.MYDimensionImpl#drawComponent(java.awt.Graphics)
	 */
	public MYDimension(int startx, int starty, int endx, int endy, int componenttype)
	{
		this.startx = startx;
		this.starty = starty;
		this.endx = endx;
		this.endy = endy;
		this.componenttype = componenttype;
	}
	public MYDimension(int startx, int starty, int endx, int endy, int distancefromtop, int componenttype)
	{
		this.startx = startx;
		this.starty = starty;
		this.endx = endx;
		this.endy = endy;
		this.componenttype = componenttype;
		this.height = endy - starty;
	}
	public void MyDimension(int startx, int starty, int radious, int componenttype)
	{
		this.startx = startx;
		this.starty = starty;
		this.componenttype = componenttype;
	}
	public void drawComponent(Graphics g)
	{
		// TODO Auto-generated method stub
	}
	/* (non-Javadoc)
	 * @see com.stockfaxforu.component.MYDimensionImpl#showSelection(java.awt.Graphics)
	 */
	public void showSelection(Graphics g)
	{
		Color c = g.getColor();
		g.setColor(Color.red);

		g.fillRect(this.startx-2,this.starty-2, 5, 5);
		g.fillRect(this.endx-3,this.starty-2, 5, 5);
		g.fillRect(this.startx-2,this.endy-3, 5, 5);
		g.fillRect(this.endx-3,this.endy-3, 5, 5);

		g.setColor(c);

		// TODO Auto-generated method stub
	}
	/* (non-Javadoc)
	 * @see com.stockfaxforu.component.MYDimensionImpl#isPointInside(int, int)
	 */
	public boolean isPointInside(int xpos, int ypos)
	{
		// TODO Auto-generated method stub
		return false;
	}
	public void drawCrossHair(int xpos, int ypos, Graphics g)
	{ // TODO Auto-generated method stub
		//	return false;
	}
	public void updateDimension()
	{
	}
	public void updateXYCord(int xxpos, int yypos,int xdiffnew,int ydiffnew)
	{
//		int xdifffromcur = xxpos - this.startx;
//		int ydifffromcur = yypos - this.starty;
		int xdiff = this.endx - this.startx;
		int ydiff = this.endy - this.starty;
	
		this.startx = this.startx + xdiffnew;
		this.starty = this.starty + ydiffnew;
		this.endx = this.startx + xdiff;
		this.endy = this.starty + ydiff;
		this.updateDimension();
	}
	public void expand(int xxpos, int yypos,int xdiffnew,int ydiffnew)
	{
		int xdifffromcur = xxpos - this.startx;
		int ydifffromcur = yypos - this.starty;
		int xdiff = this.endx - this.startx;
		int ydiff = this.endy - this.starty;
		
		this.startx = this.startx + xdiffnew;
		this.starty = this.starty + ydiffnew;
		this.updateDimension();
			
//			mydimension.endx = mydimension.startx + xdiff;
//			mydimension.endy = mydimension.starty + ydiff;
	}
	
	public void setColor(Color fontcolor,Color boundarycolor,Color fillcolor)
	{
		this.fontcolor = fontcolor;	
		this.boundarycolor = boundarycolor;
		this.fillcolor = fillcolor;
	}
	public void updateDimFromDate()
	{
		MainGraphComponent maingraph = (MainGraphComponent)GlobalData.getScreen();
		if(maingraph != null && maingraph.draggingComponent == false)
		{
			this.starty = maingraph.getYPosition(startprice);
			this.endy = maingraph.getYPosition(endprice);
			
			this.startx = maingraph.getXPosFromDate(startdate);
			this.endx = maingraph.getXPosFromDate(enddate);
		}
	}
}

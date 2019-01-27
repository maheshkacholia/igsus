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
public class Rectangle extends MYDimension  implements MYDimensionImpl
{

	/**
	 * @param startx
	 * @param starty
	 * @param endx
	 * @param endy
	 * @param componenttype
	 */
	public Rectangle(int startx, int starty, String note)
	{
		
		super(startx, starty, startx+10, starty+25, MYDimension.NOTE);
		

		int len = note.length();
		int width = len*12;
		this.endx = this.startx + width;
		this.note = note; 
		MainGraphComponent maingraph = (MainGraphComponent)GlobalData.getScreen();
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


		// TODO Auto-generated constructor stub
	}
	public void drawComponent(Graphics g)
	{
		Color c = g.getColor();
		Font f = g.getFont();
		MainGraphComponent maingraph = (MainGraphComponent)GlobalData.getScreen();

		
		if(maingraph != null && maingraph.draggingComponent == false)
		{
			this.starty = maingraph.getYPosition(startprice);
			this.endy = maingraph.getYPosition(endprice);
			
			this.startx = maingraph.getXPosFromDate(startdate);
			this.endx = maingraph.getXPosFromDate(enddate);
		}

		
		g.setColor(this.boundarycolor);
		g.drawRect(this.startx,this.starty,this.endx-this.startx,this.endy-this.starty);
		g.setColor(this.fontcolor);
		g.setFont(new Font("Arial",Font.PLAIN,10));

		g.drawBytes(this.note.getBytes(),0,this.note.length(),this.startx+5,this.starty+12);
		g.setColor(c);
		g.setFont(f);

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
		MainGraphComponent maingraph = (MainGraphComponent)GlobalData.getScreen();

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

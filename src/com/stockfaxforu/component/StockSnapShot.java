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
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.swing.text.Utilities;

import com.stockfaxforu.finance.ShowFinance;
import com.stockfaxforu.util.GetColor;
import com.stockfaxforu.util.GetDataFromIDBI;
import com.stockfaxforu.util.StockConstants;
import com.stockfaxforu.util.Utility;

/**
 * @author sdeopura
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class StockSnapShot extends MYDimension implements MYDimensionImpl
{
	/**
	 * @param startx
	 * @param starty
	 * @param endx
	 * @param endy
	 * @param componenttype
	 */
	String symbolname ="";
	String snapShot= null;
	public StockSnapShot(int startx, int starty, int endx, int endy,String symbol,String snapshot)
	{
		super(startx, starty, endx, endy, MYDimension.StockSnapShot);
		// TODO Auto-generated constructor stub
		this.symbolname = symbol;
		this.snapShot=snapshot;
	}
	public StockSnapShot(int startx, int starty, int endx, int endy)
	{
		super(startx, starty, endx, endy, MYDimension.StockSnapShot);
		// TODO Auto-generated constructor stub
	}

	
	/* (non-Javadoc)
	 * @see com.stockfaxforu.component.MYDimensionImpl#drawComponent(java.awt.Graphics)
	 */
	public void drawComponent(Graphics g)
	{
		if(StockConstants.StockSnapshot==false)
			return;
		try
		{
			
	
			Color c = g.getColor();		
			
			int end_x = this.endx;
			int financial_x = this.startx;
			int end_y = this.endy;
			int financial_y = this.starty;
			
			int width= (end_x-financial_x);
			int height = (end_y-financial_y);
	
	/*		
			g.setColor(this.boundarycolor);
	
			g.drawRect(financial_x,financial_y,width,height);
			g.setColor(this.fillcolor);
			g.fillRect(financial_x + 2,financial_y + 2,width-2,height-2);
			g.setColor(this.fillcolor);
			
			g.drawLine(financial_x + 4, financial_y, financial_x + 130, financial_y);
			g.setColor(this.fontcolor);
			g.setFont(new Font("Arial",Font.BOLD,12));
	*/
			StringTokenizer st = new StringTokenizer(snapShot,"|");
			while(st.hasMoreElements())
			{
				
				String text = st.nextToken();
				StringTokenizer st1 = new StringTokenizer(text,"^");
				String data = st1.nextToken();
				data = Utility.doCaptilizationOfWord(data);
				Color mycolor = GetColor.getColor(st1.nextToken());
				g.setColor(mycolor);
				g.drawBytes(data.getBytes(),0,data.length(),financial_x+5,financial_y+20);
				financial_y = financial_y + 20;		
			
			}
	//		String s= GetDataFromIDBI.currentPrice(this.symbolname);
	//		g.drawBytes(s.getBytes(),0,s.length(),financial_x+5,financial_y+20);
			
		}	
		catch(Exception e)
		{
			
		}
	}
	public void showSelection(Graphics g)
	{
		Color c = g.getColor();
		g.setColor(Color.red);

		g.fillRect(this.startx,this.starty, 5, 5);
		g.fillRect(this.endx,this.starty, 5, 5);
		g.fillRect(this.startx,this.endy, 5, 5);
		g.fillRect(this.endx,this.endy, 5, 5);

		g.setColor(c);

	}
}

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
import java.util.Iterator;
import java.util.Properties;
import java.util.StringTokenizer;

import com.stockfaxforu.finance.ShowFinance;
import com.stockfaxforu.util.StockConstants;

/**
 * @author sdeopura
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Financial extends MYDimension implements MYDimensionImpl
{
	/**
	 * @param startx
	 * @param starty
	 * @param endx
	 * @param endy
	 * @param componenttype
	 */
	String symbolname ="";
	public Financial(int startx, int starty, int endx, int endy,String symbol, int componenttype)
	{
		super(startx, starty, endx, endy, componenttype);
		// TODO Auto-generated constructor stub
		this.symbolname = symbol;

	}
	public Financial(int startx, int starty, int endx, int endy)
	{
		super(startx, starty, endx, endy, MYDimension.FINANCIALDATA);
		// TODO Auto-generated constructor stub
	}

	public boolean isFinancialAvailable()
	{
		String s = StockConstants.INSTALL_DIR + "/result/" + symbolname +".txt";
		File f = new File(s);
		if(f.exists())
			return true;
		else
			return false;
	}

	/* (non-Javadoc)
	 * @see com.stockfaxforu.component.MYDimensionImpl#drawComponent(java.awt.Graphics)
	 */
	public void drawComponent(Graphics g)
	{
		try
		{
			
			String s = StockConstants.INSTALL_DIR + "/result/" + symbolname +".txt";
			File f = new File(s);
			if(!f.exists())
				return ;
	
			Color c = g.getColor();		
			
			int end_x = this.endx;
			int financial_x = this.startx;
			int end_y = this.endy;
			int financial_y = this.starty;
			
			int width= (end_x-financial_x);
			int height = (end_y-financial_y);
	
			g.setColor(this.boundarycolor);
	
			g.drawRect(financial_x,financial_y,width,height);
			g.setColor(this.fillcolor);
			g.fillRect(financial_x + 2,financial_y + 2,width-2,height-2);
			g.setColor(this.fillcolor);
			
			g.drawLine(financial_x + 4, financial_y, financial_x + 130, financial_y);
			g.setColor(this.fontcolor);
			g.setFont(new Font("Arial",Font.PLAIN,12));
			s = " Fundamental Data  ";
			g.drawBytes(s.getBytes(), 0, s.length(), financial_x+10,financial_y+5);
	
			ShowFinance showfin = new ShowFinance();
			Properties p = new Properties();
			p.load(new FileInputStream(StockConstants.INSTALL_DIR+"/result/"+this.symbolname +".txt"));
			String[] keys = {"QUARTER","NETSALES","TOTALINCOME","DEPRECIATION","TAXEXPENSE","NETPROFIT","BASICEPS"};
			String[] displayName = {"Quarter","Net Sale","Total Income","Depreciation","Tax Expense","Net Profit","EPS"};
	
			int counter=0;
			int counter1=0;
			int myy=financial_y + 30;
			int myx=financial_x + 15;
	
			Color[] col = { Color.red, Color.blue, Color.pink,Color.cyan,Color.magenta };
	
			int inc = 0;
			g.setFont(new Font("Arial",Font.PLAIN,9));
			
			
			for(inc=0;inc < keys.length;inc++)
			{
				int i = inc % col.length;
				g.setColor(col[i]);
	
				String mytokenStr = p.getProperty(keys[inc]);
				
				if(mytokenStr==null)
					continue;
				mytokenStr = displayName[inc] +";" + mytokenStr; 
				StringTokenizer line1 = new StringTokenizer(mytokenStr,";");
				while(line1.hasMoreTokens())
				{
					String line2 = line1.nextToken().trim();
					g.drawBytes(line2.getBytes(),0,line2.length(),myx,myy);
					myx = myx + 65;
	
				}
				myy = myy + 20;
				myx=financial_x + 15;
			}
			g.setColor(c);
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

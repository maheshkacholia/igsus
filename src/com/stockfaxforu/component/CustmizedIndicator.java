/*
 * Created on Feb 2, 2007
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.component;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Label;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.Vector;

import com.stockfaxforu.config.ConfigUtility;
import com.stockfaxforu.formulabuilder.MessageDiaglog;
import com.stockfaxforu.query.DataBaseInMemory;
import com.stockfaxforu.query.Function;
import com.stockfaxforu.strategy.BuySellStrategy;
import com.stockfaxforu.util.GetColor;
import com.stockfaxforu.util.StockConstants;
import com.stockfaxforu.util.Utility;
public class CustmizedIndicator extends MainGraphComponent
{
	public String query;
	private float minrsi = 10;
	private float maxrsi = -10;
	public int noofdays = 9;
	public int noofdays2 = 9;
	public int movavgtype = 1;
	float[] rsi = null;
	public void showPriceOnLeft(int xpos, int ypos, Graphics g)
	{
		try
		{
			int i = this.getIndexForX(xpos);
			HashMap hs = (HashMap) inputdata.elementAt(i);
			float val = Utility.floatDataAtOnePrecision((Float.parseFloat((String) hs.get(this.getID()))));
			
			StringTokenizer st = new StringTokenizer(this.query,"\n");
			StringBuffer paraValue=new StringBuffer();
			if(st.hasMoreTokens())
			{
				String mys12 = st.nextToken();
				/*if(mys12.startsWith("@PARAMETERVALUE"))
				{
					paraValue.append("[");
					StringTokenizer st1 = new StringTokenizer(mys12," ");
					st1.nextToken();
					Vector v = new Vector();
					while(st1.hasMoreTokens())
					{
						paraValue.append(st1.nextToken()+ " ");
					}
					paraValue.append("]");

				}*/
			}
			String s = this.getID() + paraValue + " " + (val);
			g.setColor(Color.black);
			int width = s.length()*7;
			g.fillRect(startx + 5, starty +5, width, 20);
			
			g.setColor(this.col[this.componenttype % this.col.length]);
			g.setFont(new Font("Arial", Font.BOLD, 11));
			g.drawBytes(s.getBytes(), 0, s.length(), startx + 10, starty + 15);
			int newpos = startx + 5 + width;
//a
			hs = (HashMap) dataVector.elementAt(i);
			Vector v  = (Vector)hs.get(Function.LEFTPRICE);
			if(v != null)
			{
				for ( int j=0;j<v.size();j++)
				{
					HashMap leftprcHash = (HashMap)v.get(j);
					try
					{
						String value = (String)leftprcHash.get(Function.VALUE);
						if(value==null)
							value="";	
						value = Utility.floatDataAtOnePrecisionStr(value);
						s = (String)leftprcHash.get(Function.NAME) + " " + (value);
						g.setColor(Color.black);
						width = s.length()*9;
						g.fillRect(newpos, starty +5, width, 20);
						String colorStr = (String)leftprcHash.get(Function.COLOR);
						g.setColor((ConfigUtility.getColor(colorStr)));
						g.setFont(new Font("Arial", Font.BOLD, 11));
						g.drawBytes(s.getBytes(), 0, s.length(), newpos+5, starty + 15);
						newpos = newpos + width;
						
					}
					catch(Exception e)
					{
						
					}
				}
			}
			
		
		
		}
		catch (Exception e)
		{
		}
	}
	private float[] getHighValues(int noofdays, int index)
	{
		int start = index - noofdays;
		float[] retval = new float[2];
		retval[0] = 100000;
		retval[1] = 0;
		for (int i = start; i <= index; i++)
		{
			HashMap hs = (HashMap) inputdata.elementAt(i);
			float high = Float.parseFloat((String) hs.get(High));
			float low = Float.parseFloat((String) hs.get(Low));
			if (high > retval[1])
				retval[1] = high;
			if (low < retval[0])
				retval[0] = low;
		}
		// TODO Auto-generated method stub
		return retval;
	}
	//willam ind end
	public CustmizedIndicator(int startx, int starty, int endx, int endy, int totalpanelsize)
	{
		super(startx, starty, endx, endy, totalpanelsize, MYDimension.CUSTOM);
	}
	public CustmizedIndicator(int startx, int starty, int endx, int endy, int totalpanelsize, int graphtype)
	{
		super(startx, starty, endx, endy, totalpanelsize, graphtype);
	}
		
	public int getYPositionForRSI(float rsivalue)
	{
		int yposition = 0;
		float ratio = (float) (starty - endy) / (this.maxrsi - this.minrsi);
		yposition = (int) ((starty - endy) - ratio * (rsivalue - this.minrsi));
		return (yposition + starty);
	}
	public float getRSIValueYPosition(int yposition)
	{
		yposition = yposition - starty;
		float ratio = (float) (starty - endy) / (this.maxrsi - this.minrsi);
		//	float price = ((float)(( - yposition))  
		float price = ((starty - endy) + ratio * this.minrsi - yposition) / ratio;
		return price;
	}
	public void drawRateLine(Graphics g)
	{
		//		drawName(g);
		int height = starty - endy;
		//		int gap = 5;
		Color c = g.getColor();
		g.setColor(Color.lightGray);
		//drawing y area		
		for (int i = starty; i <= starty + height; i = i + StockConstants.hlinegap)
		{
			float prc1 = getRSIValueYPosition(i);
			g.setColor(StockConstants.hline);
			g.drawLine(this.startx, i, this.endx, i);
			g.setColor(StockConstants.pricecolor);
			g.setFont(new Font("Arial", Font.BOLD, 10));
			String s = Utility.floatDataAtTwoPrecision((prc1)) + "";
			g.drawBytes(s.getBytes(), 0, s.length(), this.endx + 6, i);
		}
		//drawing x area
		for (int x = startx; x < endx; x = x + StockConstants.vlinegap)
		{
			String s = "";
			try
			{
				s = getDateForXpos(x);
			}
			catch (Exception e)
			{
				s = "";
			}
			try
			{
				s = getDateForXpos(x);
			}
			catch (Exception e)
			{
				s = "";
			}
			if (s.indexOf(":") != -1)
			{
				try
				{
					StringTokenizer st = new StringTokenizer(s,"-");
					st.nextToken();
					s = st.nextToken();
					
				}
				catch(Exception e)
				{
					
				}
			}

			int xpos = x;
			int xpos1 = xpos - 20;
			g.setColor(StockConstants.timecolor);
			g.setFont(new Font("Arial", Font.BOLD, 10));
			g.drawBytes((s + "").getBytes(), 0, (s + "").length(), xpos1, starty + height + 10);
			g.setColor(StockConstants.vline);
			g.drawLine(xpos, starty + height, xpos, starty);
		}
		g.setColor(StockConstants.boundarycolor);
		g.drawLine(startx, starty, startx, starty + height);
		g.drawLine(endx, starty, endx, starty + height);
		g.drawLine(startx, starty, endx, starty);
		g.drawLine(startx, starty + height, endx, starty + height);
		g.setColor(c);
	}
	public void paintComponent(Graphics g)
	{
		try
		{
			
			int oldx = 0;
			int oldy = 0;
			int newx = 0;
			int newy = 0;
			Color c = g.getColor();
			int xposvol = 0, yposvol = 0;
			this.xpos = new int[this.inputdata.size()];
			float oldprice = 0, newprice = 0;
			int height = starty - endy;
			boolean firsttime = true;
			Color c1 = this.col[this.componenttype % this.col.length];
			g.setColor(c1);
			for (int x = 0; x < this.rsi.length; x++)
			{
				float rsi = this.rsi[x];
				if(rsi > this.maxrsi)
					continue;
				if ( rsi < this.minrsi)
					continue;	
				if (rsi == 0)
					continue;
				yposvol = getYPositionForRSI(this.rsi[x]);
				xposvol = getXPosition(x);
				this.xpos[x] = xposvol;
				if (firsttime)
				{
					oldx = xposvol;
					oldy = yposvol;
					firsttime = false;
					continue;
				}
				newx = xposvol;
				newy = yposvol;
				g.drawLine(oldx, oldy, newx, newy);
				oldx = newx;
				oldy = newy;
			}
			this.drawRateLine(g);
			drawIndicator(g);
			showYPosPriceOnRight1(xposvol,yposvol, c1, g);
			g.setColor(c);
		
		}
		catch(Exception e)
		{
			
		}
	}
	public void showYPosPriceOnRight(int xpos,int ypos,Color c,Graphics g)
	{
		//showing price on mouse move						
		//public void drawBytes(byte[] data,  int offset,  int length,  int x,  int y)
		//			String s = 	"D-12/10/2007,O-100,H-120,L-100,C-50,V-100K";		
		float f = this.getRSIValueYPosition(ypos);
		g.setColor(StockConstants.mousemovecolor);
		g.fillRect(this.endx + 6, ypos - 10, 30, 15);
		g.setColor(StockConstants.mousemovepricecolor);
		g.setFont(new Font("Arial", Font.BOLD, 10));
		g.drawBytes(((int) f +"").getBytes(), 0, ((int) f + "").length(), this.endx + 6, ypos);
	}

	public void showYPosPriceOnRight1(int xpos, int ypos, Color color, Graphics g)
	{
		Color c = g.getColor();

		try
		{
			//showing price on mouse move						
			//public void drawBytes(byte[] data,  int offset,  int length,  int x,  int y)
			//			String s = 	"D-12/10/2007,O-100,H-120,L-100,C-50,V-100K";		
			float f = Utility.floatDataAtOnePrecision(this.rsi[this.rsi.length -1]);
			g.setColor(color);
			g.fillRect(this.endx, ypos - 12, 35, 15);
			g.setColor(Color.black);
			g.setFont(new Font("Arial", Font.BOLD, 12));
			g.drawBytes(( f + "").getBytes(), 0, ( f + "").length(), this.endx + 6, ypos);
		
			HashMap hs = (HashMap)dataVector.get(dataVector.size()-1);

			Vector v  = (Vector)hs.get(Function.RIGHTPRICE);
			if(v != null)
			{
				for ( int j=0;j<v.size();j++)
				{
					HashMap rightprcHash = (HashMap)v.get(j);
					try
					{
						String value = (String)rightprcHash.get(Function.VALUE);
						if(value==null)
							continue;	
						float valuef = Float.parseFloat(Utility.floatDataAtOnePrecisionStr(value));
						color = ConfigUtility.getColor((String)rightprcHash.get(Function.COLOR));
						g.setColor(color);
						ypos = getYPositionForRSI(valuef);
						g.fillRect(this.endx, ypos - 12, 35, 15);
						g.setColor(Color.black);
						g.setFont(new Font("Arial", Font.BOLD, 12));
						g.drawBytes(( valuef + "").getBytes(), 0, ( valuef + "").length(), this.endx + 6, ypos);	
					}
					catch(Exception e)
					{
						
					}
				}
			}

		}
		catch (Exception e)
		{
		}
		
		g.setColor(c);		
	}
	public void setMaxMin(float val[])
	{
		float tmpmin = 10000;
		float tmphigh = -10000;
		int size = val.length / 2 ;
		for (int i = val.length-1; i > size ; i--)
		{
			if (tmphigh < val[i])
				tmphigh = val[i];
			if (tmpmin > val[i])
				tmpmin = val[i];
		}
		for(int j=1;j<3;j++)
		{
			val = getDrawValue("PLOT"+j);
			for (int i = val.length-1; i > 0 ; i--)
			{
				// ln(val[i]);
				if(val[i] == -1234)
					continue;
				if (tmphigh < val[i])
					tmphigh = val[i];
				if (tmpmin > val[i])
					tmpmin = val[i];
			}
			
		}
		this.minrsi = tmpmin;
		this.maxrsi = tmphigh;
		this.setMinMaxComponent();
		tmpmin = this.minrsi;
		tmphigh = this.maxrsi;
		if (tmpmin < 0)
		{
			this.minrsi = (float)(Math.abs(tmpmin) * 1.3) * -1;
			
		}
		else
		{
			this.minrsi = (float)(tmpmin * 0.8);
			
		}
		if (tmphigh < 0)
		{
			this.maxrsi = (float)(Math.abs(tmphigh) * 0.7) * -1;
			
		}
		else
		{
			this.maxrsi = (float)(tmphigh * 1.3);
			
		}
		
	}
	public Vector dataVector = new Vector();
	public MainGraphComponent maingraph=null;
	public Graphics g=null;
	Vector outputVec=null;
	public void setHighLowValues()
	{
		this.noofx = this.inputdata.size();
		BuySellStrategy buysell = new BuySellStrategy();
		try
		{
//			String[] rsiStr = buysell.getValueForDataForCustmizedInd(this.inputdata,this.query);
			outputVec = buysell.executeStrategy(this.inputdata,this.query);
			
			rsi = new float[outputVec.size()];
			dataVector = new Vector();
			for(int i=0;i<outputVec.size();i++)
			{
				HashMap hs = (HashMap)outputVec.get(i);
				rsi[i] = Float.parseFloat((String)hs.get(DataBaseInMemory.IGSTEMP));
				HashMap hsnew = new HashMap();
				dataVector.add(hsnew);
			}
			addVariables(outputVec);
			maingraph.showalerttext=true;;

			maingraph.showAlertText(g);
			HashMap outputHash = (HashMap)outputVec.get(outputVec.size() -1);
			String s = (String)outputHash.get(MainGraphComponent.ALERT);
			String s1 = (String)outputHash.get(Function.ALERTTEXT);

			if(s != null)
			{
				MessageDiaglog msg = new MessageDiaglog(s);
			}

//			rsi = buysell.getValueForDataForCustmizedInd(this.inputdata,this.query);
			
		}
		catch(Exception e)
		{
			MessageDiaglog msg = new MessageDiaglog("Exception Occured , Indicator not added");
			return;
		}

		setMaxMin(rsi);
		addToInputData(this.getID(), rsi);
	}
	public float[] getDrawValue(String plotname)
	{
		float[] f = new float[dataVector.size()];
		for(int i=0;i<f.length;i++)
		{
			HashMap hs = (HashMap)dataVector.get(i);
			String s = (String)hs.get(plotname);
			float tempf = -1;
			try
			{
				tempf = Float.parseFloat(s);
				f[i] = tempf;		
			}
			catch(Exception e)
			{
				f[i] = -1234;		
			}
			
		}
		return f;
	}
	public void plotGraph(float[] avgprices, Graphics g, String colorName)
	{
		int newx = 0, newy = 0, oldx = 0, oldy = 0;
		int y = 0;
		Color paintColor=null;
		addVariables(outputVec);

		for (int x = 0; x < this.dataVector.size(); x++)
		{

			newx = this.getXPosition(x);
			if (avgprices[x] == -1234)
				continue;
			newy = this.getYPositionForRSI(avgprices[x]);
			
			HashMap hs = (HashMap)dataVector.get(x);
			String colorStr = (String)hs.get(colorName);
//			// ln(hs);
			Color color = GetColor.getColor(colorStr);
			if (y == 0)
			{
				oldx = newx;
				oldy = newy;
				y++;
			}
			if(color != null)
			{
				if(paintColor==null)
				     paintColor = color;
				Color c1 = g.getColor();
				g.setColor(paintColor);
				g.drawLine(oldx, oldy, newx, newy);
				g.drawLine(oldx, oldy + 1, newx, newy + 1);
				oldx = newx;
				oldy = newy;
				paintColor = color;
				g.setColor(c1);
			}
		}
	}

	public void drawIndicator(Graphics g)
	{
		inddisypos = 75;
		float[] avgprices = null;
		int newx = 0;
		int newy = 0;
		int oldx = 0;
		int oldy = 0;
//		float[] close = this.getClosePrice();
//added for drawline function of igs fl
  		float[] plotf = this.getDrawValue(PLOT1);
		plotGraph(plotf, g,COLOR1);
		
		plotf = this.getDrawValue(PLOT2);
		plotGraph(plotf, g,COLOR2);
		
		plotf = this.getDrawValue(PLOT3);
		plotGraph(plotf, g,COLOR3);
		
		drawStrategyComponent(g);
	}
	public void drawStrategyComponent(Graphics g)
	{
		for(int i=0;i<dataVector.size();i++)
		{
			int newx = this.getXPosition(i);
			HashMap hs = (HashMap)dataVector.get(i);
			Vector v = (Vector)hs.get(MainGraphComponent.OTHERCOMPONENT);
			if(v==null)
			    continue;
			drawComponent(g, v, newx);    				
		}
	}
	public void setMinMaxComponent()
	{
		for(int i=0;i<dataVector.size();i++)
		{
			HashMap hs = (HashMap)dataVector.get(i);
			Vector v = (Vector)hs.get(MainGraphComponent.OTHERCOMPONENT);
			if(v==null)
				continue;
			for(int j=0;j<v.size();j++)
			{
				HashMap hs1 = (HashMap)v.get(j);
				setMinMaxComponent(hs1);    				
			}
		}
	}

	public void setMinMaxComponent(HashMap component)
	{
		try
		{
			float value2f = Float.parseFloat((String)component.get("VALUE"));
			if ( value2f < this.minrsi)
				this.minrsi = value2f;
			if(value2f > this.maxrsi)
				this.maxrsi = value2f;
		}
		catch(Exception e)
		{
			
		}
		try
		{
			float value2f = Float.parseFloat((String)component.get("VALUE1"));
			if ( value2f < this.minrsi)
				this.minrsi = value2f;
			if(value2f > this.maxrsi)
				this.maxrsi = value2f;
			
		}
		catch(Exception e)
		{
			
		}
		
	}
	public void drawComponent(Graphics g,Vector component,int newx)
	{
		for(int i=0;i<component.size();i++)
		{
			HashMap compHs = (HashMap)component.get(i);
			drawComponent(g, compHs, newx);
		}
	}
	public void drawComponent(Graphics g,HashMap component,int newx)
	{
		int width = this.getXWidth(3);
		newx = newx + width/2;
		String colorStr = (String)component.get("COLOR");
		String name = (String)component.get("NAME");
		String value1 = (String)component.get("VALUE");
		float value2f=0;
		int newy2=0;
		try
		{
			value2f = Float.parseFloat((String)component.get("VALUE1"));
			newy2 = this.getYPositionForRSI(value2f);
			
		}
		catch(Exception e)
		{
			
		}
		
		float value1f = Float.parseFloat(value1);
		
		int newy = this.getYPositionForRSI(value1f);
		String text = (String)component.get("TEXT");

		String size = (String)component.get("SIZE");
		
		int sizeint = 0;
		try
		{
			sizeint = (int)Float.parseFloat(size);
		}
		catch(Exception e)
		{
			
		}
		Color color = GetColor.getColor(colorStr);
		if(name.equalsIgnoreCase("line"))
		{
			drawStratetgyComponent(g, newx,newy,newx,newy2,name,color);		
			
		}
		else
		{
			drawStratetgyComponent(g, newx,newy,name,text,color,sizeint);		
			
		}
		
	}
	
	public void addVariables(Vector outputVec)
	{
		for(int j=0;j < outputVec.size();j++)
		{
			HashMap hs = (HashMap)outputVec.elementAt(j);
			String buy = (String)hs.get(MainGraphComponent.BUY);
			String sell = (String)hs.get(MainGraphComponent.SELL);
			String alert = (String)hs.get(MainGraphComponent.ALERT);
			String alerttext = (String)hs.get(Function.ALERTTEXT);

			String plot1 = (String)hs.get(MainGraphComponent.PLOT1);
			String plot2 = (String)hs.get(MainGraphComponent.PLOT2);
			String plot3 = (String)hs.get(MainGraphComponent.PLOT3);
		
			String color1 = (String)hs.get(MainGraphComponent.COLOR1);
			String color2 = (String)hs.get(MainGraphComponent.COLOR2);
			String color3= (String)hs.get(MainGraphComponent.COLOR3);

			String plotmain1 = (String)hs.get(Function.PLOTMAIN1);
			String plotmain2 = (String)hs.get(Function.PLOTMAIN2);
			String plotmain3 = (String)hs.get(Function.PLOTMAIN3);
		
			String colormain1 = (String)hs.get(Function.COLORMAIN1);
			String colormain2 = (String)hs.get(Function.COLORMAIN2);
			String colormain3= (String)hs.get(Function.COLORMAIN3);

			
			Vector component = (Vector)hs.get(MainGraphComponent.OTHERCOMPONENT);
			Vector leftprice = (Vector)hs.get(Function.LEFTPRICE);
			Vector rightprice = (Vector)hs.get(Function.RIGHTPRICE);
						
			HashMap hscur = (HashMap)dataVector.elementAt(j);
			HashMap inputhash = (HashMap)this.inputdata.elementAt(j);
//			String s = ":"+(String)hs.get(MainGraphComponent.Date);
			if(buy != null )
			{
				inputhash.put(MainGraphComponent.BUYIND,buy);
			}
			if(sell != null )
			{
				inputhash.put(MainGraphComponent.SELLIND,sell);
			}
			if(alert != null)
			{
				inputhash.put(MainGraphComponent.ALERT,alert);
					
			}
			if(alerttext != null)
			{
				inputhash.put(Function.ALERTTEXT,alerttext);
					
			}
			
			
			if(plot1 != null)
			{
				hscur.put(MainGraphComponent.PLOT1,plot1);
	
			}
			if(plot2 != null)
			{
				hscur.put(MainGraphComponent.PLOT2,plot2);

			}
			if(plot3 != null)
			{
				hscur.put(MainGraphComponent.PLOT3,plot3);

			}
			if(color1 != null)
			{
				hscur.put(MainGraphComponent.COLOR1,color1);
	
			}
			if(color2 != null)
			{
				hscur.put(MainGraphComponent.COLOR2,color2);

			}
			if(color3 != null)
			{
				hscur.put(MainGraphComponent.COLOR3,color3);

			}

			if(plotmain1 != null)
			{
				inputhash.put(MainGraphComponent.PLOT1,plotmain1);
	
			}
			if(plotmain2 != null)
			{
				inputhash.put(MainGraphComponent.PLOT2,plotmain2);

			}
			if(plotmain3 != null)
			{
				inputhash.put(MainGraphComponent.PLOT3,plotmain3);

			}
			if(colormain1 != null)
			{
				inputhash.put(MainGraphComponent.COLOR1,colormain1);
	
			}
			if(colormain2 != null)
			{
				inputhash.put(MainGraphComponent.COLOR2,colormain2);

			}
			if(colormain3 != null)
			{
				inputhash.put(MainGraphComponent.COLOR3,colormain3);

			}

			
			
			if(component != null)
			{
				hscur.put(MainGraphComponent.OTHERCOMPONENT,component);

			}
			if(leftprice != null)
			{
				hscur.put(Function.LEFTPRICE,leftprice);

			}
			if(rightprice != null)
			{
				hscur.put(Function.RIGHTPRICE,rightprice);

			}

									
		}
	
	}

	/**
	 * @param noofdays
	 * @return
	 */
	public static void main(String[] args)
	{
	}

}

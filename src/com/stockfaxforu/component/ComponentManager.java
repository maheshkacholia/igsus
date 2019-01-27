/*
 * Created on Feb 13, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.component;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.PaintContext;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.util.HashMap;
import java.util.Vector;

import com.stockfaxforu.component.*;

/**
 * @author sdeopura
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ComponentManager
{
	public int cursorType=1;
	public static int CROSSHAIR=1;
	public static int CURSOR=2;
	public static int MOUSE=3;
	GraphComponentContainer graphcontainer;
	public ComponentManager(GraphComponentContainer graphcontainer)
	{
		this.graphcontainer = graphcontainer;
	}

	private Vector component = new Vector();
	public void addComponent(Object o)
	{
		component.addElement(o);
	}
	public MYDimension findComponent(MYDimension myd)
	{
		for(int i=0;i<component.size();i++)
		{
			MYDimension myd1 = (MYDimension)component.elementAt(i);
			if((myd1.startx==myd.startx) && (myd1.endx==myd.endx) && 
			    (myd1.starty==myd.starty) && (myd1.endx==myd.endy) &&
			    (myd1.componenttype==myd.componenttype) && (myd1.radious == myd.radious))
			{
				    return (MYDimension)component.elementAt(i);
			}
		}
		return null;
		
	}
	public int  findComponentIndex(MYDimension myd)
	{
		for(int i=0;i<component.size();i++)
		{
			MYDimension myd1 = (MYDimension)component.elementAt(i);
			if((myd1.startx==myd.startx) && (myd1.endx==myd.endx) && 
				(myd1.starty==myd.starty) && (myd1.endx==myd.endy) &&
				(myd1.componenttype==myd.componenttype) && (myd1.radious == myd.radious))
			{
					return i;
			}
		}
		return -1;
		
	}

	public void removeComponentByIndex(int i)
	{
		component.remove(i);		
	}
	public void removeComponent(MYDimension myd)
	{
		int i = component.indexOf(myd);
		component.remove(i);
	}
	public MYDimension findFirstComponentWhereMouseIs(int xpos,int ypos)
	{
		for(int i=component.size()-1;i>=0;i--)
		{
			MYDimension myd = (MYDimension)component.elementAt(i);
			int startx = myd.startx;
			int endx = myd.endx;
			int starty = myd.starty;
			int endy = myd.endy;
			
			if((myd.componenttype==MYDimension.FINANCIALDATA) || (myd.componenttype==MYDimension.RECTANGLE) || (myd.componenttype==MYDimension.NOTE) || 
			(myd.componenttype==MYDimension.FIBRET) || (myd.componenttype==MYDimension.GANNFAN) || myd.componenttype==MYDimension.StockSnapShot) 
			    
			{
				if( (xpos >= startx) && (xpos <= endx) 
				      && (ypos >= starty) && (ypos <= endy))
				{
					return myd;				
				}	
			}
			else if((myd.componenttype==MYDimension.MAINGRAPH))
			{
			}
			else if((myd.componenttype==MYDimension.LINE) || (myd.componenttype==MYDimension.LINEHOR) || (myd.componenttype==MYDimension.LINEVER)
			|| (myd.componenttype==MYDimension.BuyArrow) || (myd.componenttype==MYDimension.SellArrow)
			)
			{
				if (Math.abs(startx - endx) > Math.abs(starty - endy))
				{
					if (Line2D.linesIntersect(startx,starty, endx, endy, xpos, ypos-5, xpos, ypos+5))
						return myd;
					
					
				}
				else
				{
					if (Line2D.linesIntersect(startx,starty, endx, endy, xpos-5, ypos, xpos+5, ypos))
						return myd;
					
				}
			}
			else
			{
			}
		}
		return null;
	}
	public MainGraphComponent findFirstGraphComponentWhereMouseIs(int xpos,int ypos)
	{
		for(int i=component.size()-1;i>=0;i--)
		{
			MYDimension myd = (MYDimension)component.elementAt(i);
			int startx = myd.startx;
			int endx = myd.endx;
			int starty = myd.starty;
			int endy = myd.endy;
			if(myd.startx > myd.endx )
			{
				startx = myd.endx;
				starty = myd.startx;
			}
			if(myd.starty > myd.endy )
			{
				starty = myd.endy;
				endy = myd.starty;
			}
			
			
			if((myd instanceof MainGraphComponent) )
			{
				MainGraphComponent c = (MainGraphComponent)myd;
				int height = myd.starty - myd.endy;
				int ypos1 = ypos - height;
				if( (xpos >= startx) && (xpos <= endx)  && (ypos1 <= c.starty) && (ypos1 >= c.endy))
				{
					return (MainGraphComponent)myd;				
				}	
			}

		}
		return null;
	}




	public Vector getAllComponent()
	{
		return component;
	}
	public Vector getAllIndiComponent()
	{
		Vector paintedComponent = getAllComponent();
		Vector retVector = new Vector();
		for (int x = 0; x < paintedComponent.size(); x++)
		{
			
			MYDimension myd = (MYDimension) paintedComponent.elementAt(x);
			if(myd instanceof MainGraphComponent)
			{
				if(myd.componenttype != myd.MAINGRAPH && myd.componenttype != myd.VOLUMEGRAPH)
				{
					retVector.addElement(myd);
				}
			}
			
		}
		return retVector;
	}
	public Vector getAllIndiDrawComponent()
		{
			Vector paintedComponent = getAllComponent();
			Vector retVector = new Vector();
			for (int x = 0; x < paintedComponent.size(); x++)
			{
			
				MYDimension myd = (MYDimension) paintedComponent.elementAt(x);
				if(myd.componenttype != myd.MAINGRAPH && myd.componenttype != myd.VOLUMEGRAPH)
				{
					retVector.addElement(myd);
				}
			
			}
			return retVector;
		}
	
	
	public Vector getAllGraphComponent()
	{
		Vector paintedComponent = getAllComponent();
		Vector retVector = new Vector();
		for (int x = 0; x < paintedComponent.size(); x++)
		{
			
			MYDimension myd = (MYDimension) paintedComponent.elementAt(x);
			if(myd instanceof MainGraphComponent)
			{
					retVector.addElement(myd);
			}
			
		}
		return retVector;
	}
	public void removeAllDrawingComponent()
	{
		for(int i=0;i<this.component.size();i++)
		{
			MYDimension myd = (MYDimension) this.component.elementAt(i);
			if(!(myd instanceof MainGraphComponent))
			{
				this.component.remove(i);
				i=i-1;
			}
			
		}
		// TODO Auto-generated method stub
		
	}

	public Vector getAllDrawingComponent()
		{
			Vector v = new Vector();
			for(int i=0;i<this.component.size();i++)
			{
				MYDimension myd = (MYDimension) this.component.elementAt(i);
				if(!(myd instanceof MainGraphComponent))
				{
					v.addElement(myd);
				}
			
			}
			return v;
			// TODO Auto-generated method stub
		
		}

	/**
	 * @param xxpos
	 * @param yypos
	 * @param mydimension
	 */
	public void updateXYCord(int xxpos, int yypos,int xdiffnew,int ydiffnew, MYDimension mydimension)
	{

		mydimension.updateXYCord(xxpos, yypos,xdiffnew,ydiffnew);

		
	}
	public void expand(int xxpos, int yypos,int xdiffnew,int ydiffnew, MYDimension mydimension)
	{

		mydimension.expand(xxpos, yypos,xdiffnew,ydiffnew);
		
	}
	/**
	 * @param g
	 */
	public void showAllAddedComponent(Graphics g)
	{

		Vector paintedComponent = getAllComponent();
		for (int x = 0; x < paintedComponent.size(); x++)
		{
			MYDimension myd = (MYDimension) paintedComponent.elementAt(x);
			myd.drawComponent(g);
		}
		
	}
	public void showAllAddedComponentForImage(Graphics g)
	{

		Vector paintedComponent = getAllComponent();
		for (int x = 0; x < paintedComponent.size(); x++)
		{
			MYDimension myd = (MYDimension) paintedComponent.elementAt(x);
			myd.drawComponent(g);
			if( myd instanceof MainGraphComponent)
			{
				MainGraphComponent m =((MainGraphComponent)myd); 
				m.showPriceOnLeft(m.getXPosition(m.inputdata.size() - 1), 0, g);
				
			}
			
		}
		
	}
		
	
	public void drawCrossHair(int xpos,int ypos, Graphics g)
	{
		try
		{
			Vector paintedComponent = this.getAllComponent();
			MainGraphComponent c = findFirstGraphComponentWhereMouseIs(xpos, ypos);
			if(c==null)
				return;
			String date = c.getDateForXpos(xpos);
				((MainGraphInterface)c).drawCursor(xpos,ypos,this.cursorType, g);
				
			for (int x = 0; x < paintedComponent.size(); x++)
			{
				MYDimension myd = (MYDimension)paintedComponent.elementAt(x);
				if(myd instanceof MainGraphComponent && !myd.equals(c) )
				{
	
					int actxpos = ((MainGraphComponent)myd).getXPosFromDate(date);
					((MainGraphComponent)myd).drawCursor(xpos,0,this.cursorType, g);
				}
				
			}
		}
		catch(Exception e)
		{
			
		}
	}
	/**
	 * @param dimension
	 */
	public void removeIndComponent(MYDimension dimension)
	{
		component.remove(dimension);
		// TODO Auto-generated method stub
		
	}
	/**
	 * 
	 */
	/**
	 * @return
	 */
	

	
}

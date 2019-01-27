/*
 * Created on Mar 5, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.util;

import java.util.HashMap;
import java.util.Vector;

import com.stockfaxforu.component.IntradayThread;

/**
 * @author sdeopura
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class GlobalData
{
	public static String MAIN_SCREEN = "MAIN_SCREEN";
	public static String INTRADAY_THREAD = "INTRADAY_THREAD";
	
	
	public static HashMap screenHash = new HashMap();
	public static HashMap stockDraw = new HashMap();
	public static Vector visitedStock = new Vector();

	public static void addVisitedStock(String symbol)
	{
		if ( !visitedStock.contains(symbol))
		{
			visitedStock.add(symbol);
		}
	}
	public static void addIntradayThread(Object obj)
	{
		Thread th = getIntradayThread();
		try
		{
			 th = GlobalData.getIntradayThread();
			if(th != null)
			{
				th.stop();
				th=null;
			}

		}
		catch(Exception e)
		{
			
		}
		screenHash.put(INTRADAY_THREAD, obj);
	}
	public static Thread getIntradayThread()
	{
		return (Thread)screenHash.get(INTRADAY_THREAD);
	}

	public static void removeIntradayThread()
	{
		try
		{
			screenHash.remove(INTRADAY_THREAD);
		}
		catch(Exception e)
		{
		}
	}

	public static void addScreen(Object obj)
	{
		screenHash.put(MAIN_SCREEN, obj);
	}
	public static Object getScreen()
	{
		return screenHash.get(MAIN_SCREEN);
	}
	public static void addDrawingObject(String symbol,Object obj)
	{
		stockDraw.put(symbol, obj);
	}
	public static Object getDrawingObject(String symbol)
		{
			return stockDraw.get(symbol);
		}
	/**
	 * @param symbol
	 * @param o
	 */
	public static void addDrawingSingleComponent(String symbol, Object o)
	{
		Vector v  = (Vector)stockDraw.get(symbol);
		if(v==null)
		{
			v = new Vector();
			v.addElement(o);
			stockDraw.put(symbol,v);
		}
		else
		{
			v.addElement(o);
		}
		// TODO Auto-generated method stub
		
	}
	/**
	 * @param symbol
	 * @param o
	 */
	public static void removeDrawingSingleComponent(String symbol, Object o)
	{
		Vector v  = (Vector)stockDraw.get(symbol);
		if(v !=null)
		{
			int i = v.indexOf(o);
			try
			{
				v.remove(i);
				
			}
			catch(Exception e)
			{
				
			}
		}
	}
	
	
}

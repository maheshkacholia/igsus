/*
 * Created on Jun 23, 2007
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.purelive;

import java.util.Iterator;

import com.stockfaxforu.util.StockConstants;


/**
 * @author sdeopura
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CollectLiveMarketDataMaster
{
	public static boolean startcollecting=false;
	public static void startCollecting()
	{
		String[] files = {"/igs1/ICICICODE1.txt","/igs1/ICICICODE2.txt"};
		for(int i=0;i<files.length;i++)
		{
			CollectLiveMarketData c = new CollectLiveMarketData(files[i]);
			Thread th = new Thread(c);
			th.start(); 	
		}
		startcollecting=true;

	}
	public static String getLiveStockData()
	{
		StringBuffer sb = new StringBuffer();
		Iterator it = StockConstants.livemarketDataMap.keySet().iterator();
		while(it.hasNext())
		{
			String s = (String)StockConstants.livemarketDataMap.get(it.next());
			sb.append(s+"<br>");
		}
		return sb.toString();
	}
}

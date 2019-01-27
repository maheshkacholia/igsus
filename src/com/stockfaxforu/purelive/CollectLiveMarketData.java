/*
 * Created on Jun 23, 2007
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.purelive;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Vector;

import com.stockfaxforu.util.StockConstants;


/**
 * @author sdeopura
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CollectLiveMarketData implements Runnable
{

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	String fileName="";

	public  CollectLiveMarketData(String fileName)
	{
		this.fileName = fileName;
	}
	public void run()
	{
		Vector v = new Vector();
		RandomAccessFile r = null;
		String line="";
		LiveMarket live = new LiveMarket();
		try
		{
			r = new RandomAccessFile(fileName,"r");
			while((line=r.readLine()) != null)
			{
				v.addElement(line);
				
			}
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			try
			{
				r.close();
			} catch (IOException e1)
			{
				// TODO Auto-generated catch block
			}
		}
		
		int i=0;
		while(true)
		{
			if(i==v.size())
				i=0;
			String symbol = (String)v.elementAt(i);
			String output = live.getDataInStr(symbol);
			if(output != null && !output.equals(""))
			{
				StockConstants.livemarketDataMap.put(symbol, output);		
			}
			i++;
		}
		
	}
}

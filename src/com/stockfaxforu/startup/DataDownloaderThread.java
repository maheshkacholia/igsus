package com.stockfaxforu.startup;

import java.util.HashMap;
import java.util.Vector;

import com.stockfaxforu.component.MainGraphComponent;
import com.stockfaxforu.scan.util.StockScreenerThreadMaster;
import com.stockfaxforu.util.IndexUtility;
import com.stockfaxforu.util.StockConstants;

public class DataDownloaderThread implements Runnable
{
	public static void main(String[] args)
	{
		DataDownloaderThread thread = new DataDownloaderThread();
		thread.run();
		
	}

	@Override
	public void run() 
	
	
	{
		System.out.println("start date"+StockConstants.STARTDATE);
		System.out.println("end date"+StockConstants.ENDDATE);
		System.out.println("end date"+StockConstants.SCREENDATE);
		
		
		Vector v = IndexUtility.getIndexStockVector("cnx500");
		Vector out = new Vector();
		for(int i=0;i<v.size();i++)
		{
			HashMap hs = new HashMap();
			hs.put(MainGraphComponent.Symbol, v.get(i));
			out.add(hs);
		}
	
		StockScreenerThreadMaster runnable = new StockScreenerThreadMaster();
		runnable.symbolArrayList = out;
		int mysize = out.size();
		
		runnable.datadownloadscreen = null; 
		Thread thread = new Thread(runnable);
		thread.start();
		// TODO Auto-generated method stub
		
	}

}

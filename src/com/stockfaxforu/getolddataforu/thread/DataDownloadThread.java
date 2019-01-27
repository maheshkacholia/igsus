/*
 * Created on May 8, 2009
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.getolddataforu.thread;

import java.util.Vector;

import com.stockfaxforu.scan.util.StockScreenerThreadMaster;

/**
 * @author Mahesh
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class DataDownloadThread implements Runnable
{

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	Vector stockList = null;
	public StockScreenerThreadMaster stockscreenmstr;
	public boolean loaded = false;
	public DataDownloadThread(Vector stockList)
	{
		this.stockList = stockList; 
	}
	public void run()
	{
		stockscreenmstr = new StockScreenerThreadMaster();
		loaded = false;
		Vector output = stockscreenmstr.getAllStocksMultipleQueryHash(stockList, "CLOSE[0] ");
		loaded = true;
		// TODO Auto-generated method stub
		
	}

	
}

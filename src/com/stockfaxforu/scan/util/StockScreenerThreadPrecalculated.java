/*
 * Created on Jun 30, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.scan.util;

import java.util.Vector;

import com.stockfaxforu.strategy.BuySellStrategy;

/**
 * @author sdeopura
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class StockScreenerThreadPrecalculated implements Runnable
{

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	Vector symbolvector;
	String query;
	StockScreenerThreadMasterPrecalculated mster;
	int queryno;
	public  StockScreenerThreadPrecalculated(Vector symbolvector,String query,StockScreenerThreadMasterPrecalculated mster,int queryno)
	{
		this.symbolvector = symbolvector;
		this.query = query;
		this.mster = mster;
		this.queryno = queryno;
	}
	public void run()
	{
		long t1 = System.currentTimeMillis();
	
		BuySellStrategy buysell = new BuySellStrategy();
//		String[] s = buysell.getAllStocksMultipleQuery(symbolvector, query);
		Vector s = null ; //buysell.getHashMapForIndicators(symbolvector, query);
		
		mster.appendResultHash(s);
	
		long t2 = System.currentTimeMillis();
	}
}

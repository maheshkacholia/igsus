/*
 * Created on Jun 30, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.scan.util;

import java.util.ArrayList;
import java.util.Vector;

import com.stockfaxforu.strategy.BuySellStrategy;
import com.stockfaxforu.util.StockConstants;

/**
 * @author sdeopura
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class StockScreenerThread implements Runnable
{

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	Vector symbolvector;
	String query;
	StockScreenerThreadMaster mster;
	int queryno;
	public  StockScreenerThread(Vector symbolvector,String query,StockScreenerThreadMaster mster,int queryno)
	{
//		// ln("threa created");
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
		buysell.stscmstr = this.mster;
		Vector s = buysell.getAllStocksMultipleQueryHashmap(symbolvector, query);
		
		mster.appendResultHash(s);
	
		long t2 = System.currentTimeMillis();
		
	//	System.out.println("start date"+StockConstants.STARTDATE);
	//	System.out.println("end date"+StockConstants.ENDDATE);
	//	System.out.println("end date"+StockConstants.SCREENDATE);
		
		System.out.println("times taken"+(t2-t1)/1000);
		
	}
}

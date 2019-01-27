/*
 * Created on Jan 16, 2007
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.getolddataforu.thread;

import com.stockfaxforu.getolddataforu.loader.Loader;
import com.stockfaxforu.util.StockConstants;

/**
 * @author sdeopu1
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class SplitThread implements Runnable
{

	/**
	 * @param i
	 * @param j
	 */
	int startPos;
	int endPos;
	public static int counter=0;
	public SplitThread(int i, int j)
	{
		startPos = i;
		endPos = j;
		
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run()
	{
		
		Loader.loadAllDataFromPosToPOs(StockConstants.STARTDATE,startPos,endPos);
		// TODO Auto-generated method stub

	}

	public static void main(String[] args)
	{
	}
	public static synchronized void addCounter()
	{
		counter++;
//		// ln("counter incremented to "+counter);
	}
}

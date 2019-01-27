/*
 * Created on Jun 30, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.scan.util;

import java.util.Vector;

/**
 * @author sdeopura
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class StockScreenerThreadMasterPrecalculated
{
	public static int divider=50;
	public Vector resultVector = new Vector();
	int completedCounter;


	public Vector getAllStocksMultipleQueryHash(Vector symbolVector, String query)
	{
		int counter=0;
		Vector sublistVec = new Vector();
		int i=0;
		completedCounter=0;

		 while(counter < symbolVector.size())
		 {
			Runnable mhyth = new StockScreenerThreadPrecalculated(subList(symbolVector,counter, counter+divider),query,this,i);
			Thread th = new Thread(mhyth);
			th.start();

			counter = counter + divider;	
			i++;		 	
		 }
		 while(completedCounter < i)
		 {
			try
			{
				Thread.sleep(2000);
			}
			catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
			}
		 }
		 return resultVector;
	}


	public Vector subList(Vector main,int startPos,int endPos)
	{
		long t1 = System.currentTimeMillis();
		Vector retVector = new Vector();

		if(endPos >= main.size())
			endPos = main.size();

		if(endPos <= startPos)
			return retVector;
				
		for(int i=startPos; i < endPos;i++)
		{
			retVector.addElement(main.elementAt(i));
		}
		long t2 = System.currentTimeMillis();


		return retVector;
	}
	/**
	 * @param s
	 */
	public synchronized void appendResult(String[] s)
	{
		if(s != null)
		{
			for(int i=0;i<s.length;i++)
				this.resultVector.addElement(s[i]);
		}
		completedCounter++;

		// TODO Auto-generated method stub
		
	}
	public synchronized void appendResultHash(Vector s)
	{
		
		if(s != null && s.size() != 0)
		{
			resultVector.addAll(s);
		}
		completedCounter++;

		// TODO Auto-generated method stub
		
	}

}

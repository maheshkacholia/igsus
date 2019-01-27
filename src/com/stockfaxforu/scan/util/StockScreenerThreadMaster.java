/*
 * Created on Jun 30, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.scan.util;

import java.util.ArrayList;
import java.util.Vector;

import com.stockfaxforu.formulabuilder.MessageDiaglog;
import com.stockfaxforu.frontend.DataDownloadScreen;
import com.stockfaxforu.util.StockConstants;

/**
 * @author sdeopura
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class StockScreenerThreadMaster implements Runnable
{
	public static int divider=50;
	public Vector resultArrayList = new Vector();
	public int completedCounter;
	public int completed;
	public int stockdone = 0;
	public DataDownloadScreen datadownloadscreen ;
	public Vector stockvector;
	public Vector symbolArrayList;

	public String[] getAllStocksMultipleQuery(Vector symbolArrayList, String query)
	{
		int counter=0;
		Vector sublistVec = new Vector();
		int i=0;
		completedCounter=0;
		
		 while(counter < symbolArrayList.size())
		 {
			Runnable mhyth = new StockScreenerThread(subList(symbolArrayList,counter, counter+divider),query,this,i);
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
		 String[] output = new String[resultArrayList.size()];
		 for(i=0;i<resultArrayList.size();i++)
		 {
		 	output[i] =  (String)resultArrayList.get(i);
		 }
	
		 return output;
	}

	public Vector getAllStocksMultipleQueryHash(Vector symbolArrayList, String query)
	{
		int counter=0;
		Vector sublistVec = new Vector();
		int i=0;
		completedCounter=0;

		 while(counter < symbolArrayList.size())
		 {
			Runnable mhyth = new StockScreenerThread(subList(symbolArrayList,counter, counter+divider),query,this,i);
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
		 return resultArrayList;
	}


	public Vector subList(Vector main,int startPos,int endPos)
	{
		long t1 = System.currentTimeMillis();
		Vector retArrayList = new Vector();

		if(endPos >= main.size())
			endPos = main.size();

		if(endPos <= startPos)
			return retArrayList;
				
		for(int i=startPos; i < endPos;i++)
		{
			retArrayList.add(main.get(i));
		}
		long t2 = System.currentTimeMillis();


		return retArrayList;
	}
	/**
	 * @param s
	 */
	public synchronized void appendResult(String[] s)
	{
		if(s != null)
		{
			for(int i=0;i<s.length;i++)
				this.resultArrayList.add(s[i]);
//			stockdone = resultArrayList.size();
		}
		completedCounter++;

		// TODO Auto-generated method stub
		
	}
	public synchronized void appendResultHash(Vector s)
	{
		
		if(s != null && s.size() != 0)
		{
			resultArrayList.addAll(s);
//			stockdone = resultArrayList.size();
			
		}
		completedCounter++;

		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run()
	{
		long t1 = System.currentTimeMillis();
		int counter=0;
		Vector sublistVec = new Vector();
		int i=0;
		completedCounter=0;
		ArrayList list = new ArrayList();	
		 while(counter < this.symbolArrayList.size())
		 {
			Runnable mhyth = new StockScreenerThread(subList(symbolArrayList,counter, counter+divider)," CLOSE[0] and TOTALINCOME[0] ",this,i);
			Thread th = new Thread(mhyth);
			th.start();

			counter = counter + divider;	
			i++;		 	
		 }
		 while(completedCounter < i)
		 {
			try
			{
				Thread.sleep(1000);
			}
			catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
			}
			if(datadownloadscreen != null)
			{
				this.datadownloadscreen.progress.setValue(stockdone);
				this.datadownloadscreen.repaint();
				
			}
		 }
			if(datadownloadscreen != null)
			{
				 this.datadownloadscreen.install.setEnabled(true);
					
					this.datadownloadscreen.downloadedIndex.put(this.datadownloadscreen.indexName, this.datadownloadscreen.indexName);
					MessageDiaglog msg = new MessageDiaglog("All data downladed for category ")	;		


			}
			else
			{
				StockConstants.DATADOWNLOADED= true;
			}
		StockConstants.RESOLUTION=1;
		long t2 = System.currentTimeMillis();
		System.out.println((t2-t1)/1000 + " seconds");
	}

}

package com.stockfaxforu.scan.frontend;

import com.stockfaxforu.formulabuilder.MessageDiaglog;

public class LiveScannerThread implements Runnable
{
	StockLiveFilter stocklive;
	public LiveScannerThread(StockLiveFilter stocklive)
	{
		this.stocklive = stocklive;
	}
	public void run()
	{
			while(true)
			{
				long l1 = System.currentTimeMillis();
				this.stocklive.dataValues = this.stocklive.drawStrategy();
				this.stocklive.addTable();			
				try
				{
					Thread.sleep(1000);
				}
				catch (InterruptedException e1)
				{
					// TODO Auto-generated catch block
			//		e1.printStackTrace();
				}
				long l2 = System.currentTimeMillis();
				System.out.println("time="+(l2 - l1)/1000);
			}
				
		// TODO Auto-generated method stub

	}

}

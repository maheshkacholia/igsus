package com.stockfaxforu.dataextractor;

import java.util.Vector;

public class DataExtractorThread implements Runnable
{
	Vector stocklist;
	public DataExtractorThread(Vector list)
	{
		this.stocklist = list;
	}
	public void run() 
	{
		DataExtractor.getStockDetail(this.stocklist);
	}

}

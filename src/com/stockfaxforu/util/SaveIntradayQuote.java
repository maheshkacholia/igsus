package com.stockfaxforu.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;

public class SaveIntradayQuote
{
	public static void main(String[] args)
	{
//		saveIntradayQuote("TCS");
		long l1 = System.currentTimeMillis();
		saveIntradayDataForCategory("cnx500");
		long l2 = System.currentTimeMillis();
		// ln((l2 - l1)/1000);
	}
	public static void saveIntradayDataForCategory(String category)
	{
		try
		{
			Properties p = new Properties();
			FileInputStream f = new FileInputStream(StockConstants.INSTALL_DIR +"/indexsymbol.txt");
			p.load(f);
			String s = (String)p.get(category);
			StringTokenizer st = new StringTokenizer(s,"|");
			while(st.hasMoreTokens())
			{
				try
				{
					String symbol = st.nextToken();
					saveIntradayQuote(symbol);
					
				}
				catch(Exception e4)
				{
					
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	public static void saveIntradayQuote(String symbol)
	{
		String data=null;
		
		StringBuffer sb = new StringBuffer();
		try
		{
			data = Utility.getUrlContent("http://www.indiabulls.com/securities/research/techanalysis/intraday.aspx?symbol="+symbol);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}
		int xx = 0;
		int i = 0;
		StringTokenizer st = new StringTokenizer(data,"\n");
		long oldvol=0;
		long newvol=0;
		String lastdate="";
		while (st.hasMoreTokens())
		{
			try
			{
				HashMap hs = new HashMap();
				String line = st.nextToken().trim();
				StringTokenizer linest = new StringTokenizer(line,",");
				String type = linest.nextToken();
				StringTokenizer datest = new StringTokenizer(type,":");
				datest.nextToken();
				String rdate1 = datest.nextToken();

				String date = linest.nextToken();
			//	String time = linest.nextToken();
				String price = linest.nextToken();
				datest = new StringTokenizer(date,":");
				String date1 = datest.nextToken();
				String date2 = datest.nextToken();
				String date3 = datest.nextToken();
				if (Integer.parseInt(date1) < 8)
				{
					int x = Integer.parseInt(date1) + 12;
					date = x +":" + date2 + ":" + date3;
	//				// ln(date);
				}
				date = rdate1 + "-" + date;
				
				String chg = linest.nextToken();
				String volume = linest.nextToken();
				long l = Long.parseLong(volume);
				newvol = l - oldvol + 1;
				oldvol = l;
				String s = date +"," + price + "," + volume + "\n";
				// ln(s);
				sb.append(s);
				lastdate = rdate1;
			}
			catch (Exception e1)
			{
//				e1.printStackTrace();
			}
			i++;
			
		}
		if(!lastdate.equals(""))
		{
			lastdate = lastdate.replace('/', '-');
			File f = new File(StockConstants.INSTALL_DIR+"/intraday/"+lastdate);
			if(!f.exists())
				f.mkdirs();
			try
			{
				
				Utility.saveContent(StockConstants.INSTALL_DIR+"/intraday/"+lastdate+"/"+symbol+".csv", sb.toString());
			}
			catch (Exception e)
			{
				// TODO Auto-generated catch block
//				e.printStackTrace();
			}
		}
	}
	
	
}

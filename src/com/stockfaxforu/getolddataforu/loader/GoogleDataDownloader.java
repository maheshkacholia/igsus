package com.stockfaxforu.getolddataforu.loader;

import java.io.File;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.TimeZone;
import java.util.Vector;

import com.stockfaxforu.component.MainGraphComponent;
import com.stockfaxforu.util.StockConstants;
import com.stockfaxforu.util.Utility;

public class GoogleDataDownloader {

	public static int INTRADAY=1;
	public static int DAILY=2;
	
	public static Vector getFinalRecordFromGoogle(String symbol) throws Exception
	{
		return getFinalRecordFromGoogle(symbol,DAILY);
	}
	public static Vector getFinalRecordFromGoogleIntraday(String symbol) throws Exception
	{
		return getFinalRecordFromGoogle(symbol,INTRADAY);
	}
	public static Vector getFinalRecord(String symbol,int type) throws Exception
	{
		String url = "https://app.quotemedia.com/quotetools/getHistoryDownload.csv?&webmasterId=501&startDay=02&startMonth=02&startYear=2014&endDay=07&endMonth=01&endYear=2020&isRanged=false&symbol="+symbol;
		System.out.println(url);
		String data =null;		
		data = Utility.getUrlContent(url);
		//data  = Utility.getFileContent("/stocks/test.txt");
		StringBuffer dataBuffer=new StringBuffer();
	//	System.out.println(data);
		long timezonediff=0;
		StringTokenizer st = new StringTokenizer(data,"\n");
		long interval=0;
		long orginaltime=0;
		System.out.println(data);
		Vector inputdata1=new Vector();
		String[] lines = data.split("\n");
		System.out.println(lines[1]);
		
		for(int i=1;i<lines.length;i++)
		{	
			try
			{
				String[] newdataline= lines[i].split(",");
				HashMap hs = new HashMap();
				hs.put(MainGraphComponent.Date, newdataline[0]);
				hs.put(MainGraphComponent.Open, newdataline[1]);
				
				hs.put(MainGraphComponent.High, newdataline[2]);
				
				hs.put(MainGraphComponent.Low, newdataline[3]);
				
				hs.put(MainGraphComponent.Close, newdataline[4]);
				
				
				hs.put(MainGraphComponent.Volume, newdataline[5]);
				System.out.println(hs);
				inputdata1.add(hs);
				
			}
			catch(Exception e1)
			{
				
			}
			
		}	
			
		Vector inputdata2=new Vector();
		
		for(int i=inputdata1.size()-1;i>=0;i--)
		{	
			inputdata2.addElement(inputdata1.get(i));
			dataBuffer.append(YahooDataDownloader.getStringFromHashMap((HashMap)inputdata1.get(i))+"\n");
		}	
		if(dataBuffer.toString().length() > 80 && type==DAILY)
		{
			Utility.saveContent(StockConstants.INSTALL_DIR+"/data/"+symbol+".csv", dataBuffer.toString());
		}
		return inputdata2;
	}
	public static Vector getFinalRecordFromGoogle(String symbol,int type) throws Exception
	{
		if(true)
		{
			return getFinalRecord(symbol, type);
		}
		Vector inputdata1=new Vector();
		
		String url = "https://www.google.com/finance/getprices?q="+URLEncoder.encode(symbol) + "&i=86400&p=2Y&f=d,c,h,l,o,v";
		if(type==INTRADAY)
		{
			url = "https://www.google.com/finance/getprices?q="+URLEncoder.encode(symbol) + "&i=60&p=5d&f=d,c,h,l,o,v";
		}
		System.out.println(url);
		String data =null;		
		data = Utility.getUrlContent(url);
		//data  = Utility.getFileContent("/stocks/test.txt");
		StringBuffer dataBuffer=new StringBuffer();
	//	System.out.println(data);
		long timezonediff=0;
		StringTokenizer st = new StringTokenizer(data,"\n");
		long interval=0;
		long orginaltime=0;
		while(true)
		{	
			String s =st.nextToken().toUpperCase();
			if(s.startsWith("DATA"))
			{
				break;
			}
			else if (s.startsWith("INTERVAL"))
			{
				if (s.toUpperCase().indexOf("INTERVAL") != -1 )
				{
					String[] s11 = s.split("=");
					interval = Long.parseLong(s11[1].trim()) * 1000;
					continue;
				}
			}
			
		}
		while(st.hasMoreTokens())
		{	
			try
			{
				String newdataline= st.nextToken();
				if (newdataline.toUpperCase().indexOf("TIMEZONE_OFFSET") != -1 )
				{
					String[] s11 = newdataline.split("=");
					timezonediff = Long.parseLong(s11[1].trim()) * 1000 * 60;;
					continue;
				}
				
				StringTokenizer dataline = new StringTokenizer(newdataline,",");
				HashMap hs = new HashMap();
				String time = dataline.nextToken().trim();
				Calendar c = Calendar.getInstance();
				
				if(time.startsWith("a"))
				{
					time = time.replace("a", "").trim();
					orginaltime = Long.parseLong(time) * 1000l;
					c.setTimeZone(TimeZone.getTimeZone("GMT"));
					c.setTimeInMillis(orginaltime + timezonediff );
						
				}
				else
				{
					time = time.trim();
					long l1 = Long.parseLong(time);
					l1 = orginaltime + l1*interval + timezonediff;
					c.setTimeZone(TimeZone.getTimeZone("GMT"));
					c.setTimeInMillis(l1 );
					
				}
				//date example 2014-03-31
				String time1 =  c.get(Calendar.YEAR) + "-" + ( c.get(Calendar.MONTH) + 1 ) + "-" + c.get(Calendar.DATE);
				if(type==INTRADAY)
				{
					time1 = ( c.get(Calendar.MONTH) +1 ) +"/" + c.get(Calendar.DATE) + "/" + c.get(Calendar.YEAR) + "-" +    c.get(Calendar.HOUR_OF_DAY) +":" + c.get(Calendar.MINUTE) +":" + c.get(Calendar.SECOND);
					
				}
				hs.put(MainGraphComponent.Date, time1);
						
				String close = dataline.nextToken().trim();
				hs.put(MainGraphComponent.Close, close);
				
				String high = dataline.nextToken().trim();
				hs.put(MainGraphComponent.High, high);
				
				String low = dataline.nextToken().trim();
				hs.put(MainGraphComponent.Low, low);
				
				String open = dataline.nextToken().trim();
				hs.put(MainGraphComponent.Open, open);
				
				
				String volume = dataline.nextToken().trim();
				hs.put(MainGraphComponent.Volume, volume);
				
		//		String totalvolume = dataline.nextToken().trim();
				hs.put(MainGraphComponent.TotalVolume, volume);
				hs.put(MainGraphComponent.Volume, volume);
				
				if((Float.parseFloat(close) < 0.001f) || (Float.parseFloat(open) < 0.001f) || (Float.parseFloat(high) < 0.001f) || (Float.parseFloat(low) < 0.001f)) 
					continue;
				inputdata1.add(hs);
				
				dataBuffer.append(YahooDataDownloader.getStringFromHashMap(hs)+"\n");
			}
			catch(Exception e1)
			{
				
			}
			
		}	
		if(dataBuffer.toString().length() > 80 && type==DAILY)
		{
			Utility.saveContent(StockConstants.INSTALL_DIR+"/data/"+symbol+".csv", dataBuffer.toString());
		}
			
	
		return inputdata1;
	}
	
//	public static Vector getDataFromYahoo(String symbol)
//	{
//		try
//		{
//	//		String data =ConvertDataUtility.getURLDataFromYahoo(symbol);
//
//	//		ArrayList list = ConvertDataUtility.convertYahooDataIntoArray(data);
//	//		return list;
//		
////			symbol=symbol.trim().toLowerCase();
//			// http://ichart.finance.yahoo.com/table.csv?s=IBM&d=3&e=4&f=2009&g=d&a=0&b=2&c=1962&ignore=.csv
//					            //http://chartapi.finance.yahoo.com/instrument/1.0/%5Edji/chartdata;type=quote;range=1d/csv/
//					
//			
//			File f = new File(StockConstants.INSTALL_DIR + "/data/" + symbol + ".csv");
//			Calendar c = Calendar.getInstance();
//			Calendar c1 = Calendar.getInstance();
//			c1.setTimeInMillis(f.lastModified());
//			c1.add(Calendar.HOUR, 6);
//			System.out.println("c1="+c1.before(c));
//			System.out.println("exit="+f.exists());
//			
//			if(f.exists()  && c1.after(c) )
//			{
//				return YahooDataDownloader.loadFile(symbol);
//			}
//			else
//			{
//				
//			
//				String url = "http://chartapi.finance.yahoo.com/instrument/1.0/"+URLEncoder.encode(symbol)+ "/chartdata;type=quote;range=3y/csv";
//					String data =null;		
//					try
//					{
//						
//						data = Utility.getUrlContent(url);
//					}
//					catch (Exception e)
//					{
//						// TODO Auto-generated catch block
//				//		e.printStackTrace();
//					}
//					
//					
//					Vector<Object> inputdata1 = new Vector();
//						
//						String s ="";
//						StringTokenizer st = new StringTokenizer(data,"\n");
//						while(true)
//						{	
//							s =st.nextToken();
//							if (s.toLowerCase().indexOf("volume:") != -1 )
//							{
//								break;
//							}
//						}
//						st.nextToken();
//						st.nextToken();
//						HashMap hs = new HashMap();
//						StringBuilder dataBuffer = new StringBuilder();
//						while(st.hasMoreTokens())
//						{
//							try
//							{
//								String newdataline = st.nextToken();
//								StringTokenizer dataline = new StringTokenizer(newdataline,",");
//								hs = new HashMap();
//								String time = dataline.nextToken().trim();
//							//	Calendar c = Calendar.getInstance();
//							//	c.setTimeInMillis(Long.parseLong(time)*1000*60*24);
//							//	String time1 = ( c.get(Calendar.MONTH) +1 ) +"/" + c.get(Calendar.DATE) + "/" + c.get(Calendar.YEAR)  ;
//							//	String time1 =  time.substring(6,8)+"-"+time.substring(4, 6) +"-"+time.substring(0,4);
//								//yyyy-mm-dd
//								String time1 =  time.substring(0,4) +"-"+ time.substring(4, 6) +"-"+time.substring(6,8);;
//								
//								hs.put(MainGraphComponent.Date, time1);
//										
//								String close = dataline.nextToken().trim();
//								hs.put(MainGraphComponent.Close, close);
//								
//								String high = dataline.nextToken().trim();
//								hs.put(MainGraphComponent.High, high);
//								
//								String low = dataline.nextToken().trim();
//								hs.put(MainGraphComponent.Low, low);
//								
//								String open = dataline.nextToken().trim();
//								hs.put(MainGraphComponent.Open, open);
//								
//								String volume = dataline.nextToken().trim();
//								hs.put(MainGraphComponent.Volume, volume);
//								
//						//		String totalvolume = dataline.nextToken().trim();
//								hs.put(MainGraphComponent.TotalVolume, volume);
//								inputdata1.add(hs);
//								
//								dataBuffer.append(getStringFromHashMap(hs)+"\n");
//							
//							}
//							catch(Exception e)
//							{
//								
//							}
//						}
//						try
//						{
//							HashMap hs1 = GoogleDataDownloader.getFinalRecordFromGoogle(symbol);
//							
//							if(Utility.compareDate(hs1.get(MainGraphComponent.Date)+"", hs.get(MainGraphComponent.Date)+""))
//							{
//								inputdata1.add(hs1);
//								dataBuffer.append(getStringFromHashMap(hs1)+"\n");
//
//							}
//							
//						}
//						catch(Exception e)
//						{
//							
//						}
//						if(dataBuffer.toString().length() > 80)
//						{
//							Utility.saveContent(StockConstants.INSTALL_DIR+"/data/"+symbol+".csv", dataBuffer.toString());
//						}
//						
//						return inputdata1;
//			}	
//		
//		
//		}
//		catch(Exception e)
//		{
//			return new Vector();
//		}
//	}	
//	
	
	public static void main(String[] args) throws Exception
	{
		System.out.println(getFinalRecordFromGoogle("GOOG"));
	}
}

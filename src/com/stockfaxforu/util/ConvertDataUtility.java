package com.stockfaxforu.util;



import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;



import com.stockfaxforu.component.MainGraphComponent;
import com.stockfaxforu.getolddataforu.loader.GoogleDataDownloader;

public class ConvertDataUtility
{
	public static void main(String[] arags) throws Exception
	{
		System.out.println(getHistData("TCS"));
	}
	public static String getHistData(String symbol) throws Exception
	{
		/*		String s = "Instrument=FUTSTK&CDSymbol="+Symbol.toUpperCase()+"&Segment=FO&Series=EQ&CDExpiryMonth=1&FOExpiryMonth=1&"+
		"IRFExpiryMonth=27-12-2013&CDIntraExpiryMonth=28-08-2013&FOIntraExpiryMonth=29-08-2013&"+
		"IRFIntraExpiryMonth=CDDate1:30-07-2010&CDDate2=30-07-2013&PeriodType=2&Periodicity=1&"+
		"ct0=g1|1|1&ct1=g2|2|1&ctcount=2&time="+System.currentTimeMillis();
	*/				

/*
 * Instrument:FUTSTK
CDSymbol:TCS
Segment:CM
Series:EQ
CDExpiryMonth:1
FOExpiryMonth:1
IRFExpiryMonth:27-12-2013
CDIntraExpiryMonth:26-09-2013
FOIntraExpiryMonth:26-09-2013
IRFIntraExpiryMonth:
CDDate1:01-08-2013
CDDate2:15-09-2013
PeriodType:1
Periodicity:1
ct0:g1|1|1
ct1:g2|2|1
ctcount:2
time:1379189667774
 * 
 * 
 * 		
 * 
 */
		
		Calendar lastcalender = Calendar.getInstance();
		lastcalender.set(Calendar.MONTH, lastcalender.get(Calendar.MONTH)-12);
		Calendar startcalender = Calendar.getInstance();
		String lastdateStr = lastcalender.get(Calendar.DATE)+"-"+(lastcalender.get(Calendar.MONTH) + 1 )+"-"+lastcalender.get(Calendar.YEAR);
		String startdateStr = startcalender.get(Calendar.DATE)+"-"+(startcalender.get(Calendar.MONTH) + 1 )+"-"+startcalender.get(Calendar.YEAR);
	
/*
 * Instrument:FUTSTK
CDSymbol:TCS
Segment:CM
Series:EQ
CDExpiryMonth:1
FOExpiryMonth:1
IRFExpiryMonth:27-12-2013
CDIntraExpiryMonth:26-09-2013
FOIntraExpiryMonth:26-09-2013
IRFIntraExpiryMonth:
CDDate1:15-09-2010
CDDate2:15-09-2013
PeriodType:1
Periodicity:1
ct0:g1|1|1
ct1:g2|2|1
ctcount:2
time:1379267717463
Response Headersview source

 * 
 * 		
 */
		String indexsymbol = GetIndexInfo.getSymbolInfoNSE(symbol);
		String segment = "CM";
		if(indexsymbol != null)
		{
			symbol=indexsymbol;
			segment= "OI";
		}
	
		
		String s = "Instrument=FUTSTK&CDSymbol="+symbol.toUpperCase()+"&Segment="+segment+"&Series=EQ&"+
				"PeriodType=1&Periodicity=1&"+"CDDate1="+lastdateStr+"&CDDate2="+startdateStr+"&"+
				"ct0=g1|1|1&ct1=g2|2|1&ctcount=2&time=";
			
			Map keywordMap = null;
			HashMap para= new HashMap();
			ArrayList keywordList = new ArrayList();
			StringTokenizer token= new StringTokenizer(s,"&");
			while(token.hasMoreTokens())
			{
				StringTokenizer tokenNew = new StringTokenizer(token.nextToken()+"","=");
				String key="";
				if(tokenNew.hasMoreTokens())
					key=tokenNew.nextToken();
				
				String value="";
				if(tokenNew.hasMoreTokens())
					value=tokenNew.nextToken();
				if(key != null || key.equals(""))
				{	
					
					keywordList.add(para);
				}	
			}
			String data=Utility.getPostData("http://www.nseindia.com/ChartApp/install/charts/data/GetHistoricalNew.jsp", s);
			
	/*
	 * Instrument:FUTSTK
	CDSymbol:TCS
	Segment:FO
	Series:EQ
	CDExpiryMonth:1
	FOExpiryMonth:1
	IRFExpiryMonth:27-12-2013
	CDIntraExpiryMonth:28-08-2013
	FOIntraExpiryMonth:29-08-2013
	IRFIntraExpiryMonth:
	CDDate1:30-07-2010
	CDDate2:30-07-2013
	PeriodType:2
	Periodicity:1
	ct0:g1|1|1
	ct1:g2|2|1
	ctcount:2
	time:1375203159928
	 * 
	 * 
	 * 		
	 */

			return data;
		
	}
	public static String getFutureIntradayData(String Symbol,String expirydate) throws Exception
	{
/*		String s = "Instrument=FUTSTK&CDSymbol="+Symbol.toUpperCase()+"&Segment=FO&Series=EQ&CDExpiryMonth=1&FOExpiryMonth=1&"+
	"IRFExpiryMonth=27-12-2013&CDIntraExpiryMonth=28-08-2013&FOIntraExpiryMonth=29-08-2013&"+
	"IRFIntraExpiryMonth=CDDate1:30-07-2010&CDDate2=30-07-2013&PeriodType=2&Periodicity=1&"+
	"ct0=g1|1|1&ct1=g2|2|1&ctcount=2&time="+System.currentTimeMillis();
*/		
		String indexsymbol = GetIndexInfo.getSymbolInfoNSE(Symbol);
		String segment = "FO";
		String instrument = "FUTSTK";
		if(indexsymbol != null)
		{
			Symbol=indexsymbol;
			segment= "FO";
			instrument="FUTIDX";
		}
		String s = "Instrument="+instrument+"&CDSymbol="+Symbol.toUpperCase()+"&Segment=FO&Series=EQ&FOExpiryMonth=1&"+
				"FOIntraExpiryMonth=29-08-2013&"+
				"PeriodType=2&Periodicity=1&"+
				"ct0=g1|1|1&ct1=g2|2|1&ctcount=2&time=";
		
		Map keywordMap = null;new HashMap();
		ArrayList keywordList = new ArrayList();
		StringTokenizer token= new StringTokenizer(s,"&");
		while(token.hasMoreTokens())
		{
			StringTokenizer tokenNew = new StringTokenizer(token.nextToken()+"","=");
			String key=tokenNew.nextToken();
			String value=tokenNew.nextToken();
			keywordMap=new HashMap();
			keywordMap.put(key, value);
			keywordList.add(keywordMap);
		}
		String data=Utility.getPostData("http://www.nseindia.com/ChartApp/install/charts/data/GetHistoricalNew.jsp", s);
		
/*
 * Instrument:FUTSTK
CDSymbol:TCS
Segment:FO
Series:EQ
CDExpiryMonth:1
FOExpiryMonth:1
IRFExpiryMonth:27-12-2013
CDIntraExpiryMonth:28-08-2013
FOIntraExpiryMonth:29-08-2013
IRFIntraExpiryMonth:
CDDate1:30-07-2010
CDDate2:30-07-2013
PeriodType:2
Periodicity:1
ct0:g1|1|1
ct1:g2|2|1
ctcount:2
time:1375203159928
 * 
 * 
 * 		
 */

		return data;
	}
	
	
	public static String getIntradayData(String Symbol) throws Exception
	{
/*		String s = "Instrument=FUTSTK&CDSymbol="+Symbol.toUpperCase()+"&Segment=FO&Series=EQ&CDExpiryMonth=1&FOExpiryMonth=1&"+
	"IRFExpiryMonth=27-12-2013&CDIntraExpiryMonth=28-08-2013&FOIntraExpiryMonth=29-08-2013&"+
	"IRFIntraExpiryMonth=CDDate1:30-07-2010&CDDate2=30-07-2013&PeriodType=2&Periodicity=1&"+
	"ct0=g1|1|1&ct1=g2|2|1&ctcount=2&time="+System.currentTimeMillis();
*/				
				
		/*		
		Instrument:FUTSTK
		CDSymbol:CNX NIFTY
		Segment:OI
		Series:EQ
		CDExpiryMonth:1
		FOExpiryMonth:1
		IRFExpiryMonth:27-03-2014
		CDIntraExpiryMonth:29-10-2013
		FOIntraExpiryMonth:31-10-2013
		IRFIntraExpiryMonth:
		CDDate1:30-09-2010
		CDDate2:30-09-2013
		PeriodType:2
		Periodicity:1
		ct0:g1|1|1
		ct1:g2|2|1
		ctcount:2
		time:1380546884574		
*/				

		String indexsymbol = GetIndexInfo.getSymbolInfoNSE(Symbol);
		String segment = "CM";
		if(indexsymbol != null)
		{
			Symbol=indexsymbol;
			segment= "OI";
		}
		String s = "Instrument=FUTSTK&CDSymbol="+Symbol.toUpperCase()+"&Segment="+segment+"&Series=EQ&CDDate1=&CDDate2=&"+
				"ct0=g1|1|1&ct1=g2|2|1&ctcount=2&PeriodType=2&Periodicity=1&time=";

		
		Map keywordMap = null;new HashMap();
		StringTokenizer token= new StringTokenizer(s,"&");
		List  nameValuePairs = new ArrayList();
		//	nameValuePairs.add(new BasicNameValuePair("id", "12345"));

		while(token.hasMoreTokens())
		{
			StringTokenizer tokenNew = new StringTokenizer(token.nextToken()+"","=");
			String key=tokenNew.nextToken();
			String value="";
			if(tokenNew.hasMoreTokens())
				value=tokenNew.nextToken();

		//	keywordMap=new HashMap();
		//	keywordMap.put(key, value);
		//	keywordList.add(keywordMap);
			nameValuePairs.add(new HashMap().put(key, value));

		}
		String data=Utility.getPostData("http://www.nseindia.com/ChartApp/install/charts/data/GetHistoricalNew.jsp", s);
	//	String data=Utility.postData("http://www.iguidestocks.com/project/test/test.jsp?", nameValuePairs);
		
		
	
		return data;
	}

	
	
	
	
	//date|g1_o|g1_h|g1_l|g1_c|g2|g2_CUMVOL~31-07-2013 09:15|1787.8500|1790.0000|1785.1500|1789.8500|10000|10000~31-07-2013 09:16|1789.8500|1793.7500|1788.0000|1792.0000|5500|15500~31-07-2013 09:17|1792.0000|1794.9000|1791.3000|1793.2000|13250|28750~31-07-2013 09:18|1793.2000|1800.0000|1793.2000|1800.0000|11000|39750~31-07-2013 09:19|1800.5500|1802.1000|1798.5500|1801.0000|10250|50000~31-07-2013 09:20|1801.0000|1802.0000|1799.3000|1799.7000|7000|57000~31-07-2013 09:21|1799.7000|1800.6000|1799.2500|1800.6000|6750|63750~31-07-2013 09:22|1800.0000|1801.0500|1798.9500|1801.0500|17750|81500~31-07-2013 09:23|1801.9500|1804.5500|1801.0000|1804.5500|18250|99750~31-07-2013 09:24|1804.7000|1811.9000|1803.2500|1811.6000|28000|127750~31-07-2013 09:25|1811.6000|1813.0000|1810.6500|1810.6500|14500|142250~31-07-2013 09:26|1811.9500|1812.0000|1810.4500|1811.2500|6000|148250~31-07-2013 09:27|1811.2500|1814.0000|1811.2500|1813.4500|12000|160250~31-07-2013 09:28|1813.2500|1816.9000|1813.1500|1814.2500|19000|179250~31-07-2013 09:29|1814.4500|1816.8000|1812.6000|1813.5000|13750|193000~31-07-2013 09:30|1813.5000|1814.0500|1813.0000|1814.0500|7250|200250~31-07-2013 09:31|1814.0500|1815.0000|1813.4500|1814.5000|6250|206500~31-07-2013 09:32|1814.5000|1815.0000|1812.0500|1812.0500|5250|211750~31-07-2013 09:33|1813.7500|1813.7500|1811.9500|1812.5500|3750|215500~31-07-2013 09:34|1812.5500|1813.9500|1812.1500|1813.9500|5750|221250~31-07-2013 09:35|1813.4500|1813.5000|1810.4500|1810.4500|16250|237500~31-07-2013 09:36|1810.4500|1811.7500|1810.0000|1810.0500|7000|244500~31-07-2013 09:37|1810.5500|1811.0000|1810.0000|1811.0000|9500|254000~31-07-2013 09:38|1811.0000|1812.9000|1811.0000|1812.9000|4000|258000~31-07-2013 09:39|1812.9000|1812.9000|1811.5500|1811.5500|3000|261000~31-07-2013 09:40|1811.5500|1812.8000|1811.5500|1812.0500|5500|266500~31-07-2013 09:41|1812.0500|1813.1000|1810.7500|1811.5000|7000|273500~31-07-2013 09:42|1811.5000|1812.1500|1810.3500|1810.5500|7750|281250~31-07-2013 09:43|1810.5500|1812.0000|1810.1500|1811.5500|2750|284000~31-07-2013 09:44|1811.5500|1812.3000|1811.0000|1811.8000|1500|285500

	
	public static String createCorrectTimeFormat(String time)
	{
		String[] temp = time.split(" ");
		String[] timeFirstPart = temp[0].split("-");
		if (temp[1].length() < 6 )
			temp[1] = temp[1] + ":00"; 
		String finalTime = timeFirstPart[1]+"/" + timeFirstPart[0] + "/" + timeFirstPart[2] + "-" + temp[1];
		return finalTime;
	}
	public static int INTRADAY=2;
	public static int FUTURE=1;
	public static int HIST=3;
	
	
	public static Vector getHistoricalIntradayData(String symbol,String tilldate)
	{
		try {
			return GoogleDataDownloader.getFinalRecordFromGoogleIntraday(symbol);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new Vector();
		}
		
	}
	public static Vector getHistoricalIntradayDataOls(String symbol,String tilldate)
	{
		
	//	long tilldatetime = Utility.gettimeinMilli(tilldate);
		Vector inputdata1 = new Vector();
		int xx = 0;
		int i = 0;
		long oldvol=0;
		long newvol=0;
		long totvol=0;
		String symbol1 = symbol;
		if(symbol.equalsIgnoreCase("nifty"))
		{
			symbol1="^nsei";
		}
		
		try
		{
			
			String url = "http://chartapi.finance.yahoo.com/instrument/1.0/"+URLEncoder.encode(symbol1)+ "/chartdata;type=quote;range=10d/csv";
			String data =null;		
			data = Utility.getUrlContent(url);
			
			String s ="";
			StringTokenizer st = new StringTokenizer(data,"\n");
			while(true)
			{	
				s =st.nextToken();
				if (s.toLowerCase().indexOf("volume:") != -1 )
				{
					break;
				}
			}
			st.nextToken();
			st.nextToken();
			HashMap hs = new HashMap();
			
			StringBuilder finalData = new StringBuilder();
			String olddate="";
			while(st.hasMoreTokens())
			{
				try
				{
					String newdataline = st.nextToken();
					StringTokenizer dataline = new StringTokenizer(newdataline,",");
					hs = new HashMap();
					String time = dataline.nextToken().trim();
					Calendar c = Calendar.getInstance();
					long l = Long.parseLong(time)*1000;
		/*			if(l>tilldatetime)
					{
						break;
					}
		*/	
					c.setTimeInMillis(l);
				
					String time1 = ( c.get(Calendar.MONTH) +1 ) +"/" + c.get(Calendar.DATE) + "/" + c.get(Calendar.YEAR) + "-" +    c.get(Calendar.HOUR_OF_DAY) +":" + c.get(Calendar.MINUTE) +":" + c.get(Calendar.SECOND);
					String close = dataline.nextToken().trim();
					
					String high = dataline.nextToken().trim();
					
					String low = dataline.nextToken().trim();
					
					String open = dataline.nextToken().trim();
					
					String volume = dataline.nextToken().trim();
					totvol = totvol + Long.parseLong(volume);
			
					hs = new HashMap();
					hs.put(MainGraphComponent.Date, time1);
					hs.put(MainGraphComponent.Open, open);
					hs.put(MainGraphComponent.High, high);
					hs.put(MainGraphComponent.Low, low);
					hs.put(MainGraphComponent.Close, close);
					hs.put(MainGraphComponent.Volume, volume);
					hs.put(MainGraphComponent.TotalVolume, volume);
					
					inputdata1.add(hs);
				}
				catch(Exception e1)
				{
					
				}
			}
			return inputdata1;
		}
		catch(Exception e)
		{
			return inputdata1;
		}
	}
	
	public static String getFutureIntradayHistoricalDataInStrFromNSE(String symbol,
			String expirydate,int type) 
	{
		StringBuilder retStrBld = new StringBuilder();
		try
		{
			String data =null;
			if(type==FUTURE)
			{	
				data = getFutureIntradayData(symbol, expirydate);
			}
			else if(type==INTRADAY)
			{
				data = getIntradayData(symbol);
			}
			else if(type==HIST)
			{
				data = getHistData(symbol);
			}
			
			StringTokenizer st = new StringTokenizer(data,"~");
			st.nextToken();
			HashMap hs = new HashMap();
			while(st.hasMoreTokens())
			{
				String newdataline = st.nextToken();
				StringTokenizer dataline = new StringTokenizer(newdataline,"|");
				try
				{
					//ACC,EQ,03-MAY-2013,1227.3,1260,1275,1222.45,1227.3,1227.3,473075,473075

					StringBuilder  line  = new StringBuilder(symbol+",EQ,");
					String time=(dataline.nextToken()).trim();
					if(type!=HIST)
						time = Utility.convertDateIntoCSVdate(time);
					
					line.append(time+",");
							
					String open = dataline.nextToken().trim();
					line.append(open+","+open+",");
					
					String high = dataline.nextToken().trim();
					line.append(high+",");
					
					String low = dataline.nextToken().trim();
					line.append(low+",");
					
					String close = dataline.nextToken().trim();
					line.append(close+","+close+",");
					
					String volume = dataline.nextToken().trim();
					line.append(volume+","+volume+"\n");
					
					retStrBld.append(line);
				}
				catch(Exception e)
				{
					
				}
			}
		}
		catch(Exception e)
		{
//			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return retStrBld.toString();
	}
	
	
	public static Vector getFutureIntradayHistoricalDataInVectorFromNSE(String symbol,
			String expirydate,int type) 
	{
		Vector inputdata1 = new Vector();
		try
		{
			String data =null;
			if(type==FUTURE)
			{	
				data = getFutureIntradayData(symbol, expirydate);
			}
			else if(type==INTRADAY)
			{
				data = getIntradayData(symbol);
			}
			else if(type==HIST)
			{
				data = getHistData(symbol);
			}
			
			StringTokenizer st = new StringTokenizer(data,"~");
			st.nextToken();
			HashMap hs = new HashMap();
			while(st.hasMoreTokens())
			{
				String newdataline = st.nextToken();
				StringTokenizer dataline = new StringTokenizer(newdataline,"|");
				try
				{
					hs = new HashMap();
					String time=(dataline.nextToken()).trim();
					if(type!=HIST)
						time = createCorrectTimeFormat(time);
					
					hs.put(MainGraphComponent.Date, time);
							
					String open = dataline.nextToken().trim();
					hs.put(MainGraphComponent.Open, open);
					
					String high = dataline.nextToken().trim();
					hs.put(MainGraphComponent.High, high);
					
					String low = dataline.nextToken().trim();
					hs.put(MainGraphComponent.Low, low);
					
					String close = dataline.nextToken().trim();
					hs.put(MainGraphComponent.Close, close);
					
					String volume = dataline.nextToken().trim();
					hs.put(MainGraphComponent.Volume, volume);
					if(type!=HIST)
					{	
						String totalvolume = dataline.nextToken().trim();
						hs.put(MainGraphComponent.TotalVolume, totalvolume);
					}
					else
					{
						hs.put(MainGraphComponent.TotalVolume, volume);
								
					}
					inputdata1.add(hs);
					
				}
				catch(Exception e)
				{
					
				}
			}
		}
		catch(Exception e)
		{
//			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return inputdata1;
	}
	
	
	
	public static String getURLDataFromYahoo(String symbol)
	{
		symbol=symbol.trim().toLowerCase();
// http://ichart.finance.yahoo.com/table.csv?s=IBM&d=3&e=4&f=2009&g=d&a=0&b=2&c=1962&ignore=.csv
		Calendar lastcalender = Calendar.getInstance();
		Calendar startcalender = Calendar.getInstance();
		startcalender.set(startcalender.get(Calendar.YEAR)-StockConstants.YAHOO_START_YEAR, startcalender.get(Calendar.MONTH), startcalender.get(Calendar.DATE));
		
		String lastmonth=lastcalender.get(Calendar.MONTH)+"";
		String lastdate=lastcalender.get(Calendar.DATE)+"";
		String lastyear=lastcalender.get(Calendar.YEAR)+"";
	
		String startmonth=startcalender.get(Calendar.MONTH)+"";
		String startdate=startcalender.get(Calendar.DATE)+"";
		String startyear=startcalender.get(Calendar.YEAR)+"";
	
		
		String url = "http://ichart.finance.yahoo.com/table.csv?s="+symbol+"&d="+lastmonth+"&e="+lastdate+"&f="+lastyear+"&g=d&a="+startmonth+"&b="
		+startdate+"&c="+startyear+"&ignore=.csv";
	//	Log.d("mahesh",url);
		String data =null;		
		try
		{
			data = Utility.getUrlContent(url);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
	//		e.printStackTrace();
		}
		return data;
		
	}
	public static ArrayList<Object> convertYahooDataIntoArray(String data)
	{
		ArrayList<Object> inputdata1 = new ArrayList();
		ArrayList<Object> outputArrayList = new ArrayList();
			StringTokenizer st = new StringTokenizer(data,"\n");
			st.nextToken();
			HashMap hs = new HashMap();
			while(st.hasMoreTokens())
			{
				String newdataline = st.nextToken();
				StringTokenizer dataline = new StringTokenizer(newdataline,",");
				hs = new HashMap();
				String time = dataline.nextToken().trim();
				hs.put(MainGraphComponent.Date, time);
						
				String open = dataline.nextToken().trim();
				hs.put(MainGraphComponent.Open, open);
				
				String high = dataline.nextToken().trim();
				hs.put(MainGraphComponent.High, high);
				
				String low = dataline.nextToken().trim();
				hs.put(MainGraphComponent.Low, low);
				
				String close = dataline.nextToken().trim();
				hs.put(MainGraphComponent.Close, close);
				
				String volume = dataline.nextToken().trim();
				hs.put(MainGraphComponent.Volume, volume);
				
				String totalvolume = dataline.nextToken().trim();
				hs.put(MainGraphComponent.TotalVolume, volume);
				
				inputdata1.add(hs);
			}
			
			for(int i = inputdata1.size()-1;i >=0;i-- )
			{
				outputArrayList.add(inputdata1.get(i));
			}
			
		// TODO Auto-generated method stub
		return outputArrayList;	
	}
	
	public static ArrayList convertIndiaBullDataInVector(String data)
	{
		ArrayList dataVector = new ArrayList();
		int xx = 0;
		int i = 0;
		StringTokenizer st = new StringTokenizer(data, "\n");
		long oldvol = 0;
		long newvol = 0;
		long totvol = 0;
		while (st.hasMoreTokens())
		{
			try
			{
				HashMap hs = new HashMap();
				String line = st.nextToken().trim();
				StringTokenizer linest = new StringTokenizer(line, ",");
				String type = linest.nextToken();
				StringTokenizer datest = new StringTokenizer(type, ":");

				datest.nextToken();
				String rdate1 = datest.nextToken();

				String date = linest.nextToken();
				// String time = linest.nextToken();
				String price = linest.nextToken();
				datest = new StringTokenizer(date, ":");
				String date1 = datest.nextToken();
				String date2 = datest.nextToken();
				String date3 = datest.nextToken();
				if (Integer.parseInt(date1) < 8)
				{
					int x = Integer.parseInt(date1) + 12;
					date = x + ":" + date2 + ":" + date3;
					// ln(date);
				}
				date = rdate1 + "-" + date;

				String chg = linest.nextToken();
				String volume = linest.nextToken();
				long l = Long.parseLong(volume);
				// newvol = l + 1;

				newvol = l - oldvol + 1;

				totvol = totvol + l;

				oldvol = l;
				String s = date + "," + price + "," + volume + "\n";
				// ln(s);
				hs.put(MainGraphComponent.Date, date);
				// // ln(hs.get("Date"));
				hs.put(MainGraphComponent.Open, price);
				hs.put(MainGraphComponent.High, price);
				hs.put(MainGraphComponent.Low, price);
				hs.put(MainGraphComponent.Close, price);
				hs.put(MainGraphComponent.Volume, newvol+"");
				hs.put(MainGraphComponent.TotalVolume, totvol + "");
				dataVector.add(hs);
				oldvol = l;
			}
			catch (Exception e1)
			{
				// e1.printStackTrace();
			}
			i++;
		}
		return dataVector;

	}

	public static ArrayList appendNewData(ArrayList oldData, HashMap newelement)
	{
		if(newelement.get(MainGraphComponent.Volume)==null)
		{
			HashMap hs = (HashMap)oldData.get(oldData.size()-1);
			long newTotalVolume = Long.parseLong(((String)newelement.get(MainGraphComponent.TotalVolume)).trim());
			long oldTotalVolume = Long.parseLong(((String)hs.get(MainGraphComponent.TotalVolume)).trim());
			long diff = newTotalVolume - oldTotalVolume;
			if(newTotalVolume==0)
			{
				diff=0;
			}
			if(diff<0)
				return oldData;
			newelement.put(MainGraphComponent.Volume, diff+"");
		}
		oldData.add(newelement);
		return oldData;
	}

	public static ArrayList setResolutionIntraday(ArrayList inputdata1, long requiredTimeDiffInMinute)
	{
		ArrayList retVector = new ArrayList();

		try
		{
			int minute = 1;
			HashMap hs = null;
			String dateStr = null;
			String oldTime = null;
			String newTime = null;
	
			int oldPosition = 0;
			int size = inputdata1.size()-1;
			for (int i = 0; i <= size; i++)
			{
				hs = (HashMap) inputdata1.get(i);
				dateStr = (String) hs.get(MainGraphComponent.Date);
				newTime = dateStr;
				if (i == 0)
				{
					oldTime = dateStr;
					oldPosition = 0;
				}
				else
				{
					if (isTimeDiferenceGreater(requiredTimeDiffInMinute, oldTime, newTime))
					{
						HashMap hstemp = getHashMapForResStatic(inputdata1, oldPosition, i);
						retVector.add(hstemp);
						oldTime = newTime;
						oldPosition = i;
					}
					else if ((i==size)  && oldPosition !=  size)  
					{
						HashMap hstemp = getHashMapForResStatic(inputdata1, oldPosition, i);
						retVector.add(hstemp);
					
					}
				}

			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
	//		Log.d("mahesh", e.getMessage());
		}
		return retVector;

/*		String myoldtime = (String) ((HashMap) inputdata1.get(oldPosition)).get(MainGraphComponent.Date);
		String mynewtime = (String) ((HashMap) inputdata1.get(inputdata1.size() - 1)).get(MainGraphComponent.Date);
		if (isTimeDiferenceGreater(requiredTimeDiffInMinute, oldTime, newTime))
		{
			HashMap hstemp = getHashMapForResStatic(inputdata1, oldPosition, inputdata1.size() - 1);
			retVector.add(hstemp);

		}
*/	
	}

	public static HashMap getHashMapForResStatic(ArrayList inputdata1, int startPos, int endPosition)
	{

		
		if (startPos + 1 == endPosition)
		{
			return (HashMap) inputdata1.get(startPos + 1);
		}

		HashMap outHash = new HashMap();
		int start = startPos;
		HashMap hs = (HashMap) inputdata1.get(start);
		float open = Float.parseFloat((String) hs.get(MainGraphComponent.Open));
		float high = Float.parseFloat((String) hs.get(MainGraphComponent.High));
		float low = Float.parseFloat((String) hs.get(MainGraphComponent.Low));
		long volume = 0;
		outHash.put(MainGraphComponent.Open, open + "");
		
		hs = (HashMap) inputdata1.get(endPosition);
		float close = Float.parseFloat((String) hs.get(MainGraphComponent.Close));
		outHash.put(MainGraphComponent.Close, close + "");
		outHash.put(MainGraphComponent.Date, hs.get(MainGraphComponent.Date));
		int countvol = 0;
		long startVolume = 0, endVolume = 0;

		long oldtotalvolume = 0, newtotalvolume = 0;
		for (int i = startPos + 1; i <= endPosition; i++)
		{

			hs = (HashMap) inputdata1.get(i);
			float tmphigh = Float.parseFloat((String) hs.get(MainGraphComponent.High));
			float tmplow = Float.parseFloat((String) hs.get(MainGraphComponent.Low));
			long tmpvol = Long.parseLong((String) hs.get(MainGraphComponent.Volume));
			newtotalvolume = Long.parseLong(hs.get(MainGraphComponent.TotalVolume) + "");

			if (newtotalvolume < oldtotalvolume)
			{
				endVolume = oldtotalvolume;
				break;
			}

			oldtotalvolume = newtotalvolume;

			if (i == startPos + 1)
			{
				try
				{
					startVolume = Long.parseLong(hs.get(MainGraphComponent.TotalVolume) + "");

				}
				catch (Exception e)
				{
	//				e.printStackTrace();
				}

			}
			if (i == endPosition)
			{
				endVolume = Long.parseLong(hs.get(MainGraphComponent.TotalVolume) + "");
			}
			if (tmphigh > high)
			{
				high = tmphigh;
			}
			if (tmplow < low)
			{
				low = tmplow;
			}
			volume = volume + tmpvol;
			countvol++;
		}
		if (endPosition > startPos)
		{
			// volume = volume / countvol;
			volume = volume;

		}
		outHash.put(MainGraphComponent.High, high + "");
		outHash.put(MainGraphComponent.Low, low + "");

		volume = endVolume - startVolume;
		if (volume < 0)
			volume = 0;
		outHash.put(MainGraphComponent.Volume, volume + "");

		outHash.put(MainGraphComponent.TotalVolume, startVolume);

		return outHash;
	}

	public static boolean isTimeDiferenceGreater(long requireDiff, String oldTime, String newTime)
	{
		requireDiff = requireDiff * 60;
		StringTokenizer oldMainst = new StringTokenizer(oldTime, "-");
		String oldDate = oldMainst.nextToken();
		StringTokenizer oldDatest = new StringTokenizer(oldDate, "/");
		int mmold = Integer.parseInt(oldDatest.nextToken()) - 1;
		int ddold = Integer.parseInt(oldDatest.nextToken());
		int yyold = Integer.parseInt(oldDatest.nextToken());

		StringTokenizer oldSt = new StringTokenizer(oldMainst.nextToken(), ":");
		int hhOld = Integer.parseInt(oldSt.nextToken());
		int mmOld = Integer.parseInt(oldSt.nextToken());
		int ssOld = Integer.parseInt(oldSt.nextToken());

		StringTokenizer newMainst = new StringTokenizer(newTime, "-");
		String newDate = newMainst.nextToken();
		StringTokenizer newDatest = new StringTokenizer(newDate, "/");
		int mmnew = Integer.parseInt(newDatest.nextToken()) - 1;
		int ddnew = Integer.parseInt(newDatest.nextToken());
		int yynew = Integer.parseInt(newDatest.nextToken());

		StringTokenizer newSt = new StringTokenizer(newMainst.nextToken(), ":");
		int hhNew = Integer.parseInt(newSt.nextToken());
		int mmNew = Integer.parseInt(newSt.nextToken());
		int ssNew = Integer.parseInt(newSt.nextToken());

		GregorianCalendar gcOld = new GregorianCalendar(yyold, mmold, ddold, hhOld, mmOld, ssOld);
		GregorianCalendar gcNew = new GregorianCalendar(yynew, mmnew, ddnew, hhNew, mmNew, ssNew);

		long oldT = gcOld.getTime().getTime();
		long newT = gcNew.getTime().getTime();

		long difference = (newT - oldT) / 1000;
		// // ln("oldtime=" + oldTime + "   newtime=" + newTime +
		// " Elapsed milliseconds: " + difference);

		if (difference >= requireDiff)
			return true;
		else
			return false;

	}

}

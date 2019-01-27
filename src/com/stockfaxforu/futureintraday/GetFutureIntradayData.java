
package com.stockfaxforu.futureintraday;

import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;

import com.stockfaxforu.component.MainGraphComponent;
import com.stockfaxforu.util.Utility;

public class GetFutureIntradayData 
{
	public static void main(String[] args)
	{
		try {
//			getFutureIntradayData("TCS","12-12-12");
			System.out.println(getHistData("TCS"));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
//intraday data format
	//date|g1_o|g1_h|g1_l|g1_c|g2|g2_CUMVOL~31-07-2013 09:15|1787.8500|1790.0000|1785.1500|1789.8500|10000|10000~31-07-2013 09:16|1789.8500|1793.7500|1788.0000|1792.0000|5500|15500~31-07-2013 09:17|1792.0000|1794.9000|1791.3000|1793.2000|13250|28750~31-07-2013 09:18|1793.2000|1800.0000|1793.2000|1800.0000|11000|39750~31-07-2013 09:19|1800.5500|1802.1000|1798.5500|1801.0000|10250|50000~31-07-2013 09:20|1801.0000|1802.0000|1799.3000|1799.7000|7000|57000~31-07-2013 09:21|1799.7000|1800.6000|1799.2500|1800.6000|6750|63750~31-07-2013 09:22|1800.0000|1801.0500|1798.9500|1801.0500|17750|81500~31-07-2013 09:23|1801.9500|1804.5500|1801.0000|1804.5500|18250|99750~31-07-2013 09:24|1804.7000|1811.9000|1803.2500|1811.6000|28000|127750~31-07-2013 09:25|1811.6000|1813.0000|1810.6500|1810.6500|14500|142250~31-07-2013 09:26|1811.9500|1812.0000|1810.4500|1811.2500|6000|148250~31-07-2013 09:27|1811.2500|1814.0000|1811.2500|1813.4500|12000|160250~31-07-2013 09:28|1813.2500|1816.9000|1813.1500|1814.2500|19000|179250~31-07-2013 09:29|1814.4500|1816.8000|1812.6000|1813.5000|13750|193000~31-07-2013 09:30|1813.5000|1814.0500|1813.0000|1814.0500|7250|200250~31-07-2013 09:31|1814.0500|1815.0000|1813.4500|1814.5000|6250|206500~31-07-2013 09:32|1814.5000|1815.0000|1812.0500|1812.0500|5250|211750~31-07-2013 09:33|1813.7500|1813.7500|1811.9500|1812.5500|3750|215500~31-07-2013 09:34|1812.5500|1813.9500|1812.1500|1813.9500|5750|221250~31-07-2013 09:35|1813.4500|1813.5000|1810.4500|1810.4500|16250|237500~31-07-2013 09:36|1810.4500|1811.7500|1810.0000|1810.0500|7000|244500~31-07-2013 09:37|1810.5500|1811.0000|1810.0000|1811.0000|9500|254000~31-07-2013 09:38|1811.0000|1812.9000|1811.0000|1812.9000|4000|258000~31-07-2013 09:39|1812.9000|1812.9000|1811.5500|1811.5500|3000|261000~31-07-2013 09:40|1811.5500|1812.8000|1811.5500|1812.0500|5500|266500~31-07-2013 09:41|1812.0500|1813.1000|1810.7500|1811.5000|7000|273500~31-07-2013 09:42|1811.5000|1812.1500|1810.3500|1810.5500|7750|281250~31-07-2013 09:43|1810.5500|1812.0000|1810.1500|1811.5500|2750|284000~31-07-2013 09:44|1811.5500|1812.3000|1811.0000|1811.8000|1500|285500
	

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
		lastcalender.set(Calendar.MONTH, lastcalender.get(Calendar.MONTH)-2);
		Calendar startcalender = Calendar.getInstance();
		String lastdateStr = lastcalender.get(Calendar.DATE)+"-"+(lastcalender.get(Calendar.MONTH))+"-"+lastcalender.get(Calendar.YEAR);
		String startdateStr = startcalender.get(Calendar.DATE)+"-"+(startcalender.get(Calendar.MONTH))+"-"+startcalender.get(Calendar.YEAR);
		
/*Instrument:FUTSTK
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
 * 	
 */
		
		String s = "Instrument=FUTSTK&CDSymbol="+symbol.toUpperCase()+"&Segment=CM&Series=EQ&"+
					"PeriodType=1&Periodicity=1&"+"CDDate1="+"01-08-2013"+"&CDDate2="+"15-09-2013"+"&"+
					"ct0=g1|1|1&ct1=g2|2|1&ctcount=2&time=";
		System.out.println(s);	
		String request = "http://www.nseindia.com/ChartApp/install/charts/data/GetHistoricalNew.jsp";
		URL url = new URL(request);

		java.net.URLConnection conn = url.openConnection();

		conn.setDoOutput(true);

		java.io.OutputStreamWriter writer = new java.io.OutputStreamWriter(conn.getOutputStream());

		writer.write(s);
		writer.flush();

		String line;
		java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.InputStreamReader(conn.getInputStream()));
		StringBuilder sb = new StringBuilder();
		while ((line = reader.readLine()) != null) 
		{
			sb.append(line);
			System.out.println(line);
		}
		writer.close();
		reader.close();         
			return sb.toString();
		
	}

	
	public static String getIntradayData(String Symbol) throws Exception
	{
		String s = "Instrument=FUTSTK&CDSymbol="+Symbol.toUpperCase()+"&Segment=FO&Series=EQ&CDExpiryMonth=1&FOExpiryMonth=1&"+
	"IRFExpiryMonth=27-12-2013&CDIntraExpiryMonth=28-08-2013&FOIntraExpiryMonth=29-08-2013&"+
	"IRFIntraExpiryMonth=CDDate1:30-07-2010&CDDate2=30-07-2013&PeriodType=2&Periodicity=1&"+
	"ct0=g1|1|1&ct1=g2|2|1&ctcount=2&time="+System.currentTimeMillis();
				
				
//		String s = "Instrument=FUTSTK&CDSymbol="+Symbol.toUpperCase()+"&Segment=CM&Series=EQ&CDDate1=&CDDate2=&"+
//				"ct0=g1|1|1&ct1=g2|2|1&ctcount=2&PeriodType=2&Periodicity=1&time=";
		
		
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
		String urlParameters = s;
		String request = "http://www.nseindia.com/ChartApp/install/charts/data/GetHistoricalNew.jsp";
	//	String request = "http://www.iguidestocks.com/project/test/test.jsp";
		
		URL url = new URL(request);

		java.net.URLConnection conn = url.openConnection();

		conn.setDoOutput(true);

		java.io.OutputStreamWriter writer = new java.io.OutputStreamWriter(conn.getOutputStream());

		writer.write(urlParameters);
		writer.flush();

		String line;
		java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.InputStreamReader(conn.getInputStream()));
		StringBuilder sb = new StringBuilder();
		while ((line = reader.readLine()) != null) 
		{
			sb.append(line);
			System.out.println(line);
		}
		writer.close();
		reader.close();         

		return sb.toString();
	}

	
	public static String getFutureIntradayData(String Symbol,String expirydate) throws Exception
	{
/*		String s = "Instrument=FUTSTK&CDSymbol="+Symbol.toUpperCase()+"&Segment=FO&Series=EQ&CDExpiryMonth=1&FOExpiryMonth=1&"+
	"IRFExpiryMonth=27-12-2013&CDIntraExpiryMonth=28-08-2013&FOIntraExpiryMonth=29-08-2013&"+
	"IRFIntraExpiryMonth=CDDate1:30-07-2010&CDDate2=30-07-2013&PeriodType=2&Periodicity=1&"+
	"ct0=g1|1|1&ct1=g2|2|1&ctcount=2&time="+System.currentTimeMillis();
*/				
				
					
	/*		String s = "Instrument=FUTSTK&CDSymbol="+Symbol.toUpperCase()+"&Segment=FO&Series=EQ&FOExpiryMonth=1&"+
					"FOIntraExpiryMonth=29-08-2013&"+
					"PeriodType=2&Periodicity=1&"+
					"ct0=g1|1|1&ct1=g2|2|1&ctcount=2&time=";
*/
		String s = "Instrument=FUTSTK&CDSymbol="+Symbol.toUpperCase()+"&Segment=FO&Series=EQ&FOExpiryMonth=1&"+
				"FOIntraExpiryMonth="+expirydate+"&"+
				"PeriodType=2&Periodicity=1&"+
				"ct0=g1|1|1&ct1=g2|2|1&ctcount=2&time=";
		
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
		String urlParameters = s;
		                  
		String request = "http://www.nseindia.com/ChartApp/install/charts/data/GetHistoricalNew.jsp";
		URL url = new URL(request);

		java.net.URLConnection conn = url.openConnection();

		conn.setDoOutput(true);

		java.io.OutputStreamWriter writer = new java.io.OutputStreamWriter(conn.getOutputStream());

		writer.write(urlParameters);
		writer.flush();

		String line;
		java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.InputStreamReader(conn.getInputStream()));
		StringBuilder sb = new StringBuilder();
		while ((line = reader.readLine()) != null) 
		{
			sb.append(line);
			System.out.println(line);
		}
		writer.close();
		reader.close();         

		return sb.toString();
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
	public static Vector getFutureIntradayDataInVector(String symbol,
			String expirydate) 
	{
		Vector inputdata1 = new Vector();
		try
		{
			String data = getFutureIntradayData(symbol, expirydate);
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
					String time = createCorrectTimeFormat(dataline.nextToken());
					hs.put(MainGraphComponent.Date, time);
							
					String open = dataline.nextToken();
					hs.put(MainGraphComponent.Open, open);
					
					String high = dataline.nextToken();
					hs.put(MainGraphComponent.High, high);
					
					String low = dataline.nextToken();
					hs.put(MainGraphComponent.Low, low);
					
					String close = dataline.nextToken();
					hs.put(MainGraphComponent.Close, close);
					
					String volume = dataline.nextToken();
					hs.put(MainGraphComponent.Volume, volume);
					
					String totalvolume = dataline.nextToken();
					hs.put(MainGraphComponent.TotalVolume, totalvolume);
					
					inputdata1.addElement(hs);
					
				}
				catch(Exception e)
				{
					
				}
			}
		}
		catch(Exception e)
		{
			
		}
		// TODO Auto-generated method stub
		return inputdata1;
	}
}

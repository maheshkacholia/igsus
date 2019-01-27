package com.stockfaxforu.getolddataforu.loader;

import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.TimeZone;
import java.util.Vector;

import com.stockfaxforu.component.MainGraphComponent;
import com.stockfaxforu.util.StockConstants;
import com.stockfaxforu.util.Utility;

public class YahooDataDownloader {

	public static String getStringFromHashMap(HashMap hs)
	{
		String line = hs.get(MainGraphComponent.Date) +"," + hs.get(MainGraphComponent.Close) +"," + hs.get(MainGraphComponent.High) +"," +
				hs.get(MainGraphComponent.Low) +","+ hs.get(MainGraphComponent.Open) + hs.get(MainGraphComponent.Volume) + "," + hs.get(MainGraphComponent.TotalVolume) ;
		return line;
	}
	public static Vector loadFile(String symbol) throws Exception
	{
		HashMap hs = new HashMap();
		Vector<Object> inputdata1 = new Vector();
		
		String data=Utility.getFileContent(StockConstants.INSTALL_DIR+"/data/"+symbol+".csv");
		StringTokenizer st = new StringTokenizer(data,"\n");
		while(st.hasMoreTokens())
		{
			try
			{
				String newdataline = st.nextToken();
				StringTokenizer dataline = new StringTokenizer(newdataline,",");
				hs = new HashMap();
				String time = dataline.nextToken().trim();
			//	Calendar c = Calendar.getInstance();
			//	c.setTimeInMillis(Long.parseLong(time)*1000*60*24);
			//	String time1 = ( c.get(Calendar.MONTH) +1 ) +"/" + c.get(Calendar.DATE) + "/" + c.get(Calendar.YEAR)  ;
			//	String time1 =  time.substring(6,8)+"-"+time.substring(4, 6) +"-"+time.substring(0,4);
				//yyyy-mm-dd
				String time1 = time;
				//time.substring(0,4) +"-"+ time.substring(4, 6) +"-"+time.substring(6,8);;
				
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
				inputdata1.add(hs);
				
				
			}
			catch(Exception e)
			{
				
			}
		}
		return inputdata1;

	}
	
	public static Vector getDataFromYahoo(String symbol)
	{
		try
		{
	//		String data =ConvertDataUtility.getURLDataFromYahoo(symbol);

	//		ArrayList list = ConvertDataUtility.convertYahooDataIntoArray(data);
	//		return list;
		
//			symbol=symbol.trim().toLowerCase();
			// http://ichart.finance.yahoo.com/table.csv?s=IBM&d=3&e=4&f=2009&g=d&a=0&b=2&c=1962&ignore=.csv
					            //http://chartapi.finance.yahoo.com/instrument/1.0/%5Edji/chartdata;type=quote;range=1d/csv/
					
			
			File f = new File(StockConstants.INSTALL_DIR + "/data/" + symbol + ".csv");
			Calendar c = Calendar.getInstance();
			Calendar c1 = Calendar.getInstance();
			c1.setTimeInMillis(f.lastModified());
			c1.add(Calendar.HOUR, 6);
			System.out.println("c1="+c1.before(c));
			System.out.println("exit="+f.exists());
			
			if(f.exists()  && c1.after(c) )
			{
				return loadFile(symbol);
			}
			else
			{
				
					StringBuffer dataBuffer=new StringBuffer();
				
					
					Vector<Object> inputdata1 = new Vector();
						try
						{
							Vector hs1 = GoogleDataDownloader.getFinalRecordFromGoogle(symbol);
								inputdata1.add(hs1);
						//		dataBuffer.append(getStringFromHashMap(hs1)+"\n");
							
						}
						catch(Exception e)
						{
							
						}
						if(dataBuffer.toString().length() > 80)
						{
							Utility.saveContent(StockConstants.INSTALL_DIR+"/data/"+symbol+".csv", dataBuffer.toString());
						}
						
						return inputdata1;
			}	
		
		
		}
		catch(Exception e)
		{
			return new Vector();
		}
	}
	
	public static void main(String[] args)
	{
	System.out.println(getDataFromYahoo("GOOG"));
		getTimeInMillisTodate();
	}
	public static void getTimeInMillisTodate()
	{
		Calendar c = Calendar.getInstance();
		long l = 1442606400*1000;
		//		 1439938327104
//		c.setTimeInMillis(l);
	//	 TimeZone tz = TimeZone.getTimeZone("America/New_York");
	//		c.setTimeZone(tz);	
		
		String time1 = ( c.get(Calendar.MONTH) +1 ) +"/" + c.get(Calendar.DATE) + "/" + c.get(Calendar.YEAR) + "-" +    c.get(Calendar.HOUR_OF_DAY) +":" + c.get(Calendar.MINUTE) +":" + c.get(Calendar.SECOND);
//		System.out.println(c.getTimeInMillis());
//		System.out.println(time1);
		//a1439928000,156.01,156.52,155.25,155.51,2016972
		//a1439892000,2745.95,2749.9,2690,2706.9,958463
		//1439928000   1440014400
//		System.out.println(1439841600  - 1440014400);
		c = Calendar.getInstance();
		c.getTimeInMillis();
		c.setTimeZone(TimeZone.getTimeZone("GMT"));
		c.setTimeInMillis(1442606400l * 1000l -  240*1000*60 );
	//	c.setTimeZone(TimeZone.getTimeZone("PST"));
		String time2 = ( c.get(Calendar.MONTH) +1 ) +"/" + c.get(Calendar.DATE) + "/" + c.get(Calendar.YEAR) + "-" +    c.get(Calendar.HOUR_OF_DAY) +":" + c.get(Calendar.MINUTE) +":" + c.get(Calendar.SECOND);
		System.out.println(time2);
		
		c.setTimeInMillis(1440014400l * 1000l );
		time2 = ( c.get(Calendar.MONTH) +1 ) +"/" + c.get(Calendar.DATE) + "/" + c.get(Calendar.YEAR) + "-" +    c.get(Calendar.HOUR_OF_DAY) +":" + c.get(Calendar.MINUTE) +":" + c.get(Calendar.SECOND);
		System.out.println(time2);
		
		
	}
}

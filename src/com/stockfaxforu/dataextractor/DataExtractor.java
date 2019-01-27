package com.stockfaxforu.dataextractor;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;

import com.stockfaxforu.formulabuilder.MessageDiaglog;
import com.stockfaxforu.util.IndexUtility;
import com.stockfaxforu.util.StockConstants;
import com.stockfaxforu.util.Utility;
public class DataExtractor 
{
	public static String rawData="";
	public static void main(String[] args)
	{
		try 
		{
/*			RandomAccessFile r = new RandomAccessFile("/test.html","r");
			byte[] b = new byte[(int)r.length()];
			r.read(b, 0, b.length);
			rawData = new String(b);
			get1YearEstimateTarget();
*/
//			getStockDetail(IndexUtility.getFilters());
			
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static boolean inside=false;
	public synchronized static void getStockDetail(Vector symbolVector)
	{
		MessageDiaglog msg = new MessageDiaglog("Financial Data Downloading...");

		inside=true;
//		Utility.setAllConstants();
//		String s = "AAPL|AA|AXP|BA|BAC|CAT|CSCO|CVX|DD|DIS|GE|HD|HPQ|IBM|INTC|JNJ|JPM|KFT|KO|MCD|MMM|MRK|MSFT|PFE|PG|T|TRV|UTX|VZ|WMT|XOM|";
//		StringTokenizer st = new StringTokenizer(s,"|");
		symbolVector.add(StockConstants.SELECTED_STOCK);
		for(int i=0;i<symbolVector.size();i++)
		{
			try 
			{	
				String symbols = (String)symbolVector.get(i); 
				// (symbols);
				String url = StockConstants.IGSROOT + "downloadresult.jsp?exchange=nse&symbol="+ URLEncoder.encode(symbols);

				rawData = Utility.getUrlContent(url);
				FileOutputStream f =  new FileOutputStream(StockConstants.INSTALL_DIR+"/result/"+symbols+".txt");	
				f.write(rawData.getBytes());
			} 
			catch (Exception e) 
			{
//				e.printStackTrace();
			}
		}
		MessageDiaglog msg1 = new MessageDiaglog("Financial Data Downloaded");
		inside=false;
	}
	public static String getEstimatePrice()
	{
		int index = rawData.indexOf("1y Target Est:");
		String s = rawData.substring(index);
		index = s.indexOf("</td>");
		s = s.substring(0,index);
		index = s.indexOf("tabledata1");
		s = s.substring(index + "tabledata1> ".length());
	//	// ln(s);
		return s;	
	}
	public static String getMarketCap()
	{
		int index = rawData.indexOf("Market Cap:");
		String s = rawData.substring(index);
		index = s.indexOf("</span>");
		s = s.substring(0,index);
		index = s.indexOf(">");
		s = s.substring(index + "tabledata1> ".length());
		index = s.indexOf(">");
		s = s.substring(index+1);
		index = s.indexOf(">");
		s = s.substring(index+1);
	//	// ln(s);
		return s;	
	}
	public static String getPE()
	{
		int index = rawData.indexOf("width=\"48%\">P/E");
		String s = rawData.substring(index);
		index = s.indexOf("</td></tr><tr>");
		s = s.substring(0,index);
		index = s.indexOf("yfnc_tabledata1");
		s = s.substring(index + "yfnc_tabledata1".length());
		index = s.indexOf(">");
		s = s.substring(index+1);
	//	// ln(s);
		return s;	
	}
	public static String getEPS()
	{
		int index = rawData.indexOf("width=\"48%\">EPS");
		String s = rawData.substring(index);
		index = s.indexOf("</td></tr><tr>");
		s = s.substring(0,index);
		index = s.indexOf("yfnc_tabledata1");
		s = s.substring(index + "yfnc_tabledata1".length());
		index = s.indexOf(">");
		s = s.substring(index+1);
		index = s.indexOf("<");
		s = s.substring(0,index);
		
	//	// ln(s);
		return s;	
	}
	public static String getDivYield()
	{
		int index = rawData.indexOf("width=\"48%\">Div");
		String s = rawData.substring(index);
		index = s.indexOf("</td></tr><tr>");
		s = s.substring(0,index);
		index = s.indexOf("yfnc_tabledata1");
		s = s.substring(index + "yfnc_tabledata1".length());
		index = s.indexOf(">");
		s = s.substring(index+1);
		index = s.indexOf("<");
		s = s.substring(0,index);
		
	//	// ln(s);
		return s;	
	}
	public static String get1YearEstimateTarget()
	{
		int index = rawData.indexOf("width=\"48%\">1y Target Est:");
		String s = rawData.substring(index);
		index = s.indexOf("</td></tr><tr>");
		s = s.substring(0,index);
		index = s.indexOf("yfnc_tabledata1");
		s = s.substring(index + "yfnc_tabledata1".length());
		index = s.indexOf(">");
		s = s.substring(index+1);
		index = s.indexOf("<");
		s = s.substring(0,index);
		
	//	// ln(s);
		return s;	
	}

}
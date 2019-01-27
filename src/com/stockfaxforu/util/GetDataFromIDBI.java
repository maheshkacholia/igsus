package com.stockfaxforu.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;

public class GetDataFromIDBI
{
	int index=0;
	public String urlContent="";
	public HashMap dataHash = new HashMap();
	public static void main(String[] args)
	{
		/*Vector v = IndexUtility.getIndexStockVector("cnx500");
		
		long t1 = System.currentTimeMillis();
		
		for(int i=0;i<v.size();i++)
		{
			System.out.println(currentPrice(v.get(i)+""));
		}
		long t2 = System.currentTimeMillis();
		*/
		
		while(true)
		{
			System.out.println(currentPrice("TCS"));
			

		}
//		System.out.println("totaltime " + (t2-t1)/1000);
		
	}
//http://trade.idbicapital.com/stsb/IDBILite/getQuoteDetail.asp?Exchange=NSE&tickerName=CNX NIFTY~F:1-MON  future sample	
	public static String getRealTimeInString(String symbol)
	{
		String s = "http://trade.idbicapital.com/stsb/IDBILite/getQuoteDetail.asp?Exchange=NSE&tickerName="+symbol+"EQ";
	//	System.out.println(s);
		try
		{
			String ssss = Utility.getUrlContent(s);
			GetDataFromIDBI g = new GetDataFromIDBI();
			g.getDataFromIDBI(ssss);
			HashMap hs = g.getDataHash();
			String date = (String)hs.get("time");
			if(date==null)
			{	
				date="";
			}
			else
			{
				date = date.substring(date.indexOf("-")+1);
			}
			String current = (String)hs.get("ltp");
			if(current==null)
				current="";
			String volume = (String)hs.get("ttq");
			if(volume==null)
				volume="";
			return date+","+current+","+volume;
		}
		catch(Exception e)
		{
			return "";
		}
		
	}
	public static String currentPrice(String symbol)
	{
		String s = "http://trade.idbicapital.com/stsb/IDBILite/getQuoteDetail.asp?Exchange=NSE&tickerName="+symbol+"EQ";
	//	System.out.println(s);
		try
		{
			String ssss = Utility.getUrlContent(s);
			GetDataFromIDBI g = new GetDataFromIDBI();
			g.getDataFromIDBI(ssss);
			HashMap hs = g.getDataHash();
			String date = hs.get("time")+"";
			if(date==null)
				date="";
			String current = hs.get("ltp")+"";
			if(current==null)
				current="";
			String volume = hs.get("ttq")+"";
			if(volume==null)
				volume="";
			return "Time "+date+" LTP "+current+" volume-"+volume;
		}
		catch(Exception e)
		{
			return "";
		}
	}
	public void createNewHash()
	{
		StringBuffer sb = new StringBuffer();
		for(Iterator it  = dataHash.keySet().iterator();it.hasNext();)
		{
			sb.append(dataHash.get(it.next())+"#");
		}
		//System.out.println(sb);
		HashMap hs = new HashMap();
		StringTokenizer st = new StringTokenizer(sb.toString(),"#");
		int i=0;
		while(st.hasMoreTokens())
		{
			try
			{
				String key = st.nextToken();
				String value = st.nextToken();
				hs.put(key, value);
				
			}
			catch(Exception e)
			{
				
			}
			
			
		}
		dataHash = hs;
	}
	public HashMap getDataHash()
	{
		return dataHash;
	}
	public void getDataFromIDBI(String data)
	{
		data = data.toLowerCase();
		urlContent=data;
		String temp = data.substring(data.indexOf("eazyorderform"));
		temp = temp.substring(temp.indexOf("</table>")+"</table>".length());
		temp = temp.substring(0,temp.indexOf("</table>"));
		urlContent = temp;
		temp = getRowdata(urlContent);
		//System.out.println(temp);
		setActualData(temp);
		
		temp = getRowdata(urlContent);
		//System.out.println(temp);
		setActualData(temp);
		
		temp = getRowdata(urlContent);
		//System.out.println(temp);
		setActualData(temp);
		
		temp = getRowdata(urlContent);
		//System.out.println(temp);
		setActualData(temp);

		String s = urlContent.substring(urlContent.indexOf("date"));
		StringTokenizer ss = new StringTokenizer(s,"\n");
		while(ss.hasMoreTokens())
		{
			String mm = ss.nextToken();
			if(mm.indexOf("reportdarkrow") != -1)
			{
				String t = getData(mm,false);
				t = convertDate(t);
				dataHash.put("4", "time#"+t);
				break;
			}
		}
		
		//System.out.println(dataHash);
		createNewHash();
		//System.out.println("newhash="+dataHash);
		
		
		
	}
	public String convertDate(String t)
	{
		String[] s = t.split(" ");
		String[] s2 = s[0].split("/");
		String ssss = s2[1]+"/"+s2[0]+"/"+s2[2]+"-"+s[1];
		return ssss;
	}
	public String getRowdata(String temp)
	{
		String temp1 = temp.substring(temp.indexOf("<tr"));
		urlContent = temp1.substring(temp1.indexOf("</tr>")+"</tr>".length());

		temp1 = temp1.substring(0,temp1.indexOf("</tr>"));
		return temp1;
		
	}
	public void setActualData(String temp)
	{
		temp = Utility.replaceString(temp,"<b>", "");
		temp = Utility.replaceString(temp,"</b>", "");
		
		StringTokenizer st = new StringTokenizer(temp,"\n");
		int i =1;
		while(st.hasMoreTokens())
		{
			String s = st.nextToken();
			if(s.indexOf("filterheader") != -1 || s.indexOf("reportdarkrow") != -1 )
			{
				String t = getData(s,true);
				String tt = (String)dataHash.get(i+"");
				if(tt==null)
					dataHash.put(i+"", t);
				else
					dataHash.put(i+"", dataHash.get(i+"")+"#"+t);
				i++;
			}
		}
		
	}
	public String getData(String line,boolean removespace)
	{
		
		line = line.substring(line.indexOf(">")+1);
		line = line.substring(0,line.indexOf("<"));
		if(removespace)
			line=Utility.replaceString(line," ", "");
		return line;
	}
}

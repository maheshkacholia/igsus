package com.stockfaxforu.util;

import java.util.StringTokenizer;

import com.stockfaxforu.formulabuilder.MessageDiaglog;

public class SplitHandler
{
	public static String url = "";
	public static void main(String[] args)
	{
		try
		{
			handleSplit("CNX Nifty");
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void handleSplit(String symbol) throws Exception
	{
		String data="";
		String data1="";
		
		try
		{
			data = Utility.getUrlContent("http://www.indiabulls.com/securities/research/techanalysis/GetChartApplet_Information.aspx?symbol="+symbol);
		}
		catch (Exception e)
		{
//			MessageDiaglog msg = new MessageDiaglog("Not able to do operation");
			throw new Exception();
		}
		StringTokenizer st = new StringTokenizer(data,"\n");
		StringBuffer sb = new StringBuffer();
		String prevclose="";
		String prevclose1="";
		
		while(st.hasMoreTokens())
		{
			try
			{
				String line = st.nextToken();
				StringTokenizer linest = new StringTokenizer(line,",");
				String typedate = linest.nextToken();
				StringTokenizer tempst = new StringTokenizer(typedate,":");
				tempst.nextToken();
				String date = getDateCorrectFormat(tempst.nextToken().trim());
				String open = linest.nextToken().trim();
				String high = linest.nextToken().trim();
				String low = linest.nextToken().trim();
				String close = linest.nextToken().trim();
				String volume = linest.nextToken().trim();
				if(prevclose.equals(""))
				{
					prevclose=close;
				}
				else
				{
					prevclose=prevclose1;
				}
				long l = Long.parseLong(volume);
				
				String s = symbol + "," + "EQ," + date + "," + prevclose + "," + open + "," + high + "," + low + "," + close + "," + close + "," + volume + "," + volume + "\n" ;
				sb.append(s);
				prevclose1 = prevclose;
			}
			catch(Exception e)
			{
				
			}
			
		}
		if(sb.toString().trim().equalsIgnoreCase(""))
		{
			throw new Exception();
		}
		Utility.saveContent(StockConstants.INSTALL_DIR+"/data/"+symbol+".csv",sb.toString());
	}
	public static String getDateCorrectFormat(String date1)
	{
		StringTokenizer st = new StringTokenizer(date1,"/");
		String mm = st.nextToken();
		String dd = st.nextToken();
		String yy = st.nextToken();
		String mm1 = Utility.getMonth(mm);
		return dd + "-" + mm1 + "-" + yy; 
		
	}
	
}

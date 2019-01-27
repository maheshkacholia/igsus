package com.stockfaxforu.dataextractor;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;
import java.util.StringTokenizer;

import com.stockfaxforu.util.StockConstants;
import com.stockfaxforu.util.Utility;

public class YahooExtration
{
	public static void main(String[] args)
	{
	/*
		try
		{
			
			String s = Utility.getUrlContent("http://finance.yahoo.com/q/ks?s=IBM");
			String retstr = getRequiredString(s, ">Market Cap (intraday)","yfs_j10_ibm\">");
			retstr = convertStringToNumber(retstr);
			//System.out.println(retstr);
			retstr = getRequiredString(s, ">Return on Assets",">");
			retstr = convertStringToNumber(retstr);
			System.out.println("ReturnonAssets" + retstr);

			retstr = getRequiredString(s, ">Beta",">");
			retstr = convertStringToNumber(retstr);
			System.out.println("Beta" + retstr);

			retstr = getRequiredString(s, ">Shares Outstand",">");
			retstr = convertStringToNumber(retstr);
			System.out.println("SharesOutstanding" + retstr);

			retstr = getRequiredString(s, ">Forward Annual Dividend Rate",">");
			retstr = convertStringToNumber(retstr);
			System.out.println("AnnualDividendRate" + retstr);

			retstr = getRequiredString(s, ">Forward Annual Dividend Yield",">");
			retstr = convertStringToNumber(retstr);
			System.out.println("ForwardAnnualDividendYield" + retstr);

			retstr = getRequiredString(s, ">Trailing Annual Dividend Rat",">");
			retstr = convertStringToNumber(retstr);
			System.out.println("TrailingAnnualDividend Rate" + retstr);

			retstr = getRequiredString(s, ">Trailing Annual Dividend Yiel",">");
			retstr = convertStringToNumber(retstr);
			System.out.println("TrailingAnnualDividendYield" + retstr);

		
			retstr = getRequiredString(s, ">Payout Ratio",">");
			retstr = convertStringToNumber(retstr);
			System.out.println("PayoutRatio" + retstr);

			
			
			
			retstr = getRequiredString(s, ">Return on Equity",">");
			retstr = convertStringToNumber(retstr);
			System.out.println("ReturnonEquity" + retstr);

			retstr = getRequiredString(s, ">Revenue",">");
			retstr = convertStringToNumber(retstr);
			System.out.println("Revenue    " + retstr);

			retstr = getRequiredString(s, ">Revenue Per Share",">");
			retstr = convertStringToNumber(retstr);
			System.out.println("RevenuePerShare" + retstr);

			retstr = getRequiredString(s, ">Gross Profit",">");
			retstr = convertStringToNumber(retstr);
			System.out.println("GrossProfit" + retstr);

			retstr = getRequiredString(s, ">EBITDA",">");
			retstr = convertStringToNumber(retstr);
			System.out.println("EBITDA   " + retstr);

			retstr = getRequiredString(s, ">EBITDA",">");
			retstr = convertStringToNumber(retstr);
			System.out.println("EBITDA   " + retstr);

			retstr = getRequiredString(s, "Diluted EPS",">");
			retstr = convertStringToNumber(retstr);
			System.out.println("DilutedEPS    " + retstr);

			retstr = getRequiredString(s, ">Qtrly Earnings Growth ",">");
			retstr = convertStringToNumber(retstr);
			System.out.println("QtrlyEarningsGrowth    " + retstr);

						
			
			
			retstr = getRequiredString(s, ">Total Cash",">");
			retstr = convertStringToNumber(retstr);
			System.out.println("TotalCash    " + retstr);

			
			retstr = getRequiredString(s, ">Total Cash Per Share",">");
			retstr = convertStringToNumber(retstr);
			System.out.println("TotalCashPerShare   " + retstr);

			
			
			retstr = getRequiredString(s, ">Total Debt ",">");
			retstr = convertStringToNumber(retstr);
			System.out.println("TotalDebt  " + retstr);
		
			retstr = getRequiredString(s, ">Current Ratio",">");
			retstr = convertStringToNumber(retstr);
			System.out.println("CurrentRatio"+retstr);

			retstr = getRequiredString(s, ">Book Value Per Share",">");
			retstr = convertStringToNumber(retstr);
			System.out.println("Book Value Per Share"+retstr);
			
			retstr = getRequiredString(s, "PEG Ratio",">");
			retstr = convertStringToNumber(retstr);
			System.out.println("PEGRatioRatio " + retstr);
			
			retstr = getRequiredString(s, "Price/Sales",">");
			retstr = convertStringToNumber(retstr);
			System.out.println("PriceSalesRatio " + retstr);
			
			retstr = getRequiredString(s, "Enterprise Value/Revenue",">");
			retstr = convertStringToNumber(retstr);
			System.out.println("EnterpriseValueRevenueRatio " + retstr);

			retstr = getRequiredString(s, "Enterprise Value/EBITDA",">");
			retstr = convertStringToNumber(retstr);
			System.out.println("EnterpriseValueEBITDARatio " + retstr);

	
		
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		*/
			
	try
	{
		downloadData("^DJI");
		
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	
	
	}
	public static String convertStringToNumber(String str)
	{
		StringBuffer sb = new StringBuffer();
		int i=0;
		int type = 0;
		int BILLION=1;
		int MILLION = 2;
		long mulby=1;
		while(i < str.length())
		{
			char c = str.charAt(i);
			if(c=='.' || c=='-')
			{
				sb.append(c);
				i++;
				continue;
			}
			else if (c=='b')
			{
				type = BILLION;
				mulby = 1000000000;
			}
			else if (c=='m')
			{
				type=MILLION;
				mulby = 1000000;
			}
			String s = new String(c+"");
			try
			{
				int j = Integer.parseInt(s);
				sb.append(c);
			}
			catch(Exception e)
			{
				
			}
			i++;
		}
		if (type==BILLION || type == MILLION)
		{
			float f = Float.parseFloat(sb.toString().trim());
			long l = (long)(f * mulby);
			return l+"";
		}
		else
			return sb.toString();
	}
	public static String getRequiredString(String dataContent,String toFind,String afterthat)
	{
		String s = dataContent.toLowerCase();
		toFind = toFind.toLowerCase();
		
		int i = s.indexOf(toFind);
		
		s = s.substring(i);
		i = s.indexOf("yfnc_tabledata1");
		s =s.substring(i);
		if(afterthat != null)
		{
			i = s.indexOf(afterthat);
			s = s.substring(i + afterthat.length());
		}
		i = s.indexOf("<");
		s = s.substring(0,i);
	//	System.out.println(s);
		return s;
		
	}
	public static void downloadData(String category) throws Exception
	{
		Properties p = new Properties();
		p.load(new FileInputStream(StockConstants.INSTALL_DIR + "/indexsymbol.txt" ));
		String s= p.getProperty(category);
		s = StockConstants.SELECTED_STOCK + "|" + s;  
		StringTokenizer st = new StringTokenizer(s,"|");
		while(st.hasMoreTokens())
		{
			try
			{
				String symbol = st.nextToken();
				String s1 = Utility.getUrlContent("http://finance.yahoo.com/q/ks?s="+symbol);
				Properties pnew = new Properties();
				for(int i=0;i<table.length;i++)
				{
					try
					{
						String retstr = getRequiredString(s1, table[i][0],table[i][2]);
						retstr = convertStringToNumber(retstr);
						pnew.put(table[i][1].toUpperCase(),retstr);
						
					}
					catch(Exception e)
					{
						
					}
					
				}
				
				pnew.save(new FileOutputStream(StockConstants.INSTALL_DIR+"/result/"+symbol+".txt"),"");

			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	public static String[][] table= { {">Return on Assets","ReturnonAsset",">"}, 
						{">Beta","BETA",">"},
						{">Shares Outstand","SharesOutstanding",">"},
						{">Forward Annual Dividend Rat","ForwardAnnualDividendRate",">"},
						{">Forward Annual Dividend Yield","ForwardAnnualDividendYield",">"},
						{">Trailing Annual Dividend Rat","TrailingAnnualDividendRate",">"},
						{">Trailing Annual Dividend Yiel","TrailingAnnualDividendYield",">"},
						{">Payout Ratio","PayoutRatio",">"},
						{">Return on Equity","ReturnonEquity",">"},
						{">Revenue Per Share","RevenuePerShare",">"},
						{">Gross Profit","GrossProfit",">"},
						{">EBITDA","EBITDA",">"},
						{">Qtrly Earnings Growth","QtrlyEarningsGrowth",">"},
						{">Total Cash","TotalCash",">"},
						{">Total Cash Per Share","TotalCashPerShare",">"},
						{">Diluted EPS","DilutedEPS",">"},
						{">Total Debt","TotalDebt",">"},
						{">Current Ratio","CurrentRatio",">"},
						{">Book Value Per Share","BookValuePerShare",">"},
						{">PEG Ratio","PEGRatio",">"},
						{">Price/Sales","PriceSalesRatio",">"},
						{">Enterprise Value/Revenue","MarketCapRevenueRatio",">"},
						{">EnterpriseValue","MarketCap",">"},
						{">Trailing P/E","TrailingPE",">"},
						{">Forward P/E","ForwardPE",">"},

	
						};
	
}

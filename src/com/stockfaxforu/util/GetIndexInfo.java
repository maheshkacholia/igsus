package com.stockfaxforu.util;

import java.util.HashMap;

//import com.livemarket.pojo.SymbolInfo;

public class GetIndexInfo
{
	private static HashMap<String, String> symbolInfoNSE=null;
	private static HashMap<String, String> symbolInfoIDBI=null;
	
	private static void loadIndexSymbol()
	{
		if(symbolInfoNSE==null)
		{
			symbolInfoNSE = new HashMap<String, String>();
			symbolInfoNSE.put("NIFTY", "CNX NIFTY");
			symbolInfoNSE.put("CNXIT", "CNX IT");
			symbolInfoNSE.put("BANKNIFTY", "BANK NIFTY");
			
		}
	}
	private static String getSymbolInfoIDBI()
	{
		return null;
	}
	public static String getSymbolInfoNSE(String symbol)
	{
		symbol = symbol.toUpperCase();
		if(symbolInfoNSE==null)
			loadIndexSymbol();
		String s = symbolInfoNSE.get(symbol);
		return s;
	}
	public static String getSymbolInfoIDBI(String symbol)
	{
		symbol = symbol.toUpperCase();
		if(symbolInfoNSE==null)
			loadIndexSymbol();
		String s = symbolInfoNSE.get(symbol);
		return s;
	}
	
	
}

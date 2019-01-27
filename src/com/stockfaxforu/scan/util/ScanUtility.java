/*
 * Created on Mar 7, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.scan.util;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;

import com.stockfaxforu.currentprice.CurrentPriceNDTV;
import com.stockfaxforu.frontend.ScanEnum;
import com.stockfaxforu.query.DataBaseInMemory;
import com.stockfaxforu.strategy.BuySellStrategy;
import com.stockfaxforu.util.IndexUtility;
import com.stockfaxforu.util.StockConstants;
import com.stockfaxforu.util.Utility;

/**
 * @author sdeopura
 * 
 *         To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ScanUtility
{
	public static String Date = "";
	private static Vector scanlist = null;
	private static Vector dailybhavcopylist = null;
	public static Vector livebhavcopylist = null;

	private static HashMap predefined = null;
	public static HashMap symbolrefrencevector = null;
	public static String symbol = "SYMBOL";
	public static String open = "OPEN";
	public static String high = "HIGH";
	public static String low = "LOW";
	public static String close = "CLOSE";
	public static String prevclose = "PREVCLOSE";
	public static String TOTTRDQTY = "VOLUME";

	private static String last = "LAST";
	// public static String[] column =
	// {"SYMBOL","OPEN","HIGH","LOW","CLOSE","PREVCLOSE","LAST","TOTTRDQTY",
	// "( CLOSE - PREVCLOSE ) * 100 / PREVCLOSE"};
	// public static String[] column =
	// {"SYMBOL","OPEN","HIGH","LOW","CLOSE","PREVCLOSE","LAST","TOTTRDQTY",
	// "SYMBOL"};
	public static String[] column = { "SYMBOL", "OPEN", "HIGH", "LOW", "CLOSE",
			"PREVCLOSE", "PERCHANGE", "VOLUME" };

	public static String[] columnlive = { "SYMBOL", "CURRPRICE", "CHANGE",
			"PERCHANGE", "VOLUME", "MKTCAP", "YEARHIGH", "YEARLOW", "DAYHIGH",
			"DAYLOW" };

	public static String priceup = "Price Up:Price Up(5%):Price Up(10-20%):Price Up(20%)";
	public static String pricedown = "Price Down:Price Down(5%):Price Down(10-20%):Price Down(20%)";

	public static HashMap executeQuery(String query)
	{
		if (dailybhavcopylist == null)
			loadBhavCopy();
		Vector v = null; // DataBaseInMemory.executeQuery(query,
							// dailybhavcopylist);
		String[][] result = new String[v.size()][column.length];
		HashMap hs = null;
		for (int i = 0; i < v.size(); i++)
		{
			hs = (HashMap) v.elementAt(i);
			for (int j = 0; j < column.length; j++)
			{
				result[i][j] = (String) hs.get(column[j]);
			}
		}
		hs = new HashMap();
		hs.put("result", result);
		hs.put("column", column);
		return hs;
	}

	public static Vector output;

	public static Vector getAllStockList()
	{
		if (dailybhavcopylist == null)
			loadBhavCopy();
		Vector output = new Vector();
		for (int i = 0; i < dailybhavcopylist.size(); i++)
		{
			HashMap hs = (HashMap) dailybhavcopylist.elementAt(i);
			String symbolName = (String) hs.get(symbol);
			output.add(symbolName);
		}
		return output;
	}

	public static HashMap executeQueryForBuySellAdvisor(String query,
			Vector indexList) throws Exception
	{
		query = query.toUpperCase();

		output = new Vector();
		for(int i=0;i<indexList.size();i++)
		{	
			Vector v1 = IndexUtility.getIndexStockVectorInHashFormat((String)indexList.get(i));
			output.addAll(v1);
		
		}	
		BuySellStrategy buysell = new BuySellStrategy();
		// ln(query);
		output = buysell.getAllStocksQuery(output, query.toUpperCase());

		DataBaseInMemory database = new DataBaseInMemory();
		query = database.lexical.findConstantStrings(query);

		database.queryParser(query);
		column = new String[database.column.size()];
		for (int i = 0; i < database.column.size(); i++)
		{
			column[i] = (String) database.column.get(i);
		}

		String[][] result = new String[output.size()][column.length];
		HashMap hs = null;
		int j = 0;
		for (int i = 0; i < output.size(); i++)
		{
			hs = (HashMap) output.elementAt(i);
			j = 0;
			for (j = 0; j < column.length; j++)
			{
				result[i][j] = (String) hs.get(column[j]);

			}
		}
		hs = new HashMap();
		hs.put("result", result);
		hs.put("column", column);
		return hs;

	}

	public static HashMap executeQuery(String query, Vector indexList)
	{
		if (dailybhavcopylist == null)
			loadBhavCopy();
		output = new Vector();
		for (int i = 0; i < dailybhavcopylist.size(); i++)
		{
			HashMap hs = (HashMap) dailybhavcopylist.elementAt(i);
			String symbolName = (String) hs.get(symbol);
			boolean b = IndexUtility.isSymbolPartOfIndex(symbolName, indexList);
			if (b == true)
			{
				output.addElement(hs);
			}
		}

		/*
		 * BuySellStrategy buysell = new BuySellStrategy(); // ln(query); output
		 * = buysell.getAllStocksQuery(output, query.toUpperCase());
		 */

		DataBaseInMemory database = new DataBaseInMemory();
		try
		{
			output = database.executeQuery(query.toUpperCase(), output);
		}
		catch (Exception e)
		{
			output = new Vector();
		}
		/*
		 * String[][] result = new String[output.size()][column.length]; HashMap
		 * hs = null; for (int i = 0; i < output.size(); i++) { hs = (HashMap)
		 * output.elementAt(i); for (int j = 0; j < column.length; j++) {
		 * result[i][j] = (String) hs.get(column[j]); } } hs = new HashMap();
		 * hs.put("result", result); hs.put("column", column); return hs;
		 * 
		 * HashMap hs1 = (HashMap)output.firstElement(); String[] column111 =
		 * {"SYMBOL"
		 * ,"CURRPRICE","PERCHANGE","VOLUME","DAYHIGH","DAYLOW","MKTCAP"
		 * ,"YEARLOW","YEARHIGH"}; int j=0;
		 * 
		 * /* for (Iterator it = hs1.keySet().iterator();it.hasNext();) {
		 * column111[j] = (String)it.next(); j++;
		 */
		// String[] column111 =
		// {"SYMBOL","CURRPRICE","PERCHANGE","VOLUME","DAYHIGH","DAYLOW","MKTCAP","YEARLOW","YEARHIGH"};

		String[][] result = new String[output.size()][column.length];
		HashMap hs = null;
		int j = 0;
		for (int i = 0; i < output.size(); i++)
		{
			hs = (HashMap) output.elementAt(i);
			j = 0;
			for (j = 0; j < column.length; j++)
			{
				result[i][j] = (String) hs.get(column[j]);

			}
		}
		hs = new HashMap();
		hs.put("result", result);
		hs.put("column", column);
		return hs;

	}

	public static HashMap executeQueryNDTV(String query, Vector indexList)
	{
		loadBhavCopyNDTV();
		DataBaseInMemory database = new DataBaseInMemory();
		try
		{
			output = database.executeQuery(query.toUpperCase(),
					livebhavcopylist);
		}
		catch (Exception e)
		{
			output = new Vector();
		}
		HashMap hs1 = (HashMap) output.firstElement();
		String[] column111 = { "SYMBOL", "CURRPRICE", "PERCHANGE", "VOLUME",
				"DAYHIGH", "DAYLOW", "MKTCAP", "YEARLOW", "YEARHIGH" };
		int j = 0;

		/*
		 * for (Iterator it = hs1.keySet().iterator();it.hasNext();) {
		 * column111[j] = (String)it.next(); j++;
		 */
		String[][] result = new String[output.size()][column111.length];
		HashMap hs = null;
		for (int i = 0; i < output.size(); i++)
		{
			hs = (HashMap) output.elementAt(i);
			j = 0;
			for (j = 0; j < column111.length; j++)
			{
				result[i][j] = (String) hs.get(column111[j]);

			}
		}
		hs = new HashMap();
		hs.put("result", result);
		hs.put("column", column111);
		return hs;
	}

	public static HashMap perchnageMap = null;
	public static HashMap priceMap = null;

	
	public static void addForPerChange(float perchnage, String symbolname)
	{
		if (perchnage > 20)
		{
			addToUpDownList(ScanEnum.TWENTY_PER_UP.getCode(), symbolname);

		}
		else if (perchnage > 10 && perchnage < 20)
		{
			addToUpDownList(ScanEnum.TEN_TWENTY_PER_UP.getCode(), symbolname);

		}
		else if (perchnage > 5 && perchnage < 10)
		{
			addToUpDownList(ScanEnum.FIVE_TEN_PER_UP.getCode(), symbolname);

		}
		else if (perchnage > 0 && perchnage < 5)
		{
			addToUpDownList(ScanEnum.FIVE_PER_UP.getCode(), symbolname);
		}
		else if (perchnage < 0 && perchnage > -5)
		{
			addToUpDownList(ScanEnum.FIVE_PER_DOWN.getCode(), symbolname);

		}
		else if (perchnage > -5 && perchnage < -10)
		{
			addToUpDownList(ScanEnum.FIVE_TEN_PER_DOWN.getCode(), symbolname);

		}
		else if (perchnage > -10 && perchnage < -20)
		{
			addToUpDownList(ScanEnum.TEN_TWENTY_PER_DOWN.getCode(), symbolname);

		}
		else if (perchnage < -20)
		{
			addToUpDownList(ScanEnum.TWENTY_PER_DOWN.getCode(), symbolname);

		}
	}

	public static void addToUpDownList(String updowntype, String symbolname)
	{
		String list = (String) perchnageMap.get(updowntype);
		if (list == null)
		{
			perchnageMap.put(updowntype, symbolname);
		}
		else
		{

			perchnageMap.put(updowntype, list + "|" + symbolname);

		}
	}

	// System.out.println(perchnageMap);

	public static void loadBhavCopy()
	{
		perchnageMap = new HashMap();
		priceMap = new HashMap();
		RandomAccessFile file = null;
		try
		{
			file = new RandomAccessFile(StockConstants.INSTALL_DIR
					+ "/output.csv", "r");
			String line = "";
			dailybhavcopylist = new Vector();
			symbolrefrencevector = new HashMap();
			int i = 0;
			while ((line = file.readLine()) != null)
			{
				if (i == 0)
				{
					i++;
					continue;
				}
				try
				{
					StringTokenizer st = new StringTokenizer(line, ",");
					String name = st.nextToken();
					st.nextToken();
					String open = st.nextToken();
					String high = st.nextToken();
					String low = st.nextToken();
					String close = st.nextToken();
					String last = st.nextToken();
					String prevclose = st.nextToken();
					String tottrdqty = st.nextToken();
					String perchange = ((Float.parseFloat(close) - Float
							.parseFloat(prevclose)) * 100 / Float
							.parseFloat(prevclose))
							+ "";

//					addForPerChange(Float.parseFloat(perchange), name);
					if (Date.equals(""))
					{
						st.nextToken();
						Date = st.nextToken();

					}
					HashMap hs = new HashMap();
					hs.put(ScanUtility.symbol, name);
					hs.put(ScanUtility.open, open);
					hs.put(ScanUtility.high, high);
					hs.put(ScanUtility.low, low);
					hs.put(ScanUtility.close, close);
					hs.put(ScanUtility.prevclose, prevclose);
					hs.put(ScanUtility.last, last);
					hs.put(ScanUtility.TOTTRDQTY, tottrdqty);
					hs.put(ScanUtility.PERCHANGE, perchange);

					dailybhavcopylist.addElement(hs);
					symbolrefrencevector.put(name, (i - 1) + "");
					i++;
				}
				catch (Exception e)
				{
				}
			}
		}
		catch (Exception e1)
		{
		}
		finally
		{
			try
			{
				file.close();
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
			}
		}
	}

	public static String CURRPrice = "CURRPRICE";
	public static String CHANGE = "CHANGE";
	public static String PERCHANGE = "PERCHANGE";
	public static String VOLUME = "VOLUME";
	public static String MKTCAP = "MKTCAP";
	public static String YEARHIGH = "YEARHIGH";
	public static String YEARLOW = "YEARLOW";
	public static String DAYHIGH = "DAYHIGH";
	public static String DAYLOW = "DAYLOW";

	public static void loadBhavCopyNDTV()
	{
		RandomAccessFile file = null;
		try
		{
			CurrentPriceNDTV current = new CurrentPriceNDTV();
			// current.getCurrentPrice();
			current.saveLiveContent();

			file = new RandomAccessFile(StockConstants.INSTALL_DIR
					+ "/temp/templive.csv", "r");
			String line = "";
			livebhavcopylist = new Vector();
			// symbolrefrencevector = new HashMap();
			int i = 0;
			while ((line = file.readLine()) != null)
			{
				if (i == 0)
				{
					i++;
					continue;
				}
				try
				{
					StringTokenizer st = new StringTokenizer(line, ",");
					String name = st.nextToken();
					st.nextToken();
					String currprice = Float.parseFloat(st.nextToken()) + "";
					String change = Float.parseFloat(st.nextToken()) + "";
					String changeper = st.nextToken();
					String volume = Float.parseFloat(st.nextToken()) + "";
					String mktcap = st.nextToken();
					String yearhigh = st.nextToken();
					String yeralow = st.nextToken();
					String dayhigh = st.nextToken();
					String daylow = st.nextToken();

					HashMap hs = new HashMap();
					hs.put(ScanUtility.symbol, name);
					hs.put(ScanUtility.CURRPrice, currprice);
					hs.put(ScanUtility.CHANGE, change);
					hs.put(ScanUtility.PERCHANGE, changeper);
					hs.put(ScanUtility.VOLUME, volume);
					hs.put(ScanUtility.MKTCAP, mktcap);
					hs.put(ScanUtility.YEARHIGH, yearhigh);
					hs.put(ScanUtility.YEARLOW, yeralow);
					hs.put(ScanUtility.DAYHIGH, dayhigh);
					hs.put(ScanUtility.DAYLOW, daylow);

					livebhavcopylist.addElement(hs);
					// symbolrefrencevector.put(name, (i - 1) + "");
					i++;
				}
				catch (Exception e)
				{
				}
			}
		}
		catch (Exception e1)
		{
		}
		finally
		{
			try
			{
				file.close();
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
			}
		}
	}

	public static HashMap getLiveRateForPortfolio(String symbol)
	{
		try
		{
			String s = Utility
					.getUrlContent("http://in.finance.yahoo.com/d/quotes.csv?s="
							+ symbol + ".NS&f=sl1d1t1c1ohgv&e=.csv");
			StringTokenizer st = new StringTokenizer(s, ",");
			st.nextToken();
			String prc = st.nextToken();
			float f = Float.parseFloat(prc);
			if (f <= 0)
			{
				throw new Exception();
			}
			HashMap hs = new HashMap();
			hs.put(ScanUtility.symbol, symbol);
			hs.put(ScanUtility.open, prc);
			hs.put(ScanUtility.high, prc);
			hs.put(ScanUtility.low, prc);
			hs.put(ScanUtility.close, prc);
			hs.put(ScanUtility.prevclose, prc);
			hs.put(ScanUtility.last, prc);
			hs.put(ScanUtility.TOTTRDQTY, "0");
			return hs;
		}
		catch (Exception e)
		{
			return getSymbolDetail(symbol);
		}
	}

	public static HashMap getSymbolDetail(String symbol)
	{
		try
		{
			if (symbolrefrencevector == null)
				loadBhavCopy();
			String i = (String) symbolrefrencevector.get(symbol);
			if (i != null)
			{
				int i1 = Integer.parseInt(i);
				return (HashMap) dailybhavcopylist.elementAt(i1);
			}
		}
		catch (Exception e)
		{
		}
		return null;
	}

	public static String[] getSymbolDetail(String symbol1, Vector fields)
	{
		String[] retStr = new String[fields.size()];
		for (int i = 0; i < fields.size(); i++)
		{
			retStr[i] = "";
			if (fields.elementAt(i).toString().equals(symbol))
				retStr[i] = symbol1;
		}
		if (symbolrefrencevector == null)
			loadBhavCopy();
		if (symbolrefrencevector == null)
			return retStr;

		String i = (String) symbolrefrencevector.get(symbol1);
		if (i != null)
		{
			int i1 = Integer.parseInt(i);
			HashMap hs = (HashMap) dailybhavcopylist.elementAt(i1);
			for (int j = 0; j < fields.size(); j++)
			{
				String s = (String) hs.get(fields.elementAt(j));
				if (s == null)
					s = "";
				retStr[j] = s;
			}
			return retStr;
		}
		return retStr;
	}

	public static Vector getPreDefinedScan(String direction)
	{
		RandomAccessFile file = null;
		Vector retVector = new Vector();
		try
		{
			if (predefined == null)
			{
				file = new RandomAccessFile(StockConstants.INSTALL_DIR
						+ "/other/" + "predefinedscan.txt", "r");
				String line = "";
				predefined = new HashMap();
				scanlist = new Vector();
				while ((line = file.readLine()) != null)
				{
					try
					{
						StringTokenizer st = new StringTokenizer(line, ":");
						String name = st.nextToken();
						String query = st.nextToken();
						predefined.put(name, query);
					}
					catch (Exception e)
					{
					}
				}
			}
			Iterator i = predefined.keySet().iterator();
			while (i.hasNext())
			{
				String name = (String) i.next();
				if (direction.equals("priceup"))
				{
					if (priceup.indexOf(name) != -1)
						retVector.addElement(name);
				}
				else if (direction.equals("pricedown"))
				{
					if (pricedown.indexOf(name) != -1)
						retVector.addElement(name);
				}
				else
				{
					retVector.addElement(name);
				}
			}
			return retVector;
		}
		catch (Exception e)
		{
			predefined = null;
			scanlist = null;
			return null;
		}
	}

	public static String getQuery(String name)
	{
		return (String) predefined.get(name);
	}

	/**
	 * @param string
	 * @return
	 */
	public static Vector filterVector = null;
	public static Vector detailVector = null;

	public static Vector getFilters()
	{
		if (filterVector == null)
		{
			filterVector = new Vector();
			filterVector.addElement("Nifty");
			filterVector.addElement("CNX 100");
			filterVector.addElement("CNX 500");
			filterVector.addElement("Jr Nifty");
			filterVector.addElement("All");
			filterVector.addElement("CNX Bank");
		}
		// TODO Auto-generated method stub
		return filterVector;
	}

	public static Vector getDetail()
	{
		if (detailVector == null)
		{
			detailVector = new Vector();
			detailVector.addElement("Graph");
			detailVector.addElement("Live Quote");
			detailVector.addElement("Tech Ind");
			detailVector.addElement("Detail");
		}
		// TODO Auto-generated method stub
		return detailVector;
	}

	/**
	 * 
	 */
	public static Vector getOutPutField()
	{
		Vector v = new Vector();
		v.addElement("symbol");
		// for (int i = 0; i < column.length; i++)
		// v.addElement(column[i]);
		return v;
		// TODO Auto-generated method stub
	}
}

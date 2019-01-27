/*
 * Created on Mar 9, 2007
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.util;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;

import com.stockfaxforu.scan.util.ScanUtility;
/**
 * @author sdeopura
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class IndexUtility
{
	public static Properties indexHashMap = null;
	public static boolean isSymbolPartOfIndex(String symbol, String index)
	{
		if (indexHashMap == null)
			loadIndexes();
		String s = (String) indexHashMap.get(index);
		if (s == null)
			return false;
		symbol = "|" + symbol + "|".toUpperCase();
		if (s.indexOf(symbol) == -1)
			return false;
		else
			return true;
	}
	public static boolean isSymbolPartOfIndex(String symbol,Vector indexVector)
   {
		if(indexVector==null || indexVector.size()==0)
		{
			return true;
		}
	   for(int i=0;i<indexVector.size();i++)	
	   {   
			String indexname = (String)indexVector.elementAt(i);
			if(isSymbolPartOfIndex(symbol, indexname))
				return true;			
	   }
	   return false;
   }
	public static boolean isExists(String category)
	{
		if(indexHashMap.get(category) != null)
			return true;
		else
			return false;	
	}
	public static void deleteCategory(String category)
	{
	try
	{
		Properties property = new Properties();
		property.load(new FileInputStream(StockConstants.INSTALL_DIR + "/indexsymbol.txt"));
		property.remove(category);
		property.store(new FileOutputStream(StockConstants.INSTALL_DIR + "/indexsymbol.txt"), null);
		
	}
	catch(Exception e)
	{
		
	}
		
		
/*		RandomAccessFile r = null;
		indexHashMap = new HashMap();
		StringBuffer sb = new StringBuffer();
		try
		{
			r = new RandomAccessFile(StockConstants.INSTALL_DIR + "/indexsymbol.txt", "r");
			String line = "";
			while ((line = r.readLine()) != null)
			{
				StringTokenizer st = new StringTokenizer(line, "=");
				String indexname = st.nextToken();
				if(indexname.equalsIgnoreCase(category))
					continue;
				sb.append(line+"\n");
			}
			String s = sb.toString();
			s = s.substring(0,s.length()-1);
			r.close();
			File f = new File(StockConstants.INSTALL_DIR + "/indexsymbol.txt");
			f.delete();
			r = new RandomAccessFile(StockConstants.INSTALL_DIR + "/indexsymbol.txt", "rw");
			r.writeBytes(s);
//			r.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
	
		}
		finally
		{
			try
			{
				r.close();
			}
			catch (Exception e1)
			{}
		}
*/
		}
	
	public static void addCategory(String category)
	{
		try
		{
			Properties property = new Properties();
			property.load(new FileInputStream(StockConstants.INSTALL_DIR + "/indexsymbol.txt"));
			property.put(category, "");
			property.store(new FileOutputStream(StockConstants.INSTALL_DIR + "/indexsymbol.txt"), null);
		
		}
		catch(Exception e)
		{
		
		}
	}


	public static void updateStocksCategory(Vector stocks,String category)
	{
		StringBuffer stocksStr=new StringBuffer();
		for(int i=0;i<stocks.size();i++)
		{
			String s = (String)stocks.elementAt(i);
				stocksStr.append(stocks.elementAt(i)+"|");
		}
		try
		{
			Properties property = new Properties();
			property.load(new FileInputStream(StockConstants.INSTALL_DIR + "/indexsymbol.txt"));
			property.put(category,stocksStr.toString());
			property.store(new FileOutputStream(StockConstants.INSTALL_DIR + "/indexsymbol.txt"), null);
		
		}
		catch(Exception e)
		{
		
		}	
	
	}

	public static void apendNewStocksToCategory(Vector stocks,String category)
	{
		Vector v = IndexUtility.getIndexStockVector(category);
		StringBuffer stocksStr = new StringBuffer();
		for(int i=0;i<v.size();i++)
		{
			String s = (String)v.elementAt(i);
			stocksStr.append(s+"|");
		}
		for(int i=0;i<stocks.size();i++)
		{
			String s = (String)stocks.elementAt(i);
			if(!v.contains(s))
				stocksStr.append(s+"|");
		}
		try
		{
			Properties property = new Properties();
			property.load(new FileInputStream(StockConstants.INSTALL_DIR + "/indexsymbol.txt"));
			property.put(category,stocksStr.toString());
			property.store(new FileOutputStream(StockConstants.INSTALL_DIR + "/indexsymbol.txt"), null);
		
		}
		catch(Exception e)
		{
		
		}		
			
	}
	public static void addStocksToCategory(Vector stocks,String category)
	{
		StringBuffer stocksStr=new StringBuffer();
		for(int i=0;i<stocks.size();i++)
		{
			String s = (String)stocks.elementAt(i);
			stocksStr.append(stocks.elementAt(i)+"|");
		}
		try
		{
			Properties property = new Properties();
			property.load(new FileInputStream(StockConstants.INSTALL_DIR + "/indexsymbol.txt"));
			property.put(category,stocksStr.toString());
			property.store(new FileOutputStream(StockConstants.INSTALL_DIR + "/indexsymbol.txt"), null);
		
		}
		catch(Exception e)
		{
		
		}		
	}
	public static void deleteStocksToCategory(Vector stocks,String category)
	{
		Properties property = new Properties();
		try
		{
			property.load(new FileInputStream(StockConstants.INSTALL_DIR + "/indexsymbol.txt"));
			String stocksname = property.getProperty(category);
			StringTokenizer sttemp =  new StringTokenizer(stocksname,"|");
			StringBuffer tempbuffer = new StringBuffer();
			while(sttemp.hasMoreTokens())
			{
				String temps = sttemp.nextToken();
				if(stocks.contains(temps))
				{
					;
				}
				else
				{
					tempbuffer.append(temps+"|");
				}
			}
			String s = tempbuffer.toString();
			property.put(category, s);
			property.store(new FileOutputStream(StockConstants.INSTALL_DIR + "/indexsymbol.txt"), null);
	
		}
		catch(Exception e)
		{

		}	
	}
	
	public static Vector categoryVector= null;
	public static Vector getCategories()
	{
		if(categoryVector==null)
			loadIndexes();
		return categoryVector;	
	}
	public static Vector getFilters()
	{
		Vector v = new Vector();
		if (indexHashMap == null)
			loadIndexes();
		Iterator it = indexHashMap.keySet().iterator();
		while(it.hasNext())
		{
			v.addElement(it.next());		
		}
		return v;
	}
	public static Vector getIndexStockVector(String name)
	{
		Vector stockVector = new Vector();
		if(indexHashMap==null)
			loadIndexes();
		String indexsymbols = (String) indexHashMap.get(name);
		if (indexsymbols == null)
			return null;
		StringTokenizer st = new StringTokenizer(indexsymbols, "|");
		while (st.hasMoreTokens())
		{
			String symbol = st.nextToken();
			if (symbol.trim().equals(""))
				continue;
			stockVector.addElement(symbol);
		}
		return stockVector;
	}
	public static Vector getIndexStockVectorInHashFormat(String name)
	{
		Vector stockVector = new Vector();
		if(indexHashMap==null)
			loadIndexes();
		String indexsymbols = (String) indexHashMap.get(name);
		if (indexsymbols == null)
			return null;
		StringTokenizer st = new StringTokenizer(indexsymbols, "|");
		while (st.hasMoreTokens())
		{
			String symbol = st.nextToken();
			if (symbol.trim().equals(""))
				continue;
			HashMap hs = new HashMap();
			hs.put(ScanUtility.symbol, symbol);
			stockVector.addElement(hs);
		}
		return stockVector;
	}
	
	public static void loadIndexes()
	{
		try
		{
				indexHashMap = new Properties();
			indexHashMap.load(new FileInputStream(StockConstants.INSTALL_DIR + "/indexsymbol.txt"));
			categoryVector = new Vector(); 

			for (Enumeration e = indexHashMap.keys(); e.hasMoreElements();)
			{
				categoryVector.add(e.nextElement());
			}
		}
		catch(Exception e)
		{
			
//			e.printStackTrace();
		}
	}
}

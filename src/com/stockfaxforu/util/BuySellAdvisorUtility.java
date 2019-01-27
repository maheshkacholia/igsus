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
public class BuySellAdvisorUtility
{
	public static Properties indexHashMap = null;

	public static Vector categoryVector= null;
	
	public static void makeAllReloadablr()
	{
		indexHashMap=null;
		categoryVector=null;
	}
	public static Vector getCategories()
	{
		if(categoryVector==null)
			loadIndexes();
		return categoryVector;	
	}
	
	public static Vector getCategoriesUnique()
	{
		if(categoryVector==null)
			loadIndexes();
		Vector categoryuniqvec = new Vector();
		for(int i=0;i<categoryVector.size();i++)
		{
			Object o = categoryVector.get(i);
			if(!categoryuniqvec.contains(o))
			{
				categoryuniqvec.add(o);
			}
		}
		return categoryuniqvec;	
		
	}
	public static boolean isCategoryExists(String category)
	{
		if(categoryVector.contains(category))
			return true;
		else
			return false;
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
	public static HashMap infohash = new HashMap();
	public static String getInfo(String name )
	{
		String s = (String)infohash.get(name);
		if(s==null)
		{
			return "";
		}
		else
		{
			return s;
		}
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
	public static String findComments(String stemp)
	{
		StringTokenizer st = new StringTokenizer(stemp,"\n");
		StringBuffer sb = new StringBuffer();

		while(true)
		{
			String s = st.nextToken();
			if(s.startsWith("@COMMENT"))
			{
				sb.append(Utility.replaceString(s, "@COMMENT", "")+"\n");
			}
			else
			{
				break;
			}
		}
		return sb.toString();
		
	}
	public static void loadIndexes()
	{
		try
		{
			indexHashMap = new Properties();
			//indexHashMap.load(new FileInputStream(StockConstants.INSTALL_DIR + "/indexsymbol.txt"));
			categoryVector = new Vector(); 
			File f = new File(StockConstants.INSTALL_DIR+"/buysell");
			String[] s = f.list();
			for (int i=0;i<s.length;i++)
			{
				try
				{
					
					StringTokenizer st = new StringTokenizer(s[i],"-");
					String str = st.nextToken();
					String str1 = st.nextToken();
					/*
					if(s[i].indexOf("dummy") != -1)
					{
						if(!categoryVector.contains(str))
							categoryVector.add(str);
						continue;
					}*/
						
					String stemp = Utility.getFileContent(StockConstants.INSTALL_DIR+"/buysell/"+s[i]);
					stemp = findComments(stemp);
					infohash.put(str1, stemp);
					
					categoryVector.add(str);
					if(indexHashMap.get(str)== null)
					{
						indexHashMap.put(str, str1);
						
					}
					else
					{
						String ss = (String)indexHashMap.get(str);
						indexHashMap.put(str, ss+"|"+str1);
						
					}
					
				}
				catch(Exception e)
				{
					
				}
				
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public static boolean isFormulaExists(String categoryName, String forName) 
	{
		File f = new File(StockConstants.INSTALL_DIR +"/buysell/"+categoryName+"-"+forName);
		if(f.exists())
			return true;
		return false;
	
	}
}

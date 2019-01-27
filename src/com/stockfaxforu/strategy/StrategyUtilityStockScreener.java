/*
 * Created on Mar 23, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.strategy;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.naming.Name;
import com.stockfaxforu.util.StockConstants;
/**
 * @author sdeopura
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class StrategyUtilityStockScreener
{
	public static String Name = "Name";
	public static String Type = "Type";
	public static String Formula = "Formula";
	public static String Comment = "Comment";
	public static String strategyFile = StockConstants.INSTALL_DIR + "/strategystockscanner.txt";
	/**
	 * @return
	 */
	public static HashMap strategyMap = null;
	public static Vector getAllStrategy()
	{
		if (strategyMap == null)
		{
			loadStrategy();
		}
		Iterator it = strategyMap.keySet().iterator();
		Vector v = new Vector();
		while (it.hasNext())
		{
			v.addElement(it.next());
		}
		return v;
	}
	public static String getFormulaAndComment(String strategyName)
	{
		HashMap hs = (HashMap) strategyMap.get(strategyName);
		if (hs == null)
			return "";
		String formula = (String) hs.get(Formula);
		String comment = (String) hs.get(Comment);
		String type = (String) hs.get(Type);
		StringBuffer sb = new StringBuffer();
		sb.append("Strategy Type:" + type + "\n");
		sb.append("Formula:\n" + formula + "\n");
		sb.append("Comment:\n" + comment);
		return sb.toString();
	}
	public static HashMap getStrategyMap(String strategyName)
	{
		if (strategyMap == null)
		{
			loadStrategy();
		}
		Object hs = strategyMap.get(strategyName);
		if (hs == null)
			return null;
		return (HashMap) hs;
	}
	public static String getFormula(String strategyName)
	{
		if (strategyMap == null)
		{
			loadStrategy();
		}
		HashMap hs = (HashMap)strategyMap.get(strategyName);
		if (hs == null)
			return null;
		return (String)hs.get(Formula);
	}

	public static void loadStrategy()
	{
		RandomAccessFile file = null;
		try
		{
			file = new RandomAccessFile(strategyFile, "r");
			String line = "";
			strategyMap = new HashMap();
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
					StringTokenizer st = new StringTokenizer(line, ":");
					String strType = st.nextToken();
					String strName = st.nextToken();
					String strFormula = st.nextToken();
					String strComment = "";
					if (st.hasMoreElements())
						strComment = st.nextToken();
					HashMap hs = new HashMap();
					hs.put(Type, strType);
					hs.put(Name, strName);
					hs.put(Formula, strFormula);
					hs.put(Comment, strComment);
					strategyMap.put(strName, hs);
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
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
			}
		}
	}
	public static void saveFormula(String formulaType, String formulaName, String formula, String comment)
	{
		StringBuffer sb = new StringBuffer();
		RandomAccessFile file = null;
		boolean notfound = true;
		if (comment == null)
			comment = "";
		notfound=true;
		try
		{
			file = new RandomAccessFile(strategyFile, "r");
			String line = "";
			while ((line = file.readLine()) != null)
			{
				StringTokenizer st = new StringTokenizer(line, ":");
				String ltype = st.nextToken();
				String lname = st.nextToken();
				String lformula = st.nextToken();
				String lcomment = "";
				if (st.hasMoreTokens())
					lcomment = st.nextToken();
				if (comment == null || comment.equals(""))
					lcomment = comment;
				if (lname.trim().equalsIgnoreCase(formulaName))
				{
					sb.append(formulaType + ":" + formulaName + ":" + formula + ":" + comment + "\n");
					notfound = false;
				}
				else
				{
					sb.append(line+"\n");
					
				}
			}
			if (notfound)
				sb.append(formulaType + ":" + formulaName + ":" + formula + ":" + comment);
			file.close();
			
			String stemp = sb.toString();
			if(stemp.charAt(stemp.length()-1) == '\n')
			{
				stemp = stemp.substring(0, stemp.length()-2);
			}


			
			File f = new File(strategyFile);
			f.delete();
			file = new RandomAccessFile(strategyFile, "rw");
			file.writeBytes(stemp);
			file.close();
			strategyMap = null;
			getAllStrategy();

		}
		catch (Exception e1)
		{
		}
		finally
		{
			try
			{
				file.close();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
//				e.printStackTrace();
			}
		}

	}

	public static void deleteFormula(String formulaName)
	{
		StringBuffer sb = new StringBuffer();
		RandomAccessFile file = null;
		boolean notfound = true;
			try
		{
			file = new RandomAccessFile(strategyFile, "r");
			String line = "";
			while ((line = file.readLine()) != null)
			{
				StringTokenizer st = new StringTokenizer(line, ":");
				String ltype = st.nextToken();
				String lname = st.nextToken();
	
				if (lname.trim().equalsIgnoreCase(formulaName))
				{
					continue;
				}
				else
				{
					sb.append(line+"\n");					
				}
			}
			String stemp = sb.toString();
			if(stemp.charAt(stemp.length()-1) == '\n')
			{
				stemp = stemp.substring(0, stemp.length()-2);
			}
			file.close();
			File f = new File(strategyFile);
			f.delete();
			file = new RandomAccessFile(strategyFile, "rw");
			file.writeBytes(stemp);
			file.close();
			strategyMap = null;
			getAllStrategy();
		}
		catch (Exception e1)
		{
		}
		finally
		{
			try
			{
				file.close();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
			}
		}

	}




}

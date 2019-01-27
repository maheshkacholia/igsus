/*
 * Created on Mar 23, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.strategy;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
public class StrategyUtility
{
	public static final String FormulaName = "FormulaName";
	public static String Expand="Expand";
	public static String Name = "Name";
	public static String Type = "Type";
	public static String Formula = "Formula";
	public static String Comment = "Comment";
	public static String Category = "Category";
	public static String FormulaType = "FormulaType";
	public static String strategyFile = StockConstants.INSTALL_DIR + "/strategy.txt";
	/**
	 * @return
	 */
	public static HashMap strategyMap = null;
	public static String del = "$";
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
			v.add(it.next());
		}
		return v;
	}



	public static String getFormula(String strategyName)
	{

		strategyName = strategyName.toUpperCase();
	
		if (strategyMap == null)
		{
			loadStrategy();
		}

		HashMap hs = (HashMap) strategyMap.get(strategyName);
		if (hs == null)
			return null;
		String formula = (String) hs.get(Formula);
		return formula;
	}

	public static void loadStrategies(String path)
	{
		File f = new File(path);
		String[] listFile = f.list();
		
		for(int i=0;i<listFile.length;i++)
		{
			try
			{
				String strName = listFile[i].toUpperCase();
				strName = strName.substring(0,strName.indexOf(".FL"));
				File f1 = new File(path+"/"+ listFile[i]);
				FileInputStream input = new FileInputStream(f1);
				byte[] b = new byte[(int)f1.length()];
				input.read(b,0,b.length);
				HashMap hs = new HashMap();
				hs.put(Name, strName);
				hs.put(Formula, new String(b));
				if(strategyMap==null)
					strategyMap = new HashMap();
				strategyMap.put(strName, hs);
				
			}
			catch(Exception e)
			{
		//		e.printStackTrace();
			}
		}
		
	}
	public static void loadStrategy()
	{
		String[] s = {"library"};
	
		
		for(int i=0;i<s.length;i++)
		{
			try
			{
				loadStrategies(StockConstants.INSTALL_DIR+"/"+s[i]);
			}
			catch(Exception e)
			{
		//		e.printStackTrace();
			}
		}
	}
	public static void main(String[] args)
	{
		loadStrategy();
	}
	public static void saveFormula(String formulaName,String formula)
	{
		if(strategyMap==null)
			loadStrategy();
		formulaName = formulaName.toUpperCase();	
		String strName = formulaName.substring(0,formulaName.indexOf(".FL"));
		
		HashMap hs = new HashMap();
		hs.put(Name, strName);
		hs.put(Formula, formula);

		strategyMap.put(strName, hs);

	}

	public static void deleteFormula(String formulaName)
	{
		if(strategyMap==null)
			loadStrategy();
		formulaName = formulaName.toUpperCase();	
		String strName = formulaName.substring(0,formulaName.indexOf(".FL"));
		strategyMap.remove(strName);

	}



	/**
	 * @param string
	 * @return
	 */
	public static HashMap getStrategyMap(String strategyName)
	{

		strategyName = strategyName.toUpperCase();
	
		if (strategyMap == null)
		{
			loadStrategy();
		}

		HashMap hs = (HashMap) strategyMap.get(strategyName);
		if (hs == null)
			return null;
		return hs;
	}



	/**
	 * @param s
	 * @return
	 */
	public static String getFormulaAndComment(String s)
	{
		// TODO Auto-generated method stub
		return null;
	}

}

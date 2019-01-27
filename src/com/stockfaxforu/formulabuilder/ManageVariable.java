/*
 * Created on Feb 20, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.formulabuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.Vector;

import com.stockfaxforu.component.Indicator;
import com.stockfaxforu.strategy.TechIndicatorLibrary;
import com.stockfaxforu.util.StockConstants;

/**
 * @author sdeopura
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ManageVariable
{

	/**
	 * @return
	 */
	public static Vector indicatorVector = null;
	public static Vector getIndicatorListHash()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return
	 */
	public static String variables =              ":SMA:MMA:EMA:WMA:RSI:WILPER:HIGH:LOW:CLOSE:OPEN:VOLUME:MACDSIGNAL:MACDLINE:UBBAND:LBBAND:HIGHINDAYS:LOWINDAYS:CCI:CMF:AROOPUP:AROOPDOWN:AROOPOSI:AVGTRUERANGE:WILLAMACCDIS:ACCDIS:";
	public static String stockscreenervariables = ":SMA:MMA:EMA:WMA:RSI:WILPER:HIGH:LOW:CLOSE:OPEN:VOLUME:MACDSIGNAL:MACDLINE:UBBAND:LBBAND:HIGHINDAYS:LOWINDAYS:CCI:CMF:AROOPUP:AROOPDOWN:AROOPOSI:AVGTRUERANGE:WILLAMACCDIS:ACCDIS:TOTALINCOME:INTEREST:NETPROFIT:BASICEPS:NETSALES:";

	public static void saveIndicatorList(Vector v)
	{
		try
		{
			FileOutputStream fos = new FileOutputStream(StockConstants.INSTALL_DIR + "/selectedindicator");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(v);
			oos.close();
		}
		catch (Exception e)
		{
		}
		
	}
	public static Vector getSelectedIndicatorForDisplay()
	{
		try
		{
			FileInputStream fos = new FileInputStream(StockConstants.INSTALL_DIR + "/selectedindicator");
			ObjectInputStream oos = new ObjectInputStream(fos);
			Vector v = (Vector)oos.readObject();
			oos.close();
			return v;
		}
		catch (Exception e)
		{
			return null;
		}
	}
	public static Vector getIndicatorName()
	{
		Vector v = new Vector();
		StringTokenizer st = new StringTokenizer(variables,":");
		while(st.hasMoreTokens())
		{
			v.addElement(st.nextToken());
		}
		return v;
	}
	public static Vector getIndicatorNameForStockScreener()
	{
		Vector v = new Vector();
		StringTokenizer st = new StringTokenizer(stockscreenervariables,":");
		while(st.hasMoreTokens())
		{
			v.addElement(st.nextToken());
		}
		return v;
	}
	
	public static int getIndiPosition(String indicatorName)
	{
		return indicatorVector.indexOf(indicatorName);
	}
	
	public static String[] indidescr = new String[50];



	public static Object getValues(String indname,int returnType)
	{
		String[] paraname= {"Period 1","Period 2","Period 3","Period 4","Period 5",};

		int[] retval= new int[5];
		int paras=0;		
		
		if(TechIndicatorLibrary.onepara.indexOf(":"+indname+":") != -1)
		{
			retval[0] = 14;
			paras=1;
			paraname[0] = "No of Days";

		}
		else if(TechIndicatorLibrary.threepara.indexOf(":"+indname+":") != -1)
		{
			retval[0] = 12;
			retval[1] = 26;
			retval[2] = 9;
			
			paras=3;

			paraname[0] = "Low of Days";
			paraname[1] = "High of Days";;
			paraname[2] = "Signal(days)";

		}
		else if(TechIndicatorLibrary.twopara.indexOf(":"+indname+":") != -1)
		{
			retval[0] = 20;
			retval[1] = 2;
		
			paras=2;


			paraname[0] = "No of days";
			paraname[1] = "% difference";

		}
		if(returnType==DEFAULTVALUE)
			return (Object)retval;
		else if(returnType==PARAS)	
			return (Object)(paras+"");
		else if(returnType==PERIODNAME)
			return (Object)paraname;	
		return null;
	}
	public static int DEFAULTVALUE=1;
	public static int PARAS=2;
	public static int PERIODNAME=3;
	
	

	public static String[] getPeriodName(String indname)
	{
		String[] retval= {"Period 1","Period 2","Period 3","Period 4","Period 5",};
		
		if(TechIndicatorLibrary.onepara.indexOf(":"+indname+":") == -1)
		{
		}
		else if(TechIndicatorLibrary.threepara.indexOf(indname) == -1)
		{
			retval[0] = "Low of Days";
			retval[1] = "High of Days";;
			retval[2] = "Signal(days)";
		}
		else if(TechIndicatorLibrary.twopara.indexOf(indname) == -1)
		{
			retval[0] = "No of days";
			retval[1] = "% difference";
		}
		return retval;
	}
	public static String[] operator = {"  +  ","  -  ","  and  ","  or  ","  <  ","  >  ","  =  ","  *  ","  /  ","  <>  ","  <=  ","  >=  "," ( ", " ) "};
	public static Vector operatorVector = null;

	public static Vector getOperators()
	{
		if(operatorVector==null)
		{
			operatorVector = new Vector();
			for(int i=0;i<operator.length;i++)
			{
				operatorVector.addElement(operator[i].trim());
			}

		}
		return operatorVector;
	}
	/**
	 * @param tokenvalue
	 * @return
	 */
	public static boolean isOperator(String tokenvalue)
	{
		if(operatorVector.contains(tokenvalue.trim()))
			return true;
		else
			return false;	
	}
	/**
	 * @return
	 */
	

}

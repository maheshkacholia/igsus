package com.intraday;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.HashMap;
import java.util.Properties;
import java.util.Vector;
/*
 * Created on Jan 28, 2007
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
/**
 * @author sdeopu1
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class GetTicByTicData
{
	public static Vector[] getIntraDayData(String symbol,String expirydate)
	{
		HashMap hs = new HashMap();
//for index intraday chart			
		hs.put("nifty","S&P CNX NIFTY");
		hs.put("cnx500","S&P CNX 500");
		hs.put("cnxbank","BANKNIFTY");
		hs.put("cnxit","CNX IT");
		hs.put("cnxmidcap","NIFTY MIDCAP 50");
		hs.put("jrnifty","CNX NIFTY JUNIOR");
		hs.put("niftymidcap50","NIFTY MIDCAP 50");
		hs.put("cnx100","S&P CNX DEFTY");

		HashMap hs1 = new HashMap();
//for index intraday chart			
		hs1.put("nifty","NIFTY");
	//	hs1.put("cnx500","S&P CNX 500");
		hs1.put("cnxbank","BANKNIFTY");
		hs1.put("cnxit","CNXIT");
	//	hs1.put("cnxmidcap","NIFTY MIDCAP 50");
		hs1.put("jrnifty","JUNIOR");
		hs1.put("niftymidcap50","NIFTY MIDCAP 50");
		hs1.put("cnx100","CNX100");


	
	
	
		
		try
			{
				
				Properties properties = new Properties();
//						properties.put("charttype", "ONLINE_INDEX");
//						properties.put("indexname", "S&P CNX NIFTY");
//				properties.put("indexname", "S%26P+CNX+NIFTY");

//				properties.put("indexname", "S&P CNX NIFTY");
//				properties.put("charttype", "ONLINE_INDEX");
				//properties.put("symbol", symbol);
//				properties.put("series", "EQ");
//				properties.put("mkttype", "N");
				String index = (String)hs.get(symbol);	
				String index1 = (String)hs1.get(symbol);	

				String method="";
				if(expirydate.equals(""))
				{
					if(index  != null)
					{
						properties.put("charttype", "ONLINE_INDEX");
						properties.put("indexname", index);
						method= "getIndexData";
					}
					else
					{
						properties.put("charttype", "ONLINE_STOCK");
						properties.put("symbol", symbol);
						properties.put("series", "EQ");
						properties.put("mkttype", "N");

						method= "getStockData";
					}
					
				}
				else
				{
					if(index1  != null)
					{
//						for future
						properties.put("charttype", "ONLINE_CONTRACT");
						properties.put("instrument","FUTIDX");
						properties.put("symbol",index1);
						properties.put("series","EQ");
						properties.put("mkttype","N");
						properties.put("expirydate",expirydate);
	  

						method= "getContractData";
					}
					else
					{
//						for future
						properties.put("charttype", "ONLINE_CONTRACT");
						properties.put("instrument","FUTSTK");
						properties.put("symbol",symbol);
						properties.put("series","EQ");
						properties.put("mkttype","N");
						properties.put("expirydate",expirydate);

						method= "getContractData";
					}
					
				}

				URL url = new URL("http://nseindia.com/chartdata");
				HttpMessage msg = new HttpMessage(url);
				InputStream inputstream = msg.sendGetMessage(properties);
				ObjectInputStream objectinputstream = new ObjectInputStream(inputstream);
				Object obj = objectinputstream.readObject();
				inputstream.close();
				Class c = obj.getClass();
				Method[] m = c.getMethods();
				objectinputstream.close();
				int i=0;
				for(i=0;i<m.length;i++)
				{
					if(m[i].getName().equals(method))
					{
						break;
					}
				}
			
				Vector[] plot1Data = (Vector[]) m[i].invoke(obj,null);

				if (plot1Data[0].size() > 0)
				{
				}
				for (i = 0; i < plot1Data[0].size(); i++)
				{
				}
				return plot1Data;
			}
			catch (Exception e)
			{
				return null;
			}
	}


	public static Vector[] getIntraDayData(String symbol,String expirydate,String startDate,String endDate)
	{
		HashMap hs = new HashMap();
//for index intraday chart			
		hs.put("nifty","S&P CNX NIFTY");
		hs.put("cnx500","S&P CNX 500");
		hs.put("cnxbank","BANKNIFTY");
		hs.put("cnxit","CNX IT");
		hs.put("cnxmidcap","NIFTY MIDCAP 50");
		hs.put("jrnifty","CNX NIFTY JUNIOR");
		hs.put("niftymidcap50","NIFTY MIDCAP 50");
		hs.put("cnx100","S&P CNX DEFTY");

		HashMap hs1 = new HashMap();
//for index intraday chart			
		hs1.put("nifty","NIFTY");
	//	hs1.put("cnx500","S&P CNX 500");
		hs1.put("cnxbank","BANKNIFTY");
		hs1.put("cnxit","CNXIT");
	//	hs1.put("cnxmidcap","NIFTY MIDCAP 50");
		hs1.put("jrnifty","JUNIOR");
		hs1.put("niftymidcap50","NIFTY MIDCAP 50");
		hs1.put("cnx100","CNX100");


	
	
	
		
		try
			{
				
				Properties properties = new Properties();
//						properties.put("charttype", "ONLINE_INDEX");
//						properties.put("indexname", "S&P CNX NIFTY");
//				properties.put("indexname", "S%26P+CNX+NIFTY");

//				properties.put("indexname", "S&P CNX NIFTY");
//				properties.put("charttype", "ONLINE_INDEX");
				//properties.put("symbol", symbol);
//				properties.put("series", "EQ");
//				properties.put("mkttype", "N");
				String index = (String)hs.get(symbol);	
				String index1 = (String)hs1.get(symbol);	

				String method="getPlot1Data";
				String method1="getPlot2Data";
				
					if(index1  != null)
					{
						properties.put("charttype", "HIST_CONTRACT");
							properties.put("symbol", index1);
//						method= "getIndexData";

						properties.put("instrument", "FUTIDX");

					}
					else
					{
						properties.put("charttype", "HIST_CONTRACT");
						properties.put("symbol", symbol);
						properties.put("instrument", "FUTSTK");

					}
						properties.put("series", "EQ");
						properties.put("mkttype", "N");
						properties.put("expirydate", expirydate);

						properties.put("stdate", startDate);
						properties.put("enddate", endDate);

						properties.put("plot1", "PRICE");
						properties.put("plot2", "TRD_QTY");

//						properties.put("indexname", "");

						

//						method= "getStockData";
					
				URL url = new URL("http://nseindia.com/chartdata");
				HttpMessage msg = new HttpMessage(url);
				InputStream inputstream = msg.sendGetMessage(properties);
				ObjectInputStream objectinputstream = new ObjectInputStream(inputstream);
				Object obj = objectinputstream.readObject();
				inputstream.close();
				Class c = obj.getClass();
				Method[] m = c.getMethods();
				objectinputstream.close();
				int i=0;
				Vector[] plot1Data=null;
				Vector[] plot2Data=null;
				for(i=0;i<m.length;i++)
				{
					if(m[i].getName().equals(method))
					{
						plot1Data = (Vector[]) m[i].invoke(obj,null);
					}
					if(m[i].getName().equals(method1))
					{
						plot2Data = (Vector[]) m[i].invoke(obj,null);
						
					}
				}
			
		


				Vector[] returnVector = new Vector[3];
				returnVector[0] = plot1Data[0];
				returnVector[1] = plot1Data[1];
				returnVector[2] = plot2Data[1];
			
				return returnVector;
			
			
			
			}
			catch (Exception e)
			{
				return null;
			}
			
	}



	public static void main(String[] args)
	{
		try
		{
			Properties properties = new Properties();
			//		properties.put("charttype", "ONLINE_INDEX");
			//		properties.put("indexname", "S&P CNX NIFTY");
			//		properties.put("indexname", "S&P CNX NIFTY");
			properties.put("charttype", "ONLINE_STOCK");
			properties.put("symbol", "TCS");
			properties.put("series", "EQ");
			properties.put("mkttype", "N");
			URL url = new URL("http://nseindia.com/chartdata");
			HttpMessage msg = new HttpMessage(url);
			InputStream inputstream = msg.sendGetMessage(properties);
			ObjectInputStream objectinputstream = new ObjectInputStream(inputstream);
			Object obj = objectinputstream.readObject();
			inputstream.close();
			Class c = obj.getClass();
			Method[] m = c.getMethods();
			objectinputstream.close();
			int i=0;
			for(i=0;i<m.length;i++)
			{
				if(m[i].getName().equals("getStockData"))
				{
					break;
				}
			}
			
			Vector[] plot1Data = (Vector[]) m[i].invoke(obj,null);

			if (plot1Data[0].size() > 0)
			{
			}
			for (i = 0; i < plot1Data[0].size(); i++)
			{
			}
		}
		catch (Exception e)
		{
		}
	}
}

/*
 * Created on May 3, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.trading;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Iterator;
import java.util.SortedMap;
import java.util.StringTokenizer;
import java.util.Vector;

import com.stockfaxforu.getolddataforu.thread.NewsThread;
import com.stockfaxforu.result.GetResultData;
import com.stockfaxforu.scan.util.ScanUtility;
import com.stockfaxforu.util.StockConstants;
import com.stockfaxforu.util.Utility;

/**
 * @author sdeopura
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class TradingDiary
{

	/**
	 * 
	 */
	public TradingDiary()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args)
	{
	}

	/**
	 * @param string
	 */
	
	public static void saveTransaction(String string)
	{
		RandomAccessFile r=null;
		try
		{	
			File f = new File(StockConstants.TRADINGHISTORY);
			if(!f.exists())
			{
				r = new RandomAccessFile(StockConstants.TRADINGHISTORY,"rw");
				r.writeBytes(string+"\n");
				r.close();
			}
			else
			{
				r  = new RandomAccessFile(StockConstants.TRADINGHISTORY,"rw");
				r.seek(r.length());
				r.writeBytes(string + "\n");
				r.close();
	
			}
				// TODO Auto-generated method stub
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				r.close();
			}
			catch(Exception e)
			{
			}
		}
		
	}
	public static Vector tradingHistory=null;

	public static String Symbol="Symbol";
	public static String Date="Date";
	public static String Type="Type";
	public static String Price="Price";
	public static String Qty="Qty";

//			TradingDiary.saveTransaction(symbol+","+date+","+buysell+","+price+","+qty);	
	
	public static void loadTradingHistory()
	{
		RandomAccessFile r = null;;
		try
		{
			tradingHistory = new Vector();
			r  = new RandomAccessFile(StockConstants.TRADINGHISTORY,"r");
			
			String line = "";
			while((line = r.readLine()) != null)
			{
				StringTokenizer st = new StringTokenizer(line,",");
				
				String[] data = new String[7];
				data[0] = st.nextToken();
				data[1] = st.nextToken();
				data[2] = st.nextToken();
				data[3] = st.nextToken();
				data[4] = st.nextToken();
				
				tradingHistory.addElement(data);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				r.close();
			}
			catch (IOException e1)
			{
				// TODO Auto-generated catch block
//				e1.printStackTrace();
			}
		}
	}
	public static String[][] getDataValue()
	{
		
		HashMap table  = new HashMap();
			String s="";
			try
			{
				s = Utility.getFileContent(StockConstants.TRADINGHISTORY);
			}
			catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			StringTokenizer st = new StringTokenizer(s,"\n");
			
			while(st.hasMoreElements())
			{
				String temp = st.nextToken().trim();
				if(temp.equals(""))
					continue;
				StringTokenizer lineToken = new StringTokenizer(temp,",");
				String symbol = lineToken.nextToken();
				String date = lineToken.nextToken();
				String buysell = lineToken.nextToken();
				String price = lineToken.nextToken();
				String qty = lineToken.nextToken();
				String[] data = new String[9]; 
				
				data[0] = symbol;
				data[1] = date;
				data[2] = buysell;
				data[3] = price;
				data[4] = qty;
				data[5] = "";
				data[6] = "";
				data[7] = "";
				data[8] = "delete";
								
				Vector v = (Vector)table.get(symbol);
				if(v==null)
				{
					v = new Vector();
					v.add(data);
					table.put(symbol, v);
				}
				else
				{
					v.add(data);
				}
			}
			int size=0;
			Vector retVec = new Vector();
			float totalinvestment = 0;
			float totallossprofit =0;
			for(Iterator it = table.keySet().iterator();it.hasNext();)
			{
				Vector v = (Vector)table.get(it.next());
				int qtyremaining =0;
				float currentvalue = 0;
				String symbol="";
				for(int i=0;i<v.size();i++)
				{
					String[] data = (String [])v.get(i);
					symbol = data[0];
					if(data[2].equalsIgnoreCase("Buy"))
					{
						qtyremaining = qtyremaining + Integer.parseInt(data[4]);
						currentvalue = currentvalue - Integer.parseInt(data[4])*Float.parseFloat(data[3]);
						
				//		data[6] = qtyremaining+"";
						data[5] = (int)((Float.parseFloat(data[3])*Float.parseFloat(data[4])))+"";
					}
					else
					{
						qtyremaining = qtyremaining - Integer.parseInt(data[4]);
						currentvalue = currentvalue + Integer.parseInt(data[4])*Float.parseFloat(data[3]);
				//		data[6] = qtyremaining+"";
						data[5] = (int)((Float.parseFloat(data[3])*Float.parseFloat(data[4])))+"";
					}
				}
				String stockDesc = Utility.getStockDes(symbol);
				//HashMap hs = ScanUtility.getSymbolDetail(symbol);
				HashMap hs = ScanUtility.getLiveRateForPortfolio(symbol);

						
				currentvalue = currentvalue + Float.parseFloat((String)hs.get(ScanUtility.close)) * qtyremaining;
				
				String[] mydata = new String[8];
				mydata[0] =  "Total";
				//price
				mydata[3] =  Float.parseFloat((String)hs.get(ScanUtility.close)) + "";
				//holding
				mydata[4] =  qtyremaining + "";
				//actual value
				mydata[5] =  Float.parseFloat((String)hs.get(ScanUtility.close)) * qtyremaining +"";
				
				//profit loss
				mydata[7] =  currentvalue + "";
				mydata[6] = ( currentvalue * 100 ) / (Float.parseFloat((String)hs.get(ScanUtility.close)) * qtyremaining - currentvalue) + "";  
				
				v.add(mydata);
				totalinvestment = totalinvestment + Float.parseFloat((String)hs.get(ScanUtility.close)) * qtyremaining;
				totallossprofit = totallossprofit + currentvalue;
				retVec.addAll(v);
			}
			String[] mydata = new String[8];
			mydata[0] =  "Overall Total ";
			//price
//			mydata[3] =  Float.parseFloat((String)hs.get(ScanUtility.close)) + "";
			//holding
//			mydata[4] =  qtyremaining + "";
			//actual value
			mydata[5] =  totalinvestment +"";
			
			//profit loss
			mydata[7] =  totallossprofit + "";
			
			mydata[6] = ( totallossprofit * 100 ) / (totalinvestment - totallossprofit) + "";  
			
			
			retVec.add(mydata);
		
			String[][] retData = new String[retVec.size()][8];
			retVec.copyInto(retData);
			return retData;
	}
	public static void deleteRow(String[][] data,int rowno)
	{
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<data.length;i++)
		{
			if(i != rowno && data[i][0].indexOf("Total") == -1 && data[i][0].indexOf("Overall") == -1 ) 
			{
				sb.append(getAsString(data[i])+"\n");
			}
		}
		File f = new File(StockConstants.TRADINGHISTORY);
		f.delete();
		try
		{
			Utility.saveContent(StockConstants.TRADINGHISTORY, sb.toString());
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static String[][] getTransactionByDate()
	{
		if(tradingHistory==null)
			loadTradingHistory();
			
		String[][] retTran = new String[tradingHistory.size()][7];
		float investedamt=0;
		float balance=0;
		for(int i=0;i<tradingHistory.size();i++)
		{
			retTran[i] = (String [] )tradingHistory.elementAt(i);
			if(retTran[i][2].equalsIgnoreCase("Buy"))
			{
				investedamt = -1 * (Float.parseFloat(retTran[i][3])*Float.parseFloat(retTran[i][4])); 	
			}
			else
			{
				investedamt =  (Float.parseFloat(retTran[i][3])*Float.parseFloat(retTran[i][4])); 	
			}
			balance = balance + investedamt;
			retTran[i][5] = investedamt + "";
			retTran[i][6] = balance + "";
		}
		
		return retTran;
	}
	public String[][] getAllSymbolTransaction(String symbol)
	{
		if(tradingHistory==null)
			loadTradingHistory();
			
		String[][] retTran = new String[tradingHistory.size()][5];
		
		Vector v = new Vector();
		float investedamt=0;
		float balance=0;
		for(int i=0;i<tradingHistory.size();i++)
		{
			retTran[i] = (String [] )tradingHistory.elementAt(i); 	
			if(retTran[i][0].equalsIgnoreCase(symbol))
			{
				v.addElement(retTran[i]);
				if(retTran[i][2].equalsIgnoreCase("Buy"))
					{
						investedamt = -1 * (Float.parseFloat(retTran[i][3])*Float.parseFloat(retTran[i][4])); 	
					}
					else
					{
						investedamt =  (Float.parseFloat(retTran[i][3])*Float.parseFloat(retTran[i][4])); 	
					}
					balance = balance + investedamt;
					retTran[i][5] = investedamt + "";
					retTran[i][6] = balance + "";
	
			}
		}
		String[][] ret = new String[v.size()][5];
		for(int i=0;i<v.size();i++)
		{
			ret[i] = (String [])v.elementAt(i);
		}
		return ret;
		
	}

	public String[][] createPortfolio()
	{
		if(tradingHistory==null)
			loadTradingHistory();
			
		String[][] retTran = new String[tradingHistory.size()][5];
		HashMap hs = new HashMap();
		Vector v = new Vector();
		for(int i=0;i<tradingHistory.size();i++)
		{
			retTran[i] = (String [] )tradingHistory.elementAt(i); 	
			if(hs.get(retTran[i][0]) == null)
			{
				hs.put(retTran[i][0],getLine(i));
			}
			else
			{
				String s = (String)hs.get(retTran[i][0]);
				hs.put(retTran[i][0],s+":"+getLine(i));
			}
		}
		Vector retVector = new Vector();
		return null;		
	}
	public static String getAsString(String[] data)
	{
		String s = data[0] + "," + data[1] + ","	+ data[2]  + "," + data[3] + "," + data[4] ;
		return s;
	}
	public static String getLine(int index)
	{
		String[] temp = (String [])tradingHistory.elementAt(index);
		String s = temp[1] + "," + temp[2] + "," + temp[3] + temp[4];
		return s;
	}
	
}

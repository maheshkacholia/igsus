/*
 * Created on Jun 21, 2007
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.livedata;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.StringTokenizer;

import com.stockfaxforu.util.Utility;
/**
 * @author sdeopura
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class LiveMarket
{
	boolean trfound = false;
	boolean tdfound = false;
				

	String tokens = ":DAY CLOSE:DATE:LAST TRADED =TIME:LAST TRADE =PRICE:BEST BID =PRICE:DAY =OPEN:BEST OFFER =PRICE:DAY =HIGH:BEST BID =QTY:DAY =LOW:BEST OFFER =QTY:PREVIOUS =DAY CLOSE:52 WEEK =HIGH:% =CHANGE:52 WEEK =LOW:DAY =VOLUME:";
	public static void main(String args[])
	{
		LiveMarket livemarket = new LiveMarket();
		try
		{
			HashMap dataHashMap = livemarket.getDataHashmap("TCS");
			System.out.println(dataHashMap);
			// ln(dataHashMap);
		}
		catch (Exception e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
/*
		long t1 = System.currentTimeMillis();
		LiveMarket getlive = new LiveMarket();
		String s="";
		try
		{
			s = getlive.getDataInStr("tcs");
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}
//		HashMap hs = getlive.getHashMap(s);
	//	// ln(s);
		long t2 = System.currentTimeMillis();
	//	// ln(t2-t1);
*/	
	}
	public HashMap getHashMap(String result)
	{
		result  = Utility.replaceString(result,",,", " , , ");
		HashMap hs = new HashMap();
		StringTokenizer st = new StringTokenizer(result,"\n");
		while(st.hasMoreTokens())
		{
			String s = st.nextToken();
			StringTokenizer st2 = new StringTokenizer(s,",");
			try
			{
				for(int i=0;i<2;i++);
				{
					String name = st2.nextToken();	
					String nse = st2.nextToken();
					String bse = st2.nextToken();	
		//			// ln("name="+"nse="+nse+"bse="+bse);
					hs.put(Utility.replaceString(name.trim().toUpperCase()," ",""), nse.trim());
				}
				
			}
			catch(Exception e)
			{
				
			}
			try
			{
				for(int i=0;i<2;i++);
				{
					String name = st2.nextToken();	
					String nse = st2.nextToken();
					String bse = st2.nextToken();	
			//		// ln("name1="+"ns1e="+nse+"bse1="+bse);

					hs.put(Utility.replaceString(name.trim().toUpperCase()," ",""), nse.trim());
				}
				
			}
			catch(Exception e)
			{
				
			}
			
		}
		return hs;
	}
	public String getData(String symbol) throws Exception
	{
//		RandomAccessFile file = null;
		byte b ;	
		byte[] b1 = new byte[1];
		StringBuffer token=new StringBuffer();
		BufferedReader file=null;
		StringBuffer finalData = new StringBuffer();
		StringBuffer eachline = new StringBuffer();	
		StringBuffer parseline = new StringBuffer();	

		try
		{
			String url="http://getquote.icicidirect.com/trading_stock_quote.aspx?Symbol="+symbol; //new
		//	String url="http://getquote.icicidirect.com/trading/equity/trading_stock_quote.asp?Symbol="+symbol;
			file = Utility.getDataFromURL(url);
			String line = "";
			
			while ((line = file.readLine()) != null)
			{
				
				if (line.indexOf("Cash Sell") != -1)
				{
					break;
				}
			}
			while ((line = file.readLine()) != null)
			{

				if (line.indexOf(">BSE</th>") != -1)
				{
					break;
				}
			}
			StringBuffer data1 = new StringBuffer();
			while ((line = file.readLine()) != null)
			{

				data1.append(line + " ");
			}			
			byte[] datastr = data1.toString().toUpperCase().getBytes();
			data1=null;
			int pos=0;
			while (pos < datastr.length)
			{
				b = datastr[pos];
				pos++;
				b1[0] = b;
				String tmp = new String(b1);
				//			// ln("b="+tmp);
				if (token.length() == 0 && b == '<')
				{
					token.append(tmp);
				}
				else if (token.length() > 0 && token.toString().charAt(0) == '<')
				{
					token.append(tmp);
				}
				else
				{
					token = new StringBuffer();
				}
				String s = token.toString();
				if(s.equalsIgnoreCase("<TBODY>"))
				{
					token = new StringBuffer();
					
				}
				if (s.equalsIgnoreCase("<TR"))
				{
					trfound = true;
					token = new StringBuffer();
				}
				else if (s.equalsIgnoreCase("</TR>"))
				{
					trfound = false;
	//				// ln(eachline);
					finalData.append(eachline + "\n");

					token = new StringBuffer();
					eachline = new StringBuffer();
	
				}
				else if (s.equalsIgnoreCase("<TD") || s.equalsIgnoreCase("<TH"))
				{
					tdfound = true;
					token = new StringBuffer();
					parseline = new StringBuffer();
				}
				else if (s.equalsIgnoreCase("</TD>") || s.equalsIgnoreCase("</TH>"))
				{
					tdfound = false;
					token = new StringBuffer();
					String data = getRequiredData(parseline.toString());
					eachline.append(data + ",");
				}
				if(b=='>' && tdfound)
				{
					token=new StringBuffer();
				//	tmp="";
				}
				if(b=='>' && trfound)
				{
					token=new StringBuffer();
				//	tmp="";
				}
				if (tdfound == true)
				{
					parseline.append(tmp);
				}
				
			}
			file.close();

		}
		catch (Exception e)
		{
			throw e;
		}
		finally
		{
			eachline=null;
			parseline=null;
			token=null;
			file.close();
			
		}	
		return finalData.toString();

	}

	public String getRequiredData(String data)
	{
		data = replaceString(data, "<B>*</B>", "");
		String retStr="";
		 int i= data.indexOf(">");
		int j= data.indexOf("<");
		if(i != -1 && j != -1)
			retStr = data.substring(i+1, j);
		
		retStr = replaceString(retStr, ",","");
		retStr = replaceString(retStr, "\r\n","");
		retStr = replaceString(retStr, "\n","");
		
		return retStr.trim(); 

	}		
	public String replaceString(String data,String oldStr,String newString)
	{
		StringBuffer sb = new StringBuffer();
		String data1;
		while(true)
		{
			int pos = data.indexOf(oldStr);
			if(data.indexOf(oldStr) == -1)
				break;
				
			data1 = data.substring(0,pos);
			data1 = data1 + newString;
			data1 = data1 + data.substring(pos+oldStr.length());  
			
			data = data1; 	
		}
		return data;		
	}	
	private boolean correctEntry(String data)
	{
		StringTokenizer st = new StringTokenizer(data,",");
		int i =0;
		while(st.hasMoreTokens())
		{
			String s = st.nextToken();
			i++;
			if(i==7)
			{
				try
				{
					float f = Float.parseFloat(s);
					if(f==0)
						return false;
					else
						return true;
				}
				catch(Exception e)
				{
					return true;
				}	
			}				
		}
		
		// TODO Auto-generated method stub
		return true;
	}
	/**
	 * @param symbol
	 * @return
	 */
	public HashMap getDataHashmap(String symbol) throws Exception
	{
		String s = getData(symbol);
		HashMap hs = getHashMap(s);
		return hs;
	}
	public String getDataInStr(String symbol) throws Exception
	{
				String s = getData(symbol);
				HashMap hs = getHashMap(s);
	//			// ln("hs=="+hs);
				StringBuffer sb = new StringBuffer();
	//			sb.append(symbol+",");
				
				
				sb.append(hs.get("DATE")+",");
				sb.append(hs.get("LAST TRADED TIME")+",");
				sb.append(hs.get("LAST TRADE PRICE")+",");
				sb.append(hs.get("DAY VOLUME")+",");
				
				sb.append(hs.get("BEST BID PRICE")+",");
				sb.append(hs.get("BEST BID QTY")+",");
			
				sb.append(hs.get("BEST OFFER PRICE")+",");
				sb.append(hs.get("BEST OFFER QTY")+",");
				
				sb.append(hs.get("DAY OPEN")+",");
				sb.append(hs.get("DAY HIGH")+",");
				sb.append(hs.get("DAY LOW")+",");
				sb.append(hs.get("PREVIOUS DAY CLOSE")+",");
				sb.append(hs.get("% CHANGE")+",");
				
				hs=null;
				return sb.toString();
		}
	
}

/*
 * Created on Dec 15, 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.currentprice;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

import com.stockfaxforu.util.StockConstants;
import com.stockfaxforu.util.Utility;

/**
 * @author sdeopu1
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CurrentPriceNDTV
{
	boolean trfound = false;
	boolean tdfound = false;
	StringBuffer sb = new StringBuffer();
	StringBuffer tddata = new StringBuffer();
	StringBuffer finalData = new StringBuffer();
				
	StringBuffer eachline = new StringBuffer();	
	StringBuffer parseline = new StringBuffer();	

	public static void main(String[] args)
	{
		CurrentPriceNDTV current = new CurrentPriceNDTV();
//		current.getCurrentPrice();
		current.saveLiveContent();

	}
	public  void saveLiveContent()
	{
		try
		{
			String s = Utility.getUrlContent("http://profit.ndtv.com/Markets/StockPrint.aspx?sector=NSESCRIPS");
			System.out.println(s);
			Utility.saveContent(StockConstants.INSTALL_DIR+ "/temp/templive.htm", s);
			parseFile();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			// TODO Auto-generated catch block
	//		e.printStackTrace();
		}
	}
	public  void parseFile()
	{
		RandomAccessFile r = null;
		RandomAccessFile file=null;
		File f = new File(StockConstants.INSTALL_DIR+ "/temp/templive.csv" );
		byte b ;	
		byte[] b1 = new byte[1];
		StringBuffer token=new StringBuffer();
		if(f.exists())
			f.delete();

		try
		{
			file = new RandomAccessFile(StockConstants.INSTALL_DIR+ "/temp/templive.csv", "rw");

			r = new RandomAccessFile(StockConstants.INSTALL_DIR+ "/temp/templive.htm","r");
			String line = "";
			while((line = r.readLine()) != null)
			{
				if(line.toLowerCase().indexOf("gvstock") != -1)
				{
					break;	
				}		
			}
/*			while((line = r.readLine()) != null)
			{
				if(line.toLowerCase().indexOf("<tr>") != -1)
				{
					break;	
				}		
			}
				
*/				
			while(r.getFilePointer() < r.length())
			{
				b = r.readByte();
				b1[0] = b;
				String tmp = new String(b1);
//				// ln("b="+tmp);
				
				if(token.length()==0 && b == '<')
				{
					token.append(tmp);
				}
				else if(token.length() > 0 && token.toString().charAt(0) == '<')
				{
					token.append(tmp);
						
				}
				else
				{
					token = new StringBuffer();

				}
						
				String s = token.toString();
				if(s.equalsIgnoreCase("<TR"))
				{
					trfound = true;
					token = new StringBuffer();
					
				}
				else if(s.equalsIgnoreCase("</TR>"))
				{
					trfound = false;
		//			// ln(eachline);
					if(correctEntry(eachline.toString()))
						finalData.append(eachline+"\n");
					token = new StringBuffer();
					eachline = new StringBuffer();
					
				}
				else if(s.equalsIgnoreCase("<TD") || s.equalsIgnoreCase("<TH"))
				{
					tdfound = true;
					token = new StringBuffer();
					parseline = new StringBuffer();
				}
				else if(s.equalsIgnoreCase("</TD>") || s.equalsIgnoreCase("</TH>"))
				{
					tdfound = false;
					token = new StringBuffer();
					String data = getRequiredData(parseline.toString());
					eachline.append(data+",");
				}

				if(tdfound==true)
				{
					parseline.append(tmp);
				}	

			}
			file.writeBytes(finalData.toString());
			file.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
	}
	/**
	 * @param string
	 * @return
	 */
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
	public String getRequiredData(String data)
	{
		String retStr="";
		 int i= data.indexOf(">");
		int j= data.indexOf("<");
		if(i != -1 && j != -1)
			retStr = data.substring(i+1, j);
		
		retStr = replaceString(retStr, ",","");
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
}

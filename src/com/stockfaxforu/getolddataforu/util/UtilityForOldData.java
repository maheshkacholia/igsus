/*
 * Created on Sep 8, 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.getolddataforu.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;

import com.stockfaxforu.util.StockConstants;

/**
 * @author sdeopu1
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class UtilityForOldData
{
	
	public static String getComputerName()
	{
		try
		{
			return InetAddress.getLocalHost().getHostName();
		}
		catch(Exception e)
		{
			return null;
		}
	}
	public static String[] ge = {"ht","tp://w","ww.ge","ocities.com/mkacho", "lia/accesslist_liv", "equoteforu.txt"}; 
	public static boolean checkComputerName()
	{
		try
		{

			StringBuffer sb1 = new StringBuffer();
			try
			{
				throw new Exception();
			}
			catch(Exception e)
			{
				for(int i=0;i<ge.length;i++)
				{
					sb1.append(ge[i]);
				}
				
			}	
			BufferedReader bf = UtilityForOldData.getDataFromURL(sb1.toString());
			StringBuffer sb = new StringBuffer();
			String line="";
			while((line=bf.readLine()) != null)
			{
				sb.append(line);
			}
			String s = sb.toString().toLowerCase();  
			if(s.indexOf(StockConstants.COMPUTER_NAME) != -1 || s.indexOf("guest") != -1)
			{
				if(s.indexOf(StockConstants.COMPUTER_NAME) != -1)
				{
					StockConstants.LogBuffer.append("Congrats!!! You are authorized to use the tool \n ");
				}
				else
				{
					StockConstants.LogBuffer.append("You are using in trial period. mail to customercare_stockfaxforu@yahoo.com ");
					StockConstants.LogBuffer.append(" and register yourself for full version \n");
				}

				return true;	
			}	
		}
		catch(Exception e)
		{
			return false;
		}	


		return false;
	}

	
	public static void setAllConstants()
	{
		try
		{
			com.stockfaxforu.util.StockConstants.LogBuffer = new StringBuffer();
			StockConstants.install.load(new java.io.FileInputStream(StockConstants.INSTALL_DIR + "/" + "install.properties"));

			StockConstants.PROXY = StockConstants.install.getProperty("proxyname");
			StockConstants.PORT = StockConstants.install.getProperty("port");
			StockConstants.USERID = StockConstants.install.getProperty("userid");
			StockConstants.PWD = StockConstants.install.getProperty("password");
			String proxy = StockConstants.install.getProperty("proxy");
			if (proxy != null && proxy.equals("yes"))
				StockConstants.IS_PROXY = true;
			else
				StockConstants.IS_PROXY = false;

		}
		catch (Exception e)
		{
			StockConstants.IS_PROXY = false;
		}

		// TODO Auto-generated method stub

	}
	public static String removeSpaces(String oldStr)
	{
		//		< > " ' % ; ) ( & + -

		String[][] s = { { " ", "%20" }
		};

		for (int x = 0; x < s.length; x++)
		{
			oldStr = replaceString(oldStr, s[x][0], s[x][1]);
		}
		return oldStr;
	}
	public static String replaceString(String oldStr, String replceStr, String replcedStr)
	{
		try
		{
			int x = 0;
			while (true)
			{
				x = oldStr.indexOf(replceStr);
				;
				;
				;
				if (x == -1)
					break;
				oldStr = oldStr.substring(0, x) + replcedStr + oldStr.substring(x + replceStr.length());
			}
		}
		catch (Exception e)
		{
		}
		return oldStr;
	}

	public static String getMonth(String string)
	{
		try
		{
			int i = Integer.parseInt(string.trim());
			String retStr = "";
			switch (i)
			{
				case 1 :
					retStr = "JAN";
					break;
				case 2 :
					retStr = "FEB";
					break;
				case 3 :
					retStr = "MAR";
					break;
				case 4 :
					retStr = "APR";
					break;
				case 5 :
					retStr = "MAY";
					break;
				case 6 :
					retStr = "JUN";
					break;
				case 7 :
					retStr = "JUL";
					break;
				case 8 :
					retStr = "AUG";
					break;
				case 9 :
					retStr = "SEP";
					break;
				case 10 :
					retStr = "OCT";
					break;
				case 11 :
					retStr = "NOV";
					break;
				case 12 :
					retStr = "DEC";
					break;

			}
			return retStr;
			// TODO Auto-generated method stub

		}
		catch (Exception e)
		{
//			e.printStackTrace();
		}
		return null;
	}




	/**
	 * @param string
	 */

	
	public  Vector getListOfAllSymbols() throws Exception
	{
		Vector v = new Vector();
		java.io.InputStream inputStream = ClassLoader.getSystemResourceAsStream(StockConstants.SYMBOLFILE);
//		ClassLoader classloader = this.getClass().getClassLoader();
//		URL io = classloader.getResource(StockConstants.SYMBOLFILE);
	
//		String filePath = io.getPath();
//		// ln("filePath="+filePath);
//		io = classloader.getResource(filePath);
		byte[] b = new byte[32000];
		int x = -1;
		StringBuffer sb = new StringBuffer();
		while(true)
		{
			x = inputStream.read(b);
			if(x == -1)
				break;
			String s = new String(b,0,x);
			sb.append(s);
		}	
		
/*		BufferedInputStream br = null;
		br = new BufferedInputStream(inputStream);
		boolean isfound = false;
		String readLine;
*/
		StringTokenizer st = new StringTokenizer(sb.toString(),"\r\n");
		while((st.hasMoreTokens())) 
		{
			v.addElement(st.nextToken());
		}	
		return v;
	}
	public static Vector getList()
	{
		Vector v= new Vector();
		v.addElement(StockConstants.QUERYLIST);
		File f = new File(StockConstants.INSTALL_DIR + "/list");
		String[] s = f.list();
		for(int i=0;i<s.length;i++)
			v.addElement(s[i].substring(0,s[i].indexOf(".")));
		return v;
	}

	public static float floatDataAtTwoPrecision(float f)
	{
		int i = (int)(f*100);
		f = (float)(i/100.0);
		return f;
	}
	public static BufferedReader getDataFromURL(String urlStr) throws Exception
	{
		BufferedReader rd =	null;
		try
		{
			URL url =	new URL(urlStr);
			URLConnection conn = url.openConnection();
			
//			StockConstants.IS_PROXY=true;
			if(StockConstants.IS_PROXY)
			{
				System.getProperties().put("proxySet", "true");
				System.getProperties().put("proxyHost", StockConstants.PROXY);
				System.getProperties().put("proxyPort", StockConstants.PORT);
						
//				sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
//				String useridpwd = StockConstants.USERID + ":" + StockConstants.PWD;
//				String encodedUserPwd = encoder.encode(useridpwd.getBytes());
//				conn.setRequestProperty("Proxy-Authorization","Basic " + encodedUserPwd);
			}
			rd =	new BufferedReader(	new InputStreamReader(conn.getInputStream()));
		}
		catch(Exception e)
		{
//			e.printStackTrace();		

			try
			{	
				rd.close();

			}
			catch(Exception e1)
			{
			}
//			e.printStackTrace();
			throw e;
		}
		
		return rd;
	}
	

	public static String getNewFormatedDate(String oldStr)
	{
		StringTokenizer st = new StringTokenizer(oldStr,"-");
		String day = st.nextToken();
		String month = st.nextToken();
		String month1 = "";
		if(month.equalsIgnoreCase("Jan"))
			month1 = "01";
		if(month.equalsIgnoreCase("Feb"))
					month1 = "02";
		if(month.equalsIgnoreCase("Mar"))
					month1 = "03";
		if(month.equalsIgnoreCase("Apr"))
					month1 = "04";
		if(month.equalsIgnoreCase("May"))
					month1 = "05";
		if(month.equalsIgnoreCase("Jun"))
					month1 = "06";
		if(month.equalsIgnoreCase("Jul"))
					month1 = "07";
		if(month.equalsIgnoreCase("Aug"))
					month1 = "08";
		if(month.equalsIgnoreCase("Sep"))
					month1 = "09";
		if(month.equalsIgnoreCase("Oct"))
					month1 = "10";
		if(month.equalsIgnoreCase("Nov"))
					month1 = "11";
		if(month.equalsIgnoreCase("Dec"))
					month1 = "12";

		String year = st.nextToken();
		String retStr = day + "/" + month1 + "/" + year  ; 
		return 	retStr;		
	}	

	public static String getNewFormatedDateInHiFen(String oldStr)
	{
		StringTokenizer st = new StringTokenizer(oldStr,"-");
		String day = st.nextToken();
		String month = st.nextToken();
		String month1 = "";
		if(month.equalsIgnoreCase("Jan"))
			month1 = "01";
		if(month.equalsIgnoreCase("Feb"))
					month1 = "02";
		if(month.equalsIgnoreCase("Mar"))
					month1 = "03";
		if(month.equalsIgnoreCase("Apr"))
					month1 = "04";
		if(month.equalsIgnoreCase("May"))
					month1 = "05";
		if(month.equalsIgnoreCase("Jun"))
					month1 = "06";
		if(month.equalsIgnoreCase("Jul"))
					month1 = "07";
		if(month.equalsIgnoreCase("Aug"))
					month1 = "08";
		if(month.equalsIgnoreCase("Sep"))
					month1 = "09";
		if(month.equalsIgnoreCase("Oct"))
					month1 = "10";
		if(month.equalsIgnoreCase("Nov"))
					month1 = "11";
		if(month.equalsIgnoreCase("Dec"))
					month1 = "12";

		String year = st.nextToken();
		String retStr = day + "-" + month1 + "-" + year  ; 
		return 	retStr;		
	}	


	
	
	public static void main(String[] args )
	{
	}
	/**
	 * @param startdate
	 * @return
	 */
	public static boolean checkdate(String startdate)
	{
		if(startdate==null || startdate.trim().equals(""))
		{
			StockConstants.error = "No Date is entered,Please Enter a date in dd-mm-yyyy format";
			return true;
		}
		
		SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		df.setLenient(false);
		ParsePosition pos = new ParsePosition(0);
		
		String strDate = startdate;
		
		Date date = df.parse(strDate, pos);
		
				// Check all possible things that signal a parsing error
		if ((date == null) || (pos.getErrorIndex() != -1)) 
		{
			StockConstants.error = "Error: " + pos.getIndex();
			if (date == null) 
			{
				StockConstants.error = "Incorrect date is Entered,Please check date ";
				return true;

			}
			if (pos.getErrorIndex() != -1) 
			{
				StockConstants.error = "Incorrect date is Entered,Please check date ";
				return true;

			}			
		}
		return false;
	}
	
	
}

/*
 * Created on Sep 8, 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.util;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.ComboBoxModel;

import com.stockfaxforu.config.ConfigUtility;
import com.stockfaxforu.formulabuilder.MessageDiaglog;
import com.stockfaxforu.frontend.ShowResultInTable;
import com.stockfaxforu.query.DataBaseInMemory;
/**
 * @author sdeopu1
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Utility
{
	public static final int FROM_LAST = 1;
	public static final int FROM_START = 2;
	public static HashMap symbomDescToCode=new HashMap();
	public static String downloadIndexData(String index,String filename)
	{
		try
		{
			
		String cont=null;
		try {
			String csvcont = Utility.getFileContent(StockConstants.INSTALL_DIR+"/data/"+filename+".csv");
			StringTokenizer st = new StringTokenizer(csvcont,"\n");
			String s3=null;
			while (st.hasMoreElements())
			{
				s3 = st.nextToken();
			}
			StringTokenizer st1 = new StringTokenizer(s3,",");
			st1.nextToken();
			st1.nextToken();
			String startDate = st1.nextToken();
			String[] ss = startDate.split("-");
			int x = getIntMonth(ss[1]);
			String t = x +"";
			if (x< 10)
				t = "0"+x;
			startDate = ss[0]+"-"+ t + "-" + ss[2];
			
			Calendar c = Calendar.getInstance();
			StringBuffer snewbuf = new StringBuffer();
			int xyz = c.get(Calendar.DATE);
			String yy = xyz+"";
			if(xyz < 10)
			{
				yy = "0"+yy;
			}
			snewbuf.append(yy+"-");
			
			xyz = c.get(Calendar.MONTH) + 1;
			yy = xyz+"";
			if(xyz < 10)
			{
				yy = "0"+yy;
			}
			snewbuf.append(yy+"-");
			snewbuf.append(c.get(Calendar.YEAR));
			
			String mm="http://www.nseindia.com/marketinfo/indices/histdata/historicalindices.jsp?indexType="+index+"&fromDate="+startDate+"&toDate="+snewbuf;
	
			cont = Utility.getUrlContent(mm);
	//		System.out.println(cont);
		} catch (Exception e) {
			// TODO Auto-generated catch block
		//	e.printStackTrace();
			return null;
		}
		cont = cont.toLowerCase();
		int i = cont.indexOf("for the period");
		cont = cont.substring(i);
		i = cont.indexOf("turnover");
		cont = cont.substring(i);
		
		i = cont.indexOf("<tr>");
		cont = cont.substring(i);
		
		StringTokenizer st = new StringTokenizer(cont,"\n");
		boolean newdataline=false;
		int datapos=0;
		String map[] = {"Date","Open","High","Low","Close","Volume","Turnver"};
		HashMap hs=new HashMap();
		StringBuffer sb = new StringBuffer();
		int x=0;
		while(st.hasMoreTokens() )
		{
			String s = st.nextToken();
			
			s = Utility.replaceString(s," ","");
			
			if(s.indexOf("<tr>") != -1)
			{
				newdataline=true;
				datapos=0;
				sb.append("\n");
			}
			else if(s.indexOf("</tr>") != -1)
			{
				newdataline=false;
				x++;
			}
			else if (s.indexOf("</table>") != -1)
			{
				break;
			}
			else if (s.indexOf("<td") != -1)
			{
				String t = Utility.replaceString(s,"<nobr>","");
				t = Utility.replaceString(t,"</nobr>","");
				
				t = t.substring(t.indexOf(">")+1);
				t = t.substring(0,t.indexOf("<"));
				
				if(datapos==0 && x!=0)
				{
					sb.append(index.toUpperCase()+",EQ,");
				}
				if(x!=0)
					sb.append(t+",");
				if((datapos==1 || datapos==4) && x != 0 )
				{
					sb.append(t+",");
							
				}
		//		System.out.println(map[datapos] +"="+t);
				
				datapos++;
			
			}
		}
		
		try
		{
			RandomAccessFile r = new RandomAccessFile(StockConstants.INSTALL_DIR+"/data/"+filename+".csv", "rw");
			r.seek(r.length());
			r.writeBytes(sb.toString().trim()+"\n");
			r.close();
		}
		catch(Exception e)
		{
			
		}
		
		
		return sb.toString().trim();
	
		}
		catch(Exception e)
		{
			return null;
		}
	}
	public static Calendar convertStringToTimeStamp(String timestampStr)
	{
	//1/6/2011-11:28:00	
		StringTokenizer st = new StringTokenizer(timestampStr,"-");
		
		String mydate = st.nextToken();
		StringTokenizer mydatest = new StringTokenizer(mydate,"//");
		String mymonth = mydatest.nextToken();
		String myday = mydatest.nextToken();
		String myyear = mydatest.nextToken();
		int myyeari = Integer.parseInt(myyear) ;
		int mymonthi = Integer.parseInt(mymonth) - 1;
		int mydayi = Integer.parseInt(myday);
		
		String time = st.nextToken();
		time = Utility.replaceString(time, "am", "");
		time = Utility.replaceString(time, "pm", "");
		
	//	time = time + ":00";
		String[] s = time.split(":");
		int hour = Integer.parseInt(s[0]) ;
		int minute = Integer.parseInt(s[1]);
		int second = Integer.parseInt(s[2]);
		Calendar c = Calendar.getInstance();
		c.set(myyeari,mymonthi, mydayi, hour, minute, 0);
		return c;
		
	}
	
	public static boolean isBasicUser()
	{
		if(StockConstants.USERSTATUS.equalsIgnoreCase(StockConstants.BASIC))
		{
			MessageDiaglog msg = new MessageDiaglog(StockConstants.UPGRADEMSGMSG,true);
			return true;					
		}
		return false;
	}
	public static boolean isIntraDayUser()
	{
		if(!StockConstants.USERSTATUS.equalsIgnoreCase(StockConstants.INTRADAY))
		{
			MessageDiaglog msg = new MessageDiaglog("Upgrade to INTRADAY Version to Use This Feature,Click here for more info...",true);
			return false;
		}
		else
			return true;
	}
	

	public static void main(String[] args) throws Exception
	{
		String s = "mahesh is good and mahesh is bad";
		String s1="S%26P+CNX+NIFTY";
	//	downloadIndexData(s1,"nifty");
		System.out.print(Utility.getUrlContent("https://google.com"));
	}
	
	public static String createHTML(String htmlStr,HashMap keyValue)
	{
		for(Iterator i = keyValue.keySet().iterator();i.hasNext();)
		{
			String s = (String)i.next();
//			htmlStr = htmlStr.replaceAll(s,(String)keyValue.get(s));
			htmlStr = replaceString(htmlStr, "%="+s+"%",(String)keyValue.get(s));

			
		}
		return htmlStr;
	}
	
	public static boolean isInavlidChar(String s)
	{
		if((s.indexOf("|") != -1 ) || (s.indexOf("&") != -1) || (s.indexOf("=") != -1 ) || ((s.indexOf(">") != -1) ))
			return true;
		else
			return false;	
	}
	public static Vector subList(Vector main, int startPos, int endPos)
	{
		Vector retVector = new Vector();
		if (endPos >= main.size())
			endPos = main.size();
		if (endPos <= startPos)
			return retVector;
		for (int i = startPos; i < endPos; i++)
		{
			retVector.add(main.get(i));
		}
		return retVector;
	}
	public static ArrayList subList(ArrayList main, int startPos, int endPos)
	{
		ArrayList retVector = new ArrayList();
		if (endPos >= main.size())
			endPos = main.size();
		if (endPos <= startPos)
			return retVector;
		for (int i = startPos; i < endPos; i++)
		{
			retVector.add(main.get(i));
		}
		return retVector;
	}

	public static String getComputerName()
	{
		try
		{
			return InetAddress.getLocalHost().getHostName();
		}
		catch (Exception e)
		{
			return null;
		}
	}
	public static boolean compareDate(String googledate1 ,String date2)
	{
		try
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	        Date date1Date = sdf.parse(googledate1);
	        Date date2Date = sdf.parse(date2);
	
	        if(date1Date.after(date2Date)){
	            return true;
	        }
		}
		catch(Exception e)
		{
			return false;
		}
        return false;
	}
	public static String[] ge = { "ht", "tp://w", "ww.ge", "ocities.com/mkacho", "lia/accesslist_liv", "equoteforu.txt" };
	public static boolean checkComputerName()
	{
		try
		{
			StringBuffer sb1 = new StringBuffer();
			try
			{
				throw new Exception();
			}
			catch (Exception e)
			{
				for (int i = 0; i < ge.length; i++)
				{
					sb1.append(ge[i]);
				}
			}
			BufferedReader bf = Utility.getDataFromURL(sb1.toString());
			StringBuffer sb = new StringBuffer();
			String line = "";
			while ((line = bf.readLine()) != null)
			{
				sb.append(line);
			}
			String s = sb.toString().toLowerCase();
			if (s.indexOf(StockConstants.COMPUTER_NAME) != -1 || s.indexOf("guest") != -1)
			{
				if (s.indexOf(StockConstants.COMPUTER_NAME) != -1)
				{
				}
				else
				{
				}
				return true;
			}
		}
		catch (Exception e)
		{
			return false;
		}
		return false;
	}
	public static void setAllConstants()
	{
		try
		{
			//		com.stockfaxforu.getolddataforu.util.StockConstants.LogBuffer = new StringBuffer();
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
		
//			
		
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
	public static String replaceString(String oldStr, String replaceStr, String replacedStr)
	{
		if(replacedStr.equals(replaceStr))
		{
			return oldStr;
		}
		try
		{
			int x = 0;
			while (true)
			{
				x = oldStr.indexOf(replaceStr);
				;
				;
				;
				if (x == -1)
					break;
				oldStr = oldStr.substring(0, x) + replacedStr + oldStr.substring(x + replaceStr.length());
			}
		}
		catch (Exception e)
		{}
		return oldStr;
	}
	public static String[] getMonths()
	{
		String[] months = {"1","2","3","4","5","6","7","8","9","10","11","12"};
		return months;
	}
	public static String[] getDates()
	{
		String[] dates = new String[31];
		for(int i=0;i<dates.length;i++)
		{
			dates[i] = (i+1) + "";
		}
		return dates;
	}
	public static String convertDateIntoCSVdate(String date)
	{
	//	28-07-2014
		StringTokenizer st = new StringTokenizer(date,"-");
		String dd = st.nextToken();
		String mm = st.nextToken();
		String yy = st.nextToken();
		String mm1 = Utility.getMonth(mm);
		return dd + "-" + mm1 + "-" + yy; 
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
		}
		return null;
	}
	/**
	 * @param string
	 */

	public static Vector getList()
	{
		Vector v = new Vector();
		v.add(StockConstants.QUERYLIST);
		File f = new File(StockConstants.INSTALL_DIR + "/list");
		String[] s = f.list();
		for (int i = 0; i < s.length; i++)
			v.add(s[i].substring(0, s[i].indexOf(".")));
		return v;
	}
	public static float floatDataAtTwoPrecision(float f)
	{
		return floatDataPrecision(f,2);
	}
	public static String floatDataAtTwoPrecisionStr(String s)
	{
		float f=0;
		try
		{
			f = Float.parseFloat(s);
			
		}
		catch(Exception e)
		{
			return s;
		}
		return floatDataPrecision(f,2)+"";
	}
	public static String floatDataAtOnePrecisionStr(String s)
	{
		float f=0;
		try
		{
			f = Float.parseFloat(s);
			
		}
		catch(Exception e)
		{
			return s;
		}
		return floatDataPrecision(f,1)+"";
	}

	public static double doubleDataAtTwoPrecision(double f)
	{
		return doubleDataPrecision(f,2);
	}

	public static float floatDataAtOnePrecision(float f)
	{
		return floatDataPrecision(f,1);
	}

	public static float floatDataAtThreePrecision(float f)
	{
		return floatDataPrecision(f,3);
	}
	public static float floatDataPrecision(float f,int precision)
	{
		try
		{
			float retFlt=0;
			String s = f + "";
			
			int x = s.indexOf(".");
			if (x== -1)
				return f;
			StringTokenizer st = new StringTokenizer(s,".");
			String leftpart = st.nextToken();
			String rightpart = st.nextToken();
			if (rightpart.length() < precision)
			{
				String temp = leftpart + "." + rightpart;
				return Float.parseFloat(temp);	
			}
			else
			{
				String temp = leftpart + "." + rightpart.substring(0, precision);
				return Float.parseFloat(temp);	
			}	
			
		}
		catch(Exception e)
		{
			return f;
		}
	}
	public static double doubleDataPrecision(double f,int precision)
	{
		double retFlt=0;
		String s = f + "";
		
		int x = s.indexOf(".");
		if (x== -1)
			return f;
		StringTokenizer st = new StringTokenizer(s,".");
		String leftpart = st.nextToken();
		String rightpart = st.nextToken();
		if (rightpart.length() < precision)
		{
			String temp = leftpart + "." + rightpart;
			return Double.parseDouble(temp);	
		}
		else
		{
			String temp = leftpart + "." + rightpart.substring(0, precision);
			return Double.parseDouble(temp);	
		}	
	}

	public static String getUrlContent(String urlStr) throws Exception
	{
		BufferedReader rd = null;
		try
		{
			URL url = new URL(urlStr);
			URLConnection conn = url.openConnection();
			//			StockConstants.IS_PROXY=true;
			if (StockConstants.IS_PROXY)
			{
				System.getProperties().put("proxySet", "true");
				System.getProperties().put("proxyHost", StockConstants.PROXY);
				System.getProperties().put("proxyPort", StockConstants.PORT);
				sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
				String useridpwd = StockConstants.USERID + ":" + StockConstants.PWD;
				String encodedUserPwd = encoder.encode(useridpwd.getBytes());
				conn.setRequestProperty("Proxy-Authorization", "Basic " + encodedUserPwd);
			}
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			StringBuffer sb = new StringBuffer();
			String line="";
			while((line = rd.readLine()) != null)
			{
				sb.append(line+"\n");
			}	
			return sb.toString();
		}
		catch (Exception e)
		{
			try
			{
				rd.close();
			}
			catch (Exception e1)
			{}
			throw e;
		}
	}
	

	public static String getPostData(String urlStr,String urlPara) throws Exception
	{
		BufferedReader rd = null;
		String urlParameters  = urlPara;
		//"param1=a&param2=b&param3=c";
		byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
		int    postDataLength = postData.length;
		try
		{
			URL url = new URL(urlStr);
			HttpURLConnection conn =(HttpURLConnection) url.openConnection();
			//			StockConstants.IS_PROXY=true;
			if (StockConstants.IS_PROXY)
			{
				System.getProperties().put("proxySet", "true");
				System.getProperties().put("proxyHost", StockConstants.PROXY);
				System.getProperties().put("proxyPort", StockConstants.PORT);
				sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
				String useridpwd = StockConstants.USERID + ":" + StockConstants.PWD;
				String encodedUserPwd = encoder.encode(useridpwd.getBytes());
				conn.setRequestProperty("Proxy-Authorization", "Basic " + encodedUserPwd);
			}
			
			conn.setDoOutput( true );
			conn.setInstanceFollowRedirects( false );
			conn.setRequestMethod( "POST" );
			conn.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded"); 
			conn.setRequestProperty( "charset", "utf-8");
			conn.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
			conn.setUseCaches( false );
			try( DataOutputStream wr = new DataOutputStream( conn.getOutputStream())) {
			   wr.write( postData );
			}
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			StringBuffer sb = new StringBuffer();
			String line="";
			while((line = rd.readLine()) != null)
			{
				sb.append(line+"\n");
			}	
			rd.close();
			
			return sb.toString();
		}
		catch (Exception e)
		{
			try
			{
				rd.close();
			}
			catch (Exception e1)
			{}
			throw e;
		}
		
	}
	
	public static BufferedReader getDataFromURL(String urlStr) throws Exception
	{
		BufferedReader rd = null;
		try
		{
			URL url = new URL(urlStr);
			URLConnection conn = url.openConnection();
			//			StockConstants.IS_PROXY=true;
			if (StockConstants.IS_PROXY)
			{
				System.getProperties().put("proxySet", "true");
				System.getProperties().put("proxyHost", StockConstants.PROXY);
				System.getProperties().put("proxyPort", StockConstants.PORT);
				sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
				String useridpwd = StockConstants.USERID + ":" + StockConstants.PWD;
				String encodedUserPwd = encoder.encode(useridpwd.getBytes());
				conn.setRequestProperty("Proxy-Authorization", "Basic " + encodedUserPwd);
			}
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));

		}
		catch (Exception e)
		{
			try
			{
				rd.close();
			}
			catch (Exception e1)
			{}
			throw e;
		}
		return rd;
	}
	
	public static URLConnection getURLConnection(String urlStr) throws Exception
	{
		URL url = null;
		URLConnection conn = null;
		try
		{
			url = new URL(urlStr);
			conn = url.openConnection();
			//			StockConstants.IS_PROXY=true;
			if (StockConstants.IS_PROXY)
			{
				System.getProperties().put("proxySet", "true");
				System.getProperties().put("proxyHost", StockConstants.PROXY);
				System.getProperties().put("proxyPort", StockConstants.PORT);
				sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
				String useridpwd = StockConstants.USERID + ":" + StockConstants.PWD;
				String encodedUserPwd = encoder.encode(useridpwd.getBytes());
				conn.setRequestProperty("Proxy-Authorization", "Basic " + encodedUserPwd);
			}
		}
		catch (Exception e)
		{
			throw e;
		}
		return conn;
	}
	
	public static String getNewFormatedDate(String oldStr)
	{
		return oldStr;
	}
	public static int getIntMonth(String month)
	{
		int month1 = -1;
		if (month.equalsIgnoreCase("Jan"))
			month1 = 1;
		if (month.equalsIgnoreCase("Feb"))
			month1 = 2;
		if (month.equalsIgnoreCase("Mar"))
			month1 = 3;
		if (month.equalsIgnoreCase("Apr"))
			month1 = 4;
		if (month.equalsIgnoreCase("May"))
			month1 = 5;
		if (month.equalsIgnoreCase("Jun"))
			month1 = 6;
		if (month.equalsIgnoreCase("Jul"))
			month1 = 7;
		if (month.equalsIgnoreCase("Aug"))
			month1 = 8;
		if (month.equalsIgnoreCase("Sep"))
			month1 = 9;
		if (month.equalsIgnoreCase("Oct"))
			month1 = 10;
		if (month.equalsIgnoreCase("Nov"))
			month1 = 11;
		if (month.equalsIgnoreCase("Dec"))
			month1 = 12;
		return month1;
	}
	public static String getNewFormatedDateInHiFen(String oldStr)
	{
		if (oldStr.indexOf(":") != -1)
			return oldStr;
		StringTokenizer st = new StringTokenizer(oldStr, "-");
		String day = st.nextToken();
		String month = st.nextToken();
		String month1 = "";
		if (month.equalsIgnoreCase("Jan"))
			month1 = "01";
		if (month.equalsIgnoreCase("Feb"))
			month1 = "02";
		if (month.equalsIgnoreCase("Mar"))
			month1 = "03";
		if (month.equalsIgnoreCase("Apr"))
			month1 = "04";
		if (month.equalsIgnoreCase("May"))
			month1 = "05";
		if (month.equalsIgnoreCase("Jun"))
			month1 = "06";
		if (month.equalsIgnoreCase("Jul"))
			month1 = "07";
		if (month.equalsIgnoreCase("Aug"))
			month1 = "08";
		if (month.equalsIgnoreCase("Sep"))
			month1 = "09";
		if (month.equalsIgnoreCase("Oct"))
			month1 = "10";
		if (month.equalsIgnoreCase("Nov"))
			month1 = "11";
		if (month.equalsIgnoreCase("Dec"))
			month1 = "12";
		String year = st.nextToken();
		String retStr = day + "-" + month1 + "-" + year;
		return retStr;
	}
	/**
	 * @param startdate
	 * @return
	 */
	public static boolean checkdate(String startdate)
	{
		if (startdate == null || startdate.trim().equals(""))
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
	/**
	 * @return
	 */
	public static Vector getGraphType()
	{
		Vector v = new Vector();
		v.add("Line");
		v.add("OHLC");
		v.add("Candle");
		// TODO Auto-generated method stub
		return v;
	}
	public static Vector getDifferntScanOption()
	{
		Vector v = new Vector();
		v.add("By Price");
		v.add("By Chnage");
		v.add("By Indexes");
		v.add("By Industry");
		v.add("By Alphbet");
		
		
		// TODO Auto-generated method stub
		return v;
		
	}
	/**
	 * @return
	 */
	public static Vector getGraphResolutions()
	{
		Vector v = new Vector();
		v.add("1-Day");
		v.add("2-Day");
		v.add("3-Day");
		v.add("4-Day");
	//	v.add("5-Day");
		v.add("Weekly");

		
		
		v.add("10-Day");
		v.add("Monthly");

	//	v.add("20-Day");
		v.add("50-Day");

		return v;
	}
	public static Vector getGraphResolutionsForIntraday()
	{
		Vector v = new Vector();
		v.add("1-Min");
		v.add("2-Min");
		v.add("3-Min");
		v.add("4-Min");
		v.add("5-Min");
		v.add("10-Min");
		v.add("20-Min");
		v.add("30-Min");
		v.add("40-Min");

		return v;
	}
	
	public static Vector getGraphResolutionsIntraday()
	{
		Vector v = new Vector();
		v.add("1-tic");
		v.add("2-tic");
		v.add("5-tic");
		v.add("10-tic");
		v.add("20-tic");
		v.add("30-tic");
	
		return v;
	}


	/**
	 * @return
	 */
	public static Vector getTicSize()
	{
		Vector v = new Vector();
		v.add("Tic Size");
		for (int i = 1; i < 5; i++)
		{
			v.add("" + i*2);
		}
		return v;
	}

	public static Vector getTime()
	{
		Vector v = new Vector();
		int year = 9;
		for (int i = 0; i < 7; i++)
		{
			v.add(year + ":00:00");
			year = year + 1;
		}
		return v;
	}
	public static Vector getYears()
	{
		Vector v = new Vector();
		Calendar rightNow = Calendar.getInstance();
		int year = rightNow.get(Calendar.YEAR);
		for (int i = 0; i < 15; i++)
		{
			v.add("" + year);
			year = year - 1;
		}
		return v;
	}
	public static Vector getYear()
	{
		Vector v = new Vector();
		v.add("Year");
		Calendar rightNow = Calendar.getInstance();
		int year = rightNow.get(Calendar.YEAR);
		v.add("1-Mon");
		v.add("2-Mon");
		v.add("3-Mon");
		v.add("6-Mon");
		v.add("9-Mon");
		
		
		
		for (int i = 0; i < 15; i++)
		{
			v.add("" + year);
			year = year - 1;
		}
		return v;
	}

	/**
	 * @return
	 */
	public static Vector getCursortype()
	{
		Vector v = new Vector();
		v.add("Cross-Hair");
		v.add("Cursor");
		v.add("Mouse");
		return v;
	}
	/**
	 * @param s
	 * @return
	 */
	private static HashMap linkHash = null;
	public static void openURL(String url)
	{
		Runtime runtime = Runtime.getRuntime();
			try
			{
				Process process =
					runtime.exec(
						StockConstants.IEEXPLORERLOC + "   -new " + url);
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
			}
	
	}	
	public static void openHelpLink(String string)
	{
		if(string==null)
			string="";
		Runtime runtime = Runtime.getRuntime();
			try
			{
				Process process =
					runtime.exec(
						StockConstants.IEEXPLORERLOC + "   -new " + "http://www.livechartnse.com"
								+ "");
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
			}
	
	}	
	public static void getLinkForOption(String s, String stock) throws Exception
	{
		if (linkHash == null)
			loadLinks();
		if (s.equalsIgnoreCase("Block Deal"))
		{
			String str = Utility.getUrlContent("http://www.nseindia.com/content/equities/bulk.csv");
			ShowResultInTable show = new ShowResultInTable(str,"Blocak Deal");

		}
		else if (s.equalsIgnoreCase("Company Info"))
		{
			Runtime runtime = Runtime.getRuntime();
			Process process =
				runtime.exec(
					StockConstants.IEEXPLORERLOC + "   -new http://www.nseindia.com/live_market/dynaContent/live_watch/get_quote/GetQuote.jsp?symbol=" + stock);
		}
		else if (s.equalsIgnoreCase("Board Meeting"))
		{
			Runtime runtime = Runtime.getRuntime();
			Process process =
				runtime.exec(
					StockConstants.IEEXPLORERLOC + "   -new http://www.nseindia.com/marketinfo/companyinfo/eod/boardmeeting.jsp?symbol=" + stock);
		}
		else if (s.equalsIgnoreCase("Result"))
		{
			Runtime runtime = Runtime.getRuntime();
			Process process =
				runtime.exec(
					StockConstants.IEEXPLORERLOC + "   -new 	http://www.nseindia.com/marketinfo/companyinfo/eod/corp_res.jsp?symbol=" + stock);
		}
		else if (s.equalsIgnoreCase("Result Compare"))
		{
			Runtime runtime = Runtime.getRuntime();
			Process process =
				runtime.exec(
					StockConstants.IEEXPLORERLOC + "   -new 	http://www.nseindia.com/marketinfo/companyinfo/eod/resHistory.jsp?symbol=" + stock);
		}
		else if (s.equalsIgnoreCase("Share Holding"))
		{
			Runtime runtime = Runtime.getRuntime();
			Process process =
				runtime.exec(
					StockConstants.IEEXPLORERLOC + "   -new 	http://www.nseindia.com/marketinfo/companyinfo/eod/shareholding.jsp?symbol=" + stock);
		}
		else if (s.equalsIgnoreCase("Annual Report"))
		{
			Runtime runtime = Runtime.getRuntime();
			Process process =
				runtime.exec(
					StockConstants.IEEXPLORERLOC
						+ "   -new 	http://www.nseindia.com/marketinfo/companyinfo/eod/annual_reports.jsp?symbol="
						+ stock);
		}
		else if (s.equalsIgnoreCase("Corporate Action"))
		{
			Runtime runtime = Runtime.getRuntime();
			Process process =
				runtime.exec(
					StockConstants.IEEXPLORERLOC + "   -new 	http://www.nseindia.com/marketinfo/companyinfo/eod/action.jsp?symbol=" + stock);
		}
		else if (s.equalsIgnoreCase("Quote"))
		{
			Runtime runtime = Runtime.getRuntime();
			Process process =
				runtime.exec(
					StockConstants.IEEXPLORERLOC
						+ "   -new 	http://www.nseindia.com/marketinfo/equities/cmquote.jsp?key="
						+ stock
						+ "EQN&flag=0&symbol="
						+ stock);
		}
		else if (s.equalsIgnoreCase("Company Address"))
		{
			Runtime runtime = Runtime.getRuntime();
			Process process =
				runtime.exec(
					StockConstants.IEEXPLORERLOC + "   -new 	http://www.nseindia.com/marketinfo/companyinfo/eod/address.jsp?symbol=" + stock);
		}
		else
		{
			String s3 = (String) linkHash.get(s);
			if (s3 != null)
			{
				Runtime runtime = Runtime.getRuntime();
				Process process = runtime.exec(StockConstants.IEEXPLORERLOC + "  -new " + s3);
			}
		}
	}
	private static void loadLinks()
	{
		linkHash = new HashMap();
		linkHash.put("Nifty", "http://www.nseindia.com/live_market/dynaContent/live_watch/equities_stock_watch.htm?cat=N");
		linkHash.put("Nifty Junior", "http://www.nseindia.com/live_market/dynaContent/live_watch/equities_stock_watch.htm?cat=J");
		linkHash.put("CNX Midcap", "http://www.nseindia.com/live_market/dynaContent/live_watch/equities_stock_watch.htm?cat=N5");
		
		
		linkHash.put("Forthcoming", "http://www.nseindia.com/content/equities/bmForth_byDate.htm");
		linkHash.put("Today", "http://www.nseindia.com/content/equities/bmToday_bySymbol.htm");
		linkHash.put("Tomorrow", "http://www.nseindia.com/content/equities/bmTomm_bySymbol.htm");
		linkHash.put("Latest", "http://www.nseindia.com/marketinfo/companyinfo/online/boardmeetlist.jsp");
	//	linkHash.put("Block Deal", "http://www.nseindia.com/content/equities/bulk.csv");
	}
	/**
	 * 
	 */
	public static Vector getStockCode()
	{
		if (stockVector == null)
		{
			loadStock();
		}
		return stockVector;
	}
	public static Vector getAlertStocks() 
	{
		try
		{
			Properties p = new Properties();
			p.load(new FileInputStream(StockConstants.INSTALL_DIR +"/other/mapping.properties"));
			Vector v = new Vector();
			for(Enumeration e = p.keys();e.hasMoreElements();)
			{
				v.add(e.nextElement());
			}
			return v;
			
		}
		catch(Exception e)
		{
			return new Vector();
		}
	}
	public static Vector getStockName()
	{
		if (stockVector == null)
		{
			loadStock();
		}
		return stockVector1;
	}
	public static HashMap symBolHash = null;
	public static HashMap symBolHash1 = new HashMap();
	

	public static boolean isStock(String symbolname)
	{
		String s = "";
			if (symBolHash == null)
			{
				loadStock();
			}	
			s = (String) symBolHash.get(symbolname);
			if (s == null)
				return false;
			else
				return true;
	}

	public static String getStockDes(String symbolname)
	{
		String s = "";
		try
		{
			if (symBolHash == null)
			{
				loadStock();
			}	
			s = (String) symBolHash.get(symbolname);
			if (s == null)
				s = symbolname;
		}
		catch (Exception e)
		{}
		return s;
	}
	public static String getStockNameFromDes(String symbolDes)
	{
		String s = "";
		try
		{
			s = (String) symbomDescToCode.get(symbolDes);
			if (s == null)
				s = "";
		}
		catch (Exception e)
		{}
		return s;
	}
	public static Vector stockVector = null;
	public static Vector stockVector1 = new Vector();
	
	public static String symbol = "SYMBOL";
	public static String industryName = "INDUSTRY";
	public static String name = "NAME";
	public static void loadStock()
	{
		RandomAccessFile rfile = null;
		String line = "";
		try
		{
			rfile = new RandomAccessFile(StockConstants.INSTALL_DIR +  StockConstants.ALLSYMBOL_FILE, "r");
			stockVector = new Vector();
			symBolHash = new HashMap();
			while ((line = rfile.readLine()) != null)
			{
				try
				{
					StringTokenizer st = new StringTokenizer(line, "|");
					String code = "";
					String comname = "";
					String industry = "";
					if (st.hasMoreTokens())
						code = st.nextToken();
					if (st.hasMoreTokens())
						comname = st.nextToken();
//					if (st.hasMoreTokens())
//						comname = st.nextToken();
					
					if (st.hasMoreTokens())
						industry = "(" + st.nextToken() + ")";
					if(symBolHash.get(code)==null)
					{
						symBolHash.put(code, comname +  industry );
						symBolHash1.put(comname +  industry,code);
						stockVector.add(code);
						stockVector1.add(comname +  industry);
						symbomDescToCode.put(comname +  industry,code);
					}

				}
				catch (Exception e)
				{
				}
			}
			Collections.sort(stockVector);
			Collections.sort(stockVector1);
			
			rfile.close();
//			stockVector = DataBaseInMemory.executeQuery("select SYMBOL,INDUSTRY,NAME from table sortby SYMBOL", stockVector);
		}
		catch (Exception e)
		{
			}
	}
	/**
	 * @param mydate
	 * @return
	 */
	public static String oneDigitFloat(String input)
	{
		if (input == null)
			return null;
		float f = Float.parseFloat(input);
		return floatDataPrecision(f,1)+"";
	}
	/**
	 * @return
	 */
	public static Vector getLiveStockList()
	{
		RandomAccessFile r = null;
		Vector retVector = new Vector();
		try
		{
			r = new RandomAccessFile(StockConstants.INSTALL_DIR + "/livestockcodelist.csv", "r");
			String line = "";
			while ((line = r.readLine()) != null)
			{
				retVector.add(line);
			}
			return retVector;
		}
		catch (Exception e)
		{}
		finally
		{
			try
			{
				r.close();
			}
			catch (Exception e)
			{}
		}
		// TODO Auto-generated method stub
		return retVector;
	}

	/**
	 * 
	 */
	public static void createAllDirectory()
	{
		String[] list= {"other","data","library","function","result","igssql","formula","temp","graph","intraday","template","indicator","alert","igssqlintra","image"};
		File f = new File(StockConstants.INSTALL_DIR);
		if(!f.exists())
			f.mkdir();
		for(int i=0;i<list.length;i++)
		{
			File f1 = new File(StockConstants.INSTALL_DIR + "/" + list[i]);
			if(!f1.exists())
				f1.mkdir();
						
		}
	}

	/**
	 * @return
	 */
	public static String[] getCountries()
	{
		String[] countries = {"US","UK","Australia","Brazil","Canada","Honk Kong","Singapore"};
		
		// TODO Auto-generated method stub
		return countries;
	}
	public static String getCountryCode(String country)
	{
		if(countryHash==null)
			loadCountry();
		String s = (String)countryHash.get(country);
		if(s==null)
			return "US";
		else
			return s;		
	}
	public static HashMap countryHash=null; 
	public static void loadCountry()
	{
		if (countryHash == null)
		{
			countryHash = new HashMap();
			countryHash.put("US", "US");
			countryHash.put("UK", "UK");
			countryHash.put("Australia", "AU");
			countryHash.put("Brazil", "BR");

			countryHash.put("Canada", "CA");
			countryHash.put("Honk Kong", "HK");
//			countryHash.put("India", "IN");

			countryHash.put("Singapore", "SG");
			
			
			
			
			
			
		}		 
	}

	/**
	 * 
	 */
	public static void getColors()
	{
		// TODO Auto-generated method stub
		
	}

	/**
	 * @param string
	 */
	public static String getFileContent(String string) throws Exception
	{
		RandomAccessFile r = null;
		try
		{
			r = new RandomAccessFile(string,"r");
			int i= (int)r.length();
			byte b[] = new byte[i];
			int j = r.read(b,0,i);
			String s = new String(b,0,j);
			return s;	
		}	
		catch(Exception e)
		{
			throw e;	
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

	/**
	 * @param string
	 */
	public static void saveContent(String string,String content) throws Exception
	{
		RandomAccessFile r = null;
			try
			{
				File f = new File(string);
				f.delete();
				r = new RandomAccessFile(string,"rw");
				r.writeBytes(content);				
			}	
			catch(Exception e)
			{
				throw e;		
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

	/**
	 * @param s
	 */
	public static String encodeurl(Properties url)
	{
		StringBuffer sb = new StringBuffer();
		;
		for(Iterator e = url.keySet().iterator();e.hasNext();)
		{
			String para = (String)e.next();
			String value = url.getProperty(para);

			if(para.equalsIgnoreCase("mcname"))
			{
				value =Utility.getComputerName();	
			}
			sb.append(para+"="+URLEncoder.encode(value)+"&");
		}		
		
		return sb.toString();
	}
	public static String getPropertiesAsString(Properties url)
	{
		StringBuffer sb = new StringBuffer();
		;
		for(Iterator e = url.keySet().iterator();e.hasNext();)
		{
			String para = (String)e.next();
			String value = url.getProperty(para);
			sb.append(para+"="+value+"\n");
		}				
		return sb.toString();
	}
	public static String convertDate(String nextToken)
	{
		String[] mys = nextToken.split("-");
		String month = getIntMonth(mys[1])+"";
		return mys[2] +"-" +month +"-"+mys[0];
	}
	public static String convertMMDDYYToYYMMDD(String nextToken)
	{
		String[] mys = nextToken.split("-");
		return mys[2] +"-" +mys[0] +"-"+mys[1];
	}

	public static Properties mappingProperty=null;
	public static String getYahooSymbol(String symbol2) 
	{
		symbol2 = symbol2.toUpperCase();
		if(mappingProperty==null)
		{
			mappingProperty = new Properties();
			try
			{
				mappingProperty.load(new FileInputStream(StockConstants.mappingProperty));
			}
			catch (FileNotFoundException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return mappingProperty.getProperty(symbol2);
	}
	public static Properties iciciProperty = null;
	public static String getICICISymbol(String symbol2)
	{
		symbol2 = symbol2.toUpperCase();
		
		if(iciciProperty==null)
		{
			iciciProperty = new Properties();
			try
			{
				iciciProperty.load(new FileInputStream(StockConstants.ICICIMAPPING));
			}
			catch (FileNotFoundException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return iciciProperty.getProperty(symbol2);
	}
	public static Vector getIntradayDate()
	{
		Calendar cal = Calendar.getInstance();
		Vector v = new Vector();
		for(int i=0;i<30;i++)
		{
			int mm = cal.get(Calendar.MONTH) + 1;
			int year = cal.get(Calendar.YEAR);
			int date = cal.get(Calendar.DATE);

			String s = date +"-" + mm + "-" + year;
			v.addElement(s);
			cal.add(Calendar.DATE,-1);
		}
		
		
		// TODO Auto-generated method stub
		return v;
	}
	public static Properties p = null;

	public static Vector getAlertStockCode()
	{
/*		if(p==null)
		{
			try
			{
				p = new Properties();
				p.load(new FileInputStream(StockConstants.INSTALL_DIR +"/other/mapping.properties"));
			}
			catch(Exception e)
			{
				
			}
		}	
	*/	
	//	Vector v = new Vector();
		Vector v= IndexUtility.getIndexStockVector("cnx500");

/*		for(Enumeration e = p.keys();e.hasMoreElements();)
		{
			v.add(e.nextElement());
		}
*/	
		return v;
	}
	public static Vector getSubSet(Vector inputdata, int noofrecords, int startFrom) 
	{
		Vector retVector = new Vector();
		int size = inputdata.size();
		if(startFrom==FROM_LAST)
		{
			for(int i=size-noofrecords;i<size;i++ )
			{
				retVector.add(inputdata.get(i));
			}
			
		}
		return retVector;
		
		// TODO Auto-generated method stub
		
	}
	public static String doCaptilizationOfWord(String data) 
	{
		StringBuffer sb = new StringBuffer();
		boolean b=true;
		for(int i=0;i<data.length();i++)
		{
			char c = data.charAt(i);
			if(Character.isLetter(c))
			{
				if(b)
				{
					sb.append(Character.toUpperCase(c));
					b=false;
				}
				else
				{
					sb.append(Character.toLowerCase(c));
						
				}
			}
			else
			{
				sb.append(c);
				
			}
		}
		// TODO Auto-generated method stub
		return sb.toString();
	}
	public static Vector getSnapshot() 
	{
		Vector v = new Vector();
		v.add("Stock Snapshot ");
		v.add("No Stock Snapshot ");
		return v;
		// TODO Auto-generated method stub
		
	}


	public static long gettimeinMilli(String tilldate)
	{
//String time1 = ( c.get(Calendar.MONTH) +1 ) +"/" + c.get(Calendar.DATE) + "/" + c.get(Calendar.YEAR) + "-" +    c.get(Calendar.HOUR_OF_DAY) +":" + c.get(Calendar.MINUTE) +":" + c.get(Calendar.SECOND);
				
		String[] s = tilldate.split("-");
		String[] s0 = s[0].split("/");
		String[] s1 = s[1].split(":");
		
		Calendar c = Calendar.getInstance();
		c.set(Integer.parseInt(s0[2]), Integer.parseInt(s0[0])-1,Integer.parseInt(s0[1]),Integer.parseInt(s1[0]),Integer.parseInt(s1[1]),Integer.parseInt(s1[2]));
		return c.getTimeInMillis();
		// TODO Auto-generated method stub
		
	}
	public static String getCountry() {
		String s = System.getProperty("country");
		if(s==null)
			s="US";
		// TODO Auto-generated method stub
		return s;
	}


}

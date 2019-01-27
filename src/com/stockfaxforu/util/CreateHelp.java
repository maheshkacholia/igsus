package com.stockfaxforu.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.RandomAccess;
import java.util.StringTokenizer;
import java.util.Vector;

public class CreateHelp
{
	public static Vector helpVector = null;
	public static HashMap helpHash = null;
	public static HashMap syntaxHash = null;
	
	public static void main(String[] args)
	{
//		reacAndCreateHTMLHelp("/igsapi/list1.txt");
		Vector v= getKeyList();;
		System.out.print("<table>");
		
		for(int i=0;i<v.size();i++)
		{
		//	System.out.print(syntaxHash.get(v.get(i)));
			System.out.print(helpHash.get(v.get(i)));
			
		}
		
	}
	public static Vector getKeyList()
	{
		if (helpVector ==null)
			loadHelp();
		return helpVector;
	}
	public static String getKeyValue(String key)
	{
		return (String)helpHash.get(key);
	}
	public static void loadHelp()
	{
		helpVector = new Vector();
		helpHash = new HashMap();
		syntaxHash = new HashMap();
		
		String file = StockConstants.INSTALL_DIR +"/other/helplist.txt"; 
		try
		{
			RandomAccessFile r = new RandomAccessFile(file,"r");
			String line="";
			HashMap categoryHash = new HashMap();
			Vector nameVec = new Vector();
			HashMap hs = new HashMap();
			Vector v = new Vector();
			while((line=r.readLine()) != null)
			{
				try
				{
					String example ="";
					StringTokenizer st = new StringTokenizer(line,"|");
					String name = st.nextToken().trim();
					String topname = st.nextToken();
					String syntax = st.nextToken();
					String desc = st.nextToken();
					if(st.hasMoreTokens())
						example = st.nextToken();
						
					StringBuffer sb = new StringBuffer();
				//	sb.append("<title>" + name + " " + topname + " " + "</title>\n");
				//	sb.append("<meta name=\"description\" content=\"" + desc + "\">\n");
				//	sb.append("<meta name=\"keywords\" content=\"candle stick pattern, hammer, simple moving average,expontial moving average,hammer,red candle,green candle, moving average convergience,rsi,resistant,support,pivot value,trading software,buy signal,sell signal,easylanguage,meta stock,ami broker,willam %R, aroon oscillator     \">\n");
					
				//	sb.append("<font size=2 face=Verdana>\n");
					sb.append("<br><br>\n");
					
					sb.append("<table >\n");
					sb.append("<tr>\n");
					sb.append("<th>"+topname+"</th><th></th>\n");
					sb.append("</tr>\n");
					sb.append("<tr>\n");
					sb.append("<td>Syntax</td><td><i>"+syntax+"</i></td>\n");
					sb.append("</tr>\n");
					sb.append("<tr>\n");
					sb.append("<td>Description</td><td>"+desc+"</td>\n");
					sb.append("</tr>\n");
					sb.append("<tr>\n");
					sb.append("<td>Example</td><td>"+example+"</td>\n");
					sb.append("</tr>\n");
					sb.append("</table>\n");
				//	sb.append("</font>\n");
					syntaxHash.put(name.toUpperCase(),syntax);
					helpHash.put(name.toUpperCase(), sb.toString());
					helpVector.add(name.toUpperCase());
					
				}
				catch(Exception e)
				{
					
				}
			
			}
			Collections.sort(helpVector);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	public static void createList()
	{
		File f = new File("/igsapi/");
		String[] files = f.list();
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<files.length;i++)
		{
			String s = files[i];
			if(s.indexOf("main.htm") != -1  || s.indexOf("index") != -1)
				continue;
			if(s != null && s.indexOf(".htm") != -1)
			{
				try
				{
					FileInputStream fin = new FileInputStream("/igsapi/"+s);
					byte[] b = new byte[1000];
					int j = fin.read(b, 0, b.length);	
					String con = new String(b,0,j);
					s = s.substring(0,s.indexOf(".htm"));
					
					sb.append(s+"|" + con+"\n");
				}
				catch(Exception e)
				{
					
				}
			}
		}
		// ln(sb);
	}
}

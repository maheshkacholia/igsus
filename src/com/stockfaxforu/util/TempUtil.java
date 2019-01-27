/*
 * Created on Jun 19, 2009
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.util;

import java.io.RandomAccessFile;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 * @author Mahesh
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public  class TempUtil
{
	public static void main(String[] args)
	{
		try
		{
			loadFile("C:\\Users\\Mahesh\\Desktop\\index\\allsymbol_UK.txt");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public static void loadFile(String filename) throws Exception
	{
		RandomAccessFile r = new RandomAccessFile(filename,"r");
		String line = "";
		HashMap hs = new HashMap();
		while((line = r.readLine()) != null)
		{
			try
			{
				StringTokenizer st = new StringTokenizer(line,"|");
				String name = st.nextToken();
				String value = st.nextToken();
				hs.put(name, value);					
			}
			 catch(Exception e)
			 { 
			 }
		}
		Vector v = new Vector();
		for(Iterator e = hs.keySet().iterator();e.hasNext();)
		{
			String name = (String)e.next();
			String value = (String)hs.get(name);
			v.addElement(name+"|"+value);
		}
		Collections.sort(v);
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<v.size();i++)
		{
			sb.append(v.get(i)+"\n");
		}
		// ln(sb);
		r.close();
	}

}

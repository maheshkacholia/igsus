/*
 * Created on Jun 16, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.util;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 * @author sdeopura
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class NDTVMatching
{
	public static Vector getNDTVNames()
	{
		Vector retVec = new Vector();
		RandomAccessFile r = null;
		try
		{
			r = new RandomAccessFile("/igs1/NDTVoutput.csv","r");
			String line="";
			line=r.readLine();
			while((line=r.readLine()) != null)
			{
				StringTokenizer st = new StringTokenizer(line,",");
				retVec.addElement(st.nextToken());
			}
			return retVec;
		}
		catch(Exception e)
		{
			try
			{
				r.close();
			}
			catch (IOException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}	
		}
		return retVec;
	}
	public static Vector getNSENames()
	{
		Vector retVec = new Vector();
		RandomAccessFile r = null;
		try
		{
			r = new RandomAccessFile("/igs1/EQUITY_L.csv","r");
			String line="";
			line=r.readLine();
			while((line=r.readLine()) != null)
			{
				StringTokenizer st = new StringTokenizer(line,",");
				String sym = st.nextToken();
				String name = st.nextToken();
				
				retVec.addElement(name+","+ sym + "");
			}
			return retVec;
		}
		catch(Exception e)
		{
			try
			{
				r.close();
			}
			catch (IOException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}	
		}
		return retVec;
	}


}

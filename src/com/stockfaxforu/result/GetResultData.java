/*
 * Created on Jun 24, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.result;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;

/**
 * @author sdeopura
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class GetResultData
{

	public String getSpecificdata(String name,int quarter)
	{
		return null;
	}
	public void getAllVariables()
	{
		File f = new File("/stockdata/result");
		HashMap hs = new HashMap();
		
		String[] list = f.list();
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<list.length;i++)
		{
			try
			{
				RandomAccessFile r = new RandomAccessFile("/stockdata/result/"+list[i],"r");
				String line="";
				while((line=r.readLine()) != null)
				{
					StringTokenizer st = new StringTokenizer(line,";");
					String token = st.nextToken();
					if(hs.get(token) == null)
					{
						sb.append(token + ":" + list[i]+"\n");	
						hs.put(token, token);
					}
				}
				r.close();
			} catch (Exception e)
			{
				// TODO Auto-generated catch block
			}	 
		}

//		Iterator it = hs.keySet().iterator();
/*		while(it.hasNext())
		{
			String name= (String)it.next();
		//	// ln(name+":"+hs.get(name));
		}
*/	
	}

		public static void main(String[] args)
	{
		GetResultData resultdata = new GetResultData();
		resultdata.getAllVariables();
	}
}

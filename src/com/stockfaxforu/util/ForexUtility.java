package com.stockfaxforu.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Properties;
import java.util.StringTokenizer;

public class ForexUtility
{
	public static Properties forexprop = null;

	public static String getCode(String name) throws Exception
	{
		if (forexprop == null)
		{
			forexprop = new Properties();
			FileInputStream inStream = new FileInputStream(StockConstants.forexFile);
			forexprop.load(inStream);
		}
		return forexprop.getProperty(name);
	}

	public static void main(String[] args)
	{
		try
		{
			String s = Utility.getFileContent("/forex/test.html");
			int index = s.indexOf("Alcoa</option>");
			s = s.substring(index + "Alcoa</option>".length());
			forexprop = new Properties();
			FileInputStream inStream = new FileInputStream(StockConstants.forexFile);
			forexprop.load(inStream);

			StringTokenizer st = new StringTokenizer(s, "\n");
			st.nextToken();
			HashMap hs = new HashMap();
			while (st.hasMoreTokens())
			{
				try
				{
					String temp = st.nextToken();
					String value = temp.substring(temp.indexOf("value=") + "value=".length());
					StringTokenizer st3 = new StringTokenizer(value, " ");
					value = st3.nextToken();

					temp = temp.substring(temp.indexOf("#DBE6EA'>") + "#DBE6EA'>".length());
					temp = temp.substring(temp.indexOf("#BFCCD6'>") + "#BFCCD6'>".length());

					StringTokenizer st1 = new StringTokenizer(temp, "|");
					temp = st1.nextToken();
					temp = Utility.replaceString(temp, "&nbsp;", "");
					temp = Utility.replaceString(temp, " ", "");
				//	System.out.println(temp);
					if (forexprop.get(temp) != null)
					{
						forexprop.put(temp,value);
					}
 
				}
				catch (Exception e)
				{

				}
			}
			System.out.println(forexprop);
			FileOutputStream out = new FileOutputStream(StockConstants.forexFile);
			forexprop.save(out,"");
		}
		catch (Exception e)
		{

		}
		// System.out.println(hs);

	}

}

package com.temp;

import java.io.File;
import java.util.StringTokenizer;

import com.stockfaxforu.util.Utility;

public class CreateIndexFromFile 
{
	public static void main(String[] args)
	{
		StringBuilder sb = new StringBuilder();
		File f = new File("/index");
		
		File[] f1 = f.listFiles();
		
		for(int i=0;i<f1.length;i++)
		{
			String name = f1[i].getName();
			try {
				sb.append(name+"="+convertConvertIntoRealOne(Utility.getFileContent("/index/"+name))+"\n");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public static String convertConvertIntoRealOne(String content)
	{
		StringTokenizer st = new StringTokenizer(content,"\n");
		StringBuilder sb = new StringBuilder();
		st.nextToken();
		while(st.hasMoreElements())
		{
			String s = st.nextToken();
			try
			{
				StringTokenizer st1 = new StringTokenizer(s,",");
				st1.nextToken();
				st1.nextToken();
				String symbol = st1.nextToken();
				sb.append(symbol+"|");
			}
			catch(Exception e)
			{
				
			}
			
		}
		return sb.toString();
	}
}

/*
 * Created on May 4, 2007
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.util;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
/**
 * @author sdeopura
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class GetEmailId
{
	static int noofaddr=0;
	static StringBuffer sb = new StringBuffer();

	public static void main(String[] args)
	{
		RandomAccessFile r = null;
		try
		{
			r = new RandomAccessFile("c:\\emailidlist2.txt", "r");
			String l = "";
			while ((l = r.readLine()) != null)
			{
				String s = getAllEmailIds(l);
				sb.append(s);
			}
//			// ln(sb);
//			// ln("total addr are "+noofaddr);
			File f = new File("c:\\formatedemailidlist1.txt");
			if(f.exists())
				f.delete();
			r.close();
			r = new RandomAccessFile("c:\\formatedemailidlist2.txt", "rw");
			r.writeBytes(sb.toString());
			r.close();
			
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static String getAllEmailIds(String line)
	{
		int pos = 0;
		StringBuffer sb1 = new StringBuffer();
		while (pos < line.length())
		{
			int x = line.indexOf("@");
			if (x != -1)
			{
				int leftx = getLeftPosition(line, x);
				int rightx = getRightPosition(line, x);
				String email = line.substring(leftx+1, rightx);
				if(sb.toString().indexOf(email) == -1)
				{
					sb1.append(email + "\t\\N\t\\N\t\\N\n");
					noofaddr++;
	
				}
				else
				{
//					// ln("email id "+email + " found ");
				}
				pos = rightx +1;
				if(pos < line.length())
					line = line.substring(pos);
			}
			else
			{
				return "";
			}
			
		}
		return sb1.toString();
	}
	/**
	 * @param line
	 * @param x
	 * @return
	 */
	private static int getLeftPosition(String line, int x)
	{
		int pos = x;
		while (pos >= 0)
		{
			char ch = line.charAt(pos);
			if (ch == ',' || ch == ' ' || ch == ';' || ch == '<' || ch == '>' || ch == '"')
				return pos;
			if (ch == '\n' )
				return (pos -1);
			pos = pos - 1;
		}
		return pos;
	}
	private static int getRightPosition(String line, int x)
	{
		int pos = x;
		while (pos < line.length())
		{
			char ch = line.charAt(pos);
			if (ch == '\n' || ch == ',' || ch == ' ' || ch == ';' || ch == '<' || ch == '>' || ch == '"')
				return pos;
			pos = pos + 1;
		}
		return pos;
	}
}

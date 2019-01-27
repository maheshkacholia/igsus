/*
 * Created on Jul 24, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.scan.util;
import java.io.File;
import java.io.RandomAccessFile;
import java.util.StringTokenizer;
import com.stockfaxforu.util.StockConstants;
/**
 * @author sdeopura
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class UpdateAllFiles
{
	public static void main(String[] args)
	{
		;
	}
	public static void filterDataFile()
	{
		File f = new File(StockConstants.INSTALL_DIR + "/data");
		String[] lists = f.list();
		RandomAccessFile r = null;
		for (int i = 0; i < lists.length; i++)
		{
			StringBuffer sb = new StringBuffer();
			try
			{
				r = new RandomAccessFile(StockConstants.INSTALL_DIR + "/data/" + lists[i], "r");
				String line = "";
				while ((line = r.readLine()) != null)
				{
					try
					{
						StringTokenizer st = new StringTokenizer(line, ",");
						st.nextToken();
						String s1 = st.nextToken();
						if (!s1.equalsIgnoreCase("EQ"))
						{
							continue;
						}
						sb.append(line + "\n");
					}
					catch (Exception e)
					{}
				}
				r.close();
				File f1 = new File(StockConstants.INSTALL_DIR + "/data/" + lists[i]);
				f1.delete();
				r = new RandomAccessFile(StockConstants.INSTALL_DIR + "/data/" + lists[i], "rw");
				r.writeBytes(sb.toString());
				r.close();
			}
			catch (Exception e)
			{
			}
		}
	}
	
}
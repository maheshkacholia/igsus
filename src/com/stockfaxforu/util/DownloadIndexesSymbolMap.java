/*
 * Created on Jun 13, 2009
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.util;
import java.io.File;
import java.io.RandomAccessFile;
import java.net.URLEncoder;
import java.util.StringTokenizer;
/**
 * @author Mahesh
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class DownloadIndexesSymbolMap
{
	public static void main(String[] args)
	{
		try
		{
			RandomAccessFile r = new RandomAccessFile("C:\\Users\\Mahesh\\Desktop\\index\\temp.txt","r");
			String line="";
			StringBuffer sb = new StringBuffer();
			while((line = r.readLine()) != null)
			{
				StringTokenizer st = new StringTokenizer(line," ");
				String symbol = st.nextToken().trim();
				StringBuffer sb1 = new StringBuffer();
				while(true)
				{
					String name="";
					try
					{
						name = st.nextToken().trim();

						name = Utility.replaceString(name, ",", "");
						float f = Float.parseFloat(name);
						break;
					}
					catch(Exception e)
					{
						if(name == null || name.equals(""))
							break;
						sb1.append(name + " ");
					}
					
				}
//				sb.append(symbol+"|"+sb1.toString()+"(NASDAQ)\n");
				sb.append(symbol+"|");

			}

		}
		catch(Exception e)
		{
//			e.printStackTrace();
		}
		
	}
	public static void saveIndex(String country)
	{
		String filename = "indexworld/indexsymbol" + "_" + country + ".txt";
		String indexname = "indexsymbol.txt";
		File f = new File(StockConstants.INSTALL_DIR + "/" + indexname);
		if(f.exists())
			return;
		RandomAccessFile r = null;
		try
		{
			String url = StockConstants.GETFILECONTENT + URLEncoder.encode(filename);

			String s = Utility.getUrlContent(url);

//			File f = new File(StockConstants.INSTALL_DIR + "/" + indexname);
			f.delete();

			r = new RandomAccessFile(StockConstants.INSTALL_DIR + "/" + indexname, "rw");
			r.writeBytes(s);
			r.close();
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
	}
	public static void saveMappingFile(String country)
	{
		String filename = "indexworld/allsymbol" + "_" + country + ".txt";
		String indexname = "allsymbol.txt";
		RandomAccessFile r = null;
		try
		{
			String url = StockConstants.GETFILECONTENT + URLEncoder.encode(filename);
			String s = Utility.getUrlContent(url);
			File f = new File(StockConstants.INSTALL_DIR + "/" + indexname);
			f.delete();
			r = new RandomAccessFile(StockConstants.INSTALL_DIR + "/" + indexname, "rw");
			r.writeBytes(s);
			r.close();
		}
		catch (Exception e)
		{
	//		e.printStackTrace();
		}
		finally
		{
			try
			{
				r.close();
			}
			catch (Exception e)
			{}
		}
	}
}

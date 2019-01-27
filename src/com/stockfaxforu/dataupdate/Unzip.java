package com.stockfaxforu.dataupdate;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.zip.*;

import com.stockfaxforu.util.StockConstants;

public class Unzip
{
	public static final void copyInputStream(InputStream in, OutputStream out) throws IOException
	{
		byte[] buffer = new byte[1024];
		int len;
		while ((len = in.read(buffer)) >= 0)
			out.write(buffer, 0, len);
		in.close();
		out.close();
	}
	private static String getMonth(String string)
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
//			e.printStackTrace();
		}
		return null;
	}
	public static void saveUrlContentBinary(String urlStr,String fileName) throws Exception
	{
		URL url = new URL(urlStr);
		URLConnection conn = url.openConnection();
		InputStream in = conn.getInputStream();
		copyInputStream(in, new BufferedOutputStream(new FileOutputStream(StockConstants.INSTALL_DIR +"/"+ fileName)));
	}	
	public static void main(String[] args)
	{
		try {
			System.out.println(saveEODData());
		} catch (Exception e) {
			// TODO Auto-generated catch block
	//		e.printStackTrace();
		}
	}
	public static String saveEODData(String date) throws Exception
	{
		StringBuffer returnStr = new StringBuffer();
		//load all data for first time
		try
		{
			StringTokenizer st = new StringTokenizer(date, "/");
			String dd = st.nextToken();
			String mm = getMonth(st.nextToken().toUpperCase());
			String yy = st.nextToken();
				saveUrlContentBinary("http://www.nseindia.com/content/historical/EQUITIES/" + yy + "/" + mm + "/" + "cm" + dd + mm + yy + "bhav.csv.zip", "temp.zip");
			return unzipAndSave();
		}
		catch (Exception e)
		{
			throw e;
		}
		// TODO Auto-generated method stub
	}

	public static String saveEODData() throws Exception
	{
		StringBuffer returnStr = new StringBuffer();
		//load all data for first time
		String dd ="";
		int ddi=0;
		try
		{
			Calendar c = Calendar.getInstance();
			 ddi = c.get(Calendar.DATE) - 1;
			 dd = ddi+"";
			String mm = getMonth((c.get(Calendar.MONTH) + 1)+"");
			String yy = c.get(Calendar.YEAR)+"";
			if(ddi < 10)
			{
				dd = "0"+ddi;
			}
			saveUrlContentBinary("http://www.nseindia.com/content/historical/EQUITIES/" + yy + "/" + mm + "/" + "cm" + dd + mm + yy + "bhav.csv.zip", "temp.zip");
			return unzipAndSave();
		}
		catch (Exception e)
		{
			throw e;
		}
		// TODO Auto-generated method stub
	}


	public static  String unzipAndSave() throws Exception
	{
		Enumeration entries;
		ZipFile zipFile;
		zipFile = new ZipFile(StockConstants.INSTALL_DIR+ "/temp.zip");
		entries = zipFile.entries();
		ZipEntry entry = (ZipEntry) entries.nextElement();
		System.err.println("Extracting file: " + entry.getName());
		copyInputStream(zipFile.getInputStream(entry), new BufferedOutputStream(new FileOutputStream(StockConstants.INSTALL_DIR+ "/temp.csv")));
		zipFile.close();
		File f = new File(StockConstants.INSTALL_DIR+ "/temp.zip");
		RandomAccessFile file = new RandomAccessFile(StockConstants.INSTALL_DIR+ "/temp.csv","r");
		byte[] b = new byte[(int)file.length()];
		int x = file.read(b);
		String s  = new String(b,0,x);
		file.close();
		f.delete();

		
		f = new File(StockConstants.INSTALL_DIR+ "/temp.csv");

		f.delete();
		
		f = new File(StockConstants.INSTALL_DIR + "/output.csv");
		if(f.exists())
			f.delete();
		file = new RandomAccessFile(StockConstants.INSTALL_DIR + "/output.csv","rw" );
		file.writeBytes(s);
		file.close();
		return s;
			
	}
	
}
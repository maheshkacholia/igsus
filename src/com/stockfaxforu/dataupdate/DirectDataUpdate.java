package com.stockfaxforu.dataupdate;

import java.io.BufferedReader;
import java.io.File;
import java.io.RandomAccessFile;
import java.util.Calendar;
import java.util.StringTokenizer;

import com.stockfaxforu.getolddataforu.loader.Loader;
import com.stockfaxforu.util.ConvertDataUtility;
import com.stockfaxforu.util.StockConstants;
import com.stockfaxforu.util.Utility;

public class DirectDataUpdate 
{
	
	public void loadDataForDate(String startDate, String name) throws Exception
	{
		name="ACC";
	//	String dataFromSaidDate = loadData(startDate, name);
		String dataFromSaidDate = handleSplit(startDate);
			
	//	if (dataFromSaidDate != null && !dataFromSaidDate.trim().equals(""))
		{
			Loader.saveAllIndexes("");
		}
		StringTokenizer st = new StringTokenizer(dataFromSaidDate, ",");
		Loader loader = new Loader();
		while (st.hasMoreTokens())
		{
			String date = st.nextToken();
			String sharePrice = getSharePriceForDate2(date);
			loader.updatePriceInShare(sharePrice);
		}
		loader.updateDataInFile();
	}
	public static String handleSplit(String mydate) throws Exception
	{
		String data="";
		String data1="";
		String[] mydate1 = mydate.split("/");
		Calendar myc1 = Calendar.getInstance();	
		Calendar myc2 = Calendar.getInstance();	
		
		Calendar myc = Calendar.getInstance();
		
		myc.set(Integer.parseInt(mydate1[2]),Integer.parseInt(mydate1[1]) - 1 , Integer.parseInt(mydate1[0]));
		try
		{
			data = Utility.getUrlContent("http://www.indiabulls.com/securities/research/techanalysis/GetChartApplet_Information.aspx?symbol=TCS");
		}
		catch (Exception e)
		{
//			MessageDiaglog msg = new MessageDiaglog("Not able to do operation");
			throw new Exception();
		}
//		System.out.println(data);
		StringTokenizer st = new StringTokenizer(data.trim(),"\n");
		StringBuffer sb = new StringBuffer();
		String prevclose="";
		String prevclose1="";
		System.out.println(data);
		while(st.hasMoreTokens())
		{
			try
			{
				String line = st.nextToken();
				StringTokenizer linest = new StringTokenizer(line,",");
				String typedate = linest.nextToken();
				StringTokenizer tempst = new StringTokenizer(typedate,":");
				tempst.nextToken();
				String date = tempst.nextToken().trim();
				String open = linest.nextToken().trim();
				String high = linest.nextToken().trim();
				String low = linest.nextToken().trim();
				String close = linest.nextToken().trim();
				String volume = linest.nextToken().trim();
				long l = Long.parseLong(volume);
			
				String[] date1 = date.split("/");
				myc1.set(Integer.parseInt(date1[2]),Integer.parseInt(date1[0]) - 1 , Integer.parseInt(date1[1]));
				
				if ( myc1.after(myc))
				{
				//	if(st.hasMoreTokens())
						sb.append(date1[1] + "/" + date1[0] + "/" + date1[2] + ",");
					
				}
				myc2 = myc1;
			}
			catch(Exception e)
			{
				
			}
			
		}
	//dd/mm/yyyy	
//		return "31/12/2010";
		if(sb.toString().equals(""))
		{
//			mydate = "03/01/2011";
			String[] s = mydate.split("/");
			Calendar c = Calendar.getInstance();
			c.set(c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DATE),1,1,1);
			Calendar c1 = Calendar.getInstance();
			
			c1.set(Integer.parseInt(s[2]), Integer.parseInt(s[1]) - 1, Integer.parseInt(s[0]),1,1,1);
			if(c.after(c1))
			{
				int ii = c.get(Calendar.MONTH) + 1;
				String ss = convertTwoDigit(c.get(Calendar.DATE))+"/"+convertTwoDigit(ii) +"/"+ c.get(Calendar.YEAR); 
				return ss; 
			}
		}
		return sb.toString();
	}

	public static String convertTwoDigit(int x)
	{
		if ( x < 10 && x >= 0)
		{
			return "0"+x;
		}
		else
			return x+"";
	}
	
	public static String getSharePriceForDate2(String date) throws Exception
	{
		StringBuffer returnStr = new StringBuffer();
		RandomAccessFile rfile =null;
		BufferedReader rd =null;
		//load all data for first time
		try
		{
			StringBuffer sb = new StringBuffer();
			StringTokenizer st = new StringTokenizer(date, "/");
			String dd = st.nextToken();
			String mm = getMonth(st.nextToken().toUpperCase());
			String yy = st.nextToken();
			
			returnStr.append(Unzip.saveEODData(date));
			


			String s= StockConstants.INSTALL_DIR + "/" + "output.csv";
			File f = new File(s);
			if (f.exists())
				f.delete();
			rfile = new RandomAccessFile(s, "rw");
			rfile.writeBytes(returnStr.toString());
			rfile.close();


			//			CreateAverage.loadAllShareData();
		}
		catch (Exception e)
		{
	//		e.printStackTrace();
			throw e;
		}
		finally
		{
			try
			{
				rd.close();
				rfile.close();
			}	
			catch(Exception e)
			{
			}	
		}	
		// TODO Auto-generated method stub
		System.out.println("Data For date " + date + " updated\n");
		return returnStr.toString();
	}
	public static String getMonth(String string)
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


}

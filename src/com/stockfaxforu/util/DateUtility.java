/*
 * Created on Jul 8, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.util;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;
/**
 * @author sdeopura
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class DateUtility
{
	public static Date getDaysAfterNoOfDays(int noofdays)
	{
		GregorianCalendar gc = new GregorianCalendar();
		//		   Subtract seven days from the object.
		gc.add(Calendar.DATE, noofdays);
		//		   Get a Date object based on the Calendar object.
		Date weekAfter = gc.getTime();
		//		   Construct a string object based on the Date object.
		return weekAfter;
	}
	public static Date getDaysAfterNoOfDays(String dateStr, int noofdays)
	{
		StringTokenizer st = new StringTokenizer(dateStr, "-");
		int dd = Integer.parseInt(st.nextToken());
		int mm = Integer.parseInt(st.nextToken());
		int yy = Integer.parseInt(st.nextToken());
		GregorianCalendar gc = new GregorianCalendar(yy, mm, dd);
		//		   Subtract seven days from the object.
		gc.add(Calendar.DATE, noofdays);
		//		   Get a Date object based on the Calendar object.
		Date weekAfter = gc.getTime();
		//		   Construct a string object based on the Date object.
		return weekAfter;
	}
	public static void main(String args[])
	{
		saveExpiryDate(getDaysAfterNoOfDays(7), StockConstants.expiryFile);
		saveExpiryDate(getDaysAfterNoOfDays(6), StockConstants.serverDateFile);
	}
	public static boolean compareDate(Date d1, Date d2)
	{
		if (d1.after(d2))
			return true;
		else
			return false;
	}
	public static void saveUserDetail(String s)
	{
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try
		{
			File f = new File(StockConstants.userDetailFile);

			if (f.exists())
			{
				f.delete();
			}
			RandomAccessFile f1 = new RandomAccessFile(StockConstants.userDetailFile, "rw");
			f1.writeBytes(s);
			f1.close();
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
		}
		finally
		{
			try
			{
			}
			catch (Exception e1)
			{
				// TODO Auto-generated catch block
			}
		}
		
	}
	public static String readUserDetail()
	{
		FileInputStream fos = null;
		RandomAccessFile out  = null;
		try
		{
			out = new RandomAccessFile(StockConstants.userDetailFile,"r");
			String s = out.readLine();
			return s;
		}
		catch (Exception e)
		{
			return "";
			// TODO Auto-generated catch block
			//			e.printStackTrace();
		}
		finally
		{
			try
			{
				out.close();
			}
			catch (Exception e1)
			{
				// TODO Auto-generated catch block
			}
		}
		
	}


	public static void saveExpiryDate(Date d, String fileStr)
	{
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try
		{
			File f = new File(fileStr);
			if (f.exists())
			{
				f.delete();
			}
			fos = new FileOutputStream(fileStr);
			oos = new ObjectOutputStream(fos);
			if (d != null)
				oos.writeObject(d);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
		}
		finally
		{
			try
			{
				oos.close();
				fos.close();
			}
			catch (Exception e1)
			{
				// TODO Auto-generated catch block
			}
		}
	}
	public static Date readExpiryDate(String fileStr)
	{
		FileInputStream fos = null;
		ObjectInputStream oos = null;
		try
		{
			File f = new File(fileStr);
			fos = new FileInputStream(fileStr);
			oos = new ObjectInputStream(fos);
			Date d = (Date) oos.readObject();
			return d;
		}
		catch (Exception e)
		{
		}
		finally
		{
			try
			{
				oos.close();
				fos.close();
			}
			catch (Exception e1)
			{
			}
		}
		return null;
	}
	public static void updateDates()
	{
		BufferedReader buff = null;
		try
		{
			String s = readUserDetail();
//			StockConstants.USERDETAIL = s;
			buff = Utility.getDataFromURL(StockConstants.IGSROOT + "checkusrexp.jsp?"+s);
			String dateStr = buff.readLine();
			buff.close();

			StringTokenizer st = new StringTokenizer(dateStr,"|");
			if(st.hasMoreElements())
			{
				String s1 = st.nextToken();
				StringTokenizer st2 = new StringTokenizer(s1," ");
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
				try
				{
					Date dt = createDate(st2.nextToken());
					saveExpiryDate(dt, StockConstants.serverDateFile);
				}
				catch (Exception e)
				{
					// TODO Auto-generated catch block
				}
				
			}
			if(st.hasMoreElements())
			{
				String s1 = st.nextToken();
				StringTokenizer st2 = new StringTokenizer(s1," ");
//				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
				try
				{
					Date dt = createDate(st2.nextToken());
					saveExpiryDate(dt, StockConstants.expiryFile);
				}
				catch (Exception e)
				{
					// TODO Auto-generated catch block
				}
	
			}
			
		}
		catch (Exception e1)
		{
			try
			{
				buff.close();
			}
			catch (Exception e2)
			{
				// TODO Auto-generated catch block
			}
			return;
		}
	}
	/**
	 * @return
	 */
	public static Date createDate(String s)
	{
		StringTokenizer st = new StringTokenizer(s,"-");
		int yy = Integer.parseInt(st.nextToken()) - 1900;
		int mm = Integer.parseInt(st.nextToken()) - 1;
		int dd = Integer.parseInt(st.nextToken());
		return new Date(yy,mm,dd);
		
	}
	public static boolean isExpired()
	{
		try
		{
			Date serverDate = readExpiryDate(StockConstants.serverDateFile);
			Date expirydate = readExpiryDate(StockConstants.expiryFile);
			if(expirydate==null)
			{
				saveExpiryDate(DateUtility.getDaysAfterNoOfDays(-2), StockConstants.expiryFile);
				expirydate = readExpiryDate(StockConstants.expiryFile);
			}
			if (compareDate(serverDate, expirydate))
				return true;
			else
				return false;

		}
		catch(Exception e)
		{
			return true;
		}
	}
/*	public static String getDate(String fileName)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
		Date dateStr = readExpiryDate(StockConstants.expiryFile);
		return dateStr.toString(); 
		

	}
*/	
	public static long noOfDaysToExpire()
	{
		Date serverDate = readExpiryDate(StockConstants.serverDateFile);
		Date expirydate = readExpiryDate(StockConstants.expiryFile);
		long serverTime = serverDate.getTime() / (24 * 60 * 60 * 1000);
		long expiryTime = expirydate.getTime() / (24 * 60 * 60 * 1000);
		return (expiryTime - serverTime);
	}
}

/*
 * Created on Aug 4, 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.getolddataforu.loader;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;

import com.stockfaxforu.component.MainGraphComponent;
import com.stockfaxforu.dataupdate.DirectDataUpdate;
import com.stockfaxforu.formulabuilder.MessageDiaglog;
import com.stockfaxforu.frontend.InstallScreen;
import com.stockfaxforu.frontend.MainScreen;
import com.stockfaxforu.getolddataforu.thread.SplitThread;
import com.stockfaxforu.getolddataforu.thread.SplitThreadForEOD;
import com.stockfaxforu.indiabull.SplitHandler;
//import com.stockfaxforu.getolddataforu.util.*;
import com.stockfaxforu.scan.util.ScanUtility;
import com.stockfaxforu.util.ConvertDataUtility;
import com.stockfaxforu.util.StockConstants;
import com.stockfaxforu.util.Utility;

/**
 * @author sdeopu1
 * 
 *         To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Loader
{
	HashMap dataForAllShare = new HashMap();

	public static HashMap fileContentHash = new HashMap();

	public static Vector getFileContentForScreening(String startDate, String name, String screenDate, String endDate) throws Exception
	{
		Runtime runtime = Runtime.getRuntime();
		// // ln("totmem=" +runtime.totalMemory ());
		// // ln("freemem="+runtime.freeMemory ());

		name = name.toUpperCase();
		Vector retVec = (Vector) fileContentHash.get(name);
		// // ln("retstr is ="+ retStr);
		Vector inputdata1 = null;
		if (retVec != null)
		{
			return retVec;
		}
		try
		{
			String stemp = (String) StockConstants.AttemptedHash.get(name);
			if (stemp != null)
			{
				int x = Integer.parseInt(stemp);
				if (x > 1)
					throw new Exception();

			}
			inputdata1 = Loader.getFileContent(screenDate, name, endDate);
			if (inputdata1 == null)
				inputdata1 = new Vector();
			// ln(inputdata1.get(0));
			fileContentHash.put(name, inputdata1);
			return inputdata1;

		}
		catch (Exception e)
		{
			throw new Exception();
		}

	}

	public static boolean isDateEquals(String date, String screendate)
	{
		StringTokenizer st = new StringTokenizer(date, "-");
		int yy = Integer.parseInt(st.nextToken());
		int mon = Integer.parseInt(st.nextToken());
		int dd = Integer.parseInt(st.nextToken());
		Calendar c = Calendar.getInstance();
		c.set(yy, mon - 1, dd);

		StringTokenizer st1 = new StringTokenizer(screendate, "-");
		int yy1 = Integer.parseInt(st1.nextToken());
		int mon1 = Integer.parseInt(st1.nextToken());
		int dd1 = Integer.parseInt(st1.nextToken());
		Calendar c1 = Calendar.getInstance();
		c1.set(yy1, mon1 - 1, dd1);
		if (c.equals(c1))
			return true;
		else
			return false;
	}

	public static boolean isDateGreater(String date, String screendate)
	{
		StringTokenizer st = new StringTokenizer(date, "-");
		int yy = Integer.parseInt(st.nextToken());
		int mon = Integer.parseInt(st.nextToken());
		int dd = Integer.parseInt(st.nextToken());
		Calendar c = Calendar.getInstance();
		c.set(yy, mon - 1, dd);

		StringTokenizer st1 = new StringTokenizer(screendate, "-");
		int yy1 = Integer.parseInt(st1.nextToken());
		int mon1 = Integer.parseInt(st1.nextToken());
		int dd1 = Integer.parseInt(st1.nextToken());
		Calendar c1 = Calendar.getInstance();
		c1.set(yy1, mon1 - 1, dd1);
		if (c.after(c1))
			return true;
		else
			return false;
		/*
		 * if (yy >= yy1) if (mon >= mon1) if (dd >= dd1) return true;
		 */
		// return false;
	}

	public static Vector getContentFromFile(String name) throws Exception
	{
		Vector retVec = null;
		RandomAccessFile fos = new RandomAccessFile(StockConstants.INSTALL_DIR + "/data/" + name + ".csv", "r");
		String line = "";
		StringBuffer sb = new StringBuffer();
		while ((line = fos.readLine()) != null)
		{
			sb.append(line + "\n");
		}

		retVec = createStockDataVector(sb.toString());
		fos.close();
		return retVec;

	}

	public static Vector getContentFromFile(String name, String startDate, String endDate) throws Exception
	{
		Vector retVec = null;
		RandomAccessFile fos = null;

		try
		{
			retVec = null;
			fos = new RandomAccessFile(StockConstants.INSTALL_DIR + "/data/" + name + ".csv", "r");
			String line = "";
			StringBuffer sb = new StringBuffer();
			while ((line = fos.readLine()) != null)
			{
				sb.append(line + "\n");
			}
			retVec = createStockDataVector(sb.toString(), startDate, endDate);
			sb = null;
			fos.close();

			return retVec;

		}
		catch (Exception e)
		{
			fos.close();
			throw e;
		}
	}

	public static Vector getFileContent(String startDate, String name, String endDate) throws Exception
	{
		File f = new File(StockConstants.INSTALL_DIR + "/data/" + name + ".csv");
		Calendar c = Calendar.getInstance();
		Calendar c1 = Calendar.getInstance();
		c1.setTimeInMillis(f.lastModified());
		int x=c1.get(Calendar.HOUR_OF_DAY);
		return GoogleDataDownloader.getFinalRecordFromGoogle(name);
	//	return YahooDataDownloader.getDataFromYahoo(name);
	}

	public static void downloadAndSecurityDataWithDelInfoFromNSE(String securityName)
	{
		String startdate = "01-01-2005";
		String endDate = "24-02-2013";
		try
		{
			String content = Utility.getUrlContent("http://www.nseindia.com/content/equities/scripvol/datafiles/" + startdate + "-TO-" + endDate + securityName + "ALLN.csv");
			Utility.saveContent(StockConstants.INSTALL_DIR + "/dataall/" + securityName + ".csv", content);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static Vector createSubList(Vector dataVector, String startDate, String endDate)
	{
		HashMap hs = null;
		Vector inputdata1 = new Vector();
		startDate = Utility.convertMMDDYYToYYMMDD(startDate);
		endDate = Utility.convertMMDDYYToYYMMDD(endDate);
		for (int i = 0; i < dataVector.size(); i++)
		{
			hs = (HashMap) dataVector.get(i);
			String mydate = hs.get(MainGraphComponent.Date) + "";

			if (!isDateGreater(mydate, startDate))
				continue;
			if (!endDate.equalsIgnoreCase(""))
			{
				if (isDateGreater(mydate, endDate))
				{
					break;
				}

			}
			inputdata1.add(hs);
		}
		return inputdata1;
	}

	public static Vector createStockDataVector(String dataStr, String startDate, String endDate)
	{
		StringTokenizer dataStrToken = new StringTokenizer(dataStr, "\n");
		Vector inputdata1 = new Vector();
		while (dataStrToken.hasMoreTokens())
		{
			try
			{
				HashMap hs = new HashMap();
				// newline = stockFile.readLine();
				String newline = dataStrToken.nextToken();
				StringTokenizer st1 = new StringTokenizer(newline, ",");
				st1.nextToken();
				st1.nextToken();
				String mydate = Utility.convertDate(st1.nextToken());
				// if (!isDateGreaterEqual(mydate))
				// continue;
				if (!isDateGreater(mydate, startDate))
					continue;
				if (!endDate.equalsIgnoreCase(""))
				{
					if (isDateGreater(mydate, endDate))
					{
						break;
					}

				}
				hs.put(MainGraphComponent.Date, mydate);
				// // ln(hs.get("Date"));
				st1.nextToken();
				hs.put(MainGraphComponent.Open, st1.nextToken());
				hs.put(MainGraphComponent.High, st1.nextToken());
				hs.put(MainGraphComponent.Low, st1.nextToken());
				st1.nextToken();
				float newprice = Float.parseFloat(st1.nextToken());
				hs.put(MainGraphComponent.Close, newprice + "");
				long newvolume = Long.parseLong(st1.nextToken());
				hs.put(MainGraphComponent.Volume, newvolume + "");
				inputdata1.add(hs);
			}
			catch (Exception e1)
			{
				// e1.printStackTrace();
			}
		}
		if (StockConstants.RESOLUTION != 1)
		{

		}
		if (StockConstants.RESOLUTION == 1)
			return inputdata1;
		else
			return setResolution(inputdata1, StockConstants.RESOLUTION);
	}

	public static Vector setResolution(Vector inputdata, int resolution)
	{
		Vector v = new Vector();

		if (resolution == MainGraphComponent.WEEKLY || resolution == MainGraphComponent.MONTHLY)
		{
			v = getSpecialResolution(inputdata, resolution);
		}
		else
		{
			int j = 0;
			for (int i = inputdata.size() - 1; i >= 0; i--)
			{
				int mod = (j) % resolution;
				if (mod == 0)
				{
					HashMap hs = getHashMapForRes(inputdata, i, resolution);
					v.addElement(hs);

				}
				j++;
			}

		}
		Vector v1 = new Vector();
		for (int i = v.size() - 1; i >= 0; i--)
		{
			v1.add(v.get(i));
		}
		return v1;
	}

	public static Vector getSpecialResolution(Vector inputdata1, int chartResolution)
	{
		Vector retvector = new Vector();
		int lastpos = inputdata1.size() - 1;
		int firstpos = 0;
		for (int i = inputdata1.size() - 1; i > 0; i--)
		{
			HashMap hs = (HashMap) inputdata1.get(i);

			String s = (String) hs.get(MainGraphComponent.Date);
			String[] s1 = s.split("-");
			if (chartResolution == MainGraphComponent.WEEKLY)
			{
				Calendar c = Calendar.getInstance();
				c.set(Integer.parseInt(s1[0]), Integer.parseInt(s1[1]) - 1, Integer.parseInt(s1[2]));
				if (c.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY)
				{
					HashMap h = getHashMapForResSpecial(inputdata1, i + 1, lastpos);
					retvector.add(h);
					lastpos = i;
				}
			}
			else if (chartResolution == MainGraphComponent.MONTHLY)
			{
				if (i < inputdata1.size() - 1)
				{
					HashMap hs4 = (HashMap) inputdata1.get(i + 1);

					String s4 = (String) hs4.get(MainGraphComponent.Date);
					String[] s45 = s4.split("-");
					if (Integer.parseInt(s45[1]) != Integer.parseInt(s1[1]))
					{
						HashMap h = getHashMapForResSpecial(inputdata1, i + 1, lastpos);
						retvector.add(h);
						lastpos = i;

					}
				}
			}

		}
		if (lastpos != 0)
		{
			HashMap h = getHashMapForResSpecial(inputdata1, 0, lastpos);
			retvector.add(h);

		}
		return retvector;
	}

	public static HashMap getHashMapForResSpecial(Vector inputdata1, int startPos, int endPosition)
	{
		HashMap outHash = new HashMap();
		int start = startPos;
		HashMap hs = (HashMap) inputdata1.elementAt(start);
		float open = Float.parseFloat((String) hs.get(MainGraphComponent.Open));
		float high = Float.parseFloat((String) hs.get(MainGraphComponent.High));
		float low = Float.parseFloat((String) hs.get(MainGraphComponent.Low));
		long volume = 0;
		outHash.put(MainGraphComponent.Open, open + "");
		hs = (HashMap) inputdata1.elementAt(endPosition);
		float close = Float.parseFloat((String) hs.get(MainGraphComponent.Close));
		outHash.put(MainGraphComponent.Close, close + "");
		outHash.put(MainGraphComponent.Date, hs.get(MainGraphComponent.Date));
		for (int i = startPos; i <= endPosition; i++)
		{
			hs = (HashMap) inputdata1.elementAt(i);
			float tmphigh = Float.parseFloat((String) hs.get(MainGraphComponent.High));
			float tmplow = Float.parseFloat((String) hs.get(MainGraphComponent.Low));
			long tmpvol = Long.parseLong((String) hs.get(MainGraphComponent.Volume));
			if (tmphigh > high)
			{
				high = tmphigh;
			}
			if (tmplow < low)
			{
				low = tmplow;
			}
			volume = volume + tmpvol;
		}
		volume = volume;
		outHash.put(MainGraphComponent.High, high + "");
		outHash.put(MainGraphComponent.Low, low + "");
		outHash.put(MainGraphComponent.Volume, volume + "");
		return outHash;
	}

	public static HashMap getHashMapForRes(Vector inputdata1, int pos, int chartResolution)
	{
		HashMap outHash = new HashMap();
		int start = pos - chartResolution + 1;
		if (start < 0)
			start = 0;
		if (pos >= inputdata1.size())
			pos = inputdata1.size() - 1;
		HashMap hs = (HashMap) inputdata1.elementAt(start);
		float open = Float.parseFloat((String) hs.get(MainGraphComponent.Open));
		float high = Float.parseFloat((String) hs.get(MainGraphComponent.High));
		float low = Float.parseFloat((String) hs.get(MainGraphComponent.Low));
		long volume = 0;
		outHash.put(MainGraphComponent.Open, open + "");
		hs = (HashMap) inputdata1.elementAt(pos);
		float close = Float.parseFloat((String) hs.get(MainGraphComponent.Close));
		outHash.put(MainGraphComponent.Close, close + "");
		outHash.put(MainGraphComponent.Date, hs.get(MainGraphComponent.Date));
		for (int i = start; i <= pos; i++)
		{
			hs = (HashMap) inputdata1.elementAt(i);
			float tmphigh = Float.parseFloat((String) hs.get(MainGraphComponent.High));
			float tmplow = Float.parseFloat((String) hs.get(MainGraphComponent.Low));
			long tmpvol = Long.parseLong((String) hs.get(MainGraphComponent.Volume));
			if (tmphigh > high)
			{
				high = tmphigh;
			}
			if (tmplow < low)
			{
				low = tmplow;
			}
			volume = volume + tmpvol;
		}

		// volume = volume / chartResolution;
		outHash.put(MainGraphComponent.High, high + "");
		outHash.put(MainGraphComponent.Low, low + "");
		outHash.put(MainGraphComponent.Volume, volume + "");
		return outHash;
	}

	public static Vector createStockDataVector(String dataStr)
	{
		StringTokenizer dataStrToken = new StringTokenizer(dataStr, "\n");
		Vector inputdata1 = new Vector();
		while (dataStrToken.hasMoreTokens())
		{
			try
			{
				HashMap hs = new HashMap();
				// newline = stockFile.readLine();
				String newline = dataStrToken.nextToken();
				StringTokenizer st1 = new StringTokenizer(newline, ",");
				st1.nextToken();
				st1.nextToken();
				String mydate = Utility.convertDate(st1.nextToken());
				// if (!isDateGreaterEqual(mydate))
				// continue;
				hs.put(MainGraphComponent.Date, mydate);
				// // ln(hs.get("Date"));
				st1.nextToken();
				hs.put(MainGraphComponent.Open, st1.nextToken());
				hs.put(MainGraphComponent.High, st1.nextToken());
				hs.put(MainGraphComponent.Low, st1.nextToken());
				st1.nextToken();
				float newprice = Float.parseFloat(st1.nextToken());
				hs.put(MainGraphComponent.Close, newprice + "");
				long newvolume = Long.parseLong(st1.nextToken());
				hs.put(MainGraphComponent.Volume, newvolume + "");
				inputdata1.add(hs);
			}
			catch (Exception e1)
			{
				// e1.printStackTrace();
			}
		}
		return inputdata1;

	}

	public static Vector createStockDataVectorIntraday(String dataStr)
	{
		StringTokenizer dataStrToken = new StringTokenizer(dataStr, "\n");
		Vector inputdata1 = new Vector();
		while (dataStrToken.hasMoreTokens())
		{
			try
			{
				HashMap hs = new HashMap();
				// newline = stockFile.readLine();
				String newline = dataStrToken.nextToken();
				StringTokenizer st1 = new StringTokenizer(newline, ",");
				// st1.nextToken();
				// st1.nextToken();
				String mydate = st1.nextToken();
				// if (!isDateGreaterEqual(mydate))
				// continue;
				hs.put(MainGraphComponent.Date, mydate);
				// // ln(hs.get("Date"));
				// st1.nextToken();
				hs.put(MainGraphComponent.Open, st1.nextToken());
				hs.put(MainGraphComponent.High, st1.nextToken());
				hs.put(MainGraphComponent.Low, st1.nextToken());
				// st1.nextToken();
				float newprice = Float.parseFloat(st1.nextToken());
				hs.put(MainGraphComponent.Close, newprice + "");
				if (st1.hasMoreTokens())
				{
					long newvolume = Long.parseLong(st1.nextToken());
					hs.put(MainGraphComponent.Volume, "1000");

				}
				inputdata1.add(hs);
			}
			catch (Exception e1)
			{
			}
		}
		return inputdata1;

	}

	public static void main(String args[])
	{
		// saveAllIndexes("01-01-2000");
		System.out.println(Loader.isDateGreater("2013-01-02", "2013-01-01"));
	}

	// code for saving indexes
	public static void saveAllIndexes(String startDate)

	{
		String[] indexval = { "BANKNIFTY", "CNX100", "CNX500", "CNXIT", "CNXMidCap200", "CNXMidcap", "Defty", "Nifty", "NiftyJunior" };

		for (int i = 0; i < indexval.length; i++)
		{
			StockConstants.error = "";
			try
			{
				loadIndexes(startDate, indexval[i], indexval[i]);
			}
			catch (Exception e)
			{
				StockConstants.error = "Error Occured While getting data for indexes see Console for message";
				// ln("Not Able to Load Data for Index " + indexval[i] +
				// " due to error ");
				// ln(e.getMessage());
			}

		}
		// ln("All Index data loaded in c:" + StockConstants.INSTALL_DIR +
		// "/Index directory");
		if (StockConstants.error.equals(""))
		{
			StockConstants.error = "All Index data loaded in c:" + StockConstants.INSTALL_DIR + "/Index directory";
		}

	}

	public void loadDataForDate()
	{
		RandomAccessFile file = null;
		try
		{
			file = new RandomAccessFile(StockConstants.INSTALL_DIR + "/data/" + StockConstants.SELECTED_STOCK + ".csv", "r");
			String line = "";
			while (file.getFilePointer() < file.length())
			{
				line = file.readLine();
			}
			StringTokenizer st = new StringTokenizer(line, ",");
			st.nextToken();
			st.nextToken();
			String date = st.nextToken();
			file.close();
		//	loadDataForDate(date);
			// mahesh new addition
			DirectDataUpdate directdata = new DirectDataUpdate();
			directdata.loadDataForDate(getNewFormatedDate(date), StockConstants.SELECTED_STOCK);
		}
		catch (Exception e)
		{
			// e.printStackTrace();
		}
		finally
		{
			try
			{
				file.close();
			}
			catch (IOException e1)
			{
				// TODO Auto-generated catch block
				// e1.printStackTrace();
			}
		}
	}

	public static String getNewFormatedDate(String oldStr)
	{
		StringTokenizer st = new StringTokenizer(oldStr, "-");
		String day = st.nextToken();
		String month = st.nextToken();
		String month1 = "";
		if (month.equalsIgnoreCase("Jan"))
			month1 = "01";
		if (month.equalsIgnoreCase("Feb"))
			month1 = "02";
		if (month.equalsIgnoreCase("Mar"))
			month1 = "03";
		if (month.equalsIgnoreCase("Apr"))
			month1 = "04";
		if (month.equalsIgnoreCase("May"))
			month1 = "05";
		if (month.equalsIgnoreCase("Jun"))
			month1 = "06";
		if (month.equalsIgnoreCase("Jul"))
			month1 = "07";
		if (month.equalsIgnoreCase("Aug"))
			month1 = "08";
		if (month.equalsIgnoreCase("Sep"))
			month1 = "09";
		if (month.equalsIgnoreCase("Oct"))
			month1 = "10";
		if (month.equalsIgnoreCase("Nov"))
			month1 = "11";
		if (month.equalsIgnoreCase("Dec"))
			month1 = "12";

		String year = st.nextToken();
		String retStr = day + "/" + month1 + "/" + year;
		return retStr;
	}

	public void updateDataInFile()
	{
		Set s = dataForAllShare.keySet();
		Iterator it = s.iterator();
		while (it.hasNext())
		{
			String name = (String) it.next();
			writeStringToFile(name, (String) dataForAllShare.get(name), StockConstants.KEEP_OLD);
		}

	}

	public void writeStringToFile(String name, String data, int status)
	{
		try
		{
			File f = new File(StockConstants.INSTALL_DIR + "/data/" + name + ".csv");
			if (!f.exists())
			{
				return;
			}

			if (status == StockConstants.KEEP_OLD && f.exists())
			{
				// only update if new data is not been updated
				if (!isStockUpdatedDateGreater(name, data))
					return;

				char c = dataFileContent.charAt(dataFileContent.length() - 1);

				RandomAccessFile f1 = new RandomAccessFile(StockConstants.INSTALL_DIR + "/data/" + name + ".csv", "rw");
				f1.seek(f1.length());
				if (c != '\n')
					f1.writeBytes("\n" + data + "\n");
				else
					f1.writeBytes(data + "\n");

				f1.close();
			}
			/*
			 * if(f.exists()) f.delete(); java.io.FileOutputStream out = new
			 * java.io.FileOutputStream(StockConstants.INSTALL_DIR + "/data/" +
			 * name + ".csv"); out.write(data.getBytes()); out.close();
			 */
		}
		catch (Exception e)
		{
		}
	}

	String dataFileContent = "";

	public boolean isStockUpdatedDateGreater(String name, String data)
	{
		try
		{
			dataFileContent = Utility.getFileContent(StockConstants.INSTALL_DIR + "/data/" + name + ".csv");
			int pos = dataFileContent.trim().lastIndexOf("\n");
			String lastLine = dataFileContent.substring(pos + 1);
			StringTokenizer tokenizer = new StringTokenizer(lastLine, ",");
			tokenizer.nextToken();
			tokenizer.nextToken();
			String histdate = tokenizer.nextToken();

			tokenizer = new StringTokenizer(data, ",");
			tokenizer.nextToken();
			tokenizer.nextToken();
			String newdate = tokenizer.nextToken();

			return Loader.isDateGreater(Utility.convertDate(newdate), Utility.convertDate(histdate));
			// return true;
		}
		catch (Exception e)
		{
			return false;
		}
	}

	public void updatePriceInShare(String sharePrice)
	{
		StringTokenizer st = new StringTokenizer(sharePrice, "\n");
		while (st.hasMoreTokens())
		{
			String line = st.nextToken();
			StringTokenizer st1 = new StringTokenizer(line, ",");
			String name = st1.nextToken();
			String type = st1.nextToken();
			if (!type.equals("EQ"))
				continue;
			updateHashTable(name, line);
		}
		Iterator it = dataForAllShare.keySet().iterator();
		while (it.hasNext())
		{
			String key = (String) it.next();
			String value = (String) dataForAllShare.get(key);

		}

		// TODO Auto-generated method stub

	}

	public void updateHashTable(String name, String line)
	{
		String oldData = (String) this.dataForAllShare.get(name);
		if (oldData == null)
		{
			dataForAllShare.put(name, convertAsOldDataFormat(line));
		}
		else
		{
			oldData = oldData + "\n" + convertAsOldDataFormat(line);
			dataForAllShare.put(name, oldData);
		}
		// TODO Auto-generated method stub

	}

	private String convertAsOldDataFormat(String line)
	{

		StringTokenizer st = new StringTokenizer(line, ",");
		String str = "";

		String symbol = st.nextToken();
		String series = st.nextToken();
		String open = st.nextToken();
		String high = st.nextToken();
		String low = st.nextToken();
		String close = st.nextToken();
		String last = st.nextToken();
		String prevclose = st.nextToken();
		String tottrdqty = st.nextToken();
		String tottrdval = Float.parseFloat(st.nextToken()) / 100000.0 + "";

		String date = st.nextToken();

		str = symbol + "," + series + "," + date + "," + prevclose + "," + open + "," + high + "," + low + "," + last + "," + close + "," + tottrdqty + "," + tottrdval;

		// TODO Auto-generated method stub
		return str;
	}


	public static void loadIndexes(String startDate, String name, String savename) throws Exception
	{


		String returnStr = ConvertDataUtility.getFutureIntradayHistoricalDataInStrFromNSE(name, "", ConvertDataUtility.HIST);
		File f = new File(StockConstants.INSTALL_DIR + "/data");
		if (!f.exists())
			f.mkdir();
		f = new File(StockConstants.INSTALL_DIR + "/data/" + name + ".csv");
		if (f.exists())
			f.delete();
		FileOutputStream filestream = new FileOutputStream(StockConstants.INSTALL_DIR + "/data/" + savename + ".csv");
		filestream.write(returnStr.toString().getBytes());
		filestream.close();

	}

	public static void getSharePriceForDatePosToPos(int startpos, int endpos)
	{
		if (endpos > StockConstants.symbollist.size())
			endpos = StockConstants.symbollist.size();
		if (endpos == startpos)
			getSharePriceForDate((String) StockConstants.symbollist.get(startpos));

		for (int i = startpos; i < endpos; i++)
		{
			try
			{
				getSharePriceForDate((String) StockConstants.symbollist.get(i));
			}
			catch (Exception e)
			{
				// ln("Not able to load data for date " + (String)
				// StockConstants.symbollist.get(i));
			}
		}
		SplitThreadForEOD.addCounter();

	}

	public static void getSharePriceForDate()
	{
		StringBuffer returnStr = new StringBuffer();

		// load all data for first time
		try
		{

			StringBuffer sb = new StringBuffer();

			String url = StockConstants.IGSROOT + "getcsvfordate.jsp?exchange=nse&symbol=" + "output.csv";

			BufferedReader rd = Utility.getDataFromURL(url);
			String line;
			// // ln("getting data for "+name);
			StringBuffer strBuffer = new StringBuffer();
			while ((line = rd.readLine()) != null)
			{
				returnStr.append(line + "\n");
			}

			rd.close();
			File f = new File(StockConstants.INSTALL_DIR + "/output.csv");
			if (f.exists())
				f.delete();
			RandomAccessFile rfile = new RandomAccessFile(StockConstants.INSTALL_DIR + "/output.csv", "rw");
			rfile.writeBytes(returnStr.toString());
			rfile.close();
		}
		catch (Exception e)
		{
		}
		// TODO Auto-generated method stub
		StockConstants.LogBuffer.append("Last EOD File written\n");
	}

	public static String getSharePriceForDate(String date)
	{
		StringBuffer returnStr = new StringBuffer();

		// load all data for first time
		try
		{

			StringBuffer sb = new StringBuffer();
			StringTokenizer st = new StringTokenizer(date, "-");
			String dd = st.nextToken();
			if (dd.length() == 1)
			{
				dd = "0" + dd;
			}
			String mm = st.nextToken().toUpperCase();
			String yy = st.nextToken();

			String url = StockConstants.IGSROOT + "getcsvfordate.jsp?exchange=nse&symbol=" + "cm" + dd + mm + yy + "bhav.csv";

			BufferedReader rd = Utility.getDataFromURL(url);
			String line;
			// // ln("getting data for "+name);
			StringBuffer strBuffer = new StringBuffer();
			while ((line = rd.readLine()) != null)
			{
				returnStr.append(line + "\n");
			}

			rd.close();
			return returnStr.toString();
		}
		catch (Exception e)
		{
		}
		return "";
	}

	public static void saveOutPut()
	{
		StringBuffer returnStr = new StringBuffer();
		BufferedReader rd = null;
		RandomAccessFile r = null;
		// load all data for first time
		try
		{

			StringBuffer sb = new StringBuffer();
			String url = StockConstants.IGSROOT + "getcsvfordate.jsp?exchange=nse&symbol=output.csv";

			rd = Utility.getDataFromURL(url);
			String line;
			// // ln("getting data for "+name);
			StringBuffer strBuffer = new StringBuffer();
			while ((line = rd.readLine()) != null)
			{
				returnStr.append(line + "\n");
			}

			File f = new File(StockConstants.INSTALL_DIR + "/output.csv");
			if (f.exists())
				f.delete();
			r = new RandomAccessFile(StockConstants.INSTALL_DIR + "/output.csv", "rw");
			r.writeBytes(returnStr.toString());

		}
		catch (Exception e)
		{
		}
		finally
		{
			try
			{
				rd.close();
				r.close();
			}
			catch (Exception e1)
			{

			}

		}
	}

	public static String returnFileContent(String fileName)
	{
		StringBuffer returnStr = new StringBuffer();
		BufferedReader rd = null;
		// load all data for first time
		try
		{

			StringBuffer sb = new StringBuffer();
			String url = StockConstants.IGSROOT + "getcsvfordate.jsp?exchange=nse&symbol=" + fileName;

			rd = Utility.getDataFromURL(url);
			String line;
			// // ln("getting data for "+name);
			StringBuffer strBuffer = new StringBuffer();
			while ((line = rd.readLine()) != null)
			{
				returnStr.append(line + "\n");
			}
		}
		catch (Exception e)
		{
			return null;
		}
		return returnStr.toString();
	}

	// method for getting all listed securities

	public static void saveAllSecurities(String date, String filename, String indexname)
	{
		StockConstants.symbollist = new ArrayList();
		SplitThread.counter = 0;
		String symbol = "";
		StringBuffer sb = new StringBuffer();
		if (filename == null)
		{
			try
			{
				String url = StockConstants.IGSROOT + "getfile.jsp?filename=allnsestock.csv";
				BufferedReader rd = Utility.getDataFromURL(url);
				String line;

				while ((line = rd.readLine()) != null)

				{
					try
					{
						sb.append(line + "\n");
						StringTokenizer st = new StringTokenizer(line, ",");
						symbol = st.nextToken();
						StockConstants.symbollist.add(symbol);
					}
					catch (Exception e)
					{
						StockConstants.LogBuffer.append("Element " + symbol + " Not able to fetch,not added in fetching list");
					}
				}
				RandomAccessFile r = new RandomAccessFile(StockConstants.INSTALL_DIR + "/allnsestock.csv", "rw");
				r.writeBytes(sb.toString());
				r.close();

			}
			catch (Exception e)
			{
				StockConstants.LogBuffer.append("Not able to fetch data , please try later");
				SplitThread.counter = StockConstants.NO_OF_THREAD;
				return;
			}

		}
		else
		{
			try
			{
				StockConstants.symbollist = new ArrayList();
				RandomAccessFile file = new RandomAccessFile(filename, "r");
				String line = "";
				while ((line = file.readLine()) != null)
				{
					StockConstants.symbollist.add(line);
				}
			}
			catch (Exception e)
			{
				// ln("Not able to fetch data ,Check the file mentioned");
				SplitThread.counter = StockConstants.NO_OF_THREAD;
				;

				return;

			}

		}

		try
		{
			if (StockConstants.symbollist == null || StockConstants.symbollist.size() == 0)
			{
				SplitThread.counter = StockConstants.NO_OF_THREAD;
				;
				// ln("Symbol list is empty");
				return;
			}
			if (StockConstants.symbollist.size() < StockConstants.NO_OF_THREAD)
			{
				loadAllDataFromPosToPOs(date, 0, StockConstants.symbollist.size());
				SplitThread.counter = StockConstants.NO_OF_THREAD;
				;
			}
			else
			{
				StockConstants.splitno = StockConstants.symbollist.size() / StockConstants.NO_OF_THREAD;
				int threadcounter = 1;
				for (int i = 0; i < StockConstants.symbollist.size();)
				{

					if (threadcounter == StockConstants.NO_OF_THREAD)
					{
						Runnable runnable = new SplitThread(i, StockConstants.symbollist.size());

						// Create the thread supplying it with the runnable
						// object
						Thread thread = new Thread(runnable);
						thread.start();
						break;
					}
					Runnable runnable = new SplitThread(i, i + StockConstants.splitno - 1);

					// Create the thread supplying it with the runnable object
					Thread thread = new Thread(runnable);
					thread.start();

					i = i + StockConstants.splitno;
					threadcounter++;
				}
			}

		}
		catch (Exception e)
		{
		}
	}

	public static void loadAllDataFromPosToPOs(String date, int startpos, int endpos)
	{
		if (endpos > StockConstants.symbollist.size())
			endpos = StockConstants.symbollist.size();
		for (int i = startpos; i < endpos; i++)
		{
			try
			{
				loadAllData(date, (String) StockConstants.symbollist.get(i));
				updateCounter();
			}
			catch (Exception e)
			{
				// ln("Not able to load data for symbol " + (String)
				// StockConstants.symbollist.get(i));
			}
		}
		SplitThread.addCounter();
	}

	public synchronized static void updateCounter()
	{
		InstallScreen.fileprocessed++;
	}

	public static void loadAllData(String startDate, String name) throws Exception
	{
		FileOutputStream stream = null;
		File f = new File(StockConstants.INSTALL_DIR + "/data/" + name + ".csv");
		if (f.exists())
		{
			StockConstants.LogBuffer.append("Data exists for " + name + ",Skipping...\n");

			return;
		}

		StringBuffer returnStr = new StringBuffer();

		// load all data for first time
		StringBuffer sb = new StringBuffer();
		StringTokenizer st = new StringTokenizer(startDate, "-");
		String dd = st.nextToken();
		String mm = st.nextToken();
		String yy = st.nextToken();

		Date d = new Date();
		int date = d.getDate();

		int month = d.getMonth() + 1;
		int year = d.getYear() + 1900;

		String url = StockConstants.IGSROOT + "downloadhistoricaldata.jsp?exchange=nse&symbol=" + URLEncoder.encode(name);
		BufferedReader rd = Utility.getDataFromURL(url);
		String line;

		// // ln("getting data for "+name);
		while ((line = rd.readLine()) != null)
		{
			returnStr.append(line + "\n");
		}

		try
		{
			stream = new FileOutputStream(StockConstants.INSTALL_DIR + "/data/" + name + ".csv");
			stream.write(returnStr.toString().getBytes());
			stream.close();
			StockConstants.LogBuffer.append("Data saved for " + name + "\n");
			rd.close();
		}
		catch (Exception e)
		{
			StockConstants.LogBuffer.append("Not able to save data for " + name + "\n");
			stream.close();
			rd.close();
		}
		// loadResultData(name);
	}

	
	public static void loadResultData(String name) throws Exception
	{

		FileOutputStream stream = null;
		StringBuffer returnStr = new StringBuffer();

		// load all data for first time
		StringBuffer sb = new StringBuffer();

		// saving result
		String url = StockConstants.IGSROOT + "downloadresult.jsp?exchange=nse&symbol=" + URLEncoder.encode(name);
		BufferedReader rd = Utility.getDataFromURL(url);
		String line;

		rd = Utility.getDataFromURL(url);

		// // ln("getting data for "+name);
		while ((line = rd.readLine()) != null)
		{
			returnStr.append(line + "\n");
		}

		try
		{
			stream = new FileOutputStream(StockConstants.INSTALL_DIR + "/result/" + name + ".txt");
			stream.write(returnStr.toString().getBytes());
			stream.close();
			rd.close();
			StockConstants.LogBuffer.append("Result saved for " + name + "\n");
			// ln("Result saved for " + name + "\n");

		}
		catch (Exception e)
		{
			stream.close();
			rd.close();
			StockConstants.LogBuffer.append("Not able to save result for " + name + "\n");
			// ln("Not able to save result for " + name + "\n");
		}
	}

	/**
	 * 
	 */
	public static Vector getIntradayDataForDate(String date, String symbol)
	{
		String fileContent = "";
		Vector returnVector = new Vector();
		try
		{
			fileContent = Utility.getFileContent(StockConstants.INSTALL_DIR + "/intraday/" + date + "/" + symbol + ".csv");
		}
		catch (Exception e)
		{

			try
			{
				fileContent = Utility.getUrlContent(StockConstants.INTRADATA_DATAURL + "symbol=" + symbol + "&date=" + date);
				File f = new File(StockConstants.INSTALL_DIR + "/intraday/" + date);
				f.mkdirs();
				Utility.saveContent(StockConstants.INSTALL_DIR + "/intraday/" + date + "/" + symbol + ".csv", fileContent);
			}
			catch (Exception e1)
			{
				return returnVector;
			}
		}
		StringTokenizer st = new StringTokenizer(fileContent, "\n");
		long totalvolume = 0;
		while (st.hasMoreTokens())
		{
			try
			{
				String line = st.nextToken();
				StringTokenizer linest = new StringTokenizer(line, ",");
				String ts = linest.nextToken().trim();
				String price = linest.nextToken().trim();
				String volume = linest.nextToken().trim();
				long l = Long.parseLong(volume);
				HashMap hs = new HashMap();
				hs.put(MainGraphComponent.Date, ts);
				hs.put(MainGraphComponent.Open, price);
				hs.put(MainGraphComponent.High, price);
				hs.put(MainGraphComponent.Low, price);
				hs.put(MainGraphComponent.Close, price);
				hs.put(MainGraphComponent.Volume, volume);
				totalvolume = totalvolume + l;
				hs.put(MainGraphComponent.TotalVolume, totalvolume);
				returnVector.add(hs);
			}
			catch (Exception e)
			{

			}
		}

		return returnVector;
	}

	public static void unloadStock(String s)
	{
		fileContentHash.remove(s);
		// TODO Auto-generated method stub

	}

}

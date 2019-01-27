/*
 * Created on Feb 1, 2007
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.component;
import com.intraday.GetTicByTicData;
import com.intraday.HttpMessage;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.File;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.RandomAccessFile;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;

import com.stockfaxforu.config.ConfigUtility;
import com.stockfaxforu.formulabuilder.MessageDiaglog;
import com.stockfaxforu.getolddataforu.loader.Loader;
import com.stockfaxforu.query.DataBaseInMemory;
import com.stockfaxforu.query.Function;
import com.stockfaxforu.strategy.BuySellStrategy;
import com.stockfaxforu.strategy.StrategyUtility;
import com.stockfaxforu.util.ConvertDataUtility;
import com.stockfaxforu.util.DateUtility;
import com.stockfaxforu.util.GetColor;
import com.stockfaxforu.util.ManageIndicator;
import com.stockfaxforu.util.StockConstants;
import com.stockfaxforu.util.Utility;
/**
 * @author sdeopu1
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class MainGraphComponent extends MYDimension implements MainGraphInterface
{
	public static final String ALERTTEXT = "ALERTTEXT";
	public static String BidPrice="BIDPRICE";
	public static String BidQty="BIDQTY";
	public static String OfferPrice="OFFERPRICE";
	public static String OfferQty="OFFERQTY";

	public static String OTHERCOMPONENT="OTHERCOMPONENT";
	public static String COLOR1="COLOR1";
	public static String COLOR2="COLOR2";
	public static String COLOR3="COLOR3";

//	public static String COMPONENT="COMPONENT";
	
	public static String PLOT1="PLOT1";
	public static String PLOT2="PLOT2";
	public static String PLOT3="PLOT3";
	
	public static String ALERT="ALERT";
	public int screenId=1;
	public static String BUY = "BUY";
	public static String SELL = "SELL";
	public static String BUYIND = "BUYIND";
	public static String SELLIND = "SELLIND";
	//public static String ALERTIND = "ALERTIND";

	public boolean draggingComponent = false;
	public String id = "";
	public String symbolName;
	public String symbolNameFull = "";
	public Vector inputdata = null;
	public Vector indicatorVector = new Vector();
	public float minprice;
	public float maxprice;
	public long maxvolume;
	public long averagevolume = 0;
	public int noofx;
	public int[] xpos = null;
	public int ymaxposinmainpanel = 0;
	public static int ZOOMOUT = 1;
	public static int ZOOMIN = 2;

	public static String Symbol="SYMBOL";

	
	public static String DayOpen = "DAYOPEN";
	public static String DayHigh = "DAYHIGH";
	public static String DayLow = "DAYLOW";
	public static String DayChange="DAYCHANGE";
	
	
	public static String Open = "OPEN";
	
	public static String High = "HIGH";
	public static String Low = "LOW";
	public static String Close = "CLOSE";
	public static String AvgPrice = "AVGPRICE";
	public static String Volume = "VOLUME";
	public static String TotalVolume="TOTALVOLUME";
	public static String Date = "DATE";
	
	public static int MINZOOMOUTSIZE = 50;
	public static int MONTHLYCOUNTER = 20;
	public static int WEEKLYCOUNTER = 5;
	public static int ZOOMCOUNTER = 0;
	public static int SMA = 1;
	public static int EMA = 2;
	public static int MMA = 3;
	public static int WMA = 4;
	public static int BB = 10;
	public static int MACD = 11;
	//display type 
	public static int LINEGRAPH = 1;
	public static int OHLCGRAPH = 2;
	public static int CANDLEGRAPH = 3;
	public int graphType = 1;
	public int xvalforwriting = 50;
	public int yvalforwriting = 20;
	public int totpanelsize = 800;
	public int chartResolution = 1;
	public String startDate = "2007-01-01";
	public String endDate = "-1";
	public static int DAILY = 1;
	public static int EVE2DAY = 2;
	public static int EVE3DAY = 3;
	public static int EVE4DAY = 4;
	public static int WEEKLY = 5;
	public static int MONTHLY = 20;
	public int inddisypos = 50;
	public boolean isintraday=false;
	public String expirydate="";
	public String defaultStrtetgyText="";
	int intstartx,intendx,intstarty,intendy;
	
	public void setInitalXY(int intstartx,int intendx,int intstarty,int intendy)
	{
		this.intstartx = intstartx;
		this.intendx = intendx;
		this.intstarty = intstarty;
		this.intendy = intstartx;
		
	}
	Color[] col = { Color.orange, Color.cyan, Color.magenta, Color.yellow, Color.red };
	int dranewy = 0;
	Indicator indicator = null;
	public void setID(Indicator indicator)
	{
		StringBuffer strBuf = new StringBuffer();
		String name = ManageIndicator.getIndicatorNameFromID(indicator.indicatorType);
		if(indicator.indicatorType  == indicator.CUSTOM)
		{
			name = indicator.indicatorName;
			strBuf.append(name);
	
		}
		else
		{	


			strBuf.append(name + "(");
			if (indicator.noofperiods == 0)
				strBuf.append(")");
			if (indicator.noofperiods == 1)
				strBuf.append(indicator.period1 + ")");
			if (indicator.noofperiods == 2)
				strBuf.append(indicator.period1 + "," + indicator.period2 + ")");
			if (indicator.noofperiods == 3)
				strBuf.append(indicator.period1 + "," + indicator.period2 + "," + indicator.period3 + ")");
		}

		this.id = strBuf.toString();
		this.indicator = indicator;
	}
	public static String createID(Indicator indicator)
	{
		StringBuffer strBuf = new StringBuffer();
		String name = ManageIndicator.getIndicatorNameFromID(indicator.indicatorType);
		
		if(indicator.indicatorType  == indicator.CUSTOM)
		{
			name = indicator.indicatorName;
			strBuf.append(name);
	
		}
		else
		{	
	
			strBuf.append(name + "(");
			if (indicator.noofperiods == 0)
				strBuf.append(")");
			if (indicator.noofperiods == 1)
				strBuf.append(indicator.period1 + ")");
			if (indicator.noofperiods == 2)
				strBuf.append(indicator.period1 + "," + indicator.period2 + ")");
			if (indicator.noofperiods == 3)
				strBuf.append(indicator.period1 + "," + indicator.period2 + "," + indicator.period3 + ")");
		
		}
		
		return strBuf.toString();
	}
	public String getID()
	{
		return this.id;
	}
	public Indicator getIndicator()
	{
		return this.indicator;
	}

	
	public MainGraphComponent(int startx, int starty, int endx, int endy, int panelsize, int componenttype)
	{
		super(startx, starty, endx, endy, componenttype);
		this.totpanelsize = panelsize;
		this.startx = startx;
		this.endx = endx;
		this.starty = panelsize - starty;
		this.endy = totpanelsize - endy;
		this.totpanelsize = panelsize;
		this.componenttype = componenttype;


		
	}
	public int getXPosFromDate(String date)
	{
		for (int x = 0; x < this.inputdata.size(); x++)
		{
			HashMap hs = (HashMap) this.inputdata.elementAt(x);
			String s = (String) hs.get(MainGraphComponent.Date);
//			if (s.equalsIgnoreCase(date))
			if (compareDate(s, date))
			{
				int xpos = this.getXPosition(x - 1);
				return xpos;
			}
		}
		int xpos = this.getXPosition(this.inputdata.size() - 1);
		return xpos;
	}
	public static boolean compareDate(String arraydate,String paradate)
	{
		try
		{
			Calendar arraycal = Calendar.getInstance();
			Calendar paradatecal = Calendar.getInstance();
			
			if(arraydate.indexOf(":") == -1)
			{
				StringTokenizer st = new StringTokenizer(arraydate,"-");
				int yy = Integer.parseInt(st.nextToken());
				int mm = Integer.parseInt(st.nextToken());
				int dd = Integer.parseInt(st.nextToken());
				arraycal.set(yy,mm,dd);
			}
			else
			{
				StringTokenizer st = new StringTokenizer(arraydate,"-");
				StringTokenizer datepart = new StringTokenizer(st.nextToken(),"/");
				int mm = Integer.parseInt(datepart.nextToken());
				int dd = Integer.parseInt(datepart.nextToken());
				int yy = Integer.parseInt(datepart.nextToken());
				
				StringTokenizer timepart = new StringTokenizer(st.nextToken(),":");
				int hh = Integer.parseInt(timepart.nextToken());
				int min = Integer.parseInt(timepart.nextToken());
				int ss = Integer.parseInt(timepart.nextToken());
				
				arraycal.set(yy,mm,dd,hh,min,ss);
				
			}
			if(paradate.indexOf(":") == -1)
			{
				StringTokenizer st = new StringTokenizer(paradate,"-");
				int yy = Integer.parseInt(st.nextToken());
				int mm = Integer.parseInt(st.nextToken());
				int dd = Integer.parseInt(st.nextToken());
				paradatecal.set(yy,mm,dd);
			}
			else
			{
				StringTokenizer st = new StringTokenizer(paradate,"-");
				StringTokenizer datepart = new StringTokenizer(st.nextToken(),"/");
				int mm = Integer.parseInt(datepart.nextToken());
				int dd = Integer.parseInt(datepart.nextToken());
				int yy = Integer.parseInt(datepart.nextToken());
				
				StringTokenizer timepart = new StringTokenizer(st.nextToken(),":");
				int hh = Integer.parseInt(timepart.nextToken());
				int min = Integer.parseInt(timepart.nextToken());
				int ss = Integer.parseInt(timepart.nextToken());
				
				paradatecal.set(yy,mm,dd,hh,min,ss);
				
			}
			if(arraycal.after(paradatecal))
				return true;
			else
				return false;
			
		}
		catch(Exception e)
		{
			return false;
		}
	}
	public static void main(String[] args)
	{
		MainGraphComponent graphcompo = new MainGraphComponent(1,1,1,1,1,1);
		graphcompo.loadFileForIntraday("TCS", "24JUN2010");
/*		MainGraphComponent convert = new MainGraphComponent(0, 0, 800, 800, 0, 0);
		try
		{
			convert.loadFile("SATYAMCOMP");
			convert.setHighLowValues();
			HashMap hs = (HashMap) convert.inputdata.elementAt(10);
			float low = Float.parseFloat((String) hs.get(Low));
			//			// ln(low);
			//			// ln(convert.getYPosition(low));
		}
		catch (Exception e)
		{
		}
	*/
	
	
	}
	public float getPriceFromYPos(int yposition)
	{
		yposition = yposition - starty;
		float ratio = (float) (starty - endy) / (maxprice - minprice);
		//		float price = ((float)(( - yposition))  
		float price = ((starty - endy) + ratio * minprice - yposition) / ratio;
		return Utility.floatDataAtTwoPrecision(price);
	}
	public int getYPosition(float price)
	{
		int yposition = 0;
		int height = starty - endy;
		float ratio = (float) (starty - endy) / (maxprice - minprice);
		yposition = (int) ((starty - endy) - ratio * (price - minprice));
		int yposss = (yposition + starty);
		if (yposss < starty)
			yposss = starty;
		if (yposss > (starty + height))
			yposss = (starty + height);
		return yposss;
	}
	public int getXWidth(int datapos)
	{
		int xposition = 0;
		float val1 = (float) datapos / (float) noofx;
		val1 = val1 * (endx - startx);
		return (int) (val1 / 4);
	}
	public int getXPosition(int datapos)
	{
		int xposition = 0;
		float val1 = (float) datapos / (float) noofx;
		val1 = val1 * (endx - startx);
		xposition = (int) val1 + startx;
		return xposition;
	}
	public Vector getDataAsSelected(int typeofwork)
	{
		Vector retVector = new Vector();
		int size = this.inputdata.size();
		if (typeofwork == ZOOMOUT || typeofwork == ZOOMIN)
		{
			if (size < MINZOOMOUTSIZE)
				return this.inputdata;
			int val = size;
			for (int x = 0; x < ZOOMCOUNTER; x++)
			{
				if (val < MINZOOMOUTSIZE)
					break;
				val = (int) (val * 0.8);
			}
			for (int x = val; x < size; x++)
				retVector.addElement(this.inputdata.elementAt(x));
			return retVector;
		}
		if (typeofwork == MONTHLY || typeofwork == WEEKLY)
		{
			int incrementcounter = 1;
			if (typeofwork == MONTHLY)
				incrementcounter = MONTHLYCOUNTER;
			if (typeofwork == WEEKLY)
				incrementcounter = WEEKLYCOUNTER;
			for (int x = 0; x < size; x++)
			{
				int modval = x % incrementcounter;
				if (modval == 0)
					retVector.addElement(inputdata.elementAt(x));
			}
			return retVector;
		}
		return this.inputdata;
	}
	public boolean isDateGreaterEqual(String date)
	{
		StringTokenizer st = new StringTokenizer(date, "-");
		int yy = Integer.parseInt(st.nextToken());
		int mon = Integer.parseInt(st.nextToken());
		int dd = Integer.parseInt(st.nextToken());
		StringTokenizer st1 = new StringTokenizer(startDate, "-");
		int yy1 = Integer.parseInt(st1.nextToken());
		int mon1 = Integer.parseInt(st1.nextToken());
		int dd1 = Integer.parseInt(st1.nextToken());

		Date d = new Date(yy,mon,dd);
		Date d1 = new Date(yy1,mon1,dd1);
		
		return DateUtility.compareDate(d,d1);
	}
	public boolean isDateLessEqual(String date)
	{
		StringTokenizer st = new StringTokenizer(date, "-");
		int yy = Integer.parseInt(st.nextToken());
		int mon = Integer.parseInt(st.nextToken());
		int dd = Integer.parseInt(st.nextToken());
		StringTokenizer st1 = new StringTokenizer(endDate, "-");
		int yy1 = Integer.parseInt(st1.nextToken());
		int mon1 = Integer.parseInt(st1.nextToken());
		int dd1 = Integer.parseInt(st1.nextToken());

		Date d = new Date(yy,mon,dd);
		Date d1 = new Date(yy1,mon1,dd1);
		
		return DateUtility.compareDate(d1,d);
	}

	public void loadFile(String symbol,String enddate)
	{
		this.endDate = enddate;
		
//		RandomAccessFile stockFile=null;
//		File f = new File(StockConstants.INSTALL_DIR + "/index/" + symbol + ".csv");
		try
		{
//			stockFile = new RandomAccessFile(StockConstants.INSTALL_DIR + "/data/" + symbol + ".csv", "r");
			//			RandomAccessFile stockFile =	new RandomAccessFile(StockConstants.INSTALL_DIR + "/data" + StockConstants.SelectedStock + "_NS.csv",	"r");
			//		ArrayList v = new ArrayList();
			Vector dataVec = null;

			try
			{
				dataVec = Loader.getFileContent(StockConstants.STARTDATE, symbol,"");
				
			}
			catch(Exception e)
			{
				return;
			}

			this.symbolName = symbol;
			String newline = "";
			StringBuffer outputBuffer = new StringBuffer();
			HashMap hs = new HashMap();
//			stockFile.readLine();
			boolean tocollect = false;
			int newval = 0;
			Vector inputdata1 = new Vector();
			this.inputdata = new Vector();
			int xx = 0;

			for(int i=0;i<dataVec.size();i++)
			{
				try
				{
					HashMap inpuths = (HashMap)dataVec.get(i);
					String mydate =(String)inpuths.get(Date);
					if (!isDateGreaterEqual(mydate))
						continue;
					if(!isDateLessEqual(mydate))
					{
						break;	
					}
					HashMap output = new HashMap();	
					output.put(Date, inpuths.get(Date));
					output.put(Open, inpuths.get(Open));
					output.put(High, inpuths.get(High));
					output.put(Low, inpuths.get(Low));
					output.put(Close, inpuths.get(Close));
					output.put(Volume, inpuths.get(Volume));

					inputdata.add(output);
				}
				catch (Exception e1)
				{
				}
			}
/*			
			for (int i = 0; i < inputdata1.size(); i++)
			{
				int mod = (i + 1) % this.chartResolution;
				if (mod == 0)
				{
					hs = getHashMapForRes(inputdata1, i + 1);
					inputdata.add(hs);

				}
			}
*/
			setResolution(this.chartResolution);
			this.noofx = inputdata.size();

//			stockFile.close();
		}
		catch (Exception e)
		{
	//		e.printStackTrace();
//			stockFile.close();
			
		}

		
	}
	public boolean dataexception=true;
	public Vector originaldata;
	public void loadFile(String symbol) throws Exception
	{
		
//		RandomAccessFile stockFile=null;
//		File f = new File(StockConstants.INSTALL_DIR + "/index/" + symbol + ".csv");
		try
		{
//			stockFile = new RandomAccessFile(StockConstants.INSTALL_DIR + "/data/" + symbol + ".csv", "r");
			//			RandomAccessFile stockFile =	new RandomAccessFile(StockConstants.INSTALL_DIR + "/data" + StockConstants.SelectedStock + "_NS.csv",	"r");
			//		ArrayList v = new ArrayList();
			Vector dataVec = null;

			try
			{
				dataexception=false;
				dataVec = Loader.getFileContent(StockConstants.STARTDATE, symbol,"");
				
			}
			catch(Exception e)
			{
				dataexception=true;
				return;
			}

			this.symbolName = symbol;
			String newline = "";
			StringBuffer outputBuffer = new StringBuffer();
			HashMap hs = new HashMap();
//			stockFile.readLine();
			boolean tocollect = false;
			int newval = 0;
			Vector inputdata1 = new Vector();
			this.inputdata = new Vector();
			int xx = 0;

			for(int i=0;i<dataVec.size();i++)
			{
				try
				{
					HashMap inpuths = (HashMap)dataVec.get(i);
					String mydate =(String)inpuths.get(Date);
					if (!isDateGreaterEqual(mydate))
						continue;
					HashMap output = new HashMap();	
					output.put(Date, inpuths.get(Date));
					output.put(Open, inpuths.get(Open));
					output.put(High, inpuths.get(High));
					output.put(Low, inpuths.get(Low));
					output.put(Close, inpuths.get(Close));
					output.put(Volume, inpuths.get(Volume));

					inputdata.add(output);
				}
				catch (Exception e1)
				{
				}
			}
/*			
			for (int i = 0; i < inputdata1.size(); i++)
			{
				int mod = (i + 1) % this.chartResolution;
				if (mod == 0)
				{
					hs = getHashMapForRes(inputdata1, i + 1);
					inputdata.add(hs);

				}
			}
*/
			setResolution(this.chartResolution);
			this.noofx = inputdata.size();

//			stockFile.close();
		}
		catch (Exception e)
		{
	//		e.printStackTrace();
//			stockFile.close();
			
		}
	}	
	public void setResolution(int resolution)
	{
		this.chartResolution = resolution;
		Vector v = new Vector();
		if(this.chartResolution==WEEKLY || this.chartResolution==MONTHLY)
		{
			v = getSpecialResolution(inputdata);
		}
		else
		{
			int j=0;
			for (int i = inputdata.size()-1; i >= 0; i--)
			{
				int mod = (j) % this.chartResolution;
				if (mod == 0)
				{
					HashMap hs = getHashMapForRes(inputdata, i);
					v.addElement(hs);

				}
				j++;
			}
			
		}
		Vector v1 = new Vector();
		for (int i = v.size()-1; i >= 0; i--)
		{
			v1.add(v.get(i));
		}
		inputdata = v1;
		this.noofx = inputdata.size();
	}
	public Vector setResolution(Vector inputdata1)
	{
		Vector v = new Vector();

		for (int i = 0; i < inputdata1.size(); i++)
		{
			int mod = (i + 1) % this.chartResolution;
			if (mod == 0)
			{
				HashMap hs = getHashMapForRes(inputdata1, i + 1);
				v.addElement(hs);

			}
		}
		this.noofx = v.size();
		return v;	
	}
	public static Vector setResolutionIntraday(Vector inputdata1,long requiredTimeDiff)
	{
		int minute = 1;
		HashMap hs = null;
		String dateStr = null ;
		String oldTime = null;
		String newTime = null;
		Vector retVector = new Vector();

		int oldPosition=0;
		
		for (int i=0;i<inputdata1.size();i++)
		{
			hs = (HashMap)inputdata1.elementAt(i);
			dateStr = (String)hs.get(Date) ;
			newTime = dateStr;
			if(i==0)
			{
				oldTime = dateStr;
				oldPosition = 0;
			}	
			else
			{
				if(isTimeDiferenceGreater(requiredTimeDiff, oldTime,newTime))
				{	
					HashMap hstemp  = getHashMapForResStatic(inputdata1, oldPosition,i);
					retVector.add(hstemp);
					oldTime = newTime;
					oldPosition = i;
				}
			}	
			
		}
		String myoldtime= (String)((HashMap)inputdata1.get(oldPosition)).get(Date);
		String mynewtime= (String)((HashMap)inputdata1.get(inputdata1.size()-1)).get(Date);
		if(isTimeDiferenceGreater(1, oldTime,newTime))
		{
			HashMap hstemp  = getHashMapForResStatic(inputdata1, oldPosition,inputdata1.size()-1);
			retVector.add(hstemp);
			
		}
		
		
/*		for(int i=(inputdata1.size()-1);i >= 0 ; i--)
		{
			hs = (HashMap)inputdata1.elementAt(i);
			dateStr = (String)hs.get(Date) ;
			newTime = dateStr;
			if(i==(inputdata1.size()-1))
			{
				oldTime = dateStr;
				oldPosition = inputdata1.size()-1;
//				retVector.addElement(hs);	
			}	
			else
			{
				if(isTimeDiferenceGreater(requiredTimeDiff, newTime, oldTime))
				{	
					HashMap hstemp  = getHashMapForResStatic(inputdata1, i+1, oldPosition);
					retVector.add(0, hstemp);
					oldTime = newTime;
					oldPosition = i;
				}
			}	
		}

		for(int i=0;i<retVector.size();i++)
		{
			hs = (HashMap)retVector.elementAt(i);
			dateStr = (String)hs.get(Date) ;
	//		// (dateStr + "  ");	
		}
*/

	//	System.out.println(retVector);
		return retVector;
	}
	
	
	public static boolean isTimeDiferenceGreater(long requireDiff,String oldTime,String newTime)
	{
		StringTokenizer oldMainst = new StringTokenizer(oldTime,"-");
		String oldDate = oldMainst.nextToken();	
		StringTokenizer oldDatest = new StringTokenizer(oldDate,"/");
		int mmold = Integer.parseInt(oldDatest.nextToken()) - 1;
		int ddold = Integer.parseInt(oldDatest.nextToken());
		int yyold = Integer.parseInt(oldDatest.nextToken());
		
			
		StringTokenizer oldSt = new StringTokenizer(oldMainst.nextToken(),":");
		int hhOld = Integer.parseInt(oldSt.nextToken());
		int mmOld = Integer.parseInt(oldSt.nextToken());
		int ssOld = Integer.parseInt(oldSt.nextToken());
		

		StringTokenizer newMainst = new StringTokenizer(newTime,"-");
		String newDate = newMainst.nextToken();	
		StringTokenizer newDatest = new StringTokenizer(newDate,"/");
		int mmnew = Integer.parseInt(newDatest.nextToken()) - 1;
		int ddnew = Integer.parseInt(newDatest.nextToken());
		int yynew = Integer.parseInt(newDatest.nextToken());

		
		StringTokenizer newSt = new StringTokenizer(newMainst.nextToken(),":");
		int hhNew = Integer.parseInt(newSt.nextToken());
		int mmNew = Integer.parseInt(newSt.nextToken());
		int ssNew = Integer.parseInt(newSt.nextToken());
		
		
		GregorianCalendar gcOld = new GregorianCalendar(yyold, mmold, ddold, hhOld, mmOld, ssOld);
	    GregorianCalendar gcNew = new GregorianCalendar(yynew, mmnew, ddnew, hhNew,mmNew,ssNew);
	    
	    long oldT = gcOld.getTime().getTime();
	    long newT = gcNew.getTime().getTime();
	    
	    long difference = (newT - oldT)/1000;
    //  // ln("oldtime=" + oldTime + "   newtime=" + newTime + " Elapsed milliseconds: " + difference);

	    if(difference >= requireDiff)
	    	return true;
	    else
	    	return false;	


	}
	public static boolean checkIndexFile(String file)
	{
		return false;
//		File f = new File(StockConstants.INSTALL_DIR + "/index/" + file + ".csv");
//		return f.exists();
		
		
	}
	public static boolean checkFile(String file)
	{
		File f = new File(StockConstants.INSTALL_DIR + "/data/" + file + ".csv");
		return f.exists();
		
		
	}
	
	public void loadIndexFile(String symbol) throws Exception
	{
		
		RandomAccessFile stockFile=null;
		try
		{
			stockFile = new RandomAccessFile(StockConstants.INSTALL_DIR + "/index/" + symbol + ".csv", "r");
			//			RandomAccessFile stockFile =	new RandomAccessFile(StockConstants.INSTALL_DIR + "/data" + StockConstants.SelectedStock + "_NS.csv",	"r");
			//		Vector v = new Vector();
			this.symbolName = symbol;
			String newline = "";
			StringBuffer outputBuffer = new StringBuffer();
			HashMap hs = new HashMap();
			stockFile.readLine();
			boolean tocollect = false;
			int newval = 0;
			Vector inputdata1 = new Vector();
			this.inputdata = new Vector();
			int xx = 0;
			while (stockFile.getFilePointer() < stockFile.length())
			{
				try
				{
					hs = new HashMap();
					newline = stockFile.readLine();
					StringTokenizer st1 = new StringTokenizer(newline, ",");
					String mydate = st1.nextToken();
					if (!isDateGreaterEqual(mydate))
						continue;
					hs.put(Date, mydate);
					//					// ln(hs.get("Date"));
					hs.put(Open, st1.nextToken().trim());
					hs.put(High, st1.nextToken().trim());
					hs.put(Low, st1.nextToken().trim());
					hs.put(Close, st1.nextToken().trim());
					hs.put(Volume, "10000");
					inputdata1.addElement(hs);
				}
				catch (Exception e1)
				{
					}
			}
			for (int i = 0; i < inputdata1.size(); i++)
			{
				int mod = (i + 1) % this.chartResolution;
				if (mod == 0)
				{
					hs = getHashMapForRes(inputdata1, i + 1);
					inputdata.addElement(hs);
				}
			}
			this.noofx = inputdata.size();
			stockFile.close();
		}
		catch (Exception e)
		{
			stockFile.close();
			
		}
	
	
	}
	
	public static Vector loadFileForIntraday(String symbol,String expirydate)
	{
		Vector[] data = GetTicByTicData.getIntraDayData(symbol,expirydate);
		if (data == null)
			return null;
		String newline = "";
		StringBuffer outputBuffer = new StringBuffer();
		HashMap hs = new HashMap();
		boolean tocollect = false;
		int newval = 0;
		Vector inputdata1 = new Vector();
		int xx = 0;
		int i = 0;
		while (i < data[0].size())
		{
			try
			{
				hs = new HashMap();
				String dataetime = (String) data[0].elementAt(i);
				StringTokenizer st = new StringTokenizer(dataetime);
				
				String[] s = st.nextToken().split("/");
				
				String time = st.nextToken();
				hs.put(Date, s[1]+"/"+s[0]+"/"+s[2]+"-"+time);
				//					// ln(hs.get("Date"));
				hs.put(Open, data[1].elementAt(i));
				hs.put(High, data[1].elementAt(i));
				hs.put(Low, data[1].elementAt(i));
				hs.put(Close, data[1].elementAt(i));
				hs.put(Volume, "100000");
				inputdata1.addElement(hs);
			}
			catch (Exception e1)
			{
			}
			i++;
		}
		return inputdata1;
	}
	public static HashMap indiaBullsHash = null;
	public static String getIndiaBullsCode(String symbol)
	{
		if (indiaBullsHash==null)
		{
			indiaBullsHash = new HashMap();
			indiaBullsHash.put("nifty", "NSE-50.NES");
			indiaBullsHash.put("cnx500", "CNX500.NES");
			indiaBullsHash.put("cnxbank", "CNXBK.NES");
			indiaBullsHash.put("niftymidcap50", "CNXMID.NES");
			
		}
		String s = (String)indiaBullsHash.get(symbol);
		if(s==null)
		{
			return symbol;
		}
		else
		{
			return s;
		}
	}
	public void loadFileForIntradayIndiaBull(String symbol) throws Exception
	{
		symbol = getIndiaBullsCode(symbol);
		String data=null;
		try
		{
			data = Utility.getUrlContent("http://www.indiabulls.com/securities/research/techanalysis/intraday.aspx?symbol="+symbol);
		}
		catch (Exception e)
		{
			throw e;
		}
		if (data == null)
			return;
		Vector inputdata1 = new Vector();
		this.inputdata = new Vector();
		int xx = 0;
		int i = 0;
		StringTokenizer st = new StringTokenizer(data,"\n");
		long oldvol=0;
		long newvol=0;
		long totvol=0;
		while (st.hasMoreTokens())
		{
			try
			{
				HashMap hs = new HashMap();
				String line = st.nextToken().trim();
				StringTokenizer linest = new StringTokenizer(line,",");
				String type = linest.nextToken();
				StringTokenizer datest = new StringTokenizer(type,":");
			
				datest.nextToken();
				String rdate1 = datest.nextToken();

				String date = linest.nextToken();
			//	String time = linest.nextToken();
				String price = linest.nextToken();
				datest = new StringTokenizer(date,":");
				String date1 = datest.nextToken();
				String date2 = datest.nextToken();
				String date3 = datest.nextToken();
				if (Integer.parseInt(date1) < 8)
				{
					int x = Integer.parseInt(date1) + 12;
					date = x +":" + date2 + ":" + date3;
					// ln(date);
				}
				date = rdate1 + "-" + date;
				
				String chg = linest.nextToken();
				String volume = linest.nextToken();
				long l = Long.parseLong(volume);
			//	newvol = l  + 1;
				
				newvol = l - oldvol + 1;
				
				totvol = totvol + l;
				
				oldvol = l;
				String s = date +"," + price + "," + volume + "\n";
				// ln(s);
				hs.put(Date, date);
				//					// ln(hs.get("Date"));
				hs.put(Open, price);
				hs.put(High, price);
				hs.put(Low, price);
				hs.put(Close, price);
				hs.put(Volume, newvol+"");
				hs.put(TotalVolume, totvol+"");	
				inputdata.addElement(hs);
				oldvol = l;
			}
			catch (Exception e1)
			{
		//		e1.printStackTrace();
			}
			i++;
		}

		this.noofx = inputdata.size();
	}
	public void setHistIntradayFromYahoo(String symbol)
	{
		inputdata = ConvertDataUtility.getHistoricalIntradayData(symbol, "");
		this.noofx = inputdata.size();
		
	}

	
	
	public void loadFileForFutureHist(String symbol,String expirydate,String startDate,String endDate) throws Exception
	{
		
//		this.chartResolution = 3;
		this.symbolName = symbol;
		Vector[] data = GetTicByTicData.getIntraDayData(symbol,expirydate,startDate,endDate);
		if (data == null)
			return;
		String newline = "";
		StringBuffer outputBuffer = new StringBuffer();
		HashMap hs = new HashMap();
		boolean tocollect = false;
		int newval = 0;
		Vector inputdata1 = new Vector();
		this.inputdata = new Vector();
		int xx = 0;
		int i = 0;
		while (i < data[0].size())
		{
			try
			{
				hs = new HashMap();
				String dataetime = (String) data[0].elementAt(i);
				StringTokenizer st = new StringTokenizer(dataetime);
//				st.nextToken();
//				String time = st.nextToken();
				hs.put(Date, dataetime);
				//					// ln(hs.get("Date"));
				hs.put(Open, data[1].elementAt(i));
				hs.put(High, data[1].elementAt(i));
				hs.put(Low, data[1].elementAt(i));
				hs.put(Close, data[1].elementAt(i));
				hs.put(Volume, data[2].elementAt(i));
				inputdata1.addElement(hs);
			}
			catch (Exception e1)
			{
			}
			i++;
		}
		for (i = 0; i < inputdata1.size(); i++)
		{
			int mod = (i + 1) % this.chartResolution;
			if (mod == 0)
			{
				hs = getHashMapForRes(inputdata1, i + 1);
				inputdata.addElement(hs);
			}
		}
		this.noofx = inputdata.size();
	}
	
	public Vector  getSpecialResolution(Vector inputdata1)
	{
		Vector retvector = new Vector();
		int lastpos=inputdata1.size()-1;
		int firstpos=0;
		for(int i=inputdata1.size()-1;i>0;i--)
		{
			HashMap hs = (HashMap) inputdata1.get(i);
			
			String s = (String)hs.get(Date);
			String[] s1 = s.split("-");
			if ( this.chartResolution == WEEKLY)
			{
				if(i < inputdata1.size() -1)
				{
				
					Calendar c = Calendar.getInstance();
					c.set(Integer.parseInt(s1[0]), Integer.parseInt(s1[1])-1,Integer.parseInt(s1[2]));
					
					if ( c.get(Calendar.DAY_OF_WEEK)  == Calendar.FRIDAY )
					{
						HashMap h = getHashMapForRes(inputdata1,i+1,lastpos);
						retvector.add(h);
						lastpos = i;
					}
				}
			}
			else if (this.chartResolution == MONTHLY)
			{
				if(i < inputdata1.size() -1)
				{
					HashMap hs4 = (HashMap) inputdata1.get(i+1);
					
					String s4 = (String)hs4.get(Date);
					String[] s45 = s4.split("-");
					if(Integer.parseInt(s45[1]) != Integer.parseInt(s1[1]) )
					{
						HashMap h = getHashMapForRes(inputdata1,i+1,lastpos);
						retvector.add(h);
						lastpos = i;
						
					}
				}
			}
			
		}
		if(lastpos != 0)
		{
			HashMap h = getHashMapForRes(inputdata1,0,lastpos);
			retvector.add(h);
	
		}
		return retvector;
	}

	public HashMap getHashMapForRes(Vector inputdata1, int pos)
	{
		HashMap outHash = new HashMap();
		int start = pos - this.chartResolution + 1;
		if(start < 0)
			start=0;
		if(pos >= inputdata1.size())
			pos = inputdata1.size() - 1;	
		HashMap hs = (HashMap) inputdata1.elementAt(start);
		float open = Float.parseFloat((String) hs.get(Open));
		float high = Float.parseFloat((String) hs.get(High));
		float low = Float.parseFloat((String) hs.get(Low));
		long volume = 0;
		outHash.put(Open, open + "");
		hs = (HashMap) inputdata1.elementAt(pos);
		float close = Float.parseFloat((String) hs.get(Close));
		outHash.put(Close, close + "");
		outHash.put(Date, hs.get(Date));
		for (int i = start; i <= pos; i++)
		{
			hs = (HashMap) inputdata1.elementAt(i);
			float tmphigh = Float.parseFloat((String) hs.get(High));
			float tmplow = Float.parseFloat((String) hs.get(Low));
			long tmpvol = Long.parseLong((String) hs.get(Volume));
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


	//	volume = volume / this.chartResolution;
		outHash.put(High, high + "");
		outHash.put(Low, low + "");
		outHash.put(Volume, volume + "");
		return outHash;
	}
	public HashMap getHashMapForRes(Vector inputdata1, int startPos,int endPosition)
	{
		HashMap outHash = new HashMap();
		int start = startPos;
		HashMap hs = (HashMap) inputdata1.elementAt(start);
		float open = Float.parseFloat((String) hs.get(Open));
		float high = Float.parseFloat((String) hs.get(High));
		float low = Float.parseFloat((String) hs.get(Low));
		long volume = 0;
		outHash.put(Open, open + "");
		hs = (HashMap) inputdata1.elementAt(endPosition);
		float close = Float.parseFloat((String) hs.get(Close));
		outHash.put(Close, close + "");
		outHash.put(Date, hs.get(Date));
		for (int i = startPos; i <= endPosition; i++)
		{
			hs = (HashMap) inputdata1.elementAt(i);
			float tmphigh = Float.parseFloat((String) hs.get(High));
			float tmplow = Float.parseFloat((String) hs.get(Low));
			long tmpvol = Long.parseLong((String) hs.get(Volume));
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
	//	volume = volume / this.chartResolution;
		outHash.put(High, high + "");
		outHash.put(Low, low + "");
		outHash.put(Volume, volume + "");
		return outHash;
	}

	public static HashMap getHashMapForResStatic(Vector inputdata1, int startPos,int endPosition)
	{
		
		if(startPos+1 == endPosition)
		{
			return (HashMap)inputdata1.get(startPos+1);
		}
		
		HashMap outHash = new HashMap();
		int start = startPos;
		HashMap hs = (HashMap) inputdata1.elementAt(start);
		float open = Float.parseFloat((String) hs.get(Open));
		float high = Float.parseFloat((String) hs.get(High));
		float low = Float.parseFloat((String) hs.get(Low));
		long volume = 0;
		outHash.put(Open, open + "");
		hs = (HashMap) inputdata1.elementAt(endPosition);
		float close = Float.parseFloat((String) hs.get(Close));
		outHash.put(Close, close + "");
		outHash.put(Date, hs.get(Date));
		int countvol=0;
		int bidprccount=0;
		int offerprccount=0;
		long startVolume=0,endVolume=0;
		float bidpricetot = 0;
		float bidqtytot = 0;
		float offerpricetot = 0;
		float offerqtytot = 0;

		long oldtotalvolume =0,newtotalvolume=0;
		for (int i = startPos+1; i <= endPosition; i++)
		{
			
			hs = (HashMap) inputdata1.elementAt(i);
			float tmphigh = Float.parseFloat((String) hs.get(High));
			float tmplow = Float.parseFloat((String) hs.get(Low));
			long tmpvol = Long.parseLong((String) hs.get(Volume));
			newtotalvolume = Long.parseLong(hs.get(TotalVolume)+"");
			
			if(newtotalvolume < oldtotalvolume)
			{
				endVolume = oldtotalvolume;
				break;
			}
			
			oldtotalvolume = newtotalvolume;
			
			if(i==startPos+1)
			{	
				try
				{
					startVolume = Long.parseLong(hs.get(TotalVolume)+"");
	
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			
			}	
			if(i==endPosition)
			{
				endVolume = Long.parseLong( hs.get(TotalVolume)+"");
					}
			float bidprice = close;
			float bidqty = 0;
			float offerprice = close;
			float offerqty = 0;

			try
			{
				bidprice = Float.parseFloat((String)hs.get(BidPrice));
				bidqty = Float.parseFloat((String)hs.get(BidQty));
				offerprice = Float.parseFloat((String)hs.get(OfferPrice));
				offerqty = Float.parseFloat((String)hs.get(OfferQty));
				
			}
			catch(Exception e )
			{
			}
			bidpricetot = bidpricetot + bidprice;
			offerpricetot = offerpricetot + offerprice;
			bidqtytot = bidqtytot + bidqty;
			offerqtytot = offerqtytot + offerqty;
			
			if (tmphigh > high)
			{
				high = tmphigh;
			}
			if (tmplow < low)
			{
				low = tmplow;
			}
			volume = volume + tmpvol;
			countvol++;
		}
		if(endPosition > startPos)
		{
//			volume = volume / countvol;
			volume = volume ;

			bidpricetot = bidpricetot /countvol;
			offerpricetot = offerpricetot /countvol;
			offerqtytot = offerqtytot /countvol;
			bidqtytot = bidqtytot / countvol;
		}
		if(endPosition >= inputdata1.size())
		{

			float bidprice = close;
			float bidqty = 0;
			float offerprice = close;
			float offerqty = 0;

			try
			{
				HashMap hs1 = (HashMap)inputdata1.lastElement();
				bidprice = Float.parseFloat((String)hs.get(BidPrice));
				bidqty = Float.parseFloat((String)hs.get(BidQty));
				offerprice = Float.parseFloat((String)hs.get(OfferPrice));
				offerqty = Float.parseFloat((String)hs.get(OfferQty));
			
				
			}
			catch(Exception e )
			{
			}
			outHash.put(BidPrice, bidprice+"");
			outHash.put(OfferPrice, offerprice+"");
			outHash.put(OfferQty, offerqty+"");
			outHash.put(BidQty, bidqty+"");
				
		}
		else
		{
			outHash.put(BidPrice, bidpricetot+"");
			outHash.put(OfferPrice, offerpricetot+"");
			outHash.put(OfferQty, offerqtytot+"");
			outHash.put(BidQty, bidqtytot+"");
			
		}
		outHash.put(High, high + "");
		outHash.put(Low, low + "");
		
		volume = endVolume - startVolume;
		if(volume < 0)
			volume=0;
		outHash.put(Volume, volume + "");
		
		outHash.put(TotalVolume, startVolume);
		
		return outHash;
	}

	
	public void setHighLowValues()
	{
		this.maxprice = 0;
		this.minprice = 0;
		this.noofx = this.inputdata.size();
		this.maxvolume = 0;
		this.averagevolume = 0;
		try
		{
			float minprice = 10000;
			float maxprice = -100;
			long highvolume = 0;
			long totalvolume = 0;
			for (int x = 0; x < this.inputdata.size(); x++)
			{
				HashMap hs = (HashMap) this.inputdata.elementAt(x);
				float low = Float.parseFloat((String) hs.get(Low));
				float high = Float.parseFloat((String) hs.get(High));
				if(low==0) continue;
				
				long vol = Long.parseLong((String) hs.get(Volume));
				totalvolume = totalvolume + vol;
				if (low < minprice)
					minprice = low;
				if (high > maxprice)
					maxprice = high;
				if (vol > highvolume)
					highvolume = vol;
			}
			if(isintraday==false  && isfutureintraday==false)
			{
				this.maxprice = (float) ((maxprice));// * (1.03));
				this.minprice = (float) ((minprice)); //* (.97)) ;
				
			}
			else
			{
				this.maxprice = (float) ((maxprice) * (1.003));
				this.minprice = (float) ((minprice) * (.997)) ;
				
				
			}
			this.noofx = this.inputdata.size();
			this.maxvolume = highvolume;
			this.averagevolume = totalvolume / (this.inputdata.size());
		
		}
		catch (Exception e)
		{
//			// ln(e);
		}
	}


	/**
	 * @param i
	 * @return
	 */
	public float[] getClosePrice()
	{
		float[] returndata = new float[inputdata.size()];
		for (int x = 0; x < inputdata.size(); x++)
		{
			HashMap hs = (HashMap) inputdata.elementAt(x);
			returndata[x] = Float.parseFloat((String) hs.get(Close));
		}
		return returndata;
	}
	public long[] getVolume()
	{
		long[] returndata = new long[inputdata.size()];
		for (int x = 0; x < inputdata.size(); x++)
		{
			HashMap hs = (HashMap) inputdata.elementAt(x);
			returndata[x] = Long.parseLong((String) hs.get(Volume));
		}
		return returndata;
	}
	public float[] getVolumeFloat()
	{
		float[] returndata = new float[inputdata.size()];
		for (int x = 0; x < inputdata.size(); x++)
		{
			HashMap hs = (HashMap) inputdata.elementAt(x);
			returndata[x] = Float.parseFloat((String) hs.get(Volume));
		}
		return returndata;
	}
	public float[] createSimpleMovingAverage(int days, float[] close)
	{
		float[] returndata = new float[inputdata.size()];
		float newmovingprice = 0;
		for (int x = 0; x < close.length; x++)
		{
			if (x < (days - 1))
			{
				newmovingprice = newmovingprice + close[x];
				returndata[x] = 0;
			}
			else if (x == (days - 1))
			{
				newmovingprice = newmovingprice + close[x];
				newmovingprice = newmovingprice / days;
			}
			else
			{
				newmovingprice = newmovingprice - (close[x - days] / days) + (close[x] / days);
				returndata[x] = newmovingprice;
			}
		}
		// TODO Auto-generated method stub
		return returndata;
	}
	public float[] createWeightedMovingAverage(int days, float[] close)
	{
		float[] returndata = new float[inputdata.size()];
		double newmovingprice = 0;
		double numerator = 0;
		double wma = 0;
		double total = 0;
		float factor = (float) ((days * (days + 1.0) / 2.0));
		for (int x = 0; x < close.length; x++)
		{
			if (x < (days - 1))
			{
				newmovingprice = newmovingprice + (x + 1) * close[x];
				returndata[x] = 0;
				total = total + close[x];
			}
			else if (x == (days - 1))
			{
				total = total + close[x];
				newmovingprice = newmovingprice + (x + 1) * close[x];
				numerator = newmovingprice;
				wma = newmovingprice / factor;
				returndata[x] = (float) wma;
			}
			else
			{
				numerator = numerator + days * close[x] - total;
				total = total + close[x] - close[x - days];
				wma = numerator / factor;
				returndata[x] = (float) wma;
			}
		}
		// TODO Auto-generated method stub
		return returndata;
	}
	public float[] createExpoMovingAverage(int days, float[] close)
	{
		float[] ema = new float[inputdata.size()];
		double newmovingprice = 0;
		double numerator = 0;
		double wma = 0;
		double total = 0;
		float alpha = (float) (2 / (days + 1.0));
		float emaprevday = calculateFirstEMA(alpha, days, close);
		ema[days - 1] = emaprevday;
		float ematoday = 0;
		for (int x = days; x < close.length; x++)
		{
			ematoday = emaprevday + alpha * (close[x] - emaprevday);
			ema[x] = ematoday;
			emaprevday = ematoday;
		}
		// TODO Auto-generated method stub
		return ema;
	}
	/**
	 * @param alpha
	 * @param days
	 * @param close
	 * @return
	 */
	private float calculateFirstEMA(float alpha, int days, float[] close)
	{
		float[] val = createWeightedMovingAverage(days, close);
		// TODO Auto-generated method stub
		return val[days - 1];
	}
	/**
	 * @param i
	 * @return
	 */
	public float[] createMaheshMovingAverage(int i, float[] close, long[] volume)
	{
		float[] returndata = new float[inputdata.size()];
		float newmovingprice = 0;
		double numerator = 0;
		double wma = 0;
		double total = 0;
		long totalvolume = averagevolume * i;
		for (int x = 0; x < close.length; x++)
		{
			if (x == 0)
			{
				newmovingprice = close[x];
				returndata[x] = newmovingprice;
			}
			else
			{
				newmovingprice = ((totalvolume - volume[x]) * newmovingprice + close[x] * volume[x]) / totalvolume;
				returndata[x] = newmovingprice;
			}
		}
		// TODO Auto-generated method stub
		return returndata;
	}
	/**
	 * @param noofdayas
	 * @param averageprice
	 * @return
	 */
	public float[][] createBollingerPrice(int noofdayas, float[] averageprice, float[] close, float bandratio)
	{
		float[][] bollinger = new float[2][averageprice.length];
		for (int x = noofdayas; x < averageprice.length; x++)
		{
			double diff = 0;
			for (int y = 0; y < (noofdayas - 1); y++)
			{
				//				// ln(close[x - y]);
				//				.out.println(averageprice[x ]);
				double diffa = (double) (close[x - y] - averageprice[x]);
				diff = diff + Math.pow(diffa, 2.0);
			}
			diff = diff / noofdayas;
			diff = Math.sqrt(diff);
			bollinger[0][x] = bandratio * (float) diff + averageprice[x];
			bollinger[1][x] = averageprice[x] - bandratio * (float) diff;
		}
		// TODO Auto-generated method stub
		return bollinger;
	}
	public float[][] createEnvelop(int noofdayas, float[] averageprice, float ratio)
	{
		float[][] envelop = new float[2][averageprice.length];
		for (int x = noofdayas; x < averageprice.length; x++)
		{
			if (averageprice[x] == 0)
			{
				envelop[0][x] = 0;
				envelop[1][x] = 0;
			}
			envelop[0][x] = (1 + ratio) * averageprice[x];
			envelop[1][x] = (1 - ratio) * averageprice[x];
		}
		// TODO Auto-generated method stub
		return envelop;
	}
	//all methods for drawing
	//method for drawing rate line
	public String calculatePivot(HashMap hs)
	{
		try
		{
			float H = Float.parseFloat((String)hs.get(High));
			float L = Float.parseFloat((String)hs.get(Low));
			float C = Float.parseFloat((String)hs.get(Close));

			float P  = (H + L + C) / 3 ;

			float R1 = (2 * P) - L; 

			float R2 = P + H - L; 

			float R3 = H + 2 * (P - L); 

			float S1 = (2 * P) - H; 

			float S2 = P - H + L; 

			float S3 = L - 2 * (H - P);
		
//			applyDefaultStrategy();
			String s = "Pivot-"+Utility.floatDataAtOnePrecision(P) + "  R1-" + Utility.floatDataAtOnePrecision(R1) +  "  R2-" + Utility.floatDataAtOnePrecision(R2) + "  S1-" + Utility.floatDataAtOnePrecision(S1) + "  S2-" + Utility.floatDataAtOnePrecision(S2);
			//mahesh return s;
		
			
			return "";
		}
		catch(Exception e)
		{
			return "";
		}
		
	}
	public void drawName(Graphics g)
	{
		try
		{
			if(!StockConstants.ShowNameAtBottom)
			{
				Color c = g.getColor();
				g.setColor(StockConstants.leftpricecolor);
				g.setFont(new Font("Arial", Font.BOLD, 11));
			
				g.drawBytes(this.symbolName.getBytes(), 0, this.symbolName.length(), startx + 10, starty + (starty - endy) - 40);
				g.setColor(c);

				return;
			}
	//		g.setColor(StockConstants.leftpricecolor);
	//		g.setFont(new Font("Arial", Font.BOLD, 12));
	//		g.drawBytes(this.note.getBytes(), 0, this.note.length(), startx + 10, starty + (starty - endy) - 20);
			
			Color c = g.getColor();
			g.setColor(StockConstants.leftpricecolor);
			g.setFont(new Font("Arial", Font.BOLD, 11));
			g.drawBytes(this.note.getBytes(), 0, this.note.length(), startx + 10, starty + (starty - endy) - 40);
			HashMap hs = (HashMap)inputdata.lastElement();
			String s = "C-" + hs.get(Close) + " O-" + hs.get(Open)  + " H-" + hs.get(High) + " L-" + hs.get(Low) + " V-" + hs.get(Volume);
			g.drawBytes(s.getBytes(), 0, s.length(), startx + 10, starty + (starty - endy) - 25);
			
			drawDefaultStrategy(g);
			
			if(!isintraday)
			{
				s = calculatePivot(hs);
				g.drawBytes(s.getBytes(), 0, s.length(), startx + 10, starty + (starty - endy) - 10);
				
			}
				
			
			
		
	
			g.setColor(c);
			
		}
		catch(Exception e)
		{
			
		}
	}
	public void drawDefaultStrategy(Graphics g)
	{
		if(true)
			return;
		Color c = g.getColor();
		StringTokenizer st = new StringTokenizer(defaultStrtetgyText,"|");
		int startpos = 125;
		int count=0;
		while(st.hasMoreTokens())
		{
			String text = st.nextToken();
			
			StringTokenizer stringtext = new StringTokenizer(text,"^");
			
			String value = stringtext.nextToken();
			
			Color mycolor = GetColor.getColor(stringtext.nextToken());
			g.setColor(mycolor);
			g.drawBytes(value.getBytes(),0,value.length(), startx + 10, starty + (starty - endy) - startpos + count * 15);
			count++;	
		}
		
		g.setColor(c);
	}
	
	public String pivotPointStyle = "pivot-none";
	public void drawResistanceSupport(Graphics g)
	{
		if(pivotPointStyle.equalsIgnoreCase("pivot-none"))
			return;		
		Color color = g.getColor();
		
		try 
		{
			String query = Utility.getFileContent(StockConstants.INSTALL_DIR+"/buysell/"+pivotPointStyle);
			Vector output = new Vector();
			HashMap hs = new HashMap();
			hs.put(MainGraphComponent.Symbol,this.symbolName);
			
			output.addElement(hs);
			
			BuySellStrategy buysell = new BuySellStrategy();
			// ln(query);
			output = buysell.getAllStocksQuery(output, query.toUpperCase());		
			

			DataBaseInMemory database = new DataBaseInMemory();
			query = database.lexical.findConstantStrings(query);

			database.queryParser(query);
			String[] column = new String[database.column.size()];
			for(int i=0;i<database.column.size();i++)
			{
				
				column[i] = (String)database.column.get(i).toString().toUpperCase();
			
				
			}

			

			String[][] result = new String[output.size()][column.length];
			hs = new HashMap();
			int j=0;
			for (int i = 0; i < output.size(); i++)
			{
				hs = (HashMap) output.elementAt(i);
				j=0;
				for (j=0;j<column.length;j++)
				{
					result[i][j] = (String) hs.get(column[j]);
					
				}
			}
			for(int i=0;i<column.length;i++)
			{
				String s = column[i].toUpperCase();
				if (s.indexOf(MainGraphComponent.Symbol) == -1 && s.indexOf(MainGraphComponent.Close)  == -1)
				{
					plotKeyValue(s,result[0][i],g);
				}
			}

		} 
		catch (Exception e) {
			// TODO Auto-generated catch block
	//		e.printStackTrace();
		}
		
		
		g.setColor(color);

	}
	public void plotKeyValue(String name,String value,Graphics g)
	{
		Color color = g.getColor();
		if(name.startsWith("R"))
			g.setColor(Color.red);
		else if (name.startsWith("S"))
			g.setColor(Color.green);
			
		int ypos = getYPosition(Float.parseFloat(value));
		g.drawLine(this.startx, ypos, this.endx, ypos);
		g.drawString(name+"("+value+")", 100, ypos);
		
		g.setColor(color);
		
	}

	public void drawRateLine(Graphics g)
	{
		Color color = g.getColor();
//		g.setColor(StockConstants.hline);
		int height = starty - endy;
		//drawing y area		
		float diff = this.maxprice - this.minprice;
		int mygap = 15;
		if (height > 400)
			mygap = 15;
		else
			mygap = 7;
		diff = diff / mygap;
		for (int i = starty; i < starty + height; i = i + StockConstants.hlinegap)
		{
			float prc1 = Utility.floatDataAtOnePrecision(getPriceFromYPos(i));
			//			int ypos = this.getYPosition(prc1);
			int ypos = i;
			g.setColor(StockConstants.hline);
			g.drawLine(this.startx, ypos, this.endx, ypos);
			//		g2.drawBytes((price + "").getBytes(), 0, (price + "").length(), StockConstants.length - 48, height - y - YBASE);
			g.setColor(StockConstants.pricecolor);
			g.setFont(new Font("Arial", Font.BOLD, 10));
			
			g.drawBytes(( prc1 + "").getBytes(), 0, ( prc1 + "").length(), this.endx + 6, ypos);
		}
		//	  g.drawLine(this.xmax , (int) (this.ymax - this.ybase), this.xmax , 0);
		//drawing x area
		for (int x = startx; x < endx; x = x + StockConstants.vlinegap)
		{
			String s = "";
			try
			{
				s = getDateForXpos(x);
			}
			catch (Exception e)
			{
				s = "";
			}
			if (s.indexOf(":") != -1)
			{
				try
				{
					StringTokenizer st = new StringTokenizer(s,"-");
					st.nextToken();
					s = st.nextToken();
					
				}
				catch(Exception e)
				{
					
				}
			}
				int xpos = x;
			int xpos1 = xpos - 20;
			g.setColor(StockConstants.timecolor);
			g.setFont(new Font("Arial", Font.BOLD, 10));
			g.drawBytes((s + "").getBytes(), 0, (s + "").length(), xpos1, starty + height + 10);
			g.setColor(StockConstants.vline);
			g.drawLine(xpos, starty + height, xpos, starty);
		}
		
		if(this.isintraday || this.isfutureintraday)
		{
			drawResistanceSupport(g);
		}
		g.setColor(StockConstants.boundarycolor);
		g.drawLine(startx, starty, startx, starty + height);
		g.drawLine(endx, starty, endx, starty + height);
		g.drawLine(startx, starty, endx, starty);
		g.drawLine(startx, starty + height, endx, starty + height);
		//	  g.drawLine(startx, actualyposfrombot, xbase, ymaxposinmainpanel);
		//	  g.drawLine(startx, actualyposfrombot, xmax, actualyposfrombot);
		//	  g.drawLine(endx, actualyposfrombot, xmax, ymaxposinmainpanel);
	
		g.setColor(color);
	}
	public float[] getDrawValue(String plotname)
	{
		float[] f = new float[inputdata.size()];
		for(int i=0;i<f.length;i++)
		{
			HashMap hs = (HashMap)inputdata.get(i);
			String s = (String)hs.get(plotname);
			float tempf = -1;
			try
			{
				tempf = Float.parseFloat(s);
				f[i] = tempf;		
			}
			catch(Exception e)
			{
				f[i] = -1;		
			}
			
		}
		return f;
	}
	public void drawIndicator(Graphics g)
	{
		
		inddisypos = 75;
		float[] avgprices = null;
		int newx = 0;
		int newy = 0;
		int oldx = 0;
		int oldy = 0;
		float[] close = this.getClosePrice();
//added for drawline function of igs fl
  		float[] plotf = this.getDrawValue(PLOT1);
		plotGraph(plotf, g,COLOR1);
		
		plotf = this.getDrawValue(PLOT2);
		plotGraph(plotf, g,COLOR2);
		
		plotf = this.getDrawValue(PLOT3);
		plotGraph(plotf, g,COLOR3);
		
		drawStrategyComponent(g);
//ended		
		for (int i = 0; i < indicatorVector.size(); i++)
		{
			Indicator type = (Indicator) indicatorVector.elementAt(i);
			if (type.indicatorType == type.SMA)
			{
				avgprices = this.createSimpleMovingAverage((int) type.period1, close);
				drawGraph(avgprices, g, col[i]);
				addToInputData(type, avgprices);
				//				String s = " SMA ( " + type.period1 + " ) ";
				//				Color c = g.getColor();
				//				g.setColor(col[i]);
				//				g.drawBytes(s.getBytes(), 0, s.length(), startx+10, starty+inddisypos);
				//				g.setColor(c);
			}
			if (type.indicatorType == type.EMA)
			{
				avgprices = this.createExpoMovingAverage((int) type.period1, close);
				drawGraph(avgprices, g, col[i]);
				addToInputData(type, avgprices);
			}
			if (type.indicatorType == type.MMA)
			{
				long[] volumes = this.getVolume();
				avgprices = this.createMaheshMovingAverage((int) type.period1, close, volumes);
				addToInputData(type, avgprices);
				drawGraph(avgprices, g, col[i]);
				//				String s = " MMA ( " + type.period1 +  " ) ";
				//				Color c = g.getColor();
				//				g.setColor(col[i]);
				//				g.drawBytes(s.getBytes(), 0, s.length(), startx+10, starty+inddisypos);
				//				g.setColor(c);
			}
			else if (type.indicatorType == type.WMA)
			{
				avgprices = this.createWeightedMovingAverage((int) type.period1, close);
				addToInputData(type, avgprices);
				drawGraph(avgprices, g, col[i]);
				//				String s = " WMA ( " + type.period1 + " ) ";
				//				Color c = g.getColor();
				//				g.setColor(col[i]);
				//				g.drawBytes(s.getBytes(), 0, s.length(), startx+10, starty+inddisypos);
				//				g.setColor(c);
			}
			else if (type.indicatorType == type.BB)
			{
				float[][] bolgprc = drawBollingerBand((int) type.period1, type.period2, type.WMA, g, col[i]);
				addToInputData(type, bolgprc);
				//				drawGraph(averageprice, g, Color.cyan);
				drawGraph(bolgprc[0], g, col[i]);
				drawGraph(bolgprc[1], g, col[i]);
				//				String s = "Bol Band ( " + type.period1 + " , " + type.period2 + " ) ";
				//public void drawBytes(byte[] data,  int offset,  int length,  int x,  int y)
				//				Color c = g.getColor();
				//				g.setColor(col[i]);
				//				g.drawBytes(s.getBytes(), 0, s.length(), startx+10, starty+inddisypos);
				//				g.setColor(c);
			}
		}
	}
	public void addToInputData(String s, float[] avgprices)
	{
		for (int i = 0; i < inputdata.size(); i++)
		{
			HashMap hs = (HashMap) inputdata.elementAt(i);
			hs.put(s, avgprices[i] + "");
		}
	}
	public void addToInputData(Indicator type, float[] avgprices)
	{
		String s = this.createID(type);
		for (int i = 0; i < inputdata.size(); i++)
		{
			HashMap hs = (HashMap) inputdata.elementAt(i);
			hs.put(s, avgprices[i] + "");
		}
	}
	public void addToInputData(Indicator type, float[][] avgprices)
	{
		String s = this.createID(type);
		float[] up = avgprices[0];
		float[] down = avgprices[1];
		for (int i = 0; i < inputdata.size(); i++)
		{
			HashMap hs = (HashMap) inputdata.elementAt(i);
			float[] val = new float[2];
			val[0] = up[i];
			val[1] = down[i];
			hs.put(s, val);
		}
	}
	public void drawCandleStick(float close, float open, float high, float low, int xpos, Graphics g,String buy,String sell)
	{
		
		Color c = g.getColor();
		int closey = this.getYPosition(close);
		int openy = this.getYPosition(open);
		int highy = this.getYPosition(high);
		int lowy = this.getYPosition(low);
		int newx = this.getXPosition(xpos);
		int width = this.getXWidth(3);
		if (width < 3)
			width = 3;
		if (close > open)
		{
			
			g.setColor(StockConstants.upcolor);
			g.fill3DRect(newx - ( width / 2 ), closey, width, (openy - closey), true);//Rect();
			g.setColor(StockConstants.upcolor);
		//	g.drawRect(newx, closey, width, (openy - closey));
			g.drawLine(newx , closey, newx , highy);
			g.drawLine(newx , openy, newx , lowy);
			
			if ( width > 5)
			{
				g.drawLine(newx-1 , closey, newx-1 , highy);
				g.drawLine(newx-1 , openy, newx-1 , lowy);
					
			}
			
		}
		else
		{
			g.setColor(StockConstants.downcolor);
			g.fill3DRect(newx- ( width / 2 ), openy, width, (closey - openy),true);
			g.setColor(StockConstants.downcolor);
		//	g.drawRect(newx, openy, width, (closey - openy));
			g.drawLine(newx , openy, newx , highy);
			g.drawLine(newx , closey, newx, lowy);
			if ( width > 5)
			{
				g.drawLine(newx-1 , openy, newx-1 , highy);
				g.drawLine(newx-1 , closey, newx-1, lowy);
						
			}
			
		}

		if (buy != null)
		{
//			g.setColor(Color.white);
//			g.drawLine(newx + width / 2, lowy, newx + width / 2, lowy + 20);

//			g.drawBytes("B".getBytes(), 0,1, newx-5, newy + 30);
				
				
//			g.drawLine(newx+1, newy, newx+1, newy + 20);
				
//			g.drawLine(newx + width / 2, lowy, newx + width / 2 + 3, lowy + 5);
//			g.drawLine(newx + width / 2, lowy, newx + width / 2 - 3, lowy + 5);
//			g.drawString(buy , newx + width / 2 - buy.length(), lowy + 25);	
			drawStratetgyComponent(g, newx , lowy, "buy", buy, StockConstants.buyarrowcolor,0);
		}
		if (sell != null)
		{

			drawStratetgyComponent(g, newx, highy, "sell", sell, StockConstants.sellarrowcolor,0);

		}


		g.setColor(c);
	}

	public void drawOHLC(float close, float open, float high, float low, int xpos, Graphics g,String buy,String sell)
	{
		Color c = g.getColor();
		int closey = this.getYPosition(close);
		int openy = this.getYPosition(open);
		int highy = this.getYPosition(high);
		int lowy = this.getYPosition(low);
		int newx = this.getXPosition(xpos);
		int width = this.getXWidth(3);
		if (width < 3)
			width = 3;
		if (close > open)
		{
			g.setColor(StockConstants.upcolor);
		}
		else
		{
			g.setColor(StockConstants.downcolor);
		}
		g.drawLine(newx + (width / 2),lowy, newx + (width / 2),highy);
//		g.setColor(Color.white);
		g.drawLine(newx + (width / 2), openy,newx , openy );
		g.drawLine(newx + (width / 2), closey,newx + width , closey );

		if (buy != null)
		{
//			g.setColor(Color.white);
//			g.drawLine(newx + width / 2, lowy, newx + width / 2, lowy + 20);

//			g.drawBytes("B".getBytes(), 0,1, newx-5, newy + 30);
				
				
//			g.drawLine(newx+1, newy, newx+1, newy + 20);
				
//			g.drawLine(newx + width / 2, lowy, newx + width / 2 + 3, lowy + 5);
//			g.drawLine(newx + width / 2, lowy, newx + width / 2 - 3, lowy + 5);
//			g.drawString(buy , newx + width / 2 - buy.length(), lowy + 25);	
			drawStratetgyComponent(g, newx + width / 2, lowy, "buy", buy, StockConstants.buyarrowcolor,0);
		}
		if (sell != null)
		{

			drawStratetgyComponent(g, newx + width / 2, highy, "sell", sell, StockConstants.sellarrowcolor,0);

		}
		g.setColor(c);


	}
	public void drawStratetgyComponent(Graphics g,int x1,int y1,String type,String text,Color color,int size)
	{
		Color c = g.getColor();
		g.setColor(color);

		if(type.equalsIgnoreCase("sell"))
		{
			g.drawLine(x1, y1, x1,y1-20);
			g.drawLine(x1-1, y1, x1-1,y1-20);

			//			g.drawLine(newx+1, newy, newx+1, newy - 20);
//			g.drawBytes("S".getBytes(), 0,1, newx-5, newy + 10);
				
			g.drawLine(x1, y1, x1 + 3, y1 - 5);
			g.drawLine(x1, y1, x1 - 3, y1 - 5);

			g.drawString(text , x1 - text.length(), y1 - 25);	
			
		}
		else if(type.equalsIgnoreCase("buy"))
		{
			g.drawLine(x1, y1, x1,y1+20);
			g.drawLine(x1-1, y1, x1-1,y1+20);
			
//			g.drawLine(newx+1, newy, newx+1, newy - 20);
//			g.drawBytes("S".getBytes(), 0,1, newx-5, newy + 10);
				
			g.drawLine(x1, y1, x1 + 3, y1 + 5);
			g.drawLine(x1, y1, x1 - 3, y1 + 5);

			g.drawString(text , x1 - text.length(), y1 + 25);	
						
		}
		else if(type.equalsIgnoreCase("circle"))
		{
			g.fillOval(x1, y1,size,size);
			g.drawString(text , x1 - text.length(), y1);	
			
		}
		else if(type.equalsIgnoreCase("text"))
		{
//			g.fillOval(x1, y1,3,3);
			g.drawString(text , x1 - text.length(), y1);	
			
		}
		else if(type.equalsIgnoreCase("rect"))
		{
			g.fillRect(x1, y1,size,size);
			g.drawString(text , x1 - text.length(), y1);	
			
		}

		g.setColor(c);
  
	}
	public void drawStratetgyComponent(Graphics g,int x1,int y1,int x2,int y2,String type,Color color)
	{
		Color c = g.getColor();
		g.setColor(color);
		if(type.equalsIgnoreCase("line") || type.equalsIgnoreCase("linexy"))
		{
			g.drawLine(x1, y1,x2,y2);
			
		}

		g.setColor(c);
  
	}

	public void plotGraph(float[] avgprices, Graphics g, String colorName)
		{
			int newx = 0, newy = 0, oldx = 0, oldy = 0;
			int y = 0;
			Color paintColor=null;
			for (int x = 0; x < this.inputdata.size(); x++)
			{
				newx = this.getXPosition(x);
				if (avgprices[x] <= 0)
					continue;
				newy = this.getYPosition(avgprices[x]);
				
				HashMap hs = (HashMap)inputdata.get(x);
				String colorStr = (String)hs.get(colorName);
//				// ln(hs);
				Color color = GetColor.getColor(colorStr);
				if (y == 0)
				{
					oldx = newx;
					oldy = newy;
					y++;
				}
				if(color != null)
				{
					if(paintColor==null)
					     paintColor = color;
					Color c1 = g.getColor();
					g.setColor(paintColor);
					g.drawLine(oldx, oldy, newx, newy);
					g.drawLine(oldx, oldy + 1, newx, newy + 1);
					oldx = newx;
					oldy = newy;
					paintColor = color;
					g.setColor(c1);
				}
			}
		}
	public void drawStrategyComponent(Graphics g)
	{
		for(int i=0;i<inputdata.size();i++)
		{
			int newx = this.getXPosition(i);
			HashMap hs = (HashMap)inputdata.get(i);
			Vector v  = (Vector)hs.get(MainGraphComponent.OTHERCOMPONENT);
			if(v==null)
			    continue;
			drawComponent(g, v, newx);    				
		}
	}
	public void drawComponent(Graphics g,Vector component,int newx)
	{
		for(int i=0;i<component.size();i++)
		{
			HashMap compHs = (HashMap)component.get(i);
			drawComponent(g, compHs, newx);
		}
	}
	public void drawComponent(Graphics g,HashMap component,int newx)
	{
		
		int width = this.getXWidth(3);
		newx = newx + width/2;
		String colorStr = (String)component.get("COLOR");
		String name = (String)component.get("NAME");
		String value1 = (String)component.get("VALUE");
		Color color = GetColor.getColor(colorStr);

		if(name.equalsIgnoreCase("linexy"))
		{
			try
			{
				int  y1 = this.getYPosition(Float.parseFloat((String)component.get(Function.Y1)));
				int  y2 = this.getYPosition(Float.parseFloat((String)component.get(Function.Y2)));

				int  x1 = this.getXPosition((int)Float.parseFloat((String)component.get(Function.X1)));
				int  x2 = this.getXPosition((int)Float.parseFloat((String)component.get(Function.X2)));
			
				drawStratetgyComponent(g, x1,y1,x2,y2,name,color);		
				
			}
			catch(Exception e)
			{
				
			}
			
			return;
		}

		
		float value2f=0;
		int newy2=0;
		try
		{
			value2f = Float.parseFloat((String)component.get("VALUE1"));
			newy2 = this.getYPosition(value2f);
			
		}
		catch(Exception e)
		{
			
		}
		
		float value1f = Float.parseFloat(value1);
		
		int newy = this.getYPosition(value1f);
		String text = (String)component.get("TEXT");

		String size = (String)component.get("SIZE");
		
		int sizeint = 0;
		try
		{
			sizeint = (int)Float.parseFloat(size);
		}
		catch(Exception e)
		{
			
		}
		if(name.equalsIgnoreCase("line"))
		{
			drawStratetgyComponent(g, newx,newy,newx,newy2,name,color);		
			
		}

		else
		{
			drawStratetgyComponent(g, newx,newy,name,text,color,sizeint);		
			
		}

		
		
	}
	
	
	public void drawGraph(float[] avgprices, Graphics g, Color c)
	{
		int newx = 0, newy = 0, oldx = 0, oldy = 0;
		g.setColor(c);
		int y = 0;
		for (int x = 0; x < this.inputdata.size(); x++)
		{
			newx = this.getXPosition(x);
			if (avgprices[x] <= 0)
				continue;
			if(avgprices[x] < 0)
				continue;
			newy = this.getYPosition(avgprices[x]);
			if (y == 0)
			{
				oldx = newx;
				oldy = newy;
				y++;
			}
			g.drawLine(oldx, oldy, newx, newy);
			g.drawLine(oldx, oldy + 1, newx, newy + 1);
			oldx = newx;
			oldy = newy;
		}
		
		int x=this.xpos.length;
		try
		{
			x = getIndexForX(intendx + GraphComponentContainer.beforeendx-20);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}
	//	// ln(this.xpos.length);
	//	// ln(x);
	//	// ln("rightprice");
			
		if ( x < this.xpos.length - 2)
		{
			newy = this.getYPosition(avgprices[x]);
			showYPosPriceOnRight(x, newy, c, g);
			
		}
		else
		{
			
			showYPosPriceOnRight(this.inputdata.size()-1, newy, c, g);
			
		}

	}
	public float[][] drawBollingerBand(int noofdayas, float bandratio, int matype, Graphics g, Color col)
	{
		long volume[] = null;
		float close[] = null;
		float averageprice[] = null;
		float bolgprc[][] = null;
		close = this.getClosePrice();
		volume = this.getVolume();
		
		averageprice = this.createExpoMovingAverage(noofdayas, close);
		
		bolgprc = this.createBollingerPrice(noofdayas, averageprice, close, bandratio);
		return bolgprc;
	}
	public void paintComponent(Graphics g)
	{
		if (inputdata==null || inputdata.size()==0)
		{
			return;
		}
		int oldx = 0, oldy = 0;
		g.setColor(StockConstants.closinglinecolor);
		this.xpos = new int[this.inputdata.size()];
		int newy = 0;
		int newx = 0;
		for (int x = 0; x < this.inputdata.size(); x++)
		{
			HashMap hs = (HashMap) this.inputdata.elementAt(x);
			float close = Float.parseFloat((String) hs.get(MainGraphComponent.Close));
			float open = Float.parseFloat((String) hs.get(MainGraphComponent.Open));
			float high = Float.parseFloat((String) hs.get(MainGraphComponent.High));
			float low = Float.parseFloat((String) hs.get(MainGraphComponent.Low));
			String buy = (String) hs.get(MainGraphComponent.BUY);
			String sell = (String) hs.get(MainGraphComponent.SELL);
			String buyind = (String) hs.get(MainGraphComponent.BUYIND);
			String sellind = (String) hs.get(MainGraphComponent.SELLIND);
			if(buy ==null)
				buy = buyind;
			if(sell ==null)
				sell = sellind;
			
			
			//			// ln(close);
			//			// ln(convert.getYPosition(close));
			newy = this.getYPosition(close);
			newx = this.getXPosition(x);
			this.xpos[x] = newx;
			int closey = this.getYPosition(close);
			int openy = this.getYPosition(open);
			int highy = this.getYPosition(high);
			int lowy = this.getYPosition(low);
			if (x == 0)
			{
				oldx = newx;
				oldy = newy;
			}
			if (graphType == MainGraphComponent.LINEGRAPH)
			{
				g.drawLine(oldx, oldy, newx, newy);
				g.drawLine(oldx-1, oldy-1, newx-1, newy-1);
				
				drawBuySell(newx, newy, buy,sell, g);

			}
			else if (graphType == MainGraphComponent.CANDLEGRAPH)
			{
				drawCandleStick(close, open, high, low, x, g,buy,sell);
			//	g.drawLine(oldx, oldy, newx, newy);
				
			}
			else if (graphType == MainGraphComponent.OHLCGRAPH)
			{
				drawOHLC(close, open, high, low, x, g,buy,sell);
			//	g.drawLine(oldx, oldy, newx, newy);
				
			}
	
			//			g.drawLine(oldx, oldy+1, newx, newy+1);
			oldx = newx;
			oldy = newy;
		}
		//		showPriceOnLeft(newx, newy, g);
		//		showAddedTechnicalIndicator(g);
		this.drawRateLine(g);
//		g.setColor(StockConstants.leftpricecolor);
//		g.setFont(new Font("Arial", Font.BOLD, 12));
//		g.drawBytes(this.note.getBytes(), 0, this.note.length(), startx + 10, starty + (starty - endy) - 20);
		int x=0;
		HashMap hs=new HashMap();
		try
		{
			
		//	if(StockConstants.ShowNameAtBottom)
				drawName(g);
	
		
		drawIndicator(g);
		
		x=this.xpos.length;
		hs = (HashMap) this.inputdata.elementAt(this.inputdata.size()-1);
		
			x = getIndexForX(intendx + GraphComponentContainer.beforeendx-20);
		}
		catch (Exception e)
		{
		}
	//	// ln(this.xpos.length);
	//	// ln(x);
	//	// ln("rightprice");
			
		if ( x < this.xpos.length - 2)
		{
			hs = (HashMap) this.inputdata.elementAt(x);
			float close = Float.parseFloat((String) hs.get(MainGraphComponent.Close));
			newy = this.getYPosition(close);
		}
		float close = Float.parseFloat((String) hs.get(MainGraphComponent.Close));
		float open = Float.parseFloat((String) hs.get(MainGraphComponent.Open));
		float high = Float.parseFloat((String) hs.get(MainGraphComponent.High));
		float low = Float.parseFloat((String) hs.get(MainGraphComponent.Low));
	
		if(open > close)
			showYPosPriceOnRight(endx, close, StockConstants.downcolor, g);
		else
			showYPosPriceOnRight(endx, close, StockConstants.upcolor, g);
		
//	
		showPriceRightFormula(g);
	
	}
	public void showPriceRightFormula(Graphics g)
	{
		HashMap hs = (HashMap)inputdata.get(inputdata.size()-1);
		Vector v  = (Vector)hs.get(Function.RIGHTPRICE);
		if(v != null)
		{
			for ( int j=0;j<v.size();j++)
			{
				HashMap rightprcHash = (HashMap)v.get(j);
				try
				{
					String value = (String)rightprcHash.get(Function.VALUE);
					if(value==null)
						continue;	
					float valuef = Float.parseFloat(Utility.floatDataAtOnePrecisionStr(value));
					Color color = ConfigUtility.getColor((String)rightprcHash.get(Function.COLOR));
					g.setColor(color);
					ypos = getYPosition(valuef);
					g.fillRect(this.endx, ypos - 12, 35, 15);
					g.setColor(Color.black);
					g.setFont(new Font("Arial", Font.BOLD, 12));
					g.drawBytes(( valuef + "").getBytes(), 0, ( valuef + "").length(), this.endx + 6, ypos);	
				}
				catch(Exception e)
				{
					
				}
			}
		}

	}
	/**
	 * @param newx
	 * @param newy
	 * @param buysell
	 * @param g
	 */
	private void drawBuySell(int newx, int newy, String buy,String sell, Graphics g)
	{
		int width = this.getXWidth(3);
			if (width < 3)
				width = 3;
	
			if (buy != null)
			{
				drawStratetgyComponent(g, newx + width/2, newy, "buy", buy, StockConstants.buyarrowcolor,0);
			}
			if (sell != null)
			{
				drawStratetgyComponent(g, newx + width/2, newy, "sell", sell, StockConstants.sellarrowcolor,0);
			}
	}
	/**
	 * @param newy
	 * @param openy
	 * @param lowy
	 * @param highy
	 * @param closey
	 * @param g
	 */
	public String getDataForDate(int x_pos) throws Exception
	{
		try
		{
			StringBuffer retStr = new StringBuffer();
			if(x_pos > xpos[xpos.length -1])
			{
				HashMap hs = (HashMap) this.inputdata.elementAt(xpos.length - 1);
				int vol = Integer.parseInt(hs.get(MainGraphComponent.Volume) + "");
				String volstr = vol + "";
				if (vol > 10000)
					volstr = (vol / 1000) + "K";
				String mydate = (String) hs.get(MainGraphComponent.Date);
				mydate = Utility.getNewFormatedDate(mydate);
				retStr.append(
					"D: "
						+ mydate
						+ " C: "
						+ hs.get(MainGraphComponent.Close)
						+ " O: "
						+ hs.get(MainGraphComponent.Open)
						+ " H: "
						+ hs.get(MainGraphComponent.High)
						+ " L: "
						+ hs.get(MainGraphComponent.Low)
						+ " V: "
						+ volstr);

				return retStr.toString();

			}
			for (int i = 1; i < this.xpos.length; i++)
			{
				if (this.xpos[i] > x_pos)
				{
					HashMap hs = (HashMap) this.inputdata.elementAt(i - 1);
					int vol = Integer.parseInt(hs.get(MainGraphComponent.Volume) + "");
					String volstr = vol + "";
					if (vol > 10000)
						volstr = (vol / 1000) + "K";
					String mydate = (String) hs.get(MainGraphComponent.Date);
					mydate = Utility.getNewFormatedDate(mydate);
					retStr.append(
						"D: "
							+ mydate
							+ " C: "
							+ hs.get(MainGraphComponent.Close)
							+ " O: "
							+ hs.get(MainGraphComponent.Open)
							+ " H: "
							+ hs.get(MainGraphComponent.High)
							+ " L: "
							+ hs.get(MainGraphComponent.Low)
							+ " V: "
							+ volstr);
					break;
				}
				else if (this.xpos[i] == x_pos)
				{
					HashMap hs = (HashMap) this.inputdata.elementAt(i);
					int vol = Integer.parseInt(hs.get(MainGraphComponent.Volume) + "");
					String volstr = vol + "";
					if (vol > 10000)
						volstr = (vol / 1000) + "K";
					String mydate = (String) hs.get(MainGraphComponent.Date);
					mydate = Utility.getNewFormatedDate(mydate);
					retStr.append(
						"D: "
							+ mydate
							+ " C: "
							+ hs.get(MainGraphComponent.Close)
							+ " O: "
							+ hs.get(MainGraphComponent.Open)
							+ " H: "
							+ hs.get(MainGraphComponent.High)
							+ " L: "
							+ hs.get(MainGraphComponent.Low)
							+ " V: "
							+ volstr);
					break;
				}
			}
			return retStr.toString();
		}
		catch (Exception e)
		{
			return "";
		}
	}
	public int getIndexForX(int x_pos) throws Exception
	{
		for (int i = 1; i < this.xpos.length; i++)
		{
			if (this.xpos[i] > x_pos)
			{
				return (i - 1);
			}
			if (this.xpos[i] == x_pos)
			{
				return (i);
			}
		}
		return xpos.length ;
	}
	public String getDateForXpos(int x_pos) throws Exception
	{
		StringBuffer retStr = new StringBuffer();
		try
		{
			if(x_pos > xpos[xpos.length - 1])
			{
				HashMap hs = (HashMap) this.inputdata.elementAt(xpos.length - 1);
				String mydate = (String) hs.get(MainGraphComponent.Date);
				return mydate;
				
			}
			for (int i = 1; i < this.xpos.length; i++)
			{
				if (this.xpos[i] > x_pos)
				{
					HashMap hs = (HashMap) this.inputdata.elementAt(i - 1);
					String mydate = (String) hs.get(MainGraphComponent.Date);
					return mydate;
				}
				if (this.xpos[i] == x_pos)
				{
					HashMap hs = (HashMap) this.inputdata.elementAt(i);
					String mydate = (String) hs.get(MainGraphComponent.Date);
					return mydate;
				}
			}
		}
		catch (Exception e)
		{
		}
		return retStr.toString();
	}
	public int getYPosForXpos(int x_pos) throws Exception
	{
		StringBuffer retStr = new StringBuffer();
		try
		{
			if(x_pos > xpos[xpos.length - 1])
			{
				HashMap hs = (HashMap) this.inputdata.elementAt(xpos.length - 1);
				String price = (String) hs.get(MainGraphComponent.Close);
				
				return getYPosition(Float.parseFloat(price));
				
			}
			for (int i = 1; i < this.xpos.length; i++)
			{
				if (this.xpos[i] > x_pos)
				{
					HashMap hs = (HashMap) this.inputdata.elementAt(i - 1);
					String price = (String) hs.get(MainGraphComponent.Close);
					int val = getYPosition(Float.parseFloat(price));
					
//					// ln("vavavavavva="+val +"and xpos="+x_pos);
					return getYPosition(Float.parseFloat(price));
				}
				if (this.xpos[i] == x_pos)
				{
					HashMap hs = (HashMap) this.inputdata.elementAt(xpos.length - 1);
					String price = (String) hs.get(MainGraphComponent.Close);
				
					return getYPosition(Float.parseFloat(price));
				}
			}
		}
		catch (Exception e)
		{
		}
		return -1;
	}




	/**
	 * @param o
	 */
	public void addComponent(Object o)
	{
		indicatorVector.addElement(o);
		// TODO Auto-generated method stub
	}
	public void removeComponent(Object o)
	{
		indicatorVector.remove(o);
		// TODO Auto-generated method stub
	}
	public void showSelection(Graphics g)
	{
		Color c = g.getColor();
		g.setColor(Color.red);
		int height = this.starty - this.endy;
		g.fillRect(this.startx - 2, this.starty - 2, 5, 5);
		g.fillRect(this.startx - 2, this.starty - 2 + height, 5, 5);
		g.fillRect(this.endx - 2, this.starty - 2, 5, 5);
		g.fillRect(this.endx, this.starty + height - 2, 5, 5);
		g.setColor(c);
	}
	public void drawComponent(Graphics g)
	{
		Color c = g.getColor();
		Font f = g.getFont();
		paintComponent(g);
		g.setColor(c);
		g.setFont(f);
	}
	public boolean isPointInside(int xpos, int ypos)
	{
		int height = this.starty - this.endy;
		int ypos1 = ypos - height;
		if ((xpos >= startx) && (xpos <= endx) && (ypos1 <= this.starty) && (ypos1 >= this.endy))
		{
//			// ("i am in");
			return true;
		}
		else
		{
			return false;
		}
	}
	public void drawCursor(int xpos, int ypos, int type, Graphics g)
	{
		Color c = g.getColor();
//		int newypos = 0;
	//	// ln("endxmynamemahesh===="+this.endx);
	//	// ln("xpos===="+xpos);
		
		try
		{
			g.setColor(StockConstants.mousemovecolor);

			if (type == ComponentManager.CROSSHAIR)
			{
				// ln("this.endx"+this.endx);
				// ln("this.startx"+this.startx);

		//		g.drawLine(this.startx, ypos, 1000, 1);
				
				g.drawLine(this.startx, ypos, this.endx, ypos);
				g.drawLine(xpos, this.starty, xpos, this.starty + (this.starty - this.endy));

				if (ypos != 0)
					showYPosPriceOnRight(xpos, ypos, StockConstants.mousemovecolor, g);

			}
			else if (type == ComponentManager.CURSOR)
			{
				g.drawLine(xpos, this.starty, xpos, this.starty + (this.starty - this.endy));
			}
			else
			{
				;
			}
			showPriceOnLeft(xpos, ypos, g);
		}
		catch (Exception e)
		{
//			// ln("exception in drawCursor");
		}
		g.setColor(c);
	}
	public void showAlertText(int xpos,int ypos, Graphics g,String text)
	{
		text = text.toLowerCase();
		Color color = g.getColor();
		g.setColor(StockConstants.graphbackgroundcolor);
//		g.fillRect(startx + 5, starty + 40, 150, 60);
		g.setColor(StockConstants.leftpricecolor);
		g.setFont(new Font("Arial", Font.BOLD, 11));
		StringTokenizer st = new StringTokenizer(text,"|");
		int y = starty + 40;
		while(st.hasMoreTokens())
		{
			String s = st.nextToken();
			StringTokenizer st1 = new StringTokenizer(s,"^");
			s = st1.nextToken();
			if(st1.hasMoreTokens())
			{
				Color c =ConfigUtility.getColor(st1.nextToken());
				g.setColor(c);
				
				g.drawBytes(s.getBytes(), 0, s.length(), startx + 10, y);
							
			}
			else
			{
				g.setColor(StockConstants.leftpricecolor);
				
				g.drawBytes(s.getBytes(), 0, s.length(), startx + 10, y);
				
			}
			y = y + 15;
		}

	}	
	boolean showalerttext=false;
	public boolean isfutureintraday=false;
	public void showAlertText(Graphics g)
	{
		if (showalerttext==false)
			return; 
		HashMap hs = (HashMap) this.inputdata.lastElement();
		String alert = (String)hs.get(MainGraphComponent.ALERT);
		String alerttext = (String)hs.get(Function.ALERTTEXT);
		
		if (alerttext != null)
		{
			this.showAlertText(0, 0, g, alerttext);
		}
		showalerttext=false;
	}
	
	public void showPriceOnLeft(int xpos, int ypos, Graphics g)
	{
		Color color = g.getColor();
		int startx = 0;
		try
		{	//showing price on mouse move						
			//public void drawBytes(byte[] data,  int offset,  int length,  int x,  int y)
			//			String s = 	"D-12/10/2007,O-100,H-120,L-100,C-50,V-100K";		
			String s = this.getDataForDate(xpos);
			g.setColor(StockConstants.graphbackgroundcolor);
			g.fillRect(startx + 5, starty + 1, endx - 30, 20);

			g.setColor(StockConstants.leftpricecolor);
			g.setFont(new Font("Arial", Font.BOLD, 11));
			g.drawBytes(s.getBytes(), 0, s.length(), startx + 10, starty + 15);
			int index = this.getIndexForX(xpos);
			int pos = 0;
			for (int i = 0; i < indicatorVector.size(); i++)
			{
				Indicator ind = (Indicator) indicatorVector.elementAt(i);
				String ids = createID(ind);
				g.setColor(this.col[i]);
				HashMap hs = (HashMap) inputdata.elementAt(index);
				String s2 = "";
				if (ind.indicatorType == Indicator.BB)
				{
					float[] ft = (float[]) hs.get(ids);
					if (ft != null)
						s2 = " UBB: " + Utility.oneDigitFloat(ft[0] + "") + " LBB: " + Utility.oneDigitFloat(ft[1] + "");
					else
						s2 = "";
					ids = ids + ":" + s2;
					g.drawBytes(ids.getBytes(), 0, ids.length(), startx + 350 + pos, starty + 15);
					pos = pos + 225;
				}
				else
				{
					s2 = (String) hs.get(ids);
					s2 = Utility.oneDigitFloat(s2);
					if (s2 == null)
						s2 = "";
					ids = ids + ":" + s2;
					g.drawBytes(ids.getBytes(), 0, ids.length(), startx + 350 + pos, starty + 15);
					pos = pos + 100;
				}
			}
			pos = startx + 350 + pos;
			//a
			HashMap hs = (HashMap) inputdata.elementAt(index);
			Vector v  = (Vector)hs.get(Function.LEFTPRICE);
			if(v != null)
			{
				for ( int j=0;j<v.size();j++)
				{
					HashMap leftprcHash = (HashMap)v.get(j);
					try
					{
						String value = (String)leftprcHash.get(Function.VALUE);
						if(value==null)
							value="";	
						value = Utility.floatDataAtOnePrecisionStr(value);
						s = (String)leftprcHash.get(Function.NAME) + " " + (value);
						g.setColor(Color.black);
						int width = s.length()*9;
						
						String colorStr = (String)leftprcHash.get(Function.COLOR);
						g.setColor((ConfigUtility.getColor(colorStr)));
						g.setFont(new Font("Arial", Font.BOLD, 11));
						g.drawBytes(s.getBytes(), 0, s.length(), pos, starty + 15);
						pos = pos + width;
						
					}
					catch(Exception e)
					{
						
					}
				}
			}

			
			
		}
		catch (Exception e)
		{
		}
		g.setColor(color);
	}
	public void showYPosPriceOnRight(int xpos, int ypos, Color color, Graphics g)
	{
		Color c = g.getColor();

		try
		{
			//showing price on mouse move						
			//public void drawBytes(byte[] data,  int offset,  int length,  int x,  int y)
			//			String s = 	"D-12/10/2007,O-100,H-120,L-100,C-50,V-100K";		
			float f = this.getPriceFromYPos(ypos);

			g.setColor(color);
			int x = getIndexForX(intendx + GraphComponentContainer.beforeendx);
		//	// ln(this.xpos.length);
		//	// ln(x);
		//	// ln("rightprice");
				
			if ( x < this.xpos.length - 2)
			{
			//	g.fillRect(this.intendx+GraphComponentContainer.beforeendx-60 + 6, ypos - 12, 50, 15);
			//	g.setColor(StockConstants.mousemovepricecolor);
				g.setFont(new Font("Arial", Font.BOLD, 12));
				g.drawBytes((f + "").getBytes(), 0, (f + "").length(), this.intendx+GraphComponentContainer.beforeendx-60 + 6, ypos);
				
			}
			else
			{
				g.fillRect(endx, ypos - 12, 50, 15);
				g.setColor(StockConstants.mousemovepricecolor);
				g.setFont(new Font("Arial", Font.BOLD, 12));
				g.drawBytes((f + "").getBytes(), 0, (f + "").length(), endx + 6, ypos);
				
			}
		
		}
		catch (Exception e)
		{
//			// ln("exception in showrighttprice method" + e);
		}
		showAlertText(g);
		g.setColor(c);		
	}
	public void showYPosPriceOnRight(int xpos, float f, Color color, Graphics g)
	{
		Color c = g.getColor();

		try
		{
			//showing price on mouse move						
			//public void drawBytes(byte[] data,  int offset,  int length,  int x,  int y)
			//			String s = 	"D-12/10/2007,O-100,H-120,L-100,C-50,V-100K";		
			int ypos = this.getYPosition(f);

			g.setColor(color);
			int x = getIndexForX(intendx + GraphComponentContainer.beforeendx);
		//	// ln(this.xpos.length);
		//	// ln(x);
		//	// ln("rightprice");
				
			if ( x < this.xpos.length - 2)
			{
			//	g.fillRect(this.intendx+GraphComponentContainer.beforeendx-60 + 6, ypos - 12, 50, 15);
			//	g.setColor(StockConstants.mousemovepricecolor);
				g.setFont(new Font("Arial", Font.BOLD, 12));
				g.drawBytes((f + "").getBytes(), 0, (f + "").length(), this.intendx+GraphComponentContainer.beforeendx-60 + 6, ypos);
				
			}
			else
			{
				g.fillRect(endx, ypos - 12, 50, 15);
				g.setColor(StockConstants.mousemovepricecolor);
				g.setFont(new Font("Arial", Font.BOLD, 12));
				g.drawBytes((f + "").getBytes(), 0, (f + "").length(), endx + 6, ypos);
				
			}
		
		}
		catch (Exception e)
		{
//			// ln("exception in showrighttprice method" + e);
		}
		showAlertText(g);
		g.setColor(c);		
	}



	/**
	 * @param ind
	 */
	public void addIndicator(Indicator ind)
	{
		indicatorVector.addElement(ind);
	}
	public void removeIndicator(Indicator ind)
	{
		int i = indicatorVector.indexOf(ind);
		if (i != -1)
			indicatorVector.removeElementAt(i);
		// TODO Auto-generated method stub
	}
	/**
	 * @param i
	 * @param incy
	 * @param j
	 * @param k
	 * @param y2
	 */
	public void updateDimension(int startx, int starty, int endx, int endy,int panelsize )
	{
		this.totpanelsize = panelsize;
		this.startx = startx;
		this.endx = endx;
		this.starty = panelsize - starty;
		this.endy = totpanelsize - endy;
		this.totpanelsize = panelsize;
		this.componenttype = componenttype;

		super.startx = startx;
		super.endx = endx;
		
		super.starty = this.starty;
		super.endy = this.endy;

	}
}

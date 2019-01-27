/*
 * Created on Mar 17, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.strategy;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;

import com.stockfaxforu.component.Financial;
import com.stockfaxforu.component.MainGraphComponent;
import com.stockfaxforu.config.DataValuePanel;
import com.stockfaxforu.finance.ShowFinance;
import com.stockfaxforu.getolddataforu.loader.Loader;
import com.stockfaxforu.query.DataBaseInMemory;
import com.stockfaxforu.query.Function;
import com.stockfaxforu.util.StockConstants;
import com.stockfaxforu.util.Utility;

/**
 * @author sdeopura
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class TechIndicatorLibrary
{
	Vector inputdata = null;
//	public static String Open = "OPEN";
//	public static String High = "HIGH";
//	public static String Low = "LOW";
//	public static String Close = "CLOSE";
//	public static String AvgPrice = "AVGPRICE";
//	public static String Volume = "VOLUME";
//	public static String Date = "DATE";
	public int noofx = 0;
	;
	public long averagevolume = 0;
	public static String OPEN = "OPEN";
	public static String HIGH = "HIGH";
	public static String LOW = "LOW";
	public static String CLOSE = "CLOSE";
	public static String TOTALVOLUME = "TOTALVOLUME";
	
	
	public static String AVGPRICE = "AVGPRICE";
	public static String VOLUME = "VOLUME";
	public static String MACD = "MACD";
	public static String SMA = "SMA";
	public static String MMA = "MMA";
	public static String WMA = "WMA";
	public static String EMA = "EMA";
	public static String RSI = "RSI";
	public static String WILPER = "WILPER";
	public static String BBAND = "BBAND";
	public static String UBBAND = "UBBAND";
	public static String LBBAND = "LBBAND";
	public static String MACDSIGNAL = "MACDSIGNAL";
	public static String MACDLINE = "MACDLINE";
	public static String MACDDIV = "MACDDIV";
	public static String PARSAR = "PARSAR";

	
	public static String HIGHINDAYS = "HIGHINDAYS";
	public static String LOWINDAYS = "LOWINDAYS";
	public static String CCI = "CCI";
	public static String CMF = "CMF";

	public static String STOCKSCREENER = "STOCKSCREENER";
	public static String BUYSELLSIGNAL = "BUYSELLSIGNAL";
	public HashMap includeHash = new HashMap();
		
	public static void main(String[] args)
	{
		Properties resultData = new Properties();
		try
		{
			resultData.load(new FileInputStream(StockConstants.INSTALL_DIR+"/other/software.properties"));
			resultData.load(new FileInputStream(StockConstants.INSTALL_DIR+"/result5/AAPL.txt"));
	//		// ln(resultData);
		}
		catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	

	public static int HIGHINDAYSSTAUS = 1000;
	public static int LOWINDAYSSTATUS = 1001;
	public static HashMap resultDataAll= new HashMap();
	public String symbol=null,status="";
	public void loadProperties()
	{
		Properties resultData = new Properties();
		try
		{  if(resultDataAll.get(symbol)==null)
			{
				StringTokenizer st = new StringTokenizer(StockConstants.ResultDirectories,",");
				while(st.hasMoreElements())
				{
					try
					{
						String s  = st.nextToken();
						resultData.load(new FileInputStream(StockConstants.INSTALL_DIR+"/"+s + "/" +symbol + ".txt"));
						
					}
					catch(Exception e)
					{
						
					}
					
				}
				resultDataAll.put(symbol,resultData);	
			}
		}
		catch (Exception e)
		{
		}

	}
	public String[] getResultData(String symbol,String variable,int value)
	{
	//	symbol = "TCS";
		Properties resultData = (Properties)resultDataAll.get(symbol);
		if(resultData==null)
		{
			loadProperties();
			resultData = (Properties)resultDataAll.get(symbol);
		}
		String s = (String)resultData.get(variable);	
	//	// ln("resultData="+resultData);
		if(s==null)
		{
			String temp  = "";
			String[] rettemp = new String[this.inputdata.size()];
			for(int i=0;i<rettemp.length;i++)
			{
				rettemp[i] = temp;
			}
			return rettemp;

		}
		StringTokenizer st = new StringTokenizer(s,";");
		Vector v = new Vector();
		while(st.hasMoreTokens())
		{
			v.addElement(st.nextToken());
		}
		try
		{
			String temp  = (String)v.get(value);
			String[] rettemp = new String[this.inputdata.size()];
			for(int i=0;i<rettemp.length;i++)
			{
				rettemp[i] = temp;
			}
			return rettemp;
		}
		catch(Exception e)
		{
			String temp  = "-10000";
			String[] rettemp = new String[this.inputdata.size()];
			for(int i=0;i<rettemp.length;i++)
			{
				rettemp[i] = temp;
			}
			
			return rettemp;		

		}
	}
	public TechIndicatorLibrary(Vector inputdata)
	{
		this.noofx = inputdata.size();
		this.inputdata = inputdata;
		setAverageVolume();
	}
	public TechIndicatorLibrary(Vector inputdata,HashMap intradayHash)
	{
		this.noofx = inputdata.size();
		this.inputdata = inputdata;
		setAverageVolume();
		this.intradHashMap = intradayHash;
	}
	HashMap intradHashMap =null;	
	
	public float[] createParaBolicSAR(float increment,float endincrement)
	{
		//will develope later
		return null;
	}
	
	public float[] createWilliam(int noofdays)
	{
		this.noofx = this.inputdata.size();
		float[] close = getClosePrice();
		float[] william = new float[close.length];
		for (int i = noofdays; i < close.length; i++)
		{
			float[] val = getHighValues(noofdays, i);
			float diffden = val[1] - val[0];
			if (diffden == 0)
			{
				diffden = 0.00001f;
			}
			william[i] = ((close[i] - val[1]) / (diffden)) * 100;
		}
		return william;
	}
	private float[] getHighValues(int noofdays, int index)
	{
		int start = index - noofdays;
		float[] retval = new float[2];
		retval[0] = 100000;
		retval[1] = 0;
		for (int i = start; i <= index; i++)
		{
			HashMap hs = (HashMap) inputdata.elementAt(i);
			float high = Float.parseFloat((String) hs.get(MainGraphComponent.High));
			float low = Float.parseFloat((String) hs.get(MainGraphComponent.Low));
			if (high > retval[1])
				retval[1] = high;
			if (low < retval[0])
				retval[0] = low;
		}
		// TODO Auto-generated method stub
		return retval;
	}


//createComodityChannelIndex
	public float[] createComodityChannelIndex(int noofdays)
	{
		float[] cci = new float[inputdata.size()];
		float[] typprc = getTypicalPrice();
		float[] simtypprc = createSimpleMovingAverage(noofdays, getClosePrice());
		for(int i=noofdays-1;i<inputdata.size();i++)
		{
			float meandeviation = getMeanDeviation(i,noofdays);
			float meandeviation1 = (float)(meandeviation*0.015);
			float diff = (float)(typprc[i] - simtypprc[i]);
			cci[i] = diff/meandeviation1;	
		}
		cci[noofdays-1] =0;
		return cci;
	
	}
	float[] getTypicalPrice()
	{
		float[] typprc = new float[inputdata.size()];
		for (int i = 0; i < inputdata.size(); i++)
		{
			HashMap hs = (HashMap) inputdata.elementAt(i);
			float close = Float.parseFloat((String) hs.get(MainGraphComponent.Close));
			float high = Float.parseFloat((String) hs.get(MainGraphComponent.High));
			float low = Float.parseFloat((String) hs.get(MainGraphComponent.Low));
			typprc[i] = (high + low + close) / 3;
		}
		return typprc;
		
	}
	float getMeanDeviation(int index,int noofdays,float[] close)
	{
		float totprc=0;
		int start = index - noofdays +1;
		for(int i=start;i<=index;i++)
		{
			totprc = totprc + close[i];			
		}
		float meanprc = totprc/noofdays;
		float meandeviation=0;
		for(int i=start;i<=index;i++)
		{
			meandeviation = meandeviation + Math.abs(close[i]-meanprc);			
		}
		meandeviation = meandeviation/noofdays;
		return meandeviation;
		
	}

	float getMeanDeviation(int index,int noofdays)
	{
		float totprc=0;
		int start = index - noofdays +1;
		for(int i=start;i<=index;i++)
		{
			HashMap hs = (HashMap) inputdata.elementAt(i);
			float close = Float.parseFloat((String) hs.get(MainGraphComponent.Close));

			totprc = totprc + close;			
		}
		float meanprc = totprc/noofdays;
		float meandeviation=0;
		for(int i=start;i<=index;i++)
		{
			HashMap hs = (HashMap) inputdata.elementAt(i);
			float close = Float.parseFloat((String) hs.get(MainGraphComponent.Close));

			meandeviation = meandeviation + Math.abs(close-meanprc);			
		}
		meandeviation = meandeviation/noofdays;
		return meandeviation;
		
	}
//end createComodityChannelIndex
	public float[] getHIGHLOWSDAYS(int days, int status)
	{
		float[] returndata = new float[inputdata.size()];
		for (int x = days; x < returndata.length; x++)
		{
			returndata[x] = getHighLowinDays(x, days, status);
		}
		return returndata;
	}
	public int POSITIVE=1;
	public int NEG=2;
	public int NODIR=0;
	
	public float[][] 	createZigzag(float[] f,float change)
	{
		float[] returndata = new float[inputdata.size()];
		float[] peak = new float[inputdata.size()];
		float[] trough = new float[inputdata.size()];

		boolean b = true;
		int zigpos=0;
		int direction=0;
		boolean zigfound=false , waslow = false , washigh = false;
		returndata[0] = f[0] ; 
		float highest = 0 , lowest = 0 ; 
		for (int x = 1; x < returndata.length; x++)
		{
			float changeper = 0;
			float diff = f[x] - f[zigpos]; 
			changeper = (float)((diff * 100 / ( f[zigpos]) + .0001));
			
			if(changeper > change)
			{
				zigpos = x;
				direction = POSITIVE;
				break;
			}
			if ( changeper < -1 * change)
			{
				zigpos = x;
				direction = NEG;
				break;
			}
		}
		int startpos = zigpos;
		for ( int x = startpos + 1; x< returndata.length;x++)
		{
			float changeper = 0;
			float diff = f[x] - f[zigpos]; 
			changeper = (float)((diff * 100 / ( f[zigpos]) + .0001));
			if (direction==POSITIVE && changeper > 0)
			{
				zigpos = x;
			}
			else if (direction==NEG && changeper < 0)
			{
				zigpos = x;
				
			}
			else if (direction==POSITIVE && changeper < -1 * change)
			{
				returndata[zigpos] = f[zigpos];
				peak[zigpos]  =  f[zigpos];
				zigpos = x;
				direction = NEG;
			}
			else if (direction==NEG && changeper > change)
			{
				returndata[zigpos] = f[zigpos];
				trough[zigpos]  = f[zigpos];
				zigpos = x;
				direction = POSITIVE;
				
			}
			if(x == returndata.length -1 )
			{
				returndata[zigpos] = f[zigpos];
				if(direction == POSITIVE)
				{
					peak[zigpos] = f[zigpos]; 
				}
				else
				{
					trough[zigpos] = f[zigpos]; 
					
				}
				
			}

		
		}

		float[][] retudataarray = new float[3][returndata.length];
		retudataarray[0] = peak;
		retudataarray[1] = trough;
		retudataarray[2] = returndata;
		
		return retudataarray;
	}
	public float[] getHIGHLOWSDAYSANY(float[] f,int days, int status)
	{
		float low = 100000;
		float high = -10000;
		float[] returndata = new float[f.length];
		for (int x = days; x < f.length; x++)
		{
			returndata[x] = getHighLowinDaysAny(f,x, days, status);
		}
		return returndata;
	}

	float getHighLowinDaysAny(float[] inputvalue,int index, int noofdays, int status)
	{
		int start = index - noofdays + 1;
		float temp = 0, f = 0;
		double downmf = 0;
		if(status == LOWINDAYSSTATUS)
		{
			temp = 100000;
		}
		for (int i = start; i <= index; i++)
		{
			f = inputvalue[i];

			if (status == LOWINDAYSSTATUS)
			{
				if (f < temp)
					temp = f;
			}
			else if (status == HIGHINDAYSSTAUS)
			{
				if (f > temp)
					temp = f;
			}
		}
		return temp;
	}
	float getHighLowinDays(int index, int noofdays, int status)
	{
		int start = index - noofdays + 1;
		float temp = 0, f = 0;
		double downmf = 0;
		if(status == LOWINDAYSSTATUS)
		{
			temp = 100000;
		}
		for (int i = start; i <= index; i++)
		{
			HashMap hs = (HashMap) inputdata.elementAt(i);
			if (status == LOWINDAYSSTATUS)
			{
				f = Float.parseFloat((String) hs.get(MainGraphComponent.Low));
				if (f < temp)
					temp = f;
			}
			else if (status == HIGHINDAYSSTAUS)
			{
				f = Float.parseFloat((String) hs.get(MainGraphComponent.High));
				if (f > temp)
					temp = f;
			}
		}
		return temp;
	}
	public float[] getClosePrice()
	{
		float[] returndata = new float[inputdata.size()];
		for (int x = 0; x < inputdata.size(); x++)
		{
			HashMap hs = (HashMap) inputdata.elementAt(x);
			returndata[x] = Float.parseFloat((String) hs.get(MainGraphComponent.Close));
		}
		return returndata;
	}
	public float[] getBidAsk(String type)
	{
		float[] returndata = new float[inputdata.size()];
		for (int x = 0; x < inputdata.size(); x++)
		{
			HashMap hs = (HashMap) inputdata.elementAt(x);
			try
			{
				String s = (String) hs.get(type);
				returndata[x] = Float.parseFloat(s);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		return returndata;
	}
	public String[] getDate()
	{
		String[] returndata = new String[inputdata.size()];
		for (int x = 0; x < inputdata.size(); x++)
		{
			HashMap hs = (HashMap) inputdata.elementAt(x);
			returndata[x] = (String) hs.get(MainGraphComponent.Date);
		}
		return returndata;
	}
//intraday indicators
	public float[] getDayOpenPrice()
	{
		float[] returndata = new float[inputdata.size()];
		for (int x = 0; x < inputdata.size(); x++)
		{
			HashMap hs = (HashMap) inputdata.elementAt(x);
			returndata[x] = Float.parseFloat((String) hs.get(MainGraphComponent.DayOpen));
		}
		return returndata;
	}
	public float[] getDayHighPrice()
	{
		float[] returndata = new float[inputdata.size()];
		for (int x = 0; x < inputdata.size(); x++)
		{
			HashMap hs = (HashMap) inputdata.elementAt(x);
			returndata[x] = Float.parseFloat((String) hs.get(MainGraphComponent.DayHigh));
		}
		return returndata;
	}
	public float[] getDayLowPrice()
	{
		float[] returndata = new float[inputdata.size()];
		for (int x = 0; x < inputdata.size(); x++)
		{
			HashMap hs = (HashMap) inputdata.elementAt(x);
			returndata[x] = Float.parseFloat((String) hs.get(MainGraphComponent.DayLow));
		}
		return returndata;
	}
	public float[] getTotalVolume()
	{
		float[] returndata = new float[inputdata.size()];
		for (int x = 0; x < inputdata.size(); x++)
		{
			HashMap hs = (HashMap) inputdata.elementAt(x);
			returndata[x] = Float.parseFloat((String) hs.get(MainGraphComponent.TotalVolume));
		}
		return returndata;
	}
	
	
	
	public float[] getOpenPrice()
	{
		float[] returndata = new float[inputdata.size()];
		for (int x = 0; x < inputdata.size(); x++)
		{
			HashMap hs = (HashMap) inputdata.elementAt(x);
			returndata[x] = Float.parseFloat((String) hs.get(MainGraphComponent.Open));
		}
		return returndata;
	}
	public float[] getHighPrice()
	{
		float[] returndata = new float[inputdata.size()];
		for (int x = 0; x < inputdata.size(); x++)
		{
			HashMap hs = (HashMap) inputdata.elementAt(x);
			returndata[x] = Float.parseFloat((String) hs.get(MainGraphComponent.High));
		}
		return returndata;
	}
	public float[] getLowPrice()
	{
		float[] returndata = new float[inputdata.size()];
		for (int x = 0; x < inputdata.size(); x++)
		{
			HashMap hs = (HashMap) inputdata.elementAt(x);
			returndata[x] = Float.parseFloat((String) hs.get(MainGraphComponent.Low));
		}
		return returndata;
	}
	public long[] getVolume()
	{
		long[] returndata = new long[inputdata.size()];
		for (int x = 0; x < inputdata.size(); x++)
		{
			HashMap hs = (HashMap) inputdata.elementAt(x);
			returndata[x] = Long.parseLong(((String) hs.get(MainGraphComponent.Volume)).trim());
		}
		return returndata;
	}
	public float[] getVolumeFloat()
	{
		float[] returndata = new float[inputdata.size()];
		for (int x = 0; x < inputdata.size(); x++)
		{
			HashMap hs = (HashMap) inputdata.elementAt(x);
			returndata[x] = Float.parseFloat((String) hs.get(MainGraphComponent.Volume));
		}
		return returndata;
	}
	public float[] createSimpleMovingAverage(int days, float[] close)
	{
		float[] returndata = new float[close.length];
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
		float[] returndata = new float[close.length];
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
		float[] ema = new float[close.length];
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
	public float[] createMaheshMovingAverage(int noofdays, float[] close, long[] volume)
	{
		float[] returndata = new float[inputdata.size()];
		float newmovingprice = 0;
		double numerator = 0;
		double wma = 0;
		double total = 0;
		long totalvolume = averagevolume * noofdays;
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
	public float[][] createBollingerPrice(int noofdayas, float bandratio)
	{
		float[] close = this.getClosePrice();
		float[] averageprice = this.createExpoMovingAverage(noofdayas, close);
		float[][] bollinger = new float[2][averageprice.length];
		for (int x = noofdayas; x < averageprice.length; x++)
		{
			double diff = 0;
			for (int y = 0; y < (noofdayas - 1); y++)
			{
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
	public float[][] createBollingerPriceAny(float[] close,int noofdayas, float bandratio)
	{

		float[] averageprice = this.createExpoMovingAverage(noofdayas, close);
		float[][] bollinger = new float[2][averageprice.length];
		for (int x = noofdayas; x < averageprice.length; x++)
		{
			double diff = 0;
			for (int y = 0; y < (noofdayas - 1); y++)
			{
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


	public float[][] createMACD(int noofdays12, int noofdays26, int noofdayssignal)
	{
		this.noofx = this.inputdata.size();
		float[] rate26 = new float[this.inputdata.size()];
		float[] rate12 = new float[this.inputdata.size()];
		float[] rate9 = new float[this.inputdata.size()];
		float[] close = this.getClosePrice();
		float[] macd = new float[this.inputdata.size()];
		float[] macd9 = new float[this.inputdata.size()];
		rate26 = this.createExpoMovingAverage(noofdays26, close);
		rate12 = this.createExpoMovingAverage(noofdays12, close);
		for (int i = 0; i < macd.length; i++)
		{
			macd[i] = 0;
			macd9[i] = 0;
		}
		for (int i = noofdays26; i < macd.length; i++)
		{
			if (rate26[i] == 0 || rate12[i] == 0)
			{
				close[i] = 0;
			}
			float macdtmp = rate12[i] - rate26[i];
			macd[i] = rate12[i] - rate26[i];
		}
		rate9 = createExpoMovingAverage(noofdayssignal, macd);
		float[][] ret = new float[2][this.inputdata.size()];
		ret[0] = macd;
		ret[1] = rate9;
		return ret;
	}
	public float[][] createMACDAny(float[] close,int noofdays12, int noofdays26, int noofdayssignal)
	{
		this.noofx = this.inputdata.size();
		float[] rate26 = new float[this.inputdata.size()];
		float[] rate12 = new float[this.inputdata.size()];
		float[] rate9 = new float[this.inputdata.size()];
		float[] macd = new float[this.inputdata.size()];
		float[] macd9 = new float[this.inputdata.size()];
		rate26 = this.createExpoMovingAverage(noofdays26, close);
		rate12 = this.createExpoMovingAverage(noofdays12, close);
		for (int i = 0; i < macd.length; i++)
		{
			macd[i] = 0;
			macd9[i] = 0;
		}
		for (int i = noofdays26; i < macd.length; i++)
		{
			if (rate26[i] == 0 || rate12[i] == 0)
			{
				close[i] = 0;
			}
			float macdtmp = rate12[i] - rate26[i];
			macd[i] = rate12[i] - rate26[i];
		}
		rate9 = createExpoMovingAverage(noofdayssignal, macd);
		float[][] ret = new float[2][this.inputdata.size()];
		ret[0] = macd;
		ret[1] = rate9;
		return ret;
	}

	public float[] getMACDSignalAny(float[] close,int noofdays12, int noofdays26, int noofdayssignal)
	{
		float[][] x = createMACDAny(close , noofdays12, noofdays26, noofdayssignal);
		return x[1];
	}
	public float[] getMACDSignal(int noofdays12, int noofdays26, int noofdayssignal)
	{
		float[][] x = createMACD(noofdays12, noofdays26, noofdayssignal);
		return x[1];
	}


	public float[] getMACDLineAny(float[] close,int noofdays12, int noofdays26, int noofdayssignal)
	{
		float[][] x = createMACDAny(close, noofdays12, noofdays26, noofdayssignal);
		return x[0];
	}
	
	public float[] getMACDLine(int noofdays12, int noofdays26, int noofdayssignal)
	{
		float[][] x = createMACD(noofdays12, noofdays26, noofdayssignal);
		return x[0];
	}
	
	public float[] getMACDDivAny(float[] close,int noofdays12, int noofdays26, int noofdayssignal)
	{
	
		float[][] x = createMACDAny(close, noofdayssignal, noofdays12, noofdays26);
		float[] retdata = new float[x[0].length];
		for(int i=noofdays26; i <x[0].length ; i++)
		{
			retdata[i] = x[0][i] - x[1][i];
		}
		return retdata;
	}
	
	public float[] getMACDDiv(int noofdays12, int noofdays26, int noofdayssignal)
	{
		
		float[][] x = createMACD(noofdays12, noofdays26, noofdayssignal);
		float[] retdata = new float[x[0].length];
		for(int i=noofdays26; i <x[0].length ; i++)
		{
			retdata[i] = x[0][i] - x[1][i];
		}
		return retdata;
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
	public void setAverageVolume()
	{
		this.noofx = this.inputdata.size();
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
				long vol = Long.parseLong((String) hs.get(MainGraphComponent.Volume));
				totalvolume = totalvolume + vol;
			}
			this.averagevolume = totalvolume / (this.inputdata.size());
		}
		catch (Exception e)
		{
		}
	}
	public float[] createMFI(int noofdays)
	{
		double[][] mftypprc = createMF();
		float[] mfi = new float[inputdata.size()];
		for (int i = noofdays; i < inputdata.size(); i++)
		{
			mfi[i] = getMFI(noofdays, i, mftypprc);
		}
	//	setMaxMin(mfi);
		return mfi;
	}
	public void setMaxMin(float val[])
	{
		float tmpmin = 50;
		float tmphigh = -100;
		for (int i = 0; i < val.length; i++)
		{
			if (tmphigh < val[i])
				tmphigh = val[i];
			if (tmpmin > val[i])
				tmpmin = val[i];
		}
	}
	float getMFI(int noofdays, int index, double[][] mf)
	{
		int start = index - noofdays + 1;
		double upmf = 0;
		double downmf = 0;
		for (int i = start; i <= index; i++)
		{
			if (mf[1][i] > mf[1][i - 1])
			{
				upmf = upmf + mf[0][i];
			}
			if (mf[1][i] < mf[1][i - 1])
			{
				downmf = downmf + mf[0][i];
			}
		}
		float moneyratio = (float) (upmf / (downmf));
		float mfi = (100 - 100 / (1 + moneyratio));
		return mfi;
	}
	public double[][] createMF()
	{
		double[][] moneyflowandtypprc = new double[2][inputdata.size()];
		double oldmoneyflow = 0;
		for (int i = 0; i < inputdata.size(); i++)
		{
			HashMap hs = (HashMap) inputdata.elementAt(i);
			float close = Float.parseFloat((String) hs.get(MainGraphComponent.Close));
			float high = Float.parseFloat((String) hs.get(MainGraphComponent.High));
			float low = Float.parseFloat((String) hs.get(MainGraphComponent.Low));
			float volume = Float.parseFloat((String) hs.get(MainGraphComponent.Volume));
			float typprc = (high + low + close) / 3;
			moneyflowandtypprc[0][i] = typprc * volume;
			moneyflowandtypprc[1][i] = typprc;
		}
		return moneyflowandtypprc;
	}
	//	method for creating Accumulation Distribution graph
	public float[] createWillamAccumulationDistribution()
	{
		float[] output = new float[inputdata.size()];
		float ad = 0;
		float prevad = 0;
		float tmplow = 0;
		float tmphigh = 0;
		HashMap hs = (HashMap) inputdata.elementAt(0);
		float prevclose = Float.parseFloat((String) hs.get(MainGraphComponent.Close));
		float open = Float.parseFloat((String) hs.get(MainGraphComponent.Open));
		float high = Float.parseFloat((String) hs.get(MainGraphComponent.High));
		float low = Float.parseFloat((String) hs.get(MainGraphComponent.Low));
		float close = 0;
		for (int i = 1; i < inputdata.size(); i++)
		{
			hs = (HashMap) inputdata.elementAt(i);
			close = Float.parseFloat((String) hs.get(MainGraphComponent.Close));
			open = Float.parseFloat((String) hs.get(MainGraphComponent.Open));
			high = Float.parseFloat((String) hs.get(MainGraphComponent.High));
			low = Float.parseFloat((String) hs.get(MainGraphComponent.Low));
			if (close > prevclose)
			{
				if (low < prevclose)
				{
					tmplow = low;
				}
				else
				{
					tmplow = prevclose;
				}
				ad = prevad + close - tmplow;
			}
			if (close < prevclose)
			{
				if (high < prevclose)
				{
					tmphigh = prevclose;
				}
				else
				{
					tmphigh = high;
				}
				ad = prevad - (tmphigh - close);
			}
			if (close == prevclose)
			{
				ad = prevad;
			}
			output[i] = ad;
			prevad = ad;
			prevclose = close;
		}
		return output;
	}
	//	method for creating Accumulation Distribution graph
	public float[] createAccumulationDistribution()
	{
		float[] output = new float[inputdata.size()];
		float prevAccDis = 0;
		for (int i = 0; i < inputdata.size(); i++)
		{
			HashMap hs = (HashMap) inputdata.elementAt(i);
			int volume = Integer.parseInt((String) hs.get(MainGraphComponent.Volume));
			volume = volume / 1000;
			float close = Float.parseFloat((String) hs.get(MainGraphComponent.Close));
			float open = Float.parseFloat((String) hs.get(MainGraphComponent.Open));
			float high = Float.parseFloat((String) hs.get(MainGraphComponent.High));
			float low = Float.parseFloat((String) hs.get(MainGraphComponent.Low));
			float clv = ((close - low) - (high - close)) / (high - low);
			float accDist = prevAccDis + clv * volume;
			int tmpint = (int) (accDist);
			output[i] = tmpint;
			prevAccDis = accDist;
		}
		//		  this.maxrsi = maxvalue;
		//		  this.minrsi = minvalue;
		return output;
	}
	public float[] createAverageTrueRange(int noofdays)
	{
		float[] truerange = createTrueRange();
		float[] emv10 = createExpoMovingAverage(noofdays, truerange);
		return emv10;
	}
	public float[] createTrueRange()
	{
		float[] truerange = new float[inputdata.size()];
		HashMap hs = (HashMap) inputdata.elementAt(0);
		float prevclose = Float.parseFloat((String) hs.get(MainGraphComponent.Close));
		float prevopen = Float.parseFloat((String) hs.get(MainGraphComponent.Open));
		float prevhigh = Float.parseFloat((String) hs.get(MainGraphComponent.High));
		float prevlow = Float.parseFloat((String) hs.get(MainGraphComponent.Low));
		float close = 0;
		float open = 0;
		float high = 0;
		float low = 0;
		float high1 = 0;
		float low1 = 0;
		for (int i = 1; i < inputdata.size(); i++)
		{
			hs = (HashMap) inputdata.elementAt(i);
			close = Float.parseFloat((String) hs.get(MainGraphComponent.Close));
			open = Float.parseFloat((String) hs.get(MainGraphComponent.Open));
			high = Float.parseFloat((String) hs.get(MainGraphComponent.High));
			low = Float.parseFloat((String) hs.get(MainGraphComponent.Low));
			if (high > prevclose)
			{
				high1 = high;
			}
			else
			{
				high1 = prevclose;
			}
			if (low < prevclose)
			{
				low1 = low;
			}
			else
			{
				low1 = prevclose;
			}
			truerange[i] = high1 - low1;
			prevclose = close;
		}
		return truerange;
	}
////Chande momentum oscillator (CMO)
	public float[] getCMO(int noofdays)
	{
		float upmove = 0;
		float downmove = 0;
		float[] cmo1 = new float[inputdata.size()];
		float[] cmo2 = new float[inputdata.size()];

		
		for (int i = 1; i < cmo1.length; i++)
		{
			HashMap hs = (HashMap) inputdata.elementAt(i);
			long volume = Long.parseLong((String) hs.get(MainGraphComponent.Volume));
			//			volume = volume/1000;
			float close = Float.parseFloat((String) hs.get(MainGraphComponent.Close));
			float open = Float.parseFloat((String) hs.get(MainGraphComponent.Open));
			float high = Float.parseFloat((String) hs.get(MainGraphComponent.High));
			float low = Float.parseFloat((String) hs.get(MainGraphComponent.Low));
			
			HashMap hsprev = (HashMap) inputdata.elementAt(i-1);
				
			long prevvolume = Long.parseLong((String) hsprev.get(MainGraphComponent.Volume));
			//			volume = volume/1000;
			float prevclose = Float.parseFloat((String) hsprev.get(MainGraphComponent.Close));
			float prevopen = Float.parseFloat((String) hsprev.get(MainGraphComponent.Open));
			float prevhigh = Float.parseFloat((String) hsprev.get(MainGraphComponent.High));
			float prevlow = Float.parseFloat((String) hsprev.get(MainGraphComponent.Low));
		
			
			float diff = close - prevclose;
			if ( diff > 0)
			{
				cmo1[i] = diff;
				cmo2[i] = 0;
			}
			else
			{
				cmo2[i] = -1 * diff;
				cmo1[i] = 0;
			}
		
		}
		float[] cmonew1 = createSimpleMovingAverage(noofdays,cmo1);
		float[] cmonew2 = createSimpleMovingAverage(noofdays,cmo2);
		float[] cmo = new float[cmonew1.length];
		for(int j=0;j<cmo.length;j++)
		{
			float myval = cmonew1[j] +cmonew2[j];
			if(myval==0)
				continue;
			cmo[j] = ( cmonew1[j] - cmonew2[j] ) / myval;
			
		}
		                        
		return cmo;
	}
	
	
//adx indicator	
	public float[] getADX(int noofdays)
	{
		float[] adx = new float[inputdata.size()];
		float[][] di = getDI(noofdays);
		float[] posdi =  di[0];
		float[] negdi =  di[1];
		
		for(int i=0;i<adx.length;i++)
		{
			try
			{
				float myval =  (posdi[i] + negdi[i]);
				if (myval==0)
				{
					adx[i] = 0;
				}
				else
				{
					adx[i] =   Math.abs(( posdi[i] - negdi[i] ) / myval) * 100;
					
				}
			}
			catch(Exception e)
			{
				adx[i] = 0;
			}
		}
		return adx;
	}
	public float[][] getDI(int noofdays)
	{
		float upmove = 0;
		float downmove = 0;
		float[] posdi = new float[inputdata.size()];
		float[] negdi = new float[inputdata.size()];

		
		for (int i = 1; i < posdi.length; i++)
		{
			HashMap hs = (HashMap) inputdata.elementAt(i);
			long volume = Long.parseLong((String) hs.get(MainGraphComponent.Volume));
			//			volume = volume/1000;
			float close = Float.parseFloat((String) hs.get(MainGraphComponent.Close));
			float open = Float.parseFloat((String) hs.get(MainGraphComponent.Open));
			float high = Float.parseFloat((String) hs.get(MainGraphComponent.High));
			float low = Float.parseFloat((String) hs.get(MainGraphComponent.Low));
			
			HashMap hsprev = (HashMap) inputdata.elementAt(i-1);
				
			long prevvolume = Long.parseLong((String) hsprev.get(MainGraphComponent.Volume));
			//			volume = volume/1000;
			float prevclose = Float.parseFloat((String) hsprev.get(MainGraphComponent.Close));
			float prevopen = Float.parseFloat((String) hsprev.get(MainGraphComponent.Open));
			float prevhigh = Float.parseFloat((String) hsprev.get(MainGraphComponent.High));
			float prevlow = Float.parseFloat((String) hsprev.get(MainGraphComponent.Low));
		
			
			upmove = high - prevhigh;
			downmove = prevlow - low;
			
			if ( upmove > downmove && upmove > 0)
			{
				posdi[i] = upmove;
			}
			else
			{
				posdi[i] = 0;
			}
		
			if ( downmove > upmove && downmove > 0)
			{
				negdi[i]  = downmove;
			}
			else
			{
				negdi[i] = 0;
			}
		}
		float[] truerange = createTrueRange();
		posdi = createExpoMovingAverage(noofdays, posdi);
		negdi = createExpoMovingAverage(noofdays, negdi);
		float[] posdinew = new float[posdi.length];
		float[] negdinew = new float[negdi.length];
		for(int i=0;i<posdi.length;i++)
		{
			if(truerange[i]==0)
			{
				posdinew[i] =0;
				negdinew[i] = 0;
				continue;
			}
			posdinew[i] = posdi[i] / truerange[i];
			negdinew[i] = negdi[i] / truerange[i];
	
		}
		float[][] retdi = new float[2][];
		retdi[0] = posdi;
		retdi[1] = negdi;
	
		return retdi;
	}
	
	
	
	public float[][] createAroopOscilator(int noofdays)
	{
		float[][] returnVal = new float[3][inputdata.size()];
		float[] aroopOsc = new float[inputdata.size()];
		float[] aroopUp = new float[inputdata.size()];
		float[] aroopDown = new float[inputdata.size()];
		
		for (int i = noofdays - 1; i < aroopOsc.length; i++)
		{
			int[] aroop = getAroopUPDown(noofdays, i);
			aroopUp[i] = (float) (100.0 * (noofdays - aroop[1]) / (float) noofdays);
			aroopDown[i] = (float) (100.0 * (noofdays - aroop[0]) / (float) noofdays);
			aroopOsc[i] = aroopUp[i] - aroopDown[i];
		}
		returnVal[0] =  aroopUp;
		returnVal[1] =  aroopDown;
		returnVal[2] =  aroopOsc;
		
		return returnVal;
	}
	int[] getAroopUPDown(int noofdays, int index)
	{
		int start = index - noofdays + 1;
		HashMap hs = (HashMap) inputdata.elementAt(start);
		float tmphigh = Float.parseFloat((String) hs.get(MainGraphComponent.High));
		float tmplow = Float.parseFloat((String) hs.get(MainGraphComponent.Low));
		int indexhigh = 1;
		int indexlow = 1;
		for (int i = start + 1; i <= index; i++)
		{
			hs = (HashMap) inputdata.elementAt(i);
			float high = Float.parseFloat((String) hs.get(MainGraphComponent.High));
			float low = Float.parseFloat((String) hs.get(MainGraphComponent.Low));
			if (low < tmplow)
			{
				indexlow++;
				tmplow = low;
			}
			if (high > tmphigh)
			{
				indexhigh++;
				tmphigh = high;
			}
		}
		int[] ret = new int[2];
		ret[0] = noofdays - indexlow;
		ret[1] = noofdays - indexhigh;
		return ret;
	}
	public float[] createChaikinMoneyFlow(int noofdays)
	{
		float[] chkosc = new float[inputdata.size()];
		for (int i = noofdays - 1; i < chkosc.length; i++)
		{
			float cmf = getChaikinMoneyFlow(noofdays, i);
			chkosc[i] = cmf;
		}
		return chkosc;
	}
	public float getChaikinMoneyFlow(int noofdays, int index)
	{
		int start = index - noofdays + 1;
		float[] output = new float[inputdata.size()];
		double clvvoltot = 0;
		double voltot = 0;
		for (int i = start; i <= index; i++)
		{
			HashMap hs = (HashMap) inputdata.elementAt(i);
			long volume = Long.parseLong((String) hs.get(MainGraphComponent.Volume));
			//			volume = volume/1000;
			float close = Float.parseFloat((String) hs.get(MainGraphComponent.Close));
			float open = Float.parseFloat((String) hs.get(MainGraphComponent.Open));
			float high = Float.parseFloat((String) hs.get(MainGraphComponent.High));
			float low = Float.parseFloat((String) hs.get(MainGraphComponent.Low));
			float clv = ((close - low) - (high - close)) / (high - low);
			float accDist = clv * volume;
			clvvoltot = clvvoltot + accDist;
			voltot = voltot + volume;
		}
		float cmf = (float) (clvvoltot / voltot);
		return cmf;
	}
	//end chanklin money flow	
	public float[] createChaikinOscillator()
	{
		float[] accdis = createAccumulationDistribution();
		float[] emv10 = createExpoMovingAverage(10, accdis);
		float[] accdis1 = createSimpleMovingAverage(3, accdis);
		float[] chkosc = new float[accdis.length];
		for (int i = 9; i < chkosc.length; i++)
		{
			chkosc[i] = accdis1[i] - emv10[i];
		}
		return chkosc;
	}
	public float[] createOnBalanceVolume()
	{
		this.noofx = this.inputdata.size();
		float[] onbalvol = new float[inputdata.size()];
		HashMap hs = (HashMap) inputdata.elementAt(0);
		long oldvol = Long.parseLong((String) hs.get(MainGraphComponent.Volume)) / 1000;
		float oldclose = Float.parseFloat((String) hs.get(MainGraphComponent.Close));
		float totvol = 0;
		for (int i = 1; i < inputdata.size(); i++)
		{
			hs = (HashMap) inputdata.elementAt(i);
			long volume = Long.parseLong((String) hs.get(MainGraphComponent.Volume));
			volume = volume / 1000;
			float close = Float.parseFloat((String) hs.get(MainGraphComponent.Close));
			if (close > oldclose)
				totvol = totvol + volume;
			if (close < oldclose)
				totvol = totvol - volume;
			onbalvol[i] = totvol;
			oldclose = close;
		}
		return onbalvol;
	}
	public float[] createRSIGraphs(int noofdays)
	{
		this.noofx = this.inputdata.size();
		float[] close = getClosePrice();
		float[] up = new float[close.length];
		float[] down = new float[close.length];
		for (int i = 0; i < close.length; i++)
		{
			if (i == 0)
			{
				up[i] = 0;
				down[i] = 0;
			}
			else
			{
				if (close[i] > close[i - 1])
				{
					up[i] = close[i] - close[i - 1];
					down[i] = 0;
				}
				else
				{
					down[i] = close[i - 1] - close[i];
					up[i] = 0;
				}
			}
		}
		float[] emaUp = createExpoMovingAverage(noofdays, up);
		float[] emaDown = createExpoMovingAverage(noofdays, down);
		float[] rs = new float[close.length];
		for (int i = 0; i < close.length; i++)
		{
			if (emaDown[i] == 0)
			{
				rs[i] = 10000000.00f;
			}
			else
			{
				rs[i] = emaUp[i] / emaDown[i];
			}
		}
		float[] rsi = new float[close.length];
		for (int i = 0; i < rsi.length; i++)
		{
			rsi[i] = 100 - 100 * (1 / (1 + rs[i]));
		}
		return rsi;
	}
//	Detrended Price Oscillator (DPO)
	public float[] createDetrendedPriceOscillator(int noofdays)
	{
		float[] dpo = new float[inputdata.size()];
	  float[] dpo1 = new float[inputdata.size()];
	  
		float[] simtypprc = createSimpleMovingAverage(noofdays, getClosePrice());
		  float[] close = getClosePrice();
		int x = noofdays/2;
		for(int i=x;i<inputdata.size();i++)
		{
		  dpo[i] = close[i] - simtypprc[i - x + 1]; 
		}
		return dpo;
	
	}	
	public float[][] createStochastic(int noofdays,int period2)
	{
		float[][] val = new float[2][];
		 val[0] = createStochastic(noofdays);
		val[1] = createFastStochastic(val[0],noofdays,period2);
		return val;
	}
	public float[] createFastStochastic(float[] perK,int period1,int period2)
	{
		float[] perD = new float[perK.length];
		
		perD = createSimpleMovingAverage(period2, perK);
		
		return perD;
	}

	public float[] createStochastic(int noofdays)
	{
		this.noofx = this.inputdata.size();
		float[] close = getClosePrice();

		float[] perK = new float[close.length];
		
		for(int i=noofdays;i<close.length;i++)
		{
			float[] val = getHighValues(noofdays, i);
			float diffden = val[1] - val[0];
			if(diffden==0)
			{
				diffden = 0.00001f;
			}
			perK[i] = ((close[i] - val[0])/(diffden))*100;
		}
		return perK;
	}

	public float[] createVolumeOscilatorGraphs(int noofdays12, int noofdays26)
	{
		this.noofx = this.inputdata.size();
		float[] volume12 = new float[this.inputdata.size()];
		float[] volume26 = new float[this.inputdata.size()];
		float[] pvo = new float[this.inputdata.size()];
		float[] volume = this.getVolumeFloat();
		volume12 = this.createExpoMovingAverage(noofdays12, volume);
		volume26 = this.createExpoMovingAverage(noofdays26, volume);
		for (int i = noofdays26 - 1; i < volume12.length; i++)
		{
			if (volume26[i] == 0)
				continue;
			//		  float voltmp = 100*((volume12[i] - volume26[i])/volume26[i]);
			float voltmp = 100 * ((volume12[i] - volume26[i]) / volume12[i]);
			pvo[i] = voltmp;
		}
		return pvo;
	}
	//method ends	

//	ease of movement	
	  private float[] createEaseofMovement(int noofdays)
	  {
		  float[] eom = new float[inputdata.size()];
		  for(int i=1;i<inputdata.size();i++)
		  {
			  HashMap hs =(HashMap) inputdata.elementAt(i);
			  float close = Float.parseFloat((String)hs.get(MainGraphComponent.Close)); 
			  float open = Float.parseFloat((String)hs.get(MainGraphComponent.Open)); 
			  float high = Float.parseFloat((String)hs.get(MainGraphComponent.High)); 
			  float low = Float.parseFloat((String)hs.get(MainGraphComponent.Low));
			  float volume = Float.parseFloat((String)hs.get(MainGraphComponent.Volume));

			  hs = (HashMap)inputdata.elementAt(i-1);
			  float close1 = Float.parseFloat((String)hs.get(MainGraphComponent.Close)); 
			  float open1 = Float.parseFloat((String)hs.get(MainGraphComponent.Open)); 
			  float high1 = Float.parseFloat((String)hs.get(MainGraphComponent.High)); 
			  float low1 = Float.parseFloat((String)hs.get(MainGraphComponent.Low));

			  float diff = (high - low)/2;
			  float diff1 = (high1 - low1)/2;
			  float volume1 = volume/(float)100000;
			  float midpoint = (diff) - (diff1);
			  float boxratio =  (volume1)/(high-low);
			  eom[i] =  midpoint/boxratio;  

		  }
		  float[] eom1 = createSimpleMovingAverage(noofdays,eom);
		  // TODO Auto-generated method stub
		  return eom1;
	  }
//Balance Of Power (BOP)      
	  

	/**
	 * @param indName
	 * @param parameter
	 * @return
	 */
	public static String allIndList =
		":COMPAREINTRA:DAYOPEN:DAYCLOSE:DAYHIGH:DAYLOW:TOTALVOLUME:HIST:COMPARE:ZIGZAG:PEAK:TROUGH:MFI:MOV:BOP:DEMA:CROSS:CMO:DIVERGENCE:DATE:SMA:MMA:EMA:WMA:RSI:WILPER:HIGH:LOW:CLOSE:OPEN:VOLUME:MACDSIGNAL:MACDSIGNALANY:MACDLINE:MACDLINEANY:MACDDIV:MACDDIVANY:UBBAND:UBBANDANY:LBBAND:LBBANDANY:HIGHINDAYS:LOWINDAYS:" + 
		"CCI:CMF:AROOPUP:AROOPDOWN:AROOPOSI:AVGTRUERANGE:WILLAMACCDIS:ACCDIS:TOTALINCOME:INTEREST:EBITDA:NETPROFIT:BASICEPS:DILUTEDEPS:NETSALES:" +
		"SMAANY:EMAANY:WMAANY:HIGHINDAYSANY:LOWINDAYSANY:DPO:VOLOSC:ONBALVOLUME:EASEOFMOVEMENT:CHKOSC:STOKPERK:STOKPERD:ADX:PDI:MDI:BIDPRICE:BIDQTY:OFFERPRICE:OFFERQTY:"
		;
//adx-->Average Directional Index            pdi--> positive directional movement         mdi--> minus directional movement  
	public static String financial = "TOTALINCOME:INTEREST:EBITDA:NETPROFIT:BASICEPS:DILUTEDEPS:NETSALES:";

	

	
	public static String zeropara = ":DAYOPEN:DAYCLOSE:DAYHIGH:DAYLOW:TOTALVOLUME:BOP:DATE:CHKOSC:ONBALVOLUME:CLOSE:OPEN:HIGH:LOW:VOLUME:WILLAMACCDIS:ACCDIS:BIDPRICE:BIDQTY:OFFERPRICE:OFFERQTY:";
	public static String onepara = ":HIST:MFI:DEMA:CMO:ADX:PDI:MDI:EASEOFMOVEMENT:SMA:DPO:WILPER:EMA:WMA:MMA:RSI:WMA:HIGHINDAYS:LOWINDAYS:CCI:CMF:AROOPUP:AROOPDOWN:AROOPOSI:AVGTRUERANGE:";//TOTALINCOME:INTEREST:EBITDA:NETPROFIT:BASICEPS:DILUTEDEPS:NETSALES:
	public static String twopara = ":COMPAREINTRA:COMPARE:ZIGZAG:PEAK:TROUGH:CROSS:STOKPERK:STOKPERD:VOLOSC:UBBAND:LBBAND:SMAANY:EMAANY:WMAANY:HIGHINDAYSANY:LOWINDAYSANY:";
	public static String threepara = ":MOV:DIVERGENCE:MACDLINE:MACDSIGNAL:MACDDIV:UBBANDANY:LBBANDANY:";
	public static String fourpara = ":MACDLINEANY:MACDSIGNALANY:MACDDIVANY:";
	
	BuySellStrategy buysell = new BuySellStrategy();
	public Vector exposeVector = new Vector();
	public String[] calculateFormulaCompare(String symbol,String expr)
	{
		buysell = new BuySellStrategy();
		buysell.symbol = symbol;;;
		
		try
		{
			Vector v = Loader.getContentFromFile(symbol);;
			v = createVectorByComparsion(v,inputdata);
			exposeVector = buysell.getValueForDataForCustmizedIndVector(v,expr);			
			
			 String[] retStr = new String[exposeVector.size()];
			 for(int i=0;i<retStr.length;i++)
			 {
				 HashMap hs = (HashMap)exposeVector.get(i);
				 
				 retStr[i] = (String)hs.get(DataBaseInMemory.IGSTEMP);
			 
			 }
			 return retStr;

		}
		catch (Exception e)
		{
			 String[] retStr = new String[inputdata.size()];
			return retStr;	
		}
		
	}
	public String[] calculateFormulaCompareIntraDay(String symbol,String expr)
	{
		buysell = new BuySellStrategy();
		buysell.symbol = symbol;;;
		
		try
		{
			Vector v = (Vector)intradHashMap.get(symbol);;
//			v = createVectorByComparsion(v,inputdata);
			
				Vector v1 = buysell.getValueForDataForCustmizedIndVector(v,expr);			
			
			 String[] retStr = new String[inputdata.size()];
			 HashMap myh =(HashMap)v1.lastElement();
			 String x = myh.get(DataBaseInMemory.IGSTEMP)+"";
			 for(int i=0;i<retStr.length;i++)
			 {
				 retStr[i] = x;
			 
			 }
			 return retStr;

		}
		catch (Exception e)
		{
			 String[] retStr = new String[inputdata.size()];
			return retStr;	
		}
		
	}
		
	public String[] calculateHistorical(String symbol,String expr)
	{
		buysell = new BuySellStrategy();
		buysell.symbol = symbol;;;
		
		try
		{
		//	Vector v = Loader.getContentFromFile(symbol);;
			Vector v = Loader.getFileContentForScreening(StockConstants.STARTDATE, symbol.toUpperCase(),StockConstants.SCREENDATE,StockConstants.ENDDATE);

			
			//		v = createVectorByComparsion(v,inputdata);
			v = buysell.getValueForDataForCustmizedIndVector(v,expr);			
			exposeVector = new Vector(inputdata.size());
			 String[] retStr = new String[inputdata.size()];
			 HashMap hs = (HashMap)v.lastElement();
			 String last = (String)hs.get(DataBaseInMemory.IGSTEMP);
			 for(int i=0;i<retStr.length;i++)
			 {
				 retStr[i] = last;
				 HashMap hs1 = new HashMap();
				 hs1.put(DataBaseInMemory.IGSTEMP, last);
				 exposeVector.add(hs1);
			 
			 }
			 return retStr;

		}
		catch (Exception e)
		{
			 String[] retStr = new String[inputdata.size()];
			return retStr;	
		}
		
	}
	int mypos=0;
	private Vector createVectorByComparsion(Vector newVector,Vector oldVector)
	{
		Vector newList = new Vector();
		
		for ( int i=0;i<oldVector.size();i++)
		{
			String oldDate = (String)((HashMap)oldVector.get(i)).get(MainGraphComponent.Date);
			HashMap hs = getCorrectHashMap(newVector,oldDate);
			if(hs !=null)
			{
				newList.add(hs);
			}
		}
		return newList;
	}
	public HashMap getCorrectHashMap(Vector newVector,String oldDate)
	{
		
		for(int i=mypos;i<newVector.size();i++)
		{
			HashMap hs = (HashMap)newVector.get(i);
			String newdate = (String)hs.get(MainGraphComponent.Date);
			if (Loader.isDateEquals(newdate,oldDate))
			{
				mypos = i;
				return hs;
			}
			else if (Loader.isDateGreater(newdate, oldDate))
			{
				mypos = i;
				return null;
			}
		}
		return null;
	}
	private String[] calculateFormula(String expr)
	{
		buysell = new BuySellStrategy();
		buysell.symbol = this.symbol;
		buysell.intradayHash = this.intradHashMap;
		exposeVector = buysell.getValueForDataForCustmizedIndVector(inputdata,expr);			
		
		
		 String[] retStr = new String[exposeVector.size()];
		 for(int i=0;i<retStr.length;i++)
		 {
			 HashMap hs = (HashMap)exposeVector.get(i);
			 
			 retStr[i] = (String)hs.get(DataBaseInMemory.IGSTEMP);
		 }
		 return retStr;
	}
	
	
	
	private String[] calculateIndicator(String indName, Vector parameter)
	{
		indName = indName.trim();
		Vector temp = new Vector();
		for(int i=0;i<parameter.size();i++)
		{
			temp.add(((String)parameter.get(i)).trim());
		}
		parameter = temp;
		
		float[] close = getClosePrice();
		long[] volume = getVolume();
		float[] retval = new float[volume.length];
		String s = getFinancialData(indName);
		
		if(includeHash.get(indName) != null)
		{
			String expr = (String)includeHash.get(indName);
			
			StringTokenizer st = new StringTokenizer(expr,"\n");
			String parameters = st.nextToken();
			if(parameters.startsWith("@PARAMETER"))
			{
				StringTokenizer st1 = new StringTokenizer(parameters," ");;
				st1.nextToken();
				Vector v = new Vector();
				while(st1.hasMoreTokens())
				{
					v.add(st1.nextToken());
				}
				for(int i=0;i<parameter.size()-1;i++)
				{
					String temps = "$"+v.elementAt(i)+"$";
					expr = Utility.replaceString(expr, temps, (String)parameter.get(i));
//					c = (byte)(c + 1);
				}
					
			}		
//			byte c = 'I';
			buysell = new BuySellStrategy();
			buysell.symbol = this.symbol;
			String[] retStr = calculateFormula(expr);			
			return retStr;
		}
		else if(s != null)
		{
			int val = Integer.parseInt((String) parameter.elementAt(0));

			String[] retStr = getResultData(this.symbol,indName,val);
			return retStr;
		}
		else if (indName.equalsIgnoreCase("DATE"))
		{
			String[] retStr = getDate();
			return retStr;
		}
	////Chande momentum oscillator (CMO)
		else if (indName.equalsIgnoreCase("CMO"))
		{
			int val = Integer.parseInt((String) parameter.elementAt(0));
			retval = getCMO(val);
			
		}
//dema-->Double Exponential Moving Average 		(2 * n-day EMA) - (n-day EMA of EMA)
		else if (indName.equalsIgnoreCase("DEMA"))
		{
			String expr = " 2 * EMA[%i%] - EMAANY[Ema[%i%;0];%i%;0] ";
			int val = Integer.parseInt((String) parameter.elementAt(0));
			expr = Utility.replaceString(expr,"%i%", val+"");
			// ln(expr);
			buysell = new BuySellStrategy();
			buysell.symbol = this.symbol;
			String[] retStr = buysell.getValueForDataForCustmizedInd(inputdata,expr);			
			return retStr;
			}
//Balance Of Power (BOP)      
		else if (indName.equalsIgnoreCase("BOP"))
		{
			String expr = " ( CLose[0] - OPen[0] )  /  ( HIgh[0]  - LOw[0] + .00001 )  ";
			// ln(expr);
			buysell = new BuySellStrategy();
			buysell.symbol = this.symbol;
			String[] retStr = buysell.getValueForDataForCustmizedInd(inputdata,expr);			
			return retStr;
		}
		else if (indName.equalsIgnoreCase("MFI"))
		{
			int val = Integer.parseInt((String) parameter.elementAt(0));
			retval = createMFI(val);
				
			
		}
		else if (indName.equalsIgnoreCase("ADX"))
		{
			int val = Integer.parseInt((String) parameter.elementAt(0));
			retval = getADX(val);
			
		}
		else if (indName.equalsIgnoreCase("PDI"))
		{
			int val = Integer.parseInt((String) parameter.elementAt(0));
			 float[][] retdata = getDI(val);
			 retval = retdata[0];
			 
		}
		else if (indName.equalsIgnoreCase("MDI"))
		{
			int val = Integer.parseInt((String) parameter.elementAt(0));
			 float[][] retdata = getDI(val);
			 retval = retdata[1];
			 
		}	else if (indName.equalsIgnoreCase("AVGTRUERANGE"))
		{
			int val = Integer.parseInt((String) parameter.elementAt(0));
			retval = createAverageTrueRange(val);
		}
		else if (indName.equalsIgnoreCase("CHKOSC"))
				{
					retval = createChaikinOscillator();
				}
		
		else if (indName.equalsIgnoreCase("EASEOFMOVEMENT"))
		{
			int val = Integer.parseInt((String) parameter.elementAt(0));
			retval = createEaseofMovement(val);
		}
		else if (indName.equalsIgnoreCase("STOKPERK"))
				{
					int val1 = Integer.parseInt((String) parameter.elementAt(0));
					int val2 = Integer.parseInt((String) parameter.elementAt(1));
					
					 float[][] x = createStochastic(val1, val2);
					retval = x[0];
				}
		else if (indName.equalsIgnoreCase("STOKPERD"))
						{
							int val1 = Integer.parseInt((String) parameter.elementAt(0));
							int val2 = Integer.parseInt((String) parameter.elementAt(1));
					
							 float[][] x = createStochastic(val1, val2);
							retval = x[1];
						}
		else if (indName.equalsIgnoreCase("DPO"))
		{
			int val = Integer.parseInt((String) parameter.elementAt(0));
			retval = createDetrendedPriceOscillator(val);
		}
		//volume oscillator
		else if (indName.equalsIgnoreCase("VOLOSC"))
		{
			int val1 = Integer.parseInt((String) parameter.elementAt(0));
			int val2 = Integer.parseInt((String) parameter.elementAt(1));
			
			retval = createVolumeOscilatorGraphs(val1, val2);
		}
		else if (indName.equalsIgnoreCase("AROOPUP"))
		{
			int val = Integer.parseInt((String) parameter.elementAt(0));
			float f[][] = createAroopOscilator(val);
			retval = f[0];
		}
		else if (indName.equalsIgnoreCase("AROOPDOWN"))
		{
			int val = Integer.parseInt((String) parameter.elementAt(0));
			float f[][] = createAroopOscilator(val);
			retval = f[1];
		}
		else if (indName.equalsIgnoreCase("AROOPOSI"))
		{
			int val = Integer.parseInt((String) parameter.elementAt(0));
			float f[][] = createAroopOscilator(val);
			retval = f[2];
		}
		else if (indName.equalsIgnoreCase(WILPER))
		{
			int val = Integer.parseInt((String) parameter.elementAt(0));
			retval = createWilliam(val);
		}

				
		else if (indName.equalsIgnoreCase(CCI))
		{
			int val = Integer.parseInt((String) parameter.elementAt(0));
			retval = createComodityChannelIndex(val);
		}
		else if (indName.equalsIgnoreCase(CMF))
		{
			int val = Integer.parseInt((String) parameter.elementAt(0));
			retval = createChaikinMoneyFlow(val);
		}
		else if (indName.equalsIgnoreCase(HIGHINDAYS))
		{
//SMAANY:EMAANY:WMAANY:HIGHINDAYSANY:LOWINDAYSANY:
			int val = Integer.parseInt((String) parameter.elementAt(0));
			retval = getHIGHLOWSDAYS( val,HIGHINDAYSSTAUS);
		}
		else if (indName.equalsIgnoreCase("HIGHINDAYSANY"))
		{
//SMAANY:EMAANY:WMAANY:HIGHINDAYSANY:LOWINDAYSANY:
			String mypara = (String) parameter.elementAt(0);
			HashMap hs = buysell.getIndNameAndPara(mypara);
			String indName1 = (String)hs.get(buysell.INDNAME);
			Vector para1 = (Vector)hs.get(buysell.PARAMETER);
			float f[] = convertStringToFloat(getResultForBuySell(indName1, para1));
			int val = Integer.parseInt((String) parameter.elementAt(1));
			retval = getHIGHLOWSDAYSANY(f, val,HIGHINDAYSSTAUS);
		}
		
		else if (indName.equalsIgnoreCase(LOWINDAYS))
		{
			int val = Integer.parseInt((String) parameter.elementAt(0));
			retval = getHIGHLOWSDAYS( val,LOWINDAYSSTATUS);
		}
	
		else if (indName.equalsIgnoreCase("LOWINDAYSANY"))
		{
			String mypara = (String) parameter.elementAt(0);
			HashMap hs = buysell.getIndNameAndPara(mypara);
			String indName1 = (String)hs.get(buysell.INDNAME);
			Vector para1 = (Vector)hs.get(buysell.PARAMETER);
			float f[] =  convertStringToFloat(getResultForBuySell(indName1, para1));
			int val = Integer.parseInt((String) parameter.elementAt(1));
			retval = getHIGHLOWSDAYSANY(f, val,LOWINDAYSSTATUS);
		}
		
		else if (indName.equalsIgnoreCase(MMA))
		{
			int val =(int)( Float.parseFloat((String) parameter.elementAt(0)));
			retval = createMaheshMovingAverage(val, close, volume);
		}
		else  if (indName.equals(SMA))
		{
			int val = Integer.parseInt((String) parameter.elementAt(0));
			
			retval = createSimpleMovingAverage(val, close);
		}
		else  if (indName.equals(EMA))
		{
			int val = Integer.parseInt((String) parameter.elementAt(0));
			
			retval = createExpoMovingAverage(val, close);
		}
		else  if (indName.equals(WMA))
		{
			int val = Integer.parseInt((String) parameter.elementAt(0));
			
			retval = createWeightedMovingAverage(val, close);
		}
		else  if (indName.equals("DIVERGENCE"))
		{
			String mypara = (String) parameter.elementAt(0);
			HashMap hs = buysell.getIndNameAndPara(mypara);
			String indName1 = (String)hs.get(buysell.INDNAME);
			Vector para1 = (Vector)hs.get(buysell.PARAMETER);
			float f[] =  convertStringToFloat(getResultForBuySell(indName1, para1));
			
			String mypara1 = (String) parameter.elementAt(1);
			HashMap hs1 = buysell.getIndNameAndPara(mypara1);
			String indName2 = (String)hs1.get(buysell.INDNAME);
			Vector para2 = (Vector)hs1.get(buysell.PARAMETER);
			float f1[] =  convertStringToFloat(getResultForBuySell(indName2, para2));

			
			int val = Integer.parseInt((String) parameter.elementAt(2));
			retval = createDivergence(f, f1,val);
		}
		else  if (indName.equals("MOV"))
		{
			String mypara = (String) parameter.elementAt(0);
			HashMap hs = buysell.getIndNameAndPara(mypara);
			String indName1 = (String)hs.get(buysell.INDNAME);
			Vector para1 = (Vector)hs.get(buysell.PARAMETER);
			float f[] =  convertStringToFloat(getResultForBuySell(indName1, para1));
			
			String days = (String) parameter.elementAt(1);
			int daysi = Integer.parseInt(days);
			String type = (String) parameter.elementAt(2);
			float f1[] = new float[f.length]; 
			if(type.equalsIgnoreCase("S"))
			{
				retval = createSimpleMovingAverage(daysi,f);
			}
			else if (type.equalsIgnoreCase("E"))
			{	
				retval = createExpoMovingAverage(daysi, f);
			}	
			else if (type.equalsIgnoreCase("W"))
			{	
				retval = createWeightedMovingAverage(daysi, f);
			}	
			
		}
		
		else  if (indName.equals("ZIGZAG"))
		{
			String mypara = (String) parameter.elementAt(0);
			HashMap hs = buysell.getIndNameAndPara(mypara);
			String indName1 = (String)hs.get(buysell.INDNAME);
			Vector para1 = (Vector)hs.get(buysell.PARAMETER);
			float f[] =  convertStringToFloat(getResultForBuySell(indName1, para1));
			
			String change = (String) parameter.elementAt(1);
			float changef = Float.parseFloat(change);
			float[][] retvalue = createZigzag(f,changef);
			
			retval = retvalue[2];
			
		}
		else  if (indName.equals("PEAK"))
		{
			String mypara = (String) parameter.elementAt(0);
			HashMap hs = buysell.getIndNameAndPara(mypara);
			String indName1 = (String)hs.get(buysell.INDNAME);
			Vector para1 = (Vector)hs.get(buysell.PARAMETER);
			float f[] =  convertStringToFloat(getResultForBuySell(indName1, para1));
			
			String change = (String) parameter.elementAt(1);
			float changef = Float.parseFloat(change);
			float[][] retvalue = createZigzag(f,changef);
			
			retval = retvalue[0];
			
		}
		else  if (indName.equals("TROUGH"))
		{
			String mypara = (String) parameter.elementAt(0);
			HashMap hs = buysell.getIndNameAndPara(mypara);
			String indName1 = (String)hs.get(buysell.INDNAME);
			Vector para1 = (Vector)hs.get(buysell.PARAMETER);
			float f[] =  convertStringToFloat(getResultForBuySell(indName1, para1));
			
			String change = (String) parameter.elementAt(1);
			float changef = Float.parseFloat(change);
			float[][] retvalue = createZigzag(f,changef);
			
			retval = retvalue[1];
			
		}
		else  if (indName.equals("CROSS"))
		{
			String mypara = (String) parameter.elementAt(0);
			HashMap hs = buysell.getIndNameAndPara(mypara);
			String indName1 = (String)hs.get(buysell.INDNAME);
			Vector para1 = (Vector)hs.get(buysell.PARAMETER);
			float f[] =  convertStringToFloat(getResultForBuySell(indName1, para1));
			
			String mypara1 = (String) parameter.elementAt(1);
			HashMap hs1 = buysell.getIndNameAndPara(mypara1);
			String indName2 = (String)hs1.get(buysell.INDNAME);
			Vector para2 = (Vector)hs1.get(buysell.PARAMETER);
			float f1[] =  convertStringToFloat(getResultForBuySell(indName2, para2));

			retval = createCross(f, f1);
		}

		
		else  if (indName.equals("SMAANY"))
		{
			String mypara = (String) parameter.elementAt(0);
			HashMap hs = buysell.getIndNameAndPara(mypara);
			String indName1 = (String)hs.get(buysell.INDNAME);
			Vector para1 = (Vector)hs.get(buysell.PARAMETER);
			float f[] =  convertStringToFloat(getResultForBuySell(indName1, para1));
			int val = Integer.parseInt((String) parameter.elementAt(1));
			retval = createSimpleMovingAverage(val, f);
		}
		
		else if (indName.equalsIgnoreCase("EMAANY"))
		{
			String mypara = (String) parameter.elementAt(0);
			HashMap hs = buysell.getIndNameAndPara(mypara);
			String indName1 = (String)hs.get(buysell.INDNAME);
			Vector para1 = (Vector)hs.get(buysell.PARAMETER);
			float f[] =  convertStringToFloat(getResultForBuySell(indName1, para1));
			int val = Integer.parseInt((String) parameter.elementAt(1));
			retval = createExpoMovingAverage(val, f);		
		}
		else if (indName.equalsIgnoreCase("WMAANY"))
		{
			String mypara = (String) parameter.elementAt(0);
			HashMap hs = buysell.getIndNameAndPara(mypara);
			String indName1 = (String)hs.get(buysell.INDNAME);
			Vector para1 = (Vector)hs.get(buysell.PARAMETER);
			float f[] =  convertStringToFloat(getResultForBuySell(indName1, para1));
			int val = Integer.parseInt((String) parameter.elementAt(1));
			retval = createWeightedMovingAverage(val, f);		
		}
		else if (indName.equalsIgnoreCase(RSI))
		{
			int val = Integer.parseInt((String) parameter.elementAt(0));
			retval = this.createRSIGraphs(val);
		}
		else if (indName.equalsIgnoreCase("WILLAMACCDIS"))
		{
			retval = createWillamAccumulationDistribution();
		}
		else if (indName.equalsIgnoreCase("ACCDIS"))
		{
			retval = createAccumulationDistribution();
		}

		else if (indName.equalsIgnoreCase(CLOSE))
		{
			retval = this.getClosePrice();
		}
		else if (indName.equalsIgnoreCase("ONBALVOLUME"))
				{
					retval = this.createOnBalanceVolume();
				}
				
		
		else if (indName.equalsIgnoreCase(VOLUME))
		{
			retval = this.getVolumeFloat();
		}
		else if (indName.equalsIgnoreCase(HIGH))
		{
			retval = this.getHighPrice();
		}
		else if (indName.equalsIgnoreCase(LOW))
		{
			retval = this.getLowPrice();
		}
		else if (indName.equalsIgnoreCase(OPEN))
		{
			retval = this.getOpenPrice();
		}
		else if (indName.equalsIgnoreCase("UBBANDANY"))
		{

			String mypara = (String) parameter.elementAt(0);
			HashMap hs = buysell.getIndNameAndPara(mypara);
			String indName1 = (String)hs.get(buysell.INDNAME);
			Vector para1 = (Vector)hs.get(buysell.PARAMETER);
			float f[] =  convertStringToFloat(getResultForBuySell(indName1, para1));
//			int val = Integer.parseInt((String) parameter.elementAt(1));
//			retval = createSimpleMovingAverage(val, f);
			
			int days = Integer.parseInt((String) parameter.elementAt(1));
			int bandratio = Integer.parseInt((String) parameter.elementAt(2));
			float[][] bval = createBollingerPriceAny(f,days, bandratio);
			retval = bval[0];
		}
		else if (indName.equalsIgnoreCase("LBBANDANY"))
		{

			String mypara = (String) parameter.elementAt(0);
			HashMap hs = buysell.getIndNameAndPara(mypara);
			String indName1 = (String)hs.get(buysell.INDNAME);
			Vector para1 = (Vector)hs.get(buysell.PARAMETER);
			float f[] =  convertStringToFloat(getResultForBuySell(indName1, para1));
//			int val = Integer.parseInt((String) parameter.elementAt(1));
//			retval = createSimpleMovingAverage(val, f);
			
			int days = Integer.parseInt((String) parameter.elementAt(1));
			int bandratio = Integer.parseInt((String) parameter.elementAt(2));
			float[][] bval = createBollingerPriceAny(f,days, bandratio);
			retval = bval[1];
		}
		else if (indName.equalsIgnoreCase(UBBAND))
		{
			int days = Integer.parseInt((String) parameter.elementAt(0));
			int bandratio = Integer.parseInt((String) parameter.elementAt(1));
			float[][] bval = createBollingerPrice(days, bandratio);
			retval = bval[0];
		}
		else if (indName.equalsIgnoreCase(LBBAND))
		{
			int days = Integer.parseInt((String) parameter.elementAt(0));
			int bandratio = Integer.parseInt((String) parameter.elementAt(1));
			float[][] bval = createBollingerPrice(days, bandratio);
			retval = bval[1];
		}
		else if (indName.equalsIgnoreCase(MACDLINE))
		{
			int day12 = Integer.parseInt((String) parameter.elementAt(0));
			int day26 = Integer.parseInt((String) parameter.elementAt(1));
			int day9 = Integer.parseInt((String) parameter.elementAt(2));
			retval = getMACDLine(day12, day26, day9);
		}
		else if (indName.equalsIgnoreCase("MACDLINEANY"))
		{

			String mypara = (String) parameter.elementAt(0);
			HashMap hs = buysell.getIndNameAndPara(mypara);
			String indName1 = (String)hs.get(buysell.INDNAME);
			Vector para1 = (Vector)hs.get(buysell.PARAMETER);
			float f[] =  convertStringToFloat(getResultForBuySell(indName1, para1));
//			int val = Integer.parseInt((String) parameter.elementAt(1));
//			retval = createSimpleMovingAverage(val, f);

			int day12 = Integer.parseInt((String) parameter.elementAt(1));
			int day26 = Integer.parseInt((String) parameter.elementAt(2));
			int day9 = Integer.parseInt((String) parameter.elementAt(3));
			retval = getMACDLineAny(f,day12, day26, day9);
		}
		else if (indName.equalsIgnoreCase("MACDSIGNALANY"))
		{

			String mypara = (String) parameter.elementAt(0);
			HashMap hs = buysell.getIndNameAndPara(mypara);
			String indName1 = (String)hs.get(buysell.INDNAME);
			Vector para1 = (Vector)hs.get(buysell.PARAMETER);
			float f[] =  convertStringToFloat(getResultForBuySell(indName1, para1));
//			int val = Integer.parseInt((String) parameter.elementAt(1));
//			retval = createSimpleMovingAverage(val, f);

			int day12 = Integer.parseInt((String) parameter.elementAt(1));
			int day26 = Integer.parseInt((String) parameter.elementAt(2));
			int day9 = Integer.parseInt((String) parameter.elementAt(3));
			retval = getMACDSignalAny(f,day12, day26, day9);
		}
		else if (indName.equalsIgnoreCase("MACDDIVANY"))
		{

			String mypara = (String) parameter.elementAt(0);
			HashMap hs = buysell.getIndNameAndPara(mypara);
			String indName1 = (String)hs.get(buysell.INDNAME);
			Vector para1 = (Vector)hs.get(buysell.PARAMETER);
			float f[] =  convertStringToFloat(getResultForBuySell(indName1, para1));
//			int val = Integer.parseInt((String) parameter.elementAt(1));
//			retval = createSimpleMovingAverage(val, f);

			int day12 = Integer.parseInt((String) parameter.elementAt(1));
			int day26 = Integer.parseInt((String) parameter.elementAt(2));
			int day9 = Integer.parseInt((String) parameter.elementAt(3));
			retval = getMACDDivAny(f,day12, day26, day9);
		}

		else if (indName.equalsIgnoreCase(MACDSIGNAL))
		{
			int day12 = Integer.parseInt((String) parameter.elementAt(0));
			int day26 = Integer.parseInt((String) parameter.elementAt(1));
			int day9 = Integer.parseInt((String) parameter.elementAt(2));
			retval = getMACDSignal(day12, day26, day9);
		}
		else if (indName.equalsIgnoreCase("COMPARE"))
		{
			String symbol = (String) parameter.elementAt(0);
			String mypara = (String) parameter.elementAt(1);
			
			retval =  convertStringToFloat(calculateFormulaCompare(symbol,mypara));
		}
		else if (indName.equalsIgnoreCase("COMPAREINTRA"))
		{
			String symbol = (String) parameter.elementAt(0);
			String mypara = (String) parameter.elementAt(1);
			
			retval =  convertStringToFloat(calculateFormulaCompareIntraDay(symbol,mypara));
		}
		
		else if (indName.equalsIgnoreCase("HIST"))
		{
		//	String symbol = (String) parameter.elementAt(0);
			String mypara = (String) parameter.elementAt(0);
			String[] ss = calculateHistorical(this.symbol, mypara);
			retval =  convertStringToFloat(ss);
		}
		
		else if (indName.equalsIgnoreCase("MACDDIV"))
		{
			int day12 = Integer.parseInt((String) parameter.elementAt(0));
			int day26 = Integer.parseInt((String) parameter.elementAt(1));
			int day9 = Integer.parseInt((String) parameter.elementAt(2));
			retval = getMACDDiv(day12, day26, day9);
		}
		else if (":BIDPRICE:BIDQTY:OFFERPRICE:OFFERQTY:".indexOf(":"+indName+":") != -1)
		{
			retval = this.getBidAsk(indName);
		}
//:DAYOPEN:DAYCLOSE:DAYHIGH:DAYLOW:TOTALVOLUME:  intraday indicators		
		else if (indName.equalsIgnoreCase("DAYOPEN"))
		{
			retval = getDayOpenPrice();
		}
		else if (indName.equalsIgnoreCase("DAYHIGH"))
		{
			retval = getDayHighPrice();
		}
		else if (indName.equalsIgnoreCase("DAYLOW"))
		{
			retval = getDayLowPrice();
		}
		else if (indName.equalsIgnoreCase("TOTALVOLUME"))
		{
			retval = getTotalVolume();
		}
		
		
		String[] retStr = new String[retval.length];
		for(int i=0;i<retval.length;i++)
		{
			retStr[i] = retval[i]+"";
		}
		return retStr;
	}
	private float[] createCross(float[] f, float[] f1)
	{
		float[] retvalue = new float[f.length];
		for(int i=1;i<f.length;i++)
		{
			if(( f[i] > f1[i] ) && ( f[i-1] <= f1[i-1]))
			{
				retvalue[i] = 1;
			}
		}
		return retvalue;
	}
//dema idicator --> 
	
	private float[] createDivergence(float[] f, float[] f1, float minchange)
	{
		float[] retvalue = new float[f.length];
		for(int i=1;i<f.length;i++)
		{
			if ( f[i-1] == 0)
			{
				continue;
			}
			float per = (f[i] - f[i-1]) * 100 / f[i-1];
			per = Math.abs(per);
			if( per < minchange )
			{
				continue;
			}
			if(( f[i] > f[i-1] ) && ( f1[i] < f1[i-1]))
			{
				retvalue[i] = 1;
			}
			if(( f[i] < f[i-1] ) && ( f1[i] > f1[i-1]))
			{
				retvalue[i] = -1;
			}
			
		}
		// TODO Auto-generated method stub
		return retvalue;
	}
	/**
	 * @param tokens
	 * @return
	 */
	
	public String getResultForScreener(String indName, Vector parameter)
	{
		String[] retval = calculateIndicator(indName, parameter);
		int oldvalue=-10000;
		if(includeHash.get(indName) != null)
		{
			if (parameter != null && parameter.size() == 1)
			{
				oldvalue = Integer.parseInt((String) parameter.elementAt(0));
			}
			
		}
		else if (zeropara.indexOf(indName) != -1)
		{
			if (parameter != null && parameter.size() == 1)
			{
				oldvalue = Integer.parseInt((String) parameter.elementAt(0));
			}
		}
		else if (onepara.indexOf(indName) != -1)
		{
			if (parameter.size() == 2)
			{
				oldvalue = Integer.parseInt((String) parameter.elementAt(1));
			}
		}
		else if (twopara.indexOf(indName) != -1)
		{
			if (parameter.size() == 3)
			{
				oldvalue = Integer.parseInt((String) parameter.elementAt(2));
			}
		}
		else if (threepara.indexOf(indName) != -1)
		{
			if (parameter.size() == 4)
			{
				oldvalue = Integer.parseInt((String) parameter.elementAt(3));
			}
		}

		if(oldvalue== -10000)
		{
			return retval[retval.length - 1];

		}
		try
		{
			String f10 = retval[retval.length - 1 + oldvalue];
			return f10;
		}
		catch(Exception e)
		{
			return "-10000";		
		}

	}
	public String[] getResultForBuySell(String indName, Vector parameter)
	{
		
		String[] retval = calculateIndicator(indName, parameter);
		indName = ":"+indName+":";	
		if (zeropara.indexOf(indName) != -1)
		{
			if (parameter != null && parameter.size() == 1)
			{
				int oldvalue = Integer.parseInt(((String) parameter.elementAt(0)).trim());
				retval = createNew(retval, oldvalue);
			}
		}
		else if (onepara.indexOf(indName) != -1)
		{
			if (parameter.size() == 2)
			{
				int oldvalue = Integer.parseInt(((String) parameter.elementAt(1)).trim());
				retval = createNew(retval, oldvalue);
			}
		}
		else if (twopara.indexOf(indName) != -1)
		{
			if (parameter.size() == 3)
			{
				int oldvalue = Integer.parseInt(((String) parameter.elementAt(2)).trim());
				retval = createNew(retval, oldvalue);
			}
		}
		else if (threepara.indexOf(indName) != -1)
		{
			if (parameter.size() == 4)
			{
				int oldvalue = Integer.parseInt(((String) parameter.elementAt(3)).trim());
				retval = createNew(retval, oldvalue);
			}
		}
		else
		{
			if (parameter != null  && parameter.size() != 0)
			{
				int oldvalue = Integer.parseInt((String) parameter.lastElement());
				retval = createNew(retval, oldvalue);
			}
			
		}
		return retval;
		
	}
	public float[] convertStringToFloat(String[] old)
	{
		float[] retval = new float[old.length];
		for(int i=0;i<old.length;i++)
		{
			retval[i] = Float.parseFloat(old[i]);
		}
		return retval;
	}
	public float[] createNew(float[] oldval, int moveby)
	{
		if(moveby > oldval.length)
		{
			return oldval;
		}
		float[] newval = new float[oldval.length];
		for (int i = 0; i < oldval.length; i++)
		{
			if ((i + moveby) < oldval.length && (i + moveby) >= 0)
				newval[i] = oldval[i + moveby];
		}
		return newval;
	}
	public String[] createNew(String[] oldval, int moveby)
	{
		if(moveby > oldval.length)
		{
			return oldval;
		}
		String[] newval = new String[oldval.length];
		for(int i=0;i<newval.length;i++)
		{
			newval[i] = "0";
		}
		for (int i = 0; i < oldval.length; i++)
		{
			if ((i + moveby) < oldval.length && (i + moveby) >= 0)
				newval[i] = oldval[i + moveby];
		}
		return newval;
	}

	public String getFinancialData(String token)
	{
		Properties p = (Properties)resultDataAll.get(this.symbol);
		if(p==null)
		{
			loadProperties();
		}
		p = (Properties)resultDataAll.get(this.symbol);
		if(p==null)
			return null;
		return p.getProperty(token);
		
	}
	public  boolean isTokenIndicator(String tokens)
	{
		tokens = tokens.trim();
		if (tokens.indexOf("[") != -1)
			tokens = tokens.substring(0, tokens.indexOf("["));
		if(StrategyUtility.getFormula(tokens) != null)
		{
			includeHash.put(tokens, StrategyUtility.getFormula(tokens));
			return true;
		}
		else if(includeHash.get(tokens) != null)
		{
			return true;
		}
		String tokens1 = ":" + tokens + ":";
		if (allIndList.indexOf(tokens1) != -1)
		{	
			return true;
		}
		else
		{	
			String s = getFinancialData(tokens);
			if(s==null)
				return false;
			return true;
		}
	}

	

}

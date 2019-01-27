package com.stockfaxforu.reports;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import org.omg.PortableInterceptor.SUCCESSFUL;

import com.stockfaxforu.component.MainGraphComponent;
import com.stockfaxforu.formulabuilder.ManageVariable;
import com.stockfaxforu.formulabuilder.MessageDiaglog;
import com.stockfaxforu.getolddataforu.loader.Loader;
import com.stockfaxforu.strategy.BuySellStrategy;
import com.stockfaxforu.strategy.StrategyUtility;
import com.stockfaxforu.util.StockConstants;
import com.stockfaxforu.util.Utility;

public class StockAnalysis 
{
	public String symbol="";
	public static String Symbol="SYMBOL";
	public static String SIGNAL_TYPE="SIGNAL_TYPE";
	public static String SIGNAL_DATE="SIGNAL_DATE";
	public static String AVG_SIGNAL_OCC="AVG_SIGNAL_OCC";
	public static String DATE="DATE";
	public static String PER_SIG_ACCURACY="PER_SIG_ACCURACY";
	public static String CLOSE="CLOSE";
	public static String SIG_PRICE="SIG_PRICE";
	public static String PER_CHG_AFTER_SIG="PER_CHG_AFTER_SIG";
		
	public HashMap indicatorHash = new HashMap();
	
	public static void main(String[] args)
	{
		StockAnalysis stockanalysis = new StockAnalysis("TCS");
		try
		{
			Vector v = StockAnalysis.getSymBolData("TCS");
			System.out.println(stockanalysis.applyDefaultStrategy("TCS",v));
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	/*	stockanalysis.calculateValue();
		StringBuffer sb = new StringBuffer(stockanalysis.createString("analysis.html"));
		sb.append(stockanalysis.createString("analysis1.html"));
		try
		{
			FileOutputStream fo = new FileOutputStream("/analysis/"+stockanalysis.symbol+".html");
			fo.write(sb.toString().getBytes());
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
	//		e.printStackTrace();
		}
		//	// ln(stock.createString());
	*/
	}

	
	public StockAnalysis(String symbol)
	{
		this.symbol = symbol;
	}
	
	public String applyBuySellFormula(String symbol,String formula,Vector data) throws Exception
	{
		Vector result = applyFormula(symbol, formula,data);
		HashMap buysellstats = findBuySellStats(result);
		if(buysellstats == null)
			return "";
		String finalString = createFinalString(buysellstats,formula);
		
		return finalString;
	}
	private String createFinalString(HashMap statsHash,String formula)
	{
		String color="";
		String colorend="</b></font>";
		
		if (statsHash.get(SIGNAL_TYPE).toString().equals(BUY))
		{
			color = "<font color=green><b>";
		}
		else
		{
			color = "<font color=red><b>";
			
		}
		StringBuilder strBuild = new StringBuilder();
		strBuild.append("<tr>\n\t");
		strBuild.append("<td>"+color + formula+colorend+"</td>");
		strBuild.append("<td>"+ color + statsHash.get(SIGNAL_DATE)+colorend+"</td>");
		strBuild.append("<td>"+color + statsHash.get(SIGNAL_TYPE)+colorend+"</td>");
		strBuild.append("<td>"+color + statsHash.get(PER_SIG_ACCURACY)+colorend+"</td>");
		strBuild.append("<td>"+color +statsHash.get(AVG_SIGNAL_OCC)+colorend+"</td>");
		strBuild.append("<td>"+color + statsHash.get(SIG_PRICE)+colorend+"</td>");
		strBuild.append("<td>"+color + statsHash.get(PER_CHG_AFTER_SIG)+colorend+"</td>");
		
		
		
		strBuild.append("</tr>\n");
		return strBuild.toString();
	}
	
	private static int NO_OF_DAYS_TO_CHECK= 3;
	int countbuysig =0;
	int countsellsig = 0;
	
	int countcorrectbuysig = 0;
	int countcorrectsellsig=0;
	
	private void calculateSignals(Vector result)
	{
		for(int i=0;i<result.size();i++)
		{
			HashMap hs = (HashMap)result.get(i);
			if (hs.get(BUY) != null)
			{
				countbuysig++;
				if (checkDifference(i,i+NO_OF_DAYS_TO_CHECK, result) > 0)
				{
					countcorrectbuysig++;
				}
			}
			else if (hs.get(SELL) != null)
			{
				countsellsig++;
				if (checkDifference(i,i+NO_OF_DAYS_TO_CHECK, result) > 0)
				{
					countcorrectsellsig++;
				}
			}
		}

	}
	private HashMap findBuySellStats(Vector result)
	{
		
		HashMap lastSignalRec = getLastSignalRecord(result);
		if (lastSignalRec==null)
			return null;
		
		calculateSignals(result);
		HashMap resultHash = createSignalReport(lastSignalRec,result);
		
		
		// TODO Auto-generated method stub
		return resultHash;
	}
	private HashMap createSignalReport(HashMap lastSignalRec,Vector result)
	{
		HashMap statHash = new HashMap();
		if (lastSignalRec.get(BUY) != null )
		{
			statHash.put(SIGNAL_TYPE, BUY);
			
			statHash.put(SIGNAL_DATE, lastSignalRec.get(DATE));
			int signaloccurance = result.size() / countbuysig;
			statHash.put(AVG_SIGNAL_OCC, signaloccurance);
			float signalaccuracy = countcorrectbuysig *100/ countbuysig;
			statHash.put(PER_SIG_ACCURACY, signalaccuracy);
			
			HashMap lastRecord = (HashMap)result.lastElement();
			float lastPrc = Float.parseFloat(lastRecord.get(CLOSE)+"");
			float sigPrc = Float.parseFloat(lastSignalRec.get(CLOSE)+"");
			float perchnageafterlastsig = (lastPrc - sigPrc)*100 / sigPrc;
			
			statHash.put(PER_CHG_AFTER_SIG, perchnageafterlastsig);
			statHash.put(SIG_PRICE, sigPrc+"");
			
			
			
		}
		else if (lastSignalRec.get(SELL) != null)
		{
			statHash.put(SIGNAL_TYPE, SELL);
			
			statHash.put(SIGNAL_DATE, lastSignalRec.get(DATE));
			int signaloccurance = result.size() / countsellsig;
			statHash.put(AVG_SIGNAL_OCC, signaloccurance);
			float signalaccuracy = countcorrectsellsig *100/ countsellsig;
			statHash.put(PER_SIG_ACCURACY, signalaccuracy);
			
			HashMap lastRecord = (HashMap)result.lastElement();
			float lastPrc = Float.parseFloat(lastRecord.get(CLOSE)+"");
			float sigPrc = Float.parseFloat(lastSignalRec.get(CLOSE)+"");
			float perchnageafterlastsig = (lastPrc - sigPrc)*100 / sigPrc;
			
			statHash.put(PER_CHG_AFTER_SIG, perchnageafterlastsig);
			statHash.put(SIG_PRICE, sigPrc+"");
			
		}
		return statHash;
	}
	public static String BUY="BUY";
	public static String SELL="SELL";
	private static int CHECK_RECORD=2;
	private HashMap getLastSignalRecord(Vector result)
	{
		int check=0;
		for(int i= result.size() -1  ; i >=0;i-- )
		{
			HashMap hs = (HashMap)result.get(i);
			if (check > CHECK_RECORD)
				return null;
			else if ((hs.get(BUY) != null) ||  hs.get(SELL) != null)  
			{
				return hs;
			}
			check++;
		}
		return null;
	}
	private float checkDifference(int currPos,int nextPos,Vector result)
	{
		if (nextPos >= result.size())
			nextPos = result.size() -1 ;
		
		HashMap currHash = (HashMap)result.get( currPos);
		HashMap newHash = (HashMap)result.get( nextPos);
		
		float currClose =  Float.parseFloat(currHash.get("CLOSE").toString());
		float newClose =  Float.parseFloat(newHash.get("CLOSE").toString());
		
		return newClose - currClose;
		

	}
	public static Vector getSymBolData(String symbol) throws Exception
	{
		return Loader.getContentFromFile(symbol);
			
	}
	public Vector applyFormula(String symbol,String formula,Vector data) throws Exception
	{
			HashMap hs1 = new HashMap();
			hs1.put(StrategyUtility.FormulaName, "def");
			String s=null;
			try
			{
				s = Utility.getFileContent("/analysis/buysell/"+formula);
			}
			catch (Exception e)
			{
				// TODO Auto-generated catch block
		//	e.printStackTrace();
			}
			hs1.put(StrategyUtility.Formula, s);
			
			hs1.put(StrategyUtility.Type, "B");
			Vector newVector = new Vector();
			newVector.add(hs1);

			String strQuery = (String)hs1.get(StrategyUtility.Formula);

//			String 	strategyType = ((String)hs1.get(StrategyUtility.Type)).substring(0,1);
			BuySellStrategy buysellstr = new BuySellStrategy();
//			Vector data=Loader.getContentFromFile(symbol);
			Vector outputVec = buysellstr.executeStrategy(data,strQuery);
		//	System.out.println(outputVec);
			return outputVec;
	}
	
	public Vector applyDefaultStrategy(String symbol,Vector data) throws Exception
	{
			HashMap hs1 = new HashMap();
			hs1.put(StrategyUtility.FormulaName, "def");
			String s=null;
			try
			{
				s = Utility.getFileContent("/analysis/buysellrecom.fl");
			}
			catch (Exception e)
			{
				// TODO Auto-generated catch block
		//	e.printStackTrace();
			}
			hs1.put(StrategyUtility.Formula, s);
			
			hs1.put(StrategyUtility.Type, "B");
			Vector newVector = new Vector();
			newVector.add(hs1);

			String strQuery = (String)hs1.get(StrategyUtility.Formula);

//			String 	strategyType = ((String)hs1.get(StrategyUtility.Type)).substring(0,1);
			BuySellStrategy buysellstr = new BuySellStrategy();
	//		Vector data=Loader.getContentFromFile(symbol);
			Vector outputVec = buysellstr.executeStrategy(data,strQuery);
		//	System.out.println(outputVec);
			return outputVec;
	}
	public void calculateIndicators()
	{
		BuySellStrategy buysell = new BuySellStrategy();
		Vector v1 = new Vector();
		HashMap hs = new HashMap();
		hs.put(Symbol, symbol);
		v1.add(hs);

		String query = " x := SMA[15;0]";
		Vector v = buysell.getAllStocksHashMap(v1, query);
		indicatorHash = (HashMap)v.get(0);
		System.out.println(indicatorHash);
		
	}
	public  void calculateValue()
	{
		BuySellStrategy buysell = new BuySellStrategy();
		Vector v1 = new Vector();
		HashMap hs = new HashMap();
		hs.put(Symbol, symbol);
		v1.add(hs);
		String[] indicators = {"MMA[15;0]","CLOSE[0]","HIGH[0]","LOW[0]","HIGHINDAYS[5;0]","HIGHINDAYS[20;0]","HIGHINDAYS[250;0]",
								"LOWINDAYS[5;0]","LOWINDAYS[20;0]","LOWINDAYS[250;0]","RSI[14;0]","WILPER[14;0]","MACDLINE[12;26;9;0]",
								"MACDLINE[12;26;9;-1]","MACDSIGNAL[12;26;9;0]","MACDSIGNAL[12;26;9;-1]","MMA[10;0]","MMA[40;0],SMA[10;0],SMA[40;0] "
		};
		StringBuffer sb = new StringBuffer(" ");
		for(int i=0;i<indicators.length;i++)
		{
			sb.append(indicators[i] + " ");
			if(i > (indicators.length - 1))
				sb.append(",");
		}
		String query = BuySellStrategy.SELECTSTOCKQUERY + sb.toString(); 
		Vector v = buysell.getAllStocksHashMap(v1, query);
		indicatorHash = (HashMap)v.get(0);
		

		float pclose = Float.parseFloat((String)indicatorHash.get("CLOSE[0]"));
		float phigh = Float.parseFloat((String)indicatorHash.get("HIGH[0]"));
		float plow = Float.parseFloat((String)indicatorHash.get("LOW[0]"));
		float pivotprice = (phigh+plow+pclose)/3;
		float r1 = Utility.floatDataAtTwoPrecision((pivotprice * 2 ) - plow);
		float r2 = Utility.floatDataAtTwoPrecision(pivotprice + (phigh - plow));
		float s1 = 	Utility.floatDataAtTwoPrecision((pivotprice * 2 ) - phigh);
		float s2 = Utility.floatDataAtTwoPrecision(pivotprice - (phigh - plow));
		String s="";
		indicatorHash.put("PIVOTPRICE", pivotprice+"");
		indicatorHash.put("Resistance1", r1+"");
		indicatorHash.put("Resistance2", r2+"");
		indicatorHash.put("Support1", s1+"");
		indicatorHash.put("Support2", s2+"");

		
		float high = Float.parseFloat((String)indicatorHash.get("HIGHINDAYS[5;0]"));
		float perdiff = Utility.floatDataAtTwoPrecision(((high - pclose)/pclose)*(-100));
		indicatorHash.put("PERDIFFWEEKLYHIGH",perdiff+"%");
		
		high = Float.parseFloat((String)indicatorHash.get("HIGHINDAYS[20;0]"));
		perdiff = Utility.floatDataAtTwoPrecision(((high - pclose)/pclose)*(-100));
		indicatorHash.put("PERDIFFMONTHLYHIGH",perdiff+"%");

		high = Float.parseFloat((String)indicatorHash.get("HIGHINDAYS[250;0]"));
		perdiff = Utility.floatDataAtTwoPrecision(((high - pclose)/pclose)*(-100));
		indicatorHash.put("PERDIFFYEARLYHIGH",perdiff+"%");

		float low = Float.parseFloat((String)indicatorHash.get("LOWINDAYS[5;0]"));
		perdiff = Utility.floatDataAtTwoPrecision(((low - pclose)/pclose)*(-100));
		indicatorHash.put("PERDIFFWEEKLYLOW",perdiff+"%");

		low = Float.parseFloat((String)indicatorHash.get("LOWINDAYS[20;0]"));
		perdiff = Utility.floatDataAtTwoPrecision(((low - pclose)/pclose)*(-100));
		indicatorHash.put("PERDIFFMONTHLYLOW",perdiff+"%");


		low = Float.parseFloat((String)indicatorHash.get("LOWINDAYS[250;0]"));
		perdiff = Utility.floatDataAtTwoPrecision(((low - pclose)/pclose)*(-100));
		indicatorHash.put("PERDIFFYEARLYLOW",perdiff+"%");


		float rsi = Float.parseFloat((String)indicatorHash.get("RSI[14;0]"));
		String rsistr="Currently RSI is between 30-70, No Signal";
		if(rsi < 30)
		{
			rsistr = "Currently RSI is less than 30, Shows stock is in oversold zone. Time to enter";
		}
		else if(rsi > 70)
		{
			rsistr = "Currently RSI is above 70, Shows stock is in overbought zone. Time to Sell";
		}
		indicatorHash.put("RSISTRING",rsistr);
		
		float wilper = Float.parseFloat((String)indicatorHash.get("WILPER[14;0]"))*(-100);
	//	// ln("wilper="+wilper);
		String wilstr="Currently Willam %R is between 10 to 90, No Signal";
		if(wilper > 90)
		{
			wilstr = "Currently Willam %R is greater than 90, Shows stock is in oversold zone. Time to enter";
		}
		else if(wilper < 10)
		{
			wilstr = "Currently Willam %R is less than 10, Shows stock is in overbought zone. Time to exit";
		}
		indicatorHash.put("WILPERSTRING",wilstr);
		
		StringBuffer macdstr = new StringBuffer();
		float macdbef = Utility.floatDataAtTwoPrecision(Float.parseFloat(indicatorHash.get("MACDLINE[12;26;9;-1]").toString()));
		float macdcurr = Utility.floatDataAtTwoPrecision(Float.parseFloat(indicatorHash.get("MACDLINE[12;26;9;0]").toString()));

		float macdsignalbef = Utility.floatDataAtTwoPrecision(Float.parseFloat(indicatorHash.get("MACDSIGNAL[12;26;9;-1]").toString()));
		float macdsignalcurr = Utility.floatDataAtTwoPrecision(Float.parseFloat(indicatorHash.get("MACDSIGNAL[12;26;9;0]").toString()));

	//	// ln("mamamamamaammamama");		
		float macddiffbefore = macdbef - macdsignalbef;
		
		float macddiffcurr = macdcurr - macdsignalcurr;
		
		macdstr.append("Current MACD is " + macdcurr + " and yesterday's MACD is " + macdbef + "<br>");
		macdstr.append("Current MACDSIGNAL is " + macdsignalcurr + " and yesterday's MACD is " + macdsignalbef + "<br>");
		
		if((macdcurr > macdbef) && (macdcurr > 0))
		{
			macdstr.append( "MACD is positive and rising,The gap between the 12-day EMA and the 26-day EMA is widening.This indicates that the rate-of-change of the faster moving average is higher than the rate-of-change for the slower moving average.Positive momentum is increasing, indicating a bullish period for the price plot");
			macdstr.append("<br>");
		}
		else if((macdcurr < macdbef) && (macdcurr < 0))
		{
			macdstr.append("MACD is negative and negative gap between the faster moving average  and the slower moving average  is expanding. Downward momentum is accelerating, indicating a bearish period of trading");
			macdstr.append("<br>");

		}
		
		if((macdbef < macdsignalbef ) && (macdcurr > macdsignalcurr) )
		{
			macdstr.append("Bullish Moving Average Crossover occuring (MACD moves above its 9-day EMA.It is most common and least reliable signal)");			
			macdstr.append("<br>");

		}
		else if((macdbef > macdsignalbef ) && (macdcurr < macdsignalcurr) )
		{
			macdstr.append("Bearish Moving Average Crossover occuring (MACD moves below its 9-day EMA.It is most common and least reliable signal)");			
			macdstr.append("<br>");
		
		}
		
		if(macdbef < 0 && macdcurr > 0)
		{
			macdstr.append("A Bullish Centerline Crossover occurs .This is a clear indication that momentum has changed from negative to positive, or from bearish to bullish.");
			macdstr.append("<br>");

		}
		else if(macdbef > 0 && macdcurr < 0)
		{
			macdstr.append("A Bearish Centerline Crossover occurs .This is a clear indication that momentum has changed from positive to negative, or from bullish to bearish.");
					macdstr.append("<br>");
		
		}
	//	// ln(macdstr);
		indicatorHash.put("MACDSTRING", macdstr.toString());	
		

		
		StringBuffer mmabuffer = new StringBuffer();
		float mma10 = Float.parseFloat((String)indicatorHash.get("MMA[10;0]"));
		float mma40 = Float.parseFloat((String)indicatorHash.get("MMA[40;0]"));
		
		float mma10perdiff = Utility.floatDataAtTwoPrecision(((pclose - mma10)/mma10)*100);
		float mma40perdiff = Utility.floatDataAtTwoPrecision(((pclose - mma40)/mma40)*100);

		float curr = pclose;
		if(curr > mma10 && curr > mma40 && mma10 > mma40)
		{
			mmabuffer.append("1)Stock trading in extremely bullish mode<br>");
			mmabuffer.append("2)Currently stock is above " + mma10perdiff + "% from MMA10.above 15% difference show stock is up without significant change is volume.Soon stock 's volume will up or correction will come to match MMA10. Time to sell if too much difference");
		} 
		else if(curr < mma10 && curr < mma40 && mma10 < mma40)
		{
			mmabuffer.append("1)Stock trading in extremely bearish mode<br>");
			mmabuffer.append("2)Currently stock is below " + mma10perdiff + "% from MMA10.Below 15% difference show stock is down without significant change is volume.Soon stock 's volume will up or price will up to match MMA10.If too much difference time to enter ");
		
		}
		else if(curr > mma10 && curr < mma40 && mma10 < mma40)
		{
			mmabuffer.append("1)Stock trading in bearish mode,trying to recover<br>");
			mmabuffer.append("2)Currently stock is above " + mma10perdiff + "% from MMA10 and below " + mma40perdiff + "% from MMA40.If stock is near to MMA40 then watch out stock and if it crosses MMA40 and stay above it time to enter");
		
		}
		else if(curr < mma10 && curr > mma40 && mma10 > mma40)
		{
			mmabuffer.append("1)Stock trading in bullish mode,time to enter<br>");
			mmabuffer.append("2)Currently stock is below " + mma10perdiff + "% from MMA10 and above " + mma40perdiff + "% from MMA40.Time to enter when stock again start moving in upward direction.Wait till correction complete");

		}

		indicatorHash.put("MMASTRING", mmabuffer.toString());	
		
		
		
		
	//	// (indicatorHash);
	}
	public String createString(String fileName)
	{
		RandomAccessFile input=null;
		StringBuffer retStrBuf = new StringBuffer();
		String retStr="";
		try
		{
			input = new RandomAccessFile(StockConstants.INSTALL_DIR + "/other/"+fileName, "r");
			
			String line="";
			while((line = input.readLine()) != null)
			{
				retStrBuf.append(line+"\n");	
			}
			retStr = Utility.createHTML(retStrBuf.toString(),indicatorHash);
		
		}
		catch(Exception e)
		{
		//	e.printStackTrace();
		}
		return retStr;
	}

	
	
		
}

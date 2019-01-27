/*
 * Created on Jul 8, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.component;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;

import com.stockfaxforu.config.ConfigUtility;
import com.stockfaxforu.config.YahooLogin;
import com.stockfaxforu.formulabuilder.MessageDiaglog;
import com.stockfaxforu.frontend.MainScreen;
import com.stockfaxforu.futureintraday.GetFutureIntradayData;
import com.stockfaxforu.livedata.LiveMarket;
import com.stockfaxforu.livemarket.YahooMail;
import com.stockfaxforu.strategy.BuySellStrategy;
import com.stockfaxforu.util.ConvertDataUtility;
import com.stockfaxforu.util.GetDataFromIDBI;
import com.stockfaxforu.util.GetIndexInfo;
import com.stockfaxforu.util.IndexUtility;
import com.stockfaxforu.util.StockConstants;
import com.stockfaxforu.util.Utility;

/**
 * @author sdeopura
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class IntradayThread implements Runnable
{
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	GraphComponentContainer graphcon = null;
	

	public IntradayThread(GraphComponentContainer graphcon)
	{
		this.graphcon = graphcon;
	}
	public static String convertICICIDate(String date)
	{
		String s="";
		StringTokenizer st = new StringTokenizer(date,"-");
		String dd = st.nextToken();
		String mm = Utility.getIntMonth(st.nextToken())+"";
		String yy = st.nextToken();
		
		return mm+"/"+dd+"/"+yy;
	}
	
	public  void getICICIData(String icicisymbol)
	{
		LiveMarket livemarket = new LiveMarket();
		String data = "";
		HashMap dataHashMap=null;
		try
		{
			dataHashMap = livemarket.getDataHashmap(icicisymbol);
			if(this.runwhile==false)
				return;
			
		}
		catch(Exception e)
		{
			
			return;
		}
//LASTTRADEPRICE		
		String current = ((String)dataHashMap.get("LASTTRADEPRICE")).trim();

		current = Utility.replaceString(current,",","");

		try
		{
			float f = Float.parseFloat(current);
			
		}
		catch(Exception e)
		{
			return;
		}
		String date = ((String)dataHashMap.get("DATE")).trim();
		date = convertICICIDate(date);
		String time = ((String)dataHashMap.get("LASTTRADEDTIME")).trim();
		date = date + "-" + time;
		HashMap hs = new HashMap();
		hs.put(MainGraphComponent.Date,date.trim());
		hs.put(MainGraphComponent.Open,current.trim());
		hs.put(MainGraphComponent.High,current.trim());
		hs.put(MainGraphComponent.Low,current.trim());
		hs.put(MainGraphComponent.Close,current.trim());

		String volume = ((String)dataHashMap.get("DAYVOLUME")).trim();
		volume = Utility.replaceString(volume,",","");

		
		try
		{
			float f = Float.parseFloat(volume);
			HashMap lastEle =(HashMap)graphcon.convert.originaldata.lastElement();
			if(lastEle != null)
			{
				try
				{
					String s = (String)lastEle.get(MainGraphComponent.TotalVolume);
					long l = Long.parseLong(s);
					long currvol = Long.parseLong(volume);
					long diff = currvol - l;
					hs.put(MainGraphComponent.TotalVolume,volume.trim());
					hs.put(MainGraphComponent.Volume,diff+"");
					
				}
				catch(Exception e2)
				{
					hs.put(MainGraphComponent.TotalVolume,volume.trim());
					hs.put(MainGraphComponent.Volume,1+"");
					
				}
				
			}
			else
			{
				hs.put(MainGraphComponent.TotalVolume,volume.trim());
				hs.put(MainGraphComponent.Volume,1+"");
			
			}
			
		}
		catch(Exception e1)
		{
			hs.put(MainGraphComponent.Volume,StockConstants.CONSTANTVOLUME+"");
			hs.put(MainGraphComponent.TotalVolume,StockConstants.CONSTANTVOLUME+"");
			
		}
//		// ln(graphcon.convert.inputdata.get(0));
//		// ln(graphcon.convert.inputdata.get(graphcon.convert.inputdata.size()-1));

//		// ln("hs="+hs);

		String bidprice = ((String)dataHashMap.get("BESTBIDPRICE")).trim();
		String bidqty = ((String)dataHashMap.get("BESTBIDQTY")).trim();
		String offerprice = ((String)dataHashMap.get("BESTOFFERPRICE")).trim();
		String offerqty = ((String)dataHashMap.get("BESTOFFERQTY")).trim();
		
		hs.put(MainGraphComponent.BidPrice, bidprice+"");
		hs.put(MainGraphComponent.BidQty, bidqty+"");
		hs.put(MainGraphComponent.OfferPrice, offerprice+"");
		hs.put(MainGraphComponent.OfferQty, offerqty+"");
		
		graphcon.convert.originaldata.add(hs);
		graphcon.convert.inputdata = BuySellStrategy.copyData(graphcon.convert.originaldata);
			graphcon.convert.noofx = graphcon.convert.inputdata.size();
		
	}
	private static int INTRADAY=1;
	private static int FUTURE=2;
	
	public void getAddIDBIDatasource(String symbol,int type)
	{
		String s ="";
		String month = ConfigUtility.getFutureMapping(graphcon.convert.expirydate);
		if(type==INTRADAY)
		{
			s = "http://trade.idbicapital.com/stsb/IDBILite/getQuoteDetail.asp?Exchange=NSE&tickerName="+symbol+"EQ";
		}
		else if (type==FUTURE)
		{
			s = "http://trade.idbicapital.com/stsb/IDBILite/getQuoteDetail.asp?Exchange=NSE&tickerName="+symbol+ "EQ%7EF%3A"+month +"-MON";
		}
	//	System.out.println(s);
		try
		{
			String ssss = Utility.getUrlContent(s);
			GetDataFromIDBI g = new GetDataFromIDBI();
			g.getDataFromIDBI(ssss);
			HashMap oldhs = g.getDataHash();
			HashMap hs = new HashMap();
			String date = oldhs.get("time")+"";
			String current = oldhs.get("ltp")+"";
			hs.put(MainGraphComponent.Date,date.trim());
			hs.put(MainGraphComponent.Open,current.trim());
			hs.put(MainGraphComponent.High,current.trim());
			hs.put(MainGraphComponent.Low,current.trim());
			hs.put(MainGraphComponent.Close,current.trim());

			
			try
			{
				String volume = oldhs.get("ttq")+"";
				HashMap lastEle =(HashMap)graphcon.convert.originaldata.lastElement();
				if(lastEle != null)
				{
					try
					{
						String s11 = (String)lastEle.get(MainGraphComponent.TotalVolume);
						long l = Long.parseLong(s11);
						long currvol = Long.parseLong(volume);
						long diff = currvol - l + 1;
						hs.put(MainGraphComponent.TotalVolume,volume.trim());
						hs.put(MainGraphComponent.Volume,diff+"");
						
					}
					catch(Exception e2)
					{
						hs.put(MainGraphComponent.TotalVolume,volume.trim());
						hs.put(MainGraphComponent.Volume,1+"");
						
					}
					
				}
				else
				{
					hs.put(MainGraphComponent.TotalVolume,volume.trim());
					hs.put(MainGraphComponent.Volume,1+"");
				
				}
				
			}
			catch(Exception e1)
			{
				hs.put(MainGraphComponent.Volume,StockConstants.CONSTANTVOLUME+"");
				hs.put(MainGraphComponent.TotalVolume,StockConstants.CONSTANTVOLUME+"");
				
			}


			
			graphcon.convert.originaldata.add(hs);
			graphcon.convert.inputdata = BuySellStrategy.copyData(graphcon.convert.originaldata);
			graphcon.convert.noofx = graphcon.convert.inputdata.size();

			
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
		//	e.printStackTrace();
		}
		
	}
	

	public void getAndAddYahoodata(String symbol) 
	{
		if(StockConstants.YAHOO==null)
		{
			try
			{
				YahooMail yahoo = new YahooMail();
				yahoo.loginToYahoo();
				StockConstants.YAHOO = yahoo;
			}
			catch(Exception e)
			{
				return;	
			}
		}	
		String data = "";
		try
		{
			data = StockConstants.YAHOO.getDataFromYahoo(symbol);
			
		}
		catch(Exception e)
		{
			
		}
		data = Utility.replaceString(data, "\"", "");
		StringTokenizer st = new StringTokenizer(data,",");
		
		st.nextToken();
		
		String current = st.nextToken();
		
		String mydate = st.nextToken();
		StringTokenizer mydatest = new StringTokenizer(mydate,"//");
		String mymonth = mydatest.nextToken();
		String myday = mydatest.nextToken();
		String myyear = mydatest.nextToken();
		int myyeari = Integer.parseInt(myyear) ;
		int mymonthi = Integer.parseInt(mymonth) - 1;
		int mydayi = Integer.parseInt(myday);
		
		String time = st.nextToken();
		time = Utility.replaceString(time, "am", "");
		time = Utility.replaceString(time, "pm", "");
		
		time = time + ":00";
		String[] s = time.split(":");
		int hour = Integer.parseInt(s[0]) ;
		int minute = Integer.parseInt(s[1]);
		int second = Integer.parseInt(s[2]);
//		// ln(hour+":"+minute+":"+second);
//		minute = minute + 30;
//		int carry = minute / 60;
//		hour = hour + carry + StockConstants.YahooHourDifference;
//		minute = minute % 60;
		// ln("after="+hour+":"+minute+":"+second);
		Calendar c = Calendar.getInstance();
		c.set(myyeari,mymonthi, mydayi, hour, minute, 0);
		c.add(Calendar.HOUR_OF_DAY, StockConstants.YahooHourDifference);
		c.add(Calendar.MINUTE, 30);
		
		time = ( c.get(Calendar.MONTH) + 1)  + "/" + c.get(Calendar.DATE) + "/" +  c.get(Calendar.YEAR) + "-" + c.get(Calendar.HOUR) + ":" +  c.get(Calendar.MINUTE) + ":00" ;
		// ln("time="+time);
		/*		Calendar cal = Calendar.getInstance();
		cal.set(Calendar., value)
		cal.set(Calendar.HOUR, Integer.parseInt(s[0]) + 9);
		cal.set(Calendar.MINUTE, Integer.parseInt(s[1]) + 30);
		cal.set(Calendar.SECOND, Integer.parseInt(s[2]) );
		time = cal.get(Calendar.HOUR) + ":" + cal.get(Calendar.MINUTE) + ":" + cal.get(Calendar.SECOND);
*/
//		// ln("time="+time);
		st.nextToken();
		st.nextToken();
		st.nextToken();
		st.nextToken();


		String volume = st.nextToken().trim();


		HashMap hs = new HashMap();
		hs.put(MainGraphComponent.Date,time.trim());
		hs.put(MainGraphComponent.Open,current.trim());
		hs.put(MainGraphComponent.High,current.trim());
		hs.put(MainGraphComponent.Low,current.trim());
		hs.put(MainGraphComponent.Close,current.trim());
		
//		hs.put(MainGraphComponent.Volume,volume.trim());
//		// ln(graphcon.convert.inputdata.get(0));
//		// ln(graphcon.convert.inputdata.get(graphcon.convert.inputdata.size()-1));

//		// ln("hs="+hs);

		
		try
		{
			float f = Float.parseFloat(volume);
			HashMap lastEle =(HashMap)graphcon.convert.originaldata.lastElement();
			if(lastEle != null)
			{
				try
				{
					String s11 = (String)lastEle.get(MainGraphComponent.TotalVolume);
					long l = Long.parseLong(s11);
					long currvol = Long.parseLong(volume);
					long diff = currvol - l + 1;
					hs.put(MainGraphComponent.TotalVolume,volume.trim());
					hs.put(MainGraphComponent.Volume,diff+"");
					
				}
				catch(Exception e2)
				{
					hs.put(MainGraphComponent.TotalVolume,volume.trim());
					hs.put(MainGraphComponent.Volume,1+"");
					
				}
				
			}
			else
			{
				hs.put(MainGraphComponent.TotalVolume,volume.trim());
				hs.put(MainGraphComponent.Volume,1+"");
			
			}
			
		}
		catch(Exception e1)
		{
			hs.put(MainGraphComponent.Volume,StockConstants.CONSTANTVOLUME+"");
			
		}

		
		
		graphcon.convert.originaldata.add(hs);
		graphcon.convert.inputdata = BuySellStrategy.copyData(graphcon.convert.originaldata);
		graphcon.convert.noofx = graphcon.convert.inputdata.size();
	
	}
	
	public static void main(String[] args)
	{
	//	IntradayThread intth = new IntradayThread();
	//	Vector v = IndexUtility.getIndexStockVector("cnx500");
		Vector v = new Vector();
		v.add("TCS");
		v.add("S&P CNX NIFTY");
		v.add("BANK NIFTY");
			
		IntradayThread th = new IntradayThread(null);
		for(int i=0;i<v.size();i++)
		{
			try
			{
	//			String s = Utility.getFileContent("/igstest/idbi.txt");
				//S&P CNX NIFTY /BANK NIFTY  /S&P CNX 500/NIFTY MIDCAP 50/ for future TCSEQ~F:1-MON // future nifty S&P CNX NIFTY~F:1-MON 
				
				//String s = Utility.getUrlContent("http://trade.idbicapital.com/stsb/IDBILite/getQuoteDetail.asp?Exchange=NSE&tickerName="+URLEncoder.encode("TCSEQ~F:1-MON"));
				String s = "http://trade.idbicapital.com/stsb/IDBILite/getQuoteDetail.asp?Exchange=NSE&tickerName="+URLEncoder.encode(v.get(i)+"EQ~F:1-MON");
				
				System.out.println(s);
				;;
				//	th.getDataFromIDBI(s);
			}
			catch(Exception e)
			{
		//		e.printStackTrace();
			}
		}
		
	/*	
		Properties iciciProperty = new Properties();
		LiveMarket livemkt = new LiveMarket();
		try
		{
			iciciProperty.load(new FileInputStream(StockConstants.ICICIMAPPING));
			Enumeration e = iciciProperty.elements();
			while(e.hasMoreElements())
			{
				String s = (String)e.nextElement();
				try
				{
					HashMap dataHashMap = livemkt.getDataHashmap(s);
					// ln("symbol " + s +  dataHashMap.toString());
				}
				catch(Exception e1)
				{
					// ln("symbol " + s +  " error occured");
		
				}			
			}	
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
		} */

	}
	public boolean runwhile=true;
	
	public void runIntraday() throws Exception
	{
		
				try
				{
					//graphcon.convert.loadFileForIntradayIndiaBull(graphcon.symbol);
					graphcon.convert.setHistIntradayFromYahoo(graphcon.symbol);
				//	ConvertDataUtility.getHistoricalIntradayData(graphcon.symbol,"");
					
					graphcon.convert.originaldata =BuySellStrategy.copyData( graphcon.convert.inputdata);
					firsttimeloaded=true;
						
				}
				catch(Exception e11)
				{
					graphcon.drawError("Not able to download data for symbol, change time (intraday graph ) or change symbol. ");
					
				}
				
			Vector v = new Vector();
			v.addAll(graphcon.histIntradayData);
			v.addAll(graphcon.convert.originaldata);
			
			graphcon.convert.inputdata = v;
			graphcon.convert.noofx = graphcon.convert.inputdata.size();
			
			setTimeResolution();
		//	graphcon.dataVector = graphcon.convert.inputdata;
			
//			graphcon.convert.isintraday = true;
			graphcon.setGraphResolution(graphcon.convert.chartResolution);
		//	graphcon.dataVector = graphcon.convert.inputdata;
			graphcon.setForZooming();
			//					graphcon.convert.setHighLowValues();
			if(graphcon.convert.expirydate==null || graphcon.convert.expirydate.equals(""))
			{
				graphcon.convert.note = Utility.getStockDes(graphcon.symbol) + "(Intraday)";

			}
			else
			{
				graphcon.convert.note = Utility.getStockDes(graphcon.symbol) + "(Intraday Future Exp " + graphcon.convert.expirydate + ")";
				
			}
			if(graphcon.convert.inputdata!=null && graphcon.convert.inputdata.size()>0)
			{
				
				// ln("removeVolumeAndSetOtherData");
				graphcon.removeVolumeAndSetOtherData(false);
				// ln("removeVolumeAndSetOtherData");

				//					graphcon.drawvolumeind = false;
				graphcon.applySettingIntraday();
				graphcon.applyIntradayDefaultStrategy();
				graphcon.repaint();
//				// ln("sleeping");

			}
			graphcon.repaint();


		
	}
		boolean firsttimeloaded=false;
		String yahoosymbol ;
		String icicisymbol ;

		public void run()
		{

		boolean firsttimeloaded=false;
		yahoosymbol = Utility.getYahooSymbol(graphcon.symbol);
		icicisymbol = Utility.getICICISymbol(graphcon.symbol);
		
		try
		{
			while (runwhile==true)
			{
				try
				{
					graphcon.convert.symbolName = graphcon.symbol;
					if(graphcon.convert.isintraday)
						runIntraday();
					else if (graphcon.convert.isfutureintraday)
					{
						runFuture();
					}
					Thread.sleep(StockConstants.autoupdateintradaygraph * 1000 + 2000);
				}
				catch (Exception e)
				{
				//	e.printStackTrace();
				}
//				MainScreen.getSingleton().doReSize();
			}
		}
		catch (Exception e1)
		{
	//		e1.printStackTrace();
		}
		// TODO Auto-generated method stub
	}
	public void runFuture()
	{
	//	graphcon.convert.inputdata = graphcon.convert.loadFileForIntraday(graphcon.symbol,graphcon.convert.expirydate);
	
		//new code
		graphcon.convert.inputdata = GetFutureIntradayData.getFutureIntradayDataInVector(graphcon.symbol,graphcon.convert.expirydate);
				
				
		graphcon.convert.noofx = graphcon.convert.inputdata.size();
		graphcon.convert.originaldata = graphcon.convert.inputdata;
		
		getAddIDBIDatasource(graphcon.symbol,FUTURE);

				
		setTimeResolution();
		
	//	graphcon.dataVector = graphcon.convert.inputdata;
		
//		graphcon.convert.isfutureintraday = true;
//		graphcon.convert.isintraday = false;
		
		graphcon.setGraphResolution(graphcon.convert.chartResolution);
		graphcon.setForZooming();

		
		if(graphcon.convert.expirydate==null || graphcon.convert.expirydate.equals(""))
		{
			graphcon.convert.note = Utility.getStockDes(graphcon.symbol) + "(Intraday)";

		}
		else
		{
			graphcon.convert.note = Utility.getStockDes(graphcon.symbol) + "(Intraday Future Exp " + graphcon.convert.expirydate + ")";
			
		}
		if(graphcon.convert.inputdata!=null && graphcon.convert.inputdata.size()>0)
		{
			
			// ln("removeVolumeAndSetOtherData");
			graphcon.removeVolumeAndSetOtherData(false);
			// ln("removeVolumeAndSetOtherData");

			//					graphcon.drawvolumeind = false;
			graphcon.applySettingIntraday();
			graphcon.applyIntradayDefaultStrategy();
			graphcon.repaint();
//			// ln("sleeping");

		}
		
	
	}
	public void setTimeResolution()
	{
		Vector v = new Vector();
	//	// ln("startdate="+graphcon.convert.startDate);
		String mystartdate="";
		mystartdate = graphcon.convert.startDate;
		if (graphcon.convert.inputdata==null || graphcon.convert.inputdata.size()==0 )
		{
			return;
		}
		if(graphcon.convert.startDate.indexOf("today") != -1)
		{
			HashMap hs = (HashMap)graphcon.convert.inputdata.lastElement();
			String date = (String)hs.get(MainGraphComponent.Date);
			StringTokenizer oldMainst = new StringTokenizer(date,"-");
			String oldDate = oldMainst.nextToken();	
			
			StringTokenizer st1 = new StringTokenizer(graphcon.convert.startDate,"-");
			st1.nextToken();
			String time = st1.nextToken();
			mystartdate = oldDate + "-" + time;
			
		}
		int pos=0;
	//	// ln("mystartdate="+mystartdate);
	//	// ln("firstele="+graphcon.convert.inputdata.get(0));

		for(pos=0;pos<graphcon.convert.inputdata.size();pos++)
		{
			//mahesh time being
			if(true)
				break;
			HashMap hs = (HashMap)graphcon.convert.inputdata.get(pos);
			String date = (String)hs.get(MainGraphComponent.Date);
		//	// ln(mystartdate+","+date);
			if(MainGraphComponent.isTimeDiferenceGreater(0, mystartdate,date))
			{
//				// ln("in break=");
		//	// ln(mystartdate+","+date);

				break;
			}
			
		}
		for(int j=pos ;j < graphcon.convert.inputdata.size();j++)
		{
			v.addElement(graphcon.convert.inputdata.get(j));
		}
		graphcon.convert.inputdata = v;
	}
	
}

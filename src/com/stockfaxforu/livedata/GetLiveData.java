/*
 * Created on Mar 11, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.livedata;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.plaf.SliderUI;
import javax.swing.table.TableCellRenderer;

import com.stockfaxforu.component.MYDimension;
import com.stockfaxforu.component.MainGraphComponent;
import com.stockfaxforu.formulabuilder.MessageDiaglog;
import com.stockfaxforu.frontend.AlertScreen;
import com.stockfaxforu.livemarket.YahooMail;
import com.stockfaxforu.livemarket.ticker.HorizontalTicker;
import com.stockfaxforu.query.DataBaseInMemory;
import com.stockfaxforu.strategy.BuySellStrategy;
import com.stockfaxforu.trading.BuySellStockScreen;
import com.stockfaxforu.util.StockConstants;
import com.stockfaxforu.util.Utility;

/**
 * @author sdeopura
 * 
 *         To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class GetLiveData implements Runnable {
	ShowLiveMarket shoelive = null;
	String[][] oldDataValue = null;
	String[][] dataValue = null;
	public boolean runThread = true;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public GetLiveData(ShowLiveMarket shoelive) {
		this.shoelive = shoelive;
	}
	HashMap globalIntradayData = null; 
	public void run() 
	{
		globalIntradayData = new HashMap();
		// String[] data = null;
		String dataStr = "";

		while (runThread) 
		{
			try 
			{
//				dataStr = yahoo.getDataFromYahoo();
//				System.out.println(dataStr);	
				if (dataStr == null) {
				} else {
					dataValue = convertDataValue(dataStr);
			}
				if (oldDataValue == null) {
					oldDataValue = dataValue;
				}
				if (shoelive.scrollPane != null && shoelive.table != null) {
					shoelive.scrollPane.remove(shoelive.table);
					shoelive.remove(shoelive.scrollPane);
				}
//				shoelive.table = new JTable(dataValue, shoelive.columnNames);
//				TableCellRenderer renderer = new CustomTableCellRenderer(
//						oldDataValue, dataValue);
//				shoelive.table.setDefaultRenderer(Object.class, renderer);
				// Add the table to a scrolling pane
				shoelive.scrollPane = JTable
						.createScrollPaneForTable(shoelive.table);
				shoelive.scrollPane.setBounds(shoelive.x1 / 2, 0,
						shoelive.x1 / 2 - 10, shoelive.y1);
				for (int i = 0; i < dataValue.length; i++) 
				{
					float dataf=0,datao=0;
					try
					{
						dataf = Float.parseFloat(dataValue[i][1]);
						datao = Float.parseFloat(oldDataValue[i][1]);
					}
					catch(Exception e)
					{
						
					}
					if (dataf >= datao) 
					{
						TableCellRenderer cell = shoelive.table
								.getCellRenderer(i, 1);
						cell.getTableCellRendererComponent(shoelive.table,
								dataf + "", true, true, i, 1);
					}
				}
				shoelive.add(shoelive.scrollPane, null);

				shoelive.repaint();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				System.out.println("i am in exception");
				e1.printStackTrace();
			}
			try 
			{
				Thread.sleep(5000);
			} catch (Exception e) {
			}
		}
	}

	// public static int val = 0;
	public String[][] convertDataValue(String data) 
	{
		StringTokenizer st = new StringTokenizer(data, "\n");
		Vector v = new Vector();
	//	String[] columnNames = { "Symbol", ,"Price","Time", "% Change", "Open", "High","Low","Volume" };

		while (st.hasMoreTokens()) 
		{
			StringTokenizer st1 = new StringTokenizer(st.nextToken(), ",");
			String[] str = new String[8];
			int i = 0;
			int y = 0;
			while (st1.hasMoreTokens()) 
			{
				String tmp = st1.nextToken(); 
				if(i==0)
				{
					tmp = Utility.replaceString(tmp,"\"", "");
					tmp = Utility.replaceString(tmp,".NS","");
				}
				if (i == 2  )
				{
					;
				}
				else
				{
					str[y] = tmp;
	//				System.out.println(str[y]);
					y++;
				}
				i++;
			}
			if(str[4] != null && !str[4].equalsIgnoreCase("N/A"))
				v.addElement(str);
		}
		String[][] retStr = new String[v.size()][8];

		int y = 0;
		for (int x = 0; x < v.size(); x++) {
			retStr[x] = (String[]) v.elementAt(x);

		}
		runAlerySystem(retStr);
		return retStr;
	}
	public void runAlerySystem(String[][] retStr)
	{
		addToGlobalIntradayHashMap(retStr);
		calculateAlertIndicatorList();
		applyAlert();
		
	}
	public void addToGlobalIntradayHashMap(String[][] data)
	{//data.length
		for(int i=0;i<data.length;i++)
		{
			String[] dataStr = (String [])data[i];
			String symbol = dataStr[0];
			if(globalIntradayData.get(symbol) == null)
			{
				Vector v = new Vector();
				HashMap hs = createHash(data[i]);
				v.addElement(hs);
				globalIntradayData.put(symbol, v);
			}
			else
			{
				Vector v = (Vector)globalIntradayData.get(symbol);
				
				HashMap hs = (HashMap)v.elementAt(v.size()-1);
				String s1 = (String)hs.get(MainGraphComponent.Date);
				String tmp = dataStr[2].replace('"',' ').trim();
				tmp = Utility.replaceString(tmp, "am", ":00");
				if(tmp.equalsIgnoreCase(s1))
				{
					try
					{
						float high = Float.parseFloat((String)hs.get(MainGraphComponent.High));
						float low =  Float.parseFloat((String)hs.get(MainGraphComponent.Low));
						float close = Float.parseFloat((String)hs.get(MainGraphComponent.Close));
						float current = Float.parseFloat((String)dataStr[1]);
						if(current > high)
						{
							hs.put(MainGraphComponent.High, current+"");
						}
						if(current < low)
						{
							hs.put(MainGraphComponent.Low, current+"");
						}
						hs.put(MainGraphComponent.Close, current+"");
						
					}
					catch(Exception e)
					{
						System.out.println("in excp"+e);
					}
				}
				else
				{
					HashMap hs2 = createHash(dataStr);
					v.addElement(hs2);
				}
			}
			System.out.println("mahesh=="+globalIntradayData);
		}
	}
	String strategyQuery = "CLOSE[0] > HIGH[0] ";
	Vector intradayTable = new Vector();
	Vector alertTable = new Vector();
	
	public void calculateAlertIndicatorList()
	{
		String query1 = "HIGH[0] > 517 ";
//		String query2 = "LOW[-2] > 520 ";
//		String query3 = "RSI[14;0] > 20 ";
		
		alertTable.add(query1);
//		alertTable.add(query2);
//		alertTable.add(query3);
		
		StringBuffer sb = new StringBuffer();
		sb.append(query1);
		for(int i=0;i<AlertScreen.activeListVector.size();i++)
		{
			String s = (String)AlertScreen.activeListVector.get(i);
			HashMap hs = (HashMap)AlertScreen.alertListHashMap.get(s);
			s = (String)hs.get(AlertScreen.Formula);
			
			sb.append("(" + s + ") " );

		}
		System.out.println(sb);
		BuySellStrategy buysell = new BuySellStrategy();
		intradayTable = new Vector();
		for(Iterator it = globalIntradayData.keySet().iterator();it.hasNext();)
		{

			String key = (String)it.next();
			Vector v = (Vector)globalIntradayData.get(key);
			HashMap hs = buysell.getHashMapForIndicatorsForIntraday(key,v, sb.toString());
			intradayTable.add(hs);
		}
//		System.out.println("intradayTable="+intradayTable);
	}
	public static String QUERY = "select symbol from table where ";
	public void applyAlert()
	{
		StringBuffer alertResult = new StringBuffer();
		System.out.println("intradaytable=="+intradayTable);	
		for(int j=0;j<AlertScreen.activeListVector.size();j++)
		{	
			try
			{
				String name = (String)AlertScreen.activeListVector.get(j);
				HashMap hs = (HashMap)AlertScreen.alertListHashMap.get(name);
				String s = (String)hs.get(AlertScreen.Formula);

				
				String query = QUERY + s;
				DataBaseInMemory d = new DataBaseInMemory();
				Vector v = d.executeQuery(query, intradayTable);
				if(v != null && v.size() > 0)
				{	
					StringBuffer tmp = new StringBuffer();
					for(int i=0;i<v.size();i++)
					{
						HashMap hs1 = (HashMap)v.get(i);
						tmp.append((String)hs1.get("symbol") + "  ");
					}
					alertResult.append("Alert " + s + " hited for "+ tmp +"\n");
					MessageDiaglog msg = new MessageDiaglog(alertResult.toString());
					AlertScreen.activeListVector.remove(name);
					AlertScreen.inactiveListVector.add(name);
					
				}
				//					System.out.println(query + "    "  +   v);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		System.out.println(alertResult);
	}
	public HashMap createHash(String [] starData)
	{
		HashMap hs = new HashMap();
//		System.out.println(starData[1] + "    " + starData[2] + "     " + starData[7]);
		String tmp = starData[2].replace('"',' ').trim();
		tmp = Utility.replaceString(tmp, "am", ":00");
//		System.out.println("tmp===="+tmp);
		hs.put(MainGraphComponent.Open, starData[1].trim());
		hs.put(MainGraphComponent.Date, tmp.trim());
		hs.put(MainGraphComponent.High, starData[1].trim());
		hs.put(MainGraphComponent.Low, starData[1].trim());
		hs.put(MainGraphComponent.Close, starData[1].trim());
		hs.put(MainGraphComponent.Volume, starData[7].trim());
		
		
		
		return hs;
	}
	public void getAndAddYahoodata(String symbol) 
	{
		Vector dataVector=null;
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
			dataVector =  (Vector)globalIntradayData.get(symbol);
			HashMap lastEle = null;
			if(dataVector != null)
			{
				lastEle = (HashMap)dataVector.lastElement();
			}
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
		if(dataVector != null)
		{
			dataVector.add(hs);
			
		}
		else
		{
			dataVector = new Vector();
			dataVector.add(hs);
			globalIntradayData.put(symbol, hs);
		}
		
			
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
			dateStr = (String)hs.get(MainGraphComponent.Date) ;
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
		String myoldtime= (String)((HashMap)inputdata1.get(oldPosition)).get(MainGraphComponent.Date);
		String mynewtime= (String)((HashMap)inputdata1.get(inputdata1.size()-1)).get(MainGraphComponent.Date);
		if(isTimeDiferenceGreater(1, oldTime,newTime))
		{
			HashMap hstemp  = getHashMapForResStatic(inputdata1, oldPosition,inputdata1.size()-1);
			retVector.add(hstemp);
			
		}
		
		
		return retVector;
	}
	public static HashMap getHashMapForResStatic(Vector inputdata1, int startPos,int endPosition)
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
		int countvol=0;
		int bidprccount=0;
		int offerprccount=0;

		float bidpricetot = 0;
		float bidqtytot = 0;
		float offerpricetot = 0;
		float offerqtytot = 0;

		for (int i = startPos+1; i <= endPosition; i++)
		{
			hs = (HashMap) inputdata1.elementAt(i);
			float tmphigh = Float.parseFloat((String) hs.get(MainGraphComponent.High));
			float tmplow = Float.parseFloat((String) hs.get(MainGraphComponent.Low));
			long tmpvol = Long.parseLong((String) hs.get(MainGraphComponent.Volume));


			float bidprice = close;
			float bidqty = 0;
			float offerprice = close;
			float offerqty = 0;

			try
			{
				bidprice = Float.parseFloat((String)hs.get(MainGraphComponent.BidPrice));
				bidqty = Float.parseFloat((String)hs.get(MainGraphComponent.BidQty));
				offerprice = Float.parseFloat((String)hs.get(MainGraphComponent.OfferPrice));
				offerqty = Float.parseFloat((String)hs.get(MainGraphComponent.OfferQty));
				
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
				bidprice = Float.parseFloat((String)hs.get(MainGraphComponent.BidPrice));
				bidqty = Float.parseFloat((String)hs.get(MainGraphComponent.BidQty));
				offerprice = Float.parseFloat((String)hs.get(MainGraphComponent.OfferPrice));
				offerqty = Float.parseFloat((String)hs.get(MainGraphComponent.OfferQty));
			
				
			}
			catch(Exception e )
			{
			}
			outHash.put(MainGraphComponent.BidPrice, bidprice+"");
			outHash.put(MainGraphComponent.OfferPrice, offerprice+"");
			outHash.put(MainGraphComponent.OfferQty, offerqty+"");
			outHash.put(MainGraphComponent.BidQty, bidqty+"");
				
		}
		else
		{
			outHash.put(MainGraphComponent.BidPrice, bidpricetot+"");
			outHash.put(MainGraphComponent.OfferPrice, offerpricetot+"");
			outHash.put(MainGraphComponent.OfferQty, offerqtytot+"");
			outHash.put(MainGraphComponent.BidQty, bidqtytot+"");
			
		}
		outHash.put(MainGraphComponent.High, high + "");
		outHash.put(MainGraphComponent.Low, low + "");
		outHash.put(MainGraphComponent.Volume, volume + "");
		
		
		
		return outHash;
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

	    if(difference > requireDiff)
	    	return true;
	    else
	    	return false;	


	}

	public static void main(String[] args) {
		// String[] temp = { "+<l c> ALLCARGOEQ</l> <b>815.55 (0.86 %)</b>" };
		// GetLiveData livedata = new GetLiveData(temp);
	}
}

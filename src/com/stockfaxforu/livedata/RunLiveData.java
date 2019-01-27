package com.stockfaxforu.livedata;

import java.io.FileInputStream;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import com.stockfaxforu.component.MainGraphComponent;
import com.stockfaxforu.formulabuilder.MessageDiaglog;
import com.stockfaxforu.frontend.AlertScreen;
import com.stockfaxforu.livemarket.YahooMail;
import com.stockfaxforu.strategy.BuySellStrategy;
import com.stockfaxforu.util.GetDataFromIDBI;
import com.stockfaxforu.util.IndexUtility;
import com.stockfaxforu.util.StockConstants;
import com.stockfaxforu.util.Utility;

public class RunLiveData implements Runnable
{
	
	Properties p = new Properties();

	Vector oldDataValue = null;
	Vector dataValue = null;

//	String[][] oldDataValue = null;
//	String[][] dataValue = null;

	String dataStr = "";
	public static HashMap yahooMappingHash = new HashMap();
	
	public static void main(String[] args)
	{
		RunLiveData r = new RunLiveData(null);
		r.getLiveData();
		try
		{
	//		System.out.println(r.loadFileForIntradayIndiaBull("TCS.NS", "TCS"));
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
	//		e.printStackTrace();
		}
		
	}
	ShowLiveMarket shoelive=null;
	public RunLiveData(ShowLiveMarket shoelive) 
	{
		this.shoelive = shoelive;
	}
	Vector indicatorVector=null;
	
//	@Override
	public boolean runthread=true;
	public void run()
	{
		
		try
		{
			p.load(new FileInputStream(StockConstants.INSTALL_DIR +"/other/mapping.properties"));
			
//			p.put("AAPL","AAPL");
//			p.put("AXP","AXP");
//			System.out.println("mahesh");
			
		}
		catch(Exception e)
		{
			
		}
		while(runthread)
		{
			long l1 = System.currentTimeMillis();
			dataStr = getLiveDataFromIDBI();
//			applyAlert();
			showData();
			long l2 = System.currentTimeMillis();
			
			System.out.println((l2-l1)/1000);
			if(!selectcat.equalsIgnoreCase(shoelive.combo.getSelectedItem().toString()))
			{
				try
				{
					shoelive.scrollPane.remove(shoelive.table);
					shoelive.jContentPane.remove(shoelive.scrollPane);
						
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
	
				shoelive.globalIntradayData = new HashMap();
				shoelive.table=null;
				shoelive.scrollPane=null;
				dataStr=null;
				oldDataValue=null;
				dataValue=null;
				selectcat = shoelive.combo.getSelectedItem().toString();
			}
	//		System.out.println(globalIntradayData);
			
		}
		// TODO Auto-generated method stub
		
	}
	Vector indVec=null;
	private void applyAlert()
	{
		for (int i=0;i< AlertScreen.activeListVector.size();i++)
		{
			 String s= (String)this.shoelive.alertscreen.activeListVector.get(i);
			 HashMap hs = (HashMap)this.shoelive.alertscreen.alertListHashMap.get(s);
			BuySellStrategy buysell = new BuySellStrategy();
			String query="";
			if ( hs.get(AlertScreen.StockList) == null || hs.get(AlertScreen.StockList).toString().trim().equalsIgnoreCase(""))
			{
				query = "SELECT SYMBOL FROM TABLE where " + hs.get(AlertScreen.Formula);
			}
			else
			{
				StringBuffer temp =new StringBuffer();
				temp.append(" ( ");
				StringTokenizer stmp = new StringTokenizer((String)hs.get(AlertScreen.StockList));
				while(stmp.hasMoreTokens())
				{
					temp.append("Symbol like " + stmp.nextToken() );
					if (stmp.hasMoreTokens())
					{
						temp.append(" or " );
							
					}
				}
				temp.append(" ) ");
				query = "SELECT SYMBOL FROM TABLE where " + hs.get(AlertScreen.Formula) + " and " + temp;
			//	System.out.println(query);
					
			}
			Vector result = buysell.getAllStocksQueryIntraday(shoelive.globalIntradayData,query);
			if(result.size() > 0)
			{
				StringBuffer sb = new StringBuffer(); 
				for(int j=0;j<result.size();j++)
				{
					sb.append(((HashMap)result.get(j)).get(MainGraphComponent.Symbol) + "," );
				}
				MessageDiaglog m = new MessageDiaglog(hs.get(AlertScreen.Name) + " hited for following stocks " + sb);
				if (hs.get(AlertScreen.Type).equals(AlertScreen.HitAndRemove))
				{
					
					String formula = (String)hs.get(AlertScreen.Name);
					this.shoelive.alertscreen.inactiveListVector.add(formula);
					this.shoelive.alertscreen.inactiveList.setListData(this.shoelive.alertscreen.inactiveListVector);					 

					this.shoelive.alertscreen.activeListVector.remove(formula);
					this.shoelive.alertscreen.activeList.setListData(this.shoelive.alertscreen.activeListVector);					 


				}
			}
			
		}
		BuySellStrategy buysell = new BuySellStrategy();
		indVec = buysell.getAllStocksQueryIntraday(shoelive.globalIntradayData,"SELECT SYMBOL, close FROM TABLE ");
	//	System.out.println(indVec);
	}

	CustomTableCellRenderer renderer = null;
	String selectcat="cnx500";
	public void showData()
	{
	
		try
			{
				if (dataStr == null) 
				{
				} 
				else 
				{
					dataValue = convertDataValueInVectorIDBI(dataStr);
				}
				if (oldDataValue == null) 
				{
					oldDataValue = dataValue;
				}
				
		//		String[][]  dataValue1 = new String[dataValue.length][dataValue[0].length + indVec.size()];
				dataValue = addColumn(dataValue);
				if ((shoelive.scrollPane == null && shoelive.table == null) ) 
				{
					
					shoelive.table = new JTable(dataValue, shoelive.columnNames);
					
					 renderer = new CustomTableCellRenderer(oldDataValue, dataValue);
					shoelive.table.setDefaultRenderer(Object.class, renderer);
					// Add the table to a scrolling pane
					shoelive.scrollPane = JTable
							.createScrollPaneForTable(shoelive.table);
					shoelive.scrollPane.setBounds(0, 25,	shoelive.x1 - 10, shoelive.y1);
					shoelive.add(shoelive.scrollPane, null);
				
				}
				else
				{
					renderer.setDataValues(oldDataValue, dataValue);
					for (int i = 0; i < dataValue.size(); i++) 
					{
						Vector x = (Vector)dataValue.get(i);
						for (int j = 0; j < x.size(); j++) 
						{
							String temp = x.get(j)+"";
							shoelive.table.setValueAt(temp,i, j);
						}
					}
					
				}
			/*	for (int i = 0; i < dataValue.size(); i++) 
				{
					
					float dataf=0,datao=0;
					try
					{
						dataf = Float.parseFloat((String)((Vector)dataValue.get(i)).get(1));
						datao = Float.parseFloat((String)((Vector)oldDataValue.get(i)).get(1));
					}
					catch(Exception e)
					{
						
					}
					if (dataf >= datao) 
					{
						TableCellRenderer cell = shoelive.table.getCellRenderer(i, 1);
						cell.getTableCellRendererComponent(shoelive.table,	dataf + "", true, true, i, 1);
					}
				}
				*/
				oldDataValue = dataValue;
		
//				shoelive.repaint();
		} 
		catch (Exception e1) 
		{
			
			// TODO Auto-generated catch block
	//		System.out.println("i am in exception");
			e1.printStackTrace();
		}
		
	}
	private Vector addColumn(Vector dataValue2)
	{
		HashMap symBolReturn = new HashMap();
		String[] newColumns={};

		if (indVec==null || indVec.size()==0)
			return dataValue2;
		HashMap hs = (HashMap)indVec.get(0);
		newColumns = new String[hs.size() - 1];
		Vector v = new Vector();
		String key="";
		for(Iterator it = hs.keySet().iterator();it.hasNext();)
		{
			String s = it.next().toString();
			if(s.equalsIgnoreCase("Symbol"))
			{
				continue;
			}
			v.add(s);
		}
	//	System.out.println(this.shoelive.columnNames);
		this.shoelive.columnNames = new Vector();
		this.shoelive.columnNames.addAll(this.shoelive.unchangedcolumnNames);
		
		this.shoelive.columnNames.addAll(v);
	//	System.out.println("newcolumn="+newColumns);
		
		for(int i=0;i<indVec.size();i++)
		{
			HashMap hstmp = (HashMap)indVec.get(i);
			symBolReturn.put(hstmp.get(MainGraphComponent.Symbol), hstmp);
		}
		for(int i=0;i<dataValue2.size();i++)
		{
			Vector  tmpV = (Vector)dataValue2.get(i);
			HashMap mhs = (HashMap)symBolReturn.get(tmpV.get(0));
			if(mhs==null)
			{
				mhs = (HashMap)symBolReturn.get(tmpV.get(0) + ".NS");
			}
			if(mhs==null)
			{
				for(int k=0;k<v.size();k++)
				{
					tmpV.add("");
				}
			}
			else
			{
				for(int k=0;k<v.size();k++)
				{
					String s = (String)mhs.get(v.get(k));
					tmpV.add(s);
				}
				
			}
		}
		
		// TODO Auto-generated method stub
		return dataValue2;
	}

	private void updateTable()
	{
		// TODO Auto-generated method stub
		
	}
	private void setResolution()
	{
		// TODO Auto-generated method stub
		
	}
	public HashMap anotherMap=new HashMap();
	
	
	
	
	private String getLiveDataFromIDBI()
	{
		
		int i=0;
		String symbol="";
		String symbolName="";
		StringBuffer sb =new StringBuffer();
		StringBuffer symbols =new StringBuffer();

//		Vector v= IndexUtility.getIndexStockVector("nifty");
		Vector v= new Vector();
		v.add("TCS");
		v.add("HEG");
		v.add("ALOKTEXT");
		v.add("UNITEC");
		v.add("ACC");

		
		
		//		v.insertElementAt("^NSEI", 0);
//		v.insertElementAt("^NSEBANK", 0);
//		v.insertElementAt("^NSMIDCP", 0);

//		anotherMap.put("^NSEI", "NIFTY");
//		anotherMap.put("^NSEBANK", "CNXBANK");
//		anotherMap.put("^NSMIDCP", "CNXMIDCAP");
		StringBuilder finalData = new StringBuilder();
		for(Enumeration e=v.elements();e.hasMoreElements();)
		{
	
			
			//	i++;
			//	if ( i > 10)
			//		break;

				symbol = (String)e.nextElement();
		
				String sss = symbol;
		
				sss = URLEncoder.encode(sss);
					
				String s = GetDataFromIDBI.getRealTimeInString(sss);
				finalData.append(symbol+","+s+"\n");
		}
		
		return finalData.toString().trim();
		
		
	}
	
	private String getLiveData()
	{
		int i=0;
		String symbol="";
		String symbolName="";
		StringBuffer sb =new StringBuffer();
		StringBuffer symbols =new StringBuffer();

		Vector v= IndexUtility.getIndexStockVector(selectcat);
	//	Vector v= new Vector();
		//v.add("TCS");
		v.insertElementAt("^NSEI", 0);
		v.insertElementAt("^NSEBANK", 0);
		v.insertElementAt("^NSMIDCP", 0);

		anotherMap.put("^NSEI", "NIFTY");
		anotherMap.put("^NSEBANK", "CNXBANK");
		anotherMap.put("^NSMIDCP", "CNXMIDCAP");
		
		for(Enumeration e=v.elements();e.hasMoreElements();)
		{
	
			
			//	i++;
			//	if ( i > 10)
			//		break;

				symbol = (String)e.nextElement();
		
				String sss = symbol;
				if(sss.length() > 9)
				{
					anotherMap.put(sss, sss = symbol.substring(0,9));
					sss = symbol.substring(0,9);
		//			System.out.println("sym="+symbol);
	
				}
		
				if ( sss.startsWith("^"))
				{
					if(yahooMappingHash.get(sss)==null)
						yahooMappingHash.put(sss, symbol);
		
					sss = URLEncoder.encode(sss);
					
				}
				else
				{
					sss = URLEncoder.encode(sss);
					if(yahooMappingHash.get(sss)==null)
						yahooMappingHash.put(sss, symbol);
					
				}  
				symbols.append(sss+",");
		}  
		
		String s = getYahooDataAll(symbols.toString());
				
		if(s != null )
					sb.append(s+"\n");
//				setResolutionIntraday(symbolName,1);
				
		return s;
		// TODO Auto-generated method stub
		
	}
	public  void setResolutionIntraday(String symbol ,long requiredTimeDiff)
	{
		int minute = 1;
		HashMap hs = null;
		String dateStr = null ;
		String oldTime = null;
		String newTime = null;
		Vector retVector = new Vector();

		int oldPosition=0;
		Vector inputdata1 =  (Vector)shoelive.globalIntradayData.get(symbol);
		if(inputdata1==null)
			return ;
		
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
		if(isTimeDiferenceGreater(1, oldTime,newTime) || inputdata1.size() <= 1)
		{
			HashMap hstemp  = getHashMapForResStatic(inputdata1, oldPosition,inputdata1.size()-1);
			retVector.add(hstemp);
			
		}
		shoelive.globalIntradayData.put(symbol,retVector);

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
	
	public static int SIZE=100;
	public String getYahooDataAll(String allsymbol)
	{
		int count =0;
		StringTokenizer st = new StringTokenizer(allsymbol,",");
		StringBuffer sb = new StringBuffer();
		StringBuffer output = new StringBuffer();
		
		while(st.hasMoreElements())
		{
			sb.append(st.nextToken()+",");
			count++;
			if(count > SIZE)
			{
				output.append(getYahooData(sb.toString()));
				sb = new StringBuffer();
				count=0;
			}
		}
		output.append(getYahooData(sb.toString()));
		
		return output.toString();
	}
	public String getYahooData(String allsymbol)
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
				return null;	
			}
		}	
		String data = "";
		try
		{
			
			data = StockConstants.YAHOO.getDataFromYahoo(allsymbol);
			
		}
		catch(Exception e)
		{
			
		}
		StringTokenizer st = new StringTokenizer(data,"\n");
		String symbol="";
		while(st.hasMoreTokens())
		{
			try
			{
				String line = st.nextToken();
				StringTokenizer st1 = new StringTokenizer(line,",");
				symbol = st1.nextToken();
				getAndAddYahoodata(symbol, line);
				
			}
			catch(Exception e)
			{
				System.out.println("exp occured for " + symbol);
			}
		}
		return data;
	}
	
	public Vector loadFileForIntradayIndiaBull(String symbolName,String symbol) throws Exception
	{
		symbol = MainGraphComponent.getIndiaBullsCode(symbol);
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
			return new Vector();
		Vector inputdata = new Vector();
		int xx = 0;
		int i = 0;
		StringTokenizer st = new StringTokenizer(data,"\n");
		long oldvol=0;
		long newvol=0;
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
				newvol = l - oldvol + 1;
				oldvol = l;
				String s = date +"," + price + "," + volume + "\n";
				// ln(s);
				hs.put(MainGraphComponent.Date, date);
				//					// ln(hs.get("Date"));
				hs.put(MainGraphComponent.Open, price);
				hs.put(MainGraphComponent.High, price);
				hs.put(MainGraphComponent.Low, price);
				hs.put(MainGraphComponent.Close, price);
				hs.put(MainGraphComponent.Volume, newvol+"");
				hs.put(MainGraphComponent.TotalVolume, l+"");	
				inputdata.addElement(hs);
				oldvol = l;
			}
			catch (Exception e1)
			{
		//		e1.printStackTrace();
			}
			i++;
		}
		return inputdata;
	}
	

	public String getAndAddYahoodata(String symbol,String data) throws Exception
	{
		data = Utility.replaceString(data, "\"", "");
		symbol = Utility.replaceString(symbol, "\"", "");

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
		String change =st.nextToken();
		String open =st.nextToken();
		String high=st.nextToken();
		String low=st.nextToken();


		String volume = st.nextToken().trim();


		HashMap hs = new HashMap();
		hs.put(MainGraphComponent.Date,time.trim());
		hs.put(MainGraphComponent.Open,current.trim());
		hs.put(MainGraphComponent.High,current.trim());
		hs.put(MainGraphComponent.Low,current.trim());
		hs.put(MainGraphComponent.Close,current.trim());
		hs.put(MainGraphComponent.TotalVolume,volume.trim());
		hs.put(MainGraphComponent.Volume,"0");

		hs.put(MainGraphComponent.DayHigh, high);
		hs.put(MainGraphComponent.DayLow, low);
		hs.put(MainGraphComponent.DayOpen, open);
		hs.put(MainGraphComponent.DayChange, change);

		
		String symbolnse = (String)yahooMappingHash.get(symbol);
		String t = (String)anotherMap.get(symbolnse);
		if(t!=null)
			symbolnse=t;
		//	System.out.println(yahooMappingHash);
		Vector dataVector = (Vector)shoelive.globalIntradayData.get(symbolnse);
		
		if(dataVector == null)
		{
			dataVector = new Vector();
			dataVector.add(hs);
			shoelive.globalIntradayData.put(symbolnse,dataVector);
			
		}
		else
		{
			HashMap myhs = (HashMap)dataVector.lastElement();
			String lastdate =(String)myhs.get(MainGraphComponent.Date);
			String currdate =(String)hs.get(MainGraphComponent.Date);
			
//if time is less then do't add 			
			long currtotvol = Long.parseLong(hs.get(MainGraphComponent.TotalVolume)+"");
			long lasttotvol = Long.parseLong(myhs.get(MainGraphComponent.TotalVolume)+"");
			
			if (currtotvol <= lasttotvol && ( lasttotvol != 0 ) )
			{
		//		System.out.println("clast is greater last is"+currtotvol +" and curr is "+lasttotvol);
				throw new Exception();
			}
			
			
			if(lastdate.equals((String)hs.get(MainGraphComponent.Date)))
			{
				myhs.put(MainGraphComponent.Close, hs.get(MainGraphComponent.Close));
				float oldHigh = Float.parseFloat((String)myhs.get(MainGraphComponent.High));
				float newHigh = Float.parseFloat((String)hs.get(MainGraphComponent.High));

				float oldLow = Float.parseFloat((String)myhs.get(MainGraphComponent.Low));
				float newLow = Float.parseFloat((String)hs.get(MainGraphComponent.Low));

				if(newLow < oldLow)
				{
					myhs.put(MainGraphComponent.Low, hs.get(MainGraphComponent.Low));
						
				}
				if(newHigh > oldHigh)
				{
					myhs.put(MainGraphComponent.High, hs.get(MainGraphComponent.High));
					
				}
				long myvolume = Long.parseLong((String)myhs.get(MainGraphComponent.Volume));
				long diff = Long.parseLong((String)hs.get(MainGraphComponent.TotalVolume)) - Long.parseLong((String)myhs.get(MainGraphComponent.TotalVolume));
				myvolume = myvolume + diff;
				myhs.put(MainGraphComponent.Volume, myvolume+"");
				myhs.put(MainGraphComponent.TotalVolume, hs.get(MainGraphComponent.TotalVolume));
				myhs.put(MainGraphComponent.DayHigh, high);
				myhs.put(MainGraphComponent.DayLow, low);
				myhs.put(MainGraphComponent.DayOpen, open);
				myhs.put(MainGraphComponent.DayChange, change);
				
					
				
			}
			else
			{
				dataVector.add(hs);
				
			}
			shoelive.globalIntradayData.put(symbolnse,dataVector);
		}
		return data;
			
	}

	// public static int val = 0;

	public Vector convertDataValueVector(String data) 
	{
		StringTokenizer st = new StringTokenizer(data, "\n");
		Vector v = new Vector();
	//	String[] columnNames = { "Symbol", ,"Price","Time", "% Change", "Open", "High","Low","Volume" };

		while (st.hasMoreTokens()) 
		{
			StringTokenizer st1 = new StringTokenizer(st.nextToken(), ",");
			Vector str = new Vector();
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
					str.add(tmp);
	//				System.out.println(str[y]);
					y++;
				}
				i++;
			}
			if(str.get(4) != null && !str.get(4).toString().equalsIgnoreCase("N/A"))
				v.add(str);
		}
		return v;
	}

	
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
			String[] retStr1 = (String[]) v.elementAt(x);
			retStr[x][0] = retStr1[0];
			retStr[x][1] = retStr1[2];
			retStr[x][2] = retStr1[1];
			retStr[x][3] = retStr1[3];
			retStr[x][4] = retStr1[4];
			retStr[x][5] = retStr1[5];
			retStr[x][6] = retStr1[6];
			retStr[x][7] = retStr1[7];
				
		}
		
		return retStr;
	}


	
	
	
	public Vector convertDataValueInVectorIDBI(String data) 
	{
		StringTokenizer st = new StringTokenizer(data, "\n");
		Vector v = new Vector();
	//	String[] columnNames = { "Symbol", ,"Price","Time", "% Change", "Open", "High","Low","Volume" };

		while (st.hasMoreTokens()) 
		{
			StringTokenizer st1 = new StringTokenizer(st.nextToken(), ",");
			String[] str = new String[4];
			int i = 0;
			int y = 0;
			while (st1.hasMoreTokens()) 
			{
				String tmp = st1.nextToken(); 
				str[y] = tmp;
				y++;
			}
			v.addElement(str);
			i++;
		}
		String[][] retStr = new String[v.size()][4];

		int y = 0;
		Vector retVec = new Vector();
		for (int x = 0; x < v.size(); x++) 
		{
			String[] retStr1 = (String[]) v.elementAt(x);
			Vector vtmp = new Vector();
			vtmp.add(retStr1[0]);
			vtmp.add(retStr1[1]);
			vtmp.add(retStr1[2]);
			vtmp.add(retStr1[3]);
			retVec.add(vtmp);
		}
		
		return retVec;
	}


	
	
	
	public Vector convertDataValueInVector(String data) 
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
		Vector retVec = new Vector();
		for (int x = 0; x < v.size(); x++) 
		{
			String[] retStr1 = (String[]) v.elementAt(x);
			Vector vtmp = new Vector();
			vtmp.add(retStr1[0]);
			vtmp.add(retStr1[2]);
			vtmp.add(retStr1[1]);
			vtmp.add(retStr1[3]);
			vtmp.add(retStr1[4]);
			vtmp.add(retStr1[5]);
			vtmp.add(retStr1[6]);
			vtmp.add(retStr1[7]);
			
			retVec.add(vtmp);
		}
		
		return retVec;
	}

	
	
	
}

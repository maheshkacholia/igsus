/*
 * Created on Feb 13, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.finance;

import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.Vector;

import com.stockfaxforu.util.StockConstants;

/**
 * @author sdeopura
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ShowFinance
{
	private static HashMap savedFinancial = new HashMap();
	
	HashMap hs = new HashMap();

	private  void  loadFinancialData(String symbol) throws Exception
	{
		RandomAccessFile in = null;
//		String checkStr="quarter:net sales:total income:interest:netprofit:dividend:basic eps:diluted eps";
		StringBuffer retStr= new StringBuffer(); 
		try
		{
			in = new RandomAccessFile(StockConstants.INSTALL_DIR + "/result/" + symbol +".txt","r");
			String line = "";
			while((line=in.readLine()) != null)
			{
					StringTokenizer st = new StringTokenizer(line,";");
					String value = st.nextToken().toLowerCase();
					StringBuffer temp = new StringBuffer();
					while(st.hasMoreTokens())
					{
						temp.append(st.nextToken()+";");
					}
					hs.put(value, temp.toString());
			}	
//			in.close();
//			savedFinancial.put(symbol, retStr.toString());
		}
		catch(Exception e)
		{
			throw e;
		}		
		
	}
	public static String[] search = {"total income","interest","profit (+)/loss (-) before depreciation and taxes","net profit (+)/loss (-) for the period","basic eps after extraordinary items", "diluted epd after extraordinary items"};
	String[] showstr = {"TotalIncome","Interest","EBITDA","NetProfit","BasicEPS","DilutedEPS"};
	String[] showstr1 = {"TOTALINCOME","INTEREST","EBITDA","NETPROFIT","BASICEPS","DILUTEDEPS"};
	
	String[] querystr = {"Quarter","NetSaleQtr","TotalIncomeQtr","InterestQtr","EBITDAQtr","NetProfitQtr","BasicEPSQtr","DilutedEPSQtr"};

	public void loadSearchAndVariable()
	{
		Vector searchStr = new Vector(); Vector eqvar = new Vector();
		searchStr.addElement("Quarter"); 												eqvar.addElement("Qtr");
		searchStr.addElement("net sales/income from operations"); 						eqvar.addElement("NetSales");
		searchStr.addElement("net sales / income from operations"); 					eqvar.addElement("NetSales");
		searchStr.addElement("Total Income"); 											eqvar.addElement("TotalIncome");
//		searchStr.addElement("Depreciation<sup>2</sup>"); 								eqvar.addElement("Depreciation");
//		searchStr.addElement("Depreciation<sup>3</sup>"); 								eqvar.addElement("Depreciation");
//		searchStr.addElement("Depreciation<sup>4</sup>"); 								eqvar.addElement("Depreciation");
		searchStr.addElement("Total Expenditure"); 										eqvar.addElement("TotalExpence");
		searchStr.addElement("Interest"); 												eqvar.addElement("Interest");
		searchStr.addElement("Profit (+)/Loss (-) before Depreciation and Taxes"); 		eqvar.addElement("EBIDAT");
		searchStr.addElement("Gross Profit"); 											eqvar.addElement("EBIDAT");
		searchStr.addElement("Tax Expenses"); 											eqvar.addElement("TaxExpenses");
		searchStr.addElement("Net Profit (+)/Loss (-) for the Period"); 				eqvar.addElement("NetProfit");
		searchStr.addElement("Dividend (%)"); 											eqvar.addElement("Dividend(%)");
		searchStr.addElement("Basic EPS after Extraordinary items"); 					eqvar.addElement("BasicEPS");
	}

	public  HashMap getQuartelyResultsHashMap(String symbol,Vector val)
	{
			HashMap returnHash = new HashMap();
			String notfoundstr = "-;-;-;-;-" ;
			StringBuffer retStrBuf= new StringBuffer();
		
			try
			{
				loadFinancialData(symbol);
			}
			catch (Exception e)
			{
				return new HashMap();
			}
			return null;
	}

	public  String getQuartelyResults(String symbol)
	{
		String notfoundstr = "-;- ;- ;- ;-" + "\n";
		StringBuffer retStrBuf= new StringBuffer();
		
		try
		{
			loadFinancialData(symbol);
		}
		catch (Exception e)
		{
			return "";
		}
		
		String quarter = (String)hs.get("quarter");
		retStrBuf.append("Quarter;"+quarter+"\n");
		
		String[] temp1 = {"net sales/income from operations","net sales / income from operations"};
		String netsale="";
		for(int i=0;i<temp1.length;i++)
		{
			netsale = (String)hs.get(temp1[i]);
			if(netsale != null)
			{
				retStrBuf.append("NetSales;" + netsale + "\n");
				break;	
			}
		}
		if (netsale==null)
		{
			retStrBuf.append("NetSales;-;- ;- ;- ;-" + "\n" );
		}


		for(int i=0;i<search.length;i++)
		{
			String tmp = (String)hs.get(search[i]);
			if(tmp != null)
			{
				retStrBuf.append(showstr[i] + ";" + tmp+"\n");
			
			}
		}
		return retStrBuf.toString();
	}
	public  HashMap getQuartelyResultsHash(String symbol)
	{
		HashMap returnHash = new HashMap();
		String notfoundstr = "-;- ;- ;- ;-" + "\n";
		StringBuffer retStrBuf= new StringBuffer();
		
		try
		{
			loadFinancialData(symbol);
		}
		catch (Exception e)
		{
			return returnHash;
		}
		
		String quarter = (String)hs.get("quarter");
		returnHash.put("QUARTER",quarter);
		
		String[] temp1 = {"net sales/income from operations","net sales / income from operations"};
		String netsale="";
		for(int i=0;i<temp1.length;i++)
		{
			netsale = (String)hs.get(temp1[i]);
			if(netsale != null)
			{
				returnHash.put("NETSALES",netsale);
				break;	
			}
		}
		if (netsale==null)
		{
			returnHash.put("NETSALES",notfoundstr);
		}
		for(int i=0;i<search.length;i++)
		{
			String tmp = (String)hs.get(search[i]);
			if(tmp != null)
			{
				returnHash.put(showstr1[i],tmp);
			
			}
		}
		return returnHash;
	}


}

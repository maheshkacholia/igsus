package com.stockfaxforu.reports;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;


import com.stockfaxforu.finance.ShowFinance;
import com.stockfaxforu.frontend.PanelForGraph;
import com.stockfaxforu.util.StockConstants;
import com.stockfaxforu.util.Utility;

public class ReportGenerator
{
	public static HashMap mappingTable = new HashMap();
	public static HashMap mainTableContent = new HashMap();
	
	public static String SYMBOL = "Symbol";
	public static String CLOSE = "Close";
	public static String Change = "Change";
	
	public static String ADVICE = "Advice";
	public static String Color = "Color";
	
	
	public static void main(String[] args)
	{
		generateReports();
//		generateReport("TCS");
	
	}
	public static void loadMapping() throws Exception
	{
		
		String symbols = Utility.getFileContent("/igsentindia/allsymbol.txt");
		StringTokenizer tokenize = new StringTokenizer(symbols,"\n");
		while(tokenize.hasMoreTokens())
		{
			try
			{
				StringTokenizer line = new StringTokenizer(tokenize.nextToken(),"|");
				String symbol = line.nextToken();
				String name = line.nextToken();
				mappingTable.put(symbol, name);
				
			}
			catch(Exception e)
			{
				
			}
		}
	}
	public static void generateReports()
	{
		Properties p = new Properties();
		try
		{
			p.load(new FileInputStream(StockConstants.INSTALL_DIR+"/indexsymbol.txt"));
			String stockList = (String)p.get("nifty");
			StringTokenizer tokenizer = new StringTokenizer(stockList,"|");
			while(tokenizer.hasMoreTokens())
			{
				String symbol = tokenizer.nextToken();
				generateReport(symbol);
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
		}
		
	}
	public static void generateReport(String symbol)
	{
		try
		{
			String htmlFile = Utility.getFileContent("/analysis/style.html");
			
			String techReport = createTechnicalReport(symbol,htmlFile);
			techReport = Utility.replaceString(techReport, "#SYMBOL#", symbol);
			techReport = updateReportWithFinancialData(symbol, techReport);
			String buysell = createBuySellSignal(symbol);
			techReport = Utility.replaceString(techReport, "#BUYSELLSIGNAL#", buysell);
				
			
				
			combineAndCreateFinalReport(symbol);
			createReportInPDFFormat(symbol);
			createGraph(symbol);
			Utility.saveContent("/analysis1/"+symbol+".html", techReport);

		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
	public static String createBuySellSignal(String symbol) throws Exception
	{
		StringBuilder buySellBuilder = new StringBuilder();
		buySellBuilder.append("<table class='gridtable' width=75%>");
		
		StringBuilder buySellBuilder1 = new StringBuilder();
		
		File f = new File("/analysis/buysell");
		File[] formulas = f.listFiles();
		Vector v = StockAnalysis.getSymBolData(symbol);
		
		for(int i=0;i<formulas.length;i++)
		{
			StockAnalysis analysis = new StockAnalysis(symbol);
			try
			{
				buySellBuilder1.append(analysis.applyBuySellFormula(symbol,formulas[i].getName(),v));
			}
			catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		if (buySellBuilder1.toString().trim().equals(""))
		{
//			buySellBuilder.append("<tr><th>Formula Name</th><th>Signal date</th><th>Signal Type</th><th>Signal Accuracy</th><th>Avg Signal Occurance(in days)</th><th>Close Price at Signal</th><th>% Chnage After Signal</tr>");

			buySellBuilder.append("<tr><td><b>No Signal Generated</b></td></tr>");
		}
		else
		{
			buySellBuilder.append("<tr><th>Formula Name</th><th>Signal date</th><th>Signal Type</th><th>Signal Accuracy</th><th>Avg Signal Occurance(in days)</th><th>Close Price at Signal</th><th>% Chnage After Signal</tr>");

			buySellBuilder.append(buySellBuilder1.toString());
		}
		buySellBuilder.append("</table>");
		return buySellBuilder.toString();
	}
	private static void createReportInPDFFormat(String symbol)
	{
		
	}
	private static void combineAndCreateFinalReport(String symbol)
	{
		
	}
	private static String createTechnicalReport(String symbol,String htmlFile) throws Exception
	{
		StockAnalysis analysis = new StockAnalysis(symbol);
		Vector retData = analysis.applyDefaultStrategy(symbol,StockAnalysis.getSymBolData(symbol));
		HashMap lastEle = (HashMap)retData.lastElement();
		htmlFile = replaceVariable(htmlFile, lastEle);
		return htmlFile;
	}
	private static String replaceVariable(String htmlFile,HashMap resultHash)
	{
	//	htmlFile = htmlFile.toUpperCase();
		for(Iterator e = resultHash.keySet().iterator();e.hasNext();)
		{
			String key = (String)e.next();
			htmlFile = Utility.replaceString(htmlFile,"#"+key+"#", resultHash.get(key)+"");
		}
		return htmlFile;
	}
	
	private static String updateReportWithFinancialData(String symbol,String report) throws Exception
	{
		String finReport = createFinancialReport(symbol);
		String htmlFile = Utility.replaceString(report,"#FINANCIALREPORT#", finReport);
		return htmlFile;
	}
	private static String createFinancialReport(String symbol) throws Exception 
	{
		
			StringBuilder finStrBuilder = new StringBuilder();
			finStrBuilder.append("<table class='gridtable' width=75%><tr><th>Financial Analysis</th></tr></table>\n");
			finStrBuilder.append("<table class='gridtable' width=75%>\n");
				
			String s = StockConstants.INSTALL_DIR + "/result/" + symbol +".txt";
			File f = new File(s);
			if(!f.exists())
				return "";
	

			Properties p = new Properties();
			p.load(new FileInputStream(StockConstants.INSTALL_DIR+"/result/"+symbol +".txt"));
			String[] keys = {"QUARTER","NETSALES","TOTALINCOME","DEPRECIATION","TAXEXPENSE","NETPROFIT","BASICEPS"};
			String[] displayName = {"Quarter","Net Sale","Total Income","Depreciation","Tax Expense","Net Profit","EPS"};
	
			
			for(int inc=0;inc < keys.length;inc++)
			{
	
				String mytokenStr = p.getProperty(keys[inc]);
				
				if(mytokenStr==null)
					continue;
				mytokenStr = displayName[inc] +";" + mytokenStr; 
				StringTokenizer line1 = new StringTokenizer(mytokenStr,";");
				finStrBuilder.append("<tr>\n");
				int x =0;
				while(line1.hasMoreTokens())
				{
					
					String line2 = line1.nextToken().trim();
					if(x==0 || inc==0)
					{
						finStrBuilder.append("\t<td><b>"+line2+"</b></td>\n");
						
					}
					else
					{
						finStrBuilder.append("\t<td>"+line2+"</td>\n");
						
					}
					x++;
				}
				finStrBuilder.append("</tr>\n");
			}
			finStrBuilder.append("</table>\n");
			return finStrBuilder.toString();
	}
	private static void createGraph(String symbol)
	{
		/*
		PanelForGraph d = new PanelForGraph(0, 0, 900, 400, symbol);
	//	d.setYear("2012");
		d.setGraphType("Line");
		d.setResolution("1-Day");
		/*
		if(customformula != null && !customformula.equalsIgnoreCase(""))
			d.checkFormulaForStrategy("custom",customformula);
		
		
		if(indname != null && !ManageIndicator.isCustom(indname))
		{
			Indicator selInd = ManageIndicator.getIndicatorObject(indname);
			if(selInd.noofperiods==1)
			{
				selInd.period1 = 14;
			}
			else if(selInd.noofperiods==2)
			{
				selInd.period1 = 2;
				selInd.period2 = 14;

			}
			else if(selInd.noofperiods==3)
			{
				selInd.period1 = 14;
				selInd.period2 = 26;
				selInd.period3 = 9;
			
			}
			d.addIndicator(selInd);
			
		}
		else
		{
			
		}

		
/*		Indicator selInd = ManageIndicator.getIndicatorObject("SMA");
		selInd.period1=14;
		
		d.addIndicator(selInd);
		
		Indicator selInd1 = ManageIndicator.getIndicatorObject("MACD");
		selInd1.period1=12;
		selInd1.period2=26;
		selInd1.period3=9;
		
		
		d.addIndicator(selInd1);
	
		
		BufferedImage image = (BufferedImage)d.macdgraph.saveGraphAsJPEG(null);
		d.macdgraph.writeImage(symbol);
//		JPEGCodec.createJPEGEncoder(out).encode(image);
*/	
	}
	
	
}

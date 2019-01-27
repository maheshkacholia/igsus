/*
 * Created on Mar 17, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.strategy;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;


import com.stockfaxforu.component.MainGraphComponent;
import com.stockfaxforu.getolddataforu.loader.Loader;
import com.stockfaxforu.parser.LexicalAnalyzer;
import com.stockfaxforu.query.DataBaseInMemory;
import com.stockfaxforu.query.Function;
import com.stockfaxforu.scan.util.StockScreenerThreadMaster;
import com.stockfaxforu.util.StockConstants;
import com.stockfaxforu.util.Utility;
/**
 * @author sdeopura
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class BuySellStrategy
{
//	public static String Open = "OPEN";
//	public static String High = "HIGH";
//	public static String Low = "LOW";
//	public static String Close = "CLOSE";
//	public static String AvgPrice = "AvgPrice";
//	public static String Volume = "Volume";
//	public static String Date = "Date";
	public static String SELECTQUERY = "select Date from table where ";
	public static String SELECTSTOCKQUERY = "select symbol from table where ";
	public DataBaseInMemory database = new DataBaseInMemory();
	public String[] getValueForDataForCustmizedInd(String symbol, String custIndQuery)
	{
		custIndQuery = custIndQuery.toUpperCase();
		Vector datavector1=null;
		try
		{
			datavector1 = Loader.getFileContentForScreening(StockConstants.STARTDATE, symbol.toUpperCase(),StockConstants.SCREENDATE,StockConstants.ENDDATE);
		}
		catch (Exception e)
		{
			return null;
			// TODO Auto-generated catch block
		}
		techind = new TechIndicatorLibrary(datavector1);
		techind.symbol = symbol;
		Vector indVec = findAllIndicatorInQuery(custIndQuery);
		for (int i = 0; i < indVec.size(); i++)
		{
			HashMap hs = getIndNameAndPara((String) indVec.elementAt(i));
			String indName = (String) hs.get(INDNAME);
			Vector parameter = (Vector) hs.get(PARAMETER);
			Object val = techind.getResultForBuySell(indName, parameter);
			addValueToVector(indName, (String) indVec.elementAt(i), val, datavector1);
		}
		String[] retstr=null;
		try
		{
			retstr = database.evaluateExpression(datavector1, custIndQuery);
		}
		catch (Exception e1)
		{
			// TODO Auto-generated catch block
	//		e1.printStackTrace();
		}
		
		return retstr;
	}
	public String[] getValueForDataForCustmizedInd(Vector datavector, String custIndQuery)
	{
		custIndQuery = custIndQuery.toUpperCase();
		Vector datavector1 = copyData(datavector);;
		 techind = new TechIndicatorLibrary(datavector1);
			if(techind.symbol==null || techind.symbol.equalsIgnoreCase(""))
				techind.symbol = this.symbol;
		Vector indVec = findAllIndicatorInQuery(custIndQuery);
		for (int i = 0; i < indVec.size(); i++)
		{
			HashMap hs = getIndNameAndPara((String) indVec.elementAt(i));
			String indName = (String) hs.get(INDNAME);
			Vector parameter = (Vector) hs.get(PARAMETER);
			Object val = techind.getResultForBuySell(indName, parameter);
			addValueToVector(indName, (String) indVec.elementAt(i), val, datavector1);
		}
		String[] retstr=null;
		try
		{
			retstr = database.evaluateFormula(datavector1, custIndQuery);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
		//	e.printStackTrace();
		}
//		float[] retfloat = new float[retstr.length];
//		for(int i=0;i<retfloat.length;i++)
//			retfloat[i] = Float.parseFloat(retstr[i]);
		return retstr;
	}
	public Vector getValueForDataForCustmizedIndVector(Vector datavector, String custIndQuery)
	{
		custIndQuery = custIndQuery.toUpperCase();
		Vector datavector1 = copyData(datavector);;
		 techind = new TechIndicatorLibrary(datavector1,this.intradayHash);
			if(techind.symbol==null || techind.symbol.equalsIgnoreCase(""))
				techind.symbol = this.symbol;
		Vector indVec = findAllIndicatorInQuery(custIndQuery);
		for (int i = 0; i < indVec.size(); i++)
		{
			HashMap hs = getIndNameAndPara((String) indVec.elementAt(i));
			String indName = (String) hs.get(INDNAME);
			Vector parameter = (Vector) hs.get(PARAMETER);
			Object val = techind.getResultForBuySell(indName, parameter);
			addValueToVector(indName, (String) indVec.elementAt(i), val, datavector1);
		}
		Vector retvec=null;
		try
		{
			retvec = database.evaluateFormulaMainVector(datavector1, custIndQuery);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
		//	e.printStackTrace();
		}
//		float[] retfloat = new float[retstr.length];
//		for(int i=0;i<retfloat.length;i++)
//			retfloat[i] = Float.parseFloat(retstr[i]);
		return retvec;
	}

	
	
	public HashMap evaluateHash=null;
	public String runScreenerForSingleStockWithdata(Vector datavector, String strategyQuery)
	{
		techind = new TechIndicatorLibrary(datavector);
		if(techind.symbol==null || techind.symbol.equalsIgnoreCase(""))
			techind.symbol = this.symbol;
		strategyQuery = strategyQuery.toUpperCase();
		Vector indVec = findAllIndicatorInQuery(strategyQuery);
		Vector nameParaVector = new Vector();
		for (int i = 0; i < indVec.size(); i++)
		{
			HashMap hs = getIndNameAndPara((String) indVec.elementAt(i));
			nameParaVector.addElement(hs);
		}
		techind.includeHash = lex.includeHash;
	   HashMap datahash = new HashMap();
	   for (int j = 0; j < nameParaVector.size(); j++)
	   {
		   try
		   {
			   String indicatorNameWithPara = (String) indVec.elementAt(j);
			   HashMap hs1 = (HashMap) nameParaVector.elementAt(j);
			   String indName = (String) hs1.get(INDNAME);
			   Vector parameter = (Vector) hs1.get(PARAMETER);
			   String val = techind.getResultForScreener(indName, parameter);
			   datahash.put(indicatorNameWithPara, val + "");
				
		   }
		   catch(Exception e)
		   {
				
		   }
	   }
	   long t2 = System.currentTimeMillis();
	   Vector resultvector=null;
	   String retStr=null;
	   try
	   {
		   retStr = database.evaluateFormulaMain(datahash, strategyQuery);
	   }
	   catch (Exception e)
	   {
		   // TODO Auto-generated catch block
	//	   e.printStackTrace();
	   }
		
	   long t3 = System.currentTimeMillis();
//	   return convertVectorIntoStringStockScreener(resultvector);
	   evaluateHash = datahash;
	   return retStr;
				
	}
	public static String RECORDSTOUSE="@RECORDSTOUSE"; 
	public int getNoOfRecordsToUse(String query)
	{
		try
		{
			int x = query.indexOf(RECORDSTOUSE); 
			if (x== -1)
			{
				return -1;
			}
			String s= query.substring(x);
			StringTokenizer st = new StringTokenizer(s,"=");
			String name = st.nextToken();
			String value = st.nextToken();
			if(name.equals(RECORDSTOUSE))
			{
				x = Integer.parseInt(value);
				return x;
			}
			return -1;
			
			
		}
		catch(Exception e)
		{
			return -1;
		}
		
		
	}
	public Vector getAllStocksQuery(Vector datavector1, String strategyQuery)
	{
		strategyQuery = strategyQuery.toUpperCase();
//		String expandedQuery = expandFormula(strategyQuery);
		techind = new TechIndicatorLibrary(datavector1);
		Vector indVec = findAllIndicatorInQuery(strategyQuery);
		
		Vector nameParaVector = new Vector();
		
		for (int i = 0; i < indVec.size(); i++)
		{
			HashMap hs = getIndNameAndPara((String) indVec.elementAt(i));
			nameParaVector.addElement(hs);
		}
		long t1 = System.currentTimeMillis();
		for (int i = 0; i < datavector1.size(); i++)
		{
			HashMap datahash = (HashMap) datavector1.elementAt(i);
			String symbol = (String) datahash.get(MainGraphComponent.Symbol);
			Vector v=null;
			
			try
			{
				v = Loader.getFileContentForScreening(StockConstants.STARTDATE, symbol.toUpperCase(),StockConstants.SCREENDATE,StockConstants.ENDDATE);
				int t = getNoOfRecordsToUse(strategyQuery.toUpperCase());
				if(t != -1 && t < v.size())
				{	List x = v.subList(v.size()-t,v.size());
					Vector v1 = new Vector();
				
					for(int ii=0;ii<x.size();ii++)
					{
						v1.add(x.get(ii));
					}
					v = v1;
				}	
				
//				v = subList(v, v.size() - 50,v.size());
				
				 techind = new TechIndicatorLibrary(v);
				techind.symbol = symbol;
				techind.includeHash = lex.includeHash;
				
				for (int j = 0; j < nameParaVector.size(); j++)
				{
					try
					{
						String indicatorNameWithPara = (String) indVec.elementAt(j);
						HashMap hs1 = (HashMap) nameParaVector.elementAt(j);
						String indName = (String) hs1.get(INDNAME);
						Vector parameter = (Vector) hs1.get(PARAMETER);
						String val = techind.getResultForScreener(indName, parameter);
						
						if(val.equalsIgnoreCase("-10000"))
							throw new Exception();	

						datahash.put(indicatorNameWithPara, val + "");
						addExpose(techind,datahash);
					}
					catch(Exception e)
					{
//						e.printStackTrace();
					}
				}

			} catch (Exception e)
			{
				// TODO Auto-generated catch block
				
	//			e.printStackTrace();
				continue;
			}
		}
		long t2 = System.currentTimeMillis();
		Vector resultvector=null;
		try
		{
			resultvector = database.executeQuery(strategyQuery, datavector1);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
	//		e.printStackTrace();
		}
		
		
		long t3 = System.currentTimeMillis();
//		return convertVectorIntoStringStockScreener(resultvector);
		return filterDuplicateRecords(resultvector);
	}
	public HashMap intradayHash=null;
	
	public Vector  filterDuplicateRecords(Vector oldList)
	{
		try
		{
			if(oldList==null)
				return oldList;
			
			Vector newList = new Vector();
			HashMap	stockListHash = new HashMap();
			
			HashMap symbolHash = (HashMap)oldList.firstElement();
			
			if(symbolHash==null)
				return oldList;
			String symbol = (String)symbolHash.get(MainGraphComponent.Symbol);
			
			if(symbol==null)
				return oldList;
			
			for(int i=0;i<oldList.size();i++)
			{
				symbolHash = (HashMap)oldList.get(i);
				symbol = (String)symbolHash.get(MainGraphComponent.Symbol);
				if(stockListHash.get(symbol) == null)
				{
					newList.add(oldList.get(i));
					stockListHash.put(symbol, symbol);
				}
						
				
			}
			return newList;
	
		}
		catch(Exception e)
		{
			return oldList;
		}
				
	
	}	
	
	public Vector getAllStocksQueryIntraday(HashMap intradayHash, String strategyQuery)
	{
		this.intradayHash=intradayHash;
		Vector datavector1 = new Vector();
		for(Iterator it = intradayHash.keySet().iterator();it.hasNext();)
		{
			HashMap hs = new HashMap();
			hs.put(MainGraphComponent.Symbol,it.next());
			datavector1.add(hs);
		}
		
		strategyQuery = strategyQuery.toUpperCase();
//		String expandedQuery = expandFormula(strategyQuery);
		techind = new TechIndicatorLibrary(datavector1,intradayHash);
		Vector indVec = findAllIndicatorInQuery(strategyQuery);
		
		Vector nameParaVector = new Vector();
		
		for (int i = 0; i < indVec.size(); i++)
		{
			HashMap hs = getIndNameAndPara((String) indVec.elementAt(i));
			nameParaVector.addElement(hs);
		}
		long t1 = System.currentTimeMillis();
		for (int i = 0; i < datavector1.size(); i++)
		{
			HashMap datahash = (HashMap) datavector1.elementAt(i);
			String symbol = (String) datahash.get(MainGraphComponent.Symbol);
			Vector v=null;
			
			try
			{
				v = (Vector)intradayHash.get(symbol);
//				v = subList(v, v.size() - 50,v.size());
				
				 techind = new TechIndicatorLibrary(v,intradayHash);
				techind.symbol = symbol;
				techind.includeHash = lex.includeHash;
				
				for (int j = 0; j < nameParaVector.size(); j++)
				{
					try
					{
						String indicatorNameWithPara = (String) indVec.elementAt(j);
						HashMap hs1 = (HashMap) nameParaVector.elementAt(j);
						String indName = (String) hs1.get(INDNAME);
						Vector parameter = (Vector) hs1.get(PARAMETER);
						String val = techind.getResultForScreener(indName, parameter);
						
						if(val.equalsIgnoreCase("-10000"))
							throw new Exception();	

						datahash.put(indicatorNameWithPara, val + "");
						addExpose(techind,datahash);
					}
					catch(Exception e)
					{
//						e.printStackTrace();
					}
				}

			} catch (Exception e)
			{
				// TODO Auto-generated catch block
//				e.printStackTrace();
				continue;
			}
		}
		long t2 = System.currentTimeMillis();
		Vector resultvector=null;
		try
		{
			resultvector = database.executeQuery(strategyQuery, datavector1);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
	//		e.printStackTrace();
		}
		
		long t3 = System.currentTimeMillis();
//		return convertVectorIntoStringStockScreener(resultvector);
		return resultvector;
	}


	public Vector getAllStocks(Vector datavector1, String strategyQuery)
	{
		strategyQuery = strategyQuery.toUpperCase();
//		String expandedQuery = expandFormula(strategyQuery);
		
		Vector indVec = findAllIndicatorInQuery(strategyQuery);
		
		Vector nameParaVector = new Vector();
		
		for (int i = 0; i < indVec.size(); i++)
		{
			HashMap hs = getIndNameAndPara((String) indVec.elementAt(i));
			nameParaVector.addElement(hs);
		}
		long t1 = System.currentTimeMillis();
		for (int i = 0; i < datavector1.size(); i++)
		{
			HashMap datahash = (HashMap) datavector1.elementAt(i);
			String symbol = (String) datahash.get(MainGraphComponent.Symbol);
			Vector v=null;
			
			try
			{
				v = Loader.getFileContentForScreening(StockConstants.STARTDATE, symbol.toUpperCase(),StockConstants.SCREENDATE,StockConstants.ENDDATE);
//				v = subList(v, v.size() - 50,v.size());
				
				 techind = new TechIndicatorLibrary(v);
				techind.symbol = symbol;
				techind.includeHash = lex.includeHash;
				
				for (int j = 0; j < nameParaVector.size(); j++)
				{
					try
					{
						String indicatorNameWithPara = (String) indVec.elementAt(j);
						HashMap hs1 = (HashMap) nameParaVector.elementAt(j);
						String indName = (String) hs1.get(INDNAME);
						Vector parameter = (Vector) hs1.get(PARAMETER);
						String val = techind.getResultForScreener(indName, parameter);
						if(val.equalsIgnoreCase("-10000"))
							throw new Exception();	

						datahash.put(indicatorNameWithPara, val + "");
						
					}
					catch(Exception e)
					{
						
					}
				}

			} catch (Exception e)
			{
				// TODO Auto-generated catch block
	//			e.printStackTrace();
				continue;
			}
		}
		long t2 = System.currentTimeMillis();
  		Vector resultvector=null;
		try
		{
			resultvector = database.evaluateFormulaMainVector(datavector1,strategyQuery);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
		//	e.printStackTrace();
		}
		
		long t3 = System.currentTimeMillis();
//		return convertVectorIntoStringStockScreener(resultvector);
		return resultvector;
	}


	public String evaluateExpress(Vector datavector, String strategyQuery)
	{
		strategyQuery = strategyQuery.toUpperCase();
//		Vector datavector1 = (Vector) datavector.clone();
		techind = new TechIndicatorLibrary(datavector);
		Vector indVec = findAllIndicatorInQuery(strategyQuery);
		HashMap datahash = new HashMap();
		Vector nameParaVector = new Vector();
		
		for (int i = 0; i < indVec.size(); i++)
		{
			HashMap hs = getIndNameAndPara((String) indVec.elementAt(i));
			nameParaVector.addElement(hs);
		}
		 techind = new TechIndicatorLibrary(datavector);
		for (int j = 0; j < nameParaVector.size(); j++)
		{
			String indicatorNameWithPara = (String) indVec.elementAt(j);
			HashMap hs1 = (HashMap) nameParaVector.elementAt(j);
			String indName = (String) hs1.get(INDNAME);
			Vector parameter = (Vector) hs1.get(PARAMETER);
			
			String val = "0";
			try
			{
				val =  techind.getResultForScreener(indName, parameter);
				if(val.equalsIgnoreCase("-10000"))
					throw new Exception();	
				datahash.put(indicatorNameWithPara, val + "");
				
			}
			catch(Exception e)
			{
				continue;
			}
		}
		Vector tempV = new Vector();
		tempV.add(datahash);

		String[] resultvector=null;
		try
		{
			resultvector = database.evaluateExpression(tempV, strategyQuery);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
	//		e.printStackTrace();
		}
		return resultvector[0];
	}

	public static Vector copyData(Vector datavector)
	{
		Vector datavector1=null;
		try
		{
			datavector1 = (Vector)deepClone(datavector);
		}
		catch (Exception e)
		{
			return null;
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}
		return datavector1;
		
/*		new Vector();
		for(int i=0;i< datavector.size();i++)
		{
			HashMap hs = (HashMap)datavector.get(i);
			HashMap copyHash = new HashMap();
			for(Iterator it = hs.keySet().iterator(); it.hasNext();)
			{
				Object o = it.next();
				copyHash.put(o, hs.get(o));
			}
			datavector1.addElement(copyHash);
		}
		return datavector1;
*/	
	}
	public static Object deepClone(Object toClone) throws Exception {
		 
        Object clone = null;
        ObjectOutputStream out = null;
        ObjectInputStream in = null;
        try {
                        ByteArrayOutputStream bout = new ByteArrayOutputStream();
                        out = new ObjectOutputStream(new BufferedOutputStream(bout));
                        out.writeObject(toClone);
                        out.flush();
                        out.close();
                        in = new ObjectInputStream(new BufferedInputStream(
                                        new ByteArrayInputStream(bout.toByteArray())));
                        clone = in.readObject();
                        in.close();
                } catch (Throwable e) {
 
                }
        return clone;
    }
	public void addExpose(TechIndicatorLibrary techind,Vector datavector1)
	{
		Vector v  = techind.exposeVector;
		for(int i=0;i<v.size();i++)
		{
			HashMap hs = (HashMap)v.get(i);
			HashMap result = (HashMap)datavector1.get(i);
			for(Iterator it = hs.keySet().iterator();it.hasNext();)
			{
				String key = (String)it.next();
				if(key.startsWith(DataBaseInMemory.EXPOSE))
				{
					String value = (String)hs.get(key);
					String actual = Utility.replaceString(key, DataBaseInMemory.EXPOSE,"");
					result.put(actual,value);
				}
			}
		}
	}
	public void addExpose(TechIndicatorLibrary techind,HashMap result)
	{
		Vector v  = techind.exposeVector;
		
			HashMap hs = (HashMap)v.lastElement();
			for(Iterator it = hs.keySet().iterator();it.hasNext();)
			{
				String key = (String)it.next();
				if(key.startsWith(DataBaseInMemory.EXPOSE))
				{
					String value = (String)hs.get(key);
					String actual = Utility.replaceString(key, DataBaseInMemory.EXPOSE,"");
					result.put(actual,value);
				}
			}
	}

	public Vector executeStrategy(Vector datavector,String realQuery)
	{
		Vector datavector1 = copyData(datavector);
		realQuery = realQuery.toUpperCase();
		techind = new TechIndicatorLibrary(datavector);
		if(techind.symbol==null || techind.symbol.equalsIgnoreCase(""))
			techind.symbol = this.symbol;
		
		Vector indVec = findAllIndicatorInQuery(realQuery);
		techind.includeHash = lex.includeHash;
		
		for (int i = 0; i < indVec.size(); i++)
		{
			HashMap hs = getIndNameAndPara((String) indVec.elementAt(i));
			String indName = (String) hs.get(INDNAME);
			Vector parameter = (Vector) hs.get(PARAMETER);
			Object val = techind.getResultForBuySell(indName, parameter);
			addExpose(techind,datavector1);
			addValueToVector(indName, (String) indVec.elementAt(i), val, datavector1);
		}
		Vector resultvector=null;
		try
		{
			resultvector = database.evaluateFormulaMainVector(datavector1, realQuery);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
	//		e.printStackTrace();
		}
		return resultvector;
	}


	public HashMap evaluateExpressHashmap(Vector datavector, String realQuery)
	{
		realQuery = realQuery.toUpperCase();
			HashMap tempHs = (HashMap)datavector.get(0);
			HashMap copyHash = new HashMap();
			for(Iterator it = tempHs.keySet().iterator(); it.hasNext();)
			{
				Object o = it.next();
				copyHash.put(o, tempHs.get(o));
			}

//		Vector datavector1 = (Vector) datavector.clone();
		Vector indVec = findAllIndicatorInQuery(realQuery);
		HashMap datahash = new HashMap();
		Vector nameParaVector = new Vector();
		
		for (int i = 0; i < indVec.size(); i++)
		{
			HashMap hs = getIndNameAndPara((String) indVec.elementAt(i));
			nameParaVector.addElement(hs);
		}
		techind = new TechIndicatorLibrary(datavector);
		if(techind.symbol==null || techind.symbol.equalsIgnoreCase(""))
			techind.symbol = this.symbol;
		
		techind.includeHash = lex.includeHash;
		
		for (int j = 0; j < nameParaVector.size(); j++)
		{
			String indicatorNameWithPara = (String) indVec.elementAt(j);
			HashMap hs1 = (HashMap) nameParaVector.elementAt(j);
			String indName = (String) hs1.get(INDNAME);
			Vector parameter = (Vector) hs1.get(PARAMETER);
			
			String val = "0";
			try
			{
				val =  techind.getResultForScreener(indName, parameter);
				if(val.equalsIgnoreCase("-10000"))
					throw new Exception();	
				tempHs.put(indicatorNameWithPara, val + "");
				
			}
			catch(Exception e)
			{
				continue;
			}
		}
		Vector tempV = new Vector();
		tempV.add(tempHs);
		Vector resultvector=null;
		try
		{
			resultvector = database.evaluateFormulaMainVector(tempV, realQuery);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}
		return (HashMap)resultvector.get(0);
	}
	
	
	
	public StockScreenerThreadMaster stscmstr=null;
	public Vector getAllStocksHashMap(Vector datavector1, String strategyQuery)
	{
		strategyQuery = strategyQuery.toUpperCase();
		techind = new TechIndicatorLibrary(datavector1);
		if(techind.symbol==null || techind.symbol.equalsIgnoreCase(""))
			techind.symbol = this.symbol;
		
		Vector indVec = findAllIndicatorInQuery(strategyQuery);
		
		Vector nameParaVector = new Vector();
		
		for (int i = 0; i < indVec.size(); i++)
		{
			HashMap hs = getIndNameAndPara((String) indVec.elementAt(i));
			nameParaVector.addElement(hs);
		}
		long t1 = System.currentTimeMillis();
		for (int i = 0; i < datavector1.size(); i++)
		{
			if(stscmstr != null)
				stscmstr.stockdone = stscmstr.stockdone + 1;
			
			HashMap datahash = (HashMap) datavector1.elementAt(i);
			String symbol = (String) datahash.get(MainGraphComponent.Symbol);
			Vector v=null;
			
			try
			{
				v = Loader.getFileContentForScreening(StockConstants.STARTDATE, symbol.toUpperCase(),StockConstants.SCREENDATE,StockConstants.ENDDATE);
				
				 techind = new TechIndicatorLibrary(v);
					if(techind.symbol==null || techind.symbol.equalsIgnoreCase(""))
						techind.symbol = this.symbol;
				techind.status = TechIndicatorLibrary.STOCKSCREENER;
				techind.includeHash = lex.includeHash;
				for (int j = 0; j < nameParaVector.size(); j++)
				{
					String indicatorNameWithPara = (String) indVec.elementAt(j);
					HashMap hs1 = (HashMap) nameParaVector.elementAt(j);
					String indName = (String) hs1.get(INDNAME);
					Vector parameter = (Vector) hs1.get(PARAMETER);
					
					String val = "0";
					try
					{
						val =  techind.getResultForScreener(indName, parameter);
						if(val.equalsIgnoreCase("-10000"))
							throw new Exception();	
						datahash.put(indicatorNameWithPara, val + "");
						
					}
					catch(Exception e)
					{
						
					}
				}
				if(stscmstr != null)
				{
					if(stscmstr.datadownloadscreen !=null)
					{
						stscmstr.datadownloadscreen.message.setText(stscmstr.datadownloadscreen.message.getText() + "Data downloaded for " + symbol +"\n");	
						stscmstr.datadownloadscreen.label2.setText("Data for " + stscmstr.stockdone + " symbols downloaded. out of " + stscmstr.datadownloadscreen.mysize);	

					}
				}

			} 
			catch (Exception e)
			{
				if(stscmstr != null)
				{
					if(stscmstr.datadownloadscreen !=null)
						stscmstr.datadownloadscreen.message.setText(stscmstr.datadownloadscreen.message.getText() + "Data can't be downloaded for " + symbol +"\n");	
				}
				continue;
			}
		}
		long t2 = System.currentTimeMillis();
		for(int i=0;i<datavector1.size();i++)
		{
			HashMap hs = (HashMap)datavector1.elementAt(i);
		}

//		Vector resultvector = database.executeQuery(strategyQuery, datavector1);
	
		Vector resultvector=null;
		try
		{
			resultvector = database.evaluateFormulaMainVector(datavector1, strategyQuery);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
	//		e.printStackTrace();
		}
		
		long t3 = System.currentTimeMillis();
		
//		return convertVectorIntoStringStockScreener(resultvector);
		return resultvector;
	}
	TechIndicatorLibrary techind;
//this is the method to run from stock scanner

//method for making intraday indicator hashmap
	public HashMap  getHashMapForIndicatorsForIntraday(String symbol, Vector datavector, String strategyQuery)
	{
	
		Vector datavector1 = copyData(datavector);
		techind = new TechIndicatorLibrary(datavector1);

		Vector indVec = findAllIndicatorInQuery(strategyQuery);
		HashMap datahash = new HashMap();
		datahash.put(MainGraphComponent.Symbol,symbol);
		
//		Vector nameParaVector = getNamePara(strategyQuery);
		Vector nameParaVector = new Vector();
		for (int i = 0; i < indVec.size(); i++)
		{
			HashMap hs = getIndNameAndPara((String) indVec.elementAt(i));
			nameParaVector.addElement(hs);
		}
		long t1 = System.currentTimeMillis();
		Vector v=datavector1;
		 techind = new TechIndicatorLibrary(v);
			if(techind.symbol==null || techind.symbol.equalsIgnoreCase(""))
				techind.symbol = this.symbol;
		
		for (int j = 0; j < nameParaVector.size(); j++)
		{
			String indicatorNameWithPara = (String) indVec.elementAt(j);
			HashMap hs1 = (HashMap) nameParaVector.elementAt(j);
			String indName = (String) hs1.get(INDNAME);
			Vector parameter = (Vector) hs1.get(PARAMETER);
			String val =  techind.getResultForScreener(indName, parameter);
			if(val.equalsIgnoreCase("-10000"))
				continue;
			datahash.put(indicatorNameWithPara, val + "");
		}
		return datahash;
	}
	public Vector getNamePara(String strategyQuery)
	{
		Vector indVec = findAllIndicatorInQuery(strategyQuery);
		Vector nameParaVector = new Vector();
		for (int i = 0; i < indVec.size(); i++)
		{
			HashMap hs = getIndNameAndPara((String) indVec.elementAt(i));
			nameParaVector.addElement(hs);
		}
		return nameParaVector;		
	}
	
	public Vector subList(Vector main,int startPos,int endPos)
		{
			Vector retVector = new Vector();

			if(endPos >= main.size())
				endPos = main.size();

			if(endPos <= startPos)
				return retVector;
				
			for(int i=startPos; i < endPos;i++)
			{
				retVector.addElement(main.elementAt(i));
			}
			return retVector;
		}

	
	public String createQuery(Vector strategyQueryVector)
	{
		StringBuffer clubedQuery = new StringBuffer();
		for(int i=0;i<strategyQueryVector.size();i++)
		{
			clubedQuery.append("( " + strategyQueryVector.elementAt(i) + " )" );
			if((i+1)<  strategyQueryVector.size())
				clubedQuery.append( " or ");
		}
		Vector indVec = findAllIndicatorInQuery(clubedQuery.toString());
		StringBuffer tempBuffer=new StringBuffer();
		for(int i=0;i<indVec.size();i++)
		{
			tempBuffer.append(indVec.elementAt(i)+",");
		}

		String clubedQueryStr = "select " + tempBuffer.toString() + "symbol from table where " + clubedQuery;
		return clubedQueryStr;
	}
	public Vector getAllStocksMultipleQueryHashmap(Vector datavector, String strategyQuery)
	{
		techind = new TechIndicatorLibrary(datavector);
		this.symbol = StockConstants.SELECTED_STOCK;
		techind.symbol = StockConstants.SELECTED_STOCK;
		Vector indVec = findAllIndicatorInQuery(strategyQuery);
		StringBuffer tempBuffer=new StringBuffer();
		for(int i=0;i<indVec.size();i++)
		{
			tempBuffer.append(indVec.elementAt(i)+",");
		}
		return getAllStocksHashMap(datavector, strategyQuery);
		
	}
	

	/*
	public String[] getAllStocksMultipleQuery(Vector datavector, Vector strategyQueryVector)
	{
		StringBuffer clubedQuery = new StringBuffer();
		for(int i=0;i<strategyQueryVector.size();i++)
		{
			clubedQuery.append("( " + strategyQueryVector.elementAt(i) + " )" );
			if((i+1)<  strategyQueryVector.size())
				clubedQuery.append( " or ");
		}
		String clubedQueryStr = BuySellStrategy.SELECTSTOCKQUERY + clubedQuery;
		String[] s =  getAllStocks(datavector, clubedQueryStr);
		return s;
	}
	*/	
	public String convertVectorIntoString(Vector resultvector)
	{
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < resultvector.size(); i++)
		{
			HashMap hs = (HashMap) resultvector.elementAt(i);
			String s = (String) hs.get(MainGraphComponent.Date);
			sb.append(":" + s);
		}
		return sb.toString();
	}
	public String[] convertVectorIntoStringStockScreener(Vector resultvector)
	{
		long t1 = System.currentTimeMillis();
		String[] sb = new String[resultvector.size()];
		for (int i = 0; i < resultvector.size(); i++)
		{
			HashMap hs = (HashMap) resultvector.elementAt(i);
			String s = (String) hs.get(MainGraphComponent.Symbol);
			sb[i] =  s;
		}
		long t2 = System.currentTimeMillis();

		return sb;
	}


	/**
	 * @param object
	 * @param datavector
	 * @param val
	 */
	/**
	 * @param indName
	 * @param string
	 * @param val
	 * @param datavector1
	 */
	private void addValueToVector(String indName, String indWithPara, Object val, Vector datavector1)
	{
		//		if(indName.equals(TechIndicatorLibrary.MMA) || indName.equals(TechIndicatorLibrary.EMA) || indName.equals(TechIndicatorLibrary.WMA) ||
		//		indName.equals(TechIndicatorLibrary.WMA) || indName.equals(TechIndicatorLibrary.RSI) || indName.equals(TechIndicatorLibrary.SMA) || 
		//		indName.equals(TechIndicatorLibrary.SMA ))
		//		{
//		float[] val1 = (float[]) val;
		String[] val1 = (String[]) val;

		for (int i = 0; i < val1.length; i++)
		{
			HashMap hs = (HashMap) datavector1.elementAt(i);
			hs.put(indWithPara, val1[i] + "");
		}
		//		}
	}
	/**
	 * @param string
	 * @return
	 */
	public static String PARAMETER = "PARAMETER";
	public static String INDNAME = "INDNAME";
/*	private HashMap getIndNameAndPara(String indStr)
	{
		HashMap retHash = new HashMap();
		String indname = "";
		int index = indStr.indexOf("[");
		if (index != -1)
		{
			indname = indStr.substring(0, index);
			indStr = indStr.substring(index + 1);
			retHash.put(INDNAME, indname);
		}
		index = indStr.indexOf("]");
		if (index != -1)
		{
			indStr = indStr.substring(0, index);
			StringTokenizer st = new StringTokenizer(indStr, ";");
			Vector para = new Vector();
			while (st.hasMoreElements())
			{
				para.addElement(st.nextToken());
			}
			retHash.put(PARAMETER, para);
			return retHash;
		}
		retHash.put(INDNAME, indStr);
		// TODO Auto-generated method stub
		return retHash;
	}*/
	public  HashMap getIndNameAndPara(String indStr) 
	{
		int pos =0;
		char c;
		StringBuffer word = new StringBuffer();
		HashMap retHash = new HashMap();
		Vector para = new Vector();
		if(indStr.indexOf("[") == -1 )
		{
			retHash.put(INDNAME, indStr);
			retHash.put(PARAMETER,new Vector());
			
		}
		while(pos < indStr.length())
		{
			c = indStr.charAt(pos);
			if( c != '[')
			{
				word.append(c);
			}
			else
			{
				String indName = word.toString().trim();
				retHash.put(INDNAME, indName);
				word = new StringBuffer();
				pos++;
				break;
			}
			pos++;
		}	
		int count = 1;
		
		while( count != 0 && pos < indStr.length())
		{
			c = indStr.charAt(pos);
			if( c == '[' )
			{
				count++;
//				CalculateToken calculatetoken = new CalculateToken(indStr,pos);
//				String paraName = calculatetoken.para;
//				pos = calculatetoken.pos;
//				word.append(paraName.toString());	
				
			}
			else if( c== ']')
			{
				count--;
	//			para.add(word.toString());
	//			word = new StringBuffer();

	//			break;
			}
			if(c==';' && count==1)
			{
			//	// ("para="+word.toString().trim());
				para.add(word.toString().trim());
				word = new StringBuffer();
				pos++;
				continue;				
			}	
			word.append(c);
			pos++;
		}
		if(!word.toString().trim().equals(""))
		{
			String tmp = word.toString().trim(); 
			int i = tmp.lastIndexOf("]");
			if(i != -1)
			{
				tmp = tmp.substring(0,i);  
			}
			para.add(tmp.trim());
		}
		if(count != 0)
		{
//			throw new Exception("In valid token " + indStr + " found ");	
//			// ln("In valid token " + indStr + " found ");
		}
		else
		{
			retHash.put(PARAMETER,para);
		}
	//	// ln("retHash="+retHash);
		return retHash;
	}
	LexicalAnalyzer lex = new LexicalAnalyzer();
	public String symbol;

	public Vector findAllIndicatorInQuery(String strategyQuery)
	{
		if(this.symbol==null || this.symbol.equalsIgnoreCase(""))
		{
			this.symbol = StockConstants.SELECTED_STOCK;
			techind.symbol = StockConstants.SELECTED_STOCK;
		}
		strategyQuery = lex.findConstantStrings(strategyQuery);
		techind.includeHash = lex.includeHash;
		Vector retVector = new Vector();
		strategyQuery = strategyQuery.toUpperCase();

//this lines now not required 		
//		Vector v = StrategyUtility.getAllStrategy();

//		Vector tempv = findAllFuncationCall(v, strategyQuery);
		Vector tempv = new Vector();

		for(int i=0;i < tempv.size();i++)
		{
			String str = (String)tempv.get(i);
			String formula = StrategyUtility.getFormula(str);
			Vector tokens1 = queryParser(formula.toUpperCase()+" ");
			for (int j = 0; j < tokens1.size(); j++)
			{
				String myt = (String) tokens1.elementAt(j);
				if (techind.isTokenIndicator(myt))
				{
					if(!retVector.contains(myt))
						retVector.add(myt);
				}
			}
		}
		Vector tokens = queryParser(strategyQuery);

		for (int i = 0; i < tokens.size(); i++)
		{
			if (techind.isTokenIndicator((String) tokens.elementAt(i)))
			{
				if(!retVector.contains(tokens.get(i)))
					retVector.add(tokens.get(i));
			}
		}
		return retVector;
	}
	public static Vector queryParser(String query)
	{
		Vector tokens = new Vector();
		int i = 0;
		int state = 0;
		StringBuffer word = new StringBuffer();
		boolean worldstart = true;
		int bracket=0;
		while (i < query.length())
		{
			char c = query.charAt(i);
			if(c=='[')
			{
				bracket++;
			}
			else if (c==']')
			{
				bracket--;
			}
			i++;
			boolean cond1 =  ( c == '=' || c== '\n' || c== '{' || c=='}' || c == ' ' || c == ',' || c == '>' || c == '<' || c == '=' || c == '(' || c == ')' || c == '!' );
			
			if (cond1 && bracket==0 )
			{
				String myword = word.toString().trim();
				myword = Utility.replaceString(myword," ", "");
				if (!myword.equals(""))
					tokens.addElement(myword);
		//		// ln(tokens);
				word = new StringBuffer();
				continue;
			}
			word.append(c);
		}
		String myword = word.toString().trim();
		
		if(!word.toString().trim().equalsIgnoreCase(""))
		{
			myword = Utility.replaceString(myword," ", "");
			tokens.add(myword);
		}
	//	// ln("token="+tokens);
		return tokens;
	}
	public static boolean findToken(String tokenName,String query)
	{
		boolean isfound=false;
		int i = 0;
		int state = 0;
		StringBuffer word = new StringBuffer();
		boolean worldstart = true;
		while (i < query.length())
		{
			char c = query.charAt(i);
			i++;
			if (c == '=' || c== '\n' || c== '{' || c=='}' || c == ' ' || c == ',' || c == '>' || c == '<' || c == '=' || c == '(' || c == ')' || c == '!' || c == '\t' || c == '\r')
			{
				String myword = word.toString().trim();
				if (myword.equalsIgnoreCase(tokenName))
					return true;
				word = new StringBuffer();
				continue;
			}
			word.append(c);
		}
		String myword = word.toString().trim();

		if (myword.equalsIgnoreCase(tokenName))
			return true;
		
		return false;
		
	}
	public static Vector findAllFuncationCall(Vector functions,String query)
	{
		Vector retVector = new Vector();
		boolean isfound=false;
		int i = 0;
		int state = 0;
		StringBuffer word = new StringBuffer();
		boolean worldstart = true;
		while (i < query.length())
		{
			char c = query.charAt(i);
			i++;
			if (c == '=' || c== '\n' || c== '{' || c=='}' || c == ' ' || c == ',' || c == '>' || c == '<' || c == '=' || c == '(' || c == ')' || c == '!' || c == '\t' || c == '\r')
			{
				String myword = word.toString().trim().toUpperCase();
				for(int j=0;j<functions.size();j++)
				{
					String s = ((String)functions.get(j)).toUpperCase();
					if(s.equals(myword) && !retVector.contains(s))
					{
						retVector.add(s);	
					}
				}
				word = new StringBuffer();
				continue;
			}
			word.append(c);
		}
		String myword = word.toString().trim();

		for(int j=0;j<functions.size();j++)
		{
			String s = ((String)functions.get(j)).toUpperCase();
			if(s.equals(myword) && !retVector.contains(s))
			{
				retVector.add(s);	
			}
		}
		return retVector;		
	}
//	public static String startDate = "2009-01-01";

	

	public static void main(String[] args)
	{
		
/*		String[] symbols = {"TCS"};
		Vector datavector = new Vector();
		for(int i=0;i<symbols.length;i++)
		{
			HashMap hs = new HashMap();
			hs.put("symbol",symbols[i]);
			datavector.addElement(hs);
		}
*/
		BuySellStrategy buysell = new BuySellStrategy();
//		Vector v = buysell.getHashMapForIndicators(datavector,BuySellStrategy.SELECTSTOCKQUERY + " CLOSE[0] , OPEN[0] , HIGH[0] , RSI[14;0] , MACDLINE[12;26;9;0] ");
	//	String[] s = buysell.getValueForDataForCustmizedInd("TCS","COMPARE[RELIANCE;CLOSE[0]]");
		try
		{
			Vector v = Loader.getFileContentForScreening(StockConstants.STARTDATE, "TCS", StockConstants.SCREENDATE,StockConstants.ENDDATE);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
	//		e.printStackTrace();
		}
				
	
	}
}

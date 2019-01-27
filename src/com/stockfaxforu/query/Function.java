package com.stockfaxforu.query;

import java.util.Calendar;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.Vector;

import com.stockfaxforu.component.MainGraphComponent;
import com.stockfaxforu.util.StockConstants;
import com.stockfaxforu.util.Utility;

public class Function 
{
	public static final String LEFTPRICE = "LEFTPRICE";
	public static final String RIGHTPRICE = "RIGHTPRICE";
	public static final String ALERTTEXT = "ALERTTEXT";

	public static final String PLOTMAIN1="PLOTMAIN1";
	public static final String PLOTMAIN2="PLOTMAIN2";
	public static final String PLOTMAIN3="PLOTMAIN3";
	
	public static final String COLORMAIN1="COLORMAIN1";
	public static final String COLORMAIN2="COLORMAIN2";
	public static final String COLORMAIN3="COLORMAIN3";
	
	
	

	
	public DataBaseInMemory database=null;
	
	public  String calculateFunction(String exp,HashMap hs,DataBaseInMemory database) 
	{
		this.database = database;
		try
		{
			char c = ' ';
			int pos=0;
			StringBuffer token = new StringBuffer();
			while(true)
			{
				c = exp.charAt(pos);
				pos++;
				if(c=='{')
				{
					break;				
				}
				else
				{
					token.append(c);
				}
			}
			String keyword = token.toString();
			int count = 1;
			token = new StringBuffer();
			while(count != 0)
			{
				c = exp.charAt(pos);
				pos++;
				if(c=='{')
				{
					count++;				
				}
				else if(c=='}')
				{
					count--;
				}
				if(count==0)
				{
					break;
				}
				token.append(c);
			}
			String value = token.toString();
	
			if(keyword.equalsIgnoreCase("echo"))
			{	
				return echo(value,hs);
			}
			
			else if(keyword.equalsIgnoreCase("plotpriceonleft"))
			{	
				return plotpriceonleft(value,hs);
			}
			else if(keyword.equalsIgnoreCase("remove"))
			{	
				return remove(value,hs);
			}

			else if(keyword.equalsIgnoreCase("plotpriceonright"))
			{	
				return plotpriceonright(value,hs);
			}

			else if(keyword.equalsIgnoreCase("addtoglobal"))
			{	
				return addtoglobal(value,hs);
			}
			else if(keyword.equalsIgnoreCase("expose"))
			{	
				return expose(value,hs);
			}

			else if(keyword.equalsIgnoreCase("getvalue"))
			{	
				return getvalue(value,hs);
			}
			else if(keyword.equalsIgnoreCase("updatevar"))
			{	
				return updatevar(value,hs);
			}

		
			else if(keyword.equalsIgnoreCase("buy"))
			{	
				return createVariable("BUY",value,hs);
			}
			else if(keyword.equalsIgnoreCase("sell"))
			{	
				return createVariable("SELL",value,hs);
			}

			else if(keyword.equalsIgnoreCase("abs"))
			{	
				return abs(value,hs);
			}
			else if(keyword.equalsIgnoreCase("invert"))
			{
				return invert(value,hs);
			}
			else if(keyword.equalsIgnoreCase("ifelse"))
			{
				return ifelse(value,hs);
			}
			else if(keyword.equalsIgnoreCase("ifcond"))
			{
				return ifcond(value,hs);
			}
			else if(keyword.equalsIgnoreCase("while"))
			{
				return whileloop(value,hs);
			}

			else if(keyword.equalsIgnoreCase("percent"))
			{
				return percent(value,hs);
			}
			else if(keyword.equalsIgnoreCase("oneof"))
			{
				return oneof(value,hs);
			}
			else if(keyword.equalsIgnoreCase("print"))
			{
				return print(value,hs);
			}
			else if(keyword.equalsIgnoreCase("println"))
			{
				return println(value,hs);
			}
			else if(keyword.equalsIgnoreCase("alert"))
			{
				return createVariable("ALERT",value,hs);
			}
			else if(keyword.equalsIgnoreCase("alerttext"))
			{
				return createVariable(ALERTTEXT,value,hs);
			}

			else if(keyword.equalsIgnoreCase("sqrt"))
			{
				return sqrt(value, hs);
			}
			else if(keyword.equalsIgnoreCase("pow"))
			{
				return pow(value, hs);
			}
			else if(keyword.equalsIgnoreCase("plot1") || keyword.equalsIgnoreCase("plot2") || keyword.equalsIgnoreCase("plot3"))
			{
				return plot(keyword,value, hs);
			}
			else if(keyword.equalsIgnoreCase("plotmain1") || keyword.equalsIgnoreCase("plotmain2") || keyword.equalsIgnoreCase("plotmain3"))
			{
				return plotmain(keyword,value, hs);
			}

			else if(keyword.equalsIgnoreCase("circle") || keyword.equalsIgnoreCase("rectangle") || keyword.equalsIgnoreCase("text"))
			{
				return plotcomponent(keyword,value, hs);
			}
			else if(keyword.equalsIgnoreCase("line"))
			{
				return plotcomponentline(keyword,value, hs);
			}
			else if(keyword.equalsIgnoreCase("linexy"))
			{
				return plotcomponentlinexy(keyword,value, hs);
			}
			
			else if(keyword.equalsIgnoreCase("intvalue"))
			{
				return intvalue(value, hs);
			}
			else if(keyword.equalsIgnoreCase("ceiling"))
			{
				return ceiling(value, hs);
			}
			else if(keyword.equalsIgnoreCase("round"))
			{
				return round(value, hs);
			}
			else if(keyword.equalsIgnoreCase("floatattwo"))
			{
				return floatAtTwo(value, hs);
			}
		
			else if(keyword.equalsIgnoreCase("log"))
			{
				return log(value, hs);
			}
			else if(keyword.equalsIgnoreCase("floor"))
			{
				return floor(value, hs);
			}

			else if(keyword.equalsIgnoreCase("var"))
			{
				return var(value, hs);
			}
			else if(keyword.equalsIgnoreCase("barsince"))
			{
				return barsince(value, hs);
			}
			else if(keyword.equalsIgnoreCase("sumbars"))
			{
				return sumbars(value, hs);
			}
			else if(keyword.equalsIgnoreCase("max"))
			{
				return max(value, hs);
			}
			else if(keyword.equalsIgnoreCase("min"))
			{
				return min(value, hs);
			}

			else if(keyword.equalsIgnoreCase("sumcondmeet"))
			{
				return sumcondmeet(value, hs);
			}
			else if(keyword.equalsIgnoreCase("dayofweek"))
			{
				return dayofweek(value, hs);
			}
			else if(keyword.equalsIgnoreCase("dayofmonth"))
			{
				return dayofmonth(value, hs);
			}
			else if(keyword.equalsIgnoreCase("getnotzerovalue"))
			{
				return getnotzerovalue(value, hs);
			}
			else if(keyword.equalsIgnoreCase("getnotzerobaraway"))
			{
				return getnotzerobaraway(value, hs);
			}


			return exp;
		}
		catch(Exception e)
		{
			return exp;
		}
		// TODO Auto-generated method stub
		
	}
	private String getnotzerovalue(String value, HashMap hs) throws Exception
	{
//	(Nth, Data Array, %Minimum Change) 
		
		Vector st = tokenize(value);
		String n = (String)st.get(0);
		String peakind = (String)st.get(1);
		
		n = database.evaluateFormula(hs,n);
		float nf = Float.parseFloat(n);
	
		
		int barno = Integer.parseInt((String)database.globalHashMap.get(DataBaseInMemory.BARNO));
		int noofbars = Integer.parseInt((String)database.globalHashMap.get(DataBaseInMemory.NOOFBARS));

		int bars = 0;
		int total = 0;
		for(int i = barno ; i > 0 ; i-- )
		{
			try
			{
				
				HashMap myhs = (HashMap)database.dataTable.get(i);
				String retval = (String)myhs.get(peakind);
				float 	retvalf = Float.parseFloat(retval);
				if (retvalf != 0)
				{
					total++;
				}
				if (total >= nf)
				{
					return retval;
				}
			}
			catch(Exception e)
			{
				
			}
			
			
			
		}
		return total+"";
			
		
	}
	private String getnotzerobaraway(String value, HashMap hs) throws Exception
	{
//	(Nth, Data Array, %Minimum Change) 
		
		Vector st = tokenize(value);
		String n = (String)st.get(0);
		String peakind = (String)st.get(1);
		
		n = database.evaluateFormula(hs,n);
		float nf = Float.parseFloat(n);
	
		
		int barno = Integer.parseInt((String)database.globalHashMap.get(DataBaseInMemory.BARNO));
		int noofbars = Integer.parseInt((String)database.globalHashMap.get(DataBaseInMemory.NOOFBARS));

		int bars = 0;
		int total = 0;
		for(int i = barno ; i > 0 ; i-- )
		{
			try
			{
				
				HashMap myhs = (HashMap)database.dataTable.get(i);
				String retval = (String)myhs.get(peakind);
				float 	retvalf = Float.parseFloat(retval);
				if (retvalf != 0)
				{
					total++;
				}
				if (total >= nf)
				{
					return bars+"";
				}
			}
			catch(Exception e)
			{
				
			}
			bars++;
			
			
		}
		return total+"";
			
		
	}


	private String max(String value, HashMap hs) throws Exception
	{
		Vector st = tokenize(value);
		String varname1 = ((String)st.get(0)).trim();
		varname1 = database.evaluateFormula(hs,varname1);
		float var1f = Float.parseFloat(varname1);
		
		String varname2 = ((String)st.get(1)).trim();
		varname2 = database.evaluateFormula(hs,varname2);
		float var2f = Float.parseFloat(varname2);
		
		if(var1f > var2f)
			return var1f+"";
		else
			return var2f+"";
	}
	private String min(String value, HashMap hs) throws Exception
	{
		Vector st = tokenize(value);
		String varname1 = ((String)st.get(0)).trim();
		varname1 = database.evaluateFormula(hs,varname1);
		float var1f = Float.parseFloat(varname1);
		
		String varname2 = ((String)st.get(1)).trim();
		varname2 = database.evaluateFormula(hs,varname2);
		float var2f = Float.parseFloat(varname2);
		
		if(var1f < var2f)
			return var1f+"";
		else
			return var2f+"";
	}

	private String expose(String value, HashMap hs) throws Exception
	{
		Vector st = tokenize(value);
		String varname = ((String)st.get(0)).trim();
		
		String val = database.evaluateFormula(hs,varname);
		
		hs.put(DataBaseInMemory.EXPOSE+varname,val);
		return "";
	}
	public Vector tokenize(String indStr)
	{
		int count=0;
		int pos=0;
		StringBuffer word = new StringBuffer();
		Vector para = new Vector();
		while(  pos < indStr.length())
		{
			char c = indStr.charAt(pos);
			if( c == '{' )
			{
				count++;
			}
			else if( c== '}')
			{
				count--;
			}
			if(c==',' && count==0)
			{
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
			para.add(tmp.trim());
		}
		return para;
	}
	private String dayofweek(String value, HashMap hs) throws Exception
	{
		Calendar cal = getCalender(value, hs);
		return cal.get(Calendar.DAY_OF_WEEK)+"";
			
	}
	private String dayofmonth(String value, HashMap hs) throws Exception
	{
		Calendar cal = getCalender(value, hs);
		return cal.get(Calendar.DAY_OF_MONTH)+"";
			
	}
	
	private Calendar getCalender(String value, HashMap hs) throws Exception
	{
		Calendar cal = Calendar.getInstance();
		String expr = database.evaluateFormula(hs,value);
		String[] str = expr.split("-");
		cal.set(Integer.parseInt(str[0]),Integer.parseInt(str[1]) - 1, Integer.parseInt(str[2]));
		return cal;
	}
	private String getvalue(String value, HashMap hs) throws Exception
	{
		Vector st = tokenize(value);
		String expr = ((String)st.get(0)).trim();
		String position = database.evaluateFormula(hs,((String)st.get(1)).trim());
		int myx = (int)Float.parseFloat(position);
		HashMap hs1 = (HashMap)database.dataTable.get(myx);
		String output = database.evaluateFormula(hs1, expr);
	
		return output;
		
	}
	private String updatevar(String value, HashMap hs) throws Exception
	{
		Vector st = tokenize(value);
		
		String var = ((String)st.get(0)).trim();
//		var = database.evaluateFormula(hs,var);

		String expr = ((String)st.get(1)).trim();
		expr = database.evaluateFormula(hs,expr);

		String position = database.evaluateFormula(hs,((String)st.get(2)).trim());
		int myx = (int)Float.parseFloat(position);
		HashMap hs1 = (HashMap)database.dataTable.get(myx);
		
		hs1.put(var,expr);

		return "";
		
	}
//need to work on this
	private String addindicator(String value, HashMap hs) throws Exception
	{
		Vector st = tokenize(value);
		
		String var = ((String)st.get(0)).trim();
//		var = database.evaluateFormula(hs,var);

		String expr = ((String)st.get(1)).trim();
		expr = database.evaluateFormula(hs,expr);
		
		String position = database.evaluateFormula(hs,((String)st.get(2)).trim());
		int myx = (int)Float.parseFloat(position);
		HashMap hs1 = (HashMap)database.dataTable.get(myx);
		
		hs1.put(var,expr);

		return "";
		
	}
	
	private String barsince(String value, HashMap hs) throws Exception
	{
		int barno = Integer.parseInt((String)database.globalHashMap.get(DataBaseInMemory.BARNO));
		int bars = 0;
		int i = barno;
		for(i = barno ; i >= 0; i-- )
		{
			HashMap myhs = (HashMap)database.dataTable.get(i);
			String retval = database.evaluateFormula(myhs, value);
			if(retval.equalsIgnoreCase("true"))
			{
				return bars+"";
			}
			bars++;
		}
		if( i < 0)
			return  -1 + "";
		else
		    return bars+"";
	}
	
	private String sumcondmeet(String value, HashMap hs) throws Exception
	{
		Vector st = tokenize(value);
		String expr = (String)st.get(0);
		String position = database.evaluateFormula(hs,(String)st.get(1));
		String direction = database.evaluateFormula(hs,(String)st.get(2));
	
		int myx = (int)Float.parseFloat(position);

		int barno = Integer.parseInt((String)database.globalHashMap.get(DataBaseInMemory.BARNO));
		int noofbars = Integer.parseInt((String)database.globalHashMap.get(DataBaseInMemory.NOOFBARS));

		int bars = 0;
		float total = 0;
		if (direction.equalsIgnoreCase("B"))
		{
			for(int i = barno ; i >= 0 && bars < myx ; i-- )
			{
				HashMap myhs = (HashMap)database.dataTable.get(i);
				String retval = database.evaluateFormula(myhs, expr);
				if( retval.equalsIgnoreCase("true"))
				{
					total = total + 1;
					
				}
				bars++;
			}
			return total+"";
			
		}
		else if (direction.equalsIgnoreCase("A"))
		{
			for(int i = barno ; i < noofbars && bars < myx ; i++ )
			{
				HashMap myhs = (HashMap)database.dataTable.get(i);
				String retval = database.evaluateFormula(myhs, expr);
				if( retval.equalsIgnoreCase("true"))
				{
					total = total + 1;
					
				}
				bars++;
			}
			return total+"";
			
		}
		return "";
	}

	private String sumbars(String value, HashMap hs) throws Exception
	{
		Vector st = tokenize(value);
		String expr = (String)st.get(0);
		String position = database.evaluateFormula(hs,(String)st.get(1));
		String direction = database.evaluateFormula(hs,(String)st.get(2));

		int myx = (int)Float.parseFloat(position);

		int barno = Integer.parseInt((String)database.globalHashMap.get(DataBaseInMemory.BARNO));
		int noofbarns = Integer.parseInt((String)database.globalHashMap.get(DataBaseInMemory.NOOFBARS));

		int bars = 0;
		float total = 0;
		if(direction.equalsIgnoreCase("B"))
		{
			for(int i = barno ; i >= 0 && bars < myx ; i-- )
			{
				HashMap myhs = (HashMap)database.dataTable.get(i);
				String retval = database.evaluateFormula(myhs, expr);
				float f = Float.parseFloat(retval);
				total = total + f;
				bars++;
			}
			return total+"";
		
			
		}
		else if (direction.equalsIgnoreCase("A"))
		{
			for(int i = barno ; i < noofbarns && bars < myx ; i++ )
			{
				HashMap myhs = (HashMap)database.dataTable.get(i);
				String retval = database.evaluateFormula(myhs, expr);
				float f = Float.parseFloat(retval);
				total = total + f;
				bars++;
			}
			return total+"";
			
		}
		return "";
	}

	private String addtoglobal(String value, HashMap hs) throws Exception
	{
		Vector st = tokenize(value);
		String varname = (String)st.get(0);
		varname = Utility.replaceString(varname, "\"", "").trim();
		String varvalue = (String)st.get(1);
		varvalue = database.evaluateFormula(hs, varvalue);
		database.globalHashMap.put(varname, varvalue);
		return "";
	}
	public String intvalue(String value, HashMap hs) throws Exception 
	{
		Vector st = tokenize(value);
			String expr = (String)st.get(0);
		String output = database.evaluateFormula(hs, expr);
		float f = Float.parseFloat(output.toString());
		int i = (int)f;		 
		return i+"";
	}
	public String ceiling(String value, HashMap hs) throws Exception 
	{
		Vector st = tokenize(value);
			String expr = (String)st.get(0);
		String output = database.evaluateFormula(hs, expr);
		float f = Float.parseFloat(output.toString());
		int i = (int)Math.ceil(f);		 
		return i+"";
	}
	public String floor(String value, HashMap hs) throws Exception 
	{
		Vector st = tokenize(value);
		
		String expr =(String) st.get(0);
		String output = database.evaluateFormula(hs, expr);
		float f = Float.parseFloat(output.toString());
		int i = (int)Math.floor(f);		 
		return i+"";
	}
	public String log(String value, HashMap hs) throws Exception 
	{
		Vector st = tokenize(value);
		String expr = (String)st.get(0);
		String output = database.evaluateFormula(hs, expr);
		float f = Float.parseFloat(output.toString());
		
		return Math.log(f)+"";
	}
	public String round(String value, HashMap hs) throws Exception 
	{
		Vector st = tokenize(value);
		String expr = (String)st.get(0);
		String output = database.evaluateFormula(hs, expr);
		float f = Float.parseFloat(output.toString());
		
		return Math.round(f)+"";
	}
	public String floatAtTwo(String value, HashMap hs) throws Exception 
	{
		Vector st = tokenize(value);
		String expr = (String)st.get(0);
		String output = database.evaluateFormula(hs, expr);
		String s = Utility.floatDataAtTwoPrecisionStr(output.toString());
		
		return s+"";
	}
	
	public String floatprecision(String value, HashMap hs) throws Exception 
	{
		Vector st = tokenize(value);
		String expr = (String)st.get(0);
		String output = database.evaluateFormula(hs, expr);
		float f = Float.parseFloat(output.toString());
		
		String precStr = (String)st.get(1);
		String retVal="";
		if(precStr.equalsIgnoreCase("1"))
		{
			retVal = Utility.floatDataAtOnePrecision(f)+"";

		}
		else if(precStr.equalsIgnoreCase("2"))
		{
			retVal = Utility.floatDataAtTwoPrecision(f)+"";
			
		}
 		return retVal + "";
	}
	public String echo(String value,HashMap hs) throws Exception
	{
		Vector st = tokenize(value);
		String expr = (String)st.get(0);
	
		String output = database.evaluateFormula(hs, expr);
		String s = (String)hs.get(output);
		if(s==null)
		{
			return output;
		}
		else
		{
			return s;
		}
	}
	public String var(String value,HashMap hs) throws Exception
		{
		Vector st = tokenize(value);
		String expr = (String)st.get(0);
	
		String output = database.evaluateFormula(hs, expr);
			return output;
		}
	public  String plot(String keyword,String value,HashMap hs) throws Exception
	{	
		Vector st = tokenize(value);
		String expr = (String)st.get(0);
	
		String color = (String)st.get(1);
		
		String output = database.evaluateFormula(hs, expr);
		color = database.evaluateFormula(hs, color);
		
		if(keyword.equalsIgnoreCase("plot1"))
		{
			hs.put(MainGraphComponent.PLOT1, output);
			hs.put(MainGraphComponent.COLOR1, color);
			
			
		}
		else if(keyword.equalsIgnoreCase("plot2"))
		{
			hs.put(MainGraphComponent.PLOT2, output);
			hs.put(MainGraphComponent.COLOR2, color);
			
		}
		else if(keyword.equalsIgnoreCase("plot3"))
		{
			hs.put(MainGraphComponent.PLOT3, output);
			hs.put(MainGraphComponent.COLOR3, color);
	
		}
		return "";
	}
	public  String plotmain(String keyword,String value,HashMap hs) throws Exception
	{	
		Vector st = tokenize(value);
		String expr = (String)st.get(0);
	
		String color = (String)st.get(1);
		
		String output = database.evaluateFormula(hs, expr);
		color = database.evaluateFormula(hs, color);
		
		if(keyword.equalsIgnoreCase("plotmain1"))
		{
			hs.put(PLOTMAIN1, output);
			hs.put(COLORMAIN1, color);
			
			
		}
		else if(keyword.equalsIgnoreCase("plotmain2"))
		{
			hs.put(PLOTMAIN2, output);
			hs.put(COLORMAIN2, color);
			
		}
		else if(keyword.equalsIgnoreCase("plotmain3"))
		{
			hs.put(PLOTMAIN3, output);
			hs.put(COLORMAIN3, color);
	
		}
		return "";
	}


	public  String plotcomponent(String keyword,String value,HashMap hs) throws Exception
		{	
		Vector st = tokenize(value);
		String expr = (String)st.get(0);

		String output = database.evaluateFormula(hs, expr);
			
			String color = (String)st.get(1);
			color = database.evaluateFormula(hs, color);

			String text = (String)st.get(2);
			text = database.evaluateFormula(hs, text);
			
			String size = (String)st.get(3);
			size = database.evaluateFormula(hs, size);

			HashMap temphs = new HashMap();
			temphs.put(NAME,keyword);
			temphs.put(COLOR,color);
			temphs.put(TEXT,text);
			temphs.put(VALUE,output);
			temphs.put(SIZE,size);

			Vector v = (Vector)hs.get(MainGraphComponent.OTHERCOMPONENT);
			if(v==null)
				v = new Vector();
			v.add(temphs);
			hs.put(MainGraphComponent.OTHERCOMPONENT, v);
			return "";

		}
	
	public static String NAME="NAME";
	public static String COLOR="COLOR";
	public static String VALUE="VALUE";
	public static String VALUE1="VALUE1";
	public static String TEXT="TEXT";
	public static String SIZE="SIZE";
	
	
	public  String plotcomponentline(String keyword,String value,HashMap hs) throws Exception
	{	
		Vector st = tokenize(value);
		String expr = (String)st.get(0);
		String output = database.evaluateFormula(hs, expr);

		String expr1 = (String)st.get(1);
		String output1 = database.evaluateFormula(hs, expr1);

		
		String color = (String)st.get(2);
		color = database.evaluateFormula(hs, color);


		HashMap temphs = new HashMap();
		temphs.put(NAME,keyword);
		temphs.put(COLOR,color);
		temphs.put(VALUE,output);
		temphs.put(VALUE1,output1);
		Vector v = (Vector)hs.get(MainGraphComponent.OTHERCOMPONENT);
		if(v==null)
			v = new Vector();
		v.add(temphs);
		hs.put(MainGraphComponent.OTHERCOMPONENT, v);
		return "";

	}
	public static String LINEXY="LINEXY";
	public static String X1="X1";
	public static String Y1="Y1";
	public static String X2="X2";
	public static String Y2="Y2";
	
	public  String plotcomponentlinexy(String keyword,String value,HashMap hs) throws Exception
	{	
		Vector st = tokenize(value);
		String expr = (String)st.get(0);
		String x1 = database.evaluateFormula(hs, expr);

		expr = (String)st.get(1);
		String y1 = database.evaluateFormula(hs, expr);

		expr = (String)st.get(2);
		String x2 = database.evaluateFormula(hs, expr);

		expr = (String)st.get(3);
		String y2 = database.evaluateFormula(hs, expr);

		
		String color = (String)st.get(4);
		color = database.evaluateFormula(hs, color);


		HashMap temphs = new HashMap();
		temphs.put(NAME,LINEXY);
		temphs.put(COLOR,color);
		temphs.put(X1,x1);
		temphs.put(Y1,y1);
		temphs.put(X2,x2);
		temphs.put(Y2,y2);
		
		Vector v = (Vector)hs.get(MainGraphComponent.OTHERCOMPONENT);
		if(v==null)
			v = new Vector();
		v.add(temphs);
		hs.put(MainGraphComponent.OTHERCOMPONENT, v);
		return "";

	}
	public  String plotpriceonleft(String value,HashMap hs) throws Exception
	{	
		Vector st = tokenize(value); 
			
		String expr = (String)st.get(0);
		String valueLeft = database.evaluateFormula(hs, expr);

		String expr1 = (String)st.get(1);
		String textLeft = database.evaluateFormula(hs, expr1);

		
		String color = (String)st.get(2);
		color = database.evaluateFormula(hs, color);


		HashMap temphs = new HashMap();
		temphs.put(NAME,textLeft);
		temphs.put(COLOR,color);
		temphs.put(VALUE,valueLeft);
		Vector v = (Vector)hs.get(Function.LEFTPRICE);
		if(v==null)
			v = new Vector();
		v.add(temphs);
		hs.put(Function.LEFTPRICE, v);
		return "";

	}
	public  String plotpriceonright(String value,HashMap hs) throws Exception
	{	
		Vector st = tokenize(value);

		String expr = (String)st.get(0);
		String valueLeft = database.evaluateFormula(hs, expr);
		
		String color = (String)st.get(1);
		color = database.evaluateFormula(hs, color);


		HashMap temphs = new HashMap();
		temphs.put(COLOR,color);
		temphs.put(VALUE,valueLeft);
		Vector v = (Vector)hs.get(Function.RIGHTPRICE);
		if(v==null)
			v = new Vector();
		v.add(temphs);
		hs.put(Function.RIGHTPRICE, v);
		return "";

	}

	public  String print(String value,HashMap hs) throws Exception
	{
//		// ("liralhsh="+litralHash);
		
		String s = (String)database.lexical.mappingHash.get(value);
//		// ("s="+s);
		
		if(s != null)
		{
			StockConstants.console.append(database.lexical.mappingHash.get(value));
		//	// (database.lexical.mappingHash.get(value));
		}
		else
		{
			StockConstants.console.append(database.evaluateFormula(hs, value));
		//	// (database.evaluateFormula(hs, value));
			
		}
			
		return "";
	}
	public  String println(String value,HashMap hs) throws Exception
	{
//		// ("liralhsh="+litralHash);
		
		String s = (String)database.lexical.mappingHash.get(value);
//		// ("s="+s);
		
		if(s != null)
		{
			StockConstants.console.append(database.lexical.mappingHash.get(value)+"\n");
	//		// ln(database.lexical.mappingHash.get(value));
		}
		else
		{
			StockConstants.console.append(database.evaluateFormula(hs, value)+"\n");
		//	// ln(database.evaluateFormula(hs, value));
			
		}
			
		return "";
	}
	public  String createVariable(String variableName,String value,HashMap hs) throws Exception
	{
		Vector st = tokenize(value);
		if(st.size()==0)
			return "";
		
		value = (String)st.get(0);
		String value2 = "";
		
		if(st.size() > 1)
		{
			value2=(String)st.get(1);
			
			value2 = database.evaluateFormula(hs, value2);
			value2 = "^"+value2;
		}
		
		String s = (String)database.lexical.mappingHash.get(value);
		
		if(s==null)
		{
			s = database.evaluateFormula(hs, value);
		}
		String s1 = (String)hs.get(variableName);
		if(s1==null)
		{
			
			hs.put(variableName,s+value2);
			
		}
		else
		{
			hs.put(variableName,s1+"|"+s+value2  );
			
		}
		return "";
	}
	public  String oneof(String value,HashMap hs)
	{
		String symbol = (String)hs.get("symbol");
		if(value.indexOf(symbol) != -1)
		{
			return "true";
		}
		else
		{
			return "false";
		}
	}
	public  String msg(String value,HashMap hs)
	{
		String s = (String)database.lexical.mappingHash.get(value);
		return s;
	}
	public  String percent(String value,HashMap hs) throws Exception
	{
		String s = database.evaluateFormula(hs, value);
		float f = Float.parseFloat(s);
		return (f*100) + "";
	}
	public  String sqrt(String value,HashMap hs) throws Exception
	{
		String s = database.evaluateFormula(hs, value);
		float f = Float.parseFloat(s);
		return Math.sqrt(f) + "";
	}
	public  String pow(String value,HashMap hs) throws Exception
	{
		Vector st = tokenize(value);

		String base = (String)st.get(0);
		String power = (String)st.get(1);

		String basestr = database.evaluateFormula(hs, base);
		String powerstr = database.evaluateFormula(hs, power);
		
		float basefloat = Float.parseFloat(basestr);
		float powerfloat = Float.parseFloat(powerstr);
		
		return Math.pow(basefloat,powerfloat) + "";
		
	}

	public  String abs(String value,HashMap hs) throws Exception
	{
		String s = database.evaluateFormula(hs, value);
		float f = Float.parseFloat(s);
		return Math.abs(f)+"";
	}
	public  String invert(String value,HashMap hs) throws Exception
	{
		String s = database.evaluateFormula(hs, value);
		if(s.equalsIgnoreCase("true"))
			return "FALSE";
		else
			return "TRUE";
	}
	public  String remove(String value,HashMap hs) throws Exception
	{
		hs.remove(value);
		return "";
	}
	
	public  String ifelse(String value,HashMap hs) throws Exception
	{
		StringTokenizer st = new StringTokenizer(value,",");
		String condition = st.nextToken().trim();
		String condLex = 	(String)database.lexical.mappingHash.get(condition);
		if(condLex != null)
			condition = condLex;

		String trueloop = st.nextToken().trim();
		condLex = 	(String)database.lexical.mappingHash.get(trueloop);
		if(condLex != null)
			trueloop = condLex;

		String falseloop = st.nextToken().trim();
		condLex = 	(String)database.lexical.mappingHash.get(falseloop);
		if(condLex != null)
			falseloop = condLex;

		String s = database.evaluateFormula(hs, condition);
		String result= null;
		if(s.equalsIgnoreCase("true"))
		{
			result = database.evaluateFormula(hs, trueloop);
		}
		else
		{
			result = database.evaluateFormula(hs, falseloop);
		}
		return result;
	}
	public  String ifcond(String value,HashMap hs) throws Exception
	{
		StringTokenizer st = new StringTokenizer(value,",");
		String condition = st.nextToken().trim();
		String condLex = 	(String)database.lexical.mappingHash.get(condition);
		if(condLex != null)
			condition = condLex;

		String trueloop = st.nextToken().trim();
		condLex = 	(String)database.lexical.mappingHash.get(trueloop);
		if(condLex != null)
			trueloop = condLex;


		String s = database.evaluateFormula(hs, condition);
		String result= "";
		if(s.equalsIgnoreCase("true"))
		{
			result = database.evaluateFormula(hs, trueloop);
		}
		return result;
	}
	public  String whileloop(String value,HashMap hs) throws Exception
		{
			StringTokenizer st = new StringTokenizer(value,",");
			String condition = st.nextToken().trim();
			String condLex = 	(String)database.lexical.mappingHash.get(condition);
			if(condLex != null)
				condition = condLex;

			String trueloop = st.nextToken().trim();
			condLex = 	(String)database.lexical.mappingHash.get(trueloop);
			if(condLex != null)
				trueloop = condLex;

			while(true)
			{
				String s = database.evaluateFormula(hs, condition);
				if(s.equalsIgnoreCase("true"))
				{
					database.evaluateFormula(hs, trueloop);
				}
				else
				{
					break;
				}
			
			}
			return "";
		}
		
	public static void main(String[] args)
	{
		Function fun = new Function();
//		fun.tokenize("abc");
	//	// ln(fun.tokenize("abc{gaga,sss},mahahah{ababa,ahahaha}"));
	}
//	public static HashMap litralHash ;
//	public static StringBuffer console = new StringBuffer();
//	public static StringBuffer alertconsole = new StringBuffer();
	 
	public static void secondPhaseIteration(String str)
	{
//if loop pattern is if(<expr>){<exprs>}
//		  litralHash = new HashMap();
		  char c = ' ';
		  int pos=0;
		  boolean status = false;
		  StringBuffer word = new StringBuffer();
		  StringBuffer litral = new StringBuffer();
		  int litpos=0;
		while (pos < str.length())
		{
			c = str.charAt(pos);
			pos++;
			if (c == '=' || c== '\n' || c== '{' || c=='}' || c == ' ' || c == ',' || c == '>' || c == '<' || c == '=' || c == '(' || c == ')' || c == '!' )
			{
				String myword = word.toString().trim();
				if(myword.equalsIgnoreCase("if"))
				{
										
				}
				word = new StringBuffer();
				continue;
			}
			word.append(c);
		}
				 
	}

}

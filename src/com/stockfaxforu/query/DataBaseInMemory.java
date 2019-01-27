/*
 * Created on Nov 16, 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.query;
import java.io.File;
import java.io.FileInputStream;
import java.io.RandomAccessFile;
import java.sql.DatabaseMetaData;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.Vector;

import com.stockfaxforu.component.MainGraphComponent;
import com.stockfaxforu.formulabuilder.MessageDiaglog;
import com.stockfaxforu.getolddataforu.loader.Loader;
import com.stockfaxforu.parser.LexicalAnalyzer;
import com.stockfaxforu.strategy.StrategyUtility;
import com.stockfaxforu.strategy.TechIndicatorLibrary;
import com.stockfaxforu.util.StockConstants;
import com.stockfaxforu.util.Utility;
/**
 * @author sdeopu1
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class DataBaseInMemory
{
	public static final String EXPOSE = "EXPOSE#";
	public static String[] keywords =
		{
			"select",
			"*",
			"from",
			"where",
			"like",
			"greater",
			"than",
			"less",
			"equals",
			"in",
			"between",
			"divide",
			"multiply",
			"addition",
			"subtract",
			"sort",
			"ascending",
			"descending" };
	public Vector allcondition = null;
	public Vector column = new Vector();
	public Vector table = new Vector();
	public HashMap globalHashMap = new HashMap();
	public Vector  dataTable = null;
	
	public  LexicalAnalyzer lexical= new LexicalAnalyzer();
	//	Vector condition = new Vector();
	public static void main(String[] args)
	{
//		DataBaseInMemory.INPUT_FILE_NAME = "C:\\Temp\\ATNINT_NS.csv";
//		DataBaseInMemory database = new DataBaseInMemory();
//		database.queryParser("select a1,a4,a8 from table where a8 > a4 * 1.05");
//		database.getDataForCondition(database.allcondition);
	
		Vector v = new Vector();
		HashMap hs = new HashMap();
		hs.put("OPEN[0]", "121.18");
		hs.put("CLOSE[0]", "115.18");
		hs.put("HIGH[0]", "130.18");
		hs.put("LOW[0]", "120.18");
		hs.put("symbol", "TCS");
		
		
		
		v.addElement(hs);
		String[] s=null;
		try		
		{
/*			DataBaseInMemory database = new DataBaseInMemory();
			FileInputStream file = new FileInputStream(new File("/igsworld/testing.txt"));
			byte[] b = new byte[2000];
			int x = file.read(b);
			String s1 = new String(b,0,x).toUpperCase();
			database.evaluateFormulaMain(hs, s1);
*/
				String exp = "a:=b<=c";
				String varname = exp.substring(0,exp.indexOf(":=")  ).trim();
				String custIndQuery = exp.substring(exp.indexOf(":=") + 2).trim();
				}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
	//		e.printStackTrace();
		}
//		String[] s = DataBaseInMemory.evaluateExpression(v," ifelse{-1 > -2 , 3, 1}");
		
	
	}
	public String[] evaluateFormulaMain(Vector v,String formula)
	{
		
		String s1 = this.lexical.findConstantStrings(formula);
			
		String s[]=null;
		try
		{
			s = this.evaluateFormula(v, s1.toUpperCase());
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
		//	e.printStackTrace();
		}
		return s;
	}
	public static String NOOFBARS="NOOFBARS";
	public static String BARNO="BARNO";
//	public static String BARNO="BARNO";
	
	
	public Vector evaluateFormulaMainVector(Vector v,String formula)
	{
		
		String s1 = this.lexical.findConstantStrings(formula);
		this.dataTable = v;
		
		
		Vector retVec=null;
		try
		{
			retVec = this.evaluateFormulaVector(v, s1.toUpperCase());
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
	//		e.printStackTrace();
		}
		return retVec;
	}

	public String evaluateFormulaMain(HashMap hs,String formula)
	{
		String s1 = this.lexical.findConstantStrings(formula);
		
		String s=null;
		try
		{
			s = this.evaluateFormula(hs, s1.toUpperCase());
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
	//		e.printStackTrace();
		}
		return s;
	}
	public static String IGSTEMP="IGSTEMP";
	public  String[] evaluateFormula(Vector datavector1, String formula) throws Exception
	{
		//adding common variable to global hashmap
		this.dataTable = datavector1;
		
		formula = formula.toUpperCase();
		formula = this.lexical.findConstantStrings(formula);
		
		try
		{
			formula = lexical.parseFile(formula);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
		//	e.printStackTrace();
			throw e;
		}
		StringTokenizer st = new StringTokenizer(formula,"\n");
		Vector varName = new Vector();
		Vector express = new Vector();
		
		while(st.hasMoreTokens())
		{
			String exp = st.nextToken();
			if(exp.trim().equalsIgnoreCase(""))
				continue;
			if(exp.indexOf(":=") == -1)
			{
				varName.add(IGSTEMP);
				express.add(exp);
				continue;
			}
			String varname = exp.substring(0,exp.indexOf(":=")  ).trim();
			String custIndQuery = exp.substring(exp.indexOf(":=") + 2).trim();
			varName.add(varname);
			express.add(custIndQuery);
		}	
		String[] returnStr = new String[datavector1.size()];
		globalHashMap.put(NOOFBARS, datavector1.size()+"");

		for (int i = 0; i < datavector1.size(); i++)
		{
			try
			{
				//adding common variable to global hashmap
				globalHashMap.put(BARNO,i+"");

				HashMap hs1 = (HashMap) datavector1.elementAt(i);
				String temp="";
				for(int j=0;j<express.size();j++)
				{	
					Vector cond = DataBaseInMemory.getAllCondition((String)express.get(j));
					
					Vector valvec = this.convertVectorIntoValues(hs1, cond);
					Vector infix = DataBaseInMemory.convertIntoInFix(valvec);
					temp = DataBaseInMemory.evaluateExpress(infix);
					String vartemp = convertVarible((String)varName.get(j),hs1);
					hs1.put(vartemp,temp);
				
				}
				hs1.put(IGSTEMP, temp);
				returnStr[i] = temp;
				
			}
			catch(Exception e)
			{
				
			}
		}
		return returnStr;
		
	}
	public String convertVarible(String varName,HashMap hs)
	{
		Function f = new Function();
		if(varName.trim().startsWith("VAR{"))
		{
			return f.calculateFunction(varName, hs, this);
		}
		else
		{
			return varName;
		}
	}
	public  String evaluateFormula(HashMap hs1, String formula) throws Exception
	{
		formula = formula.toUpperCase();
		try
		{
			formula = lexical.parseFile(formula);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
	//		e.printStackTrace();
//			throw e;
		}

		StringTokenizer st = new StringTokenizer(formula,"\n");
		Vector varName = new Vector();
		Vector express = new Vector();
		
		while(st.hasMoreTokens())
		{
			String exp = st.nextToken();
			if(exp.indexOf(":=") == -1)
			{
				varName.add(IGSTEMP);
				express.add(exp);
				continue;
			}
			String varname = exp.substring(0,exp.indexOf(":=")  ).trim();
			String custIndQuery = exp.substring(exp.indexOf(":=") + 2).trim();
			varName.add(varname);
			express.add(custIndQuery);
		}	
		String temp="";
		for(int j=0;j<express.size();j++)
		{	
			Vector cond = DataBaseInMemory.getAllCondition((String)express.get(j));
				
			Vector valvec = this.convertVectorIntoValues(hs1, cond);
			Vector infix = DataBaseInMemory.convertIntoInFix(valvec);
			temp = DataBaseInMemory.evaluateExpress(infix);
			String vartemp = convertVarible((String)varName.get(j),hs1);
			hs1.put(vartemp,temp);
			
		}
		
		return temp;
		
	}
	TechIndicatorLibrary techindlibrary=null;
	Vector  dataVector=null;
	public  Vector evaluateFormulaVector(Vector datavector1, String formula) throws Exception
	{

		formula = formula.toUpperCase();
		try
		{
			formula = lexical.parseFile(formula);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
		//	e.printStackTrace();
			throw e;
		}

		StringTokenizer st = new StringTokenizer(formula,"\n");
		Vector varName = new Vector();
		Vector express = new Vector();
		
		while(st.hasMoreTokens())
		{
			String exp = st.nextToken();
			if(exp.indexOf(":=") == -1)
			{
				varName.add(IGSTEMP);
				express.add(exp);
				continue;
			}
			String varname = exp.substring(0,exp.indexOf(":=")  ).trim();
			String custIndQuery = exp.substring(exp.indexOf(":=") + 2).trim();
			varName.add(varname);
			express.add(custIndQuery);
		}	
		//adding common variable to global hashmap
		globalHashMap.put(NOOFBARS, datavector1.size()+"");

		String[] returnStr = new String[datavector1.size()];
		for (int i = 0; i < datavector1.size(); i++)
		{
			try
			{
				//adding common variable to global hashmap
				globalHashMap.put(BARNO,i+"");
				
				HashMap hs1 = (HashMap) datavector1.elementAt(i);
				String symbol = (String)hs1.get(MainGraphComponent.Symbol);
				String temp="";
				for(int j=0;j<express.size();j++)
				{	
					Vector cond = DataBaseInMemory.getAllCondition((String)express.get(j));
						
					Vector valvec = this.convertVectorIntoValues(hs1, cond);
					Vector infix = DataBaseInMemory.convertIntoInFix(valvec);
					temp = DataBaseInMemory.evaluateExpress(infix);
					String vartemp = convertVarible((String)varName.get(j),hs1);
					hs1.put(vartemp,temp);
					
				}
				hs1.put(IGSTEMP, temp);

				returnStr[i] = temp;
			}
			catch(Exception e)
			{
				
			}
		}
		return datavector1;
		
	}
	
	public static void executeQuery(String query)
		{
			DataBaseInMemory database = new DataBaseInMemory();
			database.queryParser(query);
			database.getDataForCondition(database.allcondition);
		}
		public  Vector executeQuery(String query, Vector dataVector) throws Exception
		{
			query = lexical.findConstantStrings(query);
			String sortby = "SORTBY";
//			DataBaseInMemory database = new DataBaseInMemory();
			String queryMain = "";
			String querySort = "";
			if (query.indexOf(sortby) == -1)
			{
				this.queryParser(query);
				return this.getDataForCondition(this.allcondition, dataVector);
			}
			else
			{
				queryMain = query.substring(0, query.indexOf(sortby) - 1).trim();
				querySort = query.substring(query.indexOf(sortby) + sortby.length() + 1).trim();
				this.queryParser(queryMain);
				Vector v = this.getDataForCondition(this.allcondition, dataVector);
				v = sortBy(v, querySort);
				return v;
			}
		}
	
	public  String[] evaluateExpression(Vector datavector1, String custIndQuery) throws Exception
	{
		//adding common variable to global hashmap
		globalHashMap.put(NOOFBARS, datavector1.size()+"");

		Vector cond = DataBaseInMemory.getAllCondition(custIndQuery);

		String[] returnStr = new String[datavector1.size()];
		for (int i = 0; i < datavector1.size(); i++)
		{
			//adding common variable to global hashmap
			globalHashMap.put(BARNO, datavector1.size());

			HashMap hs1 = (HashMap) datavector1.elementAt(i);
			Vector valvec = this.convertVectorIntoValues(hs1, cond);
			Vector infix = DataBaseInMemory.convertIntoInFix(valvec);
			returnStr[i] = DataBaseInMemory.evaluateExpress(infix);
		}
		return returnStr;

	}

	public  String evaluateExpression(HashMap hs1, String custIndQuery) throws Exception
	{
		Vector cond = DataBaseInMemory.getAllCondition(custIndQuery);
		Vector valvec = this.convertVectorIntoValues(hs1, cond);
		Vector infix = DataBaseInMemory.convertIntoInFix(valvec);
		String returnStr = DataBaseInMemory.evaluateExpress(infix);
		return returnStr;

	}

	public static String sortColumn="SORTCOLUMN";
	public  Vector sortBy(Vector v, String sortQuery) throws Exception
	{
		HashMap hs1 = null;
		HashMap hs2 = null;
		if (v == null || v.size() == 0)
		{
			return v;
		}
		HashMap hs = (HashMap) v.elementAt(0);
		if (hs.get(sortQuery) == null)
		{
			Vector cond = getAllCondition(sortQuery);
			for (int i = 0; i < v.size(); i++)
			{
				try
				{
					hs1 = (HashMap) v.elementAt(i);
					Vector valvec = convertVectorIntoValues(hs1, cond);
					Vector infix = convertIntoInFix(valvec);
					String s = evaluateExpress(infix);
					
					hs1.put(sortColumn, s);
					
				}
				catch(Exception e)
				{
				//	e.printStackTrace();
				}
			}
			
		}
		else
		{
			for (int i = 0; i < v.size(); i++)
			{
				hs1 = (HashMap) v.elementAt(i);
				String s = (String)hs1.get(sortQuery);
				hs1.put(sortColumn, s);
			}
		}

		for(int i=0;i<v.size();i++)
		{
			hs1 = (HashMap) v.elementAt(i);
			String s = (String)hs1.get(sortColumn);
			if(s==null || s.equalsIgnoreCase(""))
			{
				v.remove(hs1);						
				i--;
			}
		}
		for(int i=0;i<v.size();i++)
		{
			
			for(int j=i+1;j<v.size();j++)
			{
				hs1 = (HashMap) v.elementAt(i);
				String s = (String)hs1.get(sortColumn);

				hs2 = (HashMap) v.elementAt(j);
				String s1 = (String)hs2.get(sortColumn);
				if(s==null || s1==null)
					continue;
				try
				{
					float f = Float.parseFloat(s);
					float f1 = Float.parseFloat(s1);
					if(f > f1)
					{
						HashMap temp = new HashMap();
						copyHashTable(hs1, temp);	
						copyHashTable(hs2, hs1);	
						copyHashTable(temp, hs2);
					}
						
				}
				catch(Exception e)
				{
					if(s.compareTo(s1) > 0)
					{
						HashMap temp = hs1;
						v.remove(i);
						v.insertElementAt(hs2,i);
						v.remove(j);
						v.insertElementAt(hs1, j);
					}

				}
			}
		}
		return v;
	}
	public static void copyHashTable(HashMap toHash,HashMap fromHash)
	{
		Iterator e = toHash.keySet().iterator();
		while(e.hasNext())
		{
			Object o = e.next();
			fromHash.put(o,toHash.get(o));
		}
	}
	public static Vector executeQueryInStr(String query, Vector dataVector) throws Exception
	{
		DataBaseInMemory database = new DataBaseInMemory();
		database.queryParser(query);
		return database.getDataForConditionInStr(database.allcondition, dataVector);
	}
	public static String[][] getResultOfQuery(String query)
	{
		Vector v = getAllCondition(query);
		v = convertIntoInFix(v);
		//	StockConstants.querydata = getDataForConditionAsString(v); 
		return null;
	}
	public static String operator = ":+:-:and:or:<:>:=:==:like:*:/:<>:<=:>=:".toUpperCase();

	public static String evaluateExpress(Vector expression) throws Exception
	{
		String retResult = "";
		Stack stack = new Stack();
		int i = 0;
		while (i < expression.size())
		{
			String operand = ((String) expression.elementAt(i++)).toUpperCase();
			if (operator.indexOf(":" + operand.toUpperCase() + ":") == -1)
			{
				stack.push(operand);
			}
			else
			{
				String truefalse = "TRUEFALSE";
				String x_str = (String) stack.pop();
				String y_str = (String) stack.pop();
				boolean boolean_x = false;
				boolean boolean_y = false;
//				boolean booleanOpe = false;
				int operationType = 1;
				int STROPR = 0;
				int NUMOPR = 1;
				int BINOPR = 2;
				
				
				float x = 0;
				float y = 0;
				if (truefalse.indexOf(x_str) != -1 || truefalse.indexOf(y_str) != -1)
				{
					if (x_str.equalsIgnoreCase("TRUE"))
					{
						boolean_x = true;
					}
					else
					{
						boolean_x = false;
					}
					if (y_str.equalsIgnoreCase("TRUE"))
					{
						boolean_y = true;
					}
					else
					{
						boolean_y = false;
					}
					operationType = BINOPR;
				}
				else
				{
					try
					{
						x = Float.parseFloat(x_str);
						y = Float.parseFloat(y_str);
						operationType = NUMOPR;

					}
					catch (Exception e)
					{

						operationType = STROPR;
					}
				}
				float result = 0;
				String resultStr = "";
				boolean resultboolean = false;
				if(operand.equalsIgnoreCase(">=") || operand.equalsIgnoreCase("<=") || operand.equalsIgnoreCase(">") || operand.equalsIgnoreCase("<") || operand.equalsIgnoreCase("-") || operand.equalsIgnoreCase("*") || operand.equalsIgnoreCase("/"))
				{
					if((operationType != NUMOPR) && (operationType != BINOPR))
					{
						throw new Exception("Invalid Expression only numberic operation be done on operator " + operand + " for string " + x_str + " and " + y_str);
						
					}					
				}
				
				if (operand.equalsIgnoreCase("+"))
				{
					if(operationType==NUMOPR)
					{
						result = x + y;
		//				result = Utility.floatDataAtTwoPrecision(result);
						resultStr = result + "";
						
					}
					else 
					{
						resultStr = y_str + x_str ;
					}
				}
				else if (operand.equalsIgnoreCase("-"))
				{
						result = y - x;
			//			result = Utility.floatDataAtTwoPrecision(result);
	
						resultStr = result + "";
				}
				else if (operand.equalsIgnoreCase("*"))
				{
	
					result = x * y;
//					result = Utility.floatDataAtTwoPrecision(result);
					result = result;

					resultStr = result + "";
				}
				else if (operand.equalsIgnoreCase("/"))
				{
					if(x==0)
						return "999999999999"; 
					result = y / x;
//					result = Utility.floatDataAtTwoPrecision(result);

					resultStr = result + "";
				}
				else if (operand.equalsIgnoreCase("<"))
				{
					resultboolean = (y < x);
					resultStr = (resultboolean + "").toUpperCase();
				}
				else if (operand.equalsIgnoreCase(">"))
				{
					resultboolean = (y > x);
					resultStr = (resultboolean + "").toUpperCase();
				}
				else if (operand.equalsIgnoreCase(">="))
				{
					resultboolean = (y >= x);
					resultStr = (resultboolean + "").toUpperCase();
				}
				else if (operand.equalsIgnoreCase("<="))
				{
					resultboolean = (y <= x);
					resultStr = (resultboolean + "").toUpperCase();
				}

				else if (operand.equalsIgnoreCase("=="))
				{
					if(operationType== BINOPR)
					{
						resultboolean = ( boolean_x == boolean_y );
						resultStr = (resultboolean + "").toUpperCase();

					}
					else if (operationType== NUMOPR)
					{
						resultboolean = (y == x);
						resultStr = (resultboolean + "").toUpperCase();
						
					}
					else
					{
						if(x_str==null) x_str="";
						if(y_str==null) y_str="";
						resultboolean = x_str.equalsIgnoreCase(y_str);
						resultStr = (resultboolean + "").toUpperCase();
						
					}
				}
				if (operand.equalsIgnoreCase("<>"))
				{
					if(operationType== BINOPR)
					{
						resultboolean = !( boolean_x == boolean_y );
						resultStr = (resultboolean + "").toUpperCase();

					}
					else if (operationType== NUMOPR)
					{
						resultboolean = !(y == x);
						resultStr = (resultboolean + "").toUpperCase();
						
					}
					else
					{
						if(x_str==null) x_str="";
						if(y_str==null) y_str="";
						resultboolean = !(x_str.equalsIgnoreCase(y_str));
						resultStr = (resultboolean + "").toUpperCase();
						
					}				
				}

				else if (operand.equals("AND"))
				{
					resultboolean = (boolean_x && boolean_y);
					resultStr = (resultboolean + "").toUpperCase();
				}
				else if (operand.equalsIgnoreCase("OR"))
				{
					resultboolean = (boolean_x || boolean_y);
					resultStr = (resultboolean + "").toUpperCase();
				}
	
				else if (operand.equalsIgnoreCase("like"))
				{
					if (y_str.startsWith(x_str))
						resultboolean = true;
					else
						resultboolean = false;
					;
					resultStr = (resultboolean + "").toUpperCase();
				}
				
				stack.push(resultStr + "");
			}
		}
		retResult = (String) stack.pop();
		return retResult;
	}
	public void queryParser(String query)
	{
		int i = 0;
		int state = 0;
		StringBuffer word = new StringBuffer();
		boolean worldstart = true;
		while (i < query.length())
		{
			char c = query.charAt(i);
			i++;
			if (c == ' ' || c == ',' || c == '>' || c == '<' || c == '=' || c == '(' || c == ')' || c == '!')
			{
				if (state == 0)
				{
					if (word.toString().equalsIgnoreCase("select"))
					{
						state++;
						word = new StringBuffer();
						continue;
					}
					else
					{
						return;
					}
				}
				else if (state == 1)
				{
					if (word.toString().equalsIgnoreCase("from"))
					{
						if (column.size() == 0)
						{
//							MessageDiaglog msg = new MessageDiaglog("In Correct Query column names missing");

							return;
						}
						else
						{
							state++;
							word = new StringBuffer();
							continue;
						}
					}
					else
					{
						String myword = word.toString().trim();
						if (!myword.equals(""))
							column.addElement(myword);
						word = new StringBuffer();
						continue;
					}
				}
				if (state == 2)
				{
					if (word.toString().equalsIgnoreCase("where"))
					{
						if (table.size() == 0)
						{
//							MessageDiaglog msg = new MessageDiaglog("In Correct Query table names missing");
							return;
						}
						else
						{
							state++;
							word = new StringBuffer();
							allcondition = getAllCondition(query.substring(i));
//							convertIntoInFix(allcondition);
							break;
							//							continue;
						}
					}
					else
					{
						table.addElement(word.toString());
						word = new StringBuffer();
						continue;
					}
				}
				if (state == 3)
				{
					break;
				}
			}
			word.append(c);
		}
	}
	public static Vector getAllCondition(String query)
	{
		Vector token = new Vector();
		int i = 0;
		int state = 0;
		StringBuffer sb = new StringBuffer();
		char c = ' ';
		while (i < query.length())
		{
			c = query.charAt(i);
			i++;
			if (c == ' ')
			{
				if (sb.toString().trim().length() != 0)
				{
					token.addElement(sb.toString().trim());
					sb = new StringBuffer();
					continue;
				}
				else
				{
					continue;
				}
			}
			else if(c == '{')
			{
				int count = 1;
				while(true)
				{
					sb.append(c);
					c = query.charAt(i);
					i++;
					if(c == '{')
					{
						count++;
					}
					else if(c == '}')
					{
						count--;
					}
					if(count == 0 || i >= query.length())
					{
						sb.append(c);
						if (sb.toString().trim().length() != 0)
						{
								
							token.addElement(sb.toString().trim());
						}
						sb = new StringBuffer();
						break;
					}
					
				}
				continue;
				
			}
			else if(c == '[')
			{
				int count = 1;
				while(true)
				{
					sb.append(c);
					c = query.charAt(i);
					i++;
					if(c == '[')
					{
						count++;
					}
					else if(c == ']')
					{
						count--;
					}
					if(count == 0 || i >= query.length())
					{
						sb.append(c);
						String mytoken = sb.toString().trim();
						mytoken = Utility.replaceString(mytoken, " ", "");
						if (mytoken.length() != 0)
						{
							token.addElement(mytoken.trim());
						}	
						sb = new StringBuffer();
						break;
					}
					
				}
				continue;
				
			}

			sb.append(c);
		}
		if (sb != null && sb.toString().trim().length() != 0)
			token.addElement(sb.toString().trim());
		return token;
	}
	public static Vector convertIntoInFix(Vector input)
	{
		HashMap token = new HashMap();
		token.put("(", "0");
		token.put("AND", "1");
		token.put("OR", "1");
		token.put("<", "2");
		token.put(">", "2");
		token.put("==", "2");
		token.put("<=", "2");
		token.put(">=", "2");
		token.put("<>", "2");

		token.put("LIKE", "3");
		token.put("+", "3");
		token.put("-", "3");
		token.put("*", "4");
		token.put("/", "4");
		Stack stack = new Stack();
		Vector output = new Vector();
		int i = 0;
		while (i < input.size())
		{
			String mytoken = (String) input.elementAt(i++);
			String pref = (String) token.get(mytoken);
			if (pref == null && !(mytoken.equals(")") || mytoken.equals("(")))
			{
				output.addElement(mytoken);
				//				i++;
			}
			else
			{
				if (stack.size() == 0)
				{
					stack.push(mytoken);
					continue;
				}
				else if (mytoken.equals("("))
				{
					stack.push(mytoken);
				}
				else if (mytoken.equals(")"))
				{
					while (true)
					{
						String temptoken = (String) stack.pop();
						if (temptoken.equals("("))
							break;
						output.addElement(temptoken);
					}
				}
				else
				{
					int prefint = Integer.parseInt(pref);
					String oldtoken = (String) stack.pop();
					int oldpref = Integer.parseInt((String) token.get(oldtoken));
					if (prefint > oldpref)
					{
						stack.push(oldtoken);
						stack.push(mytoken);
					}
					else
					{
						stack.push(oldtoken);
						while (true)
						{
							if (stack.size() == 0)
							{
								stack.push(mytoken);
								break;
							}
							oldtoken = (String) stack.pop();
							oldpref = Integer.parseInt((String) token.get(oldtoken));
							if (oldpref >= prefint)
							{
								output.addElement(oldtoken);
							}
							else
							{
								stack.push(oldtoken);
								stack.push(mytoken);
								break;
							}
						}
					}
				}
			}
		}
		while (stack.size() != 0)
		{
			output.addElement(stack.pop());
		}
		return output;
	}

//notused in this file
	public static int checkKeyWord(String s)
	{
		String[] token = { "AND", "OR", "LIKE", "<", ">", "=" ,};
		for (int i = 0; i < token.length; i++)
		{
			if (token[i].equalsIgnoreCase(s))
				return i;
		}
		return -1;
	}

		public static Vector data = null;
	public static String INPUT_FILE_NAME = "";
	public  Vector convertVectorIntoValues(HashMap hs, Vector expression) throws Exception
	{
		Vector output = new Vector();
		for (int i = 0; i < expression.size(); i++)
		{
			String s = (String) expression.elementAt(i);
			s = s.trim();
		//	// ln(hs);
			if(operator.indexOf(":"+s+":") != -1)
			{
				output.add(s);
			}
			else if (globalHashMap.get(s) != null)
			{
				String val = (String) globalHashMap.get(s);
				output.addElement(val);
				
			}
			else if (hs.get(s) != null)
			{
				String val = (String) hs.get(s);
				output.addElement(val);
			}
			else
			{
//added to get formula from file and evealuate dynamically
//				String formula = StrategyUtility.getFormula(s);
				String formula = null;
				
				if(formula == null)				
				{	String tmps = (String)lexical.mappingHash.get(s);
					if(tmps != null)
					{
						
						output.addElement(tmps);
					}
					else
					{
						Function function = new Function();
						String s1 = function.calculateFunction(s, hs,this);
						output.addElement(s1);
					}
				}
				else
				{
					DataBaseInMemory databasetmp = new DataBaseInMemory();
					databasetmp.globalHashMap = this.globalHashMap;
					databasetmp.dataTable = this.dataTable;
					String value = databasetmp.evaluateFormulaMain(hs, formula.trim());
					output.addElement(value);					
				}
			}
		}
		return output;
	}
	public Vector getDataForCondition(Vector condition, Vector dataVector)
	{
		Vector retVector = new Vector();
		for (int i = 0; i < dataVector.size(); i++)
		{
			try
			{
				HashMap hs = (HashMap) dataVector.elementAt(i);
				if (condition == null || condition.size() == 0)
				{
					HashMap outhash = new HashMap();
					StringBuffer outstr = new StringBuffer();
					for (int y = 0; y < this.column.size(); y++)
					{
						String val = (String) hs.get((String) column.elementAt(y));
						if (val == null)
							val = "";
						outhash.put((String) column.elementAt(y), (String) hs.get((String) column.elementAt(y)));
					}
					retVector.addElement(outhash);
				}
				else
				{
					Vector v = convertVectorIntoValues(hs, condition);
					v = convertIntoInFix(v);
					String s = evaluateExpress(v);
					if (s.equalsIgnoreCase("true"))
					{
						HashMap outhash = new HashMap();
						StringBuffer outstr = new StringBuffer();
						HashMap outHash = new HashMap();
						for (int y = 0; y < this.column.size(); y++)
						{
							String val = (String) hs.get((String) column.elementAt(y));
							if (val == null)
								val = "";
							outHash.put((String) column.elementAt(y), (String) hs.get((String) column.elementAt(y)));
						}
						retVector.addElement(outHash);
					}
				}
				
			}
			catch(Exception e)
			{
			}
		}
		return retVector;
	}
	public Vector getDataForConditionInStr(Vector condition, Vector dataVector) throws Exception
	{
		Vector retVector = new Vector();
		for (int i = 0; i < dataVector.size(); i++)
		{
			HashMap hs = (HashMap) dataVector.elementAt(i);
			if (condition == null || condition.size() == 0)
			{
				StringBuffer outstr = new StringBuffer();
				for (int y = 0; y < this.column.size(); y++)
				{
					String val = (String) hs.get((String) column.elementAt(y));
					if (val == null)
						val = "";
					outstr.append(val + ",");
				}
				if (outstr.toString().trim().length() != 0)
				{
					outstr.deleteCharAt(outstr.length() - 1);
				}
				retVector.addElement(outstr.toString());
			}
			else
			{
				Vector v = convertVectorIntoValues(hs, condition);
				v = convertIntoInFix(v);
				String s = evaluateExpress(v);
				if (s.equalsIgnoreCase("true"))
				{
					HashMap outhash = new HashMap();
					StringBuffer outstr = new StringBuffer();
					HashMap outHash = new HashMap();
					for (int y = 0; y < this.column.size(); y++)
					{
						String val = (String) hs.get((String) column.elementAt(y));
						if (val == null)
							val = "";
						outstr.append(val + ",");
					}
					if (outstr.toString().trim().length() != 0)
					{
						outstr.deleteCharAt(outstr.length() - 1);
					}
					retVector.addElement(outstr.toString());
				}
			}
		}
		return retVector;
	}
	public void getDataForCondition(Vector condition)
	{
		RandomAccessFile input = null;
		RandomAccessFile output = null;
		try
		{
			input = new RandomAccessFile(DataBaseInMemory.INPUT_FILE_NAME, "r");
			File f = new File(StockConstants.INSTALL_DIR);
			if (!f.exists())
				f.mkdirs();
			f = new File(StockConstants.INSTALL_DIR + "/" + "output.csv");
			if (f.exists())
				f.delete();
			output = new RandomAccessFile(StockConstants.INSTALL_DIR + "/" + "output.csv", "rw");
			String line = "";
			while ((line = input.readLine()) != null)
			{
				StringTokenizer st = new StringTokenizer(line, ",");
				int i = 1;
				HashMap hs = new HashMap();
				while (st.hasMoreTokens())
				{
					hs.put("a" + i + "", st.nextElement());
					i++;
				}
				if (condition == null || condition.size() == 0)
				{
					StringBuffer outstr = new StringBuffer();
					for (int y = 0; y < this.column.size(); y++)
					{
						String val = (String) hs.get((String) column.elementAt(y));
						if (val == null)
							val = "";
						outstr.append(val + ",");
					}
					if (outstr.toString().trim().length() != 0)
					{
						outstr.deleteCharAt(outstr.length() - 1);
					}
					outstr.append("\n");
					output.writeBytes(outstr.toString());
				}
				else
				{
					Vector v = convertVectorIntoValues(hs, condition);
					v = convertIntoInFix(v);
					String s = evaluateExpress(v);
					if (s.equalsIgnoreCase("true"))
					{
						HashMap outhash = new HashMap();
						StringBuffer outstr = new StringBuffer();
						for (int y = 0; y < this.column.size(); y++)
						{
							String val = (String) hs.get((String) column.elementAt(y));
							if (val == null)
								val = "";
							outstr.append(val + ",");
						}
						if (outstr.toString().trim().length() != 0)
						{
							outstr.deleteCharAt(outstr.length() - 1);
						}
						outstr.append("\n");
						output.writeBytes(outstr.toString());
					}
				}
			}
		}
		catch (Exception e)
		{
		}
		finally
		{
			try
			{
				input.close();
				output.close();
			}
			catch (Exception e)
			{
			}
		}
	}
}

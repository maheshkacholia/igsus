/*
 * Created on Apr 24, 2009
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.parser;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 * @author Mahesh
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class LexicalAnalyzer
{
	public String inputString;
	public String outputString;
	
	int currPos=0;
	int length=0;
	StringBuffer token;
	int state=0;
	char c=' ';
	public static void main(String args[])
	{
		try
		{
			LexicalAnalyzer lex = new LexicalAnalyzer();
			FileInputStream file = new FileInputStream(new File("/igsworld/testing.txt"));
			byte[] b = new byte[2000];
			int x = file.read(b);
			String s = new String(b,0,x);
//			// ln(s);
//			lex.inputString = "( ( a > b ) = true ) \n { a= b \n { b =c }\n c = d }";
//			lex.inputString = s;

//			// ln("ifloop="+lex.calculateIfLoop());
//			// ln("hashmap="+lex.mappingHash);
	//		// ln("parsing result=="+ lex.parseFile(s));

		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
	//		e.printStackTrace();
		}
	}
	public HashMap includeHash = new HashMap(); 
	public String getAllFunctions(String inputString ) throws Exception
	{
		this.inputString = inputString;
		currPos = 0;
	
		currPos = 0;
		StringBuffer output = new StringBuffer();
		while(currPos < inputString.length())
		{
			int pos = currPos;
			String temptoken = calculateToken();
			if(temptoken.equalsIgnoreCase("function"))
			{
				HashMap temp = calculateFunctionLoop();
				includeHash.put(temp.get("NAME"),temp.get("VALUE"));
			//	output.append(temp+"\n");
				skipWhiteSpace();
			
			}
			else
			{
				currPos = pos;
				String linevalue = readLine();
				if(!linevalue.trim().equals(""))
					output.append(linevalue+"\n");
			}
		}
		return output.toString();
	}
	public HashMap  calculateFunctionLoop() throws Exception
	{
		skipWhiteSpace();

		String functionname = calculateToken();
		skipWhiteSpace();

		String whileexpr = calculateExpr();
		HashMap hs = new HashMap();
		hs.put("NAME",functionname);
		hs.put("VALUE",whileexpr);
		return hs;
	}
	public  String findConstantStrings(String str)
	{
		char c = ' ';
		int pos=0;
		boolean status = false;
		StringBuffer output = new StringBuffer();
		StringBuffer litral = new StringBuffer();
		int litpos=0;
		boolean comment = false;
		boolean include = false;
		StringBuffer tempbuffer = new StringBuffer();
		
		try
		{
			str = getAllFunctions(str);
		}
		catch (Exception e)
		{
			
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		while(pos < str.length())
		{
			c = str.charAt(pos);
			tempbuffer.append(c);
			pos++;
			if(c=='\n' )
			{
				if(include==true)
				{
					StringTokenizer st = new StringTokenizer(tempbuffer.toString(),":" );
					String tokenName = st.nextToken().substring(1);
					String expr = st.nextToken().trim();
					includeHash.put(tokenName.trim(), expr + " ");


				}
				tempbuffer = new StringBuffer();

				if(comment==true || include == true )
				{
					comment=false;
					include=false;

					continue;
					
				}
			}
			if(tempbuffer.toString().trim().equals("@"))
			{
				comment = true;
				
			}
			else if(tempbuffer.toString().trim().equals("#"))
			{
				include= true;	
				
			}
			
			
			if(comment==true || include==true)
			{
				continue;
			}
			
			if(c=='"')
			{
				if(status==true)
				{
					litpos = mappingHash.size();
					mappingHash.put(myif+litpos+"", litral.toString());
					output.append(myif+litpos+"");	
					litpos++;
					litral = new StringBuffer();
					status = false;
					continue;
				}
				else
				{
					status=true;
					continue;
				}
			}
			if(status==true)
			{
				litral.append(c);
					
			}
			else
			{
				output.append(c);
				
			}
		}
//		// ln(output);
		return output.toString();
	}
	String validToken = "|/|*|(|)|==|=|<|>|<=|>=|<>|";
	String[] validTokenArray = {"/" ,"*","(",")","==","<",">","<=",">=","<>"};
	
	public void semanticAnalyzer(String inputStr) throws Exception
	{
		checkSemantics(inputStr);				
	}
	public void  checkSemantics(String line) throws Exception
	{
		StringBuffer retBuffer = new StringBuffer();
		Vector retVector = new Vector();
		int mypos=0;
		char mychar= ' ';
		int bracecount = 0;
		int bracketcount = 0;
		int parentsiscount = 0;
		int linenp=1;
		while(line.length() > mypos)
		{
			mychar = line.charAt(mypos);
			if(isWhiteSpace(mychar))
			{
				String temps = retBuffer.toString().trim(); 
				if(!temps.equals(""))
				{
					if (temps.equalsIgnoreCase("{") || temps.indexOf("{") != -1)
					{
						parentsiscount++;
						
					}
					if (temps.equalsIgnoreCase("}") || temps.indexOf("}") != -1)
					{
						parentsiscount--;
						if (parentsiscount < 0)
						{
							throw new Exception("} found before { on line no "+linenp);
						}
					}
					if (temps.equalsIgnoreCase("(") || temps.indexOf("(") != -1)
					{
						bracecount++;
					}
					if (temps.equalsIgnoreCase(")") || temps.indexOf(")") != -1)
					{
						
						bracecount--;
						if (bracecount < 0)
						{
							throw new Exception(") found before ( on line no "+linenp);
						}
					}
					if (temps.equalsIgnoreCase("[") || temps.indexOf("[") != -1)
					{
						bracketcount++;
					
					}
					if (temps.equalsIgnoreCase("]") || temps.indexOf("]") != -1)
					{
						bracketcount--;
						if (bracketcount < 0)
						{
							throw new Exception("] found before [ on line no "+linenp);
						}
					}

					if (validToken.indexOf(temps) == -1)
					{
						for(int i=0;i<validTokenArray.length;i++)
						{
							if (temps.indexOf(validTokenArray[i]) != -1)
							{
								 throw new Exception("operator found in token " + temps + " in line no " + linenp);
								 					
							}
						}
					}
					
				}
				retBuffer = new StringBuffer();
			}	
			else
			{
				retBuffer.append(mychar);
			}
			if(mychar=='\n')
			{
				if (bracecount != 0)
				{
					throw new Exception("( is not closed properly in line " + linenp);
					
				}
				if (bracketcount != 0)
				{
					throw new Exception("[ is not closed properly in line " + linenp);
	
				}
				retBuffer = new StringBuffer();
				linenp++;
				
			}

			mypos++;
		}
	}
	public void checkOperator(String token,int lineno) throws Exception
	{
		for(int i=0;i<validTokenArray.length;i++)
		{
			if(token.indexOf(validTokenArray[i]) != -1)
			{
				throw new Exception("Invalid character " + validTokenArray[i] + " found in token " + token + " in line " + lineno);
			}
		}
	}
	public String parseFile(String inputStr) throws Exception
	{
		inputString = inputStr;
		currPos = 0;
		StringBuffer output = new StringBuffer();
		while(currPos < inputString.length())
		{
			int pos = currPos;
			String temptoken = calculateToken();
			if(temptoken.equalsIgnoreCase("if"))
			{
				String temp = calculateIfLoop();
				output.append(temp+"\n");
				skipWhiteSpace();
			
			}
			else if(temptoken.equalsIgnoreCase("while"))
			{
				String temp = calculateWhileLoop();
				output.append(temp+"\n");
				skipWhiteSpace();
			}
			else
			{
				currPos = pos;
				String linevalue = readLine();
				if(!linevalue.trim().equals(""))
					output.append(linevalue+"\n");
			}
		}
//check if all statetement written correctly		
//		semanticAnalyzer(output.toString());
//		// ln("output======");
//		// ln(output);
		
		return output.toString();
	}
	public String readLine()
	{
		StringBuffer output = new StringBuffer();
		c = inputString.charAt(currPos);
		if(c=='\n')
		{
			currPos++;
			return "";
		}
//		output.append(c);
		while(c != '\n')
		{
			output.append(c);
			currPos++;
			if(currPos >= inputString.length())
				return output.toString();

			c = inputString.charAt(currPos);

		}
		return output.toString();
	}
	public HashMap mappingHash = new HashMap();
	public static String myif = "MYIF";

	public String calculateWhileLoop() throws Exception
	{

		skipWhiteSpace();

		String condexpr = calculateCondExpr();
		int val = mappingHash.size();
		String condStr = myif+val;
		mappingHash.put(condStr, condexpr);
		
		skipWhiteSpace();

		String whileexpr = calculateExpr();
		val = mappingHash.size();
		String whileStr = myif+val;
		mappingHash.put(whileStr, whileexpr);
		return "while{ "+condStr+ "," + whileStr + " }";
	}



	public String calculateIfLoop() throws Exception
	{

		skipWhiteSpace();

		String condexpr = calculateCondExpr();
		int val = mappingHash.size();
		String condStr = myif+val;
		mappingHash.put(condStr, condexpr);
		
		skipWhiteSpace();

		String ifexpr = calculateExpr();
		val = mappingHash.size();
		String ifStr = myif+val;
		mappingHash.put(ifStr, ifexpr);
		if(currPos >= inputString.length())
		{
			return "ifcond{ "+condStr+ "," + ifStr + " }";

		}
			
		int temppos1 = currPos;
		String token1 = calculateToken();
		if(token1.trim().equalsIgnoreCase(""))
		{
			return "ifcond{ "+condStr+ "," + ifStr + " }";
		}
		int temppos2 = currPos;
		String token2 = calculateToken();
		
		if(token1.equalsIgnoreCase("else") && token2.equalsIgnoreCase("if"))
		{
			skipWhiteSpace();
			String otherExpr = calculateIfLoop();
			val = mappingHash.size();
			String otherStr = myif+val;
			mappingHash.put(otherStr, otherExpr)	;
			return "ifelse{ "+condStr+ "," + ifStr + "," + otherStr + " }";
			
		}
		else if(token1.equalsIgnoreCase("else"))
		{
			currPos = temppos2;
			skipWhiteSpace();
			String elseExpr = calculateExpr();
			val = mappingHash.size();
			String elseStr = myif+val;
			mappingHash.put(elseStr, elseExpr);
			return "ifelse{ "+condStr+ "," + ifStr + "," + elseStr + " }";
		}
		else
		{
			currPos = temppos1;
			return "ifcond{ "+condStr+ "," + ifStr + " }";
		}
		
	}

	/**
	 * @return
	 */
	
	public String calculateToken() throws Exception
	{
		StringBuffer retBuffer = new StringBuffer();
		skipWhiteSpace();
		while(true)
		{
			if(currPos >= inputString.length())
				return retBuffer.toString();
			c = inputString.charAt(currPos);
			currPos++;
			if(isWhiteSpace())
			{
				break;	
			}
			retBuffer.append(c);

		}
		return retBuffer.toString();
	
	}
	
	private String calculateCondExpr() throws Exception
	{
		c = inputString.charAt(currPos);
		if(c != '(')
			throw new Exception("Invalid character found");
		int counter=1;
		StringBuffer retBuffer = new StringBuffer();
		retBuffer.append("(");
		while( true)
		{
			currPos++;
			if(currPos >= inputString.length())
				throw new Exception("Looking for closing bracket , not found check it out");

			c = (char)inputString.charAt(currPos);
	//		// (c);

			if(c=='(')
			{
				counter++;
			}
			else if(c==')')
			{
				counter--;
			}
			retBuffer.append(c);
			if(counter==0)
			{
				currPos++;
				break;

			}
				
		}
		// TODO Auto-generated method stub
		return retBuffer.toString();
	}
	
	public void skipWhiteSpace()
	{

		
		while(true)
		{
			if(currPos >= inputString.length())
				return ;

			c = (char)inputString.charAt(currPos);

			if(isWhiteSpace())
			{
				currPos++;
				continue;
			}
			else
			{
				break;
				
			}
		}
		return;
	}
	public boolean isWhiteSpace()
	{
		if(c == ' ' || c == '\t' || c== '\n' || c=='\r')
		{
			return true;		
		}
		else
		{
			return false;
		}
	}
	public boolean isWhiteSpace(char c)
		{
			if(c == ' ' || c == '\t' || c== '\n' || c=='\r')
			{
				return true;		
			}
			else
			{
				return false;
			}
		}
	/**
	 * @return
	 */
	private String calculateElseExpr()
	{
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * @return
	 */
	private String calculateExpr() throws Exception
	{
		c = inputString.charAt(currPos);
	//	// ln(c);
		if(c != '{')
			throw new Exception("Invalid character found,looking for {");
		int counter=1;
		StringBuffer retBuffer = new StringBuffer();
		while( true)
		{
			currPos++;
			if(currPos >= inputString.length())
				throw new Exception("Looking for parentisis , not found check it out");
			c = (char)inputString.charAt(currPos);

			if(c=='{')
			{
				counter++;
			}
			else if(c=='}')
			{
				counter--;
			}
			if(counter==0)
			{
				currPos++;
				break;

			}
			retBuffer.append(c);
				
		}
		// TODO Auto-generated method stub
		String retStr = retBuffer.toString().toUpperCase(); 
//		String retStr = parseFile(retBuffer.toString());
		return retBuffer.toString();
	}
}

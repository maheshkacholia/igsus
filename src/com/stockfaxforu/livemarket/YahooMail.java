package com.stockfaxforu.livemarket;

import java.lang.reflect.Method;

//package com.examples.htmlunit;


public class YahooMail implements YahooInterface
{

	public static void main(String[] args)
	{
		try
		{
			YahooMail yahoo = new YahooMail();
			yahoo.loginToYahoo();
			yahoo.getDataFromYahoo("TCS.NS");
//			yahoo.getDataFromYahoo();
//			yahoo.getDataFromYahoo();
//			yahoo.getDataFromYahoo();

		}
		catch (Exception e)
		{
			e.printStackTrace();
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
	}

	Object yahooObj=null;
	public String getDataFromYahoo() throws Exception
	{
		// TODO Auto-generated method stub
		return null;
	}

	public String getDataFromYahoo(String symbol) throws Exception
	{
		return runMethod1("getDataFromYahoo",symbol);
	}

	public void loginToYahoo() throws Exception
	{
		 yahooObj = Class.forName("yahoo.YahooMaster").newInstance(); 
		runMethod("loginToYahoo");
		// TODO Auto-generated method stub
		
	}
	public void runMethod(String MethodName) throws Exception
	{
		Class c = yahooObj.getClass();
		Method[] m = c.getMethods();
		int i=0;
		for(i=0;i<m.length;i++)
		{
			if(m[i].getName().equals(MethodName))
			{
				break;
			}
		}
		m[i].invoke(yahooObj,null);

	}
	public String runMethod1(String MethodName,String symbol) throws Exception
	{
		Class c = yahooObj.getClass();
		Method[] m = c.getMethods();
		int i=0;
		for(i=0;i<m.length;i++)
		{
			if(m[i].getName().equals(MethodName))
			{
				break;
			}
		}
		Object[] args = new Object[1];
		args[0] = symbol;
		Object retoBj = m[i].invoke(yahooObj,args);
		// ln("obj="+retoBj);
		return (String)retoBj;
	}

}

/*
 * Created on Apr 3, 2009
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.temp;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

/**
 * @author Mahesh
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Test
{
	public static void main(String[] args)
	{
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH);
		int year = cal.get(Calendar.YEAR);
		int dd = cal.get(Calendar.DATE);
//this date will go as current date 		
		String currdate = dd+"-"+month +"-" + year;
		System.out.println("current="+currdate);
		cal.add(Calendar.DATE,2);
		int expmonth = cal.get(Calendar.MONTH);
		int expyear = cal.get(Calendar.YEAR);
		int expdd = cal.get(Calendar.DATE);
//		this date will go as expiry date	
		String expirydate = expdd+"-"+expmonth +"-" + expyear;
		System.out.println("expired="+expirydate);

		System.out.println(isExpired(currdate, expirydate));
	}
	public static boolean isExpired(String currentdate,String expiryDate)
	{
		StringTokenizer st = new StringTokenizer(currentdate, "-");
		int currdd = Integer.parseInt(st.nextToken());
		int currmon = Integer.parseInt(st.nextToken());
		int curryy = Integer.parseInt(st.nextToken());

		Date currdate = new Date(curryy,currmon,currdd);		
		
		StringTokenizer st1 = new StringTokenizer(expiryDate, "-");
		int expdd = Integer.parseInt(st1.nextToken());
		int expmon = Integer.parseInt(st1.nextToken());
		int expyy = Integer.parseInt(st1.nextToken());
		Date expirydate = new Date(expyy,expmon,expyy);
		
		if (currdate.after(expirydate))
			return false;
		else
			return true;
	}
	
}

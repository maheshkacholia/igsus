/*
 * Created on May 11, 2009
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.backtesting;

import java.util.HashMap;
import java.util.Vector;

import com.stockfaxforu.component.MainGraphComponent;
import com.stockfaxforu.util.Utility;

/**
 * @author Mahesh
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class BackTesting
{
//	public float initialamt = 10000;
	public float noofshare = 0;
	float nextdayprice = 0;
//	float buypercentage = 1;
//	float sellpercentage = 1;
	public BackTesting()
	{
		BackTestingParameterScreen.loadParameter();
	}
	
	public String doBackTesting(float buypercentage,float sellpercentage,float initialamt,float transamt,String trantype, Vector data)
	{
		float intamt = initialamt;
		buypercentage = buypercentage / 100;
		sellpercentage = sellpercentage / 100;
		StringBuffer output = new StringBuffer();
		output.append("<table border=1 width=100%><tr color='pink'><th>Tran. Type</th><th>Shares</th><th>Date</th><th>Price</th><th>Wealth</th></tr>");
		int buytransaction=0;
		int selltransaction=0;
		float buyprice=0;
		float sellprice=0;
		int winbuytrade = 0;
		int winselltrade=0;
		for(int i=0;i<data.size();i++)
		{
			HashMap hs = (HashMap)data.get(i);
			if((i + 1) < data.size())
			{
				HashMap hs1 = (HashMap)data.get(i+1);
				nextdayprice = Float.parseFloat((String)(hs1.get(MainGraphComponent.Open)));
				String date = (String)(hs1.get(MainGraphComponent.Date));

				String buy = (String)hs.get(MainGraphComponent.BUY);
				String sell = (String)hs.get(MainGraphComponent.SELL);
				String buyind = (String)hs.get(MainGraphComponent.BUYIND);
				String sellind = (String)hs.get(MainGraphComponent.SELLIND);
				if ( buy==null )
					buy=buyind;
				if ( sell==null )
					sell=sellind;
				
				if(buy != null)
				{
					float amt = initialamt * buypercentage; 

					if(trantype.equalsIgnoreCase(BackTestingParameterScreen.TRANS_IN_AMT))
					{
						amt = initialamt * buypercentage - transamt;		
						if(amt <= 0)
							continue;				
						float share = amt / nextdayprice;
						noofshare = noofshare + share;
						initialamt = initialamt - ( share * nextdayprice) - transamt;
						float totalwealth = initialamt + ( share * nextdayprice);
//						output.append(share + " Shares bought on " + date + " on price " + nextdayprice + " current wealth " + totalwealth + "\n");
						output.append("<tr color='green'><td>Buy</td><td>" + Utility.floatDataAtTwoPrecision(share) + "</td><td>" + date + "</td><td>" + nextdayprice + "</td><td>" + totalwealth + "</td></tr>");
						buyprice = nextdayprice;
						buytransaction++;
						
					}
					else if (trantype.equalsIgnoreCase(BackTestingParameterScreen.TRANS_IN_PER))
					{
						amt = initialamt * buypercentage - transamt*(initialamt * buypercentage)/100;						
						if(amt <= 0)
							continue;
						float share = amt / nextdayprice;
						noofshare = noofshare + share;
						initialamt = initialamt - ( share * nextdayprice) - ( share * nextdayprice)*transamt/100;
						float totalwealth = initialamt + ( share * nextdayprice);
//						output.append(share + " Shares bought on " + date + " on price " + nextdayprice + " current wealth " + totalwealth + "\n");
						output.append("<tr color='green'><td>Buy</td><td>" + Utility.floatDataAtTwoPrecision(share) + "</td><td>" + date + "</td><td>" + nextdayprice + "</td><td>" + totalwealth + "</td></tr>");
						buyprice = nextdayprice;
						buytransaction++;
					}
				}
				else if ( sell != null)
				{
					float sharetosold = noofshare * sellpercentage;
					if(sharetosold <= 0)
						continue;					
//					output.append(sharetosold + " Shares sold on " + date  + " on price " + nextdayprice );
					output.append("<tr  color='red'><td>Sell</td><td>" + Utility.floatDataAtTwoPrecision(sharetosold) + "</td><td>" + date + "</td><td>" + nextdayprice );

					if(trantype.equalsIgnoreCase(BackTestingParameterScreen.TRANS_IN_AMT))
					{
						initialamt = initialamt + ( sharetosold * nextdayprice) - transamt;
					}
					else if (trantype.equalsIgnoreCase(BackTestingParameterScreen.TRANS_IN_PER))
					{
						initialamt = initialamt + ( sharetosold * nextdayprice) - (transamt*( sharetosold * nextdayprice)/100);

					}					
					noofshare = noofshare - sharetosold;
					sellprice = nextdayprice;
					if(sellprice > buyprice)
					{
						winbuytrade++;
					}
					float totalwealth = initialamt + ( noofshare * nextdayprice);
					selltransaction++;
//					output.append(" current wealth " + totalwealth + "\n");
					output.append( "</td><td>" + totalwealth + "</td></tr>");

					
				}
			
				
			}
		}
		output.append( "</table>" );

		HashMap hs1 = (HashMap)data.get(data.size() -1 );
		nextdayprice = Float.parseFloat((String)(hs1.get(MainGraphComponent.Close)));
		float totalamt = initialamt + noofshare * nextdayprice;
//		output.append("\n");
//		output.append("\n");
		output.append("<br>");
		output.append("<br>");
		
		output.append("<table border=1 width=100%>");

		output.append("<tr><td>Total Amount Invested Initially </td><td>"+intamt + "</td></tr>");
		output.append("<tr><td>Transaction Type </td><td>"+ trantype+ "</td></tr>");
		output.append("<tr><td>Transaction Amount/Percentage </td><td>"+ transamt+ "</td></tr>");
		output.append("<tr><td>% of Amt invested on buy signal </td><td>"+ buypercentage+ "</td></tr>");
		output.append("<tr><td>% of Share soled on sell signal </td><td>"+ sellpercentage+ "</td></tr>");
		output.append("<tr><td>Total Transactions Done </td><td>"+ (buytransaction +  selltransaction)+ "</td></tr>");
		output.append("<tr><td>Total Buy Transaction Done </td><td>"+ (buytransaction )+ "</td></tr>");
		output.append("<tr><td>Total Buy Transaction Done </td><td>"+ (selltransaction )+ "</td></tr>");
		output.append("<tr><td>No of winning trade </td><td>"+ (winbuytrade )+ "</td></tr>");
		
		

		output.append("<tr><td>Total Amount Currently </td><td>"+ Utility.floatDataAtTwoPrecision(totalamt) + "</td></tr>");
		output.append("<tr><td>Total Cash </td><td>"+ Utility.floatDataAtTwoPrecision(initialamt)+ "</td></tr>");
		output.append("<tr><td>Total Share </td><td>"+ Utility.floatDataAtTwoPrecision(noofshare)+ "</td></tr>");
		
		output.append("<tr><td>% Loss Profit </td><td>"+ ((totalamt - intamt)/10000)*100 +  "</td></tr>");

		HashMap hs = (HashMap)data.get(0);
		float myfirstprice = Float.parseFloat((String)(hs.get(MainGraphComponent.Close)));
		hs = (HashMap)data.get(data.size()-1);
		
		float mylastprice = Float.parseFloat((String)(hs.get(MainGraphComponent.Close)));
		float perchange = ((mylastprice - myfirstprice )* 100 / myfirstprice );
		
		float finalamt = intamt + intamt*perchange/100;
		

		output.append("<tr><td>% Loss Profit(Buy/Hold Strategy) </td><td>"+ perchange + "</td></tr>");
		output.append("<tr><td>Total Cash(Buy/Hold Strategy) </td><td>"+ finalamt + "</td></tr></table>");

		return output.toString();
	}
	public String returnProftLoss(float buypercentage,float sellpercentage,float initialamt,float transamt,String trantype, Vector data)
	{
		float intamt = initialamt;
		buypercentage = buypercentage / 100;
		sellpercentage = sellpercentage / 100;
		StringBuffer output = new StringBuffer(0);
		for(int i=0;i<data.size();i++)
		{
			HashMap hs = (HashMap)data.get(i);
			if((i + 1) < data.size())
			{
				HashMap hs1 = (HashMap)data.get(i+1);
				nextdayprice = Float.parseFloat((String)(hs1.get(MainGraphComponent.Open)));
				String date = (String)(hs1.get(MainGraphComponent.Date));

				String buy = (String)hs.get(MainGraphComponent.BUY);
				String sell = (String)hs.get(MainGraphComponent.SELL);
				if(buy != null)
				{
					float amt = initialamt * buypercentage; 

					if(trantype.equalsIgnoreCase(BackTestingParameterScreen.TRANS_IN_AMT))
					{
						amt = initialamt * buypercentage - transamt;		
						if(amt < 0)
							continue;				
						float share = amt / nextdayprice;
						noofshare = noofshare + share;
						initialamt = initialamt - ( share * nextdayprice) - transamt;
						output.append(share + " Shares bought on " + date + " on price " + nextdayprice + "\n");

					}
					else if (trantype.equalsIgnoreCase(BackTestingParameterScreen.TRANS_IN_PER))
					{
						amt = initialamt * buypercentage - transamt*(initialamt * buypercentage)/100;						
						if(amt < 0)
							continue;
						float share = amt / nextdayprice;
						noofshare = noofshare + share;
						initialamt = initialamt - ( share * nextdayprice) - ( share * nextdayprice)*transamt/100;
						output.append(share + " Shares bought on " + date + " on price " + nextdayprice + "\n");
						
					}
				}
				else if ( sell != null)
				{
					float sharetosold = noofshare * sellpercentage;
					if(sharetosold < 0)
						continue;					
					output.append(sharetosold + " Shares sold on " + date  + " on price " + nextdayprice + "\n");

					if(trantype.equalsIgnoreCase(BackTestingParameterScreen.TRANS_IN_AMT))
					{
						initialamt = initialamt + ( sharetosold * nextdayprice) - transamt;
					}
					else if (trantype.equalsIgnoreCase(BackTestingParameterScreen.TRANS_IN_PER))
					{
						initialamt = initialamt + ( sharetosold * nextdayprice) - (transamt*( sharetosold * nextdayprice)/100);

					}					
					noofshare = noofshare - sharetosold;
				}

				
			}
		}
		HashMap hs1 = (HashMap)data.get(data.size() -1 );
		nextdayprice = Float.parseFloat((String)(hs1.get(MainGraphComponent.Close)));
		float totalamt = initialamt + noofshare * nextdayprice;
		output.append("\n");
		output.append("\n");
		
		output.append("total amount invested initially "+intamt + "\n");
		output.append("total amount currently "+ totalamt + "\n");
		output.append("total cash "+ initialamt+ "\n");
		output.append("total share "+ noofshare+ "\n");
		
		output.append("% loss profit =  "+ ((totalamt - 10000)/10000)*100 +  "\n");
		
		
		return (((totalamt - intamt)/intamt)*100) + "";
	}


}

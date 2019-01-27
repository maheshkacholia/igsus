package com.stockfaxforu.livemarket;

public interface YahooInterface
{
	public void loginToYahoo() throws Exception;
	public  String getDataFromYahoo() throws Exception ;
	public  String getDataFromYahoo(String symbol) throws Exception ;
	
}

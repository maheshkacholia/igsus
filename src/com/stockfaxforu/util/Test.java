package com.stockfaxforu.util;

public class Test 
{
	public static void main(String[] args)
	{
		try {
			String data1 = "gmtoffset:-14400\nprevious_close:99.9000\nTimestamp:1412343000,1412366400";
			String data = data1.substring(data1.indexOf("previous_close:"));
			data = data.substring("previous_close:".length());
			data = data.substring(0,data.indexOf("\n"));
			
			
			System.out.println(data);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}

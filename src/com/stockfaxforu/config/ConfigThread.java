package com.stockfaxforu.config;

import com.stockfaxforu.formulabuilder.MessageDiaglog;
import com.stockfaxforu.util.StockConstants;
import com.stockfaxforu.util.Utility;

public class ConfigThread implements Runnable
{

	public void run() 
	{
		try
		{
			String userstatus = Utility.getUrlContent(StockConstants.USERSTATUSURL + StockConstants.ENCODEDUSERDETAIL);
			userstatus = userstatus.trim();
			if(userstatus.equals(StockConstants.BASIC) || userstatus.equals(StockConstants.ADVANCED) || userstatus.equals(StockConstants.INTRADAY))
			{
				Utility.saveContent(StockConstants.userStatusFile,userstatus);
				StockConstants.USERSTATUS = userstatus;
				
			}
		}
		catch(Exception e)
		{
			try
			{
				StockConstants.USERSTATUS = Utility.getFileContent(StockConstants.userStatusFile).trim();
				
			}
			catch(Exception e2)
			{
				
			}
			
		}
		try
		{
		//	System.out.println(StockConstants.UPGRADEMSGURL + StockConstants.ENCODEDUSERDETAIL);
			String upgrademsg = Utility.getUrlContent(StockConstants.UPGRADEMSGURL + StockConstants.ENCODEDUSERDETAIL);
			
			StockConstants.UPGRADEMSG = upgrademsg.trim();
			Utility.saveContent(StockConstants.userMessageFile,upgrademsg.trim());
			
		}
		catch(Exception e)
		{
			
		}
		try
		{
			String otherproperty = Utility.getUrlContent(StockConstants.OTHERPROPERTYURL);
			Utility.saveContent(StockConstants.otherPropertyFile,otherproperty);
			
			
		}
		catch(Exception e)
		{
			
		}
		try
		{
			String versioninfo = Utility.getUrlContent(StockConstants.VERSIONURL).trim();
			if(!versioninfo.equals(StockConstants.CURRENTVERSION))
			{
	//			MessageDiaglog m = new MessageDiaglog("New Version of IGSIndia Software is released,Pls download by clicking here","http://www.iguidestocks.com/project/igsnew/igs/download/IGSIndia.zip");
				
			}
		}
		catch(Exception e)
		{
			
		}
		
		

	}

}

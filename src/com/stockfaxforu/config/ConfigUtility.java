/*
 * Created on Nov 2, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.config;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;

import org.w3c.dom.Element;

import com.stockfaxforu.component.MainGraphComponent;
import com.stockfaxforu.formulabuilder.MessageDiaglog;
import com.stockfaxforu.util.StockConstants;
import com.stockfaxforu.util.Utility;

/**
 * @author sdeopura
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ConfigUtility
{

	public static void loadUserDetail() throws Exception
	{
		try
		{
			StockConstants.USERDETAIL.load(new FileInputStream(StockConstants.INSTALL_DIR_TEMP + StockConstants.userDetailFile));
			
		}
		catch (Exception e1)
		{
			MessageDiaglog msg = new MessageDiaglog("Error occured while reading user detail,Not able to find user detail");
			throw e1;
		}
		
	}
	public static void loadUserDetailFromInstall() throws Exception
		{
			try
			{
				StockConstants.USERDETAIL.load(new FileInputStream(StockConstants.INSTALL_DIR + StockConstants.userDetailFile));
				String computerName = Utility.getComputerName();
				
				String installcomname = StockConstants.USERDETAIL.getProperty("mcname");
				String installuid = StockConstants.USERDETAIL.getProperty("uid");
				
				if(installcomname.equalsIgnoreCase(installuid))
				{
					return;
				}
				else if(computerName.equalsIgnoreCase(installcomname))
				{
					return;
				}
				else
				{
					throw new Exception();
				}
			}
			catch (Exception e1)
			{
				MessageDiaglog msg = new MessageDiaglog("Error Occured While Reading User Detail,Not Able to Find User Detail");
				throw e1;
			}
		
		}
	public static void loadOtherProperty() throws Exception
	{

		try
		{
			StockConstants.USERDETAIL.load(new FileInputStream(StockConstants.INSTALL_DIR_TEMP + StockConstants.userDetailFile));
			StockConstants.ENCODEDUSERDETAIL = Utility.encodeurl(StockConstants.USERDETAIL);
		}
		catch (Exception e1)
		{
			MessageDiaglog msg = new MessageDiaglog("Error occured while reading user detail,Not able to find user detail");
			throw e1;
		}
		try
		{
	//		properties.load(new FileInputStream(StockConstants.FirstTime_INI));
			StockConstants.VERSION = Utility.getFileContent(StockConstants.FirstTime_INI);

		}
		catch (Exception e)
		{
			MessageDiaglog msg = new MessageDiaglog("Error occured while reading firsttime.ini file");
			throw e;
		}

		try
		{
			StockConstants.UPGRADEMSG = Utility.getFileContent(StockConstants.userMessageFile);
		}
		catch(Exception e)
		{
		}
		try
		{
			
			StockConstants.USERSTATUS = Utility.getFileContent(StockConstants.userStatusFile).trim();
		}
		catch(Exception e)
		{
		}	

		ConfigThread config = new ConfigThread();
		Thread th = new Thread(config);
		th.start();
		
		if(StockConstants.USERSTATUS.equalsIgnoreCase("NONE"))
		{
			MessageDiaglog msg = new MessageDiaglog("Your Access  Temporarily Revoked, Contact IGS ");
			throw new Exception();
			
		}
		
		
	}
	
	public static void loadProperty()
	{
		try 
		{
//			properties = new Properties();
//			properties.load(new FileInputStream(StockConstants.SOFTWARE_PROPERTY_FILE));
			
			if(ConfigXMLManager.doc==null)
				ConfigXMLManager.initialParser();

			try
			{
				StockConstants.YahooHourDifference = Integer.parseInt(ConfigUtility.getPropertyValue("Yahoo Hour Difference"));
			}
			catch(Exception e)
			{
				
			}
			
			if(ConfigUtility.getPropertyValue("Intraday Data Source") != null)
				StockConstants.IntradayDataSource = ConfigUtility.getPropertyValue("Intraday Data Source");
			
			
			
			if(ConfigUtility.getPropertyValue("Install Drive") != null)
				StockConstants.INSTALL_DRIVE = ConfigUtility.getPropertyValue("Install Drive");

			if(ConfigUtility.getPropertyValue("Explorer") != null)
				StockConstants.IEEXPLORERLOC = ConfigUtility.getPropertyValue("Explorer");
	
	
			if(ConfigUtility.getPropertyValue("Start Date") != null)
				StockConstants.STARTDATE = ConfigUtility.getPropertyValue("Start Date");

			if(ConfigUtility.getPropertyValue("Screen Date") != null)
				StockConstants.SCREENDATE = ConfigUtility.getPropertyValue("Screen Date");

			if(ConfigUtility.getPropertyValue("Auto Update Intraday Graph") != null)
			{
				try
				{
					String s = ConfigUtility.getPropertyValue("Auto Update Intraday Graph");
					StockConstants.autoupdateintradaygraph = Long.parseLong(s);
				}
				catch(Exception e)
				{
					
				}
				

			}

			if(ConfigUtility.getPropertyValue("Selected Stock") != null)
				
				StockConstants.SELECTED_STOCK = ConfigUtility.getPropertyValue("Selected Stock");

			
			if(ConfigUtility.getPropertyValue("Graph Resolution") != null)
			{
				try
				{
					String s = ConfigUtility.getPropertyValue("Graph Resolution");
					StockConstants.GraphResolution = Integer.parseInt(s);				
				}
				catch(Exception e)
				{
					
				}
			}
			if(ConfigUtility.getPropertyValue("Download Hour Gap") != null)
						{
							try
							{
								String s = ConfigUtility.getPropertyValue("Download Hour Gap");
								StockConstants.downloadHourGap = Integer.parseInt(s);				
							}
							catch(Exception e)
							{
					
							}
						}
			
			
			

//			intraDayUpdateTime



			if(ConfigUtility.getPropertyValue("Cursor Type") != null)
			{
				try
				{
					String s = ConfigUtility.getPropertyValue("Cursor Type");
					StockConstants.CursorType = s;				
				}
				catch(Exception e)
				{
					
				}
			}
			if(ConfigUtility.getPropertyValue("Download URL") != null)
			{
				try
				{
					String s = ConfigUtility.getPropertyValue("Download URL");
					StockConstants.downloadURL = StockConstants.downloadURL;				
				}
				catch(Exception e)
				{
		
				}
			}
			

			if(ConfigUtility.getPropertyValue("Show Graph Year") != null)
			{
				try
				{
					String s = ConfigUtility.getPropertyValue("Show Graph Year");
					StockConstants.ShowGraphYear = s;				
				}
				catch(Exception e)
				{
		
				}
			}
			if(ConfigUtility.getPropertyValue("Graph Display") != null)
			{
				try
				{
					String s = ConfigUtility.getPropertyValue("Graph Display");
					int i = 1;
					if(s.equalsIgnoreCase("LINEGRAPH"))
						i=MainGraphComponent.LINEGRAPH;
					else if(s.equalsIgnoreCase("OHLCGRAPH"))
						i=MainGraphComponent.OHLCGRAPH;
					else if (s.equalsIgnoreCase("CANDLEGRAPH"))
						i=MainGraphComponent.CANDLEGRAPH;	
						StockConstants.GraphDisplay = i;				
				}
				catch(Exception e)
				{

				}
			}
			
			try
			{
				String s = ConfigUtility.getPropertyValue("Horizontal Line Gap");
				StockConstants.hlinegap = Integer.parseInt(s);				
			}
			catch(Exception e)
			{
					
			}
			try
			{
				String s = ConfigUtility.getPropertyValue("Vertical Line Gap");
				StockConstants.vlinegap = Integer.parseInt(s);				
			}
			catch(Exception e)
			{
					
			}


			if(ConfigUtility.getPropertyColor("Result Directories") != null)
				StockConstants.ResultDirectories = ConfigUtility.getPropertyValue("Result Directories");


			if(ConfigUtility.getPropertyColor("Sql Output Color1") != null)
				StockConstants.SqlOutputColor1 = ConfigUtility.getPropertyColor("Sql Output Color1");
			
			if(ConfigUtility.getPropertyColor("Sql Output Color2") != null)
				StockConstants.SqlOutputColor2 = ConfigUtility.getPropertyColor("Sql Output Color2");

			
			if(ConfigUtility.getPropertyColor("Buy Arrow Color") != null)
						StockConstants.buyarrowcolor = ConfigUtility.getPropertyColor("Buy Arrow Color");
		
			if(ConfigUtility.getPropertyColor("Sell Arrow Color") != null)
						StockConstants.sellarrowcolor = ConfigUtility.getPropertyColor("Sell Arrow Color");
		

			if(ConfigUtility.getPropertyColor("Up Color") != null)
				StockConstants.upcolor = ConfigUtility.getPropertyColor("Up Color");
			if(ConfigUtility.getPropertyColor("Down Color") != null)
				StockConstants.downcolor = ConfigUtility.getPropertyColor("Down Color");
	
			if(ConfigUtility.getPropertyColor("Horizontal Line Color") != null)
				StockConstants.hline = ConfigUtility.getPropertyColor("Horizontal Line Color");
		
			if(ConfigUtility.getPropertyColor("Vertical Line Color") != null)
				StockConstants.vline = ConfigUtility.getPropertyColor("Vertical Line Color");
		
			if(ConfigUtility.getPropertyColor("Closing Line Color") != null)
				StockConstants.closinglinecolor = ConfigUtility.getPropertyColor("Closing Line Color");
			
			if(ConfigUtility.getPropertyColor("Graph Back Ground Color") != null)
				StockConstants.graphbackgroundcolor = ConfigUtility.getPropertyColor("Graph Back Ground Color");


			if(ConfigUtility.getPropertyColor("Price Color") != null)
				StockConstants.pricecolor = ConfigUtility.getPropertyColor("Price Color");

			if(ConfigUtility.getPropertyColor("Time Color") != null)
				StockConstants.timecolor = ConfigUtility.getPropertyColor("Time Color");

			if(ConfigUtility.getPropertyColor("Left Price Color") != null)
				StockConstants.leftpricecolor = ConfigUtility.getPropertyColor("Left Price Color");

			if(ConfigUtility.getPropertyColor("Mouse Move Color") != null)
				StockConstants.mousemovecolor = ConfigUtility.getPropertyColor("Mouse Move Color");

			if(ConfigUtility.getPropertyColor("Mouse Move Price Color") != null)
				StockConstants.mousemovepricecolor = ConfigUtility.getPropertyColor("Mouse Move Price Color");

			if(ConfigUtility.getPropertyColor("Boundary Color") != null)
				StockConstants.boundarycolor = ConfigUtility.getPropertyColor("Boundary Color");


			if(ConfigUtility.getPropertyColor("Drawing Component Line Color") != null)
				StockConstants.drawingcomponentlinecolor = ConfigUtility.getPropertyColor("Drawing Component Line Color");
			if(ConfigUtility.getPropertyColor("Drawing Component Font Color") != null)
				StockConstants.drawingcomponentfontcolor = ConfigUtility.getPropertyColor("Drawing Component Font Color");
			if(ConfigUtility.getPropertyColor("Drawing Component Fill Color") != null)
				StockConstants.drawingcomponentfillcolor = ConfigUtility.getPropertyColor("Drawing Component Fill Color");
//should download from internet or not 

  			if(ConfigUtility.getPropertyValue("Download Data From Internet") != null)
	  			StockConstants.downloadDataFromInternet = ConfigUtility.getPropertyValue("Download Data From Internet");

  			if(ConfigUtility.getPropertyValue("Show Name At Bottom") != null)
	  			StockConstants.ShowNameAtBottom = Boolean.parseBoolean(ConfigUtility.getPropertyValue("Show Name At Bottom"));

			


		}	
		catch (Exception e) 
		{
//			e.printStackTrace();
		}
		
	}
	public static Properties otherProperty=null;
	public static HashMap futureMapping=new HashMap();
	public static Vector futureDate = null;
	public static String getFutureMapping(String date)
	{
		return futureMapping.get(date)+"";
	}
	public static Vector getFutureExpiry()
	{
		if(futureDate==null)
		{	
			futureDate=new Vector();
		}
		else
		{
			return futureDate;
		}
		Calendar c = Calendar.getInstance();
		Calendar c1 = Calendar.getInstance();
		int i=1;
		try
		{
			if(otherProperty==null)
				otherProperty=new Properties();
			otherProperty.load(new FileInputStream(StockConstants.otherPropertyFile));
			
			String s = otherProperty.getProperty("ExpiryDate");
			StringTokenizer st = new StringTokenizer(s,",");
			Vector retV = new Vector();
			while(st.hasMoreTokens())
			{
				String stemp = st.nextToken();
				String[] tokens = stemp.split("-");
				c1.set(Integer.parseInt(tokens[2]), Integer.parseInt(tokens[1]) - 1,Integer.parseInt(tokens[0]));
				if(c1.after(c))
				{	
					futureDate.add(stemp);
					futureMapping.put(stemp, i+"");
					i++;
				}	
			}	
			return futureDate;
				
		}
		catch(Exception e)
		{
			return new Vector();
		}
			
	}
	public static String getColorString(Color color)
	{
		for(int i=0;i<ConfigUtility.color.length;i++)
		{
			Color s = ConfigUtility.color[i];
			if(s.equals(color))
			{
				
				return ConfigUtility.colorStr[i];		

			}
		}
		return "";
	}

	public static Color getColor(String colorStr)
	{
		for(int i=0;i<ConfigUtility.colorStr.length;i++)
		{
			String s = ConfigUtility.colorStr[i];
			if(s.equalsIgnoreCase(colorStr))
			{
				
				return ConfigUtility.color[i];		

			}
		}
		return null;
	}
	public static String getPropertyValue(String propertyStr)
	{
		if(ConfigXMLManager.doc==null)
		{
			ConfigXMLManager.initialParser();	
		}
		Element element = (Element) ConfigXMLManager.propertyHash.get(propertyStr);
		if(element==null)
			return null;	
		String s = ConfigXMLManager.getDataForTag(element,ConfigXMLManager.VALUE);
		if(s==null)
			return null;
		else 
			return s.trim();	
	}
	public static Color getPropertyColor(String propertyStr)
	{
		String s =  getPropertyValue(propertyStr);
		if(s==null)
			return null;
		return getColor(s);	
	}
	
	

	/**
	 * 
	 */
	public ConfigUtility()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args)
	{
		File f = new File("/Windows");
									
	}

	/**
	 * @return
	 */
	public static Color[] color =     {Color.black,Color.blue,Color.cyan,Color.darkGray,Color.gray,Color.green,Color.lightGray,Color.magenta,Color.orange,Color.pink,Color.red,Color.white,Color.yellow};
	public static String[] colorStr = {"Black",    "Blue",    "Cyan",    "Dark Gray",          "Gray",    "Green",   "Light Gray",    "Magenta",     "Orange",   "Pink",    "Red",    "White",    "Yellow"};
	
	public static String[] getColors()
	{
		// TODO Auto-generated method stub
		return colorStr;
	}
	public static void saveConfiguration()
	{
		
	}
	public static void applyConfiguration()
	{
		
	}
	public static HashMap getConfiguration()
	{
		return null;
	}
	
}

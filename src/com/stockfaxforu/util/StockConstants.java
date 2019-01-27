/*
 * Created on Aug 4, 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.util;

import java.awt.Color;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import java.util.Vector;

import com.stockfaxforu.livemarket.YahooInterface;
import com.stockfaxforu.livemarket.YahooMail;

/**
 * @author sdeopu1
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class StockConstants
{
	public static final String COUNTRY=Utility.getCountry();

	public static final String _COUNTRY="_"+COUNTRY;

	public static String ENDDATE="";
	public static final String INSTALL_DIR = System.getProperty("user.home") + "/chartalive"+_COUNTRY;
	public static final String INSTALL_DIR_O = System.getProperty("user.home") + "\\chartalive"+_COUNTRY;
	public static final String INSTALL_DIR_TEMP = System.getProperty("user.home") + "/windowu"+_COUNTRY;

//otherproperty

	public static String  OTHERPROPERTYURL = "http://www.livechartnse.com";
	public static String  VERSIONURL = "http://www.livechartnse.com"
			+ "";
	public static String CurrentExpiryDate="";
	public static String CurrentExpiryMonth="";
	
	public static String  otherPropertyFile = INSTALL_DIR + "/other/otherProperties.properties";
	
	
	
	public static final long CONSTANTVOLUME = 1000;
	public static final String QUOTEDETAIL = "";
	
	public static  int YahooHourDifference = 10;

	//intraDayUpdateTime in milli seconds	
//	public static long intraDayUpdateTime=5000;

	public static int downloadHourGap=5;

	public static String downloadDataFromInternet="true";

	public static int COUNTER=1;

	public static String downloadURL="http://ichart.finance.yahoo.com/table.csv?s=<%SYMBOL%>&d=<%CURRMONTH%>&e=<%CURRDATE%>&f=<%CURRYEAR%>&g=d&a=<%STARTYEAR%>&b=<%STARTMONTH%>&c=<%STARTDATE%>&ignore=.csv";

	public static String INSTALL_DRIVE = "C:";

	public static String USERSTATUS = "BASIC";
	public static String BASIC="BASIC";
	public static String ADVANCED="ADVANCED";
	public static String INTRADAY="INTRADAY";
	public static String UPGRADEMSG = "Upgrade to advanced version ";
	public static String UPGRADEMSGMSG = "Upgrade to advanced version,Click here fore more info... ";

	public static final String INTRADATA_DATAURL = "http://www.iguidestocks.com/project/igsworld/intradaydata.jsp?";
	
//version of software	
	public static final String CURRENTVERSION = "5.0.0";
	public static String UPGRADEMSGURL = "http://www.livechartnse.com/project/igsworld/upgrade.php?";
	public static String USERSTATUSURL = "http://www.livechartnse.com/project/igsworld/userstatus.php?";
	public static String GETFILESFROMDIRECTORY="http://www.livechartnse.com/project/igsworld/getfilesfromdirectory.php?directory=";
	public static String GETFILECONTENT="http://www.livechartnse.com/project/igsworld/getfilecontent.php?filename=";
	public static String IGSROOT = "http://www.livechartnse.com/project/fortool/";
	public static String IGSHELPURL = "http://www.livechartnse.com/project/igsworld/";
	public static String ADURL="http://www.livechartnse.com/project/igsworld/adurl.jsp?";
	public static String MESSAGEURL = "http://www.livechartnse.com/project/igsworld/getmessages.jsp?";
	public static String DELETEUSERURL="http://www.livechartnse.com/project/igsworld/deleteuser.jsp?";
	public static String ADDUSER="http://www.livechartnse.com/project/igsworld/adduser.php?";
	public static String TRADINGHISTORY = StockConstants.INSTALL_DIR + "/tradinghistory.csv";
	
	public static String serverDateFile =  "c:\\Program Files\\winntinstalligsserworld.bmp";
	public static String userDetailFile = "/userdetail.ini";

	public static String userTempFile = "c:\\Program Files\\igsuserworldtemp.bmp";
	public static String expiryFile =  "c:\\Program Files\\winntinstalligsworld.bmp";
	public static String userStatusFile = INSTALL_DIR_TEMP + "/usersattus.ini";
	public static String userMessageFile = INSTALL_DIR_TEMP + "/usermsg.ini";

	public static String FirstTime_INI = INSTALL_DIR + "/" + "firsttime.ini";
	public static String   ENCODEDUSERDETAIL="";

	public static String IEEXPLORERLOC = "c:\\Program Files\\Internet Explorer\\IEXPLORE.EXE";
	public static String SOFTWARE_PROPERTY_FILE = StockConstants.INSTALL_DIR+"/other/software.properties";
	public static String FORMULA_DIR = INSTALL_DIR_O + "\\formula";
	public static String IND_DIR = INSTALL_DIR_O + "\\indicator";

	public static String NEW_IND_DIR = INSTALL_DIR+ "/newindicator";
	public static final String NEW_STR_DIR = INSTALL_DIR+ "/newstrategy";
	public static final int YAHOO_START_YEAR = 2013;
	public static  boolean SHOWDIRECTRYIND = true;
	
	public static String SQL_DIR = INSTALL_DIR_O + "\\igssql";
	public static String SQL_DIR_INTRA = INSTALL_DIR_O + "\\igssqlintra";

	public static  String TEMPLATE_DIR = INSTALL_DIR_O + "\\template";
	public static  String IMG_DIR = INSTALL_DIR_O + "\\image";

	public static  String ALERT_DIR = INSTALL_DIR_O + "\\alert";

	public static HashMap AttemptedHash=new HashMap();

	public static long autoupdateintradaygraph=10;
	public static StringBuffer console = new StringBuffer();;
	public static String SCREENDATE="2009-01-01";
	public static Properties USERDETAIL= new Properties();

	public static String TIPS="";
	public static StringBuffer TIPSBUFFER = new StringBuffer();
	
	
	public static String PRODUCTTYPE="LIVECHARTNSE"+_COUNTRY;

	public static String  EXPIRY ="";

	public static String COPYRIGHT="";

	public static String VERSION="";
	public static boolean SHOW_FINANCE=false;;

	public static HashMap livemarketDataMap= new HashMap();

//	public static String IGSROOT = "http://localhost:9080/igswar/fortool/";
	
	public static int YPOSITIONFORGRAPH=90;
	public static int YPOSITIONFORGRAPHGAP=75;
	
	

	public static boolean loadingData=false;;
	public static String  COMPUTER_NAME = "";

	public static int NO_OF_THREAD=5;

	public static int splitno;

	public static ArrayList symbollist=null;

	public static String optionname="";

	public static String error="";
	public static boolean startthreadindex = false;
	
	public static String QUERYLIST = "Query Result";

	public static boolean[] noofday = new boolean[5];			                                                                                                                                                                                          
	
	public static String[][] querydata = null;	                                                                                                                                                                                  

	
	public static  String QUERY="";
	
	
	public static  String SHOW="";
	public static int X_POS_FOR_RES;
	public static int Y_POS_FOR_RES;

	public static int YBASE = 75;
	public static int MYINC =225;
	
	public static int NO_OF_DAYS=10;
	public static String SelectedGraph="";
	public static String SelectedStock="";

	public static String SelectedMonth="";
	public static String IndexName="nifty";

	public static String SelectedYear="";

//	public static String SAMPLE_CODE="ACC";
	public static String STARTDATE="2005-01-01";
	public static String STARTDATE_SLASH="01/01/2005";
	
	public static String SYMBOLFILE="allnsecode.txt";
	public static int KEEP_OLD=1;
	public static int NEW=2;
	

	public static String ResultDirectories = "result";

	public static Color SqlOutputColor1 = Color.yellow;
	public static Color SqlOutputColor2 = Color.green;

	
	public static String SELECTED_STOCK="AAPL";
	public static int GraphResolution=1;
	public static String CursorType="Cross-Hair";
	public static String ShowGraphYear="2009-01-01";
	public static int GraphDisplay=3;
	public static Color upcolor = Color.green;
	public static Color downcolor = Color.red;
	public static Color hline = Color.white;
	public static Color vline = Color.white;
	public static Color pricecolor = Color.white;
	public static Color timecolor = Color.white;
	public static Color leftpricecolor = Color.green;
	public static Color mousemovecolor= Color.white;
	public static Color mousemovepricecolor= Color.white;
	
	public static Color closinglinecolor= Color.green;
	public static Color  graphbackgroundcolor = Color.black;
	public static Color  boundarycolor = Color.black;
	public static Color  sellarrowcolor=Color.red;
	public static Color  buyarrowcolor = Color.green;
	public static Color  drawingcomponentlinecolor=Color.black;
	public static Color  drawingcomponentfontcolor=Color.green;
	public static Color  drawingcomponentfillcolor=Color.black;


	public static int   hlinegap = 40;
	public static int   vlinegap = 100;
	
	
	
	public static String DOWNLOAD_DIR="download";
	public static String ALLSYMBOL_FILE = "/allsymbol.txt";
	

//	public static String 	PWD="jp8236";
	public static String 	PWD="";

//	public static String 	USERID="sdeopu";
	public static String 	USERID="";
	public static boolean 	IS_PROXY=false;
	public static String 	DOWNLOAD_DATA = "download";
	public static String 	Analysis = "analysis";
	
//	public static String 	PROXY = "PHXPSGW.AEXP.COM";
	public static String 	PROXY = "";

//	public static String 	PORT = "8080";
	public static String 	PORT = "";
	
	public static int 	height = 950;
	public static int 	length = 1350;
	public static int 	gap = 22;
	


	public static Properties install  = new Properties();;
	public static StringBuffer LogBuffer = new StringBuffer();

	public static YahooInterface YAHOO=null;

	public static String propertyXML=StockConstants.INSTALL_DIR+"/other/propertyXML.xml";
	public static String   mappingProperty = StockConstants.INSTALL_DIR+"/other/mapping.properties";
	
	public static String IntradayDataSource="SOURCE1";

	public static String SOURCE1="SOURCE1";
	public static String YAHOOSOURCE="YAHOOSOURCE";
	public static String LIVESOURCE2 = "LIVESOURCE2";


	public static  String ICICIMAPPING = INSTALL_DIR_TEMP +"/livesymbol.properties";
	public static boolean SHOWFUTURE=false;
	public static int RESOLUTION=1;
	public static boolean ShowNameAtBottom=true;
	public static String forexFile="";
	public static boolean DATADOWNLOADED=false;
	public static int UNIQUECODE;
	public static boolean StockSnapshot=true;

	
	
}

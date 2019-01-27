/*
 * Created on Aug 4, 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.getolddataforu.util;

import java.util.ArrayList;
import java.util.Properties;
import java.util.Vector;

/**
 * @author sdeopu1
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class StockConstantsForOldData
{
	public static String  COMPUTER_NAME = "";

	public static int NO_OF_THREAD=5;

	public static int splitno;

	public static ArrayList symbollist=null;

	public static String optionname="";

	public static String error="";

	public static boolean startthreadindex = false;
	public static String IEEXPLORERLOC = "C:\\Program Files\\Internet Explorer\\IEXPLORE.EXE";

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

	public static String SAMPLE_CODE="3IINFOTECH";
	public static String STARTDATE="01-01-2005";
	public static String STARTDATE_SLASH="01/01/2005";
	
	public static String SYMBOLFILE="allnsecode.txt";
	public static int KEEP_OLD=1;
	public static int NEW=2;
	
	public static String DOWNLOAD_DIR="download";
	public static String INSTALL_DIR = "/getolddataforu";
	public static String 	PWD="dg0927";
	public static String 	USERID="dgup01";
	public static boolean 	IS_PROXY=true;
	public static String 	DOWNLOAD_DATA = "download";
	public static String 	Analysis = "analysis";
	
	public static String 	PROXY = "PHXPSGW.AEXP.COM";
	public static String 	PORT = "8080";
	
	public static int 	height = 500;
	public static int 	length = 950;
	public static int 	gap = 22;
	


	public static Properties install  = new Properties();;
	public static StringBuffer LogBuffer = new StringBuffer();

	
	
	
}

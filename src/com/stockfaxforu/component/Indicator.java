/*
 * Created on Feb 15, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.component;

import java.awt.Color;
import java.io.Serializable;

/**
 * @author sdeopura
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Indicator implements Serializable
{
	/**
	 * @param string
	 * @param i
	 * @param j
	 */
	public Indicator(String indicatorName, int noofperiods, int indicatorType)
	{
		this.noofperiods = noofperiods;
		this.indicatorName = indicatorName;
		this.indicatorType =  indicatorType;
	}
	
	public static String CUSTOM_NAME="CUSTOM_NAME";
	public int id=0;
	public int noofperiods=1;
	public String query = "";
//	public int[] periods=null;

	public int period1=0;
	public int period2=0;
	public int period3=0;
	public int period4=0;
	
	
	public int indicatorType=1;
	public String indicatorName=null;
	
	
	public static String SMA_NAME = "SMA";
	public static String MMA_NAME = "MMA";
	public static String EMA_NAME = "EMA";
	public static String WMA_NAME = "WMA";

	public static String MACD_NAME = "MACD";
	public static String BB_NAME = "BBAND";
	public static String RSI_NAME = "RSI";
	public static String Stochastic_NAME="Stochastic";
	public static String William_NAME="William %R";
	public static String VolumeOscilator_NAME="Volume Oscillator(PVO)";
	public static String  MFI_NAME= "Money Flow Index";
	public static String  ONBALANCEVOLUME_NAME="On Balance Volume";
	public static String  AccDistri_NAME="Accumulation/Distribution";
	public static String  ChaikinOscillator_NAME="Chaikin Oscillator";
	public static String  AverageTrueRange_NAME= "Average True Range";
	public static String  AroopOscillator_NAME="Aroop Oscillator";
	public static String  ChaikinMoneyFlow_NAME="Chaikin Money Flow(CMF)";
	public static String  WillamAD_NAME="WillamAD";
	public static String  CCI_NAME="CCI";
	public static String  DPO_NAME="DPO";
	public static String  EOM_NAME="EOM";

	public static int SMA = 1;
	public static int EMA = 2;
	public static int MMA = 3;
	public static int WMA = 4;	
	public static int BB = 10;	
	public static int MACD = 11;
	public static int RSI = 12;
	public static int Stochastic=13;
	public static int William=14;
	public static int VolumeOscilator=15;
	public static int MFI=16;
	public static int ONBALANCEVOLUME=17;
	public static int AccDistri=18;
	public static int ChaikinOscillator=19;
	public static int AverageTrueRange=20;
	public static int AroopOscillator=21;
	public static int ChaikinMoneyFlow=22;	
	public static int WillamAD=23;
	public static int CCI=24;
	public static int DPO=25;
	public static int EOM=26;


	public static int CUSTOM=100;

}

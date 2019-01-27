/*
 * Created on Feb 20, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.util;

import java.io.File;
import java.util.HashMap;
import java.util.Vector;

import com.stockfaxforu.component.Indicator;

/**
 * @author sdeopura
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ManageIndicator
{

	/**
	 * @return
	 */
	public static Vector indicatorVector = null;
	public static Vector getIndicatorListHash()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return
	 */
	public static Vector getIndicatorName()
	{
		if(indicatorVector==null)
			loadIndicator();
		return indicatorVector;	
		// TODO Auto-generated method stub
		
	}
	public static String[] getIndicatorDesc()
	{
		if(indicatorVector==null)
			loadIndicator();
		return indidescr;	
		// TODO Auto-generated method stub
		
	}
	public static int getIndiPosition(String indicatorName)
	{
		return indicatorVector.indexOf(indicatorName);
	}
	public static HashMap desMapping = new HashMap();
	public static String[] indidescr = new String[50];
	private static void loadIndicator()
	{
		indicatorVector = new Vector();
		File f = new File(StockConstants.NEW_IND_DIR);
		String[] indList = f.list();
		int x = indList.length + 14;
		indidescr = new String[x];
		
		indicatorVector.addElement(Indicator.SMA_NAME);
		indidescr[0] = "Simple Moving Average";
		
		indicatorVector.addElement(Indicator.MMA_NAME);
		indidescr[1] = "Mahesh Moving Average\n It is moving average developed by iGuideStocks. Goto iGuideStocks website for more info.";

		indicatorVector.addElement(Indicator.EMA_NAME);
		indidescr[2] = "Expo. Moving Average";

		indicatorVector.addElement(Indicator.WMA_NAME);
		indidescr[3] = "Weighted Moving Average";

//		indicatorVector.addElement(Indicator.MACD_NAME);
//		indidescr[4] = "MACD";

		indicatorVector.addElement(Indicator.BB_NAME);
		indidescr[4] = "Bollinger Band";

//		indicatorVector.addElement(Indicator.RSI_NAME);
//		indidescr[6] = "Relative Strength Index ";

//		indicatorVector.addElement(Indicator.Stochastic_NAME);
//		indidescr[7] = "Stochastic";

		indicatorVector.addElement(Indicator.William_NAME);
		indidescr[5] = "William";

//		indicatorVector.addElement(Indicator.VolumeOscilator_NAME);
//		indidescr[9] = "Volume Oscilator";

//		indicatorVector.addElement(Indicator.MFI_NAME);
//		indidescr[10] = "Money Flow Index ";


		indicatorVector.addElement(Indicator.ONBALANCEVOLUME_NAME);
		indidescr[6] = "On Balance Volume";

		indicatorVector.addElement(Indicator.AccDistri_NAME);
		indidescr[7] = "Accumulation/Distribution ";
		
//		indicatorVector.addElement(Indicator.ChaikinOscillator_NAME);
//		indidescr[13] = " Chaikin Oscillator ";
		
		indicatorVector.addElement(Indicator.AverageTrueRange_NAME);
		indidescr[8] = "Average True Range ";
		
//		indicatorVector.addElement(Indicator.AroopOscillator_NAME);
//		indidescr[15] = " Aroop Oscillator";

		indicatorVector.addElement(Indicator.ChaikinMoneyFlow_NAME);
		indidescr[9] = "Chaikin Money Flow ";

		indicatorVector.addElement(Indicator.WillamAD_NAME);
		indidescr[10] = " WillamAD ";

		indicatorVector.addElement(Indicator.CCI_NAME);
		indidescr[11] = " Commodity Channel Index ";

		indicatorVector.addElement(Indicator.DPO_NAME);
		indidescr[12] = " DPO ";

		indicatorVector.addElement(Indicator.EOM_NAME);
		indidescr[13] = " EMO ";

//custom indicator
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<indList.length;i++)
		{
			String s = indList[i];
			
			if(s != null && !s.equals("") && s.toLowerCase().indexOf(".fl") != -1)
			{
				s = s.substring(0,s.indexOf(".fl"));
				indicatorVector.addElement(s);
				indidescr[14+i] = s;
				sb.append("|"+s+"|");
			}
		}
		customIndList = sb.toString().toLowerCase();
		

	}
	public static HashMap indfileMapping=null;
	public static String AWESOMEIND="Awesome Indicator";
	
	public static String customIndList = null;//"|Awesome Indicator|".toLowerCase();
	public static boolean isCustom(String indName)
	{
		indName = indName.toLowerCase();
		if(customIndList.indexOf("|"+indName+"|") == -1)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	
	/**
	 * @param indname
	 * @return
	 */
	public static Indicator getIndicatorObject(String indname)
	{
		if(indname.equalsIgnoreCase(Indicator.SMA_NAME))
			return new Indicator(Indicator.SMA_NAME,1,Indicator.SMA);
		else if(indname.equalsIgnoreCase(Indicator.MMA_NAME))
			return new Indicator(Indicator.MMA_NAME,1,Indicator.MMA);
		else if(indname.equalsIgnoreCase(Indicator.WMA_NAME))
			return new Indicator(Indicator.WMA_NAME,1,Indicator.WMA);
		else if(indname.equalsIgnoreCase(Indicator.EMA_NAME))
			return new Indicator(Indicator.EMA_NAME,1,Indicator.EMA);
		else if(indname.equalsIgnoreCase(Indicator.MACD_NAME))
			return new Indicator(Indicator.MACD_NAME,3,Indicator.MACD);
		else if(indname.equalsIgnoreCase(Indicator.BB_NAME))
			return new Indicator(Indicator.BB_NAME,2,Indicator.BB);
		else if(indname.equalsIgnoreCase(Indicator.RSI_NAME))
			return new Indicator(Indicator.RSI_NAME,1,Indicator.RSI);
		else if(indname.equalsIgnoreCase(Indicator.Stochastic_NAME))
			return new Indicator(Indicator.Stochastic_NAME,2,Indicator.Stochastic);
		else if(indname.equalsIgnoreCase(Indicator.William_NAME))
			return new Indicator(Indicator.William_NAME,1,Indicator.William);
		else if(indname.equalsIgnoreCase(Indicator.VolumeOscilator_NAME))
			return new Indicator(Indicator.VolumeOscilator_NAME,2,Indicator.VolumeOscilator);
		
		else if(indname.equalsIgnoreCase(Indicator.MFI_NAME))
			return new Indicator(Indicator.MFI_NAME,1,Indicator.MFI);
	
		else if(indname.equalsIgnoreCase(Indicator.ONBALANCEVOLUME_NAME))
				return new Indicator(Indicator.ONBALANCEVOLUME_NAME,0,Indicator.ONBALANCEVOLUME);

		else if(indname.equalsIgnoreCase(Indicator.AccDistri_NAME))
				return new Indicator(Indicator.AccDistri_NAME,0,Indicator.AccDistri);

		else if(indname.equalsIgnoreCase(Indicator.ChaikinOscillator_NAME))
				return new Indicator(Indicator.ChaikinOscillator_NAME,0,Indicator.ChaikinOscillator);
	
		else if(indname.equalsIgnoreCase(Indicator.AverageTrueRange_NAME))
				return new Indicator(Indicator.AverageTrueRange_NAME,1,Indicator.AverageTrueRange);

		else if(indname.equalsIgnoreCase(Indicator.AroopOscillator_NAME))
				return new Indicator(Indicator.AroopOscillator_NAME,1,Indicator.AroopOscillator);

		else if(indname.equalsIgnoreCase(Indicator.ChaikinMoneyFlow_NAME))
				return new Indicator(Indicator.ChaikinMoneyFlow_NAME,1,Indicator.ChaikinMoneyFlow);

		else if(indname.equalsIgnoreCase(Indicator.WillamAD_NAME))
					return new Indicator(Indicator.WillamAD_NAME,0,Indicator.WillamAD);
		else if(indname.equalsIgnoreCase(Indicator.CCI_NAME))
					return new Indicator(Indicator.CCI_NAME,1,Indicator.CCI);
		else if(indname.equalsIgnoreCase(Indicator.DPO_NAME))
					return new Indicator(Indicator.DPO_NAME,1,Indicator.DPO);
		else if(indname.equalsIgnoreCase(Indicator.EOM_NAME))
					return new Indicator(Indicator.EOM_NAME,1,Indicator.EOM);

			
		return null;
	}







	public static int[] getDefaultValue(String indname)
	{
		int[] retval= new int[5];
		
		if(indname.equalsIgnoreCase(Indicator.SMA_NAME) || indname.equalsIgnoreCase(Indicator.MMA_NAME) || indname.equalsIgnoreCase(Indicator.WMA_NAME)
		|| indname.equalsIgnoreCase(Indicator.EMA_NAME) || indname.equalsIgnoreCase(Indicator.RSI_NAME) || indname.equalsIgnoreCase(Indicator.William_NAME)
		|| indname.equalsIgnoreCase(Indicator.MFI_NAME) || indname.equalsIgnoreCase(Indicator.AverageTrueRange_NAME) || indname.equalsIgnoreCase(Indicator.AroopOscillator_NAME)
		|| indname.equalsIgnoreCase(Indicator.ChaikinMoneyFlow_NAME) || indname.equalsIgnoreCase(Indicator.CCI_NAME) || indname.equalsIgnoreCase(Indicator.DPO_NAME)
		|| indname.equalsIgnoreCase(Indicator.EOM_NAME))
		{
			retval[0] = 14;
		}
		else if(indname.equalsIgnoreCase(Indicator.MACD_NAME))
		{
			retval[0] = 12;
			retval[1] = 26;
			retval[2] = 9;
		}
		else if(indname.equalsIgnoreCase(Indicator.BB_NAME))
		{
			retval[0] = 20;
			retval[1] = 2;
		}
		else if(indname.equalsIgnoreCase(Indicator.Stochastic_NAME))
		{
			retval[0] = 5;
			retval[1] = 3;
		}
		else if(indname.equalsIgnoreCase(Indicator.VolumeOscilator_NAME))
		{
			retval[0] = 12;
			retval[1] = 26;
			retval[2] = 9;
		}
		return retval;
	}

	public static String[] getPeriodName(String indname)
	{
		String[] retval= {"Period 1","Period 2","Period 3","Period 4","Period 5",};
		
		if(indname.equalsIgnoreCase(Indicator.SMA_NAME) || indname.equalsIgnoreCase(Indicator.MMA_NAME) || indname.equalsIgnoreCase(Indicator.WMA_NAME)
		|| indname.equalsIgnoreCase(Indicator.EMA_NAME) || indname.equalsIgnoreCase(Indicator.RSI_NAME) || indname.equalsIgnoreCase(Indicator.William_NAME)
		|| indname.equalsIgnoreCase(Indicator.MFI_NAME) || indname.equalsIgnoreCase(Indicator.AverageTrueRange_NAME) || indname.equalsIgnoreCase(Indicator.AroopOscillator_NAME)
		|| indname.equalsIgnoreCase(Indicator.ChaikinMoneyFlow_NAME) || indname.equalsIgnoreCase(Indicator.CCI_NAME) || indname.equalsIgnoreCase(Indicator.DPO_NAME)
		|| indname.equalsIgnoreCase(Indicator.EOM_NAME))
		{
			retval[0] = "No of Days";
		}
		else if(indname.equalsIgnoreCase(Indicator.MACD_NAME))
		{
			retval[0] = "Low of Days";
			retval[1] = "High of Days";;
			retval[2] = "Signal(days)";
		}
		else if(indname.equalsIgnoreCase(Indicator.BB_NAME))
		{
			retval[0] = "No of days";
			retval[1] = "% difference";
		}
		else if(indname.equalsIgnoreCase(Indicator.Stochastic_NAME))
		{
			retval[0] = "%K periods";
			retval[1] = "%D periods";
		}
		else if(indname.equalsIgnoreCase(Indicator.VolumeOscilator_NAME))
		{
			retval[0] = "Low of Days";
			retval[1] = "High of Days";;
			retval[2] = "Signal(days)";
		}
		return retval;
	}




	/**
	 * @param i
	 * @return
	 */
	public static String getIndicatorNameFromID(int i)
	{
		if(Indicator.SMA==i)
			return Indicator.SMA_NAME;
		else if(Indicator.MMA==i)
			return Indicator.MMA_NAME;
		else if(Indicator.WMA==i)
			return Indicator.WMA_NAME;
		else if(Indicator.EMA==i)
			return Indicator.EMA_NAME;
		else if(Indicator.MACD==i)
			return Indicator.MACD_NAME;
		else if(Indicator.BB==i)
			return Indicator.BB_NAME;
		else if(Indicator.William==i)
			return Indicator.William_NAME;
		else if(Indicator.RSI==i)
			return Indicator.RSI_NAME;
		else if(Indicator.VolumeOscilator==i)
			return Indicator.VolumeOscilator_NAME;
		else if(Indicator.MFI==i)
			return Indicator.MFI_NAME;
		else if(Indicator.Stochastic==i)
			return Indicator.Stochastic_NAME;
		else if(Indicator.ONBALANCEVOLUME==i)
					return Indicator.ONBALANCEVOLUME_NAME;
		else if(Indicator.ChaikinOscillator==i)
					return Indicator.ChaikinOscillator_NAME;

		else if(Indicator.AccDistri==i)
					return Indicator.AccDistri_NAME;
		
		else if(Indicator.AverageTrueRange==i)
					return Indicator.AverageTrueRange_NAME;
		

		else if(Indicator.AroopOscillator==i)
					return Indicator.AroopOscillator_NAME;

		else if(Indicator.ChaikinMoneyFlow==i)
					return Indicator.ChaikinMoneyFlow_NAME;

		else if(Indicator.WillamAD==i)
						return Indicator.WillamAD_NAME;

		else if(Indicator.CCI==i)
						return Indicator.CCI_NAME;
		else if(Indicator.DPO==i)
						return Indicator.DPO_NAME;
		else if(Indicator.EOM==i)
						return Indicator.EOM_NAME;
		else if(Indicator.CUSTOM==i)
						return Indicator.CUSTOM_NAME;

		else
			return "";
		
	}
}

package com.stockfaxforu.frontend;

import com.stockfaxforu.component.Indicator;

public interface ManageIndicatorInterface 
{
	public void addIndicator(Indicator selInd);
	public void removeIndicator(Indicator selInd);
	public void addCustomIndicator();
	public int getIndiactorType();
	public static int INDICATOR=1;
	public static int STRATEGY=2;
	
	
	
}

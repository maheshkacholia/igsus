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
public class ManageStrategy
{

	/**
	 * @return
	 */
	public static Vector strategyVector = null;

	public static Vector getStrategyName()
	{
		if(strategyVector==null)
			loadStrategy();
		return strategyVector;	
		// TODO Auto-generated method stub
		
	}
	
	private static void loadStrategy()
	{
		strategyVector = new Vector();
		File f = new File(StockConstants.NEW_STR_DIR);
		String[] strategyList = f.list();
		int x = strategyList.length;
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<strategyList.length;i++)
		{
			String s = strategyList[i];
			
			if(s != null && !s.equals("") && s.toLowerCase().indexOf(".fl") != -1)
			{
				s = s.substring(0,s.indexOf(".fl"));
				strategyVector.addElement(s);
			}
		}
		
	}
}

/*
 * Created on Feb 25, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.frontend;

import java.util.HashMap;
import java.util.Vector;

import com.stockfaxforu.component.Indicator;

/**
 * @author sdeopura
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public interface PanelForGraphImpl
{
	public void searchStockUpdate(String symbol);
	public void destroyThread();
	public void resize(int x,int y);
	public void drawStrategy(Vector newVector);
	public HashMap getStrategyHashMap();
	public Vector getStrategyVector();
	public Vector getInputVector();
	public void executeCustmizedIndicator(String fileName, String formula);
	public void  addIndicator(Indicator selInd);

	public void setYear(String selName);
	public void setGraphType(String selName);
	public void setResolution(String selName);
	public void setZooming(String selName);
//	public void executeBackTesting();
	
	public void setDate(int year,String startDate,String endDate);
	
	
//	public void updateSize();
	
}

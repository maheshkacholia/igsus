/*
 * Created on May 13, 2009
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.frontend;

import java.util.Vector;

/**
 * @author Mahesh
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public interface StrategyDataInterface
{
	public void drawStrategy(Vector v);
	public Vector getStrategyVector();
	public void drawStrategy(Vector strategyVector, String string,
			String string2, String string3);
	
}

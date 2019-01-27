/*
 * Created on Jun 21, 2009
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.component;

import java.awt.Graphics;

/**
 * @author Mahesh
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public interface MainGraphInterface
{
	public void drawCursor(int xpos, int ypos, int type, Graphics g);
	public String getDateForXpos(int x_pos) throws Exception;
}

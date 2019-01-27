/*
 * Created on May 17, 2009
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.util;

import java.awt.Color;
import java.util.HashMap;

import com.stockfaxforu.config.ConfigUtility;

/**
 * @author Mahesh
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class GetColor
{

	public static HashMap colorHash=null;
	public static Color getColor(String colorName)
	{
		Color c = ConfigUtility.getColor(colorName);

			return c;
	}
	private static void loadColor()
	{
		colorHash = new HashMap();
		colorHash.put("BLACK",Color.black);
		colorHash.put("WHITE",Color.white);
		colorHash.put("RED",Color.red);
		colorHash.put("ORANGE",Color.orange);
		colorHash.put("BLUE",Color.blue);
		colorHash.put("PINK",Color.pink);
		colorHash.put("GRAY",Color.gray);
		colorHash.put("CYAN",Color.cyan);
		colorHash.put("DARKGRAY",Color.darkGray);
		colorHash.put("LIGHTGRAY",Color.lightGray);
		colorHash.put("MAGENTA",Color.magenta);
		colorHash.put("YELLOW",Color.yellow);
		
	}
	
}

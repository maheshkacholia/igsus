/*
 * Created on Feb 14, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.component;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/**
 * @author sdeopura
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CompanyName extends MYDimension implements MYDimensionImpl
{

	/**
	 * @param startx
	 * @param starty
	 * @param endx
	 * @param endy
	 * @param componenttype
	 */
	public CompanyName(int startx, int starty, int endx, int endy, int componenttype)
	{
		super(startx, starty, endx, endy, componenttype);
		// TODO Auto-generated constructor stub
	}
	public CompanyName(int startx, int starty,String note)
	{
		super(startx, starty, 0, 0,MYDimension.COMPANYNAME);
		int len = note.length();
		int width = len*9;
		this.endx = this.startx + width;
		this.note = note; 

		// TODO Auto-generated constructor stub
	}
	
	public void drawComponent(Graphics  g)
	{
		Color c = g.getColor();
		Font f = g.getFont();

		
		g.setColor(this.fontcolor);
		g.setFont(new Font("Arial",Font.BOLD,12));
		String s = "ACC ( ACC Limited ) ";
		g.drawBytes(s.getBytes(), 0, s.length(), startx,starty);

		g.setColor(c);
		g.setFont(f);

	}

}

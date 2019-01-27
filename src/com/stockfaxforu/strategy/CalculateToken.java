/*
 * Created on May 4, 2009
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.strategy;

/**
 * @author Mahesh
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CalculateToken
{

	public int pos;

	public String para;

	/**
	 * @param indStr
	 * @param pos
	 */
	public CalculateToken(String indStr, int pos)
	{
		StringBuffer sb = new StringBuffer();
		char c = indStr.charAt(pos);
		while(c != ']' && pos < indStr.length())
		{
			
			sb.append(c);	
			pos++;
			c = indStr.charAt(pos); 
		}
		if(pos < indStr.length())
		{
			sb.append(c);
			this.pos = pos + 1;
			
		}
		para = sb.toString();
		// TODO Auto-generated constructor stub
	}
}

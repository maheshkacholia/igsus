/*
 * Created on May 25, 2009
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.swingcomponent;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

/**
 * @author Mahesh
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class IGSLabel extends JLabel
{
	public IGSLabel(String name)
	{
		super(name);
		this.setForeground(Color.black);
		this.setFont(new Font("Arial",Font.BOLD,12));
	}
}

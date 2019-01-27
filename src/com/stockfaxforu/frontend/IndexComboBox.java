/*
 * Created on May 6, 2009
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.frontend;

import java.awt.Color;
import java.awt.Font;
import java.util.Vector;

import javax.swing.JComboBox;

import com.stockfaxforu.util.IndexUtility;

/**
 * @author Mahesh
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class IndexComboBox extends JComboBox
{
	public IndexComboBox(Vector v)
	{
		super(v);

		ComboBoxRendererSymbol custom = new ComboBoxRendererSymbol(v);
		
		this.setFont(new Font("Arial", Font.PLAIN, 12));
		this.setForeground(Color.black);
		this.setRenderer(custom);
	
	}
}

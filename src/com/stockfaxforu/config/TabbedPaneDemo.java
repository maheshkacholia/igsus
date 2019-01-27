/*
 * Created on Nov 2, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.config;

/*
 * Copyright (c) 1995 - 2008 Sun Microsystems, Inc.  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Sun Microsystems nor the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

/*
 * TabbedPaneDemo.java requires one additional file:
 *   images/middle.gif.
 */

import javax.swing.JComboBox;
import javax.swing.JTabbedPane;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.util.Vector;

public class TabbedPaneDemo extends JPanel
{
	public TabbedPaneDemo()
	{
		super(new GridLayout(1, 1));

		JTabbedPane tabbedPane = new JTabbedPane();

		JComponent panel1 = makeTextPanel("Panel #1");
		tabbedPane.addTab("Graph Display", panel1);
		//       tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

		JComponent panel2 = makeTextPanel("Panel #2");
		tabbedPane.addTab("Auto Audate", panel2);
		//      tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);

		JComponent panel3 = makeTextPanel("Panel #3");
		tabbedPane.addTab("Default Color", panel3);
		//    tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);

		JComponent panel4 = makeTextPanel("Panel #4 (has a preferred size of 410 x 50).");
		panel4.setPreferredSize(new Dimension(410, 50));
		tabbedPane.addTab("Tab 4", panel4);
		//  tabbedPane.setMnemonicAt(3, KeyEvent.VK_4);

		//Add the tabbed pane to this panel.
		add(tabbedPane);

		//The following line enables to use scrolling tabs.
		//tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
	}

	protected JComponent makeTextPanel(String text)
	{
		int x = 10, y = 10;
		JPanel panel = new JPanel(null);

		JLabel bgcolor = new JLabel("Graph Back Ground Color");
		bgcolor.setBounds(x, y, 200, 20);
		panel.add(bgcolor);

		String[] v = ConfigUtility.getColors();
		JComboBox bgcolorcombo = new JComboBox(v);
		bgcolorcombo.setBounds(225, y, 100, 20);
		panel.add(bgcolorcombo);

		y = y+25;
		JLabel clinecolor = new JLabel("Closing Line Color");
		clinecolor.setBounds(x, y, 200, 20);
		panel.add(clinecolor);

		//		 String[] v = ConfigUtility.getColors();
		JComboBox clinecolorcb = new JComboBox(v);
		clinecolorcb.setBounds(225, y, 100, 20);
		panel.add(clinecolorcb);


		y = y+25;
		JLabel upcolor = new JLabel("Price Up Color");
		upcolor.setBounds(x, y, 200, 20);
		panel.add(upcolor);

		//		 String[] v = ConfigUtility.getColors();
		JComboBox upcolorcb = new JComboBox(v);
		upcolorcb.setBounds(225, y, 100, 20);
		panel.add(upcolorcb);

		y = y+25;
		JLabel downcolor = new JLabel("Price Down Color");
		downcolor.setBounds(x, y, 200, 20);
		panel.add(downcolor);

		//		 String[] v = ConfigUtility.getColors();
		JComboBox downcolorcb = new JComboBox(v);
		downcolorcb.setBounds(225, y, 100, 20);
		panel.add(downcolorcb);


		y = y+25;
		JLabel hpriceline = new JLabel("Horizontal Price Line Color");
		downcolor.setBounds(x, y, 200, 20);
		panel.add(hpriceline);

		//		 String[] v = ConfigUtility.getColors();
		JComboBox hpricelinecb = new JComboBox(v);
		hpricelinecb.setBounds(225, y, 100, 20);
		panel.add(hpricelinecb);


		y = y+25;
		JLabel vpriceline = new JLabel("Vertical Price Line Color");
		downcolor.setBounds(x, y, 200, 20);
		panel.add(hpriceline);

		//		 String[] v = ConfigUtility.getColors();
		JComboBox vpricelinecb = new JComboBox(v);
		vpricelinecb.setBounds(225, y, 100, 20);
		panel.add(vpricelinecb);


		return panel;
	}

	/** Returns an ImageIcon, or null if the path was invalid. */
	protected static ImageIcon createImageIcon(String path)
	{
		java.net.URL imgURL = TabbedPaneDemo.class.getResource(path);
		if (imgURL != null)
		{
			return new ImageIcon(imgURL);
		}
		else
		{
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}

	/**
	 * Create the GUI and show it.  For thread safety,
	 * this method should be invoked from
	 * the event dispatch thread.
	 */
	private static void createAndShowGUI()
	{
		//Create and set up the window.
		JFrame frame = new JFrame("TabbedPaneDemo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 800);
		//Add content to the window.
		frame.getContentPane().add(new TabbedPaneDemo(), BorderLayout.CENTER);

		//Display the window.
		//       frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args)
	{
		//Schedule a job for the event dispatch thread:
		//creating and showing this application's GUI.
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				//Turn off metal's use of bold fonts
				UIManager.put("swing.boldMetal", Boolean.FALSE);
				createAndShowGUI();
			}
		});
	}
}

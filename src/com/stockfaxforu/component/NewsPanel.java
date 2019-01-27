/*
 * Created on May 1, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.component;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.stockfaxforu.util.Utility;
public class NewsPanel extends JPanel implements MouseListener
{
	static int i=0;
	public static String msg = "";
	public static String url = "";
	
	public static Color[] col = { Color.white, Color.red, Color.green, Color.lightGray, Color.magenta };
	public static JLabel temp=null;
	public NewsPanel()
	{
		super(null);
		temp = new JLabel();
		temp.setBounds(0,0,1000,20);
		add(temp,null);
	}
	public void repaint()
	{
		if(i>=col.length)
			i=0;
		int xinc=10;
		int yinc=10;
		if(temp==null)
		{
			temp = new JLabel();
			temp.setBounds(0,0,1000,20);
			add(temp,null);
			temp.addMouseListener(this);
		}
		else
		{
			temp.setForeground(col[i]);
			temp.setText(msg);		
			super.repaint();
			
		}
		i++;
	
	}
	public void mouseEntered(MouseEvent arg0)
	{
//		// ln("mouseEntered");
		Object o = (Object) arg0.getSource();
		if (o instanceof JLabel)
		{
			this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

			JLabel cb = (JLabel) arg0.getSource();
			cb.setForeground(Color.yellow);
		}
	}
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	public void mouseExited(MouseEvent arg0)
	{
		this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

		Object o = (Object) arg0.getSource();
		if (o instanceof JLabel)
		{
			JLabel cb = (JLabel) arg0.getSource();
			if(i>=col.length)
				i=0;
			cb.setForeground(col[i]);

		}
	}


	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	public void mouseClicked(MouseEvent arg0)
	{
		Object o = (Object) arg0.getSource();
		if (o instanceof JLabel)
		{
			JLabel cb = (JLabel) arg0.getSource();
			if(url==null || url.equals(""))
				url="http://www.iguidestocks.com";
			Utility.openURL(url);		

		}

	}
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	public void mousePressed(MouseEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	public void mouseReleased(MouseEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */

	
}
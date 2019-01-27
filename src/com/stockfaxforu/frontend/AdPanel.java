/*
 * Created on Sep 2, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.frontend;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.JEditorPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import com.stockfaxforu.util.StockConstants;
import com.stockfaxforu.util.Utility;

/**
 * @author sdeopura
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class AdPanel extends JEditorPane implements HyperlinkListener
{

	/* (non-Javadoc)
	 * @see javax.swing.event.HyperlinkListener#hyperlinkUpdate(javax.swing.event.HyperlinkEvent)
	 */
	 public AdPanel(StringBuffer s) throws Exception
	 {
			this.setContentType("text/html"); 
			this.setEditable(false); 
			this.setText(s.toString()); 
			this.addHyperlinkListener(this);
	 }
	
	 public AdPanel(String urlstr) throws Exception
	 {
			this.setContentType("text/html"); 
			this.setEditable(false); 
		//	String s = Utility.getUrlContent(urlstr); 
			this.setText("<table><tr><td>Hi this is maheshaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa</td></tr></table>"); 
			this.addHyperlinkListener(this);
	 }
	 public void updateContent(String s)
	 {
	 	this.setText(s);
	 }
	public void hyperlinkUpdate(HyperlinkEvent hyperlinkEvent)
	{
		HyperlinkEvent.EventType type = hyperlinkEvent.getEventType();
		final URL url = hyperlinkEvent.getURL();
		if (type == HyperlinkEvent.EventType.ENTERED)
		{
		}
		else if (type == HyperlinkEvent.EventType.ACTIVATED)
		{
			Runtime runtime = Runtime.getRuntime();
			try
			{
				Process process =	runtime.exec(StockConstants.IEEXPLORERLOC + "   -new " + url);
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
			}
	
		}

		// TODO Auto-generated method stub
		
	}
	
}

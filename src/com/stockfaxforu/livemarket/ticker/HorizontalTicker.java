package com.stockfaxforu.livemarket.ticker;

import java.applet.Applet;
import java.applet.AppletContext;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

import com.stockfaxforu.util.Utility;

public class HorizontalTicker 
{

    String tickerData[] = null;
    String tmpTickerData[] = null;
    String tickerId = null;
    String tId = null;
    String dataSrc = null;
    String typeOfData = null;
    String upImg = null;
    String downImg = null;
    Thread scroller = null;
    int delay1 = 0;
    int delay2 = 0;
    int delay = 0;
    int fdelay = 0;
    int totalDelay = 0;
    boolean scroll = false;
    boolean doSetTickerId = false;
    boolean newData = false;
    boolean resetting = false;
    Graphics offg = null;
    Graphics offg2 = null;
    Image offimg = null;
    Image offimg2 = null;
    Image upArrow = null;
    Image downArrow = null;
    MediaTracker tracker = null;
    int w = 0;
    int h = 0;
    int lt = 0;
    int rt = 0;
    int x = 0;
    int inc = 0;
    int t = 0;
    int f = 0;
    int imgX = 0;
    int imgY = 0;
    int imgW = 0;
    int imgH = 0;
    final int gap1 = 10;
    final int gap2 = 30;
    Color backColor = null;
    Color fontColor = null;
    Font font = null;
    Font bfont = null;
    FontMetrics fm = null;
    FontMetrics bfm = null;
    int mX = 0;
    boolean mouseIn = false;
    boolean paused = false;
    boolean fast = false;
    boolean goLeft = false;
    Cursor def_cur = null;
    Cursor hand_cur = null;
    String userId = null;
    TickerDataObject tdo[] = null;
    Vector tdoVec = null;
    final int maxParts = 6;
    boolean fromNSE = false;

    public HorizontalTicker()
    {
  //  	init();
    }

	private String[] getData()
	  {
		  try
		  {
			  URL url = new URL("http://www.nseindia.com/TickerFeed");
			  HttpMessage httpmessage = new HttpMessage(url);
			  Properties properties = new Properties();
			  properties.put("type", "FUTIDX");
//			  properties.put("tickerid", tickerId);
//			  properties.put("userid", userId);
			  InputStream inputstream = httpmessage.sendGetMessage(properties);
			  ObjectInputStream objectinputstream = new ObjectInputStream(inputstream);
			  Object obj = objectinputstream.readObject();
			  String as[] = (String[])obj;
			  inputstream.close();
			  objectinputstream.close();
			  return as;
		  }
		  catch(Exception exception)
		  {
			  return null;
		  }
	  }
    public String[] getLiveData(int market)
    {
//    	int market=0;
        try
        {
  			URL url = new URL("http://www.nseindia.com/CustTickerFeed");
			   HttpMessage httpmessage = new HttpMessage(url);
			   Properties properties = new Properties();
/*			   properties.put("type", "");
			   properties.put("tickerid", "6");
			   properties.put("userid", "mkacholia");
			   InputStream inputstream = httpmessage.sendGetMessage(properties);
//tickerid 2-cnxit 0-nifty  1-jr nifty  
**/

			   properties.put("tickerid", market+"");
//			   properties.put("tickerid", ""+market);
//			   properties.put("userid", "mkacholia");
			   InputStream inputstream = httpmessage.sendGetMessage(properties);

   
        	
//			URL url = new URL("http://www.nseindia.com/TickerFeed?type=CM&tickerid=&userid=");
     
/*			URLConnection conn = url.openConnection();
			
			System.getProperties().put("proxySet", "true");
			System.getProperties().put("proxyHost", "PHXPSGW.AEXP.COM");
			System.getProperties().put("proxyPort", "8080");
			sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
			String useridpwd = "sdeopu" + ":" + "jp8236";
			String encodedUserPwd = encoder.encode(useridpwd.getBytes());
			conn.setRequestProperty("Proxy-Authorization","Basic " + encodedUserPwd);
			
			InputStream inputstream = conn.getInputStream();
*/

			ObjectInputStream objectinputstream = new ObjectInputStream(inputstream);
		    Object obj = objectinputstream.readObject();
            String as[] = (String[])obj;
            for(int i=0;i<as.length;i++)
            {
            }	
            inputstream.close();
            objectinputstream.close();
            return as;
        }
        catch(Exception exception)
        {
            return null;
        }
    
    
    }

	private void getData1()
		{
			try
			{
				String data = "start_price=&end_price=&priceScreen%5B%5D=gain_by_percent&percentage=gained&percent_gain=-30&up_price=&down_price=&submit=submit";
				URL url = new URL("http://www.way2wealth.com/screener/output_price.php");
     
				URLConnection conn = url.openConnection();
			
				System.getProperties().put("proxySet", "true");
				System.getProperties().put("proxyHost", "PHXPSGW.AEXP.COM");
				System.getProperties().put("proxyPort", "8080");
				sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
				String useridpwd = "dgup01" + ":" + "dg0927";
				String encodedUserPwd = encoder.encode(useridpwd.getBytes());
				conn.setRequestProperty("Proxy-Authorization","Basic " + encodedUserPwd);
			
				conn.setDoOutput(true);
				OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
				wr.write(data);
				wr.flush();

			
				InputStream inputstream = conn.getInputStream();
				BufferedReader buff = 	new BufferedReader(new InputStreamReader(inputstream));
				String line = "";
				while((line = buff.readLine()) != null)
				{
				}	
				buff.close();
				inputstream.close();
			}
			catch(Exception exception)
			{
			}
		}



    public void destroy()
    {
        scroller = null;
        scroll = false;
    }

    public void mouseEntered(MouseEvent mouseevent)
    {
        mX = mouseevent.getX();
        if(mX > lt && mX < rt)
            mouseIn = true;
    }

    public void mouseExited(MouseEvent mouseevent)
    {
        mouseIn = false;
    }

    public void mouseDragged(MouseEvent mouseevent)
    {
    }

    public void mouseMoved(MouseEvent mouseevent)
    {
        mX = mouseevent.getX();
        if(mX > lt && mX < rt)
        {
            mouseIn = true;
            if(paused)
            {
                for(int i = 0; i < tickerData.length; i++)
                {
                    if(!tdo[i].in || mX <= tdo[i].start || mX >= tdo[i].end)
                        continue;
                    offg.drawLine(tdo[i].lStart, h - 2, tdo[i].lEnd, h - 2);
 //                   repaint();
                    break;
                }

            }
        }
        else
        {
            mouseIn = false;
        }
    }

    public void mouseClicked(MouseEvent mouseevent)
    {
    }

    public void mousePressed(MouseEvent mouseevent)
    {
        mX = mouseevent.getX();
        if((mouseevent.getModifiers() & 0x10) == 16)
        {
            for(int i = 0; i < tickerData.length; i++)
            {
                if(tdo[i].link.length() <= 0 || mX < tdo[i].start || mX > tdo[i].end)
                    continue;
                URL url = null;
                try
                {
                    if(tdo[i].seg == 'c')
                    {
                        String s = tdo[i].link;
                        s = replaceAmpersand(s);
                        String s2 = s.substring(0, s.length() - 2);
                        s2 = replaceAmpersand(s2);
                        String s3 = "http://www.nseindia.com/marketinfo/equities/cmquote.jsp?key=" + s + "N&symbol=" + s2 + "&flag=0";
                        url = new URL(s3);
                    }
                    else
                    {
                        String s1 = tdo[i].link;
                        StringTokenizer stringtokenizer = new StringTokenizer(s1, " ");
                        int j = stringtokenizer.countTokens();
                        String s4 = "";
                        String s5 = "";
                        for(int k = 0; stringtokenizer.hasMoreElements(); k++)
                        {
                            String s6 = stringtokenizer.nextToken();
                            s6 = replaceAmpersand(s6);
                            if(k == 1)
                                s4 = s6;
                            if(k == 2)
                                s6 = s6.substring(0, 2) + s6.substring(3, 6).toUpperCase() + s6.substring(7);
                            if(k == 4)
                                s6 = roundOff(s6);
                            s5 = s5 + s6;
                        }

                        if(j == 3)
                            s5 = s5 + "--";
                        String s7 = "http://www.nseindia.com/marketinfo/fo/foquote.jsp?key=" + s5 + "&symbol=" + s4 + "&flag=0";
                        url = new URL(s7);
                    }
                }
                catch(MalformedURLException malformedurlexception) { }
  //              getAppletContext().showDocument(url, "f2");
                break;
            }

        }
    }

    public void mouseReleased(MouseEvent mouseevent)
    {
    }

    private String replaceAmpersand(String s)
    {
        String s1 = s;
        int i = s.indexOf("&");
        if(i != -1)
        {
            s1 = s.substring(0, i);
            s1 = s1 + "%26";
            s1 = s1 + s.substring(i + 1);
        }
        return s1;
    }

    public void pnp()
    {
        paused = !paused;
    }

    public void toggle_speed()
    {
        if(delay == delay2)
            delay = delay1;
        else
            delay = delay2;
    }

    public void reverse()
    {
        inc = -inc;
        goLeft = !goLeft;
        if(goLeft)
            x = rt;
        else
            x = lt;
    }

    private void reset()
    {
        resetting = true;
        if(goLeft)
            x = rt;
        else
            x = lt;
    }

    private String roundOff(String s)
    {
        int i = s.indexOf(".");
        int j = s.length();
        if(i != -1)
        {
            if(i == j - 2)
                s = s + "0";
        }
        else
        {
            s = s + ".00";
        }
        return s;
    }
    
    public static void main(String[]args)
    {
    		HorizontalTicker hor = new HorizontalTicker();
    		hor.getData();
    }	


}

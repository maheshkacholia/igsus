package com.stockfaxforu.livemarket;
import java.applet.Applet;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;


public class RefreshApplet extends Applet
    implements Runnable
{

    Thread refresher = null;
    boolean doRun = false;
    boolean doRefresh = false;
    String dataType = null;
    String mktTimeStr = null;
    String indicesStr = null;
    String globalStr = null;

    public RefreshApplet()
    {
    }

    public void destroy()
    {
        doRun = false;
        refresher = null;
        doRefresh = false;
    }

    private void fetchData()
    {
        try
        {
            URL url = new URL("http://www.nseindia.com/homepagedata");
     
			URLConnection conn = url.openConnection();
			
			System.getProperties().put("proxySet", "true");
			System.getProperties().put("proxyHost", "PHXPSGW.AEXP.COM");
			System.getProperties().put("proxyPort", "8080");
			sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
			String useridpwd = "dgup01" + ":" + "dg0927";
			String encodedUserPwd = encoder.encode(useridpwd.getBytes());
			conn.setRequestProperty("Proxy-Authorization","Basic " + encodedUserPwd);
			
            InputStream inputstream = conn.getInputStream();
            ObjectInputStream objectinputstream = new ObjectInputStream(inputstream);
            Object obj = objectinputstream.readObject();
            String as[] = (String[])obj;
            inputstream.close();
            objectinputstream.close();
 //           // ln("Got Data");
   //         // ln("Data length = " + as.length);
            mktTimeStr = as[0];
            indicesStr = as[1];
            globalStr = as[2];
   //         for(int i = 0; i < as.length; i++)
  //              // ln(i + " : " + as[i]);

        }
        catch(Exception exception)
        {
    //        // ln("Exception caught in fetchData : " + exception);
        }
    }

    public String getData(int i)
    {
      //  // ln("getData");
        doRefresh = true;
        String s = "";
        switch(i)
        {
        case 1: // '\001'
            s = mktTimeStr;
            break;

        case 2: // '\002'
            s = indicesStr;
            break;

        case 3: // '\003'
            s = globalStr;
            break;

        default:
            s = "Data Not Available";
            break;

        }
        // ln("type is : " + i);
        // ln("returning " + s);
        return s;
    }

    public void init()
    {
        // ln("Testing...");
    }

    public synchronized void initialize()
    {
        doRefresh = true;
    }

    public void run()
    {
        // ln("Inside run...");
        // ln("doRun is " + doRun);
        // ln("doRefresh is " + doRefresh);
        try
        {
            while(doRun) 
            {
                fetchData();
                try
                {
                    Thread.sleep(60000L);
                }
                catch(InterruptedException _ex) { }
            }

        }
        catch(Exception exception)
        {
            // ln("Exception caught in run : " + exception);
        }
    }

    public void start()
    {
        try
        {
            // ln("Starting thread...");
            doRun = true;
            refresher = new Thread(this);
            refresher.start();
        }
        catch(Exception exception)
        {
            // ln("Caught exception in start () : " + exception);
        }
    }

    public void stop()
    {
        // ln("Stopping thread...");
        doRun = false;
        doRefresh = false;
    }
    public static void main(String[] args)
    {
		RefreshApplet refreshapplet = new RefreshApplet();
		refreshapplet.fetchData();
		
    }
}

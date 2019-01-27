package com.stockfaxforu.livedata;
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
 //           System.out.println("Got Data");
   //         System.out.println("Data length = " + as.length);
            mktTimeStr = as[0];
            indicesStr = as[1];
            globalStr = as[2];
   //         for(int i = 0; i < as.length; i++)
  //              System.out.println(i + " : " + as[i]);

        }
        catch(Exception exception)
        {
    //        System.out.println("Exception caught in fetchData : " + exception);
        }
    }

    public String getData(int i)
    {
      //  System.out.println("getData");
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
        System.out.println("type is : " + i);
        System.out.println("returning " + s);
        return s;
    }

    public void init()
    {
        System.out.println("Testing...");
    }

    public synchronized void initialize()
    {
        doRefresh = true;
    }

    public void run()
    {
        System.out.println("Inside run...");
        System.out.println("doRun is " + doRun);
        System.out.println("doRefresh is " + doRefresh);
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
            System.out.println("Exception caught in run : " + exception);
        }
    }

    public void start()
    {
        try
        {
            System.out.println("Starting thread...");
            doRun = true;
            refresher = new Thread(this);
            refresher.start();
        }
        catch(Exception exception)
        {
            System.out.println("Caught exception in start () : " + exception);
        }
    }

    public void stop()
    {
        System.out.println("Stopping thread...");
        doRun = false;
        doRefresh = false;
    }
    public static void main(String[] args)
    {
		RefreshApplet refreshapplet = new RefreshApplet();
		refreshapplet.fetchData();
		
    }
}

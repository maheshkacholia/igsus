package com.stockfaxforu.livemarket.ticker;
import java.io.*;
import java.net.*;
import java.util.Enumeration;
import java.util.Properties;

import com.stockfaxforu.util.StockConstants;

public class HttpMessage
{

    URL servlet = null;
    String args = null;

    public HttpMessage(URL url)
    {
        servlet = null;
        args = null;
        servlet = url;
    }

    public InputStream sendGetMessage()
        throws IOException
    {
        return sendGetMessage(null);
    }

    public InputStream sendGetMessage(Properties properties)
        throws IOException
    {
        String s = "";
        if(properties != null)
            s = "?" + toEncodedString(properties);
        URL url = new URL(servlet.toExternalForm() + s);
        
        URLConnection urlconnection = url.openConnection();
		if (StockConstants.IS_PROXY)
			{
				System.getProperties().put("proxySet", "true");
				System.getProperties().put("proxyHost", StockConstants.PROXY);
				System.getProperties().put("proxyPort", StockConstants.PORT);
				sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
				String useridpwd = StockConstants.USERID + ":" + StockConstants.PWD;
				String encodedUserPwd = encoder.encode(useridpwd.getBytes());
				urlconnection.setRequestProperty("Proxy-Authorization", "Basic " + encodedUserPwd);
			}
	
        urlconnection.setUseCaches(false);
        InputStream inputstream = null;
		System.getProperties().put("proxySet", "true");
		System.getProperties().put("proxyHost", "PHXPSGW.AEXP.COM");
		System.getProperties().put("proxyPort", "8080");
		sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
		String useridpwd = "sdeopu" + ":" + "jp8236";
		String encodedUserPwd = encoder.encode(useridpwd.getBytes());
		urlconnection.setRequestProperty("Proxy-Authorization","Basic " + encodedUserPwd);

        inputstream = urlconnection.getInputStream();
        return inputstream;
    }

    public InputStream sendPostMessage()
        throws IOException
    {
        return sendPostMessage(null);
    }

    public InputStream sendPostMessage(Properties properties)
        throws IOException
    {
        String s = "";
        if(properties != null)
            s = toEncodedString(properties);
        URLConnection urlconnection = servlet.openConnection();
        urlconnection.setDoInput(true);
        urlconnection.setDoOutput(true);
        urlconnection.setUseCaches(false);
        urlconnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        DataOutputStream dataoutputstream = new DataOutputStream(urlconnection.getOutputStream());
        dataoutputstream.writeBytes(s);
        dataoutputstream.flush();
        dataoutputstream.close();
        return urlconnection.getInputStream();
    }

    private String toEncodedString(Properties properties)
    {
        StringBuffer stringbuffer = new StringBuffer();
        for(Enumeration enumeration = properties.propertyNames(); enumeration.hasMoreElements();)
        {
            String s = (String)enumeration.nextElement();
            String s1 = properties.getProperty(s);
            stringbuffer.append(URLEncoder.encode(s) + "=" + URLEncoder.encode(s1));
            if(enumeration.hasMoreElements())
                stringbuffer.append("&");
        }

        return stringbuffer.toString();
    }
}

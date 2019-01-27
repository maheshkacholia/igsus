package com.intraday;
///////////////////////////////////////////////////////////
// DeJaved by mDeJava v1.0. Copyright 1999 MoleSoftware. //
//       To download last version of this software:      //
//            http://molesoftware.hypermatr.net          //
//               e-mail:molesoftware@mail.ru             //
///////////////////////////////////////////////////////////

import java.io.*;
import java.net.*;
import java.util.Enumeration;
import java.util.Properties;

import com.stockfaxforu.util.Utility;

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
//        URL url = new URL(servlet.toExternalForm() + s);
		URLConnection urlconnection = null;
        
		   try
		   {
			   urlconnection = Utility.getURLConnection(servlet.toExternalForm() + s);
		   }
		   catch (Exception e)
		   {
			   // TODO Auto-generated catch block
		   }
        urlconnection.setUseCaches(false);
        InputStream inputstream = null;
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

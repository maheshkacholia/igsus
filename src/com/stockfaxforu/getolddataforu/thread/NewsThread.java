/*
 * Created on Apr 24, 2007
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.getolddataforu.thread;
import java.io.BufferedReader;
import java.io.File;
import java.io.RandomAccessFile;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.plaf.SliderUI;

import com.stockfaxforu.formulabuilder.MessageDiaglog;
import com.stockfaxforu.frontend.MainScreen;
import com.stockfaxforu.getolddataforu.loader.Loader;
import com.stockfaxforu.util.DateUtility;
import com.stockfaxforu.util.StockConstants;
import com.stockfaxforu.util.Utility;
/**
 * @author sdeopura
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class NewsThread implements Runnable
{
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public Vector newsvector = new Vector();
	public MainScreen mainscreen = null;
	public NewsThread(MainScreen mainscreen)
	{
		this.mainscreen = mainscreen;
	}
	public void run()
	{
		int i = 100;
		int newsveccounter = 0;
		
		File f = new File(StockConstants.INSTALL_DIR + "/result/");
		if(!f.exists())
		{
			try
			{
				f.mkdirs();
				
				IndexThread indexthread = new IndexThread();
				indexthread.status = IndexThread.RESULT;
				Thread th = new Thread(indexthread);
				th.start();

			}
			catch(Exception e)
			{			
			}
		}

		try
		{
			
		}
		catch(Exception e)
		{
		}
		
		while (true)
		{
			if(i % 4 == 0)
			{
				this.mainscreen.resetAd();
//				// ln("here"+i);
				
			}

			if(i % 12 == 0)
			{
				try
				{
					getAndSaveTips();
	
				}
				catch(Exception e)
				{
	
				}
			}
			if (i == 100 || i==0)
			{
				i = 0;

				try
				{
					getAndSaveNewFile();
				}
				catch(Exception e)
				{
				}
				try
				{
					Vector v = readNews();
					if(v != null && v.size() != 0)
					{
						newsvector = v;		
					}
				}
				catch(Exception e)
				{
					continue;					
				}

				
				newsveccounter = 0;
			}
			i++;

			try
			{
				Thread.sleep(5000);
			}
			catch(Exception e)
			{
			}

			if (newsvector == null || newsvector.size()==0)
				continue;

			if (newsveccounter < newsvector.size() - 1)
			{
				newsveccounter++;
			}
			else
			{
				newsveccounter=0;
			}
			String msg = (String) newsvector.elementAt(newsveccounter);
			StringTokenizer st = new StringTokenizer(msg,"{");
			if(st.hasMoreTokens())
				mainscreen.news.msg = st.nextToken();
			
			if(st.hasMoreTokens())
				mainscreen.news.url = st.nextToken();
			else
				mainscreen.news.url = "";
					
			mainscreen.news.repaint();

	
		
			
		}
	}
	/**
	 * 
	 */
	private void getAndSaveNewFile() throws Exception
	{
		
		String url = new String(StockConstants.MESSAGEURL);
		BufferedReader rd = Utility.getDataFromURL(url);
		String line;
		StringBuffer sb = new StringBuffer();
		while ((line = rd.readLine()) != null)
		{
			sb.append(line + "\n");
		}
		File f = new File(StockConstants.INSTALL_DIR + "/news.html");
		if (f.exists())
			f.delete();
		RandomAccessFile rfile = new RandomAccessFile(StockConstants.INSTALL_DIR + "/news.html", "rw");
		rfile.writeBytes(sb.toString());
		rfile.close();
		// TODO Auto-generated method stub
		
	}
	private void getAndSaveTips() throws Exception
		{
			
			String url = new String(StockConstants.IGSROOT+"gettip.jsp?"+StockConstants.ENCODEDUSERDETAIL+"&submit=tip");
			BufferedReader rd = Utility.getDataFromURL(url);
			String line;
			//	// ln("getting data for "+name);
			StringBuffer sb = new StringBuffer();
			while ((line = rd.readLine()) != null)
			{
				sb.append(line + "\n");
			}
//			// ln(sb.toString());
			
			if(sb.toString().equals(""))
				return;
			if(StockConstants.TIPS == null || !StockConstants.TIPS.equalsIgnoreCase(sb.toString()) )
			{
				try
				{
					StringTokenizer st = new StringTokenizer(sb.toString(),"|");
					String time = st.nextToken();
					String tip = st.nextToken();
					
					if(StockConstants.TIPS == null || StockConstants.TIPS.equals(""))
					{
						StockConstants.TIPS = sb.toString();
					
					}
					else
					{
						StockConstants.TIPS = sb.toString();

						MessageDiaglog msg = new MessageDiaglog(tip );
						StockConstants.TIPSBUFFER.append(StockConstants.TIPS+"\n");
						msg.show();
						
					}
						

				}
				catch(Exception e)
				{
				}
			}
		}
	private Vector readNews() throws Exception
	{
		RandomAccessFile rfile = null;
		String line = "";
		Vector retVec= new Vector();
		rfile = new RandomAccessFile(StockConstants.INSTALL_DIR + "/news.html", "r");
		while((line = rfile.readLine()) !=null)
		{
			retVec.addElement(line);
		
		}	 	
		rfile.close();
		return retVec;
	}	
}

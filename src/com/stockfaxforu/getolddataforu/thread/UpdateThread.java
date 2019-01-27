/*
 * Created on Apr 24, 2007
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.getolddataforu.thread;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.Calendar;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.plaf.SliderUI;

import com.stockfaxforu.component.MainGraphComponent;
import com.stockfaxforu.dataextractor.DataExtractor;
import com.stockfaxforu.formulabuilder.MessageDiaglog;
import com.stockfaxforu.frontend.MainScreen;
import com.stockfaxforu.frontend.PanelForGraphImpl;
import com.stockfaxforu.getolddataforu.loader.Loader;
import com.stockfaxforu.scan.util.ScanUtility;
import com.stockfaxforu.scan.util.StockScreenerThreadMaster;
import com.stockfaxforu.util.DateUtility;
import com.stockfaxforu.util.DownLoadFiles;
import com.stockfaxforu.util.DownloadIndexesSymbolMap;
import com.stockfaxforu.util.IndexUtility;
import com.stockfaxforu.util.StockConstants;
import com.stockfaxforu.util.Utility;
/**
 * @author sdeopura
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class UpdateThread implements Runnable
{
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public Vector newsvector = new Vector();
	public MainScreen mainscreen = null;

	public UpdateThread()
	{
	}
	public void run()
	{
		try
		{
	//		Thread.sleep(60000);
	//		DateUtility.updateDates();
		/*	File f = new File(StockConstants.INSTALL_DIR + "/output.csv");
			if (!f.exists())
			{
				Loader.getSharePriceForDate();
			}
		*/
			if (!Utility.isBasicUser())
			{
				Loader loader = new Loader();
				loader.loadDataForDate();
			}
			MessageDiaglog msg = new MessageDiaglog("Data is in sync ");
			Thread.sleep(1000);
			msg.dispose();
			downloadDataInMemory();
			updateOtherData();
			
			if ( MainScreen.getSingleton().panel instanceof PanelForGraphImpl )
			{
				((PanelForGraphImpl)(MainScreen.getSingleton().panel)).searchStockUpdate(StockConstants.SELECTED_STOCK);
			}
			showUpdate();	
		}
		catch(Exception e)
		{
			
		}
		try
		{
		}
		catch(Exception e)
		{
		}
	}
	private void downloadDataInMemory()
	{
		
		Vector categoryVect = IndexUtility.getCategories();
		for (int i=0;i<categoryVect.size();i++)
		{
			Vector v = IndexUtility.getIndexStockVector(categoryVect.get(i)+"");
			Vector out = new Vector();
			for(int j=0;j<v.size();j++)
			{
				HashMap hs = new HashMap();
				hs.put(MainGraphComponent.Symbol, v.get(j));
				out.add(hs);
			}
		
			StockScreenerThreadMaster runnable = new StockScreenerThreadMaster();
			runnable.symbolArrayList = out;
			int mysize = out.size();
			
			runnable.datadownloadscreen = null; 
			Thread thread = new Thread(runnable);
			thread.start();
			
		}
	}
	private void createMajorLists()
	{
		while(StockConstants.DATADOWNLOADED==false)
		{
			try
			{
				Thread.sleep(2000);
			}
			catch(Exception e)
			{
				
			}
		}
		
	}
	private void updateOtherData() throws Exception
	{
		Calendar c = Calendar.getInstance();
		Calendar c1 = null;

		String fileName = StockConstants.INSTALL_DIR + "/updateDate.obj";
		File f = new File(fileName);
		if(!f.exists())
		{
			updateData();
			ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream(fileName));
			o.writeObject(c);

		}
		else
		{

			try
			{
				ObjectInputStream o = new ObjectInputStream(new FileInputStream(fileName));
				
				c1 = (Calendar)o.readObject();
				c1.add(Calendar.DATE,7);
				
			}
			catch(Exception e )
			{
				f.delete();
			}
			if(c.after(c1))
			{
				updateData();
				ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream(fileName));
				o.writeObject(c);

			}
		
		}
				
	}
	private void updateData() throws Exception 
	{
//updating result		
		
		Vector v10 = Utility.getStockCode();
		
		updateAllDirectries();
//no finance data 		
//		DataExtractor.getStockDetail(v10);
//
		// TODO Auto-generated method stub
		
	}
	
	public void updateAllDirectries()
	{
		MessageDiaglog m = new MessageDiaglog("Updating New Strategies and Scans");
		try
		{
			Thread.sleep(1000);
		}
		catch(Exception e)
		{
			
		}
		m.dispose();
		StockConstants.SHOWDIRECTRYIND=false; 
		try {
			
			DownLoadFiles.saveSelectedDirectory();
	//	DownloadIndexesSymbolMap.saveIndex(StockConstants.COUNTRY);
			
			DownloadIndexesSymbolMap.saveMappingFile(StockConstants.COUNTRY);

		} catch (Exception e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}
		StockConstants.SHOWDIRECTRYIND=true;
		m = new MessageDiaglog(" New Strategies and Scans Updated");
		try
		{
			Thread.sleep(1000);
		}
		catch(Exception e)
		{
			
		}
		m.dispose();

	}
	/**
	 * 
	 */
	private void getAndSaveNewFile() throws Exception
	{
		
		String url = new String(StockConstants.MESSAGEURL);
		BufferedReader rd = Utility.getDataFromURL(url+"?"+StockConstants.ENCODEDUSERDETAIL);
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
	public void showUpdate()
	{
		try
		{
	//		ScanUtility.loadBhavCopyNDTV();
				
		}
		catch(Exception e)
		{
			
		}
	
		
		int i = 100;
		int newsveccounter = 0;
		mainscreen = MainScreen.getSingleton();

		while (true)
		{
			if(i % 4 == 0)
			{
			}
			if (i == 100 || i==0)
			{
				i = 0;
//loading intraday data;
				try
				{
			//		ScanUtility.loadBhavCopyNDTV();
						
				}
				catch(Exception e)
				{
					
				}
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
				mainscreen.news.msg = "   " + st.nextToken();
			
			if(st.hasMoreTokens())
				mainscreen.news.url = st.nextToken();
			else
				mainscreen.news.url = "";
					
			mainscreen.news.repaint();
		}

	}
		
}

/*
 * Created on Jan 15, 2007
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.getolddataforu.thread;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;
import java.util.StringTokenizer;

import javax.swing.JFrame;
import javax.swing.plaf.SliderUI;


import com.stockfaxforu.formulabuilder.MessageDiaglog;
import com.stockfaxforu.frontend.InstallScreen;
import com.stockfaxforu.getolddataforu.loader.Loader;
import com.stockfaxforu.util.DateUtility;
import com.stockfaxforu.util.DownLoadFiles;
import com.stockfaxforu.util.DownloadIndexesSymbolMap;
import com.stockfaxforu.util.IndexUtility;
import com.stockfaxforu.util.StockConstants;
import com.stockfaxforu.util.Utility;
import com.stockfaxforu.getolddataforu.util.UtilityForOldData;
/**
 * @author sdeopu1
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class IndexThread implements Runnable
{
	InstallScreen mainscreen = null;
	// This method is called when the thread runs
	public  String status="";
	public static String RESULT="RESULT";
	public static String SPLIT="SPLIT";
	
	
	public IndexThread()
	{
		mainscreen = InstallScreen.getSingleton();
	}
	public void run()
	{

		try
		{
			mainscreen.progress.setValue( 10);
			mainscreen.message.setText(mainscreen.message.getText()+"\n" + "Installation started.\n");
			mainscreen.repaint();
			
			DownLoadFiles.saveAllDirectory();
//			mainscreen.fileprocessed = 15;
	
			
			DownloadIndexesSymbolMap.saveIndex(StockConstants.COUNTRY);
			
			DownloadIndexesSymbolMap.saveMappingFile(StockConstants.COUNTRY);

			mainscreen.progress.setValue( mainscreen.progress.getValue() + 10);
			mainscreen.message.setText(mainscreen.message.getText()+"\n" + "Index file and mpping file downloaded.");
			mainscreen.repaint();
			String s="";
			File f = new File(StockConstants.INSTALL_DIR_TEMP + StockConstants.userDetailFile);
			if(f.exists())
			{
				try
				{
					s = Utility.getFileContent(StockConstants.INSTALL_DIR_TEMP + StockConstants.userDetailFile);
					Utility.saveContent(StockConstants.INSTALL_DIR + StockConstants.userDetailFile,s);
									
				}
				catch(Exception e)
				{
					
				}
			}
			else
			{
				s = Utility.getPropertiesAsString(StockConstants.USERDETAIL);
				Utility.saveContent(StockConstants.INSTALL_DIR + StockConstants.userDetailFile,s);
				Utility.saveContent(StockConstants.INSTALL_DIR_TEMP + StockConstants.userDetailFile,s);
				
			}


			
			s = System.getProperty("user.dir");
			s = s.substring(0,s.indexOf(':'));
			StringBuffer sb = new StringBuffer();
			sb.append("DRIVE="+s+"\n");
			sb.append("VERSION="+StockConstants.VERSION);
	
			Utility.saveContent(StockConstants.FirstTime_INI,sb.toString());
			
			mainscreen.fileprocessed = mainscreen.maxvalue;
	
			String userstatus = StockConstants.BASIC;

			Utility.saveContent(StockConstants.FirstTime_INI,StockConstants.VERSION);
			
			try
			{
				userstatus = Utility.getUrlContent(StockConstants.USERSTATUSURL + Utility.encodeurl(StockConstants.USERDETAIL));

				Utility.saveContent(StockConstants.userStatusFile,userstatus);
				DownLoadFiles.getAndSaveFileAsIS("/secret/livesymbol.properties", StockConstants.ICICIMAPPING);	
			}
			catch(Exception e)
			{
				
			}
	
		//	copyFilesFromOldVersion(); 
						
			StockConstants.LogBuffer.append("Successfully All Data is downloaded,Pls restart software now \n");
			mainscreen.fileprocessed = mainscreen.maxvalue;
			mainscreen.cancel.setText("OK");
			mainscreen.repaint();
			mainscreen.progress.setValue(mainscreen.fileprocessed);
			
			mainscreen.message.setText("Congratulations !!! Software is Successfully Installed, Please Restart the Software Now");
			
			mainscreen.repaint();

		}
		catch (Exception e)
		{
			try
			{
				Utility.getUrlContent(StockConstants.DELETEUSERURL + Utility.encodeurl(StockConstants.USERDETAIL));
			}
			catch (Exception e1)
			{
				// TODO Auto-generated catch block
		//		e1.printStackTrace();
			}
			mainscreen.message.setText(" Error Occured While writing data,Process terminating ");
//			mainscreen.message.setText(mainscreen.message.getText() +"\n" + e.getMessage() );
			File f = new File(StockConstants.INSTALL_DIR);
			f.delete();			
			mainscreen.repaint();
			return;
		}

	}
	private void copyFilesFromOldVersion()
	{
		try
		{
			String oldDir = "/igsentver/data";
			File f = new File("/igsentver/data");
			if(!f.exists())
				return;
			String symbols = Utility.getFileContent(StockConstants.INSTALL_DIR+"/allsymbol.txt");
			StringTokenizer st = new StringTokenizer(symbols,"\n");
			mainscreen.message.setText(mainscreen.message.getText() + "\nCopying Data From Old Version.");

			while(st.hasMoreTokens())
			{
				try
				{
					String line = st.nextToken();
					StringTokenizer lineToken = new StringTokenizer(line,"|");
					String name = lineToken.nextToken();
					String copyData = Utility.getFileContent(oldDir +"/"+name+".csv");
					Utility.saveContent(StockConstants.INSTALL_DIR+"/data/"+name+".csv",copyData );
					
				}
				catch(Exception e1)
				{
					
				}
			}
			
		}
		catch(Exception e)
		{
			
		}
		// TODO Auto-generated method stub
		
	}
	public void saveOtherFiles() throws Exception
	{
		String[] files = {  "firsttime.ini" };
		for (int i = 0; i < files.length; i++)
		{
			String file1content = Loader.returnFileContent(files[i]);
			java.io.FileOutputStream out = new java.io.FileOutputStream(StockConstants.INSTALL_DIR + "/" + files[i]);
			out.write(file1content.getBytes());
			out.close();
		}
	}
	public static void main(String[] args)
	{
/*		IndexThread th = new IndexThread();
		try {
			th.saveOtherFiles();
		} catch (Exception e) {
			// TODO Auto-generated catch block
//		e.printStackTrace();
		}
*/
		try
		{
			DownLoadFiles.getAndSaveFileAsIS("/secret/livesymbol.properties", StockConstants.ICICIMAPPING);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	

	}
}

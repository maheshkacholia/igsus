/*
 * Created on Jun 13, 2009
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.util;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.URLEncoder;
import java.util.StringTokenizer;
import java.util.Vector;

import com.stockfaxforu.frontend.InstallScreen;

/**
 * @author Mahesh
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class DownLoadFiles
{
//	public static InstallScreen mainscreen = InstallScreen.getSingleton();
	public static Vector getFileList(String path) throws Exception
	{
		String url = StockConstants.GETFILESFROMDIRECTORY + URLEncoder.encode(path);
		String s = Utility.getUrlContent(url);
		StringTokenizer st = new StringTokenizer(s,":");
		Vector v = new Vector();
		while(st.hasMoreTokens())
		{
			v.addElement(((String)st.nextElement()).trim());
		}
		return v;
	}
	public static void getAndSaveFile(String filename,String saveLoacation) throws Exception
	{
		RandomAccessFile r=null;
		try
		{
			String url = StockConstants.GETFILECONTENT + URLEncoder.encode(filename);
			String s = Utility.getUrlContent(url);
			File f = new File(StockConstants.INSTALL_DIR + "/" + filename);
			f.delete();
			r = new RandomAccessFile(StockConstants.INSTALL_DIR + "/" + filename,"rw");
			r.writeBytes(s);
			r.close();
			
		}
		catch(Exception e)
		{
	//		e.printStackTrace();
		}
		finally
		{
			try
			{
				r.close();
			}
			catch(Exception e)
			{
				
			}
		}
	}
	public static void getAndSaveFileAsIS(String filename,String saveLoacation) throws Exception
	{
		RandomAccessFile r=null;
		try
		{
			String url = StockConstants.GETFILECONTENT + URLEncoder.encode(filename);
			String s = Utility.getUrlContent(url);
			r = new RandomAccessFile(saveLoacation,"rw");
			r.writeBytes(s);
			r.close();
			
		}
		catch(Exception e)
		{
			
		}
		finally
		{
			try
			{
				r.close();
			}
			catch(Exception e)
			{
				
			}
		}
	}

	public static void loadAndSaveFiles(String directory)
	{
		try
		{
			Vector v = getFileList(directory);
			for(int i=0;i<v.size();i++)
			{
				try
				{
					String s = (String)v.get(i);
					if(s==null || s.equalsIgnoreCase("null") || s.equals(".") || s.equals(".."))
						continue;
					getAndSaveFile(directory+"/"+s, directory+"/"+s);	
					if (StockConstants.SHOWDIRECTRYIND)
						InstallScreen.getSingleton().progress.setValue(InstallScreen.getSingleton().progress.getValue() + 1);
					
		
				}
				catch(Exception e)
				{
//					e.printStackTrace();
				}
			}
		}
		catch(Exception e)
		{
//			e.printStackTrace();	
		}
	}
	public static void saveAllDirectory() throws Exception
	{
		String[] directory = {"formula","function","other","igssql","library","indicator","newindicator","buysell","newstrategy","help","igssqlintra"};
		for(int i=0;i<directory.length;i++)
		{
				File f = new File(StockConstants.INSTALL_DIR+"/"+directory[i]);
				f.mkdirs();
				loadAndSaveFiles(directory[i]);
				InstallScreen.getSingleton().message.setText(InstallScreen.getSingleton().message.getText()+"\n" + "Directory " + directory[i] + "   downloaded.");
				InstallScreen.getSingleton().repaint();
		}
	}
	public static void saveSelectedDirectory() throws Exception
	{
		String[] directory = {"library","newindicator","buysell","newstrategy","help","igssqlintra"};
		for(int i=0;i<directory.length;i++)
		{
				File f = new File(StockConstants.INSTALL_DIR+"/"+directory[i]);
				f.mkdirs();
				loadAndSaveFiles(directory[i]);
		}
	}

	
	public static void main(String[] s)
	{
	}
}

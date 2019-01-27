/*
 * Created on Jul 9, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.frontend;

import java.awt.Button;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.stockfaxforu.component.MainGraphComponent;
import com.stockfaxforu.formulabuilder.MessageDiaglog;
import com.stockfaxforu.getolddataforu.loader.Loader;
import com.stockfaxforu.getolddataforu.thread.DataDownloadThread;
import com.stockfaxforu.getolddataforu.thread.IndexThread;
import com.stockfaxforu.scan.frontend.SelectCategory;
import com.stockfaxforu.scan.util.StockScreenerThreadMaster;
import com.stockfaxforu.swingcomponent.IGSLabel;
import com.stockfaxforu.util.IndexUtility;
import com.stockfaxforu.util.StockConstants;
import com.stockfaxforu.util.Utility;

/**
 * @author sdeopura
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class DataDownloadScreen extends JFrame implements ActionListener
{
	public JPanel jcontentpane=null;
	public JProgressBar progress=null;
	public JButton install = null;
	public JTextArea message = null;
	public JButton cancel=null;
	public static int fileprocessed=0;
	public static int maxvalue=1500;
	Vector v = new Vector();
	public String selectedCategory;
	private static DataDownloadScreen datadownloadscreen = new DataDownloadScreen();
	JComboBox resolution;
	public static void main(String args[])
	{
		DataDownloadScreen grme = new DataDownloadScreen();
		grme.show();
	}
	private DataDownloadScreen()
	{
	//	super(maingraph,s,b);
		this.setTitle("Data Download Screen ");
		setBackground(Color.lightGray);
		this.setSize(525,600);
		this.move(200,200);
		Image im = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("image/toolicon.jpg"));
		this.setIconImage(im);

		this.setContentPane(getJContentPane());
				this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	JComboBox combo,yearCombo,monthCombo,dateCombo,yearComboEnd,monthComboEnd,dateComboEnd;
	public JLabel label2;
	public JCheckBox isDownload=null,isEndDate=null,useResolution=null;
	private javax.swing.JPanel getJContentPane() 
	{
		if(jcontentpane==null)
		{
			jcontentpane = new JPanel();
			jcontentpane.setLayout(null);
		}
		int yinc =25;
		int xinc = 50;	

		JLabel label = new IGSLabel("Category");
		label.setBounds(xinc,yinc,100,20);
		jcontentpane.add(label,null);
		xinc = xinc + 125;
		combo = new IndexComboBox(IndexUtility.getCategories());
		combo.setToolTipText("Before doing any scan pls download data for that category in memory,Pls select category for for which you want to download data");
		combo.setBounds(xinc,yinc,100,20);
		jcontentpane.add(combo,null);
		combo.setName("indexcombo");
		
		xinc = xinc + 125;
		JButton remove = new JButton("Unload Category");
		remove.setToolTipText("Unload category which is loaded in memory .If you want to reload and scan with diffrent dates");
		remove.setName("remove");
		remove.setBounds(xinc,yinc,130,20);
		jcontentpane.add(remove);
		remove.addActionListener(this);
		
		xinc = xinc + 150;
		ImageIcon cup1 = new ImageIcon(ClassLoader.getSystemResource("image/help.jpg"));
		JButton help = new JButton(cup1);
		help.setBounds(xinc, yinc, 24, 24);
		help.setName("help");
		help.setToolTipText(" Help ");
		jcontentpane.add(help);
		help.addActionListener(this);
		
		yinc = yinc + 30;
		xinc = 50;
		JLabel label1 = new IGSLabel("Start Date");
		label1.setBounds(xinc,yinc,125,20);
		label1.setToolTipText("Starting date from which data will be downloading into memory for scan");
		jcontentpane.add(label1,null);
		
		JLabel label4 = new JLabel("( yy-mm-dd ) ");
		label4.setFont(new Font("Arial",Font.PLAIN,10));
		label4.setBounds(xinc,yinc+15,100,20);
		jcontentpane.add(label4,null);
		
		
		xinc = xinc + 125;
		
		String[] dateStr = StockConstants.SCREENDATE.split("-");
		yearCombo = new JComboBox(Utility.getYears());
		yearCombo.setSelectedItem(dateStr[0]);
		yearCombo.setBounds(xinc, yinc,100,20);
		jcontentpane.add(yearCombo);
		
		monthCombo = new JComboBox(Utility.getMonths());
		monthCombo.setSelectedItem(dateStr[1]);
		monthCombo.setBounds(xinc+110, yinc,75,20);
		jcontentpane.add(monthCombo);
		
		dateCombo = new JComboBox(Utility.getDates());
		dateCombo.setSelectedItem(dateStr[2]);
		dateCombo.setBounds(xinc+110+85, yinc,75,20);
		jcontentpane.add(dateCombo);
	
		
		yinc = yinc + 30;
		xinc = 50;
		isEndDate = new JCheckBox();
		isEndDate.setBounds(20, yinc,20,20);
		isEndDate.setToolTipText("Selected this checkbox if you want to get scanned result for selected end date otherwise current date will be taken");
		jcontentpane.add(isEndDate);
		
		JLabel label6 = new IGSLabel("End Date");
		label6.setBounds(xinc,yinc,100,20);
		label6.setToolTipText("End date(if checkbox selected) till which data will be downloading into memory for scan adn scanned result will be for that date");

		jcontentpane.add(label6,null);
		
		JLabel label7 = new JLabel("( yy-mm-dd ) ");
		label7.setFont(new Font("Arial",Font.PLAIN,10));
		label7.setBounds(xinc,yinc+15,100,20);
		jcontentpane.add(label7,null);
		
		
		xinc = xinc + 125;
		Calendar c = Calendar.getInstance();
		String s11 =  c.get(Calendar.YEAR) + "-" + ( c.get(Calendar.MONTH) + 1)  + "-" + c.get(Calendar.DATE) + "" ;   
		if(!StockConstants.ENDDATE.equalsIgnoreCase(""))
		{
			dateStr = StockConstants.ENDDATE.split("-");
			
		}
		else
		{
			dateStr = s11.split("-");
				
		}
		yearComboEnd = new JComboBox(Utility.getYears());
		yearComboEnd.setSelectedItem(dateStr[0]);
		yearComboEnd.setBounds(xinc, yinc,100,20);
		jcontentpane.add(yearComboEnd);
		
		monthComboEnd = new JComboBox(Utility.getMonths());
		monthComboEnd.setSelectedItem(dateStr[1]);
		monthComboEnd.setBounds(xinc+110, yinc,75,20);
		jcontentpane.add(monthComboEnd);
		
		dateComboEnd = new JComboBox(Utility.getDates());
		dateComboEnd.setSelectedItem(dateStr[2]);
		dateComboEnd.setBounds(xinc+110+85, yinc,75,20);
		jcontentpane.add(dateComboEnd);
	
		yinc = yinc + 40;
		xinc = 50;
		useResolution = new JCheckBox();
		useResolution.setBounds(20, yinc,20,20);
		useResolution.setToolTipText("Select  checkbox if you want to use different resolution(1-day,2-day,3-day,weekly,monthly.. ");
		jcontentpane.add(useResolution);
		
		JLabel reslable = new JLabel("Time Resolution");
		reslable.setBounds(xinc,yinc,125,20);
		jcontentpane.add(reslable);
		
		
		resolution = new JComboBox(Utility.getGraphResolutions());
		resolution.setBounds(xinc+125, yinc, 150, 20);
		jcontentpane.add(resolution,null);
		resolution.setToolTipText("Use type of resolution which you want for scanning");
		
		
		yinc = yinc + 50;
		xinc = 50;
		install = new JButton("Download");
		install.setBounds(xinc,yinc, 100,25);
		install.setName("install");
		install.addActionListener(this);
		jcontentpane.add(install,null);
		
		
		xinc = xinc + 125;
		progress = new JProgressBar(0,v.size());
		progress.setBounds(xinc,yinc, 300,25);
		progress.setValue(0);
		jcontentpane.add(progress,null);
	
	
		yinc = yinc +50;
		xinc = 50;
		

		
		label2 = new JLabel();
		label2.setBounds(xinc,yinc, 400,20);
		jcontentpane.add(label2,null);
		
		yinc = yinc +50;
		xinc = 50;
		
		
		message = new JTextArea(10,5);
		message.setBounds(xinc,yinc, 425,200);
		JScrollPane scroolpane = new JScrollPane(message);
		scroolpane.setBounds(xinc,yinc, 425,200);

		message.setName("install");
		jcontentpane.add(scroolpane,null);

		yinc = yinc +225;
		xinc = 50;

		JButton proxy = new JButton("Proxy");
		proxy.setBounds(xinc,yinc, 100,25);
		proxy.addActionListener(this);
		proxy.setName("proxy");
		jcontentpane.add(proxy,null);
		
		xinc = xinc + 150;
		cancel = new JButton("Close");
		cancel.setBounds(xinc,yinc, 100,25);
		cancel.setName("cancel");

		cancel.addActionListener(this);

		jcontentpane.add(cancel,null);


		return jcontentpane;
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public int mysize=0;
	boolean buttonclick=false;
	public static  HashMap downloadedIndex = new HashMap();
	
	public static String indexName="";
	public void actionPerformed(ActionEvent arg0)
	{
		Object obj = arg0.getSource();
		StockConstants.SCREENDATE = yearCombo.getSelectedItem() + "-" + monthCombo.getSelectedItem() + "-" + dateCombo.getSelectedItem();

		v = IndexUtility.getIndexStockVector((String)combo.getSelectedItem());
		progress.setMaximum(v.size());
		Vector out = new Vector();
		for(int i=0;i<v.size();i++)
		{
			HashMap hs = new HashMap();
			hs.put(MainGraphComponent.Symbol, v.get(i));
			out.add(hs);
		}
		if(obj instanceof JButton)
		{
			
			JButton obj1 = (JButton)obj;
			String name = obj1.getName();
			selectedCategory = (String)combo.getSelectedItem();
			
			if(name.equals("remove"))
			{
				v = IndexUtility.getIndexStockVector((String)combo.getSelectedItem());
				for(int i=0;i<v.size();i++)
				{
					String s = (String)v.get(i);
					Loader.unloadStock(s);
				}
				downloadedIndex.remove(selectedCategory);
				MessageDiaglog m = new MessageDiaglog("Category " + selectedCategory + " unloaded from memory.");
			}
			
			else if(name.equals("install"))
			{
				if(downloadedIndex.get(selectedCategory) != null)
				{
					MessageDiaglog m = new MessageDiaglog("This Category is already been downloaded,Pls unload first for redownloading");
					return;
				}

				
				if(isEndDate.isSelected())
				{
					StockConstants.ENDDATE = yearComboEnd.getSelectedItem() + "-" + monthComboEnd.getSelectedItem() + "-" + dateComboEnd.getSelectedItem();
				}
				else
				{
					StockConstants.ENDDATE = "";
				}
				int days=1;
				if(useResolution.isSelected())
				{
					String s = resolution.getSelectedItem().toString();
					if(s.equalsIgnoreCase("Weekly"))
					{
						days=MainGraphComponent.WEEKLY;
					}
					else if (s.equalsIgnoreCase("Monthly"))
					{
						days=MainGraphComponent.MONTHLY;
					}
					else
					{
						try
						{
							s = s.substring(0,s.indexOf("-"));
							days = Integer.parseInt(s);
							
						}
						catch(Exception e)
						{
							
						}
					}
					StockConstants.RESOLUTION = days;
				}
				
				selectedCategory = (String)combo.getSelectedItem();
				v = IndexUtility.getIndexStockVector((String)combo.getSelectedItem());
				for(int i=0;i<v.size();i++)
				{
					String s = (String)v.get(i);
					Loader.unloadStock(s);
				}
				
				selectedCategory = (String)combo.getSelectedItem();
				buttonclick=true;	
				install.setEnabled(false);
				indexName = selectedCategory;	
				StockScreenerThreadMaster runnable = new StockScreenerThreadMaster();
				runnable.symbolArrayList = out;
				mysize = out.size();
				runnable.datadownloadscreen = this; 
				Thread thread = new Thread(runnable);
				thread.start();
				buttonclick=false;	
				
			}
			else if(name.equals("cancel"))
			{
				this.dispose();
			}
			else if(name.equals("proxy"))
			{
				ProxySetting f = new ProxySetting();
			}
			else if (name.equals("help"))
			{
				ShowContentWindow searc = new ShowContentWindow(StockConstants.INSTALL_DIR + "/help","datadownloader.html");
				
			}
			
		}
		// TODO Auto-generated method stub
		
	}
	/**
	 * @return
	 */
	public static DataDownloadScreen getSingleton()
	{
		
		return datadownloadscreen;
	}
}
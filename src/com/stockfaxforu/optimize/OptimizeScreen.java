/*
 * Created on Feb 19, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.optimize;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Label;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.RandomAccessFile;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.stockfaxforu.backtesting.BackTesting;
import com.stockfaxforu.backtesting.BackTestingParameterScreen;
import com.stockfaxforu.component.*;
import com.stockfaxforu.formulabuilder.MessageDiaglog;
import com.stockfaxforu.frontend.CustmizedIndicatorPanel;
import com.stockfaxforu.frontend.DateButton;
import com.stockfaxforu.frontend.PanelForGraphImpl;
import com.stockfaxforu.frontend.ShowContentWindow;
import com.stockfaxforu.getolddataforu.loader.Loader;
import com.stockfaxforu.strategy.BuySellStrategy;
import com.stockfaxforu.strategy.StrategyUtility;
import com.stockfaxforu.swingcomponent.IGSLabel;
import com.stockfaxforu.util.IndexUtility;
import com.stockfaxforu.util.ManageIndicator;
import com.stockfaxforu.util.StockConstants;
import com.stockfaxforu.util.Utility;

/**
 * @author sdeopura
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class OptimizeScreen extends JFrame implements ActionListener,PropertyChangeListener
{
	/**
	 * This is the default constructor
	 */
	JTextField formula =  null;
	JButton add = null;
	JButton cancel = null;
	JComboBox indiactorList = null;
	String indicatorName=null;
	Indicator selInd = null;
	JTextField[] startText;
	JTextField[] endText;
	JLabel[] name;
	JTextField[] incrementText;
	DateButton startDateButton,endDateButton;
	public OptimizeScreen()
	{
//		super(null);
		setBackground(Color.lightGray);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setContentPane(getJContentPane());
		this.setTitle("Formula Optimization Screen");
		this.move(100, 100);
		this.setSize(700,500);
		Image im = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("image/toolicon.jpg"));
		this.setIconImage(im);

		this.setVisible(true);
	}
	private javax.swing.JPanel jContentPane = null;
	JComboBox category;
	private javax.swing.JPanel getJContentPane() 
	{
		xinc = 20;
		yinc = 50;
		int xincdis=100;
		formula =  new JTextField();

		if(jContentPane == null) 
		{
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
		}
		
		JLabel lable1 = new IGSLabel("Category :");
		lable1.setBounds(xinc,yinc,100,20);	
		jContentPane.add(lable1,null);
		
		
		xinc = xinc + 125;
		category = new JComboBox(IndexUtility.getCategories());
		category.setBounds(xinc,yinc,100,20);	
		jContentPane.add(category,null);

		
		xinc = xinc + 110;
		
		ImageIcon cup1 = new ImageIcon(ClassLoader.getSystemResource("image/help.jpg"));
		JButton help = new JButton(cup1);
		help.setBounds(xinc, yinc, 24, 24);
		help.setName("help");
		help.setToolTipText(" Help ");
		jContentPane.add(help);
		help.addActionListener(this);

		yinc = yinc + 40;

		xinc =  20;
		JLabel labelxy1 = new JLabel("Start Date");
		jContentPane.add(labelxy1);
		labelxy1.setBounds(xinc,yinc,100,25);

		startDateButton = new DateButton();
		startDateButton.addPropertyChangeListener( "date", this );
		jContentPane.add(startDateButton);
		startDateButton.setBounds(xinc+125, yinc, 150, 20);
		
		yinc = yinc + 40;
	

		JLabel labelxy2 = new JLabel("End Date");
		jContentPane.add(labelxy2);
		labelxy2.setBounds(xinc,yinc,100,25);

		endDateButton = new DateButton();
		endDateButton.addPropertyChangeListener( "date", this );
		jContentPane.add(endDateButton);
		endDateButton.setBounds(xinc+125, yinc, 150, 20);

		
		
		
		
		yinc = yinc + 40;
		xinc = 20;
		
		
		
		JLabel lable = new IGSLabel("Formula :");
		lable.setBounds(xinc,yinc,100,20);	
		jContentPane.add(lable,null);
		
		xinc = xinc + 125;
		formula.setBounds(xinc,yinc,400,20);	
		jContentPane.add(formula,null);
		
		xinc  = xinc + 410;
		JButton select = new JButton();
		select.setBounds(xinc,yinc,75,20);
		select.setName("select");
		select.setText("Select");
		jContentPane.add(select,null);
		select.addActionListener(this);

		xinc = xinc =10;
		yinc = yinc + 50;
		yposition = yinc;

		JButton submit = new JButton();
		submit.setBounds(xinc+50,yinc,75,20);
		submit.setName("submit");
		submit.setText("Submit");
		jContentPane.add(submit,null);
		submit.addActionListener(this);

		JButton cancel = new JButton();
		cancel.setBounds(xinc+50+125,yinc,75,20);
		cancel.setName("cancel");
		cancel.setText("Cancel");
		jContentPane.add(cancel,null);
		cancel.addActionListener(this);


		 
		 
		 
		 return jContentPane;	 
	
	}
	int yposition,yinc,xinc;
	public void addOtherComponenet()
	{
		yinc = yposition;
		
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize()
	{
		this.setSize(300, 250);
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	
	String fileName1 ;
	String dirName1 ;
	
	
	
	public String getFormula(String dirName1,String fileName1)
	{
		String formulaFileName="";
		try
			{
				File f = new File(dirName1 + "/" + fileName1);
				FileInputStream file = new FileInputStream(f);
				long len = f.length();
				byte[] b = new byte[(int)len];
				file.read(b, 0, b.length);
				formulaFileName = new String(b);
				file.close();	
			}
			catch (Exception e)
			{
					
				// TODO Auto-generated catch block
		//		e.printStackTrace();
			}
		return formulaFileName;
	}
	JLabel[] lableTop = new JLabel[4];
	
	public void getAndAddVariables(String fileName, String formula, Vector v)
	{
		try
		{
			for(int i=0;i<name.length;i++)
			{
				jContentPane.remove(name[i]);
				jContentPane.remove(startText[i]);
				jContentPane.remove(endText[i]);
				jContentPane.remove(incrementText[i]);
				
			}
			for(int i=0;i<lableTop.length;i++)
			{
				jContentPane.remove(lableTop[i]);
			}
		}
		catch(Exception e)
		{
			
		}
		this.formula.setText(this.dirName1 + "\\" + this.fileName1);

		String[] paraValue = new String[v.size()];
		for (int i=0;i<v.size();i++)
			paraValue[i] = "";
		
		
		name = new JLabel[v.size()];
		startText = new JTextField[v.size()];
		endText = new JTextField[v.size()];
		incrementText = new JTextField[v.size()];
		yinc = yposition;

		xinc = 20;
		yinc = yinc + 30;
		
		lableTop[0] = new JLabel("Name");
		lableTop[0].setBounds(xinc, yinc, 100,20);
		lableTop[0].setToolTipText("Name of the variable");
		jContentPane.add(lableTop[0]);

		
		xinc = xinc + 100;
		
		lableTop[1] = new JLabel("Start");
		lableTop[1].setBounds(xinc, yinc, 100,20);
		lableTop[1].setToolTipText("Start Value for Optimization");
		jContentPane.add(lableTop[1]);
		
		xinc = xinc + 60;
		
		lableTop[2] = new JLabel("End");
		lableTop[2].setBounds(xinc, yinc, 100,20);
		lableTop[2].setToolTipText("End Value for Optimization");
		jContentPane.add(lableTop[2]);
		
		
		xinc = xinc + 60;
		
		lableTop[3] = new JLabel("Increment");
		lableTop[3].setBounds(xinc, yinc, 100,20);
		lableTop[3].setToolTipText("Increment Value for Optimization");
		jContentPane.add(lableTop[3]);
		
	 
		
		for ( int i=0;i<v.size();i++)
		{
			xinc = 20;

			yinc = yinc + 30;
			
			name[i]  = new JLabel(v.get(i).toString());
			name[i].setBounds(xinc, yinc,100, 20);
			jContentPane.add(name[i],null);
			
			xinc = xinc + 100;
			startText[i] = new JTextField();
			startText[i].setBounds(xinc, yinc, 50, 20);
			jContentPane.add(startText[i],null);
			
			xinc = xinc + 60;
			endText[i] = new JTextField();
			endText[i].setBounds(xinc, yinc, 50, 20);
			jContentPane.add(endText[i],null);
			
			
			xinc = xinc + 60;
			incrementText[i] = new JTextField();
			incrementText[i].setBounds(xinc, yinc, 50, 20);
			jContentPane.add(incrementText[i],null);
			
			
		}

		repaint();
		
		
		
		// TODO Auto-generated constructor stub
//		super(null);
	}

	
	public void checkFormulaForCustmizedIndicator(String fileName,String formula )
	{
		try
		{
			
		formula = formula.toUpperCase();
		StringTokenizer st = new StringTokenizer(formula,"\n");
		String parameter = st.nextToken();
		if(parameter.toUpperCase().startsWith("@PARAMETER"))
		{
			StringTokenizer st1 = new StringTokenizer(parameter," ");
			st1.nextToken();
			Vector v = new Vector();
			while(st1.hasMoreTokens())
			{
				v.add(st1.nextToken());
			}
			if(v.size()==0)
			{
				MessageDiaglog m = new MessageDiaglog("No variable for Optimization");
			}
			else
			{
				this.getAndAddVariables(fileName, formula, v);	
			}
		}
		else
		{
			MessageDiaglog m = new MessageDiaglog("No variable for Optimization");
			
		}
		}
		catch(Exception e)
		{
			
		}
	}	

	String formulaString="";
	public void actionPerformed(ActionEvent e)
	{
		Object o = (Object)e.getSource();
		if(o instanceof JButton)
		{
			JButton cb = (JButton)e.getSource();
			String name = cb.getName();
			if(name.equalsIgnoreCase("select"))
			{
				
				FileDialog fd = new FileDialog(new Frame(),"Open Formula", FileDialog.LOAD);
				fd.setFile("*.fl");
				fd.setDirectory(StockConstants.FORMULA_DIR);
				fd.setLocation(50, 50);
				fd.show();
			
				fileName1 = fd.getFile();
				dirName1 = fd.getDirectory();
				
				formulaString = getFormula(dirName1, fileName1);

				checkFormulaForCustmizedIndicator(fileName1, formulaString);
//				this.executeCustmizedIndicator(fileName1,s);

			}
			else if (name.equalsIgnoreCase("submit"))
			{
				try {
					doOptiMization();
					
				} 
				catch (Exception e1) 
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			else if (name.equalsIgnoreCase("help"))
			{
				ShowContentWindow searc = new ShowContentWindow(StockConstants.INSTALL_DIR + "/help","optimize.html");
				
			}
			else if(name.equalsIgnoreCase("cancel"))
			{
				this.dispose();
			}
			
		}
		if(o instanceof JFrame)
		{
			this.dispose();
			
		}
	}
	public Vector paraList = new Vector();
	public Vector outPut = new Vector();
	
	private void doOptiMization()  throws Exception 
	{
		paraList = new Vector();
		outPut = new Vector();
		float[] start = new float[this.startText.length];
		float[] end = new float[this.endText.length];
		float[] increment = new float[this.incrementText.length];
		
		for(int i=0;i<startText.length;i++)
		{
			try
			{
				start[i] = Float.parseFloat(this.startText[i].getText().trim());
				end[i] = Float.parseFloat(this.endText[i].getText().trim());
				increment[i] = Float.parseFloat(this.incrementText[i].getText().trim());
				
			}
			catch (Exception e)
			{
				MessageDiaglog m = new MessageDiaglog("Pls enter numeric value");
			}
		}
		for(int i=0;i<start.length;i++)
		{
			Vector paras = new Vector();
			for ( float f = start[i]; f <= end[i];f = f + increment[i])
			{
				paras.add(f+"");
			}
			paraList.add(paras);
		}
		getAllOptions(0, "");
		cat = (String)category.getSelectedItem();
		catVector = IndexUtility.getIndexStockVector(cat);
		catVector.add("Average Return");
		applyStrategy();
		catVector.insertElementAt( formula.getText().substring(formula.getText().lastIndexOf('\\')),0);
//		OptimizeResult op = new OptimizeResult( mainResult,catVector);
//		System.out.print("rrrrrrrrrrrrrr");

		Utility.saveContent(StockConstants.INSTALL_DIR+"/temp/temp.csv", getAsString());
		try
		{
			Runtime runtime = Runtime.getRuntime();
			Process process =
				runtime.exec(
					StockConstants.IEEXPLORERLOC + "   -new " + StockConstants.INSTALL_DIR_O + "\\temp\\temp.csv");
			
		}
		catch(Exception e1)
		{
//			e1.printStackTrace();
		}

	}
	String cat;
	Vector catVector;
	Vector mainResult = new Vector();
	public String getAsString()
	{
	//	System.out.println("catvector="+catVector);
	//	System.out.println("mainresult="+mainResult);

		StringBuffer sb = new StringBuffer();
		for(int i=0;i<catVector.size();i++)
			sb.append(catVector.get(i).toString()+",");
		sb.append("\n");
		for(int i=0;i<mainResult.size();i++)
		{
			Vector s = (Vector)mainResult.get(i);
			for(int j=0;j<s.size();j++)
				sb.append(s.get(j)+",");
			
			sb.append("\n");
			
		}
		return sb.toString();
		
	}
	public void applyStrategy()
	{
		for(int i=0;i<outPut.size();i++)
		{
			try
			{
				
				String s = updateFormula(outPut.get(i)+""); 
				Vector v = getReturnsForFormula(s);
				v.insertElementAt((outPut.get(i)+"").replace(',','-'), 0);
//				System.out.println(outPut.get(i)+"---" + getResult("TCS", s) );
				mainResult.add(v);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
			
	}
	
	public Vector getReturnsForFormula(String formula)
	{
		Vector results = new Vector();
		
		float avg=0;
		int counter=0;
		for(int j=0;j<catVector.size();j++)
		{
			try
			{
				String s = getResult(catVector.get(j)+"", formula)+"";
		//		System.out.println(catVector.get(j));
				results.add(s);
				try
				{
					avg = avg + Float.parseFloat(s);
					counter++;
				}
				catch(Exception e)
				{
					
				}
			}
			catch(Exception e)
			{
				System.out.println("exp occured for " +formula + " for " + catVector.get(j));
		//		e.printStackTrace();
			}
		}
		avg = avg/(float)(counter+.00001);
		results.add(Utility.floatDataAtOnePrecision(avg));
		return results;
	}
	public String getResult(String symbolname,String formula) throws Exception
	{
		BuySellStrategy buysell = new BuySellStrategy();
		BackTesting backtesting = new BackTesting();
		
		Vector v = Loader.getFileContentForScreening(StockConstants.STARTDATE, symbolname, StockConstants.SCREENDATE,"");
		v = Loader.createSubList(v, startDateButton.getText(), endDateButton.getText());
		
		Vector v1 = buysell.executeStrategy(v, formula);
		String rsult = Utility.oneDigitFloat(backtesting.returnProftLoss(BackTestingParameterScreen.buyinper,BackTestingParameterScreen.sellinper,BackTestingParameterScreen.initialamt,BackTestingParameterScreen.tranamtf,BackTestingParameterScreen.trantypestr, v1)) + "";
		return rsult;
	}
	
	
	
	
	private String updateFormula(String string) 
	{
		System.out.println("change="+string);
		String s = this.formulaString;
		StringTokenizer st = new StringTokenizer(string,",");
		for(int i=0;i<name.length;i++)
		{
			String rep = "$"+name[i].getText()+"$";
			String ss = st.nextToken();
			try
			{
				float y = Float.parseFloat(ss);;
				int x = (int)y;
				if(x==y)
				{
					ss = x+"";
				}
			}
			catch(Exception e)
			{
				
			}
			s = Utility.replaceString(s, rep,ss );
		}
		return s;
	}

	public String getAllOptions(int level,String currentVar)
	{
		if ( level == paraList.size())
		{
			return currentVar;
		}
		else
		{
			Vector x = (Vector)paraList.get(level);
			for (int i=0;i<x.size();i++ )
			{
				String s ="";
				if(currentVar.equals(""))
				{
					s = x.get(i)+"";
					
				}
				else
				{
					s =currentVar + "," + x.get(i);
					
				}
				String y = getAllOptions(level + 1, s);
				if(!y.equalsIgnoreCase(""))
				outPut.add(y);
			}
			System.out.println(outPut);
			return "";
		}
	
	}
	
	

	/**
	 * @return
	 */
	private boolean checkValue()
	{
		// TODO Auto-generated method stub
		return false;
	}
	public static void main(String[] args)
		{
			
			OptimizeScreen d = new OptimizeScreen();

		}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		
	}
	
}  //  @jve:visual-info  decl-index=0 visual-constraint="4,11"

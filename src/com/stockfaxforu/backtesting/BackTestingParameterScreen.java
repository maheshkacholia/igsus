/*
 * Created on Feb 19, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.backtesting;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Label;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.stockfaxforu.component.*;
import com.stockfaxforu.formulabuilder.MessageDiaglog;
import com.stockfaxforu.frontend.ConsoleWindow;
import com.stockfaxforu.frontend.PanelForGraph;
import com.stockfaxforu.frontend.PanelForGraphImpl;
import com.stockfaxforu.frontend.ShowContentWindow;
import com.stockfaxforu.swingcomponent.IGSLabel;
import com.stockfaxforu.util.ManageIndicator;
import com.stockfaxforu.util.StockConstants;
import com.stockfaxforu.util.Utility;

/**
 * @author sdeopura
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class BackTestingParameterScreen extends JFrame implements ActionListener
{
	/**
	 * This is the default constructor
	 */
	JTextField initialamttext =  null;
	JTextField buyinpertext =  null;
	JTextField sellinpertext =  null;
	
	JButton add = null;
	JButton cancel = null;
	JComboBox indiactorList = null;
	String indicatorName=null;
	Indicator selInd = null;
	public static float initialamt = 10000;
	public static float buyinper = 100;
	public static float sellinper = 100;
	public static float tranamtf = 0;
	public static String trantypestr="";
	Vector dataVector=null;
	public static String INITIAL_AMT="INITIAL_AMT";
	
	public static String BUY_IN_PER="BUY_IN_PER";
	public static String SELL_IN_PER="SELL_IN_PER";
	public static String TRANS_TYPE = "TRANS_TYPE";
	public static String TRANS_AMT = "TRANS_AMT";
	
	public static String TRANS_IN_PER = "Percent";
	public static String TRANS_IN_AMT = "Fix-Amount";
	public PanelForGraph panelforgraph=null;
	public static void loadParameter()
	{
		File f = new File(StockConstants.INSTALL_DIR + "/" + "backtesting.obj");
		if(f.exists())
		{
			try
			{
				FileInputStream input = new FileInputStream(f);
				ObjectInputStream obj = new ObjectInputStream(input);
				HashMap paraHash = (HashMap)obj.readObject();
				initialamt = Float.parseFloat((String)paraHash.get(INITIAL_AMT));
				buyinper = Float.parseFloat((String)paraHash.get(BUY_IN_PER));
				sellinper = Float.parseFloat((String)paraHash.get(SELL_IN_PER));
				trantypestr = (String)paraHash.get(TRANS_TYPE);
				tranamtf = Float.parseFloat((String)paraHash.get(TRANS_AMT));
					
				input.close();
				dataHash = new HashMap();
				dataHash.put(INITIAL_AMT,initialamt+"");
				dataHash.put(BUY_IN_PER,buyinper+"");
				dataHash.put(SELL_IN_PER,sellinper+"");
				dataHash.put(TRANS_TYPE,trantypestr+"");
				dataHash.put(TRANS_AMT,tranamtf+"");
					
									
			}
			catch(Exception e)
			{
				f.delete();
				
			}
		}
		
	}
	public BackTestingParameterScreen(PanelForGraph panelforgraph)
	{
		this.panelforgraph=panelforgraph;
		if (dataHash==null)
		{
			loadParameter();	
		}
		else
		{
			initialamt = Float.parseFloat((String)dataHash.get(INITIAL_AMT));
			buyinper = Float.parseFloat((String)dataHash.get(BUY_IN_PER));
			sellinper = Float.parseFloat((String)dataHash.get(SELL_IN_PER));
			trantypestr = (String)dataHash.get(TRANS_TYPE);
			tranamtf = Float.parseFloat((String)dataHash.get(TRANS_AMT));
					
		}



//		super(null);
		setBackground(Color.lightGray);
		this.dataVector = dataVector;
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setTitle("Enter Backtesting Parameter");
		this.setContentPane(getJContentPane());

		this.move(100, 100);
		this.setSize(550,500);
		Image im = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("image/toolicon.jpg"));
		this.setIconImage(im);

		this.setVisible(true);


	}
	private javax.swing.JPanel jContentPane = null;
	private javax.swing.JPanel getJContentPane() 
	{
		int xinc = 10;
		int yinc = 20;
		int xincdis=100;

		if(jContentPane == null) 
		{
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
		}
		IGSLabel lable = new IGSLabel("Initial Amount:");
//		lable.setFont(new Font("Arial",Font.BOLD,12));

//		lable.setFont(new Font("Arial",Font.BOLD,12));
		lable.setBounds(xinc,yinc,100,15);	
		jContentPane.add(lable,null);
		
		xinc = xinc + 300;
		initialamttext = new JTextField(initialamt+"");
//		initialamttext.setFont(new Font("Arial",Font.BOLD,12));
		initialamttext.setBounds(xinc,yinc,100,20);	
		jContentPane.add(initialamttext,null);
		
		yinc = yinc + 40;
		xinc = 10;
		
		IGSLabel lable1 = new IGSLabel("Percentage Amount to Use For Buying:");
//		lable1.setFont(new Font("Arial",Font.BOLD,12));

//		lable1.setFont(new Font("Arial",Font.BOLD,12));
		lable1.setBounds(xinc,yinc,300,15);	
		jContentPane.add(lable1,null);

		xinc = xinc + 300;
		buyinpertext = new JTextField(buyinper+"");
//		buyinpertext.setFont(new Font("Arial",Font.BOLD,12));
		buyinpertext.setBounds(xinc,yinc,100,20);	
		jContentPane.add(buyinpertext,null);

		yinc = yinc + 40;
		
		xinc = 10;

		IGSLabel lable2 = new IGSLabel("Percentage of Share to Sell On Sell Signal:");
//		lable2.setFont(new Font("Arial",Font.BOLD,12));

//		lable2.setFont(new Font("Arial",Font.BOLD,12));
		lable2.setBounds(xinc,yinc,300,15);	
		jContentPane.add(lable2,null);

		xinc = xinc + 300;
		sellinpertext = new JTextField(sellinper+"");
//		sellinpertext.setFont(new Font("Arial",Font.BOLD,12));
		sellinpertext.setBounds(xinc,yinc,100,20);	
		jContentPane.add(sellinpertext,null);

		yinc = yinc + 40;

		xinc = 10;

		IGSLabel lable3 = new IGSLabel("Brokerage Type:");
//		lable3.setFont(new Font("Arial",Font.BOLD,12));

//		lable3.setFont(new Font("Arial",Font.BOLD,12));
		lable3.setBounds(xinc,yinc,300,15);	
		jContentPane.add(lable3,null);

		xinc = xinc + 300;
		trantype = new JComboBox(trans);
		trantype.setBounds(xinc,yinc,100,20);	
		jContentPane.add(trantype,null);
		
		if(dataHash != null && dataHash.get(TRANS_TYPE) != null)
		{
			Object o = dataHash.get(TRANS_TYPE);
			trantype.setSelectedItem(o);
		}
		yinc = yinc + 40;

		xinc = 10;
		IGSLabel lable4 = new IGSLabel("Brokerage Amount or Percent:");
//		lable4.setFont(new Font("Arial",Font.BOLD,12));

//		lable4.setFont(new Font("Arial",Font.BOLD,12));
		lable4.setBounds(xinc,yinc,300,15);	
		jContentPane.add(lable4,null);

		xinc = xinc + 300;
		tranamt = new JTextField(tranamtf+"");
		tranamt.setBounds(xinc,yinc,100,20);	
		jContentPane.add(tranamt,null);



		yinc = yinc + 40;

		
		xinc = 10;
		
		JButton save = new JButton();
		save.setBounds(xinc,yinc,75,20);
		save.setName("save");
		save.setText("Save");
		jContentPane.add(save,null);
		save.addActionListener(this);


		xinc = xinc + 100;
/*
		JButton execute = new JButton();
		execute.setBounds(xinc,yinc,125,20);
		execute.setName("execute");
		execute.setText("Execute");
		jContentPane.add(execute,null);
		execute.addActionListener(this);


		
		xinc = xinc + 150;
*/		
		cancel = new JButton();
		cancel.setBounds(xinc,yinc,75,20);
		cancel.setName("cancel");
		cancel.setText("Cancel");
		jContentPane.add(cancel,null);
		cancel.addActionListener(this);
		 
		return jContentPane;	 
	
	}	
	JComboBox trantype;
	JTextField tranamt;
	String trans[] = {"Percent","Fix-Amount"};

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	public static HashMap dataHash = null;
	private void initialize()
	{
		this.setSize(300, 250);
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e)
	{
		Object o = (Object)e.getSource();
		if(o instanceof JButton)
		{
			JButton cb = (JButton)e.getSource();
			String name = cb.getName();
			if(name.equalsIgnoreCase("save"))
			{
				try
				{
					initialamt = Float.parseFloat(initialamttext.getText().trim());
					buyinper = Float.parseFloat(buyinpertext.getText().trim());
					sellinper = Float.parseFloat(sellinpertext.getText().trim());
					tranamtf = Float.parseFloat(tranamt.getText().trim());
					trantypestr = (String)trantype.getSelectedItem();
					if(dataHash==null)
						dataHash = new HashMap();
					dataHash.put(INITIAL_AMT,initialamt+"");
					dataHash.put(BUY_IN_PER,buyinper+"");
					dataHash.put(SELL_IN_PER,sellinper+"");
					dataHash.put(TRANS_TYPE,trantypestr);
					dataHash.put(TRANS_AMT,tranamtf+"");

					File f = new File(StockConstants.INSTALL_DIR + "/" + "backtesting.obj");
					FileOutputStream output = new FileOutputStream(f);
					ObjectOutputStream obj = new ObjectOutputStream(output);
					obj.writeObject(dataHash);

					if(this.panelforgraph != null)
						this.panelforgraph.executeBackTesting();
					this.dispose();
						
					return;
					
				}
				catch(Exception e1)
				{
					MessageDiaglog msg = new MessageDiaglog("Wrong Data is entered");
					return;		
				}
				
			}
			else if(name.equalsIgnoreCase("cancel"))
			{
				this.dispose();
			}
			if(name.equalsIgnoreCase("execute"))
			{
				try
				{
					initialamt = Float.parseFloat(initialamttext.getText().trim());
					buyinper = Float.parseFloat(buyinpertext.getText().trim());
					sellinper = Float.parseFloat(sellinpertext.getText().trim());
					dataHash.put(INITIAL_AMT,initialamt+"");
					dataHash.put(BUY_IN_PER,buyinper+"");
					dataHash.put(SELL_IN_PER,sellinper+"");

					BackTesting backtesting = new BackTesting();
//					String content = backtesting.doBackTesting(buyinper,sellinper,initialamt,dataVector);
//					ShowContentWindow showcontent = new ShowContentWindow(content);					
				}
				catch(Exception e1)
				{
					MessageDiaglog msg = new MessageDiaglog("Wrong Data is entered");
					return;		
				}
			}			
			
		}
		if(o instanceof JFrame)
		{
			this.dispose();
			
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
			JFrame frame = new JFrame("Test angled lines");
			frame.move(300, 300);

			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			Vector v = new Vector();
			v.addElement("Period 1(1..100)");
			v.addElement("Period 2(2..50)");
			
			
			BackTestingParameterScreen d = new BackTestingParameterScreen(null);
//			frame.getContentPane().add(d);
//			frame.pack();
			//		frame.setBounds(0,0,1000,700);
//			frame.setVisible(true);

		}
	
}  //  @jve:visual-info  decl-index=0 visual-constraint="4,11"

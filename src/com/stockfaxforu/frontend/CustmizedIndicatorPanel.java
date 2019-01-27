package com.stockfaxforu.frontend;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Image;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.stockfaxforu.config.ConfigUtility;
import com.stockfaxforu.formulabuilder.MessageDiaglog;
import com.stockfaxforu.scan.frontend.SelectStockScannerNew;
import com.stockfaxforu.strategy.StrategyUtility;
import com.stockfaxforu.util.Utility;

public class CustmizedIndicatorPanel extends JFrame implements ActionListener
{
	String fileName;
	String formula;
	Vector v;
	PanelForGraphImpl panelForGraph;
	JLabel[] name=null;
	JTextField[] value = null;
	int type=1;
	public static int CUSTOM=1;
	public static int STRATEGY=2;

	public CustmizedIndicatorPanel(String fileName, String formula, Vector v, PanelForGraphImpl panelForGraph,int type)
	{
		this.type =STRATEGY;

		this.fileName = fileName;
		this.formula = formula;
		this.v = v;
		paraValue = new String[v.size()];
		for (int i=0;i<v.size();i++)
			paraValue[i] = "";
		
		this.panelForGraph = panelForGraph;
		name = new JLabel[v.size()];
		value = new JTextField[v.size()];
		// TODO Auto-generated constructor stub
//		super(null);
		setBackground(Color.lightGray);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setContentPane(getJContentPane());
		this.setTitle("Apply Strategy");
		this.move(300, 300);
		this.setSize(700,300 + v.size()*50);
		Image im = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("image/toolicon.jpg"));
		this.setIconImage(im);
		
		this.setVisible(true);
	
	}

	public CustmizedIndicatorPanel(String fileName, String formula, Vector v, PanelForGraphImpl panelForGraph)
	{
		this.fileName = fileName;
		this.formula = formula;
		this.v = v;
		paraValue = new String[v.size()];
		for (int i=0;i<v.size();i++)
			paraValue[i] = "";
		
		this.panelForGraph = panelForGraph;
		name = new JLabel[v.size()];
		value = new JTextField[v.size()];
		// TODO Auto-generated constructor stub
//		super(null);
		setBackground(Color.lightGray);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setContentPane(getJContentPane());
		this.setTitle("Custmized Indicator");
		this.move(300, 300);
		this.setSize(700,300 + v.size()*50);
		Image im = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("image/toolicon.jpg"));
		this.setIconImage(im);
		
		this.setVisible(true);
	
	}
	private javax.swing.JPanel jContentPane = null;
	public String[] paraValue = null;
	public JComboBox yesno = null;
	public JComboBox colorcombo = null;

	String[] yesnoStr = {"TRUE","FALSE"};
	public static String BUYSELLSIGNAL="BUYSELLSIGNAL";
	public static String COLORDISPLAY="COLORDISPLAY";
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
		StringTokenizer st = new StringTokenizer(this.formula,"\n");
		JLabel[] comment = new JLabel[30];
		int i=0;
		if(st.hasMoreTokens())
		{
			st.nextToken();
			if(st.hasMoreTokens())
			{
				String commentLine = st.nextToken();
				if(commentLine.toUpperCase().startsWith("@COMMENT"))
				{
					while(commentLine.toUpperCase().startsWith("@COMMENT"))
					{
						String commentStr = commentLine.substring("@COMMENT".length());
						comment[i] = new JLabel(commentStr);
						comment[i].setBounds(xinc,yinc, 800,20);
						comment[i].setFont(new Font("Arial",Font.BOLD ,10));
						yinc = yinc + 15;
								
						jContentPane.add(comment[i]);
						i++;
						yinc = yinc + 30;
						commentLine = st.nextToken();	
					}
				}
				else if(commentLine.toUpperCase().startsWith("@VALUE"))
				{
					StringTokenizer st1 = new StringTokenizer(commentLine.trim()," ");
					st1.nextToken();
					int k=0;
					while(st1.hasMoreTokens())
					{
						this.paraValue[k] = st1.nextToken().toUpperCase();
						k++;	
					}

					if(st.hasMoreTokens())
					{
						commentLine = st.nextToken();
						if(commentLine.toUpperCase().startsWith("@COMMENT"))
						{
							while(commentLine.toUpperCase().startsWith("@COMMENT"))
							{
								String commentStr = commentLine.substring("@COMMENT".length());
								comment[i] = new JLabel(commentStr);
								comment[i].setFont(new Font("Arial",Font.BOLD ,10));
								comment[i].setBounds(xinc,yinc, 800,20);
								jContentPane.add(comment[i]);
								i++;
								yinc = yinc + 15;
								commentLine = st.nextToken();	
										
							}	
						}
					}	
				}
					
			}
		}
		yinc = yinc + 10;
		for(i=0;i<name.length;i++)
		{
			name[i] = new JLabel((String)this.v.get(i).toString().trim());
			name[i].setBounds(xinc,yinc, 200,20);
			jContentPane.add(name[i]);
			if (name[i].getText().equalsIgnoreCase(BUYSELLSIGNAL))
			{
				yesno = new JComboBox(yesnoStr);
				yesno.setBounds(xinc+225,yinc, 75,20);
				jContentPane.add(yesno);
					
				yinc = yinc + 30;
				
			}
			else if (name[i].getText().equalsIgnoreCase(COLORDISPLAY))
			{
				colorcombo = new JComboBox(ConfigUtility.getColors());
				colorcombo.setBounds(xinc+225,yinc, 75,20);
				jContentPane.add(colorcombo);
					
				yinc = yinc + 30;
				
			}
			else
			{
				
				value[i] = new JTextField(paraValue[i]);
				value[i].setBounds(xinc+225,yinc, 75,20);
				jContentPane.add(value[i]);
					
				yinc = yinc + 30;
				
			}
			
		}
		yinc = yinc + 20;
		
		JButton but = new JButton("Apply");
		but.setName("apply");
		but.addActionListener(this);
		but.setBounds(xinc,yinc, 100,20);
		jContentPane.add(but);
		
		JButton cancel = new JButton("Cancel");
		cancel.setName("cancel");
		cancel.addActionListener(this);
		cancel.setBounds(xinc+150,yinc, 100,20);
		jContentPane.add(cancel);
		
		return jContentPane;
	}	
	public void actionPerformed(ActionEvent e)
	{
		JButton but = (JButton)e.getSource();
		StringBuffer para=new StringBuffer("(");
		if(but.getName().equalsIgnoreCase("apply"))
		{
			StringBuffer paraValueBuff = new StringBuffer("@PARAMETERVALUE ");
			for(int i=0;i<value.length;i++)
			{
				String s="";
				if (name[i].getText().equalsIgnoreCase(BUYSELLSIGNAL))
				{
					s = yesno.getSelectedItem().toString();
					
				}
				else if (name[i].getText().equalsIgnoreCase(COLORDISPLAY))
				{
					s = colorcombo.getSelectedItem().toString();
						
				}
				else
				{
					s = value[i].getText().trim();
					
				}
				
				if(i == value.length -1)
				{
					para.append(s+")");
						
				}
				else
				{
					para.append(s+",");
							
				}
				paraValueBuff.append(s + " ");
				String rep = "$"+v.get(i).toString().trim()+"$";
				this.formula = Utility.replaceString(this.formula, rep, s);
			}
			this.formula = paraValueBuff.toString() +"\n" + this.formula;
			String formulaname="";
			if(fileName != null && !fileName.trim().equalsIgnoreCase(""))
				formulaname = this.fileName.substring(0,this.fileName.indexOf(".fl"));
			
			if(type==CUSTOM)
				this.panelForGraph.executeCustmizedIndicator(formulaname+para, this.formula.toUpperCase());
			else if(type==STRATEGY)
			{
				try
				{
					formulaname = formulaname.substring(0,formulaname.indexOf(".fl"));
				}
				catch(Exception e1)
				{
					
				}
			
				HashMap hs1 = new HashMap();
				hs1.put(StrategyUtility.Formula, formula.toUpperCase());
				hs1.put(StrategyUtility.Type, "B");
				hs1.put(StrategyUtility.FormulaName, formulaname);
				
				Vector newVector = new Vector();
				newVector.add(hs1);

				// ((PanelForGraph)maingraph).macdgraph.drawStrategy(newVector);
				panelForGraph.drawStrategy(newVector);
				
			}
	
			this.dispose();
			
		}
		else 	if(but.getName().equalsIgnoreCase("cancel"))
		{
			this.dispose();
		}

		
		// TODO Auto-generated method stub
		
	}
	

}

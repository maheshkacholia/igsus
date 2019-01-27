/*
 * Created on Feb 19, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.formulabuilder;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Label;
import java.awt.List;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.omg.CosNaming.IstringHelper;

import com.stockfaxforu.component.*;
import com.stockfaxforu.frontend.HelpWindow;
import com.stockfaxforu.frontend.MainScreen;
import com.stockfaxforu.frontend.PanelForGraph;
import com.stockfaxforu.scan.frontend.StockFilter;
import com.stockfaxforu.scan.frontend.StockFilterInterface;
import com.stockfaxforu.scan.util.StockScreenerThreadMaster;
import com.stockfaxforu.strategy.BuySellStrategy;
import com.stockfaxforu.strategy.StrategyUtility;
import com.stockfaxforu.strategy.StrategyUtilityStockScreener;
import com.stockfaxforu.util.IndexUtility;
import com.stockfaxforu.util.ManageIndicator;
import com.stockfaxforu.util.Utility;

/**
 * @author sdeopura
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class FormulaBuilderScreen extends JFrame implements ActionListener,ListSelectionListener,WindowListener
{
	/**
	 * This is the default constructor
	 */
	public JTextField[] variable =  null;
	public JButton addindicator = null;
	public JButton cancel = null;
	public JList indiactorList = null;
	public StockFilterInterface maingraph=null;
	public String indicatorName=null;
	public Indicator selInd = null;
	public JLabel[] periods = new JLabel[5];
	public JList operatorList=null;
	public String screenType="";
	public FormulaDisplayPanel fordispanel = new FormulaDisplayPanel();

	public JButton addoperator = null;
	
	public JLabel formulaName = null;
	public JLabel formulaType = null;
	
	boolean isStockScreener=false;
	JComboBox combobox;
	public FormulaBuilderScreen()
	
	{
		super();
		initialize();
	}
	
	public FormulaBuilderScreen(StockFilterInterface maingraph,String s,boolean b)
	{
//		super(maingraph,s,b);
		setBackground(Color.lightGray);
		this.isStockScreener=b;
		
		this.maingraph = maingraph;

		this.setSize(1100,300);
		this.move(200,200);
		this.setContentPane(getJContentPane());
		this.setVisible(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	public FormulaBuilderScreen(StockFilterInterface maingraph)
	{
//		super(null);
		setBackground(Color.lightGray);
		
		this.maingraph = maingraph;

		this.setSize(1100,300);
		this.move(200,200);
		this.setContentPane(getJContentPane());
				this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.addWindowListener(this);
		
	}
	private javax.swing.JPanel jContentPane = null;
	private javax.swing.JPanel getJContentPane() 
	{
		Image im = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("image/toolicon.jpg"));
		this.setIconImage(im);

		this.setTitle("Formula Builder Screen");
		int xinc = 10;
		int yinc = 35;
		int xincdis=70;
		variable =  new JTextField[3];

		
		
		if(jContentPane == null) 
		{
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
		}
		
		JButton save = new JButton();
		save.setBounds(xinc,5,150,24);
		save.setName("save");
		save.setText("Copy Formula");
		jContentPane.add(save,null);
		save.addActionListener(this);

		
		int xpos = addIcons(160,0);
/*		if(this.isStockScreener)
		{
			combobox = new JComboBox(IndexUtility.getFilters());
			combobox.setBounds(xpos, 5, 125, 20);
			jContentPane.add(combobox,null);

		}
*/		
//		jContentPane.setBackground(Color.white);
		JSeparator jsep = new JSeparator(SwingConstants.HORIZONTAL);
		jsep.setBounds(0,yinc, 1100,20);
//		jsep.setForeground(Color.white);
		jsep.setBackground(Color.black);

		jContentPane.add(jsep,null);
		


		yinc = yinc+20;


		int ysize = 150;
		Vector indiName=null;
		if(this.isStockScreener)
		{
			indiName = ManageVariable.getIndicatorNameForStockScreener();
			
		}
		else
		{
			indiName = ManageVariable.getIndicatorName();
			
		}
		indiName.addElement("Number");
		indiactorList = new JList(indiName);
		indiactorList.setFont(new Font("Arial",Font.BOLD,10));
		indiactorList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		indiactorList.addListSelectionListener(this);
		JScrollPane scroolpane = new JScrollPane(indiactorList);
		scroolpane.setBounds(xinc,yinc,200,ysize);
		
			
		jContentPane.add(scroolpane,null);
//		indiactorList.addActionListener(this);

//		yinc = yinc + 210;

		addindicator = new JButton();
		addindicator.setBounds(xinc,yinc+ysize+10,75,20);
		addindicator.setName("addindicator");
		addindicator.setText("Add");
		jContentPane.add(addindicator,null);
		addindicator.addActionListener(this);

		xinc = xinc + 210;
		
		Vector operatorVector = ManageVariable.getOperators();
		operatorList = new JList(operatorVector);
		operatorList.setFont(new Font("Arial",Font.BOLD,12));
		operatorList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scroolpane = new JScrollPane(operatorList);
		scroolpane.setBounds(xinc,yinc,60,ysize);
		
			
		jContentPane.add(scroolpane,null);
//		indiactorList.addActionListener(this);



		addoperator = new JButton();
		addoperator.setBounds(xinc,yinc+ysize+10,60,20);
		addoperator.setName("addoperation");
		addoperator.setText("Add");
		jContentPane.add(addoperator,null);
		addoperator.addActionListener(this);

		
		xinc = xinc + 20;

		JSeparator jsep2 = new JSeparator(SwingConstants.VERTICAL);
		jsep2.setBounds(xinc+50, yinc-20, 20,ysize+60);
//		jsep.setForeground(Color.white);
		jsep2.setBackground(Color.black);

		jContentPane.add(jsep2,null);


		this.fordispanel = new FormulaDisplayPanel();
		fordispanel.setBounds(xinc+70,yinc-10,800,ysize);
		fordispanel.setBackground(Color.black);
		fordispanel.setName("addoperation");
		jContentPane.add(fordispanel,null);


		JLabel label5 = new JLabel("Formula Name :");
		label5.setBounds(xinc+70,yinc+ysize-10,100,20);
		label5.setForeground(Color.red);
		jContentPane.add(label5,null);

		this.formulaName = new JLabel("");
		this.formulaName.setBounds(xinc+70+110,yinc+ysize-10,400,20);
		this.formulaName.setForeground(Color.red);
		jContentPane.add(this.formulaName,null);

		JLabel label6 = new JLabel("Formula Type :");
		label6.setBounds(xinc+70,yinc+ysize+10,100,20);
		label6.setForeground(Color.red);
		jContentPane.add(label6,null);

		
		this.formulaType = new JLabel("");
		this.formulaType.setBounds(xinc+70+110,yinc+ysize+10,800,20);
		this.formulaType.setForeground(Color.red);
		jContentPane.add(this.formulaType,null);

		
		xinc = xinc + 20;


		
		JSeparator jsep1 = new JSeparator(SwingConstants.HORIZONTAL);
		jsep1.setBounds(0,yinc+ysize+40, 1100,20);
//		jsep.setForeground(Color.white);
		jsep1.setBackground(Color.black);

		jContentPane.add(jsep1,null);
		
		
/*		cancel = new JButton();
		cancel.setBounds(xinc+100,yinc,75,20);
		cancel.setName("cancel");
		cancel.setText("Cancel");
		jContentPane.add(cancel,null);
		cancel.addActionListener(this);
*/		 
		return jContentPane;	 
	
	}	
	JButton[] icon = new JButton[1];
	String[] gifname = {"help",};
	String[] gifname1 = {"help.jpg",};

	String[] desc = {"Help",};

	public int  addIcons(int xpos,int ypos)
	{
		int iconsize=30;
		for(int i=0;i<gifname.length;i++)
		{
			String s = "image/formulabuilder/"+gifname1[i];
			ImageIcon cup = new ImageIcon(ClassLoader.getSystemResource(s));
//			if(this.isStockScreener && i==4)
//			{
//				continue;
//			}
			
			icon[i] = new JButton(cup);
			icon[i].setBounds(xpos,ypos,iconsize,iconsize);
			icon[i].setName(gifname[i]);
			icon[i].setToolTipText(desc[i]);
			jContentPane.add(icon[i],null);
			icon[i].addActionListener(this);
			xpos = xpos + iconsize +4 ;
		}
//		String s = "image/formulabuilder/open.jpg";
//		ImageIcon cup = new ImageIcon(ClassLoader.getSystemResource(s));
	
/*		icon[4] = new JButton(cup);
		icon[4].setBounds(xpos,ypos,iconsize,iconsize);
		icon[4].setName("sss");
		jContentPane.add(icon[4],null);
		icon[4].addActionListener(this);
		xpos = xpos + iconsize + 10 ;
*/
		return xpos;
		
		
	}


	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize()
	{
		this.setSize(300, 200);
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
			if (name.equalsIgnoreCase("help"))
			{
				HelpWindow searc = new HelpWindow(null);

			}

			else if(name.equalsIgnoreCase("addindicator"))
			{
				String s = (String)indiactorList.getSelectedValue();
				if(s==null)
				{
					JFrame mesg = new MessageDiaglog("No Operation is Selected,Pls Select a operation");
					return ;					
				}


				if(s.equals("Number"))
				{
					JFrame frme = new NumberEntryPanel(s,this);		
										
				}
				else
				{
					JFrame frme = new IndiactorParaEntry(s,this);		

				}
			}
			else if(name.equalsIgnoreCase("addoperation"))
			{
				String s = (String)operatorList.getSelectedValue();
				if(s==null)
				{
					JFrame mesg = new MessageDiaglog("No Operation is Selected,Pls Select a operation");
					return ;					
				}
				this.fordispanel.addToken(s);		
//				addoperator.setEnabled(false);
//				addindicator.setEnabled(true);
			}
			else if(name.equalsIgnoreCase("newformula"))
			{
				NewFormulaNameBox newformula = new NewFormulaNameBox(this);				
				newformula.operationname = name;
			}
			else if(name.equalsIgnoreCase("open"))
			{
				JFrame openformula = new OpenFormulaEditor(this);
//				newformula.operationname = name;							
				
			}
			else if(name.equalsIgnoreCase("save"))
			{
				String s  = "select symbol from table where " + fordispanel.getFormula();
				this.maingraph.updateText(s);
				this.dispose();
				
			}
			else if(name.equalsIgnoreCase("saveas"))
			{

				NewFormulaNameBox newformula = new NewFormulaNameBox(this);				
				newformula.operationname = name;

			}
			else if(name.equalsIgnoreCase("delete"))
			{
				
				
			}
			else if (name.equalsIgnoreCase("help"))
			{
				HelpWindow searc = new HelpWindow(null);

			}

			else if(name.equalsIgnoreCase("runstrategy"))
			{
				if(isStockScreener)
				{
					StockScreenerThreadMaster master = new StockScreenerThreadMaster();
					this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
					
					BuySellStrategy buysell = new BuySellStrategy();
					Vector newVector = new Vector();
					newVector.addElement(fordispanel.getFormula());

					Vector symbolVector = new Vector();
					Vector symbols = IndexUtility.getIndexStockVector((String)combobox.getSelectedItem());

						for(int i=0;i<symbols.size();i++)
						{
							HashMap hs = new HashMap();
							hs.put(MainGraphComponent.Symbol,symbols.elementAt(i));
							symbolVector.addElement(hs);
						}

					// ln( "select symbol from table where " + fordispanel.getFormula());	
					Vector output = buysell.getAllStocksQuery(symbolVector, "select symbol from table where " + fordispanel.getFormula());	
					// ln(output);
					this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
					StockScreenerResult stockscreener = new StockScreenerResult(output);
				}
				else
				{
/*					if(this.maingraph.panel instanceof PanelForGraph)
					{
						PanelForGraph panel11 = (PanelForGraph)this.maingraph.panel;
						Vector v = new Vector();
						String formulaType = this.formulaType.getText();
						if(formulaType==null || formulaType.trim().equals(""))
							formulaType="Buy";
					
						HashMap hs1 = new HashMap();
						hs1.put(StrategyUtility.Formula,fordispanel.getFormula());
						hs1.put(StrategyUtility.Type,formulaType);
		
						v.addElement(hs1);
						panel11.macdgraph.drawStrategy(v);
						this.maingraph.repaint();
					}
	*/				
				}
				

				
			}
			else if(name.equalsIgnoreCase("cancel"))
			{
//				this.dispose();
			
			}
						
		}
		// TODO Auto-generated method stub
		
	}
	/**
	 * @return
	 */
	public void saveFormula(String formula,String formulanName,String formulaType,String operationName,String comment)
	{
		if(this.isStockScreener)
		{
			StrategyUtilityStockScreener.saveFormula(formulaType,formulanName,formula,comment);
			
		}
		else
		{
	//		StrategyUtility.saveFormula(formulaType,formulanName,formula,comment);
			
		}
	}
	
	private boolean checkValue()
	{
		// TODO Auto-generated method stub
		return false;
	}
	public static void main(String[] args)
		{
			JFrame f = new JFrame();

			FormulaBuilderScreen d = new FormulaBuilderScreen(null);
		}
	/* (non-Javadoc)
	 * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
	 */
	public void valueChanged(ListSelectionEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowOpened(java.awt.event.WindowEvent)
	 */
	public void windowOpened(WindowEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowClosing(java.awt.event.WindowEvent)
	 */
	public void windowClosing(WindowEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowClosed(java.awt.event.WindowEvent)
	 */
	public void windowClosed(WindowEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowIconified(java.awt.event.WindowEvent)
	 */
	public void windowIconified(WindowEvent arg0)
	{
//		// ln("windowIconified");

		this.toFront();

		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowDeiconified(java.awt.event.WindowEvent)
	 */
	public void windowDeiconified(WindowEvent arg0)
	{
//		// ln("windowDeiconified");

		this.toFront();

		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowActivated(java.awt.event.WindowEvent)
	 */
	public void windowActivated(WindowEvent arg0)
	{
//		// ln("windowActivated");

		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowDeactivated(java.awt.event.WindowEvent)
	 */
	public void windowDeactivated(WindowEvent arg0)
	{
//		// ln("windowDeactivated");
		this.toFront();
		// TODO Auto-generated method stub
		
	}
	
}  //  @jve:visual-info  decl-index=0 visual-constraint="4,11"

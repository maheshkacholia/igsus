/*
 * Created on Feb 19, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.frontend;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Label;
import java.awt.List;
import java.awt.TextArea;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.stockfaxforu.component.*;
import com.stockfaxforu.formulabuilder.MessageDiaglog;
import com.stockfaxforu.frontend.ComboBoxRendererSymbol;
import com.stockfaxforu.frontend.IndexComboBox;
import com.stockfaxforu.frontend.MainScreen;
import com.stockfaxforu.strategy.StrategyUtility;
import com.stockfaxforu.strategy.StrategyUtilityStockScreener;
import com.stockfaxforu.swingcomponent.IGSLabel;
import com.stockfaxforu.util.IndexUtility;
import com.stockfaxforu.util.ManageIndicator;
import com.stockfaxforu.util.ManageStrategy;
import com.stockfaxforu.util.StockConstants;
import com.stockfaxforu.util.Utility;
/**
 * @author sdeopura
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ManageStrategyScreen extends JFrame implements ManageIndicatorInterface,ListSelectionListener, KeyListener, ActionListener, MouseListener
{
	public JTextField stockcodeText;
	public Vector strategyList=null;
	/**
	 * This is the default constructor
	 */
	JTextField number = null;
	JTextField daybefore = null;
	JButton add = null;
	JButton cancel = null;
	String indicatorName = null;
	Vector paras = new Vector();
	JComboBox operatorList = null;
	int index = 0;
	JComboBox type = null;
	String operationname = "";
	JLabel msg = null;
	String symbol;
	JComboBox categoryCombo;
	Vector categoryVector;
	JList list1,list;
	Vector selectedStrategies=null;
	JComboBox indexes=null;
	JButton addstock=null;
	TextArea comment=null;
	PanelForGraph panelforgraph;
	public ManageStrategyScreen(PanelForGraph panelforgraph)
	{
		if(Utility.isBasicUser())
		{
			return;
		}
		//		super(null);
		setBackground(Color.lightGray);
		this.panelforgraph = panelforgraph;
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Image im = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("image/toolicon.jpg"));
		this.setIconImage(im);

		this.setContentPane(getJContentPane());
		this.setTitle("Manage Strategies");
		this.move(300, 100);
		this.setSize(850, 650);
		this.setVisible(true);
	}
	private javax.swing.JPanel jContentPane = null;
	private javax.swing.JPanel getJContentPane()
	{
		int xinc = 50;
		int yinc = 10;
		int xincdis = 120;
		if (jContentPane == null)
		{
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
		}
		
		yinc = yinc + 25;
		xinc = 50;	

		IGSLabel lable1 = new IGSLabel("Select Strategy:");
		lable1.setBounds(xinc, yinc, 100, 15);
		jContentPane.add(lable1, null);

		xinc = xinc + 400;

		IGSLabel lable2 = new IGSLabel("Selected Strategies:");
		lable2.setBounds(xinc, yinc, 200, 15);
		jContentPane.add(lable2, null);
		
		yinc = yinc + 20;
		xinc = 50;	
		

		strategyList = ManageStrategy.getStrategyName();
		list = new JList(strategyList);
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		list.addListSelectionListener(this);
		JScrollPane listScrollPane = new JScrollPane(list);
		listScrollPane.setBounds(xinc, yinc, 275, 300);
		jContentPane.add(listScrollPane);
		
		selectedStrategies = panelforgraph.getAllAppliedStrategies();
		
		list1 = new JList(selectedStrategies);
		list1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list1.setSelectedIndex(0);
		list1.addListSelectionListener(this);
		JScrollPane pictureScrollPane = new JScrollPane(list1);
		pictureScrollPane.setBounds(xinc + 400, yinc, 275, 300);
		jContentPane.add(pictureScrollPane);

		comment = new TextArea(50,800);
		comment.setBounds(50, yinc+320, 800,200);
		comment.setBackground(this.getForeground());
//		comment.disable();
		jContentPane.add(comment);

		
		yinc = yinc + 320;
		JButton ok = new JButton("OK");
		ok.setFont(new Font("Arial", Font.BOLD, 12));
		ok.setBounds(xinc, yinc, 100, 20);
		ok.addActionListener(this);
		ok.setName("ok");
		jContentPane.add(ok);
		JButton cancel = new JButton("Cancel");
		cancel.setFont(new Font("Arial", Font.BOLD, 12));
		cancel.setBounds(xinc + 225, yinc, 100, 20);
		cancel.addActionListener(this);
		cancel.setName("cancel");
		jContentPane.add(cancel);


		xinc = 335;
		yinc = 200;

		JButton select = new JButton(">>");
		select.setFont(new Font("Arial", Font.BOLD, 12));
		select.setBounds(xinc, yinc, 100, 20);
		select.addActionListener(this);
		select.setName("select");
		jContentPane.add(select);
		
		yinc = yinc + 100;
		JButton deselect = new JButton("<<");
		deselect.setFont(new Font("Arial", Font.BOLD, 12));
		deselect.setBounds(xinc , yinc, 100, 20);
		deselect.addActionListener(this);
		deselect.setName("deselect");
		jContentPane.add(deselect);
		
		
		return jContentPane;


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
	String inexName ="";

	public void actionPerformed(ActionEvent e)
	{
		Object o = (Object) e.getSource();
		
		 if (o instanceof JButton)
		{
			JButton cb = (JButton) e.getSource();
			String name = cb.getName();
			if (name.equalsIgnoreCase("select"))
			{
				int value = list.getSelectedIndex();
				if(value == -1)
					return;
				String indname	=	strategyList.get(value)+"";
				panelforgraph.applyCustmizedIndicator(this,StockConstants.NEW_STR_DIR, indname+".fl");
				
			} 
			else if (name.equalsIgnoreCase("deselect"))
			{
				Object value = (Object )list1.getSelectedValue();
				if(value==null)
					return;
				
				panelforgraph.removeStrategy(value.toString());
				selectedStrategies = panelforgraph.getAllAppliedStrategies();
				list1.setListData(selectedStrategies);						
			} 
			else if (name.equalsIgnoreCase("ok"))
			{
				this.dispose();
			} 

			else if (name.equalsIgnoreCase("cancel"))
			{
				this.dispose();
			}
		}
	}
	public String getComments(String indname)
	{
		try
		{
			String s = Utility.getFileContent(StockConstants.NEW_STR_DIR+ "/"+indname+".fl");
			StringTokenizer st = new StringTokenizer(s,"\n");
			int i=0;
			StringBuffer sb = new StringBuffer();
			while(st.hasMoreTokens())
			{
					String commentLine = st.nextToken();
					if(!commentLine.toUpperCase().startsWith("@"))
						return sb.toString();
					if(commentLine.toUpperCase().startsWith("@COMMENT"))
					{
						sb.append(commentLine.substring("@COMMENT".length())+"\n");
					}
			}
			return sb.toString();
			
		}
		catch(Exception e)
		{
			return "";
		}
		
		
	}
	public void addCustomIndicator()
	{
	//	selectedStrategies = panelforgraph.macdgraph.getIndicators();
	//	System.out.println("selind=="+selectedIndicators);
		
	//	selectedStrategies = panelforgraph.macdgraph.getIndicators();
		selectedStrategies = panelforgraph.getAllAppliedStrategies();
		list1.setListData(selectedStrategies);						

	}
	/**
	 * @param s
	 */
	public static void main(String[] args)
	{
		ManageStrategyScreen addremove = new ManageStrategyScreen(null);
	}
	/* (non-Javadoc)
	 * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
	 */
	boolean textEventChk = false;

	public void valueChanged(ListSelectionEvent e)
	{
		try
		{
			if (textEventChk)
			{
				textEventChk = false;
				return;
			}
			JList mylist = (JList) e.getSource();
			int i = mylist.getSelectedIndex();
			String s = getComments(mylist.getSelectedValue().toString());
			comment.setText(s);
			list.setSelectedIndex(i);
			list.ensureIndexIsVisible(i);
//			stockcodeText.setText((String) list.getSelectedValue());
			
		}
		catch(Exception e1)
		{
			
		}
	}

	public void mouseClicked(MouseEvent arg0)
	{
	}
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	public void mousePressed(MouseEvent arg0)
	{
		// TODO Auto-generated method stub
	}
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	public void mouseReleased(MouseEvent arg0)
	{
		// TODO Auto-generated method stub
	}
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	public void mouseEntered(MouseEvent arg0)
	{
		// TODO Auto-generated method stub
	}
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	public void mouseExited(MouseEvent arg0)
	{
		// TODO Auto-generated method stub
	}
	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	public void keyTyped(KeyEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	public void keyPressed(KeyEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	public void keyReleased(KeyEvent arg0)
	{
		Object obj = arg0.getSource();
		if (obj instanceof JTextField)
		{
			String name = ((JTextField) obj).getName();
			if (name.equals("stockcodeText"))
			{
				String s = stockcodeText.getText().trim();
			}
		}	
	}
	public void addIndicator(Indicator selInd) 
	{
		//selInd.indicatorName;
		// TODO Auto-generated method stub
		selectedStrategies = panelforgraph.getAllAppliedStrategies();
		list1.setListData(selectedStrategies);						
		
	}
	public void removeIndicator(Indicator selInd) {
		// TODO Auto-generated method stub
		
	}
	public int getIndiactorType() {
		// TODO Auto-generated method stub
		return ManageIndicatorInterface.STRATEGY;
	}

	
} //  @jve:visual-info  decl-index=0 visual-constraint="4,11"

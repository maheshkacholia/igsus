package com.stockfaxforu.frontend;

import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.stockfaxforu.component.Indicator;
import com.stockfaxforu.formulabuilder.MessageDiaglog;
import com.stockfaxforu.livedata.MainframeInterface;
import com.stockfaxforu.scan.frontend.AlertScreenInterface;
import com.stockfaxforu.scan.frontend.StockFilterAlert;
import com.stockfaxforu.util.NDTVMatching;
import com.stockfaxforu.util.StockConstants;

public class AlertScreen extends JFrame implements AlertScreenInterface,MainframeInterface,ListSelectionListener, KeyListener, ActionListener, MouseListener
{
	private JPanel panelforgrapha;
	private JList listNDTV;
	private JList listNSE;
	private JList listFinal;

	public  Vector stockListVector = new Vector();

	public static String Name="Name";
	public static String Formula="Formula";
	public static String Type="Type";
	public static String StockList="StockList";

	
	private JTextField code,formulaName;
	private JTextField value;
	JTextArea formulaText=null,stockListText=null;
	private javax.swing.JPanel jContentPane;
	JComboBox alertType=null;
	public JList activeList=null,inactiveList=null;
	public static Vector activeListVector = new Vector();
	public static Vector inactiveListVector = new Vector();
	
	public static HashMap alertListHashMap  = new HashMap();

	public static void main(String[] args)
	{
		AlertScreen d = AlertScreen.getSingleton();
		d.setVisible(true);

	}
	public static String Continous = "Continous";
	public static String HitAndRemove = "Hit and Remove";

	private static AlertScreen alertscreen = null;
	
	public static AlertScreen getSingleton()
	{
		if (alertscreen == null)
		{
			alertscreen = new AlertScreen(); 
		}
		return alertscreen;
	}
	
	private AlertScreen()
	{
		setBackground(Color.lightGray);
		this.panelforgrapha = panelforgrapha;
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setContentPane(getJContentPane());
		this.setTitle("Intraday Alert ");
		this.move(200, 200);
		this.setSize(750, 600);
//		this.setVisible(true);

	}
	public javax.swing.JPanel getJContentPane()
	{
		if (jContentPane == null)
		{
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
		}
		int xinc = 20;
		int yinc = 10;
		jContentPane = new JPanel(null);
		JLabel lable = new JLabel("Alert Name");
		lable.setFont(new Font("Arial", Font.BOLD, 12));
		lable.setForeground(Color.black);
		lable.setBounds(xinc, yinc, 100, 15);
		jContentPane.add(lable, null);
		
		formulaName = new JTextField();
		formulaName.setBounds(xinc+120, yinc, 100, 20);
		jContentPane.add(formulaName, null);
		
		yinc = yinc + 30;
		xinc = 20;

		JLabel lable2 = new JLabel("Alert Type");
		lable2.setFont(new Font("Arial", Font.BOLD, 12));
		lable2.setForeground(Color.black);
		lable2.setBounds(xinc, yinc, 100, 15);
		jContentPane.add(lable2, null);
		
		String[] alertTypeStr= {"Hit and Remove","Continous"};
		alertType = new JComboBox(alertTypeStr);
		alertType.setBounds(xinc+120, yinc, 200, 20);
		jContentPane.add(alertType, null);
		
		
		yinc = yinc + 30;
		xinc = 20;
		
		
		
		JLabel listlable1 = new JLabel("Alert For");
		listlable1.setFont(new Font("Arial", Font.BOLD, 12));
		listlable1.setForeground(Color.black);
		listlable1.setBounds(xinc, yinc, 100, 15);
		jContentPane.add(listlable1, null);
		
		xinc = xinc + 120;
		stockListText = new JTextArea(2,100);
		stockListText.setBounds(xinc, yinc, 400, 40);
		jContentPane.add(stockListText, null);
		xinc = xinc +410;
		JButton updateStockList = new JButton("Add/Update Stocks");
		updateStockList.setFont(new Font("Arial", Font.BOLD, 12));
		updateStockList.setBounds(xinc , yinc, 150, 20);
		updateStockList.addActionListener(this);
		updateStockList.setName("updatestocks");
		updateStockList.setToolTipText("For adding alert for specific stocks. if list is empty all stock are applicable");
		jContentPane.add(updateStockList);
		
		
		yinc = yinc + 50;
		xinc = 20;
		
		
		
		JLabel lable1 = new JLabel("Formula");
		lable1.setFont(new Font("Arial", Font.BOLD, 12));
		lable1.setForeground(Color.black);
		lable1.setBounds(xinc, yinc, 100, 15);
		jContentPane.add(lable1, null);
		
		xinc = xinc + 120;
		formulaText = new JTextArea(4,100);
		formulaText.setBounds(xinc, yinc, 400, 80);
		jContentPane.add(formulaText, null);
		
		xinc = xinc +410;
		JButton createformula = new JButton("Create Formula");
		createformula.setFont(new Font("Arial", Font.BOLD, 12));
		createformula.setBounds(xinc , yinc, 150, 20);
		createformula.addActionListener(this);
		createformula.setName("createformula");
		createformula.setToolTipText("Create formula or get existing formula here");
		jContentPane.add(createformula);
		
		
		
		
		yinc = yinc + 100;
		xinc = 20;
		

		JButton add = new JButton("Add/Update");
		add.setFont(new Font("Arial", Font.BOLD, 12));
		add.setBounds(xinc , yinc, 150, 20);
		add.addActionListener(this);
		add.setName("add");
		add.setToolTipText("Will add new alert to active list or will update existing alert");
		
		jContentPane.add(add);
		
		xinc = xinc + 160;
		JButton save = new JButton("Save");
		save.setFont(new Font("Arial", Font.BOLD, 12));
		save.setBounds(xinc , yinc, 100, 20);
		save.addActionListener(this);
		save.setName("save");
		save.setToolTipText("Will save alert to Hard Disk");
		
		jContentPane.add(save);

		xinc = xinc + 110;
		JButton open = new JButton("Open");
		open.setFont(new Font("Arial", Font.BOLD, 12));
		open.setBounds(xinc , yinc, 100, 20);
		open.addActionListener(this);
		open.setName("open");
		open.setToolTipText("Will open existing alerts");
		
		jContentPane.add(open);

		
		
		xinc = xinc + 110;
		JButton cancel = new JButton("Cancel");
		cancel.setFont(new Font("Arial", Font.BOLD, 12));
		cancel.setBounds(xinc  , yinc, 100, 20);
		cancel.addActionListener(this);
		cancel.setName("cancel");
		
		jContentPane.add(cancel);		
		
		yinc = yinc +50;
		xinc = 20;
		JLabel lable4 = new JLabel("Active Alert");
		lable4.setFont(new Font("Arial", Font.BOLD, 12));
		lable4.setForeground(Color.black);
		lable4.setBounds(xinc, yinc, 100, 15);
		jContentPane.add(lable4, null);
		
		JLabel lable5 = new JLabel("Inactive Alert");
		lable5.setFont(new Font("Arial", Font.BOLD, 12));
		lable5.setForeground(Color.black);
		lable5.setBounds(xinc+250, yinc, 100, 15);
		jContentPane.add(lable5, null);

		yinc = yinc +20;
		
		activeList = new JList(activeListVector);
		activeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		activeList.addListSelectionListener(this);
		activeList.addMouseListener(this);
		JScrollPane listScrollPane = new JScrollPane(activeList);
		listScrollPane.setBounds(xinc, yinc, 150, 200);
		jContentPane.add(listScrollPane);

		inactiveList = new JList(inactiveListVector);
		inactiveList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		inactiveList.addListSelectionListener(this);
		inactiveList.addMouseListener(this);
		JScrollPane listScrollPane1 = new JScrollPane(inactiveList);
		listScrollPane1.setBounds(xinc+250, yinc, 150, 200);
		jContentPane.add(listScrollPane1);
		
		JButton select = new JButton(">>");
		select.setFont(new Font("Arial", Font.BOLD, 12));
		select.setBounds(xinc + 175 , yinc + 50, 50, 20);
		select.addActionListener(this);
		select.setName("select");
		
		jContentPane.add(select);
	
		JButton remove = new JButton("<<");
		remove.setFont(new Font("Arial", Font.BOLD, 12));
		remove.setBounds(xinc + 175, yinc + 100, 50, 20);
		remove.addActionListener(this);
		remove.setName("remove");
		
		jContentPane.add(remove);
		
		return jContentPane;
	}
	
	
	public void valueChanged(ListSelectionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	
	public void actionPerformed(ActionEvent arg0) 
	{
		Object obj = (JButton)arg0.getSource();
		if(obj instanceof JButton)
		{
			String name = ((JButton)obj).getName();
			
			if(name.equals("save"))
			{
				String formula = formulaName.getText();

				HashMap hs = new HashMap();;
				hs.put(Name,this.formulaName.getText());
				hs.put(Formula,this.formulaText.getText());
				hs.put(Type,this.alertType.getSelectedItem());
				hs.put(StockList,this.stockListText.getText());
				
				
				FileDialog fd = new FileDialog(new Frame(), "Save Alert", FileDialog.SAVE);
				fd.setFile("*.alert");
				fd.setDirectory(StockConstants.ALERT_DIR);
				fd.setLocation(50, 50);
				fd.show();

				String fileName = fd.getFile();
				String dirName = fd.getDirectory();
				if (fileName != null && !fileName.equals("") && dirName != null && !dirName.equals(""))
				{
					File f = new File(dirName + "/" + fileName);
					try
					{
						FileOutputStream file = new FileOutputStream(f);
						ObjectOutputStream obj1 = new ObjectOutputStream(file);
					
						obj1.writeObject(hs);
						obj1.close();
						file.close();
					}
					catch (Exception e1)
					{
						e1.printStackTrace();
					}

				}

				
			}
			else if (name.equals("createformula") )
			{
				JFrame f = new JFrame();
				StockFilterAlert newscan = new StockFilterAlert(this,1000,1000);
//				newscan.drawStrategy();
				f.getContentPane().add(newscan);
				f.setSize(1000,200);
				f.show();
//				SelectStockScanner d = new SelectStockScanner(null);

			}
			else 	if(name.equals("open"))
			{
				FileDialog fd = new FileDialog(new Frame(),"Open Alert", FileDialog.LOAD);
				fd.setFile("*.alert");
				fd.setDirectory(StockConstants.ALERT_DIR);
				fd.setLocation(50, 50);
				fd.show();
				
				String fileName = fd.getFile();
				String dirName = fd.getDirectory();
				if(fileName != null && !fileName.equals("") && dirName != null && !dirName.equals(""))
				{
					File f = new File(dirName + "/" + fileName);
					try
					{
						FileInputStream file = new FileInputStream(f);
						ObjectInputStream obj1 = new ObjectInputStream(file);
						HashMap hs = (HashMap)obj1.readObject();

						String name1 = (String)hs.get(Name);
						String formula = (String)hs.get(Formula);
						String type = (String)hs.get(Type);
						String stocklist = (String)hs.get(StockList);
						if(stocklist==null )
							stocklist="";
					
						formulaName.setText(name1);
						formulaText.setText(formula);
						alertType.setSelectedItem(type);
						stockListText.setText(stocklist);
							
						
						obj1.close();
						file.close();
					}
					catch(Exception e1)
					{
						e1.printStackTrace();
					}
				
				}		
			}
				

			else if(name.equals("select"))
			{
				String formula = (String)activeList.getSelectedValue();
				inactiveListVector.add(formula);
				inactiveList.setListData(inactiveListVector);					 

				activeListVector.remove(formula);
				activeList.setListData(activeListVector);					 
			}
			else if(name.equals("remove"))
			{
				String formula = (String)inactiveList.getSelectedValue();
				activeListVector.add(formula);
				activeList.setListData(activeListVector);					 

				inactiveListVector.remove(formula);
				inactiveList.setListData(inactiveListVector);					 

			}
			else if(name.equals("add"))
			{
				String formula = formulaName.getText();

				HashMap hs = (HashMap)alertListHashMap.get(formula);
				if(hs != null)
				{
					hs.put(Name,this.formulaName.getText());
					hs.put(Formula,this.formulaText.getText());
					hs.put(Type,this.alertType.getSelectedItem());
					hs.put(StockList,this.stockListText.getText());
					
					return;	
				}
				activeListVector.add(formula);
				activeList.setListData(activeListVector);
				hs = new HashMap();
				hs.put(Name,this.formulaName.getText());
				hs.put(Formula,this.formulaText.getText());
				hs.put(Type,this.alertType.getSelectedItem());
				hs.put(StockList,this.stockListText.getText());
					
				alertListHashMap.put(formula,hs);

			}
			else if(name.equals("cancel"))
			{
			}
			else if(name.equals("updatestocks"))
			{
				SelectStockList stocklist = new SelectStockList(this);
			}
		}	
		
	}
	
	
	public void mouseClicked(MouseEvent arg0) 
	{
		Object obj = arg0.getSource();
		if (obj instanceof JList)
		{
			JList mylist = (JList) arg0.getSource();
			int i = mylist.getSelectedIndex();
			String s = (String) mylist.getSelectedValue();
			HashMap hs = (HashMap)alertListHashMap.get(s);
			if(hs==null)
				return;
			String name = (String)hs.get(Name);
			String formula = (String)hs.get(Formula);
			String type = (String)hs.get(Type);
			String stocklist = (String)hs.get(StockList);
			if(stocklist==null )
				stocklist="";
		
			formulaName.setText(name);
			formulaText.setText(formula);
			alertType.setSelectedItem(type);
			stockListText.setText(stocklist);
		}
		
	}

	
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

		public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public Vector getSelectedStockList()
	{
		String s = stockListText.getText();
		if(s==null || s.trim().equals(""))
		{
			return new Vector();
		}
		StringTokenizer st = new StringTokenizer(s,",");
		Vector v = new Vector();
		while(st.hasMoreTokens())
		{
			v.add(st.nextToken());
		}
		
		// TODO Auto-generated method stub
		return v;
	}

	public void updateStockList(String stocklist)
	{
		
		stockListText.setText(stocklist);
		// TODO Auto-generated method stub
		
	}

	public void getFormula(String formula)
	{
		formulaText.setText(formula);
		// TODO Auto-generated method stub
		
	}

}

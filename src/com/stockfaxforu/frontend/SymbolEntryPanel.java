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
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import com.stockfaxforu.component.*;
import com.stockfaxforu.formulabuilder.MessageDiaglog;
import com.stockfaxforu.util.ManageIndicator;
import com.stockfaxforu.util.SplitHandler;
import com.stockfaxforu.util.StockConstants;
import com.stockfaxforu.util.Utility;
/**
 * @author sdeopura
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class SymbolEntryPanel extends JFrame implements ActionListener
{
	/**
	 * This is the default constructor
	 */
	JTextField symbol = null;
	JTextField name = null;
	
	JTextField daybefore = null;
	JButton add = null;
	JButton cancel = null;
	Vector paras = new Vector();
	JComboBox operatorList = null;
	int index = 0;


	public SymbolEntryPanel()
	{
	
		//		super(null);
		setBackground(Color.lightGray);
		this.index = index;
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setContentPane(getJContentPane());
		this.setTitle("Symbol Entry Panel");
		this.move(300, 300);
		this.setSize(450, 200);
		this.setVisible(true);
		Image im = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("image/toolicon.jpg"));
		this.setIconImage(im);
		this.setResizable(false);
	}
	private javax.swing.JPanel jContentPane = null;
	private javax.swing.JPanel getJContentPane()
	{
		int xinc = 10;
		int yinc = 20;
		int xincdis = 120;
		
		if(jContentPane == null) 
		{
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
		}

		JLabel lable1 = new JLabel("Enter NSE Symbol and Name of Stock, which is missing and needs to be downloaded.");
		lable1.setFont(new Font("Arial", Font.PLAIN, 10));
		lable1.setForeground(Color.black);
		lable1.setBounds(xinc, yinc, 450, 15);
		
		jContentPane.add(lable1, null);

		yinc = yinc + 20;

		
		JLabel lable = new JLabel("Symbol :");
		lable.setFont(new Font("Arial", Font.BOLD, 12));
		lable.setForeground(Color.black);
		lable.setBounds(xinc, yinc, 100, 15);
		
		jContentPane.add(lable, null);
		
		symbol = new JTextField();	
		symbol.setFont(new Font("Arial", Font.BOLD, 12));
		symbol.setForeground(Color.black);
		symbol.setBounds(xinc + 150, yinc, 50, 20);
		jContentPane.add(symbol, null);
		
		yinc = yinc + 40;
		
		JLabel lable3 = new JLabel("Name :");
		lable3.setFont(new Font("Arial", Font.BOLD, 12));
		lable3.setForeground(Color.black);
		lable3.setBounds(xinc, yinc, 100, 15);
		
		jContentPane.add(lable3, null);
		
		name = new JTextField();	
		name.setFont(new Font("Arial", Font.BOLD, 12));
		name.setForeground(Color.black);
		name.setBounds(xinc + 150, yinc, 250, 20);
		jContentPane.add(name, null);
		
		yinc = yinc + 40;
		
		
		add = new JButton();
		add.setBounds(xinc, yinc, 75, 20);
		add.setName("add");
		add.setText("Add");
		jContentPane.add(add, null);
		add.addActionListener(this);
		cancel = new JButton();
		cancel.setBounds(xinc + 100, yinc, 75, 20);
		cancel.setName("cancel");
		cancel.setText("Cancel");
		jContentPane.add(cancel, null);
		cancel.addActionListener(this);
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
	public void actionPerformed(ActionEvent e)
	{
		Object o = (Object) e.getSource();
		if (o instanceof JButton)
		{
			JButton cb = (JButton) e.getSource();
			String symbolName = symbol.getText().toUpperCase();
			String nameStr = name.getText();
			String bname = ((JButton)o).getName();
			
			if (bname.equalsIgnoreCase("add"))
			{
				if(!Utility.isStock(symbolName))
				{
					try
					{
						SplitHandler.handleSplit(symbolName);
						RandomAccessFile r = new RandomAccessFile(StockConstants.INSTALL_DIR +"/allsymbol.txt","rw");
						r.seek(r.length());
						String s = symbolName +"|" + nameStr; 
						r.writeBytes(s+"\n");
						r.close();
						MessageDiaglog m = new MessageDiaglog("Symbol added successfully,restart software to verify.");

					}
					catch (Exception e1)
					{
						// TODO Auto-generated catch block
						MessageDiaglog msg = new MessageDiaglog("Symbol Not found.");
					}

				}
				else
				{
					MessageDiaglog m = new MessageDiaglog("Symbol already exists");
				}
			}
			else if (bname.equalsIgnoreCase("cancel"))
			{
				this.dispose();
			}
		}
	}
	/**
	 * @return
	 */
	public static void main(String[] args)
	{
		SymbolEntryPanel s = new SymbolEntryPanel();
	}
} //  @jve:visual-info  decl-index=0 visual-constraint="4,11"
	
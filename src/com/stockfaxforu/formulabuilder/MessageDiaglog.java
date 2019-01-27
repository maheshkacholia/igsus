/*
 * Created on Feb 19, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.formulabuilder;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Label;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
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
import com.stockfaxforu.util.ManageIndicator;
import com.stockfaxforu.util.StockConstants;
import com.stockfaxforu.util.Utility;
/**
 * @author sdeopura
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class MessageDiaglog extends JFrame implements ActionListener,MouseListener
{
	/**
	 * This is the default constructor
	 */
	JTextField number = null;
	JTextField daybefore = null;
	JButton add = null;
	JButton cancel = null;
	String indicatorName = null;
	FormulaBuilderScreen forbuilder;
	Vector paras = new Vector();
	JComboBox operatorList = null;
	FormulaDisplayPanel fordisplaypanel=null;
	int index = 0;
	String message="";
	boolean click=false;
	String url=null;
	public MessageDiaglog(String message,boolean click)
	{
		this.click=true;
		//		super(null);
		setBackground(Color.lightGray);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//		this.setTitle("Message Box");
		this.message = message;
		this.move(300, 300);
		
		this.setSize(350, 150);
		Image im = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("image/toolicon.jpg"));
		this.setIconImage(im);
		this.setResizable(false);

		this.setContentPane(getJContentPane());
		this.setVisible(true);
		
	}

	public MessageDiaglog(String message)
	{
		//		super(null);
		setBackground(Color.lightGray);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//		this.setTitle("Message Box");
		this.message = message;
		this.move(300, 300);
		this.setSize(350, 150);
		Image im = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("image/toolicon.jpg"));
		this.setIconImage(im);
		this.setResizable(false);

		this.setContentPane(getJContentPane());
		this.setVisible(true);
		
	}
	public MessageDiaglog(String message,String url)
	{
		//		super(null);
		setBackground(Color.lightGray);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//		this.setTitle("Message Box");
		this.message = message;
		this.move(300, 300);
		this.setSize(350, 150);
		Image im = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("image/toolicon.jpg"));
		this.url = url;
		
		this.setIconImage(im);
		this.setResizable(false);

		this.setContentPane(getJContentPane());
		this.setVisible(true);
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

		JLabel lable = new JLabel(this.message);
		Dimension d = lable.getPreferredSize();
		int x = d.width;
		lable.setFont(new Font("Arial", Font.BOLD, 12));
		lable.setForeground(Color.black);
		lable.setBounds(xinc, yinc, x, 15);
		if(this.click || this.url != null)
		{
			lable.addMouseListener(this);
		}
		this.setSize(x+50, 150);
	
		jContentPane.add(lable, null);
		yinc = yinc + 40;

		add = new JButton();
		add.setBounds(x/2-50, yinc, 100, 20);
		add.setName("ok");
		add.setText("OK");
		jContentPane.add(add, null);
		add.addActionListener(this);
		return jContentPane;
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e)
	{
		Object o = (Object) e.getSource();
		if (o instanceof JButton)
		{
			JButton cb = (JButton) e.getSource();
			String name = cb.getName();
			if (name.equalsIgnoreCase("ok"))
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
		JFrame frame = new JFrame("Test angled lines");
		frame.move(300, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Vector v = new Vector();
		v.addElement("Period 1(1..100)");
		v.addElement("Period 2(2..50)");
//		NumberEntryPanel d = new NumberEntryPanel(null, 0, null);
//		frame.getContentPane().add(d);
		frame.pack();
		//		frame.setBounds(0,0,1000,700);
		frame.setVisible(true);
	}

	public void mouseClicked(MouseEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}

	public void mouseEntered(MouseEvent arg0)
	{
		this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		// TODO Auto-generated method stub
		
	}

	public void mouseExited(MouseEvent arg0)
	{
		this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

		// TODO Auto-generated method stub
		
	}

	public void mousePressed(MouseEvent arg0)
	{
		Runtime runtime = Runtime.getRuntime();
		try
		{
			if ( this.click==true)
			{
				Process process =
					runtime.exec(
						StockConstants.IEEXPLORERLOC + "   -new " + "http://www.iguidestocks.com/project/igsnew/igs/buy.jsp");
				
			}
			else if(this.url != null)
			{
				Process process =
					runtime.exec(
						StockConstants.IEEXPLORERLOC + "   -new " + url);
				
			}
			
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
		}

	}

	public void mouseReleased(MouseEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}
} //  @jve:visual-info  decl-index=0 visual-constraint="4,11"

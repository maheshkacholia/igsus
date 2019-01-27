/*
 * Created on Feb 19, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.frontend;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import com.stockfaxforu.component.*;
import com.stockfaxforu.component.Indicator;
import com.stockfaxforu.util.ManageIndicator;
import com.stockfaxforu.util.Utility;
/**
 * @author sdeopura
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class DrawingToolPanel extends JPanel implements ActionListener
{
	public int iconsize = 24;
//	public int width = 9 * iconsize + 5;
//	public int height = iconsize + 35;
	public int xinc = 0;
	public int yinc = 0;
	public int xincdis = 100;

	/**
	 * This is the default constructor
	 */
	JTextField note = null;
	JButton add = null;
	JButton cancel = null;
	JComboBox indiactorList = null;
	PanelForGraph maingraph = null;
	String indicatorName = null;
	Indicator selInd = null;
	public DrawingToolPanel()
	{
		super(new FlowLayout());
		ImageIcon cup1 = new ImageIcon(ClassLoader.getSystemResource("image/drawing/line.jpg"));
		JButton line = new JButton(cup1);
//		line.setBounds(xinc, yinc, iconsize, iconsize);
		line.setName("line");
		line.setForeground(Color.darkGray);
		line.setToolTipText(" Draw Line ");
		add(line);
		line.addActionListener(this);
		
		xinc = xinc + iconsize;
		cup1 = new ImageIcon(ClassLoader.getSystemResource("image/drawing/trendline.jpg"));
		JButton trendline = new JButton(cup1);
//		trendline.setBounds(xinc, yinc, iconsize, iconsize);
		trendline.setName("trendline");
		trendline.setForeground(Color.darkGray);
		trendline.setToolTipText(" Draw Trend Line ");
		add(trendline);
		trendline.addActionListener(this);
		
		xinc = xinc + iconsize;
		cup1 = new ImageIcon(ClassLoader.getSystemResource("image/drawing/horline.jpg"));
		JButton horline = new JButton(cup1);
//		horline.setBounds(xinc, yinc, iconsize, iconsize);
		horline.setName("horline");
		horline.setForeground(Color.darkGray);
		horline.setToolTipText(" Draw Horizontal Line ");
		add(horline);
		horline.addActionListener(this);
		
		xinc = xinc + iconsize;
		cup1 = new ImageIcon(ClassLoader.getSystemResource("image/drawing/verline.jpg"));
		JButton verline = new JButton(cup1);
//		verline.setBounds(xinc, yinc, iconsize, iconsize);
		verline.setName("verline");
		verline.setForeground(Color.darkGray);
		verline.setToolTipText(" Draw Vertical Line ");
		add(verline);
		verline.addActionListener(this);
		
		xinc = xinc + iconsize;
		cup1 = new ImageIcon(ClassLoader.getSystemResource("image/drawing/addnote.jpg"));
		JButton addnote = new JButton(cup1);
//		addnote.setBounds(xinc, yinc, iconsize, iconsize);
		addnote.setName("addnote");
		addnote.setForeground(Color.darkGray);
		addnote.setToolTipText(" Add Note/Comment to Graph ");
		add(addnote);
		addnote.addActionListener(this);
		
		xinc = xinc + iconsize;
		cup1 = new ImageIcon(ClassLoader.getSystemResource("image/drawing/fibhor.jpg"));
		JButton fibhor = new JButton(cup1);
//		fibhor.setBounds(xinc, yinc, iconsize, iconsize);
		fibhor.setName("fibhor");
		fibhor.setForeground(Color.darkGray);
		fibhor.setToolTipText(" Draw Fibonnoci Retracement for Time ");
		add(fibhor);
		fibhor.addActionListener(this);
		
		xinc = xinc + iconsize;
		cup1 = new ImageIcon(ClassLoader.getSystemResource("image/drawing/fib.jpg"));
		JButton fib = new JButton(cup1);
//		fib.setBounds(xinc, yinc, iconsize, iconsize);
		fib.setName("fib");
		fib.setForeground(Color.darkGray);
		fib.setToolTipText(" Draw Fibonnoci Retracement ");
		add(fib);
		fib.addActionListener(this);
		
		xinc = xinc + iconsize;
		cup1 = new ImageIcon(ClassLoader.getSystemResource("image/drawing/buyarrow.jpg"));
		JButton buyarrow = new JButton(cup1);
//		buyarrow.setBounds(xinc, yinc, iconsize, iconsize);
		buyarrow.setName("buyarrow");
		buyarrow.setForeground(Color.darkGray);
		buyarrow.setToolTipText(" Draw Buy Arrow ");
		add(buyarrow);
		buyarrow.addActionListener(this);
		
		xinc = xinc + iconsize;
		cup1 = new ImageIcon(ClassLoader.getSystemResource("image/drawing/sellarrow.jpg"));
		JButton sellarrow = new JButton(cup1);
//		sellarrow.setBounds(xinc, yinc, iconsize, iconsize);
		sellarrow.setName("sellarrow");
		sellarrow.setForeground(Color.darkGray);
		sellarrow.setToolTipText(" Draw Sell Arrow ");
		add(sellarrow);
		sellarrow.addActionListener(this);
	
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize()
	{
//		this.setSize(width, height);
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
			String name = cb.getName();
			if (name.equalsIgnoreCase("add"))
			{
				maingraph.addNote(note.getText());
//				this.dispose();
			}
			if (name.equalsIgnoreCase("cancel"))
			{
//				this.dispose();
			}
		}
		if (o instanceof JFrame)
		{
//			this.dispose();
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
} //  @jve:visual-info  decl-index=0 visual-constraint="4,11"

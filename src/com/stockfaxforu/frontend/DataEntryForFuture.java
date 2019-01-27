/*
 * Created on Sep 28, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.frontend;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Label;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import com.stockfaxforu.component.Indicator;
import com.stockfaxforu.util.ManageIndicator;
import com.stockfaxforu.util.Utility;

import javax.swing.JFrame;

/**
 * @author sdeopura
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class DataEntryForFuture extends JFrame implements ActionListener
{

	JTextField note = null;
	JButton add = null;
	JButton cancel = null;
	JComboBox indiactorList = null;
	PanelForGraph maingraph = null;
	String indicatorName = null;
	Indicator selInd = null;

	/**
	 * 
	 */
	public DataEntryForFuture()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public DataEntryForFuture(PanelForGraph maingraph)
	{
		//		super(null);
		setBackground(Color.lightGray);
		this.maingraph = maingraph;
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setContentPane(getJContentPane());
		this.setTitle("Add Note");
		this.move(300, 300);
		this.setSize(400, 200);
		Image im = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("image/toolicon.jpg"));
		this.setIconImage(im);

		this.setVisible(true);
	}
	private javax.swing.JPanel jContentPane = null;
	private javax.swing.JPanel getJContentPane()
	{
		int xinc = 10;
		int yinc = 20;
		int xincdis = 100;
		note = new JTextField();

		if (jContentPane == null)
		{
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
		}
		JLabel lable = new JLabel("Note :");
		lable.setFont(new Font("Arial", Font.BOLD, 12));
		lable.setBounds(xinc, yinc, 100, 15);
		jContentPane.add(lable, null);

		xinc = xinc + 100;
		note.setFont(new Font("Arial", Font.BOLD, 12));
		note.setBounds(xinc, yinc, 250, 15);
		jContentPane.add(note, null);

		yinc = yinc + 50;
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
		this.setSize(300, 250);
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
				this.dispose();
			}
			if (name.equalsIgnoreCase("cancel"))
			{
				this.dispose();
			}

		}
		if (o instanceof JFrame)
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

		AddNotePanel d = new AddNotePanel(null);
		frame.getContentPane().add(d);
		frame.pack();
		//		frame.setBounds(0,0,1000,700);
		frame.setVisible(true);

	}

} //  @jve:visual-info  decl-index=0 visual-constraint="4,11"

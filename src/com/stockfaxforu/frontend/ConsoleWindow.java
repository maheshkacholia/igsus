/*
 * Created on May 8, 2009
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.frontend;

import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.stockfaxforu.util.StockConstants;

/**
 * @author Mahesh
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ConsoleWindow extends JFrame
{
	
	public ConsoleWindow()
	{
		this.move(100, 100);
		Image im = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("image/toolicon.jpg"));
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setIconImage(im);
		this.setTitle("Console Window");
		this.setSize(700,600);

		this.setContentPane(getJContentPane());
		this.setVisible(true);

	}
	public JPanel jContentPane;
	private javax.swing.JPanel getJContentPane()
	{
		if(jContentPane == null) 
		{
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
		}
		JTextArea	buyformula = new JTextArea(700,800);
		buyformula.setText(StockConstants.console.toString());
		JScrollPane scrollPane = new JScrollPane(buyformula); 
		scrollPane.setBounds(0,0,700-30,800-60);
		jContentPane.add(scrollPane,null);
		return jContentPane;
	}
	public static void main(String[] args)
	{
		ConsoleWindow console = new ConsoleWindow();
	}

}

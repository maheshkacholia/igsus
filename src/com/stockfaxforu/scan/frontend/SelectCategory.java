/*
 * Created on May 3, 2009
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.scan.frontend;

import java.awt.Color;
import java.awt.Image;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.stockfaxforu.frontend.IndexComboBox;
import com.stockfaxforu.strategy.StrategyUtility;
import com.stockfaxforu.util.IndexUtility;

/**
 * @author Mahesh
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class SelectCategory extends JFrame implements ActionListener
{
	JPanel jContentPane=null;
	FrameDataInterface myframe=null;
	public static String category=null;
	public static String  filter = null;
	public TextField  filterField = null; 
	public SelectCategory(FrameDataInterface myframe)
	{
		Image im = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("image/toolicon.jpg"));
		this.setIconImage(im);
		this.myframe = myframe;
		this.setTitle("Select Category");
		setBackground(Color.lightGray);
		this.setSize(700,250);
		setBackground(Color.lightGray);
		
			
		this.move(200,200);
		this.setContentPane(getJContentPane());
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			
	}
	JComboBox combo=null;
	private javax.swing.JPanel getJContentPane() 
	{
			if(jContentPane == null) 
			{
				jContentPane = new javax.swing.JPanel();
				jContentPane.setLayout(null);
			}
			
			int x=10;int y=10;
			JLabel label = new JLabel("Category");
			jContentPane.add(label);
			label.setBounds(x,y,100,25);
			Vector v = IndexUtility.getCategories();

			combo = new IndexComboBox(v);
			combo.setBounds(125,y,150,25);
			if(category != null)
			{
				combo.setSelectedItem(category);
			}
		jContentPane.add(combo);

/*		y = y +20;
		JLabel label1 = new JLabel("Select Formula");
		jContentPane.add(label1);
		label1.setBounds(x,y,100,20);
		
		v = StrategyUtility.getAllStrategy();
		strcombo = new JComboBox(v);
		strcombo.setBounds(125,y,150,20);
		jContentPane.add(strcombo);

		JButton detail = new JButton("Detail");
		detail.setBounds(300,y,100,20);
		detail.setName("detail");
		detail.addActionListener(this);
		jContentPane.add(detail);
*/
/*		
		y = y + 50;

		JLabel label2 = new JLabel("Filter ");
		jContentPane.add(label2);
		label2.setBounds(10,y,100,20);

		filterField = new TextField("VOLUME[0] > 100000 and CLOSE[0] > 1 and CLOSE[0] < 10000 ");
		filterField.setBounds(125,y,500,20);
		jContentPane.add(filterField);
		
		if(filter != null && !filter.equals(""))
		{
			filterField.setText(filter);
		}
*/

			y = y + 50;
			
			JButton ok = new JButton("OK");
			ok.setBounds(10,y,100,20);
			ok.setName("ok");
			ok.addActionListener(this);
		jContentPane.add(ok);
			
			JButton cancel = new JButton("Cancel");
			cancel.setBounds(150,y,100,20);
		cancel.setName("cancel");
					cancel.addActionListener(this);
		jContentPane.add(cancel);
			
			return jContentPane;
			
	}
	public JComboBox strcombo=null;
	public static void main(String[] args)
	{
		SelectCategory sel = new SelectCategory(null);
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent arg0)
	{
		
		Object obj = (Object) arg0.getSource();
		if (obj instanceof JButton)
		{
			String name = ((JButton) obj).getName();
			if (name.equals("ok"))
			{
				
				category = (String)combo.getSelectedItem();
//				filter = filterField.getText();
				filter = "";					
				this.myframe.setData(category,filter);
				this.dispose();		
			}
			else if (name.equals("cancel"))
			{
				this.dispose();		
			}
		}	
		// TODO Auto-generated method stub
		
	}

}

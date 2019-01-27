package com.stockfaxforu.dataextractor;

import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.stockfaxforu.formulabuilder.MessageDiaglog;
import com.stockfaxforu.frontend.FormulaEditor;
import com.stockfaxforu.swingcomponent.IGSLabel;
import com.stockfaxforu.util.IndexUtility;

public class DataExtractorScreen extends JFrame implements ActionListener
{
	public static void main(String[] args )
	{
		DataExtractorScreen data = new DataExtractorScreen();
	}
	public DataExtractorScreen()
	{
		this.setTitle("Finacial Data Download");
		setBackground(Color.lightGray);
		this.setSize(300,300);
		this.move(100,100);
		Image im = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("image/toolicon.jpg"));
		this.setIconImage(im);
	
		this.setContentPane(getJContentPane());
				this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	}
	JPanel jcontentpane=null;
	FormulaEditor formulaeditor =null;
	JComboBox category;
	private javax.swing.JPanel getJContentPane() 
	{
		if(jcontentpane==null)
		{
			jcontentpane = new JPanel();
			jcontentpane.setLayout(null);
		}
		int yinc =10;
		int xinc = 10;
		
		IGSLabel label = new IGSLabel("Category:");
		label.setBounds(xinc, yinc, 100, 20);
		jcontentpane.add(label);
		
		category = new JComboBox(IndexUtility.getCategories());
		category.setBounds(120,yinc, 100,20);
		jcontentpane.add(category);
		
		yinc = yinc + 50;
		
		JButton ok = new JButton("Download");
		ok.setName("ok");
		ok.setBounds(10,yinc,120,20);
		ok.addActionListener(this);
		jcontentpane.add(ok);
		JButton cancel = new JButton("Cancel");
		cancel.setName("cancel");
		cancel.setBounds(150,yinc,100,20);
		cancel.addActionListener(this);
		jcontentpane.add(cancel);
		
		return jcontentpane;
	}	
	public void actionPerformed(ActionEvent e) 
	{
		JButton but = (JButton)e.getSource();
		if(but.getName().equalsIgnoreCase("cancel"))
		{
			this.dispose();
		}
		else if (but.getName().equalsIgnoreCase("ok"))
		{
			if(DataExtractor.inside==false)
			{
				String categoryName = (String)category.getSelectedItem();
				Vector v10 = IndexUtility.getIndexStockVector(categoryName);
				DataExtractorThread data = new DataExtractorThread(v10);
				Thread th = new Thread(data);
				th.start();
//				DataExtractor.getStockDetail(v10);
			}
			else
			{
				MessageDiaglog msg = new MessageDiaglog("Curretnly Downloading Result");
			}
		}
	}
}

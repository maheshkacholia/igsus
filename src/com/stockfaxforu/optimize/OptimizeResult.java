package com.stockfaxforu.optimize;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.JTableHeader;

import com.stockfaxforu.util.StockConstants;

public class OptimizeResult extends JFrame implements MouseListener
{
	public Vector dataValues;
	public Vector cols;
	public int xmax=1100,ymax=1000;
	private javax.swing.JPanel jContentPane = null;

	public OptimizeResult(Vector dataValues,Vector cols)
	{
//		super(null);
		this.dataValues = dataValues;
		this.cols = cols;
		setBackground(Color.lightGray);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setContentPane(getJContentPane());
		this.setTitle("Optimization Result");
		this.move(100, 100);
		this.setSize(xmax,ymax);
		Image im = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("image/toolicon.jpg"));
		this.setIconImage(im);

		this.setVisible(true);
	}

	@Override
	public void resize(int width, int height) 
	{
		// TODO Auto-generated method stub
		super.resize(width, height);
		scrollPane.setBounds(0, 25, width, height-30);
	}
	JScrollPane scrollPane;
	private javax.swing.JPanel getJContentPane() 
	{
		if(jContentPane == null) 
		{
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(new BorderLayout());
		}
//		JLabel label = new JLabel("Start Date  " + StockConstants.STARTDATE  +  " End Date "  +  StockConstants.ENDDATE);
//		label.setBounds(0,0,xmax,20);
//		jContentPane.add(label);
		
		JTable table = new JTable(dataValues, cols);
	
		table.addMouseListener(this);
		JTableHeader tableheader = table.getTableHeader();
		tableheader.addMouseListener(this);	
		tableheader.setToolTipText("Click to sort");
		// Add the table to a scrolling pane
		scrollPane = JTable.createScrollPaneForTable( table );
//		scrollPane.setBounds(0,25, xmax-20, ymax-30);
		
		jContentPane.add(scrollPane);
		
		return jContentPane;
	}	
//	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

//	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

//	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

//	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

//	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
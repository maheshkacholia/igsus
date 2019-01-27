/*
 * Created on May 10, 2009
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.frontend;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * @author Mahesh
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class FormulaEditorScreen extends JFrame implements ComponentListener
{
	int x=800;
	int y=800;
	PanelForGraph panelforgraph=null;
	public FormulaEditorScreen()
	{
		this.setTitle("Formula Editor Screen ");
		setBackground(Color.lightGray);
		this.setSize(x,y);
		this.move(100,100);
		Image im = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("image/toolicon.jpg"));
		this.setIconImage(im);
	
		this.setContentPane(getJContentPane());
				this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.addComponentListener(this);

	}
	public FormulaEditorScreen(PanelForGraph panelforgraph)
	{
		this.setTitle("Formula Editor Screen ");
		setBackground(Color.lightGray);
		this.setSize(x,y);
		this.move(200,200);
		this.panelforgraph = panelforgraph;
		Image im = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("image/toolicon.jpg"));
		this.setIconImage(im);
	
		this.setContentPane(getJContentPane());
				this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.addComponentListener(this);
	}

	JPanel jcontentpane=null;
	FormulaEditor formulaeditor =null;

	private javax.swing.JPanel getJContentPane() 
	{
		if(jcontentpane==null)
		{
			jcontentpane = new JPanel();
			jcontentpane.setLayout(null);
		}
		int yinc =25;
		int xinc = 50;
		if(panelforgraph==null)
		{
			JPanel panel = MainScreen.getSingleton().panel;
			if(panel instanceof PanelForGraphImpl)
			{
				PanelForGraphImpl panelimpl = (PanelForGraphImpl)panel; 
				formulaeditor = new FormulaEditor(x,y,panelimpl);	
			
			}
			else
			{
				formulaeditor = new FormulaEditor(x,y,null);	
			
			}
		}
		else
		{
				formulaeditor = new FormulaEditor(x,y,panelforgraph);	
		}
		
		formulaeditor.setBounds(0,0, x, y);
		jcontentpane.add(formulaeditor);
		return jcontentpane;
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ComponentListener#componentResized(java.awt.event.ComponentEvent)
	 */
	public void componentResized(ComponentEvent arg0)
	{
		Dimension d = this.getSize();
		x = d.width;
		y = d.height;
		formulaeditor.resize(x,y);
		formulaeditor.setBounds(0,0, x, y);
	
		formulaeditor.buyformula.resize( x-40,y-60);
		formulaeditor.scrollPane.resize(x-40,y-60);
		formulaeditor.scrollPane.setBounds(0,40,x-40,y-60);
		formulaeditor.repaint();
		repaint();
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ComponentListener#componentMoved(java.awt.event.ComponentEvent)
	 */
	public void componentMoved(ComponentEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ComponentListener#componentShown(java.awt.event.ComponentEvent)
	 */
	public void componentShown(ComponentEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ComponentListener#componentHidden(java.awt.event.ComponentEvent)
	 */
	public void componentHidden(ComponentEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}
	public static void main(String[] args)
	{
		FormulaEditorScreen f = new FormulaEditorScreen();
	}
}

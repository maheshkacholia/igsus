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
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

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
public class FormulaDisplayPanel extends JPanel implements ActionListener, MouseListener,KeyListener
{
	public int iconsize = 24;
	//	public int width = 9 * iconsize + 5;
	//	public int height = iconsize + 35;
	public int xinc = 0;
	public int yinc = 10;
	//	public int xincdis = 100;
//	public int tokenno = 0;
	/**
	 * This is the default constructor
	 */
//	JLabel[] tokens = new JLabel[200];
	 Vector tokens = new Vector();

	Vector tokenVector = new Vector();
	HashMap tokenHashmap = new HashMap();
	String formulaName = "";
	String formulaType = "";
	JLabel stratetyLabel=null;
	JLabel stratetyTypeLabel=null;
	JLabel selectedLabel;
	public int insertpos=-1;

	public FormulaDisplayPanel()
	{
		super(null);

		
	}
	public void actionPerformed(ActionEvent e)
	{
		Object o = (Object) e.getSource();
		if (o instanceof JLabel)
		{
			JLabel cb = (JLabel) e.getSource();
			String name = cb.getName();
			HashMap hs = (HashMap) tokenHashmap.get(name);
		}
	}
	public void repaint()
	{
		xinc=10;
		yinc=10;

		if(tokens==null)
			tokens = new Vector();
		for(int i=0;i<tokens.size();i++)
		{
			JLabel temp = (JLabel)tokens.elementAt(i);
			temp.setName("TOKENNO-" + i);
			String s = temp.getText();	
			int width = s.length() * 9;
			Dimension d = temp.getPreferredSize();
			width = (int)d.getWidth();
			temp.setBounds(xinc, yinc, width, 20);
			xinc = xinc + width + 10;
			if (xinc > 600)
			{
				xinc = 10;
				yinc = yinc + 30;
			}
		}
		super.repaint();
	
	}
	/**
	 * @return
	 */
	private boolean checkValue()
	{
		// TODO Auto-generated method stub
		return false;
	}
	public static String TOKENNAME = "TOKENNAME";
	public void resetTokens()
	{
		for(int i=0;i < this.tokens.size();i++)
		{
			this.remove((JLabel)tokens.elementAt(i));
//			tokens.remove(i);
//			this.tokens[i] = null;

		}
		tokens = new Vector();
		repaint();	
		
	}	
	public void openFormula(String formulaName)
	{
		resetTokens();
		StringTokenizer st = new StringTokenizer(formulaName);
		while(st.hasMoreTokens())
		{
			addTokenForOpen(st.nextToken());
		}
		repaint();		
	}
	public String getFormula()
	{
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<tokens.size();i++)
		{
//			sb.append(this.tokens[i].getText()+ " ");
			sb.append(((JLabel)tokens.elementAt(i)).getText()+ " ");

		}
		return sb.toString();
	}
	public void addToken(String token)
	{
//		tokens[tokenno] = new JLabel(token);
		JLabel temp = new JLabel(token);	
		Dimension d = temp.getPreferredSize();
		int width = (int)d.getWidth();
		
//		tokens[tokenno].setBounds(xinc, yinc, width, 20);
		temp.setName("tokenno-" + tokens.size());
		add(temp, null);
		temp.addMouseListener(this);
		temp.addKeyListener(this);

		temp.setForeground(Color.white);
		
		if(this.insertpos >= 0 && this.insertpos < this.tokens.size() )
		{
			JLabel cb1 = (JLabel)this.tokens.elementAt(this.insertpos);
			cb1.setForeground(Color.white);
			tokens.insertElementAt(temp,this.insertpos);
		}
		else
		{
			tokens.addElement(temp);
			
		}
		
		insertpos = -1;

		repaint();
	}
	public void addTokenForOpen(String token)
	{
		insertpos = -1;
		JLabel temp = new JLabel(token);
		Dimension d = temp.getPreferredSize();
		int width = (int)d.getWidth();
		
//		tokens[tokenno].setBounds(xinc, yinc, width, 20);
		temp.setName("tokenno-" + tokens.size());
		add(temp, null);
		temp.addMouseListener(this);
		temp.addKeyListener(this);
		temp.setForeground(Color.white);

		tokens.addElement(temp);
		
//		repaint();
	}


	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	public void mouseClicked(MouseEvent arg0)
	{
//		// ln("mouse clicked");
		Object o = (Object) arg0.getSource();
		if(SwingUtilities.isLeftMouseButton(arg0))
		{
			if (o instanceof JLabel)
			{
				JLabel cb = (JLabel) arg0.getSource();
				String name = cb.getName();
				//			HashMap hs = (HashMap)tokenHashmap.get(name);
				String tokenvalue = cb.getText();
				StringTokenizer st = new StringTokenizer(name, "-");
				String s1 = st.nextToken();
				String s2 = st.nextToken();
				try
				{
					float value = Float.parseFloat(tokenvalue);
					NumberEntryPanel num = new NumberEntryPanel(tokenvalue, Integer.parseInt(s2), this);
				}
				catch (Exception e)
				{
					if (ManageVariable.isOperator(tokenvalue))
					{
						OperatorEditPanel operatoredit = new OperatorEditPanel(tokenvalue, Integer.parseInt(s2), this);
					}
					else
					{
						IndiactorParaEditPanel indeditpanel = new IndiactorParaEditPanel(tokenvalue, Integer.parseInt(s2), this);
					}
				}
			}

		}
		else if(SwingUtilities.isRightMouseButton(arg0))
		{
			InsertDeleteDiaglog msg = new InsertDeleteDiaglog((JLabel)o,this);
			
		}

	}
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	public void mousePressed(MouseEvent arg0)
	{
		// TODO Auto-generated method stub
	}
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	public void mouseReleased(MouseEvent arg0)
	{
		// TODO Auto-generated method stub
	}
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	public void mouseEntered(MouseEvent arg0)
	{
//		// ln("mouseEntered");
		Object o = (Object) arg0.getSource();
		if (o instanceof JLabel)
		{
			this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

			JLabel cb = (JLabel) arg0.getSource();
			cb.setForeground(Color.red);
			selectedLabel = cb;
		}
	}
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	public void mouseExited(MouseEvent arg0)
	{
		this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

		Object o = (Object) arg0.getSource();
		selectedLabel=null;
		if (o instanceof JLabel)
		{
			JLabel cb = (JLabel) arg0.getSource();
			cb.setForeground(Color.white);
			
			if(this.insertpos >= 0 && this.insertpos < this.tokens.size() )
			{
				JLabel cb1 = (JLabel)tokens.elementAt(this.insertpos);
				cb1.setForeground(Color.yellow);
			}

		}
	}
	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	public void keyTyped(KeyEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	public void keyPressed(KeyEvent evt)
	{
//		// ln("key event");
		
		int key = evt.getKeyCode(); // keyboard code for the pressed key
		if(key == KeyEvent.VK_DELETE)
		{
			if(selectedLabel != null)
			{
//				// ln(selectedLabel.getText());

				tokens.remove(selectedLabel);
				repaint();				
			}
		}

		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	public void keyReleased(KeyEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}
} //  @jve:visual-info  decl-index=0 visual-constraint="4,11"

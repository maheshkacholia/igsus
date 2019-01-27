/*
 * Created on Feb 19, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.formulabuilder;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Label;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import com.stockfaxforu.component.Indicator;
import com.stockfaxforu.util.ManageIndicator;
import com.stockfaxforu.util.Utility;
/**
 * @author sdeopura
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class InsertDeleteDiaglog extends JFrame implements ActionListener
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
	JLabel selectedlabel=null;
	public InsertDeleteDiaglog(JLabel selectedlabel,FormulaDisplayPanel forpanel)
	{
		//		super(null);
		setBackground(Color.lightGray);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setTitle("Insert/Delete Token");
		this.selectedlabel = selectedlabel;
		this.fordisplaypanel = forpanel;
		this.move(305, 200);
		this.setSize(350, 150);
		Image im = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("image/toolicon.jpg"));
		this.setIconImage(im);

		this.setContentPane(getJContentPane());
		this.setVisible(true);
	}

	private javax.swing.JPanel jContentPane = null;
	private javax.swing.JPanel getJContentPane()
	{
		int xinc = 10;
		int yinc = 50;
		int xincdis = 120;
		
		if(jContentPane == null) 
		{
			jContentPane = new javax.swing.JPanel();
			jContentPane.setLayout(null);
		}


		add = new JButton();
		add.setBounds(xinc, yinc, 100, 20);
		add.setName("delete");
		add.setText("Delete");
		jContentPane.add(add, null);
		add.addActionListener(this);
		xinc = xinc+110;
		JButton insert = new JButton();
		insert.setBounds(xinc, yinc, 100, 20);
		insert.setName("insert");
		insert.setText("Insert");
		jContentPane.add(insert, null);
		insert.addActionListener(this);
		xinc = xinc+110;
		JButton cancel = new JButton();
		cancel.setBounds(xinc, yinc, 100, 20);
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
			if (name.equalsIgnoreCase("delete"))
			{
				if(this.fordisplaypanel.insertpos >= 0 && this.fordisplaypanel.insertpos < fordisplaypanel.tokens.size() )
				{
					JLabel cb1 = (JLabel)fordisplaypanel.tokens.elementAt(fordisplaypanel.insertpos);
					cb1.setForeground(Color.white);
				}
				int i = this.fordisplaypanel.tokens.indexOf(this.selectedlabel);

				this.fordisplaypanel.remove(this.selectedlabel);

				this.fordisplaypanel.tokens.removeElementAt(i);

				
				this.fordisplaypanel.repaint();	
				this.fordisplaypanel.insertpos = -1;

				this.dispose();
			}
			else if (name.equalsIgnoreCase("insert"))
			{
				int insertpos = 0;
				int i = this.fordisplaypanel.tokens.indexOf(this.selectedlabel);
				if(i < 0 )
					return;
				if(this.fordisplaypanel.insertpos >= 0 && this.fordisplaypanel.insertpos < fordisplaypanel.tokens.size() )
				{
					JLabel cb1 = (JLabel)fordisplaypanel.tokens.elementAt(fordisplaypanel.insertpos);
					cb1.setForeground(Color.white);
				}
				this.fordisplaypanel.insertpos = i;	
				this.selectedlabel.setForeground(Color.yellow);

				this.fordisplaypanel.repaint();	
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
} //  @jve:visual-info  decl-index=0 visual-constraint="4,11"

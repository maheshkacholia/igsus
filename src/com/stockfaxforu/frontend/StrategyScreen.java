/*
 * Created on Feb 24, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.frontend;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;
import javax.swing.event.*;

import com.stockfaxforu.component.GraphComponentContainer;
import com.stockfaxforu.strategy.StrategyUtility;
import com.stockfaxforu.swingcomponent.IGSLabel;
import com.stockfaxforu.util.IndexUtility;
import com.stockfaxforu.util.StockConstants;
import com.stockfaxforu.util.Utility;
import java.util.*;
//SplitPaneDemo itself is not a visible component.
public class StrategyScreen extends JFrame implements ListSelectionListener, ActionListener, MouseListener,PropertyChangeListener
{
	private StrategyDataInterface macdgraph;
	private JList list;
	private JList list1;
	private JTextField code;
	private JTextField value;
	private javax.swing.JPanel jContentPane;
	DateButton startDateButton,endDateButton;
	IndexComboBox comboforindex=null;
	public static Vector listvector=new Vector();
	
	private JTextArea formula=null; 
	public StrategyScreen(StrategyDataInterface macdgraph)
	{
		//		super(null);
		setBackground(Color.lightGray);
		this.macdgraph = macdgraph;
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setContentPane(getJContentPane());
		this.setTitle("Select Strategy");
		this.move(300, 200);
		Image im = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("image/toolicon.jpg"));
		this.setIconImage(im);

		this.setSize(750, 650);
		this.setVisible(true);
	}	
		public javax.swing.JPanel getJContentPane()
		{

			if (jContentPane == null)
			{
				jContentPane = new javax.swing.JPanel();
				jContentPane.setLayout(null);
			}
			int xinc = 20;
			int yinc = 10;
			jContentPane = new JPanel(null);
			

			JLabel labelxy = new JLabel("Category");
			jContentPane.add(labelxy);
			labelxy.setBounds(xinc,yinc,100,25);
			Vector v = IndexUtility.getCategories();

			comboforindex = new IndexComboBox(v);
			comboforindex.setBounds(xinc+125,yinc,150,25);
			jContentPane.add(comboforindex);
			
			yinc = yinc + 40;
		
			
			JLabel labelxy1 = new JLabel("Start Date");
			jContentPane.add(labelxy1);
			labelxy1.setBounds(xinc,yinc,100,25);

			startDateButton = new DateButton();
			startDateButton.addPropertyChangeListener( "date", this );
			jContentPane.add(startDateButton);
			startDateButton.setBounds(xinc+125, yinc, 150, 20);
			
			yinc = yinc + 40;
		

			JLabel labelxy2 = new JLabel("End Date");
			jContentPane.add(labelxy2);
			labelxy2.setBounds(xinc,yinc,100,25);

			endDateButton = new DateButton();
			endDateButton.addPropertyChangeListener( "date", this );
			jContentPane.add(endDateButton);
			endDateButton.setBounds(xinc+125, yinc, 150, 20);
			
			
			JButton fileselect = new JButton("Add Strategy");
			fileselect.setName("fileselect");
			fileselect.setBounds(530,200,150,20);
			fileselect.addActionListener(this);
			jContentPane.add(fileselect);
			
			
			JButton fileremove = new JButton("Remove Strategy");
			fileremove.setName("fileremove");
			fileremove.setBounds(530,250,150,20);
			fileremove.addActionListener(this);
			jContentPane.add(fileremove);

			yinc = yinc + 40;
			
			IGSLabel label = new IGSLabel("Selected Strategies");
			label.setBounds(xinc,yinc,200,20);
			jContentPane.add(label);
			
			yinc = yinc + 25;
			
	//		listvector = new Vector();			
			list = new JList(listvector);
			list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			list.setSelectedIndex(0);
			list.addListSelectionListener(this);
			list.addMouseListener(this);
			JScrollPane listScrollPane = new JScrollPane(list);
			listScrollPane.setBounds(xinc, yinc, 400, 400);
			jContentPane.add(listScrollPane);
			
			yinc = yinc + 425;
			
			JButton apply = new JButton("Apply");
			apply.setFont(new Font("Arial", Font.BOLD, 12));
			apply.setBounds(xinc, yinc, 100, 20);
			apply.addActionListener(this);
			apply.setName("Apply");
			jContentPane.add(apply);
			
			
			JButton cancel = new JButton("Cancel");
			cancel.setFont(new Font("Arial", Font.BOLD, 12));
			cancel.setBounds(xinc + 225, yinc, 100, 20);
			cancel.addActionListener(this);
			cancel.setName("Cancel");
			jContentPane.add(cancel);
			return jContentPane;
		}
		//Listens to the list
		boolean textEventChk = false;
		public void valueChanged(ListSelectionEvent e)
		{
			Object obj = e.getSource();
			if (obj instanceof JList)
			{
				JList mylist = (JList) e.getSource();
				String s = (String)mylist.getSelectedValue();
				String forval = StrategyUtility.getFormula(s);
				formula.setText(forval);
			}
		}
		public JPanel getSplitPane()
		{
			return jContentPane;
		}
		public static void main(String[] args)
		{
			StrategyScreen st = new StrategyScreen(null);
		}
		/* (non-Javadoc)
		 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
		 */
		 
		public void actionPerformed(ActionEvent arg0)
		{
			Object obj = (JButton)arg0.getSource();
			if(obj instanceof JButton)
			{
				String name = ((JButton)obj).getName();
				if(name.equals("fileselect"))
				{
					FileDialog fd = new FileDialog(new Frame(),"Open Formula", FileDialog.LOAD);
					fd.setFile("*.fl");
					fd.setDirectory(StockConstants.FORMULA_DIR);
					fd.setLocation(50, 50);
					fd.show();
				
					String fileName = fd.getFile();
					String dirName = fd.getDirectory();
					
					if (fileName==null || fileName.equals("") || dirName==null || dirName.equals(""))
					{
						return;
					}
					
					listvector.add(dirName+fileName);						
					list.setListData(listvector);
					
				}
				else if(name.equals("fileremove"))
				{
					if(list.getSelectedIndex() == -1)
						return;
					String s = (String)list.getSelectedValue();
					listvector.remove(s);
					list.setListData(listvector);
				}
				else if(name.equals("Cancel"))
				{
					dispose();
				}
				else if(name.equals("Apply"))
				{
					Vector strategyVector = new Vector();
					for(int i=0;i<listvector.size();i++)
					{
						String s = (String)listvector.get(i);
						String formula="";
						try
						{
							formula = Utility.getFileContent(s);
						}
						catch (Exception e)
						{
							// TODO Auto-generated catch block
	//						e.printStackTrace();
						}
						
						HashMap hs = new HashMap();
						String name1 = s.substring(s.lastIndexOf('\\') + 1);
		//				// ln(name1);
						 hs.put(StrategyUtility.Name, name1);
						 hs.put(StrategyUtility.Formula,formula);
						strategyVector.addElement(hs);
					}
					
					macdgraph.drawStrategy(strategyVector,comboforindex.getSelectedItem()+"",this.startDateButton.getText(),this.endDateButton.getText());
					dispose();
				}

			}
			// TODO Auto-generated method stub
		}
		
			public void mouseClicked(MouseEvent arg0)
		{
			Object obj = arg0.getSource();
			if (obj instanceof JList)
			{
				JList mylist = (JList) arg0.getSource();
				String s = (String)mylist.getSelectedValue();
				String forval = StrategyUtility.getFormula(s);
				formula.setText(forval);
			}
			// TODO Auto-generated method stub
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
			// TODO Auto-generated method stub
		}
		/* (non-Javadoc)
		 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
		 */
		public void mouseExited(MouseEvent arg0)
		{
			// TODO Auto-generated method stub
		}
		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			// TODO Auto-generated method stub
			
		}
	}

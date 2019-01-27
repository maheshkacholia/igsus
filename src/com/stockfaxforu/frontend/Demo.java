package com.stockfaxforu.frontend;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.util.*;
import javax.swing.*;


import com.stockfaxforu.config.ConfigUtility;
import com.stockfaxforu.util.Utility;

public class Demo extends JFrame implements PropertyChangeListener,ActionListener
{
    private DateButton startDateButton;
    private DateButton endDateButton;
    private Date startDate;
    private Date endDate;
	public JComboBox contractcombo = null; 
	PanelForGraph panelforgraph;
    public Demo(PanelForGraph panelforgraph) 
    {
		super( "Select date" );
		this.panelforgraph = panelforgraph;
		startDate = new Date();
		endDate = new Date();
	
		Image im = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("image/toolicon.jpg"));
		this.setIconImage(im);

		startDateButton = new DateButton();
		startDateButton.addPropertyChangeListener( "date", this );
		endDateButton = new DateButton();
		endDateButton.addPropertyChangeListener( "date", this );
	
		getContentPane().setLayout( new GridLayout(4,2) );
		getContentPane().add( new JLabel("Start date") );
		getContentPane().add( startDateButton );
		getContentPane().add( new JLabel("End date") );
		getContentPane().add( endDateButton );
		getContentPane().add( new JLabel("Contract") );
		Vector v = ConfigUtility.getFutureExpiry();
		contractcombo = new JComboBox(v);
		getContentPane().add( contractcombo );
		
		JButton submit = new JButton("Apply");
		submit.setName("apply");
		getContentPane().add( submit );
		submit.addActionListener(this);
		
		JButton cancel = new JButton("Cancel");
		cancel.setName("cancel");
		getContentPane().add( cancel );
		cancel.addActionListener(this);
		this.move(300,300);
		
		pack();
		setResizable( false );
    
    }

    public void propertyChange( PropertyChangeEvent e ) {
	DateButton db = (DateButton)e.getSource();
	if ( db == startDateButton )
	 ;   // ( "Start date changed: " );
	else
	  ;  // ( "End date changed: " );
//	// ln( db.getDate() );
    }

    public static void main( String[] args ) {
	(new Demo(null)).show();
    }

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent arg0)
	{
		Object o = arg0.getSource();
		if(o instanceof JButton)
		{
			JButton but = (JButton)o;
			String name = (String)but.getName();
			if(name==null)
				return;
			if(name.equalsIgnoreCase("apply") )
			{
				String startDateStr = startDateButton.getText();
				String endDateStr = endDateButton.getText();
				
				StringTokenizer st = new StringTokenizer(startDateStr,"-");
				String mm = st.nextToken();
				String dd = st.nextToken();
				String yy = st.nextToken();
				startDateStr = dd+mm+yy;
				
				st = new StringTokenizer(endDateStr,"-");
				mm = st.nextToken();
				dd = st.nextToken();
				yy = st.nextToken();
				endDateStr = dd+mm+yy;
				
				
				String contractStr = (String)contractcombo.getSelectedItem();
				// ln(startDateStr+endDateStr+contractStr);
				this.dispose();
				panelforgraph.macdgraph.setForHistoricalFuture(startDateStr,endDateStr,contractStr);
			}
			else if(name.equalsIgnoreCase("cancel"))
			{
				this.dispose();
			}
		}
		// TODO Auto-generated method stub
		
	}
}



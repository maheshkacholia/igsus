/*
 * Created on Feb 18, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.frontend;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.Customizer;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;


import com.stockfaxforu.component.*;
import com.stockfaxforu.component.MainGraphComponent;
import com.stockfaxforu.formulabuilder.CustmizedIndicatorBuilderScreen;
import com.stockfaxforu.scan.frontend.IndiacatorValueScreen;
import com.stockfaxforu.util.StockConstants;
import com.stockfaxforu.util.Utility;
/**
 * @author sdeopura
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class PanelForNews extends JPanel 
{
	String[] petStrings = { "line", "trendline", "horline", "verline", "addnote","fibhor","fib","buyarrow","sellarrow" };
		String[] desp = { "Line", "Trendline", "Hor. Line", "Ver. Line", "Add Note","Fib. Ret(Time)","Fib Ret.","Buy Arrow","Sell Arrow" };
		String[] tooltips = {"Draw Line", "Draw Trendline", "Draw Horizontal Line", "Draw Vertical Line", "Add Note","Draw Fibinnoci Retracement(Time)","Draw Fibinnoci Retracement","Draw Buy Arrow","Draw Sell Arrow" };
	
	int x1, y1, x2, y2;
	public JComboBox symbollist;
	public GraphComponentContainer macdgraph = null;
	public Vector symbolVector;
	public String symbol;
	public JComboBox graphresolutionbut,yearBox,expiryDateCombo;
	
	/**
	 * This is the default constructor
	 */
	public PanelForNews()
	{
		super();
		initialize();
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize()
	{
		this.setSize(300, 200);
	}
	public PanelForNews(int x2, int y2)
	{
		super(null);

		int xinc = 0;

		setPreferredSize(new Dimension(x2, y2));
		setBackground(Color.black);

		AdPanel adp=null;
		try
		{
//			adp = new AdPanel(StockConstants.HOMEURL);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}

		adp.setBounds(0,0,x2,y2);
		add(adp,null);
		
	}
	
}

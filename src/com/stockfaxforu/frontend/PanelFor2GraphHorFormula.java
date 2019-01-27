/*
 * Created on Feb 18, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.frontend;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.stockfaxforu.component.Indicator;
import com.stockfaxforu.formulabuilder.MessageDiaglog;
//import com.stockfaxforu.formulabuilder.NewFormulaNameBox;
//import com.stockfaxforu.formulabuilder.NewFormulaNameSave;
import com.stockfaxforu.formulabuilder.OpenFormulaEditor;
//import com.stockfaxforu.formulabuilder.OpenFormulaEditorPanel;
import com.stockfaxforu.strategy.BuySellStrategy;
import com.stockfaxforu.strategy.StrategyUtility;
import com.stockfaxforu.util.StockConstants;

/**
 * @author sdeopura
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public  class PanelFor2GraphHorFormula extends JPanel implements PanelForGraphImpl,ActionListener
{
	/**
	 * This is the default constructor
	 */
	PanelForGraph panel1;
	FormulaEditor panel2;
	JTextField formula = null;
	public PanelFor2GraphHorFormula()
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
	public PanelFor2GraphHorFormula(int x1, int y1, int x2, int y2)
	{
		super(null);

		int xinc = 0;

		setPreferredSize(new Dimension(x2, y2));
		setBackground(StockConstants.graphbackgroundcolor);

		panel1 = new PanelForGraph(0,0,x2/2,y2,com.stockfaxforu.util.StockConstants.SELECTED_STOCK,false,true);
		panel1.setBounds(0,0,x2/2,y2);
		add(panel1,null);
		
		
		panel2 = new FormulaEditor(x2/2,y2,panel1);
		panel2.setBounds(x2/2+1,0,x2/2-1,y2);
		add(panel2,null);

	
	}
	public void resize(int x2,int y2)
	{
		panel1.resize(x2/2,y2);
		panel1.setBounds(0,0,x2/2,y2);
		panel2.resize(x2/2,y2);
		panel2.setBounds(x2/2+1,0,x2/2-1,y2);

	}

	
	public JTextField sellformula,buyformula;
	
	public void searchStockUpdate(String symbol)
	{
			panel1.macdgraph.setSymbol(symbol);
			
	}
	/* (non-Javadoc)
	 * @see com.stockfaxforu.frontend.PanelForGraphImpl#destroyThread()
	 */
	public void destroyThread()
	{
		panel1.macdgraph.destroyThread();

		// TODO Auto-generated method stub
		
	}
//	
	public void actionPerformed(ActionEvent arg0) 
	{
		Object obj = (JButton)arg0.getSource();
		if(obj instanceof JButton)
		{
			String name = ((JButton)obj).getName();
			Vector newVector = new Vector();
			String formula = buyformula.getText()+ " ";
			HashMap hs1 = new HashMap();

			if(name.equalsIgnoreCase("Apply Buy"))
			{
//				String formula = buyformula.getText() + " ";
//				buyformula.setText(formula) ;

				hs1.put(StrategyUtility.Formula,formula);
				hs1.put(StrategyUtility.Type, "B");
				newVector.add(hs1);
				panel1.macdgraph.drawStrategy(newVector);

			}
			else if(name.equalsIgnoreCase("Apply Sell"))
			{
//				String formula = sellformula.getText() + " ";
				hs1.put(StrategyUtility.Formula,formula);
				hs1.put(StrategyUtility.Type, "S");
				newVector.add(hs1);
				panel1.macdgraph.drawStrategy(newVector);

			}
			else if(name.equalsIgnoreCase("Save"))
			{
//				String s1 = buyformula.getText();
				
	//			NewFormulaNameSave newformula = new NewFormulaNameSave(formula);				

			}
			else if(name.equalsIgnoreCase("Open"))
			{
				String s1 = buyformula.getText();
//				JFrame openformula = new OpenFormulaEditorPanel(this.panel1);
	
			}
			else if(name.equalsIgnoreCase("Evaluate"))
			{
				BuySellStrategy buysell = new BuySellStrategy();
				String s = buysell.evaluateExpress(this.panel1.macdgraph.convert.inputdata,formula);
				MessageDiaglog msg = new MessageDiaglog(s);
			}
			else if(name.equalsIgnoreCase("Formula"))
			{
//				FormulaEditor formulaeditor = new FormulaEditor(this.panel1);
			}

		}	
		// TODO Auto-generated method stub
		
	}
	public void executeFormula(String formulaStr)
	{
		BuySellStrategy buysell = new BuySellStrategy();
			
		String s = buysell.evaluateExpress(this.panel1.macdgraph.convert.inputdata,formulaStr);
		MessageDiaglog msg = new MessageDiaglog(s);

	}
	/* (non-Javadoc)
	 * @see com.stockfaxforu.frontend.PanelForGraphImpl#drawStrategy(java.util.Vector)
	 */
	public void drawStrategy(Vector newVector)
	{
		panel1.drawStrategy(newVector);		
	}
	/* (non-Javadoc)
	 * @see com.stockfaxforu.frontend.PanelForGraphImpl#getStrategyHashMap()
	 */
	public HashMap getStrategyHashMap()
	{
		
		// TODO Auto-generated method stub
		return panel1.getStrategyHashMap();
	}
	/* (non-Javadoc)
	 * @see com.stockfaxforu.frontend.PanelForGraphImpl#getStrategyVector()
	 */
	public Vector getStrategyVector()
	{
		// TODO Auto-generated method stub
		return panel1.getStrategyVector();
	}
	/* (non-Javadoc)
	 * @see com.stockfaxforu.frontend.PanelForGraphImpl#getInputVector()
	 */
	public Vector getInputVector()
	{
		// TODO Auto-generated method stub
		return panel1.getInputVector();
	}
	/* (non-Javadoc)
	 * @see com.stockfaxforu.frontend.PanelForGraphImpl#executeCustmizedIndicator(java.lang.String, java.lang.String)
	 */
	public void executeCustmizedIndicator(String fileName, String formula)
	{
		panel1.executeCustmizedIndicator(fileName, formula);
		
		
	}
	/* (non-Javadoc)
	 * @see com.stockfaxforu.frontend.PanelForGraphImpl#addIndicator(com.stockfaxforu.component.Indicator)
	 */
	public void addIndicator(Indicator selInd)
	{
		panel1.addIndicator(selInd);
				
	}


	
	public void setYear(String selName)
	{
		panel1.setYear(selName);
		
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see com.stockfaxforu.frontend.PanelForGraphImpl#setGraphType(java.lang.String)
	 */
	public void setGraphType(String selName)
	{
		panel1.setGraphType(selName);
			
	}
	/* (non-Javadoc)
	 * @see com.stockfaxforu.frontend.PanelForGraphImpl#setResolution(java.lang.String)
	 */
	public void setResolution(String selName)
	{
		panel1.setResolution(selName);
				
	}

	public void setZooming(String selName)
	{
		panel1.setZooming(selName);
				
	}
	/* (non-Javadoc)
	 * @see com.stockfaxforu.frontend.PanelForGraphImpl#setDate(int, java.lang.String, java.lang.String)
	 */
	public void setDate(int year, String startDate, String endDate)
	{
		panel1.setDate(year, startDate,endDate);
		// TODO Auto-generated method stub
		
	}



}
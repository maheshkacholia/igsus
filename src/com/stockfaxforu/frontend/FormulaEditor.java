/*
 * Created on Apr 21, 2009
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.frontend;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Document;
import javax.swing.text.Highlighter;
import javax.swing.text.JTextComponent;

import com.stockfaxforu.backtesting.BackTesting;
import com.stockfaxforu.backtesting.BackTestingParameterScreen;
import com.stockfaxforu.formulabuilder.MessageDiaglog; //import com.stockfaxforu.formulabuilder.NewFormulaNameSave;
//import com.stockfaxforu.formulabuilder.OpenFormulaEditorPanel;
import com.stockfaxforu.parser.LexicalAnalyzer;
import com.stockfaxforu.parser.SemanticAnalyzer;
import com.stockfaxforu.query.Function;
import com.stockfaxforu.strategy.BuySellStrategy;
import com.stockfaxforu.strategy.StrategyUtility;
import com.stockfaxforu.util.StockConstants;
import com.stockfaxforu.util.Utility;

/**
 * @author Mahesh
 * 
 *         To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class FormulaEditor extends JPanel implements ActionListener, ComponentListener
{
	PanelForGraphImpl maingraph = null;
	private javax.swing.JPanel jContentPane = null;
	int x1, y1;
	public JScrollPane scrollPane;
	String mainStr="";
	public FormulaEditor(int x1, int y1, PanelForGraphImpl maingraph)
	{
		super(null);
		this.maingraph = maingraph;
		this.x1 = x1;
		this.y1 = y1;

		int xpos = addIcons(10, 0);
		lable = new JLabel(mainStr );
		lable.setBounds(10, 30, x1, 20);
		this.add(lable,null);

		
		buyformula = new JTextArea(x1, y1);
		scrollPane = new JScrollPane(buyformula, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(0, 60, x1 - 30, y1 - 60);
		add(scrollPane, null);
//		buyformula.setText();
//		highlight(buyformula, "public");

	}

	/**
	 * @return
	 */
	public JTextArea buyformula = null;
	JButton[] icon = new JButton[13];
	String[] gifname = { "new", "open", "save", "saveas", "inspect", "evaluate", "runstrategy", "custmizedindicator", "backtesting", "semantic",
			"console", "clearconsole", "help" };
	String[] gifname1 = { "newformula.jpg", "open.jpg", "save.jpg", "saveas.jpg", "inspect.jpg", "next.jpg", "runstrategy.jpg", "custom.jpg",
			"backtesting.jpg", "semantic.jpg", "console.jpg", "clear.jpg", "help.jpg" };
	String[] desc = { "New Formula", "Open Formula", "Save Formula", "Save As", "Inspect Variable", "Test Formula Before Apply",
			"Apply Formula on a Stock", "Run As a Customized Indicator", "Back Test Your Strategy", "Semantic Analyzer", "Output Window",
			"Clear Output Window", "help" };
	
	JLabel lable = null;

	public int addIcons(int xpos, int ypos)
	{
		int iconsize = 30;
		for (int i = 0; i < gifname.length; i++)
		{
			String s = "image/formulabuilder/" + gifname1[i];
			ImageIcon cup = new ImageIcon(ClassLoader.getSystemResource(s));
			// if(this.isStockScreener && i==4)
			// {
			// continue;
			// }

			icon[i] = new JButton(cup);
			icon[i].setBounds(xpos, ypos, iconsize, iconsize);
			icon[i].setName(gifname[i]);
			icon[i].setToolTipText(desc[i]);
			add(icon[i], null);
			icon[i].addActionListener(this);
			xpos = xpos + iconsize;
		}
		// String s = "image/formulabuilder/open.jpg";
		// ImageIcon cup = new ImageIcon(ClassLoader.getSystemResource(s));

		/*
		 * icon[4] = new JButton(cup);
		 * icon[4].setBounds(xpos,ypos,iconsize,iconsize);
		 * icon[4].setName("sss"); add(icon[4],null);
		 * icon[4].addActionListener(this); xpos = xpos + iconsize + 10 ;
		 */
		return xpos;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	// String[] gifname = {"newformula","open","save", "saveas","runstrategy"};
	public HashMap outputHash = new HashMap();
	public String fileName = "";
	public String dirName = "";

	public void checkFormulaForCustmizedIndicator(String fileName,String formula )
	{
		formula = formula.toUpperCase();
		StringTokenizer st = new StringTokenizer(formula,"\n");
		String parameter = st.nextToken();
		if(parameter.toUpperCase().startsWith("@PARAMETER"))
		{
			StringTokenizer st1 = new StringTokenizer(parameter," ");
			st1.nextToken();
			Vector v = new Vector();
			while(st1.hasMoreTokens())
			{
				v.add(st1.nextToken());
			}
			if(v.size()==0)
			{
				this.maingraph.executeCustmizedIndicator(fileName,formula);
				
			}
			else
			{
				CustmizedIndicatorPanel panel = new CustmizedIndicatorPanel(fileName,formula,v,this.maingraph);
			}
		}
		else
		{
			this.maingraph.executeCustmizedIndicator(fileName,formula);
			
		}
	}	
	public void checkFormulaForStrategy(String fileName,String formula )
	{
		formula = formula.toUpperCase();
		StringTokenizer st = new StringTokenizer(formula,"\n");
		String parameter = st.nextToken();
		if(parameter.toUpperCase().startsWith("@PARAMETER"))
		{
			StringTokenizer st1 = new StringTokenizer(parameter," ");
			st1.nextToken();
			Vector v = new Vector();
			while(st1.hasMoreTokens())
			{
				v.add(st1.nextToken());
			}
			if(v.size()==0)
			{
				HashMap hs1 = new HashMap();
				hs1.put(StrategyUtility.Formula, formula);
				hs1.put(StrategyUtility.Type, "B");
				Vector newVector = new Vector();
				newVector.add(hs1);

				// ((PanelForGraph)maingraph).macdgraph.drawStrategy(newVector);
				maingraph.drawStrategy(newVector);
				
			}
			else
			{
				CustmizedIndicatorPanel panel = new CustmizedIndicatorPanel(fileName,formula,v,maingraph,CustmizedIndicatorPanel.STRATEGY);
			}
		}
		else
		{
			HashMap hs1 = new HashMap();
			hs1.put(StrategyUtility.Formula, formula);
			hs1.put(StrategyUtility.Type, "B");
			Vector newVector = new Vector();
			newVector.add(hs1);

			// ((PanelForGraph)maingraph).macdgraph.drawStrategy(newVector);
			maingraph.drawStrategy(newVector);
			
		}
	}	


	public void actionPerformed(ActionEvent arg0)
	{
		Object obj = (JButton) arg0.getSource();
		if (obj instanceof JButton)
		{
			String name = ((JButton) obj).getName();
			Vector newVector = new Vector();
			String formula = buyformula.getText().toUpperCase() + " ";
			HashMap hs1 = new HashMap();
			// formula = Function.findStrings(formula);

			if (name.equalsIgnoreCase("custmizedindicator"))
			{
	//			maingraph.executeCustmizedIndicator(fileName, formula);
				checkFormulaForCustmizedIndicator(fileName,formula );
			}
			if (name.equalsIgnoreCase("runstrategy"))
			{
				// String formula = buyformula.getText() + " ";
				checkFormulaForStrategy(fileName,formula);

				// buyformula.setText(formula) ;

//				hs1.put(StrategyUtility.Formula, formula);
//				hs1.put(StrategyUtility.Type, "B");

//				newVector.add(hs1);

				// ((PanelForGraph)maingraph).macdgraph.drawStrategy(newVector);
//				maingraph.drawStrategy(newVector);

				// int size =
				// ((PanelForGraph)maingraph).macdgraph.stratetgyOutput.size()
				// -1;
				// outputHash =
				// (HashMap)((PanelForGraph)maingraph).macdgraph.stratetgyOutput.get(size);
//				outputHash = maingraph.getStrategyHashMap();

			}
			if (name.equalsIgnoreCase("backtesting"))
			{
				// Vector v =
				// ((PanelForGraph)maingraph).macdgraph.stratetgyOutput;
				Vector v = maingraph.getStrategyVector();

				if (BackTestingParameterScreen.dataHash == null)
					BackTestingParameterScreen.loadParameter();
				HashMap hs = BackTestingParameterScreen.dataHash;
				if (hs == null)
				{
					MessageDiaglog msg = new MessageDiaglog("Please set your Backtesting parameter( goto File-->backtesting ");
					return;
				}
				BackTesting backtesting = new BackTesting();

				String content = backtesting.doBackTesting(BackTestingParameterScreen.buyinper, BackTestingParameterScreen.sellinper,
						BackTestingParameterScreen.initialamt, BackTestingParameterScreen.tranamtf, BackTestingParameterScreen.trantypestr, v);
				ShowContentWindow showcontent = new ShowContentWindow(content);

			}

			else if (name.equalsIgnoreCase("save"))
			{
				if (fileName == null || fileName.equals("") || dirName == null || dirName.equals(""))
				{
					FileDialog fd = new FileDialog(new Frame(), "Save Formula", FileDialog.SAVE);
					fd.setFile("*.fl");
					fd.setDirectory(StockConstants.FORMULA_DIR);
					fd.setLocation(50, 50);
					fd.show();

					fileName = fd.getFile();
					dirName = fd.getDirectory();

				}
				try
				{
					File f = new File(dirName + "/" + fileName);
					FileOutputStream file = new FileOutputStream(f);

					long len = formula.length();
					byte[] b = new byte[(int) len];
					file.write(formula.getBytes());
					StrategyUtility.saveFormula(fileName, formula);
					file.close();
				}
				catch (Exception e)
				{

					// TODO Auto-generated catch block
					// e.printStackTrace();
				}
			}
			else if (name.equalsIgnoreCase("saveas"))
			{
				FileDialog fd = new FileDialog(new Frame(), "Save Formula", FileDialog.SAVE);
				fd.setFile("*.fl");
				fd.setDirectory(StockConstants.FORMULA_DIR);
				fd.setLocation(50, 50);
				fd.show();

				fileName = fd.getFile();
				dirName = fd.getDirectory();
				try
				{
					File f = new File(dirName + "/" + fileName);
					FileOutputStream file = new FileOutputStream(f);

					long len = formula.length();
					byte[] b = new byte[(int) len];
					file.write(formula.getBytes());
					StrategyUtility.saveFormula(fileName, formula);

					file.close();
				}
				catch (Exception e)
				{

					// TODO Auto-generated catch block
					// e.printStackTrace();
				}
				this.lable.setText(this.mainStr + "Selected Formula  " + this.dirName +  this.fileName);
			
			}

			else if (name.equalsIgnoreCase("new"))
			{
				fileName = "";
				dirName = "";
				buyformula.setText("");
				this.lable.setText(this.mainStr + "Selected Formula  " + this.dirName +  this.fileName);

			}
			else if (name.equalsIgnoreCase("open"))
			{
				// FileDialog filediaglog = new
				// FileDialog(MainScreen.getSingleton(),)
				FileDialog fd = new FileDialog(new Frame(), "Open Formula", FileDialog.LOAD);
				fd.setFile("*.fl");
				fd.setDirectory(StockConstants.FORMULA_DIR);
				fd.setLocation(50, 50);
				fd.show();

				String fileName1 = fd.getFile();
				String dirName1 = fd.getDirectory();
				if (fileName1 == null || dirName1 == null || fileName1.equals("") || dirName1.equals(""))
				{
					return;
				}
				try
				{
					File f = new File(dirName1 + "/" + fileName1);
					FileInputStream file = new FileInputStream(f);
					long len = f.length();
					byte[] b = new byte[(int) len];
					file.read(b, 0, b.length);
					buyformula.setText(new String(b));

					fileName = fd.getFile();
					dirName = fd.getDirectory();
					
					
					file.close();

					this.lable.setText(this.mainStr + "Selected Formula  " + this.dirName +  this.fileName);
					
				}
				catch (Exception e)
				{

					// TODO Auto-generated catch block
					// e.printStackTrace();
				}
				// String s1 = buyformula.getText();
				// JFrame openformula = new OpenFormulaEditorPanel(this);

			}
			// String[] gifname = {"newformula","open","save",
			// "inspect","runstrategy"};

			else if (name.equalsIgnoreCase("evaluate"))
			{

				BuySellStrategy buysell = new BuySellStrategy();
				// ((PanelForGraph)maingraph).macdgraph.drawStrategy(newVector);
				// buysell.symbol = maingraph.
				Vector inputdata = maingraph.getInputVector();
				// String s =
				// buysell.runScreenerForSingleStockWithdata(((PanelForGraph)maingraph).macdgraph.convert.inputdata,formula);
				String s = buysell.runScreenerForSingleStockWithdata(inputdata, formula);

				MessageDiaglog msg = new MessageDiaglog(s);
				outputHash = buysell.evaluateHash;
			}
			else if (name.equalsIgnoreCase("inspect"))
			{
			//	BuySellStrategy buysell = new BuySellStrategy();
				// ((PanelForGraph)maingraph).macdgraph.drawStrategy(newVector);

				// HashMap hs =
				// buysell.evaluateExpressHashmap(((PanelForGraph)maingraph).macdgraph.convert.inputdata,formula);
				outputHash = maingraph.getStrategyHashMap();

				ShowVariableScreen show = new ShowVariableScreen(outputHash);
			}
			else if (name.equalsIgnoreCase("console"))
			{
				ConsoleWindow consolewindow = new ConsoleWindow();// MessageDiaglog
																	// msg = new
																	// MessageDiaglog(Function.console.toString());

			}
			else if (name.equalsIgnoreCase("help"))
			{
				HelpWindow searc = new HelpWindow(this);

			}

			else if (name.equalsIgnoreCase("clearconsole"))
			{
				StockConstants.console = new StringBuffer();
			}
			else if (name.equalsIgnoreCase("semantic"))
			{
				SemanticAnalyzer.doSemanticAnalasis(formula);
			}

		}
	}

	public static void main(String[] args)
	{
		JFrame f = new JFrame();
		FormulaEditor d = new FormulaEditor(500, 500, null);
		f.getContentPane().add(d);
		f.setSize(500, 500);
		f.show();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seejava.awt.event.ComponentListener#componentResized(java.awt.event.
	 * ComponentEvent)
	 */
	public void componentResized(ComponentEvent arg0)
	{
		/*
		 * Dimension d = this.getSize(); x1 =(int) d.getWidth(); y1 =
		 * (int)d.getHeight(); buyformula.resize( x1,y1);
		 * scrollPane.setBounds(0,40,x1-30,y1-60);
		 */
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ComponentListener#componentMoved(java.awt.event.ComponentEvent
	 * )
	 */
	public void componentMoved(ComponentEvent arg0)
	{
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ComponentListener#componentShown(java.awt.event.ComponentEvent
	 * )
	 */
	public void componentShown(ComponentEvent arg0)
	{
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seejava.awt.event.ComponentListener#componentHidden(java.awt.event.
	 * ComponentEvent)
	 */
	public void componentHidden(ComponentEvent arg0)
	{
		// TODO Auto-generated method stub

	}

	// Highlight the occurrences of the word "public"
	// highlight(textComp, "public");

	// Creates highlights around all occurrences of pattern in textComp
	public void highlight(JTextComponent textComp, String pattern)
	{
		// First remove all old highlights
		removeHighlights(textComp);

		try
		{
								
			Highlighter hilite = textComp.getHighlighter();
			Document doc = textComp.getDocument();
			String text = doc.getText(0, doc.getLength());
			int pos = 0;

			// Search for pattern
			while ((pos = text.indexOf(pattern, pos)) >= 0)
			{
				// Create highlighter using private painter and apply around
				// pattern
				hilite.addHighlight(pos, pos + pattern.length(), myHighlightPainter);
				pos += pattern.length();
			}
		}
		catch (Exception e)
		{
		}
	}

	// Removes only our private highlights
	public void removeHighlights(JTextComponent textComp)
	{
		Highlighter hilite = textComp.getHighlighter();
		Highlighter.Highlight[] hilites = hilite.getHighlights();

		for (int i = 0; i < hilites.length; i++)
		{
			if (hilites[i].getPainter() instanceof MyHighlightPainter)
			{
				hilite.removeHighlight(hilites[i]);
			}
		}
	}

	// An instance of the private subclass of the default highlight painter
	Highlighter.HighlightPainter myHighlightPainter = new MyHighlightPainter(Color.red);


	// A private subclass of the default highlight painter
	class MyHighlightPainter extends DefaultHighlighter.DefaultHighlightPainter
	{
		public MyHighlightPainter(Color color)
		{
			super(color);
			
		}
	}

}

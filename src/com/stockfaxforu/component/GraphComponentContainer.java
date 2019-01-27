/*
 * Created on Feb 1, 2007
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.component;
/**
 * @author sdeopu1
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;

//import sun.security.x509.OtherName;

import com.stockfaxforu.config.ConfigUtility;
import com.stockfaxforu.finance.ShowFinance;
import com.stockfaxforu.formulabuilder.MessageDiaglog;
import com.stockfaxforu.frontend.DateButton;
import com.stockfaxforu.frontend.StrategyDataInterface;
import com.stockfaxforu.query.Function;
import com.stockfaxforu.strategy.BuySellStrategy;
import com.stockfaxforu.strategy.StrategyUtility;
import com.stockfaxforu.util.GetDataFromIDBI;
import com.stockfaxforu.util.GlobalData;
import com.stockfaxforu.util.StockConstants;
import com.stockfaxforu.util.Utility;
public class GraphComponentContainer extends JPanel implements FocusListener,StrategyDataInterface,KeyListener,MouseInputListener,MouseListener
{
	public MYDimension mydimension = null;
	public boolean showstats=true;
	
	boolean isLineNotPainted = false;
	boolean isHorLine = false;
	boolean isVerLine = false;
	boolean isBuyArrow = false;
	boolean isSellArrow = false;
	boolean isExpandDragging = false;
	int dragx = 0, dragy = 0;
	Vector angledLines;
	Point startPoint = null;
	Point endPoint = null;
	Graphics g;
	public MainGraphComponent convert = null;
	public Vector histIntradayData = new Vector();
	public String expirydate="";
	public DrawVolume drawvol = null;
	Vector paintedComponent = new Vector();
	public ComponentManager compman = new ComponentManager(this);
	Vector indiVector = new Vector();
	int x1, y1, x2, y2,newx,newy;
	int intstartx,intendx,intstarty,intendy;
//	Vector dataVector = new Vector();
	boolean doselectedzoom = false;
	boolean isDragging = false;
	public Vector strategyVector = new Vector();
	
	Graphics gr;
	public String symbol = "";
	public boolean drawvolumeind=true;
	IntradayThread run=null;
	Thread th = null;
	Vector drawingComponentVector = new Vector();
	public String defaultStrtetgyText="";
	
	public void saveGraph()
	{
		
		
		Vector vec = new Vector();
		try
		{
			FileOutputStream fos = new FileOutputStream(StockConstants.INSTALL_DIR + "/graph/" + this.symbol);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			Vector v = compman.getAllDrawingComponent();
			oos.writeObject(v);
			oos.close();
		}
		catch (Exception e)
		{
		}
	}
	public Vector loadGraph()
	{
		try
		{
			java.io.FileInputStream fos = new java.io.FileInputStream(StockConstants.INSTALL_DIR + "/graph/" + this.symbol);
			ObjectInputStream oos = new ObjectInputStream(fos);
			Vector v = (Vector) oos.readObject();
			oos.close();
			return v;
		}
		catch (Exception e)
		{
			return null;
		}
	}
	public void addComponentVector(Vector v)
	{
		if (v != null)
		{
			for (int i = 0; i < v.size(); i++)
			{
				Object o = v.elementAt(i);
				if (o instanceof Indicator)
				{
					Indicator ind = (Indicator) o;
					addIndicatorWithoutPaint(ind);
				}
				else
				{
					compman.addComponent(o);
				}
			}
		}
	}
	public void doSelectedZooming()
	{
		this.doselectedzoom = true;
	}
	public static int beforeendx=75;
	public void updateDimension()
	{
		int mainheight = 0;
		int otherheight = 0;
		int incy = this.y2;
		Vector allcom = compman.getAllGraphComponent();
		int noofpanels = allcom.size();
		if (1 == (noofpanels))
		{
			mainheight = (int) (y2);
			convert.updateDimension(10, incy, this.x2 - beforeendx, incy + mainheight - 50, y2);
		}
		if (2 == (noofpanels))
		{
			mainheight = (int) (0.65 * y2);
			otherheight = (int) (0.35 * y2) - 70;
			convert.updateDimension(this.x1 + 10, incy, this.x2 - beforeendx, incy + mainheight, y2);
			incy = incy - mainheight - 20;
			MainGraphComponent myc = (MainGraphComponent) allcom.elementAt(1);
			myc.updateDimension(this.x1 + 10, incy, this.x2 - beforeendx, incy + otherheight, y2);
		}
		else if (3 == (noofpanels))
		{
			mainheight = (int) (0.50 * y2);
			otherheight = (int) (0.25 * y2) - 45;
			convert.updateDimension(this.x1 + 10, incy, this.x2 - beforeendx, incy + mainheight, y2);
			incy = incy - mainheight - 20;
			MainGraphComponent myc = (MainGraphComponent) allcom.elementAt(1);
			myc.updateDimension(this.x1 + 10, incy, x2 - beforeendx, incy + otherheight, y2);
			//			drawvol.updateDimension(10, incy, x2 - 50, incy + otherheight, y2);
			incy = incy - otherheight - 20;
			myc = (MainGraphComponent) allcom.elementAt(2);
			myc.updateDimension(this.x1 + 10, incy, this.x2 - beforeendx, incy + otherheight, y2);
		}
		else if (4 == (noofpanels))
		{
			mainheight = (int) (0.40 * y2);
			otherheight = (int) (0.20 * y2) - 40;
			convert.updateDimension(this.x1 + 10, incy, this.x2 - beforeendx, incy + mainheight, y2);
			incy = incy - mainheight - 20;
			MainGraphComponent myc = (MainGraphComponent) allcom.elementAt(1);
			myc.updateDimension(this.x1 + 10, incy, x2 - beforeendx, incy + otherheight, y2);
			//			drawvol.updateDimension(10, incy, x2 - 50, incy + otherheight, y2);
			incy = incy - otherheight - 20;
			myc = (MainGraphComponent) allcom.elementAt(2);
			myc.updateDimension(this.x1 + 10, incy, this.x2 - beforeendx, incy + otherheight, y2);
			incy = incy - otherheight - 20;
			MainGraphComponent myc1 = (MainGraphComponent) allcom.elementAt(3);
			myc1.updateDimension(this.x1 + 10, incy, this.x2 - beforeendx, incy + otherheight, y2);
		}
		else if (5 == (noofpanels))
		{
			mainheight = (int) (0.35 * y2);
			otherheight = (int) (0.16 * y2) - 33;
			convert.updateDimension(this.x1 + 10, incy, this.x2 - beforeendx, incy + mainheight, y2);
			incy = incy - mainheight - 20;
			//			drawvol.updateDimension(10, incy, x2 - 50, incy + otherheight, y2);
			MainGraphComponent myc1 = (MainGraphComponent) allcom.elementAt(1);
			myc1.updateDimension(this.x1 + 10, incy, this.x2 - beforeendx, incy + otherheight, y2);
			incy = incy - otherheight - 20;
			MainGraphComponent myc = (MainGraphComponent) allcom.elementAt(2);
			myc.updateDimension(this.x1 + 10, incy, this.x2 - beforeendx, incy + otherheight, y2);
			incy = incy - otherheight - 20;
			myc1 = (MainGraphComponent) allcom.elementAt(3);
			myc1.updateDimension(this.x1 + 10, incy, this.x2 - beforeendx, incy + otherheight, y2);
			incy = incy - otherheight - 20;
			MainGraphComponent myc2 = (MainGraphComponent) allcom.elementAt(4);
			myc2.updateDimension(this.x1 + 10, incy, this.x2 - beforeendx, incy + otherheight, y2);
		}
	}
	public int status;
	public static int LIVEDATA_GRAPH=1;
	public static int INTRADAY_GRAPH=2;

	public GraphComponentContainer(int x1, int y1, int x2, int y2, String symbol,int status)
	{
		this.status = status;
		this.symbol = symbol;
		this.x1 = x1;
		this.x2 = x2;
		this.y1 = y1;
		this.y2 = y2;
		
		loadCreateGraph(x1,y1,x2,y2,symbol);

	}	
	public void updateSize(int x1, int y1, int x2, int y2)
	{
//		// ln("old dta"+this.x1+" "+this.x2+"  "+this.y1+"   "+this.y2);
//		this.x1 = x1;
		// ln("x2="+x2);
		// ln("intx2="+intendx);
		// ln("thisx2="+this.x2);
		this.x2 = x2;
		
//		this.x1 = 0;
		intendx = x2 - beforeendx;
		Vector v = compman.getAllComponent();

		for (int i = 0; i < v.size(); i++)
		{
			MYDimension myd = (MYDimension) v.elementAt(i);
			if (myd instanceof MainGraphComponent)
			{
					myd.endx=this.x2;
			//		((MainGraphComponent) myd).intendx = intendx;
					((MainGraphComponent) myd).setInitalXY(intstartx, intendx,intstarty,intendy);

			}
		}	


		//		intstartx = 10;
//		intstarty = y1;
//		intendy = y2;
		
		
		//		this.intendx = x2 + beforeendx;
//		this.y1 = y1;
		this.y2 = y2;
//		// ln("new dta"+this.x1+" "+this.x2+"  "+this.y1+"   "+this.y2);

		updateDimension();
//		repaint();
	}
	public boolean isintraday=false;
	public GraphComponentContainer(int x1, int y1, int x2, int y2, String symbol,boolean intraday)
	{
		this.isintraday = true;
		this.symbol = symbol;
		this.x1 = x1;
		this.x2 = x2;
		this.y1 = y1;
		this.y2 = y2;
		

		loadCreateGraph(x1,y1,x2,y2,symbol);
	}

	public GraphComponentContainer(int x1, int y1, int x2, int y2, String symbol)
	{
		this.symbol = symbol;
		this.x1 = x1;
		this.x2 = x2;
		this.y1 = y1;
		this.y2 = y2;
		

		loadCreateGraph(x1,y1,x2,y2,symbol);
	}
	public void setCursorType(String selName)
	{
	
		if (selName.equalsIgnoreCase("Cross-Hair"))
		{
			this.compman.cursorType = ComponentManager.CROSSHAIR;
		}
		else if (selName.equalsIgnoreCase("Cursor"))
		{
			this.compman.cursorType = ComponentManager.CURSOR;
		}
		else if (selName.equalsIgnoreCase("Mouse"))
		{
			this.compman.cursorType = ComponentManager.MOUSE;
			
		}
	}
	public void loadCreateGraph(int x1, int y1, int x2, int y2, String symbol)
	{	
		intendx = x2 - beforeendx;
		intstartx = 10;
		intstarty = y1;
		intendy = y2;
		try
		{
			Thread th = GlobalData.getIntradayThread();
			if(th != null)
			{
				th.stop();
				GlobalData.removeIntradayThread();
				th=null;
			}

		}
		catch(Exception e)
		{
			
		}
		angledLines = new Vector();
		setPreferredSize(new Dimension(x2, y2));
		this.addMouseListener(this);
		this.addKeyListener(this);
		this.addMouseMotionListener(this);		
//		MyMouseListener ml = new MyMouseListener();
//		MyKeyboardListener kl = new MyKeyboardListener();
//		addMouseListener(ml);
//		addMouseMotionListener(ml);
//		addKeyListener(kl);

		setBackground(StockConstants.graphbackgroundcolor);

		int incy = y2;
		int mainheight = (y2 / 2);
		int otherheight = y2 / 4 - 50;
		convert = new MainGraphComponent(10, incy, x2 - beforeendx, incy + mainheight, y2, MYDimension.MAINGRAPH);
		convert.setInitalXY(intstartx, intendx,intstarty,intendy);
		//property to setup
		this.convert.chartResolution = StockConstants.GraphResolution;
		this.setCursorType(StockConstants.CursorType);
		this.convert.startDate = StockConstants.ShowGraphYear;
		this.convert.graphType = StockConstants.GraphDisplay;

		if(!this.isintraday)
			GlobalData.addScreen(convert);


		convert.isintraday = false;

		//		convert.ymaxposinmainpanel = 100;
		try
		{
			if(this.status==LIVEDATA_GRAPH || this.status==INTRADAY_GRAPH)
			{
				convert.loadFileForIntraday(symbol,convert.expirydate);
			//	dataVector = convert.inputdata;
				convert.isintraday = true;
				convert.setHighLowValues();
				convert.note = Utility.getStockDes(symbol);
				compman.addComponent(convert);
				convert.note = Utility.getStockDes(symbol);

			}
			else
			{
				convert.loadFile(symbol);
		//		this.dataVector = convert.inputdata;
				convert.setHighLowValues();
				convert.note = Utility.getStockDes(symbol);

				compman.addComponent(convert);
				convert.note = Utility.getStockDes(symbol);

				incy = incy - mainheight - 20;
				drawvol = new DrawVolume(10, incy, x2 - beforeendx, incy + otherheight, y2);
				try
				{
					//			convert.loadFile("ACC");
					drawvol.inputdata = convert.inputdata;
					drawvol.setHighLowValues();
					drawvol.note = "Volume";
				}
				catch (Exception e)
				{
				}
				compman.addComponent(drawvol);
//				Indicator id = new Indicator(" rsi14 ",14,Indicator.CUSTOM);
//				addIndicator(id);	
			}
			
			
		}
		catch (Exception e)
		{
		}
		
		Financial financial = new Financial(100, 50, 100 + 400, 50 + 200,this.symbol, MYDimension.FINANCIALDATA);
		if(financial.isFinancialAvailable() && StockConstants.SHOW_FINANCE)
			compman.addComponent(financial);

		if(!this.isintraday)
		{
			Vector x = (Vector)GlobalData.getDrawingObject(symbol);
			if(x == null)
			{
				Vector v = loadGraph();
				if(v != null)
				{

					addComponentVector(v);
					GlobalData.addDrawingObject(symbol, v);
				}
			}
			else
			{
				addComponentVector(x);
			}
//			drawStrategy("","");
			
		}

		updateDimension();
//		applyDefaultStrategy();
		
	}


	
	public void applyDefaultStrategy()
		{
				if(!StockConstants.StockSnapshot)
				{
					return;
				}
				HashMap hs1 = new HashMap();
				hs1.put(StrategyUtility.FormulaName, "def");
				String s=null;
				try
				{
					s = Utility.getFileContent(StockConstants.INSTALL_DIR +"/formula/buysellrecom.fl");
				}
				catch (Exception e)
				{
					// TODO Auto-generated catch block
			//	e.printStackTrace();
				}
				hs1.put(StrategyUtility.Formula, s);
				
				hs1.put(StrategyUtility.Type, "B");
				Vector newVector = new Vector();
				newVector.add(hs1);

				String strQuery = (String)hs1.get(StrategyUtility.Formula);

//				String 	strategyType = ((String)hs1.get(StrategyUtility.Type)).substring(0,1);
				BuySellStrategy buysellstr = new BuySellStrategy();
				buysellstr.symbol = this.symbol;	

				Vector outputVec = buysellstr.executeStrategy(Utility.getSubSet(this.convert.inputdata,50,Utility.FROM_LAST),strQuery);

			//convert.defaultStrtetgyText = ((HashMap)outputVec.lastElement()).get(MainGraphComponent.ALERTTEXT)+	"|"+ GetDataFromIDBI.currentPrice(this.symbol)+"^WHITE";
	
			convert.defaultStrtetgyText = ((HashMap)outputVec.lastElement()).get(MainGraphComponent.ALERTTEXT)+	"";
			
			
			//		g.drawBytes(s.getBytes(),0,s.length(),financial_x+5,financial_y+20);
			
				
			
			
	}
	public void destroyThread()
	{
		try
		{
		
			if(run != null)
			{
				
				run.runwhile=false;
				run=null;
				
			}
			Thread.sleep(200);
		}
		catch(Exception e)
		{
		}
		
		
	}
	public void setForIntraDay()
	{
		try
		{
			destroyThread();
			
		}
		catch(Exception e)
		{
			
		}
		convert.isintraday=true;
		convert.isfutureintraday = false;

		run = new IntradayThread(this);
		th = new Thread(run);
		th.start();
		convert.expirydate="";
	}
	public void setForIntraDayFuture(String expiryDate)
	{
		convert.isintraday=false;
		convert.isfutureintraday = true;
		convert.expirydate= expiryDate;
		destroyThread();
		run = new IntradayThread(this);
	//	run.run();
		th = new Thread(run);
		th.start();
	}
	public void setForHistoricalFuture(String startDate,String endDate,String expiryDate)
	{
		convert.symbolName = this.symbol;
		try
		{
			destroyThread();
			convert.expirydate = expiryDate;
			convert.loadFileForFutureHist(symbol,convert.expirydate,startDate,endDate);
		//	dataVector = convert.inputdata;
			convert.isintraday = true;
			convert.setHighLowValues();
			convert.note = Utility.getStockDes(symbol) + "Future-" + convert.expirydate + "";
			repaintMe();
		}
		catch (Exception e)
		{
		}
		removeVolumeAndSetOtherData(false);
//		drawvolumeind = false;
	}


	public void removeVolumeAndSetOtherData(boolean removeVolume)
		{	
			Vector v = compman.getAllComponent();
			for (int i = 0; i < v.size(); i++)
			{
				MYDimension myd = (MYDimension) v.elementAt(i);
				if (myd instanceof DrawVolume && removeVolume)
				{
					compman.removeComponent(myd);
				}
				if (myd instanceof MainGraphComponent)
				{
					((MainGraphComponent) myd).inputdata = convert.inputdata;
					((MainGraphComponent) myd).setHighLowValues();
				}
			}
			if (removeVolume)
			{
				updateDimension();
				
			}
			if(!convert.isintraday)
				paintComponent(g);
		}
/*	public void addOtherComponent(Object o)
	{
		compman.addComponent(o);
		paintComponent(g);
	}
*/
		public void addComponent(Object o)
	{
		if (o instanceof MYDimension)
		{
			compman.addComponent(o);
			if(isintraday)
			{
				drawingComponentVector.add(o);
				
			}
			else
			{
				GlobalData.addDrawingSingleComponent(symbol, o);
				
			}

		}
		else if (o instanceof Indicator)
		{
			convert.addComponent(o);
		}
	}
	public void removeComponent(Object o)
	{
		if (o instanceof MYDimension)
		{
			compman.removeComponent((MYDimension) o);
			if(isintraday)
			{
				drawingComponentVector.remove(o);
							
			}
			else
			{
				GlobalData.removeDrawingSingleComponent(symbol, o);
							
			}
		}
		else if (o instanceof Indicator)
		{
			convert.removeComponent(o);
		}
		paintComponent(g);
	}
	public void setGraphDisplay(int graphdisplaytype)
	{
		convert.graphType = graphdisplaytype;
		paintComponent(g);
	}
	public int getGraphDisplay()
	{
		return convert.graphType;
	}
	public void applySettingIntraday()
	{
		compman.removeAllDrawingComponent();
		if(isintraday)
		{
			addComponentVector(drawingComponentVector);
					
		}
		else
		{
//			addComponentVector(v3);
			
		}
		updateDimension();
//mahesh added for intraday		
	//	applyDefaultStrategy();
		drawStrategy();

		paintComponent(g);
		
	}
	public void setSymbol(String symbol)
	{

//		// ln("set symbol called");
		destroyThread();
//		if(!MainGraphComponent.checkIndexFile(symbol) && !MainGraphComponent.checkFile(symbol))
//			return;
		
		if(convert.isintraday)
		{
			convert.chartResolution=1;
			convert.isintraday=false;
		}
		try
		{
			
			convert.loadFile(symbol);
			if(convert.dataexception==true)
			{
				drawError("Error Occured, while Loading Data");
				return;	
			}
			this.symbol = symbol;
			convert.symbolName = symbol;

		//	this.dataVector = convert.inputdata;
			convert.setHighLowValues();
			convert.isintraday = false;
			convert.note = Utility.getStockDes(symbol);
			StockConstants.SELECTED_STOCK = symbol;
			if(showstats==true)
				applyDefaultStrategy();

		}
		catch (Exception e)
		{
			
		}
		removeAll();
		Vector v = compman.getAllComponent();
		for (int i = 0; i < v.size(); i++)
		{
			MYDimension myd = (MYDimension) v.elementAt(i);
			if (myd instanceof MainGraphComponent)
			{
				((MainGraphComponent) myd).inputdata = convert.inputdata;
				((MainGraphComponent) myd).setHighLowValues();
			}
		}
		if(drawvolumeind==false)
		{
			drawvol.inputdata = convert.inputdata;
			drawvol.setHighLowValues();
			compman.addComponent(drawvol);
			drawvolumeind=true;
		}
		compman.removeAllDrawingComponent();
		Vector v3 = (Vector)GlobalData.getDrawingObject(symbol);
		if(v3==null)
		{
			v3 = loadGraph();
			if(v3 != null)
			{
				GlobalData.addDrawingObject(symbol, v3);
			}
		}
		if( showstats==true)
		{
			if(StockConstants.StockSnapshot)
			{
				StockSnapShot financial = new StockSnapShot(25, 35, 400, 350,this.symbol,convert.defaultStrtetgyText);

				compman.addComponent(financial);
		}
			}
		drawStrategy();

		addComponentVector(v3);
		updateDimension();
		if(!isintraday)
			paintComponent(g);
	
	}
	public void setGraphResolution(int resolution)
	{
		convert.chartResolution = resolution;
		if (convert.inputdata.size()==0)
			return;
		if(convert.isintraday==true || convert.isfutureintraday)
		{
//			convert.inputdata = convert.setResolution(this.dataVector);
			convert.inputdata = convert.setResolutionIntraday(this.convert.inputdata,resolution*60);
			convert.setHighLowValues();
//			return;
		}
		else
		{
			try
			{
				convert.loadFile(convert.symbolName);
				convert.setHighLowValues();
			//	this.dataVector = convert.inputdata;
				this.drawStrategy();
			}
			catch (Exception e)
			{
			}
			
		}
		Vector v = compman.getAllComponent();
		for (int i = 0; i < v.size(); i++)
		{
			MYDimension myd = (MYDimension) v.elementAt(i);
			if (myd instanceof MainGraphComponent)
			{
				((MainGraphComponent) myd).inputdata = convert.inputdata;
				((MainGraphComponent) myd).setHighLowValues();
			}
		}
		
//		paintComponent(g);
	}
	public void setDate(String date)
	{
		
		convert.startDate = date;
		if(convert.isintraday)
			return;
		try
		{
			convert.loadFile(convert.symbolName);
			convert.setHighLowValues();
		//	this.dataVector = convert.inputdata;
			drawStrategy();

		}
		catch (Exception e)
		{
		}
		Vector v = compman.getAllComponent();
		for (int i = 0; i < v.size(); i++)
		{
			MYDimension myd = (MYDimension) v.elementAt(i);
			if (myd instanceof MainGraphComponent)
			{
				((MainGraphComponent) myd).inputdata = convert.inputdata;
				((MainGraphComponent) myd).setHighLowValues();
			}
		}
		paintComponent(g);
//		// ln("start date="+	convert.startDate );
	}
	public void setDate(String startdate,String enddate)
	{
		convert.startDate = startdate;
		try
		{
			convert.loadFile(convert.symbolName,enddate);
			convert.setHighLowValues();
		//	this.dataVector = convert.inputdata;
			drawStrategy();
		}
		catch (Exception e)
		{
		}
		Vector v = compman.getAllComponent();
		for (int i = 0; i < v.size(); i++)
		{
			MYDimension myd = (MYDimension) v.elementAt(i);
			if (myd instanceof MainGraphComponent)
			{
				((MainGraphComponent) myd).inputdata = convert.inputdata;
				((MainGraphComponent) myd).setHighLowValues();
			}
		}
		paintComponent(g);
//		// ln("start date="+	convert.startDate );
	}
		
	int zoom = 0;
	int rightpos = 0;
	int zoomtype=0;
	int ZOOMIN=1;
	int ZOOMOUT=2;
	int LEFT=3;
	int RIGHT=4;
	int RESET=5;

	public void doZoomOut()
	{
		zoom++;
		zoomtype = ZOOMOUT;
		setForZooming();
	}
	public void doZoomIn()
	{
		zoom--;
		zoomtype = ZOOMIN;

		setForZooming();
	}
	public void doLeft()
	{
		rightpos++;
		zoomtype = LEFT;

		setForZooming();
	}
	public void doRight()
	{
		rightpos--;
		zoomtype = RIGHT;

		setForZooming();
	}
	public void doResetZoom()
	{
		rightpos = 0;
		zoom = 0;
		zoomtype = RESET;

		setForZooming();
	}

	public void setForZooming(int zoomtype,int points)
	{
	//	// ln(points);
		if (g == null)
			g = getGraphics();
	//	Vector data = getDataForPosition();
//		convert.inputdata = data;
//		convert.setHighLowValues();
		Vector v = compman.getAllComponent();
		for (int i = 0; i < v.size(); i++)
		{
			MYDimension myd = (MYDimension) v.elementAt(i);
			if (myd instanceof MainGraphComponent)
			{
			//	((MainGraphComponent) myd).inputdata = convert.inputdata;
			//	((MainGraphComponent) myd).setHighLowValues();
				if(zoomtype==ZOOMIN)
					myd.endx=myd.endx+10;
				if(zoomtype==ZOOMOUT)
				{	
					
					myd.endx=myd.endx-10;
				
				}
				if(zoomtype==LEFT)
				{	
					myd.startx=myd.startx-points;
					myd.endx=myd.endx-points;
					
				}
				if(zoomtype==RIGHT)
				{	
					
					myd.startx=myd.startx+points;
					myd.endx=myd.endx+points;
					
				}
				if(zoomtype==RESET)
				{	
					myd.startx=intstartx;
					myd.endx = intendx;
						
				}	
			}
			
		}
	}
	
	public void setForZooming()
	{
		if (g == null)
			g = getGraphics();
	//	Vector data = getDataForPosition();
//		convert.inputdata = data;
//		convert.setHighLowValues();
		Vector v = compman.getAllComponent();
		for (int i = 0; i < v.size(); i++)
		{
			MYDimension myd = (MYDimension) v.elementAt(i);
			if (myd instanceof MainGraphComponent)
			{
			//	((MainGraphComponent) myd).inputdata = convert.inputdata;
			//	((MainGraphComponent) myd).setHighLowValues();
				if(zoomtype==ZOOMIN)
				{
					myd.startx=myd.startx-10;
				//	myd.endy=myd.endy-10;
					
				}
				else if(zoomtype==ZOOMOUT)
				{	
				//	myd.endy=myd.endy+10;
					
					myd.startx=myd.startx+10;
				}
				else if(zoomtype==LEFT)
				{	
						
					myd.startx=myd.startx+10;
					myd.endx=myd.endx+10;
				//	myd.starty=myd.starty+10;
				//	myd.endy=myd.endy+10;
					
					
				//	// ln(myd.startx  + " and " + intendx);
				}
				else if(zoomtype==RIGHT)
				{	
					
					myd.startx=myd.startx-10;
					myd.endx=myd.endx-10;
				//	myd.starty=myd.starty-10;
				//	myd.endy=myd.endy-10;
					
				}
				else if(zoomtype==RESET)
				{	
					myd.startx=intstartx;
					myd.endx = intendx;
						
				}
				
			}
			
		}
		this.x1 = convert.startx - 10;
		this.x2 = convert.endx + beforeendx;
		
	if(!isintraday)	
		paintComponent(g);
	}
	public Vector getDataForYear(String year)
	{
		try
		{
			convert.loadFile(this.getSymbol());
	//		this.dataVector = convert.inputdata;
			for (int i = 0; i < convert.inputdata.size(); i++)
			{
				HashMap hs = (HashMap) convert.inputdata.elementAt(i);
				String date = (String) hs.get(MainGraphComponent.Date);
				StringTokenizer st = new StringTokenizer(date, "-");
				String dd = st.nextToken();
				String mm = st.nextToken();
				String yy = st.nextToken();
				int yyi = Integer.parseInt(yy);
				int yeari = Integer.parseInt(year);
				if (yyi >= yeari)
				{
					Vector retVec = new Vector();
					for (int j = i; j < convert.inputdata.size(); j++)
					{
						retVec.addElement(convert.inputdata.elementAt(j));
					}
					return retVec;
				}
			}
		}
		catch (Exception e)
		{
			return convert.inputdata;
		}
		return new Vector();
	}
	public Vector getDataForPosition()
	{
		int zoomby = 20;
		Vector retVector = new Vector();
		Vector retVector1 = new Vector();
		int size = this.convert.inputdata.size();
		int actrightpos = this.rightpos * 20;
		int actzoom = this.zoom * 20;
		int startpos = size - actrightpos;
		int totpoints = size - actzoom;
		if (totpoints > size)
		{
			this.zoom = 0;
			this.rightpos = 0;
			return this.convert.inputdata;
		}
		if (totpoints < 20)
		{
			this.zoom = this.zoom - 1;
			return this.convert.inputdata;
		}
		int j = size - actrightpos;
		if (rightpos < 0)
		{
			this.rightpos = 0;
			return convert.inputdata;
		}
		if (j < 0)
		{
			this.rightpos = this.rightpos - 1;
			return convert.inputdata;
		}
		for (int i = 0; i < totpoints; i++)
		{
			retVector.addElement(this.convert.inputdata.elementAt(j - 1));
			j--;
		}
		for (int i = retVector.size() - 1; i >= 0; i--)
		{
			retVector1.addElement(retVector.elementAt(i));
		}
		return retVector1;
	}
	public String getSymbol()
	{
		return convert.symbolName;
	}
	public HashMap indHash = new HashMap();
	public void addIndicator(Indicator indicator)
	{
		addIndicatorWithoutPaint(indicator);
		paintComponent(g);
	}
	public void addIndicatorWithoutPaint(Indicator indicator)
	{
		if (g == null)
			g = getGraphics();
		if (indicator.indicatorType == indicator.MMA
			|| indicator.indicatorType == indicator.SMA
			|| indicator.indicatorType == indicator.WMA
			|| indicator.indicatorType == indicator.EMA
			|| indicator.indicatorType == indicator.BB)
		{
			convert.addIndicator(indicator);
			indHash.put(convert.createID(indicator), indicator);
		}
		else
		{
			Vector indiVec = compman.getAllIndiComponent();
			if (indiVec.size() >= 3)
			{
				MainGraphComponent cd = ((MainGraphComponent) indiVec.elementAt(0));
				compman.removeIndComponent((MYDimension) indiVec.elementAt(0));
				indHash.remove(cd.getID());
			}
			if (indicator.indicatorType == indicator.MACD)
			{
				MACDGraph drawmacd = new MACDGraph(10, 0, x2, y2, y2);
				drawmacd.inputdata = convert.inputdata;
				drawmacd.noofdays12 = (int) indicator.period1;
				drawmacd.noofdays26 = (int) indicator.period2;
				drawmacd.noofdays9 = (int) indicator.period3;
				drawmacd.setID(indicator);
				drawmacd.movavgtype = drawmacd.EMA;
				drawmacd.note = convert.createID(indicator);
				drawmacd.setHighLowValues();
				compman.addComponent(drawmacd);
				indHash.put(convert.createID(indicator), indicator);
				updateDimension();
			}
			else if (indicator.indicatorType == indicator.Stochastic)
			{
				StochasticGraph stockst = new StochasticGraph(10, 0, x2, y2, y2);
				stockst.inputdata = convert.inputdata;
				stockst.noofdays = (int) indicator.period1;
				stockst.period2 = (int) indicator.period2;
				stockst.setID(indicator);
				stockst.note = convert.createID(indicator);
				stockst.setHighLowValues();
				compman.addComponent(stockst);
				indHash.put(convert.createID(indicator), indicator);
				updateDimension();
			}
			else if (indicator.indicatorType == indicator.VolumeOscilator)
			{
				OtherIndicatorGraph stockst = new OtherIndicatorGraph(10, 0, x2, y2, y2, MYDimension.VolumeOscillator);
				stockst.inputdata = convert.inputdata;
				stockst.noofdays = (int) indicator.period1;
				stockst.noofdays2 = (int) indicator.period2;
				stockst.setID(indicator);
				stockst.note = convert.createID(indicator);
				stockst.setHighLowValues();
				compman.addComponent(stockst);
				indHash.put(convert.createID(indicator), indicator);
				updateDimension();
			}
			else if (indicator.indicatorType == indicator.RSI)
			{
				OtherIndicatorGraph rsi = new OtherIndicatorGraph(10, 0, x2, y2, y2, MYDimension.RSI);
				rsi.inputdata = convert.inputdata;
				rsi.noofdays = (int) indicator.period1;
				rsi.setID(indicator);
				rsi.note = convert.createID(indicator);
				rsi.setHighLowValues();
				compman.addComponent(rsi);
				indHash.put(convert.createID(indicator), indicator);
				updateDimension();
			}

			else if (indicator.indicatorType == indicator.MFI)
			{
				OtherIndicatorGraph rsi = new OtherIndicatorGraph(10, 0, x2, y2, y2, MYDimension.MFI);
				rsi.inputdata = convert.inputdata;
				rsi.noofdays = (int) indicator.period1;
				rsi.setID(indicator);
				rsi.note = convert.createID(indicator);
				rsi.setHighLowValues();
				compman.addComponent(rsi);
				indHash.put(convert.createID(indicator), indicator);
				updateDimension();
			}
			else if (indicator.indicatorType == indicator.William)
			{
				OtherIndicatorGraph stockst = new OtherIndicatorGraph(10, 0, x2, y2, y2, MYDimension.William);
				stockst.inputdata = convert.inputdata;
				stockst.noofdays = (int) indicator.period1;
				stockst.setID(indicator);
				stockst.note = convert.createID(indicator);
				stockst.setHighLowValues();
				compman.addComponent(stockst);
				indHash.put(convert.createID(indicator), indicator);
				updateDimension();
			}
			else if (indicator.indicatorType == Indicator.ONBALANCEVOLUME)
			{
				OtherIndicatorGraph rsi = new OtherIndicatorGraph(10, 0, x2, y2, y2, MYDimension.ONBALANCEVOLUME);
				rsi.inputdata = convert.inputdata;
				rsi.setID(indicator);
				rsi.note = convert.createID(indicator);
				rsi.setHighLowValues();
				compman.addComponent(rsi);
				indHash.put(convert.createID(indicator), indicator);
				updateDimension();
			}
			else if (indicator.indicatorType == Indicator.AccDistri)
			{
				OtherIndicatorGraph rsi = new OtherIndicatorGraph(10, 0, x2, y2, y2, MYDimension.AccDistri);
				rsi.inputdata = convert.inputdata;
				rsi.note = convert.createID(indicator);
				rsi.setID(indicator);
				rsi.setHighLowValues();
				compman.addComponent(rsi);
				indHash.put(convert.createID(indicator), indicator);
				updateDimension();
			}
			else if (indicator.indicatorType == Indicator.ChaikinOscillator)
			{
				OtherIndicatorGraph rsi = new OtherIndicatorGraph(10, 0, x2, y2, y2, MYDimension.ChaikinOscillator);
				rsi.inputdata = convert.inputdata;
				rsi.setID(indicator);
				rsi.note = convert.createID(indicator);
				rsi.setHighLowValues();
				compman.addComponent(rsi);
				indHash.put(convert.createID(indicator), indicator);
				updateDimension();
			}
			else if (indicator.indicatorType == Indicator.AverageTrueRange)
			{
				OtherIndicatorGraph rsi = new OtherIndicatorGraph(10, 0, x2, y2, y2, MYDimension.AverageTrueRange);
				rsi.inputdata = convert.inputdata;
				rsi.setID(indicator);
				rsi.noofdays = indicator.period1;
				rsi.note = convert.createID(indicator);
				rsi.setHighLowValues();
				compman.addComponent(rsi);
				indHash.put(convert.createID(indicator), indicator);
				updateDimension();
			}
			else if (indicator.indicatorType == Indicator.AroopOscillator)
			{
				OtherIndicatorGraph rsi = new OtherIndicatorGraph(10, 0, x2, y2, y2, MYDimension.AroopOscillator);
				rsi.inputdata = convert.inputdata;
				rsi.setID(indicator);
				rsi.noofdays = (int) indicator.period1;
				rsi.note = convert.createID(indicator);
				rsi.setHighLowValues();
				compman.addComponent(rsi);
				indHash.put(convert.createID(indicator), indicator);
				updateDimension();
			}
			else if (indicator.indicatorType == Indicator.ChaikinMoneyFlow)
			{
				OtherIndicatorGraph rsi = new OtherIndicatorGraph(10, 0, x2, y2, y2, MYDimension.ChaikinMoneyFlow);
				rsi.inputdata = convert.inputdata;
				rsi.setID(indicator);
				rsi.noofdays = (int) indicator.period1;
				rsi.note = convert.createID(indicator);
				rsi.setHighLowValues();
				compman.addComponent(rsi);
				indHash.put(convert.createID(indicator), indicator);
				updateDimension();
			}
			else if (indicator.indicatorType == Indicator.WillamAD)
			{
				OtherIndicatorGraph rsi = new OtherIndicatorGraph(10, 0, x2, y2, y2, MYDimension.WillamAD);
				rsi.inputdata = convert.inputdata;
				rsi.setID(indicator);
				rsi.noofdays = (int) indicator.period1;
				rsi.note = convert.createID(indicator);
				rsi.setHighLowValues();
				compman.addComponent(rsi);
				indHash.put(convert.createID(indicator), indicator);
				updateDimension();
			}
			else if (indicator.indicatorType == Indicator.CCI)
			{
				OtherIndicatorGraph rsi = new OtherIndicatorGraph(10, 0, x2, y2, y2, MYDimension.CCI);
				rsi.inputdata = convert.inputdata;
				rsi.setID(indicator);
				rsi.noofdays = (int) indicator.period1;
				rsi.note = convert.createID(indicator);
				rsi.setHighLowValues();
				compman.addComponent(rsi);
				indHash.put(convert.createID(indicator), indicator);
				updateDimension();
			}
			else if (indicator.indicatorType == Indicator.DPO)
			{
				OtherIndicatorGraph rsi = new OtherIndicatorGraph(10, 0, x2, y2, y2, MYDimension.DPO);
				rsi.inputdata = convert.inputdata;
				rsi.setID(indicator);
				rsi.noofdays = (int) indicator.period1;
				rsi.note = convert.createID(indicator);
				rsi.setHighLowValues();
				compman.addComponent(rsi);
				indHash.put(convert.createID(indicator), indicator);
				updateDimension();
			}
			else if (indicator.indicatorType == Indicator.EOM)
			{
				OtherIndicatorGraph rsi = new OtherIndicatorGraph(10, 0, x2, y2, y2, MYDimension.EOM);
				rsi.inputdata = convert.inputdata;
				rsi.setID(indicator);
				rsi.noofdays = (int) indicator.period1;
				rsi.note = convert.createID(indicator);
				rsi.setHighLowValues();
				compman.addComponent(rsi);
				indHash.put(convert.createID(indicator), indicator);
				updateDimension();
			}
			else if (indicator.indicatorType == Indicator.CUSTOM)
			{
				CustmizedIndicator rsi = new CustmizedIndicator(10, 0, x2, y2, y2, MYDimension.CUSTOM);
				rsi.inputdata = convert.inputdata;
				rsi.maingraph = convert;
				rsi.g = this.g;
				rsi.setID(indicator);
				rsi.query = indicator.query;
				rsi.note = convert.createID(indicator);
				rsi.setHighLowValues();
				compman.addComponent(rsi);
				indHash.put(convert.createID(indicator), indicator);
				updateDimension();
			}


		}
	}
	public void removeIndicator(Indicator indicator)
	{
		if (g == null)
			g = getGraphics();
		if (indicator.indicatorType == indicator.MMA
			|| indicator.indicatorType == indicator.SMA
			|| indicator.indicatorType == indicator.WMA
			|| indicator.indicatorType == indicator.EMA
			|| indicator.indicatorType == indicator.BB)
		{
			convert.removeIndicator(indicator);
			indHash.remove(convert.createID(indicator));
		}
	
		else
		{
			if(indicator.indicatorType == indicator.CUSTOM)
			{
				for(int i=0;i<convert.inputdata.size();i++)
				{
					HashMap hs = (HashMap)convert.inputdata.get(i);
					hs.remove(MainGraphComponent.BUYIND);
					hs.remove(MainGraphComponent.SELLIND);
					hs.remove(MainGraphComponent.ALERTTEXT);
					hs.remove(MainGraphComponent.ALERT);

					hs.remove(MainGraphComponent.PLOT1);
					hs.remove(MainGraphComponent.PLOT2);
					hs.remove(MainGraphComponent.PLOT3);
					
					hs.remove(MainGraphComponent.COLOR1);
					hs.remove(MainGraphComponent.COLOR2);
					hs.remove(MainGraphComponent.COLOR3);

					
					
				}
			}
			Vector indiVec = compman.getAllIndiComponent();
			for (int i = 0; i < indiVec.size(); i++)
			{
				MainGraphComponent cb = (MainGraphComponent) indiVec.elementAt(i);
				if (convert.createID(indicator).equals(cb.getID()))
				{
					compman.removeComponent((MYDimension) cb);
					indHash.remove(cb.getID());
				}
			}
		}
		updateDimension();
		paintComponent(g);
	}
	public void paintComponent(Graphics g)
	{
	//	// ln("paintcomponent called");
		if (g == null)
			g = getGraphics();
		gr = g;
		super.paintComponent(g);
		g.setColor(Color.black);
		Vector v = new Vector();
		showAllAddedComponent(g);
	
		
		
	//	if(true)
	//		return;
		
		if(!isLineNotPainted)
			return;
		
		g.setColor(Color.pink);
		g.fillRect(myx1 - 2, myy1 - 2, 5, 5);
		g.setColor(Color.pink);
	//	// ln(dragx + " " + dragy + " " +  newx + " " +  newy);
		g.drawLine(myx1, myy1, myx2,myy2);
	
	}
	
	public void drawError(String error)

	{
		if (g == null)
			g = getGraphics();
		g.setColor(Color.black);
		
		g.fillRect(95 , 95 , 200, 20);
		
		g.setColor(Color.red);
		g.drawString(error,100,100);
		
	}
	public RenderedImage saveGraphAsJPEG()
	{
		BufferedImage bufferedImage = new BufferedImage(this.x2 - this.x1, this.y2 - this.y1+50, BufferedImage.TYPE_INT_RGB);
		// Create a graphics contents on the buffered image
		Graphics2D g2d = bufferedImage.createGraphics();
		showAllAddedComponent(g2d);
		Color c = g2d.getColor();
		g2d.setColor(Color.gray);
		g2d.drawString("www.iguidestocks.com", 20,50);
		g2d.setColor(c);
		g2d.dispose();
		return bufferedImage;
	}
	public void writeImage(String filepath)
	{
		
		RenderedImage rendImage = saveGraphAsJPEG(null);
		// Write generated image to a file
		try
		{
			// Save as PNG
			//				   File file = new File("c:\\newimage.jp");
			//				   ImageIO.write(rendImage, "png", file);
			// Save as JPEG
			File file = new File(filepath);
						   ImageIO.write(rendImage, "jpg", file);
			//		   		Image
		}
		catch (Exception e)
		{
		//			// ln(e);
		}
		
	}
	public RenderedImage saveGraphAsJPEG(Graphics g)
	{
		BufferedImage bufferedImage = new BufferedImage(this.x2 - this.x1, this.y2 - this.y1+50, BufferedImage.TYPE_INT_RGB);
		// Create a graphics contents on the buffered image
		Graphics2D g2d = bufferedImage.createGraphics();
		showAllAddedComponent(g2d);
		Color c = g2d.getColor();
		g2d.setColor(Color.gray);
		g2d.drawString("www.iguidestocks.com", 20,50);
		g2d.setColor(c);
		g2d.dispose();
		return bufferedImage;
	}
	
	
	public void showAllAddedComponent(Graphics g)
	{
		if (g == null)
			g = getGraphics();
		/*
				g.setColor(Color.red);
				
				for(int i=0;i<16;i++)
					{
						g.drawLine(0,i*50,50,i*50);
						String s = (i*50) + "";
					
						g.drawBytes(s.getBytes(), 0, s.length(), 50, i*50);
					}
		*/
		
		compman.showAllAddedComponentForImage(g);
		
		
	}
	public void updateColor(Color fontcolor, Color linecolor, Color fillcolor)
	{
		
		if(mydimension != null)
			{
				mydimension.setColor(fontcolor, linecolor, fillcolor);
				repaint();
				return;		
			}
			else
			{
				MessageDiaglog msg = new MessageDiaglog("No Component Selected,Pls select drawing component");
			}
	
	}
		public void keyTyped(KeyEvent e)
		{
			if (g == null)
				g = getGraphics();
			//			// ln("keyTyped");
		}
		/* (non-Javadoc)
		 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
		 */
		public void keyPressed(KeyEvent evt)
		{
//			// ln("keypressed event called");
			if (g == null)
				g = getGraphics();
			repaint();
			
			int key = evt.getKeyCode(); // keyboard code for the pressed key
			if(key == KeyEvent.VK_DELETE)
			{
				if(mydimension != null)
				{
					removeComponent(mydimension);
					mydimension = null;
					repaint();
					return;		
				}
			}
			else if ((key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN) && (mydimension != null))
			{

				if (mydimension != null)
				{
					convert.draggingComponent=true;
					int x=0;
					int y=0;
					switch(key)
					{
						case KeyEvent.VK_LEFT :
							x= -1; y=0; break;
						case KeyEvent.VK_RIGHT :
							x= 1; y=0; break;
						case KeyEvent.VK_UP :
							x = 0;	y = -1;break;
						case KeyEvent.VK_DOWN :
							x = 0;	y = 1;break;
						default : break;
					}
//					// ln("x,y = "+ x+ ","+y);
					mydimension.updateXYCord(mydimension.startx ,mydimension.starty , x, y);
					repaint();
//					convert.draggingComponent=false;
					return;
				}

			}
			else if (key == KeyEvent.VK_RIGHT)
			{
				try
				{
					int i = convert.getIndexForX(dragx);
					if (i < (convert.xpos.length - 1))
					{
						if (dragx > intendx)
						{
						//	dragx = convert.xpos[i - 1];
								
						}
						else
						{

							dragx = convert.xpos[i + 1];
									
						}
						if (dragx > intendx )
						{
							int x = convert.xpos[i -1 ] - convert.xpos[i -2 ]; 
						//	// ln(x);
						//	// ln("endx="+convert.startx);
							setForZooming(LEFT, x);
						}
						
						
					}

				}
				catch (Exception e)
				{
				}
			}
			else if (key == KeyEvent.VK_LEFT)
			{
				try
				{
					
					int i = convert.getIndexForX(dragx);
				//	// ln("i="+i);
					
					if (i >= (0))
					{
						if (dragx < intstartx)
						{
						//	dragx = convert.xpos[i - 1];
								
						}
						else
						{
							dragx = convert.xpos[i - 1];
								
						}
					//	// ln("dragx="+dragx);

						if (dragx < intstartx  )
						{
							
							int x = convert.xpos[i -1 ] - convert.xpos[i -2 ]; 
						//	// ln(x);
						//	// ln("startx="+convert.startx);
							setForZooming(RIGHT, x);
							
						}
					
					}
				}
				catch (Exception e)
				{
				}
			}

			else if (key == KeyEvent.VK_UP)
			{
				dragy = dragy - 1;
			}
			else if (key == KeyEvent.VK_DOWN)
			{
				dragy = dragy + 1;
			}
			compman.drawCrossHair(dragx, dragy, g);
		}
		// end keyPressed()
		/* (non-Javadoc)
		 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
		 */
		public void keyReleased(KeyEvent e)
		{
		//	// ln("keyreleased event called");
//			if (g == null)
//				g = getGraphics();
			compman.drawCrossHair(dragx, dragy, g);
			//			// ln("keyReleased");
			showAlert(dragx, dragy);
		}
		public void showAlert(int xpos,int ypos)
		{
			int index=0;
			try
			{
				index = convert.getIndexForX(xpos);
			}
			catch (Exception e)
			{
				return;
			}
			if ( index >= convert.inputdata.size())
				return;
			if ( index < 0)
				return;
			
			HashMap hs = (HashMap) convert.inputdata.elementAt(index);
			String alert = (String)hs.get(MainGraphComponent.ALERT);
			String alerttext = (String)hs.get(Function.ALERTTEXT);
			
			if(alert != null)
			{
				MessageDiaglog msg = new MessageDiaglog(alert);
			}
			if (alerttext != null)
			{
				convert.showAlertText(xpos, ypos, g, alerttext);
			}
//			float price = Float.parseFloat((String) hs.get(Close));
//			newypos = getYPosition(price);

		}

		public void showSelectedComponent(MouseEvent e)
		{
			if (g == null)
				g = getGraphics();
			int xxpos = e.getX();
			int yypos = e.getY();
			mydimension = compman.findFirstComponentWhereMouseIs(xxpos, yypos);
			if (mydimension != null)
			{
				mydimension.showSelection(g);
			}
		}
		int oldx;
		int oldy;
		boolean firstTime = true;
		public void repaint()
		{

			//// ln("repain");
			super.repaint();
			
		}
		int myx1,myx2,myy1,myy2;
		public void mouseDragged(MouseEvent e)
		{
		//	// ln("mousedragged event called");
			if (g == null)
				g = getGraphics();

			if (SwingUtilities.isRightMouseButton(e))
			{
				newx = e.getX();
				newy = e.getY();
				//				// ln("nexx" + newx + "newy" + newy);
				if (dragx != 0 && dragy != 0)
				{
					int xdiffnew = newx - dragx;
					int ydiddnew = newy - dragy;
//					mydimension = compman.findFirstComponentWhereMouseIs(newx, newy);
					if (mydimension != null)
					{
						convert.draggingComponent=true;
						int xxpos = e.getX();
						int yypos = e.getY();
						if(mydimension instanceof Line)
						{
							int xdifffromcur=0;
								if (Math.abs(newx - mydimension.startx) < Math.abs(newx - mydimension.endx))
								{
									mydimension.startx = xxpos;
									
								}
								else
								{
									mydimension.endx = xxpos;
									
									
								}	
								if (Math.abs(newy - mydimension.starty) < Math.abs(newy - mydimension.endy))
								{
									mydimension.starty = yypos;
									
								}
								else
								{
									mydimension.endy = yypos;
								}	
								mydimension.updateDimension();
						}
						else
						{
							compman.expand(xxpos, yypos, xdiffnew, ydiddnew, mydimension);
							
						}
						dragx = newx;
						dragy = newy;
					}
					else
					{
						dragx = newx;
						dragy = newy;
					}
			//		// ln("calling printf in component");
					repaint();
					
				}				
			}
			else if (SwingUtilities.isLeftMouseButton(e))
			{
				int newx = e.getX();
				int newy = e.getY();
				if (dragx != 0 && dragy != 0)
				{
					if(isExpandDragging)
					{
						
						g.setColor(Color.white);
						if(newx > dragx)
							g.drawRect(dragx , convert.starty,newx-dragx, convert.starty - convert.endy );
						else
							g.drawRect(newx , convert.starty,dragx-newx, convert.starty - convert.endy );
						
						oldx = newx;
						oldy = newy;
					}
					
					else if (isLineNotPainted)
					{
					
						if (oldx != newx || oldy != newy)
						{
					//		// ln("about to call repaint "+newx +" and "+oldx);
							repaint();
						}
						try
						{
							//			wait(500);
						}
						catch (Exception e1)
						{
						}
						g.setColor(Color.pink);
						g.fillRect(dragx - 2, dragy - 2, 5, 5);
						g.setColor(Color.pink);
				//		// ln(dragx + " " + dragy + " " +  newx + " " +  newy);
						g.drawLine(dragx, dragy, newx, newy);
						myx1 = dragx;
						myy1 = dragy;
						myx2 = newx;
						myy2 = newy;
						
						oldx = newx;
						oldy = newy;
					}
					else
					{
						newx = e.getX();
						newy = e.getY();
						int xdiffnew = newx - dragx;
						int ydiddnew = newy - dragy;
//						mydimension = compman.findFirstComponentWhereMouseIs(newx, newy);
						if (mydimension != null)
						{
							convert.draggingComponent=true;
							int xxpos = e.getX();
							int yypos = e.getY();
							compman.updateXYCord(xxpos, yypos, xdiffnew, ydiddnew, mydimension);
							dragx = newx;
							dragy = newy;
						}
						else
						{
							dragx = newx;
							dragy = newy;
						}
						repaint();
					}
				}
			}
		}
		public void mouseClicked(MouseEvent e)
		{
	//		// ln("mouseclicked event called");
			if (g == null)
				g = getGraphics();
			if (g == null)
				g = getGraphics();
			dragx = e.getX();
			dragy = e.getY();
			if(isHorLine)
			{
				LineHor dim = new LineHor(10, dragy, x2, dragy,this);
				addComponent(dim);
				isHorLine = false;
				paintComponent(g);

								
			}
			else if(isVerLine)
			{
				LineVer dim = new LineVer(dragx, 0, dragx, 500,this);
				addComponent(dim);
				isVerLine = false;
				paintComponent(g);

					
			}
			else if(isBuyArrow)
			{
				BuyArrow dim = new BuyArrow(dragx, dragy, dragx+10, 500,this);
				addComponent(dim);
				isBuyArrow = false;
				paintComponent(g);

					
			}
			else if(isSellArrow)
			{
				SellArrow dim = new SellArrow(dragx, dragy, dragx+10, 500,this);
				addComponent(dim);
				isSellArrow = false;
				paintComponent(g);

					
			}
			
			else if (isLineNotPainted)
			{
				g.setColor(Color.pink);
				g.fillRect(e.getX() - 2, e.getY() - 2, 5, 5);
				oldx = e.getX();
				oldy = e.getY();
			}
			else
			{
				int xxpos = e.getX();
				int yypos = e.getY();
				mydimension = compman.findFirstComponentWhereMouseIs(xxpos, yypos);
				if(mydimension != null)
				{
					mydimension.showSelection(g);
				}
//				showSelectedComponent(e);
			}
		}
		boolean mousepainted = false;
		public void mouseMoved(MouseEvent e)
		{
//			// ln("mousemoved event called");
			if (g == null)
				g = getGraphics();
			//				g.setColor(Color.red);
			//				g.fillRect(10,10,300,20);
			//				g.setColor(Color.white);
			//				String s = e.getX()+e.getY()+"";
			//				String s = convert.getDateForXpos(e.getX());
			//				s = convert.getDataForDate(e.getX());
			//				g.drawBytes(s.getBytes(),0,s.length(),20,20);
			Vector v = compman.getAllGraphComponent();
			for (int i = 0; i < v.size(); i++)
			{
				MainGraphComponent main = (MainGraphComponent) v.elementAt(i);
				main.showPriceOnLeft(e.getX(), e.getY(), g);
			}
			Object o = compman.findFirstComponentWhereMouseIs(e.getX(),e.getY());
			if(o==null)
			{
				this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

			}
			else
			{
				this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				
			}
			
		}
		public void mousePressed(MouseEvent e)
		{
			
			if (g == null)
				g = getGraphics();
			dragx = e.getX();
			dragy = e.getY();
			if (g == null)
				g = getGraphics();
			requestFocus();
			repaint();
	//		// ln("mousepressend");
		}
		Line dim = null;
		public void mouseReleased(MouseEvent e)
		{
//			// ln("mousereleased event called");
			if (g == null)
				g = getGraphics();
			if (dragx != 0 && dragy != 0 && isLineNotPainted)
			{
				Line dim = new Line(dragx, dragy, e.getX(), e.getY(),this);
				addComponent(dim);
				isLineNotPainted = false;
				paintComponent(g);
			}
			else if(dragx != 0 && dragy != 0 && isExpandDragging)
			{
				try
				{
					int x1 = convert.getIndexForX(dragx);
					int x2 = convert.getIndexForX(e.getX());
					
					Vector data = null; 
					if(x2 > x1)
						data = Utility.subList(convert.inputdata, x1, x2);
					else
					data = Utility.subList(convert.inputdata, x2, x1);
					
					convert.inputdata = data;
					convert.setHighLowValues();
					Vector v = compman.getAllComponent();
					for (int i = 0; i < v.size(); i++)
					{
						MYDimension myd = (MYDimension) v.elementAt(i);
						if (myd instanceof MainGraphComponent)
						{
							((MainGraphComponent) myd).inputdata = convert.inputdata;
							((MainGraphComponent) myd).setHighLowValues();
						}
					}
					paintComponent(g);
					
				}
				catch(Exception e1)
				{
					
				}
				isExpandDragging = false;
			}
			else
			{
				dragx = e.getX();
				dragy = e.getY();
				if (SwingUtilities.isLeftMouseButton(e))
				{
					startPoint = e.getPoint();
				}
				compman.drawCrossHair(e.getX(), e.getY(), g);
				int xxpos = e.getX();
				int yypos = e.getY();
				mydimension = compman.findFirstComponentWhereMouseIs(xxpos, yypos);
				if (mydimension == null)
				{
					if(mydimension != null)
					{
						convert.draggingComponent=true;
						mydimension.updateDimension();
					}

					dragx = e.getX();
					dragy = e.getY();
				}
				showAlert(dragx, dragy);
				convert.draggingComponent=false;

			}
		}
	public static void main(String[] args)
	{
		JFrame frame = new JFrame("Chart Alive");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GraphComponentContainer d = new GraphComponentContainer(0, 0, 1400, 1000, "TCS");
		frame.getContentPane().add(d);
		frame.pack();
		//		frame.setBounds(0,0,1000,700);
		frame.setVisible(true);
	}
	/**
	 * @return
	 */
	public Vector getIndicators()
	{
		Vector retVector = new Vector();
		Iterator i = indHash.keySet().iterator();
		while (i.hasNext())
		{
			String val = (String) i.next();
			retVector.addElement(val);
		}
		// TODO Auto-generated method stub
		return retVector;
	}
	/* (non-Javadoc)
	 * @see java.awt.event.FocusListener#focusGained(java.awt.event.FocusEvent)
	 */
	public void focusGained(FocusEvent arg0)
	{
		// TODO Auto-generated method stub
	}
	/* (non-Javadoc)
	 * @see java.awt.event.FocusListener#focusLost(java.awt.event.FocusEvent)
	 */
	public void focusLost(FocusEvent arg0)
	{
		// TODO Auto-generated method stub
	}
	/**
	 * @param b
	 */
	

	public void doPaintBuyArrow(boolean b)
	{
		isBuyArrow = true;
		// TODO Auto-generated method stub
	}
	public void doPaintSellArrow(boolean b)
	{
		isSellArrow = true;
		// TODO Auto-generated method stub
	}

	public void doPaintLineVer(boolean b)
	{
		isVerLine = true;
		// TODO Auto-generated method stub
	}
	public void doPaintLineHor(boolean b)
	{
		isHorLine = true;
		// TODO Auto-generated method stub
	}
	public void doPaintLine(boolean b)
	{
		isLineNotPainted = true;
		// TODO Auto-generated method stub
	}
	
	public void doExpand(boolean b)
	{
		isExpandDragging = true;
		// TODO Auto-generated method stub
	}

	
	
	/**
	 * @param i
	 */
	public void setCursorType(int i)
	{
		this.compman.cursorType = i;
		// TODO Auto-generated method stub
	}
	public static String Name="Name";
	public static String Value="Value";
	
	
	public void drawStrategy(Vector strategyVector)
	{
		Vector v = new Vector();
		v.addAll(strategyVector);
		v.addAll(this.strategyVector);
		
		this.strategyVector = new Vector();
		this.strategyVector.addAll(v);
		
		this.removeVariables();
		
		drawStrategy();
		paintComponent(g);
	}
	public Vector stratetgyOutput = new Vector();
	public void removeVariables()
	{
		for(int j=0;j < convert.inputdata.size();j++)
		{
			HashMap hs = (HashMap)convert.inputdata.elementAt(j);

			hs.remove(MainGraphComponent.BUY);
			hs.remove(MainGraphComponent.SELL);

			hs.remove(MainGraphComponent.ALERT);
			hs.remove(MainGraphComponent.ALERTTEXT);

			hs.remove(MainGraphComponent.PLOT1);
			hs.remove(MainGraphComponent.PLOT2);
			hs.remove(MainGraphComponent.PLOT3);
			hs.remove(MainGraphComponent.COLOR1);
			hs.remove(MainGraphComponent.COLOR2);
			hs.remove(MainGraphComponent.COLOR3);

			hs.remove(MainGraphComponent.OTHERCOMPONENT);
			hs.remove(Function.LEFTPRICE);
			hs.remove(Function.RIGHTPRICE);
		

		}
		
	}
	public void addVariables(Vector outputVec)
	{
		for(int j=0;j < outputVec.size();j++)
		{
			HashMap hs = (HashMap)outputVec.elementAt(j);
			String buy = (String)hs.get(MainGraphComponent.BUY);
			String sell = (String)hs.get(MainGraphComponent.SELL);
			String alert = (String)hs.get(MainGraphComponent.ALERT);
			String alerttext = (String)hs.get(Function.ALERTTEXT);

			
			String plot1 = (String)hs.get(MainGraphComponent.PLOT1);
			String plot2 = (String)hs.get(MainGraphComponent.PLOT2);
			String plot3 = (String)hs.get(MainGraphComponent.PLOT3);
		
			String color1 = (String)hs.get(MainGraphComponent.COLOR1);
			String color2 = (String)hs.get(MainGraphComponent.COLOR2);
			String color3= (String)hs.get(MainGraphComponent.COLOR3);

			Vector component = (Vector)hs.get(MainGraphComponent.OTHERCOMPONENT);
			Vector leftprice = (Vector)hs.get(Function.LEFTPRICE);
			Vector rightprice = (Vector)hs.get(Function.RIGHTPRICE);
						
			HashMap hscur = (HashMap)convert.inputdata.elementAt(j);
//			String s = ":"+(String)hs.get(MainGraphComponent.Date);
			if(buy != null )
			{
				hscur.put(MainGraphComponent.BUY,buy);
			}
			if(sell != null )
			{
				hscur.put(MainGraphComponent.SELL,sell);
			}
			if(alert != null)
			{
				hscur.put(MainGraphComponent.ALERT,alert);
					
			}
			if(alerttext != null)
			{
				hscur.put(Function.ALERTTEXT,alerttext);
					
			}

			if(plot1 != null)
			{
				hscur.put(MainGraphComponent.PLOT1,plot1);
	
			}
			if(plot2 != null)
			{
				hscur.put(MainGraphComponent.PLOT2,plot2);

			}
			if(plot3 != null)
			{
				hscur.put(MainGraphComponent.PLOT3,plot3);

			}
			if(color1 != null)
			{
				hscur.put(MainGraphComponent.COLOR1,color1);
	
			}
			if(color2 != null)
			{
				hscur.put(MainGraphComponent.COLOR2,color2);

			}
			if(color3 != null)
			{
				hscur.put(MainGraphComponent.COLOR3,color3);

			}
			if(component != null)
			{
				hscur.put(MainGraphComponent.OTHERCOMPONENT,component);

			}
			if(leftprice != null)
			{
				hscur.put(Function.LEFTPRICE,leftprice);

			}
			if(rightprice != null)
			{
				hscur.put(Function.RIGHTPRICE,rightprice);

			}

									
		}
	
	}

	public void applyIntradayDefaultStrategy()
	{
		if(!StockConstants.StockSnapshot)
		{
			return;
		}
		Vector tempStrategyVector = new Vector();
		try
		{
			HashMap tempHs = new HashMap();
			tempHs.put(StrategyUtility.Formula, Utility.getFileContent(StockConstants.INSTALL_DIR + "/formula/buysellrecomintraday.fl"));
			tempHs.put(StrategyUtility.Expand, "");
			tempStrategyVector.add(tempHs);
			HashMap hs1 = (HashMap)tempStrategyVector.elementAt(0);
//			String strQuery = BuySellStrategy.SELECTQUERY + (String)hs1.get(StrategyUtility.Formula);
			String strQuery = (String)hs1.get(StrategyUtility.Formula);
	//		String strQueryExpand = (String)hs1.get(StrategyUtility.Expand);

//			String 	strategyType = ((String)hs1.get(StrategyUtility.Type)).substring(0,1);
			BuySellStrategy buysellstr = new BuySellStrategy();
			buysellstr.symbol = this.symbol;	

			Vector outputVec = buysellstr.executeStrategy(convert.inputdata,strQuery);
			
//			stratetgyOutput = outputVec;
			addVariables(outputVec);
//			int temp = convert.xpos[convert.xpos.length -1];
			
			outputHash = (HashMap)outputVec.get(outputVec.size() -1);
			String s = (String)outputHash.get(MainGraphComponent.ALERT);
			String s1 = (String)outputHash.get(Function.ALERTTEXT);
			convert.showalerttext=true;;

		}
		catch(Exception e)
		{
			
		}
	}
	public void drawStrategy()
	{	
//		removeVariables();	

		Vector tempStrategyVector = BuySellStrategy.copyData(strategyVector);
		for(int i=0;i<tempStrategyVector.size();i++)
		{
			HashMap hs1 = (HashMap)tempStrategyVector.elementAt(i);
//			String strQuery = BuySellStrategy.SELECTQUERY + (String)hs1.get(StrategyUtility.Formula);
			String strQuery = (String)hs1.get(StrategyUtility.Formula);
	//		String strQueryExpand = (String)hs1.get(StrategyUtility.Expand);

//			String 	strategyType = ((String)hs1.get(StrategyUtility.Type)).substring(0,1);
			BuySellStrategy buysellstr = new BuySellStrategy();
			buysellstr.symbol = this.symbol;	

			Vector outputVec = buysellstr.executeStrategy(convert.inputdata,strQuery);
			
//			stratetgyOutput = outputVec;
			addVariables(outputVec);
//			int temp = convert.xpos[convert.xpos.length -1];
			
			outputHash = (HashMap)outputVec.get(outputVec.size() -1);
			String s = (String)outputHash.get(MainGraphComponent.ALERT);
			String s1 = (String)outputHash.get(Function.ALERTTEXT);
			convert.showalerttext=true;;

			if(s != null)
			{
				MessageDiaglog msg = new MessageDiaglog(s);
			}
			
//			repaint();
			
		}

	}
	public HashMap outputHash = new HashMap();

	public boolean isfutureintraday=false;;
	/**
	 * 
	 */
	public void repaintMe()
	{
		if (g == null)
			g = getGraphics();
		paintComponent(g);
	
	}
	/* (non-Javadoc)
	 * @see com.stockfaxforu.frontend.StrategyDataInterface#getStrategyVector()
	 */
	public Vector getStrategyVector()
	{
		return convert.inputdata;
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
	public void drawStrategy(Vector strategyVector, String string,
			String startDateButton, String endDateButton) {
		// TODO Auto-generated method stub
		
	}
	
}
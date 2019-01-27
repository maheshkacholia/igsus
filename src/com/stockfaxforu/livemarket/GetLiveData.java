/*
 * Created on Mar 11, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.livemarket;
import javax.swing.JTable;
import javax.swing.plaf.SliderUI;
import javax.swing.table.TableCellRenderer;
import com.stockfaxforu.livemarket.ticker.HorizontalTicker;
import com.stockfaxforu.util.Utility;
/**
 * @author sdeopura
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class GetLiveData implements Runnable
{
	ShowLiveMarket shoelive = null;
	String[][] oldDataValue = null;
	String[][] dataValue = null;
	public boolean runThread = true;
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public GetLiveData(ShowLiveMarket shoelive)
	{
		this.shoelive = shoelive;
	}
	public void run()
	{
		try
		{
			
		String[] data = null;
		HorizontalTicker hticker = new HorizontalTicker();
		while (runThread)
		{
			data = hticker.getLiveData(0);
			if (data == null)
			{
			}
			else
			{
				dataValue = convertDataValue(data);
			}
			if (oldDataValue == null)
			{
				oldDataValue = dataValue;
			}
			if (shoelive.scrollPane != null && shoelive.table != null)
			{
				shoelive.scrollPane.remove(shoelive.table);
				shoelive.remove(shoelive.scrollPane);
			}
			shoelive.table = new JTable(dataValue, shoelive.columnNames);
			TableCellRenderer renderer = new CustomTableCellRenderer(oldDataValue,dataValue);
			shoelive.table.setDefaultRenderer(Object.class, renderer);
			shoelive.table.addMouseListener(shoelive);
			// Add the table to a scrolling pane
			shoelive.scrollPane = JTable.createScrollPaneForTable(shoelive.table);
			
			shoelive.scrollPane.setBounds(shoelive.x1/2,0, shoelive.x1/2 - 10, shoelive.y1 );
			for (int i = 0; i < dataValue.length; i++)
			{
				float dataf = Float.parseFloat(dataValue[i][1]);
				float datao = Float.parseFloat(oldDataValue[i][1]);
				if (dataf >= datao)
				{
					TableCellRenderer cell = shoelive.table.getCellRenderer(i, 1);
					cell.getTableCellRendererComponent(shoelive.table, dataf + "", true, true, i, 1);
				}
			}
			shoelive.add(shoelive.scrollPane, null);
			
			shoelive.repaint();

			try
			{
			//	Thread.sleep(5000);
			}
			catch (Exception e)
			{
			}
		}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
	}
//	public static int val = 0;
	public String[][] convertDataValue(String[] data)
	{
		String[][] retdataValue = new String[data.length][5];

		int i = 0;
		try
		{
			for (i = 0; i < data.length; i++)
			{
				String s = data[i];
				s = s.substring(s.indexOf('>') + 1);
				String symbol = s.substring(0, s.indexOf('<')).trim();
				symbol = symbol.substring(0, symbol.length() - 2);
				s = s.substring(s.indexOf('>') + 1);
				s = s.substring(s.indexOf('>') + 1);
				String price = s.substring(0, s.indexOf('('));
				s = s.substring(s.indexOf('(') + 1);
				String perdiff = s.substring(0, s.indexOf(')'));
				String perdiff1 = perdiff.replace('%', ' ');
				perdiff1 = perdiff1.trim();
				float pricef = Float.parseFloat(price);
				float perdifff = Float.parseFloat(perdiff1);
				float oldprice = Utility.floatDataAtTwoPrecision((100 * pricef / (100 + perdifff)));
				retdataValue[i][0] = symbol;
				;
				retdataValue[i][1] = price;
				retdataValue[i][2] = perdiff;
				if (oldDataValue != null)
					retdataValue[i][3] = oldDataValue[i][1];
				retdataValue[i][4] = oldprice + "";
			}
		}
		catch (Exception e)
		{

			retdataValue = oldDataValue;

		}
		return retdataValue;
	}
	public static void main(String[] args)
	{
//		String[] temp = { "+<l c> ALLCARGOEQ</l> <b>815.55 (0.86 %)</b>" };
		//		GetLiveData livedata = new GetLiveData(temp);
	}
}

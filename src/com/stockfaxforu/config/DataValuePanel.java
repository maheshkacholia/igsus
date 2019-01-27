package com.stockfaxforu.config;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.w3c.dom.Element;

import com.stockfaxforu.formulabuilder.MessageDiaglog;
import com.stockfaxforu.swingcomponent.IGSLabel;
import com.stockfaxforu.util.Utility;

public class DataValuePanel extends JPanel implements ActionListener
{
	public String type,value,comment,property,defaultvalue;
	public Element element;
	public static String INTEGER = "INTEGER";
	public static String COLOR = "COLOR";
	public static String STRING = "STRING";
	public static String CURSOR = "CURSOR";
	public static String GRAPHTYPE = "GRAPHTYPE";
	public static String DATE = "DATE";
	public static String BOOLEAN = "BOOLEAN";
	public static String INTRADAYDATATYPE = "INTRADAYDATATYPE";
	
	
	
	public JTextField valueText;
	public JComboBox valueCombo;

	public JComboBox yearCombo;
	public JComboBox monthCombo;
	public JComboBox dateCombo;
	public JFrame mainFrame ; 
	
	public DataValuePanel(String property,JFrame frame)
	{
		super(null);
		mainFrame = frame;
		element = (Element)ConfigXMLManager.propertyHash.get(property);
		type = ConfigXMLManager.getDataForTag(element,ConfigXMLManager.TYPE);
		comment = ConfigXMLManager.getDataForTag(element,ConfigXMLManager.description);
		value = ConfigXMLManager.getDataForTag(element,ConfigXMLManager.VALUE);
		defaultvalue = ConfigXMLManager.getDataForTag(element,ConfigXMLManager.DEFAULT);
		int x= 10;
		int y= 10;
		this.property = property;
		IGSLabel label = new IGSLabel("Property Name");
		label.setFont(new Font("Arial",Font.PLAIN,13));
		label.setBounds(x, y,100,20);
		add(label);
		
		IGSLabel valuelable = new IGSLabel(property);
		valuelable.setFont(new Font("Arial",Font.PLAIN,13));

		valuelable.setBounds(x+125, y,200,20);
		add(valuelable);
		
		y = y + 35;
		
		
		IGSLabel label1 = new IGSLabel("Type ");
		label1.setFont(new Font("Arial",Font.PLAIN,13));

		label1.setBounds(x, y,100,20);
		add(label1);
		
		IGSLabel value1 = new IGSLabel(type);
		value1.setFont(new Font("Arial",Font.PLAIN,13));

		value1.setBounds(x+125, y,200,20);
		add(value1);

		y = y + 35;
		
		
		IGSLabel label6 = new IGSLabel("Default value ");
		label6.setFont(new Font("Arial",Font.PLAIN,13));

		label6.setBounds(x, y,100,20);
		add(label6);
		
		IGSLabel value6 = new IGSLabel(defaultvalue);
		value6.setFont(new Font("Arial",Font.PLAIN,13));

		value6.setBounds(x+125, y,400,20);
		add(value6);

		
		
		y = y + 35;
		IGSLabel label2 = new IGSLabel("Value ");
		label2.setBounds(x, y,100,20);
		label2.setFont(new Font("Arial",Font.PLAIN,13));

		add(label2);
		
		if(type.equalsIgnoreCase(INTEGER))
		{
			valueText = new JTextField(value);
			valueText.setBounds(x+125, y,50,20);
			add(valueText);

		}
		else if (type.equalsIgnoreCase(STRING))
		{
			valueText = new JTextField(value);
			valueText.setBounds(x+125, y,400,20);
			add(valueText);

		}
		else if (type.equalsIgnoreCase(COLOR))
		{
			valueCombo = new JComboBox(ConfigUtility.getColors());
			valueCombo.setBounds(x+125, y,150,20);
			valueCombo.setSelectedItem(value);
			add(valueCombo);

		}
		else if (type.equalsIgnoreCase(CURSOR))
		{
			valueCombo = new JComboBox(Utility.getCursortype());
			valueCombo.setSelectedItem(value);
			valueCombo.setBounds(x+125, y,150,20);
			add(valueCombo);

		}
		else if (type.equalsIgnoreCase(GRAPHTYPE))
		{
			String[] graph = {"LINEGRAPH","OHLCGRAPH","CANDLEGRAPH"};
			valueCombo = new JComboBox(graph);
			valueCombo.setSelectedItem(value);
			valueCombo.setBounds(x+125, y,150,20);
			add(valueCombo);

		}
		else if (type.equalsIgnoreCase(BOOLEAN))
		{
			String[] graph = {"TRUE","FALSE"};
			valueCombo = new JComboBox(graph);
			valueCombo.setSelectedItem(value);
			valueCombo.setBounds(x+125, y,150,20);
			add(valueCombo);

		}
		else if (type.equalsIgnoreCase(INTRADAYDATATYPE))
		{
			String[] graph = {"YAHOOSOURCE","SOURCE1","LIVESOURCE2"};
			valueCombo = new JComboBox(graph);
			valueCombo.setSelectedItem(value);
			valueCombo.setBounds(x+125, y,150,20);
			add(valueCombo);

		}
		else if (type.equalsIgnoreCase(DATE))
		{
			String[] dateStr = value.split("-");
			yearCombo = new JComboBox(Utility.getYears());
			yearCombo.setSelectedItem(dateStr[0]);
			yearCombo.setBounds(x+125, y,100,20);
			add(yearCombo);
			
			monthCombo = new JComboBox(Utility.getMonths());
			monthCombo.setSelectedItem(dateStr[1]);
			monthCombo.setBounds(x+125+110, y,75,20);
			add(monthCombo);
			
			dateCombo = new JComboBox(Utility.getDates());
			dateCombo.setSelectedItem(dateStr[2]);
			dateCombo.setBounds(x+125+110+85, y,75,20);
			add(dateCombo);
			
		}

		y = y + 50;
		
		IGSLabel desc = new IGSLabel("Description ");
		desc.setBounds(x, y,100,20);
		desc.setFont(new Font("Arial",Font.BOLD,13));
		add(desc);
		
		y = y + 25;
		
		JTextArea textarea = new JTextArea(comment,10,500);
		Font f = new Font("Arial",Font.PLAIN,12);
		
		textarea.setFont(f);
		textarea.setBounds(x, y,500, 100);
		textarea.disable();
		textarea.setBackground(this.getBackground());
		textarea.setDisabledTextColor(Color.BLACK);
		
		add(textarea);
		
		y = y + 125;
		JButton button = new JButton("Apply");
		button.setName("apply");
		button.setBounds(x, y,100,20);
		button.addActionListener(this);
		add(button);

		JButton save = new JButton("Save To File");
		save.setName("save");
		save.setBounds(x+150, y,200,20);
		save.addActionListener(this);
		add(save);

		JButton cancel = new JButton("Cancel");
		cancel.setName("cancel");
		cancel.setBounds(x+150+225, y,100,20);
		cancel.addActionListener(this);
		add(cancel);

		
	
	}
	public void doApply()
	{
		String value="";
		if(type.equalsIgnoreCase(INTEGER))
		{
			try
			{
				int i = Integer.parseInt(valueText.getText().trim());
				value=i+"";
			}
			catch(Exception e1)
			{
				MessageDiaglog msg = new MessageDiaglog("Please enter an integer value");
				return;
			}
		}
		else if(type.equalsIgnoreCase(STRING))
		{
			value = valueText.getText().trim();
		}
		else if (type.equalsIgnoreCase(INTRADAYDATATYPE) || type.equalsIgnoreCase(COLOR) || type.equalsIgnoreCase(BOOLEAN) || type.equalsIgnoreCase(CURSOR) || type.equalsIgnoreCase(GRAPHTYPE))
		{
			value = (String)valueCombo.getSelectedItem();
		}
		else if (type.equalsIgnoreCase(DATE))
		{
			value = (String)yearCombo.getSelectedItem() + "-" + monthCombo.getSelectedItem() + "-" + dateCombo.getSelectedItem();
		}
		
		element =  ConfigXMLManager.updateTag(element, value);
		ConfigUtility.loadProperty();
		ConfigXMLManager.propertyHash.put(property,element);
	}
	public void actionPerformed(ActionEvent e)
	{
		JButton button = (JButton)e.getSource();
		String name = button.getName();
		if(name.equalsIgnoreCase("save"))
		{
			doApply();
			try
			{
				ConfigXMLManager.writingXML();
			}
			catch (Exception e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}	
		if(name.equalsIgnoreCase("apply"))
		{
			doApply();	
		}
		if(name.equalsIgnoreCase("cancel"))
		{
			this.mainFrame.dispose();;	
		}

	}

}

/*
 * Created on May 7, 2009
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.parser;

import com.stockfaxforu.formulabuilder.MessageDiaglog;

/**
 * @author Mahesh
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class SemanticAnalyzer
{
	public static void doSemanticAnalasis(String code)
	{
		try
		{
			LexicalAnalyzer lex = new LexicalAnalyzer();
			String code1 = lex.findConstantStrings(code);
			lex.parseFile(code1);
			lex.checkSemantics(code1);
			MessageDiaglog msg = new MessageDiaglog(" No semantic error found ");
			
		}
		catch(Exception e)
		{
			MessageDiaglog msg = new MessageDiaglog(e.getMessage());
		}
	}

}

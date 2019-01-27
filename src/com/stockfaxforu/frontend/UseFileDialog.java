/*
 * Created on Apr 30, 2009
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.stockfaxforu.frontend;
import java.awt.FileDialog;
import java.awt.Frame;
public class UseFileDialog
{
	public String loadFile(Frame f, String title, String defDir, String fileType)
	{
		FileDialog fd = new FileDialog(f, title, FileDialog.LOAD);
		fd.setFile(fileType);
		fd.setDirectory(defDir);
		fd.setLocation(50, 50);
		fd.show();
		return fd.getFile();
	}
	public String saveFile(Frame f, String title, String defDir, String fileType)
	{
		FileDialog fd = new FileDialog(f, title, FileDialog.SAVE);
		fd.setFile(fileType);
		fd.setDirectory(defDir);
		fd.setLocation(50, 50);
		fd.show();
		return fd.getFile();
	}
	public static void main(String s[])
	{
		UseFileDialog ufd = new UseFileDialog();
		System.exit(0);
	}
}
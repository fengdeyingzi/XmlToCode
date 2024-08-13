package com.xl.util;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;

public class ClipBoard {
	
	
	public static void setText(String text){
		 Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();  
	        Transferable tText = new StringSelection(text);  
	        clip.setContents(tText, null);  
	}
	
	

}

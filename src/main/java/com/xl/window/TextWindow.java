package com.xl.window;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class TextWindow extends JFrame{
	
	JTextArea textArea;
	public TextWindow(){
		setTitle("返回信息");
		setSize(640, 480);
		JPanel layout_mainJPanel= new JPanel();
		setContentPane(layout_mainJPanel);
		this.textArea= new JTextArea();
		textArea.setLineWrap(true);        //激活自动换行功能 
		textArea.setWrapStyleWord(true);            // 激活断行不断字功能
		//JTextArea.setSize(600,0);
		//label.setMaximumSize(new Dimension(600, 0));
		JScrollPane scrollPane= new JScrollPane(textArea);
		setContentPane(scrollPane);
	}
	
	public void setText(String text) {
		this.textArea.setText(text);
	}

}

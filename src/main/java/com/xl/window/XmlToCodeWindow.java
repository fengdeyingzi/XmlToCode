package com.xl.window;

import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.TextArea;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.xl.util.ClipBoard;
import com.xl.util.DomParser;
import com.xl.util.FileUtils;
import com.xl.util.FindViewByIdParser;
import com.xl.util.FlutterDomParser;
import com.xl.util.SwiftDomParser;

public class XmlToCodeWindow extends JFrame {

	JTextArea editArea;
	JLabel label_info;
	JButton button_findViewById;
	JButton button_tojava;
	JButton button_tokotlin;
	JButton button_toswift;
	JButton button_toflutter;
	JScrollPane scrollPane;
	TextWindow textWindow;

	public XmlToCodeWindow() {
		int screen_w, screen_h;
		textWindow = new TextWindow();
		int array[] = new int[] { 1, 21 };
		System.out.println("数组" + array.toString());

		Toolkit toolkit = Toolkit.getDefaultToolkit();
		screen_w = (int) toolkit.getScreenSize().getWidth();
		screen_h = (int) toolkit.getScreenSize().getHeight();
		JPanel mainJPanel = new JPanel();
		setContentPane(mainJPanel);
		setLayout(new BoxLayout(mainJPanel, BoxLayout.Y_AXIS));
		Box box_v = Box.createVerticalBox();
		// box_v.setAlignmentX(0.5f);
		getContentPane().add(box_v);
		mainJPanel.setSize(640, 480);
		//

		editArea = new JTextArea();
		editArea.setColumns(20);
		editArea.setRows(10);

		label_info = new JLabel("https://github.com/fengdeyingzi/XmlToCode");
		scrollPane = new JScrollPane(editArea);
		// scrollPane.add(editArea);
		button_findViewById = new JButton("findViewById");
		button_findViewById.setAlignmentX(0.5f);
		button_tojava = new JButton("xml转java");
		// 设置对齐方式 不然会出问题
		button_tojava.setAlignmentX((float) 0.5);

		button_tokotlin = new JButton("xml转kt");
		button_tokotlin.setAlignmentX(0.5f);

		button_toflutter = new JButton("xml转flutter");
		button_toflutter.setAlignmentX(0.5f);

		button_toswift = new JButton("xml转swift");
		button_toswift.setAlignmentX(0.5f);

		label_info.setPreferredSize(new Dimension(640, 24));
		label_info.setMaximumSize(new Dimension(screen_w, 24));

		label_info.setForeground(new Color(70, 120, 240));
		box_v.add(label_info);
		label_info.setAlignmentX(0.5f);
		box_v.add(scrollPane);
		Box box_h = Box.createHorizontalBox();
		box_h.add(button_findViewById);
		box_h.add(Box.createRigidArea(new Dimension(10, 20)));
		box_h.add(button_tojava);
		box_h.add(Box.createRigidArea(new Dimension(10, 20)));
		box_h.add(button_tokotlin);
		box_h.add(Box.createRigidArea(new Dimension(10, 20)));
		box_h.add(button_toswift);
		box_h.add(Box.createRigidArea(new Dimension(10, 20)));
		box_h.add(button_toflutter);
		box_v.add(box_h);
		box_h.setPreferredSize(new Dimension(640, 30));
		mainJPanel.add(box_v);
		// 设置最大宽高 用于适应布局
		// button.setPreferredSize(new Dimension(400, 60));
		button_findViewById.setMaximumSize(new Dimension(screen_w, 60));
		button_tojava.setMaximumSize(new Dimension(screen_w, 60));
		button_tokotlin.setMaximumSize(new Dimension(screen_w, 60));
		button_toswift.setMaximumSize(new Dimension(screen_w, 60));
		button_toflutter.setMaximumSize(new Dimension(screen_w, 60));
		button_findViewById.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				FindViewByIdParser parser = new FindViewByIdParser();
				parser.setXmlText(editArea.getText());
				parser.parse();
				TextWindow window = new TextWindow();
				window.setLocation(getLocation().x+getWidth(), getLocation().y);
				window.setText(parser.toString());
				window.show();

			}
		});
		button_tojava.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				DomParser parser = new DomParser();
				parser.setXmlText(editArea.getText());
				parser.parseJava();
				TextWindow window = new TextWindow();
				window.setLocation(getLocation().x+getWidth(), getLocation().y);
				window.setText(parser.toString());
				window.show();
			}
		});

		button_tokotlin.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Toast.makeText(XmlToCodeWindow.this, "暂未实现").display();
				;
				System.out.println("暂未实现");

			}
		});

		button_toswift.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				SwiftDomParser parser = new SwiftDomParser();
				parser.setXmlText(editArea.getText());
				parser.parseSwift();
				TextWindow window = new TextWindow();
				
				window.setLocation(getLocation().x+getWidth(), getLocation().y);
				window.setText(parser.toString());
				window.show();
			}
		});

		button_toflutter.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				FlutterDomParser parser = new FlutterDomParser();
				parser.setXmlText(editArea.getText());
				parser.parseFlutter();
				TextWindow window = new TextWindow();
				window.setLocation(getLocation().x+getWidth(), getLocation().y);
				window.setText(parser.toString());
				window.show();
			}
		});
		label_info.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent mouseevent) {
				try {
					browseTo(label_info.getText());
				} catch (Exception e) {
					Toast.makeText(XmlToCodeWindow.this, "跳转失败").display();
					;
					e.printStackTrace();
				}

			}

			@Override
			public void mousePressed(MouseEvent mouseevent) {

			}

			@Override
			public void mouseReleased(MouseEvent mouseevent) {

			}

			@Override
			public void mouseEntered(MouseEvent mouseevent) {

			}

			@Override
			public void mouseExited(MouseEvent mouseevent) {

			}
		});

		setSize(new Dimension(640, 480));
		setLocation((screen_w - 640) / 2, (screen_h - 480) / 2);
		textWindow.setLocation((screen_w - 640) / 2, (screen_h - 480) / 2);
		setTitle("xml布局转代码v1.1 - 风的影子");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		dragFile(editArea, new OnDragFile() {

			@Override
			public void onDragFile(List<File> list_file) {
				File file = list_file.get(0);
				try {
					String text = FileUtils.read(file, "UTF-8");
					editArea.setText(text);
				} catch (IOException e) {

					e.printStackTrace();
				}

			}
		});
		editArea.setText("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n"
				+ "<LinearLayout xmlns:android=\"http://schemas.android.com/apk/res/android\"\n"
				+ "    android:orientation=\"vertical\" android:layout_width=\"match_parent\"\n"
				+ "    android:gravity=\"center\"\n" + "    android:layout_height=\"match_parent\">\n" + "\n"
				+ "    <TextView\n" + "        android:id=\"@+id/text_info\"\n"
				+ "        android:layout_width=\"match_parent\"\n" + "        android:layout_height=\"wrap_content\"\n"
				+ "        android:textSize=\"18sp\"\n" + "        android:gravity=\"center\"\n"
				+ "        android:text=\"xml转代码\\nhttps://github.com/fengdeyingzi/XmlToCode\"\n"
				+ "        android:autoLink=\"web\"\n" + "        android:padding=\"32dp\"\n" + "        />\n"
				+ "    <TextView\n" + "        android:id=\"@+id/text_author\"\n"
				+ "        android:layout_width=\"wrap_content\"\n" + "        android:layout_height=\"wrap_content\"\n"
				+ "        android:textSize=\"16sp\"\n" + "        android:text=\"风的影子 制作\"\n" + "        />\n" + "\n"
				+ "</LinearLayout>");
		// editArea.setText("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n"
		// +"<LinearLayout
		// xmlns:android=\"http://schemas.android.com/apk/res/android\"\n"
		// +" android:orientation=\"horizontal\"
		// android:layout_width=\"match_parent\"\n"
		// +" android:layout_height=\"match_parent\">\n"
		// +" <View\n"
		// +" android:layout_width=\"30dp\"\n"
		// +" android:background=\"#f0a000\"\n"
		// +" android:layout_gravity=\"center|top\"\n"
		// +" android:layout_height=\"60dp\"/>\n"
		// +" <View\n"
		// +" android:background=\"#f0f0f0\"\n"
		// +" android:layout_width=\"60dp\"\n"
		// +" android:layout_gravity=\"center\"\n"
		// +" android:layout_height=\"60dp\"/>\n"
		// +" <View\n"
		// +" android:background=\"#70af90\"\n"
		// +" android:layout_width=\"60dp\"\n"
		// +" android:layout_gravity=\"right|center\"\n"
		// +" android:layout_height=\"60dp\"/>\n"
		// +"</LinearLayout>");
		// setVisible(true);
	}

	private void browseTo(String url) throws Exception {
		Desktop desktop = Desktop.getDesktop();

		if ((Desktop.isDesktopSupported()) && (desktop.isSupported(Desktop.Action.BROWSE))) {
			URI uri = new URI(url);
			System.out.println("跳转：" + url);
			desktop.browse(uri);
		} else {
			System.err.println("跳转失败：" + Desktop.isDesktopSupported() + desktop.isSupported(Desktop.Action.BROWSE));
		}
	}

	public void dragFile(Component c, OnDragFile onDragFile) {
		new DropTarget(c, DnDConstants.ACTION_COPY_OR_MOVE, new DropTargetAdapter() {
			@Override
			public void drop(DropTargetDropEvent dtde) {
				try {

					if (dtde.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
						dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
						List<File> list = (List<File>) (dtde.getTransferable()
								.getTransferData(DataFlavor.javaFileListFlavor));
						if (onDragFile != null) {
							onDragFile.onDragFile(list);
						}
						// String temp="";
						// for(File file:list)
						// {
						// temp+=file.getAbsolutePath()+";\n";
						// JOptionPane.showMessageDialog(null, temp);
						// dtde.dropComplete(true);
						//
						// }

					}

					else {

						dtde.rejectDrop();
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		});

	}

	public interface OnDragFile {
		public void onDragFile(List<File> list_file);
	}
}

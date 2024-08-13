package com.xl.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import com.xl.game.math.Str;
import com.xl.util.flutter.FlutterWidget;

public class FindViewByIdParser {
	String text;
	StringBuffer buf_code;
	StringBuffer buf_var;

	public void setXmlText(String text) {
		this.text = text;
	}

	public void parse() {
		buf_code = new StringBuffer();
		buf_var = new StringBuffer();
		DocumentBuilderFactory factory = null;
		DocumentBuilder builder = null;
		Document document = null;
		InputStream inputStream = null;
		factory = DocumentBuilderFactory.newInstance();
		try {
			// 惯例 取得document文件实例的过程
			builder = factory.newDocumentBuilder();
			inputStream = new ByteArrayInputStream(text.getBytes("UTF-8"));
			;// 以工程文件下assets文件夹为根目录
			document = builder.parse(inputStream);

			// 取得根Element 以此列出所有节点NodeList
			Element root = document.getDocumentElement();
			if (root instanceof Node) {
				System.out.println("========");

				String layout_root = "layout_root";
				String className = root.getNodeName();
				printFindCode(new FindClass(), root, 1);

				return;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 遍历element输出findViewById
	void printFindCode(FindClass findClass, Element element, int leve) {

		String name = element.getNodeName();
		if (name.indexOf(".") >= 0) {
			findClass.setName(name.substring(Str.strrchr(name, '.') + 1));
		} else {
			findClass.setName(name);
		}
		findClass.setLeve(leve);

		NamedNodeMap map = element.getAttributes();
		for (int ii = 0; ii < map.getLength(); ii++) {
			String key = map.item(ii).getNodeName();
			String value = element.getAttribute(key);
			if (key.equals("android:id")) {

				if (value.indexOf("/") != -1) {
					value = value.substring(value.indexOf("/") + 1);
				}
				System.out.println("    " + name + " " + value + " = findViewById(R.id." + value + ");");
				buf_var.append("    " + name + " " + value + ";" + "\n");
				buf_code.append("    " + value + " = findViewById(R.id." + value + ");" + "\n");
			}
		}

		ArrayList<Element> nodelist = XmlUtil.getChildElement(element);
		for (int n = 0; n < nodelist.size(); n++) {
			Element nodeitem = nodelist.get(n);

			printFindCode(findClass, nodeitem, leve + 1);
		}

	}

	public String toString() {
		if (buf_code != null && buf_var != null) {
			return buf_var.toString() + "\n" + buf_code.toString();
		}

		return "";
	}

	public class FindClass {
		String name;
		int leve;

		public void setName(String name) {
			this.name = name;
		}

		public String getName() {
			return this.name;
		}

		public void setLeve(int leve) {
			this.leve = leve;
		}
	}
}

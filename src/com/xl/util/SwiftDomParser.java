package com.xl.util;


import java.awt.Paint;
import java.awt.event.KeyEvent;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.plaf.basic.BasicBorders.MarginBorder;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.UserDataHandler;

import com.xl.game.math.Str;



public class SwiftDomParser {

	StringBuffer buf_code;

	String text = 
			"<?xml version=\"1.0\" encoding=\"utf-8\"?>\n"
			+"<LinearLayout xmlns:android=\"http://schemas.android.com/apk/res/android\"\n"
			+"    android:layout_width=\"match_parent\"\n"
			+"    android:layout_height=\"match_parent\"\n"
			+"    android:orientation=\"vertical\">\n"
			+"    <LinearLayout xmlns:android=\"http://schemas.android.com/apk/res/android\"\n"
			+"        android:orientation=\"vertical\" android:layout_width=\"match_parent\"\n"
			+"        android:background=\"@color/colorPrimary\"\n"
			+"        android:layout_height=\"wrap_content\">\n"
			+"        <FrameLayout\n"
			+"            android:layout_width=\"match_parent\"\n"
			+"            android:layout_height=\"wrap_content\">\n"
			+"            <LinearLayout\n"
			+"                android:layout_width=\"wrap_content\"\n"
			+"                android:paddingLeft=\"8dp\"\n"
			+"                android:paddingRight=\"8dp\"\n"
			+"                android:gravity=\"center|left\"\n"
			+"                android:orientation=\"horizontal\"\n"
			+"                android:layout_height=\"48dp\">\n"
			+"                <LinearLayout\n"
			+"                    android:id=\"@+id/layout_back\"\n"
			+"                    android:orientation=\"horizontal\"\n"
			+"                    android:layout_width=\"wrap_content\"\n"
			+"                    android:layout_height=\"wrap_content\">\n"
			+"                    <ImageView\n"
			+"                        android:id=\"@+id/toolbar_backImg\"\n"
			+"                        android:src=\"@drawable/toolbar_back\"\n"
			+"                        android:layout_width=\"22dp\"\n"
			+"                        android:layout_height=\"22dp\"/>\n"
			+"                    <TextView\n"
			+"                        android:paddingLeft=\"8dp\"\n"
			+"                        android:id=\"@+id/toolbar_backText\"\n"
			+"                        android:textSize=\"@dimen/font_h3\"\n"
			+"                        android:textColor=\"@color/white\"\n"
			+"                        android:layout_width=\"wrap_content\"\n"
			+"                        android:layout_height=\"wrap_content\"/>\n"
			+"\n"
			+"                </LinearLayout>\n"
			+"\n"
			+"                <View\n"
			+"                    android:layout_weight=\"1.0\"\n"
			+"                    android:layout_width=\"wrap_content\"\n"
			+"                    android:layout_height=\"wrap_content\"/>\n"
			+"\n"
			+"\n"
			+"            </LinearLayout>\n"
			+"\n"
			+"            <TextView\n"
			+"                android:text=\"标题\"\n"
			+"                android:textSize=\"@dimen/font_h2\"\n"
			+"                android:textColor=\"@color/white\"\n"
			+"                android:singleLine=\"true\"\n"
			+"                android:ellipsize=\"end\"\n"
			+"                android:maxWidth=\"240dp\"\n"
			+"                android:gravity=\"center\"\n"
			+"                android:layout_gravity=\"center\"\n"
			+"                android:id=\"@+id/toolbar_title\"\n"
			+"                android:layout_width=\"280dp\"\n"
			+"                android:layout_height=\"wrap_content\" />\n"
			+"            <ImageView\n"
			+"                android:id=\"@+id/img_share\"\n"
			+"                android:paddingRight=\"8dp\"\n"
			+"\n"
			+"                android:layout_gravity=\"end|center\"\n"
			+"                android:src=\"@drawable/ic_share\"\n"
			+"                android:layout_width=\"32dip\"\n"
			+"                android:layout_height=\"32dip\"/>\n"
			+"\n"
			+"        </FrameLayout>\n"
			+"\n"
			+"\n"
			+"\n"
			+"    </LinearLayout>\n"
			+"    <LinearLayout\n"
			+"        android:layout_weight=\"1.0\"\n"
			+"        android:layout_width=\"match_parent\"\n"
			+"        android:layout_height=\"match_parent\">\n"
			+"\n"
			+"        <com.ocketautoparts.qimopei.view.XWebView\n"
			+"            android:id=\"@+id/webview\"\n"
			+"            android:layout_width=\"match_parent\"\n"
			+"            android:layout_height=\"match_parent\"\n"
			+"            android:layout_weight=\"1.0\" />\n"
			+"\n"
			+"    </LinearLayout>\n"
			+"\n"
			+"    <LinearLayout\n"
			+"        android:layout_width=\"match_parent\"\n"
			+"        android:layout_height=\"50dip\"\n"
			+"        android:gravity=\"center\"\n"
			+"        android:elevation=\"3.0dp\"\n"
			+"        android:background=\"@color/white\"\n"
			+"        android:orientation=\"horizontal\">\n"
			+"\n"
			+"        <LinearLayout\n"
			+"            android:id=\"@+id/layout_home\"\n"
			+"            android:background=\"@color/white\"\n"
			+"            android:layout_width=\"match_parent\"\n"
			+"            android:layout_height=\"match_parent\"\n"
			+"            android:gravity=\"center\"\n"
			+"            android:layout_weight=\"1.0\">\n"
			+"\n"
			+"            <ImageView\n"
			+"                android:layout_width=\"28dip\"\n"
			+"                android:layout_height=\"28dip\"\n"
			+"                android:tint=\"@color/colorAccent\"\n"
			+"                android:src=\"@drawable/ic_home\" />\n"
			+"\n"
			+"            <TextView\n"
			+"                android:paddingLeft=\"15dp\"\n"
			+"                android:textColor=\"@color/colorAccent\"\n"
			+"                android:layout_width=\"wrap_content\"\n"
			+"                android:textSize=\"@dimen/font_h3\"\n"
			+"                android:layout_height=\"wrap_content\"\n"
			+"                android:text=\"查看主页\" />\n"
			+"\n"
			+"        </LinearLayout>\n"
			+"\n"
			+"        <LinearLayout\n"
			+"            android:id=\"@+id/layout_server\"\n"
			+"            android:background=\"@color/colorAccent\"\n"
			+"            android:gravity=\"center\"\n"
			+"            android:layout_width=\"match_parent\"\n"
			+"            android:layout_height=\"match_parent\"\n"
			+"\n"
			+"            android:layout_weight=\"1.0\">\n"
			+"\n"
			+"            <ImageView\n"
			+"                android:tint=\"@color/white\"\n"
			+"                android:layout_width=\"28dip\"\n"
			+"                android:layout_height=\"28dip\"\n"
			+"                android:src=\"@drawable/home_kefu\" />\n"
			+"\n"
			+"            <TextView\n"
			+"                android:textColor=\"@color/white\"\n"
			+"                android:paddingLeft=\"15dp\"\n"
			+"                android:layout_width=\"wrap_content\"\n"
			+"                android:layout_height=\"wrap_content\"\n"
			+"                android:textSize=\"@dimen/font_h3\"\n"
			+"                android:text=\"立即咨询\">\n"
			+"\n"
			+"            </TextView>\n"
			+"\n"
			+"        </LinearLayout>\n"
			+"    </LinearLayout>\n"
			+"\n"
			+"</LinearLayout>\n";



	public SwiftDomParser(){
		buf_code = new StringBuffer();
	}

	public void setXmlText(String text){
		this.text = text;
	}


	public void parseJava() {
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
				setNodesId(null,root);
//				printfNodes(root);
				String layout_root = "layout_root";
				String className = root.getNodeName();
				printAndroidCode(className, layout_root, root);
				return;
			}
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void parseSwift() {
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
				setNodesId(null,root); //初始化
//				printfNodes(root);
				String layout_root = "layout_root";
				String className = root.getNodeName();
				buf_code = new StringBuffer();
				String swiftClassName = getLayoutName(className);
				if(className.equals("LinearLayout")){
					String orientation = root.getAttribute("android:orientation");
					if(orientation.equals("vertical")){
						orientation = ".vert";
					}
					else if(orientation.equals("horizontal")){
						orientation = ".horz";
					}
					else{
						orientation = ".horz";
					}
					
					buf_code.append("    var "+layout_root+":"+swiftClassName + " = "+swiftClassName+"("+orientation+")\n");
				}
				else
					buf_code.append("    var "+layout_root+":"+swiftClassName + " = "+swiftClassName+"("+")\n");
				
				String layout_width = root.getAttribute("android:layout_width");
				String layout_height = root.getAttribute("android:layout_height");
				
				
				if (layout_width.equals("match_parent") || layout_width.equals("fill_parent")) {
					layout_width = ".fill";
				}
				else if (layout_width.equals("wrap_content")) {
					layout_width = ".wrap";
				}
				else {
					layout_width = XmlUtil.getSize(layout_width);
				}
				
				if (layout_height.equals("match_parent") || layout_height.equals("fill_parent")) {
					layout_height = ".fill";
				}
				else if (layout_height.equals("wrap_content")) {
					layout_height = ".wrap";
				}
				else {
					layout_height = XmlUtil.getSize(layout_height);
				}
				buf_code.append("    "+layout_root+".tg_width ~= "+layout_width+"\n");
				buf_code.append("    "+layout_root+".tg_height ~= "+layout_height+"\n");
				
				printiOSCode(className, layout_root, root);
				return;
			}
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	int count = 0;
	
	

	// TODO: 遍历node设置id
	void setNodesId(Element eleParent, Node node) {
		NodeList nodelist = node.getChildNodes();
		// System.out.println("node name=" + node.getNodeName() + " value=" +
		// node.getNodeValue() + " type=" + node.getNodeType());
		if (node.getNodeType() == 1) {
			printfAttributes(node);
			NamedNodeMap map = node.getAttributes();
			
			// node.setNamedItem(nodetag);
			if (node instanceof Element) {
				System.out.println("is Element");
				Element element = (Element) node;
				String id = node.getNodeName();
				if(id.indexOf('.')>0){
					id = id.substring(Str.strrchr(id, '.'));
				}
				String view_name = id.toLowerCase() + "_" + count;
				String key_id = element.getAttribute("android:id");
				if(key_id.length()!=0){
					if(key_id.startsWith("@id/") || key_id.startsWith("@+id/")){
						view_name = key_id.substring(key_id.indexOf('/')+1);
					}
				}
				((Element) node).setAttribute("id", id.toLowerCase() + "_" + count);
				element.setAttribute("name", view_name);
				((Element) node).setAttribute("layoutparams", "layoutParams_" + count);
				count++;
				
				String layout_weight = element.getAttribute("android:layout_weight");
				if(layout_weight.length()!=0){
					
					String orientation = eleParent.getAttribute("android:orientation");
					Float X = 100f;
					//获取父控件之下的所有子控件
					NodeList views = eleParent.getChildNodes();
					ArrayList<Float> list_weight = new ArrayList<Float>();
					ArrayList<String> list_width = new ArrayList<String>();
					ArrayList<String> list_height = new ArrayList<String>();
					int type = 0; //根据控件的 layout_width 和 layout_height 判断权重的计算方式
					if(orientation.length()!=0){
						if(orientation.equals("horizontal")){
							System.out.println("horizontal");
//							element.setAttribute("width_percentage", );
							int fill_index = -1; //第一次出现fill的控件index
							int ele_index = 0;
							System.out.println("======== "+views.getLength());
							for(int ii=0;ii<views.getLength();ii++){
								if(views.item(ii) instanceof Element){
									
								Element item = (Element) views.item(ii);
								String layout_width = item.getAttribute("android:layout_width");
								String layout_height = item.getAttribute("android:layout_height");
								list_width.add(layout_width);
								System.out.println("========= add 1");
								list_height.add(layout_height);
								String weight = item.getAttribute("android:layout_weight");
								list_weight.add(XmlUtil.getFloat(weight));
								if(weight.equals("match_parent") || weight.equals("fill_parent")){
									fill_index = ii;
								}
								ele_index++;
								}
								else{
									System.out.println("转换Element失败 "+views.getLength());
									list_width.clear();
									list_height.clear();
									list_weight.clear();
									for(int n=0;n<views.getLength();n++){
										Node nodeitem = views.item(n);
										if(nodeitem.getNodeType() != 1){
											NodeList mapitem = nodeitem.getChildNodes();
											for(int nn=0;nn<mapitem.getLength();nn++){
												nodeitem = mapitem.item(nn);
												if(nodeitem.getNodeType()==1){
													Element item = (Element) nodeitem;
													String layout_width = item.getAttribute("android:layout_width");
													String layout_height = item.getAttribute("android:layout_height");
													System.out.println("chile layout_width = "+layout_width);
													list_width.add(layout_width);
													System.out.println("======= add2 ");
													list_height.add(layout_height);
													String weight = item.getAttribute("android:layout_weight");
													list_weight.add(XmlUtil.getFloat(weight));
													if((layout_width.equals("match_parent") || layout_width.equals("fill_parent")) && fill_index<0){
														fill_index = ele_index;
													}
												}
												else{
													System.out.println("item child "+nodeitem.getNodeName());
												}
											}
										}
										else{
											
											System.out.println("view child "+nodeitem.getNodeName());
											
											Element item = (Element) nodeitem;
											String layout_width = item.getAttribute("android:layout_width");
											String layout_height = item.getAttribute("android:layout_height");
											System.out.println("chile layout_width = "+layout_width);
											list_width.add(layout_width);
											System.out.println("========= add3 "+views.getLength());
											list_height.add(layout_height);
											String weight = item.getAttribute("android:layout_weight");
											list_weight.add(XmlUtil.getFloat(weight));
											if((layout_width.equals("match_parent") || layout_width.equals("fill_parent")) && fill_index<0){
												fill_index = ele_index;
											}
											ele_index++;
										}
										
									}
								}
								
							}
							int zero_num=0;
							int fill_num=0;
							
							Float weights = 0f;
							for(Float we:list_weight){
								weights += we;
							}
							System.out.println("list_width = "+list_width);
							System.out.println("list_height = "+list_height);
							System.out.println("fill_num = "+fill_num+" zero_num = "+zero_num);
							for(String width:list_width){
								if(width.equals("0dp") || width.equals("0px") || width.equals("0dip")){
									zero_num++;
								}
								if(width.equals("match_parent") || width.equals("fill_parent")){
									fill_num++;
								}
							}
							if(zero_num == list_width.size()){
								type = 0;
//								第一个控件的宽度为 0+(1/(1+2+2))X=X/5
//								第二个控件的宽度为 0+(2/(1+2+2))X=2X/5
//								第三个控件的宽度为 0+(2/(1+2+2))*X=2X/5
								ArrayList<Element> list_child = XmlUtil.getChildElement(eleParent);
								for(int ii=0;ii<list_child.size();ii++){
									
									Element item = list_child.get(ii);
									Float weight = list_weight.get(ii);
									item.setAttribute("width_percentage", ""+X/weights*weight );
									System.out.println(" width_percentage "+item.getNodeName()+" "+X/weights*weight);
								}
								
							}
							else if(fill_num == list_width.size()){
								type = 1;
//								第一个控件的宽度为 X+(1/(1+2+2))(-2X)=3X/5
//								第二个控件的宽度为 X+(2/(1+2+2))(-2X)=X/5
//								第三个控件的宽度为 X+(2/(1+2+2))*(-2X)=X/5
								ArrayList<Element> list_child = XmlUtil.getChildElement(eleParent);
								for(int ii=0;ii<list_child.size();ii++){
									
									Element item = list_child.get(ii);
									Float weight = list_weight.get(ii);
									item.setAttribute("width_percentage", ""+(X+weight/weights*(-2*X)));
									System.out.println(" width_percentage "+item.getNodeName()+" "+(X+weight/weights*(-2*X)));
								}
							}
							else if(fill_num > 0 && zero_num > 0){
								type = 2;
//								第一个控件的宽度为 X+(1/(1+2+2))0=X
//								第二个控件的宽度为 0+(2/(1+2+2))0=0
//								第三个控件的宽度为 0+(2/(1+2+2))*0=0
								System.out.println(">>>>>>>>>>>　算法3 "+fill_index);
								if(fill_index>=0){
									ArrayList<Element> list_child = XmlUtil.getChildElement(eleParent);
									for(int ii=0;ii<list_child.size();ii++){
										
										Element item = list_child.get(ii);
										if(ii==fill_index){
											item.setAttribute("width_percentage", ""+X);
										}
										else{
											item.setAttribute("width_percentage", "0");
										}
										System.out.println(" width_percentage "+item.getNodeName()+" ");
									}
								}
								else{
									System.out.println(">>>>>>>>>> fill_index<0");
								}
							}
							else{
								
							}
							
							
						}
						else if(orientation.equals("vertical")){
//							element.setAttribute("height_percentage", value);
							int fill_index = -1; //第一次出现fill的控件index
							for(int ii=0;ii<views.getLength();ii++){
								
								Element item = (Element) views.item(ii);
								String layout_width = item.getAttribute("android:layout_width");
								String layout_height = item.getAttribute("android:layout_height");
								list_width.add(layout_width);
								list_height.add(layout_height);
								String weight = item.getAttribute("android:layout_weight");
								list_weight.add(XmlUtil.getFloat(weight));
								if(weight.equals("match_parent") || weight.equals("fill_parent")){
									fill_index = ii;
								}
								
							}
							int zero_num=0;
							int fill_num=0;
							
							Float weights = 0f;
							for(Float we:list_weight){
								weights += we;
							}
							for(String height:list_height){
								if(height.equals("0dp") || height.equals("0px") || height.equals("0dip")){
									zero_num++;
								}
								if(height.equals("match_parent") || height.equals("fill_parent")){
									fill_num++;
								}
							}
							if(zero_num == list_height.size()){
								type = 0;
//								第一个控件的宽度为 0+(1/(1+2+2))X=X/5
//								第二个控件的宽度为 0+(2/(1+2+2))X=2X/5
//								第三个控件的宽度为 0+(2/(1+2+2))*X=2X/5
								for(int ii=0;ii<views.getLength();ii++){
									
									Element item = (Element) views.item(ii);
									Float weight = list_weight.get(ii);
									item.setAttribute("height_percentage", ""+X/weights*weight );
									
								}
								
							}
							else if(fill_num == list_height.size()){
								type = 1;
//								第一个控件的宽度为 X+(1/(1+2+2))(-2X)=3X/5
//								第二个控件的宽度为 X+(2/(1+2+2))(-2X)=X/5
//								第三个控件的宽度为 X+(2/(1+2+2))*(-2X)=X/5
								for(int ii=0;ii<views.getLength();ii++){
									
									Element item = (Element) views.item(ii);
									Float weight = list_weight.get(ii);
									item.setAttribute("height_percentage", ""+X+weight/weights*(-2*X));
								}
							}
							else if(fill_num > 0 && zero_num > 0){
								type = 2;
//								第一个控件的宽度为 X+(1/(1+2+2))0=X
//								第二个控件的宽度为 0+(2/(1+2+2))0=0
//								第三个控件的宽度为 0+(2/(1+2+2))*0=0
								if(fill_index>=0){
									for(int ii=0;ii<views.getLength();ii++){
										
										Element item = (Element) views.item(ii);
										if(ii==fill_index){
											item.setAttribute("height_percentage", ""+X);
										}
										else{
											item.setAttribute("height_percentage", "0");
										}
									}
								}
							}
						}
					}
				}
			}
		}
		for (int n = 0; n < nodelist.getLength(); n++) {
			Node nodeitem = nodelist.item(n);
			if(node instanceof Element)
			setNodesId((Element)node, nodeitem);
			else
				setNodesId(eleParent, nodeitem);
		}
	}

	// 递归输出nodes
	void printfNodes(Node node) {
		NodeList nodelist = node.getChildNodes();
		System.out.println(
				"node name=" + node.getNodeName() + " value=" + node.getNodeValue() + " type=" + node.getNodeType());
		if (node.getNodeType() == 1) {
			printfAttributes(node);
		}
		for (int n = 0; n < nodelist.getLength(); n++) {
			Node nodeitem = nodelist.item(n);
			System.out.println("node item = " + nodeitem.getNodeName() + " value=" + nodeitem.getNodeValue() + " type="
					+ nodeitem.getNodeType() + " ");
			printfNodes(nodeitem);
		}
	}

	// 输出一个node的所有标签 android:layout_width android:layout_height等
	void printfAttributes(Node node) {
		NamedNodeMap map = node.getAttributes();
		for (int ii = 0; ii < map.getLength(); ii++) {
			System.out.println("name = " + map.item(ii).getNodeName() + " value=" + map.item(ii).getNodeValue()
					+ " type=" + map.item(ii).getNodeType());
		}
	}

// 输出android java代码
	void printAndroidCode(String className, String name, Node node) {
		NodeList nodelist = node.getChildNodes();
		// System.out.println("node name=" + node.getNodeName() + " value=" +
		// node.getNodeValue() + " type=" + node.getNodeType());
		if (node.getNodeType() == 1) {
			// 输出代码
			Element element = (Element) node;
			System.out.println("    " + element.getNodeName() + " " + element.getAttribute("name") + " = new "
					+ element.getNodeName() + "(context);");
			String layout_name = element.getAttribute("name");
			//优先处理margin top layout_width layout_height layout_weight
			String layout_width = element.getAttribute("android:layout_width");
			String layout_height = element.getAttribute("android:layout_height");
			String layout_weight = element.getAttribute("android:layout_weight");
			if (layout_width.equals("match_parent") || layout_width.equals("fill_parent")) {
				layout_width = "ViewGroup.LayoutParams.MATCH_PARENT";
			}
			else if (layout_width.equals("wrap_content")) {
				layout_width = "ViewGroup.LayoutParams.WRAP_CONTENT";
			}
			else {
				layout_width = XmlUtil.getSize(layout_width);
			}
			
			if (layout_height.equals("match_parent") || layout_height.equals("fill_parent")) {
				layout_height = "ViewGroup.LayoutParams.MATCH_PARENT";
			}
			else if (layout_height.equals("wrap_content")) {
				layout_height = "ViewGroup.LayoutParams.WRAP_CONTENT";
			}
			else {
				layout_height = XmlUtil.getSize(layout_height);
			}
			if (className.equals("LinearLayout")) {
				System.out.println("    "+className+".LayoutParams " + element.getAttribute("layoutparams")
				+ " = new "+className+".LayoutParams(" + layout_width + "," + layout_height + ");");
			}
			else if(className.equals("FrameLayout")){
				System.out.println("    "+className+".LayoutParams " + element.getAttribute("layoutparams")
				+ " = new "+className+".LayoutParams(" + layout_width + "," + layout_height + ");");
			}
			else if(className.equals("TableLayout")){
				System.out.println("    "+className+".LayoutParams " + element.getAttribute("layoutparams")
				+ " = new "+className+".LayoutParams(" + layout_width + "," + layout_height + ");");
			}
			else if(className.equals("GridLayout")){
				System.out.println("    "+className+".LayoutParams " + element.getAttribute("layoutparams")
				+ " = new "+className+".LayoutParams(" + layout_width + "," + layout_height + ");");
			}
			else if(className.equals("RelativeLayout")){
				System.out.println("    "+className+".LayoutParams " + element.getAttribute("layoutparams")
				+ " = new "+className+".LayoutParams(" + layout_width + "," + layout_height + ");");
			}
			else
				System.out.println("    ViewGroup.LayoutParams " + element.getAttribute("layoutparams")
						+ " = new ViewGroup.LayoutParams(" + layout_width + "," + layout_height + ");");
			if (layout_weight.length() != 0) {
				System.out.println("    " + element.getAttribute("layoutparams") + ".weight = "
						+ element.getAttribute("android:layout_weight") + "f;");
			}
			String margin = element.getAttribute("android:layout_margin");
			String margin_top = element.getAttribute("android:layout_marginTop");
			String margin_bottom = element.getAttribute("android:layout_marginBottom");
			String margin_left = element.getAttribute("android:layout_marginLeft");
			String margin_right = element.getAttribute("android:layout_marginRight");
			if(margin.length()!=0){
				margin_top = margin;
				margin_left = margin;
				margin_right = margin;
				margin_bottom = margin;
			}
			if(margin_left.length()!=0 || margin_top.length()!=0 || margin_right.length()!=0 || margin_bottom.length()!=0){
				margin_left = XmlUtil.getSize(margin_left);
				margin_top = XmlUtil.getSize(margin_top);
				margin_right = XmlUtil.getSize(margin_right);
				margin_bottom = XmlUtil.getSize(margin_bottom);
				System.out.println("    "+element.getAttribute("layoutparams")+ ".setMargins("+margin_left+", "+margin_top+", "+margin_right+", "+margin_bottom+");");
			}
			
			String padding = element.getAttribute("android:padding");
			String padding_left = element.getAttribute("android:paddingLeft");
			String padding_right = element.getAttribute("android:paddingRight");
			String padding_top = element.getAttribute("android:paddingTop");
			String padding_bottom = element.getAttribute("android:paddingBottom");
			if(padding.length()!=0){
				padding_left = padding;
				padding_top = padding;
				padding_right = padding;
				padding_bottom = padding;
			}
			if(padding_left.length()!=0 || padding_top.length()!=0 || padding_bottom.length()!=0 || padding_right.length()!=0){
				padding_left = XmlUtil.getSize(padding_left);
				padding_top = XmlUtil.getSize(padding_top);
				padding_right = XmlUtil.getSize(padding_right);
				padding_bottom = XmlUtil.getSize(padding_bottom);
				System.out.println("    "+layout_name+ ".setPadding("+padding_left+", "+padding_top+", "+padding_right+", "+padding_bottom+");");
	
			}
			
			NamedNodeMap map = node.getAttributes();
			for (int ii = 0; ii < map.getLength(); ii++) {
				String key = map.item(ii).getNodeName();
				String value = element.getAttribute(key);
				
				if (key.equals("android:layout_width")) {
					
					
					
					
					
					

				}
				else if(key.equals("android:layout_margin")){
				}
				else if(key.equals("android:layout_marginTop")){
					
				}
				else if(key.equals("android:layout_marginBottom")){
					
				}
				else if(key.equals("android:layout_marginLeft")){
					
				}
				else if(key.equals("android:layout_marginRight")){
					
				}
				else if (key.equals("android:layout_height")) {

				} else if (key.equals("android:theme")) {
					System.out.println("    " + layout_name + ".setTheme(" + value + ");");
				} else if (key.equals("android:background")) {
					if (value.startsWith("#")) {
						System.out.println("    " + layout_name + ".setBackgroundColor(" + XmlUtil.getColorHex(value) + ");");
					} else if (value.startsWith("@color/")) {
						value = "R.color."+value.substring(7);
						System.out.println("    " + layout_name + ".setBackgroundColor(getResources().getColor(" + value + "));");
					} else if (value.startsWith("@drawable/")) {
						value = "R.drawable."+value.substring(10);
						System.out.println("    " + layout_name + ".setBackground(getResources().getDrawable(" + value + "));");

					} else
						System.out.println("    " + layout_name + ".setBackground(" + value + ");");
				} else if (key.equals("android:orientation")) {
					if (value.equals("vertical")) {
						value = "LinearLayout.VERTICAL";
					} else if (value.equals("horizontal")) {
						value = "LinearLayout.HORIZONTAL";
					}
					System.out.println("    " + layout_name + ".setOrientation(" + value + ");");
				} else if (key.equals("android:src")) {
					if(value.startsWith("@drawable/")){
						value = "R.drawable."+value.substring(10);
					}
					else if(value.startsWith("@mipmap/")){
						value = "R.mipmap."+value.substring(8);
					}
					System.out.println("    " + layout_name + ".setImageDrawable(getDrawable(" + value + "));");
				} else if (key.equals("android:text")) {
					if(value.startsWith("@string/")){
						value = "R.string."+value.substring(8);
					}
					else{
						value = "\""+value+"\"";
					}
					System.out.println("    " + layout_name + ".setText(" + value + ");");
				} else if (key.equals("android:textColor")) {
					if (value.startsWith("#")) {
						System.out.println("    " + layout_name + ".setTextColor(" + XmlUtil.getColorHex(value) + ");");
					} 
					else if(value.startsWith("@color/")){
						System.out.println("    " + layout_name + ".setTextColor(getColor(R.color." + value.substring(7) + "));");
					}
					else
						System.out.println("    " + layout_name + ".setTextColor(" + value + ");");
				}
				else if(key.equals("android:textSize")){
					System.out.println("    "+layout_name+".setTextSize("+XmlUtil.getFontSize(value)+");");
				}
				else if(key.equals("android:ellipsize")){
					String elipsize = value;
					if(elipsize.equals("end")){
						elipsize = "TextUtils.TruncateAt.END";
					}
					else if(elipsize.equals("start")){
						elipsize = "TextUtils.TruncateAt.START";
					}
					else if(elipsize.equals("moddle")){
						elipsize = "TextUtils.TruncateAt.MIDDLE";
					}
					else if(elipsize.equals("marquee")){
						elipsize = "TextUtils.TruncateAt.MARQUEE";
					}
					System.out.println("    "+layout_name+".setEllipsize("+elipsize+");");
				}
				else if (key.equals("android:layout_weight")) {

				} 
				//date|textUri|textShortMessage|textAutoCorrect|none|numberSigned|textVisiblePassword|textWebEditText|textMultiLine|textNoSuggestions|textCapSentences|
				//textAutoComplete|textImeMultiLine|numberDecimal
				else if (key.equals("android:inputType")) {
					String items[] = value.split("\\|");
					ArrayList<String> list_items = new ArrayList<String>();
					for (String item : items)
						list_items.add(item);
					// System.out.println("........................."+items+ "
					// "+list_items);
					value = "";
					if(list_items.contains("date")){
						value += "|InputType.TYPE_CLASS_DATETIME";
					}
					if(list_items.contains("textUri")){
						value += "|InputType.TYPE_TEXT_VARIATION_URI";
					}
					if(list_items.contains("textShortMessage")){
						value += "|InputType.TYPE_TEXT_VARIATION_SHORT_MESSAGE";
					}
					if(list_items.contains("textAutoCorrect")){
						value += "|InputType.TYPE_TEXT_FLAG_AUTO_CORRECT";
					}
					if(list_items.contains("none")){
						value += "|InputType.TYPE_DATETIME_VARIATION_NORMAL";
					}
					if(list_items.contains("numberSigned")){
						value += "|InputType.TYPE_NUMBER_FLAG_SIGNED";
					}
					if(list_items.contains("textVisiblePassword")){
						value += "|InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD";
					}
					if(list_items.contains("textWebEditText")){
						value += "|InputType.TYPE_TEXT_VARIATION_WEB_EDIT_TEXT";
					}
					if(list_items.contains("textNoSuggestions")){
						value += "|InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS";
					}
					if(list_items.contains("textCapSentences")){
						value += "|InputType.TYPE_TEXT_FLAG_CAP_SENTENCES";
					}
					if(list_items.contains("textImeMultiLine")){
						value += "|InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE";
					}
			
					if(list_items.contains("numberDecimal")){
						value += "|InputType.TYPE_NUMBER_FLAG_DECIMAL";
					}
					if (list_items.contains("text")) {
						value += "|InputType.TYPE_CLASS_TEXT";
					}
					if (list_items.contains("number")) {
						value += "|InputType.TYPE_CLASS_NUMBER";
					}
					if (list_items.contains("textCapCharacters")) {
						value += "|InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS";
					}
					if (list_items.contains("textMultiLine")) {
						value += "|InputType.TYPE_TEXT_FLAG_MULTI_LINE";
					}
					if(list_items.contains("textPassword")){
						value += "|InputType.TYPE_TEXT_VARIATION_PASSWORD";
					}
					if(value.length()>0){
						value = value.substring(1);
					}
					System.out.println("    " + layout_name + ".setInputType(" + value + ");");
				} else if (key.equals("android:typeface")) {
					if (value.equals("monospace")) {
						value = "Typeface.MONOSPACE";
					} else if (value.equals("serif")) {
						value = "Typeface.SERIF";
					} else if (value.equals("bold")) {
						value = "Typeface.DEFAULT_BOLD";
					}
					else if(value.equals("sans"))
					{
						value = "Typeface.SANS_SERIF";
					}

					System.out.println("    " + layout_name + ".setTypeface(" + value + ");");
				} 
				else if(key.equals("android:gravity")){
					String gravity = "";
					if(value.indexOf("center")>=0)
						gravity+="|Gravity.CENTER";
					if(value.indexOf("left")>=0)
						gravity+="|Gravity.LEFT";
					if(value.indexOf("right")>=0)
						gravity+="|Gravity.RIGHT";
					if(value.indexOf("top")>=0)
						gravity+="|Gravity.TOP";
					if(value.indexOf("bottom")>=0)
						gravity+="|Gravity.BOTTOM";
					System.out.println("    " + layout_name+".setGravity("+gravity.substring(1)+");");
				}
				else if(key.equals("android:layout_gravity")){
					String gravity = "";
					if(value.indexOf("center")>=0)
						gravity+="|Gravity.CENTER";
					if(value.indexOf("left")>=0)
						gravity+="|Gravity.LEFT";
					if(value.indexOf("right")>=0)
						gravity+="|Gravity.RIGHT";
					if(value.indexOf("top")>=0)
						gravity+="|Gravity.TOP";
					if(value.indexOf("bottom")>=0)
						gravity+="|Gravity.BOTTOM";
					System.out.println("    " + element.getAttribute("layoutparams")+".gravity = "+gravity.substring(1)+";");

				}
				else if(key.equals("android:scrollbars")){
					if(value.equals("vertical"))
					{
						System.out.println("    " + layout_name+".setVerticalScrollBarEnabled(true);");
						System.out.println("    "+layout_name + ".setScrollBarStyle(View.SCROLLBARS_OUTSIDE_INSET);");
					}
					else if(value.equals("none"))
					{
						System.out.println("    "+ layout_name + ".setVerticalScrollBarEnabled(false);");
					}
					else
					{
						System.out.println("    " + layout_name + ".setScrollBarStyle(view.SCROLLBARS_INSIDE_OVERLAY);");
					}
				}
				else if(key.equals("android:minHeight")){
					value = XmlUtil.getSize(value);
					System.out.println("    "+layout_name+".setMinHeight("+value+");");
				}
				else if(key.equals("android:maxHeight")){
					value = XmlUtil.getSize(value);
					System.out.println("    "+layout_name+".setMaxHeight("+value+");");
				}
				else if(key.equals("android:minWidth")){
					value = XmlUtil.getSize(value);
					System.out.println("    "+layout_name+".setMinWidth("+value+");");
				}
				else if(key.equals("android:maxWidth")){
					value = XmlUtil.getSize(value);
					System.out.println("    "+layout_name+".setMaxWidth("+value+");");
				}
				else if(key.equals("android:elevation")){
					value = XmlUtil.getSize(value);
					System.out.println("    "+layout_name+".setElevation("+value+");");
				}
				else if(key.equals("android:shadowColor")){
					String shadowDx = element.getAttribute("android:shadowDx");
					String shadowDy = element.getAttribute("android:shadowDy");
					String shadowColor = value;
					if(shadowColor.startsWith("#")){
						shadowColor = XmlUtil.getColorHex(shadowColor);
					}
					else if(shadowColor.startsWith("@color/")){
						shadowColor = "R.color."+shadowColor.substring(7);
					}
					System.out.println("    "+layout_name+".setShadowLayer("+0+", "+XmlUtil.getSize(shadowDx)+", "+XmlUtil.getSize(shadowDy)+", "+shadowColor+");");
				}
				else if(key.endsWith("android:scaleType")){
					if(value.equals("matrix"))
					{
						System.out.println("    "+layout_name+".setScaleType(ImageView.ScaleType.MATRIX);");
					}
					else if(value.equals("fitXY"))
					{
						System.out.println("    "+layout_name+".setScaleType(ImageView.ScaleType.FIT_XY);");
					}
					else if(value.equals("fitStart"))
					{
						System.out.println("    "+layout_name+".setScaleType(ImageView.ScaleType.FIT_START);");
						
					}
					else if(value.equals("fitCenter"))
					{
						System.out.println("    "+layout_name+".setScaleType(ImageView.ScaleType.FIT_CENTER);");
						
					}
					else if(value.equals("fitEnd"))
					{
						System.out.println("    "+layout_name+".setScaleType(ImageView.ScaleType.FIT_END);");
						
					}else if(value.equals("center"))
					{
						System.out.println("    "+layout_name+".setScaleType(ImageView.ScaleType.CENTER);");
						
					}
					else if(value.equals("centerCrop"))
					{
						System.out.println("    "+layout_name+".setScaleType(ImageView.ScaleType.CENTER_CROP);");
						
					}
					else if(value.equals("centerInside"))
					{
						System.out.println("    "+layout_name+".setScaleType(ImageView.ScaleType.CENTER_INSIDE);");
						
					}
				}
				else if(key.equals("android:tint")){
					String color = value;
					if(color.startsWith("#")){
						color = XmlUtil.getColorHex(color);
					}
					else if(color.startsWith("@color/")){
						color = "R.color."+color.substring(7);
					}
					System.out.println("    "+layout_name+".setColorFilter("+color+");");
				}
				else if(key.equals("android:paddingTop")){
//					String padding_top = element.getAttribute("android:paddingTop");
//					String padding_bottom = element.getAttribute("android:paddingBottom");
//					String padding_left = element.getAttribute("android:paddingLeft");
//					String padding_right = element.getAttribute("android:paddingRight");
//					element.removeAttribute("android:paddingTop");
//					element.removeAttribute("android:paddingBottom");
//					element.removeAttribute("android:paddingLeft");
//					element.removeAttribute("android:paddingRight");
//					padding_top = XmlUtil.getSize(padding_top);
//					padding_left = XmlUtil.getSize(padding_left);
//					padding_right = XmlUtil.getSize(padding_right);
//					padding_bottom = XmlUtil.getSize(padding_bottom);
//					System.out.println("    "+layout_name+".setPadding("+padding_left+", "+padding_top+", "+padding_right+", "+padding_bottom+");");
				}
				else if(key.equals("android:paddingBottom")){
//					String padding_top = element.getAttribute("android:paddingTop");
//					String padding_bottom = element.getAttribute("android:paddingBottom");
//					String padding_left = element.getAttribute("android:paddingLeft");
//					String padding_right = element.getAttribute("android:paddingRight");
//					element.removeAttribute("android:paddingTop");
//					element.removeAttribute("android:paddingBottom");
//					element.removeAttribute("android:paddingLeft");
//					element.removeAttribute("android:paddingRight");
//					padding_top = XmlUtil.getSize(padding_top);
//					padding_left = XmlUtil.getSize(padding_left);
//					padding_right = XmlUtil.getSize(padding_right);
//					padding_bottom = XmlUtil.getSize(padding_bottom);
//					System.out.println("    "+layout_name+".setPadding("+padding_left+", "+padding_top+", "+padding_right+", "+padding_bottom+");");
			
				}
				else if(key.equals("android:paddingLeft")){
//					String padding_top = element.getAttribute("android:paddingTop");
//					String padding_bottom = element.getAttribute("android:paddingBottom");
//					String padding_left = element.getAttribute("android:paddingLeft");
//					String padding_right = element.getAttribute("android:paddingRight");
//					element.removeAttribute("android:paddingTop");
//					element.removeAttribute("android:paddingBottom");
//					element.removeAttribute("android:paddingLeft");
//					element.removeAttribute("android:paddingRight");
//					padding_top = XmlUtil.getSize(padding_top);
//					padding_left = XmlUtil.getSize(padding_left);
//					padding_right = XmlUtil.getSize(padding_right);
//					padding_bottom = XmlUtil.getSize(padding_bottom);
//					System.out.println("    "+layout_name+".setPadding("+padding_left+", "+padding_top+", "+padding_right+", "+padding_bottom+");");
			
				}
				else if(key.equals("android:paddingRight")){
//					String padding_top = element.getAttribute("android:paddingTop");
//					String padding_bottom = element.getAttribute("android:paddingBottom");
//					String padding_left = element.getAttribute("android:paddingLeft");
//					String padding_right = element.getAttribute("android:paddingRight");
//					element.removeAttribute("android:paddingTop");
//					element.removeAttribute("android:paddingBottom");
//					element.removeAttribute("android:paddingLeft");
//					element.removeAttribute("android:paddingRight");
//					padding_top = XmlUtil.getSize(padding_top);
//					padding_left = XmlUtil.getSize(padding_left);
//					padding_right = XmlUtil.getSize(padding_right);
//					padding_bottom = XmlUtil.getSize(padding_bottom);
//					System.out.println("    "+layout_name+".setPadding("+padding_left+", "+padding_top+", "+padding_right+", "+padding_bottom+");");
			
				}
				else if(key.equals("android:padding")){
					System.out.println("    "+layout_name+".setPadding("+value+", "+value+", "+value+", "+value+");");
				}
				else if(key.equals("android:alpha")){
					System.out.println("    "+layout_name+".setAlpha("+value+");");
				}
				else if(key.equals("android:textStyle")){
					if(value.equals("bold"))
					{
						System.out.println("    "+layout_name+".setTypeface("+layout_name+".getTypeface(), Typeface.BOLD);" );
					}
					if(value.equals("italic"))
					{
						System.out.println("    "+layout_name+".setTypeface("+layout_name+".getTypeface(), Typeface.ITALIC);" );
					}
					if(value.equals("bold_italic")){
						System.out.println("    "+layout_name+".setTypeface("+layout_name+".getTypeface(), Typeface.BOLD_ITALIC);" );
					}
					
				}
				else if(key.equals("android:visibility")){
					
				    if(value.equals("visible")){
				    	System.out.println("    "+layout_name+".setVisibility(View.VISIBLE);");
				    }
							
					else if(value.equals("invisible")){
						System.out.println("    "+layout_name+".setVisibility(View.INVISIBLE);");
					}
							
					else if(value.equals("gone")){
						System.out.println("    "+layout_name+".setVisibility(View.GONE);");
					}
							
					
				}
				else if(key.equals("android:singleLine")){
					System.out.println("    "+layout_name+".setSingleLine("+value+");");
				}
				else if(key.equals("android:ems")){
					System.out.println("    "+layout_name+".setEms("+XmlUtil.getSize(value)+");");
				}
				else if(key.equals("android:lines"))
				{
					System.out.println("    "+layout_name+".setLines("+value+");");
				}
				else if(key.equals("android:minLines")){
					System.out.println("    "+layout_name+".setMinLines("+value+");");
				}
				else if(key.equals("android:maxLines")){
					System.out.println("    "+layout_name+".setMaxLines("+value+");");
				}
				else if(key.equals("android:hint"))
				{
					if(value.startsWith("@string/")){
						value = "R.string."+value.substring(8);
					}
					System.out.println("    "+layout_name+".setHint(\""+value+"\");");
				}
				else if(key.equals("android:drawable")){
					if(value.startsWith("@drawable/")){
						value = "R.drawable."+value.substring(10);
					}
					else if(value.startsWith("@mipmap/")){
						value = "R.mipmap."+value.substring(8);
					}
					System.out.println("    "+layout_name+".setImageDrawable(context.getDrawable("+value+"));");
				}
				else if(key.equals("name")){
					
				}
				else if (key.equals("id")) {

				} else if (key.equals("layoutparams")) {

				} else if (key.equals("xmlns:app")) {

				} else if (key.equals("xmlns:android")) {

				} else if (key.equals("xmlns:tools")) {

				} else if (key.equals("android:id")) {
					if (value.startsWith("@+id/") || value.startsWith("@id/")) {
						System.out.println(
								"    " + layout_name + ".setId(R.id." + value.substring(value.indexOf("/") + 1) + ");");
					} else
						System.out.println("    " + layout_name + ".setId(" + value + ");");
				} else if (key.startsWith("android:")) {
					System.out.println(""+layout_name+"."+key.substring(8)+" = "+value);
				} 
				else if(key.startsWith("app:")){
					System.out.println(""+layout_name+"."+key.substring(4)+" = "+value);
				}
				else
					System.out.println("    " + layout_name + "." + map.item(ii).getNodeName() + " = "
							+ map.item(ii).getNodeValue() + ";");
			}

			for (int n = 0; n < nodelist.getLength(); n++) {
				Node nodeitem = nodelist.item(n);

				// System.out.println("node item = " + nodeitem.getNodeName() +
				// " value=" + nodeitem.getNodeValue() + " type="
				// + nodeitem.getNodeType() + " ");
				printAndroidCode(element.getNodeName(), layout_name, nodeitem);
			}
		}

		if (node.getNodeType() == 1) {
			Element element1 = (Element) node;
			System.out.println("    " + name + ".addView(" + element1.getAttribute("name") + ","
					+ element1.getAttribute("layoutparams") + ");");
		}
	}
	
	//通过标签获取swift类名
	String getLayoutName(String nodeName){
		if(nodeName.equals("LinearLayout")){
			return "TGLinearLayout";
		}
		else if(nodeName.equals("FrameLayout")){
			return "TGFrameLayout";
		}
		else if(nodeName.equals("TextView")){
			return "UILabel";
		}
		else if(nodeName.equals("Button")){
			return "UIButton";
		}
		else if(nodeName.equals("ImageView")){
			return "UIImageView";
		}
		else if(nodeName.equals("EditText")){
			return "UITextField";
		}
		else if(nodeName.equals("RelativeLayout")){
			return "TGRelativeLayout";
		}
		else if(nodeName.equals("TableLayout")){
			return "TGTableLayout";
		}
		else if(nodeName.equals("ListView")){
			return "UITableView";
		}
		else if(nodeName.equals("View")){
			return "UIView";
		}
		else if(nodeName.equals("ScrollView")){
			return "UIScrollView";
		}
		else if(nodeName.equals("HorizontalScrollView")){
			return "UIScrollView";
		}
		else {
			if(nodeName.indexOf('.')>0){
				return nodeName.substring(Str.strrchr(nodeName, '.')+1);
			}
			return nodeName; 
		}
	}
	
	// 输出 ios swift 代码
		void printiOSCode(String className, String name, Node node) {
			NodeList nodelist = node.getChildNodes();
			// System.out.println("node name=" + node.getNodeName() + " value=" +
			// node.getNodeValue() + " type=" + node.getNodeType());
			if (node.getNodeType() == 1) {
				// 输出代码
				Element element = (Element) node;
				
				String layout_name = element.getAttribute("name");
				String nodeName = element.getNodeName();
//				if(nodeName.equals("LinearLayout")){
//					nodeName = "TGLinearLayout";
//				}
//				if(nodeName.equals("Button")){
//					nodeName = "UIButton";
//				}
//				if(nodeName.equals("EditText")){
//					nodeName = "UITextField";
//				}
//				if(nodeName.equals("ImageView")){
//					nodeName = "UIImageView";
//				}
				//优先处理margin top layout_width layout_height layout_weight
				String layout_width = element.getAttribute("android:layout_width");
				String layout_height = element.getAttribute("android:layout_height");
//				String layout_weight = element.getAttribute("android:layout_weight");
				String orientation = element.getAttribute("android:orientation");
				if (layout_width.equals("match_parent") || layout_width.equals("fill_parent")) {
					layout_width = ".fill";
				}
				else if (layout_width.equals("wrap_content")) {
					layout_width = ".wrap";
				}
				else {
					layout_width = XmlUtil.getSize(layout_width);
				}
				
				if (layout_height.equals("match_parent") || layout_height.equals("fill_parent")) {
					layout_height = ".fill";
				}
				else if (layout_height.equals("wrap_content")) {
					layout_height = ".wrap";
				}
				else {
					layout_height = XmlUtil.getSize(layout_height);
				}
				if(orientation.equals("vertical")){
					orientation = ".vert";
				}
				else if(orientation.equals("horizontal")){
					orientation = ".horz";
				}
				
				
				

				//创建layout
				if(nodeName.equals("LinearLayout")){
					buf_code.append("    var " + layout_name + ":" + "TGLinearLayout" + " = "
						+ "TGLinearLayout" + "(" + orientation+")\n");
				}
				else if(nodeName.equals("FrameLayout")){
					buf_code.append("    var " + layout_name + ":" + "TGFrameLayout" + " = "
							+ "TGFrameLayout" + "(" + orientation+")\n");
				}
				else if(nodeName.equals("TextView")){
					buf_code.append("    var " + layout_name + ":" + "UITextView" + " = "
							+ "UITextView" + "(" +")\n");
				}
				else if(nodeName.equals("Button")){
					buf_code.append("    var " + layout_name + ":" + "UIButton" + " = "
							+ "UIButton" + "(" +")\n");
				}
				else if(nodeName.equals("ImageView")){
					buf_code.append("    var " + layout_name + ":" + "UIImageView" + " = "
							+ "UIImageView" + "(" +")\n");
				}
				else if(nodeName.equals("EditText")){
					buf_code.append("    var " + layout_name + ":" + "UITextField" + " = "
							+ "UITextField" + "(" +")\n");
				}
				else {
					buf_code.append("    var " + layout_name + ":" + getLayoutName(nodeName) + " = "
							+ getLayoutName(nodeName) + "(" +")\n");
				}
				if(layout_width.indexOf("%")>0){
					buf_code.append("    " + layout_name + ".tg_width ~= "+layout_width+"\n");
				}
				else{
					buf_code.append("    " + layout_name + ".tg_width.equal("+layout_width+")\n");
				}
				if(layout_height.indexOf("%")>0){
					buf_code.append("    " + layout_name + ".tg_height ~= "+layout_height+"\n");
				}
				else{
					buf_code.append("    " + layout_name + ".tg_height.equal("+layout_height+")\n");
				}
				
//				buf_code.append("    " + layout_name + ".weight = "+layout_weight+"\n");
				
				
				String margin = element.getAttribute("android:layout_margin");
				String margin_top = element.getAttribute("android:layout_marginTop");
				String margin_bottom = element.getAttribute("android:layout_marginBottom");
				String margin_left = element.getAttribute("android:layout_marginLeft");
				String margin_right = element.getAttribute("android:layout_marginRight");
				if(margin.length()!=0){
					margin_top = margin;
					margin_left = margin;
					margin_right = margin;
					margin_bottom = margin;
				}
				if(margin_left.length()!=0 || margin_top.length()!=0 || margin_right.length()!=0 || margin_bottom.length()!=0){
					margin_left = XmlUtil.getSize(margin_left);
					margin_top = XmlUtil.getSize(margin_top);
					margin_right = XmlUtil.getSize(margin_right);
					margin_bottom = XmlUtil.getSize(margin_bottom);
					buf_code.append("    "+layout_name+".tg_top ~= "+margin_top+"\n");
					buf_code.append("    "+layout_name+".tg_bottom ~= "+margin_bottom+"\n");
					buf_code.append("    "+layout_name+".tg_left ~= "+margin_left+"\n");
					buf_code.append("    "+layout_name+".tg_right ~= "+margin_right+"\n");
				}
				
				String padding = element.getAttribute("android:padding");
				String padding_left = element.getAttribute("android:paddingLeft");
				String padding_right = element.getAttribute("android:paddingRight");
				String padding_top = element.getAttribute("android:paddingTop");
				String padding_bottom = element.getAttribute("android:paddingBottom");
				if(padding.length()!=0){
					padding_left = padding;
					padding_top = padding;
					padding_right = padding;
					padding_bottom = padding;
				}
				if(padding_left.length()!=0 || padding_top.length()!=0 || padding_bottom.length()!=0 || padding_right.length()!=0){
					padding_left = XmlUtil.getSize(padding_left);
					padding_top = XmlUtil.getSize(padding_top);
					padding_right = XmlUtil.getSize(padding_right);
					padding_bottom = XmlUtil.getSize(padding_bottom);
					buf_code.append("    "+layout_name+".tg_padding = UIEdgeInsets(top:CGFloat("+padding_top+"), left:CGFloat("+padding_left+"), bottom:CGFloat("+padding_bottom+"), right:CGFloat("+padding_right+"))\n");
		
				}
				
				NamedNodeMap map = node.getAttributes();
				for (int ii = 0; ii < map.getLength(); ii++) {
					String key = map.item(ii).getNodeName();
					String value = element.getAttribute(key);
					
					if (key.equals("android:layout_width")) {
					}
					else if(key.indexOf("xmlns:")>=0){
						
					}
					else if(key.equals("android:layout_margin")){
					}
					else if(key.equals("android:layout_marginTop")){
						
					}
					else if(key.equals("android:layout_marginBottom")){
						
					}
					else if(key.equals("android:layout_marginLeft")){
						
					}
					else if(key.equals("android:layout_marginRight")){
						
					}
					else if (key.equals("android:layout_height")) {

					} else if (key.equals("android:theme")) {
						
					} else if (key.equals("android:background")) {
						if(value.startsWith("#")){
							value = XmlUtil.getColorHex(value);
							buf_code.append("    "+layout_name+".layer.backgroundColor = ColorUtil.getCGColor("+value+")\n");
						}
						else if(value.startsWith("@color/")){
							buf_code.append("    "+layout_name+".layer.backgroundColor = ColorUtil.getCGColor(\""+value.substring(7)+"\")\n");
						}
						else if(value.startsWith("@drawable/")){
							buf_code.append("    "+layout_name+".layer.contents = UIImage(named: \""+value.substring(10)+"\")!.cgImage\n");
						}
						else if(value.startsWith("@mipmap/")){
							buf_code.append("    "+layout_name+".layer.contents = UIImage(named: \""+value.substring(8)+"\")!.cgImage\n");
						}
					} else if (key.equals("android:orientation")) {
						
					} else if (key.equals("android:src")) {
						if(value.startsWith("@drawable/")){
							value = ""+value.substring(10);
						}
						else if(value.startsWith("@mipmap/")){
							value = ""+value.substring(8);
						}
						buf_code.append("    "+layout_name+".setImage(UIImage(named:\""+value+"\"),for: UIControl.State.normal)\n");

					} else if (key.equals("android:text")) {
						if(value.startsWith("@string/")){
							buf_code.append("    "+layout_name+".text = "+value.substring(8)+"\n");
						}
						else{
							buf_code.append("    "+layout_name+".text = \""+value+"\"\n");
						}
					} else if (key.equals("android:textColor")) {
						if (value.startsWith("#")) {
							buf_code.append("    " + layout_name + ".textColor = " + XmlUtil.getColorHex(value) + "\n");
						} 
						else if(value.startsWith("@color/")){
							buf_code.append("    " + layout_name + ".textColor = " + value.substring(7) + "\n");
						}
						else
							buf_code.append("    " + layout_name + ".textColor = " + value + "\n");
					}
					else if(key.equals("android:textSize")){
						if(value.indexOf("sp")>0){
							buf_code.append("    "+layout_name+".font = UIFont.systemFont(ofSize: "+Str.atoi(value)+")\n");
						}
						else{
							buf_code.append("    "+layout_name+".font = UIFont.systemFont(ofSize: "+ XmlUtil.getFontSize(value) +")\n");
						}
						
					}
					else if(key.equals("android:ellipsize")){
						String elipsize = value;
						if(elipsize.equals("end")){
							elipsize = "NSLineBreakMode.byTruncatingHead";
						}
						else if(elipsize.equals("start")){
							elipsize = "NSLineBreakMode.byTruncatingHead";
						}
						else if(elipsize.equals("moddle")){
							elipsize = "NSLineBreakMode.byTruncatingMiddle";
						}
						else if(elipsize.equals("marquee")){
							elipsize = "MSLineBreakMode.byWordWrapping";
						}
						buf_code.append("    "+layout_name+".lineBreakMode = "+elipsize+"\n");
					}
					else if (key.equals("android:layout_weight")) {
						System.out.println("layout_weight = "+value);
						Element eleParent = (Element) element.getParentNode();
						String orien = eleParent.getAttribute("android:orientation");
						if(orien.equals("horizontal")){
							System.out.println("orien is horizontal");
							String percentage = element.getAttribute("width_percentage");
							if(percentage.length()!=0){
								buf_code.append("    "+layout_name + ".tg_width ~= "+percentage+"%\n");
							}
							else{
								System.out.println("width_percentage is null");
							}
						}
						else if(orien.equals("vertical")){
							System.out.println("orien is vertical");
							String percentage = element.getAttribute("height_percentage");
							if(percentage.length()!=0){
								buf_code.append("    "+layout_name + "tg_height ~= "+percentage+"%\n");
							}
						}
						else{
							System.out.print("orientation 未找到"+value);
						}
					} 
					//date|textUri|textShortMessage|textAutoCorrect|none|numberSigned|textVisiblePassword|textWebEditText|textMultiLine|textNoSuggestions|textCapSentences|
					//textAutoComplete|textImeMultiLine|numberDecimal
					else if (key.equals("android:inputType")) {
//						String items[] = value.split("\\|");
//						ArrayList<String> list_items = new ArrayList<String>();
//						for (String item : items)
//							list_items.add(item);
//						// System.out.println("........................."+items+ "
//						// "+list_items);
//						value = "";
//						if(list_items.contains("date")){
//							value += "|UIKeybordType.asciiCapable";
//						}
//						if(list_items.contains("textUri")){
//							value += "|UIKeybordType.URL";
//						}
//						if(list_items.contains("textShortMessage")){
//							value += "|default";
//						}
//						if(list_items.contains("textAutoCorrect")){
//							value += "|default";
//						}
//						if(list_items.contains("none")){
//							value += "|default";
//						}
//						if(list_items.contains("numberSigned")){
//							value += "|numberPad";
//						}
//						if(list_items.contains("textVisiblePassword")){
//							value += "|";
//						}
//						if(list_items.contains("textWebEditText")){
//							value += "|";
//						}
//						if(list_items.contains("textNoSuggestions")){
//							value += "|";
//						}
//						if(list_items.contains("textCapSentences")){
//							value += "|";
//						}
//						if(list_items.contains("textImeMultiLine")){
//							value += "|";
//						}
//				
//						if(list_items.contains("numberDecimal")){
//							value += "|";
//						}
//						if (list_items.contains("text")) {
//							value += "|default";
//						}
//						if (list_items.contains("number")) {
//							value += "|numberPad";
//						}
//						if (list_items.contains("textCapCharacters")) {
//							value += "|";
//						}
//						if (list_items.contains("textMultiLine")) {
//							value += "|";
//						}
//						if(list_items.contains("textPassword")){
//							value += "|";
//						}
//						if(value.length()>0){
//							value = value.substring(1);
//						}
//						System.out.println("    " + layout_name + ".setInputType(" + value + ");");
					} else if (key.equals("android:typeface")) {
//						if (value.equals("monospace")) {
//							value = "Typeface.MONOSPACE";
//						} else if (value.equals("serif")) {
//							value = "Typeface.SERIF";
//						} else if (value.equals("bold")) {
//							value = "Typeface.DEFAULT_BOLD";
//						}
//						else if(value.equals("sans"))
//						{
//							value = "Typeface.SANS_SERIF";
//						}
//
//						System.out.println("    " + layout_name + ".setTypeface(" + value + ");");
					} 
					else if(key.equals("android:gravity")){
						String gravity_hor = "";
						String gravity_ver = "";
						if(nodeName.equals("TextView")){
							if(value.indexOf("left")>=0){
								buf_code.append("    "+layout_name+".textAlignment = "+".left\n");
							}
							if(value.indexOf("right")>=0){
								buf_code.append("    "+layout_name+".textAlignment = "+".right\n");
							}
							
							if(value.indexOf("top")>=0){
								buf_code.append("    "+layout_name+".textAlignment = "+".top\n");
							}
							if(value.indexOf("bottom")>=0){
								buf_code.append("    "+layout_name+".textAlignment = "+".bottom\n");
							}
							
							if(value.indexOf("center")>=0){
								buf_code.append("    "+layout_name+".textAlignment = "+".center\n");
							}
						}
						else{
							if(value.indexOf("center")>=0){
								buf_code.append("    "+layout_name+".centerX ~= 0\n");
								buf_code.append("    "+layout_name+".centerY ~= 0\n");
							}
							else{
								buf_code.append(""+layout_name+".gravity = "+value+"\n");
							}
						}
						
						
						
							
						
						
						
					}
					else if(key.equals("android:layout_gravity")){
						String gravity_hor = "";
						String gravity_ver = "";
						boolean isLeft = false;
						boolean isTop = false;
						boolean isRight = false;
						boolean isBottom = false;
						if(value.indexOf("left")>=0){
							isLeft = true;
							buf_code.append("    "+layout_name+".tg_left ~= 0\n");
						}
							
						if(value.indexOf("right")>=0){
							isRight = true;
							buf_code.append("    "+layout_name+".tg_right ~= 0\n");
						}
						
						if(value.indexOf("top")>=0){
							isTop = true;
							buf_code.append("    "+layout_name+".tg_top ~= 0\n");
						}
						if(value.indexOf("bottom")>=0){
							isBottom = true;
							buf_code.append("    "+layout_name+".tg_bottom ~= 0\n");
						}
						
						if(value.indexOf("center")>=0){
							if(isLeft || isRight){
								buf_code.append("    "+layout_name+".tg_centerY ~= 0\n");
							}
							else if(isBottom || isTop){
								buf_code.append("    "+layout_name+".tg_centerX ~= 0\n");
							}
							else {
								buf_code.append("    "+layout_name+".tg_centerX ~= 0\n");
								buf_code.append("    "+layout_name+".tg_centerY ~= 0\n");
							}
							
						}
						
						
							
						
						
						

					}
					else if(key.equals("android:scrollbars")){
//						if(value.equals("vertical"))
//						{
//							System.out.println("    " + layout_name+".setVerticalScrollBarEnabled(true);");
//							System.out.println("    "+layout_name + ".setScrollBarStyle(View.SCROLLBARS_OUTSIDE_INSET);");
//						}
//						else if(value.equals("none"))
//						{
//							System.out.println("    "+ layout_name + ".setVerticalScrollBarEnabled(false);");
//						}
//						else
//						{
//							System.out.println("    " + layout_name + ".setScrollBarStyle(view.SCROLLBARS_INSIDE_OVERLAY);");
//						}
					}
					else if(key.equals("android:minHeight")){
						value = XmlUtil.getSize(value);
						buf_code.append("    "+layout_name+".tg_top.min("+value+");\n");
					}
					else if(key.equals("android:maxHeight")){
						value = XmlUtil.getSize(value);
						buf_code.append("    "+layout_name+".tg_top.max("+value+");\n");
					}
					else if(key.equals("android:minWidth")){
						value = XmlUtil.getSize(value);
						buf_code.append("    "+layout_name+".tg_top.min("+value+");\n");
					}
					else if(key.equals("android:maxWidth")){
						value = XmlUtil.getSize(value);
						buf_code.append("    "+layout_name+".tg_left.max("+value+");\n");
					}
					else if(key.equals("android:elevation")){
//						value = XmlUtil.getSize(value);
//						System.out.println("    "+layout_name+".setElevation("+value+");");
					}
					else if(key.equals("android:shadowColor")){
						String shadowDx = element.getAttribute("android:shadowDx");
						String shadowDy = element.getAttribute("android:shadowDy");
						String shadowColor = value;
						if(shadowColor.startsWith("#")){
							shadowColor = XmlUtil.getColorHex(shadowColor);
						}
						else if(shadowColor.startsWith("@color/")){
							shadowColor = "R.color."+shadowColor.substring(7);
						}
						buf_code.append("    "+layout_name+".layer.shadowColor = "+shadowColor+"\n");
						buf_code.append("    "+layout_name+".layer.shadowOffset = "+"CGSize(width:"+shadowDx+", height:"+shadowDy+")\n");
					}
					else if(key.endsWith("android:scaleType")){
						if(value.equals("matrix"))
						{
							buf_code.append("    "+layout_name+".contentMode = UIView.ContentMode.center\n");
						}
						else if(value.equals("fitXY"))
						{
							buf_code.append("    "+layout_name+".contentMode = UIView.ContentMode.scaleAspectFill\n");
						}
						else if(value.equals("fitStart"))
						{
							buf_code.append("    "+layout_name+".contentMode = UIView.ContentMode.scaleAspectFill\n");
							
						}
						else if(value.equals("fitCenter"))
						{
							buf_code.append("    "+layout_name+".contentMode = UIView.ContentMode.center\n");
							
						}
						else if(value.equals("fitEnd"))
						{
							buf_code.append("    "+layout_name+".contentMode = UIView.ContentMode.center\n");
							
						}else if(value.equals("center"))
						{
							buf_code.append("    "+layout_name+".contentMode = UIView.ContentMode.center\n");
							
						}
						else if(value.equals("centerCrop"))
						{
							buf_code.append("    "+layout_name+".contentMode = UIView.ContentMode.center\n");
							
						}
						else if(value.equals("centerInside"))
						{
							buf_code.append("    "+layout_name+".contentMode = UIView.ContentMode.center\n");
							
						}
					}
					else if(key.equals("android:tint")){
						String color = value;
						if(color.startsWith("#")){
							color = XmlUtil.getColorHex(color);
						}
						else if(color.startsWith("@color/")){
							color = "R.color."+color.substring(7);
						}
						buf_code.append("    "+layout_name+".tintColor = "+color+"\n");
					}
					else if(key.equals("android:paddingTop")){

					}
					else if(key.equals("android:paddingBottom")){

					}
					else if(key.equals("android:paddingLeft")){

				
					}
					else if(key.equals("android:paddingRight")){

				
					}
					else if(key.equals("android:padding")){
						
					}
					else if(key.equals("android:alpha")){
						buf_code.append("    "+layout_name+".layer.alpha = "+value+"f\n");
					}
					else if(key.equals("android:textStyle")){
//						if(value.equals("bold"))
//						{
//							System.out.println("    "+layout_name+".setTypeface("+layout_name+".getTypeface(), Typeface.BOLD);" );
//						}
//						if(value.equals("italic"))
//						{
//							System.out.println("    "+layout_name+".setTypeface("+layout_name+".getTypeface(), Typeface.ITALIC);" );
//						}
//						if(value.equals("bold_italic")){
//							System.out.println("    "+layout_name+".setTypeface("+layout_name+".getTypeface(), Typeface.BOLD_ITALIC);" );
//						}
//						
					}
					else if(key.equals("android:visibility")){
						
					    if(value.equals("visible")){
					    	buf_code.append("    "+layout_name+".hidden = false\n");
					    }
								
						else if(value.equals("invisible")){
							buf_code.append("    "+layout_name+".hidden = true\n");
						}
								
						else if(value.equals("gone")){
							buf_code.append("    "+layout_name+".hidden = true\n");
						}
//								
						
					}
					else if(key.equals("android:singleLine")){
						if(value == "true"){
							buf_code.append("    "+layout_name+".numberOfLines = 1\n");
						}
						else{
							buf_code.append("    "+layout_name+".lineBreakMode = NSLineBreakMode.ByWordWrapping\n");
							buf_code.append("    "+layout_name+".numberOfLines = 0\n");
						}
//						System.out.println("    "+layout_name+".setSingleLine("+value+");");
					}
					else if(key.equals("android:ems")){
//						System.out.println("    "+layout_name+".setEms("+XmlUtil.getSize(value)+");");
					}
					else if(key.equals("android:lines"))
					{
						buf_code.append("    "+layout_name+".lineBreakMode = NSLineBreakMode.ByWordWrapping\n");
						buf_code.append("    "+layout_name+".numberOfLines = "+value+"\n");
					}
					else if(key.equals("android:minLines")){
//						System.out.println("    "+layout_name+".setMinLines("+value+");");
					}
					else if(key.equals("android:maxLines")){
//						System.out.println("    "+layout_name+".setMaxLines("+value+");");
					}
					else if(key.equals("android:hint"))
					{
						if(value.startsWith("@string/")){
							value = "R.string."+value.substring(8);
						}
						buf_code.append("    "+layout_name+".placeholder = \""+value+"\"\n");
					}
					else if(key.equals("android:drawable")){
						if(value.startsWith("@drawable/")){
							value = "R.drawable."+value.substring(10);
						}
						else if(value.startsWith("@mipmap/")){
							value = "R.mipmap."+value.substring(8);
						}
						buf_code.append("    "+layout_name+".setImage(UIImage(named:"+value+"),for: UIControl.State.normal)\n");
					}
					else if(key.equals("name")){
						
					}
					else if (key.equals("id")) {

					} else if (key.equals("layoutparams")) {

					} else if (key.equals("xmlns:app")) {

					} else if (key.equals("xmlns:android")) {

					} else if (key.equals("xmlns:tools")) {

					} else if (key.equals("android:id")) {
//						if (value.startsWith("@+id/") || value.startsWith("@id/")) {
//							System.out.println(
//									"    " + layout_name + ".setId(R.id." + value.substring(value.indexOf("/") + 1) + ");");
//						} else
//							System.out.println("    " + layout_name + ".setId(" + value + ");");
					} else if (key.startsWith("android:")) {
						buf_code.append(""+layout_name+"."+key.substring(8)+" = "+value+"\n");
					} 
					else if(key.startsWith("app:")){
						buf_code.append(""+layout_name+"."+key.substring(4)+" = "+value+"\n");
					}
					else
						buf_code.append("    " + layout_name + "." + map.item(ii).getNodeName() + " = "
								+ map.item(ii).getNodeValue() + ";\n");
				}

				for (int n = 0; n < nodelist.getLength(); n++) {
					Node nodeitem = nodelist.item(n);

					// System.out.println("node item = " + nodeitem.getNodeName() +
					// " value=" + nodeitem.getNodeValue() + " type="
					// + nodeitem.getNodeType() + " ");
					printiOSCode(element.getNodeName(), layout_name, nodeitem);
				}
			}

			if (node.getNodeType() == 1) {
				Element element1 = (Element) node;
				buf_code.append("    " + name + ".addSubview(" + element1.getAttribute("name") + ")\n");
			}
		}

	// 输出Swift代码
	void printSwiftCode(Node node) {

	}

	// 输出kotlin代码
	void printKotlinCode(Node node) {

	}
	
	public String toString(){
		return buf_code.toString();
	}

}

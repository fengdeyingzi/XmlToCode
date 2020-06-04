package com.xl.util;


import java.awt.Paint;
import java.awt.event.KeyEvent;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.xl.util.flutter.FlutterWidget;
import com.xl.util.flutter.LinearLayout;
import com.xl.util.flutter.Widget;



public class FlutterDomParser {

	StringBuffer buf_code;

	String text = "";
	FlutterWidget flutter_widget;



	public FlutterDomParser(){
		buf_code = new StringBuffer();
	}

	public void setXmlText(String text){
		this.text = text;
	}



	
	
	public void parseFlutter() {
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
				
				flutter_widget = new FlutterWidget(className);
				printFlutterCode(flutter_widget, root);
				System.out.println(flutter_widget.toString());
				buf_code.append(flutter_widget.toString());
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
							ArrayList<Element> list_views = XmlUtil.getChildElement(eleParent);
							for(int ii=0;ii<list_views.size();ii++){
								
								Element item = list_views.get(ii);
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




	
	// 输出 ios swift 代码
		void printFlutterCode(FlutterWidget widget, Element element) {
//			NodeList nodelist = node.getChildNodes();
			
				// 输出代码
//				Element element = (Element) node;
				String name = element.getNodeName();
				if(name.indexOf(".")>=0){
					widget.setWidgetName(name.substring(Str.strrchr(name, '.')+1));
				}
				else{
					widget.setWidgetName(name);
				}
				
				
				NamedNodeMap map = element.getAttributes();
				for (int ii = 0; ii < map.getLength(); ii++) {
					String key = map.item(ii).getNodeName();
					String value = element.getAttribute(key);
					widget.addParamItem(key, value);
				}

				ArrayList<Element> nodelist = XmlUtil.getChildElement(element);
				for (int n = 0; n < nodelist.size(); n++) {
					Element nodeitem = nodelist.get(n);
					FlutterWidget child = new FlutterWidget(element.getNodeName());
					widget.addChild(child);
					printFlutterCode(child, nodeitem);
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


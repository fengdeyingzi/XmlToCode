import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.UserDataHandler;

public class DomParser {

	class People {
		String name;
		String age;
	}

	String text = 
			"<?xml version=\"1.0\" encoding=\"utf-8\"?>\n"
			+"<LinearLayout\n"
			+"\txmlns:android=\"http://schemas.android.com/apk/res/android\"\n"
			+"\tandroid:layout_width=\"fill_parent\"\n"
			+"\tandroid:layout_height=\"match_parent\"\n"
			+"\tandroid:orientation=\"vertical\"\n"
			+"\tandroid:layout_weight=\"1.0\"\n"
			+"\tandroid:background=\"#20808080\"\n"
			+"\tandroid:id=\"@id/background\">\n"
			+"\n"
			+"\t<TextView\n"
			+"\t\tandroid:layout_width=\"wrap_content\"\n"
			+"\t\tandroid:layout_height=\"wrap_content\"\n"
			+"\t\tandroid:text=\"进制转换器\"\n"
			+"\t\tandroid:textColor=\"#989898\"\n"
			+"\t\tandroid:textSize=\"20sp\"\n"
			+"\t\tandroid:id=\"@+id/con_TextView\"/>\n"
			+"\n"
			+"\t<LinearLayout\n"
			+"\t\tandroid:layout_height=\"wrap_content\"\n"
			+"\t\tandroid:layout_width=\"match_parent\"\n"
			+"\t\tandroid:orientation=\"vertical\"\n"
			+"\t\tandroid:layout_weight=\"10\">\n"
			+"\n"
			+"\t\t<EditText\n"
			+"\t\t\tandroid:layout_height=\"wrap_content\"\n"
			+"\t\t\tandroid:scrollbars=\"vertical\"\n"
			+"\t\t\tandroid:ems=\"10\"\n"
			+"\t\t\tandroid:layout_width=\"match_parent\"\n"
			+"\t\t\tandroid:gravity=\"top|left\"\n"
			+"\t\t\tandroid:inputType=\"textCapCharacters|textMultiLine\"\n"
			+"\t\t\tandroid:id=\"@+id/con_editmain\"\n"
			+"\t\t\tandroid:typeface=\"monospace\"/>\n"
			+"\n"
			+"\t\t<LinearLayout\n"
			+"\t\t\tandroid:layout_width=\"match_parent\"\n"
			+"\t\t\tandroid:layout_height=\"wrap_content\"\n"
			+"\t\t\tandroid:orientation=\"vertical\">\n"
			+"\n"
			+"\t\t\t<Button\n"
			+"\t\t\t\tandroid:layout_height=\"wrap_content\"\n"
			+"\t\t\t\tandroid:text=\"转换十进制\"\n"
			+"\t\t\t\tandroid:layout_width=\"match_parent\"\n"
			+"\t\t\t\tandroid:id=\"@+id/con_ten\"\n"
			+"\t\t\t\tandroid:layout_weight=\"1\"/>\n"
			+"\n"
			+"\t\t\t<Button\n"
			+"\t\t\t\tandroid:layout_height=\"wrap_content\"\n"
			+"\t\t\t\tandroid:text=\"转换十六进制\"\n"
			+"\t\t\t\tandroid:layout_width=\"match_parent\"\n"
			+"\t\t\t\tandroid:id=\"@+id/con_hex\"/>\n"
			+"\n"
			+"\t\t\t<Button\n"
			+"\t\t\t\tandroid:layout_height=\"wrap_content\"\n"
			+"\t\t\t\tandroid:text=\"转换二进制\"\n"
			+"\t\t\t\tandroid:layout_width=\"match_parent\"\n"
			+"\t\t\t\tandroid:id=\"@+id/con_two\"/>\n"
			+"\n"
			+"\t\t</LinearLayout>\n"
			+"\n"
			+"\t</LinearLayout>\n"
			+"\n"
			+"</LinearLayout>\n"
			+"\n";


	public void parse() {
		DocumentBuilderFactory factory = null;
		DocumentBuilder builder = null;
		Document document = null;
		InputStream inputStream = null;
		// （2）实现DOM解析
		ArrayList list = new ArrayList<People>();
		factory = DocumentBuilderFactory.newInstance();
		try {
			// 惯例 取得document文件实例的过程
			builder = factory.newDocumentBuilder();
			inputStream = new ByteArrayInputStream(text.getBytes());
			;// 以工程文件下assets文件夹为根目录
			document = builder.parse(inputStream);

			// 取得根Element 以此列出所有节点NodeList
			Element root = document.getDocumentElement();
			if (root instanceof Node) {
				System.out.println("========");
				setNodesId(root);
				printfNodes(root);
				printAndroidCode(root.getNodeName(), "layout_root", root);
				return;
			}
			System.out.println("root xml = " + root.getNodeName() + " " + root.getNodeValue() + " type="
					+ root.getNodeType() + " tag=" + root.getTagName());
			// getElementsByTagName是在当前的Element 下查找"people"标志并生成NodeList
			NodeList nodes = root.getChildNodes(); // root.getElementsByTagName("LinearLayout");
			NamedNodeMap nodemap = root.getAttributes();
			for (int i = 0; i < nodemap.getLength(); i++) {
				System.out.println(
						"name = " + nodemap.item(i).getNodeName() + " value=" + nodemap.item(i).getNodeValue());
			}

			// 取出每个节点中的数据，这里应该分成3种数据，
			// ①是name、age那样的Attribute数据；
			// ②是在<people>括号外的NodeValue数据；
			// ③最后是在其地下的另一个node数据节点。
			for (int i = 0; i < nodes.getLength(); i++) {

				Node peopleitem = nodes.item(i);
				System.out.println(".............");
				printfNodes(peopleitem);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
int count = 0;
	//遍历node设置id
	void setNodesId(Node node){
		NodeList nodelist = node.getChildNodes();
//		System.out.println("node name=" + node.getNodeName() + " value=" + node.getNodeValue() + " type=" + node.getNodeType());
		if (node.getNodeType() == 1) {
			printfAttributes(node);
			NamedNodeMap map = node.getAttributes();

//			node.setNamedItem(nodetag);
			if(node instanceof Element){
				System.out.println("is Element");
				String id = node.getNodeName();
				
				((Element)node).setAttribute("id", id.toLowerCase()+"_"+count);
				((Element)node).setAttribute("layoutparams", "layoutParams_"+count);
				count++;
			}
		}
		for (int n = 0; n < nodelist.getLength(); n++) {
			Node nodeitem = nodelist.item(n);
			setNodesId(nodeitem);
		}
	}

	// 递归输出nodes
	void printfNodes(Node node) {
		NodeList nodelist = node.getChildNodes();
		System.out.println("node name=" + node.getNodeName() + " value=" + node.getNodeValue() + " type=" + node.getNodeType());
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
			System.out.println("name = " + map.item(ii).getNodeName() + " value=" + map.item(ii).getNodeValue()+" type="+map.item(ii).getNodeType());
		}
	}
	
	//输出android java代码
	void printAndroidCode(String className,String name,Node node){
		NodeList nodelist = node.getChildNodes();
//		System.out.println("node name=" + node.getNodeName() + " value=" + node.getNodeValue() + " type=" + node.getNodeType());
		if (node.getNodeType() == 1) {
			//输出代码
			Element element = (Element)node;
			System.out.println("    "+element.getNodeName()+" "+element.getAttribute("id")+" = new "+element.getNodeName()+"(context);");
			NamedNodeMap map = node.getAttributes();
			for (int ii = 0; ii < map.getLength(); ii++) {
				String key = map.item(ii).getNodeName();
				String value = element.getAttribute(key);
				String layout_name = element.getAttribute("id");
				if(key.equals("android:layout_width")){
					String layout_width = element.getAttribute("android:layout_width");
					String layout_height = element.getAttribute("android:layout_height");
					if(layout_width.equals("match_parent") || layout_width.equals("fill_parent")){
						layout_width = "ViewGroup.LayoutParams.MATCH_PARENT";
					}
					if(layout_width.equals("wrap_content")){
						layout_width = "ViewGroup.LayoutParams.WRAP_CONTENT";
					}
					if(layout_height.equals("match_parent") || layout_height.equals("fill_parent")){
						layout_height = "ViewGroup.LayoutParams.MATCH_PARENT";
					}
					if(layout_height.equals("wrap_content")){
						layout_height = "ViewGroup.LayoutParams.WRAP_CONTENT";
					}
					if(className.equals("LinearLayout")){
						System.out.println("    LinearLayout.LayoutParams "+element.getAttribute("layoutparams")+" = new LinearLayout.LayoutParams("+layout_width+","+layout_height+");");
					}
					else
					System.out.println("    ViewGroup.LayoutParams "+element.getAttribute("layoutparams")+" = new ViewGroup.LayoutParams("+layout_width+","+layout_height+");");
					if(element.getAttribute("android:layout_weight")!=null && element.getAttribute("android:layout_weight").length()!=0){
						System.out.println("    "+element.getAttribute("layoutparams")+".weight = "+element.getAttribute("android:layout_weight")+"f;");
					}
//					System.out.println("    "+element.getAttribute("id")+"." + "setLayoutWidth(" + "" + map.item(ii).getNodeValue()+");");
				}
				else if(key.equals("android:layout_height")){
//					System.out.println("    "+element.getAttribute("id")+"." + "setLayoutHeight(" + "" + map.item(ii).getNodeValue()+");");
				}
				else if(key.equals("android:theme")){
					System.out.println("    "+layout_name+".setTheme("+value+");");
				}
				else if(key.equals("android:background")){
					if(value.startsWith("#")){
						System.out.println("    "+layout_name+".setBackgroundColor(0x"+value.substring(1)+");");
					}
					else if(value.startsWith("@color/")){
						
					}
					else if(value.startsWith("@drawable/")){
						
					}
					else
					System.out.println("    "+layout_name+".setBackground("+value+");");
				}
				else if(key.equals("android:orientation")){
					if(value.equals("vertical")){
						value = "LinearLayout.VERTICAL";
					}
					else if(value.equals("horizontal")){
						value = "Linearlayout.HORIZONTAL";
					}
					System.out.println("    "+layout_name+".setOrientation("+value+");");
				}
				else if(key.equals("android:src")){
					System.out.println("    "+layout_name+".setImage("+value+");");
				}
				else if(key.equals("android:text")){
					System.out.println("    "+layout_name+".setText(\""+value+"\");");
				}
				else if(key.equals("android:textColor")){
					if(value.startsWith("#")){
						System.out.println("    "+layout_name+".setTextColor(0x"+value.substring(1)+");");
					}
					else
					System.out.println("    "+layout_name+".setTextColor("+value+");");
				}
				else if(key.equals("android:layout_weight")){
					
				}
				else if(key.equals("android:inputType")){
					String items[] = value.split("\\|");
					ArrayList<String> list_items = new ArrayList<String>();
					for(String item:items)
					list_items.add(item);
//					System.out.println("........................."+items+ " "+list_items);
					value = "";
					if(list_items.contains("text")){
						value += "InputType.TYPE_CLASS_TEXT";
					}
					if(list_items.contains("number")){
						if(value.length()>0)value+="|";
						value += "InputType.TYPE_CLASS_NUMBER";
					}
					if(list_items.contains("textCapCharacters")){
						if(value.length()>0)value+="|";
						value += "InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS";
					}
					if(list_items.contains("textMultiLine")){
						if(value.length()>0) value+="|";
						value += "InputType.TYPE_TEXT_FLAG_MULTI_LINE";
					}
					System.out.println("    "+layout_name+".setInputType("+value+");");
				}
				else if(key.equals("android:typeface")){
					if(value.equals("monospace")){
						value = "Typeface.MONOSPACE";
					}
					else if(value.equals("serif")){
						value = "Typeface.SERIF";
					}
					else if(value.equals("bold")){
						value = "Typeface.DEFAULT_BOLD";
					}
					
					System.out.println("    "+layout_name+".setTypeface("+value+");");
				}
				else if(key.equals("id")){
					
				}
				else if(key.equals("layoutparams")){
					
				}
				else if(key.equals("xmlns:app")){
					
				}
				else if(key.equals("xmlns:android")){
					
				}
				else if(key.equals("xmlns:tools")){
					
				}
				else if(key.equals("android:id")){
					if(value.startsWith("@+id/") || value.startsWith("@id/")){
						System.out.println("    "+layout_name+".setId(R.id."+value.substring(value.indexOf("/")+1)+");");
					}
					else
					System.out.println("    "+layout_name+".setId("+value+");");
				}
				else if(key.startsWith("android:")){
					
				}
				else
				System.out.println("    "+element.getAttribute("id")+"." + map.item(ii).getNodeName() + " = " + map.item(ii).getNodeValue()+";");
			}
			
			for (int n = 0; n < nodelist.getLength(); n++) {
				Node nodeitem = nodelist.item(n);
				
//				System.out.println("node item = " + nodeitem.getNodeName() + " value=" + nodeitem.getNodeValue() + " type="
//						+ nodeitem.getNodeType() + " ");
				printAndroidCode(element.getNodeName(),element.getAttribute("id"), nodeitem);
			}
		}
		
		if (node.getNodeType() == 1) {
			Element element1 = (Element)node;
		System.out.println("    "+name+".addView("+element1.getAttribute("id")+","+element1.getAttribute("layoutparams")+");");
		}
	}
	
	//输出Swift代码
	void printSwiftCode(Node node){
		
	}
	
	//输出kotlin代码
	void printKotlinCode(Node node){
		
	}
	

}

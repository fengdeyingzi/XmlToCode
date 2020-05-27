import java.awt.Paint;
import java.awt.event.KeyEvent;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

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

public class DomParser {

	class People {
		String name;
		String age;
	}

	String text = 
			"<?xml version=\"1.0\" encoding=\"utf-8\"?>\n"
			+"<LinearLayout xmlns:android=\"http://schemas.android.com/apk/res/android\"\n"
			+"    android:layout_width=\"match_parent\"\n"
			+"    android:layout_height=\"match_parent\"\n"
			+"    android:orientation=\"vertical\"\n"
			+"    android:paddingLeft=\"16dp\"\n"
			+"    android:paddingTop=\"24dp\"\n"
			+"    android:paddingRight=\"16dp\">\n"
			+"\n"
			+"    <TextView\n"
			+"        android:layout_width=\"wrap_content\"\n"
			+"        android:layout_height=\"wrap_content\"\n"
			+"        android:text=\"修改密码\"\n"
			+"        android:textSize=\"@dimen/font_h1\" />\n"
			+"\n"
			+"    <View\n"
			+"        android:layout_width=\"match_parent\"\n"
			+"        android:layout_height=\"24dp\" />\n"
			+"\n"
			+"    <TextView\n"
			+"        android:layout_width=\"wrap_content\"\n"
			+"        android:layout_height=\"wrap_content\"\n"
			+"        android:text=\"密码8至16位，建议包含数字、符号、字母中至少两种元素\"\n"
			+"        android:textColor=\"@color/text_gray\" />\n"
			+"\n"
			+"    <View\n"
			+"        android:layout_width=\"match_parent\"\n"
			+"        android:layout_height=\"16dp\" />\n"
			+"\n"
			+"    <TextView\n"
			+"        android:layout_width=\"wrap_content\"\n"
			+"        android:layout_height=\"wrap_content\"\n"
			+"\n"
			+"        android:text=\"输入旧密码\"\n"
			+"        android:textColor=\"@color/text_gray\" />\n"
			+"\n"
			+"    <EditText\n"
			+"        android:id=\"@+id/edit_oldpassword\"\n"
			+"        android:inputType=\"textPassword\"\n"
			+"        android:singleLine=\"true\"\n"
			+"        android:layout_width=\"match_parent\"\n"
			+"        android:layout_height=\"wrap_content\" />\n"
			+"\n"
			+"    <View\n"
			+"        android:layout_width=\"match_parent\"\n"
			+"        android:layout_height=\"16dp\" />\n"
			+"\n"
			+"    <TextView\n"
			+"        android:layout_width=\"wrap_content\"\n"
			+"        android:layout_height=\"wrap_content\"\n"
			+"        android:text=\"输入新密码\"\n"
			+"        android:textColor=\"@color/text_gray\" />\n"
			+"\n"
			+"    <EditText\n"
			+"        android:singleLine=\"true\"\n"
			+"        android:inputType=\"textPassword\"\n"
			+"        android:id=\"@+id/edit_newpassword\"\n"
			+"        android:layout_width=\"match_parent\"\n"
			+"        android:layout_height=\"wrap_content\" />\n"
			+"\n"
			+"    <TextView\n"
			+"        android:id=\"@+id/text_forgetpassword\"\n"
			+"        android:layout_width=\"wrap_content\"\n"
			+"        android:layout_height=\"wrap_content\"\n"
			+"        android:drawableRight=\"@drawable/right_arrow\"\n"
			+"        android:text=\"忘记密码 \"\n"
			+"        android:textColor=\"@color/text_gray\" />\n"
			+"\n"
			+"    <View\n"
			+"        android:layout_width=\"match_parent\"\n"
			+"        android:layout_height=\"24dp\" />\n"
			+"\n"
			+"    <Button\n"
			+"        android:id=\"@+id/btn_submit\"\n"
			+"        android:layout_width=\"match_parent\"\n"
			+"        android:layout_height=\"wrap_content\"\n"
			+"        android:text=\"确定\" />\n"
			+"</LinearLayout>";




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
	
	

	// 遍历node设置id
	void setNodesId(Node node) {
		NodeList nodelist = node.getChildNodes();
		// System.out.println("node name=" + node.getNodeName() + " value=" +
		// node.getNodeValue() + " type=" + node.getNodeType());
		if (node.getNodeType() == 1) {
			printfAttributes(node);
			NamedNodeMap map = node.getAttributes();

			// node.setNamedItem(nodetag);
			if (node instanceof Element) {
				System.out.println("is Element");
				String id = node.getNodeName();

				((Element) node).setAttribute("id", id.toLowerCase() + "_" + count);
				((Element) node).setAttribute("layoutparams", "layoutParams_" + count);
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
			System.out.println("    " + element.getNodeName() + " " + element.getAttribute("id") + " = new "
					+ element.getNodeName() + "(context);");
			NamedNodeMap map = node.getAttributes();
			for (int ii = 0; ii < map.getLength(); ii++) {
				String key = map.item(ii).getNodeName();
				String value = element.getAttribute(key);
				String layout_name = element.getAttribute("id");
				if (key.equals("android:layout_width")) {
					String layout_width = element.getAttribute("android:layout_width");
					String layout_height = element.getAttribute("android:layout_height");
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
						System.out.println("    LinearLayout.LayoutParams " + element.getAttribute("layoutparams")
								+ " = new LinearLayout.LayoutParams(" + layout_width + "," + layout_height + ");");
					} else
						System.out.println("    ViewGroup.LayoutParams " + element.getAttribute("layoutparams")
								+ " = new ViewGroup.LayoutParams(" + layout_width + "," + layout_height + ");");
					if (element.getAttribute("android:layout_weight") != null
							&& element.getAttribute("android:layout_weight").length() != 0) {
						System.out.println("    " + element.getAttribute("layoutparams") + ".weight = "
								+ element.getAttribute("android:layout_weight") + "f;");
					}
					// System.out.println(" "+element.getAttribute("id")+"." +
					// "setLayoutWidth(" + "" +
					// map.item(ii).getNodeValue()+");");
				}
				else if(key.equals("android:layout_margin")){
					String layout_params = element.getAttribute("layoutparams");
					String margin = XmlUtil.getSize(value);
					System.out.println("    "+layout_params+".setMargins("+margin+","+margin+","+margin+","+margin+");");
				}
				else if (key.equals("android:layout_height")) {
					// System.out.println(" "+element.getAttribute("id")+"." +
					// "setLayoutHeight(" + "" +
					// map.item(ii).getNodeValue()+");");
				} else if (key.equals("android:theme")) {
					System.out.println("    " + layout_name + ".setTheme(" + value + ");");
				} else if (key.equals("android:background")) {
					if (value.startsWith("#")) {
						System.out.println("    " + layout_name + ".setBackgroundColor(" + XmlUtil.getColorHex(value) + ");");
					} else if (value.startsWith("@color/")) {

					} else if (value.startsWith("@drawable/")) {

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
					System.out.println("    " + layout_name + ".setImage(" + value + ");");
				} else if (key.equals("android:text")) {
					if(value.startsWith("@string/")){
						value = "R.string."+value.substring(8);
					}
					System.out.println("    " + layout_name + ".setText(\"" + value + "\");");
				} else if (key.equals("android:textColor")) {
					if (value.startsWith("#")) {
						System.out.println("    " + layout_name + ".setTextColor(" + XmlUtil.getColorHex(value) + ");");
					} 
					else if(value.startsWith("@color/")){
						System.out.println("    " + layout_name + ".setTextColor(R.color." + value.substring(7) + ");");
					}
					else
						System.out.println("    " + layout_name + ".setTextColor(" + value + ");");
				}
				else if(key.equals("android:textSize")){
					System.out.println("    "+layout_name+".setTextSize("+XmlUtil.getFontSize(value)+");");
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
					String padding_top = element.getAttribute("android:paddingTop");
					String padding_bottom = element.getAttribute("android:paddingBottom");
					String padding_left = element.getAttribute("android:paddingLeft");
					String padding_right = element.getAttribute("android:paddingRight");
					element.removeAttribute("android:paddingTop");
					element.removeAttribute("android:paddingBottom");
					element.removeAttribute("android:paddingLeft");
					element.removeAttribute("android:paddingRight");
					padding_top = XmlUtil.getSize(padding_top);
					padding_left = XmlUtil.getSize(padding_left);
					padding_right = XmlUtil.getSize(padding_right);
					padding_bottom = XmlUtil.getSize(padding_bottom);
					System.out.println("    "+layout_name+".setPadding("+padding_left+", "+padding_top+", "+padding_right+", "+padding_bottom+");");
				}
				else if(key.equals("android:paddingBottom")){
					String padding_top = element.getAttribute("android:paddingTop");
					String padding_bottom = element.getAttribute("android:paddingBottom");
					String padding_left = element.getAttribute("android:paddingLeft");
					String padding_right = element.getAttribute("android:paddingRight");
					element.removeAttribute("android:paddingTop");
					element.removeAttribute("android:paddingBottom");
					element.removeAttribute("android:paddingLeft");
					element.removeAttribute("android:paddingRight");
					padding_top = XmlUtil.getSize(padding_top);
					padding_left = XmlUtil.getSize(padding_left);
					padding_right = XmlUtil.getSize(padding_right);
					padding_bottom = XmlUtil.getSize(padding_bottom);
					System.out.println("    "+layout_name+".setPadding("+padding_left+", "+padding_top+", "+padding_right+", "+padding_bottom+");");
			
				}
				else if(key.equals("android:paddingLeft")){
					String padding_top = element.getAttribute("android:paddingTop");
					String padding_bottom = element.getAttribute("android:paddingBottom");
					String padding_left = element.getAttribute("android:paddingLeft");
					String padding_right = element.getAttribute("android:paddingRight");
					element.removeAttribute("android:paddingTop");
					element.removeAttribute("android:paddingBottom");
					element.removeAttribute("android:paddingLeft");
					element.removeAttribute("android:paddingRight");
					padding_top = XmlUtil.getSize(padding_top);
					padding_left = XmlUtil.getSize(padding_left);
					padding_right = XmlUtil.getSize(padding_right);
					padding_bottom = XmlUtil.getSize(padding_bottom);
					System.out.println("    "+layout_name+".setPadding("+padding_left+", "+padding_top+", "+padding_right+", "+padding_bottom+");");
			
				}
				else if(key.equals("android:paddingRight")){
					String padding_top = element.getAttribute("android:paddingTop");
					String padding_bottom = element.getAttribute("android:paddingBottom");
					String padding_left = element.getAttribute("android:paddingLeft");
					String padding_right = element.getAttribute("android:paddingRight");
					element.removeAttribute("android:paddingTop");
					element.removeAttribute("android:paddingBottom");
					element.removeAttribute("android:paddingLeft");
					element.removeAttribute("android:paddingRight");
					padding_top = XmlUtil.getSize(padding_top);
					padding_left = XmlUtil.getSize(padding_left);
					padding_right = XmlUtil.getSize(padding_right);
					padding_bottom = XmlUtil.getSize(padding_bottom);
					System.out.println("    "+layout_name+".setPadding("+padding_left+", "+padding_top+", "+padding_right+", "+padding_bottom+");");
			
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
				else if(key.equals("visibility")){
					
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
					System.out.println("    " + element.getAttribute("id") + "." + map.item(ii).getNodeName() + " = "
							+ map.item(ii).getNodeValue() + ";");
			}

			for (int n = 0; n < nodelist.getLength(); n++) {
				Node nodeitem = nodelist.item(n);

				// System.out.println("node item = " + nodeitem.getNodeName() +
				// " value=" + nodeitem.getNodeValue() + " type="
				// + nodeitem.getNodeType() + " ");
				printAndroidCode(element.getNodeName(), element.getAttribute("id"), nodeitem);
			}
		}

		if (node.getNodeType() == 1) {
			Element element1 = (Element) node;
			System.out.println("    " + name + ".addView(" + element1.getAttribute("id") + ","
					+ element1.getAttribute("layoutparams") + ");");
		}
	}

	// 输出Swift代码
	void printSwiftCode(Node node) {

	}

	// 输出kotlin代码
	void printKotlinCode(Node node) {

	}

}

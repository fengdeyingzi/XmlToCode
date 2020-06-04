package com.xl.util.flutter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.lang.model.element.Element;

import com.xl.game.math.Str;
import com.xl.util.XmlUtil;

public class FlutterWidget implements Widget{
	String widgetName; // widget名字
	String layout_width;
	String layout_height;
	String paddingLeft;
	String paddingRight;
	String paddingTop;
	String paddingBottom;
	String marginLeft;
	String marginRight;
	String marginTop;
	String marginBottom;
	HashMap<String, String> map_params;
	
//	String orientation;
	List<Widget> children;
	
	public  FlutterWidget(String name) {
		this.widgetName = name;
		children = new ArrayList<Widget>();
		map_params = new HashMap<String,String>();
	}
	
	public void setWidgetName(String name) {
		this.widgetName = name;
	}
	
	@Override
	public void addParamItem(String name, String value) {
		if(name.equals("android:orientation")){
			if(value.equals("vertical")){
				map_params.put("orientation", "LinearLayout.VERTICAL") ;
			}
			else if(value.equals("horizontal")){
				map_params.put("orientation", "LinearLayout.HORIZONTAL");
			}
			else{
				map_params.put("orientation", "LinearLayout.HORIZONTAL");
			}
		}
		if(name.equals("android:layout_width")){
			if(value.equals("match_parent") || value.equals("fill_parent")){
				layout_width = "MATCH_PARENT";
			}
			else if(value.equals("wrap_content")){
				layout_width = "WRAP_CONTENT";
			}
			else if(value.indexOf("dip")>0 || value.indexOf("dp")>0){
				layout_width = ""+Str.atoi(value);
			}
			else if(value.indexOf("px")>0){
				layout_width = ""+Str.atoi(value)*1.0/3;
			}
			else if (value.startsWith("@dimen/")) {
				layout_width = "DimenUtil.getDimen(\""+value.substring(7)+"\")";
			}
		}
		else if(name.equals("android:layout_height")){
			if(value.equals("match_parent") || value.equals("fill_parent")){
				layout_height = "MATCH_PARENT";
			}
			else if(value.equals("wrap_content")){
				layout_height = "WRAP_CONTENT";
			}
			else if(value.indexOf("dip")>0 || value.indexOf("dp")>0){
				layout_height = ""+Str.atoi(value);
			}
			else if(value.indexOf("px")>0){
				layout_height = ""+Str.atoi(value)*1.0/3;
			}
			else if (value.startsWith("@dimen/")) {
				layout_height = "DimenUtil.getDimen(\""+value.substring(7)+"\")";
			}
		}
		else if(name.equals("android:layout_weight")){
			
		}
		else if(name.equals("android:paddingTop")){
			paddingTop = getValue(value);
		}
		else if(name.equals("android:paddingBottom")){
			paddingBottom = getValue(value);
		}
		else if(name.equals("android:paddingLeft")){
			paddingLeft = getValue(value);
		}
		else if(name.equals("android:paddingRight")){
			paddingRight = getValue(value);
		}
		else if(name.equals("android:padding")){
			paddingLeft = getValue(value);
			paddingRight = getValue(value);
			paddingTop = getValue(value);
			paddingBottom = getValue(value);
		}
		else if(name.equals("android:marginTop")){
			marginTop = getValue(value);
		}
		else if(name.equals("android:marginBottom")){
			marginBottom = getValue(value);
		}
		else if(name.equals("android:marginLeft")){
			marginLeft = getValue(value);
		}
		else if(name.equals("android:marginRight")){
			marginRight = getValue(value);
		}
		else if(name.equals("android:margin")){
			marginTop = getValue(value);
			marginBottom = getValue(value);
			marginLeft = getValue(value);
			marginRight = getValue(value);
		}
		else if(name.equals("android:text")){
			
			map_params.put("text", getString(value));
		}
		else if(name.equals("android:gravity")){
			String gravity = "";
			if(value.indexOf("center")>=0){
				gravity += "|Gravity.CENTER";
			}
			if(value.indexOf("top")>=0){
				gravity += "|Gravity.TOP";
			}
			if(value.indexOf("right")>=0){
				gravity += "|Gravity.RIGHT";
			}
			if(value.indexOf("bottom")>=0){
				gravity += "|Gravity.BOTTOM";
			}
			if(gravity.length()>0)
			map_params.put("gravity", gravity.substring(1));
		}
		else if(name.equals("android:layout_gravity")){
			String gravity = "";
			if(value.indexOf("center")>=0){
				gravity += "|Gravity.CENTER";
			}
			if(value.indexOf("top")>=0){
				gravity += "|Gravity.TOP";
			}
			if(value.indexOf("right")>=0){
				gravity += "|Gravity.RIGHT";
			}
			if(value.indexOf("bottom")>=0){
				gravity += "|Gravity.BOTTOM";
			}
			if(gravity.length()>0)
			map_params.put("layout_gravity", gravity.substring(1));
		}
		else if(name.equals("android:hint")){
			map_params.put("hint", getString(value));
		}
		else if(name.equals("android:src")){
			map_params.put("src",getDrawable(value));
		}
		else if(name.equals("android:background")){
			if(value.startsWith("#") || value.startsWith("@color/")){
				map_params.put("backgroundColor", getColor(value));
			}
			else{
				map_params.put("background", getDrawable(value));
			}
		}
		else if(name.equals("android:backgroundColor")){
			map_params.put("backgroundColor", getColor(value));
		}
		else if(name.equals("android:tint")){
			map_params.put("tint", getColor(value));
			
		}
		else if(name.equals("android:ellipsize")){
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
			map_params.put("ellipsize", elipsize);
		}
		else if(name.equals("android:inputType")){
			
		}
		else if(name.equals("android:textAlignment")){
			String textAlignment = "";
			if(value.indexOf("center")>=0){
				textAlignment += "|TextAlignment.center";
			}
			if(value.indexOf("left")>=0){
				textAlignment += "|TextAlignment.left";
			}
			if(value.indexOf("top")>=0){
				textAlignment += "|TextAlignment.top";
			}
			if(value.indexOf("right")>=0){
				textAlignment += "|TextAlignment.right";
			}
			if (value.indexOf("bottom")>=0) {
				textAlignment += "|TextAlignment.bottom";
			}
			if(textAlignment.length()>0){
				map_params.put("textAlignment", textAlignment);
			}
		}
		else if(name.equals("android:minHeight")){
			map_params.put("minHeight", getValue(value));
		}
		else if(name.equals("android:maxHeight")){
			map_params.put("maxHeight", getValue(value));
		}
		else if(name.equals("android:minWidget")){
			map_params.put("minWidget", getValue(value));
		}
		else if(name.equals("android:maxWidget")){
			map_params.put("maxWidget", getValue(value));
		}
		else if(name.equals("android:shadowColor")){
			map_params.put("shadowColor", getColor(value));
		}
		else if(name.equals("android:scaleType")){
			
		}
		else if(name.equals("android:alpha")){
			map_params.put("alpha", (value));
		}
		else if(name.equals("android:selectable")){
			map_params.put("selectable", value);
		}
		else if(name.equals("android:singleLine")){
			map_params.put("singleLine", value);
		}
		else if(name.equals("android:ems")){
			map_params.put("ems",value);
		}
		else if(name.equals("android:lines")){
			map_params.put("lines", value);
		}
		else if(name.equals("android:minLines")){
			map_params.put("minLines", value);
		}
		else if(name.equals("android:maxLines")){
			map_params.put("maxLines", value);
		}
		else if(name.equals("android:drawable")){
			map_params.put("drawable", getDrawable(value));
		}
		
	}
	
	//获取一个数值
	String getValue(String value) {
		String layout_height = null;
		if (value.equals("match_parent") || value.equals("fill_parent")) {
			layout_height = "double.infinity";
		} else if (value.indexOf("dip") > 0 || value.indexOf("dp") > 0) {
			layout_height = "" + Str.atoi(value);
		} else if (value.indexOf("px") > 0) {
			layout_height = "" + Str.atoi(value) * 1.0 / 3;
		} else if (value.startsWith("@dimen/")) {
			layout_height = "DimenUtil.getDimen(\"" + value.substring(7) + "\")";
		}
		return layout_height;
	}
	
	String getString(String value){
		if(value.startsWith("@string/")){
			return "StringUtil.getString(context, \"" + value.substring(8)+"\")";
		}
		return "\""+value+"\"";
	}
	
	String getColor(String value){
		if(value.startsWith("@color/")){
			return "ColorUtil.getString(context, \"" + value.substring(7)+"\")";
		}
		else if(value.startsWith("#")){
			value = XmlUtil.getColorHex(value);
			return "Color(value)";
		}
		return value;
	}
	
	String getDrawable(String value){
		if(value.startsWith("@drawable/")){
			return "DrawableUtil.getDrawable(context, \""+value.substring(10)+"\")";
		}
		if(value.startsWith("@mipmap/")){
			return "DrawableUtil.getDrawable(context, \""+value.substring(8)+"\")";
		}
		return value;
	}

	@Override
	public List<Widget> getChildList() {
		
		return children;
	}
	
	public void addChild(FlutterWidget widget){
		children.add(widget);
	}
	
	@Override
	public String toString() {
		boolean isLayout = false;
		if (widgetName.equals("LinearLayout")) {
			isLayout = true;
		}
		if (widgetName.equals("FrameLayout")) {
			isLayout = true;
		}
		if (widgetName.equals("Toolbar")) {
			isLayout = true;
		}
		if (widgetName.equals("RelativeLayout")) {

		}
		StringBuffer buffer = new StringBuffer();
		buffer.append(widgetName);
		buffer.append("(\n");
		if(layout_width!=null){
			buffer.append("    layout_width:"+layout_width+",\n");
		}
		if(layout_height!=null){
			buffer.append("    layout_height:"+layout_height+",\n");
		}
		  Iterator iter1 = map_params.entrySet().iterator();
          while (iter1.hasNext()) {
              Map.Entry entry = (Map.Entry) iter1.next();
              String key = (String) entry.getKey();
              String value = (String) entry.getValue();
              if(value!=null){
                  buffer.append("    "+key+":"+value+",\n");
              }
          }
		List<Widget> list_widget = getChildList();
		if (list_widget.size() != 0) {
			if (isLayout)
				buffer.append("    children:[\n");
			else {
				buffer.append("    child:");
			}
			for (int i = 0; i < list_widget.size(); i++) {
				buffer.append(list_widget.get(i).toString());
			}
			if (isLayout)
				buffer.append("    ],\n");
		}
		buffer.append("),\n");
		return buffer.toString();
	}

}

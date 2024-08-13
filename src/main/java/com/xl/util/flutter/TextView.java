package com.xl.util.flutter;

import java.util.ArrayList;
import java.util.List;

import com.xl.game.math.Str;

public class TextView implements Widget{
	String widgetName = "TextView";
	String layout_width;
	String layout_height;
	String textSize;
	String textColor;
	String backgroundColor;
	String background;
	
	String paddingLeft;
	String paddingTop;
	String paddingRight;
	String paddingBottom;
	
	String marginTop;
	String marginBottom;
	String marginLeft;
	String marginRight;
	
	String layout_gravity;
	String gravity;
	
	public void addParamItem(String name,String value){
		if(name.equals("android:layout_width")){
			if(value.equals("match_parent") || value.equals("fill_parent")){
				layout_width = "double.infinity";
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
				layout_height = "double.infinity";
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
			String padding = getValue(value);
			paddingLeft = padding;
			paddingTop = padding;
			paddingRight = padding;
			paddingBottom = padding;
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
			String margin = getValue(value);
			marginLeft = margin;
			marginTop = margin;
			marginRight = margin;
			marginBottom = margin;
		}
		else if(name.equals("android:gravity")){
			StringBuffer buf = new StringBuffer();
			if(value.indexOf("left")>=0){
				buf.append("|Gravity.left");
			}
			if(value.indexOf("top")>=0){
				buf.append("|Gravity.top");
			}
			if(value.indexOf("right")>=0){
				buf.append("|Gravity.right");
			}
			if(value.indexOf("bottom")>=0){
				buf.append("|Gravity.bottom");
			}
			if(value.indexOf("center")>=0){
				buf.append("|Gravity.center");
			}
			String buf_text = buf.toString();
			if(buf_text.length()>0){
				gravity = buf_text.substring(1);
			}
		}
	}
	
	//获取一个数值
	String getValue(String value){
		String layout_height = null;
		if(value.equals("match_parent") || value.equals("fill_parent")){
			layout_height = "double.infinity";
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
		return layout_height;
	}
	

	@Override
	public List<Widget> getChildList() {
		return new ArrayList<Widget>();
		
	}
	
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		
		buffer.append(widgetName);
		buffer.append("(\n");
		if(layout_width!=null){
			buffer.append("width:"+layout_width+",\n");
		}
		if(layout_height!=null){
			buffer.append("height:"+layout_height+",\n");
		}
		if(layout_gravity!=null){
			
		}
		if(gravity!=null){
			buffer.append("gravity:"+gravity+"\n");
		}
	
		buffer.append(")\n");
		return buffer.toString();
	}

}

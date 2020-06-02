package com.xl.util.flutter;

import java.util.List;

import com.xl.game.math.Str;

public class LinearLayout implements Widget{
	
	String layout_width;
	String layout_height;
	String padding;
	String paddingLeft;
	String paddingRight;
	String paddingTop;
	String paddingBottom;
	String margin;
	String marginLeft;
	String marginRight;
	String marginTop;
	String marginBottom;
	String widgetName;
	String orientation;
	List<Widget> children;
	
	public void addParamItem(String name,String value){
		if(name.equals("android:orientation")){
			if(orientation.equals("vertical")){
				orientation = ".vert";
			}
			else if(orientation.equals("horizontal")){
				orientation = ".horz";
			}
			else{
				orientation = ".horz";
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
			padding = getValue(value);
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
			margin = getValue(value);
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
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(widgetName);
		buffer.append("(\n");
		List<Widget> list_widget = getChildList();
		buffer.append("children:[\n");
		for(int i=0;i<list_widget.size();i++){
			buffer.append(list_widget.get(i).toString());
		}
		buffer.append("]\n");
		buffer.append(")\n");
		return buffer.toString();
	}

	@Override
	public List<Widget> getChildList() {
		// TODO Auto-generated method stub
		return children;
	}

}

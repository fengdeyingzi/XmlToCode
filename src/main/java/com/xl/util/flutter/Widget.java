package com.xl.util.flutter;

import java.util.List;

public interface Widget {
	void addParamItem(String name,String value);
	public List<Widget> getChildList();
	public String toString();
}

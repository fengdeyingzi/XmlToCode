package com.xl.util;
import java.util.ArrayList;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.xl.game.math.Str;

public class XmlUtil {
	
	//读取颜色值
		public static int getColor(String text)
		{
			int color=0;
			int argb[]=new int[4];
			int start=0;
			int i=0;
			int hex=0; //颜色位数 有3 4 6 8
			for(i=0;i<text.length();i++)
			{
				if(text.charAt(i)=='#')
				{
					start=i+1;
					hex=text.length()-start;
				}

			}
			if(hex==3)
			{
				for(i=0;i<3;i++)
				{
					char c=text.charAt(start+i);
					argb[0]=0xff;
					if(c>='A'&&c<='F')
					{
						argb[i+1]=(c-'A'+10)*16 + (c-'A'+10);
					}
					else if(c>='a'&&c<='f')
					{
						argb[i+1]=(c-'a'+10)*16 + (c-'a'+10);
					}
					else if(c>='0'&&c<='9')
					{
						argb[i+1]=(c-'0')*16 + (c-'0');
					}
				}
			}
			else if(hex==6)
			{
				argb[0]=0xff;
				for(i=0;i<3;i++)
				{
					char c=text.charAt(start+i*2);
					char c2=text.charAt(start+i*2+1);

					if(c>='A'&&c<='F')
					{
						argb[i+1]=(c-'A'+10)<<4;
					}
					else if(c>='a'&&c<='f')
					{
						argb[i+1]=(c-'a'+10)<<4;
					}
					else if(c>='0'&&c<='9')
					{
						argb[i+1]=(c-'0')<<4;
					}
					if(c2>='A'&&c2<='F')
					{
						argb[i+1]|=(c2-'A'+10);
					}
					else if(c2>='a'&&c2<='f')
					{
						argb[i+1]|=(c2-'a'+10);
					}
					else if(c2>='0'&&c2<='9')
					{
						argb[i+1]|=(c2-'0');
					}
				}
			}
			else if(hex==4)
			{
				for(i=0;i<4;i++)
				{
					char c=text.charAt(start+i);
					if(c>='A'&&c<='Z')
					{
						argb[i]=((c-'A')+10)*16 + ((c-'A')+10);
					}
					else if(c>='a'&&c<='z')
					{
						argb[i]=(c-'a'+10)*16 + (c-'a'+10);
					}
					else if(c>='0'&&c<='9')
					{
						argb[i]=(c-'0')*16 + (c-'0');
					}
				}
			}
			else if(hex==8)
			{
				for(i=0;i<4;i++)
				{
					char c=text.charAt(start+i*2);
					char c2=text.charAt(start+i*2+1);
					if(c>='A'&&c<='F')
					{
						argb[i]=(c-'A'+10)<<4;
					}
					else if(c>='a'&&c<='f')
					{
						argb[i]=(c-'a'+10)<<4;
					}
					else if(c>='0'&&c<='9')
					{
						argb[i]=(c-'0')<<4;
					}
					if(c2>='A'&&c2<='F')
					{
						argb[i]|=(c2-'A'+10);
					}
					else if(c2>='a'&&c2<='f')
					{
						argb[i]|=(c2-'a'+10);
					}
					else if(c2>='0'&&c2<='9')
					{
						argb[i]|=(c2-'0');
					}
				}
			}
			color=(argb[0]<<24)|(argb[1]<<16)|(argb[2]<<8)|argb[3];

			return color;
		}
		
		
		public static String getColorHex(String text){
			return String.format("0x%08x", getColor(text));
		}
		
		//读取长度信息
		public static String getSize(String text)
		{
			if(text.startsWith("@dimen/")){
				return "getResources().getDimensionPixelSize(R.dimen."+text.substring(Str.strrchr(text, '/')+1)+")";
			}
			else if(text.endsWith("dp")||text.endsWith("dip"))
			{
				return "DisplayUtil.dip2px(context, Str.atoi(\""+text+"\"))";
			}
			else if(text.endsWith("sp"))
			{
				return "DisplayUtil.sp2px(context, Str.atoi(\""+text+"\"))";
			}
			else if(text.endsWith("px"))
			{
				return ""+Str.atoi(text);
			}
			return ""+Str.atoi(text);
		}
		
		//读取字号
		public static String getFontSize(String text)
		{
			if(text.startsWith("@dimen/")){
				return "DisplayUtil.px2sp(context,getResources().getDimension(R.dimen."+text.substring(Str.strrchr(text, '/')+1)+"))";
			}
			else if(text.endsWith("dp")||text.endsWith("dip"))
			{
				return "DisplayUtil.dip2sp(context, Str.atoi(\""+text+"\"))";
			}
			else if(text.endsWith("sp"))
			{
				return "Str.atoi(\""+text+"\")";
			}
			else if(text.endsWith("px"))
			{
				return "DisplayUtil.px2sp(context, Str.atoi(\""+text+"\"))";
			}
			return "Str.atoi(\""+text+"\")";
		}
		
		//读取字号
	public static String getiOSFontSize(String text)
	{
		if (text.startsWith("@dimen/")) {
			return "DisplayUtil.px2sp(context,DimenUtil.getDimen(\""
					+ text.substring(Str.strrchr(text, '/') + 1) + "\"))";
		} else if (text.endsWith("dp") || text.endsWith("dip")) {
			return "DisplayUtil.dip2sp(context, Str.atoi(\"" + text + "\"))";
		} else if (text.endsWith("sp")) {
			return "Str.atoi(\"" + text + "\")";
		} else if (text.endsWith("px")) {
			return "DisplayUtil.px2sp(context, Str.atoi(\"" + text + "\"))";
		}
		return "Str.atoi(\"" + text + "\")";
	}
		
		//读取string
		public static String getString(String text){
			if(text.startsWith("@string/")){
				return "R.string."+text.substring(Str.strrchr(text, '/')+1);
			}
			return text;
		}
		
		public static String getiOSString(String text) {
			if(text.startsWith("@string/")){
				return "StringUtil.getString(context,\""+text.substring(Str.strrchr(text, '/')+1)+"\")";
			}
			return text;
		}
		
		//
		public static String getDrawable(String value){
			if(value.startsWith("@drawable/")){
				value = "R.drawable."+value.substring(10);
			}
			else if(value.startsWith("@mipmap/")){
				value = "R.mipmap."+value.substring(8);
			}
			else if(value.startsWith("@android:drawable/")){
				value = "android.R.drawable."+value.substring(18);
			}
			else{
				return value;
			}
			return value;
		}
		
		//读取真或假
		public static boolean getBoolean(String text)
		{
			if(text.equals("true"))
			{
				return true;
			}
			return false;
		}
		
		//读取浮点数
		public static float getFloat(String text)
		{
			int i=0;
			char c=0; 
			float f=0;
			int type=0;
			for(i=0;i<text.length();i++)
			{
				c=text.charAt(i);
				if(c>='0'&&c<='9')
				{
					if(type==0)
						f=f*10+(c-'0');
					else if(type==1)
						f=(f*10+(c-'0'))/10;
				}
				else if(c=='.')
				{
					type=1;
				}
			}
			return f;
		}
		
		//获取element下面的子element
		public static ArrayList<Element> getChildElement(Element eleParent){
			ArrayList<Element> list_child = new ArrayList<>();
			NodeList views = eleParent.getChildNodes();
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
							list_child.add(item);
						}
						else{
							System.out.println("item child "+nodeitem.getNodeName());
						}
					}
				}
				else{
					System.out.println("view child "+nodeitem.getNodeName());
					
					Element item = (Element) nodeitem;
					list_child.add(item);
					String layout_width = item.getAttribute("android:layout_width");
					String layout_height = item.getAttribute("android:layout_height");
					System.out.println("chile layout_width = "+layout_width);
					
				}
				
			}
			return list_child;
		}
		

}

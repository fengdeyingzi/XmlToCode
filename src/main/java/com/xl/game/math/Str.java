package com.xl.game.math;
import java.util.*;
import java.io.*;


/*

 字符处理
 */


public class Str
{
//检测gbk编码
	/*
	GBK应该是更简单的，因为汉字的GBK编码也是把一个汉字用两个字节来表示，其首字节对应0x81-0xFE(即129-224)，
	尾字节对应除掉(0x7F)的0x40-oxFE(即64-126和128-224)。
	*/
	
	public static boolean isGBK(byte[] buf,int size)
	{
		boolean isGBK=true;
		int start = 0;
		int end = size;
		int c=0;
		int c2=0;
		int type=0;
		StringBuffer bufer=new StringBuffer();
		while (start < end)
		{
			c = buf[start]&0xff;
			bufer.append(c);
			switch(type)
			{
				case 0:
					if(c>=0x81 && c<=0xfe)
					{
						type=1;
					}
					else if(c<128)
					{

					}
					else
					{
						isGBK=false;
						break;
					}
					break;
				case 1:
					if(c!=0x7f && c>=0x40 && c<=(0xfe))
					{
						type=0;
					}
					else
					{
						isGBK=false;
						break;
					}
			}


			start++;
		}
//		Log.e("isGb",""+start);
		return isGBK;
	}
	
	/*
	a0 =
	a0+ =
	*/
  public static boolean isGB2312(byte[] buf,int size)
	{
		boolean isGBK=true;
		int start = 0;
		int end = size;
		int c=0;
		int c2=0;
		int type=0;
		StringBuffer bufer=new StringBuffer();
		GBWHILE:
		while (start < end)
		{
			c = buf[start]&0xff;
			bufer.append(c);
			switch(type)
			{
				case 0:
			if(c>=0xa1 && c<=0xf7)
			{
				type=1;
			}
			else if(c<128)
			{
				
			}
			else
			{
				isGBK=false;
				break GBWHILE;
			}
			  break;
				case 1:
					if(c>=0xa1 && c<=(0xa0+94))
					{
						type=0;
					}
					else
					{
						isGBK=false;
						break GBWHILE;
					}
			}
			
			
			start++;
		}
//		Log.e("isGb",""+start);
		return isGBK;
	}
	
	//检测是否为UTF-8编码
	public static boolean isUTF8(byte[] pBuffer, int size)
	{
	boolean IsUTF8 = true;
	int start = 0;
	int end = size;
	int c=0;
	while (start < end)
	{
	c = pBuffer[start]&0xff;
	 if(c==0xf0) //emoj表情 f09f
		{
			start+=4;
		}
	else if (c < 0x80) // (10000000): 值小于0x80的为ASCII字符
	{
	start++;
	}
	else if (c < (0xC0)) // (11000000): 值介于0x80与0xC0之间的为无效UTF-8字符
	{
	IsUTF8 = false;
	break;
	}
	
	else if (c < (0xE0)) // (11100000): 此范围内为2字节UTF-8字符
	{
	if (start >= end - 1)
		break;
	if ((pBuffer[start+1] & (0xC0)) != 0x80)
	{
	IsUTF8 = false;
	break;
	}
	start += 2;
	}
	else if (c < (0xF0)) // (11110000): 此范围内为3字节UTF-8字符
	{
	if (start >= end - 2)
		break;
	if ((pBuffer[start+1] & (0xC0)) != 0x80 || (pBuffer[start+2] & (0xC0)) != 0x80)
	{
	IsUTF8 = false;
	break;
	}
	start += 3;
	}
	else
	{
	IsUTF8 = false;
	break;
	}
	}
	return IsUTF8;
	}
	
	//检测ascii编码
	private boolean isAscii(byte[] buf,int len)
	{
	if(len>buf.length)len=buf.length;
	for(int i=0;i<len;i++)
	{
	if((buf[i]&0xff)>=128)
	{
	return false;
	}
	}
	return true;
	}
	
	//把一个字符串中的大写转为小写
	public static String toLower(String str)
	{  
	StringBuffer sb = new StringBuffer(200);  
	if (str != null)
	{  
	for (int i=0;i < str.length();i++)
	{  
	char c = str.charAt(i);  
	if (Character.isUpperCase(c))//判断是否为大写
	{  
	sb.append(Character.toLowerCase(c));  
	}
	else
	{  
	sb.append(c);   
	}  
	}  
	}  

	return sb.toString();  
	}

	//把字符串中的小写转换为大写
	public static String toUpper(String str)
	{  
	StringBuffer sb = new StringBuffer(200);  
	if (str != null)
	{  
	for (int i=0;i < str.length();i++)
	{  
	char c = str.charAt(i);  
	if (Character.isLowerCase(c))//判断是否为小写
	{  
	sb.append(Character.toUpperCase(c));  
	}
	else
	{  
	sb.append(c);   
	}  
	}  
	}  

	return sb.toString();  
	}
	
	//检测是否为中文
		public static boolean checkCh(String text)
		{
			if(text.getBytes().length==text.length())//这句就是来判断 String是否含有中文字符。
			{
			 return false;
			}
			else
			{
				return true;
			}
		}
		//处理参数列表，生成int数组
		public static	void atoiEx(char[] text,int ptr,int[] per)
			{
				
				int i =0;
				int math = 0;
				for(;ptr<text.length;ptr++)
					{
						switch(text[ptr])
							{
								case '\n':
									per[i]=math;
									math=0;
									i++;
									return;

								//	break;
								case '0':
								case '1': 
								case '2': 
								case '3': 
								case '4': 
								case '5': 
								case '6': 
								case '7': 
								case '8': 
								case '9': 
									math=math*10+(text[ptr]-48);
									break;
								case ',':

									per[i]=math;
									math=0;
									i++;

									break;
								case '-':
									math=-math;
                  break;
								default:
									//per[i]=math;
									//i++;
									//math=0;

							}
						

					}
			}


 //将char[]转换为int
		public static int atoi(char[] text,int ptr)
			{
				
				int math = 0;
				if(text.length==0)return 0;
				//跳过0
				while(text[ptr]=='0')
					{
						ptr++;
					}

				while(ptr<text.length)
					{
						switch(text[ptr])
							{
								case '0': 
								case '1': 
								case '2': 
								case '3': 
								case '4': 
								case '5': 
								case '6': 
								case '7': 
								case '8': 
								case '9': 
									math=math*10+(text[ptr]-48);

									break;
								case ' ':

									break;
								case '-':
									math=-math;
									break;
								default:
									return math;
							}
						ptr++;
					}
				return math;
			}

	public static int atoi(String text)
	{
    if(text==null)return 0;
		int math = 0;
		int ptr=0;
		boolean isf=false;
		//跳过非数字
	/*	
		while(text.charAt(ptr)>'9' || text.charAt(ptr)<'0')
		{
			ptr++;
		}
*/
		while(ptr<text.length())
		{
			switch(text.charAt(ptr))
			{
				case '0': 
				case '1': 
				case '2': 
				case '3': 
				case '4': 
				case '5': 
				case '6': 
				case '7': 
				case '8': 
				case '9': 
					math=math*10+(text.charAt(ptr)-48);

					break;
				case ' ':

					break;
				case '-':
					isf=true;
					break;
				default:
					return math;
			}
			ptr++;
		}
		if(isf)
			return -math;
		return math;
	}

	public static Double atodouble(String text){
  	try {
		return new Double(text);
	}catch (NumberFormatException e){
  		e.printStackTrace();
  		return 0.0d;
	}
	}
			
			
		//在text中查找str	
		public static int strstr(char text[],int ptr,char str)
			{
				int i;
				
				for(i=ptr;i<text.length;i++)
					{
						if(text[i]==str)
							return i;
					}
				return -1;
			}
		
		//在text中查找text2
		public static int strstr(byte[] text, byte[] text2)
		{
			int type=0;
			
			
			for(int i=0;i<text.length;i++)
			{
				switch(type)
				{
					case 1:
						
					  break;
					case 2:
						break;
				}
				if(text[i]==text2[0])
				{
					//检测是否相同
					for(int t=0;t<text2.length;t++)
					{
						if(text[i+t]!=text2[t])
						break;
						if(t==text2.length-1)
						{
							return i;
						}
					}
				}
				
			}
		return -1;
		}

//从buf所指内存区域的前count个字节查找字符ch，当第一次遇到字符ch时停止查找。
//如果成功，返回指向字符ch的指针；否则返回NULL。
			public static int memchr(char text[],char c)
			{
				int ptr=0;
				while(ptr<text.length)
				{
					if(text[ptr]==c)
					{
						return ptr;
					}
					ptr++;
				}
				return -1;
			}
			
		//查找一个字符在字符串中最后一次出现的位置
		public static int strrchr(char text[],char c)
		{
			int ptr=text.length-1;
			while(ptr>=0)
			{
				if(text[ptr]==c)
				{
					return ptr;
				}
				ptr--;
			}
			
			return -1 ;
		}
		
	public static int strrchr(String text,char c)
	{
	int ptr=text.length() -1;
	while(ptr>=0)
	{
	if(text.charAt(ptr)==c)
	{
	return ptr;
	}
	ptr--;
	}

	return -1 ;
	}
		//将text2以0结尾的内容复制到text
		void strcpy(char text[],int ptr,char text2[],int ptr2)
		{
			int i=0;
			while(i<text2.length)
			{
				if(text2[i]!=0)
				text[ptr+i]=text2[ptr2+i];
			}
			
			
			
		}
		
		//删除ptr处size长度的字符
		public static void strremove(char text[], int ptr, int size)
		{
			int end=ptr+size;
			
			while(end<text.length)
			{
			text[ptr]=text[end];
			ptr++;
			end++;
			}
		}

		//计算char长度
		public static int strlen(char text[])
		{
			int len=0;
			for(len=0;len<text.length;len++)
			{
				if(text[len]==0)
					return len;
			}
			return len;
		}
		
		//比较字符串
		public static int strcmp(char s1[],int ptr1,char s2[],int ptr2)
		{
			int i=0;
			while(ptr2<s2.length)
			{
			if(s1[ptr1]!=s2[ptr2])
				return -1;
			ptr1++;ptr2++;
			}
			return 0;
		}
		
		//比较char[]和string，ptr为char处指针，成功返回true，失败返回false
		public static boolean strcmp(char[] text, int ptr,String str)
		{
			int i=0;
			int j=0;
			//检测字符串是否有足够长
			if(str.length()>text.length-ptr)
			{return false;}
			while(i<str.length())
			{
				if(text[ptr+i]==str.charAt(i))
				{
					i++;
				}
				else
				{
					return false;
				}
			}
		return true;
		}
		
	public static boolean strcmp(String text, int ptr,String str)
	{
		int i=0;
		int j=0;
		//检测字符串是否有足够长
		if(str.length()>text.length()-ptr)
		{return false;}
		while(i<str.length())
		{
			if(text.charAt(ptr+i)==str.charAt(i))
			{
				i++;
			}
			else
			{
				return false;
			}
		}
		return true;
	}
		
		
		
		//在char[]末尾加入字符
		public void strcat(char text[],String str)
		{
			int len=strlen(text);
			for(int i=0;i<str.length();i++)
			{
			text[len+i]=str.charAt(i);
			}
		}
		
		
		
		//测试字符串是否以指定后缀结束
		public static int strhz(char text[],char hz[])
		{
			int ptr=0;
			ptr=strrchr(text,'.');
			if(ptr==-1)return -1;
			return strcmp(text,ptr,hz,0);
				
		}
		
		
		//获取ptr处size长度字符
	public static char[] get(char text[],int ptr,int size)
	{
		char out[]=new char[size];
		for(int i=0;i<size;i++)
		{
			out[i]=text[ptr+i];
		}
		return out;
	}
	
	public static ArrayList<String> getArray(ArrayList<String> array)
	{
		ArrayList<String> list = new ArrayList<String>();
		StringSorter fileSorter=new StringSorter();
		TreeSet treeSet = new TreeSet(fileSorter);


		for (int i = 0; i < array.size(); ++i)
		{
			treeSet.add(array.get(i));
			//Logcat.e(arrfile[i].getPath());
		}
		Iterator iterator = treeSet.iterator();
		while (iterator.hasNext())
		{
			//String item = (String)iterator.next();
			list.add((String)iterator.next());
		}
		return list;
	}
	
	
	static class StringSorter
	implements Comparator<String>
	{
		
		public StringSorter()
		{
		}
  
  //gbk编码比较
		public int compare(String str1,String str2){
			try {
				byte[] b1 = str1.getBytes("GBK");
				byte[] b2 = str2.getBytes("GBK");
				int l1=b1.length;
				int l2=b2.length;
				int l=Math.min(l1, l2);
				int k=0;
				while(k<l){
					int bt1=b1[k]&0xff;
					int bt2=b2[k]&0xff;
					if(bt1!=bt2)
					  return bt1-bt2;
					k++;
				}
				return l1-l2;
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			return 0;
		}
		
		

		
		
		
		//字符串比较
		public static int strcmp(byte[] str1, byte[] str2)
		{
			int len1=str1.length;
			int len2 = str2.length;
			int minlen=Math.min(len1,len2);
			int num=0;
			if(str1==null || str2==null)
			  return 0;
			for(int i=0;i<minlen;i++)
			{
				if(str1[i]<str2[i])
				{
					num=-1; break;
				}
				else if(str1[i]>str2[i])
				{
					num=1; break;
				}
				else
				  continue;
			}
			if(num==0)
			  if(len1<len2)
				{
				  num=-1;
				}
			  else
				{
				  num=1;
				}
			return num;
		}
	}
	
	public static String  sprintf(String format, Object... args)
	{
	return String.format(format,args);
	}
	
	
	//arraylist转array(不造为何这里出错)
	public static String[] ArrayListToArray(ArrayList<String> array)
	{
	//arraylist转数组
	String[] texts = new String[array.size()];
	for(int n=0;n<array.size();n++)
	{
	texts[n] = array.get(n);
	}
	return texts;
	}

	//数组转arraylist
	public static ArrayList<String> ArrayToArrayList(String[] array){
  	ArrayList<String> list= new ArrayList<String>();
  	for(int i=0;i<array.length;i++){
  		list.add(array[i]);
	}
	return list;
	}

	//不区分大小写比较进行strstr
	public static int strstrx(String text, String str){
  		if(text!=null || str!=null){
  			return text.toLowerCase().indexOf(str.toLowerCase());
		}
  		return -1;
	}



	//测试字符串是否以指定后缀结束
	/*
	public static int strhz(char text[],char hz[])
	{
	int ptr=0;
	ptr=strrchr(text,'.');
	if(ptr==-1)return -1;
	return strcmp(text,ptr,hz,0);

	}
*/
	
	
	}

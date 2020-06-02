package com.xl.util;
import java.io.*;
import java.util.*;

public class FileUtils
{
	/*
	 * 列出目录下所有文件
	 */
	public static Collection<File> listFiles(File file,String[] miniType,boolean ischeck)
	{
		ArrayList<File> filelist = new ArrayList();
		File[] files = file.listFiles();
		if(files==null){
			return filelist;
		}
		for(int i=0;i<files.length;i++)
		{
			if(files[i].isFile())
			{
			for(String type:miniType)
			if(files[i].getPath().endsWith(type))
			{
			filelist.add(files[i]);
			break;
			}
			}
			else 
			{
				Collection<File> filelist2 = listFiles(files[i],miniType,ischeck);
				for(File f:filelist2)
				filelist.add(f);
			}
		}
		return filelist;
	}
	
	public static Collection<File> listFiles(File file,boolean ischeck)
	{
		ArrayList<File> filelist = new ArrayList();
		File[] files = file.listFiles();
		for(int i=0;i<files.length;i++)
		{
			if(files[i].isFile())
			{
			filelist.add(files[i]);
			}
			else 
			{
				Collection<File> filelist2 = listFiles(files[i],ischeck);
				for(File f:filelist2)
				filelist.add(f);
			}
		}
		return filelist;
	}
	
	
	//批量删除目录下指定格式文件
	public static void removeFiles(File path,String[] name)
	{
		Collection<File> files = listFiles(path,name,true);
		for(File file:files)
		{
			file.delete();
		}
	}
	
	public static void writeText(String filename,String info,String coding) {
		File file = new File(filename);


		try
		{
			if (!file.isFile())file.createNewFile();
		}
		catch (Exception e)
		{}
		try
		{
			FileOutputStream fileOutputStream = new FileOutputStream(file, false);
			fileOutputStream.write(info.getBytes(coding));
			fileOutputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static String read(File file,String encoding) throws IOException
	{
		String content = "";
		//	File file = new File(path);

		if(file.isFile())
		{
			FileInputStream input= new FileInputStream(file);

			byte [] buf=new byte[input.available()];
			input.read(buf);
			content = new String(buf,encoding);
		}
		return content;
	}
	
	
}

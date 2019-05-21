package com.webserver.test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * 显示user.dat文件中的所有用户信息
 * @author adminitartor
 *
 */
public class ShowAllUserDemo {
	public static void main(String[] args) throws IOException {
		RandomAccessFile raf
			= new RandomAccessFile("user.dat","r");
	
		for(int i=0;i<raf.length()/100;i++){
			//读取用户名
			byte[] data = new byte[32];
			raf.read(data);
			String username = new String(data,"UTF-8").trim();
			
			raf.read(data);
			String password = new String(data,"UTF-8").trim();
			
			raf.read(data);
			String nickname = new String(data,"UTF-8").trim();
			
			int age = raf.readInt();
			System.out.println("pos:"+raf.getFilePointer());
			
			System.out.println(username+","+password+","+nickname+","+age);
		}//for循环结束
	
		raf.close();
		
	}
}






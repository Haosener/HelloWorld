package com.webserver.servlet;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.Arrays;

import com.webserver.http.HttpRequest;
import com.webserver.http.HttpResponse;

/**
 * ����ע��ҵ��
 * @author adminitartor
 *
 */
public class RegServlet extends HttpServlet{
	public void service(HttpRequest request,HttpResponse response){
		System.out.println("RegServlet:��ʼ����ע��...");
		/*
		 * 1:ͨ��request��ȡ�û���ҳ���������ע����Ϣ
		 * 
		 * 2:�����û���ע����Ϣд�뵽�ļ�user.dat��
		 * 
		 * 3:����response��Ӧע����ҳ��
		 */
		//1
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String nickname = request.getParameter("nickname");
		int age = Integer.parseInt(
			request.getParameter("age")	
		);
		System.out.println("username:"+username);
		System.out.println("password:"+password);
		System.out.println("nickname:"+nickname);
		System.out.println("age:"+age);
		/*
		 * 2
		 */
		try(
			RandomAccessFile raf
				= new RandomAccessFile("user.dat","rw");	
		){
			/*
			 * ���ȶ�ȡuser.dat�ļ��е�ÿ���û���Ϣ
			 * �ȶ��û����뵱ǰע���û��������Ƿ�һ�£�
			 * ��һ����˵�����û����Ѿ���ʹ�ã���ʱ����
			 * response��Ӧ�û���ʾҳ��:���û��Ѵ���
			 * ����ִ������ԭ�е�ע�������
			 * 
			 * �û���ʾҳ��:reg_fail.html
			 */
			for(int i=0;i<raf.length()/100;i++){
				raf.seek(i*100);
				byte[] data = new byte[32];
				raf.read(data);
				String name = new String(data,"UTF-8").trim();
				if(name.equals(username)){
					//��ע���û�
					forward("/myweb/reg_fail.html", request, response);
					
					return;
				}
			}
			
			
			
			
			//�ֽ�ָ���ƶ����ļ�ĩβ�Ա�׷��һ���¼�¼
			raf.seek(raf.length());		
			
			//д�û���
			byte[] data = username.getBytes("UTF-8");
			data = Arrays.copyOf(data, 32);//���ݵ�32�ֽ�
			//��32�ֽ�һ����д��
			raf.write(data);
		
			//д����
			data = password.getBytes("UTF-8");
			data = Arrays.copyOf(data, 32);
			raf.write(data);
			
			//д�ǳ�
			data = nickname.getBytes("UTF-8");
			data = Arrays.copyOf(data, 32);
			raf.write(data);
			
			//д����
			raf.writeInt(age);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//3
		forward("/myweb/reg_success.html", request, response);
		
		System.out.println("RegServlet:����ע��ҵ�����!");
	}
}






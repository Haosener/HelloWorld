package com.webserver.servlet;

import java.io.File;
import java.io.RandomAccessFile;

import com.webserver.http.HttpRequest;
import com.webserver.http.HttpResponse;

/**
 * �����¼ҵ��
 * @author adminitartor
 *
 */
public class LoginServlet extends HttpServlet{
	public void service(HttpRequest request,HttpResponse response){
		System.out.println("LoginServlet:��ʼ��¼...");
		//��ȡ�û���¼��Ϣ
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		try (
			RandomAccessFile raf
				= new RandomAccessFile("user.dat","r");
		){
			for(int i=0;i<raf.length()/100;i++){
				raf.seek(i*100);
				byte[] data = new byte[32];
				raf.read(data);
				String name = new String(data,"UTF-8").trim();
				if(name.equals(username)){
					raf.read(data);
					String pwd = new String(data,"UTF-8").trim();
					if(pwd.equals(password)){
						//��ת��¼�ɹ�ҳ��
						forward("/myweb/login_success.html", request, response);
						return;
					}
					break;
				}
			}
			//��ת��¼ʧ��ҳ��
			forward("/myweb/login_fail.html",request,response);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		
		
		
		System.out.println("LoginServlet:��¼���!");
	}
}







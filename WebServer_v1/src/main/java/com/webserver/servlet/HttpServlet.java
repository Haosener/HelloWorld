package com.webserver.servlet;

import java.io.File;

import com.webserver.http.HttpRequest;
import com.webserver.http.HttpResponse;

/**
 * ����Servlet�ĳ���
 * @author adminitartor
 *
 */
public abstract class HttpServlet {
	public abstract void service(HttpRequest request,HttpResponse response);
	
	/**
	 * ��תָ��·��
	 * (TomCat�ĸ÷���ʵ������ͨ��HttpRequest��ȡ��ת
	 * ����dispatcher��Ӧ�ķ���)
	 * @param path
	 * @param request
	 * @param response
	 */
	public void forward(String path,HttpRequest request,HttpResponse response){
		File file = new File("./webapps"+path);
		response.setEntity(file);
	}
}








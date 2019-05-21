package com.webserver.core;

import java.io.File;
import java.io.IOException;
import java.net.Socket;

import com.webserver.http.HttpRequest;
import com.webserver.http.HttpResponse;
import com.webserver.servlet.HttpServlet;
import com.webserver.servlet.RegServlet;

/**
 * ���ڴ���ͻ�������
 * @author adminitartor
 *
 */
public class ClientHandler implements Runnable{
	private Socket socket;
	
	public ClientHandler(Socket socket){
		this.socket = socket;
	}
	
	public void run(){
		try {
			System.out.println("ClientHandler:��ʼ��������");
			//1 ׼������
			//1.1 ʵ�����������,��������
			HttpRequest request = new HttpRequest(socket);
			//1.2 ʵ������Ӧ����
			HttpResponse response = new HttpResponse(socket);
			
			
			
			/*
			 * 2 ��������
			 * 2.1:ͨ��request��ȡrequestURI,������֪�û�����
			 *     ����Դ��·��
			 * 2.2:��webappsĿ¼�¸��ݸ���Դ·���ҵ���Ӧ
			 *     ��Դ
			 * 2.3:�жϸ���Դ�Ƿ���ʵ���� 
			 * 2.4:��������Ӧ����Դ
			 * 2.5:����������Ӧ404ҳ��       
			 */
			// /myweb/index.html
			String path = request.getRequestURI();
			//�жϸ������Ƿ�Ϊ����ҵ����
			HttpServlet servlet 
				= ServerContext.getServlet(path);
			if(servlet!=null){
				servlet.service(request,response);	
			}else{
				//ͨ��·���ҵ�webappsĿ¼�¶�Ӧ��Դ
				File file = new File("webapps"+path);
				//�ж��û��������Դ�Ƿ���ʵ����
				if(file.exists()){
					System.out.println("ClientHandler:��Դ���ҵ�!");
					//������Դ�Ա�׼��HTTP��Ӧ��ʽ���͸��ͻ���
					
					//��Ҫ��Ӧ����Դ���õ�response��entity������
					response.setEntity(file);	
					
				}else{
					System.out.println("ClientHandler:��Դ������!");
					File f = new File("webapps/root/404.html");
					
					//����״̬���������
					response.setStatusCode(404);
					response.setStatusReason("NOT FOUND");
					
					//������Ӧ����Ϊ404ҳ��
					response.setEntity(f);
					
				}
			}
			
			//3 ������Ӧ
			response.flush();
			
			System.out.println("ClientHandler:�������!");
		
		} catch (EmptyRequestException e){
			System.out.println("������...");
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			//������Ϻ���ͻ��˶Ͽ�����
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}






package com.webserver.servlet;

import java.io.RandomAccessFile;
import java.util.Arrays;

import com.webserver.http.HttpRequest;
import com.webserver.http.HttpResponse;
/**
 * �޸�����ҵ��
 * @author adminitartor
 *
 */
public class UpdateServlet extends HttpServlet{
	public void service(HttpRequest request, HttpResponse response) {
		System.out.println("UpdateServlet:��ʼ�޸�����");
		//1 ��ȡ�û���Ϣ
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String newpassword = request.getParameter("newpassword");
		
		//2�޸Ĺ���
		try (
			RandomAccessFile raf
				= new RandomAccessFile("user.dat","rw");
		){
			for(int i=0;i<raf.length()/100;i++){
				raf.seek(i*100);
				//��ȡ�û���
				byte[] data = new byte[32];
				raf.read(data);
				String name = new String(data,"UTF-8").trim();
				//�ҵ����û�
				if(name.equals(username)){
					//��ȡ����
					raf.read(data);
					String pwd = new String(data,"UTF-8").trim();
					if(pwd.equals(password)){
						//�����޸�
						//�ƶ�ָ�뵽����λ��
						raf.seek(i*100+32);
						data = newpassword.getBytes("UTF-8");
						data = Arrays.copyOf(data, 32);
						raf.write(data);
						//����response��ת�ɹ�ҳ��
						forward("/myweb/update_success.html", request, response);
						return;
					}
					break;
				}			
			}
			
			//�����޸�ʧ��ҳ��
			forward("/myweb/update_fail.html", request, response);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		
		System.out.println("UpdateServlet:�޸��������");
	}

}

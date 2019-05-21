package com.webserver.servlet;

import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.FileTemplateResolver;

import com.webserver.http.HttpRequest;
import com.webserver.http.HttpResponse;

/**
 * ������thymeleaf��ܣ���user.dat�ļ��е��û�
 * ��Ϣչ���ھ�̬ҳ��showAllUser.html�У���
 * ��Ӧ���ͻ���
 * @author Haosener
 *
 */
public class ShowAllUserServlet extends HttpServlet{

	@Override
	public void service(HttpRequest request, HttpResponse response) {
		System.out.println("ShouAllUserServlet:��ʼչʾ�û���Ϣ");
		
		/*
		 * ����һ��������ڱ���user.dat�ļ���
		 * ��ȡ�����������û���Ϣ��������ÿ��Ԫ�ر�ʾһ���û�����Ϣ
		 * ����ʹ��Map��Ϊ����Ԫ�أ�������key���û�
		 * ��������Ϣ���û���������ȣ���value�Ǹ�
		 * ���Զ�Ӧ��ֵ��������123456...������ζ��
		 * ÿ��Mapʵ����ʾһ���û��������Ϣ
		 * 
		 * OGNL���ʽ��˼��..
		 */
		List <Map<String,String >> list = new ArrayList<>();
		try(RandomAccessFile raf = new RandomAccessFile("user.dat","r");) {
			for(int i=0;i<raf.length()/100;i++){
				//��ȡ�û���
				byte[] data = new byte[32];
				raf.read(data);
				String username = new String(data,"UTF-8").trim();
				
				raf.read(data);
				String password = new String(data,"UTF-8").trim();
				
				raf.read(data);
				String nickname = new String(data,"UTF-8").trim();
				
				int age = raf.readInt();
				Map<String,String>user = new HashMap<>();
				user.put("username", username);
				user.put("password", password);
				user.put("nickname", nickname);
				user.put("age", age+"");
				list.add(user);	
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		TemplateEngine eng = new TemplateEngine();
		/*
		 * ������
		 * ���ڸ��������ø��ֲ������Լ�����Ѱ�Ҿ�̬ҳ���
		 * FileTemplateResolver�ǻ����ļ�ϵͳѰ�Ҿ�̬��Դ�����ؾ�̬��Դʹ�õ�·����File��һ��
		 */
		FileTemplateResolver resolver = new FileTemplateResolver();
		//�����ַ������Ա�ģ��������԰�����ȷ���ַ���
		resolver.setCharacterEncoding("utf-8");
		//�����������õ�������
		eng.setTemplateResolver(resolver);
		
		//���ϲ����������ʼ�����
		
		/*
		 * Context������״�ṹ���������Ҫ��
		 * ҳ���ϳ��ֵ�����
		 */
		Context context = new Context();
		context.setVariable("list",list);
		
		/*
		 * �������潫��̬ҳ�������ݽ��а�
		 * ����ֵ��һ���ַ��������ݾ��ǰ󶨺�
		 * ���ݵ�html����
		 */
		String html = eng.process("./webapps/myweb/showAllUser.html", context);
		
		//��Ӧ�ͻ���
		try {
			response.setContentData(html.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

}

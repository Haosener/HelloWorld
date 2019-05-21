package com.webserver.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import com.webserver.core.EmptyRequestException;

/**
 * �������
 * �����ÿһ��ʵ�����ڱ�ʾ��������͹�����һ������
 * ����,ÿ�������������ֹ���(������,��Ϣͷ,��Ϣ����)
 * @author adminitartor
 *
 */
public class HttpRequest {
	/*
	 * �����������Ϣ
	 */
	//����ʽ
	private String method;
	//������Դ�ĳ���·��
	private String url;
	//����ʹ�õ�Э��汾
	private String protocol;
	
	//url��"?"�������󲿷�
	private String requestURI;
	//url��"?"�Ҳ�Ĳ�������
	private String queryString;
	//ÿһ������ key:������   value:����ֵ
	private Map<String,String> parameters = new HashMap<>();
	
	
	/*
	 * ��Ϣͷ�����Ϣ
	 */
	/*
	 * key:��Ϣͷ������
	 * value:��Ϣͷ��Ӧ��ֵ
	 */
	private Map<String,String> headers = new HashMap<>();
	
	
	/*
	 * ��Ϣ���������Ϣ
	 */
	//��Ϣ���ĵ�����
	private byte[] data;
	
	
	
	/*
	 * ��������ص�����
	 */
	private Socket socket;
	private InputStream in;
	
	/**
	 * ��ʼ��HttpRequest����
	 * ��ʼ���Ĺ��̾��ǽ�������Ĺ���,ʵ������Ϻ�
	 * ��ǰHttpRequest����ͱ�ʾ��������͹�����
	 * �������������.
	 * @throws EmptyRequestException 
	 */
	public HttpRequest(Socket socket) throws EmptyRequestException{
		System.out.println("HttpRequest:��ʼ��������...");
		try {
			this.socket = socket;
			this.in = socket.getInputStream();
			/*
			 * �������������:
			 * 1:����������
			 * 2:������Ϣͷ
			 * 3:������Ϣ����
			 */
			parseRequestLine();
			parseHeaders();
			parseContent();
		} catch (EmptyRequestException e){
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("HttpRequest:�����������!");
	}
	/**
	 *	����������
	 * @throws EmptyRequestException 
	 */
	private void parseRequestLine() throws EmptyRequestException{
		System.out.println("��ʼ����������...");
		/*
		 * 1:ͨ����������ȡ��һ���ַ���(����������)
		 * 2:�����������ݰ��տո���Ϊ������
		 * 3:���������������õ���Ӧ������(method,url,protocol)
		 */
		try {
			String line = readLine();
			System.out.println("������:"+line);
			/*
			 * �ж��Ƿ�Ϊ������
			 */
			if("".equals(line)){
				throw new EmptyRequestException();
			}
			/*
			 * ����ѭ�����տͻ������Ӻ�,���������ܻ����
			 * �����±�Խ��,�������ڿ����������,�������
			 */
			String[] data = line.split("\\s");
			this.method = data[0];
			this.url = data[1];//���������±�Խ��
			this.protocol = data[2];
			System.out.println("method:"+method);
			System.out.println("url:"+url);
			System.out.println("protocol:"+protocol);
			
			//��һ����������·������
			parseURL();
			
			
		} catch(EmptyRequestException e){
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("�������������!");
	}
	/**
	 * ��һ�������������г���·������
	 */
	private void parseURL(){
		System.out.println("��ʼ��������·��...");
		/*
		 * һ������������е��г���·���������������:
		 * 1:�����в����ģ���:
		 *   /myweb/index.html
		 * 2:���в����ģ���:
		 *   /myweb/reg?username=xxx&password=xxx&...
		 * ������ǶԳ���·����һ������:
		 * 1:�����ж�url��ֵ�Ƿ���?
		 *   1.1:��������"?"��ֱ�ӽ�url��ֵ��ֵ��
		 *       ����requestURI,��ǰ����������
		 *   1.2:������"?"ִ�в���2
		 * 
		 * 2:��url����"?"���Ϊ�����֣���һ����Ӧ����
		 *   ���󲿷֣���ֵ������requestURI.
		 *   �ڶ�����Ӧ���ǲ������֣���ֵ������queryString
		 *   
		 * 3:��һ������queryString,���䰴��"&"���Ϊ����
		 *   ��������ÿһ�������ٰ���"="���Ϊ�����������
		 *   ֵ��������������Ϊkey������ֵ��Ϊvalue���浽
		 *   ����parameters���Map����ɽ���������
		 */
		if(url.contains("?")){
			String[] data = url.split("\\?");
			requestURI = data[0];
			if(data.length>1){
				queryString = data[1];
				parseParameters(queryString);
			}
		}else{
			requestURI = url;
		}
		
		System.out.println("requestURI:"+requestURI);
		System.out.println("queryString:"+queryString);
		System.out.println("parameters:"+parameters);
		
		System.out.println("��������·�����!");
	}
	/**
	 * ��������
	 * ��ʽΪ:name=value&name=value&...
	 * @param line
	 */
	private void parseParameters(String line){
		String[] data = line.split("&");
		for(String para : data){
			String[] arr = para.split("=");
			if(arr.length>1){
				parameters.put(arr[0], arr[1]);
			}else{
				parameters.put(arr[0], null);
			}
		}
	}
	
	/**
	 * 	������Ϣͷ
	 */
	private void parseHeaders(){
		System.out.println("��ʼ������Ϣͷ...");
		try {
			/*
				 * 1:ѭ������readLine������ȡÿһ����Ϣͷ
				 * 2:����Ϣͷ����": "���,������Ϣͷ������
				 *   ��Ϊkey,��Ϣͷ��ֵ��Ϊvalue���浽����
				 *   headers���Map��
				 * 3:�������readLine�������ص���һ�����ַ�
				 *   ��,��˵�����ε�����ȡ����CRLF,��ô��
				 *   ����ֹͣ������Ϣͷ��.  
				 */
			String line = null;
			while (true) {
				line = readLine();
				if("".equals(line)){
					break;
				}
				String[] data = line.split(": ");
				headers.put(data[0], data[1]);
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("��Ϣͷ:"+headers);
		System.out.println("������Ϣͷ���!");
	}
	/**
	 * 	������Ϣ����
	 * @throws IOException 
	 */
	private void parseContent() throws IOException{
		System.out.println("��ʼ������Ϣ����...");
		//�Ƿ�Ϊpost����
		if("POST".equals(method.toUpperCase())){
			//�鿴�Ƿ���Content-Length
			if(headers.containsKey("Content-Length")){
				int len = Integer.parseInt(
					headers.get("Content-Length")	
				);
				data = new byte[len];
				//��ȡ��Ϣ��������
				in.read(data);
				
				//�ж���Ϣ��������
				String type = headers.get("Content-Type");
				if("application/x-www-form-urlencoded".equals(type)){
					//form������
					String line = new String(data,"ISO8859-1");
					parseParameters(line);	
				}
			}
		}
		
		
		
		System.out.println("������Ϣ�������!");
	}
	
	/**
	 * ͨ����Ӧ�ͻ��˵���������ȡһ���ַ���
	 * (��CRLF��β)
	 * @return
	 * @throws IOException 
	 */
	private String readLine() throws IOException{
		//��ȡһ���ַ���,��CRLF��β
		StringBuilder builder = new StringBuilder();
		//c1��ʾ�ϴζ�ȡ�����ַ�,c2��ʾ���ζ�ȡ�����ַ�
		int c1 = -1,c2 = -1;
		while((c2 = in.read())!=-1){
			//�Ƿ�������ȡ����CR,LF
			if(c1==HttpContext.CR&&c2==HttpContext.LF){
				break;
			}
			builder.append((char)c2);
			c1 = c2;
		}
		return builder.toString().trim();
	}
	
	
	public String getMethod() {
		return method;
	}
	public String getUrl() {
		return url;
	}
	public String getProtocol() {
		return protocol;
	}
	/**
	 * ��ȡָ��������Ϣͷ��Ӧ��ֵ
	 * @param name
	 * @return
	 */
	public String getHeader(String name) {
		return headers.get(name);
	}
	public String getRequestURI() {
		return requestURI;
	}
	public String getQueryString() {
		return queryString;
	}
	/**
	 * ���ݸ����Ĳ�������ȡ��Ӧ�Ĳ���ֵ
	 * @param name
	 * @return
	 */
	public String getParameter(String name){
		return this.parameters.get(name);
	}
	
	
}









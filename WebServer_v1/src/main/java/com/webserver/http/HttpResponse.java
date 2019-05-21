package com.webserver.http;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * ��Ӧ����
 * �����ÿ��ʵ�����ڱ�ʾ���͸��ͻ��˵���Ӧ����
 * 
 * һ����Ӧ����������:״̬�У���Ӧͷ����Ӧ����
 * 
 * @author adminitartor
 *
 */
public class HttpResponse {
	/*
	 * ״̬�������Ϣ
	 */
	/*
	 * ״̬����,Ĭ��ֵ200
	 * Ĭ��ֵΪ200��Ҫԭ������:
	 * 1:�����ָ��Ĭ��ֵ��intĬ��ֵΪ0��������û��
	 *   ����״̬����Ļ���HTTPЭ����û��״̬����Ϊ
	 *   0���������ģ����Բ�����0��ΪĬ��ֵ��
	 * 2:ͨ��һ����������ȷ�����ظ��ͻ���200��
	 *   �Ƚ϶����������Ĭ��ֵ��200�����ڴ󲿷���
	 *   Ӧʱ����ָ��״̬����Ͷ�Ӧ������  
	 */
	private int statusCode = 200;
	//״̬����,Ĭ��ֵOK
	private String statusReason = "OK";
	
	/*
	 * ��Ӧͷ�����Ϣ
	 */
	private Map<String,String> headers = new HashMap<>();
		
	
	
	
	/*
	 * ��Ӧ���������Ϣ
	 */
	//��Ӧ���ĵ�ʵ���ļ�
	private File entity;
	
	//�ֽ�������Ϊ��������(��Ϊ��Ӧ��̬����ʹ��)
	private byte[] data;

	
	/*
	 * ��������ص���Ϣ
	 */
	private Socket socket;
	private OutputStream out;
	/**
	 * ����HttpResponse��ͬʱ��Ҫָ��Socket
	 * ��ǰ��Ӧ�������ͨ�����Socket��ȡ�����
	 * ����Ӧ�ͻ��˷�����Ӧ���ݵ�
	 * @param socket
	 */
	public HttpResponse(Socket socket){
		try {
			this.socket = socket;
			this.out = socket.getOutputStream();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * ����ǰ��Ӧ����������һ����׼HTTP��Ӧ��ʽ
	 * ���͸��ͻ���
	 */
	public void flush(){
		try {			
			//1����״̬��
			sendStatusLine();
			//2������Ӧͷ
			sendHeaders();
			//3������Ӧ����
			sendContent();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * ����״̬��
	 * @throws IOException 
	 */
	private void sendStatusLine() throws IOException{
		System.out.println("HttpResponse:��ʼ����״̬��...");
		//1 ����״̬��
		String line = "HTTP/1.1"+" "+statusCode+" "+statusReason;
		println(line);
		System.out.println("HttpResponse:����״̬�����!");
	}
	/**
	 * ������Ӧͷ
	 */
	private void sendHeaders() throws IOException{
		System.out.println("HttpResponse:��ʼ������Ӧͷ...");
		//2 ������Ӧͷ
		Set<Entry<String,String>> entrySet 
							= headers.entrySet();
		for(Entry<String,String> header: entrySet){
			String name = header.getKey();
			String value = header.getValue();
			String line = name+": "+value;
			println(line);
		}
		
		
		//��������CRLF��ʾ��Ӧͷ����
		println("");
		System.out.println("HttpResponse:������Ӧͷ���!");
	}
	/**
	 * ������Ӧ����
	 */
	private void sendContent() throws IOException{
		System.out.println("HttpResponse:��ʼ������Ӧ����...");
		/*
		 * ���ʵ���ļ����ڣ�����Ϊ���ķ���
		 * ע:��Ӧ������һ�������Բ��������ġ�
		 */
		if(entity!=null){
			//3 ������Ӧ����
			//���û�������ļ�������Ϊ���ķ��͸��ͻ���
			try(
				FileInputStream fis
					= new FileInputStream(entity);
			){
				int len = -1;
				byte[] data = new byte[1024*10];
				while((len = fis.read(data))!=-1){
					out.write(data, 0, len);
				}
			}catch(IOException e){
				throw e;
			}
		}
		/*
		 * ��������ֽ�������ڣ��������ֽ���Ϊ��Ӧ���ķ���
		 */
		if(data!=null) {
			out.write(data);
		}
		System.out.println("HttpResponse:������Ӧ�������!");
	}
	/**
	 * ��ͻ���д��һ���ַ���(�Զ���CR,LF��β)
	 * @param line
	 * @throws IOException 
	 * @throws UnsupportedEncodingException 
	 */
	private void println(String line) throws UnsupportedEncodingException, IOException{
		out.write(line.getBytes("ISO8859-1"));
		out.write(HttpContext.CR);//written CR
		out.write(HttpContext.LF);//written LF
	}
	
	
	
	
	
	
	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusReason() {
		return statusReason;
	}

	public void setStatusReason(String statusReason) {
		this.statusReason = statusReason;
	}

	public File getEntity() {
		return entity;
	}
	/**
	 * ������Ӧ���Ķ�Ӧ��ʵ���ļ�
	 * �������ĵ�ͬʱ���Զ�����Ӧ���������˵��
	 * ��Ӧ���ĵ���Ӧͷ:Content-Type��Content-Length
	 * @param entity
	 */
	public void setEntity(File entity) {
		this.entity = entity;
		this.data = null;
		
		String fileName = entity.getName();
		//��ȡ����Դ�ĺ�׺��
		String ext = fileName.substring(fileName.lastIndexOf(".")+1);
		//�����Ӧͷ
		putHeader("Content-Type",HttpContext.getMimeType(ext));
		putHeader("Content-Length", entity.length()+"");
		
	}
	/**
	 * ��ǰ��Ӧ���������һ����Ӧͷ
	 * @param name ��Ӧͷ����
	 * @param value ��Ӧͷ��Ӧ��ֵ
	 */
	public void putHeader(String name,String value){
		this.headers.put(name, value);
	}
	
	public byte[] getContentData() {
		return data;
	}
	
	/**
	 * ������Ӧ��������
	 * @param data �����ֽڻ���Ϊ��Ӧ�������ݷ��͸��ͻ���
	 */


	public void setContentData(byte[] data) {
		this.data = data;
		this.entity = null;
		
		//�Զ����Content-Lengthͷ
		putHeader("Content-Length",data.length+"");
		
	}
}













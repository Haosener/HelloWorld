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
 * 响应对象
 * 该类的每个实例用于表示发送给客户端的响应内容
 * 
 * 一个响应包含三部分:状态行，响应头，响应正文
 * 
 * @author adminitartor
 *
 */
public class HttpResponse {
	/*
	 * 状态行相关信息
	 */
	/*
	 * 状态代码,默认值200
	 * 默认值为200主要原因两个:
	 * 1:如果不指定默认值，int默认值为0，若我们没有
	 *   设置状态代码的话，HTTP协议是没有状态代码为
	 *   0的这个情况的，所以不能用0作为默认值。
	 * 2:通常一个请求都能正确处理，回复客户端200是
	 *   比较多的情况，因此默认值用200可以在大部分响
	 *   应时不用指定状态代码和对应描述了  
	 */
	private int statusCode = 200;
	//状态描述,默认值OK
	private String statusReason = "OK";
	
	/*
	 * 响应头相关信息
	 */
	private Map<String,String> headers = new HashMap<>();
		
	
	
	
	/*
	 * 响应正文相关信息
	 */
	//响应正文的实体文件
	private File entity;
	
	//字节数据作为正文内容(作为响应动态数据使用)
	private byte[] data;

	
	/*
	 * 与连接相关的信息
	 */
	private Socket socket;
	private OutputStream out;
	/**
	 * 创建HttpResponse的同时需要指定Socket
	 * 当前响应对象就是通过这个Socket获取输出流
	 * 给对应客户端发送响应内容的
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
	 * 将当前响应对象内容以一个标准HTTP响应格式
	 * 发送给客户端
	 */
	public void flush(){
		try {			
			//1发送状态行
			sendStatusLine();
			//2发送响应头
			sendHeaders();
			//3发送响应正文
			sendContent();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 发送状态行
	 * @throws IOException 
	 */
	private void sendStatusLine() throws IOException{
		System.out.println("HttpResponse:开始发送状态行...");
		//1 发送状态行
		String line = "HTTP/1.1"+" "+statusCode+" "+statusReason;
		println(line);
		System.out.println("HttpResponse:发送状态行完毕!");
	}
	/**
	 * 发送响应头
	 */
	private void sendHeaders() throws IOException{
		System.out.println("HttpResponse:开始发送响应头...");
		//2 发送响应头
		Set<Entry<String,String>> entrySet 
							= headers.entrySet();
		for(Entry<String,String> header: entrySet){
			String name = header.getKey();
			String value = header.getValue();
			String line = name+": "+value;
			println(line);
		}
		
		
		//单独发送CRLF表示响应头发送
		println("");
		System.out.println("HttpResponse:发送响应头完毕!");
	}
	/**
	 * 发送响应正文
	 */
	private void sendContent() throws IOException{
		System.out.println("HttpResponse:开始发送响应正文...");
		/*
		 * 如果实体文件存在，则作为正文发送
		 * 注:响应与请求一样，可以不含有正文。
		 */
		if(entity!=null){
			//3 发送响应正文
			//将用户请求的文件数据作为正文发送给客户端
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
		 * 如果正文字节数组存在，则将这组字节作为响应正文发送
		 */
		if(data!=null) {
			out.write(data);
		}
		System.out.println("HttpResponse:发送响应正文完毕!");
	}
	/**
	 * 向客户端写出一行字符串(自动以CR,LF结尾)
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
	 * 设置响应正文对应的实体文件
	 * 设置正文的同时会自动向响应对象中添加说明
	 * 响应正文的响应头:Content-Type和Content-Length
	 * @param entity
	 */
	public void setEntity(File entity) {
		this.entity = entity;
		this.data = null;
		
		String fileName = entity.getName();
		//获取该资源的后缀名
		String ext = fileName.substring(fileName.lastIndexOf(".")+1);
		//添加响应头
		putHeader("Content-Type",HttpContext.getMimeType(ext));
		putHeader("Content-Length", entity.length()+"");
		
	}
	/**
	 * 向当前响应对象中添加一个响应头
	 * @param name 响应头名字
	 * @param value 响应头对应的值
	 */
	public void putHeader(String name,String value){
		this.headers.put(name, value);
	}
	
	public byte[] getContentData() {
		return data;
	}
	
	/**
	 * 设置响应正文数据
	 * @param data 该组字节会作为响应正文内容发送给客户端
	 */


	public void setContentData(byte[] data) {
		this.data = data;
		this.entity = null;
		
		//自动添加Content-Length头
		putHeader("Content-Length",data.length+"");
		
	}
}













package com.webserver.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import com.webserver.core.EmptyRequestException;

/**
 * 请求对象
 * 该类的每一个实例用于表示浏览器发送过来的一个请求
 * 内容,每个请求由三部分构成(请求行,消息头,消息正文)
 * @author adminitartor
 *
 */
public class HttpRequest {
	/*
	 * 请求行相关信息
	 */
	//请求方式
	private String method;
	//请求资源的抽象路径
	private String url;
	//请求使用的协议版本
	private String protocol;
	
	//url中"?"左侧的请求部分
	private String requestURI;
	//url中"?"右侧的参数部分
	private String queryString;
	//每一个参数 key:参数名   value:参数值
	private Map<String,String> parameters = new HashMap<>();
	
	
	/*
	 * 消息头相关信息
	 */
	/*
	 * key:消息头的名字
	 * value:消息头对应的值
	 */
	private Map<String,String> headers = new HashMap<>();
	
	
	/*
	 * 消息正文相关信息
	 */
	//消息正文的数据
	private byte[] data;
	
	
	
	/*
	 * 与连接相关的属性
	 */
	private Socket socket;
	private InputStream in;
	
	/**
	 * 初始化HttpRequest对象
	 * 初始化的过程就是解析请求的过程,实例化完毕后
	 * 当前HttpRequest对象就表示浏览器发送过来的
	 * 这个请求内容了.
	 * @throws EmptyRequestException 
	 */
	public HttpRequest(Socket socket) throws EmptyRequestException{
		System.out.println("HttpRequest:开始解析请求...");
		try {
			this.socket = socket;
			this.in = socket.getInputStream();
			/*
			 * 解析请求的三步:
			 * 1:解析请求行
			 * 2:解析消息头
			 * 3:解析消息正文
			 */
			parseRequestLine();
			parseHeaders();
			parseContent();
		} catch (EmptyRequestException e){
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("HttpRequest:解析请求完毕!");
	}
	/**
	 *	解析请求行
	 * @throws EmptyRequestException 
	 */
	private void parseRequestLine() throws EmptyRequestException{
		System.out.println("开始解析请求行...");
		/*
		 * 1:通过输入流读取第一行字符串(请求行内容)
		 * 2:将请求行内容按照空格拆分为三部分
		 * 3:将三部分内容设置到对应属性上(method,url,protocol)
		 */
		try {
			String line = readLine();
			System.out.println("请求行:"+line);
			/*
			 * 判断是否为空请求
			 */
			if("".equals(line)){
				throw new EmptyRequestException();
			}
			/*
			 * 后期循环接收客户端连接后,下面代码可能会出现
			 * 数组下标越界,这是由于空请求引起的,后面会解决
			 */
			String[] data = line.split("\\s");
			this.method = data[0];
			this.url = data[1];//这里会出现下标越界
			this.protocol = data[2];
			System.out.println("method:"+method);
			System.out.println("url:"+url);
			System.out.println("protocol:"+protocol);
			
			//进一步解析抽象路径部分
			parseURL();
			
			
		} catch(EmptyRequestException e){
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("解析请求行完毕!");
	}
	/**
	 * 进一步解析请求行中抽象路径部分
	 */
	private void parseURL(){
		System.out.println("开始解析抽象路径...");
		/*
		 * 一个请求的请求行当中抽象路径部分有两种情况:
		 * 1:不含有参数的，如:
		 *   /myweb/index.html
		 * 2:含有参数的，如:
		 *   /myweb/reg?username=xxx&password=xxx&...
		 * 因此我们对抽象路径进一步解析:
		 * 1:首先判断url的值是否含有?
		 *   1.1:若不含有"?"则直接将url的值赋值给
		 *       属性requestURI,当前方法结束。
		 *   1.2:若含有"?"执行步骤2
		 * 
		 * 2:将url按照"?"拆分为两部分，第一部分应当是
		 *   请求部分，赋值给属性requestURI.
		 *   第二部分应当是参数部分，赋值给属性queryString
		 *   
		 * 3:进一步解析queryString,将其按照"&"拆分为若干
		 *   个参数，每一个参数再按照"="拆分为参数名与参数
		 *   值。并将参数名作为key，参数值作为value保存到
		 *   属性parameters这个Map中完成解析工作。
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
		
		System.out.println("解析抽象路径完毕!");
	}
	/**
	 * 解析参数
	 * 格式为:name=value&name=value&...
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
	 * 	解析消息头
	 */
	private void parseHeaders(){
		System.out.println("开始解析消息头...");
		try {
			/*
				 * 1:循环调用readLine方法读取每一个消息头
				 * 2:将消息头按照": "拆分,并将消息头的名字
				 *   作为key,消息头的值作为value保存到属性
				 *   headers这个Map中
				 * 3:如果调用readLine方法返回的是一个空字符
				 *   串,则说明本次单独读取到了CRLF,那么就
				 *   可以停止解析消息头了.  
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
		System.out.println("消息头:"+headers);
		System.out.println("解析消息头完毕!");
	}
	/**
	 * 	解析消息正文
	 * @throws IOException 
	 */
	private void parseContent() throws IOException{
		System.out.println("开始解析消息正文...");
		//是否为post请求
		if("POST".equals(method.toUpperCase())){
			//查看是否含有Content-Length
			if(headers.containsKey("Content-Length")){
				int len = Integer.parseInt(
					headers.get("Content-Length")	
				);
				data = new byte[len];
				//读取消息正文数据
				in.read(data);
				
				//判断消息正文类型
				String type = headers.get("Content-Type");
				if("application/x-www-form-urlencoded".equals(type)){
					//form表单数据
					String line = new String(data,"ISO8859-1");
					parseParameters(line);	
				}
			}
		}
		
		
		
		System.out.println("解析消息正文完毕!");
	}
	
	/**
	 * 通过对应客户端的输入流读取一行字符串
	 * (以CRLF结尾)
	 * @return
	 * @throws IOException 
	 */
	private String readLine() throws IOException{
		//读取一行字符串,以CRLF结尾
		StringBuilder builder = new StringBuilder();
		//c1表示上次读取到的字符,c2表示本次读取到的字符
		int c1 = -1,c2 = -1;
		while((c2 = in.read())!=-1){
			//是否连续读取到了CR,LF
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
	 * 获取指定名字消息头对应的值
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
	 * 根据给定的参数名获取对应的参数值
	 * @param name
	 * @return
	 */
	public String getParameter(String name){
		return this.parameters.get(name);
	}
	
	
}









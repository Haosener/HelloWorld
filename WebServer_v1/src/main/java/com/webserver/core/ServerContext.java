package com.webserver.core;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.webserver.servlet.HttpServlet;

/**
 * 服务端相关配置信息定义
 * @author adminitartor
 *
 */
public class ServerContext {
	/**
	 * 请求与对应Servler的关系
	 * key:请求路径
	 * value:处理该请求的Sevrlet实例
	 */
	private static Map<String,HttpServlet> servletMapping = new HashMap<>();
	
	static{
		initSevrlet();
	}
	/**
	 * 初始化Servlet
	 */
	private static void initSevrlet(){
//		servletMapping.put("/myweb/reg", new RegServlet());
//		servletMapping.put("/myweb/login", new LoginServlet());
//		servletMapping.put("/myweb/update", new UpdateServlet());
		/*
		 * 解析conf/servlets.xml文件
		 * 将根标签下所有的<servlet>标签解析出来
		 * 并用其属性path的值作为key，
		 * className属性的值使用反射方式加载对应
		 * 的Servlet类并进行实例化，然后将对应的
		 * 实例作为value
		 * 保存到servletMapping这个Map中。
		 */
		try {
			SAXReader reader = new SAXReader();
			Document doc = reader.read(
					new File("conf/servlets.xml"));
			Element root = doc.getRootElement();
			List<Element> list = root.elements();
			for(Element servletEle : list){
				String key 
					= servletEle.attributeValue("path");
				String className 
					= servletEle.attributeValue("className");
				Class cls = Class.forName(className);
				HttpServlet servlet 
					= (HttpServlet)cls.newInstance();
				servletMapping.put(key, servlet);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	
	}
	/**
	 * 根据请求路径获取对应的Sevrlet实例
	 * @param path
	 * @return
	 */
	public static HttpServlet getServlet(String path){
		return servletMapping.get(path);
	}
	
}











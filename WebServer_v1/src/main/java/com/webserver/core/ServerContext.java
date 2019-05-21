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
 * ��������������Ϣ����
 * @author adminitartor
 *
 */
public class ServerContext {
	/**
	 * �������ӦServler�Ĺ�ϵ
	 * key:����·��
	 * value:����������Sevrletʵ��
	 */
	private static Map<String,HttpServlet> servletMapping = new HashMap<>();
	
	static{
		initSevrlet();
	}
	/**
	 * ��ʼ��Servlet
	 */
	private static void initSevrlet(){
//		servletMapping.put("/myweb/reg", new RegServlet());
//		servletMapping.put("/myweb/login", new LoginServlet());
//		servletMapping.put("/myweb/update", new UpdateServlet());
		/*
		 * ����conf/servlets.xml�ļ�
		 * ������ǩ�����е�<servlet>��ǩ��������
		 * ����������path��ֵ��Ϊkey��
		 * className���Ե�ֵʹ�÷��䷽ʽ���ض�Ӧ
		 * ��Servlet�ಢ����ʵ������Ȼ�󽫶�Ӧ��
		 * ʵ����Ϊvalue
		 * ���浽servletMapping���Map�С�
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
	 * ��������·����ȡ��Ӧ��Sevrletʵ��
	 * @param path
	 * @return
	 */
	public static HttpServlet getServlet(String path){
		return servletMapping.get(path);
	}
	
}











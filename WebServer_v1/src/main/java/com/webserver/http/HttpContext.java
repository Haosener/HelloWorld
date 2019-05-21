package com.webserver.http;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * HTTPЭ��涨���������
 * @author adminitartor
 *
 */
public class HttpContext {
	/**
	 * �س��� CR
	 * ASC�����Ӧֵ:13
	 */
	public static final int CR = 13;
	/**
	 * ���з� LF
	 * ASC�����Ӧֵ:10
	 */
	public static final int LF = 10;
	/**
	 * ��Դ��׺����Content-Type��Ӧͷ��Ӧֵ��ӳ���ϵ
	 * key:��Դ��׺��
	 * value:Content-Type��Ӧ��ֵ
	 */
	private static Map<String,String> mimeMapping = new HashMap<>();
	
	//��ʼ�����о�̬��Դ
	static{
		initMimeMapping();
	}
	
	private static void initMimeMapping(){
//		mimeMapping.put("html","text/html");
//		mimeMapping.put("css","text/css");
//		mimeMapping.put("js","application/javascript");
//		mimeMapping.put("png","image/png");
//		mimeMapping.put("gif","image/gif");
//		mimeMapping.put("jpg","image/jpeg");
		/*
		 * ʹ��DOM4J����conf/web.xml�ļ�
		 * ������ǩ��������Ϊ<mime-mapping>���ӱ�ǩ
		 * ��ȡ�������������е��ӱ�ǩ:
		 * <extension>�м���ı���Ϊkey
		 * <mime-type>�м���ı���Ϊvalue
		 * ����ʼ��mimeMapping���Map
		 * 
		 * ��ʼ����Ϻ����MapӦ����1000���Ԫ�ء�
		 */
		try {
			SAXReader reader = new SAXReader();
			Document doc = reader.read(
					new File("conf/web.xml"));
			Element root = doc.getRootElement();
			List<Element> list 
				= root.elements("mime-mapping");
			for(Element mimeEle : list){
				String key 
					= mimeEle.elementText("extension");
				String value 
					= mimeEle.elementText("mime-type");
				mimeMapping.put(key, value);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println(mimeMapping.size());
	}
	/**
	 * ���ݸ�������Դ��׺����ȡ��Ӧ��Content-Typeֵ
	 * @param ext
	 * @return
	 */
	public static String getMimeType(String ext){
		return mimeMapping.get(ext);
	}
	
	
	
	public static void main(String[] args) {
		String type = HttpContext.getMimeType("css");
		System.out.println(type);//  text/css
	}
}









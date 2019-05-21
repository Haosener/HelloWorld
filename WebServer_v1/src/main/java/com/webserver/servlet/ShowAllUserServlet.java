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
 * 在利用thymeleaf框架，将user.dat文件中的用户
 * 信息展现在静态页面showAllUser.html中，并
 * 响应给客户端
 * @author Haosener
 *
 */
public class ShowAllUserServlet extends HttpServlet{

	@Override
	public void service(HttpRequest request, HttpResponse response) {
		System.out.println("ShouAllUserServlet:开始展示用户信息");
		
		/*
		 * 创建一个结合用于保存user.dat文件中
		 * 读取出来饿所有用户信息，集合中每个元素表示一个用户的信息
		 * 我们使用Map作为集合元素，其中中key是用户
		 * 的属性信息（用户名，密码等），value是该
		 * 属性对应的值（张三，123456...）这意味这
		 * 每个Map实例表示一个用户的相关信息
		 * 
		 * OGNL表达式的思想..
		 */
		List <Map<String,String >> list = new ArrayList<>();
		try(RandomAccessFile raf = new RandomAccessFile("user.dat","r");) {
			for(int i=0;i<raf.length()/100;i++){
				//读取用户名
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
		 * 解释器
		 * 用于给引擎设置各种参数，以及哪里寻找静态页面等
		 * FileTemplateResolver是基于文件系统寻找静态资源，加载静态资源使用的路径与File类一致
		 */
		FileTemplateResolver resolver = new FileTemplateResolver();
		//设置字符集，以便模板引擎可以按照正确的字符集
		resolver.setCharacterEncoding("utf-8");
		//将解释器设置到引擎中
		eng.setTemplateResolver(resolver);
		
		//以上操作后，引擎初始化完毕
		
		/*
		 * Context是以树状结构组件所有需要在
		 * 页面上呈现的数据
		 */
		Context context = new Context();
		context.setVariable("list",list);
		
		/*
		 * 利用引擎将静态页面与数据进行绑定
		 * 返回值是一个字符串，内容就是绑定好
		 * 数据的html代码
		 */
		String html = eng.process("./webapps/myweb/showAllUser.html", context);
		
		//响应客户端
		try {
			response.setContentData(html.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

}

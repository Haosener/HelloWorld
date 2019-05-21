package com.webserver.servlet;

import java.io.RandomAccessFile;
import java.util.Arrays;

import com.webserver.http.HttpRequest;
import com.webserver.http.HttpResponse;
/**
 * 修改密码业务
 * @author adminitartor
 *
 */
public class UpdateServlet extends HttpServlet{
	public void service(HttpRequest request, HttpResponse response) {
		System.out.println("UpdateServlet:开始修改密码");
		//1 获取用户信息
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String newpassword = request.getParameter("newpassword");
		
		//2修改工作
		try (
			RandomAccessFile raf
				= new RandomAccessFile("user.dat","rw");
		){
			for(int i=0;i<raf.length()/100;i++){
				raf.seek(i*100);
				//读取用户名
				byte[] data = new byte[32];
				raf.read(data);
				String name = new String(data,"UTF-8").trim();
				//找到此用户
				if(name.equals(username)){
					//读取密码
					raf.read(data);
					String pwd = new String(data,"UTF-8").trim();
					if(pwd.equals(password)){
						//可以修改
						//移动指针到密码位置
						raf.seek(i*100+32);
						data = newpassword.getBytes("UTF-8");
						data = Arrays.copyOf(data, 32);
						raf.write(data);
						//设置response跳转成功页面
						forward("/myweb/update_success.html", request, response);
						return;
					}
					break;
				}			
			}
			
			//设置修改失败页面
			forward("/myweb/update_fail.html", request, response);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		
		System.out.println("UpdateServlet:修改密码完毕");
	}

}

package com.webserver.servlet;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

import javax.imageio.ImageIO;

import com.webserver.http.HttpRequest;
import com.webserver.http.HttpResponse;

/**
 * 随机验证码
 * @author Haosener
 *
 */
public class RandomImageServlet extends HttpServlet{
	char[] chars = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM1234567890".toCharArray();
	//图片宽高
	private static int IMAGE_WIDTH = 65;
	private static int IMAGE_HEIGHT = 25;
	
	@Override
	public void service(HttpRequest request, HttpResponse response) {
		System.out.println("RandomImageServlet：生成随机验证码");
		
		//创建画布 xxx.jpg
		BufferedImage image = new BufferedImage(IMAGE_WIDTH,IMAGE_HEIGHT,BufferedImage.TYPE_INT_RGB);
		//获取这张图的画笔，用于往这张图上画内容
		Graphics g = image.getGraphics();
		Color color = new Color(200,200,255);
		//设置画笔为这个颜色
		g.setColor(color);
		//使用画笔填充一个举行，颜色为当前画笔指定颜色
		g.fillRect(0, 0, IMAGE_WIDTH, IMAGE_HEIGHT);
		Random random = new Random();
		g.setFont(new Font("宋体",Font.BOLD,20));
		for(int i = 0;i<6;i++) {
			
		g.setColor(new Color(random.nextInt(180),random.nextInt(100),random.nextInt(100)));
		g.drawString(chars[random.nextInt(chars.length)]+"", i*15+5, 18);
		}
		for(int i = 0;i<6;i++) {
			g.setColor(new Color(random.nextInt(180),random.nextInt(100),random.nextInt(100)));
			g.drawLine(random.nextInt(IMAGE_WIDTH), random.nextInt(IMAGE_HEIGHT), random.nextInt(IMAGE_WIDTH), random.nextInt(IMAGE_HEIGHT));
		}

		try {
			/*
			 * ByteArrayOutputStream是一个低级流，通过
			 * 这个流写出的字节会保存到它内部的一个字节数组中
			 */
			ByteArrayOutputStream out = new ByteArrayOutputStream();
		ImageIO.write(image, "jpg", out);
		byte[] data = out.toByteArray();
		
		//将图片数据作为正文设置到相应对象中
		response.setContentData(data);
		response.putHeader("Content-Type", "image/jpeg");
	} catch (Exception e) {
		e.printStackTrace();
		}
		
		System.out.println("ok");
	}
}

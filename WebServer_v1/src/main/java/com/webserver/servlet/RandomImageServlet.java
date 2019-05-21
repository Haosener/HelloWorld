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
 * �����֤��
 * @author Haosener
 *
 */
public class RandomImageServlet extends HttpServlet{
	char[] chars = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM1234567890".toCharArray();
	//ͼƬ���
	private static int IMAGE_WIDTH = 65;
	private static int IMAGE_HEIGHT = 25;
	
	@Override
	public void service(HttpRequest request, HttpResponse response) {
		System.out.println("RandomImageServlet�����������֤��");
		
		//�������� xxx.jpg
		BufferedImage image = new BufferedImage(IMAGE_WIDTH,IMAGE_HEIGHT,BufferedImage.TYPE_INT_RGB);
		//��ȡ����ͼ�Ļ��ʣ�����������ͼ�ϻ�����
		Graphics g = image.getGraphics();
		Color color = new Color(200,200,255);
		//���û���Ϊ�����ɫ
		g.setColor(color);
		//ʹ�û������һ�����У���ɫΪ��ǰ����ָ����ɫ
		g.fillRect(0, 0, IMAGE_WIDTH, IMAGE_HEIGHT);
		Random random = new Random();
		g.setFont(new Font("����",Font.BOLD,20));
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
			 * ByteArrayOutputStream��һ���ͼ�����ͨ��
			 * �����д�����ֽڻᱣ�浽���ڲ���һ���ֽ�������
			 */
			ByteArrayOutputStream out = new ByteArrayOutputStream();
		ImageIO.write(image, "jpg", out);
		byte[] data = out.toByteArray();
		
		//��ͼƬ������Ϊ�������õ���Ӧ������
		response.setContentData(data);
		response.putHeader("Content-Type", "image/jpeg");
	} catch (Exception e) {
		e.printStackTrace();
		}
		
		System.out.println("ok");
	}
}

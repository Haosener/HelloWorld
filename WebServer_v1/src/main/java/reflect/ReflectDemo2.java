package reflect;

import java.util.Scanner;

/**
 * ���÷���ʵ��������
 * @author adminitartor
 *
 */
public class ReflectDemo2 {
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		Person p = new Person();
		System.out.println(p);
		
		/*
		 * ʵ����Person��ʵ��
		 * 
		 * java.util.HashMap
		 * java.util.Date
		 * java.util.ArrayList
		 * reflect.Person
		 * java.lang.String
		 */
		Scanner scanner = new Scanner(System.in);
		System.out.println("������Ҫʵ�������������:");
		String className = scanner.nextLine();
		Class cls = Class.forName(className);
		/*
		 * Class�ṩ��ʵ��������Ҫ�����ʾ����
		 * ��������޲ι�����������ʵ����ʱ���׳�
		 * �쳣
		 */
		Object obj = cls.newInstance();
		
		System.out.println(obj);
		
	}
}









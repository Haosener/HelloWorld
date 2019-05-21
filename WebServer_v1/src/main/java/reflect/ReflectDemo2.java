package reflect;

import java.util.Scanner;

/**
 * 利用反射实例化对象
 * @author adminitartor
 *
 */
public class ReflectDemo2 {
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		Person p = new Person();
		System.out.println(p);
		
		/*
		 * 实例化Person类实例
		 * 
		 * java.util.HashMap
		 * java.util.Date
		 * java.util.ArrayList
		 * reflect.Person
		 * java.lang.String
		 */
		Scanner scanner = new Scanner(System.in);
		System.out.println("请输入要实例化的类的名字:");
		String className = scanner.nextLine();
		Class cls = Class.forName(className);
		/*
		 * Class提供的实例化方法要求其表示的类
		 * 必须具有无参构造器，否则实例化时会抛出
		 * 异常
		 */
		Object obj = cls.newInstance();
		
		System.out.println(obj);
		
	}
}









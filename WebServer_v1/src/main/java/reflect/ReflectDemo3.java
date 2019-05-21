package reflect;

import java.lang.reflect.Method;
import java.util.Scanner;

/**
 * ���÷�����÷���
 * @author adminitartor
 *
 */
public class ReflectDemo3 {
	public static void main(String[] args)throws Exception {
		Person p = new Person();
		p.sayHello();
		
		/*
		 * ���÷������
		 */
		Class cls = Class.forName("reflect.Person");
		Object obj = cls.newInstance();
		
		Method method = cls.getDeclaredMethod("sayHello");
		method.invoke(obj);
		
		
		
	}
}






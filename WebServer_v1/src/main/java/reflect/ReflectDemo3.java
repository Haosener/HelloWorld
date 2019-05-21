package reflect;

import java.lang.reflect.Method;
import java.util.Scanner;

/**
 * 利用反射调用方法
 * @author adminitartor
 *
 */
public class ReflectDemo3 {
	public static void main(String[] args)throws Exception {
		Person p = new Person();
		p.sayHello();
		
		/*
		 * 利用反射机制
		 */
		Class cls = Class.forName("reflect.Person");
		Object obj = cls.newInstance();
		
		Method method = cls.getDeclaredMethod("sayHello");
		method.invoke(obj);
		
		
		
	}
}






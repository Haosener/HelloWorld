package reflect;

import java.lang.reflect.Constructor;
/**
 * 调用对应构造器的实例化操作
 * @author adminitartor
 *
 */
public class ReflectDemo5 {
	public static void main(String[] args)throws Exception {
		Class cls = Class.forName("reflect.Person");
		Constructor[] arr 
			= cls.getDeclaredConstructors();
		for(Constructor c : arr){
			System.out.println(c);
		}
		//Person p = new Person("张三");
		Constructor c 
			= cls.getDeclaredConstructor(String.class);
		Object o = c.newInstance("张三");
		System.out.println(o);
	}
}




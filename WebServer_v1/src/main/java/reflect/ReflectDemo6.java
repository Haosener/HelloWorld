package reflect;

import java.lang.reflect.Method;
/**
 * 利用反射机制访问一个类的私有方法
 * @author adminitartor
 *
 */
public class ReflectDemo6 {
	public static void main(String[] args)throws Exception {
//		Person p = new Person();
//		p.dosome();
		
		Class cls = Class.forName("reflect.Person");
		Object o = cls.newInstance();
		
		Method method = cls.getDeclaredMethod("dosome");
		//强制访问该私有方法
		method.setAccessible(true);
		method.invoke(o);
		
	}
}

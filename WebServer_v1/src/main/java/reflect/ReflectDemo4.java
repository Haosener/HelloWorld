package reflect;

import java.lang.reflect.Method;

/**
 * �����вη���
 * @author adminitartor
 *
 */
public class ReflectDemo4 {
	public static void main(String[] args)throws Exception {
		Class cls = Class.forName("reflect.Person");
		Object obj = cls.newInstance();
		
		//say(String,int)
		Method method = cls.getDeclaredMethod(
				"say",String.class,int.class);
		method.invoke(obj,"����",22);
		
		
	}
}









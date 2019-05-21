package reflect;

import java.lang.reflect.Method;
/**
 * ���÷�����Ʒ���һ�����˽�з���
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
		//ǿ�Ʒ��ʸ�˽�з���
		method.setAccessible(true);
		method.invoke(o);
		
	}
}

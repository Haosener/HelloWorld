package reflect;

import java.lang.reflect.Constructor;
/**
 * ���ö�Ӧ��������ʵ��������
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
		//Person p = new Person("����");
		Constructor c 
			= cls.getDeclaredConstructor(String.class);
		Object o = c.newInstance("����");
		System.out.println(o);
	}
}




package reflect;

import java.lang.reflect.Method;
import java.util.Scanner;

/**
 * ����
 * ������java��һ����̬���ơ�����ʵ�������󣬵���
 * �������������Եȹ����ӱ����ڼ�ȷ��תΪ�ڳ�������
 * �ڼ�ȷ�������������˴��������ԡ�
 * �����и������Դ�����ͽ���������Ч�ʣ����ֻ�ڹ�
 * ���ĵط�ʹ�÷��䣬����Ӧ����Ӧ�á�
 * 
 * 
 * Class��
 * �����Class������ÿһ��ʵ����������ʾJVM�Ѿ�����
 * ��һ���ࡣÿ����JVM���ص��඼����ֻ��һ��Class��
 * ʵ����֮��Ӧ��
 * ͨ����������ǿ��Ե�֪���ʾ�����һ����Ϣ:
 * ����������Щ��������Щ���ԣ���Щ�������ȡ�
 * ���ҿ��Կ���ʵ�������ʾ�����ʵ����
 * 
 * ����ĵ�һ�����ǻ�ȡҪ�������������󣬶���ȡ
 * һ������������һ�¼��ַ�ʽ:
 * 1:ÿ���඼��һ����̬����:class,����ֱ�ӵõ����
 *   �������󡣵��������ǵ��þ�̬���ԣ����ڱ��뷽
 *   ʽ��ȡ�����Բ���
 *   ����:Class cls = String.class;
 *   
 * 2:ͨ��Class�ľ�̬����forName�õ�������:
 *   Class cls = Class.forName("java.lang.String");
 *   
 * 3:�������ClassLoader    
 * 
 * @author adminitartor
 *
 */
public class ReflectDemo1 {
	public static void main(String[] args) throws ClassNotFoundException {
		//��ȡString�������
//		Class cls = String.class;
		
//		Class cls = Person.class;
		
//		Class cls = Class.forName("java.lang.String");
		
		Scanner scanner = new Scanner(System.in);
		System.out.println("������Ҫ���ص��������:");
		String className = scanner.nextLine();
		Class cls = Class.forName(className);
		
		//��ȡ����
		String name = cls.getName();
		System.out.println("����:"+name);
		/*
		 * Method��
		 * Method��ÿһ��ʵ�����ڱ�ʾһ�����ж���
		 * ��һ��������ͨ�������Եõ�������������
		 * ��Ϣ(�������η�������ֵ���ͣ���������)��
		 * ����Ҳ���Ե������������
		 */
//		Method[] methods = cls.getMethods();
		
		//��ȡ�����Լ�����ķ���
		Method[] methods = cls.getDeclaredMethods();
		for(Method method : methods){
			System.out.println(method.getName());
		}
	
		
	}
}









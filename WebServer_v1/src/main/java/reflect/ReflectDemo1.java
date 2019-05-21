package reflect;

import java.lang.reflect.Method;
import java.util.Scanner;

/**
 * 反射
 * 反射是java的一个动态机制。它将实例化对象，调用
 * 方法，操作属性等工作从编码期间确定转为在程序运行
 * 期间确定。这大大的提高了代码的灵活性。
 * 反射有更多的资源开销和较慢的运行效率，因此只在关
 * 键的地方使用反射，而不应大量应用。
 * 
 * 
 * Class类
 * 类对象Class，它的每一个实例是用来表示JVM已经加载
 * 的一个类。每个被JVM加载的类都有且只有一个Class的
 * 实例与之对应。
 * 通过类对象我们可以得知其表示的类的一切信息:
 * 类名，有哪些方法，哪些属性，哪些构造器等。
 * 并且可以快速实例化其表示的类的实例。
 * 
 * 反射的第一步就是获取要操作的类的类对象，而获取
 * 一个类的类对象有一下几种方式:
 * 1:每个类都有一个静态属性:class,可以直接得到这个
 *   类的类对象。但由于这是调用静态属性，属于编码方
 *   式获取，所以不灵活。
 *   例如:Class cls = String.class;
 *   
 * 2:通过Class的静态方法forName得到，例如:
 *   Class cls = Class.forName("java.lang.String");
 *   
 * 3:类加载器ClassLoader    
 * 
 * @author adminitartor
 *
 */
public class ReflectDemo1 {
	public static void main(String[] args) throws ClassNotFoundException {
		//获取String的类对象
//		Class cls = String.class;
		
//		Class cls = Person.class;
		
//		Class cls = Class.forName("java.lang.String");
		
		Scanner scanner = new Scanner(System.in);
		System.out.println("请输入要加载的类的名字:");
		String className = scanner.nextLine();
		Class cls = Class.forName(className);
		
		//获取类名
		String name = cls.getName();
		System.out.println("类名:"+name);
		/*
		 * Method类
		 * Method的每一个实例用于表示一个类中定义
		 * 的一个方法。通过它可以得到这个方法的相关
		 * 信息(访问修饰符，返回值类型，方法名等)，
		 * 并且也可以调用这个方法。
		 */
//		Method[] methods = cls.getMethods();
		
		//获取本类自己定义的方法
		Method[] methods = cls.getDeclaredMethods();
		for(Method method : methods){
			System.out.println(method.getName());
		}
	
		
	}
}









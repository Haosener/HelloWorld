package reflect;
/**
 * 变长参数
 * @author adminitartor
 *
 */
public class Test {
	public static void main(String[] args) {
		
		dosome(1,"one","two","three","four");
	}
	
	public static void dosome(int age,String... arg){
		System.out.println(arg.length);
		for(String str : arg){
			System.out.println(str);
		}
	}
}





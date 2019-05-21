package reflect;

public class Person {
	private String name;
	public Person(String name){
		this.name = name;
	}
	public Person(){

	}
	
	private void dosome(){
		System.out.println("我是私有方法!!!");
	}
	
	public String toString() {
		return "Person["+name+"]";
	}
	
	public void sayHello(){
		System.out.println("hello!!");
	}
	
	public void sayHi(){
		System.out.println("hi!!!");
	}
	
	public void say(String name){
		System.out.println("大家好！我是:"+name);
	}
	public void say(String name,int age){
		System.out.println(
			"大家好！我是:"+name+",我今年"+age);
	}
	
}






package reflect;

public class Person {
	private String name;
	public Person(String name){
		this.name = name;
	}
	public Person(){

	}
	
	private void dosome(){
		System.out.println("����˽�з���!!!");
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
		System.out.println("��Һã�����:"+name);
	}
	public void say(String name,int age){
		System.out.println(
			"��Һã�����:"+name+",�ҽ���"+age);
	}
	
}






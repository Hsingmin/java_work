import java.util.*;

/**
* Object construction.
* @version : 1.01
* @author : Alfred Lee
*/

public class ConstructorTest{
	public static void main(String[] args){
		//fill the staff array
		Employee[] staff = new Employee[3];
		
		staff[0] = new Employee("Alfred",14000);
		staff[1] = new Employee("Sure",13000);
		staff[2] = new Employee("QQ",12000);
		
		for(Employee e : staff)
			System.out.println("name = " + e.getName() + ", id = " + e.getId() + "salary = " + e.getSalary());
	}
}

class Employee{
	//three overloaded constructors
	public Employee(String n,double s){
		name = n;
		salary = s;
	}
	
	public Employee(double s){
		//calls the Employee(String,double) constructor
		this("Employee #" + nextId,s);
	}
	
	//the default constructor
	public Employee(){
		//name initialize to be ""
		//salary not explicitly set -- initialize to be 0
		//id intialized in block
	}
	
	public String getName(){
		return name;
	}
	
	public double getSalary(){
		return salary;
	}
	
	public int getId(){
		return id;
	}
	
	private static int nextId;
	
	private int id;
	private String name = "";
	private double salary;
	
	//static initialization block
	static{
		Random generator = new Random();
		nextId = generator.nextInt(10000);
	}
	
	//object initialization block
	{
		id = nextId;
		nextId++;
	}
	
}
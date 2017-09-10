/**
* Demonstrate parameter passing in Java
* @version : 1.00
* @author : Alfred Lee
*/
public class ParamTest{
	//Constructor
	public static void main(String[] args){
		/*Methods cannot modify numeric parameters.*/
		System.out.println("Test tripleValue : ");
		double percent = 10;
		System.out.println("Before : percent = " + percent);
		tripleValue(percent);
		System.out.println("After : percent = " + percent);
		
		/*Methods cannot change the state of objects.*/
		System.out.println("\nTest tripleSalary : ");
		Employee harry = new Employee("Alfred",14000);
		System.out.println("Before : salary = " + harry.getSalary());
		tripleSalary(harry);
		System.out.println("After : salary = " + harry.getSalary());
		
		/*Methods cannot attach new object to object parameters.*/
		System.out.println("\nTest swap : ");
		Employee a = new Employee("Alfred",14000);
		Employee b = new Employee("Pepe",20000);
		System.out.println("Before : a = " + a.getName());
		System.out.println("Before : b = " + b.getName());
		swap(a,b);
		System.out.println("After : a = " + a.getName());
		System.out.println("After : b = " + b.getName());
	}
	
	//Methods
	public static void tripleValue(double x){	//do not work
		x *= 3;
		System.out.println("End of method : x = " + x);
	}
	
	public static void tripleSalary(Employee x){	//works
		x.raiseSalary(200);
		System.out.println("End of method : salary = " + x.getSalary());
	}
	
	public static void swap(Employee x,Employee y){
		Employee temp = x;
		x = y;
		y = temp;
		System.out.println("End of method : x = " + x.getName());
		System.out.println("End of method : y = " + y.getName());
	}
}

class Employee{
	//constructor
	public Employee(String n,double s){
		name = n;
		salary = s;
	}
	
	//methods
	public String getName(){
		return name;
	}
	
	public double getSalary(){
		return salary;
	}
	
	public void raiseSalary(double byPercent){
		double raise = salary * byPercent / 100;
		salary += raise;
	}
	
	//private fields
	private String name;
	private double salary;
}














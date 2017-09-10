import java.util.*;

/**
* Demonstrate the usage of Comparable interface.
* @version : 1.30
* @author : Cay Horstmann
*/

public class EmployeeSort{
	public static void main(String[] args){
		Employee[] staff = new Employee[3];
		
		staff[0] = new Employee("Alfred Lee",13000);
		staff[1] = new Employee("Sure Wang",10000);
		staff[2] = new Employee("QQ Wang",10000);
		
		Arrays.sort(staff);
		
		for(Employee e : staff)
			System.out.println("name = " + e.getName() + "salary = " + e.getSalary());
	}
}

class Employee implements Comparable<Employee>{
	public Employee(String n,double s){
		name = n;
		salary = s;
	}
	
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
	
	/**
	* Compares employees by salary
	* @param other another Employee object
	* @return a negative value if this employee has lower salary
	*/
	public int compareTo(Employee other){
		if(salary < other.salary)
			return -1;
		if(salary > other.salary)
			return 1;
		return 0;
	}
	
	private String name;
	private double salary;
}
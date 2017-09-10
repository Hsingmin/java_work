import java.util.*;

/**
* Test the Employee class. 
* @version : 1.11
* @author : Alfred Lee
*/

public class EmployeeTest{
	public static void main(String[] args){
		//fill the staff array with three Employee objects
		Employee[] staff = new Employee[3];
		
		staff[0] = new Employee("Alfred Lee",9800,2013,7,4);
		staff[1] = new Employee("QQ Wang",10000,2013,9,2);
		staff[2] = new Employee("Sure Wang",10000,2013,5,6);
		
		//raise everyone's salary by 5%
		for(Employee e : staff)
			e.raiseSalary(5);
		
		//print out information about all Employee objects 
		for(Employee e : staff){
			System.out.println("name = " + e.getName() + ", Salary = " + e.getSalary() + ", Hireday = " + e.getHireDay());
		}
	}
}

//non-public class Employee
class Employee{
	//constructors
	public Employee(String n,double s,int year,int month,int day){
		name = n;
		salary = s;
		GregorianCalendar calendar = new GregorianCalendar(year,month-1,day);
		hireDay = calendar.getTime();
	}
	
	//methods
	public String getName(){
		return name;
	}
	
	public double getSalary(){
		return salary;
	}
	
	public Date getHireDay(){
		return (Date) hireDay.clone();
	}
	
	public void raiseSalary(double byPercent){
		double raise = this.salary * byPercent / 100;
		salary += raise;
	}
	
	//static method test
	public static void main(String[] args){
		Employee e = new Employee("Samuel Han",5000,2014,6,1);
		System.out.println(e.getName() + " " + e.getSalary());
	}
	
	//instance fields
	private String name;
	private double salary;
	private Date hireDay;
}
import java.util.*;

/**
* Inheritance machine.
* @version : 1.21
* @author : Cay Horstmann
*/
public class ManagerTest{
	public static void main(String[] args){
		//construct a manager object
		Manager boss = new Manager("John Chan",30000,2014,7,5);
		boss.setBonus(5000);
		
		Employee[] staff = new Employee[3];
		
		//fill in staff with manager and employee object
		staff[0] = boss;
		staff[1] = new Employee("Alfred Lee",8000,2014,7,2);
		staff[2] = new Employee("Sure Wang",8100,2014,5,6);
		
		for(Employee e : staff)
			System.out.println("name = " + e.getName() + ", salary = " + e.getSalary());
	}
}

class Employee{
	public Employee(String n,double s,int year,int month,int day){
		name = n;
		salary = s;
		GregorianCalendar calendar = new GregorianCalendar(year,month-1,day);
		hireDay = calendar.getTime();
	}
	
	public String getName(){
		return name;
	}
	
	public double getSalary(){
		return salary;
	}
	
	public Date getHireDay(){
		return hireDay;
	}
	
	public void raiseSalary(double byPercent){
		double raise = salary * byPercent / 100;
		salary += raise;
	}
	
	private String name;
	private double salary;
	private Date hireDay;
}

class Manager extends Employee{
	/**
	* @param n : the employee's name
	* @param s : the salary
	* @param year : the hire year
	* @param month : the hire month
	* @param day : the hire day
	*/
	
	//call super class Employee's constructor with params n, s, month and day
	public Manager(String n,double s,int year,int month,int day){
		super(n,s,year,month,day);
		bonus = 0;
	}
	
	public double getSalary(){
		double baseSalary = super.getSalary();
		return baseSalary + bonus;
	}
	
	public void setBonus(double b){
		bonus = b;
	}
	
	private double bonus;
}
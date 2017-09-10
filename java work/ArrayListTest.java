import java.util.*;

/**
* ArrayList class usage.
*/

public class ArrayListTest{
	public static void main(String[] args){
		//fill the staff list 
		ArrayList<Employee> staff = new ArrayList<Employee>();
		staff.add(new Employee("Alfred",8000,2013,7,4));
		staff.add(new Employee("Sure",8100,2013,5,6));
		staff.add(new Employee("QQ",8000,2013,9,1));
		
		for(Employee e : staff)
			System.out.println("name = " + e.getName() + ", salary = "
			 + e.getSalary() + ", hireday = " + e.getHireDay());
	}
}

class Employee{
	public Employee(String n,double n,int year,int month,int day){
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
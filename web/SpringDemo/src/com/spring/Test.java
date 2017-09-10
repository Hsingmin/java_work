package com.spring;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.bean.Person;
public class Test {
	public static void main(String[] args){
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		Person person0 = (Person)context.getBean("chinese");
		person0.Speak();
		
		Person person1 = (Person)context.getBean("american");
		person1.Speak();
	}
}
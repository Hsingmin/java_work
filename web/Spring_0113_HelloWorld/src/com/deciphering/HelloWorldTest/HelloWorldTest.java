package com.deciphering.HelloWorldTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.junit.Test;
import com.deciphering.helloworld.HelloWorld;
public class HelloWorldTest {
	public void test(){
		ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
		HelloWorld hello = (HelloWorld)context.getBean("helloworld");
		hello.SayHello();
	}
}

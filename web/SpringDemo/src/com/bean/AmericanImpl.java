package com.bean;

public class AmericanImpl {
	private String name;
	private int age;
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public int getAge(){
		return age;
	}
	
	public void setAge(int age){
		this.age = age;
	}
	
	public void Speak(){
		System.out.println("I am American, my name is " + this.name + ", I am " + this.age + " years old.");
	}
}
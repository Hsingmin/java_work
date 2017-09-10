package com.action;
import org.apache.struts2.json.annotations.JSON;
import com.opensymphony.xwork2.ActionSupport;
public class UserAction extends ActionSupport{
	private static final long serialVersionUID = 1L;
	
	private User user = new User();
	
	@JSON(name = "USER")
	public User getUser(){
		return user;
	}
	
	public void setUser(User user){
		this.user = user;
	}
	
	public String execute() throws Exception{
		user.setName("tom");
		user.setAge(20);
		return SUCCESS;
	}
}

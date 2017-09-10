package com.action;
import com.opensymphony.xwork2.ActionSupport;
public class LoginAction extends ActionSupport{
	private static final long serialVersionUID = 1L;
	
	public String execute() throws Exception{
		return SUCCESS;
	}

	public String redirect() throws Exception{
		return ERROR;
	} 
}

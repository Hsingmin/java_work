package com.action;
import com.model.UserModel;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
public class LoginAction extends ActionSupport implements ModelDriven<UserModel>{
	private static final long serialVersionUID = 1L;
	
	private UserModel user = new UserModel();
	
	public UserModel getModel(){
		return user;
	}
	
	public String execute() throws Exception{
		ActionContext context = ActionContext.getContext();
		
		context.put("user", user);
		return SUCCESS;
	}
}

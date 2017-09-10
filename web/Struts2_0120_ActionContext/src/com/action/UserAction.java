package com.action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
public class UserAction extends ActionSupport{
	private static final long serialVersionUID = 1L;
	
	public String execute() throws Exception{
		ActionContext context = ActionContext.getContext();
		
		context.put("name", "request:com");
		
		context.getApplication().put("name", "application:tom");
		
		context.getSession().put("name", "session:tom");
		
		return "hello";
	}
}

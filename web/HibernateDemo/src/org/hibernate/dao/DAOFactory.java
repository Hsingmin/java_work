package org.hibernate.dao;

public class DAOFactory {
	public static UserDAO getUserDAOInstance(){
		return new UserDAOImpl();
	}
}

package org.hibernate.dao;
import org.hibernate.*;
import org.hibernate.entity.*;
public class UserDAOImpl implements UserDAO{
	public void save(User user){
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		try{
			session.save(user);
			tx.commit();
		}
		catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		finally{
			HibernateUtil.closeSession();
		}
	}
	
	public User findById(int id){
		User user = null;
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		try{
			user = (User) session.get(User.class, id);
			tx.commit();
		}
		catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		finally{
			HibernateUtil.closeSession();
		}
		return user;
	}
	
	public void delete(User user){
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		try{
			session.delete(user);
			tx.commit();
		}
		catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		finally{
			Hibernate.Util.closeSession();
		}
	}
	
	public void update(User user){
		Session session = HibernateUtil.getSession();
		Transaction tx = session.beginTransaction();
		try{
			session.update(user);
			tx.commit();
		}
		catch(Exception e){
			e.printStackTrace();
			tx.rollback();
		}
		finally{
			HibernateUtil.closeSession();
		}
	}
}

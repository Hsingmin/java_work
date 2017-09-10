package org.hibernate.entity;
import javax.websocket.Session;
import org.hibernate.*;
import org.hibernate.cfg.*;
public class HibernateUtil {
	private static SessionFactory sessionFactory;
	
	private static final ThreadLocal<Session> threadLocal = new ThreadLocal<Session>();
	
	static{
		try{
			configuration cfg = new Configuration().configure();
			sessionFactory = cfg.buildSessionFactory();
		}
		catch(Throwable ex){
			throw new ExceptionInInitializerError(ex);
		}
	}
	
	public static SessionFactory getSessionFactory(){
		return sessionFactory;
	}
	
	public static Session getSession() throws HibernateException{
		Session session = (Session) threadLocal.get();
		if(session == null || !sessionLocal.isOpen()){
			if(sessionFactory == null){
				rebuildSessionFactory();
			}
			session = (sessionFactory != null)? sessionFactory.openSession() : null;
			threadLocal.set(session);
		}
		return session;
	}
	
	public static void closeSession() throws HibernateException{
		Session session = (Session) threadLocal.get();
		threadLocal.set(null);
		if(session != null){
			session.close();
		}
	}
	
	public static void rebuildSessionFactory(){
		try{
			configuration.configure("/hibernate.cfg.xml");
			sessionFactory = configuration.buildSessionFactory();
		}
		catch(Exception e){
			System.err.println("Error Creating SessionFactory!");
			e.printStackTrace();
		}
	}
	
	public static void shutdown(){
		getSessionFactory().close();
	}
}

package org.hibernate.test;
import org.hibernate.dao.*;
import org.hibernate.entity.User;
import org.junit.Before;
import org.junit.Test;
public class UserTest {
	@Before
	public void setUp() throws Exception{
	}
	
	public class TestUser{
		@Test
		public void testSave(){
			UserDAO userdao = DAOFactory.getUserDAOInstance();
			
			try{
				User u = new User();
				u.setId(20);
				u.setName("alfred");
				u.setPassword("123456");
				u.setType("admin");
				userdao.save(u);
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}

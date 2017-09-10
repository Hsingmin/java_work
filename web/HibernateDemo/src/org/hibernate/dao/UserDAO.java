package org.hibernate.dao;
import java.util.List;
import org.hibernate.entity.User;
public interface UserDAO {
	void save(User user);
	User findById(int id);
	void delete(User user);
	void update(User user);
}

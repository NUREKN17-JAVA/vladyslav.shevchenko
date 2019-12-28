package ua.nure.kn.shevchenko.usermanagement.db;
import  ua.nure.kn.shevchenko.usermanagement.User;	
import java.util.Collection;


public interface UserDAO {
	User create(User user) throws DatabaseException;
	
	User update(User user) throws DatabaseException;
	
	void delete(User user) throws DatabaseException;
	
	User find(Long id) throws DatabaseException;
	
	Collection findAll() throws DatabaseException;
	
	Collection<User> find(String firstName, String lastName) throws DatabaseException;

	void setConnectionFactory(ConnectionFactory connectionFactory);
}

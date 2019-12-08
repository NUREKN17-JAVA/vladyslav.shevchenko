package ua.nure.kn.shevchenko.usermanagement.db;

import ua.nure.kn.shevchenko.usermanagement.User;

import org.dbunit.DatabaseTestCase;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.dataset.xml.XmlDataSet;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;

import java.util.Collection;
import java.util.Date;


public class HsqldbUserDaoTest extends DatabaseTestCase  {
    private static final String FIRST_NAME = "Vladyslav";
    private static final String LAST_NAME = "Shevchenko";
    private HsqldbUserDao db_user;
    private User precreated_user;
    private ConnectionFactory connectionFact;
    
    private User create_user(){
    	try {
			User user = new User();
			user.setFirstName("TEST_USER");
			user.setLastName("TEST_LAST_NAME");
			user.setDateOfBirth(new Date());
			user = db_user.create(user);
			return user;
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
		return null;
    }
    

	protected void setUp() throws Exception {
		super.setUp();
		ConnectionFactory connectionFact = DaoFactory.getInstance().getConnectionFactory();
		db_user = new HsqldbUserDao(connectionFact);
		precreated_user = create_user();
	}
	
	public void testCreate() {
			assertNotNull(precreated_user);
			assertNotNull(precreated_user.getId());
	}
	public void testUpdate() {
		try {
			precreated_user.setFirstName("CHANGED_NAME");
			User changed_user = db_user.update(precreated_user);
			assertNotNull(changed_user);
			assertEquals("CHANGED_NAME", changed_user.getFirstName());
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail(e.toString());
		}
	}
	
	public void testDelete() {
		try {
            Collection<User> before_delete_users = db_user.findAll();
	        assertEquals("Collection size.", 3, before_delete_users.size());
	        
			precreated_user.setFirstName("CHANGED_NAME");
			db_user.delete(precreated_user);
			
            Collection<User> after_delete_users = db_user.findAll();
	        assertEquals("Collection size.", 2, after_delete_users.size());
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail(e.toString());
		}
	}
	
	public void testFindAll() throws DatabaseException {
        try{
            Collection<User> users_collection = db_user.findAll();
	        assertNotNull(users_collection);
	        assertNotNull("Collection is null", users_collection);
	        assertEquals("Collection size.", 3, users_collection.size());
        } catch (DatabaseException e){
        	e.printStackTrace();
        	fail(e.toString());
        }
    }


    @Override
    protected IDatabaseConnection getConnection() throws Exception {
    	connectionFact = new ConnectionFactoryImpl("", "", "", "");
        return new DatabaseConnection(connectionFact.createConnection());
    }

    @Override
    protected IDataSet getDataSet() throws Exception {
        IDataSet dataSet = new XmlDataSet(
                getClass()
                        .getClassLoader()
                        .getResourceAsStream("userDataSet.xml"));
        return dataSet;
    }
}

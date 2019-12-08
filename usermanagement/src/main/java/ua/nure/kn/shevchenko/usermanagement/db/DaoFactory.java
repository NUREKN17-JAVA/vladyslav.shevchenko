package ua.nure.kn.shevchenko.usermanagement.db;

import java.io.IOException;
import java.util.Properties;

public class DaoFactory {
	private static final String USER_DAO = "dao.knure.ctde.usermanagement.db.UserDao";
	private final Properties properties;
	
	private static final DaoFactory INSTANCE = new DaoFactory();
	
	public static DaoFactory getInstance(){
		return INSTANCE;
	}
	
	public DaoFactory(){
		properties = new Properties();
		try {
			properties.load(getClass().getClassLoader().getResourceAsStream("settings.properties"));
		} catch (IOException e){
			throw new RuntimeException(e);
		}
	}
	
	public ConnectionFactory getConnectionFactory(){
		String user = properties.getProperty("connection.user");
		String password = properties.getProperty("connection.password");
		String driver = properties.getProperty("connection.driver");
		String url = properties.getProperty("connection.url");
		return new ConnectionFactoryImpl(driver, url, user, password);
	}
	
	public UserDAO getUserDao(){
		UserDAO result = null;
		try {
			Class<?> clazz = Class.forName(properties.getProperty(USER_DAO));
			result = (UserDAO) clazz.newInstance();
			result.setConnectionFactory(getConnectionFactory());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return result;
	}
}

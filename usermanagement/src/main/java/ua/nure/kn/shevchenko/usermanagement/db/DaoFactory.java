package ua.nure.kn.shevchenko.usermanagement.db;

import java.io.IOException;
import java.util.Properties;

public abstract class DaoFactory {
	protected static final String USER_DAO = "dao.knure.ctde.usermanagement.db.UserDao";
	private static final String DAO_FACTORY = "dao.factory";
	protected static Properties properties;
	
	private static DaoFactory instance;
	
	static {
		properties = new Properties();
		try {
			properties.load(DaoFactory.class.getClassLoader().getResourceAsStream("settings.properties"));
		} catch (IOException e){
			throw new RuntimeException(e);
		}
	}
	
	public static synchronized DaoFactory getInstance(){
		if (instance == null)
		{
			Class factoryClass;
			try {
				factoryClass = Class.forName(properties.getProperty(DAO_FACTORY));
				instance = (DaoFactory) factoryClass.newInstance();
			} catch (Exception e) {
				throw new RuntimeException(e.toString());
			}

		}
		return instance;
	}
	
	protected DaoFactory(){
	}
	
	public static void init(Properties prop){
		properties = prop;
		instance = null;
	}
	
	public ConnectionFactory getConnectionFactory(){
		return new ConnectionFactoryImpl(this.properties);
	}
	
	public abstract UserDAO getUserDao();
}

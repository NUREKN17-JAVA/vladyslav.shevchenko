package ua.nure.kn.shevchenko.usermanagement.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class ConnectionFactoryImpl implements ConnectionFactory {
	
	String driver;
	String url;
	String user;
	String password;
	
	public ConnectionFactoryImpl(String driver, String url, String user,
			String password) {
		this.driver = "org.hsqldb.jdbcDriver";
		this.url = "jdbc:hsqldb:file:db/usermanagement";
		this.user = "sa";
		this.password = "";
	}
	public ConnectionFactoryImpl(Properties properties) {
		this.user = properties.getProperty("connection.user");
		this.password = properties.getProperty("connection.password");
		this.driver = properties.getProperty("connection.driver");
		this.url = properties.getProperty("connection.url");
	}
	
	public Connection createConnection() throws DatabaseException {
		
		
		try {
			Class.forName(this.driver);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
		try {
			return DriverManager.getConnection(this.url, this.user, this.password);
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
	}

}
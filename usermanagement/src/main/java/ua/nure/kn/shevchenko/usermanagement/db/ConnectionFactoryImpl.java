package ua.nure.kn.shevchenko.usermanagement.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
package main.java.ua.nure.kn.komanova.usermanagement.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;



public class ConnectionFactoryImpl implements ConnectionFactory {
	private String driver;
	private String url; 
	private String user;
	private String password;
	@Override
	public ConnectionFactoryImpl(String driver2, String url2, String user2, String password2) {
		this.driver = driver2;		
		this.url = url2;
		this.user = user2;
		this.password = password2;
	}

	public ConnectionFactoryImpl(Properties properties) {
		user = properties.getProperty("connection.user");
		driver = properties.getProperty("connection.driver");
		url = properties.getProperty("connection.url");
		password = properties.getProperty("connection.password");				
	}	
	
	public Connection createConnection() throws DatabaseExeption {
		try {			
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}		
		try {
			return DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			throw new DatabaseExeption(e);
		}
	}

}

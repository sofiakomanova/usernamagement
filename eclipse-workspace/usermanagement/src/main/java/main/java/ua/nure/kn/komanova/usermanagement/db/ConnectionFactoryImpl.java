package main.java.ua.nure.kn.komanova.usermanagement.db;

import java.sql.Connection;
import java.sql.DriverManager;

import main.java.ua.nure.cs.komanova.db.ClassNotFoundExeption;
import main.java.ua.nure.cs.komanova.db.RuntimeExeption;

public class ConnectionFactoryImpl implements ConnectionFactory {
	private String driver = "org.hsqldb.jdbcDriver";
	private String url="jdbc:hsqldb:file:db/usermanagement"; 
	private String user ="sa";
	private String password = "";
	@Override
	public Connection createConnection() throws DataBaseExeption {

		try {
			Class.forName(driver);
			} catch(ClassNotFoundExeption e){
				throw new RuntimeExeption(e);
				
			}
			try {
			return DriverManager.getConnection(url, user, password);
			}
			catch (SQLException e) {
				throw new DatabaseExeption(e);
			}
	}

}

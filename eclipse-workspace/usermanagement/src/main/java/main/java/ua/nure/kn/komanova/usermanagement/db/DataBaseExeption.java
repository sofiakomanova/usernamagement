package main.java.ua.nure.kn.komanova.usermanagement.db;

import java.sql.SQLException;

public class DataBaseExeption extends Exception {
	private static final long serialVersionUID = 5899773385953212700L;

	public DatabaseExeption(SQLException e) {
		super(e);
	}

	public DatabaseExeption(String string) {
		super(string);
	}	
}

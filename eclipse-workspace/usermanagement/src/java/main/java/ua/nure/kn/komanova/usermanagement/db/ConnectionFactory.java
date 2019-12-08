package main.java.ua.nure.kn.komanova.usermanagement.db;

import java.sql.Connection;

import main.java.ua.nure.kn.komanova.usermanagement.db.DataBaseExeption;

public interface ConnectionFactory {
	Connection createConnection() throws DataBaseExeption;
}

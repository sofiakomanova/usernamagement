package main.java.ua.nure.kn.komanova.usermanagement.agent;

import main.java.ua.nure.kn.komanova.usermanagement.db.DatabaseExeption;

public class SearchException extends Exception {
	private static final long serialVersionUID = 72239430268262666L;

	public SearchException(DatabaseExeption e) {
		e.printStackTrace();
	}
}

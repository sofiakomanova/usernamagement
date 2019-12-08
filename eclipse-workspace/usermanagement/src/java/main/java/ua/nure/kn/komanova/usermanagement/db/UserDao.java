package main.java.ua.nure.kn.komanova.usermanagement.db;

import main.java.ua.nure.kn.komanova.usermanagement.db.DataBaseExeption;

import java.util.Collection;

import main.java.ua.nure.kn.komanova.usermanagement.User;


public interface UserDao {
	User create(User entity) throws DataBaseExeption;

	
	void update(User entity) throws DataBaseExeption;

	void delete(User entity) throws DataBaseExeption;

	
	User find(Long id) throws DataBaseExeption;

	
	Collection<User> findAll() throws DataBaseExeption;
	void setConnectionFactory(ConnectionFactory connectoinFactory);

}

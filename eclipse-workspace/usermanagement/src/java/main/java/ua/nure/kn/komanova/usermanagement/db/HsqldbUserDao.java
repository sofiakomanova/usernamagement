package main.java.ua.nure.kn.komanova.usermanagement.db;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.LinkedList;


import main.java.ua.nure.kn.komanova.usermanagement.User;

public class HsqldbUserDao implements UserDao {


	private static final String SELECT_FIND_ALL = "SELECT id, firstname, lastname, dateofbirth FROM users";
	private static final String INSERT_QUERY = "INSERT INTO users (firstname, lastname, dateofbirth) VALUES (?,?,?)";
	private static final String UPDATE_QUERY = "UPDATE users SET firstname = ?, lastname = ?, dateofbirth = ?  WHERE id = ?";
	private static final String SELECT_BY_ID_QUERY = "SELECT * FROM users WHERE id = ?";
	private static final String DELETE_QUERY = "DELETE FROM users WHERE id = ?";
	private ConnectionFactory connectionFactory;
	
	public HsqldbUserDao() {
	}
	
	public HsqldbUserDao(ConnectionFactory connectoinFactory) {
		this.connectionFactory = connectoinFactory;
	}	
	
	public ConnectionFactory getConnectoinFactory() {
		return connectionFactory;
	}
	
	public void setConnectionFactory(ConnectionFactory connectoinFactory) {
		this.connectionFactory = connectoinFactory;
	}
	
	public User create(User entity) throws DatabaseExeption {
		try {
			Connection connection = connectionFactory.createConnection();
			PreparedStatement statement = connection.prepareStatement(INSERT_QUERY);
			statement.setString(1, entity.getFirstName());
			statement.setString(2, entity.getLastName());
			statement.setDate(3, new Date(entity.getDateOfBirth().getTime()));
			
			int numberOfRows = statement.executeUpdate();
			if (numberOfRows != 1) {
				throw new DatabaseExeption("Number of rows =" + numberOfRows);
			}
			CallableStatement callableStatement = connection
					.prepareCall("call IDENTITY()");
			ResultSet keys = callableStatement.executeQuery();
			if (keys.next()) {
				entity.setId(new Long(keys.getLong(1))); //Mutation
			}
			keys.close();
			callableStatement.close();
			statement.close();
			connection.close();
			return entity;
		} catch (DatabaseExeption e) {
			throw e;
		}
		catch (SQLException e) {
			throw new DatabaseExeption(e);
		}
	}

	public User find(Long id) throws DatabaseExeption {
		User resultUser = new User();
		try {
        	Connection connection = connectionFactory.createConnection();
        	PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID_QUERY);
        	statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
            	return null;             	  
            }            
            resultUser = new User(resultSet.getLong(1), resultSet.getString(2), resultSet.getString(3), resultSet.getDate(4));
            statement.close();
            resultSet.close();
            connection.close();
            return resultUser;    
        } catch (DatabaseExeption e) {
			throw e;
		} catch (SQLException e) {
        	throw new DatabaseExeption(e);
        }
	}

	public void update(User entity) throws DatabaseExeption {
        try  {
        	Connection connection = connectionFactory.createConnection();
        	PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY);
        	statement.setString(1, entity.getFirstName());
        	statement.setString(2, entity.getLastName());
        	statement.setDate(3, new Date(entity.getDateOfBirth().getTime()));
        	statement.setLong(4, entity.getId());
            int updatedRows = statement.executeUpdate();
            if (updatedRows != 1) {
                throw new DatabaseExeption("Number of updated rows =" + updatedRows);
            }
            statement.close();
            connection.close();
        } catch (DatabaseExeption e) {
			throw e;
		} catch (SQLException e) {
        	throw new DatabaseExeption(e);
        }		
	}

	public void delete(User entity) throws DatabaseExeption {
        try  {
        	Connection connection = connectionFactory.createConnection();
        	PreparedStatement statement = connection.prepareStatement(DELETE_QUERY);
        	statement.setLong(1, entity.getId());
            int updatedRows = statement.executeUpdate();
            if (updatedRows != 1) {
                throw new DatabaseExeption("Number of deleted rows =" + updatedRows);
            }
            statement.close();
            connection.close();
        } catch (DatabaseExeption e) {
			throw e;
		} catch (SQLException e) {
        	throw new DatabaseExeption(e);
        }		
	}

	public Collection<User> findAll() throws DatabaseExeption {
		Collection<User> result = new LinkedList<User>();
		try {
			Connection connection = connectionFactory.createConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(SELECT_FIND_ALL);
			while (resultSet.next()) {
				User user = new User();
				user.setId(resultSet.getLong(1));
				user.setFirstName(resultSet.getString(2));
				user.setLastName(resultSet.getString(3));
				user.setDateOfBirth(resultSet.getDate(4));
				result.add(user);
			}
			resultSet.close();
			statement.close();
			connection.close();
		} catch (DatabaseExeption e) {
			throw e;
		} catch (SQLException e) {
			throw new DatabaseExeption(e);
		}
		return result;
	}
	public Collection<User> find(String firstName, String lastName) throws DatabaseExeption {
		Collection<User> result = new LinkedList<User>();
		try {
			Connection connection = connectionFactory.createConnection();
			PreparedStatement statement = connection.prepareStatement(SELECT_BY_NAMES_QUERY);
			statement.setString(1, firstName);
			statement.setString(2, lastName);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				User user = new User();
				user.setId(new Long(resultSet.getLong(1)));
				user.setFirstName(resultSet.getString(2));
				user.setLastName(resultSet.getString(3));
				user.setDateOfBirth(resultSet.getDate(4));
				result.add(user);
			}
		} catch (DatabaseExeption e) {
			throw e;
		} catch (SQLException e) {
			throw new DatabaseExeption(e);
		}
		
		return result;
	}


}

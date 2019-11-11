package main.java.ua.nure.kn.komanova.usermanagement.db;

import java.io.IOException;
import java.util.Properties;


public class DaoFactory {
	private static final String USER_DAO = "ua.nure.kn.komanova.usermanagement.db.UserDao";
	private static final String DAO_FACTORY = "dao.factory";
	private static Properties properties;
	
	private static DaoFactory INSTANCE = new DaoFactory();
	
	private DaoFactory() {
		properties = new Properties();
	try {
		properties.load(getClass().getClassLoader().getResourceAsStream("settings.properties"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}		
	}
	
	private ConnectionFactory getConnectionFactory () {
		String user = properties.getProperty("connection.user");
		String password = properties.getProperty("connection.password");
		String url = properties.getProperty("connection.url");
		String driver = properties.getProperty("connection.driver");
		return new ConnectionFactoryImpl(driver, url, user, password);		
	}
	
	public UserDao getDao () {
		UserDao result = null;
		try {
			Class clazz = Class.forName(properties.getProperty(USER_DAO));
			result = (UserDao) clazz.newInstance();
			result.setConnectionFactory(getConnectionFactory());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}		
		return result;
	}
	
	public static synchronized DaoFactory getInstance() {
		if (INSTANCE == null) {
			Class<?> factoryClass;
			try {
				factoryClass = Class.forName(properties.getProperty(DAO_FACTORY));
				INSTANCE = (DaoFactory) factoryClass.newInstance();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return INSTANCE;
	}
}

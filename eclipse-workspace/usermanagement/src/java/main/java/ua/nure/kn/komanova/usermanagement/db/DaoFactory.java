package main.java.ua.nure.kn.komanova.usermanagement.db;

import java.io.IOException;
import java.util.Properties;


public abstract class DaoFactory {
	public class DaoFactoryImpl {

	}
	private static final String USER_DAO = "ua.nure.kn.komanova.usermanagement.db.UserDao";
	private static final String DAO_FACTORY = "dao.factory";
	private static Properties properties;
	
	private static DaoFactory INSTANCE;
	
	private DaoFactory() {
		properties = new Properties();
	try {
		properties.load(DaoFactory.class.getResourceAsStream(
				"settings.properties"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}		
	}
	
	protected ConnectionFactory getConnectionFactory () {
		return new ConnectionFactoryImpl(properties);
	
	}
	public static void init (Properties pr) {
		properties = pr;
		INSTANCE = null;
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
	public abstract DaoFactory getUserDao();
	
	
}

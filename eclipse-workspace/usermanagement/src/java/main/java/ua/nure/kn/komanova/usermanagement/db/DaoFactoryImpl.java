package main.java.ua.nure.kn.komanova.usermanagement.db;

public class DaoFactoryImpl extends DaoFactory{
	public UserDao getUserDao() {
        UserDao result = null;
        try {
            Class<?> clazz = Class.forName(properties.getProperty(USER_DAO));
            result = (UserDao) clazz.newInstance();
            result.setConnectionFactory(getConnectionFactory());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
	}
}

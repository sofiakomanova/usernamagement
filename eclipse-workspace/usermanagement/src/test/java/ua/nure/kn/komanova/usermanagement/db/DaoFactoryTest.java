package ua.nure.kn.komanova.usermanagement.db;

import junit.framework.TestCase;
import main.java.ua.nure.kn.komanova.usermanagement.db.DaoFactory;
import main.java.ua.nure.kn.komanova.usermanagement.db.UserDao;

public class DaoFactoryTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testGetDao() {
		try {
			DaoFactory daoFactory = DaoFactory.getInstance();
			assertNotNull("DaoFactory instanse is null", daoFactory);
			UserDao userDao = daoFactory.getDao();
			assertNotNull("UserDao instanse is null", userDao);
		} catch (RuntimeException e) {
			e.printStackTrace();
            fail(e.toString());
		}		
	}
}

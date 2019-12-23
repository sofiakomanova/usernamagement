package ua.nure.kn.komanova.usermanagement.web;

import java.util.Properties;

import org.junit.After;
import org.junit.Before;

import com.mockobjects.dynamic.Mock;
import com.mockrunner.servlet.BasicServletTestCaseAdapter;

import java.main.ua.nure.kn.komanova.usermanagement.db.DaoFactory;
import java.main.ua.nure.kn.komanova.usermanagement.db.MockDaoFactory;
public abstract class MockServletTestCase extends BasicServletTestCaseAdapter {

    private Mock mockUserDao;
    
    @Before
    public void setUp() throws Exception {
        super.setUp();
        Properties properties = new Properties();
        properties.setProperty("dao.Factory", MockDaoFactory.class.getName());
        DaoFactory.init(properties);
        setMockUserDao(((MockDaoFactory) DaoFactory.getInstance()).getMockUserDao());
    }

    @After
    public void tearDown() throws Exception {
        getMockUserDao().verify();
        super.tearDown();
    }
    
    public Mock getMockUserDao() {
        return mockUserDao;
    }
    
    public void setMockUserDao(Mock mockUserDao) {
        this.mockUserDao = mockUserDao;
    }
}

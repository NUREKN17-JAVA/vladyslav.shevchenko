package ua.nure.kn.shevchenko.usermanagement.web;

import java.util.Properties;

import org.junit.After;
import org.junit.Before;

import com.mockobjects.dynamic.Mock;
import com.mockrunner.servlet.BasicServletTestCaseAdapter;

import ua.nure.kn.shevchenko.usermanagement.db.DaoFactory;
import ua.nure.kn.shevchenko.usermanagement.db.MockDaoFactory;
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
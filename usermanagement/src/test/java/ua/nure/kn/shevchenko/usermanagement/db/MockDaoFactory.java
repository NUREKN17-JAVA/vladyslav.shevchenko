package ua.nure.kn.shevchenko.usermanagement.db;

import com.mockobjects.dynamic.Mock;

public class MockDaoFactory extends DaoFactory {
	private Mock mockUserDao;

    MockDaoFactory() {
        mockUserDao = new Mock(UserDAO.class);
    }

    public Mock getMockUserDao() {
        return mockUserDao;
    }
	@Override
	public UserDAO getUserDao() {
		return (UserDAO) mockUserDao.proxy();
	}

}

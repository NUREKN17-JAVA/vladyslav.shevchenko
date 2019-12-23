package ua.nure.kn.shevchenko.usermanagement.web;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;

import ua.nure.kn.shevchenko.usermanagement.User;

class BrowseServletTest extends MockServletTestCase {

	private static final String FIRST_NAME = "John";
	private static final String LAST_NAME = "Doe";
	
	public void setUp() throws Exception {
        super.setUp();
        createServlet(BrowseServlet.class);
    }
	
	public void testBrowse() {
        User user = new User(new Long(1000), FIRST_NAME, LAST_NAME, new Date());
        List list = Collections.singletonList(user);
        getMockUserDao().expectAndReturn("findAll", list);
        doGet();
        Collection collection = (Collection) getWebMockObjectFactory().getMockSession().getAttribute("users");
        assertNotNull("Could not find list of users in session", collection);
        assertSame(list, collection);
    }

    public void testEdit() {
        User expectedUser = new User(1000L, FIRST_NAME, LAST_NAME, new Date());
        getMockUserDao().expectAndReturn("find", 1000L, expectedUser);
        addRequestParameter("edit", "Edit");
        addRequestParameter("id", "1000");
        doPost();
        User returnedUser = (User) getWebMockObjectFactory().getMockSession().getAttribute("user");
        assertNotNull(returnedUser);
        assertSame(expectedUser, returnedUser);
    }

    public void testDelete() {
        User user = new User(1000L, FIRST_NAME, LAST_NAME, new Date());
        getMockUserDao().expectAndReturn("find", 1000L, user);
        addRequestParameter("id", String.valueOf(1000L));
        addRequestParameter("delete", "Delete");
        doPost();
        User returnedUser = (User) getWebMockObjectFactory().getMockSession().getAttribute("user");
        assertNotNull(returnedUser);
        assertSame(user, returnedUser);
    }

    public void testDetails() {
        User user = new User(1000L, FIRST_NAME, LAST_NAME, new Date());
        getMockUserDao().expectAndReturn("find", 1000L, user);
        addRequestParameter("id", String.valueOf(1000L));
        addRequestParameter("details", "Details");
        doPost();
        User returnedUser = (User) getWebMockObjectFactory().getMockSession().getAttribute("user");
        assertNotNull(returnedUser);
        assertSame(user, returnedUser);
    }
	
	@Test
	void test() {
		testBrowse();
		testEdit();
	}
}
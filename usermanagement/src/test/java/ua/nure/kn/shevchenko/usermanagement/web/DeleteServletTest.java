package ua.nure.kn.shevchenko.usermanagement.web;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ua.nure.kn.shevchenko.usermanagement.User;

class DeleteServletTest extends MockServletTestCase {
	
	private static final String OK_BUTTON = "Ok";
	private static final String OK_BUTTON_PARAMETER = "ok";
	private static final String USER_SESSION_ATTRIBUTE_NAME = "user";
	private static final String FIRST_NAME = "John";
	private static final String LAST_NAME = "Doe";

	@BeforeEach
	public void setUp() throws Exception {
		super.setUp();
	}
	
	public void testDelete() {
		User user = new User(new Long(1000), FIRST_NAME, LAST_NAME, new Date());
        getMockUserDao().expect("delete", user);
        setSessionAttribute(USER_SESSION_ATTRIBUTE_NAME, user);
        addRequestParameter(OK_BUTTON_PARAMETER, OK_BUTTON);
        doPost();
	}

	@Test
	void test() {
		
	}
}
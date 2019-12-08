package ua.nure.kn.shevchenko.usermanagement;

import junit.framework.TestCase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.time.LocalDate;
import java.time.ZoneId;

public class UserTest extends TestCase {
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static final String FIRST_NAME = "Vladyslav";
    private static final String LAST_NAME = "Shevchenko";
    private static final String BIRTH_DATE = "2000-10-15";
    private static final int AGE = 19;
    
    private User user;
    
    protected void setUp() throws Exception{
        super.setUp();
        this.user = new User(1L, FIRST_NAME, LAST_NAME, DATE_FORMAT.parse(BIRTH_DATE));
    }

    public void testGetFullName() {
        assertEquals("Shevchenko Vladyslav", user.getFullName());
    }
    
    public void testGetAge(){
        assertEquals(AGE, user.getAge());
    }
    
    public void testGetAgeToday(){
    	User test_user = new User(1L, FIRST_NAME, LAST_NAME, new Date());
        assertEquals(0, test_user.getAge());
    }
    public void testGetAgeTodayYearsAgo(){
    	LocalDate localdate = LocalDate.now().minusDays(365);
    	Date date = Date.from(localdate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    	User test_user = new User(1L, FIRST_NAME, LAST_NAME, date);
        assertEquals(1, test_user.getAge());
        }
  }

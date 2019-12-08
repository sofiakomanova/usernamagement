package ua.nure.kn.komanova.usermanagement;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import junit.framework.TestCase;
import main.java.ua.nure.kn.komanova.usermanagement.User;

public class UserTest extends TestCase {
	
	
	private static final int AGE = 19;
	private static final String BIRTH_DATE = "29-Apr-2000";
	private static final String DATE_FORMAT = "d-MMM-yyyy";
	private static final String LAST_NAME = "Иванов";
	private static final String FIRST_NAME = "Иван";
	
	private User user;
	private Date dateOfBirth;
	

	protected void setUp() throws Exception {
		super.setUp();
		new User(1L, FIRST_NAME, LAST_NAME, new SimpleDateFormat(DATE_FORMAT).parse(BIRTH_DATE));
	}
	 public void testGetFullName() {
	        assertEquals("Иванов, Иван", user.getFullName());
	    }
	  public void testGetAge(){
		  user.setDateOfBirth (dateOfBirth);
			assertEquals(AGE, user.getAge() );
	    }
	  public void testAgeTodaysBirthday() {
			Calendar calendar = Calendar.getInstance();
			calendar.set(calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
			user.setDateOfBirth(calendar.getTime());
			int age = user.getAge();
			assertEquals(AGE, age);
		}

}
package main.java.ua.nure.kn.komanova.usermanagement;


import java.util.Calendar;
import java.util.Date;

public class User {
	private Long id;
	private String firstName;
	private String lastName;
	private Date dateOfBirth;

	
	public User(long id, String firstName, String lastName, Date dateOfBirth) {
		this.id = id;
    	this.firstName = firstName;
    	this.lastName = lastName;
    	this.dateOfBirth = dateOfBirth;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getFullName() {
		return lastName + ", " + firstName; 
	}
	
	public int getAge() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date() );
		int currentYear = calendar.get(Calendar.YEAR);
		calendar.setTime(getDateOfBirth() );
		int year = calendar.get (Calendar.YEAR);
		return currentYear - year;
	}



}

package ua.nure.kn.shevchenko.usermanagement;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

public class User implements Serializable {
	static final long serialVersionUID = -6321546794596617232L;
    private long id;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    
    public User(long id, String firstName, String lastName, Date dateOfBirth) {
    	this.id = id;
    	this.firstName = firstName;
    	this.lastName = lastName;
    	this.dateOfBirth = dateOfBirth;
    }
    
    public User() {
    }
    
	public long getId() {
		return id;
	}
	public void setId(long id) {
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
	public String getFullName(){
		return lastName + " " + firstName; 
	}
	
	public int getAge() {
		LocalDate today = LocalDate.now();
		LocalDate birthDate = dateOfBirth.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(); 
		Period p = Period.between(birthDate, today);
		return p.getYears();
    }

}
package ua.nure.kn.shevchenko.usermanagement;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

public class User implements Serializable {
	static final long serialVersionUID = -6321546794596617232L;
    private Long id;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    
    public User(Long id, String firstName, String lastName, Date dateOfBirth) {
    	this.id = id;
    	this.firstName = firstName;
    	this.lastName = lastName;
    	this.dateOfBirth = dateOfBirth;
    }
    
    public User(String firstName, String lastName, Date date) {
    	this.firstName = firstName;
    	this.lastName = lastName;
    	this.dateOfBirth  = date;
    }
    
	public User() {
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return this.id;
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
	
	public boolean equals(Object obj){
		if (obj == null)
			return false;
		
		if (this == obj)
			return true;
		
		if (this.getId() == null && ((User) obj).getId() == null)
            return true;
        
		
		return super.equals(((User) obj).getId());
	}
	
	public int hashCode() {
        if (this.getId() == null) {
            return 0;
        }
        return this.getId().hashCode();
    }

}
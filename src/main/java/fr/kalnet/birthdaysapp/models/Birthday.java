package fr.kalnet.birthdaysapp.models;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "birthday_table")
public class Birthday {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private LocalDate date;
	private String firstname;
	private String lastname;
	
	@ManyToOne
	@JoinColumn(name = "userid", nullable = false)
	private User user;
	
	public Birthday() {
		super();
	}
	
	public Birthday(LocalDate pDate, String pFirstName, String pLastName, User pUser) {
		super();
		this.date = pDate;
		this.firstname = pFirstName;
		this.lastname = pLastName;
		this.user = pUser;
	}	
	
	public Long getId() {
		return id;
	}
	
	public LocalDate getDate() {
		return date;
	}
	
	public String getFirstName() {
		return firstname;
	}
	
	public String getLastName() {
		return lastname;
	}
	
	public Long getUserid() {
		return user.getId();
	}
	
	public void setDate(LocalDate date) {
		this.date = date;
	}
	
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	@Override
	public String toString() {
		return "\n\t\tBirthday"
				+ "\n\t\t\tid=" + id 
				+ "\n\t\t\tdate=" + date 
				+ "\n\t\t\tfirstName=" + firstname 
				+ "\n\t\t\tlastName=" + lastname 
				+ "]\n";
	}
}

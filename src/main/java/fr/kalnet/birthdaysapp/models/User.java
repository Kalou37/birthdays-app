package fr.kalnet.birthdaysapp.models;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;



@Entity
@Table(name = "user_table")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String username;
	private String email;
	private String password;
	
	@OneToMany(mappedBy="user")
	private Set<Birthday> birthdays;
	
	public User() {
		super();
	}
	
	public User(String pUsername, String pEmail, String pPassword) {
		super();
		this.username = pUsername;
		this.email = pEmail;
		this.password = pPassword;
	}
	
	public User(Long pId, String pUsername, String pEmail, String pPassword) {
		super();
		this.username = pUsername;
		this.email = pEmail;
		this.password = pPassword;
		this.id = pId;
	}
	
	
	public Long getId() {
		return id;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void addBirthday(Birthday birthday) {
		this.birthdays.add(birthday);
	}
	
	public Set<Birthday> getBirthdays() {
		return birthdays;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	@Override
	public String toString() {
		return "User"
				+ "\n\tid=" + id 
				+ "\n\tusername=" + username 
				+ "\n\temail=" + email 
				+ "\n\tpassword=" + password 
				+ "\n\tbirthdays=" + birthdays.toString()
				+ "]\n";
	}
}

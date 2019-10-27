package com.psoft.project.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {
	@Id
	private String email;
	private String firstName;
	private String lastName;
	private String credCard;
	private String password;
	
	public User(String email, String firstName, String lastName, String credCard, String password) {
		super();
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.credCard = credCard;
		this.password = password;
	}

	public User() {
		
	}

	public String getEmail() {
		return email;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getCredCard() {
		return credCard;
	}

	public String getPassword() {
		return password;
	}

}

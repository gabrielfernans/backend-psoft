package com.psoft.project.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;


@Entity
public class User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@NotBlank(message = "{email.not.blank}")
	@Email(message = "{email.not.valid}")
	@Id
	private String email;
	@NotBlank(message = "{firstName.not.blank}")
	private String firstName;
	@NotBlank(message = "{lastName.not.blank}")
	private String lastName;
	@NotBlank(message = "{credCard.not.blank}")
	private String credCard;
	@NotBlank(message = "{password.not.blank}")
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
	
	public void setPassword(String password) {
		this.password = password;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setCredCard(String credCard) {
		this.credCard = credCard;
	}

}

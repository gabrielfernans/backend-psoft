package com.psoft.project.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;


@Entity
public class User {
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

}

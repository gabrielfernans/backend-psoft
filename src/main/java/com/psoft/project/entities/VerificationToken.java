package com.psoft.project.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class VerificationToken {
	@OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
	private User user;
	@Id
	private String token;
	
	public VerificationToken() {
	}
	
	public VerificationToken(User user, String token) {
		super();
		this.user = user;
		this.token = token;
	}
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public User getUser() {
		return user;
	}
}

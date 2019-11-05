package com.psoft.project.entities;

import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

public class Comment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String comment;
	@ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
	private User user;
	
	public Comment() {
		super();
	}

	public Comment(String comment, User user) {
		super();
		this.comment = comment;
		this.user = user;
	}

	@Override
	public String toString() {
		return comment;
	}

	public User getUser() {
		return user;
	}
	
}

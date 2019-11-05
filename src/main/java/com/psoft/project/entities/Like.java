package com.psoft.project.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.OneToOne;

@Entity
@IdClass(Like.class)
public class Like implements Serializable{
	@Id
	@OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
	private User user;

	public Like(User user) {
		super();
		this.user = user;
	}

	public Like() {
	}
	
	
	
}

package com.psoft.project.entities;

import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

public class Like {
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

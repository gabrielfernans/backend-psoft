package com.psoft.project.entities;

import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

public class Like {
	@Id
	@ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
	private User user;
}

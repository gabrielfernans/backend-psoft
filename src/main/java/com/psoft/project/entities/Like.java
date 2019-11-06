package com.psoft.project.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
@IdClass(Like.class)
public class Like implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
	private User user;
	
	//@ManyToOne(targetEntity = Campaign.class, fetch = FetchType.EAGER)
	//private Campaign campaign;

	public Like(User user) {
		super();
		this.user = user;
	}

	public Like() {
	}
	
	
	
}

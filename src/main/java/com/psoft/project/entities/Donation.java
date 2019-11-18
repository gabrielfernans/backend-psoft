package com.psoft.project.entities;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;

@Entity
public class Donation {
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private LocalDate date;
	@NotBlank(message = "{value.not.blank}")
	private Double value;
	@OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
	private User owner;
	@OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
	private Campaign campaign;
	
	
	public Donation() {
		super();
	}
	
	public Donation(LocalDate date, Double value, User owner, Campaign campaign) {
		super();
		this.date = date;
		this.value = value;
		this.owner = owner;
		this.campaign = campaign;
	}
	
	public LocalDate getDate() {
		return date;
	}
	
	public void setDate(LocalDate date) {
		this.date = date;
	}
	
	public Double getValue() {
		return value;
	}
	
	public void setValue(Double value) {
		this.value = value;
	}
	
	public User getOwner() {
		return owner;
	}
	
	public void setOwner(User owner) {
		this.owner = owner;
	}
	
	public Campaign getCampaign() {
		return campaign;
	}
	
	public void setCampaign(Campaign campaign) {
		this.campaign = campaign;
	}
	
}

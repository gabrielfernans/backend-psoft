package com.psoft.project.entities;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Donation {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JoinColumn(name="donation_id")
	private int id;
	@NotNull()
	private LocalDate date;
	@NotNull(message = "{value.not.blank}")
	private Double value;
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@OneToOne(targetEntity = User.class, fetch = FetchType.LAZY)
	private User donor;	
	
	public Donation() {
		super();
	}
	
	public Donation(LocalDate date, Double value, User owner) {
		super();
		this.date = date;
		this.value = value;
		this.donor = owner;
	}
	
	public LocalDate getDate() {
		return date;
	}
	
	public Double getValue() {
		return value;
	}
	
	public User getDonor() {
		return donor;
	}
	
	public Integer getIdDonation() {
		return id;
	}
	
	public void setDate(LocalDate date) {
		this.date = date;
	}
	
	public void setValue(Double value) {
		this.value = value;
	}
	
	public void setOwner(User owner) {
		this.donor = owner;
	}
}

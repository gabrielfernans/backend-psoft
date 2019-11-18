package com.psoft.project.entities;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.psoft.project.exceptions.InvalidDateException;

@Entity
public class Campaign {
	@NotNull(message = "{id.not.blank}")
	@Id
	private Integer id;
	@NotBlank(message = "{name.not.blank}")
	private String name;
	@NotBlank(message = "{urlId.not.blank}")
	private String urlId;
	@NotBlank(message = "{description.not.blank}")
	private String description;
	@NotNull(message = "{deadLine.not.blank}")
	@JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate deadLine;
	private String status;
	@NotNull(message = "{goal.not.blank}")
	private Double goal;
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@OneToMany(targetEntity = Donation.class, fetch = FetchType.LAZY)
	private List<Donation> donations;
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@OneToOne(targetEntity = User.class, fetch = FetchType.LAZY)
	private User owner;
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@OneToMany(targetEntity = Comment.class, fetch = FetchType.LAZY)
	private List<Comment> comments;
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@OneToMany(targetEntity = User.class, fetch = FetchType.LAZY)
	private List<User> likes;
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@OneToMany(targetEntity = User.class, fetch = FetchType.LAZY)
	private List<User> dislikes;
	
	public Campaign(int id, String name, String urlId, String description, String deadLine,
			Double goal) {
		super();
		this.id = id;
		this.name = name;
		this.urlId = urlId;
		this.description = description;
		LocalDate d;
		try {
			d = LocalDate.parse(deadLine); 
		}catch(DateTimeParseException e) {
			throw new InvalidDateException("deadLine deve ser valido");
		}
		if(d.isBefore(LocalDate.now()))
			throw new InvalidDateException("deadLine deve ser valido");
		this.deadLine = d;
		this.status = "Ativa";
		this.goal = goal;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getUrlId() {
		return urlId;
	}

	public String getDescription() {
		return description;
	}

	public LocalDate getDeadLine() {
		return deadLine;
	}

	public String getStatus() {
		return status;
	}

	public Double getGoal() {
		return goal;
	}

	public List<Donation> getDonations() {
		return donations;
	}

	//@OneToOne(cascade = CascadeType.ALL, mappedBy = "owner")
	public User getOwner() {
		//this.owner.setPassword("");
		//this.owner.setCredCard("");
		return owner;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public List<User> getLikes() {
		return likes;
	}
	
	public List<User> getDislikes() {
		return dislikes;
	}

	public Campaign() {
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setUrlId(String urlId) {
		this.urlId = urlId;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setDeadLine(LocalDate deadLine) {
		this.deadLine = deadLine;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setGoal(Double goal) {
		this.goal = goal;
	}

	public void setDonations(List<Donation> donations) {
		this.donations = donations;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public void setLikes(List<User> likes) {
		this.likes = likes;
	}

	public void setDislikes(List<User> dislikes) {
		this.dislikes = dislikes;
	}
	
	public int addLike(User user) {
		if(likes.contains(user)) {
			likes.remove(user);
		}else if(!dislikes.contains(user)) likes.add(user);
		return likes.size();
	}
	
	public int addDislike(User user) {
		if(dislikes.contains(user)) {
			dislikes.remove(user);
		}else if(!likes.contains(user)) dislikes.add(user);
		return dislikes.size();
	}
	
	public void addComment(String comment, User user) {
//		Comment c = new Comment(comment, user);
//		comments.save(c);
//		return c;
	}
	
	public Donation addDonation(User user, Double value) {
		Donation d = null;
		if(value > 0) {
			d = new Donation(LocalDate.now(), value, user, this);
			donations.add(d);
		}
		return d;		
	}
	
}

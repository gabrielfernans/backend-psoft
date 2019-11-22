package com.psoft.project.entities;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
public class Comment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@NotBlank(message = "{description.not.blank}")
	private String comment;
	@NotBlank(message = "{urlId.not.blank}")
	private String urlIdComment;
	@OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
	private User user;
	@NotNull(message = "{deadLine.not.blank}")
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate date;
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@OneToMany(targetEntity = Comment.class, fetch = FetchType.LAZY)
	private List<Comment> replies;
	@ManyToOne(targetEntity = Campaign.class, fetch = FetchType.EAGER)
	private Campaign campaign;
	
	
	public Comment() {
	}

	public Comment(String comment, User user, Campaign campaign) {
		super();
		this.comment = comment;
		this.user = user;
		this.campaign = campaign;
		this.date = LocalDate.now();
		this.replies = new LinkedList<Comment>();
	}

	@Override
	public String toString() {
		return comment;
	}

	public User getUser() {
		return user;
	}
	
	public String getUrlIdComment() {
		return urlIdComment;
	}
	
	public LocalDate getDate() {
		return this.date;
	}
	
	public List<Comment> getReplies() {
		return this.replies;
	}
	
	public Campaign getCampaign() {
		return this.campaign;
	}
	
	public Comment addReply(User user, String comment, Campaign campaign) {
		Comment reply = new Comment(comment, user, campaign);
		this.replies.add(reply);
		return reply;
	}
	
	
	
	
}

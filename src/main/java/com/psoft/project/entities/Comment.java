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
	@OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
	private User user;
	@NotNull(message = "{deadLine.not.blank}")
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate date;
	@NotNull(message = "comment.not.blank}")
	private String comment;
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@OneToMany(targetEntity = Comment.class, fetch = FetchType.LAZY)
	private List<Comment> replies;
	@NotNull(message = "isDeleted.not.blank}")
	private Boolean isDeleted;
	
	public Comment() {
	}

	public Comment(String comment, User user) {
		super();
		this.comment = comment;
		this.user = user;
		this.date = LocalDate.now();
		this.replies = new LinkedList<Comment>();
		this.isDeleted = false;
	}
	
	

	@Override
	public String toString() {
		String resp = null;
		if(!isDeleted())
			resp = this.comment;
		return resp;
	}
	
	public String getComment() {
		return comment;
	}
	
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public Integer getId() {
		return id;
	}

	public User getUser() {
		return user;
	}
	
	public Boolean isDeleted() {
		return this.isDeleted;
	}
 	
	
	public LocalDate getDate() {
		return this.date;
	}
	
	public List<Comment> getReplies() {
		return this.replies;
	}
	
	
	
	public Comment addReply(User user, String comment) {
		Comment reply = new Comment(comment, user);
		this.replies.add(reply);
		return reply;
	}
	
	public void deleteComment() {
		this.isDeleted = true;
	}
	
	
	
	
}

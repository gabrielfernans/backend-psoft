package com.psoft.project.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
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
	private long id;
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@OneToOne(targetEntity = User.class, fetch = FetchType.LAZY)
	private User user;
	@NotNull(message = "{deadLine.not.null}")
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate date;
	@NotNull(message = "text.not.null}")
	private String text;
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@ManyToMany(targetEntity = Comment.class, fetch = FetchType.LAZY)
	private List<Comment> replies;
	@NotNull(message = "isDeleted.not.null}")
	private Boolean isDeleted;
	
	public Comment() {
	}

	 public Comment(Campaign campaign, User user, String text, LocalDate date, ArrayList<Comment> replies) {
	        this.user = user;
	        this.text = text;
	        this.date = date;
	        this.replies = replies;
	        this.isDeleted = false;
	    }
	
	@Override
	public String toString() {
		String resp = null;
		if(!this.getIsDeleted())
			resp = this.text;
		return resp;
	}
	
	public void addReply(Comment reply) {
        replies.add(reply);
    }

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<Comment> getReplies() {
		return replies;
	}

	public void setReplies(List<Comment> replies) {
		this.replies = replies;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	
	
	
	
}
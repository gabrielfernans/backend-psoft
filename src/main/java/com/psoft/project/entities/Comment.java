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
	@OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
	private User user;
	@NotNull(message = "{deadLine.not.blank}")
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate date;
	@NotNull(message = "text.not.blank}")
	private String text;
	@ManyToOne(targetEntity = Campaign.class, fetch = FetchType.EAGER)
	private Campaign campaign;
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@OneToMany(targetEntity = Comment.class, fetch = FetchType.LAZY)
	private List<Comment> replies;
	@NotNull(message = "isDeleted.not.blank}")
	private Boolean isDeleted;
	
	public Comment() {
	}

	 public Comment(Campaign campaign, User user, String text, LocalDate date, ArrayList<Comment> replies) {
	        this.campaign = campaign;
	        this.user = user;
	        this.text = text;
	        this.date = date;
	        this.replies = replies;
	    }
	
	

	@Override
	public String toString() {
		String resp = null;
		if(!isDeleted())
			resp = this.text;
		return resp;
	}
	
	public String getComment() {
		return text;
	}
	
	public void setComment(String text) {
		this.text = text;
	}
	
	public long getId() {
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
	
	public void deleteComment() {
        this.isDeleted = true;
        if (!getReplies().isEmpty()) {
            deleteReplies(this.getReplies());
        }
    }
	
	private void deleteReplies(List<Comment> replies) {
        if (!replies.isEmpty()) {
            for (Comment r : replies) {
                r.deleteComment();
            }
        }
    }

	
	public Campaign getCampaign() {
		return this.campaign;
	}
	
	public void setCampaign(Campaign campaign) {
		this.campaign = campaign;
	}
	
	public void addReply(Comment reply) {
        replies.add(reply);
    }
	
	
	
	
}

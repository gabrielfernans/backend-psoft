package com.psoft.project.entities;

import java.sql.Date;

import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.psoft.project.repositories.CommentRepository;
import com.psoft.project.repositories.LikeRepository;

public class Campaign {
	@Id
	private Integer id;
	private String name;
	private String urlId;
	private String description;
	@JsonFormat(pattern="yyyy-MM-dd")
    private Date deadLine;
	private String status;
	private Double goal;
	private Double donations;
	@OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
	private User owner;
	private CommentRepository<Comment, Integer> comments;
	private LikeRepository<Like, User> likes;
	
	public Campaign(Integer id, String name, String urlId, String description, Date deadLine, String status,
			Double goal, Double donations, User owner) {
		super();
		this.id = id;
		this.name = name;
		this.urlId = urlId;
		this.description = description;
		this.deadLine = deadLine;
		this.status = status;
		this.goal = goal;
		this.donations = donations;
		this.owner = owner;
	}

	public Campaign() {
	}
	
	public Long addLike(User user) {
		Like like = new Like(user);
		likes.save(like);
		return likes.count();
	}
	
	public Long deleteLike(User user) {
		if(likes.findById(user)!= null)
			likes.deleteById(user);
		return likes.count();
	}
	
	public Comment addComment(String comment, User user) {
		Comment c = new Comment(comment, user);
		comments.save(c);
		return c;
	}
	
	
}

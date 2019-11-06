package com.psoft.project.entities;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.psoft.project.exceptions.InvalidDateException;

@Entity
public class Campaign {
	@NotBlank(message = "{id.not.blank}")
	@Id
	private Integer id;
	@NotBlank(message = "{name.not.blank}")
	private String name;
	@NotBlank(message = "{urlId.not.blank}")
	private String urlId;
	@NotBlank(message = "{description.not.blank}")
	private String description;
	@NotBlank(message = "{date.not.blank}")
	@JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate deadLine;
	@NotBlank(message = "{status.not.blank}")
	private String status;
	@NotBlank(message = "{goal.not.blank}")
	private Double goal;
	@NotBlank(message = "{donation.not.blank}")
	private Double donations;
	@NotBlank(message = "{user.not.blank}")
	@OneToOne(targetEntity = User.class, fetch = FetchType.LAZY)
	private User owner;
	@OneToMany(targetEntity = Comment.class, fetch = FetchType.LAZY)
	//@NotBlank(message = "{comments.not.blank}")
	private List<Comment> comments;
	@OneToMany(targetEntity = User.class, fetch = FetchType.LAZY)
	//@NotBlank(message = "{likes.not.blank}")
	private List<User> likes;
	
	public Campaign(Integer id, String name, String urlId, String description, LocalDate deadLine, String status,
			Double goal, Double donations, User owner) {
		super();
		this.id = id;
		this.name = name;
		this.urlId = urlId;
		this.description = description;
		if(!LocalDate.now().isBefore(deadLine))
			throw new InvalidDateException("deadLine deve ser valido");
		this.deadLine = deadLine;
		this.status = status;
		this.goal = goal;
		this.donations = donations;
		this.owner = owner;
	}

	public Campaign() {
	}
	
	public Long addLike(User user) {
//		Like like = new Like(user);
//		likes.save(like);
//		return likes.count();
		return (long) 1.0;
	}
	
	public Long deleteLike(User user) {
//		if(likes.findById(user)!= null)
//			likes.deleteById(user);
//		return likes.count();
		return (long) 1.0;
	}
	
	public void addComment(String comment, User user) {
//		Comment c = new Comment(comment, user);
//		comments.save(c);
//		return c;
	}
	
	
}

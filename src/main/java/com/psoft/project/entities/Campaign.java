package com.psoft.project.entities;

import java.sql.Date;

import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

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
	private User owner;
	private Comment comment;
	private Integer likes;
	
}

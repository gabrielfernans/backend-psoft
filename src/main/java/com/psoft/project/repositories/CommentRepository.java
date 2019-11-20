package com.psoft.project.repositories;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.psoft.project.entities.Comment;


public interface CommentRepository<T, ID extends Serializable> extends JpaRepository<Comment, Integer> {
	
	//@Query(value = "select c from Comment where c.urlid = url")
	Comment findByUrlId(String url);
	
}

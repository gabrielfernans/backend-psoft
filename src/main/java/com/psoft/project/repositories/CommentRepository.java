package com.psoft.project.repositories;

import java.io.Serializable;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.psoft.project.entities.Comment;

@Repository
public interface CommentRepository<T, ID extends Serializable> extends JpaRepository<Comment, Integer> {
	
	//@Query(value = "select c from Comment where c.id = id")
	Comment findById(String id);
	
}

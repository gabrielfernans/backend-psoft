package com.psoft.project.repositories;

import java.io.Serializable;
import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.psoft.project.entities.Comment;

@Repository
public interface CommentRepository<T, ID extends Serializable> extends JpaRepository<Comment, Long> {
	
	Comment save(Comment comment);

	Comment findById(long id);
	
	List<Comment> findAll();
	
}
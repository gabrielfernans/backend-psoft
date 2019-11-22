package com.psoft.project.repositories;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.psoft.project.entities.Campaign;
import com.psoft.project.entities.Comment;

@Repository
public interface CommentRepository<T, ID extends Serializable> extends JpaRepository<Comment, Integer> {
	@SuppressWarnings("unchecked")
	public Comment save(Comment c); 

	public List<Comment> findByCampaign(Campaign c);
	
	public Comment findById(String id);
}

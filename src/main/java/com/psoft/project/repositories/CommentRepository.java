package com.psoft.project.repositories;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.psoft.project.entities.Comment;

@Repository
public interface CommentRepository<T, ID extends Serializable> extends JpaRepository<Comment, Integer>{
	
	//Recuperar todos os comentarios de um usario

}
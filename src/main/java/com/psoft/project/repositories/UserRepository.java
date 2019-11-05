package com.psoft.project.repositories;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.psoft.project.entities.User;

@Repository
public interface UserRepository<T, ID extends Serializable> extends JpaRepository<User, String>{
	
	User findByEmail(String email);
}

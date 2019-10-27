package com.psoft.project.services;

import org.springframework.stereotype.Service;

import com.psoft.project.entities.User;
import com.psoft.project.repositories.UserRepository;

@Service
public class UserService {
	
	private UserRepository<User, String> users;
	
	public UserService(UserRepository<User, String> users) {
		this.users = users;
	}
	
	public void setUser(User user){
		users.save(user);
	}
	

}

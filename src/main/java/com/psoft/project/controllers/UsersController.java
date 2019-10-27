package com.psoft.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.psoft.project.entities.User;
import com.psoft.project.services.UserService;

@RestController
public class UsersController {
	@Autowired
	private UserService userService;
	
	@PostMapping("/newUser")
	public void setUser(@RequestBody User user) {
		userService.setUser(user);
		userService.sendEmail(user.getEmail());
	}
	
	

}

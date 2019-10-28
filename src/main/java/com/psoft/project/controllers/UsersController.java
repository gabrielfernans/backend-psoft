package com.psoft.project.controllers;

import javax.servlet.ServletException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.psoft.project.entities.User;
import com.psoft.project.services.UserService;

@RestController
public class UsersController {
	@Autowired
	private UserService userService;
	
	@PostMapping("/newUser")
	public void setUser(@RequestBody @Valid User user) {
		userService.setUser(user);
	}
	
	@PostMapping("/forgotPassword")
	public void forgotPassword(@RequestBody String email) throws ServletException {
		userService.forgotPassword(email);
	}
	
	@PutMapping("/setPassword")
	public void newPassword(@RequestParam("token") String token, @RequestBody String newPassword) throws ServletException {
		userService.setPassword(token, newPassword);
	}
	
	

}

package com.psoft.project.controllers;

import javax.servlet.ServletException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.psoft.project.entities.User;
import com.psoft.project.services.JWTService;
import com.psoft.project.services.UserService;

@RestController
public class UsersController {
	@Autowired
	private UserService userService;
	@Autowired
	private JWTService jwtservice;
	
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
	
	@PutMapping("/modifyPassword")
	public ResponseEntity<User> modifyPassword(@RequestHeader("Authorization") String header, @RequestBody String newPassword) throws ServletException {
		if(jwtservice.userExist(header) == null) {
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}try {
			User user = jwtservice.userExist(header); 
			if(jwtservice.userHasPermission(header, user.getEmail())) {
				return new ResponseEntity<User>(userService.modifyPassword(user.getEmail(), newPassword), HttpStatus.OK);
			}
		}catch(ServletException s){
			//usuario esta com codigo invalido ou vencido
			return new ResponseEntity<User>(HttpStatus.FORBIDDEN);
		}//usuario nao tem permissao
		return new ResponseEntity<User>(HttpStatus.UNAUTHORIZED);
	}
	

}

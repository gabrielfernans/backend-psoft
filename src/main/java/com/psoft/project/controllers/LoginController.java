package com.psoft.project.controllers;

import java.sql.Date;

import javax.servlet.ServletException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.psoft.project.entities.User;
import com.psoft.project.services.UserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
public class LoginController {
	
	public final static String TOKEN_KEY = "login valido";
	
	private UserService uService;
	
	public LoginController(UserService uService) {
		this.uService = uService;
	}

	@PostMapping("/login")
	public LoginResponse authenticate(@RequestBody User user) throws ServletException{
		User authUser = uService.getUser(user.getEmail()); 
		
		if(authUser == null) throw new ServletException("user not found");
		
		checkPassword(authUser, user);
		
		String token = createToken(authUser.getEmail());
		
		return new LoginResponse(token);
	}
	
	private String createToken(String email) {
		return Jwts.builder().setSubject(email).signWith(SignatureAlgorithm.HS512, TOKEN_KEY)
				.setExpiration(new Date(System.currentTimeMillis() + 1 * 60 * 10000)).compact();
	}

	private void checkPassword(User authUser, User user) throws ServletException {
		if(!authUser.getPassword().equals(user.getPassword())) throw new ServletException("invalid password");
		
	}

	private class LoginResponse{
		public String token;
		
		public LoginResponse(String token) {
			this.token = token;
		}
	}
	

}

